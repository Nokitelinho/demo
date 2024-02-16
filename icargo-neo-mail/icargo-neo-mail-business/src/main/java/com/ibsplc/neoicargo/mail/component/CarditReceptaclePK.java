package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * TODO Add the purpose of this class
 * @author A-1739
 */
@Setter
@Getter
@Embeddable
public class CarditReceptaclePK implements Serializable {
	/** 
	* CompanyCode
	*/
	private String companyCode;
	/** m
	* Cardit Key
	*/
	private String carditKey;
	/** 
	* The unique identifier for the receptacle
	*/
	private String receptacleId;
}
