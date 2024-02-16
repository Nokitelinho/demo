package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport.DeleteRepairDetailsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7627	:	25-Oct-2017	:	Draft
 */
public class DeleteRepairDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report ");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";

	private static final String OPERATION_FLAG_INS_DEL = "operation_flg_insert_delete";
	/**
	 * Constants for Status Flag
	 */
	private static final String ACTION_SUCCESS = "action_success";

	/**
	 * The execute method for DeleteRepairDetailsCommand (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command
	 *      #execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		/**
		 * Obtain the logonAttributes
		 */

		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = (MaintainDamageReportSession) getScreenSession(
				MODULE, SCREENID);

		// for populating details from main screen into the session-START
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = maintainDamageReportSession
				.getULDDamageVO() != null ? maintainDamageReportSession
				.getULDDamageVO() : new ULDDamageRepairDetailsVO();
		if (uldDamageRepairDetailsVO != null) {
			uldDamageRepairDetailsVO.setDamageStatus(maintainDamageReportForm
					.getDamageStatus());
			uldDamageRepairDetailsVO.setOverallStatus(maintainDamageReportForm
					.getOverallStatus());
			uldDamageRepairDetailsVO.setRepairStatus(maintainDamageReportForm
					.getRepairStatus());
			uldDamageRepairDetailsVO.setSupervisor(maintainDamageReportForm
					.getSupervisor());
			uldDamageRepairDetailsVO
					.setInvestigationReport(maintainDamageReportForm
							.getInvRep());

		}
		// for populating details from main screen into the session-END

		ArrayList<ULDRepairVO> uldRepairVO = maintainDamageReportSession
				.getULDDamageVO().getUldRepairVOs() != null ? new ArrayList<ULDRepairVO>(
				maintainDamageReportSession.getULDDamageVO().getUldRepairVOs())
				: new ArrayList<ULDRepairVO>();

		ArrayList<ULDRepairVO> uldRepairVOtmp = new ArrayList<ULDRepairVO>();
		log.log(Log.FINE, "\n\n uldDamageVO....", uldRepairVO);
		String[] selectedRows = null;
		selectedRows = maintainDamageReportForm.getSelectedRepRowId();
		String repairRowId = maintainDamageReportForm.getSelectedRepairRowId();
		int index = 0;
		// if (selectedRows != null) {
		if (selectedRows != null && repairRowId != null && !repairRowId.isEmpty()) {
		 int size = selectedRows.length;
		 log.log(Log.FINE, "\n\nsize....", size);
		for (ULDRepairVO uldRepairvo : uldRepairVO) {
			 for (int i = 0; i < size; i++) {
			 if (index == Integer.parseInt(repairRowId)) {
			uldRepairvo.setOperationFlag(OPERATION_FLAG_INS_DEL);
			 }
			 }index++;
		}
		 }

		ArrayList<ULDDamageVO> uldDamageVO = new ArrayList<ULDDamageVO>(
				maintainDamageReportSession.getULDDamageVO().getUldDamageVOs());

		for (ULDRepairVO uldRprVO : uldRepairVO) {
			for (ULDDamageVO uldDmgVO : uldDamageVO) {
				if (uldRprVO.getDamageReferenceNumber() == uldDmgVO
						.getDamageReferenceNumber()
						&& uldDmgVO.isClosed()
						&& OPERATION_FLAG_INS_DEL.equals(uldRprVO
								.getOperationFlag())) {
					uldDmgVO.setClosed(false);
				}
			}
		}

		log.log(Log.FINE, "\n\n uldDamageVO....", uldRepairVO);
		for (ULDRepairVO uldRepairvoTmp : uldRepairVO) {
			if (!OPERATION_FLAG_INS_DEL.equals(uldRepairvoTmp.getOperationFlag())) {
				uldRepairVOtmp.add(uldRepairvoTmp);
			}

		}

		log.log(Log.FINE, "\n\n uldDamageVOtmp....", uldRepairVOtmp);
		maintainDamageReportSession.getULDDamageVO().setUldRepairVOs(
				uldRepairVOtmp);
		log.log(Log.FINE, "\n\n maintainDamageReportSession....",
				maintainDamageReportSession.getULDDamageVO());
		ArrayList<ULDRepairVO> uldRepairVOAfter = maintainDamageReportSession
				.getULDDamageVO().getUldRepairVOs() != null ? new ArrayList<ULDRepairVO>(
				maintainDamageReportSession.getULDDamageVO().getUldRepairVOs())
				: new ArrayList<ULDRepairVO>();
		boolean isRepPresent = false;
		log.log(Log.FINE, "\n\n uldRepairVOAfter....", uldRepairVOAfter);
		log.log(Log.FINE, "\n\n uldRepairVOAfter.size()....",
				uldRepairVOAfter.size());
		if (uldRepairVOAfter != null && uldRepairVOAfter.size() > 0) {
			isRepPresent = true;

		}

		if (!("DMGNOTPRESENT").equals(maintainDamageReportForm
				.getScreenStatusValue())) {
			if (isRepPresent) {
				maintainDamageReportForm.setScreenStatusValue("REPPRESENT");
			} else {
				maintainDamageReportForm.setScreenStatusValue("REPNOTPRESENT");
			}
		}
		maintainDamageReportForm.setStatusFlag("uld_def_delete_rep_success");
		invocationContext.target = ACTION_SUCCESS;
	}
}
