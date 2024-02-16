package com.ibsplc.neoicargo.mailmasters.component;

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
public class PostalAdministrationPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The paCode
	*/
	private String paCode;
}
