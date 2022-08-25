package com.ericsson.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.dbfacade.testlink.api.client.TestLinkAPIResults;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ericsson.connection.testlink.IConstants;

public class SeleniumUtil implements IConstants{
	
	  public WebDriver driver;
	  public String result = null;
	  public String exceptionNote=null;
	  public GeneratePDF pdf;
	  private String print;
	  
	  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	  
	
	public void initializeSelenium() throws Exception{
		ReadPropertyFile data = new ReadPropertyFile();
		DesiredCapabilities capability = new DesiredCapabilities();  
		capability.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, data.getpathPhantonJS());
	    capability.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
	    driver = new PhantomJSDriver(capability);
		driver.manage().window().setSize(new Dimension(1280,768));
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void resultPassed(String testcase, String date, String user) throws Exception {
		ReadPropertyFile data = new ReadPropertyFile();
		result = TestLinkAPIResults.TEST_PASSED;
		pdf.addText("Test Result: Passed");
		pdf.addText("Date: "+dateFormat.format(new Date()));
		exceptionNote=data.getpathTestlink()+user+"/"+data.getversion()+"/"+testcase+"_"+date + ".pdf";
	}
	
	public void resultFailed(String exception, String testcase, String date, String user) throws Exception {
		ReadPropertyFile data = new ReadPropertyFile();
		result = TestLinkAPIResults.TEST_FAILED;
		pdf.addText("Test Result: Failed");
		pdf.addText("Date: "+dateFormat.format(new Date()));
		exceptionNote=data.getpathTestlink()+user+"/"+data.getversion()+"/"+testcase+"_"+date+ ".pdf"+" - "+" Exception: "+exception;
	}
	
	public void printScreenshot() {
		print = ((PhantomJSDriver) driver).getScreenshotAs(OutputType.BASE64);
		pdf.addImagePDF(print, "DAMA");
	}
	
	public void waitComponent(String type ,SeleniumUtil selenium, String component) {
		WebDriverWait wait = new WebDriverWait(selenium.driver, 10);
		if(type=="path"){
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(component)));
	    }else if(type=="id"){
	    	wait.until(ExpectedConditions.elementToBeClickable(By.id(component)));
	    }
		
	}
}
