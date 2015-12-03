package com.techboy.selenium.beanconfig;

import com.techboy.selenium.browserdriver.BrowserDriverExtended;
import com.techboy.selenium.config.BrowserCapabilities;
import org.openqa.selenium.Proxy;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

/**
 * {@link} BeanConfig config class for all bean creation
 */

@PropertySource("classpath:app.properties")
@Configuration
public class BeanConfig {


    private String workingOS = System.getProperty("os.name").toLowerCase();
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);


    /**
     * @Code Initialize system path variables for browsers
     */
    @PostConstruct
    public void systemPath() throws IOException {
        if (workingOS.contains("windows")) {
            System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/windowsChromedriver/chromedriver.exe");
        } else if (workingOS.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/macChromedriver/chromedriver");
        } else if (workingOS.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "selenium_browser_drivers/linuxChromedriver/chromedriver");
        }

    }


    /**
     * @Code Proxy bean generator
     */
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

    /**
     * @Code internetExplorer bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.IECondition.class)
    public BrowserDriverExtended.InternetExplorerDriverExtended internetExplorer() {
        return new BrowserDriverExtended.InternetExplorerDriverExtended(BrowserCapabilities.newInstance().getIECapabilities());
    }

    /**
     * @Code firefox bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.FirefoxCondition.class)
    public BrowserDriverExtended.FirefoxDriverExtended firefox() {
        return new BrowserDriverExtended.FirefoxDriverExtended(BrowserCapabilities.newInstance().getFirefoxCapabilities());
    }

    /**
     * @Code Chrome bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.ChromeCondition.class)
    public BrowserDriverExtended.ChromeDriverExtended chrome() {
        return new BrowserDriverExtended.ChromeDriverExtended(BrowserCapabilities.newInstance().getChromeCapabilities());
    }

    /**
     * @Code Condition for creating firefox browser bean
     */
    private static class FirefoxCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            return env.getProperty("browser").equalsIgnoreCase("firefox") || env.getProperty("browser").equalsIgnoreCase("");
        }

    }

    /**
     * @Code Condition for creating chrome browser bean
     */
    private static class ChromeCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            return env.getProperty("browser").equalsIgnoreCase("chrome");
        }

    }

    /**
     * @Code Condition for creating IE browser bean
     */

    private static class IECondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            return env.getProperty("browser").equalsIgnoreCase("IE");
        }
    }

    @Bean
    public
    static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}






