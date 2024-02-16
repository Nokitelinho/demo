package com.ibsplc.icargo.business.uld.defaults.feature.returnuld;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibsplc.icargo.business.uld.defaults.feature.returnuld.enrichers.DemurrageEnricherTest;


@RunWith(Suite.class)
@SuiteClasses({ SaveReturnTransactionFeatureTest.class,DemurrageEnricherTest.class})

public class SaveReturnTransactionFeatureTestSuite {

}
