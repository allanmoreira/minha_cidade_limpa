-- create database minha_cidade_limpa_bd;
-- use minha_cidade_limpa_bd;

CREATE TABLE login (
                id_login INT NOT NULL,
                username VARCHAR (50) NOT NULL,
                senha VARCHAR (50) NOT NULL,
                pf_ou_pj BOOLEAN NOT NULL,
                PRIMARY KEY (id_login)
);


CREATE TABLE pessoa_juridica (
                id_pessoa_juridica INT NOT NULL,
                nome VARCHAR (50) NOT NULL,
                cnpj VARCHAR (50) NOT NULL,
                telefone VARCHAR (50),
                email VARCHAR (50) NOT NULL,
                endereco VARCHAR (50),
                id_login INT NOT NULL,
                PRIMARY KEY (id_pessoa_juridica)
);


CREATE TABLE pessoa_fisica (
                id_pessoa_fisica INT NOT NULL,
                nome VARCHAR (50) NOT NULL,
                cpf VARCHAR (50) NOT NULL,
                email VARCHAR (50) NOT NULL,
                data_nascimento DATE NOT NULL,
                telefone VARCHAR (50),
                id_login INT NOT NULL,
                PRIMARY KEY (id_pessoa_fisica)
);


CREATE TABLE marcacao_depredacao (
                id_marcacao_depredacao INT NOT NULL,
                tipo_depredacao VARCHAR (50) NOT NULL,
                descricao VARCHAR (50) NOT NULL,
                data_marcacao DATE NOT NULL,
                status VARCHAR (50) NOT NULL,
                cadidato_resolver_problema BOOLEAN NOT NULL,
                id_pessoa_fisica_fez_narcacao INT NOT NULL,
                PRIMARY KEY (id_marcacao_depredacao)
);

CREATE TABLE beneficio_empresa (
                id_marcacao_depredacao INT NOT NULL,
                id_pessoa_juridica INT NOT NULL,
                descricao_beneficio VARCHAR (50) NOT NULL,
                aprovado BOOLEAN NOT NULL
);

CREATE TABLE cadidatura_resolucao_problema (
                id_pessoa_fisica INT NOT NULL,
                id_marcacao_depredacao INT NOT NULL
);


CREATE TABLE voto_depredacao (
                id_pessoa_fisica INT NOT NULL,
                id_marcacao_depredacao INT NOT NULL,
                like_marcacao BOOLEAN NOT NULL
);


ALTER TABLE pessoa_juridica ADD CONSTRAINT login_pessoa_juridica_fk
FOREIGN KEY (id_login)
REFERENCES login (id_login)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE pessoa_fisica ADD CONSTRAINT login_pessoa_fisica_fk
FOREIGN KEY (id_login)
REFERENCES login (id_login)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE beneficio_empresa ADD CONSTRAINT pessoa_juridica_beneficio_empresa_fk
FOREIGN KEY (id_pessoa_juridica)
REFERENCES pessoa_juridica (id_pessoa_juridica)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE marcacao_depredacao ADD CONSTRAINT pessoa_fisica_marcacao_depredacao_fk
FOREIGN KEY (id_pessoa_fisica_fez_narcacao)
REFERENCES pessoa_fisica (id_pessoa_fisica)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE voto_depredacao ADD CONSTRAINT pessoa_fisica_voto_pessoa_marcacao_fk
FOREIGN KEY (id_pessoa_fisica)
REFERENCES pessoa_fisica (id_pessoa_fisica)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE cadidatura_resolucao_problema ADD CONSTRAINT pessoa_fisica_cadidatura_resolucao_problema_fk
FOREIGN KEY (id_pessoa_fisica)
REFERENCES pessoa_fisica (id_pessoa_fisica)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE voto_depredacao ADD CONSTRAINT marcacao_depredacao_voto_pessoa_marcacao_fk
FOREIGN KEY (id_marcacao_depredacao)
REFERENCES marcacao_depredacao (id_marcacao_depredacao)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE cadidatura_resolucao_problema ADD CONSTRAINT marcacao_depredacao_cadidatura_resolucao_problema_fk
FOREIGN KEY (id_marcacao_depredacao)
REFERENCES marcacao_depredacao (id_marcacao_depredacao)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE beneficio_empresa ADD CONSTRAINT marcacao_depredacao_beneficio_empresa_fk
FOREIGN KEY (id_marcacao_depredacao)
REFERENCES marcacao_depredacao (id_marcacao_depredacao)
ON DELETE NO ACTION
ON UPDATE NO ACTION;