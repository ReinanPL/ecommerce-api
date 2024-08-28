INSERT INTO Categories (name, active)
VALUES ('Eletrônicos', TRUE),
('Vestuário', TRUE),
('Móveis', TRUE),
('Informática', TRUE),
('Alimentos', TRUE);

INSERT INTO Addresses (cep, city, state, street, number, complement)
VALUES ('12345678', 'São Paulo', 'SP', 'Rua dos Exemplos', '123', 'Apartamento 101'),
('98765432', 'Rio de Janeiro', 'RJ', 'Avenida Principal', '456', 'Casa'),
('00000000', 'Brasília', 'DF', 'Rua sem nome', '1', 'Centro'),
('54321987', 'Curitiba', 'PR', 'Rua das Flores', '789', 'Fundo de quintal'),
('21098765', 'Salvador', 'BA', 'Praça da Alegria', '321', 'Comércio');


INSERT INTO users (cpf, first_name, last_name, email, password, role, address_id)
VALUES
    ('63235354085', 'John', 'Doe', 'johndoe@example.com', 'strong_password', 'CLIENT',1),
    ('25916279086', 'Jane', 'Smith', 'janesmith@example.com', 'another_strong_password', 'ADMIN',2),
    ('56536730054', 'Michael', 'Jordan', 'mjordan23@gmail.com', 'secret_slamdunk', 'CLIENT',3),
    ('79816657015', 'Alice', 'Wonderland', 'aliceinwonderland@fairytales.com', 'curious_password', 'CLIENT',4);


INSERT INTO Products (name, quantity_in_stock, price, category_id, active)
VALUES ('Smartphone Galaxy Z Flip 4', 10, 5999.99, (SELECT id FROM Categories WHERE name = 'Eletrônicos'), TRUE),
('Camisa Polo Ralph Lauren', 20, 199.90, (SELECT id FROM Categories WHERE name = 'Vestuário'), TRUE),
('Sofá Retrátil', 5, 2999.99, (SELECT id FROM Categories WHERE name = 'Móveis'), TRUE),
('Notebook Dell Inspiron', 15, 4599.90, (SELECT id FROM Categories WHERE name = 'Informática'), TRUE),
('Arroz Basmati 5kg', 30, 39.90, (SELECT id FROM Categories WHERE name = 'Alimentos'), TRUE);

INSERT INTO Sales (DATE_SALE, USER_CPF) VALUES
('2023-11-20', '63235354085'),
('2023-11-21', '25916279086'),
('2023-12-01', '63235354085'),
('2023-12-05', '25916279086'),
('2023-12-10', '79816657015'),
('2023-12-15', '56536730054');

INSERT INTO Item_Sales (sale_id, product_id, quantity, price)
VALUES (1, (SELECT id FROM Products WHERE name = 'Smartphone Galaxy Z Flip 4'), 1, 5999.99),
(2, (SELECT id FROM Products WHERE name = 'Camisa Polo Ralph Lauren'), 1, 199.90),
(3, (SELECT id FROM Products WHERE name = 'Sofá Retrátil'), 1, 2999.99),
(4, (SELECT id FROM Products WHERE name = 'Arroz Basmati 5kg'), 1, 39.90),
(5, (SELECT id FROM Products WHERE name = 'Notebook Dell Inspiron'), 1, 4599.90),
(6,(SELECT id FROM Products WHERE name = 'Notebook Dell Inspiron'), 1, 4599.90);

