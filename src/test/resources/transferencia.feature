# language: pt

Funcionalidade: Transferência de valores entre contas

  Cenário de Fundo: Contas são criadas para o cenário
    Dado que existam as seguintes contas
      | Numero | Saldo  | Nome           | Cpf         |
      | 123456 | 1500.0 | Pedro Henrique | 10338927425 |
      | 951357 | 1500.0 | Joao Paulo     | 98765432132 |
      | 654321 | 1500.0 | Paulo Joao     | 65432133121 |
      | 159357 | 1500.0 | Henrique Pedro | 15935765432 |

  Delineacao do Cenario: transferência realizada com sucesso
    Dado que seja solicitada um transferência com as seguintes informações
      | Conta do Solicitante   | Valor da Transferência  | Conta do Beneficiário        |
      | <ContaDoSolicitante>   |  <ValorDaTransferência> | <NumeroDaContaBeneficiario>  |
    Quando for executada a operação de transferência
    Então deverá ser apresentada a seguinte mensagem: "Transferência realizada com sucesso!"
    E o saldo da conta "<NumeroDaContaBeneficiario>" deverá ser de <SaldoFinal>
    Exemplos:
      | ContaDoSolicitante | ValorDaTransferência | NumeroDaContaBeneficiario | SaldoFinal |
      | 123456             | 500.0                | 951357                    | 2000.0     |
      | 951357             | 500.0                | 654321                    | 2000.0     |
      | 654321             | 500.0                | 159357                    | 2000.0     |
      | 159357             | 500.0                | 123456                    | 2000.0     |

  Delineacao do Cenario: tentativas transferências frustradas
    Dado que seja solicitada um transferência com as seguintes informações
      | Conta do Solicitante   | Valor da Transferência | Conta do Beneficiário          |
      | <ContaDoSolicitante>   | <ValorDaTransferência> | <Numero da conta beneficiário> |
    Quando for executada a operação de transferência
    Então deverá ser apresentada a seguinte mensagem de erro: "<Mensagem>"
    Exemplos:
      | ContaDoSolicitante | ValorDaTransferência | Numero da conta beneficiário | Mensagem                                                                 |
      | 123456             | 501.0                | 951357                       | Operação de transferência tem um limite máximo de 500 por operação.      |
      | 951357             | 1550.0               | 654321                       | Saldo insuficiente para a operação.                                      |


