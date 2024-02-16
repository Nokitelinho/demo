/*
 * CN66DetailsMapper.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
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
 * @author A-1556
 *
 */
public class CN66DetailsMapper implements Mapper<CN66DetailsVO> {

	private Log log = LogFactory.getLogger("MRA GPABILLING");
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
    	cn66DetailsVO.setInvoiceNumber(resultSet.getString("CN66INVNUM"));
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
    	//ADDED BY INDU
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
    	cn66DetailsVO.setInvSerialNumber(resultSet.getInt("INVSERNUM"));
    	cn66DetailsVO.setTotalPieces(resultSet.getInt("CN66TOTPCS"));
    	cn66DetailsVO.setTotalWeight(resultSet.getDouble("CN66TOTWGT"));
    	cn66DetailsVO.setBillingBasis(resultSet.getString("CN66BLGBAS"));
    	cn66DetailsVO.setMailCharge(resultSet.getString("MALCHG"));
    	cn66DetailsVO.setSurCharge(resultSet.getString("OTHCHG"));
    	
    	cn66DetailsVO.setContainerNumber(resultSet.getString("CNTIDR"));
    	
    	cn66DetailsVO.setGrossAmount(resultSet.getDouble("MALCHG")+resultSet.getDouble("OTHCHG"));
    	//added by Deepthi for bug 34845
    	cn66DetailsVO.setConsDocNo(resultSet.getString("CSGDOCNUM"));
    	cn66DetailsVO.setConsSeqNo(resultSet.getString("CSGSEQNUM"));
    	// added for report by Sandeep
    	cn66DetailsVO.setSectorForReport(resultSet.getString("CN66ORGCOD").
    			                                          concat(resultSet.getString("CN66DSTCOD")));
    	
    	if(resultSet.getString("BILFRM")!=null &&resultSet.getString("BILTOO")!=null )
    	{
    	cn66DetailsVO.setSector(resultSet.getString("BILFRM").concat("-").concat(resultSet.getString("BILTOO")));
    	cn66DetailsVO.setBillFrm(resultSet.getString("BILFRM"));
    	cn66DetailsVO.setBillTo(resultSet.getString("BILTOO"));
    	}
    	String flightNumber = resultSet.getString("FLTNUM");
    	if(resultSet.getString("FLTCARCOD")!=null && flightNumber!=null ){
    	  cn66DetailsVO.setFlightNumber(resultSet.getString("FLTCARCOD").
    			  concat(flightNumber)
    			  );
    	}
    	cn66DetailsVO.setDistance(resultSet.getString("CTYPIRDIS"));
		/*
		 * Added For bug 44509
		 */			   
		if (resultSet.getTimestamp("FLTDAT") != null) {
			cn66DetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, resultSet.getTimestamp("FLTDAT")));
		}
    	cn66DetailsVO.setFuelSurchargeRateIndicator(resultSet.getString("FULCHGRATIND"));
    	cn66DetailsVO.setFuelSurcharge(resultSet.getDouble("FULCHG"));
    	//END BUG 44509
    	// for Optmistic Locking
    	cn66DetailsVO.setLastUpdatedUser(resultSet.getString("LSTUPDUSR"));
    	if(resultSet.getTimestamp("LSTUPDTIM")!=null){
        	cn66DetailsVO.setLastupdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
        }
    	cn66DetailsVO.setBillingStatus(resultSet.getString("BLGSTA"));
    	cn66DetailsVO.setAmount(resultSet.getDouble("BLDAMT"));
    	cn66DetailsVO.setApplicableRate(resultSet.getDouble("APLRAT"));
    	cn66DetailsVO.setBillingPeriod(resultSet.getString("BLDPRD"));
    	cn66DetailsVO.setValCharges(resultSet.getDouble("VALCHG"));
    	if(resultSet.getString("BLDPRD")!=null){
    		cn66DetailsVO.setBlgPrd(resultSet.getString("BLDPRD"));
    	}
    	cn66DetailsVO.setCurrencyCode(resultSet.getString("C51SMYBLGCURCOD"));
    	try {
			if (resultSet.getString("C51SMYBLGCURCOD") != null
					&& resultSet.getString("C51SMYBLGCURCOD").trim().length() > 0) {

				Money billedAmt = CurrencyHelper.getMoney(resultSet
						.getString("C51SMYBLGCURCOD"));
				billedAmt.setAmount(resultSet.getDouble("BLDAMT"));
				cn66DetailsVO.setActualAmount(billedAmt);
				cn66DetailsVO.setGrossAmount(resultSet.getDouble("BLDAMT"));
				
				Money vatAmt = CurrencyHelper.getMoney(resultSet
						.getString("C51SMYBLGCURCOD"));
				vatAmt.setAmount(resultSet.getDouble("VATAMT"));
				cn66DetailsVO.setVatAmount(vatAmt);
				Money amtInEMS=CurrencyHelper.getMoney(resultSet
						.getString("C51SMYBLGCURCOD"));
				amtInEMS.setAmount(resultSet.getDouble("TOTAMTEMS"));
				cn66DetailsVO.setTotalWgtinEMS(amtInEMS);
				Money amtInSVS=CurrencyHelper.getMoney(resultSet
						.getString("C51SMYBLGCURCOD"));
				amtInSVS.setAmount(resultSet.getDouble("TOTAMTSVS"));
				cn66DetailsVO.setTotalWgtinSV(amtInSVS);
				Money netAmount = null;
				netAmount=CurrencyHelper.getMoney(resultSet
						.getString("C51SMYBLGCURCOD"));
				netAmount.setAmount(resultSet.getDouble("NETAMT"));
				cn66DetailsVO.setNetAmount(netAmount);
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
    	log.exiting("CN66DetailsMapper","map");
        return cn66DetailsVO;
    }

}
