DROP TABLE IF EXISTS tranzactie;
DROP TABLE IF EXISTS oferta;
DROP TABLE IF EXISTS licitatie;
DROP TABLE IF EXISTS produs;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS administrator;

CREATE TABLE administrator (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nume TEXT NOT NULL,
    prenume TEXT NOT NULL,
    email TEXT UNIQUE,
    username TEXT NOT NULL UNIQUE,
    departament TEXT NOT NULL
);

CREATE TABLE client (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nume TEXT NOT NULL,
    prenume TEXT NOT NULL,
    email TEXT UNIQUE,
    username TEXT NOT NULL UNIQUE,
    buget REAL NOT NULL DEFAULT 0
);


CREATE TABLE produs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nume TEXT NOT NULL,
    pret_start REAL NOT NULL,
    categorie TEXT NOT NULL
);

CREATE TABLE licitatie (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    produs_id INTEGER NOT NULL,
    status TEXT NOT NULL,
    data_start TEXT,
    data_sfarsit TEXT,
    FOREIGN KEY (produs_id) REFERENCES produs(id)
);

CREATE TABLE oferta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    suma REAL NOT NULL,
    data TEXT,
    status TEXT NOT NULL,
    client_id INTEGER NOT NULL,
    licitatie_id INTEGER NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (licitatie_id) REFERENCES licitatie(id)
);

CREATE TABLE tranzactie (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    client_id INTEGER NOT NULL,
    produs_id INTEGER NOT NULL,
    pret_final REAL NOT NULL,
    data TEXT,
    FOREIGN KEY (client_id) REFERENCES client(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
FOREIGN KEY (produs_id) REFERENCES produs(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);