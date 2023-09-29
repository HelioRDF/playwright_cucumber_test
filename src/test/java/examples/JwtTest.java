package examples;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObject.Control;

public class JwtTest {
	Control login;

	@BeforeClass
	void launchBrowser() {
		login = new Control();
	}

	@AfterClass
	void closeBrowser() {
		login.fecharContext();
		login.fecharPlaywright();

	}

	@BeforeMethod
	void createContextAndPage() {
	}

	@AfterMethod
	void closeContext() {
	}

	@Test
	void loginCliente() {
		login.acessarUrl("https://react-redux.realworld.io/#/login");
		login.informarUsuarioInputType("email", "alanvoigt@yahoo.com.br");
		login.informarSenhaInputType("password", "test123");
		login.clickElementoForm( "'Sign in'");
		login.salvarStateJWT("state_alanvoigt.json");
	}
	
	@Test
	void loginCliente2() {
		login.contextJwt2();
		login.acessarUrl("https://react-redux.realworld.io");

	}

}