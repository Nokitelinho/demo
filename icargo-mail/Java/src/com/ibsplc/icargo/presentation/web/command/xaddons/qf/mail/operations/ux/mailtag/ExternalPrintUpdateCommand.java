/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.mailtag.ExternalPrintUpdateCommand.java
 *
 *	Created by	:	A-9090
 *	Created on	:	14-Aug-2023
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.mailtag;

import java.util.Arrays;
import java.util.Objects;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PrintMailTagForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.mailtag.ExternalPrintUpdateCommand.java
 *	This class is used for
 */
public class ExternalPrintUpdateCommand extends BaseCommand{
	private static final Log LOGGER = LogFactory.getLogger("OPERATIONS PrintCommand");
	
	   private static final String EXTERNAL_PRINT_PREVIEW = "external_print_preview";
		 	/**
		 	 * 
		 	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
		 	 *	Added on 			: 15-Aug-2023
		 	 * 	Used for 	:
		 	 *	Parameters	:	@param invocationContext
		 	 *	Parameters	:	@throws CommandInvocationException
		 	 */
			public void execute(InvocationContext invocationContext) throws CommandInvocationException {

				LOGGER.entering("ExternalPrintUpdateCommand", "execute");

				PrintMailTagForm printMailTagForm = (PrintMailTagForm) invocationContext.screenModel;

				if (Objects.equals(EXTERNAL_PRINT_PREVIEW, printMailTagForm.getFlag())) {
					String[] opFlag = printMailTagForm.getOpFlag();
					String[] mailbagId = printMailTagForm.getMailbagId();
					String[] resultArray = Arrays.stream(opFlag).filter(AbstractVO.OPERATION_FLAG_INSERT::equals)
							.toArray(String[]::new);
					String selectedMailbagID = "";

					if (Objects.nonNull(resultArray) && Objects.equals(resultArray.length, 1)) {
						printMailTagForm.setValidPrintRequest(true);
						for (int i = 0; i < opFlag.length; i++) {
							if (AbstractVO.OPERATION_FLAG_INSERT.equals(opFlag[i])) {
								selectedMailbagID = mailbagId[i];
								break;
							}
						}

					} else {
						printMailTagForm.setValidPrintRequest(false);
					}

					printMailTagForm.setSelectedMailBagId(selectedMailbagID);

				}

				invocationContext.target = "screenload_success";
				LOGGER.exiting("ExternalPrintUpdateCommand", "execute");
			}

}
