/*
 * InboundFlightPK.java Created on Aug 3, 2006
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
 * @author a-1303
 *
 */
@Embeddable
@Setter
@Getter
public class InboundFlightPK implements Serializable {
    /**
     * The companyCode
     */
	private String companyCode;
	/**
	 * The airportCode
	 */
    private String airportCode;
    /**
     * The carrierId
     */
    private int carrierId;
    /**
     * The flightNumber
     */
    private String flightNumber;
    /**
     * The legSerialNumber
     */
    private int legSerialNumber;
    /**
     * The flightSequenceNumber
     */
    private long flightSequenceNumber;

}
