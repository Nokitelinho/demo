package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-5991
 */
@Setter
@Getter
@Embeddable
public class MailbagPK implements Serializable {
	/**
	 *
	 */
	private String companyCode;
	/**
	 * The mailSequenceNumber
	 */
	private long mailSequenceNumber;

}
