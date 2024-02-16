/*
 * ListCCAsReportAttributeBuilder.java Created on AUG, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class ListCCAsReportAttributeBuilder extends AttributeBuilderAdapter {
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
	private Log log = LogFactory.getLogger("ListCCAsReportAttributeBuilder");

	/**
	 * Method to construct the report column names. The column names corresponds
	 * to the column names of the view used while laying out the report. The
	 * order of the column names should match the order in which the database
	 * fields are laid out in the report
	 * 
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<String> the column names
	 */
	@Override
	public Vector<String> getReportColumns() {

		Vector<String> reportColumns = new Vector<String>();

		reportColumns.add("DSN");
		reportColumns.add("DSPDATE");
		reportColumns.add("GPACOD");
		reportColumns.add("GPANAM");
		reportColumns.add("CCATYP");

		reportColumns.add("ISSDAT");
		reportColumns.add("REVDUEARL");
		reportColumns.add("REVDUEPSTDBT");
		reportColumns.add("CCAREFNUM");
		reportColumns.add("CCASTA");
		reportColumns.add("CCARMK");
		reportColumns.add("ORG");
		reportColumns.add("DST");
		reportColumns.add("MCAAMT");
		reportColumns.add("CONDOCNO");
		reportColumns.add("RMK");
		reportColumns.add("OVRRND");       

		log.log(Log.FINE, "reportColumns is --------->", reportColumns);
		return reportColumns;
	}

	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * 
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {

		Vector<Vector> cCADetailsVOs = new Vector<Vector>();
		Vector<Object> row = null;
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection<OneTimeVO> ccaType = (Collection<OneTimeVO>) extraInfor;
		Object extraInform = ((ArrayList<OneTimeVO>) extraInfo).get(1);
		Collection<OneTimeVO> ccaArlBilStatus = (Collection<OneTimeVO>) extraInform;
		//		Object extraInforma = ((ArrayList<OneTimeVO>) extraInfo).get(2);
		//		Collection<OneTimeVO> ccaGpaBilStatus = (Collection<OneTimeVO>) extraInforma;
		//		Object extraInformation = ((ArrayList<OneTimeVO>) extraInfo).get(3);
		//		Collection<OneTimeVO> issueParty = (Collection<OneTimeVO>) extraInformation;
		List<CCAdetailsVO> listVOs = new ArrayList<CCAdetailsVO>();
		listVOs = new ArrayList<CCAdetailsVO>(data);

		for (CCAdetailsVO listVO : listVOs) {

			row = new Vector<Object>();
			if (listVO.getDsnNo() != null) {
				row.add(listVO.getDsnNo());

			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getDsDate()!= null) {
				row.add(listVO.getDsDate().toDisplayDateOnlyFormat());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getGpaCode() != null) {
				row.add(listVO.getGpaCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getGpaName() != null) {
				row.add(listVO.getGpaName());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getCcaType() != null) {
				for (OneTimeVO oneTimeVO : ccaType) {
					if (listVO.getCcaType().equals(oneTimeVO.getFieldValue())) {
						listVO.setCcaType(oneTimeVO.getFieldDescription());
					}
				}
				row.add(listVO.getCcaType());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			//			if (listVO.getIssuingParty() != null) {
			//				for (OneTimeVO oneTimeVO : issueParty) {
			//					if (listVO.getIssuingParty().equals(
			//							oneTimeVO.getFieldValue())) {
			//						listVO.setIssuingParty(oneTimeVO.getFieldDescription());
			//					}
			//				}
			//				row.add(listVO.getIssuingParty());
			//			} else {
			//				row.add(ReportConstants.EMPTY_STRING);
			//			}

			if (listVO.getIssueDate() != null) {
				row.add(listVO.getIssueDat().toDisplayDateOnlyFormat());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getRevDueArl() !=null) {
				double dueArl=listVO.getRevDueArl().getAmount();
				String revDueArl=String.valueOf(dueArl);
				row.add(revDueArl);
			} else {
				row.add(ZERO);
			}
			if (listVO.getRevDuePostDbt() > 0) {
				row.add(listVO.getRevDuePostDbt());
			} else if(listVO.getRevDuePostDbt() < 0){
				row.add(listVO.getRevDuePostDbt());
			}
			else {
				row.add(ZERO);
			}
			if(listVO.getUsrccanum()!=null){
				row.add(listVO.getUsrccanum());
			}else if (listVO.getCcaRefNumber() != null) {
				row.add(listVO.getCcaRefNumber());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getCcaStatus() != null) {

				for (OneTimeVO oneTimeVO : ccaArlBilStatus) {
					if (listVO.getCcaStatus().equals(oneTimeVO.getFieldValue())) {
						listVO.setCcaStatus(oneTimeVO.getFieldDescription());
					}
				}
				row.add(listVO.getCcaStatus());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getCcaRemark() != null) {
				row.add(listVO.getCcaRemark());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getOriginCode() != null) {
				row.add(listVO.getOriginCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getDestnCode() != null) {
				row.add(listVO.getDestnCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if("Approved".equals(listVO.getCcaStatus())){
			if (listVO.getRevChgGrossWeight() != null) {
					if(listVO.getRevChgGrossWeight().getAmount()==0 && listVO.getChgGrossWeight()!=null){
						listVO.getRevChgGrossWeight().setAmount(-listVO.getChgGrossWeight().getAmount());
						if(listVO.getRevTax()==0){
							listVO.setRevTax(-(listVO.getTax()));
						}


					}
				}
			}
			if (listVO.getDiffAmount()>0) {
				row.add(listVO.getDiffAmount());
			}else if(listVO.getDiffAmount()<0){
				row.add(listVO.getDiffAmount());
			} else {
				row.add(ZERO);
			}
			if(listVO.getCsgDocumentNumber()!=null){
				row.add(listVO.getCsgDocumentNumber());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(listVO.getCcaRemark()!=null){
				row.add(listVO.getCcaRemark());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(listVO.getOverrideRounding()!=null){ //Added by A-8164 for ICRD-267530  
				row.add(listVO.getOverrideRounding());         
			}  
			else{
				row.add(ReportConstants.EMPTY_STRING);
			}

			cCADetailsVOs.add(row);

			log.log(Log.INFO, " \n xxxxxx", listVO);
		}

		log.log(Log.INFO, "cCADetailsVOs", cCADetailsVOs);
		return cCADetailsVOs;
	}

	/**
	 * Method to construct the report parameters. The report parameters
	 * corresponds to the parameter fields in the report. The order of the
	 * parameters should match the order in which the parameter fields are laid
	 * out in the report
	 * 
	 * @param parameters
	 *            the parameter data
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
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection<OneTimeVO> ccaType = (Collection<OneTimeVO>) extraInfor;
		Object extraInform = ((ArrayList<OneTimeVO>) extraInfo).get(1);
		Collection<OneTimeVO> ccaArlBilStatus = (Collection<OneTimeVO>) extraInform;
		Object extraInforma = ((ArrayList<OneTimeVO>) extraInfo).get(2);
		Collection<OneTimeVO> ccaGpaBilStatus = (Collection<OneTimeVO>) extraInforma;
		Object extraInformation = ((ArrayList<OneTimeVO>) extraInfo).get(3);
		Collection<OneTimeVO> issueParty = (Collection<OneTimeVO>) extraInformation;
		ListCCAFilterVO listCCAFilterVO = (ListCCAFilterVO) dataParameters;
		log.log(Log.INFO, "listccafilterVo", listCCAFilterVO);
		if (listCCAFilterVO.getCcaRefNumber() != null) {
			reportParameters.add(listCCAFilterVO.getCcaRefNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (listCCAFilterVO.getCcaType() != null) {
			for (OneTimeVO oneTimeVO : ccaType) {
				if (listCCAFilterVO.getCcaType().equals(
						oneTimeVO.getFieldValue())) {
					listCCAFilterVO.setCcaType(oneTimeVO.getFieldDescription());
				}
			}
			reportParameters.add(listCCAFilterVO.getCcaType());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (listCCAFilterVO.getDsn() != null) {
			reportParameters.add(listCCAFilterVO.getDsn());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (listCCAFilterVO.getDsnDate() != null) {
			reportParameters.add(listCCAFilterVO.getDsnDate()
					.toDisplayDateOnlyFormat());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (listCCAFilterVO.getCcaStatus() != null) {
			for (OneTimeVO oneTimeVO : ccaArlBilStatus) {
				log.log(Log.INFO, "inside iteration" );
				if (listCCAFilterVO.getCcaStatus().equals(
						oneTimeVO.getFieldValue())) {
					log.log(Log.INFO,
							"inside iteration if loop ccaArlBilStatus",
							oneTimeVO.getFieldValue());
					listCCAFilterVO.setCcaStatus(oneTimeVO
							.getFieldDescription());
				}
			}
			for (OneTimeVO oneTimeVO : ccaGpaBilStatus) {
				log.log(Log.INFO, "inside iteration2" );
				if (listCCAFilterVO.getCcaStatus().equals(
						oneTimeVO.getFieldValue())) {
					log.log(Log.INFO,
							"inside iteration if loop ccaGpaBilStatus",
							oneTimeVO.getFieldValue());
					listCCAFilterVO.setCcaStatus(oneTimeVO
							.getFieldDescription());
				}
			}
			reportParameters.add(listCCAFilterVO.getCcaStatus());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (listCCAFilterVO.getIssueParty() != null) {
			for (OneTimeVO oneTimeVO : issueParty) {
				if (listCCAFilterVO.getIssueParty().equals(
						oneTimeVO.getFieldValue())) {
					listCCAFilterVO.setIssueParty(oneTimeVO
							.getFieldDescription());
				}
			}
			reportParameters.add(listCCAFilterVO.getIssueParty());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (listCCAFilterVO.getAirlineCode() != null) {
			reportParameters.add(listCCAFilterVO.getAirlineCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (listCCAFilterVO.getGpaCode() != null) {
			reportParameters.add(listCCAFilterVO.getGpaCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (listCCAFilterVO.getGpaName() != null) {
			reportParameters.add(listCCAFilterVO.getGpaName());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (listCCAFilterVO.getFromDate() != null) {
			reportParameters.add(listCCAFilterVO.getFromDate()
					.toDisplayDateOnlyFormat());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (listCCAFilterVO.getToDate() != null) {
			reportParameters.add(listCCAFilterVO.getToDate()
					.toDisplayDateOnlyFormat());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(listCCAFilterVO.getOrigin() !=null){
			reportParameters.add(listCCAFilterVO.getOrigin());
		}
		else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(listCCAFilterVO.getDestination() !=null){
			reportParameters.add(listCCAFilterVO.getDestination());
		}
		else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		
		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}

}
