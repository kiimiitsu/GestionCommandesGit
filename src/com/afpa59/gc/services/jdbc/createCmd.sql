CREATE TABLE article
(
	id INT GENERATED BY DEFAULT AS IDENTITY,
	libelle VARCHAR(50),
	prix FLOAT,
	PRIMARY KEY (id)
)

CREATE TABLE client
(
	id INT GENERATED BY DEFAULT AS IDENTITY,
	nom VARCHAR(50),
	prenom VARCHAR(50),
	adresse VARCHAR(50),
	PRIMARY KEY (id)
)

CREATE TABLE commande
(
	id INT GENERATED BY DEFAULT AS IDENTITY,
	client_id INT,
	PRIMARY KEY (id),
	FOREIGN KEY (client_id) REFERENCES client
)

CREATE TABLE ligne_commande
(
	id INT GENERATED BY DEFAULT AS IDENTITY,
	commande_id INT,
	article_id INT,
	quantite INT,
	PRIMARY KEY (id, commande_id),
	FOREIGN KEY (commande_id) REFERENCES commande,
	FOREIGN KEY (article_id) REFERENCES article
)