/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.AcquitContainerFromFlightMultiMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Sep 3, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
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

import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.AcquitContainerFromFlightMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Sep 3, 2016	:	Draft
 */
public class AcquitContainerFromFlightMultiMapper implements MultiMapper<MailArrivalVO>{
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String CLASS ="AcquitContainerFromFlightMapper";

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 *	Added by 			: A-4809 on Sep 3, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public List<MailArrivalVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS, "ResultSet");
		List<MailArrivalVO> mailarrivalVO = new ArrayList<MailArrivalVO>();
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		String currFlightKey = null;
		String prevFlightKey = null;
		String currContainerKey = null;
		String prevContainerKey = null;
		while (rs.next()) {
			currFlightKey = new StringBuilder().append(
					rs.getString("CMPCOD")).append(rs.getInt("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(
							rs.getLong("FLTSEQNUM")).append(
							rs.getInt("LEGSERNUM")).toString();
			if(!currFlightKey.equals(prevFlightKey)){
				MailArrivalVO arrivalVO = new MailArrivalVO();
				populateFlightDetails(arrivalVO,rs);
				containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
				arrivalVO.setContainerDetails(containerDetailsVOs);
				mailarrivalVO.add(arrivalVO);
				prevFlightKey = currFlightKey;
			}
			currContainerKey = new StringBuilder().append(
					rs.getString("CMPCOD")).append(rs.getInt("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(
							rs.getLong("FLTSEQNUM")).append(
							rs.getInt("LEGSERNUM")).append(
							rs.getString("ULDNUM")).toString();
			if(!currContainerKey.equals(prevContainerKey)){
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				populateContainerDetails(containerDetailsVO,rs);
				containerDetailsVOs.add(containerDetailsVO);
				prevContainerKey = currContainerKey;
			}
		}
		return mailarrivalVO;

}
	/**
	 * 	Method		:	AcquitContainerFromFlightMapper.populateContainerDetails
	 *	Added by 	:	A-4809 on Oct 5, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param containerDetailsVO
	 *	Parameters	:	@param rs 
	 *	Return type	: 	void
	 * @throws SQLException 
	 */
	private void populateContainerDetails(
			ContainerDetailsVO containerDetailsVO, ResultSet rs) throws SQLException {
		//containerDetailsVO.setFlightStatus(rs.getString("CLSFLG"));
		containerDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
		//containerDetailsVO.setTotalBags(rs.getInt("BAGCNT"));
		//containerDetailsVO.setTotalWeight(rs.getDouble("BAGWGT"));
		//containerDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
		//containerDetailsVO.setReceivedWeight(rs.getDouble("RCVWGT"));
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		containerDetailsVO.setPol(rs.getString("LEGORG"));
		containerDetailsVO.setPou(rs.getString("LEGDST"));
		//containerDetailsVO.setAirportCode(rs.getString("ARPCOD"));
		if (containerDetailsVO.getPol() != null && containerDetailsVO.getPou() != null) {
			containerDetailsVO.setFlightDate(new LocalDate(containerDetailsVO.getPol(), Location.ARP, rs.getDate("FLTDAT")));
		StringBuilder route = new StringBuilder().
		append(containerDetailsVO.getPol()).append("-").append(containerDetailsVO.getPou());
		containerDetailsVO.setRoute(route.toString());
		} else {
		containerDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("FLTDAT")));
		}
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setArrivedStatus(rs.getString("CONARRSTA"));
		containerDetailsVO.setDeliveredStatus(rs.getString("DLVFLG"));
		containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		containerDetailsVO.setDestination(rs.getString("DSTCOD"));
        containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
        containerDetailsVO.setTransferFlag(rs.getString("CONTRAFLG")); 
        //containerDetailsVO.setReleasedFlag(rs.getString("RELFLG"));    
        containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
        containerDetailsVO.setIntact(rs.getString("INTFLG"));
        containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
        containerDetailsVO.setPaCode(rs.getString("SBCODE"));
        if(rs.getString("REMARK")!=null){
        containerDetailsVO.setRemarks(rs.getString("REMARK"));
        }
		
	}
	/**
	 * 	Method		:	AcquitContainerFromFlightMapper.populateFlightDetails
	 *	Added by 	:	A-4809 on Oct 5, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param arrivalVO
	 *	Parameters	:	@param rs 
	 *	Return type	: 	void
	 * @throws SQLException 
	 */
	private void populateFlightDetails(MailArrivalVO mailArrivalVO, ResultSet rs) throws SQLException {
		mailArrivalVO.setCompanyCode(rs.getString("CMPCOD"));
		mailArrivalVO.setFlightNumber(rs.getString("FLTNUM"));
		mailArrivalVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailArrivalVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailArrivalVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		mailArrivalVO.setPol(rs.getString("LEGORG"));
		mailArrivalVO.setPou(rs.getString("POU"));
		//containerDetailsVO.setAirportCode(rs.getString("ARPCOD"));
		if (mailArrivalVO.getPol() != null && mailArrivalVO.getPou() != null) {
			mailArrivalVO.setFlightDate(new LocalDate(mailArrivalVO.getPol(), Location.ARP, rs.getDate("FLTDAT")));
		StringBuilder route = new StringBuilder().
		append(mailArrivalVO.getPol()).append("-").append(mailArrivalVO.getPou());
		mailArrivalVO.setRoute(route.toString());
		} else {
			mailArrivalVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("FLTDAT")));
		}
		mailArrivalVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
	}

}
