/*
 * ViewSurchargeProrationCommand.java Created on Jul 7, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewproration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeProrationDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Jul 7, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jul 7, 2015 A-5255
 * First draft
 */

public class ViewSurchargeProrationCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA GPABILLING");
	private static final String CLASS_NAME = "ViewSurchargeProrationCommand";
	private static final String LIST_SUCCESS = "open_surcharge";
	private static final String LIST_FAILURE = "failure";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String EMPTY_STRING = "";
	private static final String SCREENID_VIEW_PRORATION = "mailtracking.mra.defaults.viewproration";
	private static final String SURCHARGECHARGEHEAD_ONETIM = "mailtracking.mra.surchargeChargeHead";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		MRAViewProrationSession session = getScreenSession(MODULE_NAME,
				SCREENID_VIEW_PRORATION);
		MRAViewProrationForm form = (MRAViewProrationForm) invocationContext.screenModel;
		Collection<SurchargeProrationDetailsVO> surchargeProrationDetailsVOs = null;
		SurchargeProrationDetailsVO surchargeProrationDetailsVO=null;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes
				.getCompanyCode());
		Collection<OneTimeVO> chargeHeadOneTime = oneTimeValues
				.get(SURCHARGECHARGEHEAD_ONETIM);
		if (("List").equals(form.getFromAction())) {
			ProrationFilterVO prorationFilterVO = session
					.getProrationFilterVO();
			if (prorationFilterVO != null) {
				try {
					if (form.getSector() != null
							&& !EMPTY_STRING.equals(form.getSector())) {
						prorationFilterVO.setSerialNumber(Integer.parseInt(form
								.getSector()));
						surchargeProrationDetailsVOs = new MailTrackingMRADelegate()
								.viewSurchargeProrationDetails(prorationFilterVO);
						if (surchargeProrationDetailsVOs != null && surchargeProrationDetailsVOs.size()>0) {
							session.setSurchargeProrationVOs((ArrayList<SurchargeProrationDetailsVO>) surchargeProrationDetailsVOs);
							
							for (SurchargeProrationDetailsVO vo : surchargeProrationDetailsVOs) {
								if (vo.getChargeHead() != null
										&& !EMPTY_STRING.equals(vo
												.getChargeHead())) {
									if (chargeHeadOneTime != null) {
										for (OneTimeVO oneTimeVO : chargeHeadOneTime) {
											if (oneTimeVO.getFieldValue().equals(
													vo.getChargeHead())) {
												vo.setChargeHead(oneTimeVO
																.getFieldDescription());
												break;
											}
										}
									}
								}
							}
							surchargeProrationDetailsVO=surchargeProrationDetailsVOs.iterator().next();
							if(surchargeProrationDetailsVO.getTotalProrationAmtInUsd()!=null){
								form.setTotalSurchgInUsd(surchargeProrationDetailsVO.getTotalProrationAmtInUsd().getAmount()+"");
							}
							if(surchargeProrationDetailsVO.getTotalProrationAmtInSdr()!=null){
								form.setTotalSurchgInSdr(surchargeProrationDetailsVO.getTotalProrationAmtInSdr().getAmount()+"");
							}
							if(surchargeProrationDetailsVO.getTotalProrationAmtInBaseCurr()!=null){
								form.setTotalSurchgInBas(surchargeProrationDetailsVO.getTotalProrationAmtInBaseCurr().getAmount()+"");
							}
							if(surchargeProrationDetailsVO.getTotalProrationValue()!=null){
								form.setTotalSurchgInCur(surchargeProrationDetailsVO.getTotalProrationValue().getAmount()+"");
							}
											
							
						}else{
							session.setSurchargeProrationVOs(null);
						}
						
					}
				} catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}
			}
		}else{
			session.setSurchargeProrationVOs(null);
			
		}
		invocationContext.target = LIST_SUCCESS;
	}
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
