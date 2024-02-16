/*
 * GPABillingInvoiceEnquirySessionImpl.java Created on July 3, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;

/**
 * @author A-3434
 *
 */
public class GPABillingInvoiceEnquirySessionImpl extends AbstractScreenSession 
implements GPABillingInvoiceEnquirySession{

private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";
private static final String KEY_INVOICEENQUIRYFILTER = "invoiceenquiryfiltervos";
private static final String KEY_CN66DETAILS = "cn66details";
private static final String KEY_CN51SUMMARY="cn51summaryvo";
private static final String KEY_CN66DETAILSVO="cn66detailvo";
private static final String KEY_ONETIME_CODES_MAP = "onetimecodesmap";
private static final String KEY_PARENTSCREEN = "parentScreen";


/**
 * KEY for setting Status in session
 */
public static final String KEY_STATUS = "Status ";

/**
 * KEY for setting Status Keys in session
 */
public static final String KEY_STATUSKEYS = "StatusKeys";
/**
 * KEY for setting STATUSVOs in session
 */
public static final String KEY_STATUSVOS = "StatusVOs";
/**
 *
 */
	 /**
	  *
	  *	/
	  public GPABillingInvoiceEnquirySessionImpl() {
       super();

		} 
		 /**
	     * @return ScreenID
	     */
	    public String getScreenID() {

	        return SCREENID;
	    }

	    /**
	     * @return ModuleName
	     */
	    public String getModuleName() {
	        return MODULE_NAME;
	    }

	    /** set CN51SummaryVO
	     * @param cN51SummaryVO
	     */
	    public void setCN51SummaryVO
	    (CN51SummaryVO cN51SummaryVO){
	    	setAttribute(KEY_CN51SUMMARY, cN51SummaryVO);
	    }
	    /**
	     * get CN51SummaryVO
	     */

	    public CN51SummaryVO getCN51SummaryVO(){
	    	return getAttribute(KEY_CN51SUMMARY);
	    }

	   /**
	    * remove CN51SummaryVO
	    */

	    public void removeCN51SummaryVO(){
	    	removeAttribute(KEY_CN51SUMMARY);
	    }
	    /** set CN66DetailsVO
	     * @param cN66DetailsVO
	     */
	    public void setCN66DetailsVO(CN66DetailsVO cN66DetailsVO){
	    	setAttribute(KEY_CN66DETAILSVO,cN66DetailsVO);
	    }
	    
	    /**
	     * get CN66DetailsVO
	     */

	    public CN66DetailsVO getCN66DetailsVO(){
	    	return getAttribute(KEY_CN66DETAILSVO);
	    }

	   /**
	    * remove CN66DetailsVO
	    */

	    public void removeCN66DetailsVO(){
	    	removeAttribute(KEY_CN66DETAILSVO);
	    }
	    /** set GpaBillingInvoiceEnquiryFilterVO
	     * @param gpaBillingInvoiceEnquiryFilterVO
	     */
	    public void setGpaBillingInvoiceEnquiryFilterVO
	    (GpaBillingInvoiceEnquiryFilterVO gpaBillingInvoiceEnquiryFilterVO){
	    	setAttribute(KEY_INVOICEENQUIRYFILTER,gpaBillingInvoiceEnquiryFilterVO);
	    }
	    /**
	     * get GpaBillingInvoiceEnquiryFilterVO
	     */

	    public GpaBillingInvoiceEnquiryFilterVO getGpaBillingInvoiceEnquiryFilterVO(){
	    	return getAttribute(KEY_INVOICEENQUIRYFILTER);
	    }

	   /**
	    * remove GpaBillingInvoiceEnquiryFilterVO
	    */

	    public void removeGpaBillingInvoiceEnquiryFilterVO(){
	    	removeAttribute(KEY_INVOICEENQUIRYFILTER);
	    }
	    /** set cN66detailsVOs
	     * @param cN66detailsVOs
	     */
	    public void setCN66VOs(Collection<CN66DetailsVO> cN66detailsVOs) {

			setAttribute( KEY_CN66DETAILS, (ArrayList<CN66DetailsVO>) cN66detailsVOs );

		}
	    /**
	     * get cN66detailsVOs
	     */
	    public Collection<CN66DetailsVO> getCN66VOs() {

			return (ArrayList<CN66DetailsVO>) getAttribute(KEY_CN66DETAILS);
		}



	    /**
	     * remove cN66detailsVOs
	     */

	    public void removeCN66VOs(){
	    	removeAttribute(KEY_CN66DETAILS);
	    	
	    }
	    public HashMap<String, Collection<OneTimeVO>> getOneTimeMap() {
			return (HashMap<String, Collection<OneTimeVO>>) getAttribute(KEY_ONETIME_CODES_MAP);
		}

		/**
		 * Method to set the Onetimes map to session
		 * 
		 * @param oneTimeMap
		 * The one time map to be set to session
		 */
		public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap) {
			setAttribute(KEY_ONETIME_CODES_MAP,
					(HashMap<String, Collection<OneTimeVO>>) oneTimeMap);
		}

		/**
		 * Method to remove One Time Map from session
		 */
		public void removeOneTimeMap() {
			removeAttribute(KEY_ONETIME_CODES_MAP);

		}
		/**
		 * @return invoicestatus
		 */
		public Collection<OneTimeVO> getStatus() {
			return (Collection<OneTimeVO>) getAttribute(KEY_STATUS);
		}

		/**
		 * @param invoicestatus
		 */
		public void setStatus(Collection<OneTimeVO> invoiceStatus) {
			setAttribute(KEY_STATUS, (ArrayList<OneTimeVO>) invoiceStatus);
		}

		/**
		 * @return invoicestatusKeys
		 */
		public Collection<String> getStatusKeys() {
			return (Collection<String>) getAttribute(KEY_STATUSKEYS);
		}

		/**
		 * @param invoicestatusKeys
		 */
		public void setStatusKeys(Collection<String> invoiceStatusKeys) {
			setAttribute(KEY_STATUSKEYS, (ArrayList<String>)invoiceStatusKeys);
		}

		/**
		 * @return InvoiceStatusVOs
		 */
		public HashMap<String, String> getStatusVOs() {
			return (HashMap<String, String>) getAttribute(KEY_STATUSVOS);
		}

		/**
		 * @param InvoiceStatusVOs
		 */
		public void setStatusVOs(HashMap<String, String> invoiceStatusVOs) {
			setAttribute(KEY_STATUSVOS,
					(HashMap<String, String>) invoiceStatusVOs);
		}
		
		public String getParentScreenID(){
	    	return getAttribute(KEY_PARENTSCREEN);
	    }

	   /**
	    * remove ParentScreenID
	    */

	    public void removeParentScreenID(){
	    	removeAttribute(KEY_PARENTSCREEN);
	    }
	    /** set ParentScreenID
	     * @param ParentScreenID
	     */
	    public void setParentScreenID(String  parentScreenId){
	    	setAttribute(KEY_PARENTSCREEN,parentScreenId);
	    }


}
