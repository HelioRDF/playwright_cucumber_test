package steps;



import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class Login {

	@Dado("estou na página de login www.portal.com")
	public void estou_na_página_de_login_www_portal_com() {
		assertTrue(true);
	}

	@Quando("Inform o CPF")
	public void inform_o_cpf() {
		assertTrue(true);
	}

	@Quando("informa  a senha")
	public void informa_a_senha() {
		assertTrue(true);
	}

	@Quando("clico em entrar")
	public void clico_em_entrar() {
		assertTrue(true);
	}
	
	@Test
	@Então("é redirecionado para página home")
	public void é_redirecionado_para_página_home() {
		assertTrue(true);
	}

}
