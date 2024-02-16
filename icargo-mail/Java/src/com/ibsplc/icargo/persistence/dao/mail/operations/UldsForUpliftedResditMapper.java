/*
 * UldsForUpliftedResditMapper.java Created on Jan 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-1876
 *
 */
public class UldsForUpliftedResditMapper implements Mapper<ContainerDetailsVO> {
    
	/**
     * This class is used to map the ResultSet into the MailBagVo
     * @param rs
     * @return 
     * @throws SQLException
     */
	public ContainerDetailsVO  map(ResultSet rs)
	 throws SQLException{
		String airport=rs.getString("ASGPRT");
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setPol(rs.getString("ASGPRT"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
		containerDetailsVO.setPaCode(rs.getString("SBCODE"));
		containerDetailsVO.setAssignmentDate(new LocalDate(airport,Location.ARP,rs.getTimestamp("ASGDAT")));
		return containerDetailsVO;
	}
	
	
}
