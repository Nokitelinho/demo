/*
 * ChangeBillingStatusSessionImpl.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ChangeBillingStatusSession;


/**
 * @author A-3434
 *
 */
public class ChangeBillingStatusSessionImpl extends AbstractScreenSession implements
ChangeBillingStatusSession {
	
	/**
	 * KEY for MODULE_NAME
	 */
	public static final String KEY_MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * KEY for SCREEN_ID in session
	 */
	public static final String KEY_SCREEN_ID = "mailtracking.mra.defaults.changestatus";

	/* KEY for setting billingStatus in session
	 */
	public static final String KEY_BILLINGSTATUS = "billingStatus";
	
	/* KEY for setting selectedrow in session
	 */
	private static final String KEY_SELECT_ROW="selectedrow";
	
	/* KEY for setting DocumentBillingDetailVOs in session
	 */
	private static final String KEY_GPABILLINGDETAILSCOL="docbillingdetailsvoscollection";
	/**
	 *
	 */
	public  ChangeBillingStatusSessionImpl() {
		super();

	}
	/**
	 * This method returns the MODULE name for the ChangeBillingStatus popup
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}

	/**
	 * This method returns the SCREEN ID for the ChangeBillingStatus popup
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}
 /**

    *

    * @return SelectedRows

    */
    public String getSelectedRow(){
    	return getAttribute(KEY_SELECT_ROW);
    }
    /**

    *

    * @param selectArray

    */
    public void setSelectedRow(String selectArray){
    	setAttribute(KEY_SELECT_ROW,selectArray);
    }
    /**

    *

    *remove SelectedRows

    */
    public void removeSelectedRow(){
    	removeAttribute(KEY_SELECT_ROW);
    }
   
	 /**
	 * Method to set the Onetimes,GpaBillingStatus  to session
	 * 
	 * @param oneTimeMap
	 * The one time map to be set to session
	 */
	public void setBillingStatus(HashMap<String, Collection<OneTimeVO>> billingStatus) {
		setAttribute(KEY_BILLINGSTATUS,
				(HashMap<String, Collection<OneTimeVO>>) billingStatus);
	}
	/**
	 * Method to get the Onetimes,GpaBillingStatus  to session
	 * 
	 * @param oneTimeMap
	 * 
	 */
	public HashMap<String, Collection<OneTimeVO>> getBillingStatus() {
		return (HashMap<String, Collection<OneTimeVO>>) getAttribute(KEY_BILLINGSTATUS);
	}
	 /**

    *

    * @return documentBillingDetailsVOs

    */
    public Collection<DocumentBillingDetailsVO> getDocumentBillingDetailvoCol(){
    	return (Collection<DocumentBillingDetailsVO>)getAttribute(KEY_GPABILLINGDETAILSCOL);
    }
    /**
     * @param documentBillingDetailsVOs

    */
    public void setDocumentBillingDetailvoCol(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs){
    	setAttribute(KEY_GPABILLINGDETAILSCOL,(ArrayList<DocumentBillingDetailsVO>)documentBillingDetailsVOs);
    }
    /*
    
    *remove documentBillingDetailsVOs

    */
    public void removeDocumentBillingDetailvoCol(){
    	removeAttribute(KEY_GPABILLINGDETAILSCOL);
    }
	
}