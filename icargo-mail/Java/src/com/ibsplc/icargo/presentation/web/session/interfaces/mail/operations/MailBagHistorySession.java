/*
 * MailBagHistorySession.java Created on June 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2047
 *
 */
public interface MailBagHistorySession extends ScreenSession {
	 /**
     * @return Collection<MailbagHistoryVO>
     */
    Collection<MailbagHistoryVO> getMailBagHistoryVOs();
    
    /**
     * @param mailBagHistoryVOs
     */
    void setMailBagHistoryVOs(Collection<MailbagHistoryVO> mailBagHistoryVOs);
    
	 /**
     * @return String
     */
    String getMailBagId();
    
    String getEnquiryFlag(); //Added by A-8164 for ICRD-260365
    
    void setEnquiryFlag(String enquiryFlag);
    
    ArrayList<Long> getMailSequenceNumber();//Added by A-8149 for ICRD-248207
    
    void setMailSequenceNumber(ArrayList<Long> mailSequenceNumber);
    
    /**
     * @param mailBagId
     */
    void setMailBagId(String mailBagId);
    
    /**
     * @return Collection<OneTimeVO>
     */
    Collection<OneTimeVO> getOneTimeStatus();
    
    /**
     * @param oneTimeCategory
     */
    void setOneTimeStatus(Collection<OneTimeVO> oneTimeStatus);
    
    void setMailBagVos(ArrayList<MailbagVO> mailBagVos);//Added by A-8164
	 
	ArrayList<MailbagVO> getMailBagVos();
}
