/*
 * UpdateULDAgreementCommand.java.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;

import java.util.ArrayList;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AddULDAgreementForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 *
 */
public class UpdateULDAgreementCommand extends AddAgreementDetailsCommand {
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
    private static final String MODULE_NAME = "uld.defaults";
    
    private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";
    
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    	log.entering("UpdateUldAgreementCommand","-------uldmnagement");
    	AddULDAgreementForm addULDAgreementForm = (AddULDAgreementForm)
    	invocationContext.screenModel;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,SCREEN_ID);
		String[] check = addULDAgreementForm.getSelectedChecks();
		/*ArrayList<ULDAgreementDetailsVO> uldAgreementDetailsVOs = 
			(ArrayList<ULDAgreementDetailsVO>)session.getUldAgreementDetails().getUldAgreementDetailVOs();*/
		ArrayList<ULDAgreementDetailsVO> selectedDetails = new ArrayList<ULDAgreementDetailsVO>();
		//Added by A-8445
		Page<ULDAgreementDetailsVO> pageULDAgreementDetailsVO = session.getUldAgreementPageDetails();
		int increment=0;
		log.log(Log.INFO,"length------->"+check.length);
		for(ULDAgreementDetailsVO detailsVO:pageULDAgreementDetailsVO)	{
			
			for(int i=0;i<check.length;i++){
				if(check[i]!=null && (!"".equals(check[i]))){
					if(increment==Integer.parseInt(check[i])){
						selectedDetails.add(detailsVO);
					}
				}
			}
			increment++;
		}
		log.log(Log.FINE, "selectedDetails ---> " + selectedDetails);
		session.setUldAgreementVOs(selectedDetails);
		
		populateNewDetails(selectedDetails.get(0), addULDAgreementForm);
		
		/*
		 * update the form variables for navigation
		 */
    	addULDAgreementForm.setDisplayPage("1");
    	addULDAgreementForm.setCurrentPage("1");
    	addULDAgreementForm.setTotalRecords(String.valueOf(selectedDetails.size()));
    	addULDAgreementForm.setLastPageNumber(String.valueOf(selectedDetails.size()));
		invocationContext.target=SCREENLOAD_SUCCESS;
		
    
    }
}
		

