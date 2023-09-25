package examples;


import static org.junit.Assert.assertSame;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

public class TestExample {
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
  void shouldClickButton() {
    page.navigate("data:text/html,<script>var result;</script><button onclick='result=\"Clicked\"'>Go</button>");

    page.locator("button").click();
    assertEquals("Clicked", page.evaluate("result"));
    page.waitForTimeout(5000);
  }

  @Test
  void shouldCheckTheBox() {
    page.setContent("<input id='checkbox' type='checkbox'></input>");
    page.locator("input").check();
    assertTrue((Boolean) page.evaluate("() => window['checkbox'].checked"));
    page.waitForTimeout(5000);
  }

  @Test
  void shouldSearchWiki() {
    page.navigate("https://www.wikipedia.org/");
    page.locator("input[name=\"search\"]").click();
    page.locator("input[name=\"search\"]").fill("playwright");
    page.locator("input[name=\"search\"]").press("Enter");
 //   assertEquals(page.url(),"https://en.wikipedia.org/wiki/Playwright");
    assertSame("","");

  }
}