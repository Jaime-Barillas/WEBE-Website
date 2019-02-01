-- Author: Jaime Barillas
-- Date: 2018-01-21
-- Desc: Generate the users table.
--       The users table and the faculty, students table have a super-type
--       subtype relationship. To represent this relationship, records within
--       the students and faculty tables have a foreign + primary key called id
--       which references the appropriate record in the users table.
--       These tables have a one-to-one relationship which allows the primary
--       key to also be a foreign key without problems.

CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id BIGINT PRIMARY KEY,
    password CHAR(40) NOT NULL,        -- 40 char sha1 hash.
    firstName VARCHAR(20) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    emailAddress VARCHAR(62) NOT NULL, -- 20 + 30 + [first].[last]@dcmail.com
    lastAccess DATE NOT NULL,
    enrollDate DATE NOT NULL,
    type CHAR(1) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- Ensure the owner is the webd admin.
ALTER TABLE users OWNER TO webd4201_admin;

-- Insert Initial test values.

INSERT INTO users VALUES(
    100111111,
    ENCODE(DIGEST('password', 'sha1'), 'hex'), -- sha1 of password.
    'Mike',
    'Jones',
    'mike.jones@dcmail.com',
    '2018-01-21',
    '2017-09-01',
    's',
    TRUE
);

INSERT INTO users VALUES(
    100505421,
    ENCODE(DIGEST('laserkl', 'sha1'), 'hex'), -- sha1 of laserkl.
    'Jaime',
    'Barillas-Flores',
    'jaime.barillas@dcmail.com',
    '2018-01-21',
    '2018-01-21',
    's',
    TRUE
);

INSERT INTO users VALUES(
    100999999,
    ENCODE(DIGEST('astrea', 'sha1'), 'hex'), -- sha1 of astrea.
    'Kylie',
    'Barber',
    'kylie.barber@dcmail.com',
    '2018-01-21',
    '2018-01-21',
    's',
    TRUE
);

INSERT INTO users VALUES(
    900111111,
    ENCODE(DIGEST('tririn', 'sha1'), 'hex'), -- sha1 of tririn.
    'Geoffrey',
    'Jones',
    'geoffrey.jones@dcmail.com',
    '2018-01-21',
    '2018-01-21',
    'f',
    TRUE
);

INSERT INTO users VALUES(
    900444444,
    ENCODE(DIGEST('dender', 'sha1'), 'hex'), -- sha1 of dender.
    'Martin',
    'Huffman',
    'martin.huffman@dcmail.com',
    '2018-01-21',
    '2018-01-21',
    'f',
    TRUE
);

INSERT INTO users VALUES(
    900999999,
    ENCODE(DIGEST('slamire', 'sha1'), 'hex'), -- sha1 of slamire.
    'Alexis',
    'Buckley',
    'alexis.buckley@dcmail.com',
    '2018-01-21',
    '2018-01-21',
    'f',
    TRUE
);