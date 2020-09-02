# language: pt

Funcionalidade: Realização de operação de saque

  Cenário de Fundo: Contas são criadas para o cenário
    Dado que existam as seguintes contas
      | Numero | Saldo  | Nome           | Cpf         |
      | 123456 | 1000.0 | Pedro Henrique | 10338927425 |

  Cenario: Realização de saques com sucesso
    Dado que seja solicitado um saque de 500.0 na conta "123456"
    Quando for executada a operação de saque
    Então deverá ser apresentada a seguinte mensagem: "Saque realizado com sucesso!"
    E o saldo da conta "123456" deverá ser de 500.0

  Delineacao do Cenario: regras de negócio da transação de saque.
    Dado que seja solicitado um saque de <ValorDoSaque> na conta "<NumeroDaConta>"
    Quando for executada a operação de saque
    Então deverá ser apresentada a seguinte mensagem de erro: "<Mensagem>"
    E o saldo da conta "<NumeroDaConta>" deverá ser de <SaldoInicial>
    Exemplos:
      | ValorDoSaque | NumeroDaConta | SaldoInicial | Mensagem                                                            |
      | -500.00      | 123456        | 1000.0       | Não é possível sacar um valor negativo                              |
      |  501.00      | 123456        | 1000.0       | Operação de transferência tem um limite máximo de 500 por operação. |
      |  1001.00     | 123456        | 1000.0       | Saldo insuficiente para a operação.                                 |


