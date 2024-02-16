/*
 * ULDDamageRepairMapper.java Created on Jun 19, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 *
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3093
 *
 */

public class ULDDamageRepairMapper implements Mapper<ULDRepairDetailsListVO>{

    private Log log = LogFactory.getLogger("ULD");
    private static final String INVOICED = "Invoiced";
    private static final String OPENED="Open";
    private static final String CLOSE="Close";
	/**
     *
     * @param rs
     * @return List<ULDRepairDetailsListVO>
     * @throws SQLException
     */
	public ULDRepairDetailsListVO map(ResultSet rs)
	throws SQLException {

		log.entering("ULDDamageRepairMapper","map");
		ULDRepairDetailsListVO listVO = new ULDRepairDetailsListVO();
		listVO.setCompanyCode(rs.getString("CMPCOD"));
		listVO.setUldNumber(rs.getString("ULDNUM"));
		listVO.setDamageStatus(rs.getString("DMGSTA"));
		//added by A-3278 for Bug 17985 on 07Sep08
		listVO.setDamageCode(rs.getString("DMGDES"));
		listVO.setSection(rs.getString("DMGSEC"));
		//a-3278 ends
		listVO.setRepairStatus(rs.getString("RPRSTA"));
		//listVO.setDamageCode(rs.getString("DMGCOD"));
		listVO.setPosition(rs.getString("DMGPOS"));
		//listVO.setDamageDate(rs.getString("DMGRPTDAT"));
		listVO.setRemarks(rs.getString("RMK"));
		listVO.setRepairHead(rs.getString("RPRHED"));

		listVO.setRepairAmount(rs.getDouble("RPRAMT"));
		Date dat = rs.getDate("RPRDAT");
		if(dat != null){
					listVO.setRepairDate((
							new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("RPRDAT"))));
		}
		
		Date date = rs.getDate("DMGRPTDAT");
		if(date != null){
					listVO.setDamageDate((
							new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("DMGRPTDAT"))));
		}
		
		listVO.setInvoicedAmount(rs.getDouble("NETAMT"));
		listVO.setInvoiceStatus(rs.getString("PAYSTA"));


		listVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		if(rs.getTimestamp("LSTUPDTIM") != null){
			listVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("LSTUPDTIM")));
		}

		listVO.setInvoiceReferenceNumber(rs.getString("INVREFNUM"));
		listVO.setInitialCost(rs.getDouble("ULDPRC"));
		
		if(rs.getString("INVREFNUM") !=null){
			//listVO.setInvoiceStatus(INVOICED);
			listVO.setInvoiceStatus(CLOSE);
		}else{
			listVO.setInvoiceStatus(OPENED);
		}
		return listVO;
	}
}
