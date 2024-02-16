/*
 * DeleteSccCommand.java Created on Oct 29, 2005
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
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
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
public class DeleteSccCommand extends BaseCommand {
	
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
	
		String[]sccChecked =  maintainProductForm.getSccCheck();
		Collection<ProductSCCVO> allScc = session.getProductSccVOs();
		Collection<ProductSCCVO> newSccSet = new ArrayList<ProductSCCVO>(
				session.getProductSccVOs());
		Iterator iterator = allScc.iterator();
			while(iterator.hasNext()){
				ProductSCCVO allVO =(ProductSCCVO)iterator.next();
				for(int i=0;i<sccChecked.length;i++){
				if(sccChecked[i].equals(allVO.getScc())){
					if(ProductVO.OPERATION_FLAG_INSERT.equals(allVO.getOperationFlag())){ // Removing the scc added just now
						newSccSet.remove(allVO);
					}else{ //Updating the Flag of the scc already present in DB
						newSccSet.remove(allVO);
						if(!SAVEAS.equalsIgnoreCase(maintainProductForm.getMode())){
							allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
							newSccSet.add(allVO);							
						}
					}
				}
			}
		}
		if(newSccSet.size()==0){
			session.setProductSccVOs(null);
		}else{
			session.setProductSccVOs(newSccSet);
		}
			
		invocationContext.target = "screenload_success";
		
	}

}
