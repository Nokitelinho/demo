package com.ibsplc.neoicargo.mailmasters.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-8923Primary Key of MailMessageConfigurationParameter
 */
@Setter
@Getter
@Embeddable
public class MailMessageConfigurationParameterPK implements Serializable {
	/** 
	* The mailSequenceNumber
	*/
	private long messageConfigurationSequenceNumber;
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* parameterCode
	*/
	private String parameterCode;

}
