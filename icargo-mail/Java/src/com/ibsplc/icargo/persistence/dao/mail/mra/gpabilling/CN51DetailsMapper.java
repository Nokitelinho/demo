/*
 * CN51DetailsMapper.java Created on Jan 9, 2007
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
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;

import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1556
 *
 */
public class CN51DetailsMapper implements Mapper<CN51DetailsVO> {

	private Log log = LogFactory.getLogger("MRA GPABILLING");
    private static final String PASS_PA ="PA";
 private static final String C51DTLCURCOD="C51DTLCURCOD";
 private static final String BLGCURCOD="BLGCURCOD";
 private static final String TOTAMTSTLCUR="TOTAMTSTLCUR";
 private static final String VATAMT="VATAMT";
 private static final String C51DTLINVNUM="C51DTLINVNUM";

	/**
	 * @param resultSet
	 * @throws SQLException
	 * @return cn51DetailsVO
	 */
    public CN51DetailsVO map(ResultSet resultSet) throws SQLException {
    	log.entering("CN51DetailsMapper","map");
    	CN51DetailsVO cn51DetailsVO=new CN51DetailsVO();
    	cn51DetailsVO.setCompanyCode(resultSet.getString("C51DTLCMPCOD"));
    	cn51DetailsVO.setAirlineCode(resultSet.getString("C51DTLCMPCOD"));
    	cn51DetailsVO.setGpaCode(resultSet.getString("C51DTLGPACOD"));
    	cn51DetailsVO.setInvoiceNumber(resultSet.getString(C51DTLINVNUM));
    	cn51DetailsVO.setMailCategoryCode(resultSet.getString("C51DTLMALCTGCOD"));
    	cn51DetailsVO.setMailSubclass(resultSet.getString("C51DTLMALSUBCLS"));
    	cn51DetailsVO.setOrigin(resultSet.getString("C51DTLORGCOD"));
    	cn51DetailsVO.setDestination(resultSet.getString("C51DTLDSTCOD"));
    	cn51DetailsVO.setTotalPieces(resultSet.getInt("C51DTLTOTPCS"));
    	cn51DetailsVO.setTotalWeight(resultSet.getDouble("C51DTLTOTWGT"));
    	cn51DetailsVO.setApplicableRate(resultSet.getDouble("C51DTLAPLRAT"));
    	//Commented as part of MRA revamp
    	/*if(resultSet.getDate("FLTDAT")!=null){
    		cn51DetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("FLTDAT") ));
		}*/
    	cn51DetailsVO.setSector(resultSet.getString("SECTOR"));
    	cn51DetailsVO.setBillingPeriod(resultSet.getString("BLDPRD"));    	
    	cn51DetailsVO.setInvoiceStatus(resultSet.getString("INVSTA")); 
    	cn51DetailsVO.setDistance(resultSet.getString("CTYPIRDIS"));
    	cn51DetailsVO.setPoaAddress(resultSet.getString("POAADR"));
    	cn51DetailsVO.setCity(resultSet.getString("CTYNAM"));
    	cn51DetailsVO.setPinCode(resultSet.getString("POSCOD"));
    	cn51DetailsVO.setSurCharge(resultSet.getString("OTHCHG"));
    	cn51DetailsVO.setMailCharge(resultSet.getString("MALCHG"));
    	if(PASS_PA.equals(resultSet.getString("POATYP"))){
    		cn51DetailsVO.setFileName(resultSet.getString("INTFCDFILNAM"));
    	}
    	cn51DetailsVO.setGpaType(resultSet.getString("POATYP"));
    	log.log(log.INFO,"before money");
    	//Added for money component starts
    	try{
    	if(resultSet.getString(C51DTLCURCOD)!=null && resultSet.getString(C51DTLCURCOD).trim().length()>0 ){
    		
    		Money totAmt= CurrencyHelper.getMoney(resultSet.getString(C51DTLCURCOD));
    		
    		totAmt.setAmount(resultSet.getDouble("C51DTLTOTAMT"));
    		
    		cn51DetailsVO.setTotalAmount(totAmt);
    		//Added by A-8527 for ICRD-351434
    		cn51DetailsVO.setTotAmt(totAmt.getAmount());
    	}
    	if(resultSet.getString(BLGCURCOD)!=null && resultSet.getString(BLGCURCOD).trim().length()>0 ){
    		
    		Money billedAmt= CurrencyHelper.getMoney(resultSet.getString(BLGCURCOD));
    		
    		billedAmt.setAmount(resultSet.getDouble(TOTAMTSTLCUR));
    		
    		cn51DetailsVO.setTotalBilledAmount(billedAmt);
    		cn51DetailsVO.setScalarTotalBilledAmount(resultSet.getDouble(TOTAMTSTLCUR));
    	}
    	if(resultSet.getString(VATAMT)!=null && resultSet.getString(VATAMT).trim().length()>0 ){
    		
    		Money vatAmt= CurrencyHelper.getMoney(resultSet.getString(BLGCURCOD));
    		
    		vatAmt.setAmount(resultSet.getDouble(VATAMT));
    		
    		cn51DetailsVO.setVatAmount(vatAmt);
    	}
    	}
    	catch(CurrencyException currencyException){
    		currencyException.getErrorCode();
    	}
    	cn51DetailsVO.setSequenceNumber(resultSet.getInt("C51DTLSEQNUM"));
    	
    	//added for ICRD 7370
    	cn51DetailsVO.setServiceTax(resultSet.getDouble("SRVTAX"));
    	cn51DetailsVO.setTds(resultSet.getDouble("TDS"));
    	cn51DetailsVO.setNetAmount(resultSet.getDouble("NETAMT"));
    	cn51DetailsVO.setGrossAmount(resultSet.getDouble("C51DTLTOTAMT")); 
    	cn51DetailsVO.setBillingCurrencyCode(resultSet.getString(BLGCURCOD));
    	if(resultSet.getString("PARVAL")!=null ){
    	cn51DetailsVO.setConRatTax(resultSet.getString("PARVAL"));
    	}
    	cn51DetailsVO.setUnitCode(resultSet.getString("UNTCOD"));
    	cn51DetailsVO.setValCharges(resultSet.getDouble("VALCHG"));
    	
    	if(resultSet.getString("POANAM")!=null ){
        	cn51DetailsVO.setPoanam(resultSet.getString("POANAM"));
        	}
     	if(resultSet.getString("C51DTLINVDAT")!=null ){
        	cn51DetailsVO.setC51dtlinvdat(resultSet.getString("C51DTLINVDAT"));
        	}
     	if(resultSet.getString("C51DTLAPLRAT ")!=null ){
        	cn51DetailsVO.setAplrat(resultSet.getString("C51DTLAPLRAT"));
        	}

     	if(resultSet.getString("C51SMYCRTCURCOD")!=null ){
        	cn51DetailsVO.setC51smycrtcurcod(resultSet.getString("C51SMYCRTCURCOD"));
        	}
     	if(resultSet.getString("C51SMYTOTBLDAMT")!=null ){
        	cn51DetailsVO.setC51smytotbldamt(resultSet.getString("C51SMYTOTBLDAMT"));
        	}
    	if(resultSet.getString(TOTAMTSTLCUR)!=null ){
    		cn51DetailsVO.setScalarTotalBilledAmount(resultSet.getDouble(TOTAMTSTLCUR));
        	}
     	
    	if(resultSet.getString(C51DTLINVNUM)!=null)
		{
			String month =(resultSet.getString(C51DTLINVNUM).substring(0,3));
			int year = Integer.parseInt( (resultSet.getString(C51DTLINVNUM).substring(4, 6)));
			if("JAN".equals(month)||"FEB".equals(month)||"MAR".equals(month))
			{
				cn51DetailsVO.setInvNumberFinancial(resultSet.getString(C51DTLINVNUM)+"/"+(year-1)+"-"+year);
			}
			else
			{
				cn51DetailsVO.setInvNumberFinancial(resultSet.getString(C51DTLINVNUM)+"/"+year+"-"+(year+1));
			}
		}
		if(resultSet.getString("VATNUM")!=null)
		{
			cn51DetailsVO.setVatNumber(resultSet.getString("VATNUM"));
		}
		if(resultSet.getString("INVDATMMMFORMAT")!=null)
		{
			cn51DetailsVO.setInvDateMMMformat(resultSet.getString("INVDATMMMFORMAT"));
		}
    	
    	log.exiting("CN51DetailsMapper","map");
        return cn51DetailsVO;
    }

}
