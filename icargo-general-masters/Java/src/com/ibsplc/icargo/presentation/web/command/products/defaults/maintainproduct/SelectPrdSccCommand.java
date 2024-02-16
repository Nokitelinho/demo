/*
 * SelectPrdSccCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCLovVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
/**
 * 
 * @author A-1754
 *
 */
public class SelectPrdSccCommand extends BaseCommand {
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */
		public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
		MaintainProductForm maintainProductForm= (MaintainProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");		
		 			
		Collection<ProductSCCVO> listAlreadySelected =session.getProductSccVOs();
		Collection<SCCLovVO> scc = session.getSelectedSccLovVOs();
		session.removeSelectedSccLovVOs();
		session.removeNextAction();
		if(scc!=null){ 
			Collection<ProductSCCVO> selectedListFromLov
							= getNewlySelectedScc(scc);
				
			if(listAlreadySelected==null){
				session.setProductSccVOs(selectedListFromLov);
			}else{
						
			Collection<ProductSCCVO> newSetOfList =
									new ArrayList<ProductSCCVO>(listAlreadySelected);
			
			for(ProductSCCVO newVO : selectedListFromLov){
				boolean isPresent = false;
				for(ProductSCCVO oldVO : listAlreadySelected){
					if(!ProductVO.OPERATION_FLAG_DELETE.equals(oldVO.getOperationFlag())){
					if(oldVO.getScc().equalsIgnoreCase(newVO.getScc())){
						isPresent = true;
					}
					}
				}
				if(!isPresent){
					newSetOfList.add(newVO);
				}
			}
			session.setProductSccVOs(newSetOfList);
			}	
					
		}	
		resetLovAction(maintainProductForm);
		invocationContext.target = "screenload_success";
	
		}
	    
	    /**
	     * The function converts the VO seleced from the LOV to Product Specific VO
	     * @param selectedList
	     * @return Collection<ProductSCCVO> list
	     */
	    private Collection<ProductSCCVO> getNewlySelectedScc(Collection<SCCLovVO> selectedList) {
	    	Collection<ProductSCCVO> list = new ArrayList<ProductSCCVO>();
	    	if(selectedList!=null){
	    	for(SCCLovVO vo:  selectedList){
	    		ProductSCCVO newVO = new ProductSCCVO();
	    		newVO.setScc(vo.getSccCode());
	    		newVO.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
	    		list.add(newVO);
	    		
	    	}
	    	}
	    	return list;
	    }

	    /**
	     * The function to reset the form fileds used to display the LOV
	     *@param mintainProductForm
	     *@return void
	     *@author A-1754
	     *@exception none
	     */
	    
	    private void resetLovAction(MaintainProductForm mintainProductForm){
	    	mintainProductForm.setLovAction("");
	    	mintainProductForm.setNextAction("");
	    	mintainProductForm.setParentSession("");
	    }
		    
	}
