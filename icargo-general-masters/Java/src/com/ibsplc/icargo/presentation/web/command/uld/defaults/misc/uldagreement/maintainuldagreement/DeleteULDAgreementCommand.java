/*
 * DeleteULDAgreementCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;

import java.util.ArrayList;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 * 
 */
public class DeleteULDAgreementCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("Delete Command", "-------uldmnagement");
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ULDAgreementVO uldAgreementVO = session.getUldAgreementDetails();
		MaintainULDAgreementForm form = (MaintainULDAgreementForm) invocationContext.screenModel;
		ArrayList<ULDAgreementDetailsVO> agreementDetailVOs = (ArrayList<ULDAgreementDetailsVO>) uldAgreementVO
				.getUldAgreementDetailVOs();
		ArrayList<ULDAgreementDetailsVO> selected = new ArrayList<ULDAgreementDetailsVO>();
		String[] selectedValues = form.getSelectedChecks();
		
		//Added by A-8445
		Page<ULDAgreementDetailsVO> pageULDAgreementDetailsVO = session.getUldAgreementPageDetails();
		
		int selectIncrement = 0;		
		/*if (selectedValues != null && selectedValues.length > 0) {			
			for (int index = 0; index < selectedValues.length; index++) {
				log.log(Log.FINE,"\n yyy"+selectedValues[index]);
			}
			for (ULDAgreementDetailsVO detailsVo : agreementDetailVOs) {				
				for (int index = 0; index < selectedValues.length; index++) {
					if (!("").equals(selectedValues[index]) && selectIncrement == Integer.parseInt(selectedValues[index])) {
						if (AbstractVO.OPERATION_FLAG_INSERT.equals(detailsVo
								.getOperationFlag())) {
							// detailsVo.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
							selected.add(detailsVo);
							log
									.log(Log.FINE, "collection to remove"
											+ selected);
						} else {
							detailsVo
									.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
							uldAgreementVO
									.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
							
							pageULDAgreementDetailsVO.get(selectIncrement).setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
						}

					}
				}
				selectIncrement++;
			}
		}*/
		
		//Added by A-8445
		if (selectedValues != null && selectedValues.length > 0) {			
			for (int index = 0; index < selectedValues.length; index++) {
				log.log(Log.FINE,"\n yyy"+selectedValues[index]);
			}
			for (ULDAgreementDetailsVO pageDetailsVo : pageULDAgreementDetailsVO) {				
				for (int index = 0; index < selectedValues.length; index++) {
					if (!("").equals(selectedValues[index]) && selectIncrement == Integer.parseInt(selectedValues[index])) {
						if (AbstractVO.OPERATION_FLAG_INSERT.equals(pageDetailsVo
								.getOperationFlag())) {
							// detailsVo.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
							selected.add(pageDetailsVo);
							log
									.log(Log.FINE, "collection to remove"
											+ selected);
						} else {
							pageDetailsVo
									.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
							uldAgreementVO
									.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
							
							pageULDAgreementDetailsVO.get(selectIncrement).setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
						}

					}
				}
				selectIncrement++;
			}
			
			
			ArrayList<ULDAgreementDetailsVO> agreementDetailVOs1 = new ArrayList<ULDAgreementDetailsVO>(pageULDAgreementDetailsVO);
			uldAgreementVO.setUldAgreementDetailVOs(agreementDetailVOs1);
			
		}
		if (selected != null && selected.size() > 0) {
			agreementDetailVOs.removeAll(selected);
			pageULDAgreementDetailsVO.removeAll(selected);
		}

		session.setUldAgreementDetails(uldAgreementVO);
		session.setUldAgreementPageDetails(pageULDAgreementDetailsVO);
		invocationContext.target = SCREENLOAD_SUCCESS;
	}
}
