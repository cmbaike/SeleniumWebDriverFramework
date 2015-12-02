package test;

import com.techboy.selenium.beanconfig.Beans;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by christopher on 01/12/2015.
 */

@ComponentScan(basePackageClasses = Beans.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Beans.class)
public class TestGoogle {




    @Test
    public void lunchBrowser(){

    }
}
