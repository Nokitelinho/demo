/*
 * FindULDStockListMapper.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class EstimatedULDStockMapper implements Mapper<EstimatedULDStockVO>{
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ULDStockListVO
	 * @throws SQLException
	 */
	public EstimatedULDStockVO map(ResultSet resultSet) 
	throws SQLException {
		log.entering("EstimatedULDStockMapper","Map");  
		// List<EstimatedULDStockVO> listVOs = new ArrayList<EstimatedULDStockVO>();
		EstimatedULDStockVO listVO = new EstimatedULDStockVO();
		   //listVO.setCompanyCode(resultSet.getString("CMPCOD"));
			listVO.setUldTypeCode(resultSet.getString("ULDTYP"));   
			listVO.setCurrentAvailability(resultSet.getString("CURRENTAVAILABILITY"));
		   listVO.setUcmUldOut(resultSet.getString("ULDSOUT"));
		   listVO.setUcmUldIn(resultSet.getString("ULDSIN"));
		   listVO.setProjectedULDCount(resultSet.getString("PROJECTED"));
		   //Modified by A-3415 for ICRD-114073- Starts
		   listVO.setMaximumQuantity(resultSet.getString("MAXIMUMQTY"));
		   listVO.setStockDeviation(resultSet.getString("STOCKDEVIATION"));
		   listVO.setMinimumQuantity(resultSet.getString("MINIMUMQTY"));
		   listVO.setMinimumStockDeviation(resultSet.getString("MINSTOCKDEVIATION"));
		   listVO.setAirportCode(resultSet.getString("CURARP"));
		   //Modified by A-3415 for ICRD-114073- Ends
		//   listVOs.add(listVO);
		   return listVO;
	}
}
