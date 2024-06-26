create table tb_users(
     id bigint not null auto_increment,
     nome varchar(100) not null,
     email varchar(100) not null unique,
     senha varchar(255) not null,
     primary key(id)
)