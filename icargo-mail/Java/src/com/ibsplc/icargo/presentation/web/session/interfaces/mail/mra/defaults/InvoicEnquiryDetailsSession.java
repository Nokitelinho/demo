/*
 * InvoicEnquiryDetailsSession.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.LocationTransportationDtlsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.PaymentPriceDtlsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-2270
 *
 */
public interface InvoicEnquiryDetailsSession extends ScreenSession {
	 /**
	 * sets mailInvoicEnquiryDetailsVO
	 * @param mailInvoicEnquiryDetailsVO
	 */
	   public void setMailInvoicEnquiryDetailsVO(MailInvoicEnquiryDetailsVO mailInvoicEnquiryDetailsVO);
	   /**
		 * gets mailInvoicEnquiryDetailsVO
		 * @return mailInvoicEnquiryDetailsVO
		 */
		public MailInvoicEnquiryDetailsVO getMailInvoicEnquiryDetailsVO();
	    
		/**
		 *
		 * removes mailInvoicEnquiryDetailsVO from session
		 */
		public void removeMailInvoicEnquiryDetailsVO();
	
	     /**
		 * sets paymentPriceDtlsVO
		 * @param paymentPriceDtlsVO
		 */
		public void setPaymentPriceDtlsVO(PaymentPriceDtlsVO paymentPriceDtlsVO);

		/**
		 * gets paymentPriceDtlsVO
		 * @return paymentPriceDtlsVO
		 */
		public PaymentPriceDtlsVO getPaymentPriceDtlsVO();

		/**
		 *
		 * removes paymentPriceDtlsVO from session
		 */
		public void removePaymentPriceDtlsVO();

		
		/**
		 * sets paymentPriceDtlsVO
		 * @param paymentPriceDtlsVO
		 */
		public void setLocationTransportationDtlsVO(LocationTransportationDtlsVO locationTransportationDtlsVO);

		/**
		 * gets paymentPriceDtlsVO
		 * @return paymentPriceDtlsVO
		 */
		public LocationTransportationDtlsVO getLocationTransportationDtlsVO();

		/**
		 *
		 * removes paymentPriceDtlsVO from session
		 */
		public void removeLocationTransportationDtlsVO();

		
		
		/**
		 * returns errorVOs
		 *
		 *  @return errorVOs
		 */
		public Collection<ErrorVO> getErrorVOs();

		/**
		 * sets errorVOs
		 */
		public void setErrorVOs(ArrayList<ErrorVO> errorVOs);

		/**
		 * removes errorVOs
		 */
		public void removeErrorVOs();

}
