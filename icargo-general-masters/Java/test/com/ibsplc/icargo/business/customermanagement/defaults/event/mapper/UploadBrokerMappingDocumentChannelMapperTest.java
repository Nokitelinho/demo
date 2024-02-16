package com.ibsplc.icargo.business.customermanagement.defaults.event.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerSupportingDocumentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;

public class UploadBrokerMappingDocumentChannelMapperTest extends AbstractFeatureTest {

	private UploadBrokerMappingDocumentChannelMapper channelMapper;
	private CustomerVO customerVO;
	
	@Override
	public void setup() throws Exception {
		channelMapper = spy(UploadBrokerMappingDocumentChannelMapper.class);
		customerVO = new CustomerVO();
	}
	
	@Test
	public void shouldMapWhenCustomerAgentVOsAndCustomerConsigneeVOsAreNull() {
		DocumentRepositoryMasterVO masterVO = channelMapper.mapToDocumentRepositoryMasterVOFromCustomerVO(customerVO);
		assertTrue(masterVO.getAttachments().isEmpty());
	}
	
	@Test
	public void shouldMapWhenCustomerAgentVOsIsNotNullAndCustomerConsigneeVOsIsEmpty() {
		byte[] data = "POAUPL".getBytes();
		CustomerSupportingDocumentVO documentVO = new CustomerSupportingDocumentVO();
		documentVO.setFileData(data);
		
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("I");
		agentVO.setSupportingDocumentVOs(Arrays.asList(documentVO));
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		customerVO.setCustomerConsigneeVOs(Collections.emptyList());
		
		DocumentRepositoryMasterVO masterVO = channelMapper.mapToDocumentRepositoryMasterVOFromCustomerVO(customerVO);
		assertEquals(1, masterVO.getAttachments().size(), 0);
	}
	
	@Test
	public void shouldMapWhenCustomerAgentVOsIsInsertedAndCustomerConsigneeVOsIsDeleted() {
		byte[] data = "POAUPL".getBytes();
		CustomerSupportingDocumentVO documentVO = new CustomerSupportingDocumentVO();
		documentVO.setFileData(data);
		
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("I");
		agentVO.setSupportingDocumentVOs(Arrays.asList(documentVO));
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		CustomerAgentVO consigneeVO = new CustomerAgentVO();
		consigneeVO.setOperationFlag("D");
		customerVO.setCustomerConsigneeVOs(Arrays.asList(consigneeVO));
		
		DocumentRepositoryMasterVO masterVO = channelMapper.mapToDocumentRepositoryMasterVOFromCustomerVO(customerVO);
		assertEquals(1, masterVO.getAttachments().size(), 0);
	}

}
