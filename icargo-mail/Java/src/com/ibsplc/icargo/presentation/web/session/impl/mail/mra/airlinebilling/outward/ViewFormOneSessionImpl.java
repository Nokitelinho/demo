/*
 * ViewFormOneSessionImpl.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.outward;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormOneSession;

/**
 * @author Minu
 * Session implementation for  ViewFormOne screen
 * 
 * Revision History     
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1		   July 18, 2008        Minu				   Initial draft
 */
public class ViewFormOneSessionImpl extends AbstractScreenSession implements ViewFormOneSession {

	private static final String MODULE_NAME = "mra.airlinebilling";

		private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";
		
		private static final String KEY_FILTERVO="InterlineFilterVO";
		private static final String KEY_DETAILS="InterlineFilterVOs";
		private static final String KEY_FormVO="formOneVo";
		private static final String KEY_Invoice="invoiceInFormOneVo";
		private static final String CLOSE_STATUS = "closeStatus";
		private static final String CLR_PERIOD= "clrprd";
		private static final String ARL_CODE= "arlcode";
		 /**
	     * @return
	     */
	    @Override
	    public String getScreenID() {
	        return SCREENID;
	    }

	    /**
	     * @return
	     */
	    @Override
	    public String getModuleName() {
	        return MODULE_NAME;
	    }
	    
	    /**
		 * @return Returns the InterlineFilterVO.
		 */
	    public InterlineFilterVO getInterlineFilterVO() {
	    	return ((InterlineFilterVO) getAttribute(KEY_FILTERVO));
	    }
	   
	    /**
		 * @param interlineFilterVO
		 */
	    public void setInterlineFilterVO(InterlineFilterVO interlineFilterVO){
	    	setAttribute(KEY_DETAILS,
	    			(InterlineFilterVO)interlineFilterVO);
	    }
	    
	    /** set FormOneVO
	     * 
	     * @param formOneVO
	     */
	    
	    public void setFormOneVO
	    (FormOneVO formOneVO){
	    	setAttribute(KEY_FormVO, formOneVO);
	    }
	    
	    /**
	     * 
	     * get FormOneVO
	     */

	    
	    public FormOneVO getFormOneVO(){
	    	return getAttribute(KEY_FormVO);
	    }

	   /**
	    * 
	    * remove FormOneVO
	    */

	    public void removeFormOneVO(){
	    	removeAttribute(KEY_FormVO);
	    }
	    
	    /*
	     *  set cN66detailsVOs
	     * @param cN66detailsVOs
	     */
	    
	    public void setInvoiceFormOneDetailsVOs(Collection<InvoiceInFormOneVO> invoiceInFormOneVOs) {

			setAttribute( KEY_Invoice, (ArrayList<InvoiceInFormOneVO>) invoiceInFormOneVOs );

		}
	    
	    /**
	     * get cN66detailsVOs
	     */
	    
	    public Collection<InvoiceInFormOneVO> getInvoiceFormOneDetailsVOs() {

			return (ArrayList<InvoiceInFormOneVO>) getAttribute(KEY_Invoice);
		}



	    /**
	     * remove cN66detailsVOs
	     */

	    public void removeInvoiceFormOneDetailsVOs(){
	    	removeAttribute(KEY_Invoice);
	    	
	    }
	    /**
		 * @return String
		 */
		public String getCloseStatus(){
			return getAttribute(CLOSE_STATUS);
		}
		
		/**
		 * @param closeStatus
		 */
		public void setCloseStatus(String closeStatus){
			setAttribute(CLOSE_STATUS, closeStatus);
		}
		
		/**
		 * 
		 */
		public void removeCloseStatus(){
			removeAttribute(CLOSE_STATUS);
		}
		
		public String getClrperiod(){
			return getAttribute(CLR_PERIOD);
		}
		
		/**
		 * @param closeStatus
		 */
		public void setClrperiod(String clrperiod){
			setAttribute(CLR_PERIOD, clrperiod);
		}
		
		/**
		 * 
		 */
		public void removeClrperiod(){
			removeAttribute(CLR_PERIOD);
		}
		
		public String getAirlineCode(){
			return getAttribute(ARL_CODE);
		}
		
		/**
		 * @param closeStatus
		 */
		public void setAirlineCode(String airlineCode){
			setAttribute(ARL_CODE, airlineCode);
		}
		
		/**
		 * 
		 */
		public void removeAirlineCode(){
			removeAttribute(ARL_CODE);
		}
		
	}	