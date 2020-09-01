# language: pt

Funcionalidade: Transferência de valores entre contas

  Cenário de Fundo: Contas são criadas para o cenário
    Dado que existam as seguintes contas
      | Numero | Saldo  | Nome           | Cpf         |
      | 123456 | 1000.0 | Pedro Henrique | 10338927425 |
      | 951357 | 1000.0 | Joao Paulo     | 98765432132 |
      | 654321 | 1000.0 | Paulo Joao     | 65432133121 |
      | 159357 | 1000.0 | Henrique Pedro | 15935765432 |

  Delineacao do Cenario: transferência realizada com sucesso
    Dado que seja solicitada um transferência com as seguintes informações
      | Conta do Solicitante   | Valor da Transferência  | Conta do Beneficiário        |
      | <ContaDoSolicitante>   |  <ValorDaTransferência> | <NumeroDaContaBeneficiario>  |
    Quando for executada a operação de transferência
    Então deverá ser apresentada a seguinte mensagem "Transferência realizada com sucesso!"
    E o saldo da conta "<NumeroDaContaBeneficiario>" deverá ser de <SaldoFinal>
    Exemplos:
      | ContaDoSolicitante | ValorDaTransferência | NumeroDaContaBeneficiario | SaldoFinal |
      | 123456             | 500.0                | 951357                    | 1500.0     |
      | 951357             | 500.0                | 654321                    | 1500.0     |
      | 654321             | 500.0                | 159357                    | 1500.0     |
      | 159357             | 500.0                | 123456                    | 1500.0     |

  Delineacao do Cenario: tentativa de transferência acima do valor máximo por operação
    Dado que seja solicitada um transferência com as seguintes informações
      | Conta do Solicitante   | Valor da Transferência | Conta do Beneficiário          |
      | <ContaDoSolicitante>   | <ValorDaTransferência> | <Numero da conta beneficiário> |
    Quando for executada a operação de transferência
    Então deverá ser apresentada a seguinte mensagem de erro "Operação de transferência tem um limite máximo de 500 por operação."
    Exemplos:
      | ContaDoSolicitante | ValorDaTransferência | Numero da conta beneficiário |
      | 123456             | 501.0                | 951357                       |
      | 951357             | 660.0                | 654321                       |
      | 654321             | 770.0                | 159357                       |
      | 159357             | 880.0                | 123456                       |

  Delineacao do Cenario: tentativa de transferência acima do valor que o solicitante possui na conta
    Dado que seja solicitada um transferência com as seguintes informações
      | Conta do Solicitante   | Valor da Transferência | Conta do Beneficiário          |
      | <ContaDoSolicitante>   | <ValorDaTransferência> | <Numero da conta beneficiário> |
    Quando for executada a operação de transferência
    Então deverá ser apresentada a seguinte mensagem de erro "Saldo insuficiente para a operação."
    Exemplos:
      | ContaDoSolicitante | ValorDaTransferência | Numero da conta beneficiário |
      | 123456             | 1001.0               | 951357                       |
      | 951357             | 2000.0               | 654321                       |
      | 654321             | 3000.0               | 159357                       |
      | 159357             | 4000.0               | 123456                       |
