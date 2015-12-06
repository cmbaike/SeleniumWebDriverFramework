##SeleniumWebDriverFramework

A maven Selenium framework that is built which allows Spring to manage dependency via dependency injection.

1. Open a terminal window/command prompt
2. Clone this project.
3. CD into project directory
4. mvn clean verify

-All dependencies will be downloaded and specific driver executable dependencies are already installed in project directory

#What I Should do to run my test

-To run any unit tests that test your Selenium framework you just need to ensure that all unit test class contains Spring test annotation.

- -@SuppressWarnings("SpringJavaAutowiringInspection")
   @RunWith(SpringJUnit4ClassRunner.class)
   @ContextConfiguration(classes=BeanConfig.class)
   public class TestGoogle {
   ....
   }

- You don't need to do this:
 
   WebDriver driver=new WebDriver();
   
- To create a WebDriver instance : 
- @Autowired

  private WebDriver driver

-A webDriver object will be wired automatically
  
Note: You can name your test class whatever you want.

#Further Information

- -Dbrowser=firefox
- -Dbrowser=chrome
- -Dbrowser=ie

You don't need to download the IEDriverServer, or chromedriver binaries, they are already added to the project directory.

You can specify a grid to connect to where you can choose your browser, browser version and platform:

- -Dremote=true 
- -DseleniumGridURL=http://{username}:{accessKey}@ondemand.saucelabs.com:80/wd/hub 
- -Dplatform=xp 
- -Dbrowser=firefox 
- -DbrowserVersion=33

You can also specify a proxy to use

- -DproxyEnabled=true
- -DproxyHost=localhost
- -DproxyPort=8080

If the tests fail screenshots will be saved in ${project.basedir}/target/screenshots