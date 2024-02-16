/*
 * PostalAdministrationSessionImpl.java Created on June 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PostalAdministrationSession;

/**
 * @author A-2047
 *
 */
public class PostalAdministrationSessionImpl extends AbstractScreenSession
		implements PostalAdministrationSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.masters.postaladministration";
	
	private static final String PA_VO = "paVO";
	private static final String ONETIME_CATAGORY = "oneTimeCategory";
	/*
	 * KEY for oneTimeMap in session
	 */
	private static final String KEY_ONETIMEMAP="oneTimeMap";
	
	private static final String KEY_PADETAILSMAP="postalAdministartionDetails";
	
	
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
     * @return PostalAdministrationVO
     */
	public PostalAdministrationVO getPaVO() {
		return getAttribute(PA_VO);
	}
	
	/**
     * @param paVO
     */
	public void setPaVO(PostalAdministrationVO paVO) {
		setAttribute(PA_VO,paVO);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCategory - Collection<OneTimeVO>
     */
	public void setOneTimeCategory(Collection<OneTimeVO> oneTimeCategory) {
		setAttribute(ONETIME_CATAGORY,(ArrayList<OneTimeVO>)oneTimeCategory);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_CATAGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCategory() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_CATAGORY);
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
	public HashMap<String,Collection<PostalAdministrationDetailsVO>> getPostalAdministrationDetailsVOs(){
		return (HashMap<String,Collection<PostalAdministrationDetailsVO>>) getAttribute(KEY_PADETAILSMAP);
	}
	public void setPostalAdministrationDetailsVOs(HashMap<String,Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs){
		setAttribute(KEY_PADETAILSMAP,(HashMap<String,Collection<PostalAdministrationDetailsVO>>)postalAdministrationDetailsVOs);
	}
	public void removePostalAdministrationDetailsVOs(){
		removeAttribute(KEY_PADETAILSMAP);
		
	}
	

}
