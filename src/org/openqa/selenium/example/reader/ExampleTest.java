package org.openqa.selenium.example.reader;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
import com.thoughtworks.selenium.Wait;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExampleTest {
  private static final String BASE_URL = "http://www.google.co.uk";
  private Selenium selenium;
  
  @After
  public void tearDown() throws Exception {
    selenium.stop();
  }

  @Test
  public void demoReaderPlay() {
    selenium = new DefaultSelenium("localhost", 4444, "*firefox", BASE_URL);
    selenium.start();

    selenium.open("/reader/play/");
    selenium.waitForPageToLoad("30000");

    selenium.click("welcome-get-started-button");

    String title = selenium.getEval("selenium.browserbot.getDocument().title");
    System.out.println(title);

    waitForTitleToChangeFrom(selenium, "");

    moveToNextSlide(selenium);
    String currentTitle = moveToNextSlide(selenium);

    selenium.keyDown("current-entry", "\\39");
    selenium.keyPress("current-entry", "\\39");
    selenium.keyUp("current-entry", "\\39");
    waitForTitleToChangeFrom(selenium, currentTitle);
  }

  private String getCurrentTitle(Selenium selenium) {
    return selenium.getText("//div[@id='current-entry']//*[@class='entry-title']");
  }
  
  private String moveToNextSlide(final Selenium selenium) {
    final String firstTitle = getCurrentTitle(selenium);
    selenium.click("entry-nav-next");
    waitForTitleToChangeFrom(selenium, firstTitle);
    return getCurrentTitle(selenium);
  }

  private void waitForTitleToChangeFrom(final Selenium selenium, final String firstTitle) {
    new Wait() {
      @Override
      public boolean until() {
        try {
          String currentTitle = getCurrentTitle(selenium);
          return !firstTitle.equals(currentTitle);
        } catch (SeleniumException e) {
          return false;
        }
      }
    }.wait("Title did not change from: " + firstTitle, 60000);
  }
}
