package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-5219
 */
@Setter
@Getter
@Embeddable
public class ForceMajeureRequestPK implements Serializable {
	private String companyCode;
	private String forceMajuereID;
	private long sequenceNumber;
}
