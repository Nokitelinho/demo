/*
 * ListInvoiceSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1347
 *
 */
public interface ListInvoiceSession extends ScreenSession {
	    
    
  
     /**
      * 
      * @return
      */
    public Page<ULDChargingInvoiceVO> getULDChargingInvoiceVO();
     /**
      * 
      * @param uldChargingInvoiceVO
      */
    public void setULDChargingInvoiceVO(Page<ULDChargingInvoiceVO> uldChargingInvoiceVO);
  /**
   * 
   * @return
   */
    public ChargingInvoiceFilterVO getChargingInvoiceFilterVO();
   /**
    * 
    * @param chargingInvoiceFilterVO
    */
    public void setChargingInvoiceFilterVO(ChargingInvoiceFilterVO chargingInvoiceFilterVO);
    /**
    * @return Returns the oneTimeValues.
  	*/
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues();
	

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	/**
	 * @return Returns the lovVO.
	 */
	public Page<String> getLovVO();

	/**
	 * @param lovVO The lovVO to set.
	 */
	public void setLovVO(Page<String> lovVO); 
	
	void setTotalRecords(int totalRecords);//Added by A-5214 as part from the ICRD-22824
	
	Integer getTotalRecords();//Added by A-5214 as part from the ICRD-22824
	
	
	/**
	 * @return Returns the listStatus.
	 */
	public String getListStatus();
	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setListStatus(String listStatus);
}
