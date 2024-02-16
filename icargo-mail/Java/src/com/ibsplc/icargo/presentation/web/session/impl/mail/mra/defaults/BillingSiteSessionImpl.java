/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults.MaintainBillingSiteSessionImpl.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteBankDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.BillingSiteSession;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults.MaintainBillingSiteSessionImpl.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public class BillingSiteSessionImpl extends AbstractScreenSession implements BillingSiteSession{
	
	/** The Constant MODULE_NAME. */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/** The Constant SCREENID. */
	private static final String SCREENID = "mailtracking.mra.defaults.billingsitemaster";
	
	/** The Constant KEY_BILLINGSITE_VO. */
	private static final String KEY_BILLINGSITE_VO="billingsitevos";
	
	/** The Constant KEY_BILLINGSITEBANKDETAILS_VO. */
	private static final String KEY_BILLINGSITEBANKDETAILS_VO="billingsitebankdetailsvos";
	
	/** The Constant KEY_BILLINGSITECOUNTRY_VO. */
	private static final String KEY_BILLINGSITECOUNTRY_VO="billingsitecountriesvos";
	
	/** The Constant KEY_BILLINGSITEBACKUP_VO. */
	private static final String KEY_BILLINGSITEBACKUP_VO="billingsitebackupvos";
	
	/** The Constant KEY_FROM_PARENT_SCREEN. */
	private static final String KEY_FROM_PARENT_SCREEN="fromParentScreen";
	
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingSiteSession#getMaintainBillingSiteVO()
	 * Added by 			: A-5219 on 15-Nov-2013
	 * Used for 	:
	 * Parameters	:	@return
	 *
	 * @return the billing site vo
	 */
	public BillingSiteVO getBillingSiteVO(){
		return getAttribute(KEY_BILLINGSITE_VO);
	}
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingSiteSession#setMaintainBillingSiteVO(com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainBillingSiteVO)
	 * Added by 			: A-5219 on 15-Nov-2013
	 * Used for 	:
	 * Parameters	:	@param maintainBillingSiteVO
	 *
	 * @param billingSiteVO the new billing site vo
	 */
	public void setBillingSiteVO(BillingSiteVO billingSiteVO){
		setAttribute(KEY_BILLINGSITE_VO,billingSiteVO);
	}
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingSiteSession#removeMaintainBillingSiteVO()
	 *	Added by 			: A-5219 on 15-Nov-2013
	 * 	Used for 	:
	 *	Parameters	:	 
	 */
	public void removeBillingSiteVO(){
		removeAttribute(KEY_BILLINGSITE_VO);
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
	 * Added by 			: A-5219 on 15-Nov-2013
	 * Used for 	:
	 * Parameters	:	@return
	 *
	 * @return the module name
	 */
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MODULE_NAME;
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
	 * Added by 			: A-5219 on 15-Nov-2013
	 * Used for 	:
	 * Parameters	:	@return
	 *
	 * @return the screen id
	 */
	@Override
	public String getScreenID() {
		// TODO Auto-generated method stub
		return SCREENID;
	}
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.BillingSiteSession#getBillingSiteGPACountiresVOs()
	 * Added by 			: A-5219 on 18-Nov-2013
	 * Used for 	:
	 * Parameters	:	@return
	 *
	 * @return the billing site gpa countires v os
	 */
	public ArrayList<BillingSiteGPACountriesVO> getBillingSiteGPACountiresVOs(){
		return getAttribute(KEY_BILLINGSITECOUNTRY_VO);
	}
	
	/**
	 * Sets the billing site gpa countires v os.
	 *
	 * @param billingSiteGPACountiresVOs the new billing site gpa countires v os
	 */
	public void setBillingSiteGPACountiresVOs(ArrayList<BillingSiteGPACountriesVO> billingSiteGPACountiresVOs){
		setAttribute(KEY_BILLINGSITECOUNTRY_VO,billingSiteGPACountiresVOs);
	}
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.BillingSiteSession#getBillingSiteBankDetailsVOs()
	 * Added by 			: A-5219 on 18-Nov-2013
	 * Used for 	:
	 * Parameters	:	@return
	 *
	 * @return the billing site bank details v os
	 */
	public ArrayList<BillingSiteBankDetailsVO> getBillingSiteBankDetailsVOs(){
		return getAttribute(KEY_BILLINGSITEBANKDETAILS_VO);
	}
	
	/**
	 * Sets the billing site bank details v os.
	 *
	 * @param billingSiteBankDetailsVOs the new billing site bank details v os
	 */
	public void setBillingSiteBankDetailsVOs(ArrayList<BillingSiteBankDetailsVO> billingSiteBankDetailsVOs){
		setAttribute(KEY_BILLINGSITEBANKDETAILS_VO,billingSiteBankDetailsVOs);
	}
	
	/**
	 * Removes the billing site bank details v os.
	 */
	public void removeBillingSiteBankDetailsVOs(){
		removeAttribute(KEY_BILLINGSITEBANKDETAILS_VO);
	}
	
	/**
	 * Removes the billing site gpa countires v os.
	 */
	public void removeBillingSiteGPACountiresVOs(){
		removeAttribute(KEY_BILLINGSITECOUNTRY_VO);
	}

	/**
	 * Gets the billing site back up vo.
	 *
	 * @return the billing site back up vo
	 */
	public BillingSiteVO getBillingSiteBackUpVO(){
		return getAttribute(KEY_BILLINGSITEBACKUP_VO);
	}
	
	/**
	 * Sets the billing site back up vo.
	 *
	 * @param billingSiteVO the new billing site back up vo
	 */
	public void setBillingSiteBackUpVO(BillingSiteVO billingSiteVO){
		setAttribute(KEY_BILLINGSITEBACKUP_VO,billingSiteVO);
	}

	//Added fro CR ICRD-76222 starts
	/**
	 * 
	 *Removes the fromParentScreen
	 */
	public void removeFromParentScreen(){
		removeAttribute(KEY_FROM_PARENT_SCREEN);
	}
	/**
	 * Gets the fromParentScreen
	 *
	 * @return the fromParentScreen
	 */
	public String getFromParentScreen(){
		return getAttribute(KEY_FROM_PARENT_SCREEN);
	}
	/**
	 * Sets the fromParentScreen
	 *
	 * @param fromParentScreen
	 */
	public void setFromParentScreen(String fromParentScreen){
		setAttribute(KEY_FROM_PARENT_SCREEN,fromParentScreen);
	}
	//Added fro CR ICRD-76222 ends
	
}
