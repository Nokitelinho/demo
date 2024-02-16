package com.ibsplc.icargo.business.customermanagement.defaults.event.evaluator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;

public class InterfaceBrokerMappingDocumentChannelEvaluatorTest extends AbstractFeatureTest {

	private InterfaceBrokerMappingDocumentChannelEvaluator evaluator;
	private CustomerVO customerVO;
	
	@Override
	public void setup() throws Exception {
		evaluator = spy(InterfaceBrokerMappingDocumentChannelEvaluator.class);
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
	 * CustomerAgentVOs has OperationFlag Insert
	 * CustomerConsigneeVOs is null
	 */
	@Test
	public void shouldEvaluateTest1() {
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("I");
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		assertTrue(evaluator.evaluate(customerVO));
	}
	
	/**
	 * CustomerAgentVOs has OperationFlag Delete
	 * CustomerConsigneeVOs has OperationFlag Insert
	 */
	@Test
	public void shouldEvaluateTest2() {
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("D");
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		CustomerAgentVO consigneeVO = new CustomerAgentVO();
		consigneeVO.setOperationFlag("I");
		customerVO.setCustomerConsigneeVOs(Arrays.asList(consigneeVO));
		
		assertTrue(evaluator.evaluate(customerVO));
	}
	
	/**
	 * CustomerAgentVOs has OperationFlag Delete
	 * CustomerConsigneeVOs has OperationFlag Delete
	 */
	@Test
	public void shouldEvaluateTest3() {
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("D");
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		CustomerAgentVO consigneeVO = new CustomerAgentVO();
		consigneeVO.setOperationFlag("D");
		customerVO.setCustomerConsigneeVOs(Arrays.asList(consigneeVO));
		
		assertFalse(evaluator.evaluate(customerVO));
	}

}
