package com.techboy.selenium.browserdriver;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.util.List;

/**
 * Sizzle implementation of CSS selectors. As of the time of this writing the Selenium API does not support sending Sizzle
 * commands over the wire (or we could not figure out how). As a result we execute Sizzle injection and selectors as
 * raw javascript commands.
 */

@SuppressWarnings("unchecked")
public class SizzleSelector {

    /**
     * The driver to execute the Javascript commands
     */
    private JavascriptExecutor driver;

    /**
     * Build the Sizzle Selector
     *
     * @param webDriver to be used to execute Javascript
     */
    public SizzleSelector(WebDriver webDriver) {
        driver = (JavascriptExecutor) webDriver;
    }

    public WebElement findElementBySizzleCss(String using) {
        injectSizzleIfNeeded();
        String javascriptExpression = createSizzleSelectorExpression(using);
        List<WebElement> elements = (List<WebElement>) driver.executeScript(javascriptExpression);
        if (elements != null && elements.size() > 0) {
            return elements.get(0);
        }
        throw new NoSuchElementException("sizzle could not find CSS element: " + using);
    }

    public List<WebElement> findElementsBySizzleCss(String using) {
        injectSizzleIfNeeded();
        String javascriptExpression = createSizzleSelectorExpression(using);
        List<WebElement> webElements = (List<WebElement>) driver.executeScript(javascriptExpression);
        if (webElements != null) {
            return webElements;
        }
        throw new NoSuchElementException("sizzle could not find CSS element: " + using);
    }

    private String createSizzleSelectorExpression(String using) {
        return "return Sizzle(\"" + using + "\")";
    }

    private void injectSizzleIfNeeded() {
        if (!isSizzleLoaded()) {
            injectSizzle();
        }
    }

    private Boolean isSizzleLoaded() {
        Boolean loaded;
        try {
            loaded = (Boolean) driver.executeScript("return Sizzle()!=null");
        } catch (WebDriverException e) {
            loaded = false;
        }
        return loaded;
    }

    private void injectSizzle() {
        try {
            driver.executeScript(" var headID = document.getElementsByTagName(\"head\")[0];"
                    + "var newScript = document.createElement('script');"
                    + "newScript.type = 'text/javascript';"
                    + "newScript.innerText = " + getAmdHackSizzle()
                    + "headID.appendChild(newScript);");
        }
        catch (WebDriverException wdx) {
            // We are not sure why but whilst Sizzle is loaded into the web page, a Javascript exception is thrown
            // and propagates here as a WebDriverException (on IE8).
        }
    }

    /**
     * Gets Sizzle.js with a small hack to allow use with our Module loader
     *
     * @return sizzle.js code
     */
    private String getAmdHackSizzle() {
        try {
            return readClasspathFile("sizzle.js");
        }
        catch (IOException x) {
            throw new RuntimeException("Unable to load Sizzle.js from classpath (non recoverable so propagating as RuntimeException", x);
        }
    }

    private String readClasspathFile(String classpathFilename) throws IOException {
        return new String(getResourceContents(classpathFilename));
    }

    private byte[] getResourceContents(String resourceKey) throws IOException {
        // Ensure we have a valid resource loader, if none is set
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        return IOUtils.toByteArray(resourceLoader.getResource(resourceKey).getInputStream());
    }

}


