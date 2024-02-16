package com.ibsplc.neoicargo.mailmasters.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-6986
 */
@Setter
@Getter
@Embeddable
public class MailIncentiveMasterPK implements Serializable {
	private String companyCode;
	private int incentiveSerialNumber;

}
