CREATE TABLE hibernate_sequence (
  next_val bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE pessoa_model (
  id bigint NOT NULL,
  bairro varchar(255) DEFAULT NULL,
  cep varchar(255) DEFAULT NULL,
  cidade varchar(255) DEFAULT NULL,
  curriculo longblob,
  data_nascimento date NOT NULL,
  email varchar(255) DEFAULT NULL,
  idade int NOT NULL,
  logradouro varchar(255) DEFAULT NULL,
  nome varchar(255) DEFAULT NULL,
  nome_file_curriculo varchar(255) DEFAULT NULL,
  senha varchar(255) DEFAULT NULL,
  sexo varchar(255) DEFAULT NULL,
  sobrenome varchar(255) DEFAULT NULL,
  tipo_file_curriculo varchar(255) DEFAULT NULL,
  uf varchar(255) DEFAULT NULL,
  profissao_id bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKpmoik7p5di5s1rnqw42vmk9kf (profissao_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE profissao_model (
  id bigint NOT NULL,
  nome varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE role_model (
  id bigint NOT NULL,
  nome_role varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE telefone_model (
  id bigint NOT NULL,
  numero varchar(255) DEFAULT NULL,
  tipo varchar(255) DEFAULT NULL,
  pessoa_model_id bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKhw4j5h6oby6bwhk3cp0lxspdw (pessoa_model_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE usuario_model (
  id bigint NOT NULL,
  email varchar(255) DEFAULT NULL,
  login varchar(255) DEFAULT NULL,
  senha varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE usuarios_role (
  id bigint NOT NULL,
  role_id bigint DEFAULT NULL,
  usuario_id bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKgv9bh03vfj3dyukgnoi8u9ki4 (usuario_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


insert into role_model (id, nome_role) values (1,'ROLE_ADMIN'), (2,'ROLE_PADRAO'), (3,'ROLE_SUPER');
insert into usuario_model (id, login, senha, email) values (1,'Administrador','$2a$10$sbp6CE3sY6D9m1SQVq3nZukVB.jY0IQPj2m9l1hwCAefsJTeRqYnm','gustavo-tj@hotmail.com');
insert into usuarios_role (id, role_id, usuario_id) values (1,1,1);

insert into profissao_model (id, nome) values (1, 'Desenvolvedor Java'), (2, 'Analista de suporte'), (3, 'Desenvolvedor Júnior'), (4, 'Desenvolvedor Pleno'), (5, 'Desenvolvedor Sênior'), (6, 'Gerente Administrativo'), (7, 'Outros');