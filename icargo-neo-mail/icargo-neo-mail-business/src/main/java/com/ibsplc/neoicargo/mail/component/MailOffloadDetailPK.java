package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-5991The PK Class for OffloadDetail
 */
@Setter
@Getter
@Embeddable
public class MailOffloadDetailPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The CarrierId
	*/
	private int carrierId;
	/** 
	* The FlightNumber
	*/
	private String flightNumber;
	/** 
	* The FlightSequenceNumber 
	*/
	private long flightSequenceNumber;
	/** 
	* The segmentSerialNumber
	*/
	private int segmentSerialNumber;
	/** 
	* The Offload SerialNumber
	*/
	private int offloadSerialNumber;


}
