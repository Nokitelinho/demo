/*
 * ScreenloadSurchargeDetailsCommand.java Created on Jul 15, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.surcharge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.SurchargeBillingDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Jul 15, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jul 15, 2015 A-5255
 * First draft
 */

public class ScreenloadSurchargeDetailsCommand extends BaseCommand {

	/**
	 * 
	 * @author A-5255
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	private Log log = LogFactory.getLogger("MRA GPABILLING");
	private static final String CLASS_NAME = "ScreenloadSurchargeDetailsCommand";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.surcharge.surchargepopup";
	private static final String ACTION_SUCCESS = "success";
	private static final String BASE_CURRENCY = "shared.airline.basecurrency";
	private static final String SURCHARGECHARGEHEAD_ONETIM = "mailtracking.mra.surchargeChargeHead";
	private static final String EMPTY_STRING ="";
	private static final String OVERRIDEROUNDING = "mailtracking.mra.overrideroundingvalue";//Added for ICRD-189966

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execut e");

		SurChargeBillingDetailSession surchargeSession = (SurChargeBillingDetailSession) getScreenSession(
				MODULE_NAME, SCREENID);
		SurchargeBillingDetailsForm surchargeForm = (SurchargeBillingDetailsForm) invocationContext.screenModel;
		ErrorVO error = null;
		Collection<SurchargeBillingDetailVO> surchargeBillingDetailVOs = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes
				.getCompanyCode());
		Collection<OneTimeVO> chargeHeadOneTime = oneTimeValues
				.get(SURCHARGECHARGEHEAD_ONETIM);
		surchargeSession
				.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		double totalAmount = 0.0;
		/**
		 * setting cn51CN66FilterVO
		 */
		CN51CN66FilterVO cn51CN66FilterVO = surchargeSession
				.getGpaBillingFilterVO();
		/**
		 * Getting the base Currency
		 */
		Collection<String> codes = new ArrayList<String>();
		codes.add(BASE_CURRENCY);
		codes.add(OVERRIDEROUNDING);
		Map<String, String> results = new HashMap<String, String>();
		try {
			results = new SharedDefaultsDelegate()
					.findSystemParameterByCodes(codes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		String baseCurrency = results.get(BASE_CURRENCY);
		String overrideRounding = results.get(OVERRIDEROUNDING);

		if (cn51CN66FilterVO != null) {
			cn51CN66FilterVO.setBaseCurrency(baseCurrency);
			try {
				if(cn51CN66FilterVO.getInvoiceNumber()!=null){
				surchargeBillingDetailVOs = new MailTrackingMRADelegate()
						.findSurchargeBillingDetails(cn51CN66FilterVO);
				}else{
					surchargeBillingDetailVOs = new MailTrackingMRADelegate()
					.findSurchargeBillableDetails(cn51CN66FilterVO);
				}
				if (surchargeBillingDetailVOs != null&&surchargeBillingDetailVOs.size()>0) {

					for (SurchargeBillingDetailVO surchargeBillingDetailVO : surchargeBillingDetailVOs) {
						if (surchargeBillingDetailVO.getChargeAmt() != null) {
							totalAmount = totalAmount
									+ surchargeBillingDetailVO.getChargeAmt()
											.getAmount();
						}
						if (surchargeBillingDetailVO.getChargeHead() != null
								&& !EMPTY_STRING.equals(surchargeBillingDetailVO
										.getChargeHead())) {
							if (chargeHeadOneTime != null) {
								for (OneTimeVO oneTimeVO : chargeHeadOneTime) {
									if (oneTimeVO.getFieldValue().equals(
											surchargeBillingDetailVO
													.getChargeHead())) {
										surchargeBillingDetailVO
												.setChargeHeadDesc(oneTimeVO
														.getFieldDescription());
										break;
									}
								}
							}
						}
					}
					//Modified for ICRD-189961 starts
					if(!MailConstantsVO.FLAG_NO.equals(overrideRounding)){
						surchargeForm.setTotalAmount(Double.toString(totalAmount));
						surchargeForm.setOverrideRounding(MailConstantsVO.FLAG_YES);
					}else{
						double totalAmount_rounded= getScaledValue(totalAmount,2);
						surchargeForm.setTotalAmount(Double.toString(totalAmount_rounded));
						surchargeForm.setOverrideRounding(overrideRounding);
					}
					//Modified for ICRD-189961 ends
				}
				surchargeSession
						.setSurchargeBillingDetailVOs((ArrayList<SurchargeBillingDetailVO>) surchargeBillingDetailVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		invocationContext.target = ACTION_SUCCESS;
	}
	/**
  	 * 
  	 * @param value
  	 * @param precision
  	 * @return
  	 */
  	private double getScaledValue(double value, int precision) {

  		java.math.BigDecimal bigDecimal = new java.math.BigDecimal(value);
  		return bigDecimal.setScale(precision,
  				java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
  	}
   
	/**
	 * 
	 * @author A-5255
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(
			String companyCode) {
		log.entering(CLASS_NAME, "fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(SURCHARGECHARGEHEAD_ONETIM);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME, "fetchOneTimeDetails");
		return hashMap;
	}

}
