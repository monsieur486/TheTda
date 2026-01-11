CREATE TABLE IF NOT EXISTS joueurs (
uuid UUID NOT NULL,
reunion_uuid UUID,
ami_id INTEGER,
CONSTRAINT pk_joueurs PRIMARY KEY (uuid)
);

