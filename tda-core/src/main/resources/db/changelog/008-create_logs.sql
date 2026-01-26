CREATE TABLE IF NOT EXISTS logs_tda (
uuid UUID NOT NULL,
date_creation TIMESTAMP WITHOUT TIME ZONE,
action_code INTEGER,
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
CONSTRAINT pk_logs_tda PRIMARY KEY (uuid)
);

