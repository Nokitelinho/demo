/*
 * DespatchEnqSessionImpl.java Created on jun 25,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAAccountingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.OutstandingBalanceVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.USPSReportingVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2391
 *
 */
public class DespatchEnqSessionImpl extends AbstractScreenSession implements DespatchEnqSession {


	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/*
	 * The screen id
	 */
	private static final String SCREEN_ID =
											"mailtracking.mra.defaults.despatchenquiry";
	private static final String KEY_ONETIME_VOS="onetimevalues";
	private static final String KEY_GPADTLS="gpavos";
	
	  /**
     * Session variable for UnaccountedDispatchesFilterVO 
     */
    private static final String KEY_DISPACTCHESENQFILTERVO = "dispatchesEnqFilterVO";
    private static final String KEY_AIRLINEBILLINGDETAILS = "airlineBillingDetails";
    /**
     * Session variable for SectorRevenueDetailsVO
     */
	private static final String KEY_FLOWNDETAILS="flownDetails";
    /**
     * Session variable for SectorRevenueDetailsVO
     */
	private static final String KEY_DESPATCH_ENQUIRYVO="despatchenquiryvo";
	
	/**
	 * Session variable for MRAAccoutingVO
	 */
	private static final String KEY_ACCOUNTINGDETAILS="accountingDetails";
	/**
	 * Session variable for USPSReportingVO
	 */
	private static final String KEY_USPSREPORTING="accountingDetails";
	/**
	 * Session variable for functionPoint
	 */
	private static final String KEY_FUNCTIONPOINT="functionPoint";
	private static final String KEY_INDEXMAP = "indexmap";
	/**
	 * Session variable for OutstandingBalanceVO
	 */
	private static final String KEY_OUTSTANDINGBALANCES="outstandingbalances";
	 /**
     * @return
     */
    public String getScreenID() {

        return SCREEN_ID;
    }

    /**
     * @return MODULE_NAME
     */
    public String getModuleName() {
        return MODULE_NAME;
    }
    /**
     * 
     *
     */
    public DespatchEnqSessionImpl() {
        super();
    }

   

		 /**

	    *

	    * @return

	    */

	    public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

	    return getAttribute(KEY_ONETIME_VOS);

	    }
	    /**

	    *

	    * @param oneTimeVOs

	    */

	    public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

	    setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

	    }
	    /**

	    *

	    *remove onetimes

	    */

	    public void removeOneTimeVOs() {

	    removeAttribute(KEY_ONETIME_VOS);

	    }
	    /**
		 *
		 * @param gPABillingDetailsVO
		 */
		public void setGPABillingDtls(Collection<GPABillingDetailsVO> gPABillingDetailsVO) {
			setAttribute(KEY_GPADTLS,(ArrayList<GPABillingDetailsVO>)gPABillingDetailsVO);

		}

		/**
		 *
		 * @return
		 */
		public Collection<GPABillingDetailsVO> getGPABillingDtls() {
			// TODO Auto-generated method stub
			return (Collection<GPABillingDetailsVO>)getAttribute(KEY_GPADTLS);
		}

		/**
		 *
		 */
		public void removeGPABillingDtls() {
			removeAttribute(KEY_GPADTLS);

		}
		
		
		/**
		 * The setter method for SearchContainerFilterVO
		 * @param unaccountedDispatchesFilterVO
		 */
	    public void setDispatchFilterVO(
	    		DSNPopUpVO dSNPopUpVO) {
	    	setAttribute(KEY_DISPACTCHESENQFILTERVO,dSNPopUpVO);
	    }
	    /**
	     * The getter method for searchContainerFilterVO
	     * @return searchContainerFilterVO
	     */
	    public DSNPopUpVO getDispatchFilterVO() {
	    	return getAttribute(KEY_DISPACTCHESENQFILTERVO);
	    }
	    
	    public Collection<AirlineBillingDetailVO> getAirlineBillingDetailsVO() {
			// TODO Auto-generated method stub
			return (Collection<AirlineBillingDetailVO>)getAttribute(KEY_AIRLINEBILLINGDETAILS);
		}

		public void setAirlineBillingDetailsVO(Collection<AirlineBillingDetailVO> airlineBillingDetails) {
			setAttribute(KEY_AIRLINEBILLINGDETAILS,(ArrayList<AirlineBillingDetailVO>)airlineBillingDetails);
			
		}
		 /**
		 *
		 * @param sectorRevenueDetailsVO
		 */
		public void setFlownDetails(Collection<SectorRevenueDetailsVO> sectorRevenueDetailsVO) {
			setAttribute(KEY_FLOWNDETAILS,(ArrayList<SectorRevenueDetailsVO>)sectorRevenueDetailsVO);

		}

		/**
		 *
		 * @return Collection<SectorRevenueDetailsVO> 
		 */
		public Collection<SectorRevenueDetailsVO> getFlownDetails() {
			return (Collection<SectorRevenueDetailsVO>)getAttribute(KEY_FLOWNDETAILS);
		}

		/**
		 * method to remove flowndetails from session 
		 */
		public void removeFlownDetails() {
			removeAttribute(KEY_FLOWNDETAILS);

		}
		/**
		 * @return DespatchEnquiryVO
		 */
		public DespatchEnquiryVO getDespatchEnquiryVO() {
			return getAttribute(KEY_DESPATCH_ENQUIRYVO);
		}
		
		/**
		 * @param despatchEnquiryVO
		 */
		public void setDespatchEnquiryVO(DespatchEnquiryVO despatchEnquiryVO) {
			setAttribute(KEY_DESPATCH_ENQUIRYVO,despatchEnquiryVO);
			
		}
		
		 /**
		 *
		 * @param mraAccountingVO
		 */
		public void setAccountingDetails(Page<MRAAccountingVO> mraAccountingVOs) {
			setAttribute(KEY_ACCOUNTINGDETAILS,(Page<MRAAccountingVO>)mraAccountingVOs);

		}

		/**
		 *
		 * @return Page<MRAAccountingVO> 
		 */
		public Page<MRAAccountingVO> getAccountingDetails() {
			return (Page<MRAAccountingVO>)getAttribute(KEY_ACCOUNTINGDETAILS);
		}

		/**
		 * method to remove accountingDetails from session 
		 */
		public void removeAccountingDetails() {
			removeAttribute(KEY_ACCOUNTINGDETAILS);

		}
		 /**
		 *
		 * @param uspsReportingVO
		 */
		public void setUSPSReportingDetails(Collection<USPSReportingVO> uspsReportingVOs) {
			setAttribute(KEY_USPSREPORTING,(ArrayList<USPSReportingVO>)uspsReportingVOs);

		}

		/**
		 *
		 * @return Collection<USPSReportingVO> 
		 */
		public Collection<USPSReportingVO> getUSPSReportingDetails() {
			return (Collection<USPSReportingVO>)getAttribute(KEY_USPSREPORTING);
		}

		/**
		 * method to remove uspsReportingDetails from session 
		 */
		public void removeUSPSReportingDetails() {
			removeAttribute(KEY_USPSREPORTING);

		}
		   /**
	     * @return ArrayList<OneTimeVO>
	     */
	    public ArrayList<OneTimeVO>  getFunctionPoint(){
	        return getAttribute(KEY_FUNCTIONPOINT);
	    }
	   
	    /**
	     * @param FunctionPoint
	     * @return 
	     */
	    public  void setFunctionPoint(ArrayList<OneTimeVO> functionPoint ){
		   	setAttribute(KEY_FUNCTIONPOINT, functionPoint);
	    }
	   
	    /**
	    * Removes the  FunctionPoint 
	    */
	    public  void removeFunctionPoint(){
		   	removeAttribute(KEY_FUNCTIONPOINT);
	    }
	    /**
		 * This method is used to set indexMap to the session
		 *  @param indexMap
		 */
		public void setIndexMap(HashMap indexMap){
			setAttribute( KEY_INDEXMAP,(HashMap<String,String>)indexMap);
		 }
		/**
		 * This method returns the hashmap
		 * 
		 * @return HashMap - HashMap<String,String>
		 */
		
		public HashMap getIndexMap(){
			return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
		}
		
		 /**
		 *
		 * @param OutstandingBalanceVO
		 */
		public void setOutStandingBalances(Collection<OutstandingBalanceVO> outstandingBalanceVOs) {
			setAttribute(KEY_OUTSTANDINGBALANCES,(ArrayList<OutstandingBalanceVO>)outstandingBalanceVOs);

		}

		/**
		 *
		 * @return Collection<OutstandingBalanceVO> 
		 */
		public Collection<OutstandingBalanceVO> getOutStandingBalances() {
			return (Collection<OutstandingBalanceVO>)getAttribute(KEY_OUTSTANDINGBALANCES);
		}

		/**
		 * method to remove OutstandingBalanceVOs from session 
		 */
		public void removeOutStandingBalances() {
			removeAttribute(KEY_OUTSTANDINGBALANCES);

		}
		
}
