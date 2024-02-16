/*
 * GPABillingDetailsMapper.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1556
 *
 */
public class GPABillingDetailsMapper implements Mapper<GPABillingDetailsVO> {

	/**
	 * @author A-2391
	 * @param rs
	 * @return GPABillingDetailsVO
	 * @throws SQLException
	 */
  
    public GPABillingDetailsVO map(ResultSet rs) throws SQLException {
    	GPABillingDetailsVO gpaBillingDetailsVO=new GPABillingDetailsVO();
    	gpaBillingDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
    	gpaBillingDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
    	gpaBillingDetailsVO.setBillingStatus(rs.getString("BLGSTA"));
    	gpaBillingDetailsVO.setSequenceNumber(rs.getInt("SEQNUM") );
    	gpaBillingDetailsVO.setBillingBasis(rs.getString("BLGBAS"));
    	gpaBillingDetailsVO.setGpaCode(rs.getString("GPACOD"));
    	gpaBillingDetailsVO.setCountryCode(rs.getString("CNTCOD"));


    	if(rs.getDate("DAT") != null) {
    		gpaBillingDetailsVO.setReceivedDate(new LocalDate(
                    LocalDate.NO_STATION, Location.NONE, rs.getDate("DAT")));  
        }
    	gpaBillingDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
    	gpaBillingDetailsVO.setInvoiceNumber(rs.getString("INVNUM"));
    	gpaBillingDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
    	gpaBillingDetailsVO.setOriginOfficeOfExchange(rs.getString("ORG"));
    	gpaBillingDetailsVO.setDestinationOfficeOfExchange(rs.getString("DST"));
    	gpaBillingDetailsVO.setMailCategoryCode(rs.getString("CTGCOD"));
    	gpaBillingDetailsVO.setMailSubclass(rs.getString("SUBCLS"));
    	gpaBillingDetailsVO.setYear(rs.getInt("YEAR"));
    	gpaBillingDetailsVO.setDsn(rs.getString("DSN"));
    	gpaBillingDetailsVO.setPiecesReceived(rs.getInt("PCS"));
    	gpaBillingDetailsVO.setCcaType(rs.getString("CCATYP"));
    	if(gpaBillingDetailsVO.getCcaType()!=null){
    		gpaBillingDetailsVO.setWeightReceived(rs.getDouble("REVGRSWGT"));  
    	}else{
    	gpaBillingDetailsVO.setWeightReceived(rs.getDouble("WGT"));  
    	}
		if(rs.getDouble("WGT")== 0.0){
			gpaBillingDetailsVO.setWeightReceived(rs.getDouble("REVGRSWGT"));
		}
    	//Added as part of ICRD-111958 starts
    	gpaBillingDetailsVO.setApplicableRate(rs.getDouble("APLRAT"));
    	gpaBillingDetailsVO.setGrossAmount(rs.getDouble("GRSAMT"));
    	gpaBillingDetailsVO.setNetAmount(rs.getDouble("NETAMT"));
    	//Added as part of ICRD-120502 starts
    	if(gpaBillingDetailsVO.getNetAmount()==0){
    		gpaBillingDetailsVO.setExtraWeight(gpaBillingDetailsVO.getWeightReceived());
    	}
    	//Added as part of ICRD-120502 ends
    	gpaBillingDetailsVO.setValCharges(rs.getDouble("VALCHGCTR"));
    	gpaBillingDetailsVO.setDeclaredValue(rs.getDouble("DCLVAL"));
    	gpaBillingDetailsVO.setVatAmount(rs.getDouble("SRVTAX"));
    	gpaBillingDetailsVO.setReceptacleSerialNumber(rs.getString("RSN"));
    	//Added as part of ICRD-111958 ends
    	//Added as part of ICRD-115765 starts
    	gpaBillingDetailsVO.setCurrencyCode(rs.getString("CTRCURCOD"));
    	//Added as part of ICRD-115765 ends
    	gpaBillingDetailsVO.setBillingCurrency(rs.getString("STLCURCOD"));
    	gpaBillingDetailsVO.setNetAmtBillingCurrency(rs.getDouble("TOTAMT"));
    	gpaBillingDetailsVO.setBillingCurrency(rs.getString("STLCURCOD"));
    	gpaBillingDetailsVO.setNetAmtBillingCurrency(rs.getDouble("TOTAMT"));
    	gpaBillingDetailsVO.setTotalValCharges(rs.getDouble("TOTVAL"));
    	gpaBillingDetailsVO.setTotalVatAmount(rs.getDouble("TOTVAT"));
    	gpaBillingDetailsVO.setTotalGrsAmount(rs.getDouble("TOTGRSAMT"));
    	gpaBillingDetailsVO.setDisplayWgtUnit(rs.getString("DSPWGTUNT"));
    	
        return gpaBillingDetailsVO;
    }

}
