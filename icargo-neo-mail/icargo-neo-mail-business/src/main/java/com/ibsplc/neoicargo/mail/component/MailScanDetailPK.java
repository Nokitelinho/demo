
/*
 * MailScanDetailPK Created on Jun 27, 2016
 *
 * Copyright 2015 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.mail.component;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MailScanDetailPK implements Serializable {
	
	private String companyCode;
	private String mailBagId;
	private int serialNumber;

}
