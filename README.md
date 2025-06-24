# Medicolab
# 🩺 DiabèteCare - Gestion de dossiers patients et évaluation du risque diabétique

---

## 🔧 Technologies utilisées

<p align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white" height="25"/>  
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?logo=spring&logoColor=white" height="25"/>
  <img alt="Spring Cloud" src="https://img.shields.io/badge/Spring%20Cloud-lightgreen?logo=springboot&logoColor=white" height="25"/>
  <img alt="React" src="https://img.shields.io/badge/React-17.0.2-blue?logo=react&logoColor=white" height="25"/>
  <img alt="Vite" src="https://img.shields.io/badge/Vite-4.3.9-yellow?logo=vite&logoColor=black" height="25"/>
  <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-15.3-blue?logo=postgresql&logoColor=white" height="25"/>
  <img alt="MongoDB" src="https://img.shields.io/badge/MongoDB-6.0-green?logo=mongodb&logoColor=white" height="25"/>
  <img alt="Zipkin" src="https://img.shields.io/badge/Zipkin-Trace-purple?logo=zipkin&logoColor=white" height="25"/>
  <img alt="Eureka" src="https://img.shields.io/badge/Eureka-ServiceDiscovery-orange?logo=springboot&logoColor=white" height="25"/>
  <img alt="Spring Security" src="https://img.shields.io/badge/Spring%20Security-green?logo=springsecurity&logoColor=white" height="25"/>
</p>

---

## 📋 Description

Application microservices complète développée en Java 21 avec Spring Boot 3.4.5 et React (Vite) pour la gestion des patients et l’évaluation automatique du risque de diabète.

Fonctionnalités principales :
- Gestion des informations personnelles patients (CRUD).
- Gestion de l’historique médical et des notes.
- Calcul et génération du rapport de risque diabétique selon critères métier.
- Traçabilité des appels avec Zipkin.
- Découverte de services via Eureka.
- Passerelle API sécurisée avec Spring Cloud Gateway.

---

## 🛠️ Lancer le projet avec Docker Compose

Le projet utilise plusieurs microservices et bases de données (PostgreSQL, MongoDB), orchestrés via Docker Compose.

> ⚠️ **Note :** La base de données PostgreSQL est préinitialisée avec des données de test via les scripts dans `./postgres/init`. Il n’est donc pas nécessaire de les ajouter manuellement.

### Ports exposés

| Service            | Port local | Description                    |
|--------------------|------------|-------------------------------|
| PostgreSQL         | 5432       | Base de données patients       |
| MongoDB            | 27017      | Stockage des notes médicales   |
| Eureka Discovery   | 8761       | Service de découverte          |
| Config Server      | 8888       | Configuration centralisée      |
| Zipkin             | 9411       | Traçage distribué              |
| Patient Service    | 8080       | API gestion patients           |
| Note Service       | 8083       | API gestion notes              |
| Risque Service     | 8082       | API calcul risque diabétique   |
| Gateway Service    | 8222       | Passerelle API                 |
| Frontend (React)   | 5173       | Interface utilisateur          |

### 🔗 Liens utiles

    Documentation Spring Boot

    React

    Docker Compose

### Démarrage

```bash
docker-compose up --build

