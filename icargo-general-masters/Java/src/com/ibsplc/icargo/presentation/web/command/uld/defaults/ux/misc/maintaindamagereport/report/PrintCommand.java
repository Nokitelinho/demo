package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport.report;

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
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport.report.PrintCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7627	:	25-Oct-2017	:	Draft
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

	// Added by Tarun for CRQ_AirNZ 312 on 25Mar08
	private static final String ACTION = "printMaintainDamageRepairReport";

	// private static final String ACTION = "printmaintaindamagereport";
	private static final String ULD_NUMBER_MISSING_ERROR = "uld.defaults.maintainDmgRep.msg.err.uldnomandatory";

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
			ErrorVO errorVO = new ErrorVO(ULD_NUMBER_MISSING_ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = "normal-report-error-jsp";
			return;
		}
		log.log(Log.FINE, "uldDamageFilterVO ---> " + uldDamageFilterVO);

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

		if (getErrors() != null && getErrors().size() > 0) {

			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.exiting("MRA_GPABILLING", "PrintCommand exit");
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
