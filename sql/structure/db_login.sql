create database if not exists login_nasa;
use login_nasa;

create table if not exists users
(
    id       int          not null auto_increment,
    username varchar(255) not null,
    db_key varchar(255) null,

    primary key (id),

    unique key ak_username (username)

) engine innodb;

insert into users (username, db_key) values
('90cc33a41b541af2c1964e3e10a46088cbdedf63031efaa35d588a698c91193f', 'tNVacPopbx2/Q5QVv/MM4tNgJM/lP0AtxHvWjqBQMCfDJ8NhDFa/9im7ccqvtDh0VDIH6tuOFMQ36p7q4w=='),
('cca457407f24b80c72d89dd061837112cb99a0aa050c155514b320b7aaffe95c', 'uOmukKv19a5NT4Kp5Di/y2Bln/i682iW6zbUvr5e4deRylgYmJFNnwz8pxl342qbL73RKfDgdGiTlc2Fow==');