/*
 * PrintInventoryCommand.java Created on Dec 12, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld.report;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

 /**
  * 
  * @author A-2408
  *
  */
public class PrintInventoryCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST078";
	private static final String ULD_INVENTORY_REPORT_ID = "RPRULD029";
	private Log log = LogFactory.getLogger("List ULD");
	
	private static final String BLANK = "";
	
	
    private static final String RESOURCE_BUNDLE_KEY = "listuldResources";
	
	private static final String ACTION = "printInventory";
	private static final String ULD_INVENTORY_ACTION = "printUldInventoryList";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
    	
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

public void execute(InvocationContext invocationContext) 
									throws CommandInvocationException {
		
		ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		ULDListFilterVO uldListFilterVO = null;
					Collection<ErrorVO> errorsAirline = null;
					try {
			uldListFilterVO = constructFilterVo(listULDForm);   
					}
					catch(BusinessDelegateException businessDelegateException) {
						errorsAirline = 
							handleDelegateException(businessDelegateException);
		   			}
					if(errorsAirline != null &&
							errorsAirline.size() > 0) {
						errors.addAll(errorsAirline);
						invocationContext.addAllError(errors);
						invocationContext.target=PRINT_UNSUCCESSFUL;
						return;
					}
		
		//listULDForm.setDisplayPage("1");
		//listULDForm.setLastPageNum("0");
			listULDForm.setStatusFlag("");
			log.log(Log.FINE, "\n\n\nghgfking23465 Station----------------->",
					getApplicationSession().getLogonVO().getAirportCode());
			log.log(Log.FINE, "ListULDPrintCommand ~~~~~~uldListFilterVO~~~",
					uldListFilterVO);
			ReportSpec reportSpec = getReportSpec();
		if("PRINTULDINVENTORY".equals(uldListFilterVO.getPrintType())) {
			reportSpec.setReportId(ULD_INVENTORY_REPORT_ID);
		}else {
			reportSpec.setReportId(REPORT_ID);
		}
			reportSpec.setProductCode(listULDForm.getProduct());
			reportSpec.setSubProductCode(listULDForm.getSubProduct());
			reportSpec.setPreview(true);
			reportSpec.setHttpServerBase(invocationContext.httpServerBase);
			reportSpec.addFilterValue(uldListFilterVO);
			reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		if("PRINTULDINVENTORY".equals(uldListFilterVO.getPrintType())) {
			reportSpec.setAction(ULD_INVENTORY_ACTION);           
		}else {
			reportSpec.setAction(ACTION);
		}
			generateReport();
			
			if(getErrors() != null && getErrors().size() > 0){
				
				invocationContext.addAllError(getErrors());
				invocationContext.target = PRINT_UNSUCCESSFUL;
				return;
			}
			
			log.exiting("ULD_DEFAULTS","PrintInventoryCommand exit");
			invocationContext.target = getTargetPage();
}
/**
 * 
 * @param listULDForm
 * @return
 * @throws BusinessDelegateException 
 */
private ULDListFilterVO constructFilterVo(ListULDForm listULDForm) throws BusinessDelegateException {
	log.entering("ULD_DEFAULTS","constructFilterVo"); 
	ULDListFilterVO uldListFilterVO = new ULDListFilterVO();
	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
	LocalDate lastMvtDate = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,false);
	uldListFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
	if(listULDForm.getUldNumber()!= null && !BLANK.equals
			(listULDForm.getUldNumber())){	
		uldListFilterVO.setUldNumber(listULDForm.getUldNumber().toUpperCase());
	}else{
		uldListFilterVO.setUldNumber("");
	}
	if(listULDForm.getUldGroupCode()!= null && !BLANK.equals
			(listULDForm.getUldGroupCode())){
		uldListFilterVO.setUldGroupCode
		(listULDForm.getUldGroupCode().toUpperCase());
	}else{
		uldListFilterVO.setUldGroupCode("");
	}
	if(listULDForm.getUldTypeCode()!= null && !BLANK.equals
			(listULDForm.getUldTypeCode())){
		uldListFilterVO.setUldTypeCode(listULDForm.getUldTypeCode().toUpperCase());
	}else{
		uldListFilterVO.setUldTypeCode("");	
	}
	if(listULDForm.getAirlineCode()!= null && !BLANK.equals
			(listULDForm.getAirlineCode())){
		uldListFilterVO.setAirlineCode(listULDForm.getAirlineCode());
	}else{
		uldListFilterVO.setAirlineCode("");
	}
	if(listULDForm.getOverallStatus()!= null && !BLANK.equals
			(listULDForm.getOverallStatus())){
		uldListFilterVO.setOverallStatus(listULDForm.getOverallStatus());
	}else{
		uldListFilterVO.setOverallStatus("");
	}
	if(listULDForm.getDamageStatus()!= null && !BLANK.equals
			(listULDForm.getDamageStatus())){
		uldListFilterVO.setDamageStatus(listULDForm.getDamageStatus());
	}else{
		uldListFilterVO.setDamageStatus("");
	}
	if(listULDForm.getCleanlinessStatus()!= null && !BLANK.equals
			(listULDForm.getCleanlinessStatus())){
		uldListFilterVO.setCleanlinessStatus(listULDForm.getCleanlinessStatus());
	}else{
		uldListFilterVO.setCleanlinessStatus("");
	}
	if(listULDForm.getManufacturer()!= null && !BLANK.equals
			(listULDForm.getManufacturer())){
		uldListFilterVO.setManufacturer
		(listULDForm.getManufacturer());
	}else{
		uldListFilterVO.setManufacturer("");
	}
	if(listULDForm.getLocation()!= null && !BLANK.equals
			(listULDForm.getLocation())){
		uldListFilterVO.setLocation(listULDForm.getLocation().toUpperCase());
	}else{
		uldListFilterVO.setLocation("");
	}
	if(listULDForm.getOwnerStation()!= null && !BLANK.equals
			(listULDForm.getOwnerStation())){
		uldListFilterVO.setOwnerStation(listULDForm.
				getOwnerStation().toUpperCase());
	}else{
		uldListFilterVO.setOwnerStation("");
	}
	if(listULDForm.getUldNature()!= null && !BLANK.equals
			(listULDForm.getUldNature())){
		uldListFilterVO.setUldNature(listULDForm.
				getUldNature().toUpperCase());
	}else{
		uldListFilterVO.setUldNature("");
	}
	log.log(Log.FINE, "\n\n\nCurrent Station----------------->", listULDForm.getCurrentStation());
	if(listULDForm.getCurrentStation()!= null && !BLANK.equals
			(listULDForm.getCurrentStation())){
		uldListFilterVO.setCurrentStation(listULDForm.
				getCurrentStation().toUpperCase());
	}
	if(listULDForm.getLastMovementDate()!= null && !BLANK.equals
			(listULDForm.getLastMovementDate())){
		uldListFilterVO.setLastMovementDate(lastMvtDate.setDate
				(listULDForm.getLastMovementDate()));
	}

	if(listULDForm.getUldRangeFrom()!= null && !BLANK.equals
			(listULDForm.getUldRangeFrom())){
		uldListFilterVO.setUldRangeFrom(Integer.parseInt
				(listULDForm.getUldRangeFrom()));
	}else{
		listULDForm.setUldRangeFrom("");
		uldListFilterVO.setUldRangeFrom(-1);
	}
	log.log(Log.FINE, "\n\n\nCheckinghggd Station----------------->",
			getApplicationSession().getLogonVO().getAirportCode());
	if(listULDForm.getUldRangeTo()!= null && !BLANK.equals
			(listULDForm.getUldRangeTo())){
		uldListFilterVO.setUldRangeTo(Integer.parseInt
				(listULDForm.getUldRangeTo()));
	}else{
		listULDForm.setUldRangeTo("");
		uldListFilterVO.setUldRangeTo(-1);
	}
	Map<String,AirlineValidationVO> airlineMap = new HashMap<String,AirlineValidationVO>();
	Collection<String> airlineCodes = new ArrayList<String>();
	if((listULDForm.getAirlineCode() != null 
			&& listULDForm.getAirlineCode().trim().length() > 0)
			||(listULDForm.getOwnerAirline() != null 
			&& listULDForm.getOwnerAirline().trim().length() > 0)){			
		String cmpcod =  getApplicationSession().getLogonVO().getCompanyCode();
		if((listULDForm.getAirlineCode() != null 
				&& listULDForm.getAirlineCode().trim().length() > 0)
				&& !airlineCodes.contains(listULDForm.getAirlineCode())){
			airlineCodes.add(listULDForm.getAirlineCode().toUpperCase());
		}
		if((listULDForm.getOwnerAirline() != null 
				&& listULDForm.getOwnerAirline().trim().length() > 0)
				&& !airlineCodes.contains(listULDForm.getOwnerAirline())){
			airlineCodes.add(listULDForm.getOwnerAirline().toUpperCase());	
		}	
		airlineMap = new AirlineDelegate().validateAlphaCodes(cmpcod,airlineCodes);
		uldListFilterVO.setAirlineCode(
				listULDForm.getAirlineCode().toUpperCase());
		uldListFilterVO.setOwnerAirline(
				listULDForm.getOwnerAirline().toUpperCase());			
	}		
	if(listULDForm.getOwnerAirline() != null
			&& listULDForm.getOwnerAirline().trim().length() > 0) {			
		AirlineValidationVO airlineVO = airlineMap.get(
				listULDForm.getOwnerAirline().toUpperCase());	
		if(airlineVO != null){			
			uldListFilterVO.setOwnerAirlineidentifier(airlineVO.getAirlineIdentifier());
		}
	}		
	if(listULDForm.getAirlineCode() != null
			&& listULDForm.getAirlineCode().trim().length() > 0) {
		AirlineValidationVO airlineVO = airlineMap.get(
				listULDForm.getAirlineCode().toUpperCase());
		if(airlineVO != null){			
			uldListFilterVO.setAirlineidentifier(airlineVO.getAirlineIdentifier());
		}
	}
	if(listULDForm.getFromDate() != null && 
			listULDForm.getFromDate().trim().length() > 0) {
		LocalDate fromDate = new LocalDate(
				getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
		fromDate.setDate(listULDForm.getFromDate());
		uldListFilterVO.setFromDate(fromDate);			
	}
	if(listULDForm.getToDate() != null && 
			listULDForm.getToDate().trim().length() > 0) {
		LocalDate toDate = new LocalDate(
					getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);				 
			toDate.setDate(listULDForm.getToDate());
		uldListFilterVO.setToDate(toDate);		
	}
	if(listULDForm.getAgentCode()!= null && !BLANK.equals
			(listULDForm.getAgentCode())){
		uldListFilterVO.setAgentCode(listULDForm.
				getAgentCode().toUpperCase());
	}else{
		uldListFilterVO.setAgentCode("");
	}
    if(listULDForm.getLevelType() != null && !BLANK.equals
			(listULDForm.getLevelType())){
		uldListFilterVO.setLevelType(listULDForm.getLevelType());
	}
	if(listULDForm.getLevelValue() != null && !BLANK.equals
			(listULDForm.getLevelValue())){
		uldListFilterVO.setLevelValue(listULDForm.getLevelValue());
	}
	if(listULDForm.getContent() != null && !BLANK.equals
			(listULDForm.getContent())){
		uldListFilterVO.setContent(listULDForm.getContent());
	}
	if(listULDForm.getOffairportFlag()!=null && !BLANK.equals(listULDForm.getOffairportFlag())){
		uldListFilterVO.setOffairportFlag(listULDForm.getOffairportFlag());
	}
	if(listULDForm.getOccupiedULDFlag()!=null && !BLANK.equals(listULDForm.getOccupiedULDFlag())){
		uldListFilterVO.setOccupiedULDFlag(listULDForm.getOccupiedULDFlag());
	}
	uldListFilterVO.setFromListULD(true);	
	uldListFilterVO.setPrintType(listULDForm.getStatusFlag());  
	log.exiting("ULD_DEFAULTS","constructFilterVo"); 
	return uldListFilterVO;
}



}





