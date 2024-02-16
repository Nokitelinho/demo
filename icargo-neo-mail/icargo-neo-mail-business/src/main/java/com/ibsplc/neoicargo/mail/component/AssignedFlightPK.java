package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-3109
 */
@Setter
@Getter
@Embeddable
public class AssignedFlightPK implements Serializable {
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
