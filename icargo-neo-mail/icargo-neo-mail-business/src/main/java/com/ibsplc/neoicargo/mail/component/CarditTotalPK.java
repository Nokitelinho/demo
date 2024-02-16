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
public class CarditTotalPK implements Serializable {
	/** 
	* CompanyCode
	*/
	private String companyCode;
	/** 
	* Cardit Key
	*/
	private String carditKey;
	/** 
	* The mail class code
	*/
	private String mailClassCode;

}
