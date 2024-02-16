
/*
 * DuplicateMailBagsException.java Created on JULY 10, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 *  This class is used to create the  Exception 
 *  when there exists an DuplicateMailBag
 * @author a-1936
 *
 */
public class DuplicateMailBagsException  extends BusinessException{

	/**
	 * The ErrorCode for DuplicateMailBags
	 */
	public  static final String DUPLICATEMAILBAGS_EXCEPTION = 
		"mailtracking.defaults.acceptance.err.duplicatemailbags";
	
	public static final String INVALIDACCEPATNCE_EXCEPTION =
			"mailtracking.defaults.acceptance.err.inavlidacceptance"  ;
	//Added by A-5945 for ICRD-129779
	public static final String MAILBAG_ALREADY_ARRIVAL_EXCEPTION =
			"mailtracking.defaults.arrival.err.alreadyarrivedmailbag";
	
	public static final String MAILBAG_ALREADY_ARRIVAL_INSAMEFLIGHT_DIFF_CNTAINER_EXCEPTION =
		"mailtracking.defaults.arrival.err.alreadyarrivedmailbaginsameflightdiffcontainer";
	public static final String MAILBAG_ALREADY_RETURNED_EXCEPTION =
		"mailtracking.defaults.err.acceptance.returnedmailbag";   
	/**
	 * Constructor
	 *
	 */
	public DuplicateMailBagsException(){
		super();
	}
    /**
     * @param errorCode
     * @param exceptionCause
     */
    public DuplicateMailBagsException(String errorCode, 
    		                 Object[] exceptionCause) {
        super(errorCode, exceptionCause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param errorCode
     */
    public DuplicateMailBagsException(String errorCode) {
        super(errorCode);
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param ex
     */
    public DuplicateMailBagsException(BusinessException ex) {
        super(ex);
        // TODO Auto -generated constructor stub
        
        
       
    }
	
	
	
	
	
}
