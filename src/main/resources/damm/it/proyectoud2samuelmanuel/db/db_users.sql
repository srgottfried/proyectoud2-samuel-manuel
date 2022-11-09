CREATE USER 'samuel'@'localhost' IDENTIFIED BY 'PassSuperSecreta1';
GRANT ALL PRIVILEGES ON nasa.* TO 'samuel'@'localhost';

CREATE USER 'manuel'@'localhost' IDENTIFIED BY 'PassSuperSecreta2';
GRANT ALL PRIVILEGES ON nasa.* TO 'manuel'@'localhost';

CREATE USER 'login'@'localhost' IDENTIFIED BY 'PassPublica';
GRANT ALL PRIVILEGES ON login_nasa.* TO 'login'@'localhost';

FLUSH PRIVILEGES;