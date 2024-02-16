
	/*
	 * ProrationLogSessionImpl.java Created on Sep 18, 2008 
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services(P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;


	import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailProrationLogVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProrationLogSession;


	/**
	 * @author A-3229
	 * Session implementation for Mail Proration screen
	 * 
	 * Revision History     
	 * 
	 * Version      Date           Author          		    Description
	 * 
	 *  0.1		Sep 18, 2008    	   A-3229			      Initial draft
	 */
	public class ProrationLogSessionImpl extends AbstractScreenSession implements  ProrationLogSession{

		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.prorationlog";
		
		/**
		 * Constant for the session variable dsnFilterVO
		 */
		private static final String KEY_LISTFILTER = "dsnFilterVO";
		/**
		 * Constant for the session variable mailProrationLogVOs
		 */
		private static final String KEY_LISTDETAIL = "mailProrationLogVOs";
		/**
		 * Constant for the session variable triggerPoint
		 */
		private static final String KEY_TRIGGER="triggerPoints";
		
		/**
		 *Constant for the session variable selectedDespatch
		 *
		 */
		private static final String DESPATCH_SEL = "despatch_sel";
		
		private static final String KEY_LOGDETAILS = "logdetails";
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
	     * @return DSNFilterVO
	     */
	    
		public DSNFilterVO getDSNFilterVO() {
			return getAttribute(KEY_LISTFILTER);
		}
		
		/**
		 * @param DSNFilterVO The  DSNFilterVO to set.
		 */
		public void setDSNFilterVO(DSNFilterVO dsnFilterVO) {
			setAttribute(KEY_LISTFILTER,dsnFilterVO);
		}
		
		/**
		 * method to remove DSNFilterVO from session
		 */	
		
		public void removeDSNFilterVO() {
			removeAttribute(KEY_LISTFILTER);
		}
	   
		/**
		 * @param mailProrationLogVOs The mailProrationLogVOs to set
		 */
		public void setMailProrationLogVOs(Collection<MailProrationLogVO> dsnRoutingVOs){
			setAttribute(KEY_LISTDETAIL,(ArrayList<MailProrationLogVO>)dsnRoutingVOs);
		}
		/**
		 * @return ArrayList<MailProrationLogVO>
		 */
		public Collection<MailProrationLogVO> getMailProrationLogVOs(){
			return (Collection<MailProrationLogVO>)getAttribute(KEY_LISTDETAIL);
		}
		
		/**
		 * method to remove the mailProrationLogVOs from session
		 * 
		 */
		public void removeMailProrationLogVOs(){
			
			removeAttribute(KEY_LISTDETAIL);
			
		}
		   /**
	     * @return ArrayList<OneTimeVO>
	     */
	    public ArrayList<OneTimeVO>  getTriggerPoints(){
	        return getAttribute(KEY_TRIGGER);
	    }
	   
	    /**
	     * @param triggerPoints
	     * @return 
	     */
	    public  void setTriggerPoints(ArrayList<OneTimeVO> triggerPoints ){
		   	setAttribute(KEY_TRIGGER, triggerPoints);
	    }
	   
	    /**
	    * Removes the  triggerPoints
	    */
	    public  void removeTriggerPoints(){
		   	removeAttribute(KEY_TRIGGER);
	    }
	
	    /**
		 * 
		 * @return mailProrationLogVO
		 */
		public MailProrationLogVO getSelectedDespatchDetails() {
			return (MailProrationLogVO) getAttribute(DESPATCH_SEL);
		}

		/**
		 * 
		 * @param MailProrationLogVO the MailProrationLogVO to set
		 */
		public void setSelectedDespatchDetails(MailProrationLogVO mailProrationLogVO) {
			setAttribute(DESPATCH_SEL, (MailProrationLogVO) mailProrationLogVO);
		}

		/**
		 * 
		 * to remove despatchDetails from session
		 */
		public void removeSelectedDespatchDetails() {
			removeAttribute(DESPATCH_SEL);
		}
		
		 /**
		 * Method to return HashMap<String, Collection<CN51DetailsVO>>
		 * @return HashMap<String, Collection<CN51DetailsVO>>
		 */
		public HashMap<String, Collection<MailProrationLogVO>> getMailProrationLogVOMap(){
			return (HashMap<String, Collection<MailProrationLogVO>>)getAttribute(KEY_LOGDETAILS);
		}
		/**
		 * Method to set HashMap<String, Collection<CN51DetailsVO>>
		 * @param cn51DetailsVOs
		 */
		public void setMailProrationLogVOMap(HashMap<String,
				Collection<MailProrationLogVO>> mailProrationLogVOs){
			setAttribute(KEY_LOGDETAILS, mailProrationLogVOs);
		}
		/**
		 * Method to remove HashMap<String, Collection<AccountingEntryDetailsVO>>
		 */
		public void removeMailProrationLogVOMap(){
			removeAttribute(KEY_LOGDETAILS);
		}


}


