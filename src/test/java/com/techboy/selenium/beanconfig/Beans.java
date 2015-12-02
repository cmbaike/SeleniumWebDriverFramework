package com.techboy.selenium.beanconfig;

import com.techboy.selenium.browserdriver.BrowserDriverExtended;
import com.techboy.selenium.config.BrowserCapabilities;
import org.openqa.selenium.Proxy;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.PostConstruct;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

/**
 * Created by christopher on 01/12/2015.
 */

@ComponentScan(basePackageClasses = {Beans.class})
@Configuration
public class Beans {

    String workingDir = System.getProperty("user.dir");
    private String workingOS = System.getProperty("os.name").toLowerCase();
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);
    private static String browser = System.getProperty("browser", "chrome");


    @Bean
    public Proxy proxy() {
        Proxy proxy = null;
        if (proxyEnabled) {
            proxy = new Proxy();
            proxy.setProxyType(MANUAL);
            proxy.setHttpProxy(proxyDetails);
            proxy.setSslProxy(proxyDetails);
        }
        return new Proxy();
    }

    @Bean
    @Conditional(FirefoxCondition.class)
    public BrowserDriverExtended.FirefoxDriverExtended firefox() {
        return new BrowserDriverExtended.FirefoxDriverExtended(BrowserCapabilities.newInstance().getFirefoxCapabilities());
    }


    @Bean
    @Conditional(ChromeCondition.class)
    public BrowserDriverExtended.ChromeDriverExtended chrome() {
        return new BrowserDriverExtended.ChromeDriverExtended(BrowserCapabilities.newInstance().getChromeCapabilities());
    }

    @PostConstruct
    public void systemPath(){
        if (workingOS.contains("windows")) {
              System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/windowsChromedriver/chromedriver.exe");
        } else if (workingOS.contains("mac")) {
             System.setProperty("webdriver.chrome.driver", "/Users/christopher/IdeaProjects/BDDWebDriverFramework/src/test/resources/selenium_browser_drivers/macChromedriver/chromedriver");
        } else if (workingOS.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/linuxChromedriver/chromedriver");
        }

    }

    public static class FirefoxCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return browser.contentEquals("firefox") || browser.contentEquals("");
        }

    }

    public static class ChromeCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return browser.contentEquals("chrome");
        }

    }

    public static class IECondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return browser.contentEquals("IE");
        }

    }


}
