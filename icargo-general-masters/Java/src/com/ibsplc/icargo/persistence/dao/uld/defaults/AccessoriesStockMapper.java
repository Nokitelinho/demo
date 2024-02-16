/*
 * AccessoriesStockMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author A-1347
 *
 */

/**
 * This class implements a Mapper<AccessoriesStockConfigVO>
 * @author A-1347
 *
 */
public class AccessoriesStockMapper implements Mapper<AccessoriesStockConfigVO> {

	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     */
	public AccessoriesStockConfigVO map(ResultSet rs) throws SQLException {
	   log.entering("AccessoriesStockMapper","map");
	   AccessoriesStockConfigVO accessoriesStockConfigVO = new
	   		AccessoriesStockConfigVO();
	   accessoriesStockConfigVO.setCompanyCode(rs.getString("CMPCOD"));
	   accessoriesStockConfigVO.setAirlineCode(rs.getString("ARLCOD"));
	   accessoriesStockConfigVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
	   accessoriesStockConfigVO.setStationCode(rs.getString("ARPCOD"));
	   accessoriesStockConfigVO.setAccessoryCode(rs.getString("ACCCOD"));
	   accessoriesStockConfigVO.setAccessoryDescription(rs.getString("ACCDES"));
	   accessoriesStockConfigVO.setAvailable(rs.getInt("AVLACC"));
	   accessoriesStockConfigVO.setLoaned(rs.getInt("LNDACC"));
	   accessoriesStockConfigVO.setMinimumQuantity(rs.getInt("MINQTY"));
	   accessoriesStockConfigVO.setRemarks(rs.getString("RMK"));
	   accessoriesStockConfigVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
	   accessoriesStockConfigVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
	   log.exiting("AccessoriesStockMapper","map");
		return accessoriesStockConfigVO;

	}

}
