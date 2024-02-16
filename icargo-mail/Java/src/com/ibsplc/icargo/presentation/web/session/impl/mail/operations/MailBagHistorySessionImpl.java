/*
 * MailBagHistorySessionImpl.java Created on June 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagHistorySession;

/**
 * @author A-2047
 *
 */
public class MailBagHistorySessionImpl extends AbstractScreenSession implements
		MailBagHistorySession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailbaghistory";
	
	private static final String MAILBAG_HISTORY_VOS = "mailBagHistoryVOs";
	private static final String MAILBAG_ID = "mailBagId";
	private static final String ONETIME_STATUS = "oneTimeStatus";
	private static final String MAILBAGVOS = "mailbagVos";
	
	
	private static final String ENQUIRYFLAG="enquiryFlag"; 
	
	private static final String MAIL_SEQ_NUMBER = "mailSequenceNumber";//Added by A-8149 for ICRD-248207
	
	
	public String getEnquiryFlag() {  //Added by A-8164 for ICRD-260365
		return getAttribute(ENQUIRYFLAG);
	}

	public void setEnquiryFlag(String enquiryFlag) {
		setAttribute(ENQUIRYFLAG,enquiryFlag);
	}
	
	public  ArrayList<Long> getMailSequenceNumber() {  //Added by A-8149 for ICRD-248207
		return getAttribute(MAIL_SEQ_NUMBER);
	}

	public void setMailSequenceNumber(ArrayList<Long> mailSequenceNumber) {
		setAttribute(MAIL_SEQ_NUMBER,mailSequenceNumber);
	}
	

	/**
	 * @return Screen Id
	 */
	public String getScreenID() {
		
		return SCREEN_ID;
	}

	/**
	 * @return Module Name
	 */
	public String getModuleName() {
		
		return MODULE_NAME;
	}
	
	/**
     * @return Collection<MailbagHistoryVO>
     */
	public Collection<MailbagHistoryVO> getMailBagHistoryVOs() {
		return (Collection<MailbagHistoryVO>)getAttribute(MAILBAG_HISTORY_VOS);
	}
	
	/**
     * @param mailBagHistoryVOs
     */
	public void setMailBagHistoryVOs(Collection<MailbagHistoryVO> mailBagHistoryVOs) {
		setAttribute(MAILBAG_HISTORY_VOS,(ArrayList<MailbagHistoryVO>)mailBagHistoryVOs);
	}
	
	 /**
     * @return String
     */
	public String getMailBagId(){
		return getAttribute(MAILBAG_ID);
    }
    
    /**
     * @param mailBagId
     */
	public void setMailBagId(String mailBagId){
		setAttribute(MAILBAG_ID,mailBagId);
    }
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeStatus - Collection<OneTimeVO>
     */
	public void setOneTimeStatus(Collection<OneTimeVO> oneTimeStatus) {
		setAttribute(ONETIME_STATUS,(ArrayList<OneTimeVO>)oneTimeStatus);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_STATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeStatus() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_STATUS);
	}
	
	@Override
	public void setMailBagVos(ArrayList<MailbagVO> mailBagVos) {//Added by A-8164
		setAttribute(MAILBAGVOS,mailBagVos);
		
	}
	
	public ArrayList<MailbagVO> getMailBagVos() {
		return (ArrayList<MailbagVO>)getAttribute(MAILBAGVOS);
	}

}
