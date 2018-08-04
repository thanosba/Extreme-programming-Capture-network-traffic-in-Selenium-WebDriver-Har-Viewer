import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileOutputStream;
import java.io.IOException;

public class CaptureTraffic {
    @Deprecated
    public static void main(String[] args) throws IOException {
        ProxyServer server = new ProxyServer ( 4444 );
        server.start();

        Proxy proxy = new Proxy();
        proxy.setHttpProxy("localhost:" + server.getPort());

        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, proxy);

        // start the browser up
        System.setProperty("webdriver.gecko.driver", "/Users/imac/Desktop/traffichttpapp/src/main/resources/geckodriver");
        WebDriver driver = new FirefoxDriver ( capabilities );
        // create a new HAR with the label "dev2qa.com"
        server.newHar("dev2qa.com");

        // open dev2qa.com
        driver.get("http://www.dev2qa.com/how-to-add-selenium-server-standalone-jar-file-into-your-java-project/");

        // get the HAR data
        Har har = server.getHar();

        try
        {
            FileOutputStream fos = new FileOutputStream("/Users/imac/Desktop/traffichttpapp/dev2qa.har");
            har.writeTo(fos);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        server.stop();
        driver.quit();

    }

}
