package com.techboy.selenium.beanconfig;

import com.techboy.selenium.browserdriver.BrowserDriverExtended;
import com.techboy.selenium.config.BrowserCapabilities;
import com.techboy.selenium.config.BrowserThread;
import org.openqa.selenium.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

/**
 * Created by christopher on 01/12/2015.
 */

@ComponentScan(basePackageClasses = {Beans.class})
@Configuration
public class Beans {

    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);



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
    public BrowserDriverExtended.FirefoxDriverExtended firefox(){
        return new BrowserDriverExtended.FirefoxDriverExtended(BrowserCapabilities.newInstance().getFirefoxCapabilities(proxy()));
    }




  /*  @Bean
    public BrowserDriverExtended.ChromeDriverExtended chrome(){
        return new BrowserDriverExtended.ChromeDriverExtended(BrowserCapabilities.newInstance().getChromeCapabilities(proxy()));
}

    @Bean
    public BrowserDriverExtended.InternetExplorerDriverExtended internetExplorer(){
        return new BrowserDriverExtended.InternetExplorerDriverExtended(BrowserCapabilities.newInstance().getIECapabilities(proxy()));
    }*/


    @Bean(name = "test")
    public BrowserThread browserThread(){
        return new BrowserThread();
    }







}
