package com.vote.demo.gui;

import java.net.URL;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterFormGUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    void testRegistroExitoso() {
        String baseUrl = System.getenv().getOrDefault("VOTEAPP_URL", "http://localhost:8080/");
        driver.get(baseUrl);
        driver.findElement(By.name("id")).sendKeys(String.valueOf(System.currentTimeMillis()));
        driver.findElement(By.name("name")).sendKeys("Ana Prueba");
        driver.findElement(By.name("age")).sendKeys("30");
        new Select(driver.findElement(By.name("gender"))).selectByValue("FEMALE");
        new Select(driver.findElement(By.name("alive"))).selectByValue("true");
        driver.findElement(By.cssSelector("form button[type='submit']")).click();
        esperarYVerificarMensaje("Registro exitoso");
    }

    private void esperarYVerificarMensaje(String esperado) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("message")));
        String texto = message.getText();
        System.out.println("Mensaje recibido: " + texto);
        assertTrue(texto.contains(esperado), "No se encontró el mensaje esperado: " + esperado);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
