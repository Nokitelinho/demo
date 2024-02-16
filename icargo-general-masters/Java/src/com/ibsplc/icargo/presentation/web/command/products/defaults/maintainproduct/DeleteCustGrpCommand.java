/*
 * DeleteCustGrpCommand.java Created on Oct 29, 2005
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
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
/**The Command Class to delete from Customer group table
 * @author A-1754
 */
public class DeleteCustGrpCommand extends BaseCommand {
	
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
		String[]custGroupChecked =  maintainProductForm.getCustGroupCheck();
		Collection<RestrictionCustomerGroupVO> allCustGroup = session.getProductCustGrpVOs();
		Collection<RestrictionCustomerGroupVO> newCustGruopSet = new ArrayList<RestrictionCustomerGroupVO>(
															session.getProductCustGrpVOs());
		Iterator iterator = allCustGroup.iterator();
			while(iterator.hasNext()){
				RestrictionCustomerGroupVO allVO =(RestrictionCustomerGroupVO)iterator.next();
				for(int i=0;i<custGroupChecked.length;i++){
				if(custGroupChecked[i].equals(allVO.getCustomerGroup())){
					if(ProductVO.OPERATION_FLAG_INSERT.equals(allVO.getOperationFlag())){ // Removing the customer grp added just now
						newCustGruopSet.remove(allVO);
					}else{ //Updating the customer grp of the serivce already present in DB
						newCustGruopSet.remove(allVO);
						if(!SAVEAS.equalsIgnoreCase(maintainProductForm.getMode())){
							allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE); 
							newCustGruopSet.add(allVO);							
						}
					}
				}
			}
				
		}
		if(newCustGruopSet.size()==0){
			session.setProductCustGrpVOs(null);
		}else{
			session.setProductCustGrpVOs(newCustGruopSet);
		}
		invocationContext.target = "screenload_success";
		
	}

}
