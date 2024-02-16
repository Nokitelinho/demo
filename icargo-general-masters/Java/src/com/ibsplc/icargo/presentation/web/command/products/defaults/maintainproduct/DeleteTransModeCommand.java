/*
 * DeleteTransModeCommand.java Created on Oct 29, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
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
public class DeleteTransModeCommand extends BaseCommand {
	
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
		String[]tmodeChecked =  maintainProductForm.getTransportModeCheck();
		Collection<ProductTransportModeVO> allTmode = session.getProductTransportModeVOs();
		Collection<ProductTransportModeVO> newTModeSet = new ArrayList<ProductTransportModeVO>(
																	session.getProductTransportModeVOs());
		Iterator iterator = allTmode.iterator();
			while(iterator.hasNext()){
				ProductTransportModeVO allVO =(ProductTransportModeVO)iterator.next();
				for(int i=0;i<tmodeChecked.length;i++){
				if(tmodeChecked[i].equals(allVO.getTransportMode())){
					if(ProductVO.OPERATION_FLAG_INSERT.equals(allVO.getOperationFlag())){ // Removing the tmode added just now
						newTModeSet.remove(allVO);
					}else{ //Updating the Flag of the tmode already present in DB
						newTModeSet.remove(allVO);
						if(!SAVEAS.equalsIgnoreCase(maintainProductForm.getMode())){
							allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE); 
							newTModeSet.add(allVO);							
						}
					}
				}
			}
				
		}
		if(newTModeSet.size()==0){
			session.setProductTransportModeVOs(null);
		}else{
			session.setProductTransportModeVOs(newTModeSet);
		}		
		invocationContext.target = "screenload_success";
		
	}

}
