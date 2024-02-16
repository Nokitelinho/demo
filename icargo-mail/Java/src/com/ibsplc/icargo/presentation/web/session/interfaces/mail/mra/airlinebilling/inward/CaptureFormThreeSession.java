/*
 * CaptureFormThreeSession.java Created on july 25, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
/**
 * 
 * @author A-3108
 *
 */

public interface CaptureFormThreeSession extends ScreenSession{
	
	
	
	/**
	 * @return
	 */
	public Collection<AirlineForBillingVO> getAirlineForBillingVOs();
	/**
	 * 
	 * @param exceptionInInvoiceVOs
	 */
	public void setAirlineForBillingVOs(ArrayList<AirlineForBillingVO> airlineForBillingVOs);
	/**
	 * remove
	 */
	public void removeAirlineForBillingVOs();
	/**
	 * @return Returns the oneTimeValues.
	 */
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	 /**
	  * @author A-3447 for integrating with form 1 starts 
	  */
	
	
	/**
	 * @return AirlineCN51FilterVO
	 */
	public InterlineFilterVO getInterlineFilterVO();
	
	/**
	 * 
	 * @param InterlineFilterVO
	 */
	public void setInterlineFilterVO(InterlineFilterVO interlineFilterVO);
	
	/**
	 * Remove Filter Vo
	 */
	
	 public void removeInterlineFilterVO();
	 
	 /**
	  * @author A-3447 for integrating with form 1 ends 
	  */
	 
	 public String getAirlineNumbericCode();
		
		/**
		 * 
		 * @param InterlineFilterVO
		 */
	public void setAirlineNumbericCode(String airlineNumbericCode);
		
		/**
		 * Remove Filter Vo
		 */
		
	public void removeAirlineNumbericCode();
		
	 
	 
	 
}
