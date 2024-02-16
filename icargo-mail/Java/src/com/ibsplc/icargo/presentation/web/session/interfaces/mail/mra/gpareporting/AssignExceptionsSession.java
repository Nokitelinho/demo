/*
 * AssignExceptionsSession.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting;



import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2245
 *
 */
	public interface AssignExceptionsSession extends ScreenSession {

	   /**
		 * method to set GPAReportingFilterVO to session
		 * @param gpaReportingFilterVO
		 */
	    public void setGpaReportingFilterVO(GPAReportingFilterVO gpaReportingFilterVO);
	    /**
	      * method to get GPAReportingFilterVO from session
	      * @return GPAReportingFilterVO
	      */
	    public GPAReportingFilterVO getGpaReportingFilterVO();
	    /**
	      * method to remove GPAReportingFilterVO from session
	      */
	    public void removeGpaReportingFilterVO();
	    /**
	     * method to set gpaReportingDetailVOs to session
	     * @param gpaReportingDetailVOs
	     */
		public void setGpaReportingDetailVOs(Page<GPAReportingClaimDetailsVO> gpaReportingDetailVOs);
		/**
		 * method to get Page<GPABillingDetailsVO> from session 
		 * @return Page<GPABillingDetailsVO>
		 */
		public Page<GPAReportingClaimDetailsVO> getGpaReportingDetailVOs();
		/**
		 * method to remove Page<GPABillingDetailsVO> from session
		 */
		public void removeGpaReportingDetailVOs();
		
	    /**
	     * method to get HashMap<String, Collection<OneTimeVO>> from session
	     * @return HashMap<String, Collection<OneTimeVO>>
	     */
	     public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	     /**
	      * method to set oneTimeVOs to session
	      * @param oneTimeVOs
	      */
	     public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	     /**
	      * method to remove oneTimeVOs from session
	      */
	     public void removeOneTimeVOs();
}
