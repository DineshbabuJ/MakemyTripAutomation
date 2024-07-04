package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.FilterPage;
import pages.SearchLocationPage;

import java.util.concurrent.TimeUnit;

public class MyStepdefs {
    WebDriver driver=null;
    SearchLocationPage locationPage;
    FilterPage filterPage;

    @Given("the user is on the MakeMyTrip Buses booking page")
    public void theUserIsOnTheMakeMyTripBusesBookingPage() {
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://www.makemytrip.com/");
        driver.manage().window().maximize();
        driver.navigate().to("https://www.makemytrip.com/bus-tickets/");
    }

    @When("^user enters a valid city name (.*) and select from the dropdown suggestions$")
    public void userClicksOnTheFromFieldAndEntersAValidCityNameAndSelectFromTheDropdownSuggestions(String fromCity) throws InterruptedException {
        locationPage= new SearchLocationPage(driver);
        locationPage.enterFromLocation(fromCity);
    }

    @Then("^user able to see the from field populated with input (.*)$")
    public void userAbleToSeeTheFromFieldPopulatedWithInput(String fromLocation) {
        String from=driver.findElement(By.xpath("//input[@id='fromCity']")).getAttribute("value");
        Assert.assertTrue("From field does not reflect the input",from.contains(fromLocation));
    }

    @When("^user enters a valid destination city name (.*) and select from the dropdown suggestions$")
    public void userEntersAValidDestinationCityNameAndSelectFromTheDropdownSuggestions(String toCity) throws InterruptedException {
        locationPage.enterToLocation(toCity);
    }

    @Then("^user able to see the 'To' field populated with input (.*)$")
    public void userAbleToSeeTheToFieldPopulatedWithInput(String toLocation) {
        String from=driver.findElement(By.xpath("//input[@id='toCity']")).getAttribute("value");
        Assert.assertTrue("To field does not reflect the input",from.contains(toLocation));
    }

    @When("the user selects a valid travel date")
    public void theUserSelectsAValidTravelDate() throws InterruptedException {
        locationPage.enterDate();
    }

    @Then("user able to see the 'date' field filled by entered date")
    public void userAbleToSeeTheDateFieldFilledByEnteredDate() {
        String date=driver.findElement(By.xpath("//input[@id='travelDate']/following-sibling::p[1]")).getText();
        Assert.assertTrue("date doesnot match",date.equals("20 Jul24"));
    }

    @When("user clicks the search button")
    public void userClicksTheSearchButton() throws InterruptedException {
        locationPage.clickSearch();
    }

    @Then("the user should be navigated to the search results page")
    public void theUserShouldBeNavigatedToTheSearchResultsPage() {
        Assert.assertTrue("does not navigated to filter page",driver.getCurrentUrl().contains("Chennai"));
        driver.quit();
    }

    @Given("the user has searched for buses from {string} to {string}")
    public void theUserHasSearchedForBusesFromTo(String from, String to) throws InterruptedException {
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.get("https://www.makemytrip.com/");
        driver.manage().window().maximize();
        driver.navigate().to("https://www.makemytrip.com/bus-tickets/");
        locationPage= new SearchLocationPage(driver);
        locationPage.enterFromLocation(from);
        locationPage.enterToLocation(to);
        locationPage.enterDate();
        locationPage.clickSearch();
    }

    @When("the user applies a filter for  buses")
    public void theUserAppliesAFilterForBuses() throws InterruptedException {
        filterPage=new FilterPage(driver);
        filterPage.applyFilter();


    }
    @And("user sorts the search based on price")
    public void userSortsTheSearchBasedOnPrice() throws InterruptedException {
        filterPage.applySort();
    }

    @And("user selects the bus and select the seats")
    public void userSelectsTheBusAndSelectTheSeats() throws InterruptedException {
        filterPage.selectSeat(5);
    }

    @And("clicks the continue button")
    public void clicksTheContinueButton() {
        filterPage.clickContinue();
    }

    @Then("user navigated to complete your Booking page")
    public void userNavigatedToCompleteYourBookingPage() {
        Assert.assertTrue("not yet navigated to complete booking page",driver.findElement(By.xpath("//h1[text()='Complete your booking']")).getText().equals("Complete your booking"));
    }
}
