/*
 * InvoicEnquirySession.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquirySummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-2270
 *
 */
public interface InvoicEnquirySession extends ScreenSession {
	/**
	 *
	 * @param Collection<RateLineVO>
	 */
	 public void setMailInvoicEnquiryDetailsVOs(Page<MailInvoicEnquiryDetailsVO> mailInvoicEnquiryDetailsVOs);
		/**
		 *
		 * @return Collection<RateLineVO>
		 */
	 public Page<MailInvoicEnquiryDetailsVO> getMailInvoicEnquiryDetailsVOs();
	 	/**
		 *
		 *@ return void
		 */
	 public void removeMailInvoicEnquiryDetailsVOs();

	 	/**
		 * sets rateLineFilterVO
		 * @param rateLineFilterVO
		 */
		public void setMailInvoicEnquirySummaryVO(MailInvoicEnquirySummaryVO mailInvoicEnquirySummaryVO);

		/**
		 * gets rateLineFilterVO
		 * @return rateLineFilterVO
		 */
		public MailInvoicEnquirySummaryVO getMailInvoicEnquirySummaryVO();

		/**
		 *
		 * removes rateLineFilterVO from session
		 */
		public void removeMailInvoicEnquirySummaryVO();

		
		
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
		/**
		 * @return
		 */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

		/**
		 * @param oneTimeVOs
		 */
		public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

		/**
		 *
		 */
		public void removeOneTimeVOs();
		
		//Added by A-5218 to enable last link in pagination start
		/**
		 * @return
		 */
		public Integer getTotalRecords();
		/**
		 * @param totalRecords
		 */
		public void setTotalRecords(int totalRecords);
		/**
		 * @param page
		 */
		public void setListDisplayPages(Page<MailInvoicEnquiryDetailsVO> page);
		/**
		 * @return
		 */
		public Page<MailInvoicEnquiryDetailsVO> getListDisplayPages();

		//Added by A-5218 to enable last link in pagination end

}
