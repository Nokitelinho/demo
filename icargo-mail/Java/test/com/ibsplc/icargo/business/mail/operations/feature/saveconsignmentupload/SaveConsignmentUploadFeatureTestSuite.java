package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.validators.PostalAuthorityCodeValidatorTest;

@RunWith(Suite.class)
@SuiteClasses({ 
		PostalAuthorityCodeValidatorTest.class, 
		//FlightDetailsValidatorTest.class, 
		//ConsignmentDetailsEnricherTest.class,
		//ExistingConsignmentDetailsEnricherTest.class, 
		//SaveConsignmentUploadFeatureTest.class 
		})
public class SaveConsignmentUploadFeatureTestSuite {

}
