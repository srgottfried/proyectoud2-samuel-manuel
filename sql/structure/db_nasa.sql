CREATE DATABASE IF NOT EXISTS nasa;
USE nasa;

DROP TABLE IF EXISTS neos;
CREATE TABLE neos
(
    id           int          not null auto_increment,
    name         varchar(255) not null,
    diameter     double       not null,
    min_distance double       not null,
    speed        double       not null,
    hazarous     boolean      not null,
    date         date         not null,

    primary key (id),
    key (date)
) engine innodb;

DROP TABLE IF EXISTS apods;
CREATE TABLE apods
(
    id          int          not null auto_increment,
    date        date         not null,
    img         mediumblob   null,
    title       varchar(255) not null,
    explanation text         not null,
    copyright   varchar(255) not null,

    primary key (id),
    unique key (date)
) engine innodb;