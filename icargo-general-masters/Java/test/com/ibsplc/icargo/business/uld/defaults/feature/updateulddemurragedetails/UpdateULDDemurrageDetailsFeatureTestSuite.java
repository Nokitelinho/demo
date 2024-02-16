/**
 * Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.UpdateULDDemurrageDetailsFeatureTestSuite.java
 * Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.enrichers.DemurrageDetailsEnricherTest;
import com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.persistors.UpdateDemurrageDetailsPersistorTest;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.UpdateULDDemurrageDetailsFeatureTestSuite.java
 *	This class is used as test suite for UpdateULDDemurrageDetailsFeatureTest
 */
@RunWith(Suite.class)
@SuiteClasses({ UpdateULDDemurrageDetailsFeatureTest.class, UpdateDemurrageDetailsPersistorTest.class, DemurrageDetailsEnricherTest.class})
public class UpdateULDDemurrageDetailsFeatureTestSuite {

}
