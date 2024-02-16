/**
 * ULDDefaultsServiceBaseImpl.java created on 11/05/2018
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.uld.defaults.webservices.standard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.types.standard.ErrorDetailType;
import com.ibsplc.icargo.business.shared.defaults.types.standard.MessageHeaderType;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.types.standard.SaveULDDamageDetailsRequestType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.SaveULDDamageDetailsResponseType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.SaveULDDamageResponseType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.SaveULDDetailsRequestType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.SaveULDDetailsResponseType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.SaveUldRequestDetailsType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.SaveUldResponseDetailsType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.ULDDamageDetailsType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.ULDDamagePictureType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.ULDDamageResponseType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.ULDDamageType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.ULDResponseDetailsType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.ULDValidationType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.ValidateULDDetailRequestType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.ValidateULDDetailResponseType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.ValidateULDResponseType;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.WebServiceEndPoint;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;


@Module("uld") 
@SubModule("defaults") 
/**
 *
 * @author A-7918
 *
 */
public class ULDDefaultsServiceBaseImpl extends WebServiceEndPoint{
	
	private static final String DUPLICATE_ULD_EXIST = "uld.booking.duplicateUldexists";
		
	private static final String NO_ULD_EXIST = "uld.booking.nouldexists";
	
	/**
	 * @author A-7918
	 * @param ulddetailsRequestType
	 * @return
	 */
	public SaveULDDetailsResponseType saveULDDetails(SaveULDDetailsRequestType ulddetailsRequestType){
			ULDVO detailsVO = doPopulateULDDetailsVO(ulddetailsRequestType);
			SaveULDDetailsResponseType responseType = new SaveULDDetailsResponseType();
			try {
				//validate uld format ICRD-307339  
				despatchRequest("validateULDFormat", detailsVO.getCompanyCode(),detailsVO.getUldNumber());
				despatchRequest("saveULD", detailsVO);
			} catch (WSBusinessException businessException) {
				if (businessException.getErrors() != null && businessException.getErrors().size() > 0) {
					for (ErrorVO error : businessException.getErrors()) {
						return responseType =populateErrorResponse(ulddetailsRequestType,error,detailsVO);
					}
				}
			}
			catch (SystemException systemException) {
				for (ErrorVO error : systemException.getErrors()) {
					return responseType =populateErrorResponse(ulddetailsRequestType,error,detailsVO);
				}
			}
			  responseType= doPopulateResponseType(detailsVO,ulddetailsRequestType);
	return responseType;
	}
	
	
	public SaveULDDamageDetailsResponseType saveULDDamageDetails(
			SaveULDDamageDetailsRequestType saveULDDamageDetailsRequestType) {
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = doPopulateULDDamageDetailsVO(saveULDDamageDetailsRequestType);
		SaveULDDamageDetailsResponseType responseType = new SaveULDDamageDetailsResponseType();
		try {
			despatchRequest("saveULDDamage", uldDamageRepairDetailsVO);
		} catch (WSBusinessException businessException) {
			if (businessException.getErrors() != null && businessException.getErrors().size() > 0) {
				for (ErrorVO error : businessException.getErrors()) {
					return responseType =populateDamageErrorResponse(saveULDDamageDetailsRequestType,error,uldDamageRepairDetailsVO);
				}
			}
		}
		catch (SystemException systemException) {
			for (ErrorVO error : systemException.getErrors()) {
				return responseType =populateDamageErrorResponse(saveULDDamageDetailsRequestType,error,uldDamageRepairDetailsVO);
			}
		}
		 responseType= doPopulateDamageResponseType(uldDamageRepairDetailsVO,saveULDDamageDetailsRequestType);
     return responseType;
} 
	
	
	private SaveULDDamageDetailsResponseType populateDamageErrorResponse(SaveULDDamageDetailsRequestType saveULDDamageDetailsRequestType,
			ErrorVO error,ULDDamageRepairDetailsVO uldDamageRepairDetailsVO) {
		ErrorDetailType errorDetailType= new ErrorDetailType();	
		SaveULDDamageDetailsResponseType detailsType = new SaveULDDamageDetailsResponseType();
		SaveULDDamageResponseType detailsResponseType = new SaveULDDamageResponseType();
		if(saveULDDamageDetailsRequestType.getMessageHeader()!= null ){
			MessageHeaderType messageHeaderType = new MessageHeaderType();
			messageHeaderType.setMessageType(saveULDDamageDetailsRequestType.getMessageHeader().getMessageType());
			messageHeaderType.setSourceSystem(saveULDDamageDetailsRequestType.getMessageHeader().getSourceSystem());
			detailsType.setMessageHeader(messageHeaderType);
			}
		ULDDamageResponseType responseDetailsType = new ULDDamageResponseType();
		detailsResponseType.setRequestId(saveULDDamageDetailsRequestType.getRequestData().getRequestId());
		detailsResponseType.setSaveStatus("F");
		if(uldDamageRepairDetailsVO.getUldNumber()!=null)
			responseDetailsType.setULDNumber(uldDamageRepairDetailsVO.getUldNumber());
		
		if(detailsType != null)
			detailsResponseType.setULDDamageResponseDetails(responseDetailsType);

		errorDetailType.setErrorCode(error.getErrorCode());
		detailsType.setResponseDetails(detailsResponseType);
		detailsType.getResponseDetails().getULDDamageResponseDetails()
			.getErrorDetails().add(errorDetailType);
		return detailsType;
	}

	private SaveULDDamageDetailsResponseType doPopulateDamageResponseType(ULDDamageRepairDetailsVO uldDamageRepairDetailsVO, SaveULDDamageDetailsRequestType saveULDDamageDetailsRequestType) {
		MessageHeaderType messageHeaderType = new MessageHeaderType();
		SaveULDDamageDetailsResponseType responseType = new SaveULDDamageDetailsResponseType();
		if(saveULDDamageDetailsRequestType.getMessageHeader()!= null ){
		messageHeaderType.setMessageType(saveULDDamageDetailsRequestType.getMessageHeader().getMessageType());
		messageHeaderType.setSourceSystem(saveULDDamageDetailsRequestType.getMessageHeader().getSourceSystem());
		responseType.setMessageHeader(messageHeaderType);
		}
		SaveULDDamageResponseType uldResponseDetailsType = new SaveULDDamageResponseType();
		ULDDamageResponseType uldDamageResponseType=new ULDDamageResponseType();
		uldDamageResponseType.setULDNumber(uldDamageRepairDetailsVO.getUldNumber());
		uldResponseDetailsType.setULDDamageResponseDetails(uldDamageResponseType);
		uldResponseDetailsType.setSaveStatus("S");
		if(saveULDDamageDetailsRequestType.getRequestData().getRequestId()!=null){
			uldResponseDetailsType.setRequestId(saveULDDamageDetailsRequestType.getRequestData().getRequestId());
		}
		responseType.setResponseDetails(uldResponseDetailsType);
		return responseType;
	}
	
	
	
	private ULDDamageRepairDetailsVO doPopulateULDDamageDetailsVO(SaveULDDamageDetailsRequestType uldDamageDetailsRequestType)  {
		LogonAttributes logonAttributes=  null;
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			
		}
		LocalDate lastupdatedTime = new LocalDate(logonAttributes.getAirportCode(),Location.ARP, true); 
		ULDDamageType uldDamageType=new ULDDamageType();
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO =new ULDDamageRepairDetailsVO();
		uldDamageType=uldDamageDetailsRequestType.getRequestData().getULDDamages();
		uldDamageRepairDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		uldDamageRepairDetailsVO.setDamageStatus(uldDamageType.getDamageStatus());
		//uldDamageRepairDetailsVO.setLastUpdatedTime(lastupdatedTime);
		uldDamageRepairDetailsVO.setLastUpdatedUser(logonAttributes.getUserId());
		uldDamageRepairDetailsVO.setOverallStatus(uldDamageType.getOverallStatus());
		uldDamageRepairDetailsVO.setRepairStatus(uldDamageType.getRepairStatus());
		uldDamageRepairDetailsVO.setUldNumber(uldDamageType.getULDNumber());
	Collection <ULDDamageVO> uldDamageVOs=new ArrayList<ULDDamageVO>();
	ULDDamageVO uldDamageVO=null;
		for(ULDDamageDetailsType uldDamageDetailsType:uldDamageType.getULDDamageDetails()){
			uldDamageVO=new ULDDamageVO(); 
			uldDamageVO.setDamageDescription(uldDamageDetailsType.getDamageDescription());
			//uldDamageVO.setUldNumber(uldDamageType.getULDNumber());
			uldDamageVO.setDamagePoints(uldDamageDetailsType.getDamagePoints());
			uldDamageVO.setOperationFlag(uldDamageDetailsType.getOperationFlag());
			uldDamageVO.setFacilityType(uldDamageDetailsType.getFacilityType());
			uldDamageVO.setPartyType(uldDamageDetailsType.getPartyType());
			uldDamageVO.setLocation(uldDamageDetailsType.getLocation());
			uldDamageVO.setParty(uldDamageDetailsType.getPartyCode());
			
			uldDamageVO.setSection(uldDamageDetailsType.getSection()); 
			if(uldDamageDetailsType.getSequenceNumber()!=null){
			uldDamageVO.setSequenceNumber(uldDamageDetailsType.getSequenceNumber());
			}  
		//	uldDamageVO.setLastUpdateTime(lastupdatedTime);
			uldDamageVO.setLastUpdateUser(logonAttributes.getUserId());
			uldDamageVO.setReportedDate(lastupdatedTime);
			uldDamageVO.setSeverity(uldDamageDetailsType.getSeverity());
			uldDamageVO.setUldStatus(uldDamageDetailsType.getUldStatus());
			uldDamageVO.setReportedStation(logonAttributes.getAirportCode());
			
			//currently handles only one image
			if(uldDamageDetailsType.getUldDamagePictures()!=null&&uldDamageDetailsType.getUldDamagePictures().size()>0){
				for(ULDDamagePictureType uldDamagePictureType:uldDamageDetailsType.getUldDamagePictures()){
					ULDDamagePictureVO pictureVO=new ULDDamagePictureVO();
					pictureVO.setCompanyCode(logonAttributes.getCompanyCode());
					pictureVO.setOperationFlag(ULDDamagePictureVO.OPERATION_FLAG_INSERT);
					pictureVO.setUldNumber(uldDamageType.getULDNumber());
					if(pictureVO.getImage()==null){
						pictureVO.setImage(new ImageModel()); 
					}
					pictureVO.getImage().setData(uldDamagePictureType.getImage());
					if(uldDamageVO.getPictureVOs()==null){
						uldDamageVO.setPictureVOs(new ArrayList<ULDDamagePictureVO>());
					}
					uldDamageVO.getPictureVOs().add(pictureVO);
					uldDamageVO.setPicturePresent(true);
					//uldDamageVO.setPictureVO(pictureVO); 
				}
			}
			uldDamageVOs.add(uldDamageVO);
		}
		if(uldDamageRepairDetailsVO.getUldDamageVOs()==null){
			uldDamageRepairDetailsVO.setUldDamageVOs(new ArrayList<ULDDamageVO>()); 
		}
		uldDamageRepairDetailsVO.getUldDamageVOs().addAll(uldDamageVOs); 
		return uldDamageRepairDetailsVO;
	}
	
	/**
	 * @author A-7918
	 * @param ulddetailsRequestType
	 * @param error
	 * @return
	 */
	private SaveULDDetailsResponseType populateErrorResponse(SaveULDDetailsRequestType ulddetailsRequestType,
			ErrorVO error,ULDVO detailsVO) {
		ErrorDetailType errorDetailType= new ErrorDetailType();	
		ULDResponseDetailsType detailsType = new ULDResponseDetailsType();
		SaveULDDetailsResponseType detailsResponseType = new SaveULDDetailsResponseType();
		if(ulddetailsRequestType.getMessageHeader()!= null ){
			MessageHeaderType messageHeaderType = new MessageHeaderType();
			messageHeaderType.setMessageType(ulddetailsRequestType.getMessageHeader().getMessageType());
			messageHeaderType.setSourceSystem(ulddetailsRequestType.getMessageHeader().getSourceSystem());
			detailsResponseType.setMessageHeader(messageHeaderType);
			}
		 SaveUldResponseDetailsType responseDetailsType = new SaveUldResponseDetailsType();
		responseDetailsType.setRequestId(ulddetailsRequestType.getRequestData().getRequestId());
		responseDetailsType.setSaveStatus("F");
		if(ulddetailsRequestType.getRequestData().getULDDetails().getULDSerialNumber() != null)
		detailsType.setULDSerialNumberType(ulddetailsRequestType.getRequestData().getULDDetails().getULDSerialNumber());
		if(detailsVO.getUldType() != null)
		detailsType.setULDType(detailsVO.getUldType());
		if(ulddetailsRequestType.getRequestData().getULDDetails().getULDOwnerCode() != null)
		detailsType.setULDOwnerCodeType(ulddetailsRequestType.getRequestData().getULDDetails().getULDOwnerCode());
		if(detailsType != null)
		responseDetailsType.setULDResponseDetails(detailsType);
		if (DUPLICATE_ULD_EXIST.equals(error.getErrorCode())) {
			errorDetailType
			.setErrorDesc("Duplicate ULD exists");
		}else if (NO_ULD_EXIST.equals(error.getErrorCode()) ) {
			errorDetailType
			.setErrorDesc("Invalid ULD Number");
		}else{
			errorDetailType.setErrorDesc(error.getErrorCode());
		}
		errorDetailType.setErrorCode(error.getErrorCode());
		detailsResponseType.setResponseDetails(responseDetailsType);
		detailsResponseType.getResponseDetails().getULDResponseDetails()
			.getErrorDetails().add(errorDetailType);
		return detailsResponseType;
	}
/**
 * @author A-7918
 * @param detailsVO
 * @param ulddetailsRequestType
 * @return
 */
	private SaveULDDetailsResponseType doPopulateResponseType(ULDVO detailsVO, SaveULDDetailsRequestType ulddetailsRequestType) {
		MessageHeaderType messageHeaderType = new MessageHeaderType();
		SaveULDDetailsResponseType responseType = new SaveULDDetailsResponseType();
		if(ulddetailsRequestType.getMessageHeader()!= null ){
		messageHeaderType.setMessageType(ulddetailsRequestType.getMessageHeader().getMessageType());
		messageHeaderType.setSourceSystem(ulddetailsRequestType.getMessageHeader().getSourceSystem());
		responseType.setMessageHeader(messageHeaderType);
		}
		SaveUldResponseDetailsType uldResponseDetailsType = new SaveUldResponseDetailsType();
		uldResponseDetailsType.setULDResponseDetails(populateULDBooking(detailsVO,ulddetailsRequestType));
		uldResponseDetailsType.setSaveStatus("S");
		if(ulddetailsRequestType.getRequestData().getRequestId()!=null){
			uldResponseDetailsType.setRequestId(ulddetailsRequestType.getRequestData().getRequestId());
		}
		responseType.setResponseDetails(uldResponseDetailsType);
		return responseType;
	}
	/**
	 * @author A-7918
	 * @param detailsVO
	 * @param ulddetailsRequestType
	 * @return
	 */
	private ULDResponseDetailsType populateULDBooking(ULDVO detailsVO,SaveULDDetailsRequestType ulddetailsRequestType) {
		ULDResponseDetailsType detailsType = new ULDResponseDetailsType();
		String uldNumber = null;
		if(ulddetailsRequestType.getRequestData().getULDDetails().getULDSerialNumber() != null){
			uldNumber = ulddetailsRequestType.getRequestData().getULDDetails().getULDSerialNumber();
			while(uldNumber.length() < 5 ){		
				uldNumber = "0"+uldNumber;
		}
			detailsType.setULDSerialNumberType(uldNumber);
		}
		if(detailsVO.getUldType() != null){
		detailsType.setULDType(detailsVO.getUldType());
		}
		if(ulddetailsRequestType.getRequestData().getULDDetails().getULDOwnerCode() != null){
		detailsType.setULDOwnerCodeType(ulddetailsRequestType.getRequestData().getULDDetails().getULDOwnerCode());
		}
		return detailsType;
	}
	
/**
	 * 
	 * 	Method		:	ULDDefaultsServiceBaseImpl.doPopulateULDDetailsVO
	 *	Added by 	:	A-7918
	 * 	Used for 	:
	 *	Parameters	:	@param ulddetailsRequestType
	 *	Parameters	:	@return 
	 *	Return type	: 	ULDVO
 */
	private ULDVO doPopulateULDDetailsVO(SaveULDDetailsRequestType ulddetailsRequestType)  {
		ULDVO uldDetailsVO = new ULDVO();
		try {
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			uldDetailsVO.setLastUpdateUser(logonAttributes.getUserId());
			uldDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			uldDetailsVO.setCurrentStation(logonAttributes.getAirportCode());
			if (!nullOrEmpty(logonAttributes.getStationCode())){
				uldDetailsVO.setLastUpdateTime(new LocalDate(logonAttributes.getStationCode(), Location.STN, true));
			}
		} catch (SystemException e) {
			//log.log(Log.SEVERE, "doPopulateULDDetailsVO>>>>>logonAttributes error");
		}
			
	/*
	 * commented as uld serial number as the part number
	 * is not provided in the service
		 * if(uldDetailsType.getULDSerialNumber() !=null){
		 * uldDetailsVO.setUldSerialNumber(uldDetailsType.getULDSerialNumber()); }
	 */
		SaveUldRequestDetailsType saveUldRequestDetailsType = ulddetailsRequestType.getRequestData();
		com.ibsplc.icargo.business.uld.defaults.types.standard.ULDDetailsType uldDetailsType = 
				ulddetailsRequestType.getRequestData().getULDDetails();
		uldDetailsVO.setUldType(uldDetailsType.getULDType());
		uldDetailsVO.setUldPriceUnit(uldDetailsType.getUldPriceUnit());
		uldDetailsVO.setUldPrice(uldDetailsType.getUldPrice()!=null ? 
				uldDetailsType.getUldPrice().doubleValue() :0);
		uldDetailsVO.setCurrentValue(uldDetailsType.getCurrentValue() != null ? 
				uldDetailsType.getCurrentValue().doubleValue() : 0 );
		uldDetailsVO.setCurrentValueUnit(uldDetailsType.getCurrentValueUnit());
		uldDetailsVO.setUldNature(uldDetailsType.getUldNature());
		uldDetailsVO.setFacilityType(uldDetailsType.getFacilityType());
		uldDetailsVO.setVendor(uldDetailsType.getVendor());
		uldDetailsVO.setDamageStatus(uldDetailsType.getDamageStatus());
		uldDetailsVO.setManufacturer(uldDetailsType.getManufacturer());
		uldDetailsVO.setLifeSpan(uldDetailsType.getLifeSpan()!=null ? 
				uldDetailsType.getLifeSpan().intValue():0);
		uldDetailsVO.setTsoNumber(uldDetailsType.getTsoNumber());
		uldDetailsVO.setUldGroupCode(uldDetailsType.getUldGroupCode());
		uldDetailsVO.setUldContour(uldDetailsType.getUldContour());
		uldDetailsVO.setOperationalAirlineCode(uldDetailsType.getOperationalAirlineCode());
		uldDetailsVO.setOwnerStation(uldDetailsType.getOwnerStation());
		uldDetailsVO.setOverallStatus(uldDetailsType.getOverallStatus());
		uldDetailsVO.setCleanlinessStatus(uldDetailsType.getCleanlinessStatus());
		uldDetailsVO.setPurchaseInvoiceNumber(uldDetailsType.getPurchaseInvoiceNumber());
		uldDetailsVO.setIataReplacementCost(uldDetailsType.getIataReplacementCost() != null ?
				uldDetailsType.getIataReplacementCost().doubleValue() : 0);
		if(saveUldRequestDetailsType.getOperationalFlag() != null){
			uldDetailsVO.setOperationalFlag(saveUldRequestDetailsType.getOperationalFlag().toString());
		}

		LocalDate uldDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		if(!nullOrEmpty(uldDetailsType.getPurchaseDate())){
			uldDate.setDate(uldDetailsType.getPurchaseDate());
			uldDetailsVO.setPurchaseDate(uldDate);
		}
		if(!nullOrEmpty(uldDetailsType.getManufactureDate())){
			uldDate.setDate(uldDetailsType.getManufactureDate());
			uldDetailsVO.setManufactureDate(uldDate);
		}
		uldDetailsVO.setManufacturer(uldDetailsType.getManufacturer());
    uldDetailsVO.setSource("W");  
    //Added as part of ICRD-318745
		uldDetailsVO.setOwnerAirlineCode(uldDetailsType.getULDOwnerCode());
		
		if(!nullOrEmpty(uldDetailsType.getULDType()) 
				&& !nullOrEmpty(uldDetailsType.getULDOwnerCode()) 
				&& !nullOrEmpty(uldDetailsType.getULDSerialNumber())){
			while(uldDetailsType.getULDSerialNumber().length() < 5 ){
				uldDetailsType.setULDSerialNumber( "0" + uldDetailsType.getULDSerialNumber());
			}
			uldDetailsVO.setUldNumber(
					new StringBuilder()
					.append(uldDetailsType.getULDType())
					.append(uldDetailsType.getULDSerialNumber())
					.append(uldDetailsType.getULDOwnerCode()).toString());
		}
		
		uldDetailsVO.setTareWeight(uldDetailsType.getTareWeight() != null ? 
				new Measure(UnitConstants.WEIGHT,0,uldDetailsType.getTareWeight().doubleValue(),uldDetailsType.getTareWeightUnit()): null);
		uldDetailsVO.setStructuralWeight(uldDetailsType.getStructuralWeight() != null ?
				new Measure(UnitConstants.WEIGHT,0,uldDetailsType.getStructuralWeight().doubleValue(),uldDetailsType.getStructuralWeightUnit()):null);
		uldDetailsVO.setStructuralWeightUnit(uldDetailsType.getStructuralWeightUnit());
		
		if(uldDetailsType.getBaseLength() != null){
			//uldDetailsVO.setBaseLength(new Measure(UnitConstants.DIMENSION,uldDetailsType.getBaseLength().doubleValue()));
			uldDetailsVO.setBaseLength(
					new Measure(UnitConstants.DIMENSION,0.0,
							uldDetailsType.getBaseLength().doubleValue(),uldDetailsType.getDimensionUnit()));
		}
		if(uldDetailsType.getBaseHeight() != null){		
			//uldDetailsVO.setBaseHeight(new Measure(UnitConstants.DIMENSION,uldDetailsType.getBaseHeight().doubleValue()));
			uldDetailsVO.setBaseHeight(
					new Measure(UnitConstants.DIMENSION,0.0,
							uldDetailsType.getBaseHeight().doubleValue(),uldDetailsType.getDimensionUnit()));
		}
		if(uldDetailsType.getBaseWidth() != null){
			//uldDetailsVO.setBaseWidth(new Measure(UnitConstants.DIMENSION,uldDetailsType.getBaseWidth().doubleValue()));
			uldDetailsVO.setBaseWidth(
					new Measure(UnitConstants.DIMENSION,0.0,
							uldDetailsType.getBaseWidth().doubleValue(),uldDetailsType.getDimensionUnit()));	
		}
		try{
			/*For populating mandatory field from ULD type*/
			ULDTypeVO uldTypeVO = 
					despatchRequest("findULDTypeStructuralDetails", 
							uldDetailsVO.getCompanyCode(),
							uldDetailsVO.getUldType());

			if(uldTypeVO!=null){
				double tareWeight  = 
						uldDetailsVO.getTareWeight()!=null ? uldDetailsVO.getTareWeight().getRoundedSystemValue() : 0;
				uldDetailsVO.setTareWeight(tareWeight<=0 ? uldTypeVO.getTareWt() :uldDetailsVO.getTareWeight());

				double structuralWeight =  
						uldDetailsVO.getStructuralWeight()!=null ? uldDetailsVO.getStructuralWeight().getRoundedSystemValue() : 0;
				if(structuralWeight<=0 && uldTypeVO.getStructuralWtLmt()!=null){
					uldDetailsVO.setStructuralWeight(uldTypeVO.getStructuralWtLmt());
					uldDetailsVO.setStructuralWeightUnit(uldDetailsVO.getStructuralWeight().getDisplayUnit());
				}
				double baseLength =  
						uldDetailsVO.getBaseLength()!=null ? uldDetailsVO.getBaseLength().getRoundedSystemValue() : 0;
				uldDetailsVO.setBaseLength(
						baseLength<=0 ? uldTypeVO.getBaseDimLength():uldDetailsVO.getBaseLength());

				double baseHeight =  
						uldDetailsVO.getBaseHeight()!=null ? uldDetailsVO.getBaseHeight().getRoundedSystemValue() : 0;
				uldDetailsVO.setBaseHeight(
						baseHeight<=0 ? uldTypeVO.getBaseDimHeight() :uldDetailsVO.getBaseHeight());

				double baseWidth =  
						uldDetailsVO.getBaseWidth()!=null ? uldDetailsVO.getBaseWidth().getRoundedSystemValue() : 0;
				uldDetailsVO.setBaseWidth(
						baseWidth<=0 ? uldTypeVO.getBaseDimWidth():uldDetailsVO.getBaseWidth());
			}
		}catch (SystemException e) {
			//log.log(Log.SEVERE, "doPopulateULDDetailsVO>>>>>findULDTypeStructuralDetails error");
		}catch(WSBusinessException e){
			//log.log(Log.SEVERE, "doPopulateULDDetailsVO>>>>>findULDTypeStructuralDetails error");
    }
	return uldDetailsVO;
	}
	public  ValidateULDDetailResponseType validateULD(ValidateULDDetailRequestType validateULDDetailRequestType)
 {
		LogonAttributes logonAttributes = null;
		try {
			logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
		} catch (SystemException e) {
		}
		ULDValidationVO uldValidationVO = new ULDValidationVO();
		ValidateULDDetailResponseType responseType = new ValidateULDDetailResponseType();
		try {
			uldValidationVO = despatchRequest("validateULD",
					logonAttributes.getCompanyCode(),
					validateULDDetailRequestType.getRequestData()
							.getUldnumber());
		} catch (WSBusinessException businessException) {
			if (businessException.getErrors() != null
					&& businessException.getErrors().size() > 0) {
				for (ErrorVO error : businessException.getErrors()) {
					return responseType = populateErrorResponse(
							validateULDDetailRequestType, error,
							uldValidationVO);
				}
			}
		} catch (SystemException systemException) {
			for (ErrorVO error : systemException.getErrors()) {
				return responseType = populateErrorResponse(
						validateULDDetailRequestType, error, uldValidationVO);
			}
		}
		if (uldValidationVO == null ) {  
			return responseType =populateErrorResponse(
					validateULDDetailRequestType, null,
					uldValidationVO);
		} 
		responseType = doPopulateValidateUldResponseType(uldValidationVO,
				validateULDDetailRequestType);
		return responseType;
	}
	private ValidateULDDetailResponseType doPopulateValidateUldResponseType(ULDValidationVO detailsVO, ValidateULDDetailRequestType validateULDDetailRequestType) {
		MessageHeaderType messageHeaderType = new MessageHeaderType();
		ValidateULDDetailResponseType responseType = new ValidateULDDetailResponseType();
		if(validateULDDetailRequestType.getMessageHeader()!= null ){
		messageHeaderType.setMessageType(validateULDDetailRequestType.getMessageHeader().getMessageType());
		messageHeaderType.setSourceSystem(validateULDDetailRequestType.getMessageHeader().getSourceSystem());
		responseType.setMessageHeader(messageHeaderType);
		}
		ValidateULDResponseType uldResponseDetailsType = new ValidateULDResponseType();
		uldResponseDetailsType.setUldValidation(populateULDValidationType(detailsVO));
	    if(validateULDDetailRequestType.getRequestData().getRequestId()!=null){
			uldResponseDetailsType.setRequestId(validateULDDetailRequestType.getRequestData().getRequestId());
		}
		responseType.setRequestData(uldResponseDetailsType);
		return responseType;
	}
	private ULDValidationType populateULDValidationType(ULDValidationVO detailsVO){
		ULDValidationType uldValidationType=new ULDValidationType();
		uldValidationType.setOwnerAirlineCode(detailsVO.getOwnerAirlineCode());
		uldValidationType.setOwnerAirlineIdentifier(String.valueOf(detailsVO.getOwnerAirlineIdentifier()));
		uldValidationType.setOwnerStation(detailsVO.getOwnerStation());
		uldValidationType.setCurrentStation(detailsVO.getCurrentStation());
		uldValidationType.setOverallStatus(detailsVO.getOverallStatus());
		uldValidationType.setDamageStatus(detailsVO.getDamageStatus());
		uldValidationType.setUldNature(detailsVO.getUldNature());
		uldValidationType.setLocation(detailsVO.getLocation());
		return uldValidationType;
	}
	private ValidateULDDetailResponseType populateErrorResponse(
			ValidateULDDetailRequestType validateULDDetailRequestType,
			ErrorVO error, ULDValidationVO detailsVO) {
		ErrorDetailType errorDetailType = new ErrorDetailType();
		ValidateULDResponseType detailsType = new ValidateULDResponseType();
		ValidateULDDetailResponseType detailsResponseType = new ValidateULDDetailResponseType();
		if (validateULDDetailRequestType.getMessageHeader() != null) {
			MessageHeaderType messageHeaderType = new MessageHeaderType();
			messageHeaderType.setMessageType(validateULDDetailRequestType
					.getMessageHeader().getMessageType());
			messageHeaderType.setSourceSystem(validateULDDetailRequestType
					.getMessageHeader().getSourceSystem());
			detailsResponseType.setMessageHeader(messageHeaderType);
		}
		detailsType.setRequestId(validateULDDetailRequestType
				.getRequestData().getRequestId());
		if (detailsVO == null ) {
			errorDetailType.setErrorDesc("Uld not in the stock");
			detailsType.getErrorDetails().add(errorDetailType);
		} else if (error.getErrorCode() != null
				&& error.getErrorCode().length() > 0) {
			errorDetailType.setErrorCode(error.getErrorCode());
			detailsType.getErrorDetails().add(errorDetailType);
		}
		detailsType.setUldValidation(new ULDValidationType());
		detailsResponseType.setRequestData(detailsType); 
		return detailsResponseType;
	}
	
	/**
	 * 
	 * 	Method		:	ULDDefaultsServiceBaseImpl.nullOrEmpty
	 *	Added by 	:	A-5444 on 15-Aug-2019
	 * 	Used for 	:
	 *	Parameters	:	@param value
	 *	Parameters	:	@return 
	 *	Return type	: 	boolean
	 */
	private boolean nullOrEmpty(String value){
		return (value==null || value.trim().isEmpty());
	}
	}
	
