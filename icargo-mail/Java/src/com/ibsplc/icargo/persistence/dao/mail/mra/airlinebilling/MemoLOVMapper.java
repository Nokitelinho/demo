/*
 * MemoLOVMapper.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
/**
 * Mapper for getting  Invoices
public class MemoLOVMapper implements Mapper<MemoLovVO> {
	private Log log = LogFactory.getLogger("MemoLOVMapper");
	/**
	public MemoLovVO map(ResultSet rs) throws SQLException {
		log.entering("MemoLOVMapper---------", "Map Method");
	}
}