package com.techboy.selenium.beanconfig;

import com.techboy.selenium.browserdriver.BrowserDriverExtended;
import com.techboy.selenium.config.BrowserCapabilities;
import org.openqa.selenium.Proxy;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

/**
 * Created by christopher on 01/12/2015.
 */
@PropertySource("classpath:app.properties")
@Configuration
public class Beans  {

    @Inject
    Environment env;

    private String workingOS = System.getProperty("os.name").toLowerCase();
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);
    private static String browser="firefox";


    @PostConstruct
    public void systemPath() throws IOException {
        if (workingOS.contains("windows")) {
            System.setProperty("webdriver.chrome.driver","selenium_browser_drivers/windowsChromedriver/chromedriver.exe");
        } else if (workingOS.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/macChromedriver/chromedriver");
        } else if (workingOS.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/linuxChromedriver/chromedriver");
        }

    }

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

    @Bean(destroyMethod="quit")
    @Conditional(Beans.FirefoxCondition.class)
    public BrowserDriverExtended.FirefoxDriverExtended firefox() {
        return new BrowserDriverExtended.FirefoxDriverExtended(BrowserCapabilities.newInstance().getFirefoxCapabilities());
    }


    @Bean(destroyMethod="quit")
    @Conditional(Beans.ChromeCondition.class)
    public BrowserDriverExtended.ChromeDriverExtended chrome() {
        return new BrowserDriverExtended.ChromeDriverExtended(BrowserCapabilities.newInstance().getChromeCapabilities());
    }

    private static class FirefoxCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return browser.contentEquals("firefox") || browser.contentEquals("");
        }

    }

    private static class ChromeCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return browser.contentEquals("chrome");
        }

    }

    private static class IECondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return browser.contentEquals("IE");
        }
    }





}






