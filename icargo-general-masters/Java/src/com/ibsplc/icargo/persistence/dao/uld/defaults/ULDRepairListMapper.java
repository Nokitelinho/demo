/*
 * ULDRepairListMapper.java Created on Jul 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author A-1347
 *
 */

/**
 * This class implements a Mapper<<ULDVO>
 * @author A-1347
 *
 */
public class ULDRepairListMapper implements Mapper<ULDRepairDetailsListVO>{

    private Log log = LogFactory.getLogger("ULD");
	/**
     *
     * @param rs
     * @return List<ULDDamageVO>
     * @throws SQLException
     */
	public ULDRepairDetailsListVO map(ResultSet rs)
	throws SQLException {

		log.entering(" ULDRepairListMapper","map");
		ULDRepairDetailsListVO listVO = new ULDRepairDetailsListVO();
		listVO.setCompanyCode(rs.getString("CMPCOD"));
		listVO.setDamageReferenceNumber(rs.getLong("DMGREFNUM"));
		listVO.setUldNumber(rs.getString("ULDNUM"));
		listVO.setCurrentStation(rs.getString("CURARP"));
		listVO.setOverallStatus(rs.getString("OVLSTA"));
		listVO.setRepairedStation(rs.getString("RPRARP"));
		listVO.setRepairHead(rs.getString("RPRHED"));
		listVO.setRemarks(rs.getString("RPRRMK"));
		listVO.setRepairSequenceNumber(rs.getLong("RPRSEQNUM"));
		listVO.setRepairStatus(rs.getString("RPRSTA"));
		listVO.setRepairAmount(rs.getDouble("RPRAMT"));
		//Added by A-7359 for ICRD-248560
		listVO.setCurrency(rs.getString("DISRPRCUR"));
		Date datOne = rs.getDate("RPRDAT");
		if(datOne != null && listVO.getRepairedStation() == null){
			listVO.setRepairDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,datOne));
		}else if(datOne != null && listVO.getRepairedStation() != null){
			listVO.setRepairDate(new LocalDate(listVO.getRepairedStation(),Location.ARP,datOne));
		}
		listVO.setLastUpdateUser(rs.getString("LSTUPDUSR")); 
		if(rs.getTimestamp("LSTUPDTIM") != null){
			listVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("LSTUPDTIM")));
		}

		listVO.setInvoiceReferenceNumber(rs.getString("INVREFNUM"));
		listVO.setInitialCost(rs.getDouble("ULDPRC"));
		return listVO;
	}
}
