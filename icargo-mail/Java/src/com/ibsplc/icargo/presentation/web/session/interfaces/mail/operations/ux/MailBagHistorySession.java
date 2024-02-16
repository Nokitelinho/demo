package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailBagHistorySession.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	12-Sep-2018		:	Draft
 */
public interface MailBagHistorySession extends ScreenSession {
	
	Collection<MailbagHistoryVO> getMailBagHistoryVOs();
	
	 void setMailBagHistoryVOs(Collection<MailbagHistoryVO> mailBagHistoryVOs);
	 
	 String getMailBagId(); 
	    
	 String getEnquiryFlag(); 
	    
	 void setEnquiryFlag(String enquiryFlag);
	    
	 ArrayList<Long> getMailSequenceNumber();
	    
	 void setMailSequenceNumber(ArrayList<Long> mailSequenceNumber);
	 
	 void setMailBagId(String mailBagId);
	 
	 Collection<OneTimeVO> getOneTimeStatus();
	 
	 void setOneTimeStatus(Collection<OneTimeVO> oneTimeStatus);
	 
	 void setMailBagVos(ArrayList<MailbagVO> mailBagVos);
	 
	 ArrayList<MailbagVO> getMailBagVos();

     Collection<OneTimeVO> getOneTimeServiceLevel();
	 void setOneTimeServiceLevel(Collection<OneTimeVO> oneTimeServiceLevel);
	 
	 void setOneTimeBillingStatus(Collection<OneTimeVO> oneTimeBillingStatus);	 
	 Collection<OneTimeVO> getOneTimeBillingStatus();
	 void setMailHistoryRemarksVOs(Collection<MailHistoryRemarksVO> mailHistoryRemarksVOs);
	 Collection<MailHistoryRemarksVO> getMailHistoryRemarksVOs();
	 
}
