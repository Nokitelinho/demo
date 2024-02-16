package com.ibsplc.icargo.business.reco.defaults;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.spy;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

import org.junit.Test;


import com.ibsplc.icargo.business.reco.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.reco.defaults.proxy.SharedMasterGroupingProxy;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.area.station.vo.StationVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.persistence.dao.reco.defaults.EmbargoRulesDAO;
import com.ibsplc.icargo.persistence.dao.reco.defaults.EmbargoRulesSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;

public class EmbargoRulesControllerTest extends AbstractFeatureTest {

	private EmbargoRulesController embargoRulesControllerSpy;
	
	private SharedMasterGroupingProxy sharedMasterGroupingProxy;
	private SharedAreaProxy areaProxy;
	private EmbargoRulesSqlDAO embargoRulesSqlDAO;
	
	@Override
	public void setup() throws Exception {
		embargoRulesControllerSpy = spy(EmbargoRulesController.class);
		sharedMasterGroupingProxy= mockProxy(SharedMasterGroupingProxy.class);
		areaProxy= mockProxy(SharedAreaProxy.class);
		embargoRulesSqlDAO = mock(EmbargoRulesSqlDAO.class);
		EntityManagerMock.mockEntityManager();
	}
	
	
	public void populateGroupShipmentVos() {
	Collection<ShipmentDetailsVO> shipmentDetailsVO =new ArrayList<ShipmentDetailsVO>();
	ShipmentDetailsVO shipmentDetailVO = new ShipmentDetailsVO();
	shipmentDetailVO.setOrgStation("AUH");
	shipmentDetailVO.setDstStation("FRA");
	shipmentDetailsVO.add(shipmentDetailVO);
	}
	
//	private String getCommaSeparatedGroupNames(Collection<GeneralMasterGroupVO> generalMasterGroupVOs){
//			
//		GeneralMasterGroupVO vo = new GeneralMasterGroupVO();
//		vo.setGroupName("SGLIST");
//		StringBuilder commaSeparatedGroups = new StringBuilder(vo.getGroupName());
//		
//		return commaSeparatedGroups.toString();
//	}
	@Test
	public void shouldCheckForEmbargo() throws SystemException, EmbargoRulesBusinessException, ProxyException, PersistenceException {
		
	Map<String, Collection<String>> detailsMap = new HashMap<String, Collection<String>>();
	Collection<String> shipper = new ArrayList<>();
	Collection<String> consignee = new ArrayList<>();
	shipper.add("C1001");
	consignee.add("C1001");
	detailsMap.put("SHIPPER", shipper);
	detailsMap.put("CONSIGNEE", consignee);
	
	Collection<EmbargoDetailsVO> detailsVOs = new ArrayList<EmbargoDetailsVO>();
	Collection<ShipmentDetailsVO> shipmentVOs = new ArrayList<ShipmentDetailsVO>();
	ShipmentDetailsVO shipmentDetailVO = new ShipmentDetailsVO();
	shipmentDetailVO.setCompanyCode("EY");
	//shipmentDetailVO.setOrgStation("");
	shipmentDetailVO.setMap(detailsMap);
	shipmentDetailVO.setDstStation("JNB");
	shipmentVOs.add(shipmentDetailVO);
	populateGroupShipmentVos();
	
	Collection<String> stationCodes = new HashSet<String>();
	stationCodes.add(shipmentDetailVO.getDstStation());
	GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();
	Collection<GeneralMasterGroupVO> generalMasterGroupVOList = null;
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("ARPGRP");
	generalMasterGroupFilterVO.setGroupEntity("JNB");
	
	doReturn(null).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(any());
	shipmentDetailVO.setDstArpGrp(doReturn("SGLIST").when(embargoRulesControllerSpy).getCommaSeparatedGroupNames(any(Collection.class)));//SGLIST
	shipmentDetailVO.setEnhancedChecks(false);
	HashMap<String,String> typeMap = new HashMap();
	typeMap.put("JNB-ARPGRP","SGLIST");
	shipmentDetailVO.setTypeMap(typeMap);
	Collection<String> shipperCodes = shipmentDetailVO.getMap().get("SHIPPER");
	
	Collection<String> shipperGroups = new ArrayList<>();
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("SHPGRP");
	generalMasterGroupFilterVO.setGroupEntity("C1001");
	doReturn(generalMasterGroupVOList).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(any());
	Collection<String> consigneeCodes = shipmentDetailVO.getMap().get("CONSIGNEE");
	
	Collection<String> consigneeGroups = new ArrayList<>();
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("CNSGRP");
	generalMasterGroupFilterVO.setGroupEntity("C1001");
	doReturn(generalMasterGroupVOList).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(any());
	Map<String, StationVO> stationValidation = new HashMap<String, StationVO>();
	StationVO destVo = new StationVO();
	StationVO orgVo = new StationVO();
	doReturn(stationValidation).when(areaProxy).validateStationCodes(shipmentDetailVO.getCompanyCode(), stationCodes);
	Collection<StationVO> viaPointVOs = new ArrayList<StationVO>();
	StationVO viaPointVo = null;
	destVo.setCountryCode("JNB");
	shipmentDetailVO.setDstCountry(destVo.getCountryCode());
	typeMap.put("JNB-CNT", "ZA");
	shipmentDetailVO.setTypeMap(typeMap);
	
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("CNTGRP");
	generalMasterGroupFilterVO.setGroupEntity("ZA");
	doReturn(generalMasterGroupVOList).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(generalMasterGroupFilterVO);
	shipmentDetailVO.setDstCntGrp(null);
	shipmentDetailVO.setUserLocale("en_US");
	
	doReturn(embargoRulesSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("reco.defaults");
	embargoRulesControllerSpy.checkForEmbargo(shipmentVOs);
	}
	
	@Test
	public void shouldCheckForEmbargoEmpty() throws SystemException, EmbargoRulesBusinessException, ProxyException, PersistenceException {
		
	Map<String, Collection<String>> detailsMap = new HashMap<String, Collection<String>>();
	Collection<String> shipper = new ArrayList<>();
	Collection<String> consignee = new ArrayList<>();
	shipper.add("C1001");
	consignee.add("C1001");
	detailsMap.put("SHIPPER", shipper);
	detailsMap.put("CONSIGNEE", consignee);
	
	Collection<EmbargoDetailsVO> detailsVOs = new ArrayList<EmbargoDetailsVO>();
	Collection<ShipmentDetailsVO> shipmentVOs = new ArrayList<ShipmentDetailsVO>();
	ShipmentDetailsVO shipmentDetailVO = new ShipmentDetailsVO();
	shipmentDetailVO.setCompanyCode("EY");
	//shipmentDetailVO.setOrgStation("");
	shipmentDetailVO.setMap(detailsMap);
	shipmentDetailVO.setDstStation("JNB");
	shipmentVOs.add(shipmentDetailVO);
	populateGroupShipmentVos();
	
	Collection<String> stationCodes = new HashSet<String>();
	stationCodes.add(shipmentDetailVO.getDstStation());
	GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();
	Collection<GeneralMasterGroupVO> generalMasterGroupVOList = null;
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("ARPGRP");
	generalMasterGroupFilterVO.setGroupEntity("JNB");
	
	doReturn(null).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(any());
	shipmentDetailVO.setDstArpGrp(doReturn("SGLIST").when(embargoRulesControllerSpy).getCommaSeparatedGroupNames(any(Collection.class)));//SGLIST
	shipmentDetailVO.setEnhancedChecks(false);
	HashMap<String,String> typeMap = new HashMap();
	typeMap.put("JNB-ARPGRP","SGLIST");
	shipmentDetailVO.setTypeMap(typeMap);
	Collection<String> shipperCodes = shipmentDetailVO.getMap().get("SHIPPER");
	
	Collection<String> shipperGroups = new ArrayList<>();
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("SHPGRP");
	generalMasterGroupFilterVO.setGroupEntity("C1001");
	doReturn(generalMasterGroupVOList).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(any());
	Collection<String> consigneeCodes = shipmentDetailVO.getMap().get("CONSIGNEE");
	
	Collection<String> consigneeGroups = new ArrayList<>();
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("CNSGRP");
	generalMasterGroupFilterVO.setGroupEntity("C1001");
	doReturn(generalMasterGroupVOList).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(any());
	Map<String, StationVO> stationValidation = new HashMap<String, StationVO>();
	StationVO destVo = new StationVO();
	StationVO orgVo = new StationVO();
	doReturn(stationValidation).when(areaProxy).validateStationCodes(shipmentDetailVO.getCompanyCode(), stationCodes);
	Collection<StationVO> viaPointVOs = new ArrayList<StationVO>();
	StationVO viaPointVo = null;
	destVo.setCountryCode("JNB");
	shipmentDetailVO.setDstCountry(destVo.getCountryCode());
	typeMap.put("JNB-CNT", "ZA");
	shipmentDetailVO.setTypeMap(typeMap);
	
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("CNTGRP");
	generalMasterGroupFilterVO.setGroupEntity("ZA");
	doReturn(generalMasterGroupVOList).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(generalMasterGroupFilterVO);
	shipmentDetailVO.setDstCntGrp(null);
	shipmentDetailVO.setUserLocale("en_US");
	shipmentDetailVO.setUnknownShipper("");
	doReturn(embargoRulesSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("reco.defaults");
	embargoRulesControllerSpy.checkForEmbargo(shipmentVOs);
	}
	@Test
	public void shouldCheckForEmbargoNotEmpty() throws SystemException, EmbargoRulesBusinessException, ProxyException, PersistenceException {
		
	Map<String, Collection<String>> detailsMap = new HashMap<String, Collection<String>>();
	Collection<String> shipper = new ArrayList<>();
	Collection<String> consignee = new ArrayList<>();
	shipper.add("C1001");
	consignee.add("C1001");
	detailsMap.put("SHIPPER", shipper);
	detailsMap.put("CONSIGNEE", consignee);
	
	Collection<EmbargoDetailsVO> detailsVOs = new ArrayList<EmbargoDetailsVO>();
	Collection<ShipmentDetailsVO> shipmentVOs = new ArrayList<ShipmentDetailsVO>();
	ShipmentDetailsVO shipmentDetailVO = new ShipmentDetailsVO();
	shipmentDetailVO.setCompanyCode("EY");
	//shipmentDetailVO.setOrgStation("");
	shipmentDetailVO.setMap(detailsMap);
	shipmentDetailVO.setDstStation("JNB");
	shipmentVOs.add(shipmentDetailVO);
	populateGroupShipmentVos();
	
	Collection<String> stationCodes = new HashSet<String>();
	stationCodes.add(shipmentDetailVO.getDstStation());
	GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();
	Collection<GeneralMasterGroupVO> generalMasterGroupVOList = null;
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("ARPGRP");
	generalMasterGroupFilterVO.setGroupEntity("JNB");
	
	doReturn(null).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(any());
	shipmentDetailVO.setDstArpGrp(doReturn("SGLIST").when(embargoRulesControllerSpy).getCommaSeparatedGroupNames(any(Collection.class)));//SGLIST
	shipmentDetailVO.setEnhancedChecks(false);
	HashMap<String,String> typeMap = new HashMap();
	typeMap.put("JNB-ARPGRP","SGLIST");
	shipmentDetailVO.setTypeMap(typeMap);
	Collection<String> shipperCodes = shipmentDetailVO.getMap().get("SHIPPER");
	
	Collection<String> shipperGroups = new ArrayList<>();
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("SHPGRP");
	generalMasterGroupFilterVO.setGroupEntity("C1001");
	doReturn(generalMasterGroupVOList).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(any());
	Collection<String> consigneeCodes = shipmentDetailVO.getMap().get("CONSIGNEE");
	
	Collection<String> consigneeGroups = new ArrayList<>();
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("CNSGRP");
	generalMasterGroupFilterVO.setGroupEntity("C1001");
	doReturn(generalMasterGroupVOList).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(any());
	Map<String, StationVO> stationValidation = new HashMap<String, StationVO>();
	StationVO destVo = new StationVO();
	StationVO orgVo = new StationVO();
	doReturn(stationValidation).when(areaProxy).validateStationCodes(shipmentDetailVO.getCompanyCode(), stationCodes);
	Collection<StationVO> viaPointVOs = new ArrayList<StationVO>();
	StationVO viaPointVo = null;
	destVo.setCountryCode("JNB");
	shipmentDetailVO.setDstCountry(destVo.getCountryCode());
	typeMap.put("JNB-CNT", "ZA");
	shipmentDetailVO.setTypeMap(typeMap);
	
	generalMasterGroupFilterVO.setCompanyCode("EY");
	generalMasterGroupFilterVO.setGroupCategory("EMB");
	generalMasterGroupFilterVO.setGroupType("CNTGRP");
	generalMasterGroupFilterVO.setGroupEntity("ZA");
	doReturn(generalMasterGroupVOList).when(sharedMasterGroupingProxy).findGroupNamesofGroupEntity(generalMasterGroupFilterVO);
	shipmentDetailVO.setDstCntGrp(null);
	shipmentDetailVO.setUserLocale("en_US");
	shipmentDetailVO.setUnknownShipper("ABC");
	doReturn(embargoRulesSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("reco.defaults");
	embargoRulesControllerSpy.checkForEmbargo(shipmentVOs);
	}
}
