package com.ibsplc.icargo.business.customermanagement.defaults.event.evaluator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerSupportingDocumentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;

public class UploadBrokerMappingDocumentChannelEvaluatorTest extends AbstractFeatureTest {

	private UploadBrokerMappingDocumentChannelEvaluator evaluator;
	private CustomerVO customerVO;
	
	@Override
	public void setup() throws Exception {
		evaluator = spy(UploadBrokerMappingDocumentChannelEvaluator.class);
		customerVO = new CustomerVO();
	}
	
	@Test
	public void shouldEvaluateFalseWhenCustomerAgentVOsAndCustomerConsigneeVOsAreNull() {
		assertFalse(evaluator.evaluate(customerVO));
	}
	
	@Test
	public void shouldEvaluateFalseWhenCustomerAgentVOsAndCustomerConsigneeVOsAreEmpty() {
		customerVO.setCustomerAgentVOs(Collections.emptyList());
		customerVO.setCustomerConsigneeVOs(Collections.emptyList());
		assertFalse(evaluator.evaluate(customerVO));
	}
	
	/**
	 * CustomerAgentVOs has OperationFlag Insert but no SupportingDocumentVO
	 * CustomerConsigneeVOs has OperationFlag Insert but no SupportingDocumentVO
	 */
	@Test
	public void shouldEvaluateTest1() {
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("I");
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		CustomerAgentVO consigneeVO = new CustomerAgentVO();
		consigneeVO.setOperationFlag("I");
		customerVO.setCustomerConsigneeVOs(Arrays.asList(consigneeVO));
		
		assertFalse(evaluator.evaluate(customerVO));
	}
	
	/**
	 * CustomerAgentVOs has OperationFlag Insert but empty SupportingDocumentVO
	 * CustomerConsigneeVOs has OperationFlag Insert but empty SupportingDocumentVO
	 */
	@Test
	public void shouldEvaluateTest2() {
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("I");
		agentVO.setSupportingDocumentVOs(Collections.emptyList());
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		CustomerAgentVO consigneeVO = new CustomerAgentVO();
		consigneeVO.setOperationFlag("I");
		consigneeVO.setSupportingDocumentVOs(Collections.emptyList());
		customerVO.setCustomerConsigneeVOs(Arrays.asList(consigneeVO));
		
		assertFalse(evaluator.evaluate(customerVO));
	}
	
	/**
	 * CustomerAgentVOs has OperationFlag Insert and SupportingDocumentVO
	 */
	@Test
	public void shouldEvaluateTest3() {
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("I");
		agentVO.setSupportingDocumentVOs(Arrays.asList(new CustomerSupportingDocumentVO()));
		
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		assertTrue(evaluator.evaluate(customerVO));
	}
	
	/**
	 * CustomerAgentVOs has OperationFlag Delete
	 * CustomerConsigneeVOs has OperationFlag Insert and SupportingDocumentVO
	 */
	@Test
	public void shouldEvaluateTest4() {
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("D");
		agentVO.setSupportingDocumentVOs(Arrays.asList(new CustomerSupportingDocumentVO()));
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		CustomerAgentVO consigneeVO = new CustomerAgentVO();
		consigneeVO.setOperationFlag("I");
		consigneeVO.setSupportingDocumentVOs(Arrays.asList(new CustomerSupportingDocumentVO()));
		customerVO.setCustomerConsigneeVOs(Arrays.asList(consigneeVO));
		
		assertTrue(evaluator.evaluate(customerVO));
	}

}
