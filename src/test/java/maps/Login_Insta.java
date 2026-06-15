package maps;

import java.util.Arrays;
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
import com.microsoft.playwright.options.AriaRole;

public class Login_Insta {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @Test
    void loginInstagram() {
        String link = "https://www.instagram.com/";
        List<String> perfis = Arrays.asList(
                (link + "helio.francaa/"),
                (link + "helio.francaa/"),
                (link + "helio.francaa/"));






                

        String msg = "Olá! Tudo bem?\n" + //
                "\n" + //
                "Quero te apresentar uma solução que pode ajudar o seu estabelecimento a oferecer uma experiência mais moderna e prática para os clientes.\n"
                + //
                "\n" + //
                "Com o paladar, o seu cardápio se torna mais inteligente, facilitando a escolha dos produtos, melhorando o atendimento e tornando o processo de pedido muito mais simples e rápido.\n"
                + //
                "\n" + //
                "A proposta é ajudar o seu negócio a vender melhor, organizar os pedidos com mais eficiência e oferecer mais comodidade para quem compra.\n"
                + //
                "\n" + //
                "Acesse nosso site para mais detalhes, ou entre em contato conosco.\n" + //
                "\n" + //
                "Ficarei muito feliz em te mostrar como funciona e como isso pode se adaptar à realidade do seu estabelecimento.\n"
                + //
                "\n" + //
                "Agradeço pela atenção e desejo um ano abençoado e de muito sucesso nos negócios!\n" + //
                "\n" + //
                "https://www.paladarai.com.br/home";
        playwright = Playwright.create();

        context = browser.newContext();
        page = context.newPage();
        String p = System.getenv("GETPASS");
        page.waitForTimeout(3000);

        page.navigate("https://www.instagram.com/");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de celular, nome de")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de celular, nome de"))
                .fill("paladar_ai");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Senha")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Senha")).fill(p);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Entrar").setExact(true)).click();
        page.waitForTimeout(8000);
        // Exemplo: Imprimindo os resultados
        page.waitForTimeout(8000);
        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Salvar
        // informações")).click();
        page.waitForTimeout(8000);

        enviarMensagemParaPerfis(perfis, msg);

        page.waitForTimeout(35000);

        context.close();
        playwright.close();
        page.close();

    }

    private void enviarMensagemParaPerfis(List<String> linksPerfis, String msg) {
        for (String linkPerfil : linksPerfis) {
            page.waitForTimeout(5000);
            page.navigate(linkPerfil);
            page.waitForTimeout(6000);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Enviar mensagem")).click();
            page.waitForTimeout(3000);
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mensagem")).fill(msg);
            page.waitForTimeout(3000);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Enviar").setExact(true)).click();
            page.waitForTimeout(5000);
        }
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
