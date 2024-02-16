/*
 * ULDInventoryListMultiMapper.java Created on Sep 12, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ULDInventoryListMultiMapper  implements MultiMapper<ULDListVO> {
	private Log log= LogFactory.getLogger("ULD_DEFAULTS");
	public List<ULDListVO> map(ResultSet rs) throws SQLException{
		log.entering("ULDInventoryListMapper","ULDInventoryListMapper");
		List<ULDListVO> uldListVos = new ArrayList<ULDListVO>();  
		while(rs.next()){
			ULDListVO uldListVO =new ULDListVO();
			uldListVO.setCompanyCode(rs.getString("CMPCOD")); 
			uldListVO.setUldNumber(rs.getString("ULDNUM"));		
			uldListVO.setUldTypeCode(rs.getString("ULDTYP"));
			uldListVO.setCurrentStation(rs.getString("CURARP"));            
			uldListVO.setUldTypeNumber(rs.getString("ULDNUMPRT"));    
			uldListVO.setOwnerAirlineCode(rs.getString("TWOAPHCOD"));   
			uldListVO.setOperatingAirline(rs.getString("TWOAPHCOD"));     
			uldListVos.add(uldListVO);  
		}
		log.exiting("ULDInventoryListMapper","ULDInventoryListMapper");   
		return uldListVos;
	} 
}
