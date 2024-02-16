/*
 * SelectFromTransLovCommand.java Created on Oct 29, 2005
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
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.TransportModeLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class SelectFromTransLovCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SelectFromTransLovCommand","execute");
		TransportModeLovForm transportModeLovForm= (TransportModeLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		handleGetSelectedData(transportModeLovForm,session);		
		invocationContext.target = "screenload_success";
		log.exiting("SelectFromTransLovCommand","execute");
		
	}
	/**
	 * The method to get the selected Transport mode VOS and put in session
	 * @param form
	 * @param session
	 * @author A-1754
	 */
	private void handleGetSelectedData(
			TransportModeLovForm form,
			MaintainProductSessionInterface session){
		log.entering("SelectFromTransLovCommand","handleGetSelectedData");
		String[] selectedtransportmodes = form.getTransportModeChecked();
		Collection<OneTimeVO> selected = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> listInSession = session.getTransModeLovVOs();
		for(int i=0;i<selectedtransportmodes.length;i++){
			for(OneTimeVO vo: listInSession){
				if(vo.getFieldValue().equals(selectedtransportmodes[i])){					
					selected.add(vo);
				}			
			}
		}
		session.setSelectedTransModeLovVOs(selected);
		form.setSelectedData("Y");
		session.setTransModeLovVOs(null);
		log.exiting("SelectFromTransLovCommand","handleGetSelectedData");
	}
}