/*
 * PrintCommand.java Created on Feb 6, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.maintaindamagereport.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2046
 * 
 */
public class PrintCommand extends AbstractPrintCommand {
	/*
	 * report id
	 */

	private static final String REPORT_ID = "RPTOPR026";

	private static final String PRODUCTCODE = "uld";

	private static final String SUBPRODUCTCODE = "defaults";

	private static final String DAMAGE_SEVERITY = "uld.defaults.damageseverity";
	
	private static final String RESOURCE_BUNDLE_KEY = "maintainDamageReportResources";
	
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	
	
	//	Added by Tarun for CRQ_AirNZ 312 on 25Mar08
	private static final String ACTION = "printMaintainDamageRepairReport";
	
	//private static final String ACTION = "printmaintaindamagereport";
	
	

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("Print COMMAND", "-------Maintain Damage Report");
		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		ULDDamageFilterVO uldDamageFilterVO = new ULDDamageFilterVO();
		uldDamageFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (maintainDamageReportForm.getUldNumber() != null
				&& maintainDamageReportForm.getUldNumber().trim().length() > 0) {
			uldDamageFilterVO.setUldNumber(maintainDamageReportForm
					.getUldNumber().toUpperCase());
		} else {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.maintainDmgRep.msg.err.uldnomandatory");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = "normal-report-error-jsp";
			return;
		}
		log.log(Log.FINE, "uldDamageFilterVO ---> " + uldDamageFilterVO);
		//ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = new ULDDamageRepairDetailsVO();

		/*try {
			uldDamageRepairDetailsVO = new ULDDefaultsDelegate()
					.findULDDamageDetails(uldDamageFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.printStackTrace();
			handleDelegateException(businessDelegateException);
		}

		if (uldDamageRepairDetailsVO == null) {
			invocationContext.addError(new ErrorVO(
					"uld.defaults.maintainDmgRep.msg.err.noresults", null));
			invocationContext.addAllError(errors);
			invocationContext.target = "normal-report-error-jsp";
			return;
		}
		if (uldDamageRepairDetailsVO != null) {
			if (uldDamageRepairDetailsVO.getUldDamageVOs() == null
					|| uldDamageRepairDetailsVO.getUldDamageVOs().size() == 0) {
				invocationContext.addError(new ErrorVO(
						"uld.defaults.maintainDmgRep.msg.err.noresults", null));
				invocationContext.addAllError(errors);
				invocationContext.target = "normal-report-error-jsp";
				return;
			}
		}

		getReportSpec().setReportId(REPORT_ID);
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "uldNumber" });
		getReportSpec().addParameterMetaData(parameterMetaData);
		getReportSpec().addParameter(uldDamageFilterVO);
		Collection<ULDDamageVO> damageVOs = uldDamageRepairDetailsVO
				.getUldDamageVOs();
		if (damageVOs != null && damageVOs.size() > 0) {
			Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(getApplicationSession()
					.getLogonVO().getCompanyCode());
			Collection<OneTimeVO> dmgSta = oneTimeCollection
					.get(DAMAGE_SEVERITY);
			log.log(Log.FINE, "\n\nDamage VOS From Server-------------->"
					+ damageVOs);
			for (ULDDamageVO damageVO : damageVOs) {
				for (OneTimeVO oneTimeVO : dmgSta) {
					if (damageVO.getSeverity() != null
							&& damageVO.getSeverity().equals(
									oneTimeVO.getFieldValue())) {
						damageVO.setSeverity(oneTimeVO.getFieldDescription());
					}
				}
			}

			getReportSpec().setData(damageVOs);

		}
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "DMGREFNUM", "DMGCOD",
				"DMGPOS", "DMSSVT", "RPTARP", "DMGRPTDAT", "RMK" });
		reportMetaData.setFieldNames(new String[] { "damageReferenceNumber",
				"damageCode", "position", "severity", "reportedStation",
				"reportedDate", "remarks" });
		getReportSpec().setReportMetaData(reportMetaData);

		getReportSpec().setPreview(true);

		getReportSpec().setProductCode(PRODUCTCODE);
		getReportSpec().setSubProductCode(SUBPRODUCTCODE);
		getReportSpec().setResourceBundle("maintainDamageReportResources");

		generateReport();
		invocationContext.target = getTargetPage();*/
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(maintainDamageReportForm.getProduct());
		reportSpec.setSubProductCode(maintainDamageReportForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(uldDamageFilterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		
		generateReport();
		
		if(getErrors() != null && getErrors().size() > 0){
			
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		log.exiting("MRA_GPABILLING","PrintCommand exit");
		invocationContext.target = getTargetPage();
		

	}

	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(DAMAGE_SEVERITY);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			exception = handleDelegateException(ex);
		}
		return hashMap;
	}
}
