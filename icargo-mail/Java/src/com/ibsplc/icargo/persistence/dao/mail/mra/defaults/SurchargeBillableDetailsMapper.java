/*
 * SurchargeBillableDetailsMapper.java Created on Dec 4, 2015
 *
 * Copyright 2015 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;


public class SurchargeBillableDetailsMapper implements Mapper<SurchargeBillingDetailVO> {
	private CN51CN66FilterVO cn51CN66FilterVO=null;
	/**
	 * Constructor
	 * @param cn51CN66FilterVO
	 */
	public SurchargeBillableDetailsMapper(CN51CN66FilterVO cn51CN66FilterVO){
		this.cn51CN66FilterVO=cn51CN66FilterVO;
	}
	
    public SurchargeBillingDetailVO map(ResultSet rs) throws SQLException {
    	SurchargeBillingDetailVO surchargeBillingDetailVO=new SurchargeBillingDetailVO();
		//surchargeBillingDetailVO.setCompanyCode(rs.getString("CMPCOD"));//commented  for icrd-256997
		//surchargeBillingDetailVO.setSequenceNumber(rs.getInt("SERNUM"));//commented for icrd-256997
		surchargeBillingDetailVO.setBillingBasis(rs.getString("MALIDR"));//modified for icrd-256997
		surchargeBillingDetailVO.setGpaCode(rs.getString("POACOD"));
		surchargeBillingDetailVO.setChargeHead(rs.getString("CHGTYP"));
		surchargeBillingDetailVO.setApplicableRate(rs.getDouble("APLRAT"));
		if(cn51CN66FilterVO.getBaseCurrency()!=null &&! "".equals(cn51CN66FilterVO.getBaseCurrency())){
			Money amtctr;
			try {
				amtctr = CurrencyHelper.getMoney(cn51CN66FilterVO.getBaseCurrency());
				amtctr.setAmount(rs.getDouble("CHGVAL"));
				surchargeBillingDetailVO.setChargeAmt(amtctr);
			} catch (CurrencyException e) {
				e.getErrorCode();
			}
			
		}
		if((rs.getString("CSGDOCNUM"))!=null){
		surchargeBillingDetailVO.setConsigneeDocumentNumber(rs.getString("CSGDOCNUM"));
		}
		if((rs.getString("CSGSEQNUM"))!=null)
		{
		surchargeBillingDetailVO.setConsigneeSequenceNumber(rs.getString("CSGSEQNUM"));
		}
		return surchargeBillingDetailVO;
		

    }

}
