
	/*
	 * DSNRoutingMapper.java Created on Sep 03, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

	import java.sql.Date;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
	import com.ibsplc.icargo.framework.util.time.LocalDate;
	import com.ibsplc.icargo.framework.util.time.Location;
	import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
	import com.ibsplc.xibase.util.log.Log;
	import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * @author A-2554
	 *
	 */
	public class DSNRoutingMapper implements Mapper<DSNRoutingVO> {
		
	
		
		private Log log = LogFactory.getLogger("DSNRoutingMapper--------->");
		

		
		

		/**
		 * @author A-2554
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public DSNRoutingVO map(ResultSet rs) throws SQLException {

			
				log.entering("DSNRoutingMapper", "map");
				DSNRoutingVO dsnRoutingVO = new DSNRoutingVO();
				
			   // dsnRoutingVO.setBillingBasis(rs.getString("BLGBAS"));
				dsnRoutingVO.setCompanyCode(rs.getString("CMPCOD"));
				dsnRoutingVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				dsnRoutingVO.setMailbagId(rs.getString("MALIDR"));
			//	dsnRoutingVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
			//	dsnRoutingVO.setCsgSequenceNumber(rs.getInt("CSGSEQNUM"));
			//	dsnRoutingVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
			//	dsnRoutingVO.setPoaCode(rs.getString("POACOD"));
				
				dsnRoutingVO.setFlightCarrierCode(rs.getString("FLTCARCOD"));
				dsnRoutingVO.setFlightCarrierId(rs.getInt("FLTCARIDR"));
				dsnRoutingVO.setFlightNumber(rs.getString("FLTNUM"));
				dsnRoutingVO.setRoute(rs.getString("ROU"));
				
				Date fltDate = rs.getDate("FLTDAT");
				if(fltDate != null){
					dsnRoutingVO.setDepartureDate(
							new LocalDate(LocalDate.NO_STATION, Location.NONE,fltDate));
				}
				dsnRoutingVO.setPol(rs.getString("POL"));
				dsnRoutingVO.setPou(rs.getString("POU"));
				dsnRoutingVO.setNopieces(rs.getInt("STDBAG"));
				dsnRoutingVO.setAgreementType(rs.getString("AGRTYP"));
				dsnRoutingVO.setBlockSpaceType(rs.getString("BLKSPCTYP"));
				dsnRoutingVO.setBsaReference(rs.getString("BSAREF"));
				dsnRoutingVO.setWeight(rs.getDouble("DSPWGT"));
				dsnRoutingVO.setAcctualnopieces(rs.getInt("TOTPCS"));
				dsnRoutingVO.setAcctualweight( rs.getDouble("STDWGT") );//Code modified by Manish for IASCB-33315 start
				dsnRoutingVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
				dsnRoutingVO.setFlightSeqnum(rs.getLong("FLTSEQNUM"));
				dsnRoutingVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				dsnRoutingVO.setFlightType(rs.getString("FLTTYP"));
				dsnRoutingVO.setInterfacedFlag(rs.getString("INTFCDFLG"));
				dsnRoutingVO.setSource(rs.getString("SEGSRC"));
				dsnRoutingVO.setDisplayWgtUnit(rs.getString("DSPWGTUNT") );
				
				dsnRoutingVO.setBillingStatus(rs.getString("MRASTA"));
				return dsnRoutingVO;

			
	}
		
	}
