/*
 * ReminderDetailsGpaBillingMapper.java Created on Jan 15, 2019
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information 
 * of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
/**
 * 
 * @author A-5526
 *
 */
public class ReminderDetailsGpaBillingMapper implements MultiMapper<ReminderDetailsVO> {

	
	

	public List<ReminderDetailsVO> map(ResultSet rs) throws SQLException {

		ArrayList<ReminderDetailsVO> reminderArrayList = new ArrayList<ReminderDetailsVO>();
		
		
		String companyKey = "";
		Map companyMap = new HashMap();
		while (rs.next()) {
			String reminderKey = rs.getString("MALIDR") + "-"
					+ rs.getString("INVNUM") + "-" + rs.getString("INVSERNUM");

			String slNo = rs.getString("MALIDR");
			LocalDate awbIssueDate=null;
			if(rs.getDate("RCVDAT")!=null){
			 awbIssueDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("RCVDAT"));
			}
			
			
			companyKey = slNo;
			ReminderDetailsVO reminderDetailsVO = (ReminderDetailsVO) companyMap
					.get(companyKey);

			if (!companyMap.containsKey(reminderKey)) {
				reminderDetailsVO = new ReminderDetailsVO();
				//reminderDetailsVO.setAwbSerialNo(rs.getString("MALIDR"));
				//reminderDetailsVO.setCustomerCode(rs.getString("GPACOD"));
				//reminderDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				reminderDetailsVO.setMailbagId(rs.getString("MALIDR"));
				reminderDetailsVO.setMailSeqNum(rs.getLong("MALSEQNUM"));
				reminderDetailsVO.setGpaCode(rs.getString("GPACOD"));
				reminderDetailsVO.setReminderStatus(rs.getString("REMSTA"));
				reminderDetailsVO.setIssueDate(awbIssueDate);
				reminderDetailsVO.setOriginCode(rs.getString("ORGARPCOD"));
				reminderDetailsVO.setDestinationCode(rs.getString("DSTARPCOD"));
				reminderDetailsVO.setOriginCurrency(rs.getString("BLGCURCOD"));
				reminderDetailsVO.setTypeOfBilling("GB");          
				reminderDetailsVO.setBilledAmount(rs.getDouble("NETAMTBLGCUR")+rs.getDouble("MCAAMT"));
				reminderDetailsVO.setSettlementAmount(rs.getDouble("STLAMT"));		
				reminderDetailsVO.setReminderNumber(rs.getInt("REMNUM"));
				reminderDetailsVO.setReminderStatus(rs.getString("REMSTA"));
				reminderDetailsVO.setReminderDate(awbIssueDate);
				//modified by A-8527 for ICRD-326045
				if(rs.getString("REMSTA")==null){
				//reminderDetailsVO.setDueAmount((rs.getDouble("DUEAMT")+rs.getDouble("MCAAMT")));
				reminderDetailsVO.setDueAmount((rs.getDouble("DUEAMT")));
				}
				else{
				reminderDetailsVO.setDueAmount((rs.getDouble("DUEAMT")));	
				}
				reminderDetailsVO.setCcaRefNumbers(rs.getString("MCANO"));
				reminderDetailsVO.setInvoiceNumber(rs.getString("INVNUM"));
				reminderDetailsVO.setInvoiceSerialNo(rs.getInt("INVSERNUM"));
				reminderDetailsVO.setCcaAmount(rs.getDouble("MCAAMT"));
				reminderDetailsVO.setRebillInvoiceNumber(rs.getString("RBLINVNUM"));
				companyMap.put(reminderKey, reminderDetailsVO);    

				reminderArrayList.add(reminderDetailsVO);
			}
		}

		return (ArrayList<ReminderDetailsVO>) reminderArrayList;
	}

}
