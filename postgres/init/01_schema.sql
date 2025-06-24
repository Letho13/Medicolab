CREATE TABLE IF NOT EXISTS patient (
                          id SERIAL PRIMARY KEY,
                          nom TEXT,
                          prenom TEXT,
                          date_naissance DATE,
                          genre CHAR(1),
                          adresse TEXT,
                          telephone TEXT
);