package wp;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Whats {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @Test
    void abriWp() {
        playwright = Playwright.create();

        context = browser.newContext();
        page = context.newPage();
        String link = "https://web.whatsapp.com/";
        page.navigate(link);
        page.waitForTimeout(60000);
        
        context.close();
        playwright.close();
        page.close();

    }

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
        // page = context.newPage();
    }

    @AfterMethod
    void closeContext() {
        context.close();
    }

}
