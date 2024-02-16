/*
 * ListBillingMatrixSessionImpl.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListBillingMatrixSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2280
 *
 */
public class ListBillingMatrixSessionImpl extends AbstractScreenSession implements ListBillingMatrixSession{
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingmatrix";
	private static final String KEY_ONETIME="onetimevalues";
	private static final String KEY_PAGE_BILLINGMATRIX="billingmatrixvos";
	private static final String KEY_BILLINGMATRIXFILTERVO="billingmatrixfiltervo";

	/**
	 * 
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_ONETIME);
	}

	/**
	 * 
	 */
	public void setOneTimeValues(HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIME,oneTimeValues);
		
	}

	/**
	 * 
	 */
	public void removeOneTimeValues() {
		removeAttribute(KEY_ONETIME);
		
	}

	/**
	 * 
	 */
	public Page<BillingMatrixVO> getBillingMatrixVOs() {
		
		return getAttribute(KEY_PAGE_BILLINGMATRIX);
	}

	/**
	 * 
	 */
	public void setBillingMatrixVOs(Page<BillingMatrixVO> billingMatrixVOs) {
		setAttribute(KEY_PAGE_BILLINGMATRIX,billingMatrixVOs);
		
	}

	/**
	 * 
	 */
	public void removeBillingMatrixVOs() {
		removeAttribute(KEY_PAGE_BILLINGMATRIX);
		
	}

	

	/**
	 * 
	 */
	@Override
	public String getScreenID() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 * 
	 */
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MODULE_NAME;
	}
/**
 * @return 
 */
	public BillingMatrixFilterVO getBillingMatrixFilterVO() {
		
		return getAttribute(KEY_BILLINGMATRIXFILTERVO);
	}

	/**
	 * @param billingMatrixFilterVO
	 */
	public void setBillingMatrixFilterVO(BillingMatrixFilterVO billingMatrixFilterVO) {
		setAttribute(KEY_BILLINGMATRIXFILTERVO,billingMatrixFilterVO);
		
	}

	/**
	 * 
	 */
	public void removeBillingMatrixFilterVO() {
		removeAttribute(KEY_BILLINGMATRIXFILTERVO);
		
	}
	
	//Added by A-5218 to enable last link pagination start
	/**
	 * @return totalRecords
	 */
	
	 public Integer getTotalRecords(){
	    return (Integer)getAttribute("totalRecords");
	 }
	 /**
	  * @param totalRecords
	  */
	 public void setTotalRecords(int totalRecords){
	    setAttribute("totalRecords", Integer.valueOf(totalRecords));
	 }
	 /**
	  * @return Page
	  */
	 public Page<BillingMatrixVO> getListDisplayPages(){
	     return (Page<BillingMatrixVO>)getAttribute("listDisplayPage");
	 }
	  /**
	   * @param tariffListVOs
	   */
	 public void setListDisplayPages(Page<BillingMatrixVO> mailTrackingListVOs){
	     setAttribute("listDisplayPage", mailTrackingListVOs);
	 }
	//Added by A-5218 to enable last link pagination end

}
