CREATE DATABASE IF NOT EXISTS login_nasa;
USE login_nasa;

DROP TABLE IF EXISTS users;
CREATE TABLE `users`
(
    id        int          not null auto_increment,
    username  varchar(255) not null,
    db_key    varchar(255) null,

    PRIMARY KEY (id),

    UNIQUE KEY ak_username (username)

) engine innodb;