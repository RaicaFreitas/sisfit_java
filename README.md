📊 Sistema SISFIT 
🎯 Objetivo Geral do Sistema
O SISFIT é um Sistema de Gerenciamento para Academia/Centro de Fitness que permite:

Cadastrar e gerenciar clientes
Controlar funcionários
Registrar atividades físicas dos clientes
Vender produtos/serviços (pedidos)
Controlar acesso de usuários ao sistema


📋 Tabelas e suas Funções
1. 👥 CLIENTES
Campos: id, nome, cpf, telefone, email
Para que serve:

Armazena os dados dos alunos/clientes da academia
Cada cliente pode fazer atividades físicas
Cada cliente pode fazer pedidos (comprar produtos)

Exemplo real:
ID: 1
Nome: João Silva
CPF: 123.456.789-00
Telefone: (41) 99999-8888
Email: joao@email.com

2. 👨‍💼 FUNCIONARIOS
Campos: id, nome, cpf, telefone, email
Para que serve:

Cadastra os colaboradores da academia (personal trainers, recepcionistas, gerentes)
Funcionários podem ser responsáveis por criar atividades para os clientes
Funcionários podem ter acesso ao sistema através da tabela USUARIOS

Exemplo real:
ID: 1
Nome: Maria Santos (Personal Trainer)
CPF: 987.654.321-00
Telefone: (41) 98888-7777
Email: maria@academia.com

3. 🏋️ ATIVIDADES
Campos: id, cliente_id, funcionario_id, descricao, series, repeticoes, data_atividade
Para que serve:

Registra os exercícios/treinos que os clientes fazem
Vincula qual cliente está fazendo o treino
Vincula qual funcionário (personal) criou/orientou o treino
Guarda detalhes do exercício (quantas séries, repetições)

Exemplo real:
ID: 1
Cliente: João Silva (ID: 1)
Funcionário: Maria Santos (ID: 1) 
Descrição: Supino reto com barra
Séries: 4
Repetições: 12
Data: 27/11/2024
Relação:

Um cliente pode ter várias atividades
Um funcionário pode orientar várias atividades
Cada atividade pertence a um cliente e um funcionário


4. 📦 ITENS
Campos: id, nome, tipo, valor
Para que serve:

Cadastra os produtos e serviços vendidos pela academia
Pode ser: suplementos, equipamentos, acessórios, mensalidades, serviços

Exemplos reais:
ID: 1 | Nome: Whey Protein 900g | Tipo: Suplemento | Valor: R$ 89,90

ID: 2 | Nome: Luva de Treino | Tipo: Acessório | Valor: R$ 29,90

ID: 3 | Nome: Mensalidade Mensal | Tipo: Serviço | Valor: R$ 150,00

ID: 4 | Nome: Personal Trainer (hora) | Tipo: Serviço | Valor: R$ 80,00

5. 🛒 PEDIDOS
Campos: id, cliente_id, data_pedido, forma_pagamento, observacoes
Para que serve:

Registra as vendas/compras feitas pelos clientes
Guarda informações sobre o pedido: quem comprou, quando, como pagou
Um pedido pode conter vários itens (veja próxima tabela)

Exemplo real:
ID: 1
Cliente: João Silva (ID: 1)
Data: 27/11/2024 14:30
Forma Pagamento: Cartão de Crédito
Observações: Cliente solicitou entrega em casa

6. 📝 PEDIDO_ITEM
Campos: id, pedido_id, item_id, quantidade
Para que serve:

Lista quais itens estão dentro de cada pedido
Define a quantidade de cada item
É a tabela intermediária entre PEDIDOS e ITENS (relacionamento N para N)

Exemplo real:
Pedido #1 de João Silva contém:
- 2x Whey Protein 900g (Item ID: 1)
- 1x Luva de Treino (Item ID: 2)

Registros na tabela:
ID: 1 | pedido_id: 1 | item_id: 1 | quantidade: 2
ID: 2 | pedido_id: 1 | item_id: 2 | quantidade: 1
Por que essa tabela existe?

Um pedido pode ter vários itens ✅
Um item pode estar em vários pedidos ✅
Precisamos saber a quantidade de cada item em cada pedido ✅
