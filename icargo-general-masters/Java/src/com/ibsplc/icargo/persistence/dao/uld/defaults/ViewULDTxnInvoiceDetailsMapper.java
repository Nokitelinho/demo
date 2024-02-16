/*
 * ViewULDTxnInvoiceDetailsMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 * This mapper is used for viewing the details of Invoice
 */
public class ViewULDTxnInvoiceDetailsMapper implements 
Mapper<ULDTransactionDetailsVO> {
	
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ULDTransactionDetailsVO
	 * @throws SQLException
	 */
	public ULDTransactionDetailsVO map(ResultSet resultSet) 
	throws SQLException {
		
		log.entering("ViewULDTxnInvoiceDetailsMapper","map");
		ULDTransactionDetailsVO uLDTransactionDetailsVO = 
			new ULDTransactionDetailsVO();
		//Added by A-7359 for ICRD-248560 starts here
		uLDTransactionDetailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
		uLDTransactionDetailsVO.setOperationalAirlineIdentifier(Integer.valueOf(resultSet.getString("PTYIDR")));
		uLDTransactionDetailsVO.setCurrency(resultSet.getString("CURCOD"));
		//Added by A-7359 for ICRD-248560 ends here
		uLDTransactionDetailsVO.setUldNumber(resultSet.getString("ULDNUM"));
		uLDTransactionDetailsVO.setTransactionStationCode(
				resultSet.getString("TXNARPCOD"));
		uLDTransactionDetailsVO.setPartyType(resultSet.getString("PTYTYP"));
		uLDTransactionDetailsVO.setToPartyCode(resultSet.getString("PTYCOD"));
		uLDTransactionDetailsVO.setFromPartyCode(resultSet.getString("RTNARPCOD"));
		Date txnDate = resultSet.getDate("TXNDAT");
		if(txnDate != null && uLDTransactionDetailsVO.getTransactionStationCode() == null){
			uLDTransactionDetailsVO.setTransactionDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,txnDate));
		}else if(txnDate != null && uLDTransactionDetailsVO.getTransactionStationCode() != null){
			uLDTransactionDetailsVO.setTransactionDate(new LocalDate(uLDTransactionDetailsVO.getTransactionStationCode(),
																		Location.ARP,txnDate));
		}
		Date retDate = resultSet.getDate("RTNDAT");
		if(retDate != null){
			uLDTransactionDetailsVO.setReturnDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,retDate));
		}
		uLDTransactionDetailsVO.setDemurrageAmount(
				resultSet.getDouble("DMRAMT"));
		uLDTransactionDetailsVO.setTaxes(resultSet.getDouble("TAXAMT"));
		uLDTransactionDetailsVO.setOtherCharges(resultSet.getDouble("OTRCRG"));
		uLDTransactionDetailsVO.setWaived(resultSet.getDouble("WVRAMT"));
		uLDTransactionDetailsVO.setTotal(resultSet.getDouble("TOTAMT"));
		uLDTransactionDetailsVO.setTransactionRemark(
				resultSet.getString("TXNRMK"));
		
		log.exiting("ViewULDTxnInvoiceDetailsMapper","map");
		return uLDTransactionDetailsVO;
	}

}
