/*
 * InvoicEnquiryDetailsSessionImpl.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.LocationTransportationDtlsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.PaymentPriceDtlsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicEnquiryDetailsSession;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-2270
 *
 */
public class InvoicEnquiryDetailsSessionImpl extends AbstractScreenSession implements InvoicEnquiryDetailsSession {


	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/*
	 * The screen id
	 */
	private static final String SCREEN_ID =
											"mailtracking.mra.defaults.invoicenquirydetails";
	
	private static final String KEY_PAYMENTANDPRICE = "paymentPriceDetails";
	private static final String KEY_LOCATIONANDTRANSPORT = "locationTransportDetails";
	private static final String KEY_INVOICDETAILS = "detailsVO";
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
    public InvoicEnquiryDetailsSessionImpl() {
        super();
    }
    /**
	 * sets paymentPriceDtlsVO
	 * @param paymentPriceDtlsVO
	 */
	public void setPaymentPriceDtlsVO(PaymentPriceDtlsVO paymentPriceDtlsVO) {
		setAttribute(KEY_PAYMENTANDPRICE, paymentPriceDtlsVO);

	}

	/**
	 * gets paymentPriceDtlsVO
	 * @return paymentPriceDtlsVO
	 */
	public PaymentPriceDtlsVO getPaymentPriceDtlsVO() {
		return getAttribute(KEY_PAYMENTANDPRICE);
	}
	/**
	 *
	 * removes paymentPriceDtlsVO from session
	 */
	public void removePaymentPriceDtlsVO(){
		removeAttribute(KEY_PAYMENTANDPRICE);
	}

	
	/**
	 * sets paymentPriceDtlsVO
	 * @param paymentPriceDtlsVO
	 */
	public void setLocationTransportationDtlsVO(LocationTransportationDtlsVO locationTransportationDtlsVO){
		setAttribute(KEY_LOCATIONANDTRANSPORT, locationTransportationDtlsVO);

	}
	/**
	 * gets paymentPriceDtlsVO
	 * @return paymentPriceDtlsVO
	 */
	public LocationTransportationDtlsVO getLocationTransportationDtlsVO(){
		return getAttribute( KEY_LOCATIONANDTRANSPORT );
	}

	/**
	 *
	 * removes paymentPriceDtlsVO from session
	 */
	public void removeLocationTransportationDtlsVO(){
		removeAttribute(KEY_LOCATIONANDTRANSPORT);
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
		 * sets mailInvoicEnquiryDetailsVO
		 * @param mailInvoicEnquiryDetailsVO
		 */
		   public void setMailInvoicEnquiryDetailsVO(MailInvoicEnquiryDetailsVO mailInvoicEnquiryDetailsVO){
			   setAttribute(KEY_INVOICDETAILS, mailInvoicEnquiryDetailsVO);
		   }
		   /**
			 * gets mailInvoicEnquiryDetailsVO
			 * @return mailInvoicEnquiryDetailsVO
			 */
			public MailInvoicEnquiryDetailsVO getMailInvoicEnquiryDetailsVO(){
				return getAttribute( KEY_INVOICDETAILS );
			}
		    
			/**
			 *
			 * removes mailInvoicEnquiryDetailsVO from session
			 */
			public void removeMailInvoicEnquiryDetailsVO(){
				removeAttribute(KEY_INVOICDETAILS);
			}
		




}
