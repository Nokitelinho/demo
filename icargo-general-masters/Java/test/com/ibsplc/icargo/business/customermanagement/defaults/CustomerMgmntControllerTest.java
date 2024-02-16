/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntControllerTest.java
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.ibsplc.icargo.business.shared.customer.vo.*;
import org.junit.Test;
import org.mockito.Mockito;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.EmailAccountStatementFeature;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.uploadbrokermappingdocument.UploadBrokerMappingDocumentFeature;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.CRAAgentBillingProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.MsgbrokerMessageProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.SharedCustomerProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.SharedGeneralMasterGroupingProxy;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;

public class CustomerMgmntControllerTest extends AbstractFeatureTest {

	private CustomerMgmntController controller;
	
	private SharedGeneralMasterGroupingProxy sharedGeneralMasterGroupingProxy;
	private SharedCustomerProxy sharedCustomerProxy;
	private CRAAgentBillingProxy cRAAgentBillingProxy;
	OperationsShipmentProxy operationsShipmentProxy;
	MsgbrokerMessageProxy msgbrokerMessageProxy;
	private EmailAccountStatementFeature emailAccountStatementFeature;
	private SaveBrokerMappingFeature saveBrokerMappingFeature;
	private UploadBrokerMappingDocumentFeature uploadBrokerMappingDocumentFeature;
	@Override
	public void setup() throws Exception {
		controller = spy(CustomerMgmntController.class);
		EntityManagerMock.mockEntityManager();
		emailAccountStatementFeature = mockBean("customermanagement.defaults.emailAccountStatementFeature", EmailAccountStatementFeature.class);
		saveBrokerMappingFeature = mockBean("customermanagement.defaults.saveBrokerMappingFeature", SaveBrokerMappingFeature.class);
		uploadBrokerMappingDocumentFeature = mockBean("customermanagement.defaults.uploadBrokerMappingDocumentFeature", UploadBrokerMappingDocumentFeature.class);
		cRAAgentBillingProxy = mockProxy(CRAAgentBillingProxy.class);
		sharedGeneralMasterGroupingProxy = mockProxy(SharedGeneralMasterGroupingProxy.class);
		sharedCustomerProxy = mockProxy(SharedCustomerProxy.class);
		operationsShipmentProxy=Mockito.mock(OperationsShipmentProxy.class,Mockito.RETURNS_DEEP_STUBS);
		msgbrokerMessageProxy=mockProxy(MsgbrokerMessageProxy.class);
	}

	/**
	 * Test method for CustomerMgmntController.emailAccountStatement()
	 */
	@Test
	public void shouldSendEmailAccountStatement() throws BusinessException, SystemException {
		doReturn(new EmailAccountStatementFeatureVO()).when(emailAccountStatementFeature).execute(any(EmailAccountStatementFeatureVO.class));
		controller.emailAccountStatement(new EmailAccountStatementFeatureVO());
		verify(emailAccountStatementFeature, times(1)).execute(any(EmailAccountStatementFeatureVO.class));
	}
	
	/**
	 * Test method for CustomerMgmntController.emailAccountStatement()
	 */
	@Test(expected = SystemException.class)
	public void shouldThrowErrorOnEmailAccountStatement() throws BusinessException, SystemException {
		BusinessException exception = new BusinessException() {};
		doThrow(exception).when(emailAccountStatementFeature).execute(any(EmailAccountStatementFeatureVO.class));
		controller.emailAccountStatement(new EmailAccountStatementFeatureVO());
	}
	
	/**
	 * Test method for CustomerMgmntController.printAccountStatement()
	 * Scenario : when export mode is E
	 */
	@Test
	public void shouldPrintExcelAccountStatement() throws ProxyException, SystemException {
		CustomerFilterVO filterVO = new CustomerFilterVO();
		filterVO.setExportMode("E");
		
		doReturn(new ArrayList<CustomerInvoiceAWBDetailsVO>()).when(cRAAgentBillingProxy).findAccountStatementForPrint(any(CustomerFilterVO.class));
		doReturn("Byte".getBytes()).when(controller).createWorkBookBytesForExcelReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		byte bytes[] = controller.printAccountStatement(filterVO);
		verify(controller, times(1)).createWorkBookBytesForExcelReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		assertNotNull(bytes);
	}
	
	/**
	 * Test method for CustomerMgmntController.printAccountStatement()
	 * Scenario : when export mode is P
	 */
	@Test
	public void shouldPrintPdfAccountStatement() throws ProxyException, SystemException {
		CustomerFilterVO filterVO = new CustomerFilterVO();
		filterVO.setExportMode("P");
		
		doReturn(new ArrayList<CustomerInvoiceAWBDetailsVO>()).when(cRAAgentBillingProxy).findAccountStatementForPrint(any(CustomerFilterVO.class));
		doReturn("Byte".getBytes()).when(controller).createBytesForPDFReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		byte bytes[] = controller.printAccountStatement(filterVO);
		verify(controller, times(1)).createBytesForPDFReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		assertNotNull(bytes);
	}
	
	/**
	 * Test method for CustomerMgmntController.printAccountStatement()
	 */
	@Test(expected = SystemException.class)
	public void shouldThrowErrorOnPrintAccountStatement() throws ProxyException, SystemException {
		ProxyException exception = new ProxyException(new BusinessException() {});
		doThrow(exception).when(cRAAgentBillingProxy).findAccountStatementForPrint(any(CustomerFilterVO.class));
		controller.printAccountStatement(new CustomerFilterVO());
	}
	@Test
	public void listGroupDetailsForCustomer() throws ProxyException, SystemException {
		doReturn(new ArrayList<GeneralMasterGroupVO>()).when(sharedGeneralMasterGroupingProxy)
							.listGroupDetailsForCustomer(any(GeneralMasterGroupFilterVO.class));
		controller.listGroupDetailsForCustomer(new GeneralMasterGroupFilterVO());
	}
	@Test
	public void shouldSaveCustomerCreditDetails() throws SystemException, ProxyException {
		CustomerCreditVO customerCreditVO = new CustomerCreditVO();
		customerCreditVO.setBaseCurrency("USD");
		doReturn(customerCreditVO).when(sharedCustomerProxy).saveCustomerCreditDetails(any(CustomerCreditVO.class));
		controller.saveCustomerCreditDetails(new CustomerCreditVO());
	}
	@Test(expected = SystemException.class)
	public void shouldThrowErroronSaveCustomerCreditDetails() throws SystemException,ProxyException {
		ProxyException exception = new ProxyException(new BusinessException() {});
		doThrow(exception).when(sharedCustomerProxy).saveCustomerCreditDetails(any(CustomerCreditVO.class));
		controller.saveCustomerCreditDetails(new CustomerCreditVO());
	}
	@Test
	public void shouldFindCustomerCreditDetails() throws SystemException, ProxyException {
		CustomerCreditVO customerCreditVO = new CustomerCreditVO();
		customerCreditVO.setBaseCurrency("USD");
		doReturn(customerCreditVO).when(sharedCustomerProxy).findCustomerCreditDetails(any(CustomerCreditFilterVO.class));
		controller.findCustomerCreditDetails(new CustomerCreditFilterVO());
	}
	@Test(expected = SystemException.class)
	public void shouldThrowErroronFindCustomerCreditDetails() throws SystemException,ProxyException {
		ProxyException exception = new ProxyException(new BusinessException() {});
		doThrow(exception).when(sharedCustomerProxy).findCustomerCreditDetails(any(CustomerCreditFilterVO.class));
		controller.findCustomerCreditDetails(new CustomerCreditFilterVO());
	}

	@Test(expected = SystemException.class)
	public void shouldThrowErroronFindCustomerCertificateDetails() throws SystemException,ProxyException {
		ProxyException exception = new ProxyException(new BusinessException() {});
		doThrow(exception).when(sharedCustomerProxy).findCertificateTypes(any(CustomerFilterVO.class));
		controller.findCustomerCertificates(new CustomerVO());
	}

	@Test
	public void shouldFindCustomerCertificateDetails() throws SystemException, ProxyException {
		CustomerVO customerVO = new CustomerVO();
		customerVO.setCompanyCode("IBS");
		customerVO.setCustomerCode("0001");
		List<CustomerCertificateVO> customerCertificateVOList = new ArrayList<>();
		doReturn(customerCertificateVOList).when(sharedCustomerProxy).findCertificateTypes(any(CustomerFilterVO.class));
		controller.findCustomerCertificates(customerVO);
	}
	@Test
	public void validateShipmentDetailsTest() throws  SystemException, ProxyException{
		Collection<ShipmentVO> shipmentVo=new ArrayList<>();
		ShipmentFilterVO shipmentFilterVO=new ShipmentFilterVO();
		shipmentFilterVO.setCompanyCode("LH");
		shipmentFilterVO.setShipmentPrefix("020");
		shipmentFilterVO.setDocumentNumber("14714744");
		doReturn(shipmentVo).when(operationsShipmentProxy).validateShipmentDetails(any());
		try {
			controller.validateShipmentDetails(shipmentFilterVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void validateSinglePoaTest() throws  SystemException, ProxyException{
		Collection<CustomerAgentVO> agtvo=new ArrayList<>();
		ShipmentFilterVO shipmentFilterVO=new ShipmentFilterVO();
		shipmentFilterVO.setCompanyCode("LH");
		shipmentFilterVO.setShipmentPrefix("020");
		shipmentFilterVO.setDocumentNumber("14714744");
		doReturn(agtvo).when(operationsShipmentProxy).validateShipmentDetails(any());
       try {
    	   controller.validateSinglePoa(shipmentFilterVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void findShipmentHandlingHistory() throws SystemException,ProxyException{
		Collection<ShipmentHistoryVO> shHisVos=new ArrayList<>();
		ShipmentHistoryFilterVO shipmentHistoryFilterVO=new ShipmentHistoryFilterVO();
		shipmentHistoryFilterVO.setCompanyCode("LH");
		shipmentHistoryFilterVO.setMasterDocumentNumber("020 14714744");
		shipmentHistoryFilterVO.setDocumentOwnerId(0);
		shipmentHistoryFilterVO.setDuplicateNumber(0);
		shipmentHistoryFilterVO.setAirportCode("FRA");
		shipmentHistoryFilterVO.setTransactionCode("ATA");
		doReturn(shHisVos).when(operationsShipmentProxy).findShipmentHandlingHistory(any());
		try {
	    	   controller.findShipmentHandlingHistory(shipmentHistoryFilterVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(expected=SystemException.class)
	public void catchfindShipmentHandlingHistoryTest() throws  SystemException, ProxyException{
		ShipmentHistoryFilterVO shipmentHistoryFilterVO=new ShipmentHistoryFilterVO();
		doThrow(new ProxyException(new SystemException(" Proxy"){
	    })).when(operationsShipmentProxy).findShipmentHandlingHistory(any());
 	   	controller.findShipmentHandlingHistory(shipmentHistoryFilterVO);
	}
	@Test(expected=SystemException.class)
	public void catchTest() throws  SystemException, ProxyException{
		ShipmentFilterVO shipmentFilterVO=new ShipmentFilterVO();
		doThrow(new ProxyException(new SystemException(" Proxy"){
	    })).when(operationsShipmentProxy).validateShipmentDetails(any());
 	   controller.validateShipmentDetails(shipmentFilterVO);
	}
	@Test(expected=SystemException.class)
	public void catchTest2() throws  SystemException, ProxyException{
		ShipmentFilterVO shipmentFilterVO=new ShipmentFilterVO();
		doThrow(new ProxyException(new SystemException(" Proxy"){
	    })).when(sharedCustomerProxy).validateSinglePoa(any());
 	   controller.validateSinglePoa(shipmentFilterVO);
	}
	/**
	 * Test method for CustomerMgmntController.saveBrokerMapping()
	 */
	@Test
	public void shouldSaveBrokerMapping() throws BusinessException, SystemException {
		doReturn("CUSCOD").when(saveBrokerMappingFeature).execute(any(CustomerVO.class));
		String code = controller.saveBrokerMapping(new CustomerVO());
		assertEquals("CUSCOD", code);
	}
	/**
	 * Test method for CustomerMgmntController.saveBrokerMapping()
	 */
	@Test(expected = SystemException.class)
	public void shouldThrowErrorOnSaveBrokerMapping() throws BusinessException, SystemException {
		BusinessException exception = new BusinessException() {};
		doThrow(exception).when(saveBrokerMappingFeature).execute(any(CustomerVO.class));
		controller.saveBrokerMapping(new CustomerVO());
	}
	/**
	 * Test method for CustomerMgmntController.uploadBrokerMappingDocuments()
	 */
	@Test
	public void shouldUploadBrokerMappingDocuments() throws BusinessException, SystemException {
		doReturn(new DocumentRepositoryMasterVO()).when(uploadBrokerMappingDocumentFeature).execute(any(DocumentRepositoryMasterVO.class));
		controller.uploadBrokerMappingDocuments(new DocumentRepositoryMasterVO());
		verify(uploadBrokerMappingDocumentFeature, times(1)).execute(any(DocumentRepositoryMasterVO.class));
	}
	/**
	 * Test method for CustomerMgmntController.uploadBrokerMappingDocuments()
	 */
	@Test(expected = SystemException.class)
	public void shouldThrowErrorOnUploadBrokerMappingDocuments() throws BusinessException, SystemException {
		BusinessException exception = new BusinessException() {};
		doThrow(exception).when(uploadBrokerMappingDocumentFeature).execute(any(DocumentRepositoryMasterVO.class));
		controller.uploadBrokerMappingDocuments(new DocumentRepositoryMasterVO());
	}
	/**
	 * Test method for CustomerMgmntController.interfaceBrokerMappingDocuments()
	 */
	@Test
	public void shouldInterfaceBrokerMappingDocuments() throws BusinessException, SystemException {
		doNothing().when(msgbrokerMessageProxy).encodeAndSaveMessagesAsync(anyCollectionOf(BaseMessageVO.class));
		controller.interfaceBrokerMappingDocuments(Collections.emptyList());
		verify(msgbrokerMessageProxy, times(1)).encodeAndSaveMessagesAsync(anyCollectionOf(BaseMessageVO.class));
	}
	/**
	 * Test method for CustomerMgmntController.interfaceBrokerMappingDocuments()
	 */
	@Test(expected = SystemException.class)
	public void shouldThrowErrorOnInterfaceBrokerMappingDocuments() throws BusinessException, SystemException {
		doThrow(ProxyException.class).when(msgbrokerMessageProxy).encodeAndSaveMessagesAsync(anyCollectionOf(BaseMessageVO.class));
		controller.interfaceBrokerMappingDocuments(Collections.emptyList());
	}
}
