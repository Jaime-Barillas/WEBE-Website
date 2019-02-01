-- Author: Jaime Barillas
-- Date: 2018-01-21
-- Desc: Generate the faculty table.
--       The faculty table is a 'subtype' of the users table. To represent this
--       information, the id is both a primary and foreign key.

DROP TABLE IF EXISTS faculty;

CREATE TABLE faculty(
    id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    schoolCode CHAR(4) NOT NULL,
    schoolDescription VARCHAR(60),
    office CHAR(8) NOT NULL,  -- length of: CFCE-xxx
    extension INTEGER NOT NULL
);

-- Ensure the owner is the webd admin.
ALTER TABLE faculty OWNER TO webd4201_admin;

-- Insert Initial test values.

INSERT INTO faculty VALUES(
    900111111,
    'BITM',
    'School of Business, IT & Management',
    'C-215',
    1111
);

INSERT INTO faculty VALUES(
    900444444,
    'JES',
    'School of Justice & Emergency Services',
    'J-118',
    4444
);

INSERT INTO faculty VALUES(
    900999999,
    'MAD',
    'School of Media, Art & Design',
    'L-211',
    9999
);