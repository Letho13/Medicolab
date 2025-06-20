import React, { useState } from 'react'

function PatientForm() {
    const[patient, setPatient] = useState({nom:'',prenom:'', dateDeNaissance : '', genre:'', adresse:'', telephone:''});


    const handleSubmit = (event) => {
    event.preventDefault();
    fetch('http://localhost:8222/PATIENT-SERVICE/api/patient',{
        method:'POST',
        headers:{
            'Content-Type': 'application/json',
        },
        body:JSON.stringify(patient)
    })
    .then(response => response.json())
    .then(data => console.log('Patient crée:', data))
    .catch(error => console.error('Erreur création Patient:', error))
};

    const handleChange = (event) => {
        const {name, value} = event.target;
        setPatient(prevPatient => ({...prevPatient, [name]: value}));
    };


  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        name="nom"
        value={patient.nom}
        onChange={handleChange}
        placeholder="Nom"
      />
      <input
        type="text"
        name="prenom"
        value={patient.prenom}
        onChange={handleChange}
        placeholder="Prenom"
      />
      <input
        type="date"
        name="dateDeNaissance"
        value={patient.dateDeNaissance}
        onChange={handleChange}
        placeholder="Date de Naissance"
      />
      <label>Genre</label>
      <select
        name="genre"
        value={patient.genre}
        onChange={handleChange}
        required
      >
        <option value="">-- Sélectionnez --</option>
        <option value="F">F</option>
        <option value="M">M</option>
      </select>
      <input
        type="text"
        name="adresse"
        value={patient.adresse}
        onChange={handleChange}
        placeholder="Adresse"
      />
      <input
        type="text"
        name="telephone"
        value={patient.telephone}
        onChange={handleChange}
        placeholder="Telephone"
      />
      <button type="submit">Soumettre</button>
    </form>
  );

}
export default PatientForm