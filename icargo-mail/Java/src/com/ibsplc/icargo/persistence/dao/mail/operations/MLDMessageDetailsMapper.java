/*
 * MLDMessageDetailsMapper.java Created on Jan 10, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MLDDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author A-5526
 * 
 */
public class MLDMessageDetailsMapper implements Mapper<MLDMasterVO> {
	public MLDMasterVO map(ResultSet rs) throws SQLException {
		MLDMasterVO mldMasterVO = new MLDMasterVO();
		MLDDetailVO mldDetailVO = new MLDDetailVO();
		mldMasterVO.setCompanyCode(rs.getString("CMPCOD"));
		mldMasterVO.setSerialNumber(rs.getInt("SERNUM"));
		mldMasterVO.setBarcodeValue(rs.getString("MALIDR"));
		mldMasterVO.setWeightCode(rs.getString("WGTCOD"));
		//mldMasterVO.setWeight(rs.getString("WGT"));
		mldMasterVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("WGT"))));//added by A-7371
		mldMasterVO.setEventCOde(rs.getString("EVTMOD"));

		if ("HND".equals(rs.getString("EVTMOD"))
				|| "DLV".equals(rs.getString("EVTMOD")) || "RCF".equals(rs.getString("EVTMOD"))
					||MailConstantsVO.MLD_TFD.equals(rs.getString("EVTMOD")))  
			/*As discussed with Meera and Sony, TFD messages should be triggered based on OWN airline*/
			{
			if("HND".equals(rs.getString("EVTMOD")) && rs.getInt("OUBCARIDR")>0){        
				mldMasterVO.setAddrCarrier(String.valueOf(rs.getInt("OUBCARIDR")));
			}else
			mldMasterVO.setAddrCarrier(String.valueOf(rs.getInt("INBCARIDR")));
			}
		else {
			mldMasterVO.setAddrCarrier(String.valueOf(rs.getInt("OUBCARIDR")));
		}

		mldMasterVO.setSenderAirport(rs.getString("SNDARPCOD"));
		mldMasterVO.setReceiverAirport(rs.getString("RCVARPCOD"));
		mldMasterVO.setDestAirport(rs.getString("DSTARPCOD"));
		if (mldMasterVO.getSenderAirport() != null
				&& rs.getTimestamp("TXNTIM") != null) {
			LocalDate scnDat = new LocalDate(mldMasterVO.getSenderAirport(),
					Location.ARP, rs.getTimestamp("TXNTIM"));
			mldMasterVO.setScanTime(scnDat);

		}
		//Modified as part of bug ICRD-143950 by A-5526
		mldMasterVO.setUldNumber(rs.getString("CONNUM"));

		mldDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		mldDetailVO.setMailIdr(rs.getString("MALIDR"));
		mldDetailVO.setSerialNumber(rs.getInt("SERNUM"));

		mldDetailVO.setCarrierIdInb(rs.getInt("INBCARIDR"));
		mldDetailVO.setFlightNumberInb(rs.getString("INBFLTNUM"));
		mldDetailVO.setFlightSequenceNumberInb(rs.getInt("INBFLTSEQNUM"));
		mldDetailVO.setMailModeInb(rs.getString("INBSTACOD"));

		mldDetailVO.setPolInb(rs.getString("INBPOL"));
		if (mldMasterVO.getSenderAirport() != null
				&& rs.getTimestamp("INBEVTTIM") != null) {
			LocalDate scnDat = new LocalDate(mldMasterVO.getSenderAirport(),
					Location.ARP, rs.getTimestamp("INBEVTTIM"));
			mldDetailVO.setEventTimeInb(scnDat);

		}
		if (mldMasterVO.getSenderAirport() != null
				&& rs.getTimestamp("INBFLTDAT") != null) {
			LocalDate scnDat = new LocalDate(mldMasterVO.getSenderAirport(),
					Location.ARP, rs.getTimestamp("INBFLTDAT"));
			mldDetailVO.setFlightOperationDateInb(scnDat);

		}

		mldDetailVO.setPostalCodeOub(rs.getString("OUBPOACOD"));

		mldDetailVO.setCarrierIdOub(rs.getInt("OUBCARIDR"));
		mldDetailVO.setFlightNumberOub(rs.getString("OUBFLTNUM"));
		mldDetailVO.setFlightSequenceNumberOub(rs.getInt("OUBFLTSEQNUM"));
		mldDetailVO.setMailModeOub(rs.getString("OUBSTACOD"));

		mldDetailVO.setPouOub(rs.getString("OUBPOU"));
		if (mldMasterVO.getSenderAirport() != null
				&& rs.getTimestamp("OUBEVTTIM") != null) {
			LocalDate scnDat = new LocalDate(mldMasterVO.getSenderAirport(),
					Location.ARP, rs.getTimestamp("OUBEVTTIM"));
			mldDetailVO.setEventTimeOub(scnDat);

		}

		if (mldMasterVO.getSenderAirport() != null
				&& rs.getTimestamp("OUBFLTDAT") != null) {
			LocalDate scnDat = new LocalDate(mldMasterVO.getSenderAirport(),
					Location.ARP, rs.getTimestamp("OUBFLTDAT"));
			mldDetailVO.setFlightOperationDateOub(scnDat);

		}

		mldDetailVO.setPostalCodeOub(rs.getString("OUBPOACOD"));
		mldMasterVO.setMldDetailVO(mldDetailVO);
		mldMasterVO.setMessageVersion(rs.getString("MSGVER"));

		if(rs.getString("TRALVL") != null) {
			mldMasterVO.setTransactionLevel(rs.getString("TRALVL"));
		}
		return mldMasterVO;
	}
}