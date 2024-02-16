/*
 * GPAReportingInvoicDetailsMapper.java created on Nov 21, 2018
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-8464
 *
 */
public class GPAReportingInvoicDetailsMapper implements Mapper<InvoicDetailsVO>{


	private Log log = LogFactory.getLogger("MRA:GPAREPORTING");

	/**
	 * maps the resultset row to a VO
	 * @param rs
	 * @return invoicDetailsVO
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public InvoicDetailsVO map(ResultSet rs) throws SQLException {
		InvoicDetailsVO invoicDetailsVO = new InvoicDetailsVO();
		//From result of list query to the details needed for displaying in screen invoic Enquiry

		invoicDetailsVO.setMailIdr(rs.getString("MALIDR"));
		invoicDetailsVO.setWeight(rs.getDouble("GRSWGT"));

		try {

			Money netAmtInCtr = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			netAmtInCtr.setAmount(rs.getDouble("UPDNETAMT"));
			invoicDetailsVO.setNetamount(netAmtInCtr);

			Money disincAmtInCtr = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			disincAmtInCtr.setAmount(rs.getDouble("DISINCAMT"));
			invoicDetailsVO.setDisincentive(disincAmtInCtr);

			Money incAmtInCtr = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			incAmtInCtr.setAmount(rs.getDouble("INCAMT"));
			invoicDetailsVO.setIncentive(incAmtInCtr);

			Money chargeAmtInCtr = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			chargeAmtInCtr.setAmount(rs.getDouble("NETAMT"));
			invoicDetailsVO.setCharge(chargeAmtInCtr);

			Money invoicAmtInCtr = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			invoicAmtInCtr.setAmount(rs.getDouble("INVAMT"));
			invoicDetailsVO.setInvoicamount(invoicAmtInCtr);

			Money claimAmtInCtr = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			claimAmtInCtr.setAmount(rs.getDouble("CLMAMT"));
			invoicDetailsVO.setClaimamount(claimAmtInCtr);

			Money codeshareProratAmtInCtr = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			codeshareProratAmtInCtr.setAmount(rs.getDouble("PROVAL"));
			invoicDetailsVO.setCodeShareProrate(codeshareProratAmtInCtr);

			Money oalAmtInCtr = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			oalAmtInCtr.setAmount(rs.getDouble("OALAMT"));
			invoicDetailsVO.setCodeShareAmount(oalAmtInCtr);


			/*Money previousSettlementAmount = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			previousSettlementAmount.setAmount(rs.getDouble("PRVSTLAMT"));
			invoicDetailsVO.setPreviousSettlementAmount(previousSettlementAmount);*/


			Money totalSettlementAmount = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
			totalSettlementAmount.setAmount(rs.getDouble("TOTSTLAMT"));
			invoicDetailsVO.setTotalSettlementAmount(totalSettlementAmount);

		} catch (CurrencyException currencyException) {
			log.log(Log.WARNING, currencyException.getErrorCode());
		}

		invoicDetailsVO.setRate(rs.getDouble("APLRAT"));
		invoicDetailsVO.setConsignment(rs.getString("CSGDOCNUM"));
		invoicDetailsVO.setOrigin(rs.getString("ORGCOD"));
		invoicDetailsVO.setDestination(rs.getString("DSTCOD"));
		invoicDetailsVO.setMailClass(rs.getString("MALCLS"));

		invoicDetailsVO.setRegionCode(rs.getString("REGCOD"));
		invoicDetailsVO.setMailbagInvoicProcessingStatus(rs.getString("PROSTA"));
		invoicDetailsVO.setInvoicPayStatus(rs.getString("INVPAYSTA"));
		invoicDetailsVO.setClaimStatus(rs.getString("CLMSTA"));
		invoicDetailsVO.setRemarks(rs.getString("RMK"));
		//pk
		invoicDetailsVO.setPoaCode(rs.getString("UPDBILTOOPOA"));
		invoicDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		invoicDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		invoicDetailsVO.setVersionNumber(rs.getLong("VERNUM"));

		invoicDetailsVO.setCurrencyCode(rs.getString("CTRCURCOD"));
		invoicDetailsVO.setMailSubClass(rs.getString("MALSUBCLS"));
		invoicDetailsVO.setScheduledDelivery(rs.getString("REQDLVTIM"));
		String weightUnit = rs.getString("UNTCOD");
//		if(weightUnit!=null && weightUnit.equalsIgnoreCase("K")){//commented for IASCB-42403
//			weightUnit = "KG";
//		}else if(weightUnit!=null && weightUnit.equalsIgnoreCase("L")){
//			weightUnit = "LBS";
//		}
		
		invoicDetailsVO.setWeightUnit(weightUnit);

		invoicDetailsVO.setSerialNumber(rs.getInt("SERNUM"));
		invoicDetailsVO.setCodeshareInterlinePartner(rs.getString("CODSHRCAR"));
		if(rs.getString("FORMJRSTA")==null){
			invoicDetailsVO.setForceMajeureStatus("NA");
		}else if(rs.getString("FORMJRSTA").equals("APR")){
			invoicDetailsVO.setForceMajeureStatus("Y");
		}else if(rs.getString("FORMJRSTA").equals("REJ") || rs.getString("FORMJRSTA").equals("REQ")){
			invoicDetailsVO.setForceMajeureStatus("N");
		}
		invoicDetailsVO.setClmlessfiv(rs.getString("CLMLESSFIV"));
		invoicDetailsVO.setClmfivtoten(rs.getString("CLMFIVTOTEN"));
		invoicDetailsVO.setClmtentotwentyfiv(rs.getString("CLMTENTOTWENTYFIV"));
		invoicDetailsVO.setClmtwentyfivtofifty(rs.getString("CLMTWENTYFIVTOFIFTY"));
		invoicDetailsVO.setClmfiftytohundred(rs.getString("CLMFIFTYTOHUNDRED"));
		invoicDetailsVO.setClmhundredtofivhundred(rs.getString("CLMHUNDREDTOFIVHUNDRED"));
		invoicDetailsVO.setClmgrtfivhundred(rs.getString("CLMGRTFIVHUNDRED"));
		invoicDetailsVO.setCntawtinc(rs.getString("CNTAWTINC"));
		invoicDetailsVO.setDummydst(rs.getString("DUMMYDST"));
		invoicDetailsVO.setDummyorg(rs.getString("DUMMYORG"));

		return invoicDetailsVO;
	}

}
