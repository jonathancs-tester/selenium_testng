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
public class AddLifeCycleLifeCycleMap extends TestCase implements IConstants {
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
	selenium.pdf = new GeneratePDF(data.getpathPDFAdmin(), TEST_CASE_17+"_"+ date + ".pdf");
	selenium.pdf.addTitle("Test Suite : Add Life Cycle");	
	
	try{ 
		selenium.driver.get(data.getUrl()+"login.jsf");
		selenium.printScreenshot();
		selenium.driver.findElement(By.id("username")).clear();
		selenium.driver.findElement(By.id("username")).sendKeys(data.getUserIdAdmin());
		selenium.driver.findElement(By.id("password")).clear();
		selenium.driver.findElement(By.id("password")).sendKeys(data.getpasswordAdmin());
		selenium.driver.findElement(By.xpath("//button[contains(.,'Sign In')]")).click();
		
		selenium.driver.get(data.getUrl()+"createLifeCycleMap.jsf");
		selenium.driver.findElement(By.xpath("//button[contains(.,'Add')]")).click();
		Thread.sleep(500);
		selenium.printScreenshot();
		selenium.driver.findElement(By.id("formLifeCycleMap:nodeType")).clear();
		selenium.driver.findElement(By.id("formLifeCycleMap:nodeType")).sendKeys("AddLifeCycleMap");
		
		selenium.driver.findElement(By.xpath("//div[@id='formLifeCycleMap:idProductType']/div[3]")).click();
		selenium.driver.findElement(By.xpath("//div[@id='formLifeCycleMap:idProductType_panel']/div/ul/li[2]")).click();
		selenium.printScreenshot();
		selenium.driver.findElement(By.xpath("//form[@id='formLifeCycleMap']/descendant::button[contains(.,'Add')]")).click();
		selenium.printScreenshot();
		Thread.sleep(500);
		selenium.driver.findElement(By.id("formSoftVersion:nameOssHost")).clear();
		selenium.driver.findElement(By.id("formSoftVersion:nameOssHost")).sendKeys("1");
		selenium.driver.findElement(By.id("formSoftVersion:idOssHost")).clear();
		selenium.driver.findElement(By.id("formSoftVersion:idOssHost")).sendKeys("1");
		Thread.sleep(500);
	    selenium.driver.findElement(By.xpath("//form[@id='formSoftVersion']/descendant::button[contains(.,'Save')]")).click();
		Thread.sleep(500);
		selenium.driver.findElement(By.xpath("//form[@id='formLifeCycleMap']/descendant::button[contains(.,'Add')]")).click();
		selenium.printScreenshot();
		Thread.sleep(500);
		selenium.driver.findElement(By.id("formSoftVersion:nameOssHost")).clear();
		selenium.driver.findElement(By.id("formSoftVersion:nameOssHost")).sendKeys("2");
		selenium.driver.findElement(By.id("formSoftVersion:idOssHost")).clear();
		selenium.driver.findElement(By.id("formSoftVersion:idOssHost")).sendKeys("2");
		Thread.sleep(500);
	    selenium.driver.findElement(By.xpath("//form[@id='formSoftVersion']/descendant::button[contains(.,'Save')]")).click();
	    
		selenium.waitComponent("path",selenium, "//button[contains(@title,'Save')]");
		selenium.driver.findElement(By.xpath("//button[contains(@title,'Save')]")).click();
	    
	    Thread.sleep(500);
	    assertEquals("Life Cycle saved successfully", selenium.driver.findElement(By.xpath("//div[@id='messages_growl_container']/div/div/div[2]/p")).getText());
	    
	    selenium.printScreenshot();
    	selenium.resultPassed(TEST_CASE_17,date,"admin");
		
		}catch(Error | Exception e){
			selenium.printScreenshot();
			selenium.resultFailed(e.getMessage().toString(),TEST_CASE_17, date,"admin");
			e.printStackTrace();
		}finally{
		ExecutionResult.reportTestCaseResult(PROJECT, TEST_PLAN, TEST_CASE_17, data.getbuild(), selenium.exceptionNote, selenium.result);
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
