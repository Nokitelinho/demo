/*
 * InterLineBillingEntriesMultiMapper.java Created on April 23,2009
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
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3434
 *
 */
public class InterLineBillingEntriesMultiMapper implements Mapper<DocumentBillingDetailsVO> {

	private static final String CLASS_NAME = "InterLineBillingEntriesMultiMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 *
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */

	public DocumentBillingDetailsVO map(ResultSet resultSet) throws SQLException {
		log.entering("InterLineBillingEntriesMultiMapper", "map"); 

		/* converted from multi mapper to mapper as part of bug 80371.
		 * 
		 * for SAA cca is not considered.so clubbing not needed.
		 * 
		 */
		//List<DocumentBillingDetailsVO> documentBillingDetailsVOs =
			//new ArrayList<DocumentBillingDetailsVO>();
		DocumentBillingDetailsVO   documentBillingDetailsVO =null;
		
		/*String dsnKey = null;
		String dsnKeyPrev = null;

		while(hasNext()) {
			
			 This key is generated for clubbing CCA.
			 * if  CCA is issued for OD record,
			 * it need not be shown as seperate.
			 * it should be clubbed with the despatch with latest cca.
			 


			dsnKey=new StringBuilder().append(resultSet.getString("CMPCOD"))
			.append(resultSet.getString("BLGBAS"))
			.append(resultSet.getString("CSGDOCNUM"))
			.append(resultSet.getInt("CSGSEQNUM"))
			.append(resultSet.getString("POACOD"))
			.append(resultSet.getString("INVNUM"))
			.append(resultSet.getString("MEMCOD"))
			.append(resultSet.getString("ARLIDR"))
			.append(resultSet.getString("PAYFLG")).toString();
			log.log(Log.INFO, "\n\n<-------------dsnKey--------------------->" + dsnKey);


			if(!dsnKey.equals(dsnKeyPrev)){ 
				 
				 * 
				 * for eliminating duplicates (Parent)
				 

				if(documentBillingDetailsVO!=null){

					increment();						

				}

				dsnKeyPrev = dsnKey;*/
				try {
					documentBillingDetailsVO=new DocumentBillingDetailsVO();
					//documentBillingDetailsVOs.add(documentBillingDetailsVO);
					
					documentBillingDetailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
					documentBillingDetailsVO.setSerialNumber(resultSet.getInt("SERNUM"));
					documentBillingDetailsVO.setCsgSequenceNumber(resultSet.getInt("CSGSEQNUM"));
					documentBillingDetailsVO.setCsgDocumentNumber(resultSet.getString("CSGDOCNUM"));
					documentBillingDetailsVO.setBillingBasis(resultSet.getString("BLGBAS"));
					documentBillingDetailsVO.setMailSequenceNumber(resultSet.getInt("MALSEQNUM"));
					documentBillingDetailsVO.setPoaCode(resultSet.getString("POACOD"));
					documentBillingDetailsVO.setIntblgType(resultSet.getString("INTBLGTYP"));
					documentBillingDetailsVO.setBillingStatus(resultSet.getString("STA"));
					documentBillingDetailsVO.setSectorFrom(resultSet.getString("SECFRM"));
					documentBillingDetailsVO.setSectorTo(resultSet.getString("SECTO"));
					documentBillingDetailsVO.setWeight(resultSet.getDouble("WGT"));
					if(resultSet.getDouble("WGT")== 0.0){
					documentBillingDetailsVO.setWeight(resultSet.getDouble("REVGRSWGT"));
					}
					documentBillingDetailsVO.setYear(resultSet.getString("YER")); 
					documentBillingDetailsVO.setCurrency(resultSet.getString("CURCOD"));
					documentBillingDetailsVO.setDsn(resultSet.getString("DSN"));
					documentBillingDetailsVO.setOrgOfficeOfExchange(resultSet.getString("ORGEXGOFC"));
					documentBillingDetailsVO.setDestOfficeOfExchange(resultSet.getString("DSTEXGOFC"));
					documentBillingDetailsVO.setRsn(resultSet.getString("RSN"));
					documentBillingDetailsVO.setHni(resultSet.getString("HSN"));
					documentBillingDetailsVO.setRegInd(resultSet.getString("REGIND"));
					documentBillingDetailsVO.setDestination(resultSet.getString("DSTCOD"));
					documentBillingDetailsVO.setOrigin(resultSet.getString("ORGCOD"));
					documentBillingDetailsVO.setSubClass(resultSet.getString("SUBCLS"));
					documentBillingDetailsVO.setCategory(resultSet.getString("CTGCOD"));
					documentBillingDetailsVO.setAirlineCode(resultSet.getString("ARLCOD"));
					documentBillingDetailsVO.setInvoiceNumber(resultSet.getString("INVNUM"));
					documentBillingDetailsVO.setNoofMailbags(resultSet.getString("PCS"));
					documentBillingDetailsVO.setRemarks(resultSet.getString("RMK"));
					documentBillingDetailsVO.setCcaType(resultSet.getString("CCATYP"));
					documentBillingDetailsVO.setCcaRefNumber(resultSet.getString("MCAREFNUM"));
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
					documentBillingDetailsVO.setCcaRefNumber(resultSet.getString("MEMCOD"));
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
						
						if("D".equals(documentBillingDetailsVO.getBillingStatus())){
						documentBillingDetailsVO.setBillingStatus("WD");
						}else{
							documentBillingDetailsVO.setBillingStatus("OD");
						}
						
						documentBillingDetailsVO.setReviewCheck(DocumentBillingDetailsVO.FLAG_YES);
					}

				}
			//}
		//}
		//	This part of the code is required to add the last parent
		//	The last parent wont be added by the main loop 
		/*if(documentBillingDetailsVO!=null){

			increment();						

		}*/

			return documentBillingDetailsVO;
	}

}
