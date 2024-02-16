/*
 * SelectPrdPriorityCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
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
public class SelectPrdPriorityCommand extends BaseCommand {
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
		 			
		Collection<ProductPriorityVO> listAlreadySelected =session.getProductPriorityVOs();
		Collection<OneTimeVO> priority = session.getSelectedPriorityLovVOs();
		session.removeSelectedPriorityLovVOs();
		session.removeNextAction();	
		if(priority!=null){ 
			Collection<ProductPriorityVO> selectedListFromLov
							= getNewlySelectedPriority(priority);			
			if(listAlreadySelected==null){				
				session.setProductPriorityVOs(selectedListFromLov);
			}else{
						
			Collection<ProductPriorityVO> newSetOfList =
									new ArrayList<ProductPriorityVO>(listAlreadySelected);
			
			for(ProductPriorityVO newVO : selectedListFromLov){
				boolean isPresent = false;
				for(ProductPriorityVO oldVO : listAlreadySelected){
					if(!ProductVO.OPERATION_FLAG_DELETE.equals(oldVO.getOperationFlag())){
					if(oldVO.getPriority().equalsIgnoreCase(newVO.getPriority())){
						isPresent = true;
					}
					}
				}
				if(!isPresent){
					newSetOfList.add(newVO);
				}
			}
			session.setProductPriorityVOs(newSetOfList);
			}	
					
		}	
		resetLovAction(maintainProductForm);
		invocationContext.target = "screenload_success";
	
		}
	    /**
	     * The function converts the VO seleced from the LOV to Product Specific VO
	     * @param selectedList
	     * @return Collection<ProductPriorityVO> list
	     */
	    private Collection<ProductPriorityVO> getNewlySelectedPriority(Collection<OneTimeVO> selectedList) {
	    	Collection<ProductPriorityVO> list = new ArrayList<ProductPriorityVO>();
	    	if(selectedList!=null){
	    	for(OneTimeVO vo:  selectedList){
	    		ProductPriorityVO newVO = new ProductPriorityVO();
	    		newVO.setPriorityDisplay(vo.getFieldDescription());
	    		newVO.setPriority(vo.getFieldValue());
	    		newVO.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
	    		list.add(newVO);
	    		
	    	}
	    	}
	    	return list;
	    }

	   /**
	    * The function to reset the form fileds used to display the LOV
	    * @param mintainProductForm
	    */
	    private void resetLovAction(MaintainProductForm mintainProductForm){
	    	mintainProductForm.setLovAction("");
	    	mintainProductForm.setNextAction("");
	    	mintainProductForm.setParentSession("");
	    }
		    
	}
