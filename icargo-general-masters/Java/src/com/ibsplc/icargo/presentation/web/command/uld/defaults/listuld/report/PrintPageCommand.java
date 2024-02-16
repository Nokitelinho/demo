/*
 * PrintPageCommand.java Created on Dec 19, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
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
 * @author A-3459
 *
 */
public class PrintPageCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST020";
	private Log log = LogFactory.getLogger("List ULD");
	
	private static final String BLANK = "";
	
	private static final String OPERATSTATUS_ONETIME = 
											"uld.defaults.operationalStatus";
	private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";
	private static final String CLEANSTATUS_ONETIME = "uld.defaults.cleanlinessStatus";
	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
    private static final String RESOURCE_BUNDLE_KEY = "listuldResources";
	
	private static final String ACTION = "printpagelistuld";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
    	
	  /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

public void execute(InvocationContext invocationContext) 
							throws CommandInvocationException {

ApplicationSessionImpl applicationSession = getApplicationSession();
LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
LocalDate lastMvtDate = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,false);
ULDListFilterVO uldListFilterVO = new ULDListFilterVO();
uldListFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
if(listULDForm.getUldNumber()!= null && !BLANK.equals
										(listULDForm.getUldNumber())){	
	uldListFilterVO.setUldNumber(listULDForm.getUldNumber().toUpperCase());
}
else
{
	uldListFilterVO.setUldNumber("");
	}
if(listULDForm.getUldGroupCode()!= null && !BLANK.equals
										(listULDForm.getUldGroupCode())){
	uldListFilterVO.setUldGroupCode
							(listULDForm.getUldGroupCode().toUpperCase());
}
else
{
	uldListFilterVO.setUldGroupCode("");
}
if(listULDForm.getUldTypeCode()!= null && !BLANK.equals
										(listULDForm.getUldTypeCode())){
	uldListFilterVO.setUldTypeCode(listULDForm.getUldTypeCode().toUpperCase());
}
else
{
uldListFilterVO.setUldTypeCode("");	
}
if(listULDForm.getAirlineCode()!= null && !BLANK.equals
		(listULDForm.getAirlineCode())){
	uldListFilterVO.setAirlineCode(listULDForm.getAirlineCode());
}
else
{
	uldListFilterVO.setAirlineCode("");
}
if(listULDForm.getOverallStatus()!= null && !BLANK.equals
		(listULDForm.getOverallStatus())){
	uldListFilterVO.setOverallStatus(listULDForm.getOverallStatus());
}
else
{
	uldListFilterVO.setOverallStatus("");
	}
if(listULDForm.getDamageStatus()!= null && !BLANK.equals
									(listULDForm.getDamageStatus())){
	uldListFilterVO.setDamageStatus(listULDForm.getDamageStatus());
}
else{
uldListFilterVO.setDamageStatus("");
}
if(listULDForm.getCleanlinessStatus()!= null && !BLANK.equals
									(listULDForm.getCleanlinessStatus())){
	uldListFilterVO.setCleanlinessStatus(listULDForm.getCleanlinessStatus());
}
else
{
	uldListFilterVO.setCleanlinessStatus("");
	}
if(listULDForm.getManufacturer()!= null && !BLANK.equals
									(listULDForm.getManufacturer())){
	uldListFilterVO.setManufacturer
								(listULDForm.getManufacturer());
}
else{
	uldListFilterVO.setManufacturer("");
	}
if(listULDForm.getLocation()!= null && !BLANK.equals
												(listULDForm.getLocation())){
	uldListFilterVO.setLocation(listULDForm.getLocation().toUpperCase());
}
else
{
	uldListFilterVO.setLocation("");
	}
if(listULDForm.getOwnerStation()!= null && !BLANK.equals
											(listULDForm.getOwnerStation())){
	uldListFilterVO.setOwnerStation(listULDForm.
											getOwnerStation().toUpperCase());
}
else
{
	uldListFilterVO.setOwnerStation("");
	}
if(listULDForm.getUldNature()!= null && !BLANK.equals
		(listULDForm.getUldNature())){
	uldListFilterVO.setUldNature(listULDForm.
			getUldNature().toUpperCase());
}
else
{
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
}
else {
	listULDForm.setUldRangeFrom("");
	uldListFilterVO.setUldRangeFrom(-1);
}
log.log(Log.FINE, "\n\n\nCheckinghggd Station----------------->",
		getApplicationSession().getLogonVO().getAirportCode());
if(listULDForm.getUldRangeTo()!= null && !BLANK.equals
											(listULDForm.getUldRangeTo())){
	uldListFilterVO.setUldRangeTo(Integer.parseInt
											(listULDForm.getUldRangeTo()));
}
else {
	listULDForm.setUldRangeTo("");
	uldListFilterVO.setUldRangeTo(-1);
}

//added by a-3045 for CR AirNZ415 starts
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
	Collection<ErrorVO> errorsAirline = null;
	try {	
		airlineMap = new AirlineDelegate().validateAlphaCodes(cmpcod,airlineCodes);
	}
	catch(BusinessDelegateException businessDelegateException) {
		errorsAirline = 
		handleDelegateException(businessDelegateException);
   	}
	if(errorsAirline != null &&
		errorsAirline.size() > 0) {
			errors.addAll(errorsAirline);
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
    		return;
	}
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

ErrorVO error = null;

if(listULDForm.getFromDate() != null && 
		 listULDForm.getFromDate().trim().length() > 0) {
	LocalDate fromDate = null;
	if(DateUtilities.isValidDate(
					listULDForm.getFromDate(),"dd-MMM-yyyy")) {
		fromDate = new LocalDate(
		  getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
		fromDate.setDate(listULDForm.getFromDate());				
	}
	else {
		error = new ErrorVO("uld.defaults.invaliddate",
				new Object[]{listULDForm.getFromDate()});
		errors.add(error);
	}
	uldListFilterVO.setFromDate(fromDate);			
}
if(listULDForm.getToDate() != null && 
		listULDForm.getToDate().trim().length() > 0) {
	LocalDate toDate = null;
	if(DateUtilities.isValidDate(
					listULDForm.getToDate(),"dd-MMM-yyyy")) {
		toDate = new LocalDate(
		  getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);				 
		toDate.setDate(listULDForm.getToDate());					
	}
	else {
		error = new ErrorVO("uld.defaults.invaliddate",
				new Object[]{listULDForm.getToDate()});
		errors.add(error);
	}
	uldListFilterVO.setToDate(toDate);		
}

if(errors != null &&
		errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
    		return;
}


if(listULDForm.getAgentCode()!= null && !BLANK.equals
		(listULDForm.getAgentCode())){
uldListFilterVO.setAgentCode(listULDForm.
		getAgentCode().toUpperCase());
}
else
{
uldListFilterVO.setAgentCode("");
}

//added by a-3045 for CR AirNZ415 ends
//added by a-3045 for BUG20446 starts
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
//added by a-3045 for bug 46783 on 19May09 starts
if(listULDForm.getOffairportFlag()!=null && !BLANK.equals(listULDForm.getOffairportFlag())){
	uldListFilterVO.setOffairportFlag(listULDForm.getOffairportFlag());
}
if(listULDForm.getOccupiedULDFlag()!=null && !BLANK.equals(listULDForm.getOccupiedULDFlag())){
	uldListFilterVO.setOccupiedULDFlag(listULDForm.getOccupiedULDFlag());
}
//added by a-3045 for bug 46783 on 19May09 ends
//added by a-3045 for bug 23717 on 11Dec08 starts
uldListFilterVO.setFromListULD(true);
//added by a-3045 for bug 23717 11Dec08 ends
//added by a-3045 for BUG20446 ends

//Added by A-9775 for IASCB-165215 starts
if(listULDForm.getOalUldOnly()!=null && listULDForm.getOalUldOnly().trim().length()>0){
	uldListFilterVO.setOalUldOnly(listULDForm.getOalUldOnly());
}
if("on".equalsIgnoreCase(listULDForm.getOalUldOnly())){
uldListFilterVO.setAirlineCode(logonAttributesVO.getOwnAirlineCode());
}
//Added by A-9775 for IASCB-165215 ends
log.log(Log.FINE, "\n\n\nChedsgf3465 Station----------------->",
		getApplicationSession().getLogonVO().getAirportCode());
	//listULDForm.setDisplayPage("1");
	//listULDForm.setLastPageNum("0");
	listULDForm.setStatusFlag("");
	log.log(Log.FINE, "\n\n\nghgfking23465 Station----------------->",
			getApplicationSession().getLogonVO().getAirportCode());
	log.log(Log.FINE,
			"ListULDPrintCommand ~~~~~~listULDForm.getDisplayPage()~~~",
			listULDForm.getDisplayPage());
uldListFilterVO.setPageNumber(Integer.parseInt(listULDForm.getDisplayPage()));
	log.log(Log.FINE, "ListULDPrintCommand ~~~~~~uldListFilterVO~~~",
			uldListFilterVO);
	ReportSpec reportSpec = getReportSpec();
	reportSpec.setReportId(REPORT_ID);
	reportSpec.setProductCode(listULDForm.getProduct());
	reportSpec.setSubProductCode(listULDForm.getSubProduct());
	reportSpec.setPreview(true);
	reportSpec.setHttpServerBase(invocationContext.httpServerBase);
	reportSpec.addFilterValue(uldListFilterVO);
	reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
	reportSpec.setAction(ACTION);
	
	generateReport();
	
	if(getErrors() != null && getErrors().size() > 0){
		
		invocationContext.addAllError(getErrors());
		invocationContext.target = PRINT_UNSUCCESSFUL;
		return;
	}
	
	log.exiting("PrintCommand","PrintCommand exit");
	invocationContext.target = getTargetPage();
}

}
