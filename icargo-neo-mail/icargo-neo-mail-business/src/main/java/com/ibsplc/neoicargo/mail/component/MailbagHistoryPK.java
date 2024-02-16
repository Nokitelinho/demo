package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-5991
 */
@Setter
@Getter
@Embeddable
public class MailbagHistoryPK implements Serializable {
	/** 
	* The companyCode 
	*/
	private String companyCode;
	/** 
	* The historySequenceNumber
	*/
	private int historySequenceNumber;
	/** 
	* mailSequenceNumber
	*/
	private long mailSequenceNumber;

}
