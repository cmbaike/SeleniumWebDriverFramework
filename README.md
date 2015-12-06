#SeleniumWebDriverFramework

A maven Selenium framework that is built which allows Spring to manage dependency via dependency injection.

1. Open a terminal window/command prompt
2. Clone this project.
3. CD into project directory
4. mvn clean verify

_All dependencies will be downloaded and specific driver executable dependencies are already installed in project directory

##What I Should do to run my test

_To run any unit tests that test your Selenium framework you just need to ensure that all unit test class contains Spring test annotation

<d1>
<dd>@SuppressWarnings("SpringJavaAutowiringInspection")</dd>
<dd>@RunWith(SpringJUnit4ClassRunner.class)</dd>
<dd>@ContextConfiguration(classes=BeanConfig.class)</dd>
<dd>public class TestGoogle {</dd>
<dd> ....<dd>
<dd>}<dd>
  <d1> 

- You don't need to do this:
 
<d1>
<dt>WebDriver driver=new WebDriver()</dt>
</d1>
   
- To create a WebDriver instance :

<d1>
<dd>@Autowired</dd>
<dd>private WebDriver driver</dd>
</d1>

- A webDriver object will be wired automatically
  
Note: You can name your test class whatever you want.

##Further Information

- -Dbrowser=firefox
- -Dbrowser=chrome
- -Dbrowser=ie

-You don't need to download the IEDriverServer, or chromedriver binaries, they are already added to the project directory

-You can specify a grid to connect to where you can choose your browser, browser version and platform

- -Dremote=true 
- -DseleniumGridURL=http://{username}:{accessKey}@ondemand.saucelabs.com:80/wd/hub 
- -Dplatform=xp 
- -Dbrowser=firefox 
- -DbrowserVersion=42

-You can also specify a proxy to use

- -DproxyEnabled=true
- -DproxyHost=localhost
- -DproxyPort=8080

_If the tests fail screenshots will be saved in ${project.basedir}/target/screenshots_