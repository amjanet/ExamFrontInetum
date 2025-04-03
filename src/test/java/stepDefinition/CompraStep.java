package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class CompraStep {
    String app = "https://www.saucedemo.com/";
    WebDriver driver;

    @Given("que soy un cliente de Sauce Demo")
    public void enter_the_window() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-save-password-bubble"); // Deshabilita el popup
        options.addArguments("--disable-popup-blocking"); // Desactiva bloqueos de popups
        driver = new EdgeDriver(options);
        driver.get(app);
    }

    @When("inicio sesión con el usuario {string} y la contraseña {string}")
    public void inicioSesiónConElUsuarioYLaContraseña(String user, String pass) {
        driver.findElement(By.id("user-name")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.id("login-button")).click();
    }


    @And("agrego {string} al carrito")
    public void agregoAlCarrito(String product) {
        List<WebElement> productElements = driver.findElements(By.className("inventory_item"));

        for (WebElement item : productElements) {
            String productName = item.findElement(By.className("inventory_item_name")).getText();

            if (productName.equals(product)) {
                WebElement addToCartButton = item.findElement(By.cssSelector(".btn_inventory"));
                addToCartButton.click();
                break;


            }
        }
    }

    @And("procedo al checkout")
    public void procedoAlCheckout() throws InterruptedException {
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("checkout")).click();
    }

    @And("ingreso mi información de envío {string}  {string} y {string}")
    public void ingresoMiInformaciónDeEnvíoY(String name, String lastname, String codpostal) {
        driver.findElement(By.id("first-name")).sendKeys(name);
        driver.findElement(By.id("last-name")).sendKeys(lastname);
        driver.findElement(By.id("postal-code")).sendKeys(codpostal);
        driver.findElement(By.id("continue")).click();
    }

    @And("confirmo la compra")
    public void confirmoLaCompra() {
        driver.findElement(By.id("finish")).click();
    }

    @Then("la compra se completa exitosamente")
    public void laCompraSeCompletaExitosamente() {
        WebElement confirmationMessage = driver.findElement(By.className("complete-header"));
        Assert.assertEquals(confirmationMessage.getText(), "Thank you for your order!", "El pago no fue exitoso.");
    }
}
