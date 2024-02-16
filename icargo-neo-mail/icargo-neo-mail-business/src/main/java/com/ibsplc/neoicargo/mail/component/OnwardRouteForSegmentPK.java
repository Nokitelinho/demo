package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author a-1303
 */
@Setter
@Getter
@Embeddable
public class OnwardRouteForSegmentPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The uldNumber
	*/
	private String uldNumber;
	/** 
	* The carrierId
	*/
	private int carrierId;
	/** 
	* The flightNumber
	*/
	private String flightNumber;
	/** 
	* The flightSequenceNumber
	*/
	private long flightSequenceNumber;
	/** 
	* The segmentSerialNumber
	*/
	private int segmentSerialNumber;
	/** 
	* The routingSerialNumber
	*/
	private int routingSerialNumber;

}
