
# language: pt

Funcionalidade: Gerenciamento de pontos de coleta
  Como usuário da API EcoColeta
  Quero consultar e gerenciar pontos de coleta
  Para garantir o funcionamento do sistema de gestão de resíduos

  Cenário: Consultar todos os pontos de coleta com sucesso
    Dado que a API EcoColeta está disponível
    Quando eu realizar uma requisição GET para consultar os pontos de coleta
    Então o código de resposta HTTP da consulta de pontos de coleta deve ser 200
    E a resposta deve conter uma lista de pontos de coleta

  Cenário: Cadastrar um novo ponto de coleta com sucesso
    Dado que a API EcoColeta está disponível
    Quando eu cadastrar um novo ponto de coleta
    Então o cadastro do ponto de coleta deve ser realizado com sucesso
    E a resposta deve conter os dados do ponto de coleta cadastrado

  Cenário: Consultar um ponto de coleta pelo ID com sucesso
    Dado que a API EcoColeta está disponível
    E que existe um ponto de coleta cadastrado para consulta
    Quando eu consultar o ponto de coleta pelo ID
    Então o código de resposta HTTP da consulta por ID deve ser 200
    E a resposta deve conter os dados do ponto de coleta consultado

  Cenário: Atualizar um ponto de coleta com sucesso
    Dado que a API EcoColeta está disponível
    E que existe um ponto de coleta cadastrado para atualização
    Quando eu atualizar os dados do ponto de coleta
    Então a atualização do ponto de coleta deve ser realizada com sucesso
    E os dados atualizados do ponto de coleta devem ser persistidos corretamente


  Cenário: Excluir um ponto de coleta com sucesso
    Dado que a API EcoColeta está disponível
    E que existe um ponto de coleta cadastrado para exclusão
    Quando eu excluir o ponto de coleta pelo ID
    Então a exclusão do ponto de coleta deve ser realizada com sucesso
    E o ponto de coleta excluído não deve mais ser encontrado
