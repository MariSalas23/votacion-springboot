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
        // Usamos las opciones de Chrome y configuramos la URL del Selenium Hub
        ChromeOptions options = new ChromeOptions();
        String seleniumHubUrl = System.getenv().getOrDefault("SELENIUM_HUB_URL", "http://host.docker.internal:4444/wd/hub");
        driver = new RemoteWebDriver(new URL(seleniumHubUrl), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    void testRegistroExitoso() {
        // Configuramos la URL de la aplicación
        String baseUrl = System.getenv().getOrDefault("VOTEAPP_URL", "http://host.docker.internal:8080/");
        
        // Accedemos a la aplicación
        driver.get(baseUrl);
        
        // Encontramos los elementos del formulario e ingresamos los datos
        driver.findElement(By.name("id")).sendKeys(String.valueOf(System.currentTimeMillis())); // Genera un ID único
        driver.findElement(By.name("name")).sendKeys("Ana Prueba");
        driver.findElement(By.name("age")).sendKeys("30");

        // Seleccionamos el género usando un dropdown
        WebElement genderDropdown = driver.findElement(By.name("gender"));
        new Select(genderDropdown).selectByValue("FEMALE");

        // Seleccionamos el estado de vida usando un dropdown
        WebElement aliveDropdown = driver.findElement(By.name("alive"));
        new Select(aliveDropdown).selectByValue("true");

        // Hacemos clic en el botón de envío del formulario
        driver.findElement(By.cssSelector("form button[type='submit']")).click();
        
        // Esperamos y verificamos el mensaje de registro exitoso
        esperarYVerificarMensaje("Registro exitoso");
    }

    private void esperarYVerificarMensaje(String esperado) {
        // Esperamos a que el mensaje esté presente en la página
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("message")));
        String texto = message.getText();
        System.out.println("Mensaje recibido: " + texto);
        
        // Verificamos que el mensaje contiene la palabra esperada
        assertTrue(texto.contains(esperado), "No se encontró el mensaje esperado: " + esperado);
    }

    @AfterEach
    void tearDown() {
        // Cerramos el navegador después de cada prueba
        if (driver != null) {
            driver.quit();
        }
    }
}
