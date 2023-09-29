package pageObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserContext.StorageStateOptions;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Control {
	private Playwright playwright;
	private Browser browser;
	private BrowserContext context;
	private Page page;

	public Control() {
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

	public void aguardar(int tempo) {
		page.waitForTimeout(tempo);
	}

	public void acessarUrl(String url) {
		page.setViewportSize(1850, 900);
		page.navigate(url);
		page.waitForTimeout(5000);

	}

	public void informarUsuarioInputType(String type, String usuario) {
		page.fill("input[type = " + type + "]", usuario);
		// page.press("input[type = 'email']", "Tab");

	}

	// CPF ou CNPJ
	public void informarSenhaInputType(String type, String senha) {
		page.type("input[type = " + type + "]", senha);
	}

	// CPF ou CNPJ
	public void clickElementoForm(String valor) {
		page.click("form >> " + valor);
		page.waitForTimeout(12000);

	}

	public void contextJwt(String nome) {
		// NewContextOptions options = new NewContextOptions();
		Path path = Paths.get("./jwt/" + nome);
		// StorageStateOptions option = new StorageStateOptions();
		context = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(path));

	}

	// CPF ou CNPJ
	public void salvarStateJWT(String nome) {
		Path path = Paths.get("./jwt/" + nome);
		StorageStateOptions option = new StorageStateOptions();
		context.storageState(option.setPath(path));

	}

	// CPF ou CNPJ
	public void contextJwt2() {
		String storageStateJson = null;
		try {
			storageStateJson = new String(Files.readAllBytes(Paths.get("./jwt/state_alanvoigt.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		context = browser.newContext(new Browser.NewContextOptions().setStorageState(storageStateJson));
	}

	// Load the saved storage state

}
