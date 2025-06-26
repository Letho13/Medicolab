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

/**
 * Service permettant d'analyser le niveau de risque d'un patient
 * en fonction de ses notes médicales et de ses informations personnelles.
 */
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

    /**
     * Analyse le risque du patient en fonction de ses notes, son âge et son genre.
     *
     * @param id identifiant du patient
     * @return le niveau de risque : None, Borderline, In Danger, Early onset, etc.
     * @throws Exception en cas d'erreur lors de la récupération des données
     */
    public String analyseRisque(Integer id) throws Exception {
        Patient patient = patientClient.getPatientById(id);

        int nombreDeRisques = nombreDeRisque(patient);
        int age = patient.getAge();
        String genre = patient.getGenre();

        if (nombreDeRisques < 2) {
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

    /**
     * Charge les risques à partir du fichier JSON situé dans les ressources.
     *
     * @return un objet Risque contenant les mots-clés de détection
     * @throws Exception si le fichier est introuvable ou mal formé
     */
    public Risque chargerRisques() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream input = new ClassPathResource("data/Risques.JSON").getInputStream();
        return objectMapper.readValue(input, Risque.class);
    }


    public List<Note> listNotesPatient(Patient patient) {
        List<Note> notes = noteClient.getNotesByPatientId(patient.getId());
        return notes != null ? notes : Collections.emptyList();
    }

    /**
     * Calcule le nombre de risques détectés dans les notes du patient.
     *
     * @param patient patient à analyser
     * @return nombre de risques détectés
     * @throws Exception en cas d'erreur d'analyse
     */
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

    // Prépare une map de risques avec leur version tokenisée + stemmée
    private Map<List<String>, String> prepareRisqueTokensMap(List<String> risquesList) {
        Map<List<String>, String> map = new HashMap<>();
        for (String risque : risquesList) {
            List<String> tokensStemmed = tokenizeAndStem(risque);
            map.put(tokensStemmed, risque);
        }
        return map;
    }

    // Tokenise et applique le stemming sur un texte
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

    // Nettoie et normalise un texte
    private String normalize(String text) {
        if (text == null) return "";
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");          // retirer accents
        normalized = normalized.replaceAll("[\\p{Punct}]", " ");   // remplacer ponctuation par espace
        normalized = normalized.replaceAll("\\s+", " ").trim();    // unifier espaces
        return normalized.toLowerCase();
    }

    // Applique l'algorithme de stemming (racine du mot)
    private synchronized String stem(String mot) {
        stemmer.setCurrent(mot);
        stemmer.stem();
        return stemmer.getCurrent();
    }

    /**
     * Vérifie si une séquence de mots est contenue dans une liste de tokens.
     * Utilisé pour détecter si une expression indicative de risque est présente.
     *
     * @param list liste de mots
     * @param sequence séquence à rechercher
     * @return vrai si la séquence est trouvée dans la liste
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
