/*
 * ScreenLoadSrvLovCommand.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;



import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.shared.service.vo.ServiceFilterVO;
import com.ibsplc.icargo.business.shared.service.vo.ServiceLovVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.service.ServiceDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ServiceLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class ScreenLoadSrvLovCommand extends BaseCommand {

	
	//private static final String COMPANY_CODE="AV";
	private Log log = LogFactory.getLogger("ScreenLoadSrvLovCommand");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadSrvLovCommand","execute");
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");	
		ServiceLovForm serviceLovForm = (ServiceLovForm)invocationContext.screenModel;
		ServiceFilterVO filterVO = new ServiceFilterVO();
		filterVO.setServiceCode(serviceLovForm.getServiceCode());
		filterVO.setServiceDescription(serviceLovForm.getServiceDescription());
		filterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		Collection<ServiceLovVO> allValues = null;
		if(session.getAllServiceLovVOs()== null) {
			allValues = new ArrayList<ServiceLovVO>();
		} else {
			allValues = session.getAllServiceLovVOs(); 
		}
		log.log(Log.INFO, "allValues length-------b4------ ", allValues.size());
		Collection<ServiceLovVO> currentValues = findServiceLov(
						filterVO,Integer.parseInt(
								serviceLovForm.getDisplayPage()));
		
		if(currentValues!=null  &&  currentValues.size()!=0){
			
		for(ServiceLovVO serviceLovVO : currentValues){
			
			boolean isPresent = false;
			for(ServiceLovVO vo : allValues){
				if(vo.getServiceCode().equalsIgnoreCase(serviceLovVO.getServiceCode())){
					isPresent = true;
					break;
				}
			}
			if(!isPresent){
				allValues.add(serviceLovVO);
			}
			
			/*if(!allValues.contains(serviceLovVO)) {
				allValues.add(serviceLovVO);
			} */
			
			
			
		}
		
		log.log(Log.INFO, "currentValues length ------------- ", currentValues.size());
	}
		
		log.log(Log.INFO, "allValues length-------after------ ", allValues.size());
		session.setAllServiceLovVOs(allValues);
		session.setServiceLovVOs(
				findServiceLov(filterVO,Integer.parseInt(
						serviceLovForm.getDisplayPage())));
		//if(session.getNextAction()==null){
			session.setNextAction(serviceLovForm.getNextAction());
		//}	
		invocationContext.target = "screenload_success";
		log.exiting("ScreenLoadSrvLovCommand","execute");
	}
	/**
	 * The method to get the page of service vos
	 * @author A-1754
	 * @param serviceFilterVO
	 * @param pageNumber
	 * @return Page<ServiceLovVO> page
	 */
	
	private Page<ServiceLovVO> findServiceLov(ServiceFilterVO serviceFilterVO, int pageNumber) {
		log.entering("ScreenLoadSrvLovCommand","findServiceLov");
    	Page<ServiceLovVO> page = null;
        try{
        	page = new ServiceDelegate().findServiceLov(serviceFilterVO,pageNumber); //To be reviewed CHAGE THE FUNCTION ARGS
        }catch(BusinessDelegateException e){        	
        	e.getMessage();
        }
        log.exiting("ScreenLoadSrvLovCommand","findServiceLov");
        return page;
    }
    
}
