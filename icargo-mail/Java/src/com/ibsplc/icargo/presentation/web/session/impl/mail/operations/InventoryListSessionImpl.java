/*
 * InventoryListSessionImpl.java
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.InventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.InventorySummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.InventoryListSession;

/**
 * @author A-1862
 *
 */
public class InventoryListSessionImpl extends AbstractScreenSession
        implements InventoryListSession {

	private static final String SCREEN_ID = "mailtracking.defaults.inventorylist";
	private static final String MODULE_NAME = "mail.operations";

	
	private static final String LISTVOS = "listvos"; 
	private static final String SUMMARYVOS = "summaryvos"; 
	private static final String MAILFILTER = "mailfiltervo"; 
	private static final String KEY_ONETIMECAT = "oneTimeCat";
	private static final String KEY_CARRIER = "carrier";
		

	
	/**
	 * This method is used to set InventoryListVO to the session
	 * @param inventoryListVO
	 */
	public void setInventoryListVO(InventoryListVO inventoryListVO) {
		setAttribute(LISTVOS,(InventoryListVO)inventoryListVO);
	}

	/**
	 * This method returns the InventoryListVO
	 * @return InventoryListVO
	 */
	public InventoryListVO getInventoryListVO() {
		return (InventoryListVO)getAttribute(LISTVOS);
	}
	
	/**
	 * This method is used to set InventorySummaryVO to the session
	 * @param inventorySummaryVO
	 */
	public void setInventorySummaryVO(InventorySummaryVO inventorySummaryVO) {
		setAttribute(SUMMARYVOS,(InventorySummaryVO)inventorySummaryVO);
	}

	/**
	 * This method returns the InventorySummaryVO
	 * @return InventorySummaryVO
	 */
	public InventorySummaryVO getInventorySummaryVO() {
		return (InventorySummaryVO)getAttribute(SUMMARYVOS);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat) {
		setAttribute(KEY_ONETIMECAT,(ArrayList<OneTimeVO>)oneTimeCat);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMECAT - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMECAT);
	}
	
	
	/**
	 * This method is used to set Carrier to the session
	 * @param carrier
	 */
	public void setCarrier(String carrier) {
		setAttribute(KEY_CARRIER,carrier);
	}

	/**
	 * This method returns the Carrier
	 * @return Carrier
	 */
	public String getCarrier() {
		return getAttribute(KEY_CARRIER);
	}
	

    /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}


}
