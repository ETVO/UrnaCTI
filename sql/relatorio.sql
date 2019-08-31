SELECT COUNT(v.id_voto) FROM urna.candidato AS c INNER JOIN urna.voto AS v ON c.numero = v.n_candidato GROUP BY c.nome
