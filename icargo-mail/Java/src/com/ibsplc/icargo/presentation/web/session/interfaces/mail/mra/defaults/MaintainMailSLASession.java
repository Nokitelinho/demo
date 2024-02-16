/*
 * MaintainMailSLASession.java Created on Mar 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLAVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-2524
 *
 */
public interface MaintainMailSLASession extends ScreenSession {
	
	/**
	 * The setter method for mailSLAVo
	 * @param mailSLAVo
	 */
	public void setMailSLAVo(MailSLAVO mailSLAVo);
    /**
     * The getter method for mailSLAVo
     * @return mailSLAVo
     */
    public MailSLAVO getMailSLAVo();
    
    /**
     * method to remove MailSLAVO from session
     */
    public void removeMailSLAVo();
    
    /**
	 * Methods for getting <String,Collection<OneTimeVO>>
	 * @return oneTimeValues
	 */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues();
	
	/**
	 * Methods for setting HashMap<String,Collection<OneTimeVO>>
	 * @param oneTimeValues
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	/**
	 * Methods for removing HashMap<String,Collection<OneTimeVO>>
	 */
	public void removeOneTimeValues();
	

	/**
	 * The setter method for mailSLADetailsVOs
	 * @param mailSLADetailsVOs
	 */
	public void setMailSLADetailsVOs(Collection<MailSLADetailsVO> mailSLADetailsVOs);
	
    /**
     * The getter method for MailSLADetailsVO
     * @return MailSLADetailsVO
     */
    public Collection<MailSLADetailsVO> getMailSLADetailsVOs();
    
    /**
     * method to remove MailSLADetailsVO from session
     */
    public void removeMailSLADetailsVOs();


    
}
