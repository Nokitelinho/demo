/*
 * AWBManifestAttributeBuilder.java Created on Jan 20, 2007
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
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
//import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class AWBManifestAttributeBuilder extends AttributeBuilderAdapter {

	private final static String MAIL_STATUS = "mailtracking.defaults.mailstatus";
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
		columns.add("SHPPFX");
		columns.add("DOCNUM");
		columns.add("MTKORG");
		columns.add("MTKDST");
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
		Vector<Object> row = null;
		Object dataRecords = ((ArrayList<Object>)data).get(0);
		MailManifestVO mailManifestVO = (MailManifestVO)dataRecords;
		log.log(Log.FINE, "\n\n mailManifestVO----in reports----->  ",
				mailManifestVO);
		Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestVO.getContainerDetails();
		String str="";
		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
			
			if(containerDtlsVO.getDsnVOs()!=null && containerDtlsVO.getDsnVOs().size()>0){
			Collection<DSNVO> dsnVOs = containerDtlsVO.getDsnVOs();
			for(DSNVO dsnVO:dsnVOs){
				row = new Vector<Object>();
				if("Y".equals(containerDtlsVO.getPaBuiltFlag())){
					str = new StringBuilder().append(containerDtlsVO.getContainerNumber())
 			       .append(MailConstantsVO.LABEL_SB).toString();
					row.add(str);
				}else{
					row.add(containerDtlsVO.getContainerNumber());
				}
				row.add(containerDtlsVO.getPou());
				row.add(dsnVO.getDocumentOwnerCode());
				row.add(dsnVO.getMasterDocumentNumber());
				row.add(dsnVO.getOrigin());
				row.add(dsnVO.getDestination());
				row.add(String.valueOf(dsnVO.getBags()));
				//row.add(TextFormatter.formatDouble(dsnVO.getWeight(),2));
				row.add(String.valueOf(dsnVO.getWeight().getRoundedDisplayValue()));//added by A-7371,modified by A-8353
				row.add(String.valueOf(containerDtlsVO.getTotalBags()));
				//row.add(TextFormatter.formatDouble(containerDtlsVO.getTotalWeight(),2));
				row.add(String.valueOf(containerDtlsVO.getTotalWeight().getRoundedDisplayValue()));//added by A-7371,modified by A-8353
				tableData.add(row);
			}
			}else{
				row = new Vector<Object>();
				if("Y".equals(containerDtlsVO.getPaBuiltFlag())){
					str = new StringBuilder().append(containerDtlsVO.getContainerNumber())
 			       .append(MailConstantsVO.LABEL_SB).toString();
					row.add(str);
					
				}else{
					row.add(containerDtlsVO.getContainerNumber());
					  
				}
				row.add(containerDtlsVO.getPou());
				tableData.add(row);
			}
		}
	
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
		//System.out.println("Inside AttributeBuilder!!!!!!");
		Vector<Object> reportParameters = new Vector<Object>();		
		
		Object dataParameters = ((ArrayList<Object>)parameters).get(0);
		MailManifestVO mailManifestVO = 
				(MailManifestVO)dataParameters;
		
		reportParameters.add(mailManifestVO.getFlightCarrierCode());
		reportParameters.add(mailManifestVO.getFlightNumber());
		reportParameters.add(mailManifestVO.getDepDate().toDisplayFormat("dd-MMM-yyyy"));
		reportParameters.add(String.valueOf(mailManifestVO.getTotalbags()));
		//reportParameters.add((TextFormatter.formatDouble(mailManifestVO.getTotalWeight(),2)));
		reportParameters.add((String.valueOf(mailManifestVO.getTotalWeight().getRoundedDisplayValue())));//added by A-7371,modified by A-8353
	
		return reportParameters;
	}
}

