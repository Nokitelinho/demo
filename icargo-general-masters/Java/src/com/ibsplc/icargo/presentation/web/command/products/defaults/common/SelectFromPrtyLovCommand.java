/*
 * SelectFromPrtyLovCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;


import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.PriorityLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class SelectFromPrtyLovCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SelectFromPriorityLovCommand","execute");
		PriorityLovForm priorityLovForm= (PriorityLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		handleGetSelectedData(priorityLovForm,session);		
		invocationContext.target = "screenload_success";
		log.exiting("SelectFromPriorityLovCommand","execute");
		
	}
	/** 
	 * Method is for handling the data selected from the screen table
	 * @param session
	 * @param form
	 * @param httpServletRequest
	 */
		private void handleGetSelectedData(PriorityLovForm form,
				MaintainProductSessionInterface session){
				String[] selectedPriority = form.getPriorityChecked();
				Collection<OneTimeVO> selected = new ArrayList<OneTimeVO>();
				Collection<OneTimeVO> listInSession = session.getPriorityLovVOs();
				for(int i=0;i<selectedPriority.length;i++){
				for(OneTimeVO vo : listInSession){
						if(vo.getFieldValue().equals(selectedPriority[i])){
							selected.add(vo);
						}
					}
				}
				session.setPriorityLovVOs(null);
				session.setSelectedPriorityLovVOs(selected);
				form.setSelectedData("Y");

				
		}
}