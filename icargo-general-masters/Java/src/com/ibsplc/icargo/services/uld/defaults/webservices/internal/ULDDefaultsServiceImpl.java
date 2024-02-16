/*
 * WarehouseDefaultsServiceImpl.java Created on Jan 09, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.services.uld.defaults.webservices.internal ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.types.internal.ErrorDetailType;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.types.internal.ULDAirportLocationFilterType;
import com.ibsplc.icargo.business.uld.defaults.types.internal.ULDAirportLocationType;
import com.ibsplc.icargo.business.uld.defaults.types.internal.ULDDamageChecklistFilterType;
import com.ibsplc.icargo.business.uld.defaults.types.internal.ULDDamageChecklistType;
import com.ibsplc.icargo.business.uld.defaults.types.internal.ULDSCMReconcileType;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.WebServiceEndPoint;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;  
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * 
 * @author A-5525
 *
 */
@javax.jws.WebService( 
		name="ULDDefaultsService",
		serviceName = "ULDDefaultsService", 
		portName = "ULDDefaultsService", 
		targetNamespace = "http://www.ibsplc.com/icargo/services/ULDDefaultsService/internal/2012/03/20_01", 
		wsdlLocation = "file:./wsdl/uld/defaults/internal/ULDDefaultsService.wsdl", 
		endpointInterface = "com.ibsplc.icargo.services.uld.defaults.webservices.internal.ULDDefaultsService") 
		@Module("uld") 
		@SubModule("defaults") 
		public class ULDDefaultsServiceImpl extends WebServiceEndPoint implements ULDDefaultsService { 

	private final static String RUNTIME_EXCEPTION = "Runtime exception, Contact system administrator with business scenario";
	private static final String SUCCESS = "SUCCESS";
	 
	public UldDamageChecklistResponse listULDDamageChecklistMaster(
			UldDamageChecklistRequest paramUldDamageChecklistRequest) {
		LogonAttributes logonAttributes = null;      
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException systemException) {
			handleException(systemException);
		}
		ULDDamageChecklistFilterType uldDamageChecklistFilterType=paramUldDamageChecklistRequest.getUldDamageChecklistFilterType();
				String companyCode=null;
		if(uldDamageChecklistFilterType.getCompanyCode()!=null&&!uldDamageChecklistFilterType.getCompanyCode().isEmpty()){
			companyCode	=uldDamageChecklistFilterType.getCompanyCode();
		}else{
			companyCode =logonAttributes.getCompanyCode();
		}
		String section=uldDamageChecklistFilterType.getSection();
		Collection<ULDDamageChecklistVO> uldDamageChecklistVOs=new ArrayList<ULDDamageChecklistVO>();
		UldDamageChecklistResponse uldDamageChecklistResponse=new UldDamageChecklistResponse();
		Collection <ULDDamageChecklistType> uldDamageChecklistTypes=new ArrayList<ULDDamageChecklistType>();
		ULDDamageChecklistType uldDamageChecklistType=null;
		try{ 
			uldDamageChecklistVOs=despatchRequest("listULDDamageChecklistMaster",companyCode,section);

			if(uldDamageChecklistVOs!=null&&!uldDamageChecklistVOs.isEmpty()){
				for(ULDDamageChecklistVO uldDamageChecklistVO:uldDamageChecklistVOs){
					uldDamageChecklistType=new ULDDamageChecklistType();
					uldDamageChecklistType.setNoOfPoints(uldDamageChecklistVO.getNoOfPoints());
					uldDamageChecklistType.setDescription(uldDamageChecklistVO.getDescription());
					uldDamageChecklistType.setSection(uldDamageChecklistVO.getSection());
					uldDamageChecklistType.setSequenceNumber(uldDamageChecklistVO.getSequenceNumber());
					uldDamageChecklistTypes.add(uldDamageChecklistType);
				}   
				uldDamageChecklistResponse.getReturn().addAll(uldDamageChecklistTypes); 
			}
		}catch (WSBusinessException wsBusinessException) {
			//handleException(wsBusinessException);
			uldDamageChecklistType=new ULDDamageChecklistType();	

			uldDamageChecklistType.getErrorDetails().add(createErrorDetailType(wsBusinessException));
			uldDamageChecklistTypes.add(uldDamageChecklistType); 
			uldDamageChecklistResponse.getReturn().addAll(uldDamageChecklistTypes);
		}
		catch (SystemException systemException) {
			//handleException(systemException); 

			//handleException(wsBusinessException);
			uldDamageChecklistType=new ULDDamageChecklistType();	

			uldDamageChecklistType.getErrorDetails().add(createErrorDetailType(systemException));
			uldDamageChecklistTypes.add(uldDamageChecklistType); 
			uldDamageChecklistResponse.getReturn().addAll(uldDamageChecklistTypes);


		}
		return uldDamageChecklistResponse;
     }
	public List<ULDAirportLocationType> listULDAirportLocation(ULDAirportLocationFilterType uldAirportLocationFilterType)
	{
		Collection<ULDAirportLocationVO> uldAirportLocationVOs=null;
		List<ULDAirportLocationType> uldAirportLocationTypes= null; 
		String companyCode=uldAirportLocationFilterType.getCompanyCode();
		String airportCode=uldAirportLocationFilterType.getAirportCode();
		String facilityType=uldAirportLocationFilterType.getFacilitytype();		
		try{ 
			uldAirportLocationVOs=despatchRequest("listULDAirportLocation",companyCode,airportCode,facilityType );
			if(uldAirportLocationVOs!=null&&uldAirportLocationVOs.size()>0)
			{
				uldAirportLocationTypes=new ArrayList<ULDAirportLocationType>();
				for(ULDAirportLocationVO uldAirportLocationVO : uldAirportLocationVOs)
				{
					ULDAirportLocationType uldAirportLocationType=new ULDAirportLocationType();
					uldAirportLocationType.setFacilityCode(uldAirportLocationVO.getFacilityCode());
					uldAirportLocationType.setFacilitytype(uldAirportLocationVO.getFacilityType());
					uldAirportLocationTypes.add(uldAirportLocationType);
				} 
			}
		}
		catch (WSBusinessException wsBusinessException) {
			//handleException(wsBusinessException);
			uldAirportLocationTypes=new ArrayList<ULDAirportLocationType>();			
			ULDAirportLocationType uldAirportLocationType=new ULDAirportLocationType();
			uldAirportLocationType.getErrorDetails().add(createErrorDetailType(wsBusinessException));
			uldAirportLocationTypes.add(uldAirportLocationType); 
		}
		catch (SystemException systemException) {
			//handleException(systemException); 
			uldAirportLocationTypes=new ArrayList<ULDAirportLocationType>();			
			ULDAirportLocationType uldAirportLocationType=new ULDAirportLocationType();
			uldAirportLocationType.getErrorDetails().add(createErrorDetailType(systemException));
			uldAirportLocationTypes.add(uldAirportLocationType); 
		}
		return uldAirportLocationTypes;
	}
    
	public ErrorDetailType saveUploadDetails(List<ULDSCMReconcileType> uldSCMReconcileTypes){

		LogonAttributes logonAttributes = null;      
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException systemException) {
			handleException(systemException);
		}  
		Collection<ULDSCMReconcileVO> reconcileVOs=  new ArrayList<ULDSCMReconcileVO>(); 
		Collection<ULDSCMReconcileDetailsVO> reconcileDetailsVOs=new ArrayList<ULDSCMReconcileDetailsVO>();
		ULDSCMReconcileVO uldscmReconcileVO= new ULDSCMReconcileVO();
		//uldscmReconcileVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier()); 
		uldscmReconcileVO.setOperationFlag("I");
		boolean isMasterNotUpdated=true;    
		for (ULDSCMReconcileType uldSCMReconcileType : uldSCMReconcileTypes) {	  
			if(isMasterNotUpdated){
				uldscmReconcileVO.setCompanyCode(logonAttributes.getCompanyCode());				
				uldscmReconcileVO.setAirportCode(uldSCMReconcileType.getAirportCode());	  
				isMasterNotUpdated=false;  
			}
			AirlineValidationVO airlineValidationVO= new AirlineValidationVO();
			airlineValidationVO=populateAirlineValidationVO(logonAttributes.getCompanyCode(),uldSCMReconcileType.getCompanyCode());
			if(airlineValidationVO!=null){
				uldscmReconcileVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
			}
			ULDSCMReconcileDetailsVO uldSCMReconcileDetailsVO= new ULDSCMReconcileDetailsVO();
			//uldSCMReconcileDetailsVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
			if(airlineValidationVO!=null){
				uldSCMReconcileDetailsVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
			}
			uldSCMReconcileDetailsVO.setOperationFlag("I");    
			uldSCMReconcileDetailsVO.setUldNumber(uldSCMReconcileType.getUldNumber());
			uldSCMReconcileDetailsVO.setUldStatus("F");   
			uldSCMReconcileDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			uldSCMReconcileDetailsVO.setFacilityType(uldSCMReconcileType.getFacilitytype());
			uldSCMReconcileDetailsVO.setLocation(uldSCMReconcileType.getLocation());
			uldSCMReconcileDetailsVO.setAirportCode(uldSCMReconcileType.getAirportCode()); 		

			reconcileDetailsVOs.add(uldSCMReconcileDetailsVO);
		}
		if(uldSCMReconcileTypes!=null && uldSCMReconcileTypes.size()>0){    
			uldscmReconcileVO.setReconcileDetailsVOs(reconcileDetailsVOs);  
			reconcileVOs.add(uldscmReconcileVO);
		}
		try{
			despatchRequest("saveSCMReconcialtionDetails", reconcileVOs);
			ErrorDetailType errorDetailType = new ErrorDetailType();
			errorDetailType.setErrorCode(SUCCESS);
			return errorDetailType;
		}
		catch (WSBusinessException wsBusinessException) {
			/*handleException(wsBusinessException);
			ErrorDetailType errorDetailType = new ErrorDetailType();
			errorDetailType.setErrorCode(wsBusinessException.getErrors()
					.iterator().next().getErrorCode());*/
			return createErrorDetailType(wsBusinessException);
		} catch (SystemException systemException) {
			/*handleException(systemException);
			ErrorDetailType errorDetailType = new ErrorDetailType();
			errorDetailType.setErrorCode(systemException.getErrors()
					.iterator().next().getErrorCode());*/
			return createErrorDetailType(systemException);
		}
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param carrierCode
	 * @return
	 */
	private AirlineValidationVO populateAirlineValidationVO(String companyCode,String carrierCode){
		AirlineValidationVO airlineValidationVO= new AirlineValidationVO();
		try{
			airlineValidationVO=despatchRequest("populateAirlineCodes", companyCode,carrierCode);
		}
		catch (WSBusinessException wsBusinessException) {
			handleException(wsBusinessException);
		}
		catch (SystemException systemException) { 
			handleException(systemException);
		}
		return airlineValidationVO;
	}
	
	private void handleException(WSBusinessException businessException){
		businessException.getMessage();
		if(businessException.getErrors().iterator().next().getErrorCode()!=null &&
				businessException.getErrors().iterator().next().getErrorCode().length() > 0){
			throw new RuntimeException(businessException.getErrors().iterator().next().getErrorCode());
		}
		else{
			throw new RuntimeException(RUNTIME_EXCEPTION);
		}
	}
	private void handleException(SystemException systemException) {
		// printStackTraccee()();
		if (systemException.getErrors().iterator().next().getErrorCode() != null
				&& systemException.getErrors().iterator().next().getErrorCode()
				.length() > 0) {
			throw new RuntimeException(systemException.getErrors().iterator()
					.next().getErrorCode());
		} else {
			throw new RuntimeException(RUNTIME_EXCEPTION);
		}
	}
	/**
	 * @author A-5525
	 * @param businessException
	 * @return ErrorDetailType
	 */
	private ErrorDetailType createErrorDetailType(WSBusinessException businessException){ 
		businessException.getMessage(); 
		return constructErrorDetailType(businessException.getErrors().iterator().next()); 
	} 
	/**
	 * @author A-5525
	 * @param systemException
	 * @return ErrorDetailType
	 */
	private ErrorDetailType createErrorDetailType(SystemException systemException) { 
		systemException.getMessage();	
		return constructErrorDetailType(systemException.getErrors().iterator().next());	
	} 
	/**
	 * @author A-5525
	 * @param errorVO
	 * @return ErrorDetailType
	 */
	private ErrorDetailType constructErrorDetailType(ErrorVO errorVO) { 
		ErrorDetailType errorDetailType = new ErrorDetailType(); 
		if (errorVO.getErrorCode() != null && errorVO.getErrorCode().length() > 0) { 
			errorDetailType.setErrorCode(errorVO.getErrorCode()); 
			errorDetailType.setErrorDescription(errorVO.getErrorDescription());   
			if (errorVO.getErrorData() != null && errorVO.getErrorData().length > 0 
					&& errorVO.getErrorData()[0] != null) { 
				errorDetailType.setErrorData(errorVO.getErrorData()[0].toString()); 
			} 
		} else { 
			errorDetailType.setErrorCode(RUNTIME_EXCEPTION); 
		} 
		return errorDetailType; 
	} 

}