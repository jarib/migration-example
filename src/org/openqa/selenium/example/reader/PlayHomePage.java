package org.openqa.selenium.example.reader;

import static junit.framework.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 * Models the home page of the Reader Play
 */
public class PlayHomePage extends LoadableComponent<PlayHomePage> {
  private static final String BASE_URL = "http://www.google.co.uk/reader/play";

  private final WebDriver driver;
  @FindBy(id = "welcome-get-started-button")
  private WebElement getStarted;

  /**
   * @param driver The WebDriver instance to use
   */
  public PlayHomePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  @Override
  protected void isLoaded() throws Error {
    String url = driver.getCurrentUrl();
    assertTrue("Expected url to start with " + BASE_URL + " but was " + url,
        url.startsWith(BASE_URL));
  }

  @Override
  protected void load() {
    driver.get(BASE_URL);
  }

  /**
   * @return The list of play items
   */
  public PlayList getStarted() {
    getStarted.click();

    PlayList list = new PlayList(driver);
    return list.waitForSlide();
  }
}
