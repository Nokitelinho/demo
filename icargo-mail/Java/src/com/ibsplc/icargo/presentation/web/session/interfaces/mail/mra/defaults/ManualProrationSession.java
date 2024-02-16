/*
 * ManualProrationSession.java Created on Aug 7,2008
*
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This software is the proprietary information of IBS Software Services(P) Ltd.
* Use is subject to license terms.
*/
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;


/**
 * @author A-3229
* Session interface for ManualProrationSession Screen.
* 
* Revision History
* 
* Version      Date           Author          		    Description
* 
*  0.1		Aug 7, 2008       A-3229			     Initial draft
*/
public interface ManualProrationSession extends ScreenSession {
	
	
	 /**
    * 
    * @return prorationFilterVO
    */
   
	public ProrationFilterVO getProrationFilterVO();
	
	/**
	 * @param prorationFilterVO The  prorationFilterVO to set.
	 */
	public void setProrationFilterVO(ProrationFilterVO prorationFilterVO);
	
	/**
	 * @author A-3229
	 */	
	
	public void removeProrationFilterVO();
	
	 /**
	    * 
	    * @return prorationDetailsVO
	    */
	   
		public ProrationDetailsVO getProrationDetailsVO();
		
		/**
		 * @param prorationDetailsVO The  prorationDetailsVO to set.
		 */
		public void setProrationDetailsVO(ProrationDetailsVO prorationDetailsVO);
		
		/**
		 * @author A-3229
		 */	
		
		public void removeProrationDetailsVO();
		
	
	/**
	 * @param prorationVOs 
	 */
	public void setProrationDetailVOs(Collection<ProrationDetailsVO> prorationDetailVOs);
	
	
	/**
	 * @return ArrayList<ProrationDetailsVO>
	 */
	public Collection<ProrationDetailsVO> getProrationDetailVOs();
	
	/**
	 * @author A-3229
	 *
	 */
	public void removeProrationDetailVOs();
		
	
	 /**
     *  Method used to get baseCurrency
     * @return
     */
    public String getBaseCurrency();

    /**
     * Method used to set baseCurrency
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.cahsiering.defaults.CashDrawSession#setCashDraw(com.ibsplc.icargo.business.cashiering.defaults.cashdraw.vo.CashDrawVO)
     * @param baseCurrency
     */
    public void setBaseCurrency(String baseCurrency);

    /**
     * Method used to remove baseCurrency
     * @return
     */
    public void removeBaseCurrency();
    
    public void setDispatchFilterVO(
    		DSNPopUpVO dsnPopUpVO);
    /**
     * The getter method for UnaccountedDispatchesFilterVO
     * @return UnaccountedDispatchesFilterVO
     */
    public DSNPopUpVO getDispatchFilterVO();
    
    /**
	 * @param primaryProrationVOs 
	 */
	public void setPrimaryProrationVOs(Collection<ProrationDetailsVO> primaryProrationVOs);
	
	
	/**
	 * @return Collection<ProrationDetailsVO>
	 */
	public Collection<ProrationDetailsVO> getPrimaryProrationVOs();
	
	/**
	 * method to remove primaryProrationVOs
	 *
	 */
	public void removePrimaryProrationVOs();
		
	
	/**
	 * @param secondaryProrationVOs 
	 */
	public void setSecondaryProrationVOs(Collection<ProrationDetailsVO> secondaryProrationVOs);
	
	
	/**
	 * @return Collection<ProrationDetailsVO>
	 */
	public Collection<ProrationDetailsVO> getSecondaryProrationVOs();
	
	/**
	 * method to remove secondaryProrationVOs
	 *
	 */
	public void removeSecondaryProrationVOs();
	
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 * removes removeOneTimeVOs
	 */
	public void removeOneTimeVOs();
	
	/**
     * @param WeightRoundingVO WeightRoundingVO
     */
    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO);
    /**
     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
     */
    public UnitRoundingVO getWeightRoundingVO() ;
    /**
     * @param key
     */
    public void removeWeightRoundingVO(String key) ;

}


