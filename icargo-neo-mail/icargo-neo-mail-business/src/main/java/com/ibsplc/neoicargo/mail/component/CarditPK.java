/*
 * CarditPK.java Created on Jun 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * TODO Add the purpose of this class
 * 
 * @author A-1739
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 7, 2006 A-1739 First Draft
 *  		  July 16, 2007			A-1739		EJB3 Final changes
 */
@Setter
@Getter
@Embeddable
public class CarditPK implements Serializable {

	/**
	 * CompanyCode
	 */
	private String companyCode;

	/**
	 * Cardit Key
	 */
	private String carditKey;

}
