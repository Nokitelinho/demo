package com.ibsplc.neoicargo.mailmasters.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class MailMessageConfigurationPK implements Serializable {
	/** 
	* The mailSequenceNumber
	*/
	private long messageConfigurationSequenceNumber;
	/** 
	* The companyCode
	*/
	private String companyCode;


}
