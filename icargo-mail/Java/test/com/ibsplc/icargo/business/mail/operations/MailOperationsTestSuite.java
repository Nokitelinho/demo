package com.ibsplc.icargo.business.mail.operations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ 
	AssignedFlightSegmentTest.class,
	ContainerAuditTest.class,
	MailTransferTest.class,
	ReassignControllerTest.class,
	MailbagTest.class,
	ContainerTest.class
})
public class MailOperationsTestSuite { 

}
