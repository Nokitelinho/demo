/*
 * ReservationException.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.reservation;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1619
 *
 */
public class ReservationException extends BusinessException {

    /**
     * 
     */
    public static final String STOCK_NOTEXISTS = "stockcontrol.defaults.stocknotexists";    
    
    /**
     * 
     */
    public static final String AIRLINE_STOCK_BLK = "stockcontrol.defaults.airlinestockblocked";
    
    /**
     * 
     */
    public static final String AIRLINE_STOCK_LT_REORDERLVL = "stockcontrol.defaults.stocklessthanreorderlvl";
    
    /**
     * 
     */
    public static final String AWB_BLOCKED = "stockcontrol.defaults.documentblocked";
    
    /**
     * 
     */
    public static final String INVALID_DOCUMENT = "stockcontrol.defaults.invaliddocument";
    
    /**
     * 
     */
    public static final String RESERVATION_EXCEPTION = "stockcontrol.defaults.reservationexception";
    
    /**
     * 
     */
    public ReservationException(){
    	super();
    }
    /**
     * @param businessException
     */
    public ReservationException(BusinessException businessException) {
        super(businessException);

    }
    /**
     * @param errorCode
     */
    public ReservationException(String errorCode) {
        super(errorCode);

    }
    /**
     * @param errorCode
     * @param errorData
     */
    public ReservationException(String errorCode, Object[] errorData) {
        super(errorCode, errorData);

    }
}
