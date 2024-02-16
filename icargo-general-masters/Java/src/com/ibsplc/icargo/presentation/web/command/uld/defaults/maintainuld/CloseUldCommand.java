/*
 * CloseUldCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListRepairReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is invoked onclosing the screen
 * 
 * @author A-2001
 */
public class CloseUldCommand extends SaveULDCommand{

	private Log log = LogFactory.getLogger("closeUld");

	private static final String MODULE_LISTULD = "uld.defaults";

	private static final String SCREENID_LISTULD = "uld.defaults.listuld";

	private static final String MODULE_DAMAGE = "uld.defaults";

	private static final String SCREENID_DAMAGE = "uld.defaults.listdamagereport";

	private static final String MODULE_LISTREPAIR = "uld.defaults";

	private static final String SCREENID_LISTREPAIR = "uld.defaults.listrepairreport";

	private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";

	private static final String SCREENID_SCMERRORLOG = "uld.defaults.scmulderrorlog";

	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID = "uld.defaults.maintainuld";

	private static final String CLOSE_MASTERFAILURE = "close_materfailure";

	private static final String CLOSE_MASTERSUCCESS = "close_matersuccess";

	private static final String CLOSE_LISTCREATE = "close_listcreate";

	private static final String CLOSE_LISTDETAIL = "close_listdetail";

	private static final String CLOSE_LISTREPAIR = "close_listrepair";

	private static final String CLOSE_LISTDAMAGE = "close_listdamage";

	private static final String CLOSE_LOANBORROW = "close_loanborrow";

	private static final String CLOSE_ERRORLOG = "close_ulderrorlog";

	private static final String CLOSE_SCMERRORLOG = "close_scmulderrorlog";

	/**
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		MaintainULDSession maintainULDSession = getScreenSession(MODULE,
				SCREENID);
		ListULDSession listULDSession = (ListULDSession) getScreenSession(
				MODULE_LISTULD, SCREENID_LISTULD);
		ListRepairReportSession listRepairReportSession = (ListRepairReportSession) getScreenSession(
				MODULE_LISTREPAIR, SCREENID_LISTREPAIR);
		ListDamageReportSession listDamageReportSession = (ListDamageReportSession) getScreenSession(
				MODULE_DAMAGE, SCREENID_DAMAGE);
		SCMULDErrorLogSession scmSession=getScreenSession(MODULE,SCREENID_SCMERRORLOG);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MaintainULDForm maintainUldForm = (MaintainULDForm) invocationContext.screenModel;
		if (!"canclose".equals(maintainUldForm.getCloseStatus())) {
			if (maintainULDSession.getULDMultipleVOs() != null
					&& maintainULDSession.getULDMultipleVOs().size() > 0) {
				ArrayList<ULDVO> uldVOs = new ArrayList<ULDVO>(
						maintainULDSession.getULDMultipleVOs().values());
				int currentPage = Integer.parseInt(maintainUldForm
						.getCurrentPage());
				String currentUldNumber = maintainULDSession
						.getUldNosForNavigation().get(currentPage - 1);
				ULDVO uldVO = maintainULDSession.getULDMultipleVOs().get(
						currentUldNumber);
			/*if (isUldUpdated(uldVO, maintainUldForm)) {
					log.log(Log.SEVERE,
							"uld updated************************uldVO>>>>>"
									+ uldVO);
					log.log(Log.SEVERE,
							"uld updated************************maintainUldForm>>>>>"
									+ maintainUldForm);

					ErrorVO error = new ErrorVO(
							"uld.defaults.unsaveddataexists");
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);
				} else {
					for (ULDVO uldMultipleVO : uldVOs) {
						if (ULDVO.OPERATION_FLAG_UPDATE.equals(uldMultipleVO
								.getOperationalFlag())) {
							ErrorVO error = new ErrorVO(
									"uld.defaults.unsaveddataexists");
							error.setErrorDisplayType(ErrorDisplayType.WARNING);
							errors.add(error);
						}
					}
				}*/
			} else {
				ULDVO uldVO = maintainULDSession.getULDVO();
				boolean isupdated = false;
				if (maintainULDSession.getUldNumbersSaved() != null) {
					if ((maintainULDSession.getUldNumbersSaved().size() > 0)) {
						isupdated = true;
						log
								.log(
										Log.SEVERE,
										"uldnumbers saved************************>>>>>",
										isupdated);
					}
				}
				if (uldVO != null) {
					if (ULDVO.OPERATION_FLAG_INSERT.equals(uldVO
							.getOperationalFlag())) {
						isupdated = true;
						log.log(Log.SEVERE,
								"uldvo insert************************>>>>>",
								isupdated);
					} else if (isUldUpdated(uldVO, maintainUldForm)) {
						isupdated = true;
						log.log(Log.SEVERE,
								"uldvo update************************>>>>>",
								isupdated);
					}
				}
				/*if (isupdated) {
					ErrorVO error = new ErrorVO(
							"uld.defaults.unsaveddataexists");
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);
				}*/
			}
			if (errors.size() > 0) {
				log.log(Log.SEVERE, "************************>>>>>", errors.toString());
				maintainUldForm.setCloseStatus("whethertoclose");
				invocationContext.addAllError(errors);
				invocationContext.target = CLOSE_MASTERFAILURE;
				return;
			}
		}
		maintainULDSession.setULDMultipleVOs(null);
		maintainULDSession.setUldNosForNavigation(null);
		maintainULDSession.setUldNumbers(null);
		maintainULDSession.setUldNumbersSaved(null);
		maintainULDSession.setULDVO(null);
		maintainUldForm.setCloseStatus("");
		
		log.log(Log.INFO, "maintainUldForm.getScreenloadstatus()------>  ",
				maintainUldForm.getScreenloadstatus());
		if (("listcreate").equals(maintainUldForm.getScreenloadstatus())) {
			listULDSession.setListStatus("noListForm");
			invocationContext.target = CLOSE_LISTCREATE;
		} else if (("listdetail").equals(maintainUldForm.getScreenloadstatus())) {
			listULDSession.setListStatus("noListForm");
			invocationContext.target = CLOSE_LISTDETAIL;
		}//added by A-5844 for the BUG ICRD-63084 
		else if (("monitorStock").equals(maintainUldForm.getScreenloadstatus())) {
			listULDSession.setListStatus("monitorStock");
			invocationContext.target = CLOSE_LISTDETAIL;
		} else if (("ListRepairReport").equals(maintainUldForm.getScreenloadstatus())) {
			listRepairReportSession.setScreenId("LISTREPAIR");
			invocationContext.target = CLOSE_LISTREPAIR;
		} else if (("ListDamageReport").equals(maintainUldForm.getScreenloadstatus())) {
			listDamageReportSession.setScreenId("LISTDAMAGE");
			invocationContext.target = CLOSE_LISTDAMAGE;
		} else if (("LoanBorrow").equals(maintainUldForm.getScreenloadstatus())||("frommaintainuldlistdetail").equals(maintainUldForm.getScreenloadstatus())) {
			invocationContext.target = CLOSE_LOANBORROW;
		} else if (("fromulderrorlog").equals(maintainUldForm.getScreenloadstatus())) {

			log.log(Log.FINE, "\n reconcile  delegate ");
			ULDErrorLogSession uldErrorLogSession = (ULDErrorLogSession) getScreenSession(
					MODULE, SCREENID_ULDERRORLOG);
			uldErrorLogSession.setPageURL("frommaintainuld");
			maintainULDSession.removeAllAttributes();
			invocationContext.target = CLOSE_ERRORLOG;
		} else if ("fromScmUldReconcile".equals(maintainUldForm
				.getScreenloadstatus())) {
			scmSession.setPageUrl("frommaintainuld");
			maintainULDSession.removeAllAttributes();
			invocationContext.target = CLOSE_SCMERRORLOG;

		}

		else {
			invocationContext.target = CLOSE_MASTERSUCCESS;
		}

	}

}
