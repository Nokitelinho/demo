/*
 * InvoicEnquirySessionImpl.java Created on Jul 30, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquirySummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicEnquirySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-2270
 *
 */
public class InvoicEnquirySessionImpl extends AbstractScreenSession implements InvoicEnquirySession {


	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/*
	 * The screen id
	 */
	private static final String SCREEN_ID =
											"mailtracking.mra.defaults.invoicenquiry";
	private static final String KEY_ONETIME_VOS="onetimevalues";
	private static final String KEY_INVOICDETAILSVOS = "invoicDetails";
	
	private static final String KEY_INVOICSUMARYVOS = "invoicSummary";
		private static final String KEY_ERRORS = "key_errors";
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
    public InvoicEnquirySessionImpl() {
        super();
    }

    /**
	 * sets rateLineVOs
	 * 
	 * @param rateLineVOs
	 */

		 public void setMailInvoicEnquiryDetailsVOs(Page<MailInvoicEnquiryDetailsVO> mailInvoicEnquiryDetailsVOs){
		 setAttribute( KEY_INVOICDETAILSVOS, (Page<MailInvoicEnquiryDetailsVO>) mailInvoicEnquiryDetailsVOs );
	 }

	 /**
	 *  
	 * @return Page<RateLineVO>
	 */
	public Page<MailInvoicEnquiryDetailsVO> getMailInvoicEnquiryDetailsVOs(){
		 return (Page<MailInvoicEnquiryDetailsVO>) getAttribute(KEY_INVOICDETAILSVOS);
	}
	/**
	 *
	 * @return void
	 */
	public void removeMailInvoicEnquiryDetailsVOs(){
	 removeAttribute(KEY_INVOICDETAILSVOS);
	}
  		/**
		 * sets rateLineFilterVO
		 * @param  rateLineFilterVO
		 * @return
		 * 
		 */
		public void setMailInvoicEnquirySummaryVO(MailInvoicEnquirySummaryVO mailInvoicEnquirySummaryVO) {
			setAttribute(KEY_INVOICSUMARYVOS, mailInvoicEnquirySummaryVO);

		}

		/**
		 * gets rateLineFilterVO
		 *
		 * @return rateLineFilterVO
		 */
		public MailInvoicEnquirySummaryVO getMailInvoicEnquirySummaryVO() {
			return getAttribute( KEY_INVOICSUMARYVOS );
		}

		/**
		 * removes rateLineFilterVO
		 * @return
		 */
		public void removeMailInvoicEnquirySummaryVO() {
			removeAttribute(KEY_INVOICSUMARYVOS);

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

		 /**

	    *

	    * @return

	    */

	    public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

	    return getAttribute(KEY_ONETIME_VOS);

	    }
	    /**

	    *

	    * @param oneTimeVOs

	    */

	    public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

	    setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

	    }
	    /**

	    *

	    *remove onetimes

	    */

	    public void removeOneTimeVOs() {

	    removeAttribute(KEY_ONETIME_VOS);

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
		 public Page<MailInvoicEnquiryDetailsVO> getListDisplayPages(){
		     return (Page<MailInvoicEnquiryDetailsVO>)getAttribute("listDisplayPage");
		 }
		  /**
		   * @param tariffListVOs
		   */
		 public void setListDisplayPages(Page<MailInvoicEnquiryDetailsVO> mailInvoiceListVOs){
		     setAttribute("listDisplayPage", mailInvoiceListVOs);
		 }
		//Added by A-5218 to enable last link pagination end


}
