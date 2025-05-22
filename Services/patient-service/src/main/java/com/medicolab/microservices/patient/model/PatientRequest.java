package com.medicolab.microservices.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PatientRequest {


    private Integer id;

    @NotBlank(message = "Un nom est obligatoire")
    private String nom;

    @NotBlank(message = "Un prénom est obligatoire")
    private String prenom;

    @NotNull(message = "Une date de naissance est obligatoire")
    private String dateDeNaissance;

    @NotBlank(message = "Un genre est obligatoire")
    private String genre;

    private String adresse;
    private String telephone;

    public PatientRequest() {
    }

    public PatientRequest(Integer id, String nom, String prenom, String dateDeNaissance, String genre, String adresse, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.genre = genre;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "Un nom est obligatoire") String getNom() {
        return nom;
    }

    public void setNom(@NotBlank(message = "Un nom est obligatoire") String nom) {
        this.nom = nom;
    }

    public @NotBlank(message = "Un prénom est obligatoire") String getPrenom() {
        return prenom;
    }

    public void setPrenom(@NotBlank(message = "Un prénom est obligatoire") String prenom) {
        this.prenom = prenom;
    }

    public @NotNull(message = "Une date de naissance est obligatoire") String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(@NotNull(message = "Une date de naissance est obligatoire") String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public @NotBlank(message = "Un genre est obligatoire") String getGenre() {
        return genre;
    }

    public void setGenre(@NotBlank(message = "Un genre est obligatoire") String genre) {
        this.genre = genre;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "PatientRequest{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateDeNaissance='" + dateDeNaissance + '\'' +
                ", genre='" + genre + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
