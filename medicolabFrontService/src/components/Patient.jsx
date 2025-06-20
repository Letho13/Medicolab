import React from "react";
import { Link } from 'react-router-dom'


const Patient = ({patient}) => {
  return (
    <Link to={'/patient/${patient.id}'} className="patient_item">
      <div className="patient_header"></div>
      <div className="patient_details">
        <p className="patient_nom">{patient.nom.substring(0,15)} </p>
        <p className="patient_prenom">{patient.prenom}</p>
      </div>
      <div className="patient_body">
        <p><i className="bi bi-envelope"></i> {patient.dateDeNaissance}</p>
        <p><i className="bi bi-telephone"></i> {patient.genre}</p>
        <p><i className="bi bi-geo"></i>{patient.adresse}</p>
        <p><i className="bi bi-telephone"></i> {patient.telephone}</p>
      </div>
    </Link>
  );
};

export default Patient;
