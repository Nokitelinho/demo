/*
 * AddCommand.java Created on Sep 01,2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldserviceabilitymaster;

import java.util.ArrayList;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDServiceabilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to add serviceability
 * 
 * @author A-2052
 */
public class AddCommand extends BaseCommand {

	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("AddCommand");

	private static final String SCREENID = "uld.defaults.uldserviceability";

	private static final String MODULE = "uld.defaults";

	private static final String ADD_SUCCESS = "addRow_success";

	private static final String BLANK = "";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("AddCommand", "execute");
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();
		ULDServiceabilityForm form = (ULDServiceabilityForm) invocationContext.screenModel;
		ULDServiceabilityMasterSession session = getScreenSession(MODULE,
				SCREENID);
		ArrayList<ULDServiceabilityVO> uldServiceabilityVOColl = new ArrayList<ULDServiceabilityVO>();
		ArrayList<ULDServiceabilityVO> uldServiceabilityVOs = session
				.getULDServiceabilityVOs() != null ? new ArrayList<ULDServiceabilityVO>(
				session.getULDServiceabilityVOs())
				: new ArrayList<ULDServiceabilityVO>();
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
		ULDServiceabilityVO newULDServiceabilityVO = new ULDServiceabilityVO();
		newULDServiceabilityVO
				.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
		newULDServiceabilityVO.setCompanyCode(compCode);
		newULDServiceabilityVO.setCode(BLANK);
		newULDServiceabilityVO.setDescription(BLANK);
		uldServiceabilityVOColl.add(newULDServiceabilityVO);
		session.setULDServiceabilityVOs(uldServiceabilityVOColl);
		invocationContext.target = ADD_SUCCESS;
	}
}
