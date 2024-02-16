/*
 * ScreenLoadInventoryDetailsCommand.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.inventory;


import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDInventoryDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.ULDInventorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.InventoryULDForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 * @author a-2883
 *
 */
public class ScreenLoadInventoryDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULDMANAGEMENT");
	private static final String SCREEN_ID = 
			"uld.defaults.stock.inventoryuld";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String FLAG_S = "S";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		log.entering("ScreenLoadInventoryDetailsCommand", "execute");
		InventoryULDForm form = 
			(InventoryULDForm)invocationContext.screenModel;
		ULDInventorySession session =
			getScreenSession(MODULE_NAME, SCREEN_ID);
		String[] split = form.getChildPrimaryKey().split("~");
		form.setChildPrimaryKey(split[0]);
		form.setParentPrimaryKey(split[1]);
		
			if(session.getListInventoryULDDetails() != null &&
					session.getListInventoryULDDetails().size() > 0){
				Collection<InventoryULDVO> vos = session.getListInventoryULDDetails();
				for(InventoryULDVO vo : vos){
					for(ULDInventoryDetailsVO cvo : vo.getUldInventoryDetailsVOs()){
						if(cvo.getChildPrimaryKey().equals(form.getChildPrimaryKey())){
							form.setDetailULDType(cvo.getUldType());
							form.setDetailRequiredULD(cvo.getRequiredULD());
							form.setDetailRemarks(cvo.getRemarks());
							form.setCompanyCode(cvo.getCompanyCode());
							form.setDisplayPage(cvo.getDisplayDate());
							form.setInventoryDate(cvo.getInventoryDate().toDisplayFormat());
							form.setDisplayDate(cvo.getDisplayDate());
						}
					}
				}
			
			log.exiting("ScreenLoadInventoryDetailsCommand", "execute");
			invocationContext.target = "success";
		}
		
	}
}
