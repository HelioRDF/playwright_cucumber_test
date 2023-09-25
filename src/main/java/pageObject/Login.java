package pageObject;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Login {
	private Playwright playwright;
	private Browser browser;
	private BrowserContext context;
	private Page page;

	public Login() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		context = browser.newContext();
		page = context.newPage();
	}

	public void fecharPlaywright() {
		playwright.close();
	}

	public void fecharContext() {
		context.close();
	}

	public void acessarPortal() {
		page.setViewportSize(1850, 900);
		page.navigate("https://fdbidpl-tst1.outsystemsenterprise.com");
		page.waitForTimeout(3000);

	}

	// CPF ou CNPJ
	public void informarUsuario(String usuario) {
		page.locator("#b4-InputMask").type(usuario);
		System.out.println("Usuário = " + usuario);
	}

	public void informarSenha(String senha) {
		page.locator("#Input_Password").fill(senha);
		System.out.println("Senha = " + senha);
	}

	public String getUrl() {
		String url = page.url();
		return url;
	}

	public String getTitle() {
		String title = page.title();
		return title;
	}

	public Boolean buscarNoElementoHtml(String texto) {
		
		return  page.content().toString().contains(texto);

	}

	public void print(String teste) {
		aguardar(1000);
		page.screenshot(new Page.ScreenshotOptions()
				.setPath(Paths.get("./src/test/resources/screenshot/login/" + teste + ".png")).setFullPage(true));
		System.out.println("\n...Print da página =  " + teste + "\n");
	}

	public Boolean contem(String texto, String buscarTexto) {
		System.out.println("contém? " + texto.contains(buscarTexto));
		return texto.contains(buscarTexto);
	}

	public void clicarEntrar() {
		page.getByText("Entrar").click();
	}

	public void aguardar(int tempo) {
		page.waitForTimeout(tempo);

	}

	public void infos() {

		System.out.println("\n===============================================");
		System.out.println("Página = " + getTitle());
		System.out.println("Url = " + getUrl());
		System.out.println("===============================================\n");

	}

}
