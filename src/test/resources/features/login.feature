# language: pt
Funcionalidade: Login

  @CT01
  Cenário: Realizar login com sucesso
    Dado estou na página de login www.portal.com
    Quando Inform o CPF
    E informa  a senha
    E clico em entrar
    Então é redirecionado para página home

  @CT02
  Cenário: Realizar cadastro
    Dado que estou na tela de cadastro www.portal.com
    Quando informo meu nome
    E email
    E telefone
    E clico em cadastrar
    Então recebo uma mensagem de cliente cadastrado com sucesso

  