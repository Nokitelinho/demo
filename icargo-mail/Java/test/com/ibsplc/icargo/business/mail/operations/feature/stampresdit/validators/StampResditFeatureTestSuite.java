package com.ibsplc.icargo.business.mail.operations.feature.stampresdit.validators;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.StampResditFeatureTest;
import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.persistors.MailResditPersistorTest;

@RunWith(Suite.class)
@SuiteClasses({ StampResditFeatureTest.class, StampResditValidatorTest.class, MailResditPersistorTest.class })
public class StampResditFeatureTestSuite {

}
