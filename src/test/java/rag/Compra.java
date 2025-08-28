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

  @Test
  void MonitorarItem() {
    boolean loop = true;
    while (loop) {

      playwright = Playwright.create();
      context = browser.newContext();
      page = context.newPage();
      boolean comprar = false;
      while (!VerificaInternet.acessaInternet()) {
        page.waitForTimeout(10000);
      }
     
      int contador = 0;
      for (Item item : Item.listaDeItens()) {
        contador++;
        try {
          page.waitForTimeout(2000);
          String link = "https://historyreborn.net/?module=item&action=view&id=" + item.id;
          page.navigate(link);
          page.waitForTimeout(5000);
          int id = 2;
          String tabela;
          try {
            tabela = page.locator("#nova-sale-table tbody tr:nth-child(" + id + ") td:nth-child(4)").innerText();
          } catch (Exception e) {
            continue;
          }

          String valorAtualFormatado;
          double valorAtualZenny = -1;
          double valorAtualRops = -1;
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
          System.out.println(contador + " - " + item.toString());
          System.out.println("Valor Atual: " + Item.valorFormatado(valorAtualRops) + "c | Esperado: "+Item.valorFormatado(item.bomPrecoRop)+"c");
          System.out.println("Valor Atual: " + Item.valorFormatado(valorAtualZenny) + "z | Esperado: "+Item.valorFormatado(item.bomPrecoZenny)+"z");
          if (valorAtualRops != -1 && valorAtualRops <= item.bomPrecoRop) {
            alertar("Rop", link);
            comprar = true;
          }
          if (valorAtualZenny != -1 && valorAtualZenny <= item.bomPrecoZenny) {
            alertar("Zenny", link);
            comprar = true;
          }
          System.out.println("-----------------------------------------------------------------------------");
          page.waitForTimeout(3000);

        } catch (Exception e) {
          System.out.println(e);
        }
      }
      System.out.println("Comprar? " + comprar);
      page.waitForTimeout(600000);
      context.close();
    }
    playwright.close();
  }

  public static void alertar(String tipo, String link) {
    Toolkit.getDefaultToolkit().beep();
    System.out.println("Oportunidade de compra identificada: " + tipo);
    System.out.println(link);
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
}