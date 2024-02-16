package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-3109
 */
@Setter
@Getter
@Embeddable
public class ULDAtAirportPK implements Serializable {
	/** 
	* The companycode
	*/
	private String companyCode;
	/** 
	* The uldNumber
	*/
	private String uldNumber;
	/** 
	* The airportCode
	*/
	private String airportCode;
	/** 
	* The carrierId
	*/
	private int carrierId;

}
