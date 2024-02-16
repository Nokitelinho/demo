/*
 * SelectFromSccLovCommand.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.scc.vo.SCCLovVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.SccLovForm;
//import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class SelectFromSccLovCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("SelectFromSccLovCommand");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SelectFromSccLovCommand","execute");
		SccLovForm sccLovActionForm = (SccLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		handleGetSelectedData(sccLovActionForm,session);		
		invocationContext.target = "screenload_success";
		log.exiting("SelectFromSccLovCommand","execute");
		
	}
	
	/**
	 * 
	 * @param session
	 * @param form
	 * @param httpServletRequest
	 */
	private void handleGetSelectedData(SccLovForm form,
			MaintainProductSessionInterface session){
		log.entering("SelectFromSccLovCommand","handleGetSelectedData");
		String newSelected = form.getSaveSelectedValues(); 
		log.log(Log.INFO, "newSelected ------------- ", newSelected);
		String selectedSccs[] = newSelected.split(",");
		log
				.log(Log.INFO, "selected length ------------- ",
						selectedSccs.length);
		//form.getSccChecked();
		Collection<SCCLovVO> selected = new ArrayList<SCCLovVO>();
		Collection<SCCLovVO> listInSession = session.getAllSccLovVOs();
		for(int i=0;i<selectedSccs.length;i++){
			for(SCCLovVO vo:listInSession){
				if(vo.getSccCode().equals(selectedSccs[i])){
					selected.add(vo);
				}			
			}
		}
		session.setSelectedSccLovVOs(selected);
		session.removeALLSccLovVOs();
		session.setAllSccLovVOs(null);
		log.log(Log.INFO, "selected set in session ------------- ", selected);
		session.setSccLovVOs(null);
		form.setSelectedData("Y");
		log.exiting("SelectFromSccLovCommand","handleGetSelectedData");
		
	}
}
