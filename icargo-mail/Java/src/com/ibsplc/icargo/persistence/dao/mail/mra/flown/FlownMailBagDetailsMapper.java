/*
 * FlownMailBagDetailsMapper.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.flown;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.flown.vo.MailBagForFlownSegmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;


/**
 * This Class is for FlownMailBagDetailsMapper
 * @author A-2338
 *
 */
public class FlownMailBagDetailsMapper implements Mapper<MailBagForFlownSegmentVO> {

	 /**
     * @param resultSet
     * @return MailBagForFlownSegmentVO
     * @throws SQLException
     */
    public MailBagForFlownSegmentVO map(ResultSet resultSet) throws SQLException {
    	MailBagForFlownSegmentVO mailBagForFlownSegmentVO = 
    		new MailBagForFlownSegmentVO();

    	mailBagForFlownSegmentVO.setCompanyCode(resultSet.getString("CMPCOD"));
    	mailBagForFlownSegmentVO.setFlightNumber(resultSet.getString("FLTNUM"));
    	mailBagForFlownSegmentVO.setFlightCarrierId(resultSet.getInt("FLTCARIDR"));
    	mailBagForFlownSegmentVO.setFlightSequenceNumber(resultSet.getInt("FLTSEQNUM"));
    	mailBagForFlownSegmentVO.setSegmentSerialNumber(resultSet.getInt("SEGSERNUM"));
    	mailBagForFlownSegmentVO.setOriginOfficeOfExchange(resultSet.getString("ORGEXGOFC"));
    	mailBagForFlownSegmentVO.setDestinationOfficeOfExchange(resultSet.getString("DSTEXGOFC"));
    	   	
    	mailBagForFlownSegmentVO.setMailCategoryCode(resultSet.getString("MALCTGCOD"));
    	mailBagForFlownSegmentVO.setMailIdentifier(resultSet.getString("MALIDR"));
    	mailBagForFlownSegmentVO.setMailSubclass(resultSet.getString("MALSUBCLS"));
    	mailBagForFlownSegmentVO.setDsnNumber(resultSet.getString("DSN"));
    	mailBagForFlownSegmentVO.setMailBagWeight(resultSet.getFloat("DSPWGT"));
    	mailBagForFlownSegmentVO.setReceptacleSerialNumber(resultSet.getString("RSN"));
    	mailBagForFlownSegmentVO.setHighestReceptacleNumber(resultSet.getString("HSN"));
    	mailBagForFlownSegmentVO.setRegisteredIndicator(resultSet.getString("REGIND"));
        	
    	mailBagForFlownSegmentVO.setYear(resultSet.getInt("YER"));
    	
    	mailBagForFlownSegmentVO.setSegmentStatus(resultSet.getString("SEGSTA"));
    	mailBagForFlownSegmentVO.setMailBagSegmentStatus(resultSet.getString("MALSEGSTA"));

    	mailBagForFlownSegmentVO.setDisplayWgtUnit(resultSet.getString("DSPWGTUNT"));
    	
    	return mailBagForFlownSegmentVO;
    	
        
    }

}
