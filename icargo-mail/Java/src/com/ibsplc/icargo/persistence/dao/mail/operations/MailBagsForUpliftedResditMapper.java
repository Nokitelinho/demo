/*
 * MailBagsForUpliftedResditMapper.java Created on Aug 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-1936
 *
 */
public class MailBagsForUpliftedResditMapper implements Mapper<MailbagVO> {
    
	/**
     * This class is used to map the ResultSet into the MailBagVo
     * @param rs
     * @return 
     * @throws SQLException
     */
	public MailbagVO  map(ResultSet rs)
	 throws SQLException{
		String airport=rs.getString("SCNPRT");
		MailbagVO mailBagVo = new MailbagVO();
		mailBagVo.setCompanyCode(rs.getString("CMPCOD"));
		mailBagVo.setMailbagId(rs.getString("MALIDR"));
		mailBagVo.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailBagVo.setScannedPort(airport);
		mailBagVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailBagVo.setContainerNumber(rs.getString("ULDNUM"));    
		mailBagVo.setScannedDate(new LocalDate(airport,Location.ARP,rs.getDate("SCNDAT")));
		mailBagVo.setLatestStatus(rs.getString("MALSTA"));
		mailBagVo.setContainerType(rs.getString("CONTYP"));
		//Added as part of bug ICRD-144734 by A-5526 starts
		mailBagVo.setPou(rs.getString("POU"));
		mailBagVo.setDoe(rs.getString("DSTEXGOFC"));
		//Added as part of bug ICRD-144734 by A-5526 ends
		mailBagVo.setOoe(rs.getString("ORGEXGOFC"));
		mailBagVo.setMailCategoryCode(rs.getString("MALCTG"));
		mailBagVo.setMailSubclass(rs.getString("MALSUBCLS"));
		//added by A-8353 for ICRD-346933
		
		mailBagVo.setOrigin(rs.getString("ORGCOD"));
		mailBagVo.setDestination(rs.getString("DSTCOD"));
		mailBagVo.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailBagVo.setPaCode(rs.getString("POACOD"));
        mailBagVo.setCarrierCode(rs.getString("FLTCARCOD"));
		return mailBagVo;
	}
	
	
}
