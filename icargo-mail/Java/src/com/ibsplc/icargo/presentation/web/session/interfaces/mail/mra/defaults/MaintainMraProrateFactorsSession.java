/*
 * MaintainMraProrateFactorsSession.java Created on Mar 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 *  @author A-2105
 */

public interface MaintainMraProrateFactorsSession extends ScreenSession {

	/**
	 * @return Returns the oneTimeValues.
	 */
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	/**
	 * @param prorationFactorFilterVO 
	 */
	public void setFilterDetails(ProrationFactorFilterVO prorationFactorFilterVO);

	/**
	 * @return ExceptionFilterVO
	 */
	public ProrationFactorFilterVO getFilterDetails();

	/**
	 * @param key
	 */
	public void removeFilterDetails();
	/**
	 * Methods for getting Collection<ProrationFactorVO>
	 * @return Collection<ProrationFactorVO> 
	 */
	public ArrayList<ProrationFactorVO> getFactors();
	
	/**
	 * Methods for setting Collection<ProrationFactorVO>
	 * @param prorationFactorVOs
	 */
	public void setFactors(ArrayList<ProrationFactorVO> prorationFactorVOs);
	
	/**
	 * Methods for removing Page<ProrationFactorVO>
	 */
	public void removeFactors();
}
