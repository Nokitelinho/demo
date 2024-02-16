/*
 * ListUPURateLineSession.java Created on Jan 19, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1556
 *
 */
public interface ListUPURateLineSession extends ScreenSession {
	/**
	 *
	 * @param Collection<RateLineVO>
	 */
	 public void setRateLineVOs(Page<RateLineVO> rateLineVOs);
		/**
		 *
		 * @return Collection<RateLineVO>
		 */
	 public Page<RateLineVO> getRateLineVOs();
	 	/**
		 *
		 *@ return void
		 */
	 public void removeRateLineVOs();

	 	/**
		 *
		 * @return Collection<OneTimeVO>
		 */
		public Collection<OneTimeVO> getStatusOneTime();

		/**
		 *
		 * @param cCCollectorVO
		 */
		public void setStatusOneTime(ArrayList<OneTimeVO> statusTimeVOs) ;

        /**
         * 
         * @return HashMap
         */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

		/**
		 * @param oneTimeVOs
		 */
		public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

		/**
		 * to remove one time values
		 */
		public void removeOneTimeVOs();

		/**
		 *
		 *@ return void
		 */
		public void removeStatusOneTime();

        /**
         * 
         * @return Page<RateLineVO>
         */
		public Page<RateLineVO> getSelectedRateLineVOs();
        /**
         * 
         * @param rateCardVOs
         */
		public void setSelectedRateLineVOs(Page<RateLineVO> rateCardVOs) ;
       /**
        * 
        *
        */
		public void removeSelectedRateLineVOs();

		/**
		 * sets rateLineFilterVO
		 * @param rateLineFilterVO
		 */
		public void setRateLineFilterVO(RateLineFilterVO rateLineFilterVO);

		/**
		 * gets rateLineFilterVO
		 * @return rateLineFilterVO
		 */
		public RateLineFilterVO getRateLineFilterVO();

		/**
		 *
		 * removes rateLineFilterVO from session
		 */
		public void removeRateLineFilterVO();

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
