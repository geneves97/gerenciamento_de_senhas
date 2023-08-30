CREATE TABLE senhas (
    id INT PRIMARY KEY,
    descricao VARCHAR(255),
    tag VARCHAR(255),
    senha VARCHAR(255)
);
INSERT INTO senhas (id, descricao,tag, senha ) VALUES (1, 'teste', 'testes', '12345678' );
INSERT INTO senhas (id, descricao,tag, senha ) VALUES (2, 'teste2', 'testes', '12345678' );
INSERT INTO senhas (id, descricao,tag, senha ) VALUES (3, 'teste3', 'testes', '12345678' )