/*
 * ViewFormOneMultiMapper.java Created on July 16, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-3456
 * 
 */
public class ViewFormOneMultiMapper implements MultiMapper<FormOneVO> {

	/**
	 * logger
	 */
	private Log log = LogFactory.getLogger("ViewFormOneMultiMapper");

	/**
	 * class name
	 */
	private static final String CLASS_NAME = "ViewFormOneMultiMapper";

	/**
	 * @param resultSet
	 * @throws SQLException
	 * @return List<CN51SummaryVO>
	 */
	public List<FormOneVO> map(ResultSet resultSet) throws SQLException {

		List<FormOneVO> formOneVOList = null;
		FormOneVO formOneVO = null;
		String billingCurrency = null;
		if (resultSet != null) {

			Collection<InvoiceInFormOneVO> invoiceInFormOneVOs = null;
			InvoiceInFormOneVO invoiceInFormOneVO = null;
			boolean isFirstResultSet = true;

			double totAmtContractCurr = 0.0D;

			while (resultSet.next()) {
				if (isFirstResultSet) {
					formOneVO = new FormOneVO();
					formOneVO.setCompanyCode(resultSet.getString("CMPCOD"));
					formOneVO.setBillingCurrency(resultSet
							.getString("BLGCURCOD"));
					formOneVO.setListingCurrency(resultSet
							.getString("LSTCURCOD"));
					formOneVO.setExchangeRateBillingCurrency(resultSet
							.getDouble(("EXGRATLSTBLGCUR")));
					// formOneVO.setExchangeRateListingCurrency(Double.parseDouble(resultSet.getString("EXGRATLSTCUR")));
					formOneVO.setClassType(resultSet.getString("CLSTYP"));
					invoiceInFormOneVOs = new ArrayList<InvoiceInFormOneVO>();
					isFirstResultSet = false;

					billingCurrency = resultSet.getString("BLGCURCOD");
					if ((!("".equals(billingCurrency)))
							|| billingCurrency != null) {
						formOneVO.setBillingCurrency(billingCurrency);
					}
				}

				if (resultSet.getString("INVNUM") != null) { // to check if
																// child details
																// are present
					invoiceInFormOneVO = new InvoiceInFormOneVO();

					try {
						this.populateDetailsVO(resultSet, invoiceInFormOneVO);
					} catch (CurrencyException e) {
						// TODO Auto-generated catch block
						e.getErrorCode();
					}
					// totAmtContractCurr += detailsChildVO
					// .getAmount();
					invoiceInFormOneVOs.add(invoiceInFormOneVO);
				}

			}// end of while( rs.next() )

			if (formOneVO != null) {

				// cn51SummaryVO.setTotalAmount(totAmtContractCurr);

				formOneVO.setInvoiceInFormOneVOs(invoiceInFormOneVOs);
				formOneVOList = new ArrayList<FormOneVO>();
				formOneVOList.add(formOneVO);
			}

		}// end of if(rs != null)
		log.log(Log.INFO, "inside mapper", formOneVOList);
		return formOneVOList;
	}

	/**
	 * 
	 * @param rs
	 * @param detailsVO
	 *            the CN66DetailsVO for population
	 * @throws SQLException
	 * @throws CurrencyException
	 */
	private void populateDetailsVO(ResultSet resultSet,
			InvoiceInFormOneVO invoiceInFormOneVO) throws SQLException,
			CurrencyException {

		invoiceInFormOneVO.setCompanyCode(resultSet.getString("CMPCOD"));
		invoiceInFormOneVO.setInvoiceNumber(resultSet.getString("INVNUM"));
		/**
		 * @author a-3447
		 */

		String baseCurrency = "NZD";
		Money baseTotal = CurrencyHelper.getMoney(baseCurrency);
		if (baseTotal != null) {
			baseTotal.setAmount(resultSet.getDouble("TOTBASAMT"));
			invoiceInFormOneVO.setTotalBaseAmount(baseTotal);
			invoiceInFormOneVO.setTotalBaseAmt(baseTotal.getAmount());

		}
		// invoiceInFormOneVO.setListingTotalAmt(resultSet.getDouble("TOTMISAMT"));
		// invoiceInFormOneVO.setBillingTotAmt(resultSet.getDouble("TOTBLGAMT"));
		// BLGAMT FOR FORM1
		String billingCurrency = resultSet.getString("BLGCURCOD");
		Money billingTotal = CurrencyHelper.getMoney(billingCurrency);
		if (billingTotal != null) {
			billingTotal.setAmount(resultSet.getDouble("TOTBLGAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
			invoiceInFormOneVO.setBillingTotalAmt(billingTotal);
			invoiceInFormOneVO.setBillingTotAmt(billingTotal.getAmount());
		}
		String listingCurrency = resultSet.getString("LSTCURCOD");
		if (listingCurrency != null) {
			Money lstTotal = CurrencyHelper.getMoney(listingCurrency);
			lstTotal.setAmount(resultSet.getDouble("TOTMISAMTLSTCUR"));   //Modified by A-7929 as part of ICRD-265471
			invoiceInFormOneVO.setListingTotAmount(lstTotal);
			invoiceInFormOneVO.setListingTotalAmt(lstTotal.getAmount());

		}
		if (resultSet.getDate("INVDAT") != null) {
			invoiceInFormOneVO.setInvoiceDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, resultSet
							.getDate("INVDAT")));
		}

	}

}
