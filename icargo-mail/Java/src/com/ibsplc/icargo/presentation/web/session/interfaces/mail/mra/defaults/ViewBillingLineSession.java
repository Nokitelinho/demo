/*
 * ViewBillingLineSession.java Created on Mar 12, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 *  @author A-2398
 */

public interface ViewBillingLineSession extends ScreenSession {

	
	
	/**
     * This method is used for PageAwareMultiMapper to get the Index Map
     * @return  HashMap<String,String>
     */
    public HashMap<String,String>getIndexMap();

    /**Sets the hashmap for Absolute index of page
     * @param indexMap
     */
    public void setIndexMap(HashMap<String,String>indexMap);
    
    /**
     * Removes the hashmap for Absolute index of page
     *
     */
    public void removeIndexMap();
    
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
     * @return
     */
    public Page<BillingLineVO> getBillingLineDetails();
	
	/**
	 * @param oneTimeVOs
	 */
	public void setBillingLineDetails(Page<BillingLineVO> billingLineDetails);
	
	/**
	 * 
	 */
	public void removeBillingLineDetails();
	/**
	 * 
	 * @return
	 */
	public BillingLineFilterVO getBillingLineFilterVO();
	/**
	 * 
	 * @param billingLineFilterVO
	 */
	public void setBillingLineFilterVO(BillingLineFilterVO billingLineFilterVO);
	/**
	 * 
	 *
	 */
	public void removeBillingLineFilterVO();
	
	/**
	 * 
	 * @return
	 */
	public String getParentScreenId();
	/**
	 * 
	 * @param parentScreenId
	 */
	public void setParentScreenId(String parentScreenId);
	/**
	 * 
	 *
	 */
	public void removeParentScreenId();
	/**
	 * Gets the total records count.
	 * Added by A-5497 for ICRD-69455
	 * @return the total records count
	 */
	public Integer getTotalRecordsCount();
	/**
	 * Sets the total records count.
	 * Added by A-5497 for ICRD-69455
	 * @param totalRecordsCount the new total records count
	 */
	public void setTotalRecordsCount(int totalRecordsCount);
	/**
	 * 
	 * @param selectedBillingLine
	 */
	public void setSelectedBillingLine(BillingLineVO selectedBillingLine);
	/**
	 * 
	 * @return
	 */
	public BillingLineVO getSelectedBillingLine();
	
}
