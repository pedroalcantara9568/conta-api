# language: pt

Funcionalidade: Criar nova conta no banco


  Delineação do Cenário: : criação de conta com sucesso
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome   | Cpf   | Saldo   |
      | <Nome> | <Cpf> | <Saldo> |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser retornado o número da conta criada
    E deverá ser apresentada a seguinte mensagem "Conta cadastrada com sucesso!"
    Exemplos:
      | Nome            | Cpf          | Saldo   |
      | Jhon Frend      |  12345678912 | 500.00  |
      | Pedro Henrique  |  98765421232 | 1000.00 |
      | Paulo Vital     |  98765432121 | 1500.00 |
      | Freud da Grécia |  95135765421 | 2000.00 |

  Delineação do Cenário: tentativa de criação de conta com balanço menor que o limite
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome   | Cpf   | Saldo   |
      | <Nome> | <Cpf> | <Saldo> |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser apresentada a seguinte mensagem de erro "Saldo insuficiente para abertura de nova conta."
    Exemplos:
      | Nome            | Cpf         | Saldo |
      | Jhon Frend      | 10338938565 | 49.00 |
      | Pedro Henrique  | 32165498732 | 48.00 |
      | Paulo Vital     | 65498732165 | 0.00  |
      | Freud da Grécia | 65432154565 | 10.00 |


  Delineação do Cenário: tentativa de criar conta sem cpf informado
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome   | Cpf   | Saldo   |
      | <Nome> | <Cpf> | <Saldo> |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser apresentada a seguinte mensagem de erro "É necessário informar um cpf para abertura de nova conta."
    Exemplos:
      | Nome            | Cpf         | Saldo |
      | Jhon Frend      |             | 49.00 |
      | Pedro Henrique  |             | 48.00 |
      | Paulo Vital     |             | 0.00  |
      | Freud da Grécia |             | 10.00 |


  Delineação do Cenário: tentativa de criar conta com cpf inválido
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome   | Cpf   | Saldo   |
      | <Nome> | <Cpf> | <Saldo> |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser apresentada a seguinte mensagem de erro "CPF informado para criação de conta está inválido."
    Exemplos:
      | Nome            | Cpf         | Saldo |
      | Jhon Frend      |  65454151   | 49.00 |
      | Pedro Henrique  |  951354     | 48.00 |
      | Pablo Vital     |  65454      | 0.00  |
      | Freud da Grécia |  654        | 10.00 |


  Delineação do Cenário: tentativa de criar conta com cpf inválido com letras de a-z
    Dado que seja solicitada a criação de uma nova conta com os seguintes valores
      | Nome   | Cpf   | Saldo   |
      | <Nome> | <Cpf> | <Saldo> |
    Quando for enviada a solicitação de criação de nova conta
    Então deverá ser apresentada a seguinte mensagem de erro "CPF informado para criação de conta está inválido."
    Exemplos:
      | Nome            | Cpf         | Saldo |
      | Jhon Frend      |  cedrerer   | 49.00 |
      | Pedro Henrique  |  cedrer     | 48.00 |
      | Paulo Vital     |  cedrer     | 0.00  |
      | Freud da Grécia |  cedrdsdsr  | 10.00 |


