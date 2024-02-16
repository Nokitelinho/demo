/*
 * SurchargeCCADetailsCommand.java Created on Jul 9, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeCCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeProrationDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Jul 9, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jul 9, 2015 A-5255
 * First draft
 */

public class SurchargeCCADetailsCommand extends BaseCommand {

	/**
	 * @author A-5255
	 * @param arg0
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "SurchargeCCADetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintaincca";

	private static final String LIST_SUCCESS = "success";
	private static final String LIST_FAILURE = "failure";
	private static final String UPDATE_SUCCESS = "updatesuccess";
	private static final String BASE_CURRENCY = "shared.airline.basecurrency";
	private static final String LIST = "List";
	private static final String UPDATE = "Update";
	private static final String EMPTYSTRING = "";
	private static final String ERROR_DUPLICATECHARGEHEAD = "mailtracking.mra.defaults.maintaincca.duplicatechargehead";
	private static final String ERROR_REVISED_ORIGINAL_CHARGEZERO = "mailtracking.mra.defaults.maintaincca.revisedandoriginalchargezero";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute ");
		MaintainCCASession maintainCCASession = getScreenSession(MODULE_NAME,
				SCREENID);
		double revSurChargeTotal = 0;
		double orgSurChargeTotal = 0;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;
		SurchargeCCAdetailsVO surchargeCCAdetailsVO = null;
		Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs = null;
		Collection<SurchargeProrationDetailsVO> surchargeProrationDetailsVOs = null;
		if (LIST.equals(maintainCCAForm.getSurChargeAction())) {
			if(maintainCCASession.getSurchargeCCAdetailsVOs()==null || maintainCCASession.getSurchargeCCAdetailsVOs().size()==0){
						
			Collection<String> codes = new ArrayList<String>();
			codes.add(BASE_CURRENCY);
			Map<String, String> results = new HashMap<String, String>();
			try {
				results = new SharedDefaultsDelegate()
						.findSystemParameterByCodes(codes);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			String baseCurrency = results.get(BASE_CURRENCY);
			MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();
			maintainCCAFilterVO = maintainCCASession.getMaintainCCAFilterVO();
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			if (maintainCCAFilterVO != null) {
				maintainCCAFilterVO.setBaseCurrency(baseCurrency);
				if (maintainCCAFilterVO.getCcaReferenceNumber() != null) {
					try {
						surchargeCCAdetailsVOs = mailTrackingMRADelegate
								.getSurchargeCCADetails(maintainCCAFilterVO);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
				} else {

					try {
						surchargeProrationDetailsVOs = mailTrackingMRADelegate
								.viewSurchargeProrationDetailsForMCA(populateProrationFilterVO(maintainCCAFilterVO));
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					surchargeCCAdetailsVOs = convertToSurchargeCCAdetailsVOs(
							surchargeProrationDetailsVOs, maintainCCAForm);
					if((surchargeProrationDetailsVOs==null||surchargeProrationDetailsVOs.size()==0)&&maintainCCAFilterVO.isApprovedMCAExists()){
						ArrayList<String> allCCARefNumbers=(ArrayList<String>)maintainCCASession.getCCARefNumbers();
						if(allCCARefNumbers!=null&&allCCARefNumbers.size()>0){
						maintainCCAFilterVO.setCcaReferenceNumber(allCCARefNumbers.get(allCCARefNumbers.size()-1));
							try{
							surchargeCCAdetailsVOs=mailTrackingMRADelegate.
									getSurchargeCCADetails(maintainCCAFilterVO);
							}catch (BusinessDelegateException businessDelegateException) {
								errors = handleDelegateException(businessDelegateException);
							}
						}
						maintainCCAFilterVO.setCcaReferenceNumber(null);
					}
				}
				surchargeCCAdetailsVOs=updateSurChgAmt(maintainCCASession,surchargeCCAdetailsVOs);
				maintainCCASession
						.setSurchargeCCAdetailsVOs((ArrayList<SurchargeCCAdetailsVO>) surchargeCCAdetailsVOs);


			}
			}
			invocationContext.target = LIST_SUCCESS;
		} else if (UPDATE.equals(maintainCCAForm.getSurChargeAction())) {
			errors=updateSurchargeCCAdetailsVOs(maintainCCAForm, maintainCCASession, surchargeCCAdetailsVOs);
			if(errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
			maintainCCAForm.setSurChargeAction("close");
			invocationContext.target = LIST_SUCCESS;
		}else if("Close".equals(maintainCCAForm.getSurChargeAction())){
			maintainCCAForm.setSurChargeAction("close");
			invocationContext.target = LIST_SUCCESS;
		}

		log.exiting(CLASS_NAME, "execute");

	}

	/**
	 * 
	 * @author A-5255
	 * @param maintainCCAFilterVO
	 * @return
	 */
	private ProrationFilterVO populateProrationFilterVO(
			MaintainCCAFilterVO maintainCCAFilterVO) {
		ProrationFilterVO prorationFilterVO = new ProrationFilterVO();
		prorationFilterVO.setCompanyCode(maintainCCAFilterVO.getCompanyCode());
		prorationFilterVO.setConsigneeDocumentNumber(maintainCCAFilterVO
				.getConsignmentDocNum());
		prorationFilterVO.setConsigneeSequenceNumber(maintainCCAFilterVO
				.getConsignmentSeqNum());
		prorationFilterVO.setPoaCode(maintainCCAFilterVO.getPOACode());
		prorationFilterVO
				.setBillingBasis(maintainCCAFilterVO.getBillingBasis());
		prorationFilterVO
				.setSerialNumber(maintainCCAFilterVO.getBlgDtlSeqNum());
		prorationFilterVO.setBaseCurrency(maintainCCAFilterVO.getBaseCurrency());
		return prorationFilterVO;
	}

	/**
	 * 
	 * @author A-5255
	 * @param surchargeProrationDetailsVOs
	 * @param maintainCCAForm
	 */
	private Collection<SurchargeCCAdetailsVO> convertToSurchargeCCAdetailsVOs(
			Collection<SurchargeProrationDetailsVO> surchargeProrationDetailsVOs,
			MRAMaintainCCAForm maintainCCAForm) {
		Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs = new ArrayList<SurchargeCCAdetailsVO>();
		SurchargeCCAdetailsVO surchargeCCAdetailsVO = null;
		Money revSurCharge = null;
		for (SurchargeProrationDetailsVO surchargeProrationDetailsVO : surchargeProrationDetailsVOs) {
			surchargeCCAdetailsVO = new SurchargeCCAdetailsVO();
			surchargeCCAdetailsVO.setCompanyCode(surchargeProrationDetailsVO
					.getCompanyCode());
			surchargeCCAdetailsVO.setBillingBasis(surchargeProrationDetailsVO
					.getBillingBasis());

			surchargeCCAdetailsVO
					.setCsgSequenceNumber(surchargeProrationDetailsVO
							.getCsgSeqNumber());
			surchargeCCAdetailsVO
					.setCsgDocumentNumber(surchargeProrationDetailsVO
							.getCsgDocumentNumber());
			surchargeCCAdetailsVO.setPoaCode(surchargeProrationDetailsVO
					.getPoaCode());
			surchargeCCAdetailsVO.setChargeHeadName(surchargeProrationDetailsVO
					.getChargeHead());
			try {
				revSurCharge = CurrencyHelper.getMoney(maintainCCAForm
						.getRevCurCode());
			} catch (CurrencyException e) {
				log.log(Log.FINE, e.getMessage());
			}
			revSurCharge.setAmount(surchargeProrationDetailsVO
					.getProrationValue().getAmount());
			surchargeCCAdetailsVO.setRevSurCharge(revSurCharge);
			surchargeCCAdetailsVO.setOrgSurCharge(surchargeProrationDetailsVO
					.getProrationValue());
			
			
			if(maintainCCAForm.getRevGrossWeight()!=null){
				
				surchargeCCAdetailsVO.setSurchareOrgRate(surchargeProrationDetailsVO.getSurchargeRate());
				surchargeCCAdetailsVO.setSurchargeRevRate(surchargeProrationDetailsVO.getSurchargeRevisedRate());
				revSurCharge.setAmount(surchargeProrationDetailsVO.getSurchargeRevisedRate()*Double.parseDouble(maintainCCAForm.getRevGrossWeight()));
				surchargeCCAdetailsVO.setRevSurCharge(revSurCharge);
				
				
			}
			
			
			
			//Added by A-7540
			if(maintainCCAForm.getSurchargeRevRate()!=null) //Added by A-8464 for ICRD-277867
			{
				surchargeCCAdetailsVO.setSurchargeRevRate(Double
					.parseDouble(maintainCCAForm.getSurchargeRevRate().toString()));
			}
			if(maintainCCAForm.getSurchareOrgRate()!=null)//Added by A-8464 for ICRD-277867
			{
				surchargeCCAdetailsVO.setSurchareOrgRate(Double
					.parseDouble(maintainCCAForm.getSurchareOrgRate().toString()));
			}
			surchargeCCAdetailsVOs.add(surchargeCCAdetailsVO);
		}
		return surchargeCCAdetailsVOs;
	}
	/**
	 * 
	 * @author A-5255
	 * @param maintainCCAForm
	 * @param maintainCCASession
	 * @param surchargeCCAdetailsVOs
	 * @return
	 */
	private Collection<ErrorVO> updateSurchargeCCAdetailsVOs(
			MRAMaintainCCAForm maintainCCAForm,
			MaintainCCASession maintainCCASession,
			Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs) {
		SurchargeCCAdetailsVO surchargeCCAdetailsVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String[] chargeHeadNames = maintainCCAForm.getChargeHeadName();
		String[] revSurCharges = maintainCCAForm.getRevSurCharge();
		String[] orgSurCharges = maintainCCAForm.getOrgSurCharge();
		String[] surchargeOpFlags = maintainCCAForm.getSurchargeOpFlag();
		String[] revRates = maintainCCAForm.getSurchargeRevRate();
		String[] orgRates = maintainCCAForm.getSurchareOrgRate();
		Set<String> chargeHeadSet = new HashSet<String>();
		int i = 0;
		Money revSurCharge = null;
		Money orgSurCharge = null;
		surchargeCCAdetailsVOs = new ArrayList<SurchargeCCAdetailsVO>();
		for (String surchargeOpFlag : surchargeOpFlags) {
			try {
				if (!EMPTYSTRING.equals(chargeHeadNames[i])
						&& !SurchargeCCAdetailsVO.OPERATION_FLAG_DELETE
								.equals(surchargeOpFlag) &&!"NOOP"
								.equals(surchargeOpFlag)) {
					if (!chargeHeadSet.add(chargeHeadNames[i])) {
						errors.add(new ErrorVO(ERROR_DUPLICATECHARGEHEAD));
						// invocationContext.target = LIST_FAILURE;
						return errors;
					}
					
					revSurCharge = CurrencyHelper.getMoney(maintainCCAForm
							.getRevCurCode());

					orgSurCharge = CurrencyHelper.getMoney(maintainCCAForm
							.getRevCurCode());
					if (!EMPTYSTRING.equals(revSurCharges[i])){
						revSurCharge.setAmount(Double.parseDouble(revSurCharges[i].replace(",", "")));//added by A-8353 for ICRD-286238
				    }
					else{
						revSurCharge.setAmount(0);
					}
					if (!EMPTYSTRING.equals(orgSurCharges[i])) {
						orgSurCharge.setAmount(Double
								.parseDouble(orgSurCharges[i].replace(",", "")));
					} else {
						orgSurCharge.setAmount(0);
					}
					if(!"listbillingentries".equals(maintainCCASession.getFromScreen())){
					if (revSurCharge.getAmount() == 0
							&& orgSurCharge.getAmount() == 0) {
						errors.add(new ErrorVO(
								ERROR_REVISED_ORIGINAL_CHARGEZERO));
						// invocationContext.target = LIST_FAILURE;
						//return errors;   
					}
					}
					surchargeCCAdetailsVO = new SurchargeCCAdetailsVO();
					surchargeCCAdetailsVO.setChargeHeadName(chargeHeadNames[i]);
					surchargeCCAdetailsVO.setRevSurCharge(revSurCharge);
					surchargeCCAdetailsVO.setOrgSurCharge(orgSurCharge);
					//Added by A-7540
					if (!EMPTYSTRING.equals(revRates[i])) {
					surchargeCCAdetailsVO.setSurchargeRevRate(Double.parseDouble(revRates[i].replace(",", "")));  //added by A-8353 for ICRD-286238 
					}
					if (!EMPTYSTRING.equals(orgRates[i])) {
						surchargeCCAdetailsVO.setSurchareOrgRate(Double.parseDouble(orgRates[i].replace(",", "")));  
						}
						
					
					
					surchargeCCAdetailsVOs.add(surchargeCCAdetailsVO);
				}
				i++;
			} catch (CurrencyException e) {
				log.log(Log.FINE, e.getMessage());
			}
		}
		if (surchargeCCAdetailsVOs != null && surchargeCCAdetailsVOs.size() > 0) {
			maintainCCASession
					.setSurchargeCCAdetailsVOs((ArrayList<SurchargeCCAdetailsVO>) surchargeCCAdetailsVOs);
		}
		return errors;
	}
	/**
	 * Updating the reverese surcharge amount if GPA changed(Reversed Mail charge is negative)
	 * @param maintainCCASession
	 * @param surchargeCCAdetailsVOs
	 * @return
	 */
	private Collection<SurchargeCCAdetailsVO> updateSurChgAmt(
			MaintainCCASession maintainCCASession,
			Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs){
		if(maintainCCASession.getCCAdetailsVO()!=null){
			if(maintainCCASession.getCCAdetailsVO().getRevChgGrossWeight()!=null){
				if(maintainCCASession.getCCAdetailsVO().getRevChgGrossWeight().getAmount()<0){
		for(SurchargeCCAdetailsVO surchargeCCAdetailsVO:surchargeCCAdetailsVOs){
			if(surchargeCCAdetailsVO.getRevSurCharge()!=null&&surchargeCCAdetailsVO.getOrgSurCharge()!=null){
			surchargeCCAdetailsVO.getRevSurCharge().setAmount(-surchargeCCAdetailsVO.getOrgSurCharge().getAmount());
			//Added by A-7540
			surchargeCCAdetailsVO.setSurchargeRevRate(surchargeCCAdetailsVO.getSurchargeRevRate());
			surchargeCCAdetailsVO.setSurchareOrgRate(surchargeCCAdetailsVO.getSurchareOrgRate());
				  }
				}
			  }
			}
			

        }
		return surchargeCCAdetailsVOs;
	}
}
