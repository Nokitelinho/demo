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
public class CarditContainerPK implements Serializable {
	/** 
	* CompanyCode
	*/
	private String companyCode;
	/** 
	* Cardit Key
	*/
	private String carditKey;
	/** 
	* Equipment identification number
	*/
	private String containerNumber;

}
