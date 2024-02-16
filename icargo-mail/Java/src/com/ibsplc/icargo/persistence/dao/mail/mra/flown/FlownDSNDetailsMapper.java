/*
 * FlownDSNDetailsMapper.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.flown;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.flown.vo.DSNForFlownSegmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;


/**
 * This Class is for FlownDSNDetailsMapper
 * @author A-2338
 *
 */
public class FlownDSNDetailsMapper implements Mapper<DSNForFlownSegmentVO> {

	 /**
     * @param resultSet
     * @return DSNForFlownSegmentVO
     * @throws SQLException
     */
    public DSNForFlownSegmentVO map(ResultSet resultSet) throws SQLException {
    	DSNForFlownSegmentVO dSNForFlownSegmentVO=new DSNForFlownSegmentVO();
    	dSNForFlownSegmentVO.setAcceptedMailBagCount(resultSet.getInt("ACPBAG"));
    	dSNForFlownSegmentVO.setAcceptedMailBagWeight(resultSet.getFloat("ACPWGT"));
    	dSNForFlownSegmentVO.setCompanyCode(resultSet.getString("CMPCOD"));
    	dSNForFlownSegmentVO.setFlightNumber(resultSet.getString("FLTNUM"));
    	dSNForFlownSegmentVO.setConsignmentDocumentNumber(resultSet.getString("CSGDOCNUM"));
        dSNForFlownSegmentVO.setDestinationOfficeOfExchange(resultSet.getString("DSTEXGOFC"));
    	dSNForFlownSegmentVO.setDsnNumber(resultSet.getString("DSN"));
       	dSNForFlownSegmentVO.setFlightCarrierId(resultSet.getInt("FLTCARIDR"));
    	dSNForFlownSegmentVO.setFlightSequenceNumber(resultSet.getInt("FLTSEQNUM"));
    	dSNForFlownSegmentVO.setMailCategoryCode(resultSet.getString("MALCTGCOD"));
    	dSNForFlownSegmentVO.setMailSubclass(resultSet.getString("MALSUBCLS"));
    	dSNForFlownSegmentVO.setActualSubClass(resultSet.getString("SUBCLSGRP"));
    	dSNForFlownSegmentVO.setOriginOfficeOfExchange(resultSet.getString("ORGEXGOFC"));
       	dSNForFlownSegmentVO.setSegmentSerialNumber(resultSet.getInt("SEGSERNUM"));
       	dSNForFlownSegmentVO.setYear(resultSet.getInt("YER"));
       	
       	dSNForFlownSegmentVO.setSegmentStatus(resultSet.getString("SEGSTA"));
       	dSNForFlownSegmentVO.setDsnSegmentStatus(resultSet.getString("DSNSTA"));
       
    	
        return dSNForFlownSegmentVO;
    }

}
