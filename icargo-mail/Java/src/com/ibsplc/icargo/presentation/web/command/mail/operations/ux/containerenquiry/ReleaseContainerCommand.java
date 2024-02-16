/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ReassignContainerCommand.java
 *
 *	Created by	:	a-7779
 *	Created on	:	26-Sep-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ReassignContainerCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	26-Sep-2018	:	Draft
 */
public class ReleaseContainerCommand extends AbstractCommand{
	
	 private Log log = LogFactory.getLogger("MAIL");


	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		log.entering("ReleaseContainerCommand","execute"); 
		ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel(); 
		Collection<ContainerDetails> selectedContainerData = listContainerModel.getSelectedContainerData();
		LogonAttributes logonAttributes =  getLogonAttribute();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = null;
		
    	
    	
    		if(!selectedContainerData.isEmpty() &&selectedContainerData.size()==1){
    			for(ContainerDetails container:selectedContainerData){	
        	if ("N".equals(container.getTransitFlag())){
    			
        			ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.releasecontainer.msg.err.alreadyreleased");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors = new ArrayList<>();
					errors.add(errorVO);
					break;
        		
        	}
    			}
        	}else{
        		for(ContainerDetails container:selectedContainerData){	
                	
        		if ("N".equals(container.getTransitFlag())){
        			
        			ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.releasecontainer.msg.err.selectunreleased");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors = new ArrayList<>();
					errors.add(errorVO);
					break;
        		
        	}
        	}
        	}
    	
    	
    	if(errors!=null&&!errors.isEmpty()){
			 ErrorVO curError = errors.iterator().next();
			 actionContext.addError(curError);
			 return;
    	}
     	Collection<ContainerVO> containerVOsToBeReleased = new ArrayList<ContainerVO>();
     	for(ContainerDetails container:selectedContainerData){	
            containerVOsToBeReleased.add(MailOperationsModelConverter.constructContainerVO(container, logonAttributes)); 
     	}
     	if( !containerVOsToBeReleased.isEmpty()){
     		try {
        		
    			
     			mailTrackingDefaultsDelegate.releaseContainers(containerVOsToBeReleased);

    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}	
     	}
     	
		 ResponseVO responseVO = new ResponseVO();	  
			if (errors != null && !errors.isEmpty()) {
				ErrorVO curError = errors.iterator().next();
				actionContext.addError(curError);
				return;
			}
		 responseVO.setStatus("success");
	     actionContext.setResponseVO(responseVO); 
	     log.exiting("ReleaseContainerCommand","execute");
	    	
	}

	
		
}
