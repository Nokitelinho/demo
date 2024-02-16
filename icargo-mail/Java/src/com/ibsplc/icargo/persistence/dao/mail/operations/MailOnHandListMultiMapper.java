/*
 * MailOnHandListMultiMapper Created on Dec23, 2016
 *
 * Copyright 2016 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailOnHandDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailOnHandListMultiMapper implements
		MultiMapper<MailOnHandDetailsVO> {
	private Log log = LogFactory.getLogger("MAIL_OPERATIONS ");

	/**
	 * @param rs
	 * @return List<MailOnHandDetailsVO>
	 * @throws SQLException
	 */
	public List<MailOnHandDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering("MailOnHandListMultiMapper ", "map");
		List<MailOnHandDetailsVO> mailOnHandDetailsVOs = new ArrayList<MailOnHandDetailsVO>();
		String key = null;
		MailOnHandDetailsVO mailOnHandDetailsVO = null;
		Map<String, MailOnHandDetailsVO> mailOnHandDtls = new LinkedHashMap<String, MailOnHandDetailsVO>();
		log.entering("-----MailOnHandListMultiMapper---- ", "mapping");
		while (rs.next()) {
			String uldCount = null;
			double totalMailBagVolume= 0;
			double noOfMTSpace = 0;
			key = new StringBuilder().append(rs.getString("CMPCOD"))
					.append("-").append(rs.getString("ASGPRT")).append("-")
					.append(rs.getString("DSTCOD")).append("-")
					.append(rs.getString("SUBCLSGRP")).toString();
			if (!mailOnHandDtls.containsKey(key)) {
				mailOnHandDetailsVO = new MailOnHandDetailsVO();
				mailOnHandDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				mailOnHandDetailsVO.setCurrentAirport(rs.getString("ASGPRT"));
				mailOnHandDetailsVO.setDestination(rs.getString("DSTCOD"));
				mailOnHandDetailsVO.setSubclassGroup(rs.getString("SUBCLSGRP"));

				if (!MailConstantsVO.CONST_BULK.equals(rs.getString("CONTYP"))) {
					if (rs.getString("COUNTOFULD") != null
							&& rs.getString("COUNTOFULD").trim().length() > 0) {
						uldCount = new StringBuilder()
								.append(rs.getString("COUNTOFULD"))
								.append(rs.getString("CONTYP")).toString();
						mailOnHandDetailsVO.setUldCount(uldCount);
					}
				} else {
					mailOnHandDetailsVO.setMailTrolleyCount(rs.getInt("NOMT"));
				}
				//Added by A-5945 for ICRD-104487 starts
				if (MailConstantsVO.CONST_BULK.equals(rs.getString("CONTYP"))) {
					totalMailBagVolume= rs.getDouble("SUM(VOL)");
					log.log(Log.FINE, " totalMailBagVolume ", totalMailBagVolume);
					if(totalMailBagVolume>0){
						noOfMTSpace=	totalMailBagVolume/4;
					}
					log.log(Log.FINE, " noOfMTSpace ", noOfMTSpace);
					Double MTNum=Math.ceil(noOfMTSpace);
					int MTNumber =MTNum.intValue();
					log.log(Log.FINE, " MTNumber ", MTNumber);
					mailOnHandDetailsVO.setNoOfMTSpace(MTNumber);
					log.log(Log.FINE, " mailOnHandDetailsVO.setNoOfMTSpace ", mailOnHandDetailsVO.getNoOfMTSpace());
				}
				//Added by A-5945 for ICRD-104487 ends
				mailOnHandDetailsVO.setScanPort(rs.getString("SCNPRT"));
				mailOnHandDetailsVO.setScnDate(new LocalDate(rs
						.getString("SCNPRT"), Location.ARP, rs
						.getTimestamp("SCNDAT")));
				mailOnHandDetailsVO.setSubclassGroup(rs.getString("SUBCLSGRP"));
				mailOnHandDtls.put(key, mailOnHandDetailsVO);
			} else {
				mailOnHandDetailsVO = mailOnHandDtls.get(key);
				if (!MailConstantsVO.CONST_BULK.equals(rs.getString("CONTYP"))) {

					if (rs.getString("COUNTOFULD") != null
							&& rs.getString("COUNTOFULD").trim().length() > 0) {
						if(mailOnHandDetailsVO.getUldCount() != null){
						uldCount = new StringBuilder()
								.append(mailOnHandDetailsVO.getUldCount())
								.append(", ")
								.append(rs.getString("COUNTOFULD"))
								.append(rs.getString("CONTYP")).toString();
						}else{
							uldCount = new StringBuilder()
							.append(rs.getString("COUNTOFULD"))
							.append(rs.getString("CONTYP")).toString();
						}
						mailOnHandDetailsVO.setUldCount(uldCount);
					}

				} else {
					if (MailConstantsVO.CONST_BULK.equals(rs.getString("CONTYP"))) {
						totalMailBagVolume= rs.getDouble("SUM(VOL)");
						log.log(Log.FINE, " totalMailBagVolume ", totalMailBagVolume);
						if(totalMailBagVolume>0){
							noOfMTSpace=	totalMailBagVolume/4;
						}
						log.log(Log.FINE, " noOfMTSpace inside else part ", noOfMTSpace);
						Double MTNum=Math.ceil(noOfMTSpace);
						int MTNumber =MTNum.intValue();
						log.log(Log.FINE, " MTNumber  inside else part  ", MTNumber);
						mailOnHandDetailsVO.setNoOfMTSpace(MTNumber);
						log.log(Log.FINE, " mailOnHandDetailsVO.setNoOfMTSpace   inside else part ", mailOnHandDetailsVO.getNoOfMTSpace());
					}
					mailOnHandDetailsVO.setMailTrolleyCount(rs.getInt("NOMT"));
				}
				LocalDate scnDate = new LocalDate(rs.getString("SCNPRT"),
						Location.ARP, rs.getTimestamp("SCNDAT"));
				mailOnHandDetailsVO.setScanPort(rs.getString("SCNPRT"));
				if (mailOnHandDetailsVO.getScnDate().isGreaterThan(scnDate)) {
					mailOnHandDetailsVO.setScnDate(scnDate);
				}

			}
		}
		for (MailOnHandDetailsVO mailOnHandDtlVO : mailOnHandDtls.values()) {
			LocalDate currentDate = new LocalDate(
					mailOnHandDtlVO.getScanPort(), Location.ARP, true);
			long timeDiff = currentDate.findDifference(mailOnHandDtlVO
					.getScnDate());
			int timediffIndays = (int) (timeDiff / (1000 * 60 * 60 * 24));
			if(timeDiff % (1000*60*60*24) > 0 ){
				timediffIndays = timediffIndays + 1;
			}
			mailOnHandDtlVO.setNoOfDaysInCurrentLoc(timediffIndays);
            if(mailOnHandDtlVO.getUldCount()!=null&&mailOnHandDtlVO.getUldCount().trim().length()>1)
            {
            	String[] uldType = mailOnHandDtlVO.getUldCount().split(",");
            	StringBuilder uldDisplay=new StringBuilder();
            	if(uldType.length>3)
            	{
            		for(int count=0;count<3;count++){
                		uldDisplay.append(uldType[count]);
                		if(count<2)
                		{
                		uldDisplay.append(",");
                		}
                	}
            		uldDisplay.append("..");
            		mailOnHandDtlVO.setUldCountDisplay(uldDisplay.toString());
            	}
            	else
            	{
            		mailOnHandDtlVO.setUldCountDisplay(mailOnHandDtlVO.getUldCount());	
            	}
            	
            	
            }
			
			mailOnHandDetailsVOs.add(mailOnHandDtlVO);
		}

		return mailOnHandDetailsVOs;
	}
}
