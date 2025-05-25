-- ENDEREÇOS
INSERT INTO endereco (id_endereco, cep, logradouro, complemento, bairro, localidade, uf, ibge, gia, ddd, siafi, IS_ACTIVE, numero)
VALUES
(1, '01001-000', 'Praça da Sé', '', 'Sé', 'São Paulo', 'SP', '3550308', '', '11', '7107', TRUE, 100),
(2, '02020-000', 'Av. Paulista', 'Apto 101', 'Bela Vista', 'São Paulo', 'SP', '3550308', '', '11', '7107', TRUE, 200),
(3, '03030-000', 'Rua Vergueiro', '', 'Liberdade', 'São Paulo', 'SP', '3550308', '', '11', '7107', TRUE, 300);

-- USUÁRIOS
INSERT INTO usuario (Id, Nome, CPF_CNPJ, Telefone, Email, Senha, Ativo, Data_Cadastro, Funcao, codigo_recuperar_senha, validade_codigo_senha, fk_endereco_id)
VALUES
  (1, 'Alice Admin', '11111111111', '11999990000', 'alice@admin.com', '$2a$10$NdM9BmDQMCaqyig57FcH7.WLlnLfGp7SzSm4Oy5YRQs8YnOaKGNPm', TRUE, CURRENT_DATE, 'SYS_ADM', NULL, NULL, 1),
  (2, 'Bruno Gerente', '22222222222', '11988880000', 'bruno@gerente.com', '$2a$10$x.iozsuniSMd0R/cLmXhHuW9a4w5ks3iRkaLofbMvYR6hSeibnHj.', TRUE, CURRENT_DATE, 'GERENTE', NULL, NULL, 2),
  (3, 'Clara Cliente', '33333333333', '11977770000', 'clara@user.com', '$2a$10$niN8fTTGpt.HrJgPVdO.QeAOCiPs5QUj/anAwEV7Qg4NfR69dkngm', TRUE, CURRENT_DATE, 'USER', NULL, NULL, 3);

-- clara@user.com    -    user123
-- alice@admin.com    -    admin123
-- bruno@gerente.com    -    gerente123


-- VEÍCULOS
INSERT INTO veiculos (id, placa, modelo, marca, cor, ano_fabricacao, numero_chassi, fk_usuario_id)
VALUES
  (1, 'AAA1A11', 'Civic', 'Honda', 'Preto', 2022, 'CHASSI123A', 1),
  (2, 'BBB2B22', 'Gol', 'Volkswagen', 'Branco', 2018, 'CHASSI456B', 2),
  (3, 'CCC3C33', 'Corolla', 'Toyota', 'Prata', 2020, 'CHASSI789C', 3);

-- PRODUTOS
INSERT INTO produtos (id_produto, nome, descricao, categoria, qtd_estoque, preco, fornecedor, localizacao, data_atualizacao, cod_barra, imagem_url)
VALUES
  (1, 'Óleo 5W30', 'Lubrificante sintético', 'Lubrificantes', 50, 35.00, 'Petrobras', 'Estoque 1', CURRENT_DATE, '7890000000001', NULL),
  (2, 'Filtro de Óleo', 'Filtro compatível com carros populares', 'Filtros', 30, 20.00, 'Bosch', 'Estoque 2', CURRENT_DATE, '7890000000002', NULL),
  (3, 'Pneu Aro 15', 'Pneu de alta performance', 'Pneus', 15, 250.00, 'Michelin', 'Estoque 3', CURRENT_DATE, '7890000000003', NULL),
  (4, 'Pastilha de Freio', 'Pastilha dianteira', 'Freios', 40, 90.00, 'Fremax', 'Estoque 4', CURRENT_DATE, '7890000000004', NULL);

-- PEDIDOS
INSERT INTO Pedidos (ID_Pedido, dataPedido, total, status, observacoes, fk_usuario)
VALUES
  (1, CURRENT_DATE, 145.00, 'PENDENTE', 'Troca de óleo e filtro', 2),
  (2, CURRENT_DATE, 340.00, 'CONCLUIDO', 'Compra de pneu', 3);

-- ITENS DOS PEDIDOS (Servico_Produtos)
INSERT INTO servico_produtos (ID_Servico_Produto, Quantidade_Produtos, ID_Produto, ID_Pedido)
VALUES
  (1, 1, 1, 1),  -- Óleo
  (2, 1, 2, 1),  -- Filtro de Óleo
  (3, 1, 3, 2);  -- Pneu
