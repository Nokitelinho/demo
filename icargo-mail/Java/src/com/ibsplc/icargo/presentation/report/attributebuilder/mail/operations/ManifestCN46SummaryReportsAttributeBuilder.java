/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations.ManifestCN46SummaryReportsAttributeBuilder.java
 *
 *	Created by	:	A-10647
 *	Created on	:	09-Nov-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations.ManifestCN46SummaryReportsAttributeBuilder.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10647	:	09-Nov-2022	:	Draft
 */
public class ManifestCN46SummaryReportsAttributeBuilder extends AttributeBuilderAdapter{
	@Override
	public Vector<String> getReportColumns() {
		
		Vector<String> columns = new Vector<>();
		columns.add("ARPCOD"); 
		columns.add("CSGDAT");
		columns.add("CSGTYP"); 
		columns.add("SUBTYP");
		columns.add("RMK");
		columns.add("ORGEXGOFC");
		columns.add("DSTEXGOFC"); 
		columns.add("FSTFLTDEPDAT");
		columns.add("ORIGIN"); 
		columns.add("DESTINATION");
		columns.add("POACOD"); 
		columns.add("DSN");
		columns.add("MALSUBCLS"); 
		columns.add("POU");
		columns.add("FLIGHTNUMS");
		columns.add("LCSUBCLS");
		columns.add("CPSUBCLS");
		columns.add("EMSSUBCLS");
		columns.add("AIRLINENAME");
		columns.add("FTLDATES");
		columns.add("FTLNUM");
		columns.add("FTLDATE");
		columns.add("AIRTRANS");
		columns.add("POANAM");
		columns.add("WGT");
		columns.add("PIECES");
		columns.add("OPRORG");
		columns.add("OOEDES");
		columns.add("DOEDES");
		columns.add("CSGDOCNUM");
		columns.add("LCCNT");
		columns.add("CPCNT");
		columns.add("EMSCNT");
		columns.add("SVCNT");
		columns.add("LCWGT");
		columns.add("CPWGT");
		columns.add("EMSWGT");
		columns.add("SVWGT");
		
		
		
		
		
		return columns;
		
		 
	}
	
	@Override
	public Vector <Vector<String>> getSubReportColumns(){
		Vector <Vector<String>>subReportColumns = new Vector<>();
	    Vector <String> columns1 = new Vector<> ();
	     
		columns1.add("ULDNUM");
		columns1.add("CONSELNUM");

		subReportColumns.add(columns1);
		return subReportColumns;
	}
	
	@Override
	public Vector<Vector<Vector>> getSubReportData(Collection data, Collection extraInfo)
	  {
		Vector<Vector> subReportDatas = new Vector<>();
		Vector <Vector<Vector>>subReportData = new Vector<>();
		ArrayList<ConsignmentDocumentVO> consignmentDocumentVO =  (ArrayList<ConsignmentDocumentVO>) data;
		Collection<ConsignmentDocumentVO> consignDocumentVOs = (Collection<ConsignmentDocumentVO>) consignmentDocumentVO.get(0);
		for(ConsignmentDocumentVO consignmentDocumentVo : consignDocumentVOs){
		if (consignmentDocumentVo != null) {
			String uldNumber = null;
			String sealNumber = null;
			String consignmentKey ="";
			String previousonsignmentKey ="";
			
			for(MailInConsignmentVO mailInconsign : consignmentDocumentVo.getMailInConsignmentcollVOs()){
				consignmentKey = mailInconsign.getKeyCondition();
				if(mailInconsign.getUldNumber()!=null && !consignmentKey.equals(previousonsignmentKey) ){
					Vector<Object> row1 = new Vector<>();
				uldNumber = mailInconsign.getUldNumber();
				sealNumber = mailInconsign.getSealNumber();
				row1.add(uldNumber);
				row1.add(sealNumber);
				subReportDatas.add(row1);
				}
				previousonsignmentKey = consignmentKey;
			}
		}
	}
		subReportData.add(subReportDatas); 
		return subReportData;
	  }
	
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		List<ConsignmentDocumentVO> consignmentDocumentVO = (ArrayList<ConsignmentDocumentVO>) data;
		Vector<Vector> reportData = new Vector<>();
		List<MailInConsignmentVO> updVOs = null;
		ConsignmentDocumentVO consignmentDocumentVo = consignmentDocumentVO.get(0);
		
		if (consignmentDocumentVo != null) {
			updVOs = new ArrayList<>();	
			Collection<String> dsns = new ArrayList<>();
			MailInConsignmentVO previousVO = null; 
			for(MailInConsignmentVO vo : consignmentDocumentVo
						.getMailInConsignmentcollVOs()){
				String key = (new StringBuilder(vo.getOriginExchangeOffice()).append("-")
						.append(vo.getDestinationExchangeOffice())).append("-")
						.append(vo.getMailCategoryCode()).append("-")
						.append(vo.getMailSubClassGroup()).append("-")
						.append(vo.getDsn()).append("-").append(vo.getYear()).toString();
				if(!dsns.contains(key)){
					dsns.add(key);
					updVOs.add(vo);
					previousVO = vo;
				}else{
					MailInConsignmentVO oldVO = updVOs.get(updVOs.size()-1);
					oldVO.setTotalLetterBags(previousVO.getTotalLetterBags()+vo.getTotalLetterBags());
					oldVO.setTotalParcelBags(previousVO.getTotalParcelBags()+vo.getTotalParcelBags());
					oldVO.setTotalEmsBags(previousVO.getTotalEmsBags()+vo.getTotalEmsBags());
					oldVO.setTotalSVbags(previousVO.getTotalSVbags()+vo.getTotalSVbags());
					if(vo.getTotalLetterWeight() != null && previousVO.getTotalLetterWeight() != null) {
						oldVO.getTotalLetterWeight().setDisplayValue(previousVO.getTotalLetterWeight().getDisplayValue()+vo.getTotalLetterWeight().getDisplayValue());
					}
					if(vo.getTotalParcelWeight() != null && previousVO.getTotalParcelWeight() != null) {
						oldVO.getTotalParcelWeight().setDisplayValue(previousVO.getTotalParcelWeight().getDisplayValue()+vo.getTotalParcelWeight().getDisplayValue());
					}
					if(vo.getTotalEmsWeight() != null && previousVO.getTotalEmsWeight() != null) {
						oldVO.getTotalEmsWeight().setDisplayValue(previousVO.getTotalEmsWeight().getDisplayValue()+vo.getTotalEmsWeight().getDisplayValue());
					}
					if(vo.getTotalSVWeight() != null && previousVO.getTotalSVWeight() != null) {
						oldVO.getTotalSVWeight().setDisplayValue(previousVO.getTotalSVWeight().getDisplayValue()+vo.getTotalSVWeight().getDisplayValue());
					}
					previousVO = vo;
				}
			}
			
			for(MailInConsignmentVO mailInconsign : updVOs){
			
			Vector<Object> row = new Vector<>();
			row.add(consignmentDocumentVo.getAirportCode());

			if(consignmentDocumentVo.getConsignmentDate()!= null){
				row.add(consignmentDocumentVo.getConsignmentDate().toDisplayDateOnlyFormat());

			}
			row.add(consignmentDocumentVo.getReportType());
			row.add(consignmentDocumentVo.getSubType());
			row.add(consignmentDocumentVo.getRemarks());
			row.add(mailInconsign.getOriginExchangeOffice());
			row.add(mailInconsign.getDestinationExchangeOffice());
			row.add(consignmentDocumentVo.getFirstFlightDepartureDate()!=null?consignmentDocumentVo.getFirstFlightDepartureDate().toDisplayDateOnlyFormat():ReportConstants.EMPTY_STRING);
			
			
			
			if (consignmentDocumentVo.getFlightRoute() != null
					&& consignmentDocumentVo.getFlightRoute().contains("-")) {
				String[] routes = consignmentDocumentVo.getFlightRoute().split(
						"-");
				if(routes!=null && routes.length>0){
					row.add(routes[0]);
					row.add(routes[1]);
				}
			}
			row.add(consignmentDocumentVo.getPaCode());
			row.add(mailInconsign.getDsn());
			row.add(mailInconsign.getMailSubclass());
			row.add(consignmentDocumentVo.getPou());
			row.add(consignmentDocumentVo.getFlightDetails());
			row.add(String.valueOf(mailInconsign.getTotalLetterBags()));
			row.add(String.valueOf(mailInconsign.getTotalParcelBags()));
			row.add(String.valueOf(mailInconsign.getTotalEmsBags()));
			row.add(consignmentDocumentVo.getAirlineName());
			row.add(consignmentDocumentVo.getFlightDates());
			row.add(consignmentDocumentVo.getFlightDetails());
			row.add(consignmentDocumentVo.getFlightDates());
			row.add(consignmentDocumentVo.getAirportOftransShipment());
			row.add(consignmentDocumentVo.getPaName());
			if(mailInconsign.getStatedWeight()!=null){
			row.add(String.valueOf(mailInconsign.getStatedWeight().getRoundedDisplayValue()));
			}
			row.add(String.valueOf(consignmentDocumentVo.getStatedBags()));
			row.add(consignmentDocumentVo.getOperatorOrigin() != null ? consignmentDocumentVo.getOperatorOrigin() : ReportConstants.EMPTY_STRING);
			row.add(consignmentDocumentVo.getOoeDescription() != null ? consignmentDocumentVo.getOoeDescription() : ReportConstants.EMPTY_STRING);
			row.add(consignmentDocumentVo.getDoeDescription() != null ? consignmentDocumentVo.getDoeDescription() : ReportConstants.EMPTY_STRING);
			row.add(consignmentDocumentVo.getConsignmentNumber()!=null?consignmentDocumentVo.getConsignmentNumber():ReportConstants.EMPTY_STRING);
			row.add((mailInconsign.getTotalLetterBags() > 0) ? String.valueOf(mailInconsign.getTotalLetterBags()): ReportConstants.EMPTY_STRING);
			row.add((mailInconsign.getTotalParcelBags() > 0) ? String.valueOf(mailInconsign.getTotalParcelBags()): ReportConstants.EMPTY_STRING);
			row.add((mailInconsign.getTotalEmsBags() > 0) ? String.valueOf(mailInconsign.getTotalEmsBags()): ReportConstants.EMPTY_STRING);
			row.add((mailInconsign.getTotalSVbags() > 0) ? String.valueOf(mailInconsign.getTotalSVbags()): ReportConstants.EMPTY_STRING);
			row.add((mailInconsign.getTotalLetterWeight() != null && mailInconsign.getTotalLetterWeight().getDisplayValue() > 0.0) ?String.valueOf(mailInconsign.getTotalLetterWeight().getDisplayValue()): 0);
			row.add((mailInconsign.getTotalParcelWeight() != null && mailInconsign.getTotalParcelWeight().getDisplayValue() > 0.0) ?String.valueOf(mailInconsign.getTotalParcelWeight().getDisplayValue()):0);
			row.add((mailInconsign.getTotalEmsWeight() != null && mailInconsign.getTotalEmsWeight().getDisplayValue() > 0.0) ? String.valueOf(mailInconsign.getTotalEmsWeight().getDisplayValue()): 0);
			row.add((mailInconsign.getTotalSVWeight() != null && mailInconsign.getTotalSVWeight().getDisplayValue() > 0.0) ? String.valueOf(mailInconsign.getTotalSVWeight().getDisplayValue()): 0);
			reportData.add(row);
			}
		}
		
		return reportData;
	}
	
	@Override
	public Vector<Object> getReportParameters(Collection parameters, Collection extraInfo) {
		return new Vector<>();
	}

}
