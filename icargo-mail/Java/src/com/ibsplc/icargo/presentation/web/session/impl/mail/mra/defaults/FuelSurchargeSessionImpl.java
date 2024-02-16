/*
 * FuelSurchargeSessionImpl.java Created on Apr 23,2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

/**
 * @author A-2391
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.FuelSurchargeVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.FuelSurchargeSession;

/**
 * @author A-2391
 * 
 * 
 */
public class FuelSurchargeSessionImpl extends AbstractScreenSession implements
 FuelSurchargeSession {

	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/*
	 * The screen id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.fuelsurcharge";

	private static final String KEY_ONETIME_VOS = "onetimevalues";

	

	private static final String KEY_DETAILS = "fuelSurchargeVOs";
	
	/**
	 * @return
	 */
	public String getScreenID() {

		return SCREEN_ID;
	}

	/**
	 * @return MODULE_NAME
	 */
	public String getModuleName() {
		return MODULE_NAME;
	}

	/**
	 * constructor
	 * 
	 */
	public FuelSurchargeSessionImpl() {
		super();
	}

	/**
	 * @author A-2391 for getting one time into the session
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

		return getAttribute(KEY_ONETIME_VOS);

	}

	/**
	 * @param OneTimeVO
	 *            for setting one times
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {

		setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

	}

	/**
	 * for removing one times
	 */
	public void removeOneTimeVOs() {

		removeAttribute(KEY_ONETIME_VOS);

	}

	
	

	/**
	 * setting fuelSurchargeVOs 
	 * 
	 */
	public void setFuelSurchargeVOs(Collection<FuelSurchargeVO> fuelSurchargeVOs) {
		setAttribute(KEY_DETAILS, (ArrayList<FuelSurchargeVO>)fuelSurchargeVOs);

	}

	/**
	 * getting Collection<FuelSurchargeVO>
	 */
	public Collection<FuelSurchargeVO> getFuelSurchargeVOs() {

		return (ArrayList<FuelSurchargeVO>) getAttribute(KEY_DETAILS);
	}

	/**
	 * removes FuelSurchargeVOs
	 */
	public void removeFuelSurchargeVOs() {
		removeAttribute(KEY_DETAILS);

	}

	

}
