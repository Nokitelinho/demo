/*
 * DestnManifestSummaryMultiMapper.java Created on Sep 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSummaryVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1876
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Sep 02, 2007				A-1876		Created
 */
public class DestnManifestSummaryMultiMapper implements
				MultiMapper<MailSummaryVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	/**
	 * TODO Purpose
	 * Sep 02, 2007, A-1876
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */
	public  List<MailSummaryVO> map(ResultSet rs )throws SQLException {
		log.entering("InventorySummaryMapper","map-method");
//		MailSummaryVO mailSummaryVO= new MailSummaryVO();
//		mailSummaryVO.setBagCount(rs.getInt("ACPBAGS"));
//		mailSummaryVO.setTotalWeight(rs.getDouble("ACPWGT"));
//		mailSummaryVO.setDestination(rs.getString("DST"));
//		mailSummaryVO.setMailCategory(rs.getString("MALCTGCOD"));
//		return mailSummaryVO;
		Map<String, ArrayList<MailSummaryVO>> summaryMap = new HashMap<String, ArrayList<MailSummaryVO>>();
		List<MailSummaryVO> mailSummaryVOs = new ArrayList<MailSummaryVO>();
		ArrayList<MailSummaryVO> summaryVOsForStorage = null;
		ArrayList<MailSummaryVO> storredDetailsInMap = null;
		MailSummaryVO mailSummaryVO = null;
		String categoryCode = null;
		String mailClass = null;
		String destination = null;
		String currDestnKey = "";
		String prevDestnKey = "";
		/*int sumbags = 0;
		double sumWeight = 0;*/
		
		while(rs.next()) {
			
		destination = rs.getString("DST");
		categoryCode = rs.getString("MALCTGCOD");
		mailClass = rs.getString("MALCLS");
			
			for(int cls=0;cls < MailConstantsVO.MILITARY_CLASS.length;cls++){
				if(MailConstantsVO.MILITARY_CLASS[cls].equals(mailClass)){
					categoryCode = "M";
				}
			}
			
			currDestnKey = new StringBuilder().append(destination).
								append(categoryCode).toString();
			if (summaryMap.get(currDestnKey) != null) {
				mailSummaryVO = new MailSummaryVO();
				mailSummaryVO.setDestination(destination);
				mailSummaryVO.setMailCategory(categoryCode);	
				mailSummaryVO.setBagCount(rs.getInt("ACPBAGS"));
				//mailSummaryVO.setTotalWeight(rs.getDouble("ACPWGT"));
				mailSummaryVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371
				summaryMap.get(currDestnKey).add(mailSummaryVO);
			} else {
				summaryVOsForStorage = new ArrayList<MailSummaryVO>();
				mailSummaryVO = new MailSummaryVO();
				mailSummaryVO.setDestination(destination);
				mailSummaryVO.setMailCategory(categoryCode);	
				mailSummaryVO.setBagCount(rs.getInt("ACPBAGS"));
				//mailSummaryVO.setTotalWeight(rs.getDouble("ACPWGT"));
				mailSummaryVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371
				summaryVOsForStorage.add(mailSummaryVO);
				summaryMap.put(currDestnKey, summaryVOsForStorage);
			}
			
			/*if(!currDestnKey.equals(prevDestnKey)) {
				sumbags = 0;
				sumWeight = 0;
				prevDestnKey = currDestnKey;
				mailSummaryVO = new MailSummaryVO();
				mailSummaryVO.setDestination(destination);
				mailSummaryVO.setMailCategory(categoryCode);			
				mailSummaryVOs.add(mailSummaryVO);
			}
			if(mailSummaryVO != null) {
				sumbags += rs.getInt("ACPBAGS");
				sumWeight += rs.getDouble("ACPWGT");
				mailSummaryVO.setBagCount(sumbags);
				mailSummaryVO.setTotalWeight(sumWeight);
			}*/
		}
		for (Entry<String, ArrayList<MailSummaryVO>> entryInsideMap : summaryMap
				.entrySet()) {
			storredDetailsInMap = entryInsideMap.getValue();
			int sumbags = 0;
			double sumWeight = 0.0;
			for(MailSummaryVO mailSummaryVo:storredDetailsInMap){
				sumbags+=mailSummaryVo.getBagCount();
				//sumWeight+=mailSummaryVo.getTotalWeight();
				sumWeight+=mailSummaryVo.getTotalWeight().getRoundedDisplayValue();//added by A-7371
			}
			MailSummaryVO mailSummaryVOForSave = storredDetailsInMap.get(0);
			mailSummaryVOForSave.setBagCount(sumbags);
			//mailSummaryVOForSave.setTotalWeight(sumWeight);
			mailSummaryVOForSave.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,0.0,sumWeight,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371
			mailSummaryVOs.add(mailSummaryVOForSave);
		}

			
		return mailSummaryVOs;
		
	}

}
