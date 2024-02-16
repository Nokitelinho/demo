/*
 * SelectFromCmdtyLovCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.commodity.vo.CommoditySccVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.CommodityLovForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class SelectFromCmdtyLovCommand extends BaseCommand {
	
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
		handleGetSelectedData(session,(CommodityLovForm)invocationContext.screenModel);
		invocationContext.target = "screenload_success";
		log.exiting("SelectFromTransLovCommand","execute");
		
	}
	/**
	 * The method to handle the data selected from the LOV
	 * The method retrieves the selected Commodities and add them in collection
	 * The collection is added to the session
	 * @param session
	 * @param form
	 * @param maintainProductSessionInterface
	 */	
	private void handleGetSelectedData(MaintainProductSessionInterface session,
			CommodityLovForm form){
		String[] selectedCustGrps = form.getCommodityChecked();
		Collection<CommoditySccVO> selected = new ArrayList<CommoditySccVO>();
		Page<CommoditySccVO> listInSession = session.getCommodityLovVOs();
		for(int i=0;i<selectedCustGrps.length;i++){
			for(CommoditySccVO vo :listInSession ){
				if(vo.getCommodityCode().equals(selectedCustGrps[i])){
					selected.add(vo);
				}			
			}
		}
		session.setSelectedComodityLovVOs(selected);
		form.setSelectedData("Y");
		session.setCommodityLovVOs(null);
		form.setCommodityChecked(null);
		
	}
}