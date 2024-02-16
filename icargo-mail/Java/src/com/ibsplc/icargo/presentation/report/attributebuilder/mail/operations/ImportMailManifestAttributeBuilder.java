/*
 * ImportMailManifestAttributeBuilder.java Created on APR17, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-3353
 *
 */
public class ImportMailManifestAttributeBuilder extends AttributeBuilderAdapter {

	private static final String CLASS_NAME = "ImportMailManifestAttributeBuilder";
	private Log log = LogFactory.getLogger(CLASS_NAME);
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
		columns.add("ULDNUM");
		columns.add("POU");
		columns.add("DSN");
		columns.add("OOE");
		columns.add("DOE");
		columns.add("CAT");
		columns.add("CLS");
		columns.add("SCLS");
		columns.add("YEAR");
		columns.add("ACPPCS");
		columns.add("ACPWGT");
		columns.add("NOB");
		columns.add("WGT");
		columns.add("AWB");
		columns.add("MBGID");
		columns.add("TNUB");
		columns.add("TWFU");
		return columns;
	}


	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the table data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<Vector>();

		Object dataRecords = ((ArrayList<Object>)data).get(0);
		MailManifestVO mailManifestVO = (MailManifestVO)dataRecords;
		log.log(Log.FINE, "\n\n mailManifestVO----in reports----->  ",
				mailManifestVO);
		Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestVO.getContainerDetails();	
		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
			if(containerDtlsVO.getDsnVOs()!=null  && containerDtlsVO.getDsnVOs().size()>0){
			Collection<DSNVO> dsnVOS = containerDtlsVO.getDsnVOs();
			log.log(Log.FINE, "\n\n dsnVOS----in reports----->  ", dsnVOS);
			for(DSNVO dsnVO:dsnVOS){

				if("Y".equals(dsnVO.getPltEnableFlag())){
					Collection<MailbagVO> mailbagVOS = dsnVO.getMailbags();
					if(mailbagVOS!=null && mailbagVOS.size()>0){
						for(MailbagVO mailbagVO:mailbagVOS){
							log.log(Log.FINE,
									"\n\n mailBagVO----in reports----->  ",
									mailbagVO);
							Vector<Object> row = new Vector<Object>();
							if("Y".equals(containerDtlsVO.getPaBuiltFlag())){
								String str1 = new StringBuilder().append(containerDtlsVO.getContainerNumber())
			 			       .append(MailConstantsVO.LABEL_SB).toString();
								row.add(str1);
							}else{
								row.add(containerDtlsVO.getContainerNumber());
							}				
							row.add(containerDtlsVO.getPou()!=null?containerDtlsVO.getPou():ReportConstants.EMPTY_STRING);
							row.add(mailbagVO.getDespatchSerialNumber()!=null?mailbagVO.getDespatchSerialNumber():ReportConstants.EMPTY_STRING);
							row.add(mailbagVO.getOoe()!=null?mailbagVO.getOoe():ReportConstants.EMPTY_STRING);
							row.add(mailbagVO.getDoe()!=null?mailbagVO.getDoe():ReportConstants.EMPTY_STRING);
							row.add(mailbagVO.getMailCategoryCode()!=null?mailbagVO.getMailCategoryCode():ReportConstants.EMPTY_STRING);
							row.add(mailbagVO.getMailClass()!=null?mailbagVO.getMailClass():ReportConstants.EMPTY_STRING);
							row.add(mailbagVO.getMailSubclass()!=null?mailbagVO.getMailSubclass():ReportConstants.EMPTY_STRING);
							/**
							 * For bug 83788
							 */
						    row.add(String.valueOf(mailbagVO.getYear()));

						    row.add(mailbagVO.getAcceptedBags());
						    row.add(mailbagVO.getAcceptedWeight().getRoundedDisplayValue());
						    if("Y".equals(mailbagVO.getArrivedFlag())){
								row.add("1");
//								Commented by RENO for BUG 14321.  Desc : Two decimal places need to be shown.
//								Float db = new Float(mailbagVO.getWeight());
//								row.add(db!=null?String.valueOf(db):ReportConstants.EMPTY_STRING);
								//row.add(mailbagVO.getWeight());
								//Added by A-7929 as part of ICRD-250940 starts
								Float fl = new Float(mailbagVO.getWeight().getRoundedDisplayValue());
								row.add(fl!=null?(String.valueOf(mailbagVO.getWeight().getRoundedDisplayValue())) :ReportConstants.EMPTY_STRING);
								//Added by A-7929 as part of ICRD-250940 starts ends   
						    }else{
							    row.add("0");
							    row.add("0.00");
							    }
						    row.add(mailbagVO.getAwbNumber());
							row.add(mailbagVO.getMailbagId()!=null?mailbagVO.getMailbagId():ReportConstants.EMPTY_STRING);
							
							row.add(String.valueOf(containerDtlsVO.getTotalBags()));
//							Commented by RENO  for BUG 14321.  Desc : Two decimal places need to be shown.
//							Float fl = new Float(containerDtlsVO.getTotalWeight());
//							row.add(fl!=null?String.valueOf(fl):ReportConstants.EMPTY_STRING);
							//row.add(containerDtlsVO.getTotalWeight());
							//Added by A-7929 as part of ICRD-250940 starts
							Float fl = new Float(String.valueOf(containerDtlsVO.getTotalWeight().getRoundedDisplayValue()));
							row.add(fl!=null?(String.valueOf(containerDtlsVO.getTotalWeight().getRoundedDisplayValue())):ReportConstants.EMPTY_STRING);
							//Added by A-7929 as part of ICRD-250940 starts ends	
							tableData.add(row);
							
						}
						}

					}else{
						log.log(Log.FINE, "\n\n dsnVO----in reports----->  ",
								dsnVO);
						Vector<Object> row = new Vector<Object>();
						if("Y".equals(containerDtlsVO.getPaBuiltFlag())){
							String str1 = new StringBuilder().append(containerDtlsVO.getContainerNumber())
		 			       .append(MailConstantsVO.LABEL_SB).toString();
							row.add(str1);
						}else{
							row.add(containerDtlsVO.getContainerNumber());
						}
					row.add(containerDtlsVO.getPou()!=null?containerDtlsVO.getPou():ReportConstants.EMPTY_STRING);
					row.add(dsnVO.getDsn()!=null?dsnVO.getDsn():ReportConstants.EMPTY_STRING);
					row.add(dsnVO.getOriginExchangeOffice()!=null?dsnVO.getOriginExchangeOffice():ReportConstants.EMPTY_STRING);
					row.add(dsnVO.getDestinationExchangeOffice()!=null?dsnVO.getDestinationExchangeOffice():ReportConstants.EMPTY_STRING);
					row.add(dsnVO.getMailCategoryCode()!=null?dsnVO.getMailCategoryCode():ReportConstants.EMPTY_STRING);
					row.add(dsnVO.getMailClass()!=null?dsnVO.getMailClass():ReportConstants.EMPTY_STRING);
					row.add(dsnVO.getMailSubclass()!=null?dsnVO.getMailSubclass():ReportConstants.EMPTY_STRING);
					
					/**
					 * Changed for bug 83788 starts
					 */

					row.add(String.valueOf(dsnVO.getYear()));
					/**
					 * Changed for bug 83788 ends
					 */
					row.add(String.valueOf(dsnVO.getBags())!=null?String.valueOf(dsnVO.getBags()):ReportConstants.EMPTY_STRING);
//					Commented by RENO  for BUG 14321. Desc : Two decimal places need to be shown.
//					row.add(String.valueOf(dsnVO.getWeight())!=null?String.valueOf(dsnVO.getWeight()):ReportConstants.EMPTY_STRING);
					//row.add(dsnVO.getWeight());
					//Added by A-7929 as part of ICRD-250940 starts
					Float fl = new Float(dsnVO.getWeight().getRoundedDisplayValue());
					row.add(fl!=null?(String.valueOf(dsnVO.getWeight().getRoundedDisplayValue())) :ReportConstants.EMPTY_STRING);
					//Added by A-7929 as part of ICRD-250940 ends
					
					row.add(ReportConstants.EMPTY_STRING);
					Integer int1 =  Integer.valueOf(containerDtlsVO.getTotalBags());
					row.add(String.valueOf(int1));
// 					Commented by RENO  for BUG 14321.  Desc : Two decimal places need to be shown.
//					Float fl = new Float(containerDtlsVO.getTotalWeight());
//					row.add(fl!=null?String.valueOf(fl):ReportConstants.EMPTY_STRING);
					//row.add(containerDtlsVO.getTotalWeight());
					//Added by A-7929 as part of ICRD-250940 starts
					Float f2 = new Float(String.valueOf(containerDtlsVO.getTotalWeight().getRoundedDisplayValue()));
					row.add(f2!=null?(String.valueOf(containerDtlsVO.getTotalWeight().getRoundedDisplayValue())):ReportConstants.EMPTY_STRING);
					//Added by A-7929 as part of ICRD-250940 starts ends	
					tableData.add(row);
					}
				}
			}else{
				Vector<Object> row = new Vector<Object>();
				if("Y".equals(containerDtlsVO.getPaBuiltFlag())){
				
					String str = new StringBuilder().append(containerDtlsVO.getContainerNumber())
 			       .append(MailConstantsVO.LABEL_SB).toString();
					row.add(str);
					row.add(containerDtlsVO.getPou()!=null?containerDtlsVO.getPou():ReportConstants.EMPTY_STRING);
					tableData.add(row);
				}else{
					row.add(containerDtlsVO.getContainerNumber());
					row.add(containerDtlsVO.getPou()!=null?containerDtlsVO.getPou():ReportConstants.EMPTY_STRING);
					tableData.add(row);
				}
			}

			}
		log.log(Log.FINE, "Exiting Attribute ---->>", tableData);
		return tableData;
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
	public Vector<Object> getReportParameters(
			Collection parameters, Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();

		Object dataParameters = ((ArrayList<Object>)parameters).get(0);
		MailManifestVO mailManifestVO =
				(MailManifestVO)dataParameters;

		reportParameters.add(mailManifestVO.getFlightCarrierCode()!=null?mailManifestVO.getFlightCarrierCode():ReportConstants.EMPTY_STRING);
		reportParameters.add(mailManifestVO.getFlightNumber()!=null?mailManifestVO.getFlightNumber():ReportConstants.EMPTY_STRING);
		reportParameters.add(mailManifestVO.getDepDate()!=null?mailManifestVO.getDepDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase():ReportConstants.EMPTY_STRING);
		reportParameters.add(mailManifestVO.getDepPort()!=null?mailManifestVO.getDepPort():ReportConstants.EMPTY_STRING);
		reportParameters.add(mailManifestVO.getFlightRoute()!=null?mailManifestVO.getFlightRoute():ReportConstants.EMPTY_STRING);
		reportParameters.add(String.valueOf(mailManifestVO.getTotalbags()));
		//String weight = new StringBuilder().append(String.valueOf(mailManifestVO.getTotalWeight())).append("0").toString();
		//reportParameters.add(weight);
		//Added by A-7929 as part of ICRD-250940 starts
		Float fl = new Float(String.valueOf(mailManifestVO.getTotalWeight().getRoundedDisplayValue()));
		reportParameters.add(fl!=null?(String.valueOf(mailManifestVO.getTotalWeight().getRoundedDisplayValue())):ReportConstants.EMPTY_STRING);
		//Added by A-7929 as part of ICRD-250940 ends
		log.log(Log.FINE, "parameters..", reportParameters);
		return reportParameters;
	}
}
