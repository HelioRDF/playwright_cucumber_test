# language: pt

@Vendas
Funcionalidade: Vendas


  @CT03
  Cenário: Realizar cadastro
    Dado que estou na tela de cadastro www.portal.com
    Quando informo meu nome
    E email
    E telefone
    E clico em cadastrar
    Então recebo uma mensagem de cliente cadastrado com sucesso

  