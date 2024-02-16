/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.invoicesettlementmailbag.InvoiceSettlementMailbagSession.java
 *
 *	Created by	:	A-7531
 *	Created on	:	24-Apr-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.invoicesettlementmailbag.InvoiceSettlementMailbagSession.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	24-Apr-2018	:	Draft
 */
public interface InvoiceSettlementMailbagSession extends ScreenSession{
	
	
	
	
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();
	
	public HashMap<String, String> getSystemparametres();
	
	public void setSystemparametres(HashMap<String,String> systemparameters);
	
	 public void setInvoiceSettlementFilterVO(InvoiceSettlementFilterVO invoiceSettlementFilterVO);
	
	 public InvoiceSettlementFilterVO getInvoiceSettlementFilterVO();
	 
	 public Collection<GPASettlementVO> getSelectedGPASettlementVO();
	    public void setSelectedGPASettlementVO(Collection<GPASettlementVO> selectedGPASettlementVO);
	

		 public void setInvoiceSettlementHistoryVO(Collection<InvoiceSettlementHistoryVO> invoiceSettlementHistoryVO);
		
		 public Collection<InvoiceSettlementHistoryVO> getInvoiceSettlementHistoryVO();
		 
		    public Page<GPASettlementVO> getGPASettlementVO();
		    /**
		     * @param gpaSettlementVOs
		     */
		    public void setGPASettlementVO(Page<GPASettlementVO> gpaSettlementVO);
		    
		 
public Integer getTotalRecords();
 public void setTotalRecords(int totalRecords);
}
