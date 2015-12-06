package com.techboy.selenium.config;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import static org.openqa.selenium.remote.CapabilityType.PROXY;

/**
 * Created by christopher on 01/12/2015.
 */


public class BrowserCapabilities implements BrowserService<DesiredCapabilities>{

    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);


    public DesiredCapabilities getFirefoxCapabilities() throws IOException {
        String firebugPath = "selenium_browser_drivers/firebug-2.0.11-fx.xpi";
        String firePathPath = "selenium_browser_drivers/firepath-0.9.7.1-fx.xpi";
        String netExportPath = "selenium_browser_drivers/netExport-0.9b6.xpi";
        FirefoxProfile firefoxProfile=new FirefoxProfile();
        firefoxProfile.addExtension(new File(firebugPath));
        firefoxProfile.addExtension(new File(firePathPath));
        firefoxProfile.addExtension(new File(netExportPath));
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("extensions.firebug.currentVersion", "2.0.11");
        firefoxProfile.setPreference("extensions.firebug.onByDefault", true);
        firefoxProfile.setPreference("extensions.firebug.previousPlacement", 2);
        firefoxProfile.setPreference("extensions.firebug.netFilterCategories", "xhr");
        firefoxProfile.setPreference("extensions.firebug.defaultPanelName", "net");
        firefoxProfile.setPreference("extensions.firebug.net.enableSites", true);
        firefoxProfile.setPreference("extensions.firebug.net.persistent", true);
        firefoxProfile.setPreference("extensions.firebug.netexport.autoExportToServer", false);
        firefoxProfile.setPreference("extensions.firebug.netexport.defaultLogDir", System.getProperty("user.dir"));
        firefoxProfile.setPreference("extensions.firebug.netexport.showPreview", false);
        firefoxProfile.setPreference("extensions.firebug.netexport.sendToConfirmation", false);
        firefoxProfile.setPreference("extensions.firebug.netexport.pageLoadedTimeout", 360000);
        firefoxProfile.setPreference("extensions.firebug.netexport.timeout", 360000);
        firefoxProfile.setPreference("extensions.firebug.netexport.Automation", true);
        firefoxProfile.setPreference("extensions.firebug.netexport.compress", false);
        firefoxProfile.setPreference("extensions.firebug.netexport.includeResponseBodies", true);
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(FirefoxDriver.PROFILE,firefoxProfile);
        return addProxySettings(capabilities, proxy());
    }


    public DesiredCapabilities getChromeCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
        HashMap<String, String> chromePreferences = new HashMap<String, String>();
        chromePreferences.put("profile.password_manager_enabled", "false");
        capabilities.setCapability("chrome.prefs", chromePreferences);
        System.out.println(proxy());
        return addProxySettings(capabilities, proxy());
    }

    public DesiredCapabilities getIECapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
        capabilities.setCapability("requireWindowFocus", true);
        return addProxySettings(capabilities, proxy());
    }

    protected DesiredCapabilities addProxySettings(DesiredCapabilities capabilities, Proxy proxy) {
        if (null != proxy) {
            capabilities.setCapability(PROXY, proxy);
        }

        return capabilities;
    }

    public Proxy proxy() {
       Proxy proxy;
            if (proxyEnabled) {
                proxy = new Proxy();
                proxy.setProxyType(MANUAL);
                proxy.setHttpProxy(proxyDetails);
                proxy.setSslProxy(proxyDetails);
            }else{
                proxy = new Proxy();
                proxy.setProxyType(Proxy.ProxyType.SYSTEM);
                proxy.isAutodetect();
            }
        return proxy;
    }

    public static BrowserCapabilities newInstance(){
        return new BrowserCapabilities();
    }


}
