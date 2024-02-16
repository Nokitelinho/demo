/*
 * MailSubClassCodeMapper.java Created on June 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * @author
 * This class is used to Map the Resultset into MailSubClassVO
 *
 */
public class MailSubClassCodeMapper implements Mapper<MailSubClassVO>{
	 /**
     * @param rs
     * @return 
     * @throws SQLException
     */
   public MailSubClassVO map(ResultSet rs) throws SQLException {
	   MailSubClassVO mailSubClassVO=new MailSubClassVO();
	   mailSubClassVO.setCompanyCode(rs.getString("CMPCOD"));
	   mailSubClassVO.setCode(rs.getString("SUBCLSCOD"));
	   mailSubClassVO.setDescription(rs.getString("DES"));
	   mailSubClassVO.setSubClassGroup(rs.getString("SUBCLSGRP"));
	   Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
	     if(lstUpdTime != null) {
	    	 mailSubClassVO.setLastUpdateTime(
	    		 new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
	     }
	   mailSubClassVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
	   return mailSubClassVO;
	   
   }
}
