package org.openqa.selenium.example.reader;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertFalse;

public class PageObjectTest {
  private WebDriver driver;

  @Test
  public void demoReaderPlay() {
    driver = new FirefoxDriver();

    PlayHomePage play = new PlayHomePage(driver).get();
    PlayList list = play.getStarted();

    list = list.moveToNextSlide();
    list = list.moveToNextSlide();
    
    String currentTitle = list.getCurrentTitle();
    list = list.moveToNextSlideUsingKeyboard();
    
    assertFalse(currentTitle.equals(list.getCurrentTitle()));
  }
  
  @After
  public void tearDown() throws Exception {
    driver.quit();
  }
}
