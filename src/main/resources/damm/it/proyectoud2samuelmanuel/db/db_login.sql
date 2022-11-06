create database if not exists login_nasa;
use login_nasa;

create table if not exists users
(
    id       int          not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    api_key varchar(255) null default 'DEMO_KEY',

    primary key (id),

    unique key ak_username (username)

) engine innodb;