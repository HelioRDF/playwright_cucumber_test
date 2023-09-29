package examples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class JwtTest2 {

	@Test
	public static void main(String[] args) {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

		String storageStateJson = null;
		try {
			storageStateJson = new String(Files.readAllBytes(Paths.get("./jwt/state_alanvoigt.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BrowserContext context = browser.newContext(new Browser.NewContextOptions().setStorageState(storageStateJson));

		Page page = context.newPage();

		// Navigate to the website
		page.navigate("https://react-redux.realworld.io/");

		// Get the inner HTML of the element with class '.feed-toggle'
		page.waitForTimeout(9000);
		// Close the browser
		browser.close();

		playwright.close();

	}

}