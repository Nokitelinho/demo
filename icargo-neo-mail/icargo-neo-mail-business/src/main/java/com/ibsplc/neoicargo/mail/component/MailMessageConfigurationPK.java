package com.ibsplc.neoicargo.mail.component;

import javax.persistence.Embeddable;
import java.io.Serializable;

//@SequenceKeyGenerator(name="ID_GEN",sequence="MALMSGCFG_SEQ")
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