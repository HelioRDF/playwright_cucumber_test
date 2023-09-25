package examples;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObject.Login;

public class Autenticar {
	Login login;

	@BeforeClass
	void launchBrowser() {
		login = new Login();
	}

	@AfterClass
	void closeBrowser() {
		login.fecharContext();
		login.fecharPlaywright();
		
	}

	@BeforeMethod
	void createContextAndPage() {}

	@AfterMethod
	void closeContext() {}

	@Test
	void loginPortalCliente() {
		login.acessarPortal();
		login.infos();
		login.informarUsuario("756.279.760-98");
		login.informarSenha("Teste@234");
		login.print("loginPortalClienteInicio");
		
		assertEquals(login.getTitle(), "Login");
		assertEquals(login.getUrl(), "https://fdbidpl-tst1.outsystemsenterprise.com/");
		assertTrue(login.contem(login.getUrl(),"fdbidpl-tst1"));
		assertTrue(login.buscarNoElementoHtml("Esqueci minha senha"));
				
		login.aguardar(5000);	
		login.clicarEntrar();			
		login.print("loginPortalClienteFim");
		login.aguardar(5000);	
	}
	
	@Test
	void paginaloginPortalCliente() {
		login.acessarPortal();
		login.print("loginPortalClienteInicio");
		
	}
}