/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.dnata.mail.operations.DNATAMailUploadControllerTest.java
 *
 * 	Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.dnata.mail.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.ForceAcceptanceException;
import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.WarehouseDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.warehouse.defaults.storageunit.vo.StorageUnitValidationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.persistence.dao.xaddons.dnata.mail.operations.DNATAMailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

public class DNATAMailUploadControllerTest extends AbstractFeatureTest {

	public static final String STORAGEUNIT_SYSTEM_PARAM_KEY = "mail.operations.storageunitintegrationneeded";
	public static final String INVALID_STORAGEUNIT = "ABCD";
	public static final String MAILBAGID = "FRCDGADEFRAAACA21011111010020";
	public static final String DNATAMAIL_OPRNS = "dnatamail.operations";

	private DNATAMailUploadController dnataMailUploadControllerSpy;
	private MailUploadVO mailUploadVO;
	private ScannedMailDetailsVO scannedMailDetailsVO;
	private WarehouseDefaultsProxy warehouseDefaultsProxy;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private LogonAttributes logonAttributes;
	private DNATAMailTrackingDefaultsDAO dao;
	private FlightValidationVO flightValidationVO;

	@Override
	public void setup() throws Exception {
		dnataMailUploadControllerSpy = spy(new DNATAMailUploadController());
		warehouseDefaultsProxy = mockProxy(WarehouseDefaultsProxy.class);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		dao = mock(DNATAMailTrackingDefaultsDAO.class);

		EntityManagerMock.mockEntityManager();

		mailUploadVO = new MailUploadVO();
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		flightValidationVO = new FlightValidationVO();
		logonAttributes = new LogonAttributes();
	}

	@Test
	public void shouldNotThrowExceptionOnSecificMailbagValidationForAndroid() throws ProxyException, SystemException, MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		mailUploadVO.setStorageUnit("STRUNT");
		Map<String, String> systemParamMap = new HashMap<>();
		systemParamMap.put(STORAGEUNIT_SYSTEM_PARAM_KEY, "Y");
		StorageUnitValidationVO validationVO = new StorageUnitValidationVO();
		doReturn(systemParamMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(validationVO).when(warehouseDefaultsProxy).validateStorageUnit(any(String.class), any(String.class),
				any(String.class));
		dnataMailUploadControllerSpy.specificMailbagValidationForAndroid(mailUploadVO);
		assertThat(validationVO, is(IsNull.notNullValue()));
	}

	@Test
	public void OnSecificMailbagValidationForAndroidWhenSystemParamIsNo() throws SystemException, MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		mailUploadVO.setStorageUnit("STRUNT");
		Map<String, String> systemParamMap = new HashMap<>();
		systemParamMap.put(STORAGEUNIT_SYSTEM_PARAM_KEY, "N");
		StorageUnitValidationVO validationVO = new StorageUnitValidationVO();
		doReturn(systemParamMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(validationVO).when(warehouseDefaultsProxy).validateStorageUnit(any(String.class), any(String.class),
				any(String.class));
		dnataMailUploadControllerSpy.specificMailbagValidationForAndroid(mailUploadVO);
		assertThat(validationVO, is(IsNull.notNullValue()));
	}

	@Test
	public void shouldThrowExceptionOnSecificMailbagValidationForAndroidWhenStorageUnitNull()
			throws SystemException, MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		mailUploadVO.setStorageUnit(null);
		Map<String, String> systemParamMap = new HashMap<>();
		systemParamMap.put(STORAGEUNIT_SYSTEM_PARAM_KEY, "Y");
		StorageUnitValidationVO validationVO = new StorageUnitValidationVO();
		doReturn(systemParamMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(validationVO).when(warehouseDefaultsProxy).validateStorageUnit(any(String.class), any(String.class),
				any(String.class));
		dnataMailUploadControllerSpy.specificMailbagValidationForAndroid(mailUploadVO);
		verify(sharedDefaultsProxy, times(0)).findSystemParameterByCodes(anyCollectionOf(String.class));
	}

	@Test
	public void OnSecificMailbagValidationForAndroidWhenStorageUnitIsEmpty()
			throws  SystemException, MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		mailUploadVO.setStorageUnit("");
		Map<String, String> systemParamMap = new HashMap<>();
		systemParamMap.put(STORAGEUNIT_SYSTEM_PARAM_KEY, "Y");
		StorageUnitValidationVO validationVO = new StorageUnitValidationVO();
		doReturn(systemParamMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(validationVO).when(warehouseDefaultsProxy).validateStorageUnit(any(String.class), any(String.class),
				any(String.class));
		dnataMailUploadControllerSpy.specificMailbagValidationForAndroid(mailUploadVO);
		verify(sharedDefaultsProxy, times(0)).findSystemParameterByCodes(anyCollectionOf(String.class));
	}

	@Test
	public void shouldThrowExceptionOnSecificMailbagValidationForAndroidWhenStorageUnit()
			throws SystemException, MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		mailUploadVO.setStorageUnit("TRUNT");
		StorageUnitValidationVO validationVO = new StorageUnitValidationVO();
		doReturn(null).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(validationVO).when(warehouseDefaultsProxy).validateStorageUnit(any(String.class), any(String.class),
				any(String.class));
		dnataMailUploadControllerSpy.specificMailbagValidationForAndroid(mailUploadVO);
		verify(sharedDefaultsProxy, times(1)).findSystemParameterByCodes(anyCollectionOf(String.class));
	}

	@Test(expected=MailHHTBusniessException.class)
	public void shouldThrowExceptionOnSecificMailbagValidationForAndroidWhenStorageVONull()
			throws SystemException,MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		mailUploadVO.setStorageUnit("TRUNT");
		Map<String, String> systemParamMap = new HashMap<>();
		systemParamMap.put(STORAGEUNIT_SYSTEM_PARAM_KEY, "Y");
		StorageUnitValidationVO validationVO = null;
		doReturn(systemParamMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(validationVO).when(warehouseDefaultsProxy).validateStorageUnit(any(String.class), any(String.class),
				any(String.class));
		dnataMailUploadControllerSpy.specificMailbagValidationForAndroid(mailUploadVO);
	}

	@Test
	public void shouldNotThrowExceptionOndoMailbagDeliveryOnstoargeUnitScan()
			throws SystemException, MailHHTBusniessException, MailMLDBusniessException, PersistenceException,ForceAcceptanceException {
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		logonAttributes = new LogonAttributes();
		logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVo = new MailbagVO();
		mailbagVo.setFlightNumber("5526");
		mailbagVo.setFromFlightSequenceNumber(235);
		mailbagVo.setCarrierCode("EK");
		mailbagVOs.add(mailbagVo);
		scannedMailDetailsVO.setStorageUnit("ULD");
		dao = mock(DNATAMailTrackingDefaultsDAO.class);
		doNothing().when(dnataMailUploadControllerSpy).saveDeliverFromUpload(any(ScannedMailDetailsVO.class),
				any(LogonAttributes.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(DNATAMAIL_OPRNS);
		doReturn(mailbagVOs).when(dao).findMailbagsInStorageUnit(any(String.class));
		doReturn(flightValidationVO).when(dnataMailUploadControllerSpy).validateFlight(any(MailbagVO.class));
		dnataMailUploadControllerSpy.doMailbagDeliveryOnstoargeUnitScan(scannedMailDetailsVO, logonAttributes);
		verify(dnataMailUploadControllerSpy, times(1)).saveDeliverFromUpload(any(ScannedMailDetailsVO.class),
				any(LogonAttributes.class));
	}

	@Test
	public void doMailbagDeliveryOnstoargeUnitScanwhenMailbagVoisNull()
			throws SystemException, MailHHTBusniessException, MailMLDBusniessException, PersistenceException,ForceAcceptanceException {
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		logonAttributes = new LogonAttributes();
		logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVo = new MailbagVO();
		mailbagVo.setFlightNumber("5526");
		mailbagVo.setFromFlightSequenceNumber(235);
		mailbagVo.setCarrierCode("EK");
		mailbagVOs.add(mailbagVo);
		Map<String, FlightValidationVO> flightDetailMap = new HashMap<>();
		flightDetailMap.put("flightDetailKey", flightValidationVO);
		scannedMailDetailsVO.setStorageUnit("ULD");
		dao = mock(DNATAMailTrackingDefaultsDAO.class);
		doNothing().when(dnataMailUploadControllerSpy).saveDeliverFromUpload(any(ScannedMailDetailsVO.class),
				any(LogonAttributes.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(DNATAMAIL_OPRNS);
		doReturn(null).when(dao).findMailbagsInStorageUnit(any(String.class));
		doReturn(flightValidationVO).when(dnataMailUploadControllerSpy).validateFlight(any(MailbagVO.class));
		dnataMailUploadControllerSpy.doMailbagDeliveryOnstoargeUnitScan(scannedMailDetailsVO, logonAttributes);
		verify(dnataMailUploadControllerSpy, times(0)).saveDeliverFromUpload(any(ScannedMailDetailsVO.class),
				any(LogonAttributes.class));
	}

	@Test
	public void doMailbagDeliveryOnstoargeUnitScanMultipleMAilbagVOs()
			throws SystemException, MailHHTBusniessException, MailMLDBusniessException, PersistenceException,ForceAcceptanceException {
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		logonAttributes = new LogonAttributes();
		logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVo = new MailbagVO();
		mailbagVo.setFlightNumber("5526");
		mailbagVo.setFromFlightSequenceNumber(235);
		mailbagVo.setCarrierCode("EK");
		mailbagVOs.add(mailbagVo);
		MailbagVO mailbagVo1 = new MailbagVO();
		mailbagVo1.setFlightNumber("5526");
		mailbagVo1.setFromFlightSequenceNumber(235);
		mailbagVo1.setCarrierCode("EK");
		mailbagVOs.add(mailbagVo1);
		scannedMailDetailsVO.setStorageUnit("ULD");
		dao = mock(DNATAMailTrackingDefaultsDAO.class);
		doNothing().when(dnataMailUploadControllerSpy).saveDeliverFromUpload(any(ScannedMailDetailsVO.class),
				any(LogonAttributes.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(DNATAMAIL_OPRNS);
		doReturn(mailbagVOs).when(dao).findMailbagsInStorageUnit(any(String.class));
		doReturn(flightValidationVO).when(dnataMailUploadControllerSpy).validateFlight(any(MailbagVO.class));
		dnataMailUploadControllerSpy.doMailbagDeliveryOnstoargeUnitScan(scannedMailDetailsVO, logonAttributes);
		verify(dnataMailUploadControllerSpy, times(1)).saveDeliverFromUpload(any(ScannedMailDetailsVO.class),
				any(LogonAttributes.class));
	}

}
