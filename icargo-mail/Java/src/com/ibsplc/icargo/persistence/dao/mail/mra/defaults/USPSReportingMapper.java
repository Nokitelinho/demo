

/*
 * USPSReportingMapper.java Created on Nov 12, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.USPSReportingVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3229
 *
 */
public class USPSReportingMapper implements Mapper {

	/**
	 * @author A-3229
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	 public USPSReportingVO map(ResultSet rs) throws SQLException {

	        Log log =LogFactory.getLogger("MRA DEFAULTS");
	        
	        log.entering("USPSReportingMapper" ,"MAP METHOD");
	        USPSReportingVO uspsReportingVO = new USPSReportingVO();
	        
	        //Money
	        Money baseTotalAmt=null;
	        //Money lhDollarRate=null;
	        //Money thDollarRate=null;
	        try{
	        uspsReportingVO.setDsnNumber(rs.getString("DSN"));
	        uspsReportingVO.setSectorDestiantion(rs.getString("SECTOO"));
	        uspsReportingVO.setSectorOrigin(rs.getString("SECFRM"));
	        uspsReportingVO.setGCM(rs.getInt("GCM"));
	        
	        if(rs.getDouble("DOTRAT")!=0){
	        baseTotalAmt = CurrencyHelper.getMoney("USD");
	        baseTotalAmt.setAmount(rs.getDouble("DOTRAT"));
	        uspsReportingVO.setBaseTotalAmt(baseTotalAmt);
	        }
	        
	      
	        uspsReportingVO.setLhDollarRate(rs.getDouble("LHLRAT"));
	        uspsReportingVO.setThDollarRate(rs.getDouble("THGRAT"));    
	        uspsReportingVO.setInvoiceNumber(rs.getString("INVKEY"));
	        
            if(rs.getDate("INVRCVDAT")!=null){
	        LocalDate invDate=new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("INVRCVDAT"));	
	        uspsReportingVO.setInvoiceDate(invDate);
            }
            
	        uspsReportingVO.setRecStatus(rs.getString("INVADVRCLSTA"));
	        
	        
	        }catch(CurrencyException e){
	        	e.getErrorCode();
	        }
	        
	        log.log(Log.INFO," USPSReportingMapper-----------> "+uspsReportingVO);
	        log.exiting("USPSReportingMapper","MAP METHOD");
	        return uspsReportingVO;
	}


}

