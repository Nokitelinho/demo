/*
 * ListUPURateLineSessionImpl.java Created on Jan 10, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateLineSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1556
 *
 */
public class ListUPURateLineSessionImpl extends AbstractScreenSession implements ListUPURateLineSession {


	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/*
	 * The screen id
	 */
	private static final String SCREEN_ID =
											"mailtracking.mra.defaults.viewupurate";
	private static final String KEY_RATELINEVOS= "rateDetails";

	private static final String KEY_SELECTED_RATELINE = "selectedRateLine";

	private static final String KEY_STATUS_ONETIMEVOS = "statusOneTimeVOs";

	private static final String KEY_STATUS_ONETIME   = "onetimes";

	private static final String KEY_FILTER = "ratelinefilter";


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
    public ListUPURateLineSessionImpl() {
        super();
    }

    /**
	 * sets rateLineVOs
	 * 
	 * @param rateLineVOs
	 */

		 public void setRateLineVOs(Page<RateLineVO> rateLineVOs){
		 setAttribute( KEY_RATELINEVOS, (Page<RateLineVO>) rateLineVOs );
	 }

	 /**
	 *  
	 * @return Page<RateLineVO>
	 */
	public Page<RateLineVO> getRateLineVOs(){
		 return (Page<RateLineVO>) getAttribute(KEY_RATELINEVOS);
	}
	/**
	 *
	 * @return void
	 */
	public void removeRateLineVOs(){
	 removeAttribute(KEY_RATELINEVOS);
	}
    /**
     * @return HashMap
     */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

	     return getAttribute(KEY_STATUS_ONETIME);

	     }
	     /**

	     *  sets onetimes

	     * @param oneTimeVOs

	     */
	     public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

	     setAttribute(KEY_STATUS_ONETIME, oneTimeVOs);

	     }
	     /**

	     *

	     *remove onetimes

	     */

	     public void removeOneTimeVOs() {

	     removeAttribute(KEY_STATUS_ONETIME);

	     }

	/**
	 *
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getStatusOneTime(){
		return (ArrayList<OneTimeVO>) getAttribute(KEY_STATUS_ONETIMEVOS);
	};

	/**
	 * sets StatusOneTime values
	 * @param statusTimeVOs
	 *  
	 */
	public void setStatusOneTime(ArrayList<OneTimeVO> statusTimeVOs){
		 setAttribute( KEY_STATUS_ONETIMEVOS, (ArrayList<OneTimeVO>) statusTimeVOs );
		}

		/**
	 *
	 * @return 
	 */
		public void removeStatusOneTime(){
		 removeAttribute(KEY_STATUS_ONETIMEVOS);
	}
     /**
      * @return Page<RateLineVO>
      */
		public Page<RateLineVO> getSelectedRateLineVOs() {

			return getAttribute( KEY_SELECTED_RATELINE );
		}
		/**
		 * sets RateLineVOs
		 * @param rateLineVOs
		 * @return
		 * 
		 */

		public void setSelectedRateLineVOs(Page<RateLineVO> rateLineVOs) {
			setAttribute(KEY_SELECTED_RATELINE, rateLineVOs);

		}
        /**
         * @return void
         */
		public void removeSelectedRateLineVOs() {
			removeAttribute(KEY_SELECTED_RATELINE);

		}

		/**
		 * sets rateLineFilterVO
		 * @param  rateLineFilterVO
		 * @return
		 * 
		 */
		public void setRateLineFilterVO(RateLineFilterVO rateLineFilterVO) {
			setAttribute(KEY_FILTER, rateLineFilterVO);

		}

		/**
		 * gets rateLineFilterVO
		 *
		 * @return rateLineFilterVO
		 */
		public RateLineFilterVO getRateLineFilterVO() {
			return getAttribute( KEY_FILTER );
		}

		/**
		 * removes rateLineFilterVO
		 * @return
		 */
		public void removeRateLineFilterVO() {
			removeAttribute(KEY_FILTER);

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




}
