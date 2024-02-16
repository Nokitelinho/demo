/*
 * BillingSummaryDetailsMapper.java Created on Mar 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2408
 *
 */
public class BillingSummaryDetailsMapper implements Mapper<BillingSummaryDetailsVO> {

	private Log log = LogFactory.getLogger("MRA GPABILLING");


	/**
	 * @param resultSet
	 * @throws SQLException
	 * @return billingSummaryDetailsVO
	 */
    public BillingSummaryDetailsVO map(ResultSet resultSet) throws SQLException {
    	log.entering("BillingSummaryDetailsMapper","map");
    	BillingSummaryDetailsVO billingSummaryDetailsVO=new BillingSummaryDetailsVO();
    	billingSummaryDetailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
    	billingSummaryDetailsVO.setBillingPeriod(resultSet.getString("BLDPRD"));
    	billingSummaryDetailsVO.setGpaCode(resultSet.getString("GPACOD"));
    	billingSummaryDetailsVO.setGpaName(resultSet.getString("POANAM"));
    	billingSummaryDetailsVO.setInvoiceNumber(resultSet.getString("INVNUM"));
    	if(resultSet.getString("BLDDAT")!=null){
    		billingSummaryDetailsVO.setInvoiceDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("BLDDAT")));
    	}
    	billingSummaryDetailsVO.setCurrency(resultSet.getString("CRTCURCOD"));
    	billingSummaryDetailsVO.setBilledValue(resultSet.getDouble("TOTAMTCTRCUR"));
    	billingSummaryDetailsVO.setGrandTotal(resultSet.getDouble("TOTAMTBASCUR"));//Added for ICRD-105572
    	log.exiting("BillingSummaryDetailsMapper","map");
        return billingSummaryDetailsVO;
    }

}
