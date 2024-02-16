/*
 * CarditMailbagDetailsMapper.java Created on Jan13,2009
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
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;



/**
 * @author a-2107
 * This class is used to map the ResultSet into Vos returned to the Request...
 */
public class CarditMailbagDetailsMapper implements Mapper<MailbagVO> {

    	public MailbagVO map(ResultSet rs) throws SQLException {
    		MailbagVO mailbagVO = new MailbagVO();
    		mailbagVO.setMailbagId(rs.getString("MALIDR"));
    		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
    		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
			mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
			mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
			mailbagVO.setMailSubclass( rs.getString("MALSUBCLS"));
			mailbagVO.setYear(rs.getInt("YER"));
			mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
			mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
			mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
			mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
			Measure strWt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"));
			mailbagVO.setStrWeight(strWt);//modified by A-7371
			mailbagVO.setWeight(strWt);
			mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
			mailbagVO.setMailDestination(rs.getString("DSTCOD"));
			mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("SCNDAT")));
			mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
			return mailbagVO;
		}
}
