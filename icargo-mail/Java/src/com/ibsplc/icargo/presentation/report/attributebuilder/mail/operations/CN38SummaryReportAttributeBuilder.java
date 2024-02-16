/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations.ConsignmentSummaryReportsAttributeBuilder.java
 *
 *	Created by	:	A-9084
 *	Created on	:	13-Nov-2020
 *
 *  Copyright 2020 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
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
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations.ConsignmentSummaryReportsAttributeBuilder.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	13-Nov-2020	:	Draft
 */
public class CN38SummaryReportAttributeBuilder extends AttributeBuilderAdapter {
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter#getReportColumns()
	 *	Added by 			: A-5219 on 29-Nov-2020
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	@Override
	public Vector<String> getReportColumns() {
		
		Vector<String> columns = new Vector<String>();
		columns.add("OPRORG");
		columns.add("CSGDAT");
		columns.add("CSGDOCNUM"); 
		columns.add("ORGEXGOFCDES");
		columns.add("DSTEXGOFCDES");
		columns.add("ORGEXGOFC");
		columns.add("DSTEXGOFC"); 
		columns.add("MALCTGCOD"); 
		columns.add("DSN");
		columns.add("LCCNT");
		columns.add("CPCNT");
		columns.add("EMSCNT");
		columns.add("LCWGT");
		columns.add("CPWGT");
		columns.add("EMSWGT");
		columns.add("RMK");
		columns.add("PRYMAL");
		columns.add("AIRMAL");
		columns.add("SVCNT");
		columns.add("SVWGT");
		columns.add("OPRDST");
		columns.add("PRY1");
		columns.add("NONPRY1");
		columns.add("ARMAL");
		columns.add("SAL");
		columns.add("SUR");
		columns.add("PRY2");
		columns.add("NONPRY2");
		columns.add("PAR");
		columns.add("EMS");
		columns.add("FLTNUM");
		columns.add("DATEOFDEP");
		columns.add("TIM");
		columns.add("ARPOFTRANS");
		columns.add("ARPOFOFL");
		return columns;
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter#getSubReportColumns()
	 *	Added by 			: A-5219 on 29-Nov-2020
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	@Override
	public Vector <Vector<String>> getSubReportColumns(){
		Vector <Vector<String>>subReportColumns = new Vector<Vector<String>>();
	    Vector <String> columns1 = new Vector<String> ();
		columns1.add("ULDNUM1");
		columns1.add("CONSELNUM1");
		subReportColumns.add(columns1);
		return subReportColumns;
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter#getSubReportData(java.util.Collection, java.util.Collection)
	 *	Added by 			: A-5219 on 29-Nov-2020
	 * 	Used for 	:
	 *	Parameters	:	@param data
	 *	Parameters	:	@param extraInfo
	 *	Parameters	:	@return
	 */
	@Override
	public Vector<Vector<Vector>> getSubReportData(Collection data, Collection extraInfo){
		Vector<Vector> subReportDatas = new Vector<Vector>();
		Vector <Vector<Vector>>subReportData = new Vector<Vector<Vector>>();
		ArrayList consignmentDocumentVO = (ArrayList<ConsignmentDocumentVO>) data;
		if(consignmentDocumentVO != null && !consignmentDocumentVO.isEmpty()){
			ArrayList<ConsignmentDocumentVO> consignmentDocumentVOs = (ArrayList)consignmentDocumentVO.get(0);
			ConsignmentDocumentVO consignmentDocumentVo = consignmentDocumentVOs.get(0);
			if(consignmentDocumentVo.getMailInConsignmentcollVOs() != null &&
					!consignmentDocumentVo.getMailInConsignmentcollVOs().isEmpty()){
				for(MailInConsignmentVO mailInconsign : consignmentDocumentVo.getMailInConsignmentcollVOs()){
				if(mailInconsign.getUldNumber() != null){
					Vector<Object> row = new Vector<Object>();
				//for(MailInConsignmentVO mailInconsign : consignmentDocumentVo.getMailInConsignmentcollVOs()){
					row.add((mailInconsign.getUldNumber() != null) ? mailInconsign.getUldNumber()
							: ReportConstants.EMPTY_STRING);
					row.add((mailInconsign.getSealNumber() != null) ? mailInconsign.getSealNumber()
							: ReportConstants.EMPTY_STRING);
					subReportDatas.add(row);
				}
				}
			}
		}
		subReportData.add(subReportDatas); 
		return subReportData;
	  }
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter#getReportData(java.util.Collection, java.util.Collection)
	 *	Added by 			: A-5219 on 29-Nov-2020
	 * 	Used for 	:
	 *	Parameters	:	@param data
	 *	Parameters	:	@param extraInfo
	 *	Parameters	:	@return
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		List<ConsignmentDocumentVO> consignmentDocumentVOs = (ArrayList<ConsignmentDocumentVO>) data;
		Vector<Vector> reportData = new Vector<Vector>();
		List<MailInConsignmentVO> updVOs = null;
		if (consignmentDocumentVOs != null && !consignmentDocumentVOs.isEmpty()) {

			ConsignmentDocumentVO consignDocumentVO = consignmentDocumentVOs
					.iterator().next();
			if(consignDocumentVO != null && consignDocumentVO
					.getMailInConsignmentcollVOs() != null && !consignDocumentVO
							.getMailInConsignmentcollVOs().isEmpty()){
				updVOs = new ArrayList<MailInConsignmentVO>();
				Collection<String> dsns = new ArrayList<String>();
				MailInConsignmentVO previousVO = null; 
				for(MailInConsignmentVO vo : consignDocumentVO
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
						if(vo.getTotalLetterWeight() != null && previousVO.getTotalLetterWeight() != null)
							oldVO.getTotalLetterWeight().setDisplayValue(previousVO.getTotalLetterWeight().getDisplayValue()+vo.getTotalLetterWeight().getDisplayValue());
						if(vo.getTotalParcelWeight() != null && previousVO.getTotalParcelWeight() != null)
							oldVO.getTotalParcelWeight().setDisplayValue(previousVO.getTotalParcelWeight().getDisplayValue()+vo.getTotalParcelWeight().getDisplayValue());
						if(vo.getTotalEmsWeight() != null && previousVO.getTotalEmsWeight() != null)
							oldVO.getTotalEmsWeight().setDisplayValue(previousVO.getTotalEmsWeight().getDisplayValue()+vo.getTotalEmsWeight().getDisplayValue());
						if(vo.getTotalSVWeight() != null && previousVO.getTotalSVWeight() != null)
							oldVO.getTotalSVWeight().setDisplayValue(previousVO.getTotalSVWeight().getDisplayValue()+vo.getTotalSVWeight().getDisplayValue());
						previousVO = vo;
					}
				}
			for (MailInConsignmentVO mailVo : updVOs) {
				Vector<Object> row = new Vector<Object>();
				row.add((consignDocumentVO.getOperatorOrigin() != null) ? consignDocumentVO
						.getOperatorOrigin() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getConsignmentDate() != null) ? consignDocumentVO
						.getConsignmentDate().toDisplayDateOnlyFormat()
						: ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getConsignmentNumber() != null) ? consignDocumentVO
						.getConsignmentNumber() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getOoeDescription() != null) ? consignDocumentVO
						.getOoeDescription() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getDoeDescription() != null) ? consignDocumentVO
						.getDoeDescription() : ReportConstants.EMPTY_STRING);
				row.add((mailVo.getOriginExchangeOffice() != null) ? mailVo
						.getOriginExchangeOffice()
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getDestinationExchangeOffice() != null) ? mailVo
						.getDestinationExchangeOffice()
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getMailCategoryCode() != null) ? mailVo
						.getMailCategoryCode()
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getDsn() != null) ? mailVo.getDsn()
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getTotalLetterBags() > 0) ? String.valueOf(mailVo.getTotalLetterBags())
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getTotalParcelBags() > 0) ? String.valueOf(mailVo.getTotalParcelBags())
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getTotalEmsBags() > 0) ? String.valueOf(mailVo.getTotalEmsBags())
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getTotalLetterWeight() != null && mailVo.getTotalLetterWeight().getDisplayValue() > 0.0) ? 
							String.valueOf(mailVo.getTotalLetterWeight().getDisplayValue())
						: MailConstantsVO.ZERO);
				row.add((mailVo.getTotalParcelWeight() != null && mailVo.getTotalParcelWeight().getDisplayValue() > 0.0) ? 
						String.valueOf(mailVo.getTotalParcelWeight().getDisplayValue())
					: MailConstantsVO.ZERO);
				row.add((mailVo.getTotalEmsWeight() != null && mailVo.getTotalEmsWeight().getDisplayValue() > 0.0) ? 
						String.valueOf(mailVo.getTotalEmsWeight().getDisplayValue())
					: MailConstantsVO.ZERO);
				row.add((consignDocumentVO.getRemarks() != null) ? 
						consignDocumentVO.getRemarks(): ReportConstants.EMPTY_STRING);
				row.add(consignDocumentVO.isPriorityMalExists() ? "Y" : "N");
				row.add(consignDocumentVO.isAirMalExists() ? "Y" : "N");
				row.add((mailVo.getTotalSVbags() > 0) ? String.valueOf(mailVo.getTotalSVbags())
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getTotalSVWeight() != null && mailVo.getTotalSVWeight().getDisplayValue() > 0.0) ? 
						String.valueOf(mailVo.getTotalSVWeight().getDisplayValue())
					: MailConstantsVO.ZERO);
				row.add((consignDocumentVO.getOperatorDestination() != null) ? 
						consignDocumentVO.getOperatorDestination(): ReportConstants.EMPTY_STRING);
				row.add(consignDocumentVO.isPriority() ? "Y" : "N");
				row.add(consignDocumentVO.isNonPriority() ? "Y" : "N");
				row.add(consignDocumentVO.isByAir() ? "Y" : "N");
				row.add(consignDocumentVO.isBySal() ? "Y" : "N");
				row.add(consignDocumentVO.isBySurface() ? "Y" : "N");
				row.add(consignDocumentVO.isPriorityByAir() ? "Y" : "N");
				row.add(consignDocumentVO.isNonPrioritySurface() ? "Y" : "N");
				row.add(consignDocumentVO.isParcels() ? "Y" : "N");
				row.add(consignDocumentVO.isEms() ? "Y" : "N");
				row.add((consignDocumentVO.getFlightDetails() != null) ? consignDocumentVO
						.getFlightDetails() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getFirstFlightDepartureDate() != null) ? consignDocumentVO
						.getFirstFlightDepartureDate()
						.toDisplayDateOnlyFormat()
						: ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getFirstFlightDepartureDate() != null) ? consignDocumentVO
						.getFirstFlightDepartureDate().toDisplayTimeOnlyFormat(
								true) : ReportConstants.EMPTY_STRING);
				if(consignDocumentVO.getRoutingInConsignmentVOs() != null && 
						!consignDocumentVO.getRoutingInConsignmentVOs().isEmpty() &&
						consignDocumentVO.getRoutingInConsignmentVOs().size() >1){
					String transhipment = null;
					if(consignDocumentVO.getAirportOftransShipment() != null 
							&& consignDocumentVO.getAirportOftransShipment().trim().length() > 0){
						String[] airports = consignDocumentVO.getAirportOftransShipment().split("-");
						if(airports != null && airports.length == 1 && consignDocumentVO.getTranAirportName() != null ){
							transhipment =consignDocumentVO.getTranAirportName();
						}
					}
					if(transhipment == null){
						transhipment = consignDocumentVO.getAirportOftransShipment();
					}
					row.add((transhipment!= null) ? 
							transhipment	
							: ReportConstants.EMPTY_STRING);
					row.add((consignDocumentVO.getPouName() != null && consignDocumentVO.getPouName().trim().length() > 0) ? 
							consignDocumentVO.getPouName()
							: ReportConstants.EMPTY_STRING);
				}else{
				if (consignDocumentVO.getFlightRoute() != null
						&& consignDocumentVO.getFlightRoute().contains("-")) {
					String[] routes = consignDocumentVO.getFlightRoute().split(
							"-");

					row.add((routes != null && routes.length > 0) ? routes[0]
							: ReportConstants.EMPTY_STRING);
					row.add((routes != null && routes.length > 1) ? routes[1]
							: ReportConstants.EMPTY_STRING);

				}else{
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
				}
				}
				reportData.add(row);
			}
			}
		}
		return reportData;
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter#getReportParameters(java.util.Collection, java.util.Collection)
	 *	Added by 			: A-5219 on 29-Nov-2020
	 * 	Used for 	:
	 *	Parameters	:	@param parameters
	 *	Parameters	:	@param extraInfo
	 *	Parameters	:	@return
	 */
	@Override
	public Vector<Object> getReportParameters(Collection parameters, Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		return reportParameters;
	}
	
}
