/*
 * DeleteServiceCommand.java Created on Oct 29, 2005
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
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
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
public class DeleteServiceCommand extends BaseCommand {
	
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
		String[]sercivesChecked =  maintainProductForm.getProductServiceCheck();
		Collection<ProductServiceVO> allServices = session.getProductServiceVOs();
		Collection<ProductServiceVO> newServiceSet =
					new ArrayList<ProductServiceVO>(session.getProductServiceVOs());
		Iterator iterator = allServices.iterator();
			while(iterator.hasNext()){
				ProductServiceVO allVO =(ProductServiceVO)iterator.next();
				for(int i=0;i<sercivesChecked.length;i++){
				if(sercivesChecked[i].equals(allVO.getServiceCode())){
					if(ProductVO.OPERATION_FLAG_INSERT.equals(allVO.getOperationFlag())){ // Removing the service added just now
						newServiceSet.remove(allVO);
					}else{ //Updating the Flag of the serivce already present in DB
						newServiceSet.remove(allVO);
						if(!SAVEAS.equalsIgnoreCase(maintainProductForm.getMode())){
							allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE); 
							newServiceSet.add(allVO);							
						}
					}
				}
			}
				
			}
			if(newServiceSet.size()==0){
				session.setProductServiceVOs(null);
			}else{
				session.setProductServiceVOs(newServiceSet);
			}	
		invocationContext.target = "screenload_success";
		
	}

}
