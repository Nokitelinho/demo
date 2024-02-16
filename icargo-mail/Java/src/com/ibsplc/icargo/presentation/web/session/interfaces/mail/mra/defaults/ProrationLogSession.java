	
	/*
	 * ProrationLogSession.java Created on Sep 18,2008
	*
	* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	*
	* This software is the proprietary information of IBS Software Services(P) Ltd.
	* Use is subject to license terms.
	*/
	package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

	import java.util.ArrayList;
	import java.util.Collection;
import java.util.HashMap;

	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNFilterVO;
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailProrationLogVO;
	import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
	
import com.ibsplc.icargo.framework.session.ScreenSession;


	/**
	 * @author A-3229
	 *
	 */
	
	public interface ProrationLogSession extends ScreenSession {
		
		
		 /**
	    * 
	    * @return dsnFilterVO
	    */
	   
		public DSNFilterVO getDSNFilterVO();
		
		/**
		 * @param dsnFilterVO The  dsnFilterVO to set.
		 */
		public void setDSNFilterVO(DSNFilterVO dsnFilterVO);
		
		/**
		 * method to remove dsnFilterVO
		 */	
		
		public void removeDSNFilterVO();
		
	
		
		/**
		 * @param mailProrationLogVO 
		 */
		public void setMailProrationLogVOs(Collection<MailProrationLogVO> mailProrationLogVO);
		
		
		/**
		 * @return ArrayList<MailProrationLogVO>
		 */
		public Collection<MailProrationLogVO> getMailProrationLogVOs();
		
		/**
		 * method to remove mailProrationLogVO
		 *
		 */
		public void removeMailProrationLogVOs();
			
		
		 /**
	     * @return ArrayList<OneTimeVO>
	     */
	    public ArrayList<OneTimeVO>  getTriggerPoints();
	   
	    /**
	     * @param triggerPoints
	     * @return 
	     */
	    public  void setTriggerPoints(ArrayList<OneTimeVO> triggerPoints );
	   
	    /**
	    * Removes the  triggerPoints
	    */
	    public  void removeTriggerPoints();
	    
	    /**
		 * 
		 * @return MailProrationLogVO
		 */
		public MailProrationLogVO getSelectedDespatchDetails();

		/**
		 * 
		 * @param mailProrationLogVO
		 */
		public void setSelectedDespatchDetails(MailProrationLogVO mailProrationLogVO);

		/**
		 * method to remove selectedDespatchDetails from session
		 * 
		 */
		public void removeSelectedDespatchDetails();
		 /**
		 * Method to return HashMap<String, Collection<CN51DetailsVO>>
		 * @return HashMap<String, Collection<CN51DetailsVO>>
		 */
		public HashMap<String, Collection<MailProrationLogVO>> getMailProrationLogVOMap();
		
		/**
		 * Method to set HashMap<String, Collection<CN51DetailsVO>>
		 * @param cn51DetailsVOs
		 */
		public void setMailProrationLogVOMap(HashMap<String,
				Collection<MailProrationLogVO>> mailProrationLogVOs);
		/**
		 * Method to remove HashMap<String, Collection<AccountingEntryDetailsVO>>
		 */
		public void removeMailProrationLogVOMap();
		


}

