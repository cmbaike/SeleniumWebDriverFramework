package com.techboy.selenium.config;

import org.openqa.selenium.WebDriver;

/**
 * Created by christopher on 01/12/2015.
 */


public class BrowserThread {

    public BrowserThread(){}


    public WebDriver driver;
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final String browser = "chrome";







public static BrowserThread browserThread(){
    return new BrowserThread();
}

}
