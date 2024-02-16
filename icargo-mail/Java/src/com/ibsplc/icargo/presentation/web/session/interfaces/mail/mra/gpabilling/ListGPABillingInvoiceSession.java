/*
 * ListGPABillingInvoiceSession.java Created on June 9, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3434
 * 
 */
public interface ListGPABillingInvoiceSession extends ScreenSession {
	/**
	 * Method to get the onetime map in the session
	 * 
	 * @return HashMap the onetime map from session
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeMap();

	/**
	 * Method to set the Onetimes map to session
	 * 
	 * @param oneTimeMap
	 *            The one time map to be set to session
	 */
	public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap);

	/**
	 * Method to remove One Time Map from session
	 */
	public void removeOneTimeMap();

	/**
	 * @return invoiceStatus
	 */
	public Collection<OneTimeVO> getInvoiceStatus();

	/**
	 * @param invoiceStatus
	 */
	public void setInvoiceStatus(Collection<OneTimeVO> invoiceStatus);

	/**
	 * @return InvoiceStatusKeys
	 */
	public Collection<String> getInvoiceStatusKeys();

	/**
	 * @param InvoiceStatusKeys
	 */
	public void setInvoiceStatusKeys(Collection<String> invoiceStatusKeys);

	/**
	 * @return InvoiceStatusVOs
	 */
	public HashMap<String, String> getInvoiceStatusVOs();

	/**
	 * @param InvoiceStatusVOs
	 */
	public void setInvoiceStatusVOs(HashMap<String, String> invoiceStatusVOs);

	/**
	 * 
	 * @return
	 */
	public Collection<CN51SummaryVO> getCN51SummaryVOs();

	/**
	 * 
	 * @param cn51SummaryVOs
	 */
	void setCN51SummaryVOs(Page<CN51SummaryVO> cn51SummaryVOs);

	/**
	 * removes cn51SummaryVOs
	 * 
	 */
	public void removeCN51SummaryVOs();

	/**
	 * 
	 * @param cn51SummaryFilterVO
	 */
	public void setCN51SummaryFilterVO(CN51SummaryFilterVO cn51SummaryFilterVO);

	/**
	 * 
	 * @return cn51SummaryFilterVO
	 */
	public CN51SummaryFilterVO getCN51SummaryFilterVO();

	/**
	 * removes CN51SummaryFilterVO
	 * 
	 */
	public void removeCN51SummaryFilterVO();

	public Integer getTotalRecords();

	/**
	 * @param totalRecords
	 *            The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords);

	/**
	 * This method removes the totalRecords in session
	 */
	public void removeTotalRecords();
	
	/**
	 * 
	 * @param fromScreen
	 */
	public void setFromScreen(String fromScreen);
	/**
	 * 
	 * @return
	 */
	public String getFromScreen();
	
	/**
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> getSystemparametres();
	/**
	 * 
	 * @param sysparameters sysparameters
	 */
	public void setSystemparametres(HashMap<String, String> sysparameters);
	/**
	 * Remove system parameters from session
	 */
	public void removeSystemparametres();

}
