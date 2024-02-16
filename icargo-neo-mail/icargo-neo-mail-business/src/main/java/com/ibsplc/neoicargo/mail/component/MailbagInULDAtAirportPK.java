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
public class MailbagInULDAtAirportPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The carrierId
	*/
	private int carrierId;
	/** 
	* The uldNumber 
	*/
	private String uldNumber;
	/** 
	* The airportCode
	*/
	private String airportCode;
	/** 
	* mailSequenceNumber
	*/
	private long mailSequenceNumber;

}
