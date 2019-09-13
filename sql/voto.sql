CREATE TABLE urna.voto(
    id_voto BIGSERIAL PRIMARY KEY,
    n_candidato INT REFERENCES urna.candidato(numero) NOT NULL,
    momento TIMESTAMP NOT NULL DEFAULT NOW()
);