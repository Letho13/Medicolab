import React from "react";

import { useNavigate } from 'react-router-dom';

const PatientList = ({ data }) => {
  const navigate = useNavigate();

  if (!data || data.length === 0) {
    return <div>No Patients. Please add a new patient</div>;
  }

 return (
    <main>
      <h2>Liste des Patients</h2>
      <table border="1" cellPadding="8" style={{ borderCollapse: "collapse", width: "100%" }}>
        <thead>
          <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Date de Naissance</th>
            <th>Genre</th>
            <th>Adresse</th>
            <th>Téléphone</th>
          </tr>
        </thead>
        <tbody>
          {data.map((patient) => (
            <tr
              key={patient.id}
              onClick={() => navigate(`/patient/${patient.id}`)}
              style={{ cursor: "pointer", backgroundColor: "#fff" }}
              onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = "#f0f0f0")}
              onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = "#fff")}
            >
              <td>{patient.nom}</td>
              <td>{patient.prenom}</td>
              <td>{patient.dateDeNaissance}</td>
              <td>{patient.genre}</td>
              <td>{patient.adresse}</td>
              <td>{patient.telephone}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  );
};

export default PatientList;
