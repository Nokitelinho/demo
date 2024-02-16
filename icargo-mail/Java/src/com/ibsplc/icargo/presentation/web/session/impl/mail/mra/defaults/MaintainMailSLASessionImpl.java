/*
 * MaintainMailSLASessionImpl.java Created on Mar 30, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLAVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailSLASession;

/**
 * @author a-2524
 *
 */
public class MaintainMailSLASessionImpl extends AbstractScreenSession 
	implements MaintainMailSLASession {
	
	private static final String SCREENID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String MODULE = "mailtracking.mra.defaults";
	
	/*
	 * KEY for mailSLAVo in session
	 */
	private static final String KEY_MAILSLAVO="mailslavo";
	
	/*
	 * KEY for mailsladetailsvos in session
	 */
	private static final String KEY_SLADETAILSVOS="sladetailsvos";
	
	/*
	 * KEY for oneTimeMap in session
	 */
	private static final String KEY_ONETIMEMAP="oneTimeMap";

	
	/**
	 * returns screenId
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * returns Modulename
	 */	
	public String getModuleName() {
		return MODULE;
	}

	
	/**
	 * Methods for setting mailSLAVo
	 * @param mailSLAVo
	 */
	public void setMailSLAVo(MailSLAVO mailSLAVo) {
		setAttribute(KEY_MAILSLAVO, mailSLAVo);

	}

	/**
	 * Methods for getting MailSLAVO
	 * @return MailSLAVO
	 */
	public MailSLAVO getMailSLAVo() {
		return getAttribute(KEY_MAILSLAVO);
	}

	/**
     * This method removes the MailSLAVO in session
     */
	public void removeMailSLAVo() {
		removeAttribute(KEY_MAILSLAVO);

	}
	/**
	 * Methods for getting <String,Collection<OneTimeVO>>
	 * @return oneTimeStatus 
	 */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues(){
		return (HashMap<String,Collection<OneTimeVO>>) getAttribute(KEY_ONETIMEMAP);
	}
	
	/**
	 * Methods for setting HashMap<String,Collection<OneTimeVO>>
	 * @param oneTimeValues
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues){
		setAttribute(KEY_ONETIMEMAP,(HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
	}
	
	/**
	 * Methods for removeing HashMap<String,Collection<OneTimeVO>>
	 */
	public void removeOneTimeValues(){
		removeAttribute(KEY_ONETIMEMAP);
	}
	
	/**
	 * The setter method for mailSLADetailsVOs
	 * @param mailSLADetailsVOs
	 */
	public void setMailSLADetailsVOs(Collection<MailSLADetailsVO> mailSLADetailsVOs){
		setAttribute(KEY_SLADETAILSVOS,(ArrayList<MailSLADetailsVO>)mailSLADetailsVOs);
	}
    /**
     * The getter method for MailSLADetailsVO
     * @return MailSLADetailsVO
     */
    public Collection<MailSLADetailsVO> getMailSLADetailsVOs(){
    	return (Collection<MailSLADetailsVO>) getAttribute(KEY_SLADETAILSVOS);
    }
    
    /**
     * method to remove MailSLADetailsVO from session
     */
    public void removeMailSLADetailsVOs(){
    	removeAttribute(KEY_SLADETAILSVOS);
    }

}
