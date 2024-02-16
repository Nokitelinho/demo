/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.BillingSession.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteBankDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.BillingSession.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public interface BillingSiteSession extends ScreenSession{
	
	/**
	 * Sets the maintain billing site vo.
	 *
	 * @param billingSiteVO the new billing site vo
	 */
	public void setBillingSiteVO(BillingSiteVO billingSiteVO);
	
	/**
	 * Gets the maintain billing site vo.
	 *
	 * @return the maintain billing site vo
	 */
	public BillingSiteVO getBillingSiteVO();
	
	/**
	 * Removes the maintain billing site vo.
	 */
	public void removeBillingSiteVO();
	
	/**
	 * Gets the billing site gpa countires v os.
	 *
	 * @return the billing site gpa countires v os
	 */
	public ArrayList<BillingSiteGPACountriesVO> getBillingSiteGPACountiresVOs();
	
	/**
	 * Sets the billing site gpa countires v os.
	 *
	 * @param billingSiteGPACountiresVOs the new billing site gpa countires v os
	 */
	public void setBillingSiteGPACountiresVOs(ArrayList<BillingSiteGPACountriesVO> billingSiteGPACountiresVOs);
				
	
	/**
	 * Gets the billing site bank details v os.
	 *
	 * @return the billing site bank details v os
	 */
	public ArrayList<BillingSiteBankDetailsVO> getBillingSiteBankDetailsVOs();
	
	/**
	 * Sets the billing site bank details v os.
	 *
	 * @param billingSiteBankDetailsVOs the new billing site bank details v os
	 */
	public void setBillingSiteBankDetailsVOs(ArrayList<BillingSiteBankDetailsVO> billingSiteBankDetailsVOs);
	
	/**
	 * Removes the billing site gpa countires v os.
	 */
	public void removeBillingSiteGPACountiresVOs();
	
	/**
	 * Removes the billing site bank details v os.
	 */
	public void removeBillingSiteBankDetailsVOs();
	
	/**
	 * Gets the billing site back up vo.
	 *
	 * @return the billing site back up vo
	 */
	public BillingSiteVO getBillingSiteBackUpVO();
	
	/**
	 * Sets the billing site back up vo.
	 *
	 * @param billingSiteVO the new billing site back up vo
	 */
	public void setBillingSiteBackUpVO(BillingSiteVO billingSiteVO);
	
	//Added fro CR ICRD-76222 starts
	public void removeFromParentScreen();
	public String getFromParentScreen();
	public void setFromParentScreen(String fromParentScreen);
	//Added fro CR ICRD-76222 ends

}
