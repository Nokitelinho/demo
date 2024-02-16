/*
 * ListULDMovementSessionImpl.java Created on Feb 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;




import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;

import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDMovementSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2122
 *
 */
public  class   ListULDMovementSessionImpl extends AbstractScreenSession
		implements ListULDMovementSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.misc.listuldmovement";
	
	private static final String KEY_LISTFILTER = "uldMovementFilterVO";
	private static final String KEY_ULDMOVEMENTDTLS="uldMovementDetails";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	private static final String  LISTSTATUS = "listStatus";	
	private static final String KEY_BREAKDOWNDEATILS="operationalULDAuditVO";
	private static final String KEY_ACTIONHISTORY="auditDetailsVO";
	private static final String KEY_TOTALRECORDS = "totalRecords";
	/**
	 *
	 * /** Method to get ScreenID
	 *
	 * @return ScreenID 
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 *
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}
	/**
	 * 
	 * @return Returns the uldMovementFilterVO.
	 */
	public ULDMovementFilterVO getUldMovementFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}
	/**
	 * @param uldMovementFilterVO The
	 *  uldMovementFilterVO to set.
	 */
	public void setUldMovementFilterVO(ULDMovementFilterVO
			uldMovementFilterVO) {
		setAttribute(KEY_LISTFILTER,uldMovementFilterVO);
	}
	
	
	/**
	 * This method is used to get the uldMovementDetailVos from the session
	 * @return Page<ULDMovementDetailVO>
	 */
	
	 public Page<ULDMovementDetailVO> getUldMovementDetails() {
		return (Page<ULDMovementDetailVO>) getAttribute(KEY_ULDMOVEMENTDTLS);
	 }
	 
	 
	 /**
     * This method is used to set the uldMovementDetailVos in the Session
     * @param uldMovementDetailVOs The  uldMovementDetailVOs to set
     */
	 public void setUldMovementDetails(Page<ULDMovementDetailVO> 
	 					uldMovementDetailVOs) {
			setAttribute(KEY_ULDMOVEMENTDTLS,(Page<ULDMovementDetailVO>) 
						uldMovementDetailVOs);
	}
	 /**
	  * 
	  * @return HashMap<String,Collection<OneTimeVO>>
	  */
	 public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
			return getAttribute(KEY_ONETIMEVALUES);
		}
	
		/**
		 * @param oneTimeValues The oneTimeValues to set.
		 */
	 public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> 
	 									oneTimeValues) {
			setAttribute(KEY_ONETIMEVALUES,oneTimeValues);
		}
	 	 /**
		 * @return Returns the listStatus.
		 */
		public String getListStatus() {
			return getAttribute(LISTSTATUS);
		}

		/**
		 * @param listStatus The listStatus to set.
		 */
		public void setListStatus(String listStatus) {
			setAttribute(LISTSTATUS,listStatus);
		} 
		/**
		 * @author a-3093
		 * 
		 */
		public Page<OperationalULDAuditVO> getOperationalULDAuditVO() {
			return (Page<OperationalULDAuditVO>) getAttribute(KEY_BREAKDOWNDEATILS);
		 }
		 	 
	   /**
		 * @author a-3093
	     * This method is used to set the uldbreakdownDetailVOs in the Session
	     * @param uldbreakdownDetailVOs The  uldbreakdownDetailVOs to set
	     */
		 public void setOperationalULDAuditVO(Page<OperationalULDAuditVO> 
		 				operationalULDAuditVO) {
				setAttribute(KEY_BREAKDOWNDEATILS,(Page<OperationalULDAuditVO>) 
						operationalULDAuditVO);
		}
		 /**
		  * @author a-3093
		  * 
		  * @return
		  */
		 public Page<AuditDetailsVO> getAuditDetailsVO() {
				return (Page<AuditDetailsVO>) getAttribute(KEY_ACTIONHISTORY);
			 }
			/**
			 * @author a-3093
			 * @param uldConfigAuditVO
			 */
		 public void setAuditDetailsVO(Page<AuditDetailsVO> 
		 auditDetailsVO) {
			 setAttribute(KEY_ACTIONHISTORY,(Page<AuditDetailsVO>) 
					 auditDetailsVO);
}
		
		 public Integer getTotalRecords() {
				return getAttribute(KEY_TOTALRECORDS);
			} 
			
			public void setTotalRecords(int totalRecords) {
				setAttribute(KEY_TOTALRECORDS, totalRecords);
			}
			
			public void removeTotalRecords(){
				removeAttribute(KEY_TOTALRECORDS);
			}
			
	
    
}
