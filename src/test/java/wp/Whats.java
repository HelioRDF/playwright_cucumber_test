package wp;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
import com.microsoft.playwright.options.AriaRole;

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

        nextStep("Clique em OK para continuar a automação.", "Confirmação");

        page.waitForTimeout(3000);
        //page.getByTestId("list-item-0").getByTestId("cell-frame-title").click();
        //page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Helio Franca")).click();
        page.getByText("Helio Franca").click();
        page.getByRole(AriaRole.PARAGRAPH).click();
        page.getByTestId("conversation-compose-box-input").fill("ola");
        page.waitForTimeout(3000);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Enviar")).click();
        nextStep("Clique em OK para continuar a automação.", "Confirmação");
        String conversationTitle = page.getByTestId("list-item-0").getByTestId("cell-frame-title").textContent();
        System.out.println("Título da conversa: " + conversationTitle);
        context.close();
        playwright.close();
        page.close();
    }

    void nextStep(String mensagem, String titulo) {
        try {
            SwingUtilities.invokeAndWait(() -> JOptionPane.showMessageDialog(
                    null,
                    mensagem,
                    titulo,
                    JOptionPane.INFORMATION_MESSAGE));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Confirmação interrompida", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
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
