package com.ibsplc.icargo.business.uld.defaults.event;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibsplc.icargo.business.uld.defaults.event.evaluator.UpdateULDForOPSOnSaveAcceptanceChannelEvaluatorTest;
import com.ibsplc.icargo.business.uld.defaults.event.evaluator.UpdateULDsForOperationsOnSaveULDAcceptanceEvaluatorTest;
import com.ibsplc.icargo.business.uld.defaults.event.mapper.UpdateULDForOperationsChannelMapperTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	
	UpdateULDForOPSOnSaveAcceptanceChannelEvaluatorTest.class,
	UpdateULDForOperationsChannelMapperTest.class,
	UpdateULDsForOperationsOnSaveULDAcceptanceEvaluatorTest.class
})

public class UpdateULDForOperationsChannelTestSuite {

}
