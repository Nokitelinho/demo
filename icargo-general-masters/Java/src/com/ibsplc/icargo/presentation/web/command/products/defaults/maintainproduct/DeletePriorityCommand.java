/*
 * DeletePriorityCommand.java Created on Oct 29, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
/**
 * 
 * @author A-1754
 *
 */
public class DeletePriorityCommand extends BaseCommand {
	
	private static final String	SAVEAS = "saveas";
	
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {	
		MaintainProductForm maintainProductForm= (MaintainProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");		
		String[]priorityChecked =  maintainProductForm.getPriorityCheck();
		Collection<ProductPriorityVO> allPriority = session.getProductPriorityVOs();
		Collection<ProductPriorityVO> newPrioritySet = new ArrayList<ProductPriorityVO>(
													session.getProductPriorityVOs());
		Iterator iterator = allPriority.iterator();
			while(iterator.hasNext()){
				ProductPriorityVO allVO =(ProductPriorityVO)iterator.next();
				for(int i=0;i<priorityChecked.length;i++){
				if(priorityChecked[i].equals(allVO.getPriority())){
					if(ProductVO.OPERATION_FLAG_INSERT.equals(allVO.getOperationFlag())){ // Removing the priority added just now
						newPrioritySet.remove(allVO);
					}else { //Updating the Flag of the priority already present in DB
						newPrioritySet.remove(allVO);
						if(!SAVEAS.equalsIgnoreCase(maintainProductForm.getMode())){
							allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE); 
							newPrioritySet.add(allVO);							
						}
					}
				}
			}	
		}
		if(newPrioritySet.size()==0){
			session.setProductPriorityVOs(null);
		}else{
			session.setProductPriorityVOs(newPrioritySet);
		}
			
		invocationContext.target = "screenload_success";
		
	}

}
