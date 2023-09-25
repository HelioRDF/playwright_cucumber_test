package steps;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class Cadastrar {


@Dado("que estou na tela de cadastro www.portal.com")
public void que_estou_na_tela_de_cadastro_www_portal_com() {
	assertTrue(true);
}

@Quando("informo meu nome")
public void informo_meu_nome() {
	assertTrue(true);
}

@Quando("email")
public void email() {
	assertTrue(true);
}

@Quando("telefone")
public void telefone() {
	assertTrue(true);
}

@Quando("clico em cadastrar")
public void clico_em_cadastrar() {
	assertTrue(true);
}

@Então("recebo uma mensagem de cliente cadastrado com sucesso")
public void recebo_uma_mensagem_de_cliente_cadastrado_com_sucesso() {
	assertTrue(true);
}



}
