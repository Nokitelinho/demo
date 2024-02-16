package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class CoterminusAirportPK implements Serializable {
	/** 
	* The CompanyCode
	*/
	private String companyCode;
	/** 
	* The GPACode
	*/
	private String gpaCode;
	/** 
	* The SERNUM
	*/
	private long sernum;

}
