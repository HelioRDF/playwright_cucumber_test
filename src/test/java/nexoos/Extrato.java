package nexoos;

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

public class Extrato {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @Test
    void ConsultarExtrato() {
        playwright = Playwright.create();

        context = browser.newContext();
        page = context.newPage();
        String link = "https://investimento.nexoos.com.br";
        page.navigate(link);
        page.waitForTimeout(2000);
        page.locator("#email").fill("heliordf@hotmail.com");
        String pass = System.getenv("PASSNEXOOS");
        page.locator("#password").fill(pass);
        page.getByText("Entrar").click();
        page.waitForTimeout(3000);
        page.navigate("https://investimento.nexoos.com.br/operacoes-financeiras/extrato");
        page.waitForTimeout(2000);
        // Localiza o grupo de elementos
        Locator buttons = page.getByTestId("AddIcon");

        // Descobre quantos existem na tela no momento
        int count = buttons.count();

        page.waitForTimeout(5000);
        // Itera sobre cada um pelo índice
        while (buttons.count() > 0) {
            page.waitForTimeout(1000);
            buttons.nth(0).click();
            page.waitForTimeout(800);
        }

        page.waitForTimeout(2000);

        // Localiza todos os h1 da página
        Locator cabecalhos = page.locator("h1");

        // Extrai o texto de todos e joga para uma lista de Strings
        List<String> listaDeTextos = cabecalhos.allTextContents();
        double total = 0;
        double totalResumido = 0;
        double saque = 0;

        for (int i = 3; i < listaDeTextos.size(); i += 2) {
            String texto = listaDeTextos.get(i);
            String valorRecebido = "";

            int x = i + 1;
            // Só tenta pegar o próximo se ele estiver dentro do limite da lista
            if (x < listaDeTextos.size()) {

                valorRecebido = listaDeTextos.get(x);
                String valorLimpo = valorRecebido.replaceAll("[^0-9,]", "").replace(",", ".");
                if (valorRecebido.contains("-")) {
                    valorLimpo = "-" + valorLimpo;
                }
                ExtratoDeTexto.salvarItemResumo(texto, valorRecebido);
                // System.out.println(valorLimpo);
                double valorFinal = Double.parseDouble(valorLimpo);
                if (texto.contains("Remuneração") || texto.contains("000") && valorFinal > 0) {
                    total += valorFinal;
                } else if (texto.contains("Saque feito")) {
                    saque += valorFinal;
                } else if (texto.contains("janeiro") && valorFinal > 0) {
                    System.out.println("-----" + texto + " | " + valorFinal + " | " + valorRecebido);
                    totalResumido += valorFinal;
                }
            } else {
                valorRecebido = "VALOR AUSENTE"; // Ou ignore
            }

            // System.out.println(texto + " | " + valorRecebido);
        }
        String totalTxt = String.valueOf(total);
        String totalResumidoTxt = String.valueOf(totalResumido);
        String saqueTxt = String.valueOf(saque);
        ExtratoDeTexto.salvarItemResumo("Total ", totalTxt);
        ExtratoDeTexto.salvarItemResumo("TotalResumido ", totalResumidoTxt);
        ExtratoDeTexto.salvarItemResumo("Saque ", saqueTxt);

        // Exemplo: Imprimindo os resultados
        page.waitForTimeout(30000);

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
