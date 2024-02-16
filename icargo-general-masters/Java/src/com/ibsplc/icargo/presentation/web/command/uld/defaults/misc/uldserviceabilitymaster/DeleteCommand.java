/* DeleteCommand.java Created on Sep 01,2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldserviceabilitymaster;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDServiceabilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to delete serviceability
 * @author A-2052
 */
public class DeleteCommand extends BaseCommand {

	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("DeleteCommand");

	private static final String SCREENID = "uld.defaults.uldserviceability";

	private static final String MODULE = "uld.defaults";

	private static final String DELETE_SUCCESS = "deleteRow_success";

	private static final String OPERATION_FLAG_INS_DEL = "operation_flg_insert_delete";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("DeleteCommand", "execute");

		ULDServiceabilityForm form = (ULDServiceabilityForm) invocationContext.screenModel;
		ULDServiceabilityMasterSession session = getScreenSession(MODULE,
				SCREENID);
		ArrayList<ULDServiceabilityVO> uldServiceabilityVOColl = new ArrayList<ULDServiceabilityVO>();
		ArrayList<ULDServiceabilityVO> uldServiceabilityVOs = session
				.getULDServiceabilityVOs() != null ? new ArrayList<ULDServiceabilityVO>(
				session.getULDServiceabilityVOs())
				: new ArrayList<ULDServiceabilityVO>();
		ArrayList<ULDServiceabilityVO> uldServiceabilityVOtmp = new ArrayList<ULDServiceabilityVO>();
		String[] rowIds = form.getSelectedRows();
		if (uldServiceabilityVOs != null && uldServiceabilityVOs.size() > 0) {
			String[] facilityCode = form.getServiceCode();
			String[] description = form.getServiceDescription();
			
			if (facilityCode.length - 1 >= 0) {
				int index = 0;
				for (ULDServiceabilityVO uldServiceabilityVO : uldServiceabilityVOs) {
					if ((uldServiceabilityVO.getOperationFlag() != null && !uldServiceabilityVO
							.getOperationFlag().equals(
									AbstractVO.OPERATION_FLAG_DELETE))
							|| uldServiceabilityVO.getOperationFlag() == null) {
						if (!uldServiceabilityVO.getCode().equals(
								facilityCode[index])
								|| !uldServiceabilityVO.getDescription()
										.equals(description[index])) {
							if (uldServiceabilityVO.getOperationFlag() == null) {
								uldServiceabilityVO
										.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
							}
						}
						if (facilityCode[index] != null
								&& facilityCode[index].trim().length() != 0) {
							uldServiceabilityVO.setCode(facilityCode[index]
									.toUpperCase());
						}
						if (description[index] != null
								&& description[index].trim().length() != 0) {
							uldServiceabilityVO
									.setDescription(description[index]);
						}

					}
					uldServiceabilityVOColl.add(uldServiceabilityVO);
					index++;
				}
			}
		}
		if (rowIds != null) {
			int index = 0;
			for (ULDServiceabilityVO vo : uldServiceabilityVOColl) {
				for (int i = 0; i < rowIds.length; i++) {
					if (index == Integer.parseInt(rowIds[i])) {
						if (vo.getOperationFlag() != null
								&& !vo.getOperationFlag().equals(
										AbstractVO.OPERATION_FLAG_INSERT)) {
							vo
									.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
						}
						if (vo.getOperationFlag() == null) {
							vo
									.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
						}
						if (vo.getOperationFlag() != null
								&& vo.getOperationFlag().equals(
										AbstractVO.OPERATION_FLAG_INSERT)) {
							vo.setOperationFlag(OPERATION_FLAG_INS_DEL);
						}

					}
				}
				index++;
			}
		}

		for (ULDServiceabilityVO uldServiceabilityVOTmp : uldServiceabilityVOColl) {
			if (uldServiceabilityVOTmp.getOperationFlag() != null
					&& !uldServiceabilityVOTmp.getOperationFlag().equals(
							OPERATION_FLAG_INS_DEL)) {
				uldServiceabilityVOtmp.add(uldServiceabilityVOTmp);
			}
			if (uldServiceabilityVOTmp.getOperationFlag() == null) {
				uldServiceabilityVOtmp.add(uldServiceabilityVOTmp);
			}

		}
		session.setULDServiceabilityVOs(uldServiceabilityVOtmp);
		invocationContext.target = DELETE_SUCCESS;
	}
}
