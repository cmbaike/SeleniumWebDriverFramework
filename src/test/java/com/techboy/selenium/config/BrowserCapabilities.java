package com.techboy.selenium.config;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

/**
 * Created by christopher on 01/12/2015.
 */


public class BrowserCapabilities extends DesiredCapabilities implements BrowserService{

    private BrowserCapabilities(){}

    @Autowired(required=false)
    public DesiredCapabilities getFirefoxCapabilities(Proxy proxy) {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        return addProxySettings(capabilities, proxy);
    }

    @Autowired(required=false)
    public DesiredCapabilities getChromeCapabilities(Proxy proxy) {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
        HashMap<String, String> chromePreferences = new HashMap<String, String>();
        chromePreferences.put("profile.password_manager_enabled", "false");
        capabilities.setCapability("chrome.prefs", chromePreferences);
        return addProxySettings(capabilities, proxy);
    }

    @Autowired(required=false)
    public DesiredCapabilities getIECapabilities(Proxy proxy) {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
        capabilities.setCapability("requireWindowFocus", true);
        return addProxySettings(capabilities, proxy);
    }

    protected DesiredCapabilities addProxySettings(DesiredCapabilities capabilities, Proxy proxy) {
        if (null != proxy) {
            capabilities.setCapability(PROXY, proxy);
        }

        return capabilities;
    }

    public static BrowserCapabilities newInstance(){
        return new BrowserCapabilities();
    }


}
