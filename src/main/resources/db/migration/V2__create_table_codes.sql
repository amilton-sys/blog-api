CREATE TABLE tb_codes (
      code_id BIGINT AUTO_INCREMENT PRIMARY KEY,
      code INT NOT NULL UNIQUE,
      creation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      active BOOLEAN,
      email VARCHAR(255) NOT NULL UNIQUE
);

