/*
 * FmtBillingLinesCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
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

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

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

	private static final String CONST_DISCNT = "DISCNT";

	private static final String CONST_DISCTY = "DISCTY";

	private static final String CONST_ULDTYP = "ULDGRP";

	private static final String CONST_CLASS = "CLS";
	
	private static final String CONST_SUBCLS_GRP ="SUBCLSGRP";

	private static final String CONST_SUBCLS = "SUBCLS";

	private static final String CONST_CAT = "CAT";

	private static final String CONST_FLTNUM = "FLTNUM";
	
	private static final String CONST_TRASFEREDBY = "TRFBY";
	
	private static final String  CONST_TRANSFEREDPA = "TRFPOA";
	
	private static final String OPFLAG_DEL = "D";

	private static final String KEY_BLG_CATEGORY = "mailtracking.defaults.mailcategory";

	private static final String KEY_BLG_CLASS = "mailtracking.defaults.mailclass";

	private static final String KEY_RATE_STATUS = "mra.gpabilling.ratestatus";

	private static final String KEY_BILLED_SECTOR = "mailtracking.mra.billingSector";

	private static final String CLASS_NAME = "FmtBillingLinesCommand";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String KEY_BILLING_PARTY = "mailtracking.mra.billingparty";
	private static final String KEY_UNTCOD ="mra.gpabilling.untcod";

	private static final String CONST_AGENT = "AGT";//Added by A-7531 for icrd-224979

	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";//added by a-7871 for ICRD-214766

	//Added by -7540
	private static final String CONST_ORGAIR="ORGARPCOD";
	private static final String CONST_DESAIR="DSTARPCOD";
 	private static final String CONST_VIAPNT="VIAPNT";
	private static final String CONST_MALSRV="MALSRVLVL";
	private static final String CONST_PABUILT="POAFLG";
	private static final String CONST_UPLARP="UPLARPCOD";
	private static final String CONST_DISARP="DISARPCOD";
	private static final String CONST_FLNCAR="FLNCAR";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
 
		log.entering(CLASS_NAME, "execute");
		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;

		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE, SCREENID);

		// Return in case of any Errors.

		Collection<ErrorVO> errs = setOneTimeValues(session);
		if (errs != null && errs.size() > 0) {
			invocationContext.addAllError(errs);
		}

		Page<BillingLineVO> billingLineDetails = null;

		ArrayList<String> formValues = null;

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
		
		ArrayList<String> transferedBy = new ArrayList<String>();
		
		ArrayList<String> transferedPA = new ArrayList<String>();
		
		ArrayList<String> uldType = new ArrayList<String>();

		ArrayList<String> billingClass = new ArrayList<String>();
		
		ArrayList<String> subClass = new ArrayList<String>();

		ArrayList<String> subClassGroup = new ArrayList<String>();

		ArrayList<String> category = new ArrayList<String>();
		
		ArrayList<String> mailCompany = new ArrayList<String>();

		ArrayList<String> agentCode = new ArrayList<String>();//Added by a-7531 for icrd-224979
		
        //Added by a7540
		ArrayList<String> orgAirport = new ArrayList<String>();
		ArrayList<String> desAirport = new ArrayList<String>();
		ArrayList<String> viaPoint = new ArrayList<String>();
		ArrayList<String> mailService = new ArrayList<String>();
		ArrayList<String> paBuilt = new ArrayList<String>();
		String params[] = new String[50];

		ArrayList<String> uplAirport = new ArrayList<String>();
		ArrayList<String> disAirport = new ArrayList<String>();
		ArrayList<String> flownCarrier = new ArrayList<>();
		
		billingLineDetails = session.getBillingLineDetails();
		BillingMatrixVO billingMatrixVO = session.getBillingMatrixVO();
		if (billingMatrixVO != null
				&& billingMatrixVO.getOperationFlag() != null) {
			form.setOperationFlag(billingMatrixVO.getOperationFlag());
		}
		log.log(Log.INFO, "VO from session is --->\n", billingMatrixVO);
		if (billingLineDetails != null) {
			log.log(Log.INFO, "No of billing lines", billingLineDetails.getActualPageSize());
			int index = 0;

			StringBuilder strBldr = null;

			for (BillingLineVO blgLineVO : billingLineDetails) {
				strBldr = new StringBuilder();
				// String
				// billingSector=getBilledSectorValue(blgLineVO.getBillingSector(),session);
				if (!OPFLAG_DEL.equals(blgLineVO.getOperationFlag())) {
					Collection<BillingLineParameterVO> blgLineParameters = blgLineVO
							.getBillingLineParameters();
					formValues = populateValues(form, blgLineParameters, index,
							session);
					// int flag=0;
					/** *************************************************************** */
					/* formatting parameters */
					if (formValues.get(0) != null || formValues.get(1) != null
							|| formValues.get(2) != null) {
						strBldr.append("< Origin - ");
						// flag=1;
					}
					if (formValues.get(0) != null) {
						orgCity.add(formValues.get(0));
						strBldr.append(formValues.get(0));
					} else {
						orgCity.add("");
					}
					if (formValues.get(0) != null && formValues.get(1) == null
							&& formValues.get(2) == null) {
						strBldr.append(">");
					}
					if (formValues.get(1) != null) {
						strBldr.append("|");
						orgCountry.add(formValues.get(1));
						strBldr.append(formValues.get(1));

					} else {
						orgCountry.add("");
					}
					if (formValues.get(0) != null && formValues.get(1) != null
							&& formValues.get(2) == null) {
						strBldr.append(">");
					}
					if (formValues.get(2) != null) {
						strBldr.append("|");
						orgRegion.add(formValues.get(2));
						strBldr.append(formValues.get(2));
						strBldr.append(">");
					} else {
						orgRegion.add("");
					/* formatting parameters */
					/** *************************************************************** */
					}

					if (formValues.get(3) != null || formValues.get(4) != null
							|| formValues.get(5) != null) {
						strBldr.append("< Destination - ");
						// flag=1;
					}

					if (formValues.get(3) != null) {
						destCity.add(formValues.get(3));
						strBldr.append(formValues.get(3));

					} else {
						destCity.add("");
					}
					if (formValues.get(3) != null && formValues.get(4) == null
							&& formValues.get(5) == null) {
						strBldr.append(">");

					}
					if (formValues.get(4) != null) {
						strBldr.append("|");
						destCountry.add(formValues.get(4));
						strBldr.append(formValues.get(4));

					} else {
						destCountry.add("");
					}
					if (formValues.get(3) != null && formValues.get(4) != null
							&& formValues.get(5) == null) {
						strBldr.append(">");
					}
					if (formValues.get(5) != null) {
						strBldr.append("|");
						destRegion.add(formValues.get(5));
						strBldr.append(formValues.get(5));
						strBldr.append(">");
					} else {
						destRegion.add("");
					/** ******************************************* */
					}

					if (formValues.get(6) != null || formValues.get(7) != null) {
						strBldr.append("< Uplifts - ");
					}

					if (formValues.get(6) != null) {
						upliftCity.add(formValues.get(6));
						strBldr.append(formValues.get(6));

					} else {
						upliftCity.add("");
					}
					if (formValues.get(6) != null && formValues.get(7) == null) {
						strBldr.append(">");
					}
					if (formValues.get(7) != null) {
						strBldr.append("|");
						upliftCountry.add(formValues.get(7));
						strBldr.append(formValues.get(7));
						strBldr.append(">");
					} else {
						upliftCountry.add("");
					/** ****************************** */
					}

					if (formValues.get(8) != null || formValues.get(9) != null) {
						strBldr.append("< Discharge - ");
					}

					if (formValues.get(8) != null) {
						dischargeCity.add(formValues.get(8));
						strBldr.append(formValues.get(8));
					} else {
						dischargeCity.add("");
					}
					if (formValues.get(8) != null && formValues.get(9) == null) {
						strBldr.append(">");
					}
					if (formValues.get(9) != null) {
						strBldr.append("|");
						dischargeCountry.add(formValues.get(9));
						strBldr.append(formValues.get(9));
						strBldr.append(">");
					} else {
						dischargeCountry.add("");
					/** **************************************** */
					}

					if (formValues.get(10) != null) {
						strBldr.append("< Category - ");
						category.add(formValues.get(10));
						strBldr.append(formValues.get(10));
						strBldr.append(">");
					} else {
						category.add("");
					}

					if (formValues.get(11) != null) {
						strBldr.append("< Class - ");
						billingClass.add(formValues.get(11));
						strBldr.append(formValues.get(11));
						strBldr.append(">");
					} else {
						billingClass.add("");
					}
					
					if (formValues.get(12) != null) {
						strBldr.append("< subClass - ");
						subClass.add(formValues.get(12));
						strBldr.append(formValues.get(12));
						strBldr.append(">");
					} else {
						subClass.add("");
					}

					if (formValues.get(13) != null) {
						strBldr.append("< SubClass group - ");
						subClassGroup.add(formValues.get(13));
						strBldr.append(formValues.get(13));
						strBldr.append(">");
					} else {
						subClassGroup.add("");
					}

					if (formValues.get(14) != null) {
						strBldr.append("< Uld - ");
						uldType.add(formValues.get(14));
						strBldr.append(formValues.get(14));
						strBldr.append(">");
					} else {
						uldType.add("");
					}

					if (formValues.get(15) != null) {
						strBldr.append("< Flight No. - ");
						flightNum.add(formValues.get(15));
						strBldr.append(formValues.get(15));
						strBldr.append(">");
					} else {
						flightNum.add("");
					}
					if (formValues.get(16) != null) {
						
						strBldr.append("< Transfered By. - ");
						transferedBy.add(formValues.get(16));
						strBldr.append(formValues.get(16));
						strBldr.append(">");
					} else {
						transferedBy.add("");
					}
					if (formValues.get(17) != null) {
						
						strBldr.append("< TransferedPA. - ");
						transferedPA.add(formValues.get(17));
						strBldr.append(formValues.get(17));
						strBldr.append(">");
					} else {
						transferedPA.add("");
					}
					
					if (formValues.get(18) != null) {
						
						strBldr.append("< MailCompanyCode. - ");
						mailCompany.add(formValues.get(18));
						strBldr.append(formValues.get(18));
						strBldr.append(">");
					} else {
						mailCompany.add("");
					}
                   //Added by A-7531 for icrd-224979
                    if (formValues.get(19) != null) {

						strBldr.append("< AgentCode. - ");
						agentCode.add(formValues.get(19));
						strBldr.append(formValues.get(19));
						strBldr.append(">");
					} else {
						agentCode.add("");
					}
                    
                    //Added by a7540
                    if(formValues.get(20) != null) {
                    	strBldr.append("< OriginAirport. - ");
						orgAirport.add(formValues.get(20));
						strBldr.append(formValues.get(20));
						strBldr.append(">");
                    }else {
                    	orgAirport.add("");
					}
                    if(formValues.get(21) != null) {
                    	strBldr.append("< DestinationAirport. - ");
						desAirport.add(formValues.get(21));
						strBldr.append(formValues.get(21));
						strBldr.append(">");
                    }else {
                    	desAirport.add("");
					}
                    if(formValues.get(22) != null) {
                    	strBldr.append("< Viapoint. - ");
						viaPoint.add(formValues.get(22));
						strBldr.append(formValues.get(22));
						strBldr.append(">");
                    }else {
                    	viaPoint.add("");
					}
                    if(formValues.get(23) != null) {
                    	strBldr.append("< MailService. - ");
						mailService.add(formValues.get(23));
						strBldr.append(formValues.get(23));
						strBldr.append(">");
                    }else {
                    	mailService.add("");
					}
                    
                    if(formValues.get(24) != null) {
                    	strBldr.append("< PABuilt. - ");
                    	paBuilt.add(formValues.get(24));
						strBldr.append(formValues.get(24));
						strBldr.append(">");
                    }
                   else{
                	   paBuilt.add("");
                   }
                    if(formValues.get(25) != null) {
                    	strBldr.append("< Uplift Airport. - ");
                    	uplAirport.add(formValues.get(25));
						strBldr.append(formValues.get(25));
						strBldr.append(">");
                    }
                   else{
                	   uplAirport.add("");
                   }
                    if(formValues.get(26) != null) {
                    	strBldr.append("< Discharge Airport. - ");
                    	disAirport.add(formValues.get(26));
						strBldr.append(formValues.get(26));
						strBldr.append(">");
                    }
                   else{
                	   disAirport.add("");
                   }
                    
                    if(formValues.get(27) != null) {
                    	strBldr.append("< Flown Carrier. - ");
                    	flownCarrier.add(formValues.get(27));
						strBldr.append(formValues.get(27));
						strBldr.append(">");
                    }
                   else{
                	   flownCarrier.add("");
                   }
                    
                    
					params[index] = (String) strBldr.toString();
					log.log(Log.INFO, "PARAM VALUES ", params, index);
					index++;

				} else {
					log.log(Log.INFO, "Deleted vo-------->>", blgLineVO);
				}
			}

		} else {
			log.log(Log.INFO, "No Billing Line Details Set in session");
		}
		if (params.length > 0) {
			form.setParameters(params);
		}
		StringBuilder param = new StringBuilder();
		log.log(Log.INFO, "billingClass..size", billingClass.size());
		log.log(Log.INFO, "billingClass.....", billingClass.toArray(new String[billingClass
		                                                   					.size()]));
		if (orgCity.size() > 0) {
			form.setOrgCity((String[]) orgCity.toArray(new String[orgCity
					.size()]));

		}
		if (orgCountry.size() > 0) {

			form.setOrgCountry(orgCountry
					.toArray(new String[orgCountry.size()]));

		}
		if (orgRegion.size() > 0) {
			form.setOrgRegion((String[]) orgRegion.toArray(new String[orgRegion
					.size()]));

		}
		if (destCity.size() > 0) {
			form.setDestCity((String[]) destCity.toArray(new String[destCity
					.size()]));

		}

		if (destCountry.size() > 0) {
			form.setDestCountry(destCountry.toArray(new String[destCountry
					.size()]));

		}

		if (destRegion.size() > 0) {
			form.setDestRegion((String[]) destRegion
					.toArray(new String[destRegion.size()]));

		}

		if (upliftCity.size() > 0) {
			form.setUpliftCity(upliftCity
					.toArray(new String[upliftCity.size()]));

		}
		//Added by a-7531 for icrd-224979
		if (agentCode.size() > 0) {

			form.setAgentCode(agentCode
					.toArray(new String[agentCode.size()]));

		}
		if (upliftCountry.size() > 0) {
			form.setUpliftCountry(upliftCountry
					.toArray(new String[upliftCountry.size()]));

		}
		if (dischargeCountry.size() > 0) {
			form.setDischargeCountry(dischargeCountry
					.toArray(new String[dischargeCountry.size()]));

		}
		if (dischargeCity.size() > 0) {
			form.setDischargeCity(dischargeCity
					.toArray(new String[dischargeCity.size()]));

		}
		if (uldType.size() > 0) {
			form.setBillingUldType(uldType.toArray(new String[uldType.size()]));

		}
		if (flightNum.size() > 0) {
			form.setBillingFlightNo(flightNum.toArray(new String[flightNum
					.size()]));

		}
		if (transferedBy.size() > 0) {
			form.setTransferedBy(transferedBy.toArray(new String[transferedBy
					.size()]));
		}
		if (transferedPA.size() > 0) {
			form.setTransferedPA(transferedPA.toArray(new String[transferedPA
					.size()]));
		}
		if (mailCompany.size() > 0) {
			form.setMailCompanyCode(mailCompany.toArray(new String[mailCompany
					.size()]));
		}
		if (category.size() > 0) {
			form.setBillingCategory(category
					.toArray(new String[category.size()]));

		}
		if (billingClass.size() > 0) {
			log.log(Log.INFO, "billingClass..size inside", billingClass.size());
			form.setBillingClass(billingClass.toArray(new String[billingClass
					.size()]));

		}
		if (subClass.size() > 0) {
			form.setSubClass(subClass
					.toArray(new String[subClass.size()]));

		}
		if (subClassGroup.size() > 0) {
			form.setBillingSubClass(subClassGroup
					.toArray(new String[subClassGroup.size()]));

		}
		//Added by a-7540 starts
		if (orgAirport.size() > 0) {
			form.setOrgAirport(orgAirport
					.toArray(new String[orgAirport.size()]));

		}
		if (desAirport.size() > 0) {
			form.setDesAirport(desAirport
					.toArray(new String[desAirport.size()]));

		}
		if (viaPoint.size() > 0) {
			form.setViaPoint(viaPoint
					.toArray(new String[viaPoint.size()]));

		}
		if (mailService.size() > 0) {
			form.setMailService(mailService
					.toArray(new String[mailService.size()]));
		}
		if(paBuilt.size() > 0){
			form.setPaBuilt(paBuilt
					.toArray(new String[paBuilt.size()]));
		}
		if(uplAirport.size() > 0){
			form.setUplAirport(uplAirport
					.toArray(new String[uplAirport.size()]));
		}if(disAirport.size() > 0){
			form.setDisAirport(disAirport
					.toArray(new String[disAirport.size()]));
		}
		
		if(!flownCarrier.isEmpty()){
			form.setFlownCarrier(flownCarrier
					.toArray(new String[flownCarrier.size()]));
		}
		
		//Added by a-7540 ends
		log.log(Log.INFO, "parameter value ", param.toString());
		if (param != null) {
			// form.setParameters(param.toArray(new String [subClass.size()]));
		}
		log.exiting(CLASS_NAME, "execute");
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {
			log.log(Log.FINE, "Erros....size..:", invocationContext.getErrors().size());
			invocationContext.target = FMTVIEW_FAILURE;
			return;
		} else {
			invocationContext.target = FMTVIEW_SUCCESS;
		}
		if (session.getBillingMatrixVO() != null) {
			if (session.getBillingMatrixVO().getValidityStartDate() != null) {
				form.setValidFrom(session.getBillingMatrixVO()
						.getValidityStartDate().toDisplayDateOnlyFormat());
			}
			if (session.getBillingMatrixVO().getValidityEndDate() != null) {
				form.setValidTo(session.getBillingMatrixVO()
						.getValidityEndDate().toDisplayDateOnlyFormat());
			}
			form.setBlgMatrixID(session.getBillingMatrixVO()
					.getBillingMatrixId());
			form.setDescription(session.getBillingMatrixVO().getDescription());
			log.log(Log.INFO, "opflag ", session.getBillingMatrixVO().getOperationFlag());
			if("I".equals(session.getBillingMatrixVO().getOperationFlag())){
				session.getBillingMatrixVO().setBillingMatrixStatus("New");
				//form.setStatus("N");
			}
		}
		return;
	}

	private ArrayList<String> populateValues(BillingMatrixForm form,
			Collection<BillingLineParameterVO> blgLineParameters, int index,
			MaintainBillingMatrixSession session) {
		String orgCountry = null;
		String orgRegion = null;
		String orgCity = null;
		String destCountry = null;
		String destRegion = null;
		String destCity = null;
		String upliftCountry = null;
		String upliftCity = null;
		String dischargeCountry = null;
		String dischargeCity = null;
		String flightNum = null;
		String transferedBy=null; 
		String transferedPA=null; 
		String billingClass = null;
		String subClassGroup = null;
		String subClass = null;
		String uldType = null;
		String category = null;
		String mailCompanyCode = null;
		String agent = null;//Added by a-7531 for icrd-224979

		//Added by A-7540
		String orgAirport=null;
		String desAirport=null;
		String viaPoint=null;
		String mailService=null;
		String paBuilt=null;

		String uplAirport=null;
		String disAirport=null;
		String flownCarrier=null;
		
		ArrayList<String> values = new ArrayList<String>();
		
		Collection<BillingLineParameterVO> newParams =
    		new ArrayList<BillingLineParameterVO>();
    	BillingLineParameterVO newVO = new BillingLineParameterVO();
    	for(BillingLineParameterVO parVO: blgLineParameters){
    		String[] parCode= parVO.getParameterValue().split(",");
    		if(parCode!=null && parCode.length>0){
    			for(String par : parCode){
		    			newVO = new BillingLineParameterVO();
		    			newVO.setExcludeFlag(parVO.getExcludeFlag());
		    			newVO.setParameterCode(parVO.getParameterCode());
		    			newVO.setParameterValue(par);
		    			newParams.add(newVO);
		    		}
    		}
    	}
    	blgLineParameters = newParams;

		for (BillingLineParameterVO blgLineParameterVO : blgLineParameters) {
			log
					.log(Log.INFO, "Billing Line parameters....",
							blgLineParameterVO);
			if (blgLineParameterVO.getParameterCode().equals(CONST_ORGCNT)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (orgCountry == null) {
					orgCountry = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					orgCountry = new StringBuilder(orgCountry)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}

			} else if (blgLineParameterVO.getParameterCode().equals(CONST_ORGCTY)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (orgCity == null) {
					orgCity = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					orgCity = new StringBuilder(orgCity)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} else if (blgLineParameterVO.getParameterCode().equals(CONST_ORGREG)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (orgRegion == null) {
					orgRegion = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					orgRegion = new StringBuilder(orgRegion)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} else if (blgLineParameterVO.getParameterCode().equals(CONST_DSTREG)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (destRegion == null) {
					destRegion = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					destRegion = new StringBuilder(destRegion)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} else if (blgLineParameterVO.getParameterCode().equals(CONST_DSTCNT)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (destCountry == null) {
					destCountry = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					destCountry = new StringBuilder(destCountry)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}

			} else if (blgLineParameterVO.getParameterCode().equals(CONST_DSTCTY)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (destCity == null) {
					destCity = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					destCity = new StringBuilder(destCity)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} else if (blgLineParameterVO.getParameterCode().equals(CONST_UPLCNT)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (upliftCountry == null) {
					upliftCountry = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					upliftCountry = new StringBuilder(upliftCountry)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} else if (blgLineParameterVO.getParameterCode().equals(CONST_UPLCTY)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (upliftCity == null) {
					upliftCity = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					upliftCity = new StringBuilder(upliftCity)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} 
			//Added by a-7531 for icrd-224979
			else if (blgLineParameterVO.getParameterCode().equals(CONST_AGENT)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (agent == null) {
					agent = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					agent = new StringBuilder(agent)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}

			} 
			//added by a-7540 starts
			else if (blgLineParameterVO.getParameterCode().equals(CONST_ORGAIR)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
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
			else if (blgLineParameterVO.getParameterCode().equals(CONST_DESAIR)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
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
			else if (blgLineParameterVO.getParameterCode().equals(CONST_VIAPNT)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
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
			else if (blgLineParameterVO.getParameterCode().equals(CONST_MALSRV)&& 
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
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
			else if(blgLineParameterVO.getParameterCode().equals(CONST_PABUILT)&&
					blgLineParameterVO.getParameterValue()!=null
					&&blgLineParameterVO.getParameterValue().length()>0){
				if(paBuilt == null){
					paBuilt = new StringBuilder(blgLineParameterVO
							.getParameterValue()).toString();
				}else {
					paBuilt = new StringBuilder(paBuilt)
					.toString();
		}
				
			}
			//Added by A-7540 ends
			else if (blgLineParameterVO.getParameterCode().equals(CONST_DISCTY)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (dischargeCity == null) {
					dischargeCity = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					dischargeCity = new StringBuilder(dischargeCity)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			}

			else if (blgLineParameterVO.getParameterCode().equals(CONST_DISCNT)&&
					blgLineParameterVO.getParameterValue()!=null 
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (dischargeCountry == null) {
					dischargeCountry = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					dischargeCountry = new StringBuilder(dischargeCountry)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} 
			
			else if (blgLineParameterVO.getParameterCode().equals(CONST_FLTNUM) &&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0) {
				if (flightNum == null) {
					flightNum = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					flightNum = new StringBuilder(flightNum)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} else if (blgLineParameterVO.getParameterCode().equals(CONST_TRASFEREDBY)&&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (transferedBy == null) {
					transferedBy = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					transferedBy = new StringBuilder(transferedBy)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} else if (blgLineParameterVO.getParameterCode().equals(CONST_TRANSFEREDPA)&&
					blgLineParameterVO.getParameterValue()!=null 
					&& blgLineParameterVO.getParameterValue().length()>0) {
				if (transferedPA == null) {
					transferedPA = new StringBuilder(blgLineParameterVO
					.getParameterValue()).append("(").append(
					getFlag(blgLineParameterVO.getExcludeFlag()))
					.append(")").toString();
				} else {
				transferedPA = new StringBuilder(transferedPA)
						.append(",")
						.append(blgLineParameterVO.getParameterValue())
						.append("(")
						.append(
						getFlag(blgLineParameterVO.getExcludeFlag()))
						.append(")").toString();
					}
			}else if (blgLineParameterVO.getParameterCode().equals(CONST_CLASS) &&
					blgLineParameterVO.getParameterValue()!=null 
				&& blgLineParameterVO.getParameterValue().length()>0){
				if (billingClass == null) {
					billingClass = new StringBuilder(getClassValue(
							blgLineParameterVO.getParameterValue(), session))
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					billingClass = new StringBuilder(billingClass)
							.append(",")
							.append(
									getClassValue(blgLineParameterVO
											.getParameterValue(), session))
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			}
			else if (blgLineParameterVO.getParameterCode().equals(CONST_SUBCLS) &&
					blgLineParameterVO.getParameterValue()!=null
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (subClass == null) {
					subClass = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					subClass = new StringBuilder(subClass)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			}else if (blgLineParameterVO.getParameterCode().equals(CONST_SUBCLS_GRP) &&
					blgLineParameterVO.getParameterValue()!=null 
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (subClassGroup == null) {
					subClassGroup = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					subClassGroup = new StringBuilder(subClassGroup)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} else if (blgLineParameterVO.getParameterCode().equals(CONST_CAT) &&
					blgLineParameterVO.getParameterValue()!=null	
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (category == null) {
					category = new StringBuilder(getCatValue(blgLineParameterVO
							.getParameterValue(), session)).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					category = new StringBuilder(category).append(",").append(
							getCatValue(blgLineParameterVO.getParameterValue(),
									session)).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			} else if (blgLineParameterVO.getParameterCode().equals(CONST_ULDTYP)&& 
					blgLineParameterVO.getParameterValue()!=null 
					&& blgLineParameterVO.getParameterValue().length()>0){
				if (uldType == null) {
					uldType = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					uldType = new StringBuilder(uldType)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
			}
			else if ("MALCMPCOD".equals(blgLineParameterVO.getParameterCode()) &&
					blgLineParameterVO.getParameterValue()!=null){
				if (mailCompanyCode == null) {
					mailCompanyCode = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					mailCompanyCode = new StringBuilder(mailCompanyCode)
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
			
			
			else if (CONST_FLNCAR.equals(blgLineParameterVO.getParameterCode()) &&
					blgLineParameterVO.getParameterValue()!=null){
				if (flownCarrier == null) {
					flownCarrier = new StringBuilder(blgLineParameterVO
							.getParameterValue()).append("(").append(
							getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				} else {
					flownCarrier = new StringBuilder(flownCarrier)
							.append(",")
							.append(blgLineParameterVO.getParameterValue())
							.append("(")
							.append(
									getFlag(blgLineParameterVO.getExcludeFlag()))
							.append(")").toString();
				}
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
		
		values.add(subClassGroup);

		values.add(uldType);

		values.add(flightNum);
		
		values.add(transferedBy);
		
		values.add(transferedPA);
		
		values.add(mailCompanyCode);
		
		values.add(agent);//Added by a-7531 for icrd-224979 
		
		//added by a7540
		values.add(orgAirport);
		
		values.add(desAirport);
		
		values.add(viaPoint);
		
		values.add(mailService);
		
		values.add(paBuilt);
        values.add(uplAirport);
		
		values.add(disAirport);
		values.add(flownCarrier);
		
		log.log(Log.FINE, "Values to form...", values);
		return values;

	}

	// private String getCategoryDescription(String
	// catVal,MaintainBillingMatrixSession session){
	// // if(session.getOneTimeVOs() != null )
	// // {
	// // if(session.getOneTimeVOs().containsKey(KEY_BLG_CATEGORY)){
	// // log.log(1,"Present status onetimes.........."+catVal+"-");
	// // Collection<OneTimeVO> oneTimeStatus =
	// // session.getOneTimeVOs().get(KEY_BLG_CATEGORY);
	// // if(oneTimeStatus!=null)
	// // for(OneTimeVO vo : oneTimeStatus){
	// // log.log(1,"Present fld val
	// onetimes.........."+catVal+"-"+vo.getFieldValue());
	// // if(vo.getFieldValue().equals(catVal))
	// // return vo.getFieldDescription();
	// // }
	// // }
	// // }
	// // else
	// // log.log(1,"Null one time");
	// return catVal;
	// }
	// private String getClassDescription(String
	// clsVal,MaintainBillingMatrixSession session){
	// // if(session.getOneTimeVOs() != null )
	// // {
	// // if(session.getOneTimeVOs().containsKey(KEY_BLG_CLASS)){
	// // log.log(1,"Present status onetimes.........."+clsVal+"-");
	// // Collection<OneTimeVO> oneTimeStatus =
	// // session.getOneTimeVOs().get(KEY_BLG_CLASS);
	// // if(oneTimeStatus!=null)
	// // for(OneTimeVO vo : oneTimeStatus){
	// // log.log(1,"Present fld val
	// onetimes.........."+clsVal+"-"+vo.getFieldValue());
	// // if(vo.getFieldValue().equals(clsVal))
	// // return vo.getFieldDescription();
	// // }
	// // }
	// // }
	// // else
	// // log.log(1,"Null one time");
	// return clsVal;
	// }

	private Collection<ErrorVO> setOneTimeValues(
			MaintainBillingMatrixSession session) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Map<String, String> systemParameterValues = null;
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();

		/** adding attributes to map for passing to SharedDefaultsDelegate */
		oneTimeActiveStatusList.add(KEY_RATE_STATUS);
		oneTimeActiveStatusList.add(KEY_BLG_CLASS);
		oneTimeActiveStatusList.add(KEY_BLG_CATEGORY);
		oneTimeActiveStatusList.add(KEY_BILLED_SECTOR);
		oneTimeActiveStatusList.add(KEY_BILLING_PARTY);
		oneTimeActiveStatusList.add(KEY_UNTCOD);
		

		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}

		/** setting OneTime hashmap to session */
		// log.log(Log.INFO," the oneTimeHashMap after server call is
		// "+oneTimeHashMap);
		session
				.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeHashMap);
		session.setSystemparametres((HashMap<String, String>)systemParameterValues);
		return errors;
	}

	/**
	 * Method to convert the include/exclide flag as I/E ...
	 * 
	 * @param flag
	 * @return
	 */
	private String getFlag(String flag) {

		if ("Y".equals(flag)) {
			return "E";
		} else if ("N".equals(flag)) {
			return "I";
		}
		return flag;
	}

	private String getCatValue(String val, MaintainBillingMatrixSession session) {
		String desc = null;
		Collection<OneTimeVO> oneTimeVOs = session.getOneTimeVOs().get(
				"mailtracking.defaults.mailcategory");
		for (OneTimeVO oneTimeVO : oneTimeVOs) {
			if (oneTimeVO.getFieldValue().equalsIgnoreCase(val)) {
				desc = oneTimeVO.getFieldDescription();
			}
		}
		return desc;

	}

	private String getClassValue(String val,
			MaintainBillingMatrixSession session) {
		String desc = null;
		Collection<OneTimeVO> oneTimeVOs = session.getOneTimeVOs().get(
				"mailtracking.defaults.mailclass");
		for (OneTimeVO oneTimeVO : oneTimeVOs) {
			if (oneTimeVO.getFieldValue().equalsIgnoreCase(val)) {
				desc = oneTimeVO.getFieldDescription();
			}
		}
		return desc;

	}
	 private Collection<String> getSystemParameterTypes(){
	    	log.entering("RefreshCommand", "getSystemParameterTypes");
	    	ArrayList<String> systemparameterTypes = new ArrayList<String>();

	    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
	    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
	    	return systemparameterTypes;
	      }
}
