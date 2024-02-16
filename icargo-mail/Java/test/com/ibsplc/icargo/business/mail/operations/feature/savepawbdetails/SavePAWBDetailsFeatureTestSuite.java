package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.persistors.SavePAWBDetailsPersistorTest;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.preinvokers.SavePostalHAWBInvokerTest;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.preinvokers.SavePostalShipmentInvokerTest;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators.DocumentInStockValidatorTest;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators.MandatoryFieldsInPawbValidatorTest;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators.lh.DocumentInStockValidatorLHTest;

@RunWith(Suite.class)
@SuiteClasses({ SavePAWBDetailsFeatureTest.class,SavePostalShipmentInvokerTest.class,
	SavePostalHAWBInvokerTest.class,SavePAWBDetailsPersistorTest.class,DocumentInStockValidatorTest.class,
	MandatoryFieldsInPawbValidatorTest.class,DocumentInStockValidatorLHTest.class
	})
public class SavePAWBDetailsFeatureTestSuite {
	

	}


