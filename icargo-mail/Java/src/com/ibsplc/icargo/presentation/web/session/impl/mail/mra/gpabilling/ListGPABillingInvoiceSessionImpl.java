/*
 * ListGPABillingInvoiceSessionImpl.java Created on June 9, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3434
 *
 */
public class ListGPABillingInvoiceSessionImpl extends AbstractScreenSession implements
ListGPABillingInvoiceSession {
	
	/**
	 * KEY for MODULE_NAME
	 */
	public static final String KEY_MODULE_NAME = "mailtracking.mra.gpabilling";

	/**
	 * KEY for SCREEN_ID in session
	 */
	public static final String KEY_SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";

	/**
	 * @author a-3434 Key for getting one time values 
	 * 
	 */

	private static final String KEY_ONETIME_CODES_MAP = "onetimecodesmap";
	/**
	 * KEY for setting invoiceStatus in session
	 */
	public static final String KEY_INVOICESTATUS = "invoiceStatus ";

	/**
	 * KEY for setting invoiceStatus Keys in session
	 */
	public static final String KEY_INVOICESTATUSKEYS = "invoiceStatusKeys";
	/**
	 * KEY for setting reportConfigVOs in session
	 */
	public static final String KEY_INVOICESTATUSVOS = "reportConfigVOs";
	private static final String KEY_CN51DETAILS = "cn51detailsvos";

	private static final String KEY_CN51FILTER = "cn51filtervo";
	/**
	 *
	 */
	private static final String  TOTALRECORDS = "totalRecords";
	private static final String KEY_SYSPARAMETERS="systemParameterByCodes";//Added for ICRD-189966
	public  ListGPABillingInvoiceSessionImpl() {
		super();

	}
	/**
	 * This method returns the MODULE name for the ListGPABillingInvoice screen
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}

	/**
	 * This method returns the SCREEN ID for the ListGPABillingInvoice screen
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}


	public HashMap<String, Collection<OneTimeVO>> getOneTimeMap() {
		return (HashMap<String, Collection<OneTimeVO>>) getAttribute(KEY_ONETIME_CODES_MAP);
	}

	/**
	 * Method to set the Onetimes map to session
	 * 
	 * @param oneTimeMap
	 * The one time map to be set to session
	 */
	public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap) {
		setAttribute(KEY_ONETIME_CODES_MAP,
				(HashMap<String, Collection<OneTimeVO>>) oneTimeMap);
	}

	/**
	 * Method to remove One Time Map from session
	 */
	public void removeOneTimeMap() {
		removeAttribute(KEY_ONETIME_CODES_MAP);

	}
	/**
	 * @return invoicestatus
	 */
	public Collection<OneTimeVO> getInvoiceStatus() {
		return (Collection<OneTimeVO>) getAttribute(KEY_INVOICESTATUS);
	}

	/**
	 * @param invoicestatus
	 */
	public void setInvoiceStatus(Collection<OneTimeVO> invoiceStatus) {
		setAttribute(KEY_INVOICESTATUS, (ArrayList<OneTimeVO>) invoiceStatus);
	}

	/**
	 * @return invoicestatusKeys
	 */
	public Collection<String> getInvoiceStatusKeys() {
		return (Collection<String>) getAttribute(KEY_INVOICESTATUSKEYS);
	}

	/**
	 * @param invoicestatusKeys
	 */
	public void setInvoiceStatusKeys(Collection<String> invoiceStatusKeys) {
		setAttribute(KEY_INVOICESTATUSKEYS, (ArrayList<String>)invoiceStatusKeys);
	}

	/**
	 * @return InvoiceStatusVOs
	 */
	public HashMap<String, String> getInvoiceStatusVOs() {
		return (HashMap<String, String>) getAttribute(KEY_INVOICESTATUSVOS);
	}

	/**
	 * @param InvoiceStatusVOs
	 */
	public void setInvoiceStatusVOs(HashMap<String, String> invoiceStatusVOs) {
		setAttribute(KEY_INVOICESTATUSVOS,
				(HashMap<String, String>) invoiceStatusVOs);
	}
	

	

	/**
	 *  @return Page<CN51SummaryVO>
	 */
	public Page<CN51SummaryVO> getCN51SummaryVOs() {

		return (Page<CN51SummaryVO>) getAttribute( KEY_CN51DETAILS );
	}

	/**
	 * sets cn51SummaryVOs
	 * 
	 * @param cn51SummaryVOs
	 */
	public void setCN51SummaryVOs(Page<CN51SummaryVO> cn51SummaryVOs) {

		setAttribute( KEY_CN51DETAILS, (Page<CN51SummaryVO>) cn51SummaryVOs );

	}

	/**
	 *
	 * removes CN51SummaryVOs
	 */
	public void removeCN51SummaryVOs() {

		removeAttribute( KEY_CN51DETAILS );
	}
	/**
	 * sets cn51SummaryFilterVO
	 * 
	 * @param cn51SummaryFilterVO
	 */
	public void setCN51SummaryFilterVO(CN51SummaryFilterVO cn51SummaryFilterVO){

		setAttribute(KEY_CN51FILTER,cn51SummaryFilterVO);
    }

	/**
	 *  returns cn51SummaryFilterVO
	 *  @return cn51SummaryFilterVO
	 */
    public CN51SummaryFilterVO getCN51SummaryFilterVO(){

    	return getAttribute(KEY_CN51FILTER);
    }

    /**
	 *
	 * removes CN51SummaryFilterVO
	 */
    public void removeCN51SummaryFilterVO(){

    	removeAttribute(KEY_CN51FILTER);
    }
    /**
	 * @return Returns the totalRecords.
	 */
	public Integer getTotalRecords() {
		return getAttribute(TOTALRECORDS);
	}
	
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTALRECORDS,totalRecords);
	}
	
	/**
     * This method removes the totalRecords in session
     */
	public void removeTotalRecords() {
	 	removeAttribute(TOTALRECORDS);
	}
	
	public void setFromScreen(String fromScreen) {
		// TODO Auto-generated method stub
		setAttribute("fromScreen",fromScreen);
		
	}
	@Override
	public String getFromScreen() {
		// TODO Auto-generated method stub
		return getAttribute("fromScreen");
	}
	/**
	 * Set system parameters from session
	 */
	public HashMap<String, String> getSystemparametres()
	{
		return getAttribute(KEY_SYSPARAMETERS);
	}
	/**
	 * Get system parameters from session
	 */
	public void setSystemparametres(HashMap<String, String> sysparameters)
	{
		setAttribute(KEY_SYSPARAMETERS, sysparameters);
	}
	/**
	 * Remove system parameters from session
	 */
     public void removeSystemparametres() {

     removeAttribute(KEY_SYSPARAMETERS);
     }

}