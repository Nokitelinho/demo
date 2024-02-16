/*
 * InvoiceExceptionsSession.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1946
 * 
 */
public interface InvoiceExceptionsSession extends ScreenSession {

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

	/**
	 * Method to getExceptionInInvoiceVOs
	 * 
	 * @return Page<ExceptionInInvoiceVO>
	 */
	public Page<ExceptionInInvoiceVO> getExceptionInInvoiceVOs();

	/**
	 * Method to setExceptionInInvoiceVOs
	 * 
	 * @param exceptionInInvoiceVOs
	 */
	void setExceptionInInvoiceVOs(
			Page<ExceptionInInvoiceVO> exceptionInInvoiceVOs);

	/**
	 * remove
	 */
	public void removeExceptionInInvoiceVOs();

	/**
	 * @return
	 */

	public ExceptionInInvoiceFilterVO getExceptionInInvoiceFilterVO();

	/**
	 * 
	 * @param exceptionInInvoiceFilterVO
	 */
	public void setExceptionInInvoiceFilterVO(
			ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO);

	/**
	 * remove
	 */
	public void removeExceptionInInvoiceFilterVO();

	/**
	 * @return String[]
	 */
	public String[] getSelectedRows();

	/**
	 * @param str
	 */
	public void setSelectedRows(String[] str);

	/**
     * 
     */
	public void removeSelectedRows();

	/**
	 * Method to getTotalRecords
	 * 
	 * @return Integer
	 */
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

}
