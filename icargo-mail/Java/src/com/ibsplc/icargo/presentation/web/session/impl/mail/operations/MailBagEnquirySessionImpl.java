/*
 * MailBagEnquirySessionImpl.java
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1861
 *
 */
public class MailBagEnquirySessionImpl extends AbstractScreenSession
        implements MailBagEnquirySession {

	private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";
	private static final String MODULE_NAME = "mail.operations";

	private static final String ONETIME_CONTAINERTYPES = "onetime_containertypes";
	private static final String ONETIME_OPERATIONTYPES = "onetime_operationtypes";
	private static final String ONETIME_CURRENTSTATUS = "current_status";
	private static final String ONETIME_MAILCATEGORY = "mailcategory";
	private static final String MAILBAGVOS = "mailbagvos"; 
	private static final String MAILBAGENQUIRYFILTERVO = "mailbagenquiryfiltervo";
	private static final String KEY_TOTAL_RECORDS = "totalRecords";
	
	
	/**
     * This method is used to set MailbagEnquiryFilterVO to the session
     * @param mailbagEnquiryFilterVO - MailbagEnquiryFilterVO
     */
	public void setMailbagEnquiryFilterVO(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) {
		setAttribute(MAILBAGENQUIRYFILTERVO,mailbagEnquiryFilterVO);
	}
	/**
     * This method returns the onetime vos
     * @return MAILBAGENQUIRYFILTERVO - MailbagEnquiryFilterVO
     */
	public MailbagEnquiryFilterVO getMailbagEnquiryFilterVO() {
		return getAttribute(MAILBAGENQUIRYFILTERVO);
	}
	
		
	/**
     * This method is used to set onetime values to the session
     * @param containerTypes - Collection<OneTimeVO>
     */
	public void setContainerTypes(Collection<OneTimeVO> containerTypes) {
		setAttribute(ONETIME_CONTAINERTYPES,(ArrayList<OneTimeVO>)containerTypes);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_CONTAINERTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getContainerTypes() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_CONTAINERTYPES);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param operationTypes - Collection<OneTimeVO>
     */
	public void setOperationTypes(Collection<OneTimeVO> operationTypes) {
		setAttribute(ONETIME_OPERATIONTYPES,(ArrayList<OneTimeVO>)operationTypes);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_OPERATIONTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOperationTypes() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_OPERATIONTYPES);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param currentStatus - Collection<OneTimeVO>
     */
	public void setCurrentStatus(Collection<OneTimeVO> currentStatus) {
		setAttribute(ONETIME_CURRENTSTATUS,(ArrayList<OneTimeVO>)currentStatus);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_CURRENTSTATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getCurrentStatus() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_CURRENTSTATUS);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory) {
		setAttribute(ONETIME_MAILCATEGORY,(ArrayList<OneTimeVO>)mailCategory);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAILCATEGORY);
	}
	
	/**
	 * This method is used to set MailbagVOs to the session
	 * @param mailbagvos - Page<MailbagVO>
	 */
	public void setMailbagVOs(Page<MailbagVO> mailbagvos) {
		setAttribute(MAILBAGVOS,(Page<MailbagVO>)mailbagvos);
	}

	/**
	 * This method returns the MailbagVOs
	 * @return MAILBAGVOS - Page<MailbagVO>
	 */
	public Page<MailbagVO> getMailbagVOs() {
		return (Page<MailbagVO>)getAttribute(MAILBAGVOS);
	}

    /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	/* added by A-5216
	 * to enable last link and total record count
	 * for Jira Id:  ICRD-21098 and ScreenId MTK009
	 * This method is used to set and get the total records values in session
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTAL_RECORDS, Integer.valueOf(totalRecords));
	}

	public Integer getTotalRecords() {
		return (Integer) getAttribute(KEY_TOTAL_RECORDS);
	}

}
