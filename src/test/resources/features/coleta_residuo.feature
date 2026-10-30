# language: pt

Funcionalidade: Gerenciamento de coletas de resíduos
  Como responsável pelo gerenciamento de resíduos
  Quero consultar as coletas cadastradas
  Para acompanhar as operações de coleta

  Cenário: Consultar todas as coletas de resíduos
    Dado que a API EcoColeta está disponível
    Quando eu realizar uma requisição GET para consultar as coletas de resíduos
    Então o código de resposta HTTP da consulta de coletas deve ser 200
    E a resposta deve conter uma lista de coletas de resíduos


  Cenário: Cadastrar uma nova coleta de resíduos
    Dado que a API EcoColeta está disponível
    E que existem um tipo de resíduo e um ponto de coleta válidos
    Quando eu cadastrar uma nova coleta de resíduos
    Então o cadastro da coleta de resíduos deve ser realizado com sucesso
    E a resposta deve conter os dados da coleta cadastrada

  Cenário: Consultar uma coleta de resíduos pelo ID
    Dado que a API EcoColeta está disponível
    E que existem um tipo de resíduo e um ponto de coleta válidos
    E que existe uma coleta de resíduos cadastrada
    Quando eu consultar a coleta de resíduos pelo ID
    Então o código de resposta HTTP da consulta de coleta por ID deve ser 200
    E a resposta deve conter os dados da coleta consultada

  Cenário: Atualizar uma coleta de resíduos existente
    Dado que a API EcoColeta está disponível
    E que existem um tipo de resíduo e um ponto de coleta válidos
    E que existe uma coleta de resíduos cadastrada
    Quando eu atualizar a coleta de resíduos cadastrada
    Então a atualização da coleta de resíduos deve ser realizada com sucesso
    E os dados atualizados da coleta devem estar persistidos

  Cenário: Excluir uma coleta de resíduos existente
    Dado que a API EcoColeta está disponível
    E que existem um tipo de resíduo e um ponto de coleta válidos
    E que existe uma coleta de resíduos cadastrada
    Quando eu excluir a coleta de resíduos cadastrada
    Então a exclusão da coleta de resíduos deve ser realizada com sucesso
    E a coleta de resíduos excluída não deve mais existir