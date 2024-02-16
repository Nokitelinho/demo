/*
 * ListLoyaltyProgrammesMultiMapper.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class ListLoyaltyProgrammesMultiMapper implements
	MultiMapper<LoyaltyProgrammeVO>{
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return List<LoyaltyProgrammeVO>
	 * @throws SQLException
	 */
	public List<LoyaltyProgrammeVO> map(ResultSet resultSet)
		throws SQLException {
		log.entering("ListLoyaltyProgrammesMultiMapper","Map");
		String previousLoyaltyProgrammePK = "";
		String currentLoyaltyProgrammePK = "";
		List<LoyaltyProgrammeVO> loyaltyProgrammeVOs = null;
		Collection<LoyaltyParameterVO> parameterVOs = null;
		LoyaltyProgrammeVO loyaltyProgrammeVO = null;
		LoyaltyParameterVO loyaltyParameterVO = null ;
		StringBuilder buffer = null;
		while(resultSet.next()){
			buffer = new StringBuilder(resultSet.getString("CMPCOD")).
					append(resultSet.getString("LTYPRGCOD"));
			currentLoyaltyProgrammePK = buffer.toString();
			log.log(Log.FINE, "Current PK -->", currentLoyaltyProgrammePK);
			if(!currentLoyaltyProgrammePK.equals(previousLoyaltyProgrammePK)){
				loyaltyProgrammeVO = new LoyaltyProgrammeVO();
				loyaltyProgrammeVO.setCompanyCode(
						resultSet.getString("CMPCOD"));
				loyaltyProgrammeVO.setLoyaltyProgrammeCode(
						resultSet.getString("LTYPRGCOD"));
				loyaltyProgrammeVO.setActiveStatus(
						resultSet.getString("ACTSTA"));
				loyaltyProgrammeVO.setEntryPoints(
						resultSet.getDouble("ENTPTS"));
				loyaltyProgrammeVO.setExpiryDuration(
						resultSet.getString("EXPDUR"));
				loyaltyProgrammeVO.setExpiryPeriod(
						resultSet.getDouble("EXPPER"));
				loyaltyProgrammeVO.setAttibute(
						resultSet.getString("ATR"));
				loyaltyProgrammeVO.setUnits(
						resultSet.getString("UNT"));
				loyaltyProgrammeVO.setAmount(resultSet.getDouble("AMT"));
				loyaltyProgrammeVO.setPoints(resultSet.getDouble("PTS"));
				Date fromDate = resultSet.getDate("FRMDAT");
				if(fromDate != null){
				 loyaltyProgrammeVO.setFromDate(new LocalDate(
						LocalDate.NO_STATION,Location.NONE,fromDate));
				}
				Date toDate = resultSet.getDate("TOODAT");
				if(toDate != null){
				 loyaltyProgrammeVO.setToDate(new LocalDate(
						LocalDate.NO_STATION,Location.NONE,toDate));
				}
				loyaltyProgrammeVO.setLoyaltyProgrammeDesc(resultSet.getString("LTYPRGDES"));
				loyaltyProgrammeVO.setLastUpdatedUser(resultSet.getString("LSTUPDUSR"));

				loyaltyProgrammeVO.setLastUpdatedTime(new LocalDate(
						LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));

				parameterVOs = new ArrayList<LoyaltyParameterVO>();
					if(loyaltyProgrammeVOs == null){
						loyaltyProgrammeVOs =
							new ArrayList<LoyaltyProgrammeVO>();
					}
					loyaltyProgrammeVOs.add(loyaltyProgrammeVO);
			}
			if(resultSet.getString("LTYPRGPAR") != null){
			loyaltyParameterVO = new LoyaltyParameterVO();
			loyaltyParameterVO.setCompanyCode(resultSet.getString("CMPCOD"));
			loyaltyParameterVO.setLoyaltyProgrammeCode(resultSet.getString("LTYPRGCOD"));
			loyaltyParameterVO.setParameterCode(resultSet.getString("LTYPRGPAR"));
			loyaltyParameterVO.setParameterValue(resultSet.getString("PARVAL"));
			loyaltyParameterVO.setSequenceNumber(resultSet.getString("SEQNUM"));
			parameterVOs.add(loyaltyParameterVO);
			}
			loyaltyProgrammeVO.setLoyaltyParameterVOs(parameterVOs);
			previousLoyaltyProgrammePK = currentLoyaltyProgrammePK;

		}// end of while

		log.exiting("ListLoyaltyProgrammesMultiMapper","Map");
		return loyaltyProgrammeVOs;
	}
}
