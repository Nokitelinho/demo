/*
 * ResditConfigurationPK.java Created on Feb 1, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Feb 1, 2007			A-1739		Created
 */
@Embeddable
public class ResditConfigurationPK implements Serializable {
	
	private String companyCode;
	
	private int carrierId;
	
	private String transactionId;

}
