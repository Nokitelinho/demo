/*
 * GPABillingDetailsMapper.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class GPABillingDetailsMapper implements Mapper<DocumentBillingDetailsVO> {
	private Log log = LogFactory.getLogger("MRA_GPABilling");
	/**
	 * @author A-2391
	 * @param rs
	 * @return GPABillingDetailsVO
	 * @throws SQLException
	 */
  
    public DocumentBillingDetailsVO map(ResultSet rs) throws SQLException {
    	DocumentBillingDetailsVO documentBillingDetailsVO=new DocumentBillingDetailsVO();
    	documentBillingDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
    	documentBillingDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
    	documentBillingDetailsVO.setBillingStatus(rs.getString("BLGSTA"));
    	documentBillingDetailsVO.setSerialNumber(rs.getInt("SEQNUM") );
    	documentBillingDetailsVO.setBillingBasis(rs.getString("BLGBAS"));
    	documentBillingDetailsVO.setGpaCode(rs.getString("COD"));
    	//documentBillingDetailsVO.setCountryCode(rs.getString("CNTCOD"));
    	log.log(Log.FINE, "before setting flight date");
    	if(rs.getDate("DAT") != null) {
    		documentBillingDetailsVO.setFlightDate(new LocalDate(
                    LocalDate.NO_STATION, Location.NONE, rs.getDate("DAT")));
        }
    	log
				.log(Log.FINE, "after setting flight date",
						documentBillingDetailsVO);
		documentBillingDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
    	documentBillingDetailsVO.setInvoiceNumber(rs.getString("INVNUM"));
    	documentBillingDetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
    	documentBillingDetailsVO.setCsgSequenceNumber(rs.getInt("CSGSEQNUM"));
    	documentBillingDetailsVO.setPoaCode(rs.getString("POACOD"));
    	documentBillingDetailsVO.setOrigin(rs.getString("ORG"));
    	documentBillingDetailsVO.setDestination(rs.getString("DST"));
    	documentBillingDetailsVO.setCategory(rs.getString("CTGCOD"));
    	documentBillingDetailsVO.setSubClass(rs.getString("SUBCLS"));
    	documentBillingDetailsVO.setYear(rs.getString("YEAR"));
    	documentBillingDetailsVO.setDsn(rs.getString("DSN"));
    	documentBillingDetailsVO.setNoofMailbags(rs.getString("PCS"));
    	documentBillingDetailsVO.setWeight(rs.getDouble("WGT"));
    	documentBillingDetailsVO.setCcaType(rs.getString("CCATYP"));
    	documentBillingDetailsVO.setCcaRefNumber(rs.getString("CCAREFNO"));
    	if(rs.getTimestamp("LSTUPDTIM") != null) {
    		documentBillingDetailsVO.setLastUpdatedTime(new LocalDate(
                    LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("LSTUPDTIM")));
        }
    	return documentBillingDetailsVO;
    }

}
