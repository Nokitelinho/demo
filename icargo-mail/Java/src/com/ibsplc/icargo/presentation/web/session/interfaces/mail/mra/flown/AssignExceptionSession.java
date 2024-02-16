/*
 * AssignExceptionSession.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2401
 *
 */
public interface AssignExceptionSession extends ScreenSession{
	/**
	 * @param flightVO FlownMailFilterVO
	 */
	public void setFilterDetails(FlownMailFilterVO flownMailFilterVO);

	/**
	 * @return FlownMailFilterVO
	 */
	public FlownMailFilterVO getFilterDetails();
	/**
	 * @param key
	 */
	public void removeFilterDetails(String key);
	
	/**
	 * @param flownAwbVOs Collection<AWBExceptionVO>
	 */
	public void setExceptions(ArrayList<FlownMailExceptionVO> flownAwbVOs);
	/**
	 * @return Collection<AWBExceptionVO>
	 */
	public ArrayList<FlownMailExceptionVO> getExceptions();
	/**
	 * @param key
	 */
	public void removeExceptions(String key);
	
	/**
	 * @return Returns the oneTimeValues.
	 */
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);

}
