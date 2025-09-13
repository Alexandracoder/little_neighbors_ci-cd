-- Base y usuario de test
CREATE DATABASE IF NOT EXISTS testdb
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'testuser'@'%' IDENTIFIED BY 'testpassword';
GRANT ALL PRIVILEGES ON testdb.* TO 'testuser'@'%';

-- Base y usuario de app principal
CREATE DATABASE IF NOT EXISTS littleneighbors
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'littleneighbors_user'@'%' IDENTIFIED BY 'littleneighbors_password';
GRANT ALL PRIVILEGES ON littleneighbors.* TO 'littleneighbors_user'@'%';

FLUSH PRIVILEGES;
