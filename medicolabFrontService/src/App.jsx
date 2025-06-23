import PatientList from "./components/PatientList";
import { getPatients, savePatient } from "./api/PatientService.js";
import { useState, useEffect, useRef } from "react";
import Header from "./components/Header";
import "./App.css";
import {Navigate, Route, Routes, useNavigate} from "react-router-dom";
import PatientDetails from "./components/PatientDetails";
import { toastError } from "./api/ToastService";
import { ToastContainer } from "react-toastify";
import { updatePatient as apiUpdatePatient } from "./api/PatientService.js";
import { deletePatient as apiDeletePatient } from "./api/PatientService.js";
import { login, getToken, logout} from "./api/AuthService.jsx";
import PrivateRoute from "./components/PrivateRoute";
import Unauthorized from './components/Unauthorized';

function App() {
  const modalRef = useRef();
  const navigate = useNavigate();

  const username = import.meta.env.VITE_LOGIN_USERNAME;
  const password = import.meta.env.VITE_LOGIN_PASSWORD;

  const [data, setData] = useState([]);
  const [values, setValues] = useState({
    nom: "",
    prenom: "",
    dateDeNaissance: "",
    genre: "",
    adresse: "",
    telephone: "",
  });
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = getToken();

    if (token) {
      setIsAuthenticated(true);
      getAllPatients();
    } else {
      login(username, password)
          .then(data => {
            console.log("Login réussi, réponse complète :", data);
            if (data.token) {
              setIsAuthenticated(true);
              getAllPatients();
            } else {
              console.error("Aucun token dans la réponse de login !");
              setIsAuthenticated(false);
              toastError("Erreur d'authentification : token manquant");
            }
          })
          .catch((err) => {
            console.error("Échec de l'authentification automatique", err);
            toastError("Erreur d'authentification");
            setIsAuthenticated(false);
          });
    }
  }, []);

  const handleLogout = () => {
    logout();
    setIsAuthenticated(false);
    navigate("/");
  };

  const getAllPatients = async () => {
    try {
      const response = await getPatients();
      setData(response.data);
      console.log(response.data);
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setData(0)
      } else {
      console.log(error);
      toastError(error.message);
      }
    }
  };

  const onChange = (event) => {
    setValues({ ...values, [event.target.name]: event.target.value });
  };

  const handleNewPatient = async (event) => {
    event.preventDefault();
    try {
      const { data } = await savePatient(values);
      toggleModal(false);
      setValues({
        nom: "",
        prenom: "",
        dateDeNaissance: "",
        genre: "",
        adresse: "",
        telephone: "",
      });
      getAllPatients();
    } catch (error) {
      console.log(error);
      toastError(error.message);
    }
  };

  const updatePatient = async (patient) => {
    try {
      const { data } = await apiUpdatePatient(patient);
      console.log(data);
    } catch (error) {
      console.log(error);
      toastError(error.message);
    }
  };

  const deletePatient = async (id) => {
    try {
      await apiDeletePatient(id);
      getAllPatients();
    } catch (error) {
      console.log(error);
      toastError(error.message);
    }
  };

  const toggleModal = (show) =>
    show ? modalRef.current.showModal() : modalRef.current.close();

  return (
    <>
      <Header toggleModal={toggleModal} nbOfPatients={data.length} />
      <main className="main">
        <div className="container">
          <Routes>

            <Route path="/" element={<Navigate to={"/patient"} />} />

            <Route
              path="/patient"
              element={
                <PrivateRoute>
                <>
                  <PatientList data={data} />

                  <div style={{ marginTop: "2rem", textAlign: "center" }}>
                    <button onClick={() => toggleModal(true)} className="btn">
                      <i className="bi bi-plus-square"></i> Ajouter un nouveau
                      patient
                    </button>
                  </div>
                </>
                  </PrivateRoute>
              }
            />

            <Route
              path="/patient/:id"
              element={
                <PrivateRoute>
                <PatientDetails
                  updatePatient={updatePatient}
                  deletePatient={deletePatient}
                />
                </PrivateRoute>
              }
            />
            <Route path="/unauthorized" element={<Unauthorized />} />
          </Routes>
        </div>
      </main>

      {/* Modal */}
      <dialog ref={modalRef} className="modal" id="modal">
        <div className="modal__header">
          <h3>Nouveau Patient</h3>
        </div>
        <div className="divider"></div>
        <div className="modal__body">
          <form onSubmit={handleNewPatient}>
            <div className="patient-details">
              <div className="input-box">
                <span className="details">Nom</span>
                <input
                  type="text"
                  value={values.nom}
                  onChange={onChange}
                  name="nom"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Prenom</span>
                <input
                  type="text"
                  value={values.prenom}
                  onChange={onChange}
                  name="prenom"
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Date de Naissance</span>
                <input
                  type="date"
                  value={values.dateDeNaissance}
                  onChange={onChange}
                  name="dateDeNaissance"
                  required
                />
              </div>
             <div className="input-box">
                <span className="details">Genre</span>
                <select
                  name="genre"
                  value={values.genre}
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
                  value={values.adresse}
                  onChange={onChange}
                  name="adresse"
                />
              </div>
              <div className="input-box">
                <span className="details">telephone</span>
                <input
                  type="text"
                  value={values.telephone}
                  onChange={onChange}
                  name="telephone"
                />
              </div>
            </div>
            <div className="form_footer">
              <button
                onClick={() => toggleModal(false)}
                type="button"
                className="btn btn-danger"
              >
                Cancel
              </button>
              <button type="submit" className="btn">
                Enregistrer
              </button>
            </div>
          </form>
        </div>
      </dialog>

      <ToastContainer />
    </>
  );
}

export default App;
