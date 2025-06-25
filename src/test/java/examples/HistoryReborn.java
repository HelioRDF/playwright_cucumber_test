package examples;

import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
import com.microsoft.playwright.Page.ScreenshotOptions;
import com.microsoft.playwright.Playwright;

public class HistoryReborn {
  // Shared between all tests in this class.
  Playwright playwright;
  Browser browser;

  // New instance for each test method.
  BrowserContext context;
  // Page page;
  Page page2;

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

  public String teste() {
    return "";
  }
  
  @Test
  void mercadoHistoryReborn() {
    String texto="";
    for (int id = 6690; id <= 8000; id++) {
      playwright = Playwright.create();
      browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      context = browser.newContext();
      page2 = context.newPage();
      try {
        page2.waitForTimeout(10000);
        String link = "http://historyreborn.net/?module=item&action=view&id=" + id;
        page2.navigate(link);
        page2.waitForTimeout(20000);
        String bodyText = page2.locator("body").innerText();
        page2.screenshot(new ScreenshotOptions().setPath(Paths.get("history/screenshot"+id+".png")));
        if (bodyText.contains("22/06/2025")||bodyText.contains("23/06/2025")) {
        String nomeArquivo = "History_Link.txt";
        String linkRef ="\n"+link;
        texto =texto+linkRef;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write(texto);
            System.out.println("Texto salvo com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o texto: " + e.getMessage());
        }
        }
      } catch (Exception e) {
      //  System.out.println("Falha ID:" + id);
      }
      page2.waitForTimeout(20000);
      context.close();
     playwright.close();
    }
  }
}