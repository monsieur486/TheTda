CREATE TABLE IF NOT EXISTS reunions (
uuid UUID NOT NULL,
est_active BOOLEAN,
status INTEGER,
CONSTRAINT pk_reunions PRIMARY KEY (uuid)
);

