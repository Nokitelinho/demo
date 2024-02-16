package com.ibsplc.icargo.business.master.defaults.orchestration;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.admin.user.vo.UserEnquiryFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.master.defaults.proxy.ProductsDefaultsProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedAgentProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedCommodityProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedCustomerProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedGeneralMasterGroupingProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedSCCProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.TariffOthersProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.UserProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.WarehouseDefaultsProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageMIPErrorVO;
import com.ibsplc.icargo.business.master.defaults.proxy.WarehouseProxy;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentFilterVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportFilterVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.commodity.CommodityBusinessException;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityFilterVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParameterConfigurationVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupDetailsVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.scc.SCCBusinessException;
import com.ibsplc.icargo.business.shared.scc.vo.SCCLovFilterVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCVO;
import com.ibsplc.icargo.business.tariff.others.vo.ChargeHeadLovFilterVO;
import com.ibsplc.icargo.business.tariff.others.vo.ChargeHeadLovVO;
import com.ibsplc.icargo.business.tariff.others.vo.ServiceLovFilterVO;
import com.ibsplc.icargo.business.tariff.others.vo.ServiceLovVO;
import com.ibsplc.icargo.business.warehouse.defaults.handlingarea.vo.HandlingAreaFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.handlingarea.vo.HandlingAreaTypeFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.handlingarea.vo.HandlingAreaTypeVO;
import com.ibsplc.icargo.business.warehouse.defaults.handlingarea.vo.HandlingAreaVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.WarehouseLocationFilterLovVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.WarehouseLocationLovVO;
import com.ibsplc.icargo.business.warehouse.defaults.storagestructure.vo.StorageStructureFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.storagestructure.vo.StorageStructureVO;
import com.ibsplc.icargo.business.warehouse.defaults.zone.vo.ZoneFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.zone.vo.ZoneVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.suggest.vo.SuggestRequestVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestResponseVO;

public class MasterTypeOrchestrator {
	private Log log = LogFactory.getLogger("MASTERS");
	private static final String FILTERTYPE_ZONE = "ZONE";
	
	private static final String WRKFLOWMSG_GRP_CAT= "LOCCODGRP";
	private static final String GEN_STRING = "GEN";
	private static final String CITY_CODE_MASTER= "City Code Master";
	
	public List<SuggestResponseVO> despatch(SuggestRequestVO requestVO) throws SystemException, RemoteException, ServiceNotAccessibleException, SCCBusinessException, ProxyException{
		log.entering("MasterTypeOrchestrator", "despatch");
		List<SuggestResponseVO> suggestList=new ArrayList<SuggestResponseVO>();
		Set<String> responseSet= new HashSet<>();
		if(MasterTypesConfig.AIRPORTMASTER.equalsIgnoreCase(requestVO.getMasterType())){
			AirportFilterVO filterVO=new AirportFilterVO();
			filterVO.setMappingVO(requestVO);
			List<AirportVO> xList=new SharedAreaProxy().findAirports(filterVO);
			for(AirportVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
			}
	
		} else if (MasterTypesConfig.PRODCUCTMASTER.equalsIgnoreCase(requestVO.getMasterType())) {
			
			ProductLovFilterVO filterVO=new ProductLovFilterVO();
			filterVO.setMappingVO(requestVO);
			Collection<ProductLovVO> xList=null;
			try {
				xList = new ProductsDefaultsProxy().findProductForMaster(filterVO);
			} catch (ProxyException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			for(ProductLovVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
			}
		}else if (MasterTypesConfig.PRDCOD.equalsIgnoreCase(requestVO.getMasterType())) {
			ProductFilterVO productFilterVO = new ProductFilterVO();
			productFilterVO.setMappingVO(requestVO);
			Collection<ProductVO> productList = new ProductsDefaultsProxy().findProducts(productFilterVO);
			if (productList != null) {
				for (ProductVO productVO : productList) {
					suggestList.add(productVO.getMappedVO());
				}
			}
		}
		else if(MasterTypesConfig.COMMODITYMASTER.equalsIgnoreCase(requestVO.getMasterType())){
			CommodityFilterVO filterVO=new CommodityFilterVO();
			filterVO.setMappingVO(requestVO);
			 Collection<CommodityValidationVO> xList=null;
			 try {
				xList=new SharedCommodityProxy().getAllCommoditiesForMaster(filterVO);
			} catch (ProxyException e) {
				// TODO Auto-generated catch block
				log.entering("ProxyException","getAllCommodities");
			} catch (CommodityBusinessException e) {
				// TODO Auto-generated catch block
				log.entering("CommodityBusinessException","getAllCommodities");
			}
			for(CommodityValidationVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
			}
		}else if(MasterTypesConfig.SCCMASTER.equalsIgnoreCase(requestVO.getMasterType())){
			SCCLovFilterVO filterVO=new SCCLovFilterVO();
			filterVO.setMappingVO(requestVO);
			Collection<SCCVO> xList=null;
			try {
				xList=new SharedSCCProxy().findSCCsMaster(filterVO);
			} catch (ProxyException e) {
				// TODO Auto-generated catch block
				log.entering("ProxyException","findSCCs");
			}
			for(SCCVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
			}

		} else if(MasterTypesConfig.SCCCOD.equalsIgnoreCase(requestVO.getMasterType())){
			LogonAttributes logonAttributes =  ContextUtils.getSecurityContext().getLogonAttributesVO();
			SCCLovFilterVO sCCLovFilterVO = new SCCLovFilterVO();
			sCCLovFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			sCCLovFilterVO.setMappingVO(requestVO);
			Collection<SCCVO> sccList = new SharedSCCProxy().findSCCDetails(sCCLovFilterVO);
			if (sccList != null) {
				for (SCCVO sCCVO : sccList) {
					suggestList.add(sCCVO.getMappedVO());
				}
			}
		} 
		else if(MasterTypesConfig.SRCLOCCOD.equalsIgnoreCase(requestVO.getMasterType())){
			WarehouseLocationFilterLovVO warehouseLocationFilterLovVO =  new WarehouseLocationFilterLovVO();
			warehouseLocationFilterLovVO.setMappingVO(requestVO);
			Collection<WarehouseLocationLovVO>  xList=null;
			try {
				xList = new  WarehouseProxy().
						findWarehouseLocationsToSuggest(warehouseLocationFilterLovVO);
			} catch (ProxyException e) {
				log.entering("ProxyException","findWarehouseLocationsToSuggest");
			}
	
			if(xList!=null){
			for(WarehouseLocationLovVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
				}
			}
		}
		
		else if (MasterTypesConfig.ZONECOD.equalsIgnoreCase(requestVO.getMasterType())) {
			LogonAttributes logonAttributes =  ContextUtils.getSecurityContext().getLogonAttributesVO();
			ZoneFilterVO zoneFilterVO = new ZoneFilterVO();
			zoneFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			zoneFilterVO.setMappingVO(requestVO);
			Collection<ZoneVO> zoneList = new WarehouseProxy().findZoneCodeLov(zoneFilterVO);
			if (zoneList != null) {
				for (ZoneVO zoneVO : zoneList) {
					suggestList.add(zoneVO.getMappedVO());
				}
			}
		}
		else if (MasterTypesConfig.STORAGEUNITCODE.equalsIgnoreCase(requestVO.getMasterType())) {
			LogonAttributes logonAttributes =  ContextUtils.getSecurityContext().getLogonAttributesVO();
			StorageStructureFilterVO storageStructureFilterVO = new StorageStructureFilterVO();
			storageStructureFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			storageStructureFilterVO.setAirportCode(logonAttributes.getAirportCode());
			storageStructureFilterVO.setWarehouseCode(logonAttributes.getDefaultWarehouseCode());
			storageStructureFilterVO.setMappingVO(requestVO);
			StorageStructureVO storageList =new WarehouseProxy().findStorageStructureDetails(storageStructureFilterVO);
			if (storageList != null) {
				StorageStructureVO storageStructureVO = new StorageStructureVO();
				storageStructureVO=storageList;
					suggestList.add(storageStructureVO.getMappedVO());
				}
			}
		else if("OTHERCHARGE".equalsIgnoreCase(requestVO.getMasterType())){/*Added by A_5782 for ICRD-124172 starts*/
			ChargeHeadLovFilterVO filterVO=new ChargeHeadLovFilterVO();
			filterVO.setMappingVO(requestVO);
			Collection<ChargeHeadLovVO> xList=null;
			try {
				xList = new TariffOthersProxy().findAllChargeHeads(filterVO);
			} catch (ProxyException e) {
				log.entering("ProxyException","findAllChargeHeads");
			}
			if(xList!=null){
			for(ChargeHeadLovVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
			}
			}
		}else if("AGENT".equalsIgnoreCase(requestVO.getMasterType())){
			AgentFilterVO filterVO=new AgentFilterVO();
			filterVO.setMappingVO(requestVO);
			List<AgentVO> xList=new SharedAgentProxy().findAgentMasters(filterVO);
			for(AgentVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
			}
		}else if("CUSTOMER".equalsIgnoreCase(requestVO.getMasterType())){
			CustomerFilterVO filterVO=new CustomerFilterVO();
			filterVO.setMappingVO(requestVO);
			Collection<CustomerVO> xList=new SharedCustomerProxy().findCustomerMasters(filterVO);
			for(CustomerVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
			}/*Added by A_5782 for ICRD-124172 ends*/
		}
		else if("SERVICECODE".equalsIgnoreCase(requestVO.getMasterType())){
			ServiceLovFilterVO serviceLovFilterVO=new ServiceLovFilterVO();
			serviceLovFilterVO.setMappingVO(requestVO);
			Collection<ServiceLovVO> xList=null;
			try {
				xList = new TariffOthersProxy().findAllServices(serviceLovFilterVO);
			} catch (ProxyException e) {
				log.entering("ProxyException","findAllServices");
			}
			if(xList!=null){
			for(ServiceLovVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
				}
			}
		}
		else if(MasterTypesConfig.HANDLINGAREAMASTER.equalsIgnoreCase(requestVO.getMasterType())){
			HandlingAreaFilterVO handlingAreaFilterVO =  new HandlingAreaFilterVO();
			handlingAreaFilterVO.setMappingVO(requestVO);
			Collection<HandlingAreaVO>  xList=null;
			try {
				xList = new  WarehouseProxy().findHandlingAreas(handlingAreaFilterVO);
			} catch (ProxyException e) {
				log.entering("ProxyException","findHandlingAreas");
			}
	
			if(xList!=null){
			for(HandlingAreaVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
				}
			}
		}
		else if(MasterTypesConfig.HANDLINGAREAFORTHEUSER.equalsIgnoreCase(requestVO.getMasterType())){
			//Else condition added by E-1289 for ICRD-2254
			LogonAttributes logonAttributes =  ContextUtils.getSecurityContext().getLogonAttributesVO();
			HandlingAreaFilterVO handlingAreaFilterVO =  new HandlingAreaFilterVO();
			handlingAreaFilterVO.setMappingVO(requestVO);
			Map<String, String> filterMap= new HashMap<String, String>();
			filterMap = requestVO.getCustomFilter();
			Collection<HandlingAreaVO>  xList=null;
			try {
				xList = new  WarehouseProxy().findHandlingAreas(handlingAreaFilterVO);
			} catch (ProxyException e) {
				e.getMessage();
			}
			if(xList!=null){
				if(filterMap!= null && filterMap.get(MasterTypesConfig.USERID)!=null) {
					try {
						logonAttributes = new UserProxy().getUserParametersValueMap(logonAttributes.getCompanyCode(), filterMap.get(MasterTypesConfig.USERID).toUpperCase()) ;
					}catch (ProxyException e) {
						e.getMessage();
					}
				}else {    
					try {
						logonAttributes = new UserProxy().getUserParametersValueMap(logonAttributes.getCompanyCode(), logonAttributes.getUserId()) ;
					}catch (ProxyException e) {
						e.getMessage();
					}	
				}
				if(logonAttributes.getUserParameterMap() != null && logonAttributes.getUserParameterMap().get(MasterTypesConfig.HACONFIGUREDFORUSER)!=null){
					String handlingAreas = logonAttributes.getUserParameterMap().get(MasterTypesConfig.HACONFIGUREDFORUSER);
					String[] HACodes = handlingAreas.split(",");
					if (HACodes!= null && HACodes.length >0){
							for(HandlingAreaVO tmpVo:xList){
							if(Arrays.asList(HACodes).contains(tmpVo.getHandlingAreaCode())){
									suggestList.add(tmpVo.getMappedVO());
								}
						}

					}

				}	else if(xList!=null){
					for(HandlingAreaVO tmpVo:xList){
						suggestList.add(tmpVo.getMappedVO());
					}
				}
			}
		}
		else if(MasterTypesConfig.HANDLINGAREAUSERSMASTER.equalsIgnoreCase(requestVO.getMasterType())){
			UserEnquiryFilterVO userEnquiryFilterVO = new UserEnquiryFilterVO();
			userEnquiryFilterVO.setMappingVO(requestVO);
			List<UserVO> xList = null;
			try {
				xList =new  UserProxy().findAllHandlingAreaUsers(userEnquiryFilterVO);
			}catch (ProxyException e) {
				log.entering("ProxyException","findAllHandlingAreaUsers");
			}
			if(xList!=null){
			for(UserVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
				}
			}
		}
		else if(MasterTypesConfig.LOCATION.equalsIgnoreCase(requestVO.getMasterType())){
			WarehouseLocationFilterLovVO warehouseLocationFilterLovVO =  new WarehouseLocationFilterLovVO();
			warehouseLocationFilterLovVO.setMappingVO(requestVO);
			Collection<WarehouseLocationLovVO>  xList=null;
			try {
				xList = new  WarehouseProxy().
						findWarehouseLocationsToSuggest(warehouseLocationFilterLovVO);
			} catch (ProxyException e) {
				log.entering("ProxyException","findWarehouseLocationsToSuggest");
			}
	
			if(xList!=null){
			for(WarehouseLocationLovVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
				}
			}
		}
		else if(MasterTypesConfig.BUILDUPLOCATION.equalsIgnoreCase(requestVO.getMasterType())){
			WarehouseLocationFilterLovVO warehouseLocationFilterLovVO =  new WarehouseLocationFilterLovVO();
			warehouseLocationFilterLovVO.setBuildupLocationsOnly(true);
			warehouseLocationFilterLovVO.setMappingVO(requestVO);
			Collection<WarehouseLocationLovVO>  xList=null;
			try {
				xList = new  WarehouseProxy().
						findWarehouseLocationsToSuggest(warehouseLocationFilterLovVO);
			} catch (ProxyException e) {
				log.entering("ProxyException","findWarehouseLocationsToSuggest");
			}
	
			if(xList!=null){
			for(WarehouseLocationLovVO tmpVo:xList){
				suggestList.add(tmpVo.getMappedVO());
				}
			}
		}else if(MasterTypesConfig.RAMPLOCATION.equalsIgnoreCase(requestVO.getMasterType())){
			Collection<LocationVO> locations = null;
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			ZoneFilterVO zoneFilterVO = new ZoneFilterVO();
			String zoneCode = null;
			Collection<String> zoneCodes = new ArrayList<String>();
			
			zoneFilterVO.setMappingVO(requestVO);
			zoneFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			zoneFilterVO.setAirportCode(logonAttributes.getAirportCode());
			zoneFilterVO.setWarehouseCode(logonAttributes.getDefaultWarehouseCode());
			if(requestVO.getCustomFilter() != null 
					&& requestVO.getCustomFilter().get(FILTERTYPE_ZONE) != null 
					&& requestVO.getCustomFilter().get(FILTERTYPE_ZONE).trim().length() > 0){
				zoneCode = requestVO.getCustomFilter().get(FILTERTYPE_ZONE);
				String[] zoneCodesCollection = zoneCode.split("~");
				for(String zone : zoneCodesCollection){
					zoneCodes.add(zone);
				}
				if(zoneCodes.size() > 0){
					zoneFilterVO.setSuggestLocationsZoneCodes(zoneCodes);
				}
			}
			
			try {
				locations=new WarehouseDefaultsProxy().findAllLocationDetails(zoneFilterVO);
			} catch (ProxyException e) {
				e.printStackTrace();
			}
			if(locations!=null){
				for(LocationVO locationVO : locations){
					suggestList.add(locationVO.getMappedVO());
				}
			}
		}
		else if(MasterTypesConfig.MIPERRORCODE.equalsIgnoreCase(requestVO.getMasterType())){
			MessageMIPErrorVO messageMIPErrorVO = new MessageMIPErrorVO();
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			messageMIPErrorVO.setMappingVO(requestVO);
			messageMIPErrorVO.setCompanyCode(logonAttributes.getCompanyCode());
			Collection<MessageMIPErrorVO> xList = null;
			try {
				xList = new MsgBrokerMessageProxy().findMultipleMIPErrorCodeForLOV(messageMIPErrorVO,1);
			} catch (ProxyException e) {
				log.entering("ProxyException","");
			}

			if(xList!=null){
				for(MessageMIPErrorVO tmpVo:xList){
					suggestList.add(tmpVo.getMappedVO());
				}
			}

		}else if(MasterTypesConfig.HANDLINGAREATYPE.equalsIgnoreCase(requestVO.getMasterType())){
			LogonAttributes logonAttributes =  ContextUtils.getSecurityContext().getLogonAttributesVO();
			HandlingAreaTypeFilterVO handlingAreaTypeFilterVO = new HandlingAreaTypeFilterVO();
			handlingAreaTypeFilterVO.setMappingVO(requestVO);
			handlingAreaTypeFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			handlingAreaTypeFilterVO.setAirportCode(logonAttributes.getAirportCode());
			//handlingAreaTypeFilterVO.setRoleGroupCode(logonAttributes.getRoleGroupCode());
			handlingAreaTypeFilterVO.setWarehouseCode(logonAttributes.getDefaultWarehouseCode());
			handlingAreaTypeFilterVO.setSuggest(true);
			Collection<HandlingAreaTypeVO> xList = null;
			
			xList = new WarehouseProxy().findAllHandlingAreaTypes(handlingAreaTypeFilterVO);
			
			if(xList != null){
				for(HandlingAreaTypeVO handlingAreaTypeVO : xList){
					suggestList.add(handlingAreaTypeVO.getMappedVO());
				}
			}
		}else if(MasterTypesConfig.ASSIGNEDTOUSER.equalsIgnoreCase(requestVO.getMasterType())){
			UserEnquiryFilterVO userEnquiryFilterVO = new UserEnquiryFilterVO();
			SuggestResponseVO suggestAssignedToUsr = new SuggestResponseVO();
			userEnquiryFilterVO.setMappingVO(requestVO);
			HandlingAreaFilterVO handlingAreaFilterVO =  new HandlingAreaFilterVO();
			handlingAreaFilterVO.setMappingVO(requestVO);
			List<UserVO> xList1 = null;
			Collection<HandlingAreaVO> xList2 = null;
			try {
				xList1 =new  UserProxy().findAllHandlingAreaUsers(userEnquiryFilterVO);
			}catch (ProxyException e) {
				log.entering("ProxyException","findAllHandlingAreaUsers");
		}
			try {
				xList2 = new  WarehouseProxy().findHandlingAreas(handlingAreaFilterVO);
			} catch (ProxyException e) {
				log.entering("ProxyException","findHandlingAreas");
			}
			if(xList1!=null){
				for(UserVO tmpVo:xList1){
					suggestList.add(tmpVo.getMappedVO());
				}
			}
			if(xList2!=null){
				for(HandlingAreaVO tmpVo:xList2){
					suggestList.add(tmpVo.getMappedVO());
				}
			}
			suggestAssignedToUsr.setCode("SYSTEM");
			suggestAssignedToUsr.setDescription("SYSTEM");
			suggestList.add(suggestAssignedToUsr);
		}else if(MasterTypesConfig.LOCATIONGROUP.equalsIgnoreCase(requestVO.getMasterType())){
			LogonAttributes logonAttributes =  ContextUtils.getSecurityContext().getLogonAttributesVO();
			
			Collection<GeneralMasterGroupDetailsVO> xList = null;
			//ArrayList<String> coolWorkflowNames = new ArrayList<String>();
			GeneralMasterGroupFilterVO filterVO = new GeneralMasterGroupFilterVO();
			filterVO.setCompanyCode(logonAttributes.getCompanyCode());
			filterVO.setGroupType(WRKFLOWMSG_GRP_CAT);
			//filterVO.setGroupName(systemParameterMap.get(OPERATIONS_COOLTOOL_SYSPAR_COOLWORKFLOWMSGGROUPNAME));
			filterVO.setGroupCategory(GEN_STRING);
				try {
					xList = new SharedGeneralMasterGroupingProxy().findGroupsByType(filterVO);
				} catch (ProxyException e) {
					log.log(Log.FINE,  "ProxyException");
				}

				if (xList != null) {
					for(GeneralMasterGroupDetailsVO generalMasterGroupDetailsVO : xList){
						if(responseSet.add(generalMasterGroupDetailsVO.getMappedVO().getCode())){
							suggestList.add(generalMasterGroupDetailsVO.getMappedVO());
						}
					}
				}
			
		}else if(MasterTypesConfig.HANDLINGAREATYPE.equalsIgnoreCase(requestVO.getMasterType())){
			LogonAttributes logonAttributes =  ContextUtils.getSecurityContext().getLogonAttributesVO();
			HandlingAreaTypeFilterVO handlingAreaTypeFilterVO = new HandlingAreaTypeFilterVO();
			handlingAreaTypeFilterVO.setMappingVO(requestVO);
			handlingAreaTypeFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			handlingAreaTypeFilterVO.setAirportCode(logonAttributes.getAirportCode());
			//handlingAreaTypeFilterVO.setRoleGroupCode(logonAttributes.getRoleGroupCode());
			handlingAreaTypeFilterVO.setWarehouseCode(logonAttributes.getDefaultWarehouseCode());
			handlingAreaTypeFilterVO.setSuggest(true);
			Collection<HandlingAreaTypeVO> xList = null;
			
			xList = new WarehouseProxy().findAllHandlingAreaTypes(handlingAreaTypeFilterVO);
			
			if(xList != null){
				for(HandlingAreaTypeVO handlingAreaTypeVO : xList){
					suggestList.add(handlingAreaTypeVO.getMappedVO());
				}
			}
		}	
		//Added by A-7797 for ICRD-259080
		else if (MasterTypesConfig.CITYCODEMASTER.equalsIgnoreCase(requestVO
				.getMasterType())) {
			SuggestResponseVO suggestResponseVO = null;
			StringBuilder countyCityCode = null;
			Collection<GeneralParameterConfigurationVO> generalParameterConfigurationVOs = null;
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			GeneralParameterConfigurationVO generalParameterConfigurationVO = new GeneralParameterConfigurationVO();
			generalParameterConfigurationVO.setCompanyCode(logonAttributes.getCompanyCode());
			generalParameterConfigurationVO.setMasterType(CITY_CODE_MASTER);
			generalParameterConfigurationVO.setParameterValue(requestVO.getCodeVal());
			generalParameterConfigurationVOs = new SharedDefaultsProxy().findGeneralParameterConfiguration(generalParameterConfigurationVO);
			if (generalParameterConfigurationVOs != null
					&& !generalParameterConfigurationVOs.isEmpty()) {				
				for (GeneralParameterConfigurationVO generalParameterConfigurationResultVO : generalParameterConfigurationVOs) {
					suggestResponseVO = new SuggestResponseVO();
					countyCityCode = new StringBuilder();
					countyCityCode.append(generalParameterConfigurationResultVO.getConfigurationReferenceOne())
							.append(generalParameterConfigurationResultVO.getParmeterCode());
					suggestResponseVO.setCode(countyCityCode.toString());
					suggestResponseVO.setDescription(generalParameterConfigurationResultVO.getParameterValue());
					suggestList.add(suggestResponseVO);
				}
			}

		}
		log.exiting("MasterTypeOrchestrator", "despatch");
		return suggestList;
	}
}
