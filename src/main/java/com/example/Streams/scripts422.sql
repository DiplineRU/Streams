create table car
(
    id    integer not null primary key,
    stamp varchar not null,
    model varchar not null,
    cost  INTEGER NOT NULL
);
create table person
(
    carId          integer                    not null primary key,
    name           varchar                    not null,
    age            integer check ( age > 16 ) not null default 20,
    driversLicense BIT,
    foreign key (carId) references car (id)
)