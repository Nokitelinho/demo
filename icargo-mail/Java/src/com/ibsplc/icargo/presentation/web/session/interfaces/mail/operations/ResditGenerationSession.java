/*
 * ResditGenerationSession.java Created on Oct 06, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


/**
 * @author A-2122
 *
 */
public interface ResditGenerationSession extends ScreenSession {
	
	
	
	/**
     * This method is used to set onetime values to the session
     * @param resditEvents - ArrayList<OneTimeVO>
     */
	public void setResditEvents(ArrayList<OneTimeVO> resditEvents);
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_RESDITEVENTS - ArrayList<OneTimeVO>
     */
	public ArrayList<OneTimeVO> getResditEvents();
	
	
	/**
     * This method is used to set onetime values to the session
     * @param offloadReasonCodes - ArrayList<OneTimeVO>
     */
	public void setOffloadReasonCodes(ArrayList<OneTimeVO> offloadReasonCodes);
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_OFFLOADREASONCODES - ArrayList<OneTimeVO>
     */
	public ArrayList<OneTimeVO> getOffloadReasonCodes();
	
	/**
     * This method is used to set onetime values to the session
     * @param returnReasonCodes - ArrayList<OneTimeVO>
     */
	public void setReturnReasonCodes(ArrayList<OneTimeVO> returnReasonCodes);
	/**
     * This method returns the onetime vos
     * @return ONETIME_RETURNREASONCODES - ArrayList<OneTimeVO>
     */
	
	public ArrayList<OneTimeVO> getReturnReasonCodes();
	
	/**
     * This method is used to set onetime values to the session
     * @param category - ArrayList<OneTimeVO>
     */
	public void setCategory(ArrayList<OneTimeVO> category);
	/**
     * This method returns the onetime vos
     * @return ONETIME_CATEGORY - ArrayList<OneTimeVO>
     */
	
	public ArrayList<OneTimeVO> getCategory();

	/**
     * This method returns the mailbagVOS
     * @return MAILBAGVOS - Collection<MailbagVO>
     */
	public Collection<MailbagVO> getMailbagVOs();
	
	/**
     * This method is used to set MailbagVOs to the session
     * @param mailbagVOs - Collection<MailbagVO>
     */
	public void setMailbagVOs(Collection<MailbagVO> mailbagVOs);
	/**
	 * 
	 * @return MailbagVO
     */
	public MailbagVO getMailbagVO();
	
    /**
     * @param mailbagVO
     */
	public void setMailbagVO(MailbagVO mailbagVO);
    
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeRI(Collection<OneTimeVO> oneTimeRI);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeRI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRI();
       
    
}
