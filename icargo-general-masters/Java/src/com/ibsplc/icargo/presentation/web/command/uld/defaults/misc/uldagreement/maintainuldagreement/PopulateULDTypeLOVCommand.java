/*
 * PopulateULDTypeLOVCommand.java.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;


import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AddULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class PopulateULDTypeLOVCommand  extends AddAgreementDetailsCommand {
    
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
    	Log log = LogFactory.getLogger("ULD_AGREEMENT");
    	log.entering("ScreenloadCommand","------------PopulateULDTypeLOVCommand--------");
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,SCREEN_ID);
    	AddULDAgreementForm addULDAgreementForm = (AddULDAgreementForm)invocationContext.screenModel;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	ULDAgreementDetailsVO detailsVO = new ULDAgreementDetailsVO();
    	detailsVO.setOperationFlag(OPERATION_FLAG_INSERT);
    	//detailsVO.setSequenceNumber(0);
    	//To generate sequence nuber on screen load
    	Collection<ULDAgreementDetailsVO> details=session.getUldAgreementDetails()
    	.getUldAgreementDetailVOs();
    	//set the sequence number using populate sequence
    	if(details!=null){
    	detailsVO.setSequenceNumber(populateSequence(details));
    	}else{
    		detailsVO.setSequenceNumber(0);
    	}
    	ArrayList<ULDAgreementDetailsVO> detailsVOs = new ArrayList<ULDAgreementDetailsVO>();
    	detailsVOs.add(detailsVO);
    	session.setUldAgreementVOs(detailsVOs);
    	AreaDelegate areaDelegate = new AreaDelegate();
    	String defCur = "";
		try {
			defCur = areaDelegate.defaultCurrencyForStation(logonAttributes
					.getCompanyCode(), logonAttributes.getStationCode());
		} catch (BusinessDelegateException e) {
			e.getMessage();
		}
		addULDAgreementForm.setCurrencyCode(defCur);
    	
  		/*
		 * update the form variables for navigation
		 */
    	addULDAgreementForm.setDisplayPage("1");
    	addULDAgreementForm.setCurrentPage("1");
    	addULDAgreementForm.setTotalRecords(String.valueOf(detailsVOs.size()));
    	addULDAgreementForm.setLastPageNumber(String.valueOf(detailsVOs.size()));
        invocationContext.target =  SCREENLOAD_SUCCESS;
        }
    
}
