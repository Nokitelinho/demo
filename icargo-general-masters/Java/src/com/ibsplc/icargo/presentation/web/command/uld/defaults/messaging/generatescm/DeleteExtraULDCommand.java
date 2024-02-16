/* DeleteExtraULDCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.generatescm;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1862
 */
public class DeleteExtraULDCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("DeleteExtraULDCommand");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.generatescm";

	private static final String DELETE_SUCCESS = "deleteRow_success";

    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("DeleteExtraULDCommand", "execute");
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String compCode = logonAttributes.getCompanyCode();

		GenerateSCMSession generateSCMSession = (GenerateSCMSession) getScreenSession(
				MODULE, SCREENID);

		GenerateSCMForm generateSCMForm = (GenerateSCMForm) invocationContext.screenModel;

		log
				.log(
						Log.FINE,
						"\n\n\n\n generateSCMSession.getExtraUld() BEFORE DELETE ---> ",
						generateSCMSession.getSystemStock());
		ArrayList<ULDVO> extraULDs = generateSCMSession.getSystemStock() != null ? new ArrayList<ULDVO>(
				generateSCMSession.getSystemStock())
				: new ArrayList<ULDVO>();


		if (extraULDs != null && extraULDs.size() > 0) {
			String[] extraULDForm = generateSCMForm.getExtrauld();

			int index = 0;
			for (ULDVO uldVO : extraULDs) {
				uldVO.setUldNumber(extraULDForm[index].toUpperCase());
				index++;
			}
		}

		

		String[] rowIds = generateSCMForm.getSelectedSysStock();
		ArrayList<ULDVO> uldVOsToRemove = new ArrayList<ULDVO>();
		if (rowIds != null) {
			log.log(Log.FINE, "Selected length---------------->", rowIds.length);
			int delIndex = 0;
			for (ULDVO uldVo : extraULDs) {

				for (int i = 0; i < rowIds.length; i++) {
					if (delIndex == Integer.parseInt(rowIds[i])) {
						uldVOsToRemove.add(uldVo);
					}
					
				}
				delIndex++;
			}
			log.log(Log.FINE, "\n\n\nuldVOs to remove", uldVOsToRemove);
			if (uldVOsToRemove != null && uldVOsToRemove.size() > 0) {
				extraULDs.removeAll(uldVOsToRemove);
			}

		} 

		log.log(Log.FINE, "\n\n\n\n extraULDs AFETR DELETE ---> ", extraULDs);
		Page<ULDVO> ULDpage = new Page<ULDVO>(extraULDs,0,0,0,0,0,false);
		generateSCMSession.setSystemStock(ULDpage);
		invocationContext.target = DELETE_SUCCESS;
	}
}
