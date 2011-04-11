package org.openqa.selenium.example.reader;

import static org.openqa.selenium.Keys.ARROW_RIGHT;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Models the list of items that Play presents as a slideshow.
 */
public class PlayList {

  private String currentTitle = "";
  private Wait<WebDriver> wait;
  
  @FindBys({
    @FindBy(id = "current-entry"), @FindBy(className = "entry-title")
  })
  private WebElement entryTitle;
  @FindBy(id = "entry-nav-next")
  private WebElement next;

  public PlayList(WebDriver driver) {
    this.wait = new WebDriverWait(driver, 30);
    PageFactory.initElements(driver, this);
  }

  public PlayList waitForSlide() {
    currentTitle = wait.until(titleChangesFrom(currentTitle));
    return this;
  }
  
  /**
   * @return The PlayList to use once the next item has advanced.
   */
  public PlayList moveToNextSlide() {
    next.click();
    return waitForSlide();
  }

  public PlayList moveToNextSlideUsingKeyboard() {
    next.sendKeys(ARROW_RIGHT);
    return waitForSlide();
  }

  public String getCurrentTitle() {
    return entryTitle.getText();
  }
  
  private ExpectedCondition<String> titleChangesFrom(final String firstTitle) {
    return new ExpectedCondition<String>() {
      public String apply(WebDriver ignored) {
        String currentTitle = getCurrentTitle();
        if (!firstTitle.equals(currentTitle)) {
          return currentTitle;
        }
        return null;
      }
    };
  }
}
