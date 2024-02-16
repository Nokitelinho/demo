/*
 * MailBagEnquirySession.java Created on June 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1861
 *
 */
public interface MailBagEnquirySession extends ScreenSession {
	
	

	/**
     * This method is used to set MailbagEnquiryFilterVO to the session
     * @param mailbagEnquiryFilterVO - MailbagEnquiryFilterVO
     */
	public void setMailbagEnquiryFilterVO(MailbagEnquiryFilterVO mailbagEnquiryFilterVO);
	/**
     * This method returns the onetime vos
     * @return MAILBAGENQUIRYFILTERVO - MailbagEnquiryFilterVO
     */
	public MailbagEnquiryFilterVO getMailbagEnquiryFilterVO() ;
	
	/**
     * This method is used to set onetime values to the session
     * @param containerTypes - Collection<OneTimeVO>
     */
	public void setContainerTypes(Collection<OneTimeVO> containerTypes);

	/**
     * This method returns the onetime vos
     * @return ONETIME_CONTAINERTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getContainerTypes();
	
	/**
     * This method is used to set onetime values to the session
     * @param currentStatus - Collection<OneTimeVO>
     */
	public void setCurrentStatus(Collection<OneTimeVO> currentStatus);

	/**
     * This method returns the onetime vos
     * @return ONETIME_CURRENTSTATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getCurrentStatus();
	
	/**
     * This method is used to set onetime values to the session
     * @param operationTypes - Collection<OneTimeVO>
     */
	public void setOperationTypes(Collection<OneTimeVO> operationTypes);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OPERATIONTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOperationTypes();
	
	/**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory);

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory();
	
	/**
	 * This method is used to set MailbagVOs to the session
	 * @param mailbagvos - Page<MailbagVO>
	 */
	public void setMailbagVOs(Page<MailbagVO> mailbagvos);

	/**
	 * This method returns the MailbagVOs
	 * @return MAILBAGVOS - Page<MailbagVO>
	 */
	public Page<MailbagVO> getMailbagVOs();
	
	/* added by A-5216
	 * to enable last link and total record count
	 * for Jira Id:  ICRD-21098 and ScreenId MTK009
	 */
	Integer getTotalRecords();
	
	void setTotalRecords(int totalRecords);
	
}

