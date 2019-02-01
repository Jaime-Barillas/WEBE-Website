-- Author: Jaime Barillas
-- Date: 2018-01-21
-- Desc: Generate the students table.
--       The students table is a 'subtype' of the users table. To represent this
--       information, the id is both a primary and foreign key.

DROP TABLE IF EXISTS students;

CREATE TABLE students(
    id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    programCode CHAR(4) NOT NULL,
    programDescription VARCHAR(60),
    year INTEGER NOT NULL
);

-- Ensure the owner is the webd admin.
ALTER TABLE students OWNER TO webd4201_admin;

-- Insert Initial test values.

INSERT INTO students VALUES(
    100111111,
    'CSTY',
    'Computer System Technology',
    3
);

INSERT INTO students VALUES(
    100505421,
    'CPA',
    'Computer Programmer Analyst',
    2
);

INSERT INTO students VALUES(
    100999999,
    'GDES',
    'Graphic Design',
    1
);