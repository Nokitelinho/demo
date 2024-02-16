/*
 * ClearMaintainULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is invoked clearing the screen
 *
 * @author A-2001
 */
public class ClearMaintainULDCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("clearUld");
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String CLEAR_FAILURE = "clear_success";

    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainULDForm maintainUldForm = 
    				(MaintainULDForm)invocationContext.screenModel;
    	MaintainULDSession maintainULDSession = 
    			getScreenSession(MODULE, SCREENID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510

		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	log.log(Log.INFO, "can clear", maintainUldForm.getCloseStatus());
		if(!("canclear").equals(maintainUldForm.getCloseStatus())) {
	      	if(maintainULDSession.getULDMultipleVOs() != null &&
	      			maintainULDSession.getULDMultipleVOs().size() > 0) {
	      		ArrayList<ULDVO> uldVOs = new ArrayList<ULDVO>(maintainULDSession.getULDMultipleVOs().values());
	      		int currentPage = Integer.parseInt(maintainUldForm.getCurrentPage());
    	    	String currentUldNumber = maintainULDSession.getUldNosForNavigation().get(currentPage-1);
    	    	ULDVO uldVO = maintainULDSession.getULDMultipleVOs().get(currentUldNumber);
	      		if(isUldUpdated(uldVO,maintainUldForm)) {
	      			log.log(Log.SEVERE,
							"uld updated************************uldVO>>>>>",
							uldVO);
					log
							.log(
									Log.SEVERE,
									"uld updated************************maintainUldForm>>>>>",
									maintainUldForm);
					ErrorVO error  = new ErrorVO("uld.defaults.unsaveddataexists");
	      			error.setErrorDisplayType(ErrorDisplayType.WARNING);
	      			errors.add(error);
	      		}
	      		else {
		      		for(ULDVO uldMultipleVO : uldVOs) {
		      			if(ULDVO.OPERATION_FLAG_UPDATE.equals(uldMultipleVO.getOperationalFlag())) {
		      				ErrorVO error  = new ErrorVO("uld.defaults.unsaveddataexists");
		      				error.setErrorDisplayType(ErrorDisplayType.WARNING);
			      			errors.add(error);
		      			}
		      		}
	      		}
	      	}
	      	else  {
	      		ULDVO uldVO = maintainULDSession.getULDVO();
	      		boolean isupdated = false;
	      		if(maintainULDSession.getUldNumbersSaved() != null) {
	      			if((maintainULDSession.getUldNumbersSaved().size() > 0)) {
	      				isupdated= true;
	      				log
								.log(
										Log.SEVERE,
										"uldnumbers saved************************>>>>>",
										isupdated);
	      			}
	      		}
	      		if(uldVO != null && maintainUldForm.getTotalNoofUlds() != null 
	      						&& maintainUldForm.getTotalNoofUlds().trim().length() > 0) {
		      		if(ULDVO.OPERATION_FLAG_INSERT.equals(uldVO.getOperationalFlag())) {
		      			isupdated= true;	
		      			log.log(Log.SEVERE,
								"uldvo insert************************>>>>>",
								isupdated);
		      		}
		      		else if(isUldUpdated(uldVO,maintainUldForm)){
		      			isupdated= true;
		      			log.log(Log.SEVERE,
								"uldvo update************************>>>>>",
								isupdated);
		      		}
	      		}
	      		if(isupdated) {
	      			ErrorVO error  = new ErrorVO("uld.defaults.unsaveddataexists");
	      			error.setErrorDisplayType(ErrorDisplayType.WARNING);
	      			errors.add(error);
	      		}
	      	}
	      	if(errors.size() > 0) {
	      		log.log(Log.SEVERE, "************************>>>>>", errors.toString());
				maintainUldForm.setCloseStatus("whethertoclear");
	      		invocationContext.addAllError(errors);
	      		invocationContext.target = CLEAR_FAILURE;
	      		return;
	      	} 
      	}
      	if("SAVE_ULD_SUCCESS".equals(maintainUldForm.getSaveIndFlag())){
      		
      		invocationContext.addError(new ErrorVO("uld.defaults.maintainuld.savedsuccessfully"));
      	}
    	clearForm(maintainUldForm);
    	if(maintainULDSession.getWeightVO() != null) {
    		maintainUldForm.setDisplayTareWeightUnit(maintainULDSession.getWeightVO().getUnitCode());
    		maintainUldForm.setDisplayStructuralWeightUnit(maintainULDSession.getWeightVO().getUnitCode());
    	}
    	
    	// Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471)
    	// Commented by Manaf for INT ULD510
      	//Collection<ErrorVO> error = new ArrayList<ErrorVO>();
      	//removed by nisha on 20Apr08
    
			log
					.log(Log.INFO, "crate multiple", maintainUldForm.getStatusFlag());
			if("check".equals(maintainUldForm.getStatusFlag())){
				log.log(Log.INFO, "crate multiple", maintainUldForm.getCreateMultiple());
			}else{
			maintainUldForm.setOperationalAirlineCode("");
			}
		
    	//Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471) ends
    	
    	maintainULDSession.setULDMultipleVOs(null);
    	maintainULDSession.setUldNosForNavigation(null);
    	maintainULDSession.setUldNumbers(null);
    	maintainULDSession.setUldNumbersSaved(null);
    	maintainULDSession.setULDVO(null);
    	maintainUldForm.setStructuralFlag("");
		maintainUldForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	
		invocationContext.target = CLEAR_SUCCESS;

    }
    
    
    private void clearForm(MaintainULDForm maintainULDForm) {
    	maintainULDForm.setCloseStatus("");
		maintainULDForm.setUldNumber("");
		maintainULDForm.setUldType("");
		maintainULDForm.setOperationalAirlineCode("");
		maintainULDForm.setUldContour("");
		maintainULDForm.setDisplayTareWeight("");
		//maintainULDForm.setDisplayTareWeightUnit("");
		maintainULDForm.setDisplayStructuralWeight("");
		maintainULDForm.setDisplayStructuralWeightUnit("");
		maintainULDForm.setDisplayBaseLength("");
		maintainULDForm.setDisplayBaseWidth("");
		maintainULDForm.setDisplayBaseHeight("");
		maintainULDForm.setTransitStatus("");
		maintainULDForm.setDisplayDimensionUnit("");
		maintainULDForm.setOwnerAirlineCode("");  //uncomented by shemeer p.p T-1824 for the bug 105925 on 18/2/11
		maintainULDForm.setUldOwnerCode("");
		maintainULDForm.setOperationalOwnerAirlineCode("");
		maintainULDForm.setCurrentStation("");
		maintainULDForm.setOwnerStation("");
		maintainULDForm.setLocation("");
		maintainULDForm.setUldNature("GEN");
		maintainULDForm.setFacilityType("");
		maintainULDForm.setVendor("");
		maintainULDForm.setManufacturer("");
		maintainULDForm.setUldSerialNumber("");
		maintainULDForm.setPurchaseDate("");
		maintainULDForm.setPurchaseInvoiceNumber("");
		maintainULDForm.setUldPrice("");
		//maintainULDForm.setUldPriceUnit("");
		maintainULDForm.setIataReplacementCost("");
		//maintainULDForm.setIataReplacementCostUnit("");
		maintainULDForm.setCurrentValue("");
		//maintainULDForm.setCurrentValueUnit("");
		maintainULDForm.setTotalNoofUlds("");
		maintainULDForm.setRemarks("");
		maintainULDForm.setOverallStatus("O");
    	maintainULDForm.setDamageStatus("N");
    	maintainULDForm.setCleanlinessStatus("C");
		maintainULDForm.setCreateMultiple(false);
		maintainULDForm.setSaveIndFlag("");
		maintainULDForm.setLifeSpan("");
    	maintainULDForm.setManufactureDate("");
    	maintainULDForm.setTsoNumber("");
    	AreaDelegate areaDelegate = new AreaDelegate();
    	String defCur="";
		try {
			defCur = areaDelegate.defaultCurrencyForStation(
					getApplicationSession().getLogonVO().getCompanyCode(),
					getApplicationSession().getLogonVO().getStationCode());
		} catch (BusinessDelegateException e) {
			e.getMessage();
		}
		maintainULDForm.setCurrency(defCur);
		maintainULDForm.setIataReplacementCostUnit(defCur);
		maintainULDForm.setCurrentValueUnit(defCur);
	}
    
    private boolean isUldUpdated(ULDVO uldVO, 
			MaintainULDForm maintainULDForm) {
	  
		log.entering("isUldUpdated","isUldUpdated");
		boolean isUpdated = false;
		
		if(uldVO.getUldContour() != null) {
			if(!uldVO.getUldContour().equals(maintainULDForm.getUldContour())) {
				isUpdated = true;
				//return isUpdated;
			}
		} else {
			if(maintainULDForm.getUldContour() != null &&		
				maintainULDForm.getUldContour().trim().length() > 0 ) {
				isUpdated = true;
				//return isUpdated;
			}
		}    	
		log.exiting("isUldUpdated+++++++++++*********>>>","1"+Boolean.toString(isUpdated));
		
		if(maintainULDForm.getDisplayTareWeight() != null &&
				maintainULDForm.getDisplayTareWeight().trim().length() > 0) {
			if(getDoubleValue(uldVO.getTareWeight(),ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayTareWeight())) {
				isUpdated = true;
				//return isUpdated;
			}
			
		}
		else {
			//if(uldVO.getDisplayTareWeight() != Double.parseDouble(maintainULDForm.getDisplayTareWeight())) 
			isUpdated = true;
			//return isUpdated;
		}
		
		log.exiting("isUldUpdated+++++++++++*********>>>","2"+Boolean.toString(isUpdated));
		/*if(maintainULDForm.getDisplayTareWeight() != null &&
				maintainULDForm.getDisplayTareWeight().trim().length() > 0) {
			if(uldVO.getTareWeight(). != Double.parseDouble(maintainULDForm.getTareWeight())) {
				isUpdated = true;
				//return isUpdated;
			}
			
		}
		else {
			isUpdated = true;
			//return isUpdated;
		}*/
		log.exiting("isUldUpdated+++++++++++*********>>>","3"+Boolean.toString(isUpdated));
		/*if(uldVO.getDisplayTareWeightUnit() != null) {
			if(!uldVO.getDisplayTareWeightUnit().equals(maintainULDForm.getDisplayTareWeightUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getDisplayTareWeightUnit() != null &&
				maintainULDForm.getDisplayTareWeightUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}*/
		log.exiting("isUldUpdated+++++++++++*********>>>","4"+Boolean.toString(isUpdated));
		if(maintainULDForm.getDisplayStructuralWeight() != null &&
				maintainULDForm.getDisplayStructuralWeight().trim().length() > 0) {
			if(getDoubleValue(uldVO.getStructuralWeight(),ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayStructuralWeight())) {
				isUpdated = true;
				//return isUpdated;
			}
			
		}
		else {
			isUpdated = true;
			//return isUpdated;
		}
		log.exiting("isUldUpdated+++++++++++*********>>>","5"+Boolean.toString(isUpdated));
		if(getUnitType(uldVO.getStructuralWeight(),ULDVO.DIS_VAL) != null) {
			if(!getUnitType(uldVO.getStructuralWeight(),ULDVO.DIS_VAL).equals(maintainULDForm.getDisplayStructuralWeightUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getDisplayStructuralWeightUnit() != null &&
				maintainULDForm.getDisplayStructuralWeightUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		log.exiting("isUldUpdated+++++++++++*********>>>","6"+Boolean.toString(isUpdated));
		if(maintainULDForm.getDisplayBaseLength() != null &&
				maintainULDForm.getDisplayBaseLength().trim().length() > 0) {
			if(getDoubleValue(uldVO.getBaseLength(),ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayBaseLength())) {
				isUpdated = true;
				//return isUpdated;
			}
			
		}
		else {
			isUpdated = true;
			//return isUpdated;
		}
		log.exiting("isUldUpdated+++++++++++*********>>>","7"+Boolean.toString(isUpdated));
		if(maintainULDForm.getDisplayBaseWidth() != null &&
				maintainULDForm.getDisplayBaseWidth().trim().length() > 0) {
			if(getDoubleValue(uldVO.getBaseWidth(),ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayBaseWidth())) {
				isUpdated = true;
				//return isUpdated;
			}
			
		}
		else {
			isUpdated = true;
			//return isUpdated;
		}		
		log.exiting("isUldUpdated+++++++++++*********>>>","8"+Boolean.toString(isUpdated));
		if(maintainULDForm.getDisplayBaseHeight() != null &&
				maintainULDForm.getDisplayBaseHeight().trim().length() > 0) {
			if(getDoubleValue(uldVO.getBaseHeight(),ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayBaseHeight())) {
				isUpdated = true;
				//return isUpdated;
			}
			
		}
		else {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","9"+Boolean.toString(isUpdated));
		if(getUnitType(uldVO.getBaseHeight(),ULDVO.DIS_VAL) != null) {
			if(!getUnitType(uldVO.getBaseHeight(),ULDVO.DIS_VAL).equals(maintainULDForm.getDisplayDimensionUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getDisplayDimensionUnit() != null &&
				maintainULDForm.getDisplayDimensionUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","10"+Boolean.toString(isUpdated));
		if(uldVO.getOperationalAirlineCode() != null) {
			if(!uldVO.getOperationalAirlineCode().equals(maintainULDForm.getOperationalAirlineCode())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getOperationalAirlineCode() != null &&
				maintainULDForm.getOperationalAirlineCode().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","11"+Boolean.toString(isUpdated));
		if(uldVO.getCurrentStation() != null) {
			if(!uldVO.getCurrentStation().equals(maintainULDForm.getCurrentStation())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getCurrentStation() != null &&
				maintainULDForm.getCurrentStation().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","12"+Boolean.toString(isUpdated));
		if(uldVO.getOverallStatus() != null) {
			if(!uldVO.getOverallStatus().equals(maintainULDForm.getOverallStatus())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getOverallStatus() != null &&
				maintainULDForm.getOverallStatus().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","13"+Boolean.toString(isUpdated));
		if(uldVO.getCleanlinessStatus() != null) {
			if(!uldVO.getCleanlinessStatus().equals(maintainULDForm.getCleanlinessStatus())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getCleanlinessStatus() != null &&
				maintainULDForm.getCleanlinessStatus().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","14"+Boolean.toString(isUpdated));
		if(uldVO.getOwnerStation() != null) {
			if(!uldVO.getOwnerStation().equals(maintainULDForm.getOwnerStation())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getOwnerStation() != null &&
				maintainULDForm.getOwnerStation().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","15"+Boolean.toString(isUpdated));
		if(uldVO.getLocation() != null) {
			if(!uldVO.getLocation().equals(maintainULDForm.getLocation())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		
		else if(maintainULDForm.getLocation() != null &&
				maintainULDForm.getLocation().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		
		log.exiting("isUldUpdated+++++++++++*********>>>","15"+Boolean.toString(isUpdated));
		if(uldVO.getUldNature() != null) {
			if(!uldVO.getUldNature().equals(maintainULDForm.getUldNature())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		
		else if(maintainULDForm.getUldNature() != null &&
				maintainULDForm.getUldNature().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		
		if(uldVO.getFacilityType() != null) {
			if(!uldVO.getFacilityType().equals(maintainULDForm.getFacilityType())) {
				isUpdated = true;
				//return isUpdated;
			}
		}else if(maintainULDForm.getFacilityType() != null &&
				maintainULDForm.getFacilityType().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		
		log.exiting("isUldUpdated+++++++++++*********>>>","16"+Boolean.toString(isUpdated));
		if(uldVO.getDamageStatus() != null) {
			if(!uldVO.getDamageStatus().equals(maintainULDForm.getDamageStatus())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getDamageStatus() != null &&
				maintainULDForm.getDamageStatus().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","17"+Boolean.toString(isUpdated));
		if(uldVO.getVendor() != null) {
			if(!uldVO.getVendor().equals(maintainULDForm.getVendor())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getVendor() != null &&
				maintainULDForm.getVendor().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","18"+Boolean.toString(isUpdated));
		if(uldVO.getManufacturer() != null) {
			if(!uldVO.getManufacturer().equals(maintainULDForm.getManufacturer())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getManufacturer() != null &&
				maintainULDForm.getManufacturer().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","19"+Boolean.toString(isUpdated));
		if(uldVO.getUldSerialNumber() != null) {
			if(!uldVO.getUldSerialNumber().equals(maintainULDForm.getUldSerialNumber())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getUldSerialNumber() != null &&
				maintainULDForm.getUldSerialNumber().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","20"+Boolean.toString(isUpdated));
		if(uldVO.getPurchaseDate() != null) {
			if(!(TimeConvertor.toStringFormat(
					uldVO.getPurchaseDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT).
					equals(maintainULDForm.getPurchaseDate()))) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getPurchaseDate() != null &&
				maintainULDForm.getPurchaseDate().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","21"+Boolean.toString(isUpdated));
		if(uldVO.getPurchaseInvoiceNumber() != null) {
			if(!uldVO.getPurchaseInvoiceNumber().equals(maintainULDForm.getPurchaseInvoiceNumber())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getPurchaseInvoiceNumber() != null &&
				maintainULDForm.getPurchaseInvoiceNumber().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","22"+Boolean.toString(isUpdated));
		if(maintainULDForm.getUldPrice() != null &&
				maintainULDForm.getUldPrice().trim().length() > 0) {
			if(uldVO.getDisplayUldPrice() != Double.parseDouble(maintainULDForm.getUldPrice())) {
				isUpdated = true;
				//return isUpdated;
			}
			
		}
		else {
			isUpdated = true;
			//return isUpdated;
		}
		log.exiting("isUldUpdated+++++++++++*********>>>","23"+Boolean.toString(isUpdated));
		if(uldVO.getUldPriceUnit() != null) {
			if(!uldVO.getUldPriceUnit().equals(maintainULDForm.getUldPriceUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getUldPriceUnit() != null &&
				maintainULDForm.getUldPriceUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","24"+Boolean.toString(isUpdated));
		if(maintainULDForm.getIataReplacementCost() != null &&
				maintainULDForm.getIataReplacementCost().trim().length() > 0) {
			if(uldVO.getDisplayIataReplacementCost() != Double.parseDouble(maintainULDForm.getIataReplacementCost())) {
				isUpdated = true;
				//return isUpdated;
			}
			
		}
		else {
			isUpdated = true;
			//return isUpdated;
		}
		log.exiting("isUldUpdated+++++++++++*********>>>","25"+Boolean.toString(isUpdated));
		if(uldVO.getDisplayIataReplacementCostUnit() != null) {
			if(!uldVO.getDisplayIataReplacementCostUnit().equals(maintainULDForm.getIataReplacementCostUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getIataReplacementCostUnit() != null &&
				maintainULDForm.getIataReplacementCostUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}	
		log.exiting("isUldUpdated+++++++++++*********>>>","26"+Boolean.toString(isUpdated));
		if(maintainULDForm.getCurrentValue() != null &&
				maintainULDForm.getCurrentValue().trim().length() > 0) {
			if(uldVO.getDisplayCurrentValue() != Double.parseDouble(maintainULDForm.getCurrentValue())) {
				isUpdated = true;
				//return isUpdated;
			}
			
		}
		else {
			isUpdated = true;
			//return isUpdated;
		}
		log.exiting("isUldUpdated+++++++++++*********>>>","27"+Boolean.toString(isUpdated));	
		if(uldVO.getCurrentValueUnit() != null) {
			if(!uldVO.getCurrentValueUnit().equals(maintainULDForm.getCurrentValueUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getCurrentValueUnit() != null &&
				maintainULDForm.getCurrentValueUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		log.exiting("isUldUpdated+++++++++++*********>>>","28"+Boolean.toString(isUpdated));
		if(uldVO.getRemarks() != null) {
			if(!uldVO.getRemarks().equals(maintainULDForm.getRemarks())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getRemarks() != null &&
				maintainULDForm.getRemarks().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		log.exiting("isUldUpdated+++++++++++*********>>>","29"+Boolean.toString(isUpdated));
		log.exiting("isUldUpdated","isUldUpdated");
		 log.log(Log.FINE, "isUldUpdated  =======>>>", isUpdated);
		return isUpdated;

    }
    public double getDoubleValue(Measure measureObj, String valType){
     Double d = 0.0;
     if(measureObj !=null)
    	{
    	if("S".equalsIgnoreCase(valType)){
    		d=measureObj.getRoundedSystemValue();
    	}else{
    		d=measureObj.getRoundedDisplayValue();
    	}
    	}
    	return d;
    }
    public String getUnitType(Measure measureObj, String valType){
        String s = null;
        if(measureObj !=null)
       	{
       	if("S".equalsIgnoreCase(valType)){
       		s=measureObj.getSystemUnit();
       	}else{
       		s=measureObj.getDisplayUnit();
       					}
       	}
       	return s;
       }
}
