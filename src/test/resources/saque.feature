# language: pt

Funcionalidade: Realização de operação de saque

  Cenário de Fundo: Criação das contas
    Dado que existam as seguintes contas
      | Numero Conta | Saldo  |
      | 123456       | 1000.0 |

  Cenário: 01 - Realização de saque acima do valor máximo por operação
    Dado que seja solicitado um saque de "501.0"
    Quando for executada a operação de saque
    Então deverá ser apresentada a seguinte mensagem de erro "Operação de transferência tem um limite máximo de 500 por operação."
    E o saldo da conta "123456" deverá ser de "1000.0"

  Cenário: 02 - Realização de saque com sucesso
    Dado que seja solicitado um saque de "500.0"
    Quando for executada a operação de saque
    Então deverá ser apresentada a seguinte mensagem "Saque realizado com sucesso!"
    E o saldo da conta "123456" deverá ser de "500.0"