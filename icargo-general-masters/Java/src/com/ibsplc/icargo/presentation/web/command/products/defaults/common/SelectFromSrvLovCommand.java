/*
 * SelectFromSrvLovCommand.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.service.vo.ServiceLovVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ServiceLovForm;
//import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class SelectFromSrvLovCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("SelectFromSrvLovCommand");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SelectFromSccLovCommand","execute");
		ServiceLovForm serviceLovForm = (ServiceLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		handleGetSelectedData(serviceLovForm,session);		
		invocationContext.target = "screenload_success";
		log.exiting("SelectFromSccLovCommand","execute");
		
	}
	
	/**
	 * 
	 * @param session
	 * @param form
	 * @param httpServletRequest
	 */
	private void handleGetSelectedData(ServiceLovForm form,
			MaintainProductSessionInterface session){
		String newSelected = form.getSaveSelectedValues(); 
		log.log(Log.INFO, "newSelected ------------- ", newSelected);
		String selectedServices[] = newSelected.split(",");
		
		//String[] selectedServices = form.getServiceChecked();
		Collection<ServiceLovVO> selected = new ArrayList<ServiceLovVO>();
		Collection<ServiceLovVO> listInSession = session.getAllServiceLovVOs();
			
		for(int i=0;i<selectedServices.length;i++){
		for(ServiceLovVO vo: listInSession){
				if(vo.getServiceCode().equals(selectedServices[i])){														
					selected.add(vo);
				}			
			}
		}
		session.setSelectedServiceLovVOs(selected);
		session.removeALLServiceLovVOs();
		session.setServiceLovVOs(null);
		form.setSelectedData("Y");			
		
	}
}
