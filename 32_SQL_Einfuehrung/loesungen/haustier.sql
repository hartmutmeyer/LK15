DROP SCHEMA IF EXISTS haustier;

# Übung 1, Aufgabe 1

CREATE SCHEMA haustier DEFAULT CHARACTER SET utf8;
USE haustier;
CREATE TABLE tier
(
	name VARCHAR(20) NOT NULL,
	tierart VARCHAR(20) NOT NULL,
	lebendig ENUM('ja','nein') DEFAULT 'ja',
	geschlecht ENUM('weiblich','männlich') DEFAULT 'weiblich',
	geburtstag DATE,
	todestag DATE
);
#DESCRIBE tier;

# Übung 1, Aufgabe 2
# 2A
INSERT INTO tier VALUES
('Bello', 'Hund',          'ja',   'männlich', '2003-05-01', NULL),
('Daisy', 'Kanarienvogel', 'nein', 'weiblich', '1996-12-06', '2004-08-17'),
('Mausi', 'Katze',         'ja',   'weiblich', '2002-11-17', NULL);
# 2B
INSERT INTO tier (name, tierart) VALUES
('Daisy',  'Schildkröte'),
('Lassie', 'Hund'),
('Maja',   'Hund');
# 2C
INSERT INTO tier (name, tierart, geschlecht) VALUES
('Hasso',  'Hund',    'männlich'),
('Blacky', 'Katze',   'männlich'),
('Harald', 'Hamster', 'männlich');
# 2D
UPDATE tier
SET geburtstag = '2003-12-06'
WHERE tierart = 'Schildkröte';

UPDATE tier
SET geburtstag = '2004-04-23'
WHERE geburtstag IS NULL;

UPDATE tier
SET geburtstag = '2001-07-29', todestag = '2003-09-15', lebendig = 'nein'
WHERE name = 'Harald';

# Übung 1, Aufgabe 3
# 3A) Zeige die gesamte Tabelle tier an.
#SELECT *
#FROM tier;

# 3B) Zeige nur die Spalten name und tierart an.
#SELECT name, tierart
#FROM tier;

# 3C) Liste die Spalten name und tierart auf. Sortiere dabei in aufsteigender alphabetischer
#     Reihenfolge nach dem Namen. An zweiter Stelle soll nach der Tierart sortiert werden
#     (auch aufsteigend).
#SELECT name, tierart
#FROM tier
#ORDER BY name, tierart ASC;

# 3D) Zeige nur die Spalte geburtstag an. Sorge dafür, dass jeder Wert nur einmal angezeigt wird.
#SELECT geburtstag
#FROM tier
#GROUP BY geburtstag;

# 3E) Zeige die Spalten name und tierart für die Tiere an, die noch leben.
#SELECT name, tierart
#FROM tier
#WHERE lebendig = 'ja';

# 3F) Liste die Namen aller Tiere auf, die vor dem Jahr 2004 geboren wurden und noch nicht
#     tot sind. Sortiere die Namen in absteigender alphabetischer Reihenfolge.
#SELECT name
#FROM tier
#WHERE geburtstag < '2004-01-01'
#AND todestag IS NULL
#ORDER BY name DESC;

# 3G) Liste die gesamten Spalten aller Tiere auf, die weder Hund noch Katze sind.
#SELECT *
#FROM tier
#WHERE tierart <> 'Hund'
#AND tierart <> 'Katze';

# 3H) Zeige die Namen und die Geburtstage aller Tiere an, für die kein Todestag angegeben wurde.
#SELECT name, geburtstag
#FROM tier
#WHERE todestag IS NULL;

# 3I) Zähle wie viele Tiere nach dem 01.01.2003 geboren wurden.
#SELECT COUNT(*)
#FROM tier
#WHERE geburtstag > '2003-01-01';

# 3J) Liste auf, wie viele Tiere es von jeder Tierart gibt. Das Ergebnis soll eine Tabelle
#     mit einer Spalte tierart sein und einer Spalte, die zu jeder Tierart die Anzahl angibt.
#SELECT COUNT(*), tierart
#FROM tier
#GROUP BY tierart;

# 3K) Liste alle Tierarten auf, von denen es zwei oder mehr Tiere gibt.
#SELECT tierart
#FROM tier
#GROUP BY tierart
#HAVING COUNT(*)>1;

# 3L) Zähle die Anzahl der unterschiedlichen Tierarten, die es gibt.
#SELECT COUNT(DISTINCT tierart)
#FROM tier;

# Übung 2, Aufgabe 1

CREATE TABLE besitzer
(
	besitzer_id INT AUTO_INCREMENT,
	anrede ENUM('Herr', 'Frau', 'Firma') DEFAULT 'Herr',
	vorname VARCHAR(20),
	nachname VARCHAR(20) NOT NULL,
	straße VARCHAR(30),
	plz INT,
	ort VARCHAR(30),
	telefonnr VARCHAR(20),
	PRIMARY KEY (besitzer_id)
);

ALTER TABLE tier 
	ADD COLUMN (tier_besitzer_id INT),
	ADD FOREIGN KEY (tier_besitzer_id) REFERENCES besitzer (besitzer_id)
;

# Übung 3, Aufgabe 1

