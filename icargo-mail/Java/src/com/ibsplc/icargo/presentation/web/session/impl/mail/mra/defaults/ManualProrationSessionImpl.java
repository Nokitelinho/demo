
	/*
	 * ManualProrationSessionImpl.java Created on Aug 8, 2008 
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services(P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;


	import java.util.ArrayList;
	import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
	import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;


	/**
	 * @author A-3229
	 * Session implementation for Manual Proration screen
	 * 
	 * Revision History     
	 * 
	 * Version      Date           Author          		    Description
	 * 
	 *  0.1		Aug 8, 2008    	   A-3229			      Initial draft
	 */
	public class ManualProrationSessionImpl extends AbstractScreenSession implements  ManualProrationSession{

		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.manualproration";
		
		/**
		 * Constant for the session variable prorationFilterVO
		 */
		private static final String KEY_LISTFILTER = "prorationFilterVO";
		/**
		 * Constant for the session variable prorationDetailsVO
		 */
		private static final String KEY_LISTDETAIL = "prorationDetailsVO";
		
		/**
		 * Constant for the session variable prorationVOs
		 */
		private static final String KEY_PRORATIONVOS = "prorationDetailVOs";
		/**
		 * Constant for the session variable baseCurrency
		 */
		private static final String KEY_BASECURRENCY="baseCurrency";
		
	    private static final String KEY_DISPACTCHESENQFILTERVO = "dispatchesEnqFilterVO";
		
	    /**
		 * Constant for the session variable primaryProrationVOs
		 */
		private static final String KEY_PRIMARYPRORATIONVOS = "primaryProrationVOs";
		
		/**
		 * Constant for the session variable secondaryProrationVOs
		 */
		private static final String KEY_SECONDARYPRORATIONVOS = "secondaryProrationVOs";
		private static final String KEY_ONETIME_VOS = "onetimevalues";
		private static final String KEY_WEIGHTROUNDINGVO = "rounding_wt_vo";
		
		/*
	     * (non-Javadoc)
	     *
	     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
	     */
		/**
		 * @return screenID 
		 */
	    public String getScreenID() {

	        return SCREEN_ID;
	    }

	    /**
	     * @return moduleName
	     */
	    /*
	     * (non-Javadoc)
	     *
	     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
	     */
	    public String getModuleName() {
	        return MODULE_NAME;
	    }
	    
	    
	    /**
	     * 
	     * @return prorationDetailsVO
	     */
	    
		public ProrationDetailsVO getProrationDetailsVO() {
			return getAttribute(KEY_LISTDETAIL);
		}
		/**
		 * @param prorationDetailsVO The  prorationDetailsVO to set.
		 */
		public void setProrationDetailsVO(ProrationDetailsVO prorationDetailsVO) {
			setAttribute(KEY_LISTDETAIL,prorationDetailsVO);
		}
		
		
		/**
		 * @author A-3229
		 */	
		
		public void removeProrationDetailsVO() {
			// TODO Auto-generated method stub
			removeAttribute(KEY_LISTDETAIL);
		}
	    
	    /**
	     * 
	     * @return prorationFilterVO
	     */
	    
		public ProrationFilterVO getProrationFilterVO() {
			return getAttribute(KEY_LISTFILTER);
		}
		/**
		 * @param prorationFilterVO The  prorationFilterVO to set.
		 */
		public void setProrationFilterVO(ProrationFilterVO prorationFilterVO) {
			setAttribute(KEY_LISTFILTER,prorationFilterVO);
		}
		
		/**
		 * @author A-3229
		 */	
		
		public void removeProrationFilterVO() {
			// TODO Auto-generated method stub
			removeAttribute(KEY_LISTFILTER);
		}
	   
		/**
		 * @param prorationVOs 
		 */
		public void setProrationDetailVOs(Collection<ProrationDetailsVO> prorationDetailVOs){
			setAttribute(KEY_PRORATIONVOS,(ArrayList<ProrationDetailsVO>)prorationDetailVOs);
		}
		/**
		 * @return ArrayList<ProrationDetailsVO>
		 */
		public Collection<ProrationDetailsVO> getProrationDetailVOs(){
			return (Collection<ProrationDetailsVO>)getAttribute(KEY_PRORATIONVOS);
		}
		
		/**
		 * @author A-3229
		 *
		 */
		public void removeProrationDetailVOs(){
			
			removeAttribute(KEY_PRORATIONVOS);
			
		}
		
		 /**
	     *  Method used to get baseCurrency
	     * @return
	     */
	    public String getBaseCurrency() {
	    	return (String)getAttribute(KEY_BASECURRENCY);
	    }

	    /**
	     * Method used to set baseCurrency
	     * @see com.ibsplc.icargo.presentation.web.session.interfaces.cahsiering.defaults.CashDrawSession#setCashDraw(com.ibsplc.icargo.business.cashiering.defaults.cashdraw.vo.CashDrawVO)
	     * @param baseCurrency
	     */
	    public void setBaseCurrency(String baseCurrency) {
	    	setAttribute(KEY_BASECURRENCY,baseCurrency);
	    }

	    /**
	     * Method used to remove baseCurrency
	     * @return
	     */
	    public void removeBaseCurrency() {
	    	removeAttribute(KEY_BASECURRENCY);    	
	    }
	    /**
		 * The setter method for SearchContainerFilterVO
		 * @param unaccountedDispatchesFilterVO
		 */
	    public void setDispatchFilterVO(
	    		DSNPopUpVO dSNPopUpVO) {
	    	setAttribute(KEY_DISPACTCHESENQFILTERVO,dSNPopUpVO);
	    }
	    /**
	     * The getter method for searchContainerFilterVO
	     * @return searchContainerFilterVO
	     */
	    public DSNPopUpVO getDispatchFilterVO() {
	    	return getAttribute(KEY_DISPACTCHESENQFILTERVO);
	    }
	    /**
		 * @param primaryProrationVOs 
		 */
		public void setPrimaryProrationVOs(Collection<ProrationDetailsVO> primaryProrationVOs){
			setAttribute(KEY_PRIMARYPRORATIONVOS,(ArrayList<ProrationDetailsVO>)primaryProrationVOs);
		}
		/**
		 * @return Collection<ProrationDetailsVO>
		 */
		public Collection<ProrationDetailsVO> getPrimaryProrationVOs(){
			return (Collection<ProrationDetailsVO>)getAttribute(KEY_PRIMARYPRORATIONVOS);
		}
		
		/**
		 * method to remove primaryProrationVOs
		 *
		 */
		public void removePrimaryProrationVOs(){
			
			removeAttribute(KEY_PRIMARYPRORATIONVOS);
			
		}
		
		/**
		 * @param secondaryProrationVOs 
		 */
		public void setSecondaryProrationVOs(Collection<ProrationDetailsVO> secondaryProrationVOs){
			setAttribute(KEY_SECONDARYPRORATIONVOS,(ArrayList<ProrationDetailsVO>)secondaryProrationVOs);
		}
		/**
		 * @return Collection<ProrationDetailsVO>
		 */
		public Collection<ProrationDetailsVO> getSecondaryProrationVOs(){
			return (Collection<ProrationDetailsVO>)getAttribute(KEY_SECONDARYPRORATIONVOS);
		}
		
		/**
		 * method to remove secondaryProrationVOs
		 *
		 */
		public void removeSecondaryProrationVOs(){
			
			removeAttribute(KEY_SECONDARYPRORATIONVOS);
			
		}
		
		/**
		 * @author A-3447 for getting one time into the session
		 */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

			return getAttribute(KEY_ONETIME_VOS);

		}

		/**
		 * @param OneTimeVO
		 *            for setting one times
		 */
		public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {

			setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

		}

		/**
		 * for removing one times
		 */
		public void removeOneTimeVOs() {

			removeAttribute(KEY_ONETIME_VOS);

		}
		
		 
	    /**
	     * @param WeightRoundingVO WeightRoundingVO
	     */
	    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO) {
	    	setAttribute(KEY_WEIGHTROUNDINGVO, unitRoundingVO);
	    }

	    /**
	     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
	     */
	    public UnitRoundingVO getWeightRoundingVO() {
	    	return getAttribute(KEY_WEIGHTROUNDINGVO);
	    }

	    /**
	     * @param key String
	     */
	    public void removeWeightRoundingVO(String key) {
	    }    
		

}
