/*
 * ClearIMPCPopuUpCommand.java Created on July 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;




import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OfficeOfExchangeMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeMasterForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3108
 *	
 */
public class ClearIMPCPopuUpCommand extends BaseCommand {

	private static final String SUCCESS = "clear_success";
//	private static final String FAILURE = "add_failure";
	
	private Log log = LogFactory.getLogger("AddOECommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
							"mailtracking.defaults.masters.officeofexchange";
	
	private static final String ADD_STATUS = "ADD";	
	
	private static final String EMPTY_STRING = "";
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in ClearIMPCPopuUpCommand----------> \n\n");
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();		
		String companyCode = logonAttributes.getCompanyCode();
    	OfficeOfExchangeMasterForm oeMasterForm =
					(OfficeOfExchangeMasterForm)invocationContext.screenModel;
    	OfficeOfExchangeMasterSession oeSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);

    	
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setCountryCode(EMPTY_STRING);
		officeOfExchangeVO.setCityCode(EMPTY_STRING);
		officeOfExchangeVO.setOfficeCode(EMPTY_STRING);
		officeOfExchangeVO.setCodeDescription(EMPTY_STRING);
		officeOfExchangeVO.setPoaCode(EMPTY_STRING);
		
		officeOfExchangeVO.setActive(false);
		officeOfExchangeVO.setCode(EMPTY_STRING);
		officeOfExchangeVO.setCompanyCode(companyCode);
		oeMasterForm.setStatus(ADD_STATUS);
		officeOfExchangeVO.setOperationFlag
								(OfficeOfExchangeVO.OPERATION_FLAG_INSERT);

		oeSession.setOfficeOfExchangeVO(officeOfExchangeVO);
		oeMasterForm.setScreenStatusFlag
			(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SUCCESS;
	}

}
