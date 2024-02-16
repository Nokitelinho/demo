/*
 * GPABillingInvoiceEnquirySession.java Created on July 3, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-3434
 *
 */
public interface GPABillingInvoiceEnquirySession extends ScreenSession{
	/**
     * 
     * @param cN66DetailsVO
     */
	 public void setCN66VOs(Collection<CN66DetailsVO> cN66DetailsVO);
    /**
     * 
     * @return
     */
	 public Collection<CN66DetailsVO> getCN66VOs();
    /**
     * 
     *
     */
	 public void removeCN66VOs();
    /** 
     * 
     * @param gpaBillingInvoiceEnquiryFilterVO
     */
	 public void setGpaBillingInvoiceEnquiryFilterVO(GpaBillingInvoiceEnquiryFilterVO gpaBillingInvoiceEnquiryFilterVO);
	 /**
	  * 
	  * @return
	  */
	 public GpaBillingInvoiceEnquiryFilterVO getGpaBillingInvoiceEnquiryFilterVO();
	 /**
	  * 
	  *
	  */
	 public void removeGpaBillingInvoiceEnquiryFilterVO();
	 /**
	  * 
	  * @param cN51SummaryVO
	  */
	 public void setCN51SummaryVO(CN51SummaryVO cN51SummaryVO);
	 public CN51SummaryVO getCN51SummaryVO();
	 public void removeCN51SummaryVO();
	 /** 
	     * 
	     * @param gpaBillingInvoiceEnquiryFilterVO
	     */
		 public void setCN66DetailsVO(CN66DetailsVO cN66DetailsVO);
		 /**
		  * 
		  * @return
		  */
		 public CN66DetailsVO getCN66DetailsVO();
		 /**
		  * 
		  *
		  */
		 public void removeCN66DetailsVO();
	 /**
		 *   Method to get the onetime map in the
		 *         session
		 * @return HashMap the onetime map from session
		 */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeMap();

		/**
		 *  Method to set the Onetimes map to session
		 * @param oneTimeMap
		 *            The one time map to be set to session
		 */
		public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap);

		/**
		 *  Method to remove One Time Map from
		 *         session
		 */
		public void removeOneTimeMap();
		
		/**
		 * @return invoiceStatus
		 */
		public Collection<OneTimeVO> getStatus();

		/**
		 * @param invoiceStatus
		 */
		public void setStatus(Collection<OneTimeVO> status);
		/**
		 * @return InvoiceStatusKeys
		 */
		public Collection<String> getStatusKeys();

		/**
		 * @param InvoiceStatusKeys
		 */
		public void setStatusKeys(Collection<String> statusKeys);
		/**
		 * @return InvoiceStatusVOs
		 */
		public HashMap<String, String> getStatusVOs();
		
		/**
		 * @param InvoiceStatusVOs
		 */
		public void setStatusVOs(HashMap<String, String> statusVOs);
		/**
		 *
		 * @return
		 */
		
		public String getParentScreenID();

	    public void removeParentScreenID();
	    	
	    public void setParentScreenID(String  parentScreenId);
	    
}
	    
