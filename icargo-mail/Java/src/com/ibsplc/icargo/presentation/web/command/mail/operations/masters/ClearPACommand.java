/*
 * ClearPACommand.java Created on June 22, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PostalAdministrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ClearPACommand extends BaseCommand {

	private static final String SUCCESS = "clear_success";
	
	private Log log = LogFactory.getLogger("ClearPACommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.masters.postaladministration";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the clear command----------> \n\n");
    	
    	PostalAdministrationForm paMasterForm =
						(PostalAdministrationForm)invocationContext.screenModel;
		PostalAdministrationSession paSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);

    	
    	paMasterForm.setPaCode("");
    	paMasterForm.setPaName("");
    	paMasterForm.setCountryCode("");
    	paMasterForm.setAddress("");
    	paMasterForm.setMessagingEnabled("Y");
    	paMasterForm.setPartialResdit(false);
    	paMasterForm.setMsgEventLocationNeeded(false);
    	//paMasterForm.setSettlementCurrencyCode("");
    	paMasterForm.setBaseType("");
    	//paMasterForm.setBillingFrequency("");
    	//paMasterForm.setBillingSource("");    	
    	paMasterForm.setCity("");
    	paMasterForm.setCountry("");
    	paMasterForm.setDebInvCode("");
    	//paMasterForm.setCusCode("");
    	paMasterForm.setEmail("");
    	paMasterForm.setFax("");
    	paMasterForm.setMobile("");
    	paMasterForm.setPhone1("");
    	paMasterForm.setPhone2("");
    	paMasterForm.setPostCod("");
    	paMasterForm.setRemarks("");
    	paMasterForm.setState("");
    	paMasterForm.setStatus("");
    	paMasterForm.setConPerson("");
    	
    	paMasterForm.setAccNum("");
    	paMasterForm.setResidtversion("");
    	//Added by A-7540
    	paMasterForm.setLatValLevel("");

    	paMasterForm.setVatNumber("");
    	paMasterForm.setDueInDays("");
    	paMasterForm.setProformaInvoiceRequired("");
    	//Added as part of IASCB-853 starts
    	paMasterForm.setSecondaryEmail1("");
    	paMasterForm.setSecondaryEmail2("");
    	//Added as part of IASCB-853 ends
    	paSession.setPaVO(null);
    	paSession.setPostalAdministrationDetailsVOs(null);
    	
    	paMasterForm.setOpFlag(OPERATION_FLAG_INSERT);
    	
    	paMasterForm.setScreenStatusFlag
     					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = SUCCESS;
    	
	}

}
