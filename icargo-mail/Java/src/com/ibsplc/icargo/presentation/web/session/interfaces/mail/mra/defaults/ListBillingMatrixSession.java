/*
 * ListBillingMatrixSession.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2280
 *
 */
public interface ListBillingMatrixSession extends ScreenSession{

	
	/**
	 * 
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * 
	 * @param oneTimeValues
	 */
	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues);

	/**
	 * 
	 *
	 */
	public void removeOneTimeValues();
	
	/**
	 * 
	 * @return
	 */
	public Page<BillingMatrixVO> getBillingMatrixVOs();
	
	/**
	 * 
	 * @param billingMatrixVOs
	 */
	public void setBillingMatrixVOs(Page<BillingMatrixVO> billingMatrixVOs);
	/**
	 * 
	 *
	 */
	public void removeBillingMatrixVOs();
	/**
	 * 
	 * @return
	 */
	public BillingMatrixFilterVO getBillingMatrixFilterVO();
	/**
	 * 
	 * @param billingMatrixFilterVO
	 */
	
	public void setBillingMatrixFilterVO(BillingMatrixFilterVO billingMatrixFilterVO);
	/**
	 * 
	 *
	 */
	
	public void removeBillingMatrixFilterVO();
	
	 
	//Added by A-5218 to enable last link in pagination start
	 Integer getTotalRecords();
		
	 void setTotalRecords(int totalRecords);
		
	 void setListDisplayPages(Page<BillingMatrixVO> page);

	 Page<BillingMatrixVO> getListDisplayPages();

	//Added by A-5218 to enable last link in pagination end

	
}
