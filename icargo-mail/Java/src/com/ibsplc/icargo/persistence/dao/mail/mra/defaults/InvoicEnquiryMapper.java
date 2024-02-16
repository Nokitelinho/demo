/*
 * InvoicEnquiryMapper.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */

public class InvoicEnquiryMapper implements Mapper<MailInvoicEnquiryDetailsVO> {

	private static final String CLASS_NAME = "InvoicEnquiryMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
    public MailInvoicEnquiryDetailsVO map(ResultSet rs) throws SQLException {
    	log.entering(CLASS_NAME,"Mapper");
    	
    	MailInvoicEnquiryDetailsVO mailInvoicEnquiryDetailsVO = new MailInvoicEnquiryDetailsVO();
    	mailInvoicEnquiryDetailsVO.setReceptacleIdentifier(rs.getString("RCPIDR"));
    	mailInvoicEnquiryDetailsVO.setSectorOrigin(rs.getString("SECORG"));
    	mailInvoicEnquiryDetailsVO.setSectorDestination(rs.getString("SECDST"));
    	try{
    	if(rs.getString("STLCURCOD")!=null && rs.getString("STLCURCOD").trim().length()>0){
    		Money basAmt=CurrencyHelper.getMoney(rs.getString("STLCURCOD"));
    		basAmt.setAmount(rs.getDouble("BASTOTAMT"));
    	    mailInvoicEnquiryDetailsVO.setBaseTotalAmount(basAmt);
    	    Money totAdjAmt=CurrencyHelper.getMoney(rs.getString("STLCURCOD"));
    	    totAdjAmt.setAmount(rs.getDouble("TOTAJDAMT"));
    	    mailInvoicEnquiryDetailsVO.setTotalAdjustmentAmount(totAdjAmt);
    	    
    	    Money adjTotAmt=CurrencyHelper.getMoney(rs.getString("STLCURCOD"));
    	    adjTotAmt.setAmount(rs.getDouble("ADJTOTPMT"));
    	    mailInvoicEnquiryDetailsVO.setAdjustmentTotalAmount(adjTotAmt);
    	}
    	}catch(CurrencyException e){
    		e.getErrorCode();
    	}
    	mailInvoicEnquiryDetailsVO.setRateTypeIndicator(rs.getString("RATTYPIND"));
    	mailInvoicEnquiryDetailsVO.setGcm(rs.getString("GCM"));
    	mailInvoicEnquiryDetailsVO.setConsignmentDocumentNumber(rs.getString("CONDOCNUM"));
    	mailInvoicEnquiryDetailsVO.setLHDollarRate(rs.getDouble("LINHALDOLRAT"));
    	mailInvoicEnquiryDetailsVO.setTHDollarRate(rs.getDouble("TERHDLDOLRAT"));
    	mailInvoicEnquiryDetailsVO.setContainerWeight(rs.getDouble("CNTWGT"));
    	mailInvoicEnquiryDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
    	mailInvoicEnquiryDetailsVO.setPoaCode(rs.getString("POACOD"));
    	mailInvoicEnquiryDetailsVO.setPaymentType(rs.getString("PAYTYP"));
    	mailInvoicEnquiryDetailsVO.setReconcilStatus(rs.getString("INVADVRCLSTA"));
    	mailInvoicEnquiryDetailsVO.setCarrierCode(rs.getString("CARCOD"));
    	mailInvoicEnquiryDetailsVO.setCarrierName(rs.getString("CARNAM"));
    	mailInvoicEnquiryDetailsVO.setContractType(rs.getString("CNTTYP"));
    	mailInvoicEnquiryDetailsVO.setInvoiceKey(rs.getString("INVKEY"));
		 if(rs.getDate("SCHINVDAT") != null){
			 mailInvoicEnquiryDetailsVO.setScheduledInvoiceDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("SCHINVDAT")));				
		   }
		   // for 
		   mailInvoicEnquiryDetailsVO.setPayCarrier(rs.getString("PAYCAR"));   
		    mailInvoicEnquiryDetailsVO.setOriginAirport(rs.getString("ORGPRT"));
		    mailInvoicEnquiryDetailsVO.setDestinationAirport(rs.getString("DSTPRT"));	
		    mailInvoicEnquiryDetailsVO.setCarrierFinalDest(rs.getString("CARFINDST"));
		    mailInvoicEnquiryDetailsVO.setCarrierAssigned(rs.getString("CARASG"));
		    mailInvoicEnquiryDetailsVO.setScanCarrier(rs.getString("SCNCARCOD"));
		    //private String scanLocation;
		    if(rs.getDate("DEPDAT") != null){
				 mailInvoicEnquiryDetailsVO.setDepartureDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("DEPDAT")));				
			   }
		    if(rs.getDate("ARLDAT") != null){
				 mailInvoicEnquiryDetailsVO.setArrivalDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("ARLDAT")));				
			   }
		    if(rs.getDate("DLVSCNDAT") != null){
				 mailInvoicEnquiryDetailsVO.setDLVScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("DLVSCNDAT")));				
			   }
		    //private LocalDate scanDate;
		    mailInvoicEnquiryDetailsVO.setPossessionCarrier(rs.getString("POSSCNCAR"));
		    mailInvoicEnquiryDetailsVO.setPossessionLocation(rs.getString("POSSCNLOC"));
		    if(rs.getDate("POSSCNDAT") != null){
				 mailInvoicEnquiryDetailsVO.setPossessionDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("POSSCNDAT")));				
			   }
		    mailInvoicEnquiryDetailsVO.setDLVScanCarrier(rs.getString("DLVSCNCAR"));
		    mailInvoicEnquiryDetailsVO.setDLVScanLocation(rs.getString("DLVSCNLOC"));
		    // for payment price details
		   
		    //mailInvoicEnquiryDetailsVO.setLineHaulCharge(rs.getDouble(""));
			//private double lineHaulCaharge;
		    //private double terminalHandlingCharge;
		    mailInvoicEnquiryDetailsVO.setLineHaulDollarRate(rs.getDouble("LINHALDOLRAT"));
		    mailInvoicEnquiryDetailsVO.setLineHaulSDRRate(rs.getDouble("LINHALSDRRAT"));
		    mailInvoicEnquiryDetailsVO.setTHDollarRate(rs.getDouble("TERHDLDOLRAT"));
		    mailInvoicEnquiryDetailsVO.setTHSDRRate(rs.getDouble("TERHDLSDRRAT"));
		    mailInvoicEnquiryDetailsVO.setContainerRate(rs.getDouble("CNTRAT"));
		    mailInvoicEnquiryDetailsVO.setLineHaulCharge(rs.getDouble("LINHALCHG"));
		    mailInvoicEnquiryDetailsVO.setTerminalHandlingCharge(rs.getDouble("TERHANCHG"));
		  //  mailInvoicEnquiryDetailsVO.sett(rs.getDouble("ADJTOTPMT"));
		    if(rs.getDate("SDRDAT") != null){
		    	mailInvoicEnquiryDetailsVO.setSDRDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("SDRDAT")));				
			   }
		    mailInvoicEnquiryDetailsVO.setSDRRate(rs.getDouble("SDRRAT"));
			return mailInvoicEnquiryDetailsVO;
    }

}
