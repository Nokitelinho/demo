package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.ux;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailBagHistorySession;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.impl.mail.operations.ux.MailBagHistorySessionImpl.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	12-Sep-2018		:	Draft
 */
public class MailBagHistorySessionImpl extends AbstractScreenSession implements
			MailBagHistorySession {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailbaghistory";
	
	private static final String MAILBAG_HISTORY_VOS = "mailBagHistoryVOs";
	private static final String MAILBAG_REMARKS_VOS = "mailBagHistoryRemarksVOs";
	private static final String MAILBAG_ID = "mailBagId";
	private static final String ONETIME_STATUS = "oneTimeStatus";
	private static final String ONETIME_SERVICELEVEL = "oneTimeServiceLevel";
	private static final String ONETIME_BILLINGSTATUS = "oneTimeBillingStatus";
	
	private static final String ENQUIRYFLAG="enquiryFlag"; 
	
	private static final String MAIL_SEQ_NUMBER = "mailSequenceNumber";
	private static final String MAILBAGVOS = "mailbagVos";
	
	public String getEnquiryFlag() { 
		return getAttribute(ENQUIRYFLAG);
	}

	public void setEnquiryFlag(String enquiryFlag) {
		setAttribute(ENQUIRYFLAG,enquiryFlag);
	}
	
	public  ArrayList<Long> getMailSequenceNumber() {  
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
	public void setMailHistoryRemarksVOs(Collection<MailHistoryRemarksVO> mailHistoryRemarksVOs) {
		setAttribute(MAILBAG_REMARKS_VOS,(ArrayList<MailHistoryRemarksVO>)mailHistoryRemarksVOs);
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
	public void setMailBagVos(ArrayList<MailbagVO> mailBagVos) {
		setAttribute(MAILBAGVOS,mailBagVos);
		
	}
	
	public ArrayList<MailbagVO> getMailBagVos() {
		return (ArrayList<MailbagVO>)getAttribute(MAILBAGVOS);
	}
	public void setOneTimeServiceLevel(Collection<OneTimeVO> oneTimeServicelevel) {
		setAttribute(ONETIME_SERVICELEVEL,(ArrayList<OneTimeVO>)oneTimeServicelevel);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_STATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeServiceLevel() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_SERVICELEVEL);
	}
	
	@Override 
	public void setOneTimeBillingStatus(Collection<OneTimeVO> oneTimeBillingStatus) {
		setAttribute(ONETIME_BILLINGSTATUS,(ArrayList<OneTimeVO>)oneTimeBillingStatus);
	}

	@Override
	public Collection<OneTimeVO> getOneTimeBillingStatus() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_BILLINGSTATUS);
	}
	@Override
	public Collection<MailHistoryRemarksVO> getMailHistoryRemarksVOs() {
		return (Collection<MailHistoryRemarksVO>)getAttribute(MAILBAG_REMARKS_VOS);
	}


}
