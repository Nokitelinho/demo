
	
	/*
	 * DSNRoutingSession.java Created on Sep 2,2008
	*
	* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	*
	* This software is the proprietary information of IBS Software Services(P) Ltd.
	* Use is subject to license terms.
	*/
	package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

	import java.util.ArrayList;
	import java.util.Collection;

	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingFilterVO;
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
	
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;


	/**
	 * @author A-3229
	 *
	 */
	
	public interface DSNRoutingSession extends ScreenSession {
		
		
		 /**
	    * 
	    * @return dsnRoutingFilterVO
	    */
	   
		public DSNRoutingFilterVO getDSNRoutingFilterVO();
		
		/**
		 * @param dsnRoutingFilterVO The  dsnRoutingFilterVO to set.
		 */
		public void setDSNRoutingFilterVO(DSNRoutingFilterVO dsnRoutingFilterVO);
		
		/**
		 * @author A-3229
		 */	
		
		public void removeDSNRoutingFilterVO();
		
	
		
		/**
		 * @param dsnRoutingVO 
		 */
		public void setDSNRoutingVOs(Collection<DSNRoutingVO> DSNRoutingVO);
		
		
		/**
		 * @return ArrayList<dsnRoutingVO>
		 */
		public Collection<DSNRoutingVO> getDSNRoutingVOs();
		
		/**
		 * @author A-3229
		 *
		 */
		public void removeDSNRoutingVOs();
			
		
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
	    public void setAgreementTypes(ArrayList<OneTimeVO> agreementTypes);
	    public ArrayList<OneTimeVO> getAgreementTypes();
	    public ArrayList<OneTimeVO> getBlockSpaceTypes();
	    public void setBlockSpaceTypes(ArrayList<OneTimeVO> blockSpaceTypes);
	    public ArrayList<OneTimeVO> getMailSources();
	    public void setMailSources(ArrayList<OneTimeVO> mailSources);
	    public void removeAgreementTypes();
	    public void removeBlockSpaceTypes();



}
