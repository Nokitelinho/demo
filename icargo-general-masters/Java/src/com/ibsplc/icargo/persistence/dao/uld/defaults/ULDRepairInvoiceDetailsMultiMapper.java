/*
 * ULDRepairInvoiceDetailsMultiMapper.java Created on Oct 5, 2005
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
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 * This mapper is used for Find Repair Invoice Details
 */
public class ULDRepairInvoiceDetailsMultiMapper 
	implements MultiMapper<ULDRepairInvoiceDetailsVO>{
	
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ULDTransactionDetailsVO
	 * @throws SQLException
	 */
	public List<ULDRepairInvoiceDetailsVO> map(ResultSet resultSet) 
		throws SQLException {
		
		log.entering("RepairInvoiceDetailsMapper","Map");
		List<ULDRepairInvoiceDetailsVO> uLDRepairInvoiceDetailsVOs = null;
		String previousRepairDetailPK = "";
		String currentRepairDetailPK = "";
		Collection<Integer> damageRefNumbers = null;
		ULDRepairInvoiceDetailsVO uLDRepairInvoiceDetailsVO = null;
		StringBuilder buffer = null;
		while(resultSet.next()){
			buffer = new StringBuilder(resultSet.getString("CMPCOD")).
					append(resultSet.getString("ULDNUM"))
					.append(resultSet.getInt("RPRSEQNUM"));
			currentRepairDetailPK = buffer.toString();
			log.log(Log.FINE, "Current PK -->", currentRepairDetailPK);
			if(!currentRepairDetailPK.equals(previousRepairDetailPK)){
				uLDRepairInvoiceDetailsVO = new ULDRepairInvoiceDetailsVO();
				uLDRepairInvoiceDetailsVO.setCompanyCode(
						resultSet.getString("CMPCOD"));
				uLDRepairInvoiceDetailsVO.setRepairSeqNumber(
						resultSet.getInt("RPRSEQNUM"));
				uLDRepairInvoiceDetailsVO.setUldNumber(
						resultSet.getString("ULDNUM"));
				uLDRepairInvoiceDetailsVO.setRepairHead(
						resultSet.getString("RPRHED"));
				uLDRepairInvoiceDetailsVO.setActualAmount(
						resultSet.getDouble("RPRAMT"));
				uLDRepairInvoiceDetailsVO.setRepairRemarks(
						resultSet.getString("RPRRMK"));
				uLDRepairInvoiceDetailsVO.setRepairStation(
						resultSet.getString("RPRARP"));
				uLDRepairInvoiceDetailsVO.setWaivedAmount(
						resultSet.getDouble("WVDAMT"));
				// Added by Sreekumar S on 29th Oct
				if(resultSet.getTimestamp("LSTUPDTIM") != null){
					uLDRepairInvoiceDetailsVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION , Location.NONE , resultSet.getTimestamp("LSTUPDTIM")));
				}
				//ends
				//Added by Sreekumar on 13th August
				uLDRepairInvoiceDetailsVO.setInvoicedAmount((resultSet.getDouble("RPRAMT"))-(resultSet.getDouble("WVDAMT")));
					damageRefNumbers = new ArrayList<Integer>();
					if(uLDRepairInvoiceDetailsVOs == null){
						uLDRepairInvoiceDetailsVOs = 
							new ArrayList<ULDRepairInvoiceDetailsVO>();
					}
				uLDRepairInvoiceDetailsVOs.add(uLDRepairInvoiceDetailsVO);
			}
			Integer damageRefNumber = resultSet.getInt("DMGREFNUM");
			damageRefNumbers.add(damageRefNumber);
			uLDRepairInvoiceDetailsVO.setDamageRefNumbers(damageRefNumbers);
			previousRepairDetailPK = currentRepairDetailPK;
			
		}// end of while
		
		log.exiting("RepairInvoiceDetailsMapper","Map");
		return uLDRepairInvoiceDetailsVOs;
	}
}
