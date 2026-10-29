# language: pt

Funcionalidade: Consulta de tipos de resíduos
  Como consumidor da API EcoColeta
  Quero consultar os tipos de resíduos cadastrados
  Para verificar se a API retorna os dados corretamente

  Cenário: Consultar todos os tipos de resíduos com sucesso
    Dado que a API EcoColeta está disponível
    Quando eu realizar uma requisição GET para consultar os tipos de resíduos
    Então o código de resposta HTTP deve ser 200
    E a resposta deve conter uma lista de tipos de resíduos
    E o primeiro tipo de resíduo deve conter os campos obrigatórios

  Cenário: Consultar um tipo de resíduo inexistente
    Dado que a API EcoColeta está disponível
    Quando eu realizar uma requisição GET para consultar o tipo de resíduo com ID 9999
    Então o código de resposta HTTP deve ser 404
    E a resposta deve informar que o tipo de resíduo não foi encontrado