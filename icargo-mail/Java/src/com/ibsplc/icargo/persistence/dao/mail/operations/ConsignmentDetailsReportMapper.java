/*
 * ConsignmentDetailsReportMapper.java Created on May 02, 2018
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
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 * @author A-6986
 * 
 */

public class ConsignmentDetailsReportMapper implements MultiMapper<ConsignmentDocumentVO> {
	
	private Log log = LogFactory.getLogger("mail.operations");
	public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
		
		List<ConsignmentDocumentVO>  consignmentDocumentVOs  = null;
	
		String currentKey = null;
		String previousKey = null;
		StringBuilder stringBuilder = null;
		Collection<MailInConsignmentVO> mailInConsignmentVOs = null;
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = null;
		ConsignmentDocumentVO consignmentDocumentVO = null;
		MailInConsignmentVO mailInConsignmentVO = null;
		String mailCurrentKey = null;
		String mailPreviousKey = null;
		StringBuilder mailKey = null;
		Collection<Integer> routingSerialNumbers = null;
		RoutingInConsignmentVO routingInConsignmentVO = null;
		Collection<String> mailInConsignment = null;
		
	
			while (rs.next()) {
				if (consignmentDocumentVOs == null) {
					consignmentDocumentVOs = new ArrayList<ConsignmentDocumentVO>();
				}
				stringBuilder = new StringBuilder();
				currentKey = stringBuilder.append(rs.getString("CMPCOD")).append(
						rs.getString("CSGDOCNUM")).append(rs.getString("POACOD"))
						.append(rs.getInt("CSGSEQNUM")).toString();
		
				if (!currentKey.equals(previousKey)) {
					
					consignmentDocumentVO = new ConsignmentDocumentVO();
					collectConsignmentDetails(consignmentDocumentVO, rs);
					mailInConsignmentVOs = new ArrayList<MailInConsignmentVO>();
					routingInConsignmentVOs = new ArrayList<RoutingInConsignmentVO>();
					
					consignmentDocumentVO
							.setMailInConsignmentcollVOs(mailInConsignmentVOs);
					consignmentDocumentVO
					.setRoutingInConsignmentVOs(routingInConsignmentVOs);
					
					consignmentDocumentVOs.add(consignmentDocumentVO);
					previousKey = currentKey;
				}
				
				mailKey = new StringBuilder();
				mailKey.append(rs.getString("ORGEXGOFC"))
						.append(rs.getString("DSTEXGOFC"));
						//.append(rs.getString("SUBCLSGRP"));
				mailCurrentKey = mailKey.toString();
				if(mailInConsignment == null){
					mailInConsignment = new ArrayList<String>();
				}
				if (!mailInConsignment.contains(mailCurrentKey)) {
					
					mailInConsignmentVO = new MailInConsignmentVO();
					
					mailInConsignmentVO.setOriginExchangeOffice(rs.getString("ARPCODORG"));
					mailInConsignmentVO.setDestinationExchangeOffice(rs
							.getString("ARPCODDST"));
					collectMailDetails(mailInConsignmentVO, rs);
					
				
					mailInConsignmentVOs.add(mailInConsignmentVO);
					
					//mailPreviousKey = mailCurrentKey;
					mailInConsignment.add(mailCurrentKey);
				}else{
					for(MailInConsignmentVO mailVO : mailInConsignmentVOs ){
						if(mailVO.getOriginExchangeOffice().equals(rs.getString("ARPCODORG"))
								&& mailVO.getDestinationExchangeOffice().equals(rs.getString("ARPCODDST"))){
							collectMailDetails(mailVO, rs);
						}
					}
				}
				if (rs.getInt("RTGSERNUM") > 0) {
					routingInConsignmentVO = new RoutingInConsignmentVO();
					routingInConsignmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
					routingInConsignmentVO.setOnwardCarrierCode(rs.getString("FLTCARCOD"));
					routingInConsignmentVO.setOnwardFlightNumber(rs.getString("FLTNUM"));
					routingInConsignmentVO.setPol(rs.getString("POL"));
					if (routingSerialNumbers == null) {
						routingSerialNumbers = new ArrayList<Integer>();
					}
					if (!routingSerialNumbers.contains(Integer
							.valueOf(routingInConsignmentVO
									.getRoutingSerialNumber()))) {
						routingSerialNumbers.add(Integer
								.valueOf(routingInConsignmentVO
										.getRoutingSerialNumber()));
					routingInConsignmentVOs.add(routingInConsignmentVO);
					}
				}
		}
			return consignmentDocumentVOs;
			
		
		}
		
		
		private void collectConsignmentDetails(
				ConsignmentDocumentVO consignmentDocumentVO, ResultSet rs)
				throws SQLException {
			LogonAttributes logonAttributes=null;
			 try {
				 logonAttributes = ContextUtils.getSecurityContext()
							.getLogonAttributesVO();
			} catch (SystemException e) {
				logonAttributes=null;
			}
		
			StringBuilder airport  = null;
			airport = new StringBuilder();
			
			airport = airport.append(rs.getString("ARPCODDST")).append(",").append(
					rs.getString("CTYCODDST")).append(",").append(rs.getString("CNTCODDST"));
			
			consignmentDocumentVO.setDestination(airport.toString());
			
			if (rs.getDate("CSGDAT") != null) {
				if(rs.getString("ARPCODORG")!=null && !rs.getString("ARPCODORG").isEmpty()){
				consignmentDocumentVO.setConsignmentDate(new LocalDate(rs
						.getString("ARPCODORG"), Location.ARP, rs.getDate("CSGDAT")));
				}else{
					consignmentDocumentVO.setConsignmentDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, rs.getDate("CSGDAT"))); 
				}    
			}
			consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			
			consignmentDocumentVO.setOriginOfficeOfExchange(rs.getString("ARPCODORG"));
			consignmentDocumentVO.setDestinationOfficeOfExchange(rs.getString("ARPCODDST"));
			consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
			consignmentDocumentVO.setRemarks(rs.getString("RMK"));
			consignmentDocumentVO.setFlightDetails(rs.getString("FLTDTL"));
			/*consignmentDocumentVO.setFlightRoute(rs.getString("FLTRUT"));*/
			
			
	
		}
		private void collectMailDetails(MailInConsignmentVO mailInConsignmentVO,
				ResultSet rs) throws SQLException {
			log.entering("ConsignmentDetailsReportMapper", "collectMailDetails");
			if(mailInConsignmentVO.getTotalLetterBags()==0)
				mailInConsignmentVO.setTotalLetterBags(rs.getInt("LC_CNT"));
			//mailInConsignmentVO.setTotalLetterWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("LC_WGT")));
			if(mailInConsignmentVO.getTotalLetterWeight()== null ||
					mailInConsignmentVO.getTotalLetterWeight().getDisplayValue()== 0.0)
				mailInConsignmentVO.setTotalLetterWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("LC_WGT"),0,"K"));
			if(mailInConsignmentVO.getTotalParcelBags()==0)
				mailInConsignmentVO.setTotalParcelBags(rs.getInt("CP_CNT"));
			if(mailInConsignmentVO.getTotalParcelWeight()==null ||
					mailInConsignmentVO.getTotalParcelWeight().getDisplayValue()== 0.0)
				mailInConsignmentVO.setTotalParcelWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CP_WGT"),0,"K"));
			log.exiting("ConsignmentDetailsReportMapper", "collectMailDetails");
		}
		
	
	}
