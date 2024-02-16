/*
 * MailRevenueReportDetailsMapper.java Created on Jun 22, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.flown;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailRevenueVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-2270
 * 
 */
public class MailRevenueReportDetailsMapper implements
		Mapper<FlownMailRevenueVO> {
	//String baseCurrency;

	public MailRevenueReportDetailsMapper(FlownMailFilterVO flownMailFilterVO) {
		//this.baseCurrency = flownMailFilterVO.getBaseCurrencyCode();
	}

	public FlownMailRevenueVO map(ResultSet resultSet) throws SQLException {

		FlownMailRevenueVO flownMailRevenueVO = new FlownMailRevenueVO();
		
		flownMailRevenueVO.setOriginCountry(resultSet.getString("ORGCNT"));
		flownMailRevenueVO.setOriginCity(resultSet.getString("ORGCTY"));
		flownMailRevenueVO.setDestinationCountry(resultSet.getString("DSTCNT"));
		flownMailRevenueVO.setDestinationCity(resultSet.getString("DSTCTY"));
		flownMailRevenueVO.setDsnNumber(resultSet.getString("DSN"));
		if (resultSet.getDate("EXEDAT") != null) {
			flownMailRevenueVO.setFlightDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, resultSet
							.getDate("EXEDAT")));
		}
		flownMailRevenueVO.setFlightNumber(resultSet.getString("FLTNUM"));
		flownMailRevenueVO.setSectorFrom(resultSet.getString("SEGORGCOD"));
		flownMailRevenueVO.setSectorTo(resultSet.getString("SEGDSTCOD"));
		flownMailRevenueVO.setSubClass(resultSet.getString("SUBCLS"));
		flownMailRevenueVO.setAirCraftType(resultSet.getString("ACRTYP"));
		flownMailRevenueVO.setRegionType(resultSet.getString("REGIND"));
		flownMailRevenueVO.setWeight(resultSet.getDouble("WGT"));
		flownMailRevenueVO.setStation(resultSet.getString("ORGCTY"));
		
		try {
			
			Money amountInZAR = CurrencyHelper.getMoney("ZAR");
			
			if((resultSet.getDouble("CRR") > resultSet.getDouble("DRR"))){
				amountInZAR.setAmount(resultSet.getDouble("CRR")- resultSet.getDouble("DRR"));
			}
			else if((resultSet.getDouble("DRR")> resultSet.getDouble("CRR"))){
				amountInZAR.setAmount(-(resultSet.getDouble("DRR")- resultSet.getDouble("CRR")));
			}
			//amountInZAR.setAmount((resultSet.getDouble("CRR")>0)? resultSet.getDouble("CRR") : -(resultSet.getDouble("DRR")));
			flownMailRevenueVO.setAmountInZAR(amountInZAR);
			
		} catch (CurrencyException currencyException) {
			throw new SQLException(currencyException.getErrorCode());
		}
		//flownMailRevenueVO.setAccountCode(resultSet.getString("GNLACCNUM")); Commented BY A-5791 for ICRD-69710
		return flownMailRevenueVO;
	}

}
