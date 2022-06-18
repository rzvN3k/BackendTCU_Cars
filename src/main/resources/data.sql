CREATE TABLE car_informations (
   car_vin VARCHAR(255) NOT NULL,
   car_model VARCHAR(255) NULL,
   car_release_date datetime NULL,
   car_colour VARCHAR(255) NULL,
   car_number_of_doors SMALLINT NULL,
   car_engine_power INT NULL,
   car_cylindrical_capacity INT NULL,
   car_mapped_to_account BIT NULL DEFAULT 0,
   car_security_mapping_key VARCHAR(255) NULL,
   car_owner_email VARCHAR(255) NULL,
   car_registration_status BIT NULL DEFAULT 0,
   CONSTRAINT pk_carinformations PRIMARY KEY (car_vin)
);

CREATE TABLE car_linkage_to_account_token (
  confirmation_token_id BIGINT NOT NULL,
  confirmation_token VARCHAR(255) NULL,
  token_creation_date datetime NULL,
  CONSTRAINT pk_carlinkagetoaccounttoken PRIMARY KEY (confirmation_token_id)
);


INSERT
INTO
  car_informations
  (car_VIN, car_model, car_release_date, car_colour, car_number_of_doors, car_engine_power, car_cylindrical_capacity, car_mapped_to_account, car_security_mapping_key, car_owner_email, car_registration_status)
VALUES
  ('WDDTEST00000', 'TestModel', NOW(), 'TestColour', 5, 384, 3000, 0, 'TESTCODEXXXXX', '', 0);