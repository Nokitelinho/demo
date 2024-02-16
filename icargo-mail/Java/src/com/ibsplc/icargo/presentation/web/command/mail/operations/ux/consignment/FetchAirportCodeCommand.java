/*
 * FetchAirportCodeCommand.java Created on Jun 12 2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8672
 * 
 */
public class FetchAirportCodeCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.consignment";

	/**
	 * This method overrides the execute method of BaseComand class
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(ActionContext actionContext) throws CommandInvocationException {

		log.entering("FetchAirportCodeCommand", "execute");
		MaintainConsignmentModel maintainConsignmentModel = (MaintainConsignmentModel) actionContext.getScreenModel();
		ArrayList results = new ArrayList();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		String companyCode=logonAttributes.getCompanyCode();
		String mailbagId= maintainConsignmentModel.getMailId();
		String [] orgDestPort=new String[2];
		orgDestPort[0]=	mailbagId.substring(0, 6);	
		orgDestPort[1]=	mailbagId.substring(6, 12);
		String orginDestcode[]=new String[2];
		Collection<OfficeOfExchangeVO> officeOEVos =new ArrayList <OfficeOfExchangeVO>();
		OfficeOfExchangeVO OfficeOfExchangevo=null;
		List<ErrorVO> errors = null;
		
		
			try {
				for(String orgDest :orgDestPort){
					if(orgDest!=null){
						OfficeOfExchangevo=	new MailTrackingDefaultsDelegate().validateOfficeOfExchange(companyCode,orgDest);
								if(OfficeOfExchangevo!=null){
									officeOEVos.add(OfficeOfExchangevo);
								}
				
					}
			}
	       }catch (BusinessDelegateException businessDelegateException) {
	 			errors = handleDelegateException(businessDelegateException);
	 	    }
	 	  	
		if(officeOEVos!=null && officeOEVos.size()>1 ){
			Iterator<OfficeOfExchangeVO> it =officeOEVos.iterator();
			int i=0;
			
			while(it.hasNext()){
			if(i==0){
			orginDestcode[0]=it.next().getAirportCode();
			}else{
			orginDestcode[1]=it.next().getAirportCode(); 
			}
			i++;
			}
			maintainConsignmentModel.setOrgDestAirCodes(orginDestcode);
			
		
		}else{
			actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.invalidOoeDoe",new Object[]{mailbagId}));
			return;
		}

		ResponseVO responseVO = new ResponseVO();	  
	    responseVO.setStatus("fetchCode_success");
	    results.add(maintainConsignmentModel);
	    responseVO.setResults(results);
	    actionContext.setResponseVO(responseVO);  
		log.exiting("FetchAirportCodeCommand","execute");
	}
}
