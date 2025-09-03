package rag;

import java.nio.file.Paths;
import java.util.List;
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

import examples.BuscarData;
import examples.ManipularArquivo;

import com.microsoft.playwright.Playwright;

public class HistoryReborn {
  Playwright playwright;
  Browser browser;
  BrowserContext context;
  // Configurar as opções de lançamento, incluindo 'headless: true'
  // BrowserType.LaunchOptions launchOptions = new
  // BrowserType.LaunchOptions().setHeadless(true);
  Page page2;

  public static void main(String[] args) {
    // lerArquivo();
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

  public String teste() {
    return "";
  }

  @Test
  void historyLinks() {
    List<String> listaComLinks = ManipularArquivo.DadosDoArquivo();
    // System.out.println(listaComLinks);
    for (int id = 154526; id <= 200000; id++) {
      playwright = Playwright.create();
      // browser = playwright.chromium().launch(new
      // BrowserType.LaunchOptions().setHeadless(false));
      context = browser.newContext();
      page2 = context.newPage();
      while (!VerificaInternet.acessaInternet()) {
        page2.waitForTimeout(15000);
      }

      try {
        page2.waitForTimeout(2000);
        String link = "http://historyreborn.net/?module=item&action=view&id=" + id;
        page2.navigate(link);
        page2.waitForTimeout(9000);
        String bodyText = page2.locator("body").innerText();
        page2.screenshot(new ScreenshotOptions().setPath(Paths.get("history/screenshot" + id + ".png")));

        if (bodyText.contains(BuscarData.dataDe2DiasFormatada())
            || bodyText.contains(BuscarData.dataDeOntemFormatada())
            || bodyText.contains(BuscarData.dataAtualFormatada())) {
          String linkRef = link;
          try {
            listaComLinks.add(linkRef);
            System.out.println(link);
          } catch (Exception e) {
            System.err.println("Erro ao salvar o texto: " + e.getMessage());
          }
        } else {
          // listaComLinks.add("Sem Vendas: " + id);
          System.out.println("Sem Vendas: " + id);
        }
      } catch (Exception e) {
        System.out.println("-> Falha ID: " + id);
        // listaComLinks.add("Falha: " + id);
      }
      page2.waitForTimeout(3000);
      ManipularArquivo.salvarLinks(listaComLinks);
      context.close();
      playwright.close();
    }

  }

}