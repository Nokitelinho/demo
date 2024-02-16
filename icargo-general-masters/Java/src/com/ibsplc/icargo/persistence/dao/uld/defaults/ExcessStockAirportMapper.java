/*
 * ExcessStockAirportMapper.java Created on Oct 22, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.PageAwareMultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2934
 *
 */
public class ExcessStockAirportMapper implements Mapper<ExcessStockAirportVO>{
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ULDStockListVO
	 * @throws SQLException
	 */
	public ExcessStockAirportVO map(ResultSet resultSet) 
	throws SQLException {
		log.entering("ExcessStockAirportMapper","Map");  
		// List<EstimatedULDStockVO> listVOs = new ArrayList<EstimatedULDStockVO>();
		ExcessStockAirportVO listVO = new ExcessStockAirportVO();
		   //listVO.setCompanyCode(resultSet.getString("CMPCOD"));
			listVO.setUldTypeCode(resultSet.getString("ARPCOD"));
			listVO.setCurrentAvailability(resultSet.getString("CURRENTAVAILABILITY"));
		   listVO.setUcmUldOut(resultSet.getString("ULDSOUT"));
		   listVO.setUcmUldIn(resultSet.getString("ULDSIN"));
		   listVO.setProjectedULDCount(resultSet.getString("PROJECTED"));
		   listVO.setMinimumQuantity(resultSet.getString("MAXIMUMQTY"));
		   listVO.setStockDeviation(resultSet.getString("STOCKDEVIATION"));
		//   listVOs.add(listVO);
		   return listVO;
	}
}
