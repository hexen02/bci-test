DROP TABLE IF EXISTS TBL_PHONE;
DROP TABLE IF EXISTS TBL_USER;

CREATE TABLE TBL_USER (
ID INT PRIMARY KEY,
NAME VARCHAR(100) NOT NULL,
EMAIL VARCHAR(100) NOT NULL,
PASSWORD VARCHAR(100) NOT NULL,
TOKEN VARCHAR(250) NOT NULL);

CREATE TABLE TBL_PHONE (
ID_USER INT,
NUMBER VARCHAR(15) PRIMARY KEY,
CITY_CODE VARCHAR(5) NOT NULL,
COUNTRY_CODE VARCHAR(5) NOT NULL,
FOREIGN KEY (ID_USER) REFERENCES TBL_USER(ID));