
/*
 * InvoiceEnquiryMapper.java Created on July 7, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author a-3434
 *
 */
public class InvoiceEnquiryMapper implements MultiMapper<CN51SummaryVO> {


	/**
	 * @param resultSet
	 * @throws SQLException
	 * @return List<CN51SummaryVO>
	 */
	public List<CN51SummaryVO> map(ResultSet resultSet) throws SQLException {

		List<CN51SummaryVO> summaryVOList = null;

		CN51SummaryVO   cn51SummaryVO = null;

		if (resultSet != null) {

			Collection<CN66DetailsVO> c66DetailsVOs = null;
			CN66DetailsVO detailsChildVO = null;
			boolean isFirstResultSet = true;

			double totAmtContractCurr = 0.0D;


			while (resultSet.next()) {

				if (isFirstResultSet) {
					cn51SummaryVO = new CN51SummaryVO();
					cn51SummaryVO.setCompanyCode(resultSet.getString("C51SMYCMPCOD"));
					cn51SummaryVO.setGpaCode(resultSet.getString("C51SMYGPACOD"));
					cn51SummaryVO.setInvoiceNumber(resultSet.getString("C51SMYINVNUM"));
					cn51SummaryVO.setBillingCurrencyCode(resultSet.getString("C51SMYBLGCURCOD"));
					cn51SummaryVO.setInvoiceStatus(resultSet.getString("C51SMYINVSTA"));

					cn51SummaryVO.setContractCurrencyCode(resultSet.getString("C51SMYBLGCURCOD"));

						        if(resultSet.getDate("C51SMYBLDDAT") != null ){
									  cn51SummaryVO.setBilledDate(new LocalDate(LocalDate.NO_STATION,
																        		Location.NONE,
																        	 resultSet.getDate("C51SMYBLDDAT")));
						        }

					cn51SummaryVO.setBillingPeriod(resultSet.getString("C51SMYBLDPRD"));
					cn51SummaryVO.setBillingPeriodFrom((new LocalDate("***", Location.NONE, resultSet.getDate("C51SMYBLGPRDFRM"))).toDisplayDateOnlyFormat());
                    cn51SummaryVO.setBillingPeriodTo((new LocalDate("***", Location.NONE, resultSet.getDate("C51SMYBLGPRDTOO"))).toDisplayDateOnlyFormat());
                    
                    cn51SummaryVO.setGpaName(resultSet.getString("POANAM"));


					c66DetailsVOs = new ArrayList<CN66DetailsVO>();
					isFirstResultSet = false;
				}

				if (resultSet.getInt("C66SEQNUM") != 0) { // child details are present
					detailsChildVO = new CN66DetailsVO();

					this.populateDetailsVO(resultSet, detailsChildVO);
					totAmtContractCurr += detailsChildVO.getActualAmount().getAmount();
							
					c66DetailsVOs.add(detailsChildVO);
				}

			}// end of while( rs.next() )

			if (cn51SummaryVO != null) {


				cn51SummaryVO.setTotalAmount(totAmtContractCurr);

				cn51SummaryVO
						.setCn66details(c66DetailsVOs);
				summaryVOList = new ArrayList<CN51SummaryVO>();
				summaryVOList.add(cn51SummaryVO);
			}

		}// end of if(rs != null)
		return summaryVOList;
	}

	/**
	 *
	 * @param rs
	 * @param detailsVO
	 *            the CN66DetailsVO  for population
	 * @throws SQLException
	 */
	private void populateDetailsVO(ResultSet resultSet, CN66DetailsVO detailsVO)
			throws SQLException {
					Double weight=0.0D;
					Double rate=0.0D;
					Double amount=0.0D;

					try{
					detailsVO.setCompanyCode(resultSet.getString("C51SMYCMPCOD"));
					detailsVO.setGpaCode(resultSet.getString("C51SMYGPACOD"));
					detailsVO.setBillingBasis(resultSet.getString("C66BLGBAS"));
					detailsVO.setSequenceNumber(resultSet.getInt("C66SEQNUM"));
					detailsVO.setInvoiceNumber(resultSet.getString("C51SMYINVNUM"));
					detailsVO.setDsn(resultSet.getString("C66DSN"));
					detailsVO.setOrigin(resultSet.getString("C66ORGCOD"));
					detailsVO.setDestination(resultSet.getString("C66DSTCOD"));
					detailsVO.setMailSubclass(resultSet.getString("C66MALSUBCLS"));
					detailsVO.setBillingStatus(resultSet.getString("C66BLGSTA"));
					detailsVO.setConsDocNo(resultSet.getString("C66CSGDOCNUM"));
					detailsVO.setConsSeqNo(resultSet.getString("C66CSGSEQNUM"));
					detailsVO.setRemarks(resultSet.getString("C66RMK"));
					detailsVO.setCcaRefNo(resultSet.getString("C66CCAREFNUM"));
					detailsVO.setActualSubCls(resultSet.getString("ACTSUBCLS"));
					//added for cr ICRD-7370
					detailsVO.setServiceTax(resultSet.getDouble("SRVTAX"));
					detailsVO.setTds(resultSet.getDouble("TDS"));
					
					Timestamp time = resultSet.getTimestamp("C66RCVDAT");

			    	if(time!=null){

					LocalDate date=new LocalDate(
							LocalDate.NO_STATION,Location.NONE,time);
					detailsVO.setReceivedDate(date);

				}

			   	 
			  Timestamp upTime = resultSet.getTimestamp("C66LSTUPTIME");

			    	if(upTime!=null){
					LocalDate date=new LocalDate(
							LocalDate.NO_STATION,Location.NONE,upTime);
					detailsVO.setLastupdatedTime(date);

				}
			    
				detailsVO.setTotalWeight(resultSet.getDouble("C66TOTWGT"));
				detailsVO.setApplicableRate(resultSet.getDouble("C66APLRAT"));
				weight=detailsVO.getTotalWeight();
				rate=detailsVO.getApplicableRate();
				amount= weight*rate;
				detailsVO.setAmount(amount);
				/*
				 * Added For bug 44509
				 */
				detailsVO.setFuelSurchargeRateIndicator(resultSet.getString("FULCHGRATIND"));
			    detailsVO.setFuelSurcharge(resultSet.getDouble("FULCHG"));
			    detailsVO.setFlightNumber(resultSet.getString("FLTNUM"));
			    detailsVO.setFlightCarrierCode(resultSet.getString("FLTCARCOD"));			   
				if (resultSet.getTimestamp("FLTDAT") != null) {
					 detailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
							Location.NONE, resultSet.getTimestamp("FLTDAT")));
				}
				
			 if(resultSet.getString("C51SMYBLGCURCOD")!=null && resultSet.getString("C51SMYBLGCURCOD").trim().length()>0 ){				    		
				Money netAmount=null;
				 Money billedAmt= CurrencyHelper.getMoney(resultSet.getString("C51SMYBLGCURCOD"));
				 billedAmt.setAmount(resultSet.getDouble("C66AMT"));				    		
				detailsVO.setActualAmount(billedAmt);
				netAmount=CurrencyHelper.getMoney(resultSet.getString("C51SMYBLGCURCOD"));
				netAmount.setAmount(resultSet.getDouble("NETAMT"));
				detailsVO.setNetAmount(netAmount);
				
				}
			}
		catch(CurrencyException currencyException){
			currencyException.getErrorCode();
		}
	}

}
