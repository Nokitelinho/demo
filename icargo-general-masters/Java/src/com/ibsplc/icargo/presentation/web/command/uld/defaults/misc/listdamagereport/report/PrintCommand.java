/*
 * PrintCommand.java Created on Dec 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listdamagereport.report;


import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;


/**
 * @author A-2052
 *
 */

public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST047";
	private Log log = LogFactory.getLogger("List Damage");
	private static final String BLANK = "";
	private static final String SELECT="ALL";
	private static final String PRINT_FAILURE = "print_failure";
	private static final String SCREENID = "uld.defaults.listdamagereport";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String DAMAGE_STATUS = "uld.defaults.damageStatus";
	private static final String ULD_STATUS = "uld.defaults.overallStatus";
	private static final String PRODUCT="uld";
	private static final String SUBPRODUCT="defaults";
	private static final String ACTION="printULDDamageReport";

	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		//Collection<ErrorVO> errorVos= new ArrayList<ErrorVO>();

		ListDamageReportForm form =
					(ListDamageReportForm)invocationContext.screenModel;
		ListDamageReportSession session = getScreenSession(MODULE_NAME, SCREENID);
		//ULDDefaultsDelegate uldDefaultsDelegate = null;

		ULDDamageFilterVO filterVO = new ULDDamageFilterVO();


		/*uldDamageFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		uldDamageFilterVO.setUldNumber(upper(listDamageReportForm.getUldNo()));
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+listDamageReportForm.getDamageRefNo());
		if(listDamageReportForm.getDamageRefNo().equals("")){
			invocationContext.target=PRINT_FAILURE;
			return;
		}
		if(!(listDamageReportForm.getDamageRefNo().equals(""))){
			uldDamageFilterVO.setDamageReferenceNumber(Long.parseLong(listDamageReportForm.getDamageRefNo()));
		}

		//uldDamageFilterVO.setDamageReferenceNumber(new Long(1).longValue());
		uldDamageFilterVO.setUldTypeCode(upper(listDamageReportForm.getUldTypeCode()));
		if(listDamageReportForm.getUldStatus() != null && !BLANK.equals(listDamageReportForm.getUldStatus())
					&&!SELECT.equals(listDamageReportForm.getUldStatus())){
		uldDamageFilterVO.setUldStatus(upper(listDamageReportForm.getUldStatus()));}
		if(listDamageReportForm.getUldDamageStatus() != null && !BLANK.equals(listDamageReportForm.getUldDamageStatus())
				&&!SELECT.equals(listDamageReportForm.getUldDamageStatus())){
		uldDamageFilterVO.setDamageStatus(upper(listDamageReportForm.getUldDamageStatus()));}
		uldDamageFilterVO.setCurrentStation(upper(listDamageReportForm.getCurrentStn()));
		uldDamageFilterVO.setReportedStation(upper(listDamageReportForm.getReportedStn()));
		uldDamageFilterVO.setFromDate(listDamageReportForm.getRepairedDateFrom());
		uldDamageFilterVO.setToDate(listDamageReportForm.getRepairedDateTo());
		uldDamageFilterVO.setPageNumber(Integer.parseInt(listDamageReportForm.getDisplayPage()));*/

		String companyCode = logonAttributesVO.getCompanyCode();
		String uldNo =form.getUldNo();
		String damageRefNo = form.getDamageRefNo();
		String uldTypeCode = form.getUldTypeCode();
		String uldStatus = form.getUldStatus();
		String damageStatus = form.getUldDamageStatus();
		String currentStn = form.getCurrentStn();
		String reportedStn = form.getReportedStn();
		String repairedDateFrom = form.getRepairedDateFrom();
		String repairedDateTo = form.getRepairedDateTo();
		//Added by A-7924 as part of ICRD-301463 starts
		String partyType = form.getPartyType(); 
		String party = form.getParty();
		String facilityType = form.getFacilityType();
		String location = form.getLocation();
		//Added by A-7924 as part of ICRD-301463 ends

		HashMap indexMap = null;
		HashMap finalMap = null;
		if (session.getIndexMap()!=null){
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.FINE,"INDEX MAP IS NULL");
			indexMap = new HashMap();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String toDisplayPage = form.getDisplayPage();//Modified to resolve compilation issue
		int displayPage = Integer.parseInt(toDisplayPage);
		String strAbsoluteIndex = (String)indexMap.get(toDisplayPage);
		if(strAbsoluteIndex != null){
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}

		filterVO.setCompanyCode(companyCode);
		if(repairedDateFrom!=null && repairedDateFrom.trim().length()>0){
			filterVO.setFromDate(repairedDateFrom);
		}

		if(repairedDateTo!=null && repairedDateTo.trim().length()>0){
			filterVO.setToDate(repairedDateTo);
		}
		if(uldNo!= null && !BLANK.equals(uldNo)&& !SELECT.equals(uldNo)){
			filterVO.setUldNumber(uldNo);
		}
		if(!(("").equals(form.getDamageRefNo()))){
			filterVO.setDamageReferenceNumber(Long.parseLong(damageRefNo));
		}
		if(uldTypeCode!= null && !BLANK.equals(uldTypeCode)&& !SELECT.equals(uldTypeCode)){
			filterVO.setUldTypeCode(uldTypeCode);
		}
		if(uldStatus!= null && !BLANK.equals(uldStatus)&& !SELECT.equals(uldStatus)){
			filterVO.setUldStatus(uldStatus);
		}
		if(damageStatus!= null && !BLANK.equals(damageStatus)&& !SELECT.equals(damageStatus)){
			filterVO.setDamageStatus(damageStatus);
		}
		if(currentStn!= null && !BLANK.equals(currentStn)&& !SELECT.equals(currentStn)){
			filterVO.setCurrentStation(currentStn);
		}
		if(reportedStn!= null && !BLANK.equals(reportedStn)&& !SELECT.equals(reportedStn)){
			filterVO.setReportedStation(reportedStn);
		}
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));//Modified to resolve compilation issue
		//Added by A-7924 as part of ICRD-301463 starts
		if(partyType != null && !BLANK.equals(partyType)&& !SELECT.equals(partyType)){
			filterVO.setPartyType(partyType);
		}
		
		if(party != null && !BLANK.equals(party)&& !SELECT.equals(party)){
			filterVO.setParty(party);
		}
		
		if(facilityType != null && !BLANK.equals(facilityType)&& !SELECT.equals(facilityType)){
			filterVO.setFacilityType(facilityType);
		}
		
		if(location != null && !BLANK.equals(location)&& !SELECT.equals(location)){
			filterVO.setLocation(location);
		}
		//Added by A-7924 as part of ICRD-301463 ends
		/*Page<ULDDamageDetailsListVO> uldDamageRepairDetailsVOs = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try{
			uldDefaultsDelegate = new ULDDefaultsDelegate();
			uldDamageRepairDetailsVOs = uldDefaultsDelegate.findULDDamageList(filterVO);
		}
		catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			errorVos = handleDelegateException(businessDelegateException);
		}
		if(uldDamageRepairDetailsVOs == null || uldDamageRepairDetailsVOs.size()==0){
			ErrorVO error = new ErrorVO("uld.defaults.noschexists");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_FAILURE;
			return;
		}
		Map<String,Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributesVO.getCompanyCode());
		Collection<OneTimeVO> dmgSta = oneTimeCollection.get(DAMAGE_STATUS);
		Collection<OneTimeVO> uldSta = oneTimeCollection.get(ULD_STATUS);
		log.log(Log.FINE,"\n\n\n----------Obtained uldDamageRepairDetailsVOs from the server----->"+dmgSta);
		log.log(Log.FINE,"\n\n\n----------Obtained uldDamageRepairDetailsVOs from the server----->"+uldSta);
		for(ULDDamageDetailsListVO uldDamageRepairDetailsVO :uldDamageRepairDetailsVOs ){
			for(OneTimeVO oneTimeVO:dmgSta){
				log.log(Log.FINE,"\n\n\n----------Damage Status "+uldDamageRepairDetailsVO.getDamageStatus());
				if(uldDamageRepairDetailsVO.getDamageStatus() != null &&
					uldDamageRepairDetailsVO.getDamageStatus().equals(oneTimeVO.getFieldValue())){
					uldDamageRepairDetailsVO.setDamageStatus(oneTimeVO.getFieldDescription());
				}
			}
			for(OneTimeVO oneTimeVO:uldSta){
				log.log(Log.FINE,"\n\n\n----------Over All Status "+uldDamageRepairDetailsVO.getOverallStatus());
				if(uldDamageRepairDetailsVO.getOverallStatus() != null &&
					uldDamageRepairDetailsVO.getOverallStatus().equals(oneTimeVO.getFieldValue())){
					uldDamageRepairDetailsVO.setOverallStatus(oneTimeVO.getFieldDescription());
				}
			}
		}
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[]{
				"ULDNUM","DMGREFNUM", "RPTSTN", "CURSTN", "RPRSTA", "OVLSTA", "RMK"});
		reportMetaData.setFieldNames(new String[] {"uldNumber","damageReferenceNumber",
				"reportedStation","currentStation",
				"repairStatus","uldStatus","remarks"
				});*/
		//log.log(Log.FINE,"\n\n\n----------Obtained uldDamageRepairDetailsVOs from the server----->"+uldDamageRepairDetailsVOs);
		//getReportSpec().setReportMetaData(reportMetaData);
		///getReportSpec().setData(uldDamageRepairDetailsVOs);
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(PRODUCT);
		getReportSpec().setSubProductCode(SUBPRODUCT);
		getReportSpec().setPreview(true);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().addFilterValue(filterVO);
		getReportSpec().setResourceBundle("listDamageReportResources");
		getReportSpec().setAction(ACTION);

		log.log(Log.FINE, "\n\n\n----------REPORT_ID----->", REPORT_ID);
		generateReport();

		log.log(Log.FINE,"\n\n\n----------AFTER GENERATE REPORT----");
		invocationContext.target = getTargetPage();
		log.log(Log.FINE, "\n\n\n----------report----->",
				invocationContext.target);
	}
	private Map<String,Collection<OneTimeVO>> fetchScreenLoadDetails(String companyCode){
		Map<String,Collection<OneTimeVO>> hashMap = new
		HashMap<String,Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(DAMAGE_STATUS);
		oneTimeList.add(ULD_STATUS);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try{
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);

		}catch(BusinessDelegateException ex){
			ex.getMessage();
			exception = handleDelegateException(ex);
		}
		return hashMap;
	}
}
