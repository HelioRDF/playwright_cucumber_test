package examples;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Portal2 {
	// Shared between all tests in this class.
	Playwright playwright;
	Browser browser;

	// New instance for each test method.
	BrowserContext context;
	Page page;

	@BeforeClass
	void launchBrowser() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

	}

	@AfterClass
	void closeBrowser() {
		playwright.close();
	}

	@BeforeMethod
	void createContextAndPage() {
		context = browser.newContext();
		page = context.newPage();
	}

	@AfterMethod
	void closeContext() {
		context.close();
	}

	@Test
	void loginPortalCliente() {
		page.setViewportSize(1850, 900);
		page.navigate("https://fdbidpl-tst1.outsystemsenterprise.com");
		page.waitForTimeout(3000);
		String title = page.title();
		String url = page.url();
		page.locator("#b4-InputMask").type("756.279.760-98");
		page.locator("#Input_Password").fill("Teste@234");
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot/screenshot.png")).setFullPage(true));
		assertEquals(title, "Login");
		assertEquals(url, "https://fdbidpl-tst1.outsystemsenterprise.com/");
		System.out.println(title);
		page.isVisible(title);
		assertTrue(url.contains("fdbidpl-tst1"));
		page.getByText("Entrar").click();
		page.waitForTimeout(5000);
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot/screenshot2.png")).setFullPage(true));
		page.waitForTimeout(10000);
	}
}