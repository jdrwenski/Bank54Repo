CREATE DATABASE Bank54DB;
USE Bank54DB;

CREATE TABLE Kunde (
	KundenNr INT AUTO_INCREMENT,
	Nachname VARCHAR(30) NOT NULL,
	Vorname VARCHAR(20) NOT NULL,
	Geburtsdatum DATE NOT NULL,
	Adresse VARCHAR(200) NOT NULL,
	Telefon VARCHAR(20),
	PRIMARY KEY (KundenNr)
);

CREATE TABLE Konto (
	KontoNr VARCHAR(10),
	InhaberNr INT NOT NULL,
	PRIMARY KEY (KontoNr),
	FOREIGN KEY (InhaberNr) 
		REFERENCES Kunde (KundenNr)
);

CREATE TABLE Buchung (
	Id BIGINT AUTO_INCREMENT,
	Betrag DECIMAL(16,2) NOT NULL,
	Verwendungszweck VARCHAR(140) DEFAULT '',
	Datum DATE NOT NULL DEFAULT CURRENT_DATE,
	KontoNr VARCHAR(10) NOT NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY (KontoNr)
		REFERENCES Konto (KontoNr)
);

ALTER TABLE Kunde AUTO_INCREMENT = 12345678;

INSERT INTO Kunde 
(Nachname,Vorname,Geburtsdatum,Adresse)
VALUES
('Mustermann','Max','1980-05-23','Fichtenallee 12, 50670 K�ln'),
('Musterfrau','Anna','1982-12-01','Domstr. 5, 50999 K�ln'),
('Schmidt','Lisa','2000-10-30','Kirchweg 78, 51149 K�ln');

INSERT INTO Konto
VALUES
('1234567810',12345678),
('1234567812',12345678),
('1234568001',12345680);

INSERT INTO Buchung
(Betrag,Verwendungszweck,Datum,KontoNr)
VALUES
(-99.98,'Amazon','2019-11-15','1234567810'),
(-123.45,'Versicherung','2019-12-15','1234567810'),
(-500,'Miete','2019-12-31','1234567810'),
(250,'Gutschrift','2020-01-01','1234567810'),
(2500,'Gehalt','2020-01-31','1234567812'),
(-64.50,'Strom','2020-02-02','1234567810');


CREATE FUNCTION getKontostand(nummer VARCHAR(10)) 
	RETURNS DECIMAL(16,2) DETERMINISTIC
RETURN (
	SELECT SUM(Betrag) FROM Buchung
	WHERE KontoNr = nummer
);
 

ALTER TABLE Konto ADD Guthaben DECIMAL(16,2) NOT NULL DEFAULT 0.0;
UPDATE Konto SET Guthaben = getKontostand(KontoNr);

CREATE TRIGGER Guthaben_aktualisieren
AFTER INSERT ON Buchung
FOR EACH ROW
	UPDATE Konto
	SET Guthaben = Guthaben + NEW.Betrag
	WHERE KontoNr = NEW.KontoNr;
