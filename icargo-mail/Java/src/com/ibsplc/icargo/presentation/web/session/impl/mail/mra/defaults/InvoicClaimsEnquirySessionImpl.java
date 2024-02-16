/*
 * InvoicClaimsEnquirySessionImpl.java Created on Aug 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicClaimsEnquirySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-2270
 *
 */
public class InvoicClaimsEnquirySessionImpl extends AbstractScreenSession implements InvoicClaimsEnquirySession {


	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/*
	 * The screen id
	 */
	private static final String SCREEN_ID =
											"mailtracking.mra.defaults.invoicclaimsenquiry";
	
	private static final String KEY_CLAIM_DETAILS = "claimDetails";
	
	private static final String KEY_ONETIME_VALUES = "oneTimeValues";
	
	private static final String KEY_ERRORS = "key_errors";
	
	private static final String TOTAL_RECORDS_COUNT = "totalRecordsCount";
			
	 /**
     * @return
     */
    public String getScreenID() {

        return SCREEN_ID;
    }

    /**
     * @return MODULE_NAME
     */
    public String getModuleName() {
        return MODULE_NAME;
    }
    /**
     * 
     *
     */
    public InvoicClaimsEnquirySessionImpl() {
        super();
    }
    
    /**
	 *
	 * @param Collection<RateLineVO>
	 */
	 public void setMailInvoicClaimsEnquiryVOs(Page<MailInvoicClaimsEnquiryVO> mailInvoicClaimsEnquiryVOs){
		 setAttribute( KEY_CLAIM_DETAILS, (Page<MailInvoicClaimsEnquiryVO>) mailInvoicClaimsEnquiryVOs );
	 }
		/**
		 *
		 * @return Collection<RateLineVO>
		 */
	 public Page<MailInvoicClaimsEnquiryVO> getMailInvoicClaimsEnquiryVOs(){
		 return (Page<MailInvoicClaimsEnquiryVO>) getAttribute(KEY_CLAIM_DETAILS);
	 }
	 	/**
		 *
		 *@ return void
		 */
	 public void removeMailInvoicClaimsEnquiryVOs(){
		 removeAttribute(KEY_CLAIM_DETAILS);
	 }
	 
	 
	 
//	 /**
//		 * 
//		 * @return
//		 */	 
//		public Collection<OneTimeVO> getStatusOneTimeVOs(){
//			return (ArrayList<OneTimeVO>)getAttribute(KEY_CLAIMSTATUS_ONETIME);
//		}
//		/**
//		 * 
//		 * @param taxTypeOneTimeVOs
//		 */
//		public void setStatusOneTimeVOs(ArrayList<OneTimeVO> statusOneTimeVOs) {
//			setAttribute(KEY_CLAIMSTATUS_ONETIME, statusOneTimeVOs);
//		}
//
//		/**
//		 * 
//		 *
//		 */
//		public void removeStatusOneTimeVOs() {
//			removeAttribute(KEY_CLAIMSTATUS_ONETIME);
//		}
		
		    
		     
		     
		     
//		 /**
//		  * for one times
//		  */
//		
	 /**
		 * @return Returns the oneTimeValues.
		 */
		public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
			return getAttribute(KEY_ONETIME_VALUES);
		}

		/**
		 * @param oneTimeValues The oneTimeValues to set.
		 */
		public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues) {
			setAttribute(KEY_ONETIME_VALUES,oneTimeValues);
		}
		     
		    
	 
	 
    	/**
		 * returns RateCardVOs
		 * @return Collection<ErrorVO>
		 */
		public Collection<ErrorVO> getErrorVOs() {

			return (ArrayList<ErrorVO>)getAttribute(KEY_ERRORS );
		}

		/**
		 * sets ErrorVOs
		 * @param  errorVOs
		 */
		public void setErrorVOs(ArrayList<ErrorVO> errorVOs) {
			setAttribute(KEY_ERRORS, errorVOs);

		}

		/**
		 * removes StatusOneTime
		 * @return
		 */
		public void removeErrorVOs() {
			removeAttribute(KEY_ERRORS);
		}
		
		// Added by A-5183 for < ICRD-21098 > Starts 
		
		public Integer getTotalRecordsCount() {
			
			return getAttribute(TOTAL_RECORDS_COUNT);
		}
		
		public void setTotalRecordsCount(int totalRecordsCount) {
			
			setAttribute(TOTAL_RECORDS_COUNT, totalRecordsCount);
					
		}	
		
		public void removeTotalRecordsCount(){
			removeAttribute(TOTAL_RECORDS_COUNT);
		}
		
		// Added by A-5183 for < ICRD-21098 > Ends


}
