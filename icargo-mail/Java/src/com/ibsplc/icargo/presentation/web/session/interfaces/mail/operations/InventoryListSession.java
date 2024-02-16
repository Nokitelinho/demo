/*
 * InventoryListSession.java Created on Jan 17,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.InventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.InventorySummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1862
 *
 */
public interface InventoryListSession extends ScreenSession {

	
	
	/**
	 * This method is used to set InventoryListVO to the session
	 * @param inventoryListVO
	 */
	public void setInventoryListVO(InventoryListVO inventoryListVO);

	/**
	 * This method returns the InventoryListVO
	 * @return InventoryListVO
	 */
	public InventoryListVO getInventoryListVO();
	
	/**
	 * This method is used to set InventorySummaryVO to the session
	 * @param inventorySummaryVO
	 */
	public void setInventorySummaryVO(InventorySummaryVO inventorySummaryVO);

	/**
	 * This method returns the InventorySummaryVO
	 * @return InventorySummaryVO
	 */
	public InventorySummaryVO getInventorySummaryVO();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeCat - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat();
	
	/**
	 * This method is used to set carrier to the session
	 * @param carrier
	 */
	public void setCarrier(String carrier);

	/**
	 * This method returns the carrier
	 * @return carrier
	 */
	public String getCarrier();
	
}

