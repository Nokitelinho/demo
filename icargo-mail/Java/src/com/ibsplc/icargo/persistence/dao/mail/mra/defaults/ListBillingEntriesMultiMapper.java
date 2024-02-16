/*
 * ListBillingEntriesMultiMapper.java Created on Nov 20 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListBillingEntriesVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Sandeep.T
 * Mapper for ListBillingEntriesMultiMapper.
 * 
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Nov 20 2007 Sandeep.T Initial draft
 * 
 * 
 */

public class ListBillingEntriesMultiMapper implements
		MultiMapper<ListBillingEntriesVO> {
	private Log log = LogFactory.getLogger("MRA_defaults");

	/**
	 * Collection<> MultiMapper for InvoiceDetailsReportVO
	 * 
	 * @param rs
	 * @return List<InvoiceDetailsReportVO>
	 * @throws SQLException
	 */
	public List<ListBillingEntriesVO> map(ResultSet rs) throws SQLException {
		log
				.log(Log.FINE,
						"\n\n\n\n Inside Multi Mapper Class ListBillingEntriesMultiMapper");

		HashMap<String, ListBillingEntriesVO> billingEntriesMap = new HashMap<String, ListBillingEntriesVO>();

		Collection<ListBillingEntriesVO> listBillingEntriesVOs = new ArrayList<ListBillingEntriesVO>();
		String key = null;
		ListBillingEntriesVO listBillingEntriesVO = null;
		while (rs.next()) {
			log.log(Log.FINER, "inside rs,next");  //append(rs.getString("CSGDAT")).
			 key = new StringBuilder().append(
					rs.getString("CSGDOCNUM")).append(rs.getDate("CSGDAT").toString())  // Consignment Date
					.append(rs.getString("MALCTGCOD")).append(
							rs.getString("POL")).append(rs.getString("POU"))
					.toString();
			
			log.log(Log.FINE, "\n\n\n\n key Useddddddddddddddd..............",
					key);
			if (billingEntriesMap.containsKey(key)) {
				 log.log(Log.FINER,"in If and addinguppppppppppp>>");
				 listBillingEntriesVO = billingEntriesMap.get(key);
				   if (rs.getString("BLBAMT") == null) {
						//listBillingEntriesVO.setAmountString("0.0");
					} else {
						double previousAmount = listBillingEntriesVO.getTotalAmount();
						double newAmount  = previousAmount +  rs.getDouble("BLBAMT");
						listBillingEntriesVO.setTotalAmount(newAmount);
						StringBuilder amount = new StringBuilder(String.valueOf(newAmount));
//						if (amount.length() == 8) {
//							amount.append("0");
//						}
//						listBillingEntriesVO.setAmountString(amount.toString());
					}
				    	 double previousWeight = listBillingEntriesVO.getTotalWeight();
				    	 listBillingEntriesVO.setTotalWeight(previousWeight+rs.getDouble("GRSWGT"));
				    	 log
								.log(
										Log.FINEST,
										"ListBillingEntriesVOs in If part after summinng",
										listBillingEntriesVO);
				     
			} else {
				log
						.log(Log.FINE,
								"Key not Found*********************Creating New Vo hence");
				listBillingEntriesVO = new ListBillingEntriesVO();

				if (rs.getString("CSGDAT") != null) {
					listBillingEntriesVO.setConsignmentDate(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs
									.getDate("CSGDAT")));
				}
				listBillingEntriesVO.setConsignmentNumber(rs
						.getString("CSGDOCNUM"));
				listBillingEntriesVO.setMailCategoryCode(rs
						.getString("MALCTGCOD"));
				if (rs.getString("POL") != null && rs.getString("POU") != null) {
					listBillingEntriesVO.setRoute(rs.getString("POL").concat(
							"-").concat(rs.getString("POU")));
				} else {
					listBillingEntriesVO.setRoute("");
				}
				if (rs.getString("BILFRM") != null
						&& rs.getString("BILTOO") != null) {
					listBillingEntriesVO.setSector(rs.getString("BILFRM")
							.concat("-").concat(rs.getString("BILTOO")));
				} else {
					listBillingEntriesVO.setSector("");
				}
				listBillingEntriesVO.setTotalWeight(rs.getDouble("GRSWGT"));
				if (rs.getString("BLBAMT") == null) {
					listBillingEntriesVO.setAmountString("0.0");
				} else {
					StringBuilder amount = new StringBuilder("0");
					if (rs.getString("BLBAMT").indexOf(".") == 0) {
						amount.append(rs.getString("LHLRAT"));
					}
					if (rs.getString("BLBAMT").length() == 8) {
						amount.append("0");
					}
					listBillingEntriesVO.setAmountString(amount.toString());
				}
				listBillingEntriesVO.setTotalAmount(rs.getDouble("BLBAMT"));
				log
						.log(
								Log.FINEST,
								"ListBillingEntriesVOs before adding to Map&&&&&&&&&&&&&&",
								listBillingEntriesVO);
				billingEntriesMap.put(key, listBillingEntriesVO);
			}

		}
		for (ListBillingEntriesVO listBillingEntriesVo : billingEntriesMap
				.values()) {
			   StringBuilder amount = new StringBuilder(String.valueOf(listBillingEntriesVo.getTotalAmount()));
			  if (amount.length() == 8) {
				amount.append("0");
			  }
			listBillingEntriesVO.setAmountString(amount.toString());
			listBillingEntriesVOs.add(listBillingEntriesVo);
			// making the amountString 
		}
		log
				.log(
						Log.FINER,
						"ListBillingEntriesVO in ListBillingEntriesMultiMapper ********************************>>",
						listBillingEntriesVOs);
		return (ArrayList<ListBillingEntriesVO>) listBillingEntriesVOs;

	}
}
