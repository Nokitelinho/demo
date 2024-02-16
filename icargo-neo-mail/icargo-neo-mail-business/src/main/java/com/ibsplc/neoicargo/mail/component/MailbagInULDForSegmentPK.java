package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Embeddable
public class MailbagInULDForSegmentPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
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
	* The uldNumber
	*/
	private String uldNumber;
	/** 
	* The mailSequenceNumber
	*/
	private long mailSequenceNumber;

}
