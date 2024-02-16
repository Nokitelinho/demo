/*
 * ListInvoiceSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.transaction;


import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListInvoiceSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1496
 *
 */
public class ListInvoiceSessionImpl extends AbstractScreenSession
		implements ListInvoiceSession {

	private static final String MODULE = "uld.defaults";
	
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	
	private static final String KEY_INVOICELISTVO = "uLDChargingInvoiceVO";
	
	private static final String KEY_INVOICEFILTERVO = "chargingInvoiceFilterVO";
	
	private static final String KEY_LOVVO = "lovVO";
	
	private static final String KEY_LISTSTATUS = "listStatus";
	
	private static final String KEY_TOTAL_RECORDS = "totalRecords";//Added by A-5214 as part from the ICRD-22824
	
	
	private static final String SCREENID =
		"uld.defaults.listinvoice";

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
	 * @return
	 */
	 public Page<ULDChargingInvoiceVO> getULDChargingInvoiceVO(){
		 return getAttribute(KEY_INVOICELISTVO);
	 }
     /**
      * @param uldChargingInvoiceVO
      */
      public void setULDChargingInvoiceVO(Page<ULDChargingInvoiceVO> uldChargingInvoiceVO){
    	  setAttribute(KEY_INVOICELISTVO,uldChargingInvoiceVO);
      }
      /**
  	 * @return
  	 */
     public ChargingInvoiceFilterVO getChargingInvoiceFilterVO(){
    	  return getAttribute(KEY_INVOICEFILTERVO);
	 }
    /**
     * @param chargingInvoiceFilterVO
     */
     public void setChargingInvoiceFilterVO(ChargingInvoiceFilterVO chargingInvoiceFilterVO){
    	 setAttribute(KEY_INVOICEFILTERVO,chargingInvoiceFilterVO);
    	   
     }
	 /**
	 * @return Returns the oneTimeValues.
	 */
  	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
  		return getAttribute(KEY_ONETIMEVALUES);
  	}

  	/**
  	 * @param oneTimeValues The oneTimeValues to set.
  	 */
  	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues) {
  		setAttribute(KEY_ONETIMEVALUES,oneTimeValues);
  	}

	/**
	 * @return Returns the lovVO.
	 */
	public Page<String> getLovVO() {
		return getAttribute(KEY_LOVVO);
	}

	/**
	 * @param lovVO The lovVO to set.
	 */
	public void setLovVO(Page<String> lovVO) {
		setAttribute(KEY_LOVVO,lovVO);
	}

	/**
	 * @return Returns the listStatus.
	 */
	public String getListStatus() {
		return getAttribute(KEY_LISTSTATUS);
	}

	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setListStatus(String listStatus) {
		setAttribute(KEY_LISTSTATUS,listStatus);
	} 
	
	/**
	  * Added by A-5214 as part from the ICRD-22824
	  * This method is used to set total records values in session
	  * @param int
	*/
	public void setTotalRecords(int totalRecords){
     setAttribute(KEY_TOTAL_RECORDS, Integer.valueOf(totalRecords));
  }
	
	/**
	  * Added by A-5214 as part from the ICRD-22824
	  * This method is used to get total records values from session
	  * from session
	  * @return Integer
	*/
	
	public Integer getTotalRecords() {
		return (Integer)getAttribute(KEY_TOTAL_RECORDS);
	}
  
}
