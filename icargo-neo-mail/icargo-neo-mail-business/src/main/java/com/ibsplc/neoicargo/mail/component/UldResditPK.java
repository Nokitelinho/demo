package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-3109
 */
@Setter
@Getter
@Embeddable
public class UldResditPK implements Serializable {

	private String companyCode;

	private String uldNumber;

	private String eventCode;

	private long sequenceNumber;

}
