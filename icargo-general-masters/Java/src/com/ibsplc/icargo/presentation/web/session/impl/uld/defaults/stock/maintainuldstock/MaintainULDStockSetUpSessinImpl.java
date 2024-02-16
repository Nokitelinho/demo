/*
 * MaintainULDStockSetUpSessinImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.stock.maintainuldstock;


import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.MaintainULDStockSetUpSession;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author A-1496
 *
 */
public class MaintainULDStockSetUpSessinImpl extends AbstractScreenSession
		implements MaintainULDStockSetUpSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID ="uld.defaults.maintainuldstocks";
	
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";

	private static final String KEY_MAINTAINSTOCKDETAILS="MaintainULDStockDetails";

/**
 * @return
 */
		public String getScreenID(){

			return SCREENID;

		}
		/**
		 * @return
		 */
		public String getModuleName(){

			return MODULE;

		}
		/**
		 * @return
		 */
		public Collection<ULDStockConfigVO> getMaintainULDStockDetails() {
			return (Collection<ULDStockConfigVO>) getAttribute(KEY_MAINTAINSTOCKDETAILS);

		}
/**
 * @param uldDetails
 */

		public void setMaintainULDStockDetails(Collection<ULDStockConfigVO> uldDetails) {
			setAttribute(KEY_MAINTAINSTOCKDETAILS, (ArrayList<ULDStockConfigVO>) uldDetails);
	}
		/**
		 * @return Returns the oneTimeValues.
		 */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
			return getAttribute(KEY_ONETIMEVALUES);
		}

		/**
		 * @param oneTimeValues
		 *            The oneTimeValues to set.
		 */
		public void setOneTimeValues(
				HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
			setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
		}

}