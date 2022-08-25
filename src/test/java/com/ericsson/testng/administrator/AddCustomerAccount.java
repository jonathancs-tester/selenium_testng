package com.ericsson.testng.administrator;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.Assert.fail;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import com.ericsson.connection.testlink.ExecutionResult;
import com.ericsson.connection.testlink.IConstants;
import com.ericsson.util.GeneratePDF;
import com.ericsson.util.ReadPropertyFile;
import com.ericsson.util.SeleniumUtil;


@SuppressWarnings("serial")
public class AddCustomerAccount extends TestCase implements IConstants {
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
	selenium.pdf = new GeneratePDF(data.getpathPDFAdmin(), TEST_CASE_4+"_"+ date + ".pdf");
	selenium.pdf.addTitle("Test Suite : Add Customer Account");	
	
	try{ 
		selenium.driver.get(data.getUrl()+"login.jsf");
		selenium.printScreenshot();
		selenium.driver.findElement(By.id("username")).clear();
		selenium.driver.findElement(By.id("username")).sendKeys(data.getUserIdAdmin());
		selenium.driver.findElement(By.id("password")).clear();
		selenium.driver.findElement(By.id("password")).sendKeys(data.getpasswordAdmin());
		selenium.driver.findElement(By.xpath("//button[contains(.,'Sign In')]")).click();
		selenium.printScreenshot();

	/*  selenium.driver.findElement(By.xpath("//a[@id='mobile-menu-button']")).click();
	    selenium.driver.findElement(By.linkText("Administration")).click();
		selenium.driver.findElement(By.linkText("Customer")).click();
	    selenium.driver.findElement(By.linkText("Account")).click();*/
	    
	    selenium.driver.get(data.getUrl()+"createAccount.jsf");
		selenium.driver.findElement(By.xpath("//button[contains(.,'Add')]")).click();
		selenium.printScreenshot();

		selenium.driver.findElement(By.id("formAccountRoles:accountName")).clear();
	    selenium.driver.findElement(By.id("formAccountRoles:accountName")).sendKeys("AddAccount");
	    selenium.driver.findElement(By.id("formAccountRoles:customerUnitName_input")).sendKeys("TEST");
	    selenium.driver.findElement(By.cssSelector("span.ui-autocomplete-query")).click();
		selenium.printScreenshot();

	    selenium.driver.findElement(By.xpath("//button[contains(.,'Save')]")).click();
		
	    Thread.sleep(500);
	    assertEquals("Success to save AddAccount", selenium.driver.findElement(By.xpath("//div[@id='messages_growl_container']/div/div/div[2]/p")).getText());
	    selenium.printScreenshot();
    	selenium.resultPassed(TEST_CASE_4,date,"admin");
		}
		catch(Error | Exception e){
			selenium.printScreenshot();
			selenium.resultFailed(e.getMessage().toString(),TEST_CASE_4, date, "admin");
			e.printStackTrace();
		}finally{
		ExecutionResult.reportTestCaseResult(PROJECT, TEST_PLAN, TEST_CASE_4, data.getbuild(), selenium.exceptionNote, selenium.result);
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
