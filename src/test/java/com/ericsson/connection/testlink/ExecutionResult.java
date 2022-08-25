package com.ericsson.connection.testlink;

import org.dbfacade.testlink.api.client.TestLinkAPIClient;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPIException;

public class ExecutionResult implements IConstants {
	public static void reportTestCaseResult(String testProject, String testPlan, String testCase, String buildName, String note, String result) throws TestLinkAPIException, org.dbfacade.testlink.api.client.TestLinkAPIException { 
			TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(DEVKEY, URL);
			testlinkAPIClient.reportTestCaseResult(testProject, testPlan, testCase, buildName, note, result);
			
	}
	
}
