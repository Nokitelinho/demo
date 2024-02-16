/*
 * AWBDetailsMapper.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author a-1883
 *
 */
public class AWBDetailsMapper implements Mapper<AWBDetailVO> {
/**
 * @param rs
 * @return AWBDetailVO
 * @throws SQLException
 */
	public AWBDetailVO map(ResultSet rs)throws SQLException{
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		awbDetailVO.setOwnerId(rs.getInt("DOCOWRIDR"));
		awbDetailVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		awbDetailVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		awbDetailVO.setSequenceNumber(rs.getInt("SEQNUM"));
		
		awbDetailVO.setOrigin(rs.getString("ORG"));
		awbDetailVO.setDestination(rs.getString("DST"));
		awbDetailVO.setOwnerCode(rs.getString("DOCOWRCOD"));
		return awbDetailVO;
	}
}
