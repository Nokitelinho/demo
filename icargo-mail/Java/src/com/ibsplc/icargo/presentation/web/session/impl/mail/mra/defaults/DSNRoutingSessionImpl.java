
	/*
	 * DSNRoutingSessionImpl.java Created on Sep 2, 2008 
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services(P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;


	import java.util.ArrayList;
	import java.util.Collection;

	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingFilterVO;
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
	
	import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;


	/**
	 * @author A-3229
	 * Session implementation for Mail Proration screen
	 * 
	 * Revision History     
	 * 
	 * Version      Date           Author          		    Description
	 * 
	 *  0.1		Sep 2, 2008    	   A-3229			      Initial draft
	 */
	public class DSNRoutingSessionImpl extends AbstractScreenSession implements  DSNRoutingSession{

		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.despatchrouting";
		
		/**
		 * Constant for the session variable despatchFilterVO
		 */
		private static final String KEY_LISTFILTER = "dsnRoutingFilterVO";
		/**
		 * Constant for the session variable despatchRoutingVO
		 */
		private static final String KEY_LISTDETAIL = "dsnRoutingVOs";
		private static final String KEY_WEIGHTROUNDINGVO = "rounding_wt_vo";
		private static final String KEY_AGRMNTTYP = "agreementtype";
		private static final String KEY_BLKSPCTYP = "blockSpaceType";
		private static final String KEY_MAILSOURCE = "mailSources";
		
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
	     * @return DSNRoutingFilterVO
	     */
	    
		public DSNRoutingFilterVO getDSNRoutingFilterVO() {
			return getAttribute(KEY_LISTFILTER);
		}
		
		/**
		 * @param DSNRoutingFilterVO The  DSNRoutingFilterVO to set.
		 */
		public void setDSNRoutingFilterVO(DSNRoutingFilterVO dsnRoutingFilterVO) {
			setAttribute(KEY_LISTFILTER,dsnRoutingFilterVO);
		}
		
		/**
		 * @author A-3229
		 */	
		
		public void removeDSNRoutingFilterVO() {
			removeAttribute(KEY_LISTFILTER);
		}
	   
		/**
		 * @param dsnRoutingVOs 
		 */
		public void setDSNRoutingVOs(Collection<DSNRoutingVO> dsnRoutingVOs){
			setAttribute(KEY_LISTDETAIL,(ArrayList<DSNRoutingVO>)dsnRoutingVOs);
		}
		/**
		 * @return ArrayList<DSNRoutingVO>
		 */
		public Collection<DSNRoutingVO> getDSNRoutingVOs(){
			return (Collection<DSNRoutingVO>)getAttribute(KEY_LISTDETAIL);
		}
		
		/**
		 * @author A-3229
		 *
		 */
		public void removeDSNRoutingVOs(){
			
			removeAttribute(KEY_LISTDETAIL);
			
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
		public void setAgreementTypes(ArrayList<OneTimeVO> agreementTypes) {
			setAttribute(KEY_AGRMNTTYP, agreementTypes);
		}
		
		public void removeAgreementTypes() {
			removeAttribute(KEY_AGRMNTTYP);
		}
		public ArrayList<OneTimeVO> getAgreementTypes() {
			return (ArrayList<OneTimeVO>)getAttribute(KEY_AGRMNTTYP);
		}   
	

		public void setBlockSpaceTypes(ArrayList<OneTimeVO> blockSpaceTypes) {
			setAttribute(KEY_BLKSPCTYP, blockSpaceTypes);
		}
		
		public void removeBlockSpaceTypes() {
			removeAttribute(KEY_BLKSPCTYP);
		}
		public ArrayList<OneTimeVO> getBlockSpaceTypes() {
			return (ArrayList<OneTimeVO>)getAttribute(KEY_BLKSPCTYP);
		}

		public ArrayList<OneTimeVO> getMailSources() {
			return (ArrayList<OneTimeVO>)getAttribute(KEY_MAILSOURCE);
		}

		public void setMailSources(ArrayList<OneTimeVO> mailSources) {
			setAttribute(KEY_MAILSOURCE, mailSources);
		}   


}



