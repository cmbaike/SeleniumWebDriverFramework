package com.techboy.selenium.config;

import com.techboy.selenium.browserdriver.BrowserDriverExtended;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by christopher on 01/12/2015.
 */


public class BrowserThread {

    public BrowserThread(){}


    public WebDriver driver;
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final String browser = "chrome";


    @Autowired
    private Proxy proxy;



    public WebDriver Driver(){

        switch(browser){
            case "FIREFOX":
            case "":
                driver= new BrowserDriverExtended.FirefoxDriverExtended(BrowserCapabilities.newInstance().getFirefoxCapabilities(proxy));
        }
        return driver;
    }

public static BrowserThread browserThread(){
    return new BrowserThread();
}

}
