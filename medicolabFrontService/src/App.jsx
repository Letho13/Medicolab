import { useState, useEffect, useRef } from "react";
import { Routes, Route, Navigate, useNavigate, useLocation } from "react-router-dom";

import PatientList from "./components/PatientList";
import PatientDetails from "./components/PatientDetails";
import Header from "./components/Header";
import PrivateRoute from "./components/PrivateRoute";
import Unauthorized from "./components/Unauthorized";

import { getPatients, savePatient, updatePatient as apiUpdatePatient, deletePatient as apiDeletePatient } from "./api/PatientService";
import { login, getToken, logout } from "./api/AuthService";
import { toastError, toastSuccess } from "./api/ToastService";

import { ToastContainer } from "react-toastify";
import "./App.css";

function App() {
  const modalRef = useRef();
  const navigate = useNavigate();
  const location = useLocation();

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
          .then((res) => {
            if (res.token) {
              setIsAuthenticated(true);
              getAllPatients();
            } else {
              toastError("Erreur d'authentification : token manquant");
              navigate("/unauthorized");
            }
          })
          .catch((err) => {
            toastError("Erreur d'authentification");
            navigate("/unauthorized");
          });
    }
  }, []);


  useEffect(() => {
    if (isAuthenticated && location.pathname === "/patient") {
      getAllPatients();
    }
  }, [location.pathname, isAuthenticated]);


  const getAllPatients = async () => {
    const token = getToken();
    if (!token) {
      toastError("Token manquant ou expiré. Veuillez vous reconnecter.");
      setIsAuthenticated(false);
      navigate("/unauthorized");
      return;
    }

    try {
      const response = await getPatients();
      setData(response.data);
    } catch (error) {
      if (error.response?.status === 401) {
        toastError("Session expirée. Reconnexion nécessaire.");
        setIsAuthenticated(false);
        logout();
        navigate("/unauthorized");
      } else {
        toastError("Erreur lors de la récupération des patients.");
        console.error(error);
      }
    }
  };

  const handleLogout = () => {
    logout();
    setIsAuthenticated(false);
    navigate("/");
  };

  const onChange = (event) => {
    setValues({ ...values, [event.target.name]: event.target.value });
  };

  const toggleModal = (show) =>
      show ? modalRef.current.showModal() : modalRef.current.close();

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
      const { data: updatedPatient } = await apiUpdatePatient(patient);
      setData((prev) =>
          prev.map((p) => (p.id === updatedPatient.id ? updatedPatient : p))
      );
      toastSuccess("Patient mis à jour.");
    } catch (error) {
      toastError("Erreur lors de la mise à jour du patient.");
      console.error(error);
    }
  };

  const deletePatient = async (id) => {
    try {
      await apiDeletePatient(id);
      setData((prev) => prev.filter((p) => p.id !== id));
      toastSuccess("Patient supprimé.");
    } catch (error) {
      toastError("Erreur lors de la suppression du patient.");
      console.error(error);
    }
  };

  return (
      <>
        <Header toggleModal={toggleModal} nbOfPatients={data.length} onLogout={handleLogout} />

        <main className="main">
          <div className="container">
            <Routes>
              <Route path="/" element={<Navigate to="/patient" />} />
              <Route
                  path="/patient"
                  element={
                    <PrivateRoute>
                      <>
                        <PatientList data={data} />
                        <div style={{ marginTop: "2rem", textAlign: "center" }}>
                          <button onClick={() => toggleModal(true)} className="btn">
                            <i className="bi bi-plus-square"></i> Ajouter un nouveau patient
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
                      <PatientDetails updatePatient={updatePatient} deletePatient={deletePatient} />
                    </PrivateRoute>
                  }
              />
              <Route path="/unauthorized" element={<Unauthorized />} />
            </Routes>
          </div>
        </main>

        {/* Modal création patient */}
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
                  <input type="text" name="nom" value={values.nom} onChange={onChange} required />
                </div>
                <div className="input-box">
                  <span className="details">Prenom</span>
                  <input type="text" name="prenom" value={values.prenom} onChange={onChange} required />
                </div>
                <div className="input-box">
                  <span className="details">Date de Naissance</span>
                  <input type="date" name="dateDeNaissance" value={values.dateDeNaissance} onChange={onChange} required />
                </div>
                <div className="input-box">
                  <span className="details">Genre</span>
                  <select name="genre" value={values.genre} onChange={onChange} required>
                    <option value="">-- Sélectionnez --</option>
                    <option value="F">F</option>
                    <option value="M">M</option>
                  </select>
                </div>
                <div className="input-box">
                  <span className="details">Adresse</span>
                  <input type="text" name="adresse" value={values.adresse} onChange={onChange} />
                </div>
                <div className="input-box">
                  <span className="details">Téléphone</span>
                  <input type="text" name="telephone" value={values.telephone} onChange={onChange} />
                </div>
              </div>
              <div className="form_footer">
                <button type="button" onClick={() => toggleModal(false)} className="btn btn-danger">
                  Annuler
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

