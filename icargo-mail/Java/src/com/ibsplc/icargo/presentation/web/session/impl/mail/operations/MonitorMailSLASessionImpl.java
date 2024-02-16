/*
 * MonitorMailSLASessionImpl.java Created on Apr 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.MailActualDetailFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailActualDetailVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MonitorMailSLASession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2105
 *
 */
public class MonitorMailSLASessionImpl extends AbstractScreenSession
		implements MonitorMailSLASession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.monitormailsla";
	
	private static final String CATEGORY = "categories";
	private static final String ACTIVITY = "activities";
	private static final String STATUS = "status";
	private static final String FILTERVO = "filterVO";
	private static final String MAILSLADETAILS = "mailSlaDetails";
	
	
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
     * This method is used to set onetime values to the session
     * @param category - ArrayList<OneTimeVO>
     */
	public void setCategories(ArrayList<OneTimeVO> category){
		setAttribute(CATEGORY,category);		
	}
   
    /**
     * This method returns the onetime vos
     * @return ArrayList<OneTimeVO>
     */
	public ArrayList<OneTimeVO> getCategories() {
		return getAttribute(CATEGORY);
	}
    
    /**
     * This method is used to set onetime values to the session
     * @param activity - ArrayList<OneTimeVO>
     */
	public void setActivities(ArrayList<OneTimeVO> activity) {
		setAttribute(ACTIVITY, activity);	
	}
   
    /**
     * This method returns the onetime vos
     * @return ArrayList<OneTimeVO> 
     */
	public ArrayList<OneTimeVO> getActivities() {
		return getAttribute(ACTIVITY);
	}
	
	  /**
     * This method is used to set onetime values to the session
     * @param status - ArrayList<OneTimeVO>
     */
	public void setStatus(ArrayList<OneTimeVO> status) {
		setAttribute(STATUS, status);
	}
   
    /**
     * This method returns the onetime vos
     * @return ArrayList<OneTimeVO> 
     */
	public ArrayList<OneTimeVO> getStatus() {
		return getAttribute(STATUS);
	}
	
	 /**
     * This method is used to set filter values to the session
     * @param mailActualDetailFilterVO
     */
	public void setFilterVO(MailActualDetailFilterVO mailActualDetailFilterVO) {
		setAttribute(FILTERVO, mailActualDetailFilterVO);
	}
   
    /**
     * This method returns the filter vo
     * @return MailActualDetailFilterVO 
     */
	public MailActualDetailFilterVO getFilterVO() {
		return getAttribute(FILTERVO);
	}
	
	 /**
     * This method is used to set mailActualDetailVOs to the session
     * @param mailActualDetailVOs
     */
	public void setMailSlaDetails(Page<MailActualDetailVO> mailActualDetailVOs) {
		setAttribute(MAILSLADETAILS, mailActualDetailVOs);
	}
   
    /**
     * This method returns the Page<MailActualDetailVO>
     * @return Page<MailActualDetailVO>
     */
	public Page<MailActualDetailVO> getMailSlaDetails() {
		return getAttribute(MAILSLADETAILS);
	}

}
