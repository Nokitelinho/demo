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
public class MailSubClassPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The subClassCode
	*/
	private String subClassCode;
}
