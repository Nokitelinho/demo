package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport.CloseCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7627	:	25-Oct-2017	:	Draft
 */
public class CloseCommand extends BaseCommand {

	/**
	 * Logger for Maintain DamageReport
	 */
	private Log log = LogFactory.getLogger("Maintain DamageReport");

	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";

	private static final String SCREEN_ID = "uld.defaults.listdamagereport";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLOSE_TOLISTDAMAGE = "close_tolistdamage";

	private static final String CLOSE_TOLISTULD = "close_tolistuld";

	private static final String CLOSE_FAILURE = "close_failure";

	private static final String CLOSE_TOLOANBORROW = "close_loanborrow";

	private static final String CLOSE_TOMIDLOANBORROW = "close_midloanborrow";

	private static final String CLOSE_TORETURNLOANBORROW = "close_returnloanborrow";

	private static final String MODULE_LISTULD = "uld.defaults";

	private static final String SCREEN_ID_ONE = "uld.defaults.loanborrowuld";

	private static final String SCREENID_LISTULD = "uld.defaults.listuld";

	private static final String CLOSE_ERRORLOG = "close_ulderrorlog";

	private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";

	private static final String PAGE_URL = "fromScmUldReconcileDamage";

	private static final String CLOSE_TOMAILACCEPTANCEFO = "close_to_mail_acceptancefo";
	private static final String CLOSE_TOMAILACCEPTANCEFC = "close_to_mail_acceptancefc";
	private static final String CLOSE_TOMAILACCEPTANCECO = "close_to_mail_acceptanceco";
	private static final String CLOSE_TOMAILACCEPTANCECC = "close_to_mail_acceptancecc";

	private static final String MAINTAIN_DAMAGE_REPORT_SAVE_ERROR = "uld.defaults.maintainDmgRep.msg.err.dmgnotsaved";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = (MaintainDamageReportSession) getScreenSession(
				MODULE, SCREENID);
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(MODULE,
				SCREEN_ID_ONE);
		ListDamageReportSession session = (ListDamageReportSession) getScreenSession(
				MODULE, SCREEN_ID);
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = maintainDamageReportSession
				.getULDDamageVO() != null ? maintainDamageReportSession
				.getULDDamageVO() : new ULDDamageRepairDetailsVO();

		boolean canSave = false;

		// ADDED BY A-3251 SREEJITH P.C. FOR MAILTRACKING ANZ401.
		if (maintainDamageReportForm.getPageURL() != null
				&& ("FromScreenMAILACCEPTANCEFO")
						.equals(maintainDamageReportForm.getPageURL())) {
			invocationContext.target = CLOSE_TOMAILACCEPTANCEFO;
			return;
		}
		if (maintainDamageReportForm.getPageURL() != null
				&& ("FromScreenMAILACCEPTANCEFC")
						.equals(maintainDamageReportForm.getPageURL())) {
			invocationContext.target = CLOSE_TOMAILACCEPTANCEFC;
			return;
		}

		if (maintainDamageReportForm.getPageURL() != null
				&& ("FromScreenMAILACCEPTANCECO")
						.equals(maintainDamageReportForm.getPageURL())) {
			invocationContext.target = CLOSE_TOMAILACCEPTANCECO;
			return;
		}

		if (maintainDamageReportForm.getPageURL() != null
				&& ("FromScreenMAILACCEPTANCECC")
						.equals(maintainDamageReportForm.getPageURL())) {
			invocationContext.target = CLOSE_TOMAILACCEPTANCECC;
			return;
		}
		
		// added by saritha
		if (maintainDamageReportSession.getParentScreenId() != null
				&& maintainDamageReportSession.getParentScreenId().length() > 0) {
			maintainDamageReportForm.setScreenMode("uldacceptance");
			maintainDamageReportSession.removeParentScreenId();
		}

		if (maintainDamageReportSession.getULDDamageVO() != null) {

			ArrayList<ULDRepairVO> uldRepairVOs = maintainDamageReportSession
					.getULDDamageVO().getUldRepairVOs() != null ? new ArrayList<ULDRepairVO>(
					maintainDamageReportSession.getULDDamageVO()
							.getUldRepairVOs()) : null;

			ArrayList<ULDDamageVO> uldDamageVOs = maintainDamageReportSession
					.getULDDamageVO().getUldDamageVOs() != null ? new ArrayList<ULDDamageVO>(
					maintainDamageReportSession.getULDDamageVO()
							.getUldDamageVOs()) : null;

			if (maintainDamageReportSession.getSavedULDDamageVO() != null) {

				ArrayList<ULDRepairVO> uldSavedRepairVOs = maintainDamageReportSession
						.getSavedULDDamageVO().getUldRepairVOs() != null ? new ArrayList<ULDRepairVO>(
						maintainDamageReportSession.getSavedULDDamageVO()
								.getUldRepairVOs()) : null;

				ArrayList<ULDDamageVO> uldSavedDamageVOs = maintainDamageReportSession
						.getSavedULDDamageVO().getUldDamageVOs() != null ? new ArrayList<ULDDamageVO>(
						maintainDamageReportSession.getSavedULDDamageVO()
								.getUldDamageVOs()) : null;

				if (uldSavedRepairVOs != null && uldRepairVOs != null) {
					if (!uldSavedRepairVOs.equals(uldRepairVOs)) {
						canSave = true;

					}
				}
				if (uldSavedRepairVOs == null && uldRepairVOs != null) {
					canSave = true;
				}
				if (uldSavedDamageVOs != null && uldDamageVOs != null) {
					if (!uldSavedDamageVOs.equals(uldDamageVOs)) {
						canSave = true;

					}
				}
				if (uldSavedDamageVOs == null && uldDamageVOs != null) {
					canSave = true;
				}
			}
			if (maintainDamageReportSession.getSavedULDDamageVO() == null
					&& uldRepairVOs != null) {
				canSave = true;
			}
			if (maintainDamageReportSession.getSavedULDDamageVO() == null
					&& uldDamageVOs != null) {
				for (ULDDamageVO uldDamageVO : uldDamageVOs) {
					if (uldDamageVO.getOperationFlag() != null) {
						canSave = true;
						break;
					}

				}
			}

			if ((uldDamageRepairDetailsVO.getDamageStatus() != null && !uldDamageRepairDetailsVO
					.getDamageStatus().equals(
							maintainDamageReportForm.getDamageStatus()))
					|| (uldDamageRepairDetailsVO.getOverallStatus() != null && !uldDamageRepairDetailsVO
							.getOverallStatus()
							.equals(maintainDamageReportForm.getOverallStatus()))
					|| (uldDamageRepairDetailsVO.getRepairStatus() != null && !uldDamageRepairDetailsVO
							.getRepairStatus().equals(
									maintainDamageReportForm.getRepairStatus()))
					|| (uldDamageRepairDetailsVO.getSupervisor() != null && !uldDamageRepairDetailsVO
							.getSupervisor().equals(
									maintainDamageReportForm.getSupervisor()))
					|| (uldDamageRepairDetailsVO.getInvestigationReport() != null && !uldDamageRepairDetailsVO
							.getInvestigationReport().equals(
									maintainDamageReportForm.getInvRep()))) {
				canSave = true;
			}

		}
		/*
		 * if(toSave) { maintainDamageReportForm.setSaveStatus("whethertosave");
		 * ErrorVO error = new
		 * ErrorVO("uld.defaults.maintainDmgRep.msg.err.dmgnotsaved");
		 * error.setErrorDisplayType(ErrorDisplayType.WARNING);
		 * invocationContext.addError(error); invocationContext.target =
		 * CLOSE_FAILURE; return; }
		 */
		if(maintainDamageReportSession.getPageURL()!=null){   //Added by A-7924 as part of ICRD-304117
			maintainDamageReportForm.setPageURL(maintainDamageReportSession
				.getPageURL());
		}
		log.log(Log.FINE, "maintainDamageReportForm.getPageURL() ---> ",
				maintainDamageReportForm.getPageURL());
		if (maintainDamageReportForm.getPageURL() != null
				&& ("LISTDAMAGEREPORT").equals(maintainDamageReportForm
						.getPageURL())) {
			session.setScreenId("LISTDAMAGE");
			invocationContext.target = CLOSE_TOLISTDAMAGE;
			return;
		}
		if (maintainDamageReportForm.getPageURL() != null
				&& (("LoanBorrow")
						.equals(maintainDamageReportForm.getPageURL())
						|| ("fromulderrorlogforborrowDamage")
								.equals(maintainDamageReportForm.getPageURL())
						|| ("fromScmReconcileBorrowDamage")
								.equals(maintainDamageReportForm.getPageURL())
						|| ("fromulderrorlogforloanDamage")
								.equals(maintainDamageReportForm.getPageURL()) || "fromScmUldReconcileDamage"
							.equals(maintainDamageReportForm.getPageURL()))) {
			loanBorrowULDSession.setPageURL(maintainDamageReportForm
					.getPageURL());
			invocationContext.target = CLOSE_TOLOANBORROW;
			return;
		}
		// added for scm reconcile
		if (maintainDamageReportForm.getPageURL() != null
				&& PAGE_URL.equals(maintainDamageReportForm.getPageURL())) {
			loanBorrowULDSession.setPageURL(maintainDamageReportForm
					.getPageURL());
			invocationContext.target = CLOSE_TOLOANBORROW;
			return;
		}
		if (maintainDamageReportForm.getPageURL() != null
				&& ("MidLoanBorrow").equals(maintainDamageReportForm
						.getPageURL())) {
			invocationContext.target = CLOSE_TOMIDLOANBORROW;
			return;
		}
		if (maintainDamageReportForm.getPageURL() != null
				&& ("ReturnLoanBorrow").equals(maintainDamageReportForm
						.getPageURL())) {
			invocationContext.target = CLOSE_TORETURNLOANBORROW;
			return;
		}
		if (maintainDamageReportForm.getFromScreen()!= null
				&& ("ULD006").equals(maintainDamageReportForm.getFromScreen())) {
			ListULDSession listULDSession = (ListULDSession) getScreenSession(
					MODULE_LISTULD, SCREENID_LISTULD);
			listULDSession.setListStatus("noListForm");
			invocationContext.target = CLOSE_TOLISTULD;
			return;
		}
		if (maintainDamageReportForm.getPageURL() != null
				&& ("fromulderrorlog").equals(maintainDamageReportForm
						.getPageURL())) {

			log.log(Log.FINE, "\n reconcile  delegate ");
			ULDErrorLogSession uldErrorLogSession = (ULDErrorLogSession) getScreenSession(
					MODULE_LISTULD, SCREENID_ULDERRORLOG);
			uldErrorLogSession.setPageURL("frommaintainuld");
			maintainDamageReportSession.removeAllAttributes();
			invocationContext.target = CLOSE_ERRORLOG;
			return;
		}

		if (canSave) {
			maintainDamageReportForm.setSaveStatus("whethertosave");
			ErrorVO error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_SAVE_ERROR);
			error.setErrorDisplayType(ErrorDisplayType.WARNING);
			invocationContext.addError(error);
			invocationContext.target = CLOSE_FAILURE;
			return;
		}

		maintainDamageReportSession.setPageURL(null);
		invocationContext.target = CLOSE_SUCCESS;

	}

}
