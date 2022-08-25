package com.ericsson.testng.administrator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import com.ericsson.connection.testlink.ExecutionResult;
import com.ericsson.connection.testlink.IConstants;
import com.ericsson.util.GeneratePDF;
import com.ericsson.util.ReadPropertyFile;
import com.ericsson.util.SeleniumUtil;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;


@SuppressWarnings("serial")
public class LoginAdmin extends TestCase implements IConstants {
  private StringBuffer verificationErrors = new StringBuffer();
  SeleniumUtil selenium = new SeleniumUtil();
  DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
  
  
  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	 selenium.initializeSelenium();
  }
  @Test public void testLoginNG() throws Exception {
	ReadPropertyFile data = new ReadPropertyFile();
	String date =dateFormat.format(new Date());
	selenium.pdf = new GeneratePDF(data.getpathPDFAdmin(), TEST_CASE_1+"_"+ date + ".pdf");
	selenium.pdf.addTitle("Test Suite : Login Administrator");	
	
	try{ 
		selenium.driver.get(data.getUrl()+"login.jsf");
		selenium.printScreenshot();
		selenium.driver.findElement(By.id("username")).clear();
		selenium.driver.findElement(By.id("username")).sendKeys(data.getUserIdAdmin());
		selenium.printScreenshot();
		selenium.driver.findElement(By.id("password")).clear();
		selenium.driver.findElement(By.id("password")).sendKeys(data.getpasswordAdmin());
		selenium.waitComponent("path",selenium, "//button[contains(.,'Sign In')]");
		selenium.driver.findElement(By.xpath("//button[contains(.,'Sign In')]")).click();
		selenium.printScreenshot();
		selenium.waitComponent("path",selenium, "//ul[@id='top-menu']/li[3]/a/i");
		selenium.driver.findElement(By.xpath("//ul[@id='top-menu']/li[3]/a/i")).click();
    	selenium.resultPassed(TEST_CASE_1, date,"admin");
		}catch(Exception exception){
			selenium.printScreenshot();
			selenium.resultFailed(exception.getMessage().toString(),TEST_CASE_1, date,"admin");
			exception.printStackTrace();
		}finally{
		ExecutionResult.reportTestCaseResult(PROJECT, TEST_PLAN, TEST_CASE_1, data.getbuild(), selenium.exceptionNote, selenium.result);
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
