package com.ericsson.testng.administrator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.*;

import com.ericsson.connection.testlink.ExecutionResult;
import com.ericsson.connection.testlink.IConstants;
import com.ericsson.util.GeneratePDF;
import com.ericsson.util.ReadPropertyFile;
import com.ericsson.util.SeleniumUtil;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;


@SuppressWarnings("serial")
public class EditCustomerCountry extends TestCase implements IConstants {
  private StringBuffer verificationErrors = new StringBuffer();
  SeleniumUtil selenium = new SeleniumUtil();
  DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
  
  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	 selenium.initializeSelenium();
  }
  @Test
  public void testLoginNG() throws Exception {
	ReadPropertyFile data = new ReadPropertyFile();
	String date =dateFormat.format(new Date());
	selenium.pdf = new GeneratePDF(data.getpathPDFAdmin(), TEST_CASE_7+"_"+ date + ".pdf");
	selenium.pdf.addTitle("Test Suite : Edit Customer Country");	
	
	try{ 
		selenium.driver.get(data.getUrl()+"login.jsf");
		selenium.printScreenshot();
		selenium.driver.findElement(By.id("username")).clear();
		selenium.driver.findElement(By.id("username")).sendKeys(data.getUserIdAdmin());
		selenium.driver.findElement(By.id("password")).clear();
		selenium.driver.findElement(By.id("password")).sendKeys(data.getpasswordAdmin());
		selenium.driver.findElement(By.xpath("//button[contains(.,'Sign In')]")).click();
		selenium.printScreenshot();

	/*    selenium.driver.findElement(By.xpath("//a[@id='mobile-menu-button']")).click();
	    selenium.driver.findElement(By.linkText("Administration")).click();
		selenium.driver.findElement(By.linkText("Customer")).click();
	    selenium.driver.findElement(By.linkText("Country")).click(); */
	    
	    selenium.driver.get(data.getUrl()+"createCountry.jsf");
	    
	    selenium.driver.findElement(By.id("formCountry:countrysTable:countryName:filter")).clear();
	    selenium.driver.findElement(By.id("formCountry:countrysTable:countryName:filter")).sendKeys("AddCountry");
	    selenium.printScreenshot();
	    Thread.sleep(500);
	    selenium.driver.findElement(By.xpath("//button[contains(@title,'Edit')]")).click();
	    selenium.printScreenshot();
	    
	    selenium.driver.findElement(By.id("formCountryRoles:countryName")).clear();
	    selenium.driver.findElement(By.id("formCountryRoles:countryName")).sendKeys("EditCountry");
	    selenium.printScreenshot();
	    selenium.driver.findElement(By.xpath("//button[contains(.,'Save')]")).click();

	    assertEquals("Success to save EditCountry", selenium.driver.findElement(By.xpath("//div[@id='messages_growl_container']/div/div/div[2]/p")).getText());
	    selenium.printScreenshot();
	    selenium.resultPassed(TEST_CASE_7, date,"admin");
		}catch(Error | Exception e){
			selenium.printScreenshot();
			selenium.resultFailed(e.getMessage().toString(),TEST_CASE_7, date,"admin");
			e.printStackTrace();
		}finally{
		ExecutionResult.reportTestCaseResult(PROJECT, TEST_PLAN, TEST_CASE_7, data.getbuild(), selenium.exceptionNote, selenium.result);
	}
}
  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
	selenium.driver.quit();
	selenium.pdf.closePDF();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
