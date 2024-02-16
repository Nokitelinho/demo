package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-1303
 */
@Setter
@Getter
@Embeddable
public class CarditReceptacleHistoryPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The dsn
	*/
	private long mailSeqNum;
	/** 
	* The historySequenceNumber
	*/
	private int sequenceNumber;

}
