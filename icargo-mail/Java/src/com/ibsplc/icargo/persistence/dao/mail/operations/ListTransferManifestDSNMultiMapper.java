/*
 * ListTransferManifestDSNMultiMapper.java Created on  Apr 03 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The mapper for fetching transfer manifest details
 * 
 * @author a-2553
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 *03 Apr 2008 Paulson First Draft
 */
public class ListTransferManifestDSNMultiMapper implements
		MultiMapper<TransferManifestVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<TransferManifestVO> map(ResultSet rs) throws SQLException {

		List<TransferManifestVO>  transferManifestVOs =new ArrayList<TransferManifestVO>();
		DSNVO dsnVO = null;
		Collection<DSNVO> dsnVOs = new ArrayList<DSNVO>();
		String currDSNKey = null;
		String prevDSNKey = null;
		while (rs.next()) {
			TransferManifestVO transferManifestVO = new TransferManifestVO();
			
			currDSNKey = new StringBuilder().append(rs.getString("CMPCOD"))
						.append(rs.getString("TRFMFTIDR")).append(
								rs.getString("DSN")).append(rs.getString("ORGEXGOFC"))
								.append(rs.getString("DSTEXGOFC")).append(
										rs.getString("MALSUBCLS")).append(
												rs.getString("MALCTGCOD")).append(rs.getInt("YER"))
												.toString();
			log.log(Log.FINE, "CurrDSNKey ", currDSNKey);
			//if (rs.getString("DSN") != null && !currDSNKey.equals(prevDSNKey)) {
				dsnVO = new DSNVO();
				populateDSNDetails(dsnVO, rs);
				dsnVOs.add(dsnVO);
				prevDSNKey = currDSNKey;
				transferManifestVO.setCompanyCode(rs.getString("CMPCOD"));
				transferManifestVO.setTransferManifestId(rs.getString("TRFMFTIDR"));
				transferManifestVO.setAirPort(rs.getString("ARPCOD"));
				transferManifestVO.setTransferredFromCarCode(rs.getString("FRMCARCOD"));
				transferManifestVO.setTransferredToCarrierCode(rs.getString("TOCARCOD"));
				if("-1".equals(rs.getString("FRMFLTNUM"))){
                transferManifestVO.setTransferredFromFltNum(rs.getString(""));
                 }	else{
                transferManifestVO.setTransferredFromFltNum(rs.getString("FRMFLTNUM"));
                 }
				if("-1".equals(rs.getString("TOFLTNUM"))){
					transferManifestVO.setTransferredToFltNumber("");
				}else{
				transferManifestVO.setTransferredToFltNumber(rs.getString("TOFLTNUM"));
				}
				
				if(rs.getDate("TRFDAT")!=null){
					transferManifestVO.setTransferredDate(new LocalDate(transferManifestVO.getAirPort(),Location.ARP,rs.getTimestamp("TRFDAT")));	
				}
				if(rs.getDate("TODAT")!=null){
					transferManifestVO.setToFltDat(new LocalDate(transferManifestVO.getAirPort(),Location.ARP,rs.getTimestamp("TODAT")));	
				}
				transferManifestVO.setMailsequenceNumber(rs.getLong("MALSEQNUM"));
				transferManifestVO.setContainerNumber(rs.getString("CONNUM"));
			//}
				transferManifestVO.setTransferredfrmFltSeqNum(rs.getLong("FRMFLTSEQNUM"));
				transferManifestVO.setTransferredfrmSegSerNum(rs.getInt("FRMSEGSERNUM"));
				transferManifestVO.setTransferStatus(rs.getString("TRFSTA"));//partial transfer
				transferManifestVO.setDsnVOs(dsnVOs);		
				transferManifestVOs.add(transferManifestVO);
			
		}
		log.log(Log.FINE, "transferManifestVOs", transferManifestVOs);
		return transferManifestVOs;
	}
	

	/**
	 * A-2553
	 * 
	 * @param dsnVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs)
			throws SQLException {
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		dsnVO.setMailClass((rs.getString("MALSUBCLS")).substring(0,1));
		dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setBags(rs.getInt("BAGCNT"));
		//dsnVO.setWeight(rs.getDouble("BAGWGT"));
		dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("BAGWGT")));//added by A-7371
		dsnVO.setContainerNumber(rs.getString("CONNUM"));
		if(rs.getString("MSTDOCNUM")!=null){
			dsnVO.setAwbNumber(rs.getString("SHPPFX")+"-"+rs.getString("MSTDOCNUM"));
		}
		dsnVO.setMailbagId(rs.getString("MALIDR"));
		dsnVO.setOrigin(rs.getString("ORGCOD"));
		dsnVO.setDestination(rs.getString("DSTCOD"));
		dsnVO.setPaCode(rs.getString("POACOD"));
		dsnVO.setUpliftAirport(rs.getString("ARPCOD"));
		if(rs.getString("CSGDOCNUM")!=null && rs.getDate("DSPDAT")!=null){
			dsnVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			dsnVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("DSPDAT")));
		}

	}

}
