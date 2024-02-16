/*
 * ULDResditMapper.java Created on Aug 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 * @author A-1936
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.UldResditVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * @author
 * This class is used to map the ResultSet in to the
 * UldResditVO
 *
 */
public class ULDResditMapper implements  Mapper<UldResditVO>{

	/**
	 * @author A-1936
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	public UldResditVO map(ResultSet rs)
	 throws SQLException{
		UldResditVO  uldResditVO =  new UldResditVO();
		uldResditVO.setResditSentFlag(rs.getString("RDTSND"));
		uldResditVO.setResditSequenceNum(rs.getLong("SEQNUM"));
		uldResditVO.setProcessedStatus(rs.getString("PROSTA"));
		return uldResditVO;
	}




}
