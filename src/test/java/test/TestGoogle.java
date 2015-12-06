package test;

import com.techboy.selenium.beanconfig.BeanConfig;
import com.techboy.selenium.listeners.ScreenshotTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by christopher on 01/12/2015.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=BeanConfig.class)
public class TestGoogle {

    protected static final Logger LOG = LoggerFactory.getLogger(TestGoogle.class);

@Autowired
private WebDriver driver;

 @Rule
 @Autowired
 public ScreenshotTestRule screenshotTestRule;

    @Test
    public void googleCheeseExample() throws Exception {
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        // Enter something to search for
        element.clear();
        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        LOG.info("Page title is: " + driver.getTitle());

        // TestGoogle's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });

        // Should see: "cheese! - TestGoogle Search"
        LOG.info("Page title is: " + driver.getTitle());
    }

    @Test
    public void googleMilkExample() throws Exception {
        // Create a new WebDriver instance
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.


        // And now use this to visit TestGoogle
        driver.get("http://www.google.com");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        element.clear();
        element.sendKeys("Milk!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        LOG.info("Page title is: " + driver.getTitle());

        // TestGoogle's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("milk!");
            }
        });

        // Should see: "cheese! - TestGoogle Search"
        LOG.info("Page title is: " + driver.getTitle());
    }
}
