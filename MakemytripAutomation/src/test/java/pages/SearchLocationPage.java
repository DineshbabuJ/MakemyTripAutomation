package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class SearchLocationPage {
    protected WebDriver driver;
    @FindBy(xpath = "//input[@id='fromCity']")
    WebElement fromCityBox;

    @FindBy(xpath = "//input[@title='From']")
    WebElement fromCityInput;

    @FindBy(css = "p[class='searchedResult font14 darkText']")
    List<WebElement> suggestions;

    @FindBy(xpath = "//input[@id='toCity']")
    WebElement toCityBox;

    @FindBy(xpath = "//input[@title='To']")
    WebElement toCityBoxInput;

    @FindBy(xpath = "//div[@aria-label='Sat Jul 20 2024']")
    WebElement dateChoose;

    @FindBy(xpath = "//button[@id='search_button']")
    WebElement searchBtn;

    public SearchLocationPage(WebDriver driver){
        this.driver=driver;
        AjaxElementLocatorFactory factory= new AjaxElementLocatorFactory(driver,30);
        PageFactory.initElements(factory,this);
    }

    public void enterFromLocation(String fromCity) throws InterruptedException {
        fromCityBox.click();
        fromCityInput.sendKeys(fromCity);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> dynamicList=wait.until(driver -> {
            List<WebElement> elements = suggestions;
            for (WebElement element : elements) {
                if (element.getText().contains(fromCity)) {
                    return elements;
                }
            }
            return null;
        });

        for(int i=0;i<dynamicList.size();i++){
            String text=dynamicList.get(i).getText();
            if (text.contains(fromCity)) {
                dynamicList.get(i).click();
                break;
            }
        }
        suggestions.clear();
    }

    public void enterToLocation(String toCity) throws InterruptedException {
//        driver.findElement(toCityBox).click();
        toCityBoxInput.sendKeys(toCity);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        List<WebElement> dynamicList=wait.until(driver -> {
            List<WebElement> elements = suggestions;
            for (WebElement element : elements) {
                if (element.getText().contains(toCity)) {
                    return elements;
                }
            }
            return null;
        });
        for(int i=0;i<dynamicList.size();i++){
            String text=dynamicList.get(i).getText();
            if (text.contains(toCity)) {
                dynamicList.get(i).click();
                break;
            }
        }
    }
    public void enterDate() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(dateChoose));
        dateChoose.click();

    }

    public void clickSearch() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
        searchBtn.click();
    }

}
