/*
 * DespatchEnqMapper.java Created on Jul 10, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * 
 * @author A-2391
 *
 */
public class DespatchEnqMapper implements Mapper<DespatchEnquiryVO> {

	/**
	 *
	 * Jan 24, 2007, A-2270
	 * @param rs
	 * @return RateLineErrorVO
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *
	 */
    public DespatchEnquiryVO map(ResultSet rs) throws SQLException {
    	DespatchEnquiryVO despatchEnquiryVO =
    		                        new DespatchEnquiryVO();

    	despatchEnquiryVO.setCsgDocNo(rs.getString("CSGDOCNUM"));
    	despatchEnquiryVO.setOrigin(rs.getString("ORGEXGOFC"));
    	despatchEnquiryVO.setDestn(rs.getString("DSTEXGOFC"));
    	despatchEnquiryVO.setCategory(rs.getString("MALCTGCOD"));
    	despatchEnquiryVO.setSubClass(rs.getString("MALSUBCLS"));
    	despatchEnquiryVO.setPieces(rs.getInt("TOTPCS"));
    	despatchEnquiryVO.setWeight(rs.getDouble("UPDGRSWGT"));
    	if(rs.getDate("FLTDAT")!= null){
    		despatchEnquiryVO.setDespatchDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,rs.getDate("FLTDAT")));
		}
    	despatchEnquiryVO.setRoute(rs.getString("ROU"));
    	
    	
    
    return despatchEnquiryVO;
    }

}










