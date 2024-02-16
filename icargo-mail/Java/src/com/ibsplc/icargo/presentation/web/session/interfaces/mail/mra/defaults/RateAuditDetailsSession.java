/*
 * RateAuditDetailsSession.java.java Created on jul 14,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
/**
 * @author A-2391
 *
 */
public interface RateAuditDetailsSession extends ScreenSession {
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();
	/**
	 * 
	 * @param rateLineVOs
	 */
	public void setRateAuditVO(RateAuditVO rateAuditVO);
	/**
	 * 
	 * @return
	 */
	public RateAuditVO getRateAuditVO() ;
	/**
	 * 
	 *
	 */
	public void removeRateAuditVO();
	
	/**
	 * 
	 * @param parChangeFlag
	 */
	public void setParChangeFlag(String parChangeFlag) ;
	
	/**
	 * 
	 * @return
	 */
	public String getParChangeFlag();
	/**
	 * 
	 *
	 */
	public void removeParChangeFlag();
	
	
	 /**
	  * @return
    */

    public Collection<OneTimeVO> getOneTimeCatVOs();
    
    	/**
	    * @param catVOs
	    */

	    public void setOneTimeCatVOs(Collection<OneTimeVO> catVOs);
	    
	    /**
	    *
	    *remove onetimecatVOs
	    */

	    public void removeOneTimeCatVOs();
	    
	    
	    
	    
		/**
		 *
		 * @param statusinfo
		 */
		public void setStatusinfo(String statusinfo);

		/**
		 *
		 * @return
		 */
		public String getStatusinfo();
		/**
		 *
		 */
		public void removeStatusinfo(); 	    
		public void setRateAuditFilterVO(RateAuditFilterVO rateAuditFilterVO);
		/**
		 * 
		 * @return RateAuditFilterVO
		 */
		public RateAuditFilterVO getRateAuditFilterVO() ;
		/**
		 * 
		 *
		 */
		public void removeRateAuditFilterVO();
		
		
		/**
		 *
		 * @param billToChgFlag
		 */
		public void setBillToChgFlag(String billToChgFlag);

		/**
		 *
		 * @return
		 */
		public String getBillToChgFlag();

		/**
		 *
		 */
		public void removeBillToChgFlag();

		 public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO);
		    
	    public UnitRoundingVO getVolumeRoundingVO();
	    
	    public void removeVolumeRoundingVO();
	    
	    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO);
	    
	    public UnitRoundingVO getWeightRoundingVO();
	    
	    public void removeWeightRoundingVO();
		
		
		

}
