/*
 * InvoiceDetailsReportMapper.java Created on Dec 22-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

/**
 * @author a-3447
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
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


public class  InvoiceDetailsReportMapper implements  Mapper<InvoiceDetailsReportVO> {
	private Log log = LogFactory.getLogger("MRA_GPABILLING");	
	private static final String BLDPRD="BLDPRD";
	private static final String BLDDAT = "BLDDAT";
	private static final String BLGCURCOD="BLGCURCOD";
	private static final String INVNUM="INVNUM";
	/**Collection<>
	 * MultiMapper for InvoiceDetailsReportVO
	 * @param rs
	 * @return List<InvoiceDetailsReportVO>
	 * @throws SQLException
	 */
	public   InvoiceDetailsReportVO  map(ResultSet rs) throws SQLException {
		log.log(Log.FINE,"\n\n\n\n Inside  Mapper Classs InvoiceDetailsReportMapper--->");
			
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
			refactor3(rs, invoiceDetailsReportVO);
			if(rs.getString( "BLGPRDFRM" )!=null){			
				invoiceDetailsReportVO.setFromDateString(rs.getString( "BLGPRDFRM" ));
			}
			if(rs.getString( "BLGPRDTOO" )!=null){			
				invoiceDetailsReportVO.setToDateString(rs.getString( "BLGPRDTOO" ));
			}
			if(rs.getString( "BNKNAM" )!=null){		
				invoiceDetailsReportVO.setBankName(rs.getString( "BNKNAM" ));				
			}
			if(rs.getDate( BLDDAT ) != null) {
				invoiceDetailsReportVO.setBilledDate( new LocalDate(
						LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( BLDDAT )));
				invoiceDetailsReportVO.setBilledDateString( new LocalDate(
						LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( BLDDAT )).toDisplayDateOnlyFormat());
			  }
			if(rs.getString(BLGCURCOD)!=null){
			    invoiceDetailsReportVO.setBillingCurrencyCode(rs.getString(BLGCURCOD));
			}
			if(rs.getString("CRTCURCOD")!=null){
			    invoiceDetailsReportVO.setContractCurrencyCode(rs.getString("CRTCURCOD"));
			}
			if(rs.getString(INVNUM)!=null){
				invoiceDetailsReportVO.setInvoiceNumber(rs.getString(INVNUM));
			}
			if(rs.getString("RMK")!=null)
			{
				invoiceDetailsReportVO.setFreeText(rs.getString("RMK"));
			}
			if(rs.getString(BLDPRD)!=null){
				
				invoiceDetailsReportVO.setBilledDateString(rs.getString(BLDPRD));
				
			}
			//added for sonar 
			refactor2(rs, invoiceDetailsReportVO);
			refactorcn66(rs, invoiceDetailsReportVO);
			//Added by A-8527 for ICRD-332631 starts
			if(rs.getLong("DUEDAY") >0){			
				invoiceDetailsReportVO.setDuedays(rs.getLong("DUEDAY"));
			}else {
				invoiceDetailsReportVO.setDuedays(0);
			}
			//Added by A-8527 for ICRD-332631 Ends
				invoiceDetailsReportVO.setTotalAmountinBillingCurrency(rs.getDouble("TOTAMTBLGCUR"));
				invoiceDetailsReportVO.setTotalAmountinContractCurrency(rs.getDouble("TOTAMTCRTCUR"));
				invoiceDetailsReportVO.setTotalAmountinsettlementCurrency(rs.getDouble("TOTAMTSTLCUR"));
				//Added by a-7871 for ICRD-214766 starts--
				try {
					Money totalAmtBillCurr = CurrencyHelper.getMoney(rs.getString(BLGCURCOD));
					totalAmtBillCurr.setAmount(rs.getDouble("TOTAMTBLGCUR"));
					invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtBillCurr);
				} catch (CurrencyException currencyException) {
					
					currencyException.getErrorCode();
				}
				//Added by a-7871 for ICRD-214766 ends--
		return invoiceDetailsReportVO;
	}

	private void refactor3(ResultSet rs, InvoiceDetailsReportVO invoiceDetailsReportVO) throws SQLException {
		if(rs.getString( "BNKBRN" )!=null){		
			invoiceDetailsReportVO. setBranch(rs.getString( "BNKBRN" ));				
		}
		if(rs.getString( "PHONE2" )!=null){			
			invoiceDetailsReportVO.setPhone2(rs.getString( "PHONE2" ));	
		}	
		if(rs.getString( "GPACOD" )!=null){			
			invoiceDetailsReportVO.setGpaCode(rs.getString( "GPACOD" ));
		}		
		if(rs.getString( "FAX" )!=null){				
			invoiceDetailsReportVO.setFax(rs.getString( "FAX" ));
		}			
		if(rs.getString( "VATNUM" )!=null){		
			invoiceDetailsReportVO.setVatNumber(rs.getString( "VATNUM" ));				
		}
	}

	private void refactor2(ResultSet rs, InvoiceDetailsReportVO invoiceDetailsReportVO) throws SQLException {
	
		if(rs.getString( "SWTCOD" )!=null){		
			invoiceDetailsReportVO.setSwiftCode(rs.getString( "SWTCOD" ));				
		}
		if(rs.getString( "ACCNUM" )!=null){		
			invoiceDetailsReportVO.setAccNo(rs.getString( "ACCNUM" ));				
		}
		if(rs.getString( "bnkdtlAccnum" )!=null){		
			invoiceDetailsReportVO.setBnkdtlAccnum(rs.getString( "bnkdtlAccnum" ));			
		}
		if(rs.getString("ARLADR")!=null){	
		invoiceDetailsReportVO.setAirlineAddress(rs.getString("ARLADR"));
		
		}
		if(rs.getString("SECTOR")!=null){
		    invoiceDetailsReportVO.setSector(rs.getString("SECTOR"));
		}
		if(rs.getString("MALCTGCOD")!=null){
			invoiceDetailsReportVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		}
		if(rs.getString("BLDAMT")!=null)
		{
			invoiceDetailsReportVO.setBldamt(rs.getString("BLDAMT"));
		}
		if(rs.getString("TOTAMTCP")!=null)
		{
			invoiceDetailsReportVO.setTotamtcp(rs.getString("TOTAMTCP"));
		}
		if(rs.getString("SRVTAX")!=null)
		{
			invoiceDetailsReportVO.setSrvtax(rs.getString("SRVTAX"));
		}
		if(rs.getString("NETAMT")!=null)
		{
			invoiceDetailsReportVO.setNetamt(rs.getString("NETAMT"));
		}
		if(rs.getString("C51SMYBLGCURCOD")!=null)
		{
			invoiceDetailsReportVO.setC51smyblgcurcod(rs.getString("C51SMYBLGCURCOD"));
		}
		if(rs.getString(BLDPRD)!=null)
		{
			invoiceDetailsReportVO.setBldprd(rs.getString(BLDPRD));
		}
		if(rs.getString("INVDAT")!=null)
		{
			invoiceDetailsReportVO.setCn66gpacod(rs.getString("INVDAT"));
		}
		if(rs.getString("CN66GPACOD")!=null)
		{
			invoiceDetailsReportVO.setCn66gpacod(rs.getString("CN66GPACOD"));
		}
		
		if(rs.getString("CN66INVNUM")!=null)
		{
			invoiceDetailsReportVO.setCn66invnum(rs.getString("CN66INVNUM"));
		}
		if(rs.getString( "DATEPERIOD" )!=null){			
			invoiceDetailsReportVO.setDateperiod(rs.getString( "DATEPERIOD" ));
		}
	}

	private void refactorcn66(ResultSet rs, InvoiceDetailsReportVO invoiceDetailsReportVO) throws SQLException {
		if(rs.getString( "POANAM" )!=null){			
			invoiceDetailsReportVO.setPaName(rs.getString( "POANAM" ));
		}
		if(rs.getString( "POAADR" )!=null){			
			invoiceDetailsReportVO.setAddress(rs.getString( "POAADR" ));
		}
		if(rs.getString( "CTYNAM" )!=null){			
			invoiceDetailsReportVO.setCity(rs.getString( "CTYNAM" ));
			
		}
		if(rs.getString( "STANAM" )!=null){			
			invoiceDetailsReportVO.setState(rs.getString( "STANAM" ));
		}
		if(rs.getString( "CNTCOD" )!=null){			
			invoiceDetailsReportVO.setCountry(rs.getString( "CNTCOD" ));
			
		}
		if(rs.getString( "PHNONE" )!=null){			
			invoiceDetailsReportVO.setPhone1(rs.getString( "PHNONE" ));
		}
		if(rs.getString("CN66ORGCOD")!=null)
		{
			invoiceDetailsReportVO.setCn66orgcod(rs.getString("CN66ORGCOD"));
		}
		
		if(rs.getString("CN66DSTCOD")!=null)
		{
			invoiceDetailsReportVO.setCn66dstcod(rs.getString("CN66DSTCOD"));
		}
		
		if(rs.getString("CN66MALCTGCOD")!=null)
		{
			invoiceDetailsReportVO.setCn66malctgcod(rs.getString("CN66MALCTGCOD"));
		}
		
		if(rs.getString("CN66DSN")!=null)
		{
			invoiceDetailsReportVO.setCn66dsn(rs.getString("CN66DSN"));
		}
		if(rs.getString("CNTIDR")!=null)
		{
			invoiceDetailsReportVO.setCntidr(rs.getString("CNTIDR"));
		}
		if(rs.getString("FLTNUM")!=null)
		{
			invoiceDetailsReportVO.setFltnum(rs.getString("FLTNUM"));
		}
		if(rs.getString("FLTDAT")!=null)
		{
			invoiceDetailsReportVO.setFltdat(rs.getString("FLTDAT"));
		}
		
		if(rs.getString("CN66MALSUBCLS")!=null)
		{
			invoiceDetailsReportVO.setCn66malsubcls(rs.getString("CN66MALSUBCLS"));
		}
		
		if(rs.getString("CN66TOTWGT")!=null)
		{
			invoiceDetailsReportVO.setCn66totwgt(rs.getString("CN66TOTWGT"));
		}
		if(rs.getString(INVNUM)!=null)
		{
			String month =(rs.getString(INVNUM).substring(0,3));
			int year = Integer.parseInt( (rs.getString(INVNUM).substring(4, 6)));
			if("JAN".equals(month)||"FEB".equals(month)||"MAR".equals(month))
			{
				invoiceDetailsReportVO.setInvNumberFinancial(rs.getString(INVNUM)+"/"+(year-1)+"-"+year);
			}
			else
			{
				invoiceDetailsReportVO.setInvNumberFinancial(rs.getString(INVNUM)+"/"+year+"-"+(year+1));
			}
		}
		if(rs.getString("INVDATMMMFORMAT")!=null)
		{
			invoiceDetailsReportVO.setInvDateMMMformat(rs.getString("INVDATMMMFORMAT"));
		}
		
		
	}
}
