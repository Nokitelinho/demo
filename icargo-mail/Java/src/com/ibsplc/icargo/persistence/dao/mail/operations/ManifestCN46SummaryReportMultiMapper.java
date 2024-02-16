/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ManifestCN46SummaryReportMultiMapper.java
 *
 *	Created by	:	A-10647
 *	Created on	:	11-Nov-2022
 *
 *  Copyright 2020 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ManifestCN46SummaryReportMultiMapper implements MultiMapper<ConsignmentDocumentVO>{
	private static final Log LOGGER  = LogFactory.getLogger("mail.operations");
	private static final String MALCTGCOD = "MALCTGCOD";
	private static final String ORGEXGOFC = "ORGEXGOFC";
	private static final String POACOD = "POACOD";
	private static final String FSTFLTDEPDAT = "FSTFLTDEPDAT";
	private static final String FLTDAT = "FLTDAT";
	private static final String MALSUBCLS = "MALSUBCLS";
	private static final String SUBCLSGRP = "SUBCLSGRP";
	private static final String DSTEXGOFC = "DSTEXGOFC";
	private static final String ULDNUM = "ULDNUM";
	private static final String FLTNUM = "FLTNUM";
	private static final String FLTCARCOD = "FLTCARCOD";




	public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {

		LOGGER.entering("ConsignmentDetailsMultimapper", "map");
		
		ArrayList<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();

		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		String key ="";
		String previousKey ="";
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();

		
		while (rs.next()) {
			key =rs.getString(MALCTGCOD)+"-"+ rs.getString(ORGEXGOFC)+"-"+ rs.getString(POACOD);
			Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();

			if(!key.equals(previousKey)) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
			}
			MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
			RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
			
			if(rs.getString("CMPCOD")!=null) {
			consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
			}
			if(rs.getString("ARPCOD")!=null) {
			consignmentDocumentVO.setAirportCode(rs.getString("ARPCOD"));
			}
			if (rs.getTimestamp("CSGDAT") != null) {
				consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("CSGDAT")));
			}
			if(rs.getString("CSGTYP")!=null) {
			consignmentDocumentVO.setReportType(rs.getString("CSGTYP"));
			}
			if(rs.getString(MALCTGCOD).equals("A")) {
				consignmentDocumentVO.setSubType("CN38");	
			}
			if(rs.getString(MALCTGCOD).equals("B")) {
				consignmentDocumentVO.setSubType("CN41");	
			}
			if(rs.getString("RMK")!=null) {
				consignmentDocumentVO.setRemarks(rs.getString("RMK"));
			}
			if(rs.getString(FSTFLTDEPDAT)!=null) {
				consignmentDocumentVO.setDespatchDate(rs.getString(FSTFLTDEPDAT));
				consignmentDocumentVO.setFirstFlightDepartureDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp(FSTFLTDEPDAT)));
			}
			
			if(rs.getString("FLTRUT")!=null) {
				consignmentDocumentVO.setFlightRoute(rs.getString("FLTRUT"));
			}
			if(rs.getString(POACOD)!=null) {
				consignmentDocumentVO.setPaCode(rs.getString(POACOD));
			}
			if(rs.getString("ARLNAM")!=null) {
				consignmentDocumentVO.setAirlineName(rs.getString("ARLNAM"));
			}
			if(rs.getString("POANAM")!=null) {
				consignmentDocumentVO.setPaName(rs.getString("POANAM"));
			}
			if(rs.getString("OPRORG") != null) {
				consignmentDocumentVO.setOperatorOrigin(rs.getString("OPRORG"));
			}
			if(rs.getString("OPRDST") != null) {
				consignmentDocumentVO.setOperatorDestination(rs.getString("OPRDST"));
			}
			if(rs.getString("ORGEXGOFCDES") != null) {
				consignmentDocumentVO.setOoeDescription(rs.getString("ORGEXGOFCDES"));
			}
			if(rs.getString("DSTEXGOFCDES") != null) {
				consignmentDocumentVO.setDoeDescription(rs.getString("DSTEXGOFCDES"));
			}
			RoutingInConsignmentVO routingInConsignmentVo = populateRoutingInformation(routingInConsignmentVO, rs);
			if(routingInConsignmentVo!=null){
				if(rs.getString(FSTFLTDEPDAT)==null&&consignmentDocumentVO.getFirstFlightDepartureDate()==null && routingInConsignmentVo.getRoutingSerialNumber() == 1
						&& rs.getString(FLTDAT) != null){
					consignmentDocumentVO.setFirstFlightDepartureDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp(FLTDAT)));
				}
				routingInConsignmentVOs.add(routingInConsignmentVo);
				if(consignmentDocumentVO.getFlightRoute()==null) {
					consignmentDocumentVO.setFlightRoute(routingInConsignmentVo.getPolAirportName()+"-"+
							routingInConsignmentVo.getPouAirportName());	
				}
			}
			MailInConsignmentVO mailInConsignmentVo = populateMailInformation(mailInConsignmentVO, rs);
			if(mailInConsignmentVo!=null && key.equals(previousKey)){
			mailInConsignmentVOs.add(mailInConsignmentVo);
			}else {
				mailInConsignmentVOs = new ArrayList<>();
				mailInConsignmentVOs.add(mailInConsignmentVo);

			}
			consignmentDocumentVO.setAirportOftransShipment(rs.getString("AIRTRANS"));
			consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);
			consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
			if(!key.equals(previousKey)) {
			consignmentDocumentVOs.add(consignmentDocumentVO);
			}
			previousKey = key;
		}
		
		
		return consignmentDocumentVOs;
		
	
	}
	/**
	 * 
	 * 	Method		:	ManifestCN46SummaryReportMultiMapper.populateMailInformation
	 *	Added by 	:	A-10647 on 02-Nov-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mailInConsignmentVO
	 *	Parameters	:	@param rs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 *	Return type	: 	MailInConsignmentVO
	 */
	private MailInConsignmentVO populateMailInformation(MailInConsignmentVO mailInConsignmentVO, ResultSet rs) throws SQLException {
		if(rs.getString("DSN")!=null) {
			mailInConsignmentVO.setDsn(rs.getString("DSN"));
		}
		if(rs.getString(MALSUBCLS)!=null) {
			mailInConsignmentVO.setMailSubclass(rs.getString(MALSUBCLS));
		}
		if(rs.getString(ORGEXGOFC)!=null) {
			mailInConsignmentVO.setOriginExchangeOffice(rs.getString(ORGEXGOFC));
		}
		if(rs.getString(DSTEXGOFC)!=null) {
			mailInConsignmentVO.setDestinationExchangeOffice(rs.getString(DSTEXGOFC));
		}
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM);
		if(rs.getString("WGT")!=null) {
			mailInConsignmentVO.setStatedWeight(wgt);
		}
		if(rs.getString("PCS")!=null) {
			mailInConsignmentVO.setStatedBags(rs.getInt("PCS"));
		}
		if(rs.getString(SUBCLSGRP)!=null) {
			mailInConsignmentVO.setMailSubClassGroup(rs.getString(SUBCLSGRP));
		}
		if(rs.getString(MALCTGCOD)!=null) {
			mailInConsignmentVO.setMailCategoryCode(rs.getString(MALCTGCOD));
		}
		if(rs.getString("LCCOUNT")!=null) {
			mailInConsignmentVO.setTotalLetterBags(rs.getInt("LCCOUNT"));
		}
		if(rs.getString("CPCOUNT")!=null) {
			mailInConsignmentVO.setTotalParcelBags(rs.getInt("CPCOUNT"));
		}
		if(rs.getString("EMSCOUNT")!=null) {
			mailInConsignmentVO.setTotalEmsBags(rs.getInt("EMSCOUNT"));
		}
		if(rs.getString("SVCOUNT")!=null) {
			mailInConsignmentVO.setTotalSVbags(rs.getInt("SVCOUNT"));
		}
		if(rs.getString("LCWEIGHT")!=null) {
			mailInConsignmentVO.setTotalLetterWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("LCWEIGHT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		}
		if(rs.getString("CPWEIGHT")!=null) {
			mailInConsignmentVO.setTotalParcelWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CPWEIGHT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		}
		if(rs.getString("EMSWEIGHT")!=null) {
			mailInConsignmentVO.setTotalEmsWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("EMSWEIGHT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		}
		if(rs.getString("SVWEIGHT")!=null) {
			mailInConsignmentVO.setTotalSVWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("SVWEIGHT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		}
		if(rs.getString(MALCTGCOD)!=null && rs.getString(ORGEXGOFC)!=null && rs.getString(POACOD)!=null) {
			String key = rs.getString(MALCTGCOD)+"~"+ rs.getString(ORGEXGOFC)+"~"+ rs.getString(POACOD);
			mailInConsignmentVO.setKeyCondition(key);
		}
		if(rs.getString(ULDNUM)!=null && !"N".equals(rs.getString(ULDNUM))) {
			mailInConsignmentVO.setUldNumber(rs.getString(ULDNUM));
		}
		if(rs.getString("CONSELNUM")!=null) {
			mailInConsignmentVO.setSealNumber(rs.getString("CONSELNUM"));
		}
		if(rs.getString("DSN")!=null && rs.getString(MALSUBCLS)!=null&&rs.getString(ORGEXGOFC)!=null&& 
				rs.getString(DSTEXGOFC)!=null&& rs.getString("WGT")!=null &&rs.getString("PCS")!=null &&
				rs.getString(SUBCLSGRP)!=null){
			return mailInConsignmentVO;
		}else{
		return null;
		}
	}

	/**
	 * 
	 * 	Method		:	ManifestCN46SummaryReportMultiMapper.populateRoutingInformation
	 *	Added by 	:	A-10647 on 02-Nov-2022
	 * 	Used for 	:
	 *	Parameters	:	@param routingInConsignmentVO
	 *	Parameters	:	@param rs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 *	Return type	: 	RoutingInConsignmentVO
	 */
	private RoutingInConsignmentVO populateRoutingInformation(RoutingInConsignmentVO routingInConsignmentVO, ResultSet rs) throws SQLException {
		if(rs.getString(FLTNUM)!=null) {
			routingInConsignmentVO.setOnwardFlightNumber(rs.getString(FLTNUM));
		}
		routingInConsignmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
		if(rs.getString("POL")!=null) {
			routingInConsignmentVO.setPol(rs.getString("POL"));
		}
		if(rs.getString("POU")!=null) {
			routingInConsignmentVO.setPou(rs.getString("POU"));
		}
		if(rs.getString(FLTCARCOD)!=null) {
			routingInConsignmentVO.setOnwardCarrierCode(rs.getString(FLTCARCOD));
		}
		if (rs.getTimestamp(FLTDAT) != null && rs.getString(FLTNUM)!=null) {
			routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp(FLTDAT)));
		}
		routingInConsignmentVO.setPolAirportName(rs.getString("POLARPNAM"));
		routingInConsignmentVO.setPouAirportName(rs.getString("POUARPNAM"));
		if(rs.getString(FLTNUM)!=null && rs.getString("POL")!=null && rs.getString("POU")!=null
				&& rs.getString(FLTCARCOD)!=null && rs.getTimestamp(FLTDAT) != null){
			return routingInConsignmentVO;
		}else{
		return null;
		}
	}

}
