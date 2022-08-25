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
public class EditLifeCycleLifeCycle extends TestCase implements IConstants {
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
	selenium.pdf = new GeneratePDF(data.getpathPDFAdmin(), TEST_CASE_15+"_"+ date + ".pdf");
	selenium.pdf.addTitle("Test Suite : Edit Life Cycle");	
	
	try{ 
		selenium.driver.get(data.getUrl()+"login.jsf");
		selenium.printScreenshot();
		selenium.driver.findElement(By.id("username")).clear();
		selenium.driver.findElement(By.id("username")).sendKeys(data.getUserIdAdmin());
		selenium.driver.findElement(By.id("password")).clear();
		selenium.driver.findElement(By.id("password")).sendKeys(data.getpasswordAdmin());
		selenium.driver.findElement(By.xpath("//button[contains(.,'Sign In')]")).click();
		
		selenium.driver.get(data.getUrl()+"createLifeCycle.jsf");
		selenium.driver.findElement(By.id("formManageLifeCycle:lifeCycleTableID:attributeValue:filter")).clear();
	    selenium.driver.findElement(By.id("formManageLifeCycle:lifeCycleTableID:attributeValue:filter")).sendKeys("AddLifeCycle");
	    Thread.sleep(500);
	    selenium.driver.findElement(By.xpath("//button[contains(@title,'Edit')]")).click();
	    selenium.printScreenshot();
		
	    selenium.waitComponent("path",selenium, "//div[@id='formLifeCycle:idProductType']/div[3]");
		selenium.driver.findElement(By.xpath("//div[@id='formLifeCycle:idProductType']/div[3]")).click();
		selenium.waitComponent("path",selenium, "//div[@id='formLifeCycle:idProductType_panel']/descendant::li[contains(.,'AIR')]");
		selenium.driver.findElement(By.xpath("//div[@id='formLifeCycle:idProductType_panel']/descendant::li[contains(.,'AIR')]")).click();
		selenium.driver.findElement(By.id("formLifeCycle:lifeCycleAttributeValue")).clear();
		selenium.driver.findElement(By.id("formLifeCycle:lifeCycleAttributeValue")).sendKeys("EditLifeCycle");	
	    Thread.sleep(500);
	    selenium.printScreenshot();
	    selenium.driver.findElement(By.xpath("//form[@id='formLifeCycle']/descendant::button[contains(.,'Save')]")).click();
	    selenium.driver.findElement(By.xpath("//form[@id='formLifeCycle']/descendant::button[contains(.,'Save')]")).click();
	    assertEquals("Life Cycle saved successfully", selenium.driver.findElement(By.xpath("//div[@id='messages_growl_container']/div/div/div[2]/p")).getText());
	    
	    Thread.sleep(500);
	    selenium.printScreenshot();
    	selenium.resultPassed(TEST_CASE_15,date,"admin");
		
		}catch(Error | Exception e){
			selenium.printScreenshot();
			selenium.resultFailed(e.getMessage().toString(),TEST_CASE_15, date,"admin");
			e.printStackTrace();
		}finally{
		ExecutionResult.reportTestCaseResult(PROJECT, TEST_PLAN, TEST_CASE_15, data.getbuild(), selenium.exceptionNote, selenium.result);
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
