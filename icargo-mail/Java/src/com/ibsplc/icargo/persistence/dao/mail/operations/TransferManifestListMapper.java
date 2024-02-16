/*
 * TransferManifestListMapper.java  Created on 03 April 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * 
 * @author a-1936
 *
 */
public class TransferManifestListMapper implements Mapper<TransferManifestVO> {

	/**
	 * @author a-1936
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public TransferManifestVO map(ResultSet rs) throws SQLException {
		TransferManifestVO transferManifestVo = new TransferManifestVO();
		transferManifestVo.setCompanyCode(rs.getString("CMPCOD"));
		transferManifestVo.setTransferManifestId(rs.getString("TRFMFTIDR"));
		transferManifestVo.setTransferredFromCarCode(rs.getString("FRMCARCOD"));
		transferManifestVo.setTransferredToCarrierCode(rs.getString("TOCARCOD"));
		if("-1".equals(rs.getString("TOFLTNUM"))){
			transferManifestVo.setTransferredToFltNumber("");
		}else{
		transferManifestVo.setTransferredToFltNumber(rs.getString("TOFLTNUM"));
		}
		if("-1".equals(rs.getString("FRMFLTNUM"))){
			transferManifestVo.setTransferredFromFltNum("");
		}else{
		transferManifestVo.setTransferredFromFltNum(rs.getString("FRMFLTNUM"));
		}
		transferManifestVo.setTotalBags(rs.getInt("TOTBAG"));
		//transferManifestVo.setTotalWeight(rs.getDouble("TOTWGT"));
		transferManifestVo.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("TOTWGT")));//added by A-7371
		transferManifestVo.setAirPort(rs.getString("ARPCOD"));
		if(rs.getDate("TRFDAT")!=null){
			transferManifestVo.setTransferredDate(new LocalDate(transferManifestVo.getAirPort(),Location.ARP,rs.getTimestamp("TRFDAT")));	
		}
		if(rs.getDate("FRMDAT")!=null){
			transferManifestVo.setFromFltDat(new LocalDate(transferManifestVo.getAirPort(),Location.ARP,rs.getDate("FRMDAT")));	
		}
		if(rs.getDate("TODAT")!=null){
			transferManifestVo.setToFltDat(new LocalDate(transferManifestVo.getAirPort(),Location.ARP,rs.getDate("TODAT")));
		}
		if(rs.getString("TRFSTA")!=null && rs.getString("TRFSTA").equals("TRFINT")){
			transferManifestVo.setTransferStatus("Transfer Initiated");
		}else if(rs.getString("TRFSTA")!=null && rs.getString("TRFSTA").equals("TRFREJ")){
			transferManifestVo.setTransferStatus("Transfer Rejected");
		}
		else if(rs.getString("TRFSTA")!=null && rs.getString("TRFSTA").equals("TRFEND")){
			transferManifestVo.setTransferStatus("Transfer Ended");
		}
		transferManifestVo.setTransferredfrmSegSerNum(rs.getInt("FRMSEGSERNUM"));
		transferManifestVo.setTransferredfrmFltSeqNum(rs.getLong("FRMFLTSEQNUM"));
		return transferManifestVo;
	}
}
