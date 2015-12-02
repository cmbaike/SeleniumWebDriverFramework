package com.techboy.selenium.beanconfig;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by christopher on 01/12/15.
 */
public class BeanCondition  {

    public static class FirefoxCondition implements Condition{
        private final String browser="";
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return  browser.contentEquals("firefox")||browser.contentEquals("");
        }

    }
    public static class ChromeCondition implements Condition{
        private final String browser = "chrome";

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return browser.contentEquals("chrome");
        }

    }

    public static class IECondition implements Condition{
        private final String browser = "IE";

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return browser.contentEquals("IE");
        }

    }




}
