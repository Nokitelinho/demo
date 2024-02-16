/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling.ux.invoicesettlementmailbag.InvoiceSettlementMailbagSessionImpl.java
 *
 *	Created by	:	A-7531
 *	Created on	:	24-Apr-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling.ux;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling.ux.invoicesettlementmailbag.InvoiceSettlementMailbagSessionImpl.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	24-Apr-2018	:	Draft
 */
public class InvoiceSettlementMailbagSessionImpl extends AbstractScreenSession implements InvoiceSettlementMailbagSession{

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";

	private static final String KEY_ONETIME_VOS="oneTimeVOs";
	
	private static final String KEY_SYSTEM_PAR="systemparameters";
	
	private static final String KEY_INV_SETTLE_FILTERVO="invoiceSettlementFilterVO";
	
	private static final String KEY_SELECTED_GPA_SETTLE_VOS = "selectedGPASettlementVO";
	 private static final String KEY_SETTLEMENT_HISTOPRYVO = "invoiceSettlementHistoryVO";
	 private static final String KEY_SETTLEMENT_TOTALRECORDS = "totalrecords";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
	 *	Added by 			: A-7531 on 24-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	
	
	public InvoiceSettlementMailbagSessionImpl() {
		super();

	}
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MODULE_NAME;
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
	 *	Added by 			: A-7531 on 24-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public String getScreenID() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

		return getAttribute(KEY_ONETIME_VOS);

	}
	/**

	 *

	 * @param oneTimeVOs

	 */

	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

		setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

	}
	/**

	 *

	 *remove onetimes

	 */

	public void removeOneTimeVOs() {

		removeAttribute(KEY_ONETIME_VOS);

	}
	public HashMap<String, String>getSystemparametres(){

		return getAttribute(KEY_SYSTEM_PAR);

	}
	/**

	 *

	 * @param systemparameters

	 */

	public void setSystemparametres(HashMap<String, String> systemparameters){

		setAttribute(KEY_SYSTEM_PAR, systemparameters);

	}
	public void setInvoiceSettlementFilterVO(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO) {
		setAttribute(KEY_INV_SETTLE_FILTERVO,invoiceSettlementFilterVO);
		
	}
	
	public InvoiceSettlementFilterVO getInvoiceSettlementFilterVO() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_INV_SETTLE_FILTERVO);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession#getSelectedGPASettlementVO()
	 *	Added by 			: A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public Collection<GPASettlementVO> getSelectedGPASettlementVO() {
		// TODO Auto-generated method stub
		return (Collection<GPASettlementVO>)getAttribute(KEY_SELECTED_GPA_SETTLE_VOS);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession#setSelectedGPASettlementVO(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO)
	 *	Added by 			: A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param gpaSettlementVO 
	 */
	@Override
	public void setSelectedGPASettlementVO(Collection<GPASettlementVO> selectedGPASettlementVO) {
		setAttribute(KEY_SELECTED_GPA_SETTLE_VOS,(ArrayList<GPASettlementVO>) selectedGPASettlementVO);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession#setGPASettlementVO(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO)
	 *	Added by 			: A-7531 on 11-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param gpaSettlementVO 
	 */
	@Override
	public void setInvoiceSettlementHistoryVO(Collection<InvoiceSettlementHistoryVO> invoiceSettlementHistoryVO) {
		setAttribute(KEY_SETTLEMENT_HISTOPRYVO,(ArrayList<InvoiceSettlementHistoryVO>) invoiceSettlementHistoryVO);
		
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession#getGPASettlementVO()
	 *	Added by 			: A-7531 on 11-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public Collection<InvoiceSettlementHistoryVO> getInvoiceSettlementHistoryVO() {
		// TODO Auto-generated method stub
		return (Collection<InvoiceSettlementHistoryVO>)getAttribute(KEY_SETTLEMENT_HISTOPRYVO);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession#getTotalRecords()
	 *	Added by 			: A-7531 on 18-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	public Integer getTotalRecords() {
		
		return getAttribute(KEY_SETTLEMENT_TOTALRECORDS);
	}
	
	
	public void setTotalRecords(int totalRecords) {
		
		setAttribute(KEY_SETTLEMENT_TOTALRECORDS, totalRecords);
	}
	@Override
	public Page<GPASettlementVO> getGPASettlementVO() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setGPASettlementVO(Page<GPASettlementVO> gpaSettlementVO) {
		// TODO Auto-generated method stub
		
	}  

}
