/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.LUCMessageFeatureTestSuite.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.lucmessage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.AgentPartyTypeDetailsEnricherTest;
import com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.NonCarrierIDEnricherTest;
import com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.validators.ULDFormatValidatorTest;


/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.LUCMessageFeatureTestSuite.java
 *	This class is used for
 */
@RunWith(Suite.class)
@SuiteClasses({ LUCMessageFeatureTest.class,
	ULDFormatValidatorTest.class,
	NonCarrierIDEnricherTest.class,
	AgentPartyTypeDetailsEnricherTest.class})
public class LUCMessageFeatureTestSuite {

	
}
