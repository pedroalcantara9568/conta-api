# language: pt

Funcionalidade: Realização de operação de depósito

  Cenário: 01 - Realização de depósito com sucesso
    Dado que existam as seguintes contas
      | Numero Conta | Saldo  |
      | 123456       | 1000.0 |
    E que seja solicitado um depósito de "500.0"
    Quando for executada a operação de depósito
    Então deverá ser apresentada a seguinte mensagem "Depósito realizado com sucesso!"
    E o saldo da conta "123456" deverá ser de "1500.0"

  Cenário: 02 - Realização de depósito com valor negativo 
    Dado que existam as seguintes contas
      | Numero Conta | Saldo  |
      | 123456       | 1000.0 |
    E que seja solicitado um depósito de "-500.0"
    Quando for executada a operação de depósito
    Então deverá ser apresentada a seguinte mensagem de erro "Não é possivel depositar valor negativo"