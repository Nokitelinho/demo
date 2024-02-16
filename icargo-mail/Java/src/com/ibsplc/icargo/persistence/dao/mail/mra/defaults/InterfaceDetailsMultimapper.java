/*
 * InterfaceDetailsMultimapper.java Created on Sep 02, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author a-5526
 *
 */
public class InterfaceDetailsMultimapper implements
		MultiMapper<FlightRevenueInterfaceVO> {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "InterfacedDetailsMultimapper";
	

	/**
	 * @param rs
	 * @return List<FlightRevenueInterfaceVO>
	 * @throws SQLException
	 */
	public List<FlightRevenueInterfaceVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs = new ArrayList<FlightRevenueInterfaceVO>();
		FlightRevenueInterfaceVO flightRevenueInterfaceVO = null;
		
	

		while (rs.next()) {

			flightRevenueInterfaceVO = new FlightRevenueInterfaceVO();	
			
			
			flightRevenueInterfaceVO.setCompanyCode(rs.getString("CMPCOD"));
			flightRevenueInterfaceVO.setSequenceNumber(rs.getLong("SEQNUM"));
			if(rs.getString("REFONE")!=null)
			flightRevenueInterfaceVO.setMailSeqNumber(Long.parseLong(rs.getString("REFONE")));
			if(rs.getString("REFTWO")!=null)
			flightRevenueInterfaceVO.setSerNumber(Integer.parseInt(rs.getString("REFTWO")));
			if(rs.getString("REFTHREE")!=null)
			flightRevenueInterfaceVO.setVersionNumber(Integer.parseInt(rs.getString("REFTHREE")));
			flightRevenueInterfaceVO.setAccountDate(rs.getString("REFTHIRTYONE"));
			flightRevenueInterfaceVO.setAdjustCode(rs.getString("REFTHIRTYTWO"));
			flightRevenueInterfaceVO.setBillingBranch(rs.getString("REFTHIRTYTHREE"));
			flightRevenueInterfaceVO.setBlockCheckMailWeight(rs.getString("REFTWENTYEIGHT"));
			flightRevenueInterfaceVO.setBlockCheckRateAmount(rs.getString("REFTWENTYNINE"));
			flightRevenueInterfaceVO.setBlockCheckRateAmountInUSD(rs.getString("REFTHIRTY"));
			flightRevenueInterfaceVO.setBranchCode(rs.getString("REFFOUR"));
			flightRevenueInterfaceVO.setCarrTypeC(rs.getString("REFFIVE"));
			flightRevenueInterfaceVO.setCurrency(rs.getString("REFSIX"));
			flightRevenueInterfaceVO.setFirstFlightDate(rs.getString("REFSEVEN"));
			flightRevenueInterfaceVO.setFlightDate(rs.getString("REFEIGHT"));
			flightRevenueInterfaceVO.setFlightDestination(rs.getString("REFNINE"));
			flightRevenueInterfaceVO.setFlightLineCode(rs.getString("REFTEN"));
			flightRevenueInterfaceVO.setFlightNumber(rs.getString("REFELEVEN"));
			flightRevenueInterfaceVO.setFlightOrigin(rs.getString("REFTWELVE"));
			flightRevenueInterfaceVO.sethLNumber(rs.getString("REFTHIRTEEN"));
			flightRevenueInterfaceVO.setInterfaceDate(rs.getString("REFFOURTEEN"));
			flightRevenueInterfaceVO.setInterfaceFlag(rs.getString("INTFCDFLG"));
			flightRevenueInterfaceVO.setMailCategory(rs.getString("REFSIXTEEN"));
			flightRevenueInterfaceVO.setMailDestination(rs.getString("REFSEVENTEEN"));
			flightRevenueInterfaceVO.setMailNumber(rs.getString("REFEIGHTEEN"));
			flightRevenueInterfaceVO.setMailOrigin(rs.getString("REFNINTEEN"));
			flightRevenueInterfaceVO.setMailWeight(rs.getString("REFTWENTY"));
			flightRevenueInterfaceVO.setRateAmount(rs.getString("REFTWENTYONE"));
			flightRevenueInterfaceVO.setRateAmountInUSD(rs.getString("REFTWENTYTWO"));
			flightRevenueInterfaceVO.setRegionCode(rs.getString("REFTWENTYTHREE"));
			flightRevenueInterfaceVO.setrSN(rs.getString("REFTWENTYFOUR"));
			flightRevenueInterfaceVO.setSerialNumber(rs.getString("REFTWENTYFIVE"));
			flightRevenueInterfaceVO.setSettlement(rs.getString("REFTWENTYSIX"));
			flightRevenueInterfaceVO.setSubClassGroup(rs.getString("REFTWENTYSEVEN"));
			flightRevenueInterfaceVO.setTriggerPoint(rs.getString("REFFIFTEEN"));
			
			
			flightRevenueInterfaceVOs.add(flightRevenueInterfaceVO);

		}
		log.exiting(CLASS_NAME, "map");
		return (ArrayList<FlightRevenueInterfaceVO>) flightRevenueInterfaceVOs;
	}
}
