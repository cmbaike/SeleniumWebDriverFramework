package com.techboy.selenium.config;

import java.io.IOException;

/**
 * Created by christopher on 01/12/2015.
 */

public interface BrowserService <T> {

    T getFirefoxCapabilities() throws IOException;
    T getChromeCapabilities();
    T getIECapabilities();

}
