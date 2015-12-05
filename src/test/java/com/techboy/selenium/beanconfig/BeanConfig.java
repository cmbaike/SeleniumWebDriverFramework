package com.techboy.selenium.beanconfig;

import com.techboy.selenium.browserdriver.BrowserDriverExtended;
import com.techboy.selenium.config.BrowserCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private Environment environment;

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
    @Conditional(BeanConfig.DefaultFirefoxCondition.class)
    public DesiredCapabilities firefoxDesiredCapabilities(){
        return BrowserCapabilities.newInstance().getFirefoxCapabilities();
    }

    @Bean
    @Conditional(BeanConfig.ChromeCondition.class)
    public DesiredCapabilities chromeDesiredCapabilities(){
        return BrowserCapabilities.newInstance().getChromeCapabilities();
    }

    @Bean
   @Conditional(BeanConfig.IECondition.class)
    public DesiredCapabilities ieDesiredCapabilities(){
        return BrowserCapabilities.newInstance().getIECapabilities();
    }

    /**
     * @link Condition for creating firefox browser bean as default
     */
    private static class DefaultFirefoxCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            List<Boolean>firefoxSelector=new ArrayList<>();
            firefoxSelector.add(env.getProperty("browser", "firefox").equalsIgnoreCase("firefox")||env.getProperty("browser").isEmpty());
            firefoxSelector.add(env.getProperty("remote", "false").equalsIgnoreCase("false")||env.getProperty("remote").isEmpty());
            return firefoxSelector.get(0)&&firefoxSelector.get(1);
        }
    }

    /**
     * @link Condition for creating chrome browser bean
     */
    private static class ChromeCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            List<Boolean>chromeSelector=new ArrayList<>();
            chromeSelector.add(env.getProperty("browser", "firefox").equalsIgnoreCase("chrome"));
            chromeSelector.add(env.getProperty("remote", "false").equalsIgnoreCase("false")||env.getProperty("remote").isEmpty());
            return chromeSelector.get(0)&&chromeSelector.get(1);
        }

    }

    /**
     * @link Condition for creating IE browser bean
     */

    private static class IECondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            List<Boolean>ieSelector=new ArrayList<>();
            ieSelector.add(env.getProperty("browser", "firefox").equalsIgnoreCase("IE"));
            ieSelector.add(env.getProperty("remote", "false").equalsIgnoreCase("false")||env.getProperty("remote").isEmpty());
            return ieSelector.get(0)&&ieSelector.get(1);
        }
    }



}






