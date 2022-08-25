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
public class EditLifeCycleLifeCycleMap extends TestCase implements IConstants {
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
	selenium.pdf = new GeneratePDF(data.getpathPDFAdmin(), TEST_CASE_18+"_"+ date + ".pdf");
	selenium.pdf.addTitle("Test Suite : Edit Life Cycle Map");	
	
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
	    selenium.driver.findElement(By.id("formManageLifeCycleMap:lifeCycleMapTable:attribute:filter")).sendKeys("AddLifeCycleMap");
	    Thread.sleep(500);
	    selenium.driver.findElement(By.xpath("//button[contains(@title,'Edit')]")).click();
	    Thread.sleep(500);
	    selenium.printScreenshot();
		
	    selenium.driver.findElement(By.id("formLifeCycleMap:nodeType")).clear();
		selenium.driver.findElement(By.id("formLifeCycleMap:nodeType")).sendKeys("EditLifeCycleMap");
		
		selenium.driver.findElement(By.xpath("//div[@id='formLifeCycleMap:idProductType']/div[3]")).click();
		selenium.driver.findElement(By.xpath("//div[@id='formLifeCycleMap:idProductType_panel']/div/ul/li[3]")).click();
		selenium.printScreenshot();
		
		/* ==========TO DO AFTER ISSUE - BUG #1438
	 	selenium.driver.findElement(By.id("formLifeCycleMap:svdDataTableId:softwareVersion:filter")).clear();
	    selenium.driver.findElement(By.id("formLifeCycleMap:svdDataTableId:softwareVersion:filter")).sendKeys("1");
	    selenium.waitComponent("path",selenium, "//button[contains(@title,'Edit')]");
	    selenium.printScreenshot();
	    selenium.driver.findElement(By.xpath("//form[@id='formLifeCycleMap']/descendant::button[contains(@title,'Edit')]")).click();
	    selenium.driver.findElement(By.id("formSoftVersion:nameOssHost")).clear();
	    selenium.driver.findElement(By.id("formSoftVersion:nameOssHost")).sendKeys("3");
		selenium.driver.findElement(By.id("formSoftVersion:idOssHost")).clear();
		selenium.driver.findElement(By.id("formSoftVersion:idOssHost")).sendKeys("3");
		selenium.driver.findElement(By.xpath("//form[@id='formSoftVersion']/descendant::button[contains(.,'Edit')]")).click();
	    selenium.printScreenshot();
	    */
	    
		selenium.driver.findElement(By.xpath("//form[@id='formLifeCycleMap']/descendant::button[contains(.,'Add')]")).click();
		selenium.printScreenshot();
	    selenium.driver.findElement(By.id("formSoftVersion:nameOssHost")).clear();
		selenium.driver.findElement(By.id("formSoftVersion:nameOssHost")).sendKeys("4");
		selenium.printScreenshot();
		selenium.driver.findElement(By.id("formSoftVersion:idOssHost")).clear();
		selenium.driver.findElement(By.id("formSoftVersion:idOssHost")).sendKeys("4");
		selenium.printScreenshot();
		
	    selenium.driver.findElement(By.xpath("//form[@id='formSoftVersion']/descendant::button[contains(.,'Save')]")).click();
 
		selenium.driver.findElement(By.id("formLifeCycleMap:svdDataTableId:softwareVersion:filter")).clear();
	    selenium.driver.findElement(By.id("formLifeCycleMap:svdDataTableId:softwareVersion:filter")).sendKeys("2");
	    selenium.driver.findElement(By.xpath("//button[contains(@title,'Delete')]")).click();
	    selenium.printScreenshot();
		selenium.driver.findElement(By.xpath("//button[contains(@title,'Save')]")).click();
	    
	    Thread.sleep(500);
	    assertEquals("Life Cycle saved successfully", selenium.driver.findElement(By.xpath("//div[@id='messages_growl_container']/div/div/div[2]/p")).getText());
	    Thread.sleep(500);
	    selenium.printScreenshot();
    	selenium.resultPassed(TEST_CASE_18,date,"admin");
		
		}catch(Error | Exception e){
			selenium.printScreenshot();
			selenium.resultFailed(e.getMessage().toString(),TEST_CASE_18, date,"admin");
			e.printStackTrace();
		}finally{
		ExecutionResult.reportTestCaseResult(PROJECT, TEST_PLAN, TEST_CASE_18, data.getbuild(), selenium.exceptionNote, selenium.result);
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
