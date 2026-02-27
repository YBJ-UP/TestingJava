package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try{
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inventory_container")));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("inventory_item_name")));

            WebElement[] containers = {
                    driver.findElement(By.xpath("//div[text()='Sauce Labs Bolt T-Shirt']/ancestor::div[@class='inventory_item']")),
                    driver.findElement(By.xpath("//div[text()='Sauce Labs Fleece Jacket']/ancestor::div[@class='inventory_item']")),
                    driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item']")),
                    driver.findElement(By.xpath("//div[text()='Test.allTheThings() T-Shirt (Red)']/ancestor::div[@class='inventory_item']"))
            };

            for(WebElement container : containers){
                obtenerNombrePrecio(container);
                container.findElement(By.tagName("button")).click();
            }

            String cart_badge = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_badge"))).getText();

            assert cart_badge.equals("4");

            System.out.println("Productos agregados exitosamente");
        }
        finally{
            driver.quit();
        }
    }

    private static void obtenerNombrePrecio(WebElement container){
        String nombre = container.findElement(By.className("inventory_item_name")).getText();
        String precio = container.findElement(By.className("inventory_item_price")).getText();
        System.out.println("Producto: " + nombre);
        System.out.println("Precio: " + precio);
    }
}