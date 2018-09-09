package myprojects.automation.assignment4.utils.logging;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.util.logging.Logger;

public class WebDriverLogger extends AbstractWebDriverEventListener {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(WebDriverLogger.class));

        @Override
        public void afterNavigateTo(String url, WebDriver driver) {
            LOGGER.info("WebDriver navigated to '" + url + "'");
        }

        @Override
        public void beforeClickOn(WebElement element, WebDriver driver) {
            LOGGER.info("WebDriver click on element - "
                    + elementDescription(element));
        }

        @Override
         public void afterClickOn(WebElement element, WebDriver driver) {
            LOGGER.info("Element is clicked");
    }

        private String elementDescription(WebElement element) {
            String description = "tag:" + element.getTagName();
            if (element.getAttribute("id") != null) {
                description += " id: " + element.getAttribute("id");
            }
            else if (element.getAttribute("name") != null) {
                description += " name: " + element.getAttribute("name");
            }

            description += " ('" + element.getText() + "')";

            return description;
        }
}
