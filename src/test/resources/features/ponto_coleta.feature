
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
