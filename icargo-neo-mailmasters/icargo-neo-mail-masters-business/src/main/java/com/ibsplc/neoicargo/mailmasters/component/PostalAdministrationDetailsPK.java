package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-3108
 */
@Setter
@Getter
@Embeddable
public class PostalAdministrationDetailsPK implements Serializable {
	/** 
	* companyCode
	*/
	private String companyCode;
	/** 
	* poacod
	*/
	private String poacod;
	/** 
	* parcod
	*/
	private String parcod;
	/** 
	* profInv
	*/
	private String sernum;


}
