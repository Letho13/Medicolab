package com.medicolab.risqueservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicolab.risqueservice.model.Note;
import com.medicolab.risqueservice.model.Patient;
import com.medicolab.risqueservice.model.Risque;
import com.medicolab.risqueservice.repository.NoteClient;
import com.medicolab.risqueservice.repository.PatientClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.tartarus.snowball.ext.FrenchStemmer;

import java.io.InputStream;
import java.text.Normalizer;
import java.util.*;



@Service
public class RisqueService {

    private final PatientClient patientClient;
    private final NoteClient noteClient;

    private Risque risque;
    private Map<List<String>, String> risquesTokensMap;
    private final FrenchStemmer stemmer = new FrenchStemmer();

    public RisqueService(PatientClient patientClient, NoteClient noteClient) throws Exception {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
        this.risque = chargerRisques();
        this.risquesTokensMap = prepareRisqueTokensMap(risque.getRisque());
    }


    public String analyseRisque(Integer id) throws Exception {
        Patient patient = patientClient.getPatientById(id);

        int nombreDeRisques = nombreDeRisque(patient);
        int age = patient.getAge();
        String genre = patient.getGenre();

        if (nombreDeRisques <2) {
            return "None";
        } else if (age > 30 && nombreDeRisques >= 8) {
            return "Early onset";
        } else if ("M".equals(genre) && age < 30 && nombreDeRisques >= 5) {
            return "Early onset";
        } else if ("F".equals(genre) && age < 30 && nombreDeRisques >= 7) {
            return "Early onset";
        } else if (age > 30 && nombreDeRisques >= 6 && nombreDeRisques <= 7) {
            return "In Danger";
        } else if ("F".equals(genre) && age < 30 && nombreDeRisques == 4) {
            return "In Danger";
        } else if ("M".equals(genre) && age < 30 && nombreDeRisques >= 3 && nombreDeRisques <= 4) {
            return "In Danger";
        } else if (age > 30 && nombreDeRisques >= 2 && nombreDeRisques <= 5) {
            return "Borderline";
        } else {
            return "Pas classifié";
        }
    }


    public Risque chargerRisques() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream input = new ClassPathResource("data/Risques.JSON").getInputStream();
        return objectMapper.readValue(input, Risque.class);
    }


    public List<Note> listNotesPatient(Patient patient) {

        List<Note> notes = noteClient.getNotesByPatientId(patient.getId());
        return notes != null ? notes : Collections.emptyList();
    }


    public int nombreDeRisque(Patient patient) throws Exception {
        List<Note> notes = listNotesPatient(patient);
        Set<String> risquesDetectes = new HashSet<>();

        for (Note note : notes) {
            List<String> noteTokensStemmed = tokenizeAndStem(note.getNote());

            for (Map.Entry<List<String>, String> entry : risquesTokensMap.entrySet()) {
                List<String> risqueTokens = entry.getKey();
                String risqueOriginal = entry.getValue();

                if (containsSequence(noteTokensStemmed, risqueTokens)) {
                    risquesDetectes.add(risqueOriginal);
                }
            }
        }

        return risquesDetectes.size();
    }



//        Risque risques = chargerRisques();
//        List<String> listDeRisques = risques.getRisque();
//
//        List<Note> notes = listNotesPatient(patient);
//        Set<String> risquesDetectes = new HashSet<>();
//
//        Map<String, String> risquesStemmedMap = new HashMap<>();
//        for (String risque : listDeRisques) {
//            risquesStemmedMap.put(normalizeAndStem(risque), risque);
//        }
//
//
//        for (Note note : notes) {
//            String noteContenuNormalized = normalize(note.getNote());
//            String[] mots = noteContenuNormalized.split("\\s+");
//
//            for (String mot : mots) {
//                String motStemmed = stem(mot);
//                if (risquesStemmedMap.containsKey(motStemmed)) {
//                    risquesDetectes.add(risquesStemmedMap.get(motStemmed));
//                }
//            }
//        }
//        return risquesDetectes.size();
//    }

    private Map<List<String>, String> prepareRisqueTokensMap(List<String> risquesList) {
        Map<List<String>, String> map = new HashMap<>();
        for (String risque : risquesList) {
            List<String> tokensStemmed = tokenizeAndStem(risque);
            map.put(tokensStemmed, risque);
        }
        return map;
    }

    private List<String> tokenizeAndStem(String text) {
        String normalized = normalize(text);
        String[] mots = normalized.split("\\s+");
        List<String> stemmedTokens = new ArrayList<>();
        for (String mot : mots) {
            if (!mot.isBlank()) {
                stemmedTokens.add(stem(mot));
            }
        }
        return stemmedTokens;
    }

    private String normalize(String text) {
        if (text == null) return "";
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");          // retirer accents
        normalized = normalized.replaceAll("[\\p{Punct}]", " ");   // remplacer ponctuation par espace
        normalized = normalized.replaceAll("\\s+", " ").trim();    // unifier espaces
        return normalized.toLowerCase();
    }

    private synchronized String stem(String mot) {
        stemmer.setCurrent(mot);
        stemmer.stem();
        return stemmer.getCurrent();
    }

    /**
     * Vérifie si 'sequence' est contenue comme une sous-liste contiguë dans 'list'.
     */
    private boolean containsSequence(List<String> list, List<String> sequence) {
        if (sequence.isEmpty() || list.isEmpty() || sequence.size() > list.size()) {
            return false;
        }
        for (int i = 0; i <= list.size() - sequence.size(); i++) {
            boolean found = true;
            for (int j = 0; j < sequence.size(); j++) {
                if (!list.get(i + j).equals(sequence.get(j))) {
                    found = false;
                    break;
                }
            }
            if (found) return true;
        }
        return false;
    }

}

