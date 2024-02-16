/*
 * DestCategoryManifestAttributeBuilder.java Created on Jan 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
//import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class DestCategoryManifestAttributeBuilder extends AttributeBuilderAdapter {

	private static final String MAIL_CATEGORY = "mailtracking.defaults.mailcategory";
	private Log log = LogFactory.getLogger("Mailbag Manifest");	
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
		columns.add("MTKDST");
		columns.add("MTKCAT");
		columns.add("ORGPOA");
		columns.add("DSTPOA");
		columns.add("TOTPCS");
		columns.add("TOTWGT");
		columns.add("TOTCNTPCS");
		columns.add("TOTCNTWGT");
		return columns;
	}


	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<Vector>();
		Vector<Object> row = new Vector<Object>();
		Object dataRecords = ((ArrayList<Object>)data).get(0);
		MailManifestVO mailManifestVO = (MailManifestVO)dataRecords;
		Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestVO.getContainerDetails();
		String str="";
		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
			
			if(containerDtlsVO.getMailSummaryVOs()!=null && containerDtlsVO.getMailSummaryVOs().size()>0){
			Collection<MailSummaryVO> mailSummaryVOs = containerDtlsVO.getMailSummaryVOs();
			for(MailSummaryVO mailSummaryVO:mailSummaryVOs){
				row = new Vector<Object>();
				if("Y".equals(containerDtlsVO.getPaBuiltFlag())){
					str = new StringBuilder().append(containerDtlsVO.getContainerNumber())
 			       .append(MailConstantsVO.LABEL_SB).toString();
					row.add(str);
				}else{
					row.add(containerDtlsVO.getContainerNumber());
				}				
				row.add(containerDtlsVO.getPou());
				row.add(mailSummaryVO.getDestination());
				
				if(extraInfo!=null){
				Object oneTimeData = ((ArrayList<Object>)extraInfo).get(0);
				Map<String,Collection<OneTimeVO>> oneTimeDetails = 
					(Map<String,Collection<OneTimeVO>>)oneTimeData;
			
				Collection<OneTimeVO> frequencyonetime =
					(Collection<OneTimeVO>)oneTimeDetails.get(MAIL_CATEGORY);
				String oprdesc = formatOneTime(mailSummaryVO.getMailCategory(),frequencyonetime);
				row.add(oprdesc);
				}else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				
				row.add(mailSummaryVO.getOriginPA());
				row.add(mailSummaryVO.getDestinationPA());
				row.add(String.valueOf(mailSummaryVO.getBagCount()));
				//Float fl = new Float(mailSummaryVO.getTotalWeight());
				Float fl = new Float(mailSummaryVO.getTotalWeight().getRoundedDisplayValue());//added by A-7371
				//row.add(fl!=null?(TextFormatter.formatDouble(mailSummaryVO.getTotalWeight(),2)):ReportConstants.EMPTY_STRING);
				row.add(fl!=null?(String.valueOf(mailSummaryVO.getTotalWeight().getRoundedDisplayValue())):ReportConstants.EMPTY_STRING);//added by A-7371
				row.add(String.valueOf(containerDtlsVO.getTotalBags()));
				log.log(Log.FINE, "\n\n mailManifestVO----in reports----->  ",
						//TextFormatter.formatDouble(containerDtlsVO.getTotalWeight(),2));
						String.valueOf(containerDtlsVO.getTotalWeight().getRoundedDisplayValue()));//added by A-7371
				//fl = new Float(containerDtlsVO.getTotalWeight());
				fl = new Float(containerDtlsVO.getTotalWeight().getRoundedDisplayValue());//added by A-7371
				//row.add(fl!=null?(TextFormatter.formatDouble(containerDtlsVO.getTotalWeight(),2)):ReportConstants.EMPTY_STRING);
				row.add(fl!=null?(String.valueOf(containerDtlsVO.getTotalWeight().getRoundedDisplayValue())):ReportConstants.EMPTY_STRING);//added by A-7371
				tableData.add(row);
			}
			}else{
				 row = new Vector<Object>();
				if("Y".equals(containerDtlsVO.getPaBuiltFlag())){
					str = new StringBuilder().append(containerDtlsVO.getContainerNumber())
 			       .append(MailConstantsVO.LABEL_SB).toString();
					row.add(str);
					row.add(containerDtlsVO.getPou());
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add("0");
					row.add("0.00");
					tableData.add(row);
				}else{
					row.add(containerDtlsVO.getContainerNumber());
					row.add(containerDtlsVO.getPou());
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add("0");
					row.add("0.00");
					tableData.add(row);
				}
			}
		}
	
		return tableData;
	}
	
	
	public static String formatOneTime(Object value, Collection oneTime) {	
		//System.out.println("Inside formatOneTime!!!!!");
      String code=(String)value;
      String description="";
      if("M".equals(code)){
    	  description = "MILITARY";
      }else{
	      Collection<OneTimeVO> oneTimeCollection=(Collection<OneTimeVO>)oneTime;
	      if(oneTimeCollection!=null){
	    	  if(oneTimeCollection.size()>0){
	    		  for(OneTimeVO oneTimeVo:oneTimeCollection){
	    			  if(oneTimeVo.getFieldValue().equals(code)){
	    				  description=oneTimeVo.getFieldDescription();
	    			  }
	    		  }
	    	  }
	      }
      }
      
      return description;
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
		//System.out.println("Inside AttributeBuilder!!!!!!");
		Vector<Object> reportParameters = new Vector<Object>();		
		
		Object dataParameters = ((ArrayList<Object>)parameters).get(0);
		MailManifestVO mailManifestVO = 
				(MailManifestVO)dataParameters;
		
		reportParameters.add(mailManifestVO.getFlightCarrierCode());
		reportParameters.add(mailManifestVO.getFlightNumber());
		reportParameters.add(mailManifestVO.getDepDate().toDisplayFormat("dd-MMM-yyyy"));
		reportParameters.add(String.valueOf(mailManifestVO.getTotalbags()));
		log.log(Log.FINE, "\n\n mailManifestVO----in reports----->  ",
				//TextFormatter.formatDouble(mailManifestVO.getTotalWeight(),2));
				String.valueOf(mailManifestVO.getTotalWeight().getRoundedDisplayValue()));
		//reportParameters.add(String.valueOf(mailManifestVO.getTotalWeight())!=null?(TextFormatter.formatDouble(mailManifestVO.getTotalWeight(),2)):ReportConstants.EMPTY_STRING);
		reportParameters.add(String.valueOf(mailManifestVO.getTotalWeight().getRoundedDisplayValue())!=null?(String.valueOf(mailManifestVO.getTotalWeight().getRoundedDisplayValue())):ReportConstants.EMPTY_STRING);//added by A-7371
		
		return reportParameters;
	}
	
	
	/**
	 * Method to obtain the sub-report column names. There can be multiple
	 * sub-reports in a report. Each element in the outer Vector corresponds to
	 * one sub-report. Each element in the inner Vector corresponds to a column
	 * (field) in the sub-report. The order in which the data is returned should
	 * match the order in which the fields are laid out in the report
	 * @return Vector<Vector<String>> the columns in the report
	 */
	@Override
	public Vector<Vector<String>> getSubReportColumns() {

		Vector<Vector<String>> subReportColumns = new Vector<Vector<String>>();
		
		Vector<String> columnsOne = new Vector<String>();
		columnsOne.add("MTKDST");
		columnsOne.add("MTKSUBCLS");
		columnsOne.add("TOTBAG");
		columnsOne.add("TOTWGT");
		subReportColumns.add(columnsOne);
		
		return subReportColumns;
	}
	
	/**
	 * Method to obtain the sub-report data. There can be can be multiple
	 * sub-reports in a report. Each element in the outer-most Vector
	 * corresponds to one sub-report. Each element in the inner Vector
	 * corresponds to a row in the sub-report. The order in which the data is
	 * returned should match the order in which the fields are laid out in the
	 * report
	 * @param data the data to be printed in the sub-report
	 * @param extraInfo the data required for formatting the data
	 * @return Vector<Vector<Vector>> the data in the report
	 */

	@Override
	public Vector<Vector<Vector>> getSubReportData(Collection data, Collection extraInfo) {

		Vector<Vector<Vector>> subReportData = new Vector<Vector<Vector>>();
		ArrayList dataCollns = (ArrayList)data;
			
		if(dataCollns!=null){
		Collection<MailSummaryVO> summaryManifestVOs 
				=(ArrayList<MailSummaryVO>) dataCollns.get(0);
		Vector<Vector> subReportDataOne = new Vector<Vector>();
		
		if (summaryManifestVOs != null && summaryManifestVOs.size() > 0) {
		  for(MailSummaryVO mailSummaryVO:summaryManifestVOs){
				Vector<Object> row = new Vector<Object>();
				row.add(mailSummaryVO.getDestination());
				
				if(extraInfo!=null){
				Object oneTimeData = ((ArrayList<Object>)extraInfo).get(0);
				Map<String,Collection<OneTimeVO>> oneTimeDetails = 
					(Map<String,Collection<OneTimeVO>>)oneTimeData;
				Collection<OneTimeVO> categoryonetime = (Collection<OneTimeVO>)oneTimeDetails.get(MAIL_CATEGORY);
				String catdesc = formatOneTime(mailSummaryVO.getMailCategory(),categoryonetime);
				row.add(catdesc);
				}
				
				//row.add(mailSummaryVO.getMailCategory());
				row.add(String.valueOf(mailSummaryVO.getBagCount()));
				log.log(Log.FINE, "\n\n mailManifestVO----in reports----->  ",
						//TextFormatter.formatDouble(mailSummaryVO.getTotalWeight(),2));
						String.valueOf(mailSummaryVO.getTotalWeight().getRoundedDisplayValue()));//added by A-7371
				//row.add(TextFormatter.formatDouble(mailSummaryVO.getTotalWeight(),2));
				row.add(String.valueOf(mailSummaryVO.getTotalWeight().getRoundedDisplayValue()));//added by A-7371
				subReportDataOne.add(row);
		  }
		}
	
		
		
		subReportData.add(subReportDataOne);	
		}
		return subReportData;
	}
	
	
}

