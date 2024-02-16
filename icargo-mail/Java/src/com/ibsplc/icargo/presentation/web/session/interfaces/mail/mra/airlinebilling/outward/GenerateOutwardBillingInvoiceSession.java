
	/*
	 * GenerateOutwardBillingInvoiceSession.java Created on Apr 18, 2006
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward;

	import java.util.ArrayList;

	import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;


	/**
	 * @author A-3229
	 *
	 */
	public interface GenerateOutwardBillingInvoiceSession extends
			com.ibsplc.icargo.framework.session.ScreenSession {
		

	    
	    /**
	     * Used to get clearingHouses
	     * @return ArrayList<OneTimeVO>
	     */
	    public ArrayList<OneTimeVO>  getClearingHouses();
	   
	    /**
	     * @param clearingHouses
	     * @return 
	     */
	    public  void setClearingHouses(ArrayList<OneTimeVO> clearingHouses );
	   
	    /**
	    * Removes the  clearingHouses
	    */
	    public  void removeClearingHouses();

	    

}
