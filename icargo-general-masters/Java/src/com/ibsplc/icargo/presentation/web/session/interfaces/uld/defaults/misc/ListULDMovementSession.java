/*
 * ListULDMovementSession.java Created on Feb 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;



import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2122
 *
 */
public interface ListULDMovementSession extends ScreenSession {
	/**
	 * 
	 * @return String
	 */
	public String getScreenID();
	/**
	 * 
	 * @return String 
	 */
    public String getModuleName();
    /**
     * 
     * @param uldMovementDetailVOs
     */
	public void setUldMovementDetails(Page<ULDMovementDetailVO> 
												uldMovementDetailVOs);
	/**
	 * 
	 * @return Page<ULDMovementDetailVO>
	 */
	public Page<ULDMovementDetailVO> getUldMovementDetails();
	/**
	 * 
	 * @param uldMovementFilterVO
	 */
	public void setUldMovementFilterVO(ULDMovementFilterVO
			uldMovementFilterVO);
	/**
	 * 
	 * @return ULDMovementFilterVO
	 */
	
	public ULDMovementFilterVO getUldMovementFilterVO(); 
	

    /**
	 * @return Returns the listStatus.
	 */
	String getListStatus();

	/**
	 * @param listStatus The listStatus to set.
	 */
	void setListStatus(String listStatus);
	
	 /**
	 * @return Returns the oneTimeValues.
	 */
		HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	public void setOperationalULDAuditVO(Page<OperationalULDAuditVO> operationalULDAuditVO);  
	/**
	* 
	* @return Page<ULDMovementDetailVO>
	*/
	public Page<OperationalULDAuditVO> getOperationalULDAuditVO();
	
	 public Page<AuditDetailsVO> getAuditDetailsVO();
		
		/**
		 * @author a-3093
		 * @param uldConfigAuditVO
		 */
	 public void setAuditDetailsVO(Page<AuditDetailsVO> auditDetailsVO); 
	 /**
	  * 
	  * @return int
	  */
	 public Integer getTotalRecords();
	 /**
	  * 
	  * @return void
	  */
	 public void setTotalRecords(int totalRecords);
	 /**
	  * 
	  * @return void
	  */
	 public void removeTotalRecords();
		
	
}
