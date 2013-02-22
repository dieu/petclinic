
INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT INTO pets VALUES (1, 'Leo', TO_DATE('2000-09-07', 'YYYY-MM-DD'), 1, 1);
INSERT INTO pets VALUES (2, 'Basil', TO_DATE('2002-08-06', 'YYYY-MM-DD'), 6, 2);
INSERT INTO pets VALUES (3, 'Rosy', TO_DATE('2001-04-17', 'YYYY-MM-DD'), 2, 3);
INSERT INTO pets VALUES (4, 'Jewel', TO_DATE('2000-03-07', 'YYYY-MM-DD'), 2, 3);
INSERT INTO pets VALUES (5, 'Iggy', TO_DATE('2000-11-30', 'YYYY-MM-DD'), 3, 4);
INSERT INTO pets VALUES (6, 'George', TO_DATE('2000-01-20', 'YYYY-MM-DD'), 4, 5);
INSERT INTO pets VALUES (7, 'Samantha', TO_DATE('1995-09-04', 'YYYY-MM-DD'), 1, 6);
INSERT INTO pets VALUES (8, 'Max', TO_DATE('1995-09-04', 'YYYY-MM-DD'), 1, 6);
INSERT INTO pets VALUES (9, 'Lucky', TO_DATE('1999-08-06', 'YYYY-MM-DD'), 5, 7);
INSERT INTO pets VALUES (10, 'Mulligan', TO_DATE('1997-02-24', 'YYYY-MM-DD'), 2, 8);
INSERT INTO pets VALUES (11, 'Freddy', TO_DATE('2000-03-09', 'YYYY-MM-DD'), 5, 9);
INSERT INTO pets VALUES (12, 'Lucky', TO_DATE('2000-06-24', 'YYYY-MM-DD'), 2, 10);
INSERT INTO pets VALUES (13, 'Sly', TO_DATE('2002-06-08', 'YYYY-MM-DD'), 1, 10);

INSERT INTO visits VALUES (1, 7, TO_DATE('1996-03-04', 'YYYY-MM-DD'), 'rabies shot');
INSERT INTO visits VALUES (2, 8, TO_DATE('1996-03-04', 'YYYY-MM-DD'), 'rabies shot');
INSERT INTO visits VALUES (3, 8, TO_DATE('1996-06-04', 'YYYY-MM-DD'), 'neutered');
INSERT INTO visits VALUES (4, 7, TO_DATE('1996-09-04', 'YYYY-MM-DD'), 'spayed');

EXIT;
