CREATE TABLE IF NOT EXISTS parties (
uuid UUID NOT NULL,
numero_partie INTEGER,
contrat_id INTEGER,
preneur_id INTEGER,
appel_id INTEGER,
mort_id INTEGER,
est_fait BOOLEAN,
score INTEGER,
petit_au_bout_id INTEGER,
chelem BOOLEAN,
capot BOOLEAN,
CONSTRAINT pk_parties PRIMARY KEY (uuid)
);

