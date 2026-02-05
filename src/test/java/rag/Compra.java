package rag;

import java.awt.Toolkit;

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

public class Compra {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @Test
    void MonitorarItem3() {
        boolean loop = true;
        int intervalo = 3000;
        ManipularArquivoCompra.DadosDoArquivo();
        while (loop) {

            boolean comprar = false;

            int contador = 0;
            for (Item item : Item.listaDeItens3()) {
                playwright = Playwright.create();

                context = browser.newContext();
                page = context.newPage();
                while (!VerificaInternet.acessaInternet()) {
                    page.waitForTimeout(10000);
                }
                contador++;

                try {
                    page.waitForTimeout(intervalo);
                    String link = "http://historyreborn.net/?module=item&action=view&id=" + item.id;
                    page.navigate(link);
                    page.waitForTimeout(intervalo);
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
                    ManipularArquivoCompra.salvarItemResumo(item, Item.valorFormatado(valorAtualRops) + "c", Item.valorFormatado(valorAtualZenny));
                    System.out.println(contador + " - " + item.toString());
                    System.out.println("Valor Atual: " + Item.valorFormatado(valorAtualRops) + "c | Esperado: "
                            + Item.valorFormatado(item.bomPrecoRop) + "c");
                    System.out.println("Valor Atual: " + Item.valorFormatado(valorAtualZenny) + "z | Esperado: "
                            + Item.valorFormatado(item.bomPrecoZenny) + "z");
                    if (valorAtualRops != -1 && valorAtualRops <= item.bomPrecoRop) {
                        alertar("Rop", link);
                        ManipularArquivoCompra.salvarItem(item, valorAtualRops + "c");
                        comprar = true;
                    }
                    if (valorAtualZenny != -1 && valorAtualZenny <= item.bomPrecoZenny) {
                        alertar("Zenny", link);
                        ManipularArquivoCompra.salvarItem(item, valorAtualZenny + "c");
                        comprar = true;
                    }
                    page.waitForTimeout(intervalo);

                } catch (Exception e) {
                    System.out.println(e);
                }
                context.close();
                playwright.close();
                page.close();

            }

            ManipularArquivoCompra.separdorDeLinhas();
            System.out.println("\n----------------------------------------------------------------\n\n");
            System.out.println("Comprar? " + comprar);

        }
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
