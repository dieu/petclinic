
CREATE TABLE vets (
  id NUMBER(10) NOT NULL,
  first_name VARCHAR2(30),
  last_name VARCHAR2(30),
  CONSTRAINT vets_pk PRIMARY KEY (id)
);
CREATE INDEX vets_last_name ON vets(last_name);
CREATE SEQUENCE vets_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER vets_ai
  BEFORE INSERT ON vets
  FOR EACH ROW
BEGIN
  :new.id := vets_seq.nextval;
END;
/

CREATE TABLE specialties (
  id NUMBER(10) NOT NULL,
  name VARCHAR2(80),
  CONSTRAINT specialties_pk PRIMARY KEY (id)
);
CREATE INDEX specialties_name ON specialties(name);
CREATE SEQUENCE specialties_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER specialties_ai
  BEFORE INSERT ON specialties
  FOR EACH ROW
BEGIN
  :new.id := specialties_seq.nextval;
END;
/

CREATE TABLE vet_specialties (
  vet_id NUMBER(10) NOT NULL,
  specialty_id NUMBER(10) NOT NULL,
  FOREIGN KEY (vet_id) REFERENCES vets(id),
  FOREIGN KEY (specialty_id) REFERENCES specialties(id),
  CONSTRAINT vet_specialties_uniq UNIQUE (vet_id,specialty_id)
);

CREATE TABLE types (
  id NUMBER(10) NOT NULL,
  name VARCHAR2(80),
  CONSTRAINT types_pk PRIMARY KEY (id)
);
CREATE INDEX types_name ON types(name);
CREATE SEQUENCE types_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER types_ai
  BEFORE INSERT ON types
  FOR EACH ROW
BEGIN
  :new.id := types_seq.nextval;
END;
/

CREATE TABLE owners (
  id NUMBER(10) NOT NULL,
  first_name VARCHAR2(30),
  last_name VARCHAR2(30),
  address VARCHAR2(255),
  city VARCHAR2(80),
  telephone VARCHAR2(20),
  CONSTRAINT owners_pk PRIMARY KEY (id)
);
CREATE INDEX owners_last_name ON owners(last_name);
CREATE SEQUENCE owners_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER owners_ai
  BEFORE INSERT ON owners
  FOR EACH ROW
BEGIN
  :new.id := owners_seq.nextval;
END;
/

CREATE TABLE pets (
  id NUMBER(10) NOT NULL,
  name VARCHAR2(30),
  birth_date DATE,
  type_id NUMBER(10) NOT NULL,
  owner_id NUMBER(10) NOT NULL,
  FOREIGN KEY (owner_id) REFERENCES owners(id),
  FOREIGN KEY (type_id) REFERENCES types(id),
  CONSTRAINT pets_pk PRIMARY KEY (id)
);
CREATE INDEX pets_name ON pets(name);
CREATE SEQUENCE pets_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER pets_ai
  BEFORE INSERT ON pets
  FOR EACH ROW
BEGIN
  :new.id := pets_seq.nextval;
END;
/

CREATE TABLE visits (
  id NUMBER(10) NOT NULL,
  pet_id NUMBER(10) NOT NULL,
  visit_date DATE,
  description VARCHAR2(255),
  FOREIGN KEY (pet_id) REFERENCES pets(id),
  CONSTRAINT visits_pk PRIMARY KEY (id)
);
CREATE SEQUENCE visits_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER visits_ai
  BEFORE INSERT ON visits
  FOR EACH ROW
BEGIN
  :new.id := visits_seq.nextval;
END;
/

EXIT;
