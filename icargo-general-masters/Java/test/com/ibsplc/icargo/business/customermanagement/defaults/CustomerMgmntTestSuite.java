/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntTestSuite.java
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibsplc.icargo.business.customermanagement.defaults.event.UploadBrokerMappingDocumentChannelTest;
import com.ibsplc.icargo.business.customermanagement.defaults.event.evaluator.InterfaceBrokerMappingDocumentChannelEvaluatorTest;
import com.ibsplc.icargo.business.customermanagement.defaults.event.evaluator.UploadBrokerMappingDocumentChannelEvaluatorTest;
import com.ibsplc.icargo.business.customermanagement.defaults.event.lh.InterfaceBrokerMappingDocumentChannelTest;
import com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.UploadBrokerMappingDocumentChannelMapperTest;
import com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.lh.InterfaceBrokerMappingDocumentChannelMapperTest;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.EmailAccountStatementFeatureTest;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.enrichers.MessageTemplateEnricherTest;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.otherfeatures.GetAccountStatementInvokerTest;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeatureTest;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures.AddTransactionLockInvokerTest;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures.ReleaseTransactionLockInvokerTest;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.uploadbrokermappingdocument.UploadBrokerMappingDocumentFeatureTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	CustomerMgmntControllerTest.class,
	EmailAccountStatementFeatureTest.class,
	GetAccountStatementInvokerTest.class,
	MessageTemplateEnricherTest.class,
	SaveBrokerMappingFeatureTest.class,
	AddTransactionLockInvokerTest.class,
	ReleaseTransactionLockInvokerTest.class,
	UploadBrokerMappingDocumentFeatureTest.class,
	UploadBrokerMappingDocumentChannelTest.class,
	UploadBrokerMappingDocumentChannelEvaluatorTest.class,
	UploadBrokerMappingDocumentChannelMapperTest.class,
	InterfaceBrokerMappingDocumentChannelTest.class,
	InterfaceBrokerMappingDocumentChannelEvaluatorTest.class,
	InterfaceBrokerMappingDocumentChannelMapperTest.class
})
public class CustomerMgmntTestSuite {
	
}
