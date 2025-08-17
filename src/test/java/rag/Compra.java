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
    boolean comprar = false;
    while (!VerificaInternet.acessaInternet()) {
      page.waitForTimeout(10000);
    }
    ArrayList<Item> itens = new ArrayList<>();
    itens.add(new Item("Pedaço de Pele do Guardião", 2554, 500, 400, 32000000, 25000000));
    itens.add(new Item("Crown of Beelzebub", 400110, 19000, 10000, 1, 1));
    itens.add(new Item("Beteleuse Soul", 1000397, 900, 500, 1, 1));
    itens.add(new Item("Nebula Suit of Concentration", 450171, 70000, 40000, 1, 1));
    itens.add(new Item("Omni-Oridecon", 6438, 700, 500, 1, 1));
    itens.add(new Item("Super Omni-Oridecon", 70001, 1400, 1200, 1, 1));
    itens.add(new Item("Black Candy", 70058, 40000, 20000, 0, 0));
    itens.add(new Item("Bênção do Ferreiro", 6635, 120, 100, 9000000, 7000000));
    itens.add(new Item("Manual de Mascar", 14799, 2200, 2000, 1, 1));
    itens.add(new Item("Caixa de Areia de Bruxa [30k]", 11167, 5000, 4000, 300000000, 280000000));
    itens.add(new Item("Elixir Carnavalesco", 11248, 2100, 2000, 1, 1));
    itens.add(new Item("Mega-Elunium", 6439, 490, 450, 12000000, 10000000));
    itens.add(new Item("Super Mega-Elunium", 70002, 3300, 3100, 1, 1));
    itens.add(new Item("[1000] Moeda ROPS", 40108, 1, 1, 62000000, 60000000));
    itens.add(new Item("Bolsa de Rubis [100kk]", 40400, 1900, 1600, 1, 1));
    itens.add(new Item("Bolsa de Diamantes [1b]", 40097, 19000, 18000, 1, 1));
    itens.add(new Item("Cartão VIP [30 Dias]", 37009, 11000, 10000, 1, 1));
    itens.add(new Item("Mini-Refinadora", 33362, 12000, 11000, 1, 1));

    int contador=0;
    for (Item item : itens) {
      contador++;
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
        System.out.println(contador+" - "+item.toString());
        System.out.println("Menor Valor Rops: " + valorAtualRops);
        System.out.println("Menor Valor Zenny: " + valorAtualZenny);
        if (valorAtualRops != -1 && valorAtualRops <= item.bomPrecoRop) {
          alertar("Rop");
          comprar = true;
        }
        if (valorAtualZenny != -1 && valorAtualZenny <= item.bomPrecoZenny) {
          alertar("Zenny");
          comprar = true;
        }
        System.out.println("-----------------------------------------------------------------------------");
        page.waitForTimeout(3000);

      } catch (Exception e) {
        System.out.println(e);
      }
    }
    System.out.println("Comprar? " + comprar);
    context.close();
    playwright.close();
  }

  public static void alertar(String tipo) {
    Toolkit.getDefaultToolkit().beep();
    System.out.println("Oportunidade de compra identificada: " + tipo);
  }
}