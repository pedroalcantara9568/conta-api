# language: pt

Funcionalidade: Realização de operação de depósito

  Cenário de Fundo: 01 - Contas são criadas para o cenário
    Dado que exista a seguinte conta
      | Numero | Saldo  | Nome           | Cpf         |
      | 123456 | 1000.0 | Pedro Henrique | 10338927425 |

    Delineacao do Cenario: 01 - Depósito com sucesso
      E que seja solicitado um depósito de <ValorDoDeposito> na conta "<NumeroDaConta>"
      Quando for executada a operação de depósito
      Então deverá ser apresentada a seguinte mensagem: "<Mensagem>"
      E o saldo da conta "<NumeroDaConta>" deverá ser de <ValorFinalEsperado>
      Exemplos:
        | ValorDoDeposito   | NumeroDaConta | ValorFinalEsperado | Mensagem                                   |
        | 500.0             | 123456        | 1500.0             | Depósito realizado com sucesso!            |

    Delineação do Cenário: 02 - Depósito negado
      E que seja solicitado um depósito de <ValorDoDeposito> na conta "<NumeroDaConta>"
      Quando for executada a operação de depósito
      Então deverá ser apresentada a seguinte mensagem de erro: "<Mensagem>"
      E o saldo da conta "<NumeroDaConta>" deverá ser de <ValorInicial>
      Exemplos:
        | ValorDoDeposito | NumeroDaConta | ValorInicial | Mensagem                                    |
        | -100.0          | 123456        | 1000.0       | Não é possivel depositar valor negativo     |
        |  500.0          |               | 1000.0       | A conta do beneficiario deve ser informada! |
