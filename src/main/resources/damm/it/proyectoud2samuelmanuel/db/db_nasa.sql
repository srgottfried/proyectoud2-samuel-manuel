create database if not exists nasa;
use nasa;

drop table neos;
create table neos
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

drop table apods;
create table apods
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