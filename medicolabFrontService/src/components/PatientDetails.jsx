import React, { useEffect, useRef, useState } from "react";
import "./PatientDetail.css";
import { Link, useParams, useNavigate } from "react-router-dom";
import { deletePatient, getPatient } from "../api/PatientService.js";
import { toastError, toastSuccess } from "../api/ToastService";
import NoteList from "./NoteList";
import { getNotesByPatientId, saveNote, deleteNote } from "../api/NoteService.js";
import NoteForm from "./NoteForm";
import { getAnalyseByPatientId } from "../api/RisqueService.js";

const PatientDetails = ({ updatePatient, deletePatient }) => {
  const inputRef = useRef();
  const [patient, setPatient] = useState({
    id: "",
    nom: "",
    prenom: "",
    dateDeNaissance: "",
    genre: "",
    adresse: "",
    telephone: "",
  });

  const [notes, setNotes] = useState([]);

  const [risque, setRisque] = useState([""]);

  const navigate = useNavigate();

  const { id } = useParams();

  const fetchPatient = async (id) => {
    try {
      const { data } = await getPatient(id);
      setPatient(data);
      console.log(data);
    } catch (error) {
      console.error(error);
      toastError(error.message);
    }
  };

  const fetchNotes = async (id) => {
    try {
      const { data } = await getNotesByPatientId(id);
      setNotes(data);
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setNotes([])
      }else{
        console.error(error);
      toastError("Erreur du chargement des notes");
      }
    }
  };

  const fetchRisque = async (id) =>{
    try {
      const {data} = await getAnalyseByPatientId(id);
      setRisque(data);
    }catch (error) {
      if (error.response && error.response.status === 404){
        setRisque([])
      }else {
      console.error(error);
      toastError("Erreur de l'analyse")
      }
    }
  };

  const handleDeleteNote = async (noteId) => {
    if (!window.confirm("Voulez-vous supprimer cette note ?")) return;

    try {
      await deleteNote(noteId);
      toastSuccess("Note supprimée");
      fetchNotes(id);
    } catch (error) {
      console.error(error);
      toastError("Erreur lors de la suppression");
    }
  };

  const onChange = (event) => {
    setPatient({ ...patient, [event.target.name]: event.target.value });
  };

  const handleDeletePatient = async () => {
    if (!window.confirm("Voulez-vous vraiment supprimer ce patient?")) return;

    try {
      await deletePatient(patient.id);
      // toastSuccess("Patient supprimé");
      navigate("/patient");
    } catch (error) {
      toastError("Erreur lors de la suppression du patient");
      console.error(error);
    }
  };

  const onUpdatePatient = async (event) => {
    event.preventDefault();
    await updatePatient(patient);

    // toastSuccess("Patient Updated");
  };

  useEffect(() => {
    fetchPatient(id);
    fetchNotes(id);
    fetchRisque(id);
  }, []);

  return (
    <>
      <div className="patient-header">
        <div></div>
        <div className="patient-name">
          {patient.prenom} {patient.nom}
        </div>
        <div className="retour-container">
          <Link to={"/patient"} className="link-retour">
            <i className="bi bi-arrow-left"></i> Retour à la liste
          </Link>
        </div>
      </div>
      <div className="profile_setting">
        <div className="form-notes-container">
          <form onSubmit={onUpdatePatient} className="patient-form">
            <div className="patient-details">
              <input
                type="hidden"
                defaultValue={patient.id}
                name="id"
                required
              />
              <div className="input-box">
                <span className="details">Nom</span>
                <input
                  type="text"
                  value={patient.nom}
                  onChange={onChange}
                  name="nom"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Prenom</span>
                <input
                  type="text"
                  value={patient.prenom}
                  onChange={onChange}
                  name="prenom"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Date de Naissance</span>
                <input
                  type="date"
                  value={patient.dateDeNaissance}
                  onChange={onChange}
                  name="dateDeNaissance"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Genre</span>
                <select
                  name="genre"
                  value={patient.genre}
                  onChange={onChange}
                  required
                >
                  <option value="">-- Sélectionnez --</option>
                  <option value="F">F</option>
                  <option value="M">M</option>
                </select>
              </div>
              <div className="input-box">
                <span className="details">Adresse</span>
                <input
                  type="text"
                  value={patient.adresse}
                  onChange={onChange}
                  name="adresse"
                />
              </div>
              <div className="input-box">
                <span className="details">Telephone</span>
                <input
                  type="text"
                  value={patient.telephone}
                  onChange={onChange}
                  name="telephone"
                />
              </div>
            </div>
            <div className="form_footer">
              <button type="submit" className="btn">
                Enregistrer
              </button>
              <button
                className="btn btn-danger"
                onClick={handleDeletePatient}
                type="button"
              >
                Supprimer
              </button>
            </div>
          </form>
          <div className="notes-container">
            <NoteList notes={notes} onDelete={handleDeleteNote} />
            <NoteForm
              patId={id}
              patientName={`${patient.nom}`}
              onNoteAdded={() => fetchNotes(id)}
            />
          </div>
          <div className="risque-container">
            <h3>Analyse de Risque</h3>
            <p>
              <strong>Résultat :</strong> {risque || "En cours d'analyse..."}
            </p>
          </div>
        </div>
      </div>
    </>
  );
};

export default PatientDetails;
