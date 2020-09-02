# language: pt

Funcionalidade: Realização de operação de depósito

  Cenário de Fundo: Contas são criadas para o cenário
    Dado que existam as seguintes contas
      | Numero | Saldo  | Nome           | Cpf         |
      | 123456 | 1000.0 | Pedro Henrique | 10338927425 |
      | 951357 | 1000.0 | Joao Paulo     | 98765432132 |
      | 654321 | 1000.0 | Paulo Joao     | 65432133121 |
      | 159357 | 1000.0 | Henrique Pedro | 15935765432 |

    Delineacao do Cenario: 01 - depósito é efetuado com sucesso
      E que seja solicitado um depósito de <ValorDoDeposito> na conta "<NumeroDaConta>"
      Quando for executada a operação de depósito
      Então deverá ser apresentada a seguinte mensagem: "Depósito realizado com sucesso!"
      E o saldo da conta "<NumeroDaConta>" deverá ser de <ValorFinalEsperado>
      Exemplos:
        |ValorDoDeposito   | NumeroDaConta | ValorFinalEsperado |
        |500.0             | 123456        | 1500.0             |
        |100.0             | 951357        | 1100.0             |
        |1000.0            | 654321        | 2000.0             |
        |1300.1            | 159357        | 2300.1             |

    Delineação do Cenário: 02 - depósito com valor negativo é negado
      E que seja solicitado um depósito de <ValorDoDeposito> na conta "<NumeroDaConta>"
      Quando for executada a operação de depósito
      Então deverá ser apresentada a seguinte mensagem de erro: "Não é possivel depositar valor negativo"
      E o saldo da conta "<NumeroDaConta>" deverá ser de <ValorInicial>
      Exemplos:
        | ValorDoDeposito   | NumeroDaConta | ValorInicial |
        | -501.0            | 123456        | 1000.0       |
        | -1001.0           | 951357        | 1000.0       |
        | -1000.0           | 654321        | 1000.0       |
        | -1300.1           | 159357        | 1000.0       |