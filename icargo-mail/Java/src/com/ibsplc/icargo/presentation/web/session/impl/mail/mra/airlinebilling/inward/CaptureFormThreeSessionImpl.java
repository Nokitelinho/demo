/*
 * CaptureFormThreeSessionImpl.java Created on july 25, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.inward;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormThreeSession;
/**
 * 
 * @author A-3108
 *
 */

public class CaptureFormThreeSessionImpl extends AbstractScreenSession implements CaptureFormThreeSession {

	
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureformthree";
	/**
	 * One time values KEY
	 */
	private static final String KEY_AIRLINEFORBILLING_VOS="airlineforbillingvos";
	/**
	 * one time values
	 */
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	/**
	 * Key Filter Vo
	 */
	private static final String KEY_FILTERVO = "AirlineCN51FilterVO";
	
	private static final String KEY_AIRLINECODE = "airlineNumbericCode";
	
	

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
     * @return Collection<ExceptionInInvoiceVO>
     */
    public Collection<AirlineForBillingVO> getAirlineForBillingVOs(){
    	return (Collection<AirlineForBillingVO>)getAttribute(KEY_AIRLINEFORBILLING_VOS);
    }
    
    
    /**
     * @param exceptionInInvoiceVOs
     */
    public void setAirlineForBillingVOs(ArrayList<AirlineForBillingVO> airlineForBillingVOs){
    	setAttribute(KEY_AIRLINEFORBILLING_VOS, (ArrayList<AirlineForBillingVO>)airlineForBillingVOs);
    }
   
    
    /**
     * @param
     */
    public void removeAirlineForBillingVOs(){
    	removeAttribute(KEY_AIRLINEFORBILLING_VOS);
    }
    /**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES,oneTimeValues);
	}
	public InterlineFilterVO getInterlineFilterVO() {
		return ((InterlineFilterVO) getAttribute(KEY_FILTERVO));
	}

	/**
	 * @param AirlineCN51FilterVO
	 */
	public void setInterlineFilterVO(InterlineFilterVO interlineFilterVO) {
		setAttribute(KEY_FILTERVO, (InterlineFilterVO) interlineFilterVO);
	}
	
	 /**
     * @param
     */
    public void removeInterlineFilterVO(){
    	removeAttribute(KEY_FILTERVO);
    }
    
    
    public String getAirlineNumbericCode() {
		return ((String) getAttribute(KEY_AIRLINECODE));
	}

	/**
	 * @param AirlineCN51FilterVO
	 */
	public void setAirlineNumbericCode(String airlineNumbericCode) {
		setAttribute(KEY_AIRLINECODE, (String) airlineNumbericCode);
	}
	
	 /**
     * @param
     */
    public void removeAirlineNumbericCode(){
    	removeAttribute(KEY_AIRLINECODE);
    }
    
    
    
}
