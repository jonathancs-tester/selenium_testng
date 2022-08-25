package com.ericsson.testng.administrator;

import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;

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
public class DeleteLifeCycleLifeCycleMap extends TestCase implements IConstants {
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
	selenium.pdf = new GeneratePDF(data.getpathPDFAdmin(), TEST_CASE_19+"_"+ date + ".pdf");
	selenium.pdf.addTitle("Test Suite : Delete Life Cycle Map");
	
	
	
	try{ 
		selenium.driver.get(data.getUrl()+"login.jsf");
		selenium.printScreenshot();
		selenium.driver.findElement(By.id("username")).clear();
		selenium.driver.findElement(By.id("username")).sendKeys(data.getUserIdAdmin());
		selenium.driver.findElement(By.id("password")).clear();
		selenium.driver.findElement(By.id("password")).sendKeys(data.getpasswordAdmin());
		selenium.driver.findElement(By.xpath("//button[contains(.,'Sign In')]")).click();
		
		selenium.driver.get(data.getUrl()+"createLifeCycleMap.jsf");
		selenium.driver.findElement(By.id("formManageLifeCycleMap:lifeCycleMapTable:attribute:filter")).clear();
	    selenium.driver.findElement(By.id("formManageLifeCycleMap:lifeCycleMapTable:attribute:filter")).sendKeys("EditLifeCycleMap");
	    selenium.printScreenshot();
	    
	    selenium.driver.findElement(By.xpath("//button[contains(@title,'Delete')]")).click();
	    selenium.printScreenshot();
	    selenium.waitComponent("path",selenium, "//button[contains(@title,'Yes')]");
	    selenium.printScreenshot();
	    
	    selenium.driver.findElement(By.xpath("//button[contains(@title,'Yes')]")).click();	
	    selenium.printScreenshot();
	    assertEquals("Life cycle map has been deleted", selenium.driver.findElement(By.xpath("//div[@id='messages_growl_container']/div/div/div[2]/p")).getText());
    	selenium.resultPassed(TEST_CASE_19,date,"admin");
		
		}catch(Error | Exception e){
			selenium.printScreenshot();
			selenium.resultFailed(e.getMessage().toString(),TEST_CASE_19, date,"admin");
			e.printStackTrace();
		}finally{
		ExecutionResult.reportTestCaseResult(PROJECT, TEST_PLAN, TEST_CASE_19, data.getbuild(), selenium.exceptionNote, selenium.result);
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
