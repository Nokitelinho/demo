/*
 * FindTransferFromInfoFromCarditMapper.java Created on 08, Jul 2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
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
 * @author A-3251
 *
 */
public class FindTransferFromInfoFromCarditMapper implements Mapper<MailbagVO>{

	public MailbagVO map(ResultSet rs) throws SQLException {
		MailbagVO mailbagVO = new MailbagVO();
		
	    if(rs.getDate("RCVDAT") != null) {
	    	mailbagVO.setFromFlightDate((new LocalDate(
                    LocalDate.NO_STATION, Location.NONE,
                    rs.getDate("RCVDAT"))));
        }		
		
		mailbagVO.setTransferFromCarrier(rs.getString("RCVCARCOD"));		
		mailbagVO.setFromFightNumber(rs.getString("FLTNUM"));//Added by a-7871 for ICRD-240184
		mailbagVO.setPou(rs.getString("POU"));   
		return mailbagVO;
	}
}
