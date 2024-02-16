/*
 * FmtBillingLinesCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2398
 * 
 */
public class FmtBillingLinesCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";

	private static final String FMTVIEW_SUCCESS = "fmt_success";

	private static final String FMTVIEW_FAILURE = "fmt_failure";
	
	private static final String CONST_ORGCNT = "ORGCNT";
	
	private static final String CONST_ORGCTY = "ORGCTY";
	
	private static final String CONST_ORGREG = "ORGREG";
	
	private static final String CONST_DSTCNT = "DSTCNT";
	
	private static final String CONST_DSTCTY = "DSTCTY";
	
	private static final String CONST_DSTREG = "DSTREG";
	
	private static final String CONST_UPLCNT = "UPLCNT";
	
	private static final String CONST_UPLCTY = "UPLCTY";
	
	private static final String CONST_UPLARP = "UPLARPCOD";

	private static final String CONST_DISARP = "DISARPCOD";

	private static final String CONST_DISCNT = "DISCNT";
	
	private static final String CONST_DISCTY = "DISCTY";
	
	private static final String CONST_ULDTYP = "ULDTYP";
	
	private static final String CONST_ULDGRP = "ULDGRP";
	
	private static final String CONST_CLASS = "CLS";
	
	private static final String CONST_SUBCLS = "SUBCLS";
	
	private static final String CONST_CAT = "CAT";
	
	private static final String CONST_FLTNUM = "FLTNUM";
	//Added as part of Bug ICRD-106391 starts
	
	private static final String CONST_SUBCLASSGROUP = "SUBCLSGRP";
	
	private static final String CONST_TRFBY= "TRFBY";
	
	private static final String CONST_TRFPSA = "TRFPOA";
	
	private static final String CONST_MALCMPCOD = "MALCMPCOD";
	
	
	private static final String CONST_ORGAIR="ORGARPCOD";
	private static final String CONST_DESAIR="DSTARPCOD";
	 private static final String CONST_VIAPNT="VIAPNT";
	private static final String CONST_MALSRV="MALSRVLVL";
	private static final String CONST_PABUILT="POAFLG";
	private static final String CONST_FLNCAR= "FLNCAR";
	//Added as part of Bug ICRD-106391 ends
		
	private static final String OPFLAG_DEL = "D";
	
	
	
//	private static final String KEY_BLG_CATEGORY =
//		"mailtracking.defaults.mailcategory";
//	
//	private static final String KEY_BLG_CLASS =
//		"mailtracking.defaults.mailclass";
//	
//	private static final String KEY_RATE_STATUS =
//		"mra.gpabilling.ratestatus";

	private static final String CLASS_NAME = "FormatCommand";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ViewBillingLineForm form = (ViewBillingLineForm) invocationContext.screenModel;
		
		ViewBillingLineSession session = (ViewBillingLineSession) getScreenSession(
				MODULE, SCREENID);
		
		
		 Page<BillingLineVO> billingLineDetails = null;
		
		 ArrayList<String> formValues=null;

		 ArrayList<String> orgCountry = new ArrayList<String>();
		
		 ArrayList<String> orgCity = new ArrayList<String>();
		
		 ArrayList<String> orgRegion = new ArrayList<String>();
		
		 ArrayList<String> destCountry = new ArrayList<String>();
		
		 ArrayList<String> destRegion = new ArrayList<String>();
		
		 ArrayList<String> destCity = new ArrayList<String>();
		
		 ArrayList<String> upliftCountry = new ArrayList<String>();
		
		 ArrayList<String> upliftCity = new ArrayList<String>();
		
		 ArrayList<String> dischargeCountry = new ArrayList<String>();
		
		 ArrayList<String> dischargeCity = new ArrayList<String>();
		 
		 ArrayList<String> flightNum = new ArrayList<String>();
		 
		 ArrayList<String> uldType = new ArrayList<String>();
		 
		 ArrayList<String> billingClass = new ArrayList<String>();
		 
		 ArrayList<String> subClass = new ArrayList<String>();
		 
		 ArrayList<String> category = new ArrayList<String>();
		//Added as part of Bug ICRD-106391 starts
		
		 ArrayList<String> transferredBy = new ArrayList<String>();
		 
		 ArrayList<String> transferredPA = new ArrayList<String>();
		 
		 ArrayList<String> company = new ArrayList<String>();
		 
		 ArrayList<String> subClassGroup = new ArrayList<String>();
		//Added as part of Bug ICRD-106391 ends
		
		//Added by a7540
		ArrayList<String> orgAirport = new ArrayList<String>();
		ArrayList<String> desAirport = new ArrayList<String>();
		ArrayList<String> viaPoint = new ArrayList<String>();
		ArrayList<String> mailService = new ArrayList<String>();
		ArrayList<String> paBuilt = new ArrayList<String>();
		ArrayList<String> upliftAirport = new ArrayList<String>();
		ArrayList<String> dischargeAirport = new ArrayList<String>();
		ArrayList<String> uplAirport = new ArrayList<String>();
		ArrayList<String> disAirport = new ArrayList<String>();
		 ArrayList<String> flownCarrier = new ArrayList<>();
		
		billingLineDetails = session.getBillingLineDetails();
		
		ArrayList<String> uldGrp = new ArrayList<String>();
		
		if(billingLineDetails != null){
				log.log(Log.FINE, "Page returned....", billingLineDetails.toString());
				log.log(Log.INFO, "Size of page...", billingLineDetails.getActualPageSize());
				int index = 0;
				for(BillingLineVO blgLineVO : billingLineDetails){
					if(!OPFLAG_DEL.equals(blgLineVO.getOperationFlag())){
					Collection<BillingLineParameterVO> blgLineParameters = blgLineVO.getBillingLineParameters();
					if(blgLineParameters!= null && blgLineParameters.size() > 0){
					formValues = populateValues(form,blgLineParameters,index,session);
					if(formValues.get(0) != null) {
						orgCity.add(formValues.get(0));
					} else {
						orgCity.add("");
					}
					if(formValues.get(1) != null) {
						orgCountry.add(formValues.get(1));
					} else {
						orgCountry.add("");
					}
					if(formValues.get(2) != null) {
						orgRegion.add(formValues.get(2));
					} else {
						orgRegion.add("");
					}
					if(formValues.get(3) != null) {
						destCity.add(formValues.get(3));
					} else {
						destCity.add("");
					}
					if(formValues.get(4) != null) {
						destCountry.add(formValues.get(4));
					} else {
						destCountry.add("");
					}
					if(formValues.get(5) != null) {
						destRegion.add(formValues.get(5));
					} else {
						destRegion.add("");
					}
					if(formValues.get(6) != null) {
						upliftCity.add(formValues.get(6));
					} else {
						upliftCity.add("");
					}
					if(formValues.get(7) != null) {
						upliftCountry.add(formValues.get(7));
					} else {
						upliftCountry.add("");
					}
					if(formValues.get(8) != null) {
						dischargeCity.add(formValues.get(8));
					} else {
						dischargeCity.add("");
					}
					if(formValues.get(9) != null) {
						dischargeCountry.add(formValues.get(9));
					} else {
						dischargeCountry.add("");
					}
					
					if(formValues.get(10) != null) {
						category.add(formValues.get(10));
					} else {
						category.add("");
					}
					
					if(formValues.get(11) != null) {
						billingClass.add(formValues.get(11));
					} else {
						billingClass.add("");
					}
					
					if(formValues.get(12) != null) {
						subClass.add(formValues.get(12));
					} else {
						subClass.add("");
					}
					
					if(formValues.get(13) != null) {
						uldType.add(formValues.get(13));
					} else {
						uldType.add("");
					}
					/*Added by indu*/
					if(formValues.get(14) != null) {
						uldGrp.add(formValues.get(14));
					} else {
						uldGrp.add("");
					}
					/*Added by indu ends*/
					if(formValues.get(15) != null) {
						flightNum.add(formValues.get(15));
					} else {
						flightNum.add("");
					}
					//Added as part of Bug ICRD-106391 starts
					if(formValues.get(16) != null) {
						transferredBy.add(formValues.get(16));
					} else {
						transferredBy.add("");
					}
					
					if(formValues.get(17) != null) {
						transferredPA.add(formValues.get(17));
					} else {
						transferredPA.add("");
					}
					
					if(formValues.get(18) != null) {
						company.add(formValues.get(18));
					} else {
						company.add("");
					}
					
					if(formValues.get(19) != null) {
						subClassGroup.add(formValues.get(19));
					} else {
						subClassGroup.add("");
					}
					//Added as part of Bug ICRD-106391 ends
					//added by a7540
					if(formValues.get(20) != null) {
						orgAirport.add(formValues.get(20));
					} else {
						orgAirport.add("");
					}
					
					if(formValues.get(21) != null) {
						desAirport.add(formValues.get(21));
					} else {
						desAirport.add("");
					}
					
					if(formValues.get(22) != null) {
						viaPoint.add(formValues.get(22));
					} else {
						viaPoint.add("");
					}
					
					
					if(formValues.get(23) != null) {
						mailService.add(formValues.get(23));
					} else {
						mailService.add("");
					}
					
					
					if(formValues.get(24) != null) {
						paBuilt.add(formValues.get(24));
					} else {
						paBuilt.add("");
					}
					if(formValues.get(25) != null) {
						uplAirport.add(formValues.get(25));
					} else {
						uplAirport.add("");
					}
					if(formValues.get(26) != null) {
						disAirport.add(formValues.get(26));
					} else {
						disAirport.add("");
					}
					if(formValues.get(25) != null) {
						upliftAirport.add(formValues.get(25));
					} else {
						upliftAirport.add("");
					}
					if(formValues.get(26) != null) {
						dischargeAirport.add(formValues.get(26));
					} else {
						dischargeAirport.add("");
					}
					if(formValues.get(29) != null) {
						flownCarrier.add(formValues.get(29));
					} else {
						flownCarrier.add("");
					}
					index++;
				
					}
					} else {
						log.log(Log.INFO, "Deleted vo-------->>", blgLineVO);
					}
				}
				
			} else {
			log.log(log.INFO,"No Billing Line Details Set in session");
		}
			if(orgCity.size() > 0) {
				form.setOrgCity((String[])orgCity.toArray(new String[orgCity.size()]));
			}
			if(orgCountry.size() > 0) {
				form.setOrgCountry(orgCountry.toArray(new String[orgCountry.size()]));
			}
			
			if(orgRegion.size() > 0) {
				form.setOrgRegion((String[])orgRegion.toArray(new String[orgRegion.size()]));
			}
			
			if(destCity.size() > 0) {
				form.setDestCity((String[])destCity.toArray(new String[destCity.size()]));
			}
			
			if(destCountry.size() > 0) {
				form.setDestCountry(destCountry.toArray(new String[destCountry.size()]));
			}
			
			if(destRegion.size() > 0) {
				form.setDestRegion((String[])destRegion.toArray(new String[destRegion.size()]));
			}
			
			if(upliftCity.size() > 0) {
				form.setUpliftCity(upliftCity.toArray(new String[upliftCity.size()]));
			}
			
			if(upliftCountry.size() > 0) {
				form.setUpliftCountry(upliftCountry.toArray(new String[upliftCountry.size()]));
			}
			
			if(dischargeCountry.size() > 0) {
				form.setDischargeCountry(dischargeCountry.toArray(new String[dischargeCountry.size()]));
			}
			
			if(dischargeCity.size() > 0) {
				form.setDischargeCity(dischargeCity.toArray(new String[dischargeCity.size()]));
			}
			
			if(uldType.size() > 0) {
				form.setBillingUldType(uldType.toArray(new String[uldType.size()]));
			}
			
			if(uldGrp.size() > 0) {
				form.setBillingUldGrp(uldGrp.toArray(new String[uldGrp.size()]));
			}
			
			if(flightNum.size() > 0) {
				form.setBillingFlightNo(flightNum.toArray(new String[flightNum.size()]));
			}
			
			if(category.size() > 0) {
				form.setBillingCategory(category.toArray(new String[category.size()]));
			}
			
			if(billingClass.size() > 0) {
				form.setBillingClasses(billingClass.toArray(new String[billingClass.size()]));
			}
			
			if(subClass.size() > 0) {
				form.setBillingSubClass(subClass.toArray(new String [subClass.size()]));
			}
			//Added as part of Bug ICRD-106391 starts
			if(transferredBy.size() > 0) {
				form.setTransferredBy(transferredBy.toArray(new String [transferredBy.size()]));
			}
			
			if(transferredPA.size() > 0) {
				form.setTransferredPA(transferredPA.toArray(new String [transferredPA.size()]));
			}
			
			if(company.size() > 0) {
				form.setCompany(company.toArray(new String [company.size()]));
			}
			
			if(subClassGroup.size() > 0) {
				form.setSubClassGroup(subClassGroup.toArray(new String [subClassGroup.size()]));
			}
			
			//Added as part of Bug ICRD-106391 ends
			
			//Added as part of icrd-232319
			if (orgAirport.size() > 0) {
				form.setOrgAirport(orgAirport.toArray(new String[orgAirport.size()]));
			}
			
			if (desAirport.size() > 0) {
				form.setDesAirport(desAirport.toArray(new String[desAirport.size()]));
			}
			
			if (viaPoint.size() > 0) {
				form.setViaPoint(viaPoint.toArray(new String[viaPoint.size()]));
		    }
			
			if (mailService.size() > 0) {
				form.setMailService(mailService.toArray(new String[mailService.size()]));
			}
			
			if(paBuilt.size() > 0){
				form.setPaBuilt(paBuilt.toArray(new String[paBuilt.size()]));
			}
			if(uplAirport.size() > 0){
				form.setUplAirport(uplAirport
						.toArray(new String[uplAirport.size()]));
			}if(disAirport.size() > 0){
				form.setDisAirport(disAirport
						.toArray(new String[disAirport.size()]));
			}
			if(upliftAirport.size() > 0){
				form.setUpliftAirport(upliftAirport.toArray(new String[upliftAirport.size()]));
			}
			if(dischargeAirport.size() > 0){
				form.setDischargeAirport(dischargeAirport.toArray(new String[dischargeAirport.size()]));
			}
			if(!flownCarrier.isEmpty()){
				form.setFlownCarrier(flownCarrier.toArray(new String[flownCarrier.size()]));
			}
//			 Return in case of any Errors.			
			if(invocationContext.getErrors()!=null){
				log.log(Log.INFO, "Erros....size..:", invocationContext.getErrors().size());
					Collection<ErrorVO> errors = invocationContext.getErrors();
					invocationContext.target = FMTVIEW_FAILURE;
					return;
			}
			
			log.exiting(CLASS_NAME, "execute");
			invocationContext.target = FMTVIEW_SUCCESS;
		
		return;
	}

	private ArrayList<String> populateValues(ViewBillingLineForm form, 
			Collection<BillingLineParameterVO> blgLineParameters,int index,ViewBillingLineSession session){
		String orgCountry = null;
		String orgRegion = null;
		String orgCity =null;
		String destCountry = null;
		String destRegion = null;
		String destCity =null;
		String upliftCountry=null;
		String upliftCity=null;
		String upliftAirport=null;
		String dischargeAirport=null;
		String dischargeCountry=null;
		String dischargeCity=null;
		String flightNum=null;
		String billingClass=null;
		String subClass=null;
		String uldType=null;
		String uldGrp=null;
		String category=null;
		//Added as part of Bug ICRD-106391 starts
		String transferredBy=null;
		String transferredPA=null;
		String company=null;
		String subClassGroup=null;
		//Added by A-7540
		String orgAirport=null;
		String desAirport=null;
		String viaPoint=null;
		String mailService=null;
		String paBuilt=null;
		String uplAirport=null;
		String disAirport=null;
		String flownCarrier=null;
		//Added as part of Bug ICRD-106391 ends
		
		
		ArrayList<String> values = new ArrayList<String>();
		Collection<BillingLineParameterVO> newParams =
    		new ArrayList<BillingLineParameterVO>();
    	BillingLineParameterVO newVO = new BillingLineParameterVO();
    	for(BillingLineParameterVO parVO: blgLineParameters){
    	if (parVO.getParameterValue()!=null && parVO.getParameterValue().trim().length()>0)	
    	{
    		String[] parCode= parVO.getParameterValue().split(",");
    		if(parCode!=null && parCode.length>0){
    			for(String par : parCode){
		    			newVO = new BillingLineParameterVO();
		    			newVO.setExcludeFlag(parVO.getExcludeFlag());
		    			newVO.setParameterCode(parVO.getParameterCode());
		    			newVO.setParameterValue(par);
		    			newParams.add(newVO);
		    		}}
    		}
    	}
    	blgLineParameters = newParams;
		if(blgLineParameters != null && blgLineParameters.size() > 0) {
			for(BillingLineParameterVO blgLineParameterVO : blgLineParameters ){
				log.log(Log.INFO, "Billing Line parameters....",
						blgLineParameterVO);
				if(blgLineParameterVO.getParameterCode().equals(CONST_ORGCNT)){
					if(orgCountry == null){
						orgCountry = new StringBuilder(blgLineParameterVO.
								getParameterValue()).append("(").append(
										getFlag(blgLineParameterVO.getExcludeFlag())).
										append(")").toString();
					}
					else{
						orgCountry = new StringBuilder(orgCountry).append(",").
								append(blgLineParameterVO.getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
										append(")").toString();
					}
					
				}
				else if(blgLineParameterVO.getParameterCode().equals(CONST_ORGCTY)){
					if(orgCity == null){
						orgCity = new StringBuilder(blgLineParameterVO.
								getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
					else{
						orgCity = new StringBuilder(orgCity).append(",").
								append(blgLineParameterVO.getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
				}
				else if(blgLineParameterVO.getParameterCode().equals(CONST_ORGREG)){
					if(orgRegion == null){
						orgRegion = new StringBuilder(blgLineParameterVO.
								getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
					else{
						orgRegion = new StringBuilder(orgRegion).append(",").
								append(blgLineParameterVO.getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
				}
				else if(blgLineParameterVO.getParameterCode().equals(CONST_DSTREG)){
					if(destRegion == null){
						destRegion = new StringBuilder(blgLineParameterVO.
								getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
					else{
						destRegion = new StringBuilder(destRegion).append(",").
								append(blgLineParameterVO.getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
				}
				else if(blgLineParameterVO.getParameterCode().equals(CONST_DSTCNT)){
					if(destCountry == null){
						destCountry = new StringBuilder(blgLineParameterVO.
								getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
					else{
						destCountry = new StringBuilder(destCountry).append(",").
								append(blgLineParameterVO.getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
					
				}
				else if(blgLineParameterVO.getParameterCode().equals(CONST_DSTCTY)){
					if(destCity == null){
						destCity = new StringBuilder(blgLineParameterVO.
								getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
					else{
						destCity = new StringBuilder(destCity).append(",").
								append(blgLineParameterVO.getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
				}
				else if(blgLineParameterVO.getParameterCode().equals(CONST_UPLCNT)){
					if(upliftCountry == null){
						upliftCountry = new StringBuilder(blgLineParameterVO.
								getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
					else{
						upliftCountry = new StringBuilder(upliftCountry).append(",").
								append(blgLineParameterVO.getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
				}
				else if(blgLineParameterVO.getParameterCode().equals(CONST_UPLCTY)){
					if(upliftCity == null){
						upliftCity = new StringBuilder(blgLineParameterVO.
								getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
					else{
						upliftCity = new StringBuilder(upliftCity).append(",").
								append(blgLineParameterVO.getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
				}
				else if(blgLineParameterVO.getParameterCode().equals(CONST_DISCTY)){
					if(dischargeCity == null){
						dischargeCity = new StringBuilder(blgLineParameterVO.
								getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
					else{
						dischargeCity = new StringBuilder(dischargeCity).append(",").
								append(blgLineParameterVO.getParameterValue()).append("(").
								append(getFlag(blgLineParameterVO.getExcludeFlag())).
								append(")").toString();
					}
				}
			
			else if(blgLineParameterVO.getParameterCode().equals(CONST_DISCNT)){
				if(dischargeCountry == null){
					dischargeCountry = new StringBuilder(blgLineParameterVO.
							getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					dischargeCountry = new StringBuilder(dischargeCountry).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_FLTNUM)){
				if(flightNum == null){
					flightNum = new StringBuilder(blgLineParameterVO.
							getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					flightNum = new StringBuilder(flightNum).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_CLASS)){
				if(billingClass == null){
					billingClass = new StringBuilder(blgLineParameterVO.
							getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					billingClass = new StringBuilder(billingClass).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_SUBCLS)){
				if(subClass == null){
					subClass = new StringBuilder(blgLineParameterVO.
							getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					subClass = new StringBuilder(subClass).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_CAT)){
				if(category == null){
					category = new StringBuilder(blgLineParameterVO.
							getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}                                              
				else{
					category = new StringBuilder(category).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_ULDTYP)){
				if(uldType == null){
					uldType = new StringBuilder(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					uldType = new StringBuilder(uldType).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_ULDGRP)){
				if(uldGrp == null){
					uldGrp = new StringBuilder(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					uldGrp = new StringBuilder(uldGrp).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
				//Added as part of Bug ICRD-106391 starts
			else if(blgLineParameterVO.getParameterCode().equals(CONST_TRFBY)){
				if(transferredBy == null){
					transferredBy = new StringBuilder(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					transferredBy = new StringBuilder(transferredBy).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_TRFPSA)){
				if(transferredPA == null){
					transferredPA = new StringBuilder(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					transferredPA = new StringBuilder(transferredPA).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_MALCMPCOD)){
				if(company == null){
					company = new StringBuilder(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					company = new StringBuilder(company).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_SUBCLASSGROUP)){
				if(subClassGroup == null){
					subClassGroup = new StringBuilder(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
				else{
					subClassGroup = new StringBuilder(subClassGroup).append(",").
							append(blgLineParameterVO.getParameterValue()).append("(").
							append(getFlag(blgLineParameterVO.getExcludeFlag())).
							append(")").toString();
				}
			}
				//Added as part of Bug ICRD-106391 ends
				//added by a-7540 starts
			else if (blgLineParameterVO.getParameterCode().equals(CONST_ORGAIR)){
				if (orgAirport == null) {
					orgAirport = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					orgAirport = new StringBuilder(orgAirport)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}

			}
			else if (blgLineParameterVO.getParameterCode().equals(CONST_DESAIR)){
				if (desAirport == null) {
					desAirport = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					desAirport = new StringBuilder(desAirport)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}

			}
			else if (CONST_UPLARP.equals(blgLineParameterVO.getParameterCode()) &&
					blgLineParameterVO.getParameterValue()!=null){
				if (uplAirport == null) {
					uplAirport = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					uplAirport = new StringBuilder(uplAirport)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			}
			else if (CONST_DISARP.equals(blgLineParameterVO.getParameterCode()) &&
					blgLineParameterVO.getParameterValue()!=null){
				if (disAirport == null) {
					disAirport = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					disAirport = new StringBuilder(disAirport)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			}
			else if (blgLineParameterVO.getParameterCode().equals(CONST_VIAPNT)){
				if (viaPoint == null) {
					viaPoint = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					viaPoint = new StringBuilder(viaPoint)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}

			}
			else if (blgLineParameterVO.getParameterCode().equals(CONST_MALSRV)){
				if (mailService == null) {
					mailService = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					mailService = new StringBuilder(mailService)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
		}
			else if(blgLineParameterVO.getParameterCode().equals(CONST_PABUILT)){
				if(paBuilt == null){
					paBuilt = new StringBuilder(blgLineParameterVO
							.getParameterValue()).toString();
				}else {
					paBuilt = new StringBuilder(paBuilt)
					.toString();
		}
				
			}
				else if (blgLineParameterVO.getParameterCode().equals(CONST_UPLARP)){
					if (upliftAirport == null) {
						upliftAirport = new StringBuilder(blgLineParameterVO
								.getParameterValue()).append("(").append(
								getFlag(blgLineParameterVO.getExcludeFlag()))
								.append(")").toString();
					} else {
						upliftAirport = new StringBuilder(upliftAirport)
								.append(",")
								.append(blgLineParameterVO.getParameterValue())
								.append("(")
								.append(
										getFlag(blgLineParameterVO.getExcludeFlag()))
								.append(")").toString();
					}

				}
				else if (blgLineParameterVO.getParameterCode().equals(CONST_DISARP)){
					if (dischargeAirport == null) {
						dischargeAirport = new StringBuilder(blgLineParameterVO
								.getParameterValue()).append("(").append(
								getFlag(blgLineParameterVO.getExcludeFlag()))
								.append(")").toString();
					} else {
						dischargeAirport = new StringBuilder(dischargeAirport)
								.append(",")
								.append(blgLineParameterVO.getParameterValue())
								.append("(")
								.append(
										getFlag(blgLineParameterVO.getExcludeFlag()))
								.append(")").toString();
					}

				}
			else{
			if (CONST_FLNCAR.equals(blgLineParameterVO.getParameterCode())) {
					if (flownCarrier == null) {
						flownCarrier = new StringBuilder(blgLineParameterVO.getParameterValue()).append("(")
								.append(getFlag(blgLineParameterVO.getExcludeFlag())).append(")").toString();
					} else {
						flownCarrier = new StringBuilder(flownCarrier).append(",")
								.append(blgLineParameterVO.getParameterValue()).append("(")
								.append(getFlag(blgLineParameterVO.getExcludeFlag())).append(")").toString();
					}
					}
				}
			//Added by A-7540 ends
			}
		}
		
			values.add(orgCity);
		
			values.add(orgCountry);
		
			values.add(orgRegion);
		
			values.add(destCity);
		
			values.add(destCountry);
	
			values.add(destRegion);
		
			values.add(upliftCity);
	
			values.add(upliftCountry);
		
			values.add(dischargeCity);
		
			values.add(dischargeCountry);
			
			values.add(category);
			
			values.add(billingClass);
			
			values.add(subClass);
			
			values.add(uldType);
			
			values.add(uldGrp);
			
			values.add(flightNum);
			//Added as part of Bug ICRD-106391 starts
			
			values.add(transferredBy);
			
			values.add(transferredPA);
			
			values.add(company);
			
			values.add(subClassGroup);
			//Added as part of Bug ICRD-106391 ends
			
			//added by a7540
			values.add(orgAirport);
			
			values.add(desAirport);
			
			values.add(viaPoint);
			
			values.add(mailService);
			
			values.add(paBuilt);
			values.add(uplAirport);
				
			values.add(disAirport);

			values.add(upliftAirport);

			values.add(dischargeAirport);
			
			values.add(flownCarrier);
			
		log.log(Log.INFO, "Values to form...", values);
		return values;
		
		
	}
	private String 	getFlag(String flag){
	
	if("Y".equals(flag)){
		return "E";
	}
	else
		if("N".equals(flag)){
			return "I";
		}
	return flag;
}
	
	}
