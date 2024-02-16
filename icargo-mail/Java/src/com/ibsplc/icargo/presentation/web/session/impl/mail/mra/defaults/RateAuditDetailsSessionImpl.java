/*
 * RateAuditDetailsSessionImpl.java Created on jul 14,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;

/**
 * @author A-2391
 *
 */
public class RateAuditDetailsSessionImpl extends AbstractScreenSession implements RateAuditDetailsSession {


	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/*
	 * The screen id
	 */
	private static final String SCREEN_ID =
											"mailtracking.mra.defaults.rateauditdetails";
	private static final String KEY_ONETIME_VOS="onetimevalues";
	private static final String KEY_RATEAUDITDTLS="gpavos";
	private static final String KEY_RATEAUDITFILTER="filtervos";
	private static final String KEY_PARCHANGEFLAG="parchangeflag";
	private static final String KEY_ONETIME_CATVOS="mailcatvos";
    private static final  String KEY_STATUS_INFO = "statusinfo";
    private static final String KEY_BILLTOCHGFLAG="billtochgflag";
	private static final  String KEY_VOLUMEROUNDINGVO = "volumeRounding";
	private static final  String KEY_WEIGHTROUNDINGVO = "weightRounding";
	
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
    public RateAuditDetailsSessionImpl() {
        super();
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
	    /**
		 *
		 * @param rateAuditVO
		 */
		public void setRateAuditVO(RateAuditVO rateAuditVO) {
			setAttribute(KEY_RATEAUDITDTLS,rateAuditVO);

		}

		/**
		 *
		 * @return
		 */
		public RateAuditVO getRateAuditVO() {
			// TODO Auto-generated method stub
			return (RateAuditVO)getAttribute(KEY_RATEAUDITDTLS);
		}

		/**
		 *
		 */
		public void removeRateAuditVO() {
			removeAttribute(KEY_RATEAUDITDTLS);

		}
		
		/**
		 *
		 * @param rateAuditVO
		 */
		public void setParChangeFlag(String parChangeFlag) {
			setAttribute(KEY_PARCHANGEFLAG,parChangeFlag);

		}

		/**
		 *
		 * @return
		 */
		public String getParChangeFlag() {
			// TODO Auto-generated method stub
			return getAttribute(KEY_PARCHANGEFLAG);
		}

		/**
		 *
		 */
		public void removeParChangeFlag() {
			removeAttribute(KEY_PARCHANGEFLAG);

		}
		

		 /**

	    *

	    * @return

	    */

	    public Collection<OneTimeVO> getOneTimeCatVOs(){

	    return (ArrayList<OneTimeVO>)getAttribute(KEY_ONETIME_CATVOS);

	    }
	    /**
	    * @param catVOs
	    */

	    public void setOneTimeCatVOs(Collection<OneTimeVO> catVOs){

	    setAttribute(KEY_ONETIME_CATVOS,(ArrayList<OneTimeVO>) catVOs);

	    }
	    /**

	    *

	    *remove onetimes

	    */

	    public void removeOneTimeCatVOs() {

	    removeAttribute(KEY_ONETIME_CATVOS);

	    }
	    
		/**
		 *
		 * @param statusinfo
		 */
		public void setStatusinfo(String statusinfo) {
			setAttribute(KEY_STATUS_INFO,statusinfo);

		}

		/**
		 *
		 * @return
		 */
		public String getStatusinfo() {
			// TODO Auto-generated method stub
			return getAttribute(KEY_STATUS_INFO);
		}

		/**
		 *
		 */
		public void removeStatusinfo() {
			removeAttribute(KEY_STATUS_INFO);

		}
	    
		/**
		 *
		 * @param rateAuditVO
		 */
		public void setRateAuditFilterVO(RateAuditFilterVO rateAuditFilterVO) {
			setAttribute(KEY_RATEAUDITFILTER,rateAuditFilterVO);

		}

		/**
		 *
		 * @return
		 */
		public RateAuditFilterVO getRateAuditFilterVO() {
			// TODO Auto-generated method stub
			return (RateAuditFilterVO)getAttribute(KEY_RATEAUDITFILTER);
		}

		/**
		 *
		 */
		public void removeRateAuditFilterVO() {
			removeAttribute(KEY_RATEAUDITFILTER);

		}
		
		/**
		 *
		 * @param rateAuditVO
		 */
		public void setBillToChgFlag(String billToChgFlag) {
			setAttribute(KEY_BILLTOCHGFLAG,billToChgFlag);

		}

		/**
		 *
		 * @return
		 */
		public String getBillToChgFlag() {
			// TODO Auto-generated method stub
			return getAttribute(KEY_BILLTOCHGFLAG);
		}

		/**
		 *
		 */
		public void removeBillToChgFlag() {
			removeAttribute(KEY_BILLTOCHGFLAG);

		}
		
		 public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO) {
		    	setAttribute(KEY_VOLUMEROUNDINGVO, unitRoundingVO);
		    }

		    public UnitRoundingVO getVolumeRoundingVO() {
		    	return getAttribute(KEY_VOLUMEROUNDINGVO);
		    }

		    public void removeVolumeRoundingVO() {   
		    	removeAttribute(KEY_VOLUMEROUNDINGVO);
		    }  
		    
		    
		    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO) {
		    	setAttribute(KEY_WEIGHTROUNDINGVO, unitRoundingVO);
		    }

		    public UnitRoundingVO getWeightRoundingVO() {
		    	return getAttribute(KEY_WEIGHTROUNDINGVO);
		    }

		    public void removeWeightRoundingVO() {
		    	removeAttribute(KEY_WEIGHTROUNDINGVO);
		    	
		    } 
		
		

}
