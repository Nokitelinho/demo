/*
 * ListAccessoryTransactionMapper.java Created on Jan 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 * This mapper is for list accessory details transaction
 */
public class ListAccessoryTransactionMapper implements Mapper<AccessoryTransactionVO> {
	
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return AccessoryTransactionVO
	 * @throws SQLException
	 */
	public AccessoryTransactionVO map(ResultSet resultSet) throws SQLException {
		log.entering("ListAccessoryTransactionMapper","map");
		AccessoryTransactionVO accessoryTransactionVO = new AccessoryTransactionVO();
		accessoryTransactionVO.setAccessoryCode(resultSet.getString("ACCCOD"));
		accessoryTransactionVO.setQuantity(resultSet.getInt("ACCQTY"));
		accessoryTransactionVO.setTransactionType(resultSet.getString("TXNTYP"));
		accessoryTransactionVO.setPartyType(resultSet.getString("PTYTYP"));
		accessoryTransactionVO.setToPartyCode(resultSet.getString("PTYCOD"));		
		accessoryTransactionVO.setTransactionStationCode(resultSet.getString("TXNARPCOD"));
		Date date = resultSet.getDate("TXNDAT");
		if(date != null && accessoryTransactionVO.getTransactionStationCode() != null){
			LocalDate txnDate = new LocalDate(accessoryTransactionVO.getTransactionStationCode(),Location.ARP,date);	
			accessoryTransactionVO.setTransactionDate(txnDate);
			accessoryTransactionVO.setStrTxnDate(txnDate.toDisplayFormat(CALENDAR_DATE_FORMAT));
		}else if(date != null && accessoryTransactionVO.getTransactionStationCode() == null){
			LocalDate txnDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,date);	
			accessoryTransactionVO.setTransactionDate(txnDate);
			accessoryTransactionVO.setStrTxnDate(txnDate.toDisplayFormat(CALENDAR_DATE_FORMAT));
		}
		accessoryTransactionVO.setTransactionRemark(resultSet.getString("TXNRMK"));
		accessoryTransactionVO.setTransactionRefNumber(resultSet.getInt("TXNREFNUM"));
		accessoryTransactionVO.setOperationalAirlineIdentifier(Integer.
					valueOf(resultSet.getString("PTYIDR")));
		accessoryTransactionVO.setCompanyCode(resultSet.getString("CMPCOD"));
		accessoryTransactionVO.setCurrOwnerCode(resultSet.getInt("CUROWNCOD"));
		accessoryTransactionVO.setFromPartyCode(resultSet.getString("FRMPTYCOD"));
		log.exiting("ListAccessoryTransactionMapper","map");
		return accessoryTransactionVO;
	}
}
