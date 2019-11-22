CREATE TABLE urna.candidato
(
   numero integer, 
   n_partido integer, 
   nome text, 
   cargo integer, 
   CONSTRAINT numero PRIMARY KEY (numero), 
   CONSTRAINT n_partido FOREIGN KEY (n_partido) REFERENCES urna.partido (numero) ON UPDATE CASCADE ON DELETE CASCADE
);