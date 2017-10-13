package com.editordatabase.step_definitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.editordatabase.pages.HomePage;
import com.editordatabase.utilities.ConfigurationReader;
import com.editordatabase.utilities.Driver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DataValidation {
	private WebDriver driver;
	HomePage homePage = new HomePage();

	@Given("^Navigate to https://editor\\.datatables\\.net/examples/simple/simple\\.html$")
	public void navigate_to_https_editor_datatables_net_examples_simple_simple_html() throws Throwable {
		driver = Driver.getInstance();
		driver.get(ConfigurationReader.getProperty("url"));

	}

	@When("^Wait for the webtable to be visible$")
	public void wait_for_the_webtable_to_be_visible() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(homePage.example));

	}

	@When("^Assert that headers size is (\\d+)$")
	public void assert_that_headers_size_is(int arg1) throws Throwable {
		Assert.assertEquals(6, homePage.headers.size());

	}

	@When("^Assert headers are following$")
	public void assert_headers_are_following() throws Throwable {

		Map<Integer, String> headerValues = new HashMap<>();
		headerValues.put(1, "Name");
		headerValues.put(2, "Position");
		headerValues.put(3, "Office");
		headerValues.put(4, "Extn.");
		headerValues.put(5, "Start date");
		headerValues.put(6, "Salary");

		for (int i = 0; i < homePage.headers.size(); i++) {

			Assert.assertEquals(headerValues.get(i+1), homePage.headers.get(i).getText());
			
			System.out.println(homePage.headers.get(i).getText());
		}
	}

	@When("^Find Bruno Nash in the table then verify that he is a Software Engineer and works in London$")
	public void find_Bruno_Nash_in_the_table_then_verify_that_he_is_a_Software_Engineer_and_works_in_London()
			throws Throwable {
		List<WebElement> rowNames = driver.findElements(By.xpath("//table[@id='example']/tbody/tr/td[1]"));

		List<String> realName = new ArrayList<>();
		for (WebElement names : rowNames) {
			realName.add(names.getText());

		}
		Assert.assertTrue(realName.contains("Bruno Nash"));

		WebElement occupation = driver
				.findElement(By.xpath("//table[@id='example']/tbody/tr/td[.='Bruno Nash']/../td[2]"));
		Assert.assertEquals("Software Engineer", occupation.getText());

		WebElement place = driver.findElement(By.xpath("//table[@id='example']/tbody/tr/td[.='Bruno Nash']/../td[3]"));
		Assert.assertEquals("London", place.getText());
	}

	@When("^Print all webtable content in a similar looking format$")
	public void print_all_webtable_content_in_a_similar_looking_format() throws Throwable {

		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='example']/tbody/tr"));

		for (int i = 1; i < rows.size(); i++) {

			for (int j = 1; j < homePage.headers.size(); j++) {

				System.out.print(
						homePage.example.findElement(By.xpath("//tr[" + i + "]/td[" + j + "]")).getText() + " | ");
			}
			System.out.println();
		}

	}

	@When("^Click on NEW button$")
	public void click_on_NEW_button() throws Throwable {

		homePage.newbtn.click();

	}

	@When("^Add new Employee information$")
	public void add_new_Employee_information() throws Throwable {
		homePage.firstName.sendKeys("Elizabeth");
		homePage.lastName.sendKeys("Carry");
		homePage.position.sendKeys("Artist");
		homePage.office.sendKeys("Holywood");
		homePage.extention.sendKeys("1616");
		homePage.startDate.sendKeys("2017-05-12" + Keys.ENTER);
		homePage.salary.sendKeys("167.899");
		homePage.createbtn.click();

	}

	@When("^Search for the employee$")
	public void search_for_the_employee() throws Throwable {
		homePage.search.sendKeys("Bruno Nash" + Keys.ENTER);
	}

	@Then("^Verify All data displayed in the webtable matched the data you entered in step (\\d+)$")
	public void verify_All_data_displayed_in_the_webtable_matched_the_data_you_entered_in_step(int arg1)
			throws Throwable {
		
		Assert.assertEquals("Bruno Nash", driver.findElement(By.cssSelector(".sorting_1")).getText());
		Assert.assertEquals("Software Engineer", driver.findElement(By.cssSelector("td.sorting_1+td")).getText());
		Assert.assertEquals("London", driver.findElement(By.cssSelector("td.sorting_1+td+td")).getText());
		Assert.assertEquals("6222", driver.findElement(By.cssSelector("td.sorting_1+td+td+td")).getText());
		Assert.assertEquals("2011-05-03", driver.findElement(By.cssSelector("td.sorting_1+td+td+td+td")).getText());
		Assert.assertEquals("$163,500", driver.findElement(By.cssSelector("td.sorting_1+td+td+td+td+td")).getText());
		
		System.out.println("=========== 1 ============verify both way ======== 2 =======");
	
		
		List<WebElement> nameValues=driver.findElements(By.cssSelector("#row_3>td"));
		
		List<String> stringNames=new ArrayList<>();
		for(WebElement name :nameValues){
			stringNames.add(name.getText());
			
		}
		
		for (int i = 0; i < nameValues.size(); i++) {
			
			//Assert.assertEquals(ConfigurationReader.getProperty(""+i+1), stringNames.get(i)); //both samething
			Assert.assertEquals(ConfigurationReader.getProperty(String.valueOf(i)), stringNames.get(i));
		}
	
		
	}

}
