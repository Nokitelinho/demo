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

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations.ConsignmentSummaryReportsAttributeBuilder.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-9084	:	13-Nov-2020	:	Draft
 */
public class ConsignmentSummaryReportsAttributeBuilder extends AttributeBuilderAdapter {
	
	@Override
	public Vector<String> getReportColumns() {
		
		Vector<String> columns = new Vector<String>();
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
		
		columns.add("TRPMNS");
		columns.add("CSGPRI");
		columns.add("FLTROU");
		columns.add("TIME");
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
		Vector <Vector<String>>subReportColumns = new Vector<Vector<String>>();
	    Vector <String> columns1 = new Vector<String> ();
	     
		columns1.add("ULDNUM");
		columns1.add("CONSELNUM");

		subReportColumns.add(columns1);
		return subReportColumns;
	}
	
	@Override
	public Vector<Vector<Vector>> getSubReportData(Collection data, Collection extraInfo)
	  {
		Vector<Vector> subReportDatas = new Vector<Vector>();
		Vector <Vector<Vector>>subReportData = new Vector<Vector<Vector>>();
		ArrayList consignmentDocumentVO = (ArrayList<ConsignmentDocumentVO>) data;
		Collection<ConsignmentDocumentVO> consignDocumentVOs = new ArrayList<ConsignmentDocumentVO>();
		consignDocumentVOs = (ArrayList)consignmentDocumentVO.get(0);
		for(ConsignmentDocumentVO consignmentDocumentVo : consignDocumentVOs){
		if (consignmentDocumentVo != null) {
			String uldNumber = null;
			String sealNumber = null;
			
			for(MailInConsignmentVO mailInconsign : consignmentDocumentVo.getMailInConsignmentcollVOs()){
				if(mailInconsign.getUldNumber()!=null){
					Vector<Object> row1 = new Vector<Object>();
				uldNumber = mailInconsign.getUldNumber();
				sealNumber = mailInconsign.getSealNumber();
				row1.add(uldNumber);
				row1.add(sealNumber);
				subReportDatas.add(row1);
				}
			}
		}
	}
		subReportData.add(subReportDatas); 
		return subReportData;
	  }
	
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		List<ConsignmentDocumentVO> consignmentDocumentVO = (ArrayList<ConsignmentDocumentVO>) data;
		Vector<Vector> reportData = new Vector<Vector>();
		List<MailInConsignmentVO> updVOs = null;
		ConsignmentDocumentVO consignmentDocumentVo = consignmentDocumentVO.get(0);
		
		if (consignmentDocumentVo != null) {
			updVOs = new ArrayList<MailInConsignmentVO>();
			String airportCode = null;
			String consignmentDate = null;
			String reportType = null;
			String subType = null;
			String remarks = null;
			String ooe = null;
			String doe = null;
			String dispatchDate = null;
			String dsn = null;
			String mailSubClass = null;
			String pou= null;
			String airportOfOrigin = null;
			String airportOfDestination = null;
			String poaCode = null;
			String airportOfTransit = null;
			String airlineName = null;
			String flightNumbers = null;
			String flightDates = null;
			String lcCount;
			String cpCount;
			String emsCount;
			String flightNumber = null;
			String flightDate = null;
			String paName = null;
			String wgt = null;
			String pieces = null;
			int loopCheck = 0;
			String transportationMeans=null;
			String consignmentPriority=null;
			String flightRoute=null;
			String time=null;
			String operatorOrigin=null;
			String ooeDes=null;
			String doeDes=null;
			String consignmentNumber=null;
			
			Collection<String> dsns = new ArrayList<String>();
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
						oldVO.getStatedWeight().setDisplayValue(oldVO.getTotalLetterWeight().getDisplayValue());
					}
					if(vo.getTotalParcelWeight() != null && previousVO.getTotalParcelWeight() != null) {
						oldVO.getTotalParcelWeight().setDisplayValue(previousVO.getTotalParcelWeight().getDisplayValue()+vo.getTotalParcelWeight().getDisplayValue());
						oldVO.getStatedWeight().setDisplayValue(oldVO.getTotalParcelWeight().getDisplayValue());

					}
					if(vo.getTotalEmsWeight() != null && previousVO.getTotalEmsWeight() != null) {
						oldVO.getTotalEmsWeight().setDisplayValue(previousVO.getTotalEmsWeight().getDisplayValue()+vo.getTotalEmsWeight().getDisplayValue());
						oldVO.getStatedWeight().setDisplayValue(oldVO.getTotalEmsWeight().getDisplayValue());

					}
					if(vo.getTotalSVWeight() != null && previousVO.getTotalSVWeight() != null) {
						oldVO.getTotalSVWeight().setDisplayValue(previousVO.getTotalSVWeight().getDisplayValue()+vo.getTotalSVWeight().getDisplayValue());
						oldVO.getStatedWeight().setDisplayValue(oldVO.getTotalSVWeight().getDisplayValue());

					}
					previousVO = vo;
				}
			}
			
			for(MailInConsignmentVO mailInconsign : updVOs){
			
			loopCheck++;	
			Vector<Object> row = new Vector<Object>();	
			airportCode = consignmentDocumentVo.getAirportCode();
			if(consignmentDocumentVo.getConsignmentDate()!= null){
			consignmentDate = consignmentDocumentVo.getConsignmentDate().toDisplayDateOnlyFormat();
			}
			reportType = consignmentDocumentVo.getReportType();
			subType = consignmentDocumentVo.getSubType();
			remarks = consignmentDocumentVo.getRemarks();
			ooe = mailInconsign.getOriginExchangeOffice();
			doe = mailInconsign.getDestinationExchangeOffice();
			dispatchDate =consignmentDocumentVo.getFirstFlightDepartureDate()!=null?consignmentDocumentVo.getFirstFlightDepartureDate().toDisplayDateOnlyFormat():ReportConstants.EMPTY_STRING;
			
			
			if (consignmentDocumentVo.getFlightRoute() != null
					&& consignmentDocumentVo.getFlightRoute().contains("-")) {
				String[] routes = consignmentDocumentVo.getFlightRoute().split(
						"-");
				if(routes!=null && routes.length>0){
					airportOfOrigin = routes[0];
					airportOfDestination = routes[1];
				}
			}
			
			poaCode = consignmentDocumentVo.getPaCode();
			dsn = mailInconsign.getDsn();
			mailSubClass = mailInconsign.getMailSubclass();
			
			if(loopCheck == 1){
			lcCount= String.valueOf(consignmentDocumentVo.getSubClassLC());
			cpCount = String.valueOf(consignmentDocumentVo.getSubClassCP());
			emsCount = String.valueOf(consignmentDocumentVo.getSubClassEMS());
			
			flightNumbers = consignmentDocumentVo.getFlightDetails();
			flightDates = consignmentDocumentVo.getFlightDates();
			pou = consignmentDocumentVo.getPou();
			airportOfTransit = consignmentDocumentVo.getAirportOftransShipment();
			}else{
				lcCount= ReportConstants.EMPTY_STRING;
				cpCount = ReportConstants.EMPTY_STRING;
				emsCount = ReportConstants.EMPTY_STRING;
				flightNumbers = ReportConstants.EMPTY_STRING;
				flightDates = ReportConstants.EMPTY_STRING;
				pou = ReportConstants.EMPTY_STRING;
				airportOfTransit = ReportConstants.EMPTY_STRING;
			}
			
			airlineName = consignmentDocumentVo.getAirlineName();
			flightNumber = consignmentDocumentVo.getFlightDetails();
			flightDate = consignmentDocumentVo.getFlightDates();

			paName = consignmentDocumentVo.getPaName();
			if(mailInconsign.getStatedWeight()!=null){
			wgt = String.valueOf(mailInconsign.getStatedWeight().getRoundedDisplayValue());
			}
			pieces = String.valueOf(consignmentDocumentVo.getStatedBags());
			transportationMeans=consignmentDocumentVo.getTransportationMeans()!=null?consignmentDocumentVo.getTransportationMeans():ReportConstants.EMPTY_STRING;
			consignmentPriority=consignmentDocumentVo.getConsignmentPriority() != null ? consignmentDocumentVo.getConsignmentPriority(): ReportConstants.EMPTY_STRING;
			flightRoute=consignmentDocumentVo.getFlightRoute() != null ? consignmentDocumentVo.getFlightRoute() : ReportConstants.EMPTY_STRING;
			time=consignmentDocumentVo.getFirstFlightDepartureDate() != null ? consignmentDocumentVo.getFirstFlightDepartureDate().toDisplayTimeOnlyFormat(true) : ReportConstants.EMPTY_STRING;
			operatorOrigin=consignmentDocumentVo.getOperatorOrigin() != null ? consignmentDocumentVo.getOperatorOrigin() : ReportConstants.EMPTY_STRING;
			ooeDes=consignmentDocumentVo.getOoeDescription() != null ? consignmentDocumentVo.getOoeDescription() : ReportConstants.EMPTY_STRING;
			doeDes=consignmentDocumentVo.getDoeDescription() != null ? consignmentDocumentVo.getDoeDescription() : ReportConstants.EMPTY_STRING;
			consignmentNumber=consignmentDocumentVo.getConsignmentNumber()!=null?consignmentDocumentVo.getConsignmentNumber():ReportConstants.EMPTY_STRING;
			
			row.add(airportCode);
			row.add(consignmentDate);
			row.add(reportType);
			row.add(subType);
			row.add(remarks);
			row.add(ooe);
			row.add(doe);
			row.add(dispatchDate);
			row.add(airportOfOrigin);
			row.add(airportOfDestination);
			row.add(poaCode);
			row.add(dsn);
			row.add(mailSubClass);
			row.add(pou);
			row.add(flightNumbers);
			row.add(lcCount);
			row.add(cpCount);
			row.add(emsCount);
			row.add(airlineName);
			row.add(flightDates);
			row.add(flightNumber);
			row.add(flightDate);
			row.add(airportOfTransit);
			row.add(paName);
			row.add(wgt);
			row.add(pieces);
			row.add(transportationMeans);
			row.add(consignmentPriority);
			row.add(flightRoute);
			row.add(time);
			row.add(operatorOrigin);
			row.add(ooeDes);
			row.add(doeDes);
			row.add(consignmentNumber);
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

		Vector<Object> reportParameters = new Vector<Object>();
		return reportParameters;
	}
	
}
