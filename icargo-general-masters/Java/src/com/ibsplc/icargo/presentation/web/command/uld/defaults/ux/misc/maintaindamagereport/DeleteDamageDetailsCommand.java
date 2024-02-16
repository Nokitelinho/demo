package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport.DeleteDamageDetailsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7627	:	25-Oct-2017	:	Draft
 */
public class DeleteDamageDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report ");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	private static final String TOTALPOINTS = "uld.defaults.totalpointstomakeuldunserviceable";
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
	 * The execute method for DeleteDamageDetailsCommand (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command
	 *      #execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

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

		ArrayList<ULDDamageVO> uldDamageVO = maintainDamageReportSession
				.getULDDamageVO().getUldDamageVOs() != null ? new ArrayList<ULDDamageVO>(
				maintainDamageReportSession.getULDDamageVO().getUldDamageVOs())
				: new ArrayList<ULDDamageVO>();
		ArrayList<ULDDamageVO> uldDamageVOtmp = new ArrayList<ULDDamageVO>();
		log.log(Log.FINE, "\n\n uldDamageVO....", uldDamageVO);
		String[] selectedRows = null;
		selectedRows = maintainDamageReportForm.getSelectedDmgRowId();
		String damageRowId = maintainDamageReportForm.getSelectedDamageRowId();
		int index = 0;
		if (selectedRows != null && damageRowId != null && !damageRowId.isEmpty()) {
			int size = selectedRows.length;
		    log.log(Log.FINE, "\n\nsize....", size);
			for (ULDDamageVO uldDamagevo : uldDamageVO) {
				for (int i = 0; i < size; i++) {
					if (index == Integer.parseInt(damageRowId)) {

						if (uldDamagevo.getOperationFlag() != null
								&& !uldDamagevo.getOperationFlag().equals(
										AbstractVO.OPERATION_FLAG_INSERT)) {
							uldDamagevo
									.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
						}
						if (uldDamagevo.getOperationFlag() == null) {
							uldDamagevo
									.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
						}
						if (uldDamagevo.getOperationFlag() != null
								&& uldDamagevo.getOperationFlag().equals(
										AbstractVO.OPERATION_FLAG_INSERT)) {
							uldDamagevo
									.setOperationFlag(OPERATION_FLAG_INS_DEL);
						}

					}
				}
				index++;
			}
		}
		log.log(Log.FINE, "\n\n uldDamageVO....", uldDamageVO);
		String totalpoints = null;
		// if(maintainDamageReportSession.getDamageTotalPoint()==0){

		Collection<String> parameterTypes = new ArrayList<String>();
		Map<String, String> systemParameterMap = null;

		parameterTypes.add(TOTALPOINTS);

		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();

		try {
			systemParameterMap = sharedDefaultsDelegate
					.findSystemParameterByCodes(parameterTypes);
		} catch (BusinessDelegateException e) {
			// To be reviewed Auto-generated catch block
			e.getMessage();
		}
		if (systemParameterMap != null) {
			totalpoints = systemParameterMap.get(TOTALPOINTS);

			log.log(Log.FINE,
					"The Total Value frm SystemParameter from Delete",
					totalpoints);

		}

		int totalDamagePoints = 0;

		if (uldDamageVO != null && uldDamageVO.size() > 0) {
			for (ULDDamageVO uldDamageVo : uldDamageVO) {
				if (uldDamageVo.getDamagePoints() != null) {
					int damagePoints = Integer.parseInt(uldDamageVo
							.getDamagePoints());
					if (uldDamageVo.getOperationFlag() == null
							&& damagePoints != 0) {

						totalDamagePoints = totalDamagePoints + damagePoints;

					}

				}
			}
		}
		log.log(Log.FINE, "The damageChecklistVO after main screen details",
				totalDamagePoints);
		if (totalDamagePoints < Integer.parseInt(totalpoints)) {
			// Modified by A-3415 for ICRD-113953
			// User can manually set overall status. prevent modification unless
			// damage points satisfied.
			// maintainDamageReportForm.setOverStatus("O");
		} else {
			maintainDamageReportForm.setOverStatus("N");
		}
		log.log(Log.FINE, "THe Form value of OverStatus from Delete is",
				maintainDamageReportForm.getOverStatus());
		for (ULDDamageVO uldDamagevoTmp : uldDamageVO) {
			if (uldDamagevoTmp.getOperationFlag() != null
					&& !uldDamagevoTmp.getOperationFlag().equals(
							OPERATION_FLAG_INS_DEL)) {
				uldDamageVOtmp.add(uldDamagevoTmp);
			}
			if (uldDamagevoTmp.getOperationFlag() == null) {
				uldDamageVOtmp.add(uldDamagevoTmp);
			}
		}

		log.log(Log.FINE, "\n\n uldDamageVOtmp....", uldDamageVOtmp);
		maintainDamageReportSession.getULDDamageVO().setUldDamageVOs(
				uldDamageVOtmp);
		maintainDamageReportSession.getULDDamageVO().setOverallStatus(
				maintainDamageReportForm.getOverStatus());
		log.log(Log.FINE, "\n\n maintainDamageReportSession....",
				maintainDamageReportSession.getULDDamageVO());
		ArrayList<ULDDamageVO> uldDamageVOAfter = maintainDamageReportSession
				.getULDDamageVO().getUldDamageVOs() != null ? new ArrayList<ULDDamageVO>(
				maintainDamageReportSession.getULDDamageVO().getUldDamageVOs())
				: new ArrayList<ULDDamageVO>();
		log.log(Log.FINE, "\n\n uldDamageVOAfter+++++++++++++++++",
				uldDamageVOAfter);
		boolean isDmgPresent = false;
		for (ULDDamageVO uldDamageVOafter : uldDamageVOAfter) {
			if (uldDamageVOafter.getOperationFlag() == null
					|| (uldDamageVOafter.getOperationFlag() != null && uldDamageVOafter
							.getOperationFlag() != (AbstractVO.OPERATION_FLAG_INSERT))
					&& uldDamageVOafter.getOperationFlag() != (AbstractVO.OPERATION_FLAG_DELETE)) {
				isDmgPresent = true;
				break;
			}

		}
		// added by a-3045 for bug 17989 starts
		boolean dmgFlag = false;
		for (ULDDamageVO uldDamageVOafter : uldDamageVOAfter) {
			if (uldDamageVOafter.getOperationFlag() == null
					|| (AbstractVO.OPERATION_FLAG_INSERT)
							.equals(uldDamageVOafter.getOperationFlag())
					|| (("U").equals(uldDamageVOafter.getOperationFlag()) && !uldDamageVOafter
							.isClosed())) {
				dmgFlag = true;
				break;
			}
		}
		log.log(Log.FINE, "\n\n dmgFlag----------", dmgFlag);
		if (!dmgFlag) {
			log.log(Log.FINE,
					"\n\n maintainDamageReportForm.getDamageStatus()..---------------",
					maintainDamageReportForm.getDamageStatus());
			maintainDamageReportForm.setDamageStatus("N");
			uldDamageRepairDetailsVO.setDamageStatus("N");
		}
		log.log(Log.FINE,
				"\n\n maintainDamageReportForm.getDamageStatus()..---------------",
				maintainDamageReportForm.getDamageStatus());
		log.log(Log.FINE, "\n\n uldDamageVOAfter.size()..---------------",
				uldDamageVOAfter.size());
		log.log(Log.FINE, "\n\n uldDamageVOAfter..---------------",
				uldDamageVOAfter);
		// added by a-3045 for bug 17989 ends
		if (!isDmgPresent) {
			log.log(Log.FINE, "\n\n DMGNOTPRESENT....");
			maintainDamageReportForm.setScreenStatusValue("DMGNOTPRESENT");
			// maintainDamageReportForm.setScreenStatusFlag("DMGNOTPRESENT");
		}
		// added by nisha for bug fix on 03Mar08
		maintainDamageReportForm.setScreenReloadStatus("reload");
		maintainDamageReportForm.setStatusFlag("uld_def_delete_dmg_success");
		invocationContext.target = ACTION_SUCCESS;
	}

}
