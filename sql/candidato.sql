CREATE TABLE urna.candidato (
    numero INT PRIMARY KEY,
    n_partido INT REFERENCES urna.partido(numero) NOT NULL ON UPDATE CASCADE,
    nome TEXT NOT NULL,
    cargo INT NOT NULL
);