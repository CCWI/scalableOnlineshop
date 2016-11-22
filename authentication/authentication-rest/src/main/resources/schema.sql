DROP TABLE ServiceTemplate IF EXISTS;

CREATE TABLE ServiceTemplate (
  id        BIGINT IDENTITY PRIMARY KEY,
  name		VARCHAR(30)
);