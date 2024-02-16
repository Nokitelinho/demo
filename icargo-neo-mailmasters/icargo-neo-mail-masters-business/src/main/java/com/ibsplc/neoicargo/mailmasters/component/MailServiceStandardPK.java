package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-8149
 */
@Setter
@Getter
@Embeddable
public class MailServiceStandardPK implements Serializable {
	private String companyCode;
	private String gpaCode;
	private String originCode;
	private String destCode;
	private String serviceLevel;
}
