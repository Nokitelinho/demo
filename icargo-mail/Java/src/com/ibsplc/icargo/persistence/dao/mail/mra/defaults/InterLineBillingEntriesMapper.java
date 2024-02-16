/*
 * InterLineBillingEntriesMapper.java Created on Aug 8,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-3434
 *
 */
public class InterLineBillingEntriesMapper implements Mapper <DocumentBillingDetailsVO> {

	private static final String CLASS_NAME = "InterLineBillingEntriesMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */

	public DocumentBillingDetailsVO map(ResultSet resultSet) throws SQLException {

		log.entering(CLASS_NAME, "map");
		DocumentBillingDetailsVO   documentBillingDetailsVO =null;


		try {
			documentBillingDetailsVO=new DocumentBillingDetailsVO();
			documentBillingDetailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
			documentBillingDetailsVO.setSerialNumber(resultSet.getInt("SERNUM"));
			documentBillingDetailsVO.setCsgSequenceNumber(resultSet.getInt("CSGSEQNUM"));
			documentBillingDetailsVO.setCsgDocumentNumber(resultSet.getString("CSGDOCNUM"));
			documentBillingDetailsVO.setBillingBasis(resultSet.getString("BLGBAS"));
			documentBillingDetailsVO.setPoaCode(resultSet.getString("POACOD"));
			documentBillingDetailsVO.setIntblgType(resultSet.getString("INTBLGTYP"));
			documentBillingDetailsVO.setBillingStatus(resultSet.getString("STA"));
			documentBillingDetailsVO.setSectorFrom(resultSet.getString("SECFRM"));
			documentBillingDetailsVO.setSectorTo(resultSet.getString("SECTO"));
			documentBillingDetailsVO.setWeight(resultSet.getDouble("WGT"));
			documentBillingDetailsVO.setYear(resultSet.getString("YEAR"));
			documentBillingDetailsVO.setCurrency(resultSet.getString("CURCOD"));
			documentBillingDetailsVO.setDsn(resultSet.getString("DSN"));
			documentBillingDetailsVO.setDestination(resultSet.getString("DSTCOD"));
			documentBillingDetailsVO.setOrigin(resultSet.getString("ORGCOD"));
			documentBillingDetailsVO.setSubClass(resultSet.getString("SUBCLS"));
			documentBillingDetailsVO.setCategory(resultSet.getString("CTGCOD"));
			documentBillingDetailsVO.setAirlineCode(resultSet.getString("ARLCOD"));
			documentBillingDetailsVO.setInvoiceNumber(resultSet.getString("INVNUM"));
			documentBillingDetailsVO.setNoofMailbags(resultSet.getString("PCS"));
			documentBillingDetailsVO.setRemarks(resultSet.getString("RMK"));
			documentBillingDetailsVO.setCcaType(resultSet.getString("CCATYP"));
			documentBillingDetailsVO.setCcaRefNumber(resultSet.getString("CCAREFNUM"));
			documentBillingDetailsVO.setAirlineIdr(resultSet.getInt("ARLIDR"));
			
			if("Y".equals(resultSet.getString("MEMO"))){
				
				documentBillingDetailsVO.setCurrency("USD");
				
			}
			if(documentBillingDetailsVO.getCurrency()!=null){

				Money amount = CurrencyHelper.getMoney(documentBillingDetailsVO.getCurrency());
				amount.setAmount(resultSet.getDouble("AMT"));
				documentBillingDetailsVO.setAmount(amount);

			}
			if(resultSet.getTimestamp("LSTUPDTIM")!= null){
				documentBillingDetailsVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,
						Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
			}
		}
		catch(CurrencyException e) {
			throw new SQLException(e.getErrorCode());
		}

		if("Y".equals(resultSet.getString("REV"))){

			documentBillingDetailsVO.setReviewCheck(DocumentBillingDetailsVO.FLAG_YES);
		}

		else if("N".equals(resultSet.getString("REV"))){

			documentBillingDetailsVO.setReviewCheck(DocumentBillingDetailsVO.FLAG_NO);
		}
		/*
		 * check whether the record is from MTKARLMEM table
		 * if it is billed(OD),INVNUM is OUTINVNUM
		 * if it is billable(OB),INVNUM is INWINVNUM
		 */
		if("Y".equals(resultSet.getString("MEMO"))){
			documentBillingDetailsVO.setMemoFlag("Y");
			if("OD".equals(documentBillingDetailsVO.getBillingStatus())){
				documentBillingDetailsVO.setInvoiceNumber(resultSet.getString("OUTINVNUM"));
			}
			else if("OB".equals(documentBillingDetailsVO.getBillingStatus())){
				documentBillingDetailsVO.setInvoiceNumber(resultSet.getString("INWINVNUM"));
			}
		}
		/*
		 * Records From MTKARLC66DTL
		 * If billing type is INWARD(I),status is inward billed(IB)
		 * If billing type is OUTWARD(O),status is outward billed(OD)
		 */
		else{
			if("I".equals(documentBillingDetailsVO.getIntblgType())){
				documentBillingDetailsVO.setBillingStatus("IB");
			}
			else if("O".equals(documentBillingDetailsVO.getIntblgType())){
				documentBillingDetailsVO.setBillingStatus("OD");
				documentBillingDetailsVO.setReviewCheck(DocumentBillingDetailsVO.FLAG_YES);
			}

		}

		//log.log(Log.FINE,"documentBillingDetailsVO: "+documentBillingDetailsVO);

		return documentBillingDetailsVO;
	}

}
