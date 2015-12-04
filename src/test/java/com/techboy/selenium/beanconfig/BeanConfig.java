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

    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);
    private String workingOS = System.getProperty("os.name").toLowerCase();

    @Bean
    public
    static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * @link Initialize system path variables for browsers
     */
    @PostConstruct
    public void systemPath() throws IOException {

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
     * @link Proxy bean generator
     */
    @Bean
    public Proxy proxy() {
        Proxy proxy;
        if (proxyEnabled) {
            proxy = new Proxy();
            proxy.setProxyType(MANUAL);
            proxy.setHttpProxy(proxyDetails);
            proxy.setSslProxy(proxyDetails);
        }
        return new Proxy();
    }

    /**
     * @link internetExplorer bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.IECondition.class)
    public BrowserDriverExtended.InternetExplorerDriverExtended internetExplorer() {
        return new BrowserDriverExtended.InternetExplorerDriverExtended(BrowserCapabilities.newInstance().getIECapabilities());
    }

    /**
     * @link firefox bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.DefaultFirefoxCondition.class)
    public BrowserDriverExtended.FirefoxDriverExtended firefox() {
        return new BrowserDriverExtended.FirefoxDriverExtended(BrowserCapabilities.newInstance().getFirefoxCapabilities());
    }

    /**
     * @link Chrome bean generator
     */
    @Bean(destroyMethod = "quit")
    @Conditional(BeanConfig.ChromeCondition.class)
    public BrowserDriverExtended.ChromeDriverExtended chrome() {
        return new BrowserDriverExtended.ChromeDriverExtended(BrowserCapabilities.newInstance().getChromeCapabilities());
    }

    /**
     * @link Condition for creating firefox browser bean as default
     */
    private static class DefaultFirefoxCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            return env.getProperty("browser", "firefox").equalsIgnoreCase("firefox")&&env.getProperty("remote", "false").equalsIgnoreCase("false");

        }
    }

    /**
     * @link Condition for creating chrome browser bean
     */
    private static class ChromeCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            return env.getProperty("browser", "firefox").equalsIgnoreCase("chrome")&&env.getProperty("remote", "false").equalsIgnoreCase("false");
        }

    }

    /**
     * @link Condition for creating IE browser bean
     */

    private static class IECondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            return env.getProperty("browser", "firefox").equalsIgnoreCase("IE")&&env.getProperty("remote", "false").equalsIgnoreCase("false");
        }
    }

    /**
     * @link Condition for creating RemoteWebDriver browser bean
     */
    private static class RemoteWebDriverCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            return env.getProperty("remote", "false").equalsIgnoreCase("true");
        }
    }

}






