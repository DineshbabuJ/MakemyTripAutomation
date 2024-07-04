package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class FilterPage{
    protected WebDriver driver;

    @FindBy(xpath ="//div[text()='Separate single window seats']" )
    WebElement singleSeat;

    @FindBy(xpath ="//div[@id='busList']/div[1]/div/div[2]/ul/li[1]/div[2]/div/div[1]/span[2]" )
    WebElement acFilter;

    @FindBy(xpath = "//div/ul/li[4][@class='containerHorizontal']/ul/li/div/div/span[2]")
    List<WebElement> pickUpPoints;

    @FindBy(xpath = "//div/ul/li[5][@class='containerHorizontal']/div[2]/div/span[2]")
    List<WebElement> pickUpTimings;

    @FindBy(xpath = "//div[@class='busCardContainer '][1]" )
    WebElement firstResultBus;

    @FindBy(xpath = "//span[@data-testid='seat_horizontal_sleeper_available']")
    List<WebElement> availableSeatLocators;

    @FindBy(xpath = "//div[@class='appendBottom8 paddingL15 makeFlex vrtlCenter']/div[1]/ul/li/span/span[2]")
    List<WebElement> finalPickupPoint;

    @FindBy(xpath = "//span[text()='Continue']")
    WebElement continueBtn;

    public FilterPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void applyFilter() throws InterruptedException {

        acFilter.click();
        singleSeat.click();
        List<WebElement> dynamicList=pickUpPoints;
        String pickupPoint="Gandhipuram";
        for(int i=0;i<dynamicList.size();i++){
            String text=dynamicList.get(i).getText();
            if (text.contains(pickupPoint)) {
                dynamicList.get(i).click();
                break;
            }
        }
        Thread.sleep(2000);
        List<WebElement> dynamicPikUpTime=pickUpTimings;
        String pickUpTime="6 AM to 11 AM";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});", dynamicPikUpTime.get(dynamicPikUpTime.size()-1));
        for(int i=0;i<dynamicPikUpTime.size();i++){
            String text=dynamicPikUpTime.get(i).getText();
            if (text.contains(pickUpTime)) {
                dynamicPikUpTime.get(i).click();
                break;
            }
        }


    }

    public void applySort() throws InterruptedException {
        String sortOption="Cheapest";
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//li[text()='"+sortOption+"']"))).perform();
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});", driver.findElement(By.xpath("//li[text()='"+sortOption+"']")));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//li[text()='"+sortOption+"']"))));
        driver.findElement(By.xpath("//li[text()='"+sortOption+"']")).click();

    }

    public void selectSeat(int seats) throws InterruptedException {
        firstResultBus.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> availableSeats = wait.until(ExpectedConditions.visibilityOfAllElements(availableSeatLocators));

        int i=0;
        for(WebElement element:availableSeats){
            if(i<seats){
                element.click();
                i++;
            }
        }
        String pickPoint="Gandhipuram";
        List<WebElement> pickupPoints=finalPickupPoint;
        for(WebElement element:pickupPoints){
            if(element.getText().contains(pickPoint)){
                element.click();
                break;
            }
        }

    }

    public void clickContinue() {
        continueBtn.click();
    }
}
