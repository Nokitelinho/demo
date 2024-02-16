package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Embeddable
public class ULDForSegmentPK implements Serializable {
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
	* For Bulk , uldNumber is BULK/assignmentPort
	*/
	private String uldNumber;

}
