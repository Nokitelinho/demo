/*
 * ListCN51CN66Session.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1556
 * 
 */
public interface ListCN51CN66Session extends ScreenSession {
	/**
	 * This method is used to set indexMap to the session
	 * 
	 */
	void setIndexMap(HashMap indexMap);

	/**
	 * This method returns the hashmap
	 * 
	 * @return HashMap - HashMap<String,String>
	 */

	HashMap getIndexMap();

	/**
	 * This method is used to set invoiceStatus to the session
	 * 
	 * @param invoiceStatus
	 */
	/**
	 * 
	 * @param cN51CN66VO
	 */
	public void setCN51CN66VO(CN51CN66VO cN51CN66VO);

	/**
	 * 
	 * @return
	 */
	public CN51CN66VO getCN51CN66VO();

	/**
      * 
      *
      */
	public void removeCN51CN66VO();

	/**
	 * 
	 * @param cN66DetailsVO
	 */
	void setCN66VOs(Page<CN66DetailsVO> cN66DetailsVO);

	/**
	 * 
	 * @return Page<CN66DetailsVO>
	 */
	Page<CN66DetailsVO> getCN66VOs();

	/**
      * 
      *
      */
	public void removeCN66VOs();

	/**
	 * 
	 * @param cn51CN66FilterVO
	 */
	public void setCN51CN66FilterVO(CN51CN66FilterVO cn51CN66FilterVO);

	/**
	 * 
	 * @return
	 */
	public CN51CN66FilterVO getCN51CN66FilterVO();

	/**
	  * 
	  *
	  */
	public void removeCN51CN66FilterVO();

	/**
	 * 
	 * @return Page<CN51DetailsVO>
	 */

	Page<CN51DetailsVO> getCN51DetailsVOs();

	/**
	 * 
	 * @param cn51DetailsVOs
	 */
	void setCN51DetailsVOs(Page<CN51DetailsVO> cn51DetailsVOs);

	/**
	 * Method to remove HashMap<String, Collection<AccountingEntryDetailsVO>>
	 */
	public void removeCN51DetailsVOs();

	/**
	 * 
	 * @return HashMap
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 * to remove one time values
	 */
	public void removeOneTimeVOs();

	/**
	 * Method to return ParentScreenFlag
	 * 
	 * @return parentScreenFlag
	 */
	public String getParentScreenFlag();

	/**
	 * Method to set ParentScreenFlag
	 * 
	 * @param parentScreenFlag
	 */
	public void setParentScreenFlag(String parentScreenFlag);

	/**
	 * Method to remove ParentScreenFlag
	 */
	public void removeParentScreenFlag();

	public Integer getTotalRecords();

	public void setTotalRecords(int totalRecords);

	/**
	 * This method removes the totalRecords in session
	 */
	public void removeTotalRecords();

	/**
	 * @return Returns the totalRecords.
	 */
	public Integer getTotalRecordsUnlabelled();

	/**
	 * @param totalRecords
	 *            The totalRecords to set.
	 */
	public void setTotalRecordsUnlabelled(int totalRecords);

	/**
	 * This method removes the totalRecords in session
	 */
	public void removeTotalRecordsUnlabelled();

	/**
	 * 
	 * @return
	 */
	public HashMap<String, Collection<CN51DetailsVO>> getCN51VO();

	/**
	 * Method to set HashMap<String, Collection<AccountingEntryDetailsVO>>
	 * 
	 * @param accountingDetailsEntryVOs
	 */
	public void setCN51VO(
			HashMap<String, Collection<CN51DetailsVO>> cn51DetailsVOs);
	
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
