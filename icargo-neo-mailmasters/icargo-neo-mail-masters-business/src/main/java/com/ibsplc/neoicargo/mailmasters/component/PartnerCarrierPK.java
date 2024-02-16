package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-1303
 */
@Setter
@Getter
@Embeddable
public class PartnerCarrierPK implements Serializable {
	/** 
	* The CompanyCode
	*/
	private String companyCode;
	/** 
	* The OwnCarrierCode
	*/
	private String ownCarrierCode;
	/** 
	* The PartnerCarrierCode
	*/
	private String partnerCarrierCode;
	/** 
	* The AirportCode
	*/
	private String airportCode;

}
