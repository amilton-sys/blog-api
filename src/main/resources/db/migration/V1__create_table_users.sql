CREATE TABLE tb_users
(
    user_id  BINARY(16) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE tb_roles
(
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL
);

CREATE TABLE tb_users_roles
(
    user_id BINARY(16) NOT NULL,
    role_id BIGINT     NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES tb_users (user_id),
    FOREIGN KEY (role_id) REFERENCES tb_roles (role_id)
);

CREATE TABLE tb_posts
(
    post_id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    content            TEXT         NOT NULL,
    author_id          BINARY(16),
    creation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES tb_users (user_id)
);



INSERT INTO tb_roles (name, role_id)
VALUES ('ADMIN', 1);
INSERT INTO tb_roles (name, role_id)
VALUES ('BASIC', 2);
