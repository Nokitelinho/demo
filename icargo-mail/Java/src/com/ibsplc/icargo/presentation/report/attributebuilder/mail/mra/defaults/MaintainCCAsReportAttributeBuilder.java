/*
 * MaintainCCAsReportAttributeBuilder.java.java Created on SEP- 15-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 * @author A-3447
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.defaults;

/**
 * @author A-3447
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447
 *
 */
public class MaintainCCAsReportAttributeBuilder extends AttributeBuilderAdapter {

	//	----------------------------------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Blank Space
	 */
	public static final String BLANKSPACE = "";

	/**
	 * Zero
	 */
	public static final String ZERO = "0.0";

	/**
	 * log defined
	 */
	private Log log = LogFactory.getLogger("MaintainCCAsReportAttributeBuilder");


	//----------------------------------------------------------------------------------------------------------------------------------------------------------

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
		columns.add("CONDOCNO");
		columns.add("CCASTA");
		columns.add("ISSDAT");
		columns.add("CCATYP");
		columns.add("BLGPRDFRM");
		columns.add("BLGPRDTO");
		columns.add("GPACOD");
		columns.add("REVGPACOD");
		columns.add("CURCOD");
		columns.add("REVCURCOD");
		columns.add("WGT");
		columns.add("REVWGT");
		columns.add("WGTCHG");
		columns.add("REVWGTCHG");
		//Added as part of ICRD-136662 starts
		columns.add("OTHCHG");
		columns.add("REVOTHCHG");
		//Added as part of ICRD-136662 ends
		columns.add("GPAIND");
		columns.add("WGTIND");
		columns.add("CURRIND");
		columns.add("CHGIND");
		columns.add("RMK");
		columns.add("TAX");
		columns.add("REVTAX");
		columns.add("NETVAL"); //Added by A-8149 as part of ICRD-267530  
		columns.add("CCANUM");
		columns.add("DSN");
		columns.add("OVRRND"); //Added by A-8164 as part of ICRD-267530   
		//Added as part of IASCB-860
		columns.add("MCARSNCOD");
		return columns;
	}

	/**@author A-3447
	 *  Method to construct the  SubReport  column names. The column names corresponds
	 * to the column names of the view used while laying out the report. The
	 * order of the column names should match the order in which the database
	 * fields are laid out in the report.The Sub Report Columns Corresponds to The Flight Details
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<String> the column names
	 */



	/**
	 * method to get Columns for sub report
	 *
	 * @return subReportColumns1
	 *
	 */
	@Override
	public Vector<Vector<String>> getSubReportColumns() {

		Vector<Vector<String>> subReportColumns1 = new Vector<Vector<String>>();
		Vector<String> columns1 = new Vector<String>();

		columns1.add("FLTDAT");
		columns1.add("FLTCARCOD");
		columns1.add("FLTNUM");
		columns1.add("POL");
		columns1.add("POU");
		subReportColumns1.add(columns1);
		log.log(Log.FINE, "SubReportColumns-->>", columns1);
		return subReportColumns1;
	}




	/**@author A-3447
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report	 *
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */



	@Override
	public Vector<Vector<Vector>> getSubReportData(Collection data,
			Collection extraInfo) {

		Vector<Vector<Vector>> subReportData = new Vector<Vector<Vector>>();
		ArrayList dataCollns = (ArrayList) data;

		Collection<DSNRoutingVO> listVOs = (ArrayList<DSNRoutingVO>) dataCollns
		.get(0);
		Vector<Object> row = new Vector<Object>();
		Vector<Vector> subReportDataOne = new Vector<Vector>();

		/**
		 * Iterating DsnRoutingVo for Getting FlightDetails
		 */
		for (DSNRoutingVO listVO : listVOs) {
			row = new Vector<Object>();
			if (listVO.getDepartureDate() != null) {
				row.add(String.valueOf(listVO.getDepartureDate()
						.toDisplayFormat()));

			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getFlightCarrierCode() != null) {
				row.add(listVO.getFlightCarrierCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getFlightNumber() != null) {
				row.add(listVO.getFlightNumber());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getPol() != null) {
				row.add(listVO.getPol());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getPou() != null) {
				row.add(listVO.getPou());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			subReportDataOne.add(row);
			log.log(Log.INFO, " \n sUB rEPORT dAtA--", listVO);
		}
		subReportData.add(subReportDataOne);
		return subReportData;
	}





	/**
	 * Method to construct the report parameters. The report parameters
	 * corresponds to the parameter fields in the report. The order of the
	 * parameters should match the order in which the parameter fields are laid
	 * out in the report
	 *
	 * @param parameters
	 * *            the parameter data
	 * @param extraInfo
	 *            information required for formatting the parameters
	 * @return Vector the report parameters
	 */

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilder#getReportParameters(java.util.Collection,
	 *      java.util.Collection)
	 */
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		//Added as part of IASCB-860 starts
	
		StringBuilder ReasonDesc=new StringBuilder("");
		String[] reasncode=null;
		Collection<CRAParameterVO> cRAParameterVOs= (Collection<CRAParameterVO>) ((ArrayList)extraInfo).get(5);
		//Added as part of IASCB-860 ends
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		Object dataParameterss = ((ArrayList<Object>) parameters).get(1);
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection<OneTimeVO> ccaType = (Collection<OneTimeVO>) extraInfor;
		Object extraInform = ((ArrayList<OneTimeVO>) extraInfo).get(1);
		Collection<OneTimeVO> ccaStatus = (Collection<OneTimeVO>) extraInform;
		Collection <OneTimeVO> mailCategory=(Collection <OneTimeVO>) ((ArrayList<Object>)extraInfo).get(2);
		Collection <OneTimeVO> mailSubClass=(Collection <OneTimeVO>) ((ArrayList<Object>)extraInfo).get(3);

		MaintainCCAFilterVO maintainCCAFilterVO = (MaintainCCAFilterVO) dataParameters;
		CCAdetailsVO cCAdetailsVO = (CCAdetailsVO) dataParameterss;
		log.log(Log.INFO, "maintainCCAFilterVO--->>", maintainCCAFilterVO);
		//Added as part of IASCB-860 starts
		if(cCAdetailsVO.getMcaReasonCodes()!=null)
		if(cCAdetailsVO.getMcaReasonCodes().contains(",")){
			
			reasncode=cCAdetailsVO.getMcaReasonCodes().split(",");
		}
		for(CRAParameterVO cRAParameterVO:cRAParameterVOs){
	if(cCAdetailsVO.getMcaReasonCodes()!=null && cCAdetailsVO.getMcaReasonCodes().trim().length()>0){
		if(cCAdetailsVO.getMcaReasonCodes().contains(",")){
			for(int i=0;i<reasncode.length;i++){
		
			if(reasncode[i].equals(cRAParameterVO.getParameterCode())){
			ReasonDesc=ReasonDesc.append(cRAParameterVO.getParameterCode()+":"+cRAParameterVO.getParameterDescription()).append("\r\n\n");
			}
		}
		}
	else{
		if(cCAdetailsVO.getMcaReasonCodes().contains(cRAParameterVO.getParameterCode())){
			
			ReasonDesc=ReasonDesc.append(cCAdetailsVO.getMcaReasonCodes()+":"+cRAParameterVO.getParameterDescription()).append("\r\n");
		
		}
	}}
	}
		//Added as part of IASCB-860 ends
	
		if (maintainCCAFilterVO.getCcaReferenceNumber() != null) {
			reportParameters.add(maintainCCAFilterVO.getCcaReferenceNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (maintainCCAFilterVO.getDsnNumber() != null) {
			reportParameters.add(maintainCCAFilterVO.getDsnNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (cCAdetailsVO.getCsgDocumentNumber() != null) {
			reportParameters.add(cCAdetailsVO.getCsgDocumentNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getDsnDate() != null) {
			reportParameters.add(cCAdetailsVO.getDsnDate());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getCcaStatus() != null) {
			for (OneTimeVO oneTimeVO : ccaStatus) {
				if (cCAdetailsVO.getCcaStatus().equals(
						oneTimeVO.getFieldValue())) {
					cCAdetailsVO.setCcaStatus(oneTimeVO
							.getFieldDescription());
				}
			}
			reportParameters.add(cCAdetailsVO.getCcaStatus());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		//		if (maintainCCAFilterVO.getIssuedBy() != null) {
		//
		//			for (OneTimeVO oneTimeVO : issueParty) {
		//				if (maintainCCAFilterVO.getIssuedBy().equals(
		//						oneTimeVO.getFieldValue())) {
		//					log.log(Log.FINE, "issueParty---" + oneTimeVO.getFieldDescription());
		//							maintainCCAFilterVO.setIssuedBy(oneTimeVO
		//							.getFieldDescription());
		//
		//
		//				}
		//			}
		//			reportParameters.add(maintainCCAFilterVO.getIssuedBy());
		//		} else {
		//			reportParameters.add(ReportConstants.EMPTY_STRING);
		//		}
		//
		//		if (maintainCCAFilterVO.getPartyCode() != null) {
		//			reportParameters.add(maintainCCAFilterVO.getPartyCode());
		//		} else {
		//			reportParameters.add(ReportConstants.EMPTY_STRING);
		//		}
		//		if (maintainCCAFilterVO.getPartyLocation() != null) {
		//			reportParameters.add(maintainCCAFilterVO.getPartyLocation());
		//		} else {
		//			reportParameters.add(ReportConstants.EMPTY_STRING);
		//		}
		/**
		 * Report Filters Ends
		 */

		/*
		 * CCA Details Starts
		 */
		if (cCAdetailsVO.getIssueDate() != null) {
			reportParameters.add(cCAdetailsVO.getIssueDate());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getCcaType() != null) {
			for (OneTimeVO oneTimeVO : ccaType) {
				if (cCAdetailsVO.getCcaType().equals(oneTimeVO.getFieldValue())) {
					log.log(Log.FINE, "ccaType---", oneTimeVO.getFieldDescription());
					cCAdetailsVO.setCcaType(oneTimeVO.getFieldDescription());

				}
			}
			reportParameters.add(cCAdetailsVO.getCcaType());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getBillingPeriodFrom() != null) {
			reportParameters.add(cCAdetailsVO.getBillingPeriodFrom());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getBillingPeriodTo() != null) {
			reportParameters.add(cCAdetailsVO.getBillingPeriodTo());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		/**
		 * CCA Details Ends
		 */

		/*
		 * Dsn details Starts
		 */

		if (cCAdetailsVO.getOrigin() != null) {
			reportParameters.add(cCAdetailsVO.getOrigin());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getDestination() != null) {
			reportParameters.add(cCAdetailsVO.getDestination());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (cCAdetailsVO.getCategory() != null) {
			for (OneTimeVO oneTimeVO : mailCategory) {
				if (cCAdetailsVO.getCategory().equals(
						oneTimeVO.getFieldValue())) {
					cCAdetailsVO.setCategory(oneTimeVO
							.getFieldDescription());
				}
			}
			reportParameters.add(cCAdetailsVO.getCategory());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}


		if (cCAdetailsVO.getSubClass() != null) {
			for (OneTimeVO oneTimeVO : mailSubClass) {
				if (cCAdetailsVO.getSubClass().equals(
						oneTimeVO.getFieldValue())) {
					cCAdetailsVO.setSubClass(oneTimeVO
							.getFieldDescription());
				}
			}
			reportParameters.add(cCAdetailsVO.getSubClass());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		//Added as part of IASCB-860
        if(cCAdetailsVO.getMcaReasonCodes()!=null)
        {
       	 reportParameters.add(ReasonDesc.toString());
        }
        else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
      //Added as part of IASCB-860
		


		// * * Dsn details Ends

		/**
		 * I)Revision---->> Charge Details
		 */

		if (cCAdetailsVO.getGrossWeight() > 0) {
			reportParameters.add(String.valueOf(cCAdetailsVO.getGrossWeight()));

		} else {
			reportParameters.add(ZERO);
		}

		if (cCAdetailsVO.getRevGrossWeight() > 0) {
			reportParameters.add(String.valueOf(cCAdetailsVO
					.getRevGrossWeight()));

		} else {
			reportParameters.add(ZERO);
		}

		if (cCAdetailsVO.getRevChgGrossWeight() !=null) {
			double revChg = cCAdetailsVO.getRevChgGrossWeight().getAmount();

			reportParameters.add(String.valueOf(revChg));

		} else {
			reportParameters.add(ZERO);
		}

		if (cCAdetailsVO.getChgGrossWeight()!=null) {
			double chgGrossWeight= cCAdetailsVO.getChgGrossWeight().getAmount();
			reportParameters.add(String.valueOf(chgGrossWeight));

		} else {
			reportParameters.add(ZERO);
		}
		//Added as part of ICRD-136662 starts
		if (cCAdetailsVO.getOtherRevChgGrossWgt() !=null) {
			double revOtherChg = cCAdetailsVO.getOtherRevChgGrossWgt().getAmount();
			reportParameters.add(String.valueOf(revOtherChg));
		} else {
			reportParameters.add(ZERO);
		}
		if (cCAdetailsVO.getOtherChgGrossWgt()!=null) {
			double chgOtherGrossWeight= cCAdetailsVO.getOtherChgGrossWgt().getAmount();
			reportParameters.add(String.valueOf(chgOtherGrossWeight));
		} else {
			reportParameters.add(ZERO);
		}
		//Added as part of ICRD-136662 ends
		if (cCAdetailsVO.getRevDueArl() !=null) {
			double revDueArl= cCAdetailsVO.getRevDueArl().getAmount();
			reportParameters.add(String.valueOf(revDueArl));

		} else {
			reportParameters.add(ZERO);
		}

		if (cCAdetailsVO.getDueArl() !=null) {
			double dueArl= cCAdetailsVO.getDueArl().getAmount();
			reportParameters.add(String.valueOf(dueArl));

		} else {
			reportParameters.add(ZERO);
		}

		if (cCAdetailsVO.getRevDuePostDbt() > 0) {
			reportParameters.add(String
					.valueOf(cCAdetailsVO.getRevDuePostDbt()));

		} else {
			reportParameters.add(ZERO);
		}

		if (cCAdetailsVO.getDuePostDbt() != null) {
			double duePostDbt= cCAdetailsVO.getDuePostDbt().getAmount();
			reportParameters.add(String.valueOf(duePostDbt));

		} else {
			reportParameters.add(ZERO);
		}

		// 2)Revision------>DSN Details
		if (cCAdetailsVO.getRevOrgCode() != null) {
			reportParameters.add(cCAdetailsVO.getRevOrgCode());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (cCAdetailsVO.getOriginCode() != null) {
			reportParameters.add(cCAdetailsVO.getOriginCode());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getRevDStCode() != null) {
			reportParameters.add(cCAdetailsVO.getRevDStCode());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getDestnCode() != null) {
			reportParameters.add(cCAdetailsVO.getDestnCode());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		// 3)Revision------>GPA Details
		if (cCAdetailsVO.getRevGpaCode() != null) {
			reportParameters.add(cCAdetailsVO.getRevGpaCode());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getGpaCode() != null) {
			reportParameters.add(cCAdetailsVO.getGpaCode());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getRevGpaName() != null) {
			reportParameters.add(cCAdetailsVO.getRevGpaName());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getGpaName() != null) {
			reportParameters.add(cCAdetailsVO.getGpaName());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		// 4)Remarks------>
		if (cCAdetailsVO.getCcaRemark() != null) {
			reportParameters.add(cCAdetailsVO.getCcaRemark());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		// 5)Resons------>
		if (cCAdetailsVO.getGrossWeightChangeInd() != null) {
			reportParameters.add(cCAdetailsVO.getGrossWeightChangeInd());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getWeightChargeChangeInd() != null) {
			reportParameters.add(cCAdetailsVO.getWeightChargeChangeInd());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getGpaChangeInd() != null) {
			reportParameters.add(cCAdetailsVO.getGpaChangeInd());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getDoeChangeInd() != null) {
			reportParameters.add(cCAdetailsVO.getDoeChangeInd());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cCAdetailsVO.getWriteOffInd() != null) {
			reportParameters.add(cCAdetailsVO.getWriteOffInd());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (cCAdetailsVO.getContCurCode() != null) {
			reportParameters.add(cCAdetailsVO.getContCurCode());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (cCAdetailsVO.getRevContCurCode() != null) {
			reportParameters.add(cCAdetailsVO.getRevContCurCode());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (cCAdetailsVO.getCurrChangeInd() != null) {
			reportParameters.add(cCAdetailsVO.getCurrChangeInd());

		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		log.log(Log.FINE, "reportParameters is ------------>>--->",
				reportParameters);
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

	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<Vector>();
		if (data != null) {
			int count  =0;
			Object dataRecords = ((ArrayList<Object>)data).get(0);
			Object extraInfoObj = ((ArrayList<Object>)extraInfo).get(0);
			Collection <OneTimeVO>oneTimevoS =(Collection <OneTimeVO>) ((ArrayList<Object>)extraInfo).get(0);
			CCAdetailsVO ccAdetailsVO = (CCAdetailsVO)dataRecords;
			Vector<Object> row = new Vector<Object>();
			count = count +1;
			if(ccAdetailsVO.getCsgDocumentNumber()!=null){
				row.add(ccAdetailsVO.getCsgDocumentNumber());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getCcaStatus()!=null){
				row.add(ccAdetailsVO.getCcaStatus());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getIssueDate()!=null){
				row.add(ccAdetailsVO.getIssueDate());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getCcaType()!=null){
				row.add(ccAdetailsVO.getCcaType());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getBillingPeriodFrom()!=null){
				row.add(ccAdetailsVO.getBillingPeriodFrom());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getBillingPeriodTo()!=null){
				row.add(ccAdetailsVO.getBillingPeriodTo());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getGpaCode()!=null){
				row.add(ccAdetailsVO.getGpaCode());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getRevGpaCode()!=null){
				row.add(ccAdetailsVO.getRevGpaCode());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getContCurCode()!=null){
				row.add(ccAdetailsVO.getContCurCode());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getRevContCurCode()!=null){
				row.add(ccAdetailsVO.getRevContCurCode());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getGrossWeight()!=0){
				row.add(ccAdetailsVO.getGrossWeight());
			}
			else{
				row.add(ZERO);
			}
			if(ccAdetailsVO.getRevGrossWeight()!=0){
				row.add(ccAdetailsVO.getRevGrossWeight());
			}
			else{
				row.add(ZERO);
			}
			if(ccAdetailsVO.getChgGrossWeight()!=null){
				double chgGrossWeight= ccAdetailsVO.getChgGrossWeight().getAmount(); // Modified by A-8399 as part of ICRD-305527
				row.add(chgGrossWeight);
				//row.add(ccAdetailsVO.getChgGrossWeight());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getRevChgGrossWeight()!=null){
				double revchgGrossWeight= ccAdetailsVO.getRevChgGrossWeight().getAmount(); // Modified by A-8399 as part of ICRD-305527
				if("Approved".equals(ccAdetailsVO.getCcaStatus())){
					if(ccAdetailsVO.getRevChgGrossWeight().getAmount()==0){
						revchgGrossWeight =-1*ccAdetailsVO.getChgGrossWeight().getRoundedAmount(); 
					}
				}
				row.add(revchgGrossWeight);
				//row.add(ccAdetailsVO.getRevChgGrossWeight());

			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			////Added as part of ICRD-136662 starts
			if(ccAdetailsVO.getOtherChgGrossWgt()!=null){
				double chgOtherGrossWeight= ccAdetailsVO.getOtherChgGrossWgt().getAmount();
				row.add(chgOtherGrossWeight);
				//row.add(ccAdetailsVO.getChgGrossWeight());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getOtherRevChgGrossWgt()!=null){
				double revOtherChgGrossWeight= ccAdetailsVO.getOtherRevChgGrossWgt().getAmount();
				row.add(revOtherChgGrossWeight);
				//row.add(ccAdetailsVO.getRevChgGrossWeight());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			////Added as part of ICRD-136662 ends
			if(ccAdetailsVO.getGpaChangeInd()!=null){
				row.add(ccAdetailsVO.getGpaChangeInd());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getGrossWeightChangeInd()!=null){
				row.add(ccAdetailsVO.getGrossWeightChangeInd());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getCurrChangeInd()!=null){
				row.add(ccAdetailsVO.getCurrChangeInd());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getWeightChargeChangeInd()!=null){
				row.add(ccAdetailsVO.getWeightChargeChangeInd());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getCcaRemark()!=null){
				row.add(ccAdetailsVO.getCcaRemark());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getTax()!=0){
				row.add(ccAdetailsVO.getTax());
			}
			else{
				row.add(ZERO);
			}
			if(ccAdetailsVO.getRevTax()!=0){
				row.add(ccAdetailsVO.getRevTax());
			}
			else{
				row.add(ZERO);
			}
			if(ccAdetailsVO.getDifferenceAmount().getAmount()!=0){ //Added by A-8149 as part of ICRD-267530  
				row.add(ccAdetailsVO.getDifferenceAmount().getAmount());
			}
			else{
				row.add(ZERO);
			}
			if(ccAdetailsVO.getCcaRefNumber()!=null){
				row.add(ccAdetailsVO.getCcaRefNumber());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getDsnNo()!=null){
				row.add(ccAdetailsVO.getDsnNo());
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(ccAdetailsVO.getOverrideRounding()!=null){ //Added by A-8164 for ICRD-267530           
				row.add(ccAdetailsVO.getOverrideRounding());              
			}
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			//Added as part of IASCB-860 starts
         if(ccAdetailsVO.getMcaReasonCodes()!=null){
        	 row.add(ccAdetailsVO.getMcaReasonCodes());
         }
        	 else{
        		 row.add(ReportConstants.EMPTY_STRING);
        	 }
       //Added as part of IASCB-860 ends
			tableData.add(row);
		}
		return tableData;

	}

}
