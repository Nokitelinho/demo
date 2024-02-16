/*
 * CN51DetailsPrintMapper.java Created on Mar 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2408
 *
 */
public class CN51DetailsPrintMapper implements Mapper<CN51DetailsVO> {

	private Log log = LogFactory.getLogger("MRA GPABILLING");


	/**
	 * @param resultSet
	 * @throws SQLException
	 * @return vo
	 */
    public CN51DetailsVO map(ResultSet resultSet) throws SQLException {
    	log.entering("CN51DetailsPrintMapper","map");
    	CN51DetailsVO vo=new CN51DetailsVO();
    	vo.setCompanyCode(resultSet.getString("CMPCOD"));
    	vo.setBillingPeriod(resultSet.getString("BLDPRD"));
    	vo.setGpaCode(resultSet.getString("GPACOD"));
    	vo.setGpaName(resultSet.getString("POANAM"));
    	vo.setBillingCurrencyCode(resultSet.getString("BLGCURCOD"));
    	vo.setOrigin(resultSet.getString("ORGCOD"));
    	vo.setDestination(resultSet.getString("DSTCOD"));
    	vo.setMailCategoryCode(resultSet.getString("MALCTGCOD"));

    	vo.setNetAmount(resultSet.getDouble("NETAMTBLGCUR"));
    	if(("LC").equals(resultSet.getString("SUBCLSGRP"))){
    		vo.setTotalWeightLC(resultSet.getDouble("TOTWGT"));
    		vo.setDisplayWgtUnitLC(resultSet.getString("UNTCOD")); //Added by A-9002
    		vo.setApplicableRateLC(resultSet.getDouble("APLRAT"));
    		vo.setTotalAmountLC(resultSet.getDouble("TOTAMTBLGCUR"));
    		//Added for 76551
    		vo.setMailChgLC(resultSet.getDouble("MALCHGBLGCUR"));
    		vo.setSurChgLC(resultSet.getDouble("OTHCHGBLGCUR"));
    	}else if(("CP").equals(resultSet.getString("SUBCLSGRP"))){
    		vo.setTotalWeightCP(resultSet.getDouble("TOTWGT"));
    		vo.setDisplayWgtUnitCP(resultSet.getString("UNTCOD")); //Added by A-9002
    		vo.setApplicableRateCP(resultSet.getDouble("APLRAT"));
    		vo.setTotalAmountCP(resultSet.getDouble("TOTAMTBLGCUR"));
    		//Added for 76551 
    		vo.setMailChgCP(resultSet.getDouble("MALCHGBLGCUR"));
    		vo.setSurChgCP(resultSet.getDouble("OTHCHGBLGCUR"));
    	}else if(CN51DetailsVO.MAILSUBCLASS_SV.equals(resultSet.getString("SUBCLSGRP"))){
    		vo.setTotalWeightSV(resultSet.getDouble("TOTWGT"));
    		vo.setDisplayWgtUnitSV(resultSet.getString("UNTCOD")); //Added by A-9002
    		vo.setApplicableRateSV(resultSet.getDouble("APLRAT"));
    		vo.setTotalAmountSV(resultSet.getDouble("TOTAMTBLGCUR"));
    		 //Added by A-7929 for ICRD-260958 starts
    		vo.setMailChgSV(resultSet.getDouble("MALCHGBLGCUR"));
    		vo.setSurChgSV(resultSet.getDouble("OTHCHGBLGCUR"));
    		 //Added by A-7929 for ICRD-260958 ends
    	}else if(CN51DetailsVO.MAILSUBCLASS_EMS.equals(resultSet.getString("SUBCLSGRP"))){
    		vo.setTotalWeightEMS(resultSet.getDouble("TOTWGT"));
    		vo.setDisplayWgtUnitEMS(resultSet.getString("UNTCOD")); //Added by A-9002
    		vo.setApplicableRateEMS(resultSet.getDouble("APLRAT"));
    		vo.setTotalAmountEMS(resultSet.getDouble("TOTAMTBLGCUR"));
    		 //Added by A-7929 for ICRD-260958 starts
    		vo.setMailChgEMS(resultSet.getDouble("MALCHGBLGCUR"));
    		vo.setSurChgEMS(resultSet.getDouble("OTHCHGBLGCUR"));
    		 //Added by A-7929 for ICRD-260958 ends
    	}else{
    		vo.setTotalWeightCP(0.0);
    		vo.setApplicableRateCP(0.0);
    		vo.setTotalAmountCP(0.0);
    		vo.setDisplayWgtUnit("");
    	}  
    	//Code added by Manish for IASCB-40187 start
    	vo.setRate(resultSet.getDouble("APLRAT"));
		vo.setMailChrg(resultSet.getDouble("TOTAMTBLGCUR"));
		vo.setAmount(resultSet.getDouble("TOTAMTBLGCUR"));
		//Code added by Manish for IASCB-40187 end
    	vo.setMailSubclass(resultSet.getString("SUBCLSGRP"));
    	vo.setWeight(resultSet.getDouble("TOTWGT"));
    	vo.setUnitCode(resultSet.getString("UNTCOD"));
    	vo.setServiceTax(resultSet.getDouble("SRVTAX"));
    	vo.setConRatTax(resultSet.getString("PARVAL"));
    	log.exiting("CN51DetailsPrintMapper","map");
        return vo;
    }

}
