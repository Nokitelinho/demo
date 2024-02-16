
/*

 *

 * ListProrationExceptionsSession.java Created on Sep 2008

 *

 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS
 *  Software Services (P) Ltd.

 * Use is subject to license terms.

 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3108
 *
 */
public interface ListProrationExceptionsSession extends ScreenSession {

	/**
	 * @param prorationExceptionsFilterVO
	 */
	public void setProrationExceptionFilterVO(ProrationExceptionsFilterVO prorationExceptionsFilterVO);
	 /**
	 * @return
	 */
	public ProrationExceptionsFilterVO getProrationExceptionFilterVO();
	 /**
	 * 
	 */
	public void removeProrationException();
	 /**
	 * @return
	 */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues();
	 /**
	 * @param oneTimeValues
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	 /**
	 * @return
	 */
	public Page<ProrationExceptionVO> getProrationExceptionVOs();
	 /**
	 * @param prorationExceptionVOs
	 */
	public void setProrationExceptionVOs(Page<ProrationExceptionVO> prorationExceptionVOs);
	 /**
	 * @return
	 */
	public Collection<ProrationExceptionVO> getProrationExceptionVOss();
	 /**
	 * @param prorationExceptionVOs
	 */
	public void setProrationExceptionVOss(Collection<ProrationExceptionVO> prorationExceptionVOs);
	 /**
	 * This method removes
	 */
	public void removeProrationExceptionVOs(); 
	 /**
	 * This method removes
	 */
	public void removeProrationExceptionVOss();
	/**
	    * Methods for getting parentId
	    * @return parentId
	    */
	   public String getParentId();
	  /**
	   * Methods for setting ParentId
	   * @param parentId
	   */
	   public void setParentId(String parentId);
	  /**
	   * This method removes the ParentId in session
	   */
	   public void removeParentId();
	   /**
	    * Methods for getting fromScreenFlag
	    * @return fromScreenFlag
	    */
	   public String getFromScreenFlag();
	  /**
	   * Methods for setting fromScreenFlag
	   * @param fromScreenFlag
	   */
	   public void setFromScreenFlag(String fromScreenFlag);
	   
	 //added by A-5223 for ICRD-21098 starts
		public Integer getTotalRecords();
		public void setTotalRecords(int totalRecords);
		//added by A-5223 for ICRD-21098 ends
		public HashMap<String, String> getSystemParametres();
		public void setSystemParametres(HashMap<String, String> paramHashMap);
		
		
		/**
		 * 
		 * @param gpaBillingDetailsVOs
		 */
		public void setSelectedVoidMailbags(Collection<DocumentBillingDetailsVO> gpaBillingDetailsVOs);
		
		/**
		 * 
		 * @return
		 */
		public Collection<DocumentBillingDetailsVO> getSelectedVoidMailbags();
		
		/**
		 * @return
		 */
		public String[] getSelectedRows();
		/**
		 * @param selectArray
		 */
		public void setSelectedRows(String[] selectArray);
}

