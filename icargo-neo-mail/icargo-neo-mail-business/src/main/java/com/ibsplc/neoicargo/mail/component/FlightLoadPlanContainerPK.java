/*
 * FlightLoanPlanContainerPK.java Created on July1 , 2022
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * @author a-3429
 * 
 */
@Embeddable
@Setter
@Getter
public class FlightLoadPlanContainerPK implements Serializable {
	private String companyCode;
	private String containerNumber;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private String segOrigin;
	private String segDestination;
	private int loadPlanVersion;
}
