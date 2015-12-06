package com.techboy.selenium.beanconfig;

import com.google.common.annotations.VisibleForTesting;
import com.techboy.selenium.browserdriver.BrowserDriverExtended;
import com.techboy.selenium.config.BrowserCapabilities;
import com.techboy.selenium.listeners.ScreenshotTestRule;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

/**
 * {@link} BeanConfig config class for all bean creation
 */

@PropertySource("classpath:app.properties")
@Configuration
public class BeanConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private URL seleniumGridURL;

    protected static final Logger LOG = LoggerFactory.getLogger(BeanConfig.class);
    private String workingOS = System.getProperty("os.name").toLowerCase();
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");

    /**
     * @link Initialize system path variables for browsers
     */
    @PostConstruct
    public void systemPath() throws IOException {

        LOG.info(" ");
        LOG.info("Current Operating System: " + operatingSystem);
        LOG.info("Current Architecture: " + systemArchitecture);
        LOG.info("Current Browser Selection: " + environment.getProperty("browser","firefox"));
        LOG.info("Use RemoteWebDriver: " + environment.getProperty("remote", "false"));
        LOG.info(" ");

        if (workingOS.contains("windows")) {
            System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/windowsChromedriver/chromedriver.exe");
            System.setProperty("webdriver.ie.driver", "selenium_browser_drivers\\IEDriverServer.exe");
        } else if (workingOS.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/macChromedriver/chromedriver");
        } else if (workingOS.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/linuxChromedriver/chromedriver");
        }
    }


    /**
     * @link internetExplorer bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.IECondition.class)
    @Autowired
    public BrowserDriverExtended.InternetExplorerDriverExtended internetExplorer(DesiredCapabilities capabilities) {
        return new BrowserDriverExtended.InternetExplorerDriverExtended(capabilities);
    }

    /**
     * @link firefox bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.DefaultFirefoxCondition.class)
    @Autowired
    public BrowserDriverExtended.FirefoxDriverExtended firefox(DesiredCapabilities capabilities) {
        return new BrowserDriverExtended.FirefoxDriverExtended(capabilities);
    }

    /**
     * @link Chrome bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.ChromeCondition.class)
    @Autowired
    public BrowserDriverExtended.ChromeDriverExtended chrome(DesiredCapabilities capabilities) {
        return new BrowserDriverExtended.ChromeDriverExtended(capabilities);
    }

   @Bean
    public URL seleniumGridURL() throws MalformedURLException {
       return new URL(environment.getProperty("gridURL"));
    }

    @Bean
    public ScreenshotTestRule screenshotTestRule(){
        return new ScreenshotTestRule();
    }

    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.RemoteWebDriverCondition.class)
    @Autowired
    public BrowserDriverExtended.RemoteWebDriverExtended remote(DesiredCapabilities capabilities) throws MalformedURLException {
        String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
        String desiredPlatform = System.getProperty("desiredPlatform");
        if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
            capabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
        }
        if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
            capabilities.setVersion(desiredBrowserVersion);
        }
        return new BrowserDriverExtended.RemoteWebDriverExtended(seleniumGridURL, capabilities);
    }

    @Bean
    @Conditional(BeanConfig.FirefoxCapabilityCondition.class)
    public FirefoxProfile firefoxProfile(){
        return new FirefoxProfile();
    }


    @Bean
    @Conditional(BeanConfig.FirefoxCapabilityCondition.class)
    public DesiredCapabilities firefoxDesiredCapabilities() throws IOException {
        return BrowserCapabilities.newInstance().getFirefoxCapabilities();
    }

    @Bean
    @Conditional(BeanConfig.ChromeCapabilityCondition.class)
    public DesiredCapabilities chromeDesiredCapabilities(){
        return BrowserCapabilities.newInstance().getChromeCapabilities();
    }

    @Bean
    @Conditional(BeanConfig.IECapabilityCondition.class)
    public DesiredCapabilities ieDesiredCapabilities(){
        return BrowserCapabilities.newInstance().getIECapabilities();
    }

    private static class FirefoxCapabilityCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            return browser.equalsIgnoreCase("firefox")||browser.isEmpty();
        }
    }

    private static class ChromeCapabilityCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            return browser.equalsIgnoreCase("chrome");
        }
    }

    private static class IECapabilityCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            return browser.equalsIgnoreCase("IE");
        }
    }

    /**
     * @link Condition for creating firefox browser bean as default
     */
    private static class DefaultFirefoxCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            List<Boolean>firefoxSelector=new ArrayList<>();
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            String remote=env.getProperty("remote", "false");
            firefoxSelector.add(browser.equalsIgnoreCase("firefox") || browser.isEmpty());
            firefoxSelector.add(remote.equalsIgnoreCase("false") || remote.isEmpty());
            return firefoxSelector.get(0)&&firefoxSelector.get(1);
        }
    }

    /**
     * @link Condition for creating chrome browser bean
     */
    private static class ChromeCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            List<Boolean>chromeSelector=new ArrayList<>();
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            String remote=env.getProperty("remote", "false");
            chromeSelector.add(browser.equalsIgnoreCase("chrome"));
            chromeSelector.add(remote.equalsIgnoreCase("false") || remote.isEmpty());
            return chromeSelector.get(0)&&chromeSelector.get(1);
        }
    }

    /**
     * @link Condition for creating IE browser bean
     */

    private static class IECondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            List<Boolean>ieSelector=new ArrayList<>();
            Environment env=context.getEnvironment();
            String browser=env.getProperty("browser","firefox");
            String remote=env.getProperty("remote", "false");
            ieSelector.add(browser.equalsIgnoreCase("IE"));
            ieSelector.add(remote.equalsIgnoreCase("false") || remote.isEmpty());
            return ieSelector.get(0)&&ieSelector.get(1);
        }
    }

    private static class RemoteWebDriverCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env=context.getEnvironment();
            String remote=env.getProperty("remote", "false");
            return remote.equalsIgnoreCase("true");
        }
    }
}






