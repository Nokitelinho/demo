
	/*
	 * GenerateOutwardBillingInvoiceSessionImpl.java Created on Aug 27, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.outward;

	import java.util.ArrayList;

	
	import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
	import com.ibsplc.icargo.framework.session.AbstractScreenSession;
	import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.GenerateOutwardBillingInvoiceSession;
	

	/**
	 * @author A-3229
	 *
	 */
	public class GenerateOutwardBillingInvoiceSessionImpl extends AbstractScreenSession
			implements GenerateOutwardBillingInvoiceSession {

		private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

		private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.outward.generateinvoice";

		
		/*
		 * Key for clearingHouses
		 */
		private static final String CLEARINGHOUSES= 
			"clearinghouses";

		
		
		/**
		 * 
		 * @return
		 */
		@Override
		public String getModuleName() {
			return MODULE_NAME;
		}
		
		/**
		 * 
		 * @return
		 */
		@Override
		public String getScreenID() {
			return SCREEN_ID;
		}
		
	   	   
	 
	    /**
	     * Used to get clearingHouses
	     * @return ArrayList<OneTimeVO>
	     */
	    public ArrayList<OneTimeVO>  getClearingHouses(){
	        return getAttribute(CLEARINGHOUSES);
	    }
	   
	    /**
	     * @param clearingHouses
	     * @return 
	     */
	    public  void setClearingHouses(ArrayList<OneTimeVO> clearingHouses ){
		   	setAttribute(CLEARINGHOUSES, clearingHouses);
	    }
	   
	    /**
	    * Removes the  clearingHouses
	    */
	    public  void removeClearingHouses(){
		   	removeAttribute(CLEARINGHOUSES);
	    }

	  


}
