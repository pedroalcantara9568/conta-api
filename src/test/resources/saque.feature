# language: pt

Funcionalidade: Realização de operação de saque

  Cenário de Fundo: Contas são criadas para o cenário
    Dado que existam as seguintes contas
      | Numero | Saldo  | Nome           | Cpf         |
      | 123456 | 1000.0 | Pedro Henrique | 10338927425 |
      | 951357 | 1000.0 | Joao Paulo     | 98765432132 |
      | 654321 | 1000.0 | Paulo Joao     | 65432133121 |
      | 159357 | 1000.0 | Henrique Pedro | 15935765432 |


  Delineacao do Cenario: Realização de saque com sucesso
    Dado que seja solicitado um saque de <ValorDoSaque> na conta "<NumeroDaConta>"
    Quando for executada a operação de saque
    Então deverá ser apresentada a seguinte mensagem "Saque realizado com sucesso!"
    E o saldo da conta "<NumeroDaConta>" deverá ser de <SaldoFinal>
    Exemplos:
      | ValorDoSaque | NumeroDaConta | SaldoFinal |
      | 500.0        | 123456        | 500.0      |
      | 350.0        | 951357        | 650.0      |
      | 200.0        | 654321        | 800.0      |
      | 123.0        | 159357        | 877.0      |

  Delineacao do Cenario: Tentativa de saque de valor negativo
    Dado que seja solicitado um saque de <ValorDoSaque> na conta "<NumeroDaConta>"
    Quando for executada a operação de saque
    Então deverá ser apresentada a seguinte mensagem de erro "Não é possível sacar um valor negativo"
    E o saldo da conta "<NumeroDaConta>" deverá ser de <SaldoInicial>
    Exemplos:
      | ValorDoSaque | NumeroDaConta | SaldoInicial |
      | -500.0       | 123456        | 1000.0       |
      | -350.0       | 951357        | 1000.0       |
      | -200.0       | 654321        | 1000.0       |
      | -123.0       | 159357        | 1000.0       |

  Delineacao do Cenario: Tentativa de saque acima do valor máximo por operação
    Dado que seja solicitado um saque de <ValorDoSaque> na conta "<NumeroDaConta>"
    Quando for executada a operação de saque
    Então deverá ser apresentada a seguinte mensagem de erro "Operação de transferência tem um limite máximo de 500 por operação."
    E o saldo da conta "<NumeroDaConta>" deverá ser de "<SaldoInicial>"
    Exemplos:
      |ValorDoSaque | NumeroDaConta | SaldoInicial |
      | 501.0       | 123456        | 1000.0       |
      | 999.0       | 951357        | 1000.0       |
      | 750.0       | 654321        | 1000.0       |
      | 1000.0      | 159357        | 1000.0       |

