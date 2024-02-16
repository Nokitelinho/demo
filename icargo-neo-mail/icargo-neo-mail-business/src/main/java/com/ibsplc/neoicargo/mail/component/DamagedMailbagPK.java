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
public class DamagedMailbagPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The damageCode
	*/
	private String damageCode;
	/** 
	* The airportCode
	*/
	private String airportCode;
	/** 
	* mailSequenceNumber
	*/
	private long mailSequenceNumber;

}
