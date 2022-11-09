CREATE USER IF NOT EXISTS 'samuel'@'localhost' IDENTIFIED BY 'PassSuperSecreta1';
GRANT ALL PRIVILEGES ON nasa.* TO 'samuel'@'localhost';

CREATE USER IF NOT EXISTS 'manuel'@'localhost' IDENTIFIED BY 'PassSuperSecreta2';
GRANT ALL PRIVILEGES ON nasa.* TO 'manuel'@'localhost';

CREATE USER IF NOT EXISTS 'login'@'localhost' IDENTIFIED BY 'PassPublica';
GRANT ALL PRIVILEGES ON login_nasa.users TO 'login'@'localhost';

FLUSH PRIVILEGES;

DELETE FROM users;
INSERT INTO users (username, db_key) VALUES
('90cc33a41b541af2c1964e3e10a46088cbdedf63031efaa35d588a698c91193f', 'tNVacPopbx2/Q5QVv/MM4tNgJM/lP0AtxHvWjqBQMCfDJ8NhDFa/9im7ccqvtDh0VDIH6tuOFMQ36p7q4w=='),
('cca457407f24b80c72d89dd061837112cb99a0aa050c155514b320b7aaffe95c', 'uOmukKv19a5NT4Kp5Di/y2Bln/i682iW6zbUvr5e4deRylgYmJFNnwz8pxl342qbL73RKfDgdGiTlc2Fow==');