/*
 * ChangeStatusException.java Created on Sep 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
/**
 * @author A-3434
 *
 */

public class ChangeStatusException extends BusinessException{

	/**
     * CCA exists Exception
     */
    public static final String MRA_ACTUALCCA_EXISTS  = 
        "mailtracking.mra.err.ccaexists";
    
    /**
     * Constructor
     */
    public ChangeStatusException() {
        super(MRA_ACTUALCCA_EXISTS);
    }
    /**
     * Constructor
     * @param errorData
     */
    public ChangeStatusException(Object[] errorData) {
        super(MRA_ACTUALCCA_EXISTS, errorData);
    }
    
}
