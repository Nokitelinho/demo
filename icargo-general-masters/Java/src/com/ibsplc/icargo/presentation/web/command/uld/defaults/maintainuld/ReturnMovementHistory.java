/*
 * ReturnMovementHistory.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDNavigationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is invoked clearing the screen
 *
 * @author A-2001
 */
public class ReturnMovementHistory extends BaseCommand {
	
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	private static final String RETURNMOVEMENTHISTORY_SUCCESS = "retun_success";

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
    	/*clearForm(maintainUldForm);
    	maintainULDSession.setULDMultipleVOs(null);
    	maintainULDSession.setUldNosForNavigation(null);
    	maintainULDSession.setUldNumbers(null);
    	maintainULDSession.setUldNumbersSaved(null);
    	maintainULDSession.setULDVO(null);
		maintainUldForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	*/
    	ULDNavigationVO uldNavigationVO = maintainULDSession.getUldNavigationVO();
    	maintainUldForm.setCurrentPage(uldNavigationVO.getCurrentPage());
    	maintainUldForm.setDisplayPage(uldNavigationVO.getDisplayPage());
    	maintainUldForm.setLastPageNum(uldNavigationVO.getLastPageNum());
    	maintainUldForm.setTotalRecords(uldNavigationVO.getTotalrecords());
    	maintainUldForm.setScreenloadstatus(uldNavigationVO.getScreenloadStatus());
    	ULDVO uldVO = maintainULDSession.getTemporaryUldVO();
    	loadMaintainULDForm(uldVO,maintainUldForm);
    	
    	maintainUldForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
		invocationContext.target = RETURNMOVEMENTHISTORY_SUCCESS;

    }
    /**
	 * @param uldVO
	 * @param maintainULDForm 
	 * @return 
	 */
	
	private void loadMaintainULDForm(ULDVO uldVO, 
						MaintainULDForm maintainULDForm) {
		if(uldVO.getUldNumber() != null) {
			maintainULDForm.setUldNumber(uldVO.getUldNumber());
		}
		
		if(uldVO.getUldType() != null) {
			maintainULDForm.setUldType(uldVO.getUldType());
		}
		if(uldVO.getUldContour() != null) {
			maintainULDForm.setUldContour(uldVO.getUldContour());
		}
		
		maintainULDForm.setDisplayTareWeight(Double.toString(uldVO.getTareWeight()!=null?uldVO.getTareWeight().getRoundedDisplayValue():0.0));
		maintainULDForm.setTareWtMeasure(uldVO.getTareWeight());
		/*if(uldTypeVO.getTareWtUnit() != null) {
			maintainULDForm.setDisplayTareWeightUnit(
										uldTypeVO.getTareWtUnit());
		}*/
		maintainULDForm.setDisplayStructuralWeight(Double.toString(uldVO.getStructuralWeight()!=null?uldVO.getStructuralWeight().getRoundedDisplayValue():0.0));
		maintainULDForm.setStructWeightMeasure(uldVO.getStructuralWeight());
		if(uldVO.getStructuralWeight() != null) {
			maintainULDForm.setDisplayStructuralWeightUnit(uldVO.getStructuralWeight().getDisplayUnit());
		}else {
			maintainULDForm.setDisplayStructuralWeightUnit("");
		}
		
		maintainULDForm.setBaseLengthMeasure(uldVO.getBaseLength());
		maintainULDForm.setDisplayBaseLength(Double.toString(uldVO.getBaseLength()!=null?uldVO.getBaseLength().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseWidthMeasure(uldVO.getBaseWidth());
		maintainULDForm.setDisplayBaseWidth(Double.toString(uldVO.getBaseWidth()!=null?uldVO.getBaseWidth().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseHeightMeasure(uldVO.getBaseHeight());
		maintainULDForm.setDisplayBaseHeight(Double.toString(uldVO.getBaseHeight()!=null?uldVO.getBaseHeight().getRoundedDisplayValue():0.0));

		
		if(uldVO.getOwnerAirlineCode() != null) {
			maintainULDForm.setOwnerAirlineCode(
									uldVO.getOwnerAirlineCode());
		}
		if(uldVO.getOperationalAirlineCode() != null) {
			maintainULDForm.setOperationalAirlineCode(
									uldVO.getOperationalAirlineCode());
		}
		if(uldVO.getCurrentStation() != null) {
			maintainULDForm.setCurrentStation(
											uldVO.getCurrentStation());
		}
		if(uldVO.getOverallStatus() != null) {
			maintainULDForm.setOverallStatus(
											uldVO.getOverallStatus());
		}
		if(uldVO.getCleanlinessStatus() != null) {
			maintainULDForm.setCleanlinessStatus(
											uldVO.getCleanlinessStatus());
		}
		if(uldVO.getOwnerStation() != null) {
			maintainULDForm.setOwnerStation(
											uldVO.getOwnerStation());
		}
		if(uldVO.getLocation() != null) {
			maintainULDForm.setLocation(uldVO.getLocation());
		}
		if(uldVO.getUldNature() != null) {
			maintainULDForm.setUldNature(uldVO.getUldNature());
		}
		if(uldVO.getFacilityType() != null) {
			maintainULDForm.setFacilityType(uldVO.getFacilityType());
		}
		if(uldVO.getDamageStatus() != null) {
			maintainULDForm.setDamageStatus(uldVO.getDamageStatus());
		}
		if(uldVO.getVendor() != null) {
			maintainULDForm.setVendor(uldVO.getVendor());
		}
		maintainULDForm.setTransitStatus(uldVO.getTransitStatus());
		if(uldVO.getManufacturer() != null) {
			maintainULDForm.setManufacturer(uldVO.getManufacturer());
		}
		if(uldVO.getUldSerialNumber() != null) {
			maintainULDForm.setUldSerialNumber(
											uldVO.getUldSerialNumber());
		}
		if(uldVO.getPurchaseDate() != null) {
			maintainULDForm.setPurchaseDate(
				TimeConvertor.toStringFormat(
				uldVO.getPurchaseDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT));
		}
		if(uldVO.getPurchaseInvoiceNumber() != null) {
			maintainULDForm.setPurchaseInvoiceNumber(
									uldVO.getPurchaseInvoiceNumber());
		}
		
		maintainULDForm.setUldPrice(Double.toString(uldVO.getDisplayUldPrice()));
		
		if(uldVO.getUldPriceUnit() != null) {
			maintainULDForm.setUldPriceUnit(uldVO.getUldPriceUnit());
		}
		
		maintainULDForm.setIataReplacementCost(
						Double.toString(uldVO.getDisplayIataReplacementCost()));
		
		if(uldVO.getDisplayIataReplacementCostUnit() != null) {
			maintainULDForm.setIataReplacementCostUnit(
								uldVO.getDisplayIataReplacementCostUnit());
		}
		
		maintainULDForm.setCurrentValue(
							Double.toString(uldVO.getDisplayCurrentValue()));
		
		
		if(uldVO.getCurrentValueUnit() != null) {
			maintainULDForm.setCurrentValueUnit(uldVO.getCurrentValueUnit());
		}
		if(uldVO.getRemarks() != null) {
			maintainULDForm.setRemarks(uldVO.getRemarks());
		}
		
	}
}
