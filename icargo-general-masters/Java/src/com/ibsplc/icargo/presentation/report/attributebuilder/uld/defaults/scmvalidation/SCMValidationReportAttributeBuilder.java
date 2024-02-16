/*
 * SCMValidationReportAttributeBuilder.java Created on Jan 5, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults.scmvalidation;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class SCMValidationReportAttributeBuilder extends AttributeBuilderAdapter {

	private Log log = LogFactory.getLogger("ListDamageReportAttributeBuilder");
	
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
		reportColumns.add("ULDNUM");
		reportColumns.add("FACTYP");
		reportColumns.add("LOC");
		reportColumns.add("SCMFLG");
		reportColumns.add("SCMFLGTWO");
		reportColumns.add("FLTDTL");
		reportColumns.add("FLTSEG");
		reportColumns.add("RMK");
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
		Collection<OneTimeVO> facilityType = (Collection<OneTimeVO>) extraInfor;
		Object extraInform = ((ArrayList<OneTimeVO>) extraInfo).get(1);
		Collection<OneTimeVO> scmStatus = (Collection<OneTimeVO>) extraInform;
		List<SCMValidationVO> scmValidationVOs= new ArrayList<SCMValidationVO>();
		scmValidationVOs = new ArrayList<SCMValidationVO>(data);

		for (SCMValidationVO scmValidationVO : scmValidationVOs) {
			row = new Vector<Object>();
			if (scmValidationVO.getUldNumber() != null) {
				row.add(scmValidationVO.getUldNumber());

			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			
			if (scmValidationVO.getFacilityType() != null) {
				for (OneTimeVO oneTimeVO : facilityType) {
					if (scmValidationVO.getFacilityType().equals(
							oneTimeVO.getFieldValue())) {
						scmValidationVO.setFacilityType(oneTimeVO.getFieldDescription());
					}
				}
				row.add(scmValidationVO.getFacilityType());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (scmValidationVO.getLocation() != null) {
				row.add(scmValidationVO.getLocation());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (scmValidationVO.getScmFlag() != null) {
				row.add(scmValidationVO.getScmFlag());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (scmValidationVO.getScmFlag() != null) {
				if("Y".equals(scmValidationVO.getScmFlag())){
					row.add("N");
				}
				if("N".equals(scmValidationVO.getScmFlag())){
					row.add("Y");
				}
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (scmValidationVO.getFlightDetails() != null) {
				row.add(scmValidationVO.getFlightDetails());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (scmValidationVO.getFlightSegment() != null) {
				row.add(scmValidationVO.getFlightSegment());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (scmValidationVO.getRemarks() != null) {
				row.add(scmValidationVO.getRemarks());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			cCADetailsVOs.add(row);

			log.log(Log.FINE, "  scmValidationVO", scmValidationVO);
		}

		log.log(Log.FINE, "scmValidationVOs", scmValidationVOs);
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
		Collection<OneTimeVO> facilityType = (Collection<OneTimeVO>) extraInfor;
		Object extraInform = ((ArrayList<OneTimeVO>) extraInfo).get(1);
		Collection<OneTimeVO> scmStatus = (Collection<OneTimeVO>) extraInform;
		SCMValidationFilterVO scmValidationFilterVO = (SCMValidationFilterVO) dataParameters;
		log.log(Log.INFO, "scmValidationFilterVO", scmValidationFilterVO);
		if (scmValidationFilterVO.getUldTypeCode() != null) {
			reportParameters.add(scmValidationFilterVO.getUldTypeCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (scmValidationFilterVO.getAirportCode() != null) {
			reportParameters.add(scmValidationFilterVO.getAirportCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}	
		if (scmValidationFilterVO.getFacilityType() != null) {
			for (OneTimeVO oneTimeVO : facilityType) {
				if (scmValidationFilterVO.getFacilityType().equals(
						oneTimeVO.getFieldValue())) {
					scmValidationFilterVO.setFacilityType(oneTimeVO.getFieldDescription());
				}
			}
			reportParameters.add(scmValidationFilterVO.getFacilityType());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (scmValidationFilterVO.getLocation() != null) {
			reportParameters.add(scmValidationFilterVO.getLocation());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		
		if (scmValidationFilterVO.getScmStatus()!= null) {
			for (OneTimeVO oneTimeVO : scmStatus) {
				if (scmValidationFilterVO.getScmStatus().equals(
						oneTimeVO.getFieldValue())) {
					log.log(Log.INFO, "inside iteration if loop scmStatus",
							oneTimeVO.getFieldValue());
					scmValidationFilterVO.setScmStatus(oneTimeVO
							.getFieldDescription());
				}
			}
			reportParameters.add(scmValidationFilterVO.getScmStatus());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (scmValidationFilterVO.getTotal() != null) {
			reportParameters.add(scmValidationFilterVO.getTotal());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (scmValidationFilterVO.getNotSighted() != null) {
			reportParameters.add(scmValidationFilterVO.getNotSighted());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (scmValidationFilterVO.getMissing() != null) {
			reportParameters.add(scmValidationFilterVO.getMissing());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}
}
