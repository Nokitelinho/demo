/*
 * AirlineExceptionsSessionImpl.java Created on Feb 19, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.inward;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.AirlineExceptionsSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-2521
 *
 */
public class AirlineExceptionsSessionImpl extends AbstractScreenSession 
implements AirlineExceptionsSession{
	
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.airlineexceptions";
	
	private static final String KEY_EXCEPTIONS = "exceptions";
	
	private static final String KEY_ONETIMES = "exceptioncodes";
	
	private static final String KEY_FILTER = "exceptionfilter";
	/**
	 * 
	 */
	private static final String KEY_SELECT_ROWS="selectedrows";
	private static final String  TOTALRECORDS = "totalRecords";
	/**
	 * 
	 */
	@Override
	public String getScreenID() {
		
		return SCREEN_ID;
	}
	
	/**
	 * 
	 */
	@Override
	public String getModuleName() {
		
		return MODULE_NAME;
	}
	
	/**
	 * returns AirlineExceptionsVOs
	 * 
	 * @return 
	 */
	public Page<AirlineExceptionsVO> getAirlineExceptionsVOs() {

		return (Page<AirlineExceptionsVO>)getAttribute( KEY_EXCEPTIONS );
	}

	/**
	 * sets airlineExceptionsVOs
	 * 
	 * @param airlineExceptionsVOs
	 */
	public void setAirlineExceptionsVOs(Page<AirlineExceptionsVO> airlineExceptionsVOs) {
		setAttribute(KEY_EXCEPTIONS, (Page<AirlineExceptionsVO>) airlineExceptionsVOs);

	}

	/**
	 * removes airlineExceptionsVOs
	 */
	public void removeAirlineExceptionsVOs() {
		removeAttribute(KEY_EXCEPTIONS);

	}
	
	/**
	 * sets airlineExceptionsFilterVO
	 * 
	 * @param airlineExceptionsFilterVO
	 */
	public void setAirlineExceptionsFilterVO(AirlineExceptionsFilterVO airlineExceptionsFilterVO) {
		setAttribute(KEY_FILTER, airlineExceptionsFilterVO);

	}

	/**
	 * gets airlineExceptionsFilterVO
	 *
	 * @return airlineExceptionsFilterVO
	 */
	public AirlineExceptionsFilterVO getAirlineExceptionsFilterVO() {
		return getAttribute( KEY_FILTER );
	}

	/**
	 * removes airlineExceptionsFilterVO
	 */
	public void removeAirlineExceptionsFilterVO() {
		removeAttribute(KEY_FILTER);

	}
	
	/**
	 * @return exceptionsOneTimeVOs
	 */
	public HashMap<String, Collection<OneTimeVO>> getExceptionsOneTime() {

		return getAttribute(KEY_ONETIMES);
	}
	
	/**
	 * @param exceptionsOneTimeVOs
	 */
	public void setExceptionsOneTime(HashMap<String, Collection<OneTimeVO>> exceptionsOneTimeVOs) {
		setAttribute(KEY_ONETIMES, exceptionsOneTimeVOs);
		
	}

	/**
	 * removes exceptionsOneTimeVOs
	 */
	public void removeExceptionsOneTime() {
		
		removeAttribute(KEY_FILTER);
	}
	
	/**
     * 
     */
    public String[] getSelectedRows(){
    	return (String[])getAttribute(KEY_SELECT_ROWS);
    }
    /**
     * 
     */
    public void setSelectedRows(String[] str){
    	setAttribute(KEY_SELECT_ROWS, str);
    }
    /**
     * 
     */
    public void removeSelectedRows(){
    	removeAttribute(KEY_SELECT_ROWS);
    }
    
    /**
	 * @return Returns the totalRecords.
	 */
	public Integer getTotalRecords() {
		return getAttribute(TOTALRECORDS);
	}
	
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTALRECORDS,totalRecords);
	}
	
	/**
     * This method removes the totalRecords in session
     */
	public void removeTotalRecords() {
	 	removeAttribute(TOTALRECORDS);
	}
	
}
