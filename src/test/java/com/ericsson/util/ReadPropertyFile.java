package com.ericsson.util;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadPropertyFile {
	
	protected Properties prop = null;
	protected FileInputStream input = new FileInputStream("src/test/resources/config.properties");
	
	public ReadPropertyFile() throws Exception{
		prop = new Properties();
	    prop.load(input);	
	}
	public String getUrl(){
		return prop.getProperty("urlServer");	
	}
	
	public String getUserIdAdmin(){
		return prop.getProperty("userIdAdmin");
	}
	public String getpasswordAdmin(){
		return prop.getProperty("passwordAdmin");	
	}
	public String getuserIdView(){
		return prop.getProperty("userIdView");	
	}
	public String getpasswordView(){
		return prop.getProperty("passwordView");	
	}
	public String getbuild(){
		return prop.getProperty("build");	
	}
	public String getpathPDFAdmin(){
		return prop.getProperty("pathPDFAdmin");	
	}
	public String getpathPDFView(){
		return prop.getProperty("pathPDFView");	
	}
	public String getpathPhantonJS(){
		return prop.getProperty("pathPhantonJS");	
	}
	public String getpathTestlink(){
		return prop.getProperty("pathTestlink");	
	}
	public String getversion(){
		return prop.getProperty("version");	
	}
	
}
