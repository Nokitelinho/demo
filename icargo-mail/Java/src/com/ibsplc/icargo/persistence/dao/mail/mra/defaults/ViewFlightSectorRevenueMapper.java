/*
 * ViewFlightSectorRevenueMapper.java Created on Aug08, 2008
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
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
 * @author a-3429
 * 
 */
public class ViewFlightSectorRevenueMapper implements
		Mapper<SectorRevenueDetailsVO> {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCCAMapper";

	private static final String CURRENCY = "NZD";

	/**
	 * 
	 * @param rs
	 * @return SectorRevenueDetailsVO
	 * @throws SQLException
	 */
	public SectorRevenueDetailsVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		SectorRevenueDetailsVO sectorRevenueDetailsVO = new SectorRevenueDetailsVO();

		if (rs.getString("CMPCOD") != null) {
			sectorRevenueDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		}
		if (rs.getString("UPDGRSWGT") != null) {//modified for icrd-257974
			sectorRevenueDetailsVO.setGrossWeight(round(
					(rs.getDouble("UPDGRSWGT")), 3));
		}
		if (rs.getString("UPDWGTCHG") != null) {
			sectorRevenueDetailsVO.setWeightCharge(rs.getString("UPDWGTCHG"));
			if(rs.getString("SRCFLG")!=null && "D".equals(rs.getString("SRCFLG")) ){
				sectorRevenueDetailsVO.setCurrency(CURRENCY);
			}
		}
		if (rs.getString("WGTCHGBAS") != null) {
			
			 Money amount;
			 Money netreven;
			try {
				amount = CurrencyHelper.getMoney(CURRENCY);
				amount.setAmount(rs.getDouble("WGTCHGBAS"));
	    		sectorRevenueDetailsVO.setWeightChargeBase(amount);
	    		netreven = CurrencyHelper.getMoney(CURRENCY);					    		
				double netRevenue = (Double.parseDouble(rs.getString("WGTCHGBAS")));	/// (Double.parseDouble(rs.getString("UPDWGT")));
				netreven.setAmount(netRevenue);
				sectorRevenueDetailsVO.setNetRevenue(netreven);
				
			} catch (CurrencyException e) {
				// TODO Auto-generated catch block
				e.getErrorCode();
			}
    		
			
			//sectorRevenueDetailsVO.setWeightChargeBase(round((rs.getDouble("WGTCHGBAS")), 3));
		

		}
		if (rs.getString("DSN") != null) {
			sectorRevenueDetailsVO.setDsn(rs.getString("DSN"));
		}
		if (rs.getString("ACCSTA") != null) {
			sectorRevenueDetailsVO.setAccStatus(rs.getString("ACCSTA"));
		}
		if (rs.getString("SRCFLG") != null) {
			
			sectorRevenueDetailsVO.setErrorPresent(rs.getString("SRCFLG"));
		}
		
		//added by A-3229 for Despatch enquiry flown details
		if(rs.getString("SECFRM")!=null && rs.getString("SECTOO")!=null){
			String sectorFrom=rs.getString("SECFRM");
			String sectorTo=rs.getString("SECTOO");
			StringBuilder sector=new StringBuilder(sectorFrom).append("-").append(sectorTo);
			sectorRevenueDetailsVO.setSector(sector.toString());
		}
		if(rs.getString("SECFRM")!=null){
			sectorRevenueDetailsVO.setSegmentOrigin(rs.getString("SECFRM"));
		}
		if(rs.getString("SECTOO")!=null){
			sectorRevenueDetailsVO.setSegmentDestination(rs.getString("SECTOO"));
		}
		if(rs.getInt("FLTCARIDR")!=0){
			sectorRevenueDetailsVO.setFlightCarrierId(rs.getInt("FLTCARIDR"));
		}
		if(rs.getString("FLTNUM")!=null){
			sectorRevenueDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		}
		
		Date date = rs.getDate("FLTDAT");
		if(date != null){
			sectorRevenueDetailsVO.setFlightDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE,date));
		}
		if (rs.getInt("TOTPCS") != 0) {
			sectorRevenueDetailsVO.setPieces(rs.getInt("TOTPCS"));//modified for icrd-257974
		}
		if (rs.getString("BLGBAS") != null) {
			sectorRevenueDetailsVO.setBlgbas(rs.getString("BLGBAS"));
		}
		//Added By Deepthi for BUG 27213
		if (rs.getString("CSGDOCNUM") != null) {
			sectorRevenueDetailsVO.setCsgdocnum(rs.getString("CSGDOCNUM"));
		}
		if (rs.getInt("CSGSEQNUM") >0) {
			sectorRevenueDetailsVO.setCsgseqnum(rs.getInt("CSGSEQNUM"));
		}
		if (rs.getString("POACOD") != null) {
			sectorRevenueDetailsVO.setPoaCode(rs.getString("POACOD"));
		}
		return sectorRevenueDetailsVO;
	}

	/**
	 * Round a double value to a specified number of decimal places.
	 * 
	 * @Author A-3429
	 * @param val
	 *            the value to be rounded.
	 * @param places
	 *            the number of decimal places to round to.
	 * @return val rounded to places decimal places.
	 */
	private double round(double val, int places) {
		long factor = (long) Math.pow(10, places);
		val = val * factor;
		long tmp = Math.round(val);
		return (double) tmp / factor;
	}
}
