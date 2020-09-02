# language: pt

Funcionalidade: Criar nova conta no banco

  Cenário: 01 - Criação de conta com sucesso
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome            | Cpf          | Saldo   |
      | Jhon Frend      |  12345678912 | 500.00  |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser retornado o número da conta criada
    E deverá ser apresentada a seguinte mensagem: "Conta cadastrada com sucesso!"

  Delineação do Cenário: 02 - Criação de conta frustrada
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome   | Cpf   | Saldo   |
      | <Nome> | <Cpf> | <Saldo> |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser apresentada a seguinte mensagem de erro: "<Mensagem>"
    Exemplos:
      | Nome            | Cpf         | Saldo | Mensagem                                                  |
      | Jhon Frend      | 10338938565 | 49.00 | Saldo insuficiente para abertura de nova conta.           |
      | Pedro Henrique  |             | 48.00 | É necessário informar um cpf para abertura de nova conta. |
      | Paulo Vital     | 6549873     | 0.00  | CPF informado para criação de conta está inválido.        |
      | Freud da Grécia | ONGOING     | 10.00 | CPF informado para criação de conta está inválido.        |




