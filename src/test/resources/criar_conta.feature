# language: pt

Funcionalidade: Criar nova conta no banco

  Cenário: 01 - Criação de conta com balanço menor que o limite
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome     | Cpf         | Saldo |
      | John Doe | 17448936590 | 49.00 |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser apresentada a seguinte mensagem de erro "Saldo insuficiente para abertura de nova conta."

  Cenário: 02 - Criação de conta sem cpf informado
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome     | Cpf         | Saldo |
      | John Doe |             | 50.00 |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser apresentada a seguinte mensagem de erro "É necessário informar um cpf para abertura de nova conta."

  Cenário: 03 - Criação de conta com cpf inválido
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome     | Cpf          | Saldo |
      | John Doe | 111111111111 | 50.00 |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser apresentada a seguinte mensagem de erro "CPF informado para criação de conta está inválido."

  Cenário: 04 - Criação de conta com sucesso
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome     | Cpf         | Saldo  |
      | John Doe | 17448936590 | 50.00  |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser retornado o número da conta criada
    E deverá ser apresentada a seguinte mensagem "Conta cadastrada com sucesso!"