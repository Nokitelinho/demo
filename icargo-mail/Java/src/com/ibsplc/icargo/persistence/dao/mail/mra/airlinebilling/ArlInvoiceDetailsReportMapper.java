/*
 * ArlInvoiceDetailsReportMapper.java Created on Mar 17,2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

/**
 * @author a-2391
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ArlInvoiceDetailsReportVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Sandeep.T
 * Mapper for InvoiceDetailsReport.
 * 
 * 
 * Revision History
 * 
 * Version     Date 		Author 				Description
 * 
 * 0.1 		Mar 02 2007  Sandeep.T 		Initial draft
 * 
 * 0.2   DEC 22 2008 Muralee 			AirNz EnhanceMent
 */


public class  ArlInvoiceDetailsReportMapper implements  Mapper<ArlInvoiceDetailsReportVO> {
	private Log log = LogFactory.getLogger("MRA_ARLBILLING");	
	
	/**Collection<>
	 * MultiMapper for InvoiceDetailsReportVO
	 * @param rs
	 * @return List<InvoiceDetailsReportVO>
	 * @throws SQLException
	 */
	public   ArlInvoiceDetailsReportVO  map(ResultSet rs) throws SQLException {
		log.log(Log.FINE,"\n\n\n\n Inside  Mapper Classs ArlInvoiceDetailsReportMapper--->");
			
		ArlInvoiceDetailsReportVO invoiceDetailsReportVO = new ArlInvoiceDetailsReportVO();
		//AreaValidationVO vo=new  AreaValidationVO();
			invoiceDetailsReportVO =new ArlInvoiceDetailsReportVO();
		
			if(rs.getString( "ARLNAM" )!=null){			
				invoiceDetailsReportVO.setArlName(rs.getString( "ARLNAM" ));
				
			}
			if(rs.getString( "BLGADR" )!=null){			
				invoiceDetailsReportVO.setAddress(rs.getString( "BLGADR" ));
				
			}
			
			if(rs.getString( "CTYNAM" )!=null){			
				invoiceDetailsReportVO.setCity(rs.getString( "CTYNAM" ));
				
			}
			
			if(rs.getString( "BLGSTANAM" )!=null){			
				invoiceDetailsReportVO.setState(rs.getString( "BLGSTANAM" ));
				
			}
			
			if(rs.getString( "CNTNAM" )!=null){			
				invoiceDetailsReportVO.setCountry(rs.getString( "CNTNAM" ));
				
			}
			
			if(rs.getString( "BLGPHNONE" )!=null){			
				invoiceDetailsReportVO.setPhone1(rs.getString( "BLGPHNONE" ));
				
			}
			
			
			if(rs.getString( "BLGPHNTWO" )!=null){			
				invoiceDetailsReportVO.setPhone2(rs.getString( "BLGPHNTWO" ));
				
			}	
						
			if(rs.getString( "BLGFAX" )!=null){				
				invoiceDetailsReportVO.setFax(rs.getString( "BLGFAX" ));
				
			}
			
			if(rs.getString( "CLRPRD" )!=null){				
				invoiceDetailsReportVO.setClrPrd(rs.getString( "CLRPRD" ));
				
			}
			
			if(rs.getDate( "BLDDAT" ) != null) {
				invoiceDetailsReportVO.setBilledDate( new LocalDate(
						LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLDDAT" )));
				invoiceDetailsReportVO.setBilledDateString( new LocalDate(
						LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLDDAT" )).toDisplayDateOnlyFormat());
			  }
			
			
			if(rs.getString("BLGCURCOD")!=null){
			    invoiceDetailsReportVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
			}
			if(rs.getString("CRTCURCOD")!=null){
			    invoiceDetailsReportVO.setContractCurrencyCode(rs.getString("CRTCURCOD"));
			    
			}if(rs.getString("INVNUM")!=null){
				invoiceDetailsReportVO.setInvoiceNumber(rs.getString("INVNUM"));
			}
			if(rs.getString("MALCTGCOD")!=null){
				invoiceDetailsReportVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
			
			}
				//invoiceDetailsReportVO.setTotalAmountinBillingCurrency(rs.getDouble("TOTAMTBLGCUR"));
			    invoiceDetailsReportVO.setTotalAmountinBillingCurrency(rs.getDouble("TOTAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
				invoiceDetailsReportVO.setTotalAmountinContractCurrency(rs.getDouble("TOTAMTCRTCUR"));
				//invoiceDetailsReportVO.setTotalAmountinsettlementCurrency(rs.getDouble("AMTINUSD"));
				invoiceDetailsReportVO.setTotalAmountinsettlementCurrency(rs.getDouble("NETAMTUSD")); //Modified by A-7929 as part of ICRD-265471
			
		return invoiceDetailsReportVO;
		
	}
}
