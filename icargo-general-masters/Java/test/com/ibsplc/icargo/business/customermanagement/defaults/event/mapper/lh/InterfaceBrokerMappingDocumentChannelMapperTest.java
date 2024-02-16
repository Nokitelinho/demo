package com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.lh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerSupportingDocumentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

public class InterfaceBrokerMappingDocumentChannelMapperTest extends AbstractFeatureTest {

	private InterfaceBrokerMappingDocumentChannelMapper channelMapper;
	private CustomerVO customerVO;
	
	@Override
	public void setup() throws Exception {
		channelMapper = spy(InterfaceBrokerMappingDocumentChannelMapper.class);
		customerVO = new CustomerVO();
	}
	
	@Test
	public void shouldMapWhenCustomerAgentVOsAndCustomerConsigneeVOsAreNull() {
		Collection<POADocumentJMSTemplateVO> templateVOs = channelMapper.mapToPOADocumentJMSTemplateVOListFromCustomerVO(customerVO);
		assertTrue(templateVOs.isEmpty());
	}
	
	/**
	 * Scenario : CustomerAgentVO operation flag is Insert, poa type is General POA
	 */
	@Test
	public void shouldMapWhenCustomerAgentVOsIsNotNullAndCustomerConsigneeVOsIsEmpty() {
		byte[] data = "POAUPL".getBytes();
		CustomerSupportingDocumentVO documentVO = new CustomerSupportingDocumentVO();
		documentVO.setFileData(data);
		
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("I");
		agentVO.setCustomerCode("CUSCOD");
		agentVO.setPoaType("General POA");
		agentVO.setPoaCreationTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		agentVO.setSupportingDocumentVOs(Arrays.asList(documentVO));
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		customerVO.setCustomerConsigneeVOs(Collections.emptyList());
		
		Collection<POADocumentJMSTemplateVO> templateVOs = channelMapper.mapToPOADocumentJMSTemplateVOListFromCustomerVO(customerVO);
		assertEquals(1, templateVOs.size(), 0);
	}
	
	/**
	 * Scenario : CustomerAgentVO operation flag is Delete, poa type is Special POA
	 * 				  and CustomerConsigneeVO  operation flag is Insert
	 */
	@Test
	public void shouldMapWhenCustomerAgentVOsIsDeletedAndCustomerConsigneeVOsIsInserted() {
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("D");
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		byte[] data = "POAUPL".getBytes();
		CustomerSupportingDocumentVO documentVO = new CustomerSupportingDocumentVO();
		documentVO.setFileData(data);
		
		CustomerAgentVO consigneeVO = new CustomerAgentVO();
		consigneeVO.setOperationFlag("I");
		consigneeVO.setCustomerCode("CUSCOD");
		consigneeVO.setPoaType("Special POA");
		consigneeVO.setScc("CHA,CHB,CHC,CHD,CHE,CHF,CHG,CHH,CHI,CHJ,CHK,CHL,CHM");
		consigneeVO.setPoaCreationTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		consigneeVO.setSupportingDocumentVOs(Arrays.asList(documentVO));
		customerVO.setCustomerConsigneeVOs(Arrays.asList(consigneeVO));
		
		Collection<POADocumentJMSTemplateVO> templateVOs = channelMapper.mapToPOADocumentJMSTemplateVOListFromCustomerVO(customerVO);
		assertEquals(1, templateVOs.size(), 0);
	}
	
	/**
	 * Scenario : CustomerAgentVO operation flag is Insert, poa type is Single POA
	 */
	@Test
	public void shouldMapWhenCustomerAgentVOsIsWithAwbAndCustomerConsigneeVOsIsNull() {
		byte[] data = "POAUPL".getBytes();
		CustomerSupportingDocumentVO documentVO = new CustomerSupportingDocumentVO();
		documentVO.setFileData(data);
		
		CustomerAgentVO agentVO = new CustomerAgentVO();
		agentVO.setOperationFlag("I");
		agentVO.setCustomerCode("CUSCOD");
		agentVO.setPoaType("Single POA");
		agentVO.setPoaCreationTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		agentVO.setAwbNum("SHPPFX MSTDOCNUM");
		agentVO.setSupportingDocumentVOs(Arrays.asList(documentVO));
		customerVO.setCustomerAgentVOs(Arrays.asList(agentVO));
		
		Collection<POADocumentJMSTemplateVO> templateVOs = channelMapper.mapToPOADocumentJMSTemplateVOListFromCustomerVO(customerVO);
		assertEquals(1, templateVOs.size(), 0);
	}

}
