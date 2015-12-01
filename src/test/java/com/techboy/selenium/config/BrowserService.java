package com.techboy.selenium.config;

/**
 * Created by christopher on 01/12/2015.
 */

import org.openqa.selenium.Proxy;

public interface BrowserService <T> {

    T getFirefoxCapabilities(Proxy proxy);
    T getChromeCapabilities(Proxy proxy);
    T getIECapabilities(Proxy proxy);

}
