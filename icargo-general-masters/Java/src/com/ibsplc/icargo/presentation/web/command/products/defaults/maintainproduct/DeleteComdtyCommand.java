/*
 * DeleteComdtyCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * The Command Class to delete from commodity table
 * @author A-1754
 *
 */
public class DeleteComdtyCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String	SAVEAS = "saveas";
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("DeleteComdtyCommand","execute");
		MaintainProductForm maintainProductForm= (MaintainProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		String[]cmdtyChecked =  maintainProductForm.getCommodityCheck();
		Collection<RestrictionCommodityVO> allComdty = session.getProductCommodityVOs();
		Collection<RestrictionCommodityVO> newCmdtySet = 
			new ArrayList<RestrictionCommodityVO>(session.getProductCommodityVOs());
		Iterator iterator = allComdty.iterator();
			while(iterator.hasNext()){
				RestrictionCommodityVO allVO =(RestrictionCommodityVO)iterator.next();
				for(int i=0;i<cmdtyChecked.length;i++){
				if(cmdtyChecked[i].equals(allVO.getCommodity())){
					if(ProductVO.OPERATION_FLAG_INSERT.equals(allVO.getOperationFlag())){ // Removing the cmdty added just now
						newCmdtySet.remove(allVO);
					}else{ //Updating the cmdty of the serivce already present in DB
						newCmdtySet.remove(allVO);
						if(!SAVEAS.equalsIgnoreCase(maintainProductForm.getMode())){
							allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE); 
							newCmdtySet.add(allVO);							
						}
					}
				}
			}
				
		}
		if(newCmdtySet.size()==0){
			session.setProductCommodityVOs(null);
		}else{
			session.setProductCommodityVOs(newCmdtySet);
		}	
		invocationContext.target = "screenload_success";
		log.exiting("DeleteComdtyCommand","execute");
		
	}

}
