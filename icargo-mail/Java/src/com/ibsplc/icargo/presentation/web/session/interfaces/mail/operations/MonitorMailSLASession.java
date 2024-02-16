/*
 * MonitorMailSLASession.java Created on Apr 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.MailActualDetailFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailActualDetailVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1876
 *
 */
public interface MonitorMailSLASession extends ScreenSession {

	    
    /**
     * This method is used to set onetime values to the session
     * @param category - ArrayList<OneTimeVO>
     */
	public void setCategories(ArrayList<OneTimeVO> category);
   
    /**
     * This method returns the onetime vos
     * @return ArrayList<OneTimeVO>
     */
	public ArrayList<OneTimeVO> getCategories();
    
    /**
     * This method is used to set onetime values to the session
     * @param activity - ArrayList<OneTimeVO>
     */
	public void setActivities(ArrayList<OneTimeVO> activity);
   
    /**
     * This method returns the onetime vos
     * @return ArrayList<OneTimeVO> 
     */
	public ArrayList<OneTimeVO> getActivities();
	
	  /**
     * This method is used to set onetime values to the session
     * @param activity - ArrayList<OneTimeVO>
     */
	public void setStatus(ArrayList<OneTimeVO> status);
   
    /**
     * This method returns the onetime vos
     * @return ArrayList<OneTimeVO> 
     */
	public ArrayList<OneTimeVO> getStatus();
	
	 /**
     * This method is used to set filter values to the session
     * @param mailActualDetailFilterVO
     */
	public void setFilterVO(MailActualDetailFilterVO mailActualDetailFilterVO);
   
    /**
     * This method returns the filter vo
     * @return MailActualDetailFilterVO 
     */
	public MailActualDetailFilterVO getFilterVO();
	
	 /**
     * This method is used to set mailActualDetailVOs to the session
     * @param mailActualDetailVOs
     */
	public void setMailSlaDetails(Page<MailActualDetailVO> mailActualDetailVOs);
   
    /**
     * This method returns the Page<MailActualDetailVO>
     * @return Page<MailActualDetailVO>
     */
	public Page<MailActualDetailVO> getMailSlaDetails();
	
	
}

