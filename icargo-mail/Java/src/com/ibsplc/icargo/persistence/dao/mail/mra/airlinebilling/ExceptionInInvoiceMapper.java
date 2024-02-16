/* ExceptionInInvoiceMapper.java Created on Feb 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2518
 *
 */
public class ExceptionInInvoiceMapper implements Mapper<ExceptionInInvoiceVO>{
	private Log log = LogFactory.getLogger("ExceptionInInvoiceMapper");

	private static final String CLASS_NAME = "ExceptionInInvoiceMapper";

	/**
	 * @return AirlineCN66DetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	public ExceptionInInvoiceVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		ExceptionInInvoiceVO exceptionInInvoiceVO = new ExceptionInInvoiceVO();
		exceptionInInvoiceVO.setCompanyCode(rs.getString("CMPCOD"));
		exceptionInInvoiceVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
		exceptionInInvoiceVO.setInvoiceNumber(rs.getString("INVNUM"));
		exceptionInInvoiceVO.setClearancePeriod(rs.getString("CLRPRD"));
		exceptionInInvoiceVO.setRemark(rs.getString("RMK"));
		exceptionInInvoiceVO.setExceptionStatus(rs.getString("EXPSTA"));
		exceptionInInvoiceVO.setMemoCode(rs.getString("MEMCOD"));
		exceptionInInvoiceVO.setAirlineCode(rs.getString("ARLCOD"));
		exceptionInInvoiceVO.setMemoStatus(rs.getString("MEMSTA"));
		
		try {
			String contractCurrCode=rs.getString("CRTCURCOD");
			Money provAmount=CurrencyHelper.getMoney(contractCurrCode);
			Money rptdAmount=CurrencyHelper.getMoney(contractCurrCode);
			Money diffAmount=CurrencyHelper.getMoney(contractCurrCode);
			
			provAmount.setAmount(rs.getDouble("PVNAMTLSTCUR"));    //modified as part of ICRD_265471
			exceptionInInvoiceVO.setProvisionalAmount(rs.getDouble("PVNAMTLSTCUR")); //modified as part of ICRD_265471
			exceptionInInvoiceVO.setProvAmt(provAmount);
			
			
			rptdAmount.setAmount(rs.getDouble("RPDAMT"));
			exceptionInInvoiceVO.setReportedAmount(rs.getDouble("RPDAMT"));
			exceptionInInvoiceVO.setReportedAmt(rptdAmount);
			
			diffAmount.setAmount(rs.getDouble("DIFAMT"));
			exceptionInInvoiceVO.setDifferenceAmount(rs.getDouble("DIFAMT"));
			exceptionInInvoiceVO.setDiffAmt(diffAmount);
			
			
			exceptionInInvoiceVO.setContractCurrency(rs.getString("CRTCURCOD"));
			
			
			
		} catch (CurrencyException e) {
			// TODO Auto-generated catch block
			e.getErrorCode();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.getErrorCode();
		}
		
		
		
		
		
		
		
		
		exceptionInInvoiceVO.setInterlineBillingType(rs.getString("INTBLGTYP"));
		
		LocalDate fromDate = rs.getDate("FRMDAT") == null ? null :
			new LocalDate(LocalDate.NO_STATION,	Location.NONE, rs.getDate("FRMDAT"));
		exceptionInInvoiceVO.setFromdate(fromDate);
		
		LocalDate toDate = rs.getDate("TOODAT") == null ? null :
			new LocalDate(LocalDate.NO_STATION,	Location.NONE, rs.getDate("TOODAT"));
		exceptionInInvoiceVO.setToDate(toDate);
		/**
		 * Added by Sandeep as part of optimistic Locking Mechanism
		 */
		exceptionInInvoiceVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
    	if(rs.getTimestamp("LSTUPDTIM")!=null){
    		exceptionInInvoiceVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,
    				                Location.NONE,rs.getTimestamp("LSTUPDTIM")));
        }

		log.exiting(CLASS_NAME, "map");
		return exceptionInInvoiceVO;
	}

}
