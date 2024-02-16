/* AirlineCN51DetailsMapper.java Created on Mar 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;


import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingPersistenceConstants;
/**
 *
 * @author A-2458
 *
 */
public class AirlineCN51DetailsMapper implements Mapper<AirlineCN51DetailsVO> {

	private Log log = LogFactory.getLogger("AirlineCN51DetailsMapper");

	private static final String CLASS_NAME = "AirlineCN51DetailsMapper";

	/**
	 * @return AirlineCN51DetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	public AirlineCN51DetailsVO map(ResultSet rs) 
	throws SQLException {
		log.entering(CLASS_NAME, "map");
		AirlineCN51DetailsVO airlineCN51DetailsVo = new AirlineCN51DetailsVO();
		airlineCN51DetailsVo.setAirlineidr(rs.getInt("ARLIDR"));
		airlineCN51DetailsVo.setClearanceperiod(rs.getString("CLRPRD"));
		airlineCN51DetailsVo.setInvoicenumber(rs.getString("INVNUM"));
		airlineCN51DetailsVo.setMailsubclass(rs.getString("SUBCLSGRP"));
		if((MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_LC)
				.equals(airlineCN51DetailsVo.getMailsubclass()))
			{
			airlineCN51DetailsVo.setWeightLC(rs.getDouble("TOTAMT"));
			}
		else if((MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_CP)
				.equals(airlineCN51DetailsVo.getMailsubclass()))
			{
			airlineCN51DetailsVo.setWeightCP(rs.getDouble("TOTAMT"));
			}else if(MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_SV.equals(airlineCN51DetailsVo.getMailsubclass())){
				airlineCN51DetailsVo.setWeightSV(rs.getDouble("TOTAMT"));
			}else if(MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_EMS.equals(airlineCN51DetailsVo.getMailsubclass())){
				airlineCN51DetailsVo.setWeightEMS(rs.getDouble("TOTAMT"));
			}
		airlineCN51DetailsVo.setAirlineCode(rs.getString("TWOAPHCOD"));
		airlineCN51DetailsVo.setAirlineNumber(rs.getString("THRNUMCOD"));
		airlineCN51DetailsVo.setAirlineName(rs.getString("ARLNAM"));
		//airlineCN51DetailsVo.setListingcurrencycode(rs.getString("LSTCUR"));//Added by A-5945	for ICRD-100255
		airlineCN51DetailsVo.setListingcurrencycode(rs.getString("LSTCURCOD"));
		log.exiting(CLASS_NAME, "map");
		return airlineCN51DetailsVo;
	}

}
