#SeleniumWebDriverFramework

A maven Selenium framework that is built which allows Spring to manage dependency via dependency injection.

1. Open a terminal window/command prompt
2. Clone this project.
3. CD into project directory
4. mvn clean verify

_All dependencies will be downloaded and specific driver executable dependencies are already installed in project directory_

##What I Should do to run my test

_To run any unit tests that test your Selenium framework you just need to ensure that all unit test class contains Spring test annotation_

@SuppressWarnings("SpringJavaAutowiringInspection")

   @RunWith(SpringJUnit4ClassRunner.class)
   
   @ContextConfiguration(classes=BeanConfig.class)
   
   public class TestGoogle {
   
   ....
   
   }
   

_You don't need to do this:_
 
   WebDriver driver=new WebDriver();
   
_To create a WebDriver instance :_ 

@Autowired

  private WebDriver driver

_A webDriver object will be wired automatically_
  
Note: You can name your test class whatever you want.

##Further Information

- -Dbrowser=firefox
- -Dbrowser=chrome
- -Dbrowser=ie

_You don't need to download the IEDriverServer, or chromedriver binaries, they are already added to the project directory._

_You can specify a grid to connect to where you can choose your browser, browser version and platform:_

- -Dremote=true 
- -DseleniumGridURL=http://{username}:{accessKey}@ondemand.saucelabs.com:80/wd/hub 
- -Dplatform=xp 
- -Dbrowser=firefox 
- -DbrowserVersion=33

_You can also specify a proxy to use_

- -DproxyEnabled=true
- -DproxyHost=localhost
- -DproxyPort=8080

_If the tests fail screenshots will be saved in ${project.basedir}/target/screenshots_