
/*
 * MRAIrregularityAttributeBuilder.java Created on Oct 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3229
 *
 */
public class MRAIrregularityAttributeBuilder extends AttributeBuilderAdapter{
	
	private Log log=LogFactory.getLogger("MRA DEFAULTS");
	
	private static final String IRP_STATUS = "mra.defaults.irpstatus";
	private static final String BLANKSPACE ="";
	
	
	/**
	 * main report columns
	 */
/**
 * Method to construct the report column names. The column names corresponds
 * to the column names of the view used while laying out the report. The
 * order of the column names should match the order in which the database
 * fields are laid out in the report
 * @param parameters
 * @param extraInfo
 * @return Vector<String> the column names
 */

@Override
public Vector<String> getReportColumns() {
	Vector<String> columns = new Vector<String>();

	columns.add("DSN");
	columns.add("RCVDAT");
	columns.add("MOP");	
	
	columns.add("ORGEXGOFC");
	columns.add("DSTEXGOFC");
	
	columns.add("UPDWGTCHG");
	columns.add("TOTAL");
	

	log.log(Log.FINE, "***********COLUMNS***********", columns);
	return columns;
}


/**
 * Method to construct the report parameters. The report parameters
 * corresponds to the parameter fields in the report. The order of the
 * parameters should match the order in which the parameter fields are laid
 * out in the report
 * @param parameters the parameter data
 * @param extraInfo information required for formatting the parameters
 * @return Vector the report parameters
 */
	@Override
public Vector<Object> getReportParameters(Collection parameters,
		Collection extraInfo) {
	
	Vector<Object> reportParameters = new Vector<Object>();
	
	Object dataParameters = ((ArrayList<Object>) parameters).get(0);
	
	MRAIrregularityFilterVO filterVO = (MRAIrregularityFilterVO) dataParameters;
	
	// retreive the one time values from the extra Info
	Map<String, Collection<OneTimeVO>> oneTimeDetails = null;
	Collection<OneTimeVO> irp = null;
	

	if (extraInfo != null && extraInfo.size() > 0) {
		Object onetimes = ((ArrayList<Object>) extraInfo).get(0);
		oneTimeDetails = (Map<String, Collection<OneTimeVO>>) onetimes;
		irp = oneTimeDetails.get(IRP_STATUS);
		
		
	}

	
	
	// From Date
	if (filterVO.getFromDate() != null) {
		LocalDate fromDate = filterVO.getFromDate();
		
		reportParameters.add(fromDate.toDisplayDateOnlyFormat());
		} else {
			reportParameters.add(BLANKSPACE);
		}
	
	//To Date
	if (filterVO.getToDate() != null) {
		LocalDate toDate = filterVO.getToDate();
		
		reportParameters.add(toDate.toDisplayDateOnlyFormat());
		} else {
			reportParameters.add(BLANKSPACE);
		}

	// Irregularity status
	if (filterVO.getIrpStatus() != null
			&& filterVO.getIrpStatus().trim().length() > 0) {
		String irpStatus = filterVO.getIrpStatus();

		if (irp != null && irp.size() > 0) {
			for (OneTimeVO oneTimeVo : irp) {
				if (oneTimeVo.getFieldValue().equals(irpStatus)) {
					reportParameters.add(oneTimeVo.getFieldDescription());
					break;
				}
			}
		} else {
			reportParameters.add(BLANKSPACE);
		}

	} else {
		reportParameters.add(BLANKSPACE);
	}
	
	// Station
	if (filterVO.getOffloadStation() != null
			&& filterVO.getOffloadStation().trim().length() > 0) {
		String station = filterVO.getOffloadStation();
		reportParameters.add(station);

	} else {
		reportParameters.add(BLANKSPACE);
	}
	
	
	// origin
	if (filterVO.getOrigin() != null
			&& filterVO.getOrigin().trim().length() > 0) {
		String origin = filterVO.getOrigin();
		reportParameters.add(origin);

	} else {
		reportParameters.add(BLANKSPACE);
	}
	
	// Destination
	if (filterVO.getDestination() != null
			&& filterVO.getDestination().trim().length() > 0) {
		String destination = filterVO.getDestination();
		reportParameters.add(destination);

	} else {
		reportParameters.add(BLANKSPACE);
	}
	
	
	
	// DSN Number
	if (filterVO.getDsn() != null
			&& filterVO.getDsn().trim().length() > 0){
		
		String dsn = filterVO.getDsn();
		reportParameters.add(dsn);

	} else {
		reportParameters.add(BLANKSPACE);
	}
	
	// Effective Date
	if (filterVO.getEffectiveDate() != null) {
		LocalDate effDate = filterVO.getEffectiveDate();
		
		reportParameters.add(effDate.toDisplayDateOnlyFormat());
	} else {
			reportParameters.add(BLANKSPACE);
	}

	log.exiting("MRAIrregularityAttributeBuilder", "getReportParameters");
	
	return reportParameters;

}
	
	
	
	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<Vector>();
	
		log.log(Log.FINE, "Report Data ===> ", data);
		if (data != null) {
			
			for (MRAIrregularityVO irregularityVO : ((Collection<MRAIrregularityVO>) data)) {
				
								
				Vector<Object> row = new Vector<Object>();
				
				//DSN Number
				if (irregularityVO.getDsnNumber() != null ) {
					String dsn = irregularityVO.getDsnNumber();
					
					row.add(dsn);
					
				} else {
					
					row.add(BLANKSPACE);
				}		
				
				
				//execution date
				if (irregularityVO.getExecutionDate() != null) {
					row.add(irregularityVO.getExecutionDate().toDisplayDateOnlyFormat());
				} else {
					row.add(BLANKSPACE);
				}
				
				
				//ModeOfPayment
				if (irregularityVO.getModeOfPayment() != null) {
					row.add(irregularityVO.getModeOfPayment());
				} else {
					row.add(BLANKSPACE);
				}
				
				
				//Origin
				if (irregularityVO.getAwbOrigin() != null) {
					row.add(irregularityVO.getAwbOrigin());
				} else {
					row.add(BLANKSPACE);
				}
				
				//Destination
				if (irregularityVO.getAwbDestination() != null) {
					row.add(irregularityVO.getAwbDestination());
				} else {
					row.add(BLANKSPACE);
				}
				
				//FrieghtCharges
				if (irregularityVO.getFrieghtCharges() != null) {
					row.add(String.valueOf(irregularityVO.getFrieghtCharges().getAmount()));
				} else {
					row.add(BLANKSPACE);
				}
				
				
			
				
				//Total
				if (irregularityVO.getTotal() != null) {			    				
					row.add(String.valueOf(irregularityVO.getTotalCharges().getAmount()));
				} else {
					row.add(BLANKSPACE);
				}
				
				tableData.add(row);
				
		}
		
		} else {
			Vector<Object> emptyRow = new Vector<Object>();
			emptyRow.add(BLANKSPACE);
			emptyRow.add(BLANKSPACE);
			emptyRow.add(BLANKSPACE);
			emptyRow.add(BLANKSPACE);
			emptyRow.add(BLANKSPACE);
			emptyRow.add(BLANKSPACE);
			emptyRow.add(BLANKSPACE);
			
			
			tableData.add(emptyRow);
		}	
		return tableData;
	}
	
	
	/**
	 * sub report  column
	 */
	@Override
	public Vector<Vector<String>> getSubReportColumns() {

		Vector<Vector<String>> subReportColumns = new Vector<Vector<String>>();


		Vector<String> columnsForSubRptOne = new Vector<String>();

		columnsForSubRptOne.add("DSN");
		columnsForSubRptOne.add("ORGFLTNUM");
		columnsForSubRptOne.add("ORGFLTDAT");
		columnsForSubRptOne.add("IRPDAT");
		columnsForSubRptOne.add("OFLSTN");
		columnsForSubRptOne.add("OFLWGT");
		columnsForSubRptOne.add("REVFLTNUM");
		columnsForSubRptOne.add("REVFLTDAT");

		subReportColumns.add(columnsForSubRptOne);
		log.log(Log.INFO, "**************columns1*************",
				columnsForSubRptOne);
		Vector<String> columnsForSubRptTwo = new Vector<String>();

		columnsForSubRptTwo.add("DSN");
		columnsForSubRptTwo.add("UPDWGT");
		columnsForSubRptTwo.add("ROUTE");
		columnsForSubRptTwo.add("IRPSTA");
		
		
		subReportColumns.add(columnsForSubRptTwo);
		log.log(Log.INFO, "**************columns2*************",
				columnsForSubRptTwo);
		return subReportColumns;
		
		
	}
	
	
	
	/**
	 * subreport data
	 * @param data
	 * @param extraInfo
	 */
	@Override
	public Vector<Vector<Vector>> getSubReportData(Collection data, Collection extraInfo) {
		
		
		// retreive tthe one time values from the extra Info
		Map<String, Collection<OneTimeVO>> oneTimeDetails = null;
		Collection<OneTimeVO> irpStatus = null;
	
		if (extraInfo != null && extraInfo.size() > 0) {
			Object onetimes = ((ArrayList<Object>) extraInfo).get(0);
			oneTimeDetails = (Map<String, Collection<OneTimeVO>>) onetimes;
			irpStatus = oneTimeDetails.get(IRP_STATUS);
			
			
		}
	
		Vector<Vector<Vector>> subReportDatas = new Vector<Vector<Vector>>();
		Vector<Vector> subReportOneDatas = new Vector<Vector>();
		 ArrayList subReportDataArray = (ArrayList)data;		
		for(MRAIrregularityVO reportVO:(Collection<MRAIrregularityVO>) subReportDataArray.get(0)){
			if(reportVO !=null){
				Collection<MRAIrregularityDetailsVO> detailsVO =
											new ArrayList<MRAIrregularityDetailsVO>();
				detailsVO=reportVO.getFlightDetails();
				for(MRAIrregularityDetailsVO irregularityDetailsVO:detailsVO ){
					Vector<Object> row = new Vector<Object>();
					
					if(irregularityDetailsVO !=null){

						
					//DSN Number
					if (irregularityDetailsVO.getDsn() != null ) {
						String dsn = irregularityDetailsVO.getDsn();
						
						row.add(dsn);
						
					} else {
						
						row.add(ReportConstants.EMPTY_STRING);
					}
					
					//Flight Number
					if(irregularityDetailsVO.getFlightNumber()!=null){
						row.add(irregularityDetailsVO.getFlightNumber());
					}else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					
					//Flight Date
					if(irregularityDetailsVO.getFlightDate()!=null){
						row.add(irregularityDetailsVO.getFlightDate().toDisplayDateOnlyFormat());
					}else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					
					//OffLoadedDate
					if(irregularityDetailsVO.getOffLoadedDate()!=null){
						row.add(irregularityDetailsVO.getOffLoadedDate().toDisplayDateOnlyFormat());
					}else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					
					//OffloadedStation
					if(irregularityDetailsVO.getOffloadedStation()!=null){
						row.add(irregularityDetailsVO.getOffloadedStation());
					}else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					
					//OffloadedWeight
					if(irregularityDetailsVO.getOffloadedWeight()!=0.0){
						row.add(irregularityDetailsVO.getOffloadedWeight());
					}else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					

					//RescheduledFlightNumber
					if(irregularityDetailsVO.getRescheduledFlightNumber()!=null){
						row.add(irregularityDetailsVO.getRescheduledFlightNumber());
					}else{
						row.add(ReportConstants.EMPTY_STRING);
					}					
					
					//RescheduledFlightDate
					if(irregularityDetailsVO.getRescheduledFlightDate()!=null){
						row.add(irregularityDetailsVO.getRescheduledFlightDate().toDisplayDateOnlyFormat());
					}else{
						row.add(ReportConstants.EMPTY_STRING);
					}
				
					subReportOneDatas.add(row);
					}

				}
			}
		}
		subReportDatas.add(subReportOneDatas);
		log.log(Log.FINE, "----------subReportOneDatas--------",
				subReportOneDatas);
		Vector<Vector> subReportTwoDatas = new Vector<Vector>();
		 ArrayList subReportTwoDataArray = (ArrayList)data;
		
		for(MRAIrregularityVO reportVO:(Collection<MRAIrregularityVO>) subReportTwoDataArray.get(0)){
			if(reportVO !=null){
				Collection<MRAIrregularityDetailsVO> detailsVO =
											new ArrayList<MRAIrregularityDetailsVO>();
				detailsVO=reportVO.getFlightDetails();
				for(MRAIrregularityDetailsVO irregularityDetailsVO:detailsVO ){
					Vector<Object> row = new Vector<Object>();

					if(irregularityDetailsVO !=null){
						
						//DSN Number
						if (irregularityDetailsVO.getDsn() != null ) {
						
							String dsn = irregularityDetailsVO.getDsn();
							row.add(dsn);
							
						} else {
							row.add(ReportConstants.EMPTY_STRING);
						}
						
						//Weight
						if(irregularityDetailsVO.getWeight()!=0){
							row.add(irregularityDetailsVO.getWeight());
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}
						
						//Route
						if(irregularityDetailsVO.getRoute()!=null){
							row.add(irregularityDetailsVO.getRoute());
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}
						
					 // FunctionPoint
						if (irregularityDetailsVO.getIrregularityStatus() != null
								&& irregularityDetailsVO.getIrregularityStatus().trim().length() > 0) {
							String status = irregularityDetailsVO.getIrregularityStatus();

							if (irpStatus != null && irpStatus.size() > 0) {
								for (OneTimeVO oneTimeVo : irpStatus) {
									if (oneTimeVo.getFieldValue().equals(status)) {
										row.add(oneTimeVo.getFieldDescription());
										break;
									}
								}
							}  else {
								row.add(BLANKSPACE);
							}

						} else {
							row.add(BLANKSPACE);
						}
						
						subReportTwoDatas.add(row);
					}

				}
			}
		}
		subReportDatas.add(subReportTwoDatas);
		log.log(Log.FINE, "----------subReportTwoDatas--------",
				subReportTwoDatas);
		log
				.log(Log.FINE, "----------subReportDataMain--------",
						subReportDatas);
		return subReportDatas;
	}

	
	
	

}
