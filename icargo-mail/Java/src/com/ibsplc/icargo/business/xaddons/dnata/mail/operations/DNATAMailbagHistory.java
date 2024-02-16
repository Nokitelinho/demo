/*
 * MailbagHistory.java Created on Jun 17, 2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.dnata.mail.operations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.MailbagHistory;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-9619
 * class created for implementing xaddons  
 *
 */

@Table(name = "MALHIS_VW_DNATA")
@Entity
public class DNATAMailbagHistory extends MailbagHistory{
	
	private static final Log LOG = LogFactory.getLogger("DNATA MAILBAG HISTORY");

	private String screeningUser;//Added by A-9498 as part of IASCB-44577
	private String storageUnit;//A-9619 for IASCB-44572
	
	
	@Column(name = "STGUNT")
	public String getStorageUnit() {
		return storageUnit;
	}
	
	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
	}
	
	@Column(name = "SCRUSR")
	public String getScreeningUser() {
		return screeningUser;
	}
	
	public void setScreeningUser(String screeningUser) {
		this.screeningUser = screeningUser;
	}
	
	/**
	 * @author A-9619  as part of IASCB-55196 xaddons
	 */
	@Override
	public void populateAttributes(MailbagHistoryVO mailbagHistoryVO) {
		
		LOG.entering("DNATAMailBagHistory", "populateAttributes");
		super.populateAttributes(mailbagHistoryVO);
		setScreeningUser(mailbagHistoryVO.getScreeningUser());//Added by A-9498 as part of IASCB-44577 
        setStorageUnit(mailbagHistoryVO.getStorageUnit());  //added by A-9529 for IASCB-44567
        
        LOG.exiting("DNATAMailBagHistory", "populateAttributes");
	}
}
