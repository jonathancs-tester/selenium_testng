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
public class SendRequest extends TestCase implements IConstants {
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
	selenium.pdf = new GeneratePDF(data.getpathPDFAdmin(), TEST_CASE_2+"_"+ date + ".pdf");
	selenium.pdf.addTitle("Test Suite : Request for Access");	
	
	try{ 
    selenium.driver.get(data.getUrl()+"login.jsf");
    selenium.printScreenshot();
    selenium.driver.findElement(By.xpath("//button[contains(.,'Request for Access')]")).click();
    selenium.printScreenshot();
    selenium.driver.findElement(By.xpath("//button[contains(.,'Cancel')]")).click();
	selenium.resultPassed(TEST_CASE_2, date,"admin");
	
	}catch(Exception exception){
		selenium.printScreenshot();
		selenium.resultFailed(exception.getMessage().toString(),TEST_CASE_2, date,"admin");
		exception.printStackTrace();
	}finally{
		ExecutionResult.reportTestCaseResult(PROJECT, TEST_PLAN, TEST_CASE_2, data.getbuild(), selenium.exceptionNote, selenium.result);  
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
