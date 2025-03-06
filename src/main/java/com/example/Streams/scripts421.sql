create table students
(
    age  INTEGER CHECK ( age > 16 ) default 20,
    name text unique not null

);
create table faculties
(
    color varchar unique,
    name  varchar unique
);