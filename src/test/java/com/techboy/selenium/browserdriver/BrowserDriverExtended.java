package com.techboy.selenium.browserdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

/**
 * Created by christopher on 01/12/2015.
 */
@Component
public class BrowserDriverExtended {
    /**
     * Sizzle CSS Selector implementation
     */

    private BrowserDriverExtended(){}

    private static SizzleSelector sizzleSelector;

    public static class FirefoxDriverExtended extends FirefoxDriver {

        /**
         * Driver constructor
         *
         * @param desiredCapabilities to be passed into the standard FirefoxDriver
         */
        public FirefoxDriverExtended(Capabilities desiredCapabilities) {
            super(desiredCapabilities);
            sizzleSelector = new SizzleSelector(this);
        }

        @Override
        public WebElement findElementByCssSelector(String using) {
            return sizzleSelector.findElementBySizzleCss(using);
        }

        @Override
        public List<WebElement> findElementsByCssSelector(String using) {
            return sizzleSelector.findElementsBySizzleCss(using);
        }
    }

    public static class ChromeDriverExtended extends ChromeDriver {

        /**
         * Driver constructor
         *
         * @param desiredCapabilities to be passed into the standard ChromeDriver
         */
        public ChromeDriverExtended(Capabilities desiredCapabilities) {
            super(desiredCapabilities);
            sizzleSelector = new SizzleSelector(this);
        }

        @Override
        public WebElement findElementByCssSelector(String using) {
            return sizzleSelector.findElementBySizzleCss(using);
        }

        @Override
        public List<WebElement> findElementsByCssSelector(String using) {
            return sizzleSelector.findElementsBySizzleCss(using);
        }
    }

    public static class InternetExplorerDriverExtended extends InternetExplorerDriver {
        /**
         * Driver constructor
         *
         * @param desiredCapabilities to be passed into the standard InternetExplorerDriver
         */
        public InternetExplorerDriverExtended (Capabilities desiredCapabilities) {
            super(desiredCapabilities);
            sizzleSelector = new SizzleSelector(this);
        }

        @Override
        public WebElement findElementByCssSelector(String using) {
            return sizzleSelector.findElementBySizzleCss(using);
        }

        @Override
        public List<WebElement> findElementsByCssSelector(String using) {
            return sizzleSelector.findElementsBySizzleCss(using);
        }
    }

    public static class RemoteWebDriverExtended extends RemoteWebDriver {

        /**
         * Driver constructor
         *
         * @param desiredCapabilities to be passed into the standard FirefoxDriver
         */
        public RemoteWebDriverExtended (URL seleniumGridURL, Capabilities desiredCapabilities){
            super(seleniumGridURL, desiredCapabilities);
            sizzleSelector = new SizzleSelector(this);
        }

        @Override
        public WebElement findElementByCssSelector(String using) {
            return sizzleSelector.findElementBySizzleCss(using);
        }

        @Override
        public List<WebElement> findElementsByCssSelector(String using) {
            return sizzleSelector.findElementsBySizzleCss(using);
        }
    }
}
