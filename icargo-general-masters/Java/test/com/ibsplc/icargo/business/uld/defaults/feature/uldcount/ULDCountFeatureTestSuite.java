package com.ibsplc.icargo.business.uld.defaults.feature.uldcount;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibsplc.icargo.business.uld.defaults.feature.uldcount.enrichers.UCMCountEnricherTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	ULDCountFeatureTest.class,
	UCMCountEnricherTest.class
})
public class ULDCountFeatureTestSuite {

}
