/*
 * SelectMilestoneLovCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.products.defaults.vo.EventLovVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MileStoneLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class SelectMilestoneLovCommand extends BaseCommand {
	
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
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		MileStoneLovForm mileStoneLovForm = (MileStoneLovForm)invocationContext.screenModel;
		handleGetSelectedData(session,mileStoneLovForm);
		invocationContext.target = "screenload_success";
		log.exiting("SelectFromTransLovCommand","execute");
		
	}
	/**
	 * The method to pick the milestones selected by user
	 * @param session
	 * @param form
	 */

	private void handleGetSelectedData(MaintainProductSessionInterface session,
			MileStoneLovForm form){
		String[] selectedMilestones = form.getMileStoneChecked();
		Collection<EventLovVO> selected = new ArrayList<EventLovVO>();
		Collection<EventLovVO> listInSession = session.getMileStoneLovVos();
		for(int i=0;i<selectedMilestones.length;i++){
			for(EventLovVO vo:listInSession){
				if(vo.getMilestoneCode().equals(selectedMilestones[i])){
					selected.add(vo);
				}			
			}
		}
		session.setSelectedMileStoneLovVos(selected);
		session.setMileStoneLovVos(null);	
		form.setSelectedData("Y");
	}
}