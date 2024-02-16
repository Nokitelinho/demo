package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * TODO Add the purpose of this class
 * @author A-3109
 */
@Setter
@Getter
@Embeddable
public class MailResditPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The mailSequenceNumber
	*/
	private long mailSequenceNumber;
	/** 
	* The eventCode
	*/
	private String eventCode;
	/** 
	* The sequenceNumber
	*/
	private long sequenceNumber;

}
