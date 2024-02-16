/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.CN66DetailsReportMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Aug 13, 2015
 *
 *  Copyright 2015 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.CN66DetailsReportMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Aug 13, 2015	:	Draft
 */
public class CN66DetailsReportMapper implements Mapper<CN66DetailsVO> {
	
	private Log log = LogFactory.getLogger("MRA GPABILLING");
	private static final String BLDAMT="BLDAMT";
	private static final String FLTDAT="FLTDAT";
	private static final String BLDPRD="BLDPRD";
	private static final String C51SMYBLGCURCOD="C51SMYBLGCURCOD";
	private static final String CN66INVNUM="CN66INVNUM";
	/**
     * @param resultSet
     * @return CN66DetailsVO
     * @throws SQLException
     */
    public CN66DetailsVO map(ResultSet resultSet) throws SQLException {

    	log.entering("CN66DetailsMapper","map");
    	CN66DetailsVO cn66DetailsVO=new CN66DetailsVO();
    	cn66DetailsVO.setCompanyCode(resultSet.getString("CN66CMPCOD"));
    	cn66DetailsVO.setGpaCode(resultSet.getString("CN66GPACOD"));
    	cn66DetailsVO.setCcaRefNo(resultSet.getString("CCAREFNUM"));
    	cn66DetailsVO.setInvoiceNumber(resultSet.getString(CN66INVNUM));
    	cn66DetailsVO.setMailCategoryCode(resultSet.getString("CN66MALCTGCOD"));
    	cn66DetailsVO.setMailSubclass(resultSet.getString("CN66MALSUBCLS"));
    	if("LC".equals(cn66DetailsVO.getMailSubclass())){
    		cn66DetailsVO.setWeightLC(resultSet.getDouble("CN66TOTWGT"));    		
		}
    	else if("CP".equals(cn66DetailsVO.getMailSubclass())){
    		cn66DetailsVO.setWeightCP(resultSet.getDouble("CN66TOTWGT"));    		
		}
    	else if("SV".equals(cn66DetailsVO.getMailSubclass())){
    		cn66DetailsVO.setWeightSV(resultSet.getDouble("CN66TOTWGT"));    		
		}
    	else if("EMS".equals(cn66DetailsVO.getMailSubclass())){
    		cn66DetailsVO.setWeightEMS(resultSet.getDouble("CN66TOTWGT"));    		
		}
    	cn66DetailsVO.setActualSubCls(resultSet.getString("ACTSUBCLS"));
    	cn66DetailsVO.setOrigin(resultSet.getString("CN66ORGCOD"));
    	cn66DetailsVO.setDestination(resultSet.getString("CN66DSTCOD"));
    	if(resultSet.getString("CN66RCVDAT")!=null){
    	cn66DetailsVO.setReceivedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("CN66RCVDAT")));
    	}
    	cn66DetailsVO.setDsn(resultSet.getString("CN66DSN"));
    	cn66DetailsVO.setRsn(resultSet.getString("CN66RSN"));
    	cn66DetailsVO.setHsn(resultSet.getString("HSN"));
    	cn66DetailsVO.setRegInd(resultSet.getString("REGIND"));
    	cn66DetailsVO.setRemarks(resultSet.getString("CN66RMK"));
    	cn66DetailsVO.setSequenceNumber(resultSet.getInt("CN66SEQNUM"));
    	cn66DetailsVO.setTotalPieces(resultSet.getInt("CN66TOTPCS"));
    	cn66DetailsVO.setTotalWeight(resultSet.getDouble("CN66TOTWGT"));
    	cn66DetailsVO.setBillingBasis(resultSet.getString("CN66BLGBAS"));
    	cn66DetailsVO.setMailCharge(resultSet.getString("MALCHG"));
    	cn66DetailsVO.setSurCharge(resultSet.getString("OTHCHG")); 
    	cn66DetailsVO.setConsDocNo(resultSet.getString("CSGDOCNUM"));
    	cn66DetailsVO.setConsSeqNo(resultSet.getString("CSGSEQNUM"));
    	cn66DetailsVO.setSectorForReport(resultSet.getString("SECTOR"));
    	log.log(Log.INFO, "Sector.....", cn66DetailsVO.getSectorForReport());
    	if(resultSet.getString("BILFRM")!=null &&resultSet.getString("BILTOO")!=null ){
    	cn66DetailsVO.setSector(resultSet.getString("BILFRM").concat("-").concat(resultSet.getString("BILTOO")));
    	cn66DetailsVO.setBillFrm(resultSet.getString("BILFRM"));
    	cn66DetailsVO.setBillTo(resultSet.getString("BILTOO"));
    	}
    	String flightNumber = resultSet.getString("FLTNUM");
    	if(resultSet.getString("FLTCARCOD")!=null && flightNumber!=null ){
    		//Modified by A-7794 as part of ICRD-234354
    	  cn66DetailsVO.setFlightNumber(resultSet.getString("FLTCARCOD").concat("-").
    			  concat(flightNumber));
    	}
    	cn66DetailsVO.setDistance(resultSet.getString("CTYPIRDIS"));		   
		if (resultSet.getTimestamp(FLTDAT) != null) {
			cn66DetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, resultSet.getTimestamp(FLTDAT)));
		}
    	cn66DetailsVO.setFuelSurchargeRateIndicator(resultSet.getString("FULCHGRATIND"));
    	cn66DetailsVO.setFuelSurcharge(resultSet.getDouble("FULCHG"));
    	cn66DetailsVO.setLastUpdatedUser(resultSet.getString("LSTUPDUSR"));
    	if(resultSet.getTimestamp("LSTUPDTIM")!=null){
        	cn66DetailsVO.setLastupdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
        }
    	cn66DetailsVO.setBillingStatus(resultSet.getString("BLGSTA"));
    	cn66DetailsVO.setAmount(resultSet.getDouble(BLDAMT));
    	cn66DetailsVO.setApplicableRate(resultSet.getDouble("APLRAT"));
    	cn66DetailsVO.setBillingPeriod(resultSet.getString(BLDPRD));
    	cn66DetailsVO.setValCharges(resultSet.getDouble("VALCHG"));
    	//Added by A-6991 for ICRD-213474
    	cn66DetailsVO.setDeclaredValue(resultSet.getDouble("DCLVAL"));
    	if(resultSet.getString(BLDPRD)!=null){
    		cn66DetailsVO.setBlgPrd(resultSet.getString(BLDPRD));
    	}
    	cn66DetailsVO.setCurrencyCode(resultSet.getString(C51SMYBLGCURCOD));
    	try {
			if (resultSet.getString(C51SMYBLGCURCOD) != null
					&& resultSet.getString(C51SMYBLGCURCOD).trim().length() > 0) {

				Money billedAmt = CurrencyHelper.getMoney(resultSet
						.getString(C51SMYBLGCURCOD));
				billedAmt.setAmount(resultSet.getDouble(BLDAMT));
				cn66DetailsVO.setActualAmount(billedAmt);
				
				Money vatAmt = CurrencyHelper.getMoney(resultSet
						.getString(C51SMYBLGCURCOD));
				vatAmt.setAmount(resultSet.getDouble("VATAMT"));
				cn66DetailsVO.setVatAmount(vatAmt);
				Money amtInEMS=CurrencyHelper.getMoney(resultSet
						.getString(C51SMYBLGCURCOD));
				amtInEMS.setAmount(resultSet.getDouble("TOTAMTEMS"));
				cn66DetailsVO.setTotalWgtinEMS(amtInEMS);
				Money amtInSVS=CurrencyHelper.getMoney(resultSet
						.getString(C51SMYBLGCURCOD));
				amtInSVS.setAmount(resultSet.getDouble("TOTAMTSVS"));
				cn66DetailsVO.setTotalWgtinSV(amtInSVS);
				Money netAmount = null;
				netAmount=CurrencyHelper.getMoney(resultSet
						.getString(C51SMYBLGCURCOD));
				netAmount.setAmount(resultSet.getDouble("NETAMT"));
				cn66DetailsVO.setNetAmount(netAmount);
				cn66DetailsVO.setNetamtdouble(resultSet.getDouble("NETAMT"));
				cn66DetailsVO.setScalarNetAmount(resultSet.getDouble("NETAMT"));
				//Added by A-8527 for ICRD-351434
				cn66DetailsVO.setDb_netAmount(netAmount.getAmount());
			}
			Money totalAmtinLC = CurrencyHelper.getMoney("XDR");
			totalAmtinLC.setAmount(resultSet.getDouble("TOTAMTLC"));
			cn66DetailsVO.setTotalAmtinLC(totalAmtinLC);
			Money totalAmtinCP = CurrencyHelper.getMoney("XDR");
			totalAmtinCP.setAmount(resultSet.getDouble("TOTAMTCP"));
			cn66DetailsVO.setTotalAmtinCP(totalAmtinCP);
		} catch (CurrencyException currencyException) {
			currencyException.getErrorCode();
		}
		cn66DetailsVO.setServiceTax(resultSet.getDouble("SRVTAX"));
		cn66DetailsVO.setTds(resultSet.getDouble("TDS"));
		cn66DetailsVO.setMailSequenceNumber(resultSet.getLong("MALSEQNUM"));//added by A-7371 for ICRD-259050 
		cn66DetailsVO.setSettlementCurrencyCode(resultSet.getString("STLCURCOD")); //added by A-8527 for ICRD-324283
		cn66DetailsVO.setWeight(resultSet.getDouble("CN66TOTWGT"));
		cn66DetailsVO.setUnitcode(resultSet.getString("UNTCOD"));// Added by A-8527 for IASCB-22915
		if("Y".equals(resultSet.getString("POAFLG"))){
		cn66DetailsVO.setContainerNumber(resultSet.getString("CNTIDR"));
		}
		
		/**
		 * @author A-10383
		 */
		
		if(resultSet.getString("CN66ORGCOD")!=null)
		{
			cn66DetailsVO.setCn66orgcod(resultSet.getString("CN66ORGCOD"));
		}
		
		if(resultSet.getString("CN66DSTCOD")!=null)
		{
			cn66DetailsVO.setCn66dstcod(resultSet.getString("CN66DSTCOD"));
		}
		
		if(resultSet.getString("CN66MALCTGCOD")!=null)
		{
			cn66DetailsVO.setCn66malctgcod(resultSet.getString("CN66MALCTGCOD"));
		}
		
		if(resultSet.getString("CN66DSN")!=null)
		{
			cn66DetailsVO.setCn66dsn(resultSet.getString("CN66DSN"));
		}
		if(resultSet.getString("CNTIDR")!=null)
		{
			cn66DetailsVO.setCntidr(resultSet.getString("CNTIDR"));
		}
		if(resultSet.getString("FLTNUM")!=null)
		{
			cn66DetailsVO.setFltnum(resultSet.getString("FLTNUM"));
		}
		
		if(resultSet.getString(FLTDAT)!=null)
		{
			cn66DetailsVO.setFltdat(resultSet.getString(FLTDAT));
		}
		
		if(resultSet.getString("CN66MALSUBCLS")!=null)
		{
			cn66DetailsVO.setCn66malsubcls(resultSet.getString("CN66MALSUBCLS"));
		}
		
		if(resultSet.getString("CN66TOTWGT")!=null)
		{
			cn66DetailsVO.setCn66totwgt(resultSet.getString("CN66TOTWGT"));
		}
		
		if(resultSet.getString(BLDAMT)!=null)
		{
			cn66DetailsVO.setBldamt(resultSet.getString(BLDAMT));
		}
		
		if(resultSet.getString("TOTAMTCP")!=null)
		{
			cn66DetailsVO.setTotamtcp(resultSet.getString("TOTAMTCP"));
		}
		
		if(resultSet.getString("SRVTAX")!=null)
		{
			cn66DetailsVO.setSrvtax(resultSet.getString("SRVTAX"));
		}
		
		if(resultSet.getString("NETAMT")!=null)
		{
			cn66DetailsVO.setNetamt(resultSet.getString("NETAMT"));
		}
		if(resultSet.getString("TOTAL")!=null)
		{
			cn66DetailsVO.setTotal(resultSet.getString("TOTAL"));
		}
		
		if(resultSet.getString(C51SMYBLGCURCOD)!=null)
		{
			cn66DetailsVO.setC51smyblgcurcod(resultSet.getString(C51SMYBLGCURCOD));
		}
		
		
		if(resultSet.getString("C51SMYCTRCURCOD")!=null)
		{
			cn66DetailsVO.setC51smyctrcurcod(resultSet.getString("C51SMYCTRCURCOD"));
		}
		if(resultSet.getString("TOTNETBLGCUR")!=null)
		{
			cn66DetailsVO.setTotnetblgcur(resultSet.getString("TOTNETBLGCUR"));
		}
		if(resultSet.getString("TOTNETCTRCUR")!=null)
		{
			cn66DetailsVO.setTotnetctrcur(resultSet.getString("TOTNETCTRCUR"));
		}
		
		
		if(resultSet.getString(BLDPRD)!=null)
		{
			cn66DetailsVO.setBldprd(resultSet.getString(BLDPRD));
		}
		if(resultSet.getString("POANAM")!=null)
		{
			cn66DetailsVO.setPonam(resultSet.getString("POANAM"));
		}
		if(resultSet.getString("POAADR")!=null)
		{
			cn66DetailsVO.setPoaadr(resultSet.getString("POAADR"));
		}
		if(resultSet.getString("INVDAT")!=null)
		{
			cn66DetailsVO.setInvdat(resultSet.getString("INVDAT"));
		}
		if(resultSet.getString("APLRAT")!=null)
		{
			cn66DetailsVO.setRate(resultSet.getString("APLRAT"));
		}
		if(resultSet.getString("MCA")!=null)
		{
			cn66DetailsVO.setMca(resultSet.getString("MCA"));
		}
		if(resultSet.getString("CN66GPACOD")!=null)
		{
			cn66DetailsVO.setCn66gpacod(resultSet.getString("CN66GPACOD"));
		}
		
		if(resultSet.getString(CN66INVNUM)!=null)
		{		
            cn66DetailsVO.setCn66invnum(resultSet.getString(CN66INVNUM));
			String month =(resultSet.getString(CN66INVNUM).substring(0,3));
			int year = Integer.parseInt( (resultSet.getString(CN66INVNUM).substring(4, 6)));
			if("JAN".equals(month)||"FEB".equals(month)||"MAR".equals(month))
			{
				cn66DetailsVO.setInvNumberFinancial(resultSet.getString(CN66INVNUM)+"/"+(year-1)+"-"+year);
			}
			else
			{
				cn66DetailsVO.setInvNumberFinancial(resultSet.getString(CN66INVNUM)+"/"+year+"-"+(year+1));
			}
		}
		if(resultSet.getString("VATNUM")!=null)
		{
			cn66DetailsVO.setVatNumber(resultSet.getString("VATNUM"));
		}
		if(resultSet.getString("INVDATMMMFORMAT")!=null)
		{
			cn66DetailsVO.setInvDateMMMformat(resultSet.getString("INVDATMMMFORMAT"));
		}
		
    	log.exiting("CN66DetailsMapper","map");
        return cn66DetailsVO;
    }


}
