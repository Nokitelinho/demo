/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.ResditMessageDetailsMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Jun 7, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.ResditMessageDetailsMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Jun 7, 2019	:	Draft
 * @param <ResditEventVO>
 */
public class ResditMessageDetailsMapper implements Mapper<ResditEventVO>{

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-4809 on Jun 7, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public ResditEventVO map(ResultSet rs) throws SQLException {
		
		ResditEventVO resditEventVO = new ResditEventVO();
		resditEventVO.setCompanyCode(rs.getString("CMPCOD"));
		resditEventVO.setMsgText(rs.getString("MSGDTL"));
		resditEventVO.setPaCode(rs.getString("POACOD"));
		resditEventVO.setResditVersion(rs.getString("MSGVERNUM"));
		resditEventVO.setEventPort(rs.getString("EVTPRT"));
		return resditEventVO;
	}

}
