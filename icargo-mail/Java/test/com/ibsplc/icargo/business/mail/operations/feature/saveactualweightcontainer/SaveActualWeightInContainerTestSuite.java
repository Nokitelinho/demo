package com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.enrichers.ActualWeightStatusEnricherTest;
import com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.persistors.SaveActualWeighInContainerPersistorTest;


@RunWith(Suite.class)
@SuiteClasses({ SaveActualWeightInContainerTest.class,SaveActualWeighInContainerPersistorTest.class,ActualWeightStatusEnricherTest.class
	})

public class SaveActualWeightInContainerTestSuite {

}
