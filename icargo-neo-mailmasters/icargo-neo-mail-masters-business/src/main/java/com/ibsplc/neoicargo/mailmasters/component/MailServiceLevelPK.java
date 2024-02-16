package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-6986
 */
@Setter
@Getter
@Embeddable
public class MailServiceLevelPK implements Serializable {
	private String companyCode;
	private String poaCode;
	private String mailCategory;
	private String mailClass;
	private String mailSubClass;

}
