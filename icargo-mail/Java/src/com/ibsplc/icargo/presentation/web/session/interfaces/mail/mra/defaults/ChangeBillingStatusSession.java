/*
 * ChangeBillingStatusSession.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


/**
 * @author A-3434
 *
 */
public  interface ChangeBillingStatusSession extends ScreenSession{
	
	public String getSelectedRow();
	/**
	 * @param selectArray
	 */
	public void setSelectedRow(String selectArray);
	/**
	 * remove SelectedRows
	 */
	public void removeSelectedRow();
	
	 /**
	 *   Method to get the onetime map in the
	 *    session
	 * @return HashMap the onetime map from session
	 */
	public HashMap<String, Collection<OneTimeVO>> getBillingStatus();

	/**
	 *  Method to set the Onetimes map to session
	 * @param oneTimeMap
	 *  The one time map to be set to session
	 */
	public void setBillingStatus(HashMap<String, Collection<OneTimeVO>> billingStatus);
	/**
	 * @param documentBillingDetailsVOs
	 */
	public void setDocumentBillingDetailvoCol(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs);
	/**
	 * @return documentBillingDetailsVOs
	 */
	public Collection<DocumentBillingDetailsVO> getDocumentBillingDetailvoCol();

	/**
	 * to remove documentBillingDetailsVOs
	 */
	public void removeDocumentBillingDetailvoCol();

}
