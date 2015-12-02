package test;

import com.techboy.selenium.beanconfig.Beans;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by christopher on 01/12/2015.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Beans.class)
public class TestGoogle {


@Autowired
private WebDriver browser;

    @Test
    public void lunchBrowser() throws InterruptedException {
     browser.get("http://www.google.com");
        Thread.sleep(1000l);
    }


}
