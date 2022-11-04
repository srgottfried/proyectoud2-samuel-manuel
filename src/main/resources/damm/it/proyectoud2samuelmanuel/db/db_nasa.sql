create database if not exists nasa;
use nasa;

create table if not exists neos
(
    id           int          not null auto_increment,
    name         varchar(255) not null,
    diameter     double       not null,
    min_distance double       not null,
    speed        double       not null,
    hazarous     boolean      not null,
    date         datetime     not null,

    primary key (id)
) engine innodb;

create table if not exists apods
(
    id          int          not null auto_increment,
    date        datetime     not null,
    url         text         not null,
    title       varchar(255) not null,
    explanation text         not null,
    copyright   varchar(255) not null,

    primary key (id)
) engine innodb;