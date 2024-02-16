/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations.ConsignmentScreeningDetailsAttributeBuilder.java;
 *
 *	Created by	:	A-9084
 *	Created on	:	Nov 10, 2020
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;



public class ConsignmentScreeningDetailsAttributeBuilder extends AttributeBuilderAdapter {
	
	@Override
	public Vector<String> getReportColumns() {
		
		Vector<String> columns = new Vector<String>();
		columns.add("CONSTACOD"); 
		columns.add("CSGDOCNUM");
		columns.add("MALCTG"); 
		columns.add("CSGORG");
		columns.add("CSGDST");
		columns.add("POU");
		columns.add("SECSTACOD");
		columns.add("SCRAPLAUT"); 
		columns.add("SECSCRMTHCOD");
		columns.add("SECRSNCOD"); 
		columns.add("SECSTAPTY");
		columns.add("SECSTADAT"); 
		columns.add("SECSTATIM"); 
		columns.add("ADLSECINF");
		return columns;
	}
	
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		List<ConsignmentDocumentVO> consignmentDocumentVO = (ArrayList<ConsignmentDocumentVO>) data;
		Vector<Vector> reportData = new Vector<Vector>();
		ConsignmentDocumentVO consignmentDocumentVo = consignmentDocumentVO.get(0);
		
		
		if (consignmentDocumentVo != null) {
			Vector<Object> row = new Vector<Object>();
			String constatusCode = null;
			String consignmentNumber = null;
			String origin = null;
			String destination = null;
			Collection<String> pou = new ArrayList<>();
			String securityStatusCode = null;
			String securityAuthSM= null;
			String screeningReasonSE ="Mail";
			String secStaParty = null;
			String[] secStaDate = null;
			String secStatusDate = null;
			String date = null;
			String time = null;
			String addSecurityInfo = null;
			String category = null;
			Collection<String> screeningMethodSM = new ArrayList<>();
			long serialNumber = 0;
			int count=0;
			
			for(Map.Entry<String,Collection<OneTimeVO>> oneTimeValue : consignmentDocumentVo.getOneTimes().entrySet()){
				for(OneTimeVO oneTimeVO : oneTimeValue.getValue()){
					if(oneTimeVO.getFieldValue().equals(consignmentDocumentVo.getMailCategory())){
						category = oneTimeVO.getFieldDescription();
						break;
					}
				}
			}
			
			consignmentNumber = consignmentDocumentVo.getConsignmentNumber();
			origin = consignmentDocumentVo.getConsignmentOrigin();
			destination = consignmentDocumentVo.getConsigmentDest(); 
			securityStatusCode = consignmentDocumentVo.getSecurityStatusCode(); 
			addSecurityInfo =consignmentDocumentVo.getMstAddionalSecurityInfo();
			
			for(ConsignmentScreeningVO screening : consignmentDocumentVo.getConsignementScreeningVOs()){
				if((MailConstantsVO.REASON_CODE_CS).equals(screening.getSecurityReasonCode()) 
						&& (MailConstantsVO.RA_ISSUING).equals(screening.getAgentType())
					&& serialNumber<screening.getSerialNumber()){
					constatusCode = new StringBuilder(screening.getIsoCountryCode()).append("-")
							.append(screening.getAgentType()).append("-")
							.append(screening.getAgentID()).append("-")
							.append(screening.getExpiryDate()).toString();
					serialNumber = screening.getSerialNumber();		
				} 
				
				if((MailConstantsVO.REASON_CODE_SM).equals(screening.getSecurityReasonCode())){
					securityAuthSM = screening.getScreeningAuthority(); 
					if(!screeningMethodSM.contains(screening.getScreeningMethodCode()) && count<3 && screening.getScreeningMethodCode()!=null)
					{
						screeningMethodSM .add( screening.getScreeningMethodCode());
						count++;
					}
					secStaParty = screening.getSecurityStatusParty();
					
					if(screening.getSecurityStatusDate()!=null){
					secStatusDate = screening.getSecurityStatusDate().toDisplayFormat("dd/MM/YYYY hh:mm");
					secStaDate = secStatusDate.split(" ");
					date = secStaDate[0];
					time = secStaDate[1];
					}
					
				}
			
			}
			
			
			for(RoutingInConsignmentVO routing : consignmentDocumentVo.getRoutingInConsignmentVOs()){
				if(routing!=null && routing.getPou()!=null){
				pou.add(routing.getPou());
				}
			}
			
			row.add(constatusCode);
			row.add(consignmentNumber);
			row.add(category);
			row.add(origin);
			row.add(destination);
			row.add(pou);
			row.add(securityStatusCode);
			row.add(securityAuthSM);
			row.add(screeningMethodSM);
			row.add(screeningReasonSE);
			row.add(secStaParty);
			row.add(date);
			row.add(time);
			row.add(addSecurityInfo);
		
			reportData.add(row);
		
		}
		return reportData;
	}
	
	
	@Override
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		return reportParameters;
	}
	
}
	

