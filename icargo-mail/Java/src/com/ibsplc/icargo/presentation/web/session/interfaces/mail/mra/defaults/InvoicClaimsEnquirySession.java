/*
 * InvoicClaimsEnquirySession.java Created on Aug 02, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-2270
 *
 */
public interface InvoicClaimsEnquirySession extends ScreenSession {
	/**
	 *
	 * @param Collection<RateLineVO>
	 */
	 public void setMailInvoicClaimsEnquiryVOs(Page<MailInvoicClaimsEnquiryVO> mailInvoicClaimsEnquiryVOs);
		/**
		 *
		 * @return Collection<RateLineVO>
		 */
	 public Page<MailInvoicClaimsEnquiryVO> getMailInvoicClaimsEnquiryVOs();
	 	/**
		 *
		 *@ return void
		 */
	 public void removeMailInvoicClaimsEnquiryVOs();
	 
	 /**
		 * @return Returns the oneTimeValues.
		 */
		HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

		/**
		 * @param oneTimeValues The oneTimeValues to set.
		 */

		void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	 
//	 
//	    /**
//		 * 
//		 * @return Collection<OneTimeVO>
//		 */	 
//		public Collection<OneTimeVO> getStatusOneTimeVOs();
//		
//		/**
//		 * 
//		 * @param cCCollectorVO
//		 */
//		public void setStatusOneTimeVOs(ArrayList<OneTimeVO> statusOneTimeVOs) ;
//
//		/**
//		 * 
//		 *@ return void 
//		 */
//		public void removeStatusOneTimeVOs();
		
		
		
		
		
		
//		 /**
//		 * 
//		 * @return Collection<OneTimeVO>
//		 */	 
//		public Collection<OneTimeVO> getTypeOneTimeVOs();
//		
//		/**
//		 * 
//		 * @param cCCollectorVO
//		 */
//		public void setTypeOneTimeVOs(ArrayList<OneTimeVO> statusOneTimeVOs) ;
//
//		/**
//		 * 
//		 *@ return void 
//		 */
//		public void removeTypeOneTimeVOs();
		
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

		
		// Added by A-5183 for < ICRD-21098 > Starts
		 
	 	/**
		 * @return Returns the totalRecords.
		 */
	 
	 	public Integer getTotalRecordsCount();
	 
	 	/**
		 * @param totalRecords The totalRecords to set.
		 */
	 	
	 	public void setTotalRecordsCount(int totalRecordsCount);
	 	
	 	
	 	public void removeTotalRecordsCount();
		
	 	// Added by A-5183 for < ICRD-21098 > Ends
		
		
		
		
		

}
