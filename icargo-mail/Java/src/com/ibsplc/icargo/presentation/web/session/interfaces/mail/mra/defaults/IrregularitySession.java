
	/*
	 * IrregularitySession.java Created on Sep 26, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

	import java.util.ArrayList;
import java.util.Collection;

	import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;


	/**
	 * @author A-3229
	 *
	 */
	public interface IrregularitySession extends
			com.ibsplc.icargo.framework.session.ScreenSession {
		

	    
	    /**
	     * Used to get irpStatus
	     * @return ArrayList<OneTimeVO>
	     */
	    public ArrayList<OneTimeVO>  getIrpStatus();
	   
	    /**
	     * @param irpStatus
	     * @return 
	     */
	    public  void setIrpStatus(ArrayList<OneTimeVO> irpStatus );
	   
	    /**
	    * Removes the irpStatus
	    */
	    public  void removeIrpStatus();
	    

		 /**
	    * 
	    * @return irregularityFilterVO
	    */
	   
		public MRAIrregularityFilterVO getIrregularityFilterVO();
		
		/**
		 * @param irregularityFilterVO The irregularityFilterVO to set.
		 */
		public void setIrregularityFilterVO(MRAIrregularityFilterVO irregularityFilterVO);
		
		/**
		 * method to remove irregularityFilterVO
		 */	
		
		public void removeIrregularityFilterVO();
		

	    /**
	     * 
	     * @return irregularityVO 
	     */
		public Collection<MRAIrregularityVO> getIrregularityVOs();
		
		/**
		 * @param irregularityVO The irregularityVO to set
		 */
		public void setIrregularityVOs(Collection<MRAIrregularityVO> irregularityVOs);
		/**
		 * method to remove irregularityVO from session
		 */
		public void removeIrregularityVOs();
} 
