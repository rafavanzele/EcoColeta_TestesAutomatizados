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

  Cenário: Cadastrar um novo tipo de resíduo com sucesso
    Dado que a API EcoColeta está disponível
    Quando eu realizar uma requisição POST autenticada para cadastrar um tipo de resíduo
    Então o cadastro do tipo de resíduo deve ser realizado com sucesso
    E a resposta deve conter os dados do tipo de resíduo cadastrado

  Cenário: Atualizar um tipo de resíduo com sucesso
    Dado que a API EcoColeta está disponível
    E que existe um tipo de resíduo criado para atualização
    Quando eu realizar uma requisição PUT autenticada para atualizar o tipo de resíduo
    Então a atualização do tipo de resíduo deve ser realizada com sucesso
    E os dados atualizados devem ser persistidos corretamente

  Cenário: Excluir um tipo de resíduo com sucesso
    Dado que a API EcoColeta está disponível
    E que existe um tipo de resíduo criado para exclusão
    Quando eu realizar uma requisição DELETE autenticada para excluir o tipo de resíduo
    Então a exclusão do tipo de resíduo deve ser realizada com sucesso
    E o tipo de resíduo excluído não deve mais existir