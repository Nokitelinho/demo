/*
 * DespatchEnquirySessionImpl.java Created on Feb 12, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailFlownVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnquirySession;

/**
 * @author A-3295
 * 
 */
public class DespatchEnquirySessionImpl extends AbstractScreenSession implements
		DespatchEnquirySession {

	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/*
	 * The screen id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.despatchenquiry";

	private static final String KEY_ONETIME_VOS = "onetimevalues";
	private static final String KEY_DESPATCHVO_VOS = "despatchVOs";
	private static final String KEY_MAILFLOWN_VOS = "mailflownVOs";
	//private static final String KEY_MAILGPA_VOS = "gpaBillingDetails";
	

	/**
	 * @return
	 */
	public String getScreenID() {

		return SCREEN_ID;
	}

	/**
	 * @return MODULE_NAME
	 */
	public String getModuleName() {
		return MODULE_NAME;
	}

	/**
	 * @return
	 */

	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {
		return getAttribute(KEY_ONETIME_VOS);
	}

	/**
	 * @param oneTimeVOs
	 */

	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VOS, oneTimeVOs);
	}

	/**
	 * remove onetimes
	 */

	public void removeOneTimeVOs() {
		removeAttribute(KEY_ONETIME_VOS);
	}

	/**
	 * @return DespatchVO
	 */
	public DespatchVO getDespatchDetails() {
		return getAttribute(KEY_DESPATCHVO_VOS);
	}

	/**
	 * remove DespatchVOs
	 */

	public void removeDespatchDetails() {
		removeAttribute(KEY_DESPATCHVO_VOS);
	}
	/**
	 * remove onetimes
	 */
	public void setDespatchDetails(DespatchVO despatchVO) {
		setAttribute(KEY_DESPATCHVO_VOS, despatchVO);		
	}
	
	/** 
	 * @return  Collection<MailFlownVO>
	 */ 
	public Collection<MailFlownVO> getMailFlownDetails() {
		return (Collection<MailFlownVO>)getAttribute(KEY_MAILFLOWN_VOS);
		
	}
	/**
	 * remove mailFlownVOs
	 */
	public void removeMailFlownDetails() {
		removeAttribute(KEY_MAILFLOWN_VOS);
	}
	/**
	 * @param mailFlownVOs
	 */

	public void setMailFlownDetails(Collection<MailFlownVO> mailFlownVOs) {
		setAttribute(KEY_MAILFLOWN_VOS,(ArrayList<MailFlownVO>)mailFlownVOs );		
	}
	/** 
	 * @return  Collection<MailGPABillingVO>
	 *//* 
	public Collection<MailGPABillingVO> getMailGPABillingDetails() {
		return (Collection<MailGPABillingVO>)getAttribute(KEY_MAILGPA_VOS);
		
	}
	*//**
	 * remove gpaBillingDetails
	 *//*
	public void removeMailGPABillingDetails() {
		removeAttribute(KEY_MAILGPA_VOS);
	}
	*//**
	 * @param gpaBillingDetails
	 *//*

	public void setMailGPABillingDetails(Collection<MailGPABillingVO> gpaBillingDetails) {
		setAttribute(KEY_MAILGPA_VOS,(ArrayList<MailGPABillingVO>)gpaBillingDetails );		
	}*/
}