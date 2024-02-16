/*
 * MovementHistoryLoadCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDNavigationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;

/**
 * This command class is invoked clearing the screen
 *
 * @author A-2001
 */
public class MovementHistoryLoadCommand extends BaseCommand {

	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	private static final String MOVEMENTHISTORYLIST_SUCCESS = "movementlist_success";

	private static final String MOVEMENTHISTORYCREATE_SUCCESS = "movementcreate_success";

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
    	ULDVO uldVO = new ULDVO();
    	loadUldFromForm(uldVO,maintainUldForm);
    	maintainULDSession.setTemporaryUldVO(uldVO);
    	ULDNavigationVO uldNavigationVO = new ULDNavigationVO();
    	uldNavigationVO.setCurrentPage(maintainUldForm.getCurrentPage());
    	uldNavigationVO.setDisplayPage(maintainUldForm.getDisplayPage());
    	uldNavigationVO.setLastPageNum(maintainUldForm.getLastPageNum());
    	uldNavigationVO.setTotalrecords(maintainUldForm.getTotalRecords());
    	uldNavigationVO.setScreenloadStatus(maintainUldForm.getScreenloadstatus());

    	maintainULDSession.setUldNavigationVO(uldNavigationVO);
    	maintainUldForm.setStatusFlag("toMovementHistory");
    	if(maintainUldForm.getUldNumber() != null &&
    			maintainUldForm.getUldNumber().trim().length() >0) {
    		invocationContext.target = MOVEMENTHISTORYLIST_SUCCESS;

    	}
    	else {
    		invocationContext.target = MOVEMENTHISTORYCREATE_SUCCESS;

    	}
	}
    /**
     *
     * @param uldVO
     * @param maintainULDForm
     * @param companyCode
     */
    private void loadUldFromForm(ULDVO uldVO,MaintainULDForm maintainULDForm) {

    	uldVO.setUldNumber(maintainULDForm.getUldNumber().toUpperCase());
		uldVO.setCleanlinessStatus(maintainULDForm.getCleanlinessStatus());
		uldVO.setCurrentStation(maintainULDForm.getCurrentStation().toUpperCase());
		if(maintainULDForm.getCurrentValue()!= null
				&& maintainULDForm.getCurrentValue().trim().length() > 0) {
			uldVO.setDisplayCurrentValue(Double.parseDouble(maintainULDForm.getCurrentValue()));
		}
		uldVO.setCurrentValueUnit(maintainULDForm.getCurrentValueUnit());
		uldVO.setDamageStatus(maintainULDForm.getDamageStatus());
		/*if(maintainULDForm.getDisplayBaseHeight()!= null
				&& maintainULDForm.getDisplayBaseHeight().trim().length() > 0) {
		uldVO.setDisplayBaseHeight(
				Double.parseDouble(maintainULDForm.getDisplayBaseHeight()));
    	}
		if(maintainULDForm.getDisplayBaseLength()!= null
				&& maintainULDForm.getDisplayBaseLength().trim().length() > 0) {
			uldVO.setDisplayBaseLength(
					Double.parseDouble(maintainULDForm.getDisplayBaseLength()));
		}
		if(maintainULDForm.getDisplayBaseWidth()!= null
				&& maintainULDForm.getDisplayBaseWidth().trim().length() > 0) {
			uldVO.setDisplayBaseWidth(
					Double.parseDouble(maintainULDForm.getDisplayBaseWidth()));
		}
		uldVO.setDisplayDimensionUnit(maintainULDForm.getDisplayDimensionUnit());*/
		
		uldVO.setBaseHeight(maintainULDForm.getBaseHeightMeasure());
    	uldVO.setBaseLength(maintainULDForm.getBaseLengthMeasure());
		uldVO.setBaseWidth(maintainULDForm.getBaseWidthMeasure());
		
		/*if(maintainULDForm.getDisplayStructuralWeight()!= null
				&& maintainULDForm.getDisplayStructuralWeight().trim().length() > 0) {
			uldVO.setDisplayStructuralWeight(
			Double.parseDouble(maintainULDForm.getDisplayStructuralWeight()));
		}
		uldVO.setDisplayStructuralWeightUnit(
						maintainULDForm.getDisplayStructuralWeightUnit());*/
		uldVO.setStructuralWeight(maintainULDForm.getStructWeightMeasure());
		
		/*if(maintainULDForm.getDisplayTareWeight()!= null
				&& maintainULDForm.getDisplayTareWeight().trim().length() > 0) {
			uldVO.setDisplayTareWeight(
					Double.parseDouble(maintainULDForm.getDisplayTareWeight()));
		}
		uldVO.setDisplayTareWeightUnit(maintainULDForm.getDisplayStructuralWeightUnit());*/
		uldVO.setTareWeight(maintainULDForm.getTareWtMeasure());
		if(maintainULDForm.getIataReplacementCost()!= null
				&& maintainULDForm.getIataReplacementCost().trim().length() > 0) {
			uldVO.setDisplayIataReplacementCost(
				Double.parseDouble(maintainULDForm.getIataReplacementCost()));
		}
		uldVO.setDisplayIataReplacementCostUnit(
							maintainULDForm.getIataReplacementCostUnit());
		uldVO.setLocation(maintainULDForm.getLocation().toUpperCase());
		uldVO.setUldNature(maintainULDForm.getUldNature().toUpperCase());
		uldVO.setFacilityType(maintainULDForm.getFacilityType().toUpperCase());
		uldVO.setManufacturer(maintainULDForm.getManufacturer());
		uldVO.setOverallStatus(maintainULDForm.getOverallStatus());
		uldVO.setOwnerStation(maintainULDForm.getOwnerStation().toUpperCase());
		if(maintainULDForm.getPurchaseDate()!= null &&
				maintainULDForm.getPurchaseDate().trim().length() > 0) {
			LocalDate purchaseDate =
				new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			uldVO.setPurchaseDate(purchaseDate.setDate(maintainULDForm.getPurchaseDate()));
		}
		uldVO.setPurchaseInvoiceNumber(
				maintainULDForm.getPurchaseInvoiceNumber().toUpperCase());
		uldVO.setUldContour(maintainULDForm.getUldContour().toUpperCase());
		if(maintainULDForm.getUldPrice()!= null
				&& maintainULDForm.getUldPrice().trim().length() > 0) {
			uldVO.setDisplayUldPrice(Double.parseDouble(maintainULDForm.getUldPrice()));
		}
		uldVO.setUldPriceUnit(maintainULDForm.getUldPriceUnit());
		uldVO.setUldSerialNumber(maintainULDForm.getUldSerialNumber().toUpperCase());
		uldVO.setVendor(maintainULDForm.getVendor().toUpperCase());
		uldVO.setOperationalAirlineCode(
				maintainULDForm.getOperationalAirlineCode().toUpperCase());
		uldVO.setUldNumber(maintainULDForm.getUldNumber().toUpperCase());
		uldVO.setOwnerAirlineCode(maintainULDForm.getOwnerAirlineCode());
		uldVO.setOperationalAirlineCode(maintainULDForm.getOperationalAirlineCode());
		uldVO.setRemarks(maintainULDForm.getRemarks());
		uldVO.setRemarks(maintainULDForm.getRemarks());


	}
}
