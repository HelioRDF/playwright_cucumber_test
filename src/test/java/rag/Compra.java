package rag;

import java.util.ArrayList;

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
import java.awt.Toolkit;

public class Compra {
  Playwright playwright;
  Browser browser;
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
  void MonitorarItem() {
    playwright = Playwright.create();
    context = browser.newContext();
    page = context.newPage();
    while (!VerificaInternet.acessaInternet()) {
      page.waitForTimeout(10000);
    }
    ArrayList<Item> itens = new ArrayList<>();
    itens.add(new Item("Crown of Beelzebub", 400110, 19000, 10000, 1, 1));
    itens.add(new Item("Beteleuse Soul", 1000397, 900, 500, 1, 1));
    itens.add(new Item("Omni-Oridecon", 6438, 700, 500, 1, 1));
    itens.add(new Item("Pedaço de Pele do Guardião", 2554, 500, 400, 32000000, 50000000));
    itens.add(new Item("Black Candy", 70058, 40000, 20000, 0, 0));
    itens.add(new Item("[1000] Moeda ROPS", 40108, 1, 1, 62000000, 60000000));
    itens.add(new Item("Cartão VIP [30 Dias]", 37009, 11000, 10000, 1, 1));

    for (Item item : itens) {
      try {
        page.waitForTimeout(2000);
        String link = "https://historyreborn.net/?module=item&action=view&id=" + item.id;
        page.navigate(link);
        page.waitForTimeout(5000);
        int id = 2;
        String tabela = page.locator("#nova-sale-table tbody tr:nth-child(" + id + ") td:nth-child(4)").innerText();
        String valorAtualFormatado;
        int valorAtualZenny = -1;
        int valorAtualRops = -1;
        if (tabela.contains("z")) {
          valorAtualFormatado = tabela.replaceAll("[^0-9]", "");
          valorAtualZenny = Integer.parseInt(valorAtualFormatado);
        }
        while (tabela.contains("z")) {
          id++;
          tabela = page.locator("#nova-sale-table tbody tr:nth-child(" + id + ") td:nth-child(4)").innerText();
        }
        if (tabela.contains("c")) {
          valorAtualFormatado = tabela.replaceAll("[^0-9]", "");
          valorAtualRops = Integer.parseInt(valorAtualFormatado);
        }
        System.out.println(item.toString());
        System.out.println("Menor Valor Rops: " + valorAtualRops);
        System.out.println("Menor Valor Zenny: " + valorAtualZenny);
        if (valorAtualRops !=-1 && valorAtualRops <= item.bomPrecoRop) {
          alertar("Rop");
        }
        if (valorAtualZenny!=-1 && valorAtualZenny <= item.bomPrecoZenny) {
          alertar("Zenny");
        }
        System.out.println("-----------------------------------------------------------------------------");
        page.waitForTimeout(3000);

      } catch (Exception e) {
        System.out.println(e);
      }
    }
    context.close();
    playwright.close();
  }

  public static void alertar(String tipo) {
    Toolkit.getDefaultToolkit().beep();
    System.out.println("Oportunidade de compra identificada: "+tipo);
  }
}