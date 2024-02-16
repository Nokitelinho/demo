
	/*
	 * IrregularitySessionImpl.java Created on Aug 27, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;
	import java.util.ArrayList;
import java.util.Collection;

	
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityVO;
	import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
	import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.IrregularitySession;
	

	/**
	 * @author A-3229
	 *
	 */
	public class IrregularitySessionImpl extends AbstractScreenSession
			implements IrregularitySession {

		private static final String MODULE_NAME = "mailtracking.mra.defaults";

		private static final String SCREEN_ID = "mailtracking.mra.defaults.irregularity";
		
		/**
		 * key for filterVo
		 */
        private static final String KEY_FILTERVO="irregularityfiltervo";
		
		/*
		 * Key for irpStatus
		 */
		private static final String IRPSTATUS= 
			"irpstatus";
		/**
		 * Key for irregularityVO
		 */
		private static final String KEY_DETAILS="irregularityVOs";
		
		
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
	     * Used to get irpStatus
	     * @return ArrayList<OneTimeVO>
	     */
	    public ArrayList<OneTimeVO>  getIrpStatus(){
	        return getAttribute(IRPSTATUS);
	    }
	   
	    /**
	     * @param irpStatus
	     * @return 
	     */
	    public  void setIrpStatus(ArrayList<OneTimeVO> irpStatus ){
		   	setAttribute(IRPSTATUS,irpStatus);
	    }
	   
	    /**
	    * Removes the irpStatus
	    */
	    public  void removeIrpStatus(){
		   	removeAttribute(IRPSTATUS);
	    }

	    /**
	     * Used to get irregularityFilterVO
	     * @return IrregularityFilterVO
	     */
	  
	    public MRAIrregularityFilterVO getIrregularityFilterVO(){
	    	return getAttribute(KEY_FILTERVO);
	    }
	    /**
	     * @param irregularityFilterVO
	     */

	    public void setIrregularityFilterVO(MRAIrregularityFilterVO irregularityFilterVO){
		 setAttribute(KEY_FILTERVO,irregularityFilterVO);
	    }
	    /**
	     * Removes the irregularityFilterVO
	     */
	    public void removeIrregularityFilterVO(){
	    	removeAttribute(KEY_FILTERVO);
	    }
	    /**
	     *  Used to get irregularityVO
	     *  @return MRAIrregularityVO
	     */
	    public Collection<MRAIrregularityVO> getIrregularityVOs(){
	    	return (Collection<MRAIrregularityVO>)getAttribute(KEY_DETAILS);
	    }
	    /**
	     * @param irregularityVOs
	     */
	    public void setIrregularityVOs(Collection<MRAIrregularityVO> irregularityVOs){
	    	setAttribute(KEY_DETAILS,(ArrayList<MRAIrregularityVO>)irregularityVOs);
	    }
	    /**
	     * Removes the irregularityVOs 
	     */
	    public void removeIrregularityVOs(){
	    	removeAttribute(KEY_DETAILS);
	    }
	    
	}