CREATE DATABASE vehicleDatabase;

USE vehicleDatabase;

CREATE TABLE vehicles
(
   ID Integer NOT NULL AUTO_INCREMENT,
   PS Integer NOT NULL,
   Color varChar(255) NOT NULL,
   Brand varChar(255) NOT NULL,
   Price Integer NOT NULL,
   ExtraEquipment varChar(255),
   PRIMARY KEY (ID)
);
