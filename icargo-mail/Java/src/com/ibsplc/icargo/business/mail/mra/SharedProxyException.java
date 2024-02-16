/*
 * SharedProxyException.java Created on Aug 11, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

 /**
  * 
  * @author A-1936
  *
 */
public class SharedProxyException  extends BusinessException{
	
	
	/**
	 * The ErrorCode when the City is invalid
	 */
	public static final String INVALID_CITY = "shared.area.invalidcity";
	
	/**
	 * The ErrorCode when the Country is invalid
	 */
	public static final String INVALID_COUNTRY = "shared.area.invalidcountry";
	/**
	 * The ErrorCode when the airline  is invalid
	 */
	public static final String INVALID_AIRLINE = "shared.airline.invalidairline";
	/**
	 * The ErrorCode when the ULDType  is invalid
	 */
	public static final String INVALID_ULDTYPE= "shared.uld.invaliduldType";
	
	public SharedProxyException(){
		super();
	}
    
	/**
     * @param errorCode
     * @param exceptionCause
     */
    public SharedProxyException(String errorCode, 
    		                 Object[] exceptionCause) {
        super(errorCode, exceptionCause);
        // TODO Auto-generated constructor stub
    }

    
    /**
     * @param errorCode
     */
    public SharedProxyException(String errorCode) {
        super(errorCode);
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param ex
     */
    public SharedProxyException(BusinessException ex) {
        super(ex);
        // TODO Auto-generated constructor stub
    }
}
