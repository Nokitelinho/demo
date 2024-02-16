/*
 * FuelSurchargeSession.java Created on Apr 23,2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.FuelSurchargeVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * 
 * @author A-2391
 *
 */

	public interface FuelSurchargeSession extends ScreenSession {
		/**
		 * @return
		 */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

		/**
		 * @param oneTimeVOs
		 */
		public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

		/**
		 * removes removeOneTimeVOs
		 */
		public void removeOneTimeVOs();

		/**
		 * 
		 * @return Collection<FuelSurchargeVO>
		 */
		public Collection<FuelSurchargeVO> getFuelSurchargeVOs();

		/**
		 * 
		 * @param fuelSurchargeVOs
		 */
		public void setFuelSurchargeVOs(Collection<FuelSurchargeVO> fuelSurchargeVOs);

		/**
		 * removes fuelSurchargeVOs
		 * 
		 */
		public void removeFuelSurchargeVOs();
		
	}

