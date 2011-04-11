package org.openqa.selenium.example.reader;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.Keys.ARROW_RIGHT;

public class MigratedTest {
  private static final String BASE_URL = "http://www.google.co.uk";
  private WebDriver driver;
  
  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

  @Test
  public void demoReaderPlay() {
    driver = new FirefoxDriver();

    driver.get(BASE_URL + "/reader/play/");
    
    WebElement getStarted = driver.findElement(By.id("welcome-get-started-button"));
    getStarted.click();

    Wait<WebDriver> wait = new WebDriverWait(driver, 30);
    wait.until(titleChangesFrom(""));

    moveToNextSlide(driver, wait);
    String currentTitle = moveToNextSlide(driver, wait);
    
    driver.findElement(By.id("current-entry")).sendKeys(ARROW_RIGHT);
    wait.until(titleChangesFrom(currentTitle));
  }

  private String getCurrentTitle(WebDriver driver) {
    WebElement div = driver.findElement(By.id("current-entry"));
    return div.findElement(By.className("entry-title")).getText();
  }
  
  private String moveToNextSlide(WebDriver driver, Wait<WebDriver> wait) {
    String firstTitle = getCurrentTitle(driver);
    
    driver.findElement(By.id("entry-nav-next")).click();
    
    return wait.until(titleChangesFrom(firstTitle));
  }

  private ExpectedCondition<String> titleChangesFrom(final String firstTitle) {
    return new ExpectedCondition<String>() {
      public String apply(WebDriver driver) {
        String currentTitle = getCurrentTitle(driver);
        if (!firstTitle.equals(currentTitle)) {
          return currentTitle;
        }
        return null;
      }
    };
  }
}
