package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempDetailsPK.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-6287	:	26-Feb-2020	:	Draft
 */
@Setter
@Getter
@Embeddable
public class CarditTempDetailsPK implements Serializable {

	private String companyCode;
	private long sequenceNumber;
	private String ConsignmentIdentifier;
}
