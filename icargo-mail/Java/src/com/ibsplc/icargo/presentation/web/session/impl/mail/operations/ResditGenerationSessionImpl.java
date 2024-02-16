/*
 * ResditGenerationSessionImpl.java Created on Oct 06, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;



import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ResditGenerationSession;


/**
 * @author A-2122
 *
 */
public class ResditGenerationSessionImpl extends AbstractScreenSession
		implements ResditGenerationSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.resditgeneration";
	
	private static final String ONETIME_RESDITEVENTS = "resditEvents";
	private static final String ONETIME_OFFLOADREASONCODES = "offloadReasonCodes";
	private static final String ONETIME_RETURNREASONCODES = "returnReasonCodes";
	private static final String ONETIME_CATEGORY = "category";
	private static final String KEY_ONETIMEHNI = "oneTimeHNI";
	private static final String KEY_ONETIMERI = "oneTimeRI";
		
	private static final String MAILBAGVOS = "mailbagVOs";
	private static final String MAILBAGVO = "mailbagVO";

	
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
     * @param resditEvents - ArrayList<OneTimeVO>
     */
	public void setResditEvents(ArrayList<OneTimeVO> resditEvents) {
		setAttribute(ONETIME_RESDITEVENTS,(ArrayList<OneTimeVO>)resditEvents);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_OFFLOADREASONCODES - ArrayList<OneTimeVO>
     */
	public ArrayList<OneTimeVO> getResditEvents() {
		return (ArrayList<OneTimeVO>)getAttribute(ONETIME_RESDITEVENTS);
	}	
	
	/**
     * This method is used to set onetime values to the session
     * @param offloadReasonCodes - ArrayList<OneTimeVO>
     */
	public void setOffloadReasonCodes(ArrayList<OneTimeVO> offloadReasonCodes) {
		setAttribute(ONETIME_OFFLOADREASONCODES,(ArrayList<OneTimeVO>)offloadReasonCodes);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_OFFLOADREASONCODES - ArrayList<OneTimeVO>
     */
	public ArrayList<OneTimeVO> getOffloadReasonCodes() {
		return (ArrayList<OneTimeVO>)getAttribute(ONETIME_OFFLOADREASONCODES);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param returnReasonCodes - ArrayList<OneTimeVO>
     */
	public void setReturnReasonCodes(ArrayList<OneTimeVO> returnReasonCodes) {
		setAttribute(ONETIME_RETURNREASONCODES,(ArrayList<OneTimeVO>)returnReasonCodes);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_RETURNREASONCODES - ArrayList<OneTimeVO>
     */
	public ArrayList<OneTimeVO> getReturnReasonCodes() {
		return (ArrayList<OneTimeVO>)getAttribute(ONETIME_RETURNREASONCODES);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param category - ArrayList<OneTimeVO>
     */
	public void setCategory(ArrayList<OneTimeVO> category) {
		setAttribute(ONETIME_CATEGORY,(ArrayList<OneTimeVO>)category);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_CATEGORY - ArrayList<OneTimeVO>
     */
	public ArrayList<OneTimeVO> getCategory() {
		return (ArrayList<OneTimeVO>)getAttribute(ONETIME_CATEGORY);
	}
	
	
	
	/**
     * This method returns the MailbagVO
     * @return MAILBAGVOS - Collection<MailbagVO>
     */
	public Collection<MailbagVO> getMailbagVOs() {
		return (Collection<MailbagVO>)getAttribute(MAILBAGVOS);
	}
	
	/**
     * This method is used to set MailbagVOs to the session
     * @param mailbagVOs - Collection<MailbagVO>
     */
	public void setMailbagVOs(Collection<MailbagVO> mailbagVOs) {
		setAttribute(MAILBAGVOS,(ArrayList<MailbagVO>)mailbagVOs);
	}
	/**
     * @return MailbagVO
     */
	public MailbagVO getMailbagVO() {
		return getAttribute(MAILBAGVO);
	}
	
	/**
     * @param mailbagVO
     */
	public void setMailbagVO(MailbagVO mailbagVO) {
		setAttribute(MAILBAGVO,mailbagVO);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI) {
		setAttribute(KEY_ONETIMEHNI,(ArrayList<OneTimeVO>)oneTimeHNI);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEHNI);
	}
	/**
     * This method is used to set onetime values to the session
     * @param KEY_ONETIMERI - Collection<OneTimeVO>
     */
	public void setOneTimeRI(Collection<OneTimeVO> oneTimeRI) {
		setAttribute(KEY_ONETIMERI,(ArrayList<OneTimeVO>)oneTimeRI);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMERI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRI() {
		
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMERI);
}
	
	
	
	

}
