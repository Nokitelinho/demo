/*
 * SelectPrdTransModeCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;

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
public class SelectPrdTransModeCommand extends BaseCommand {
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
		 			
		Collection<ProductTransportModeVO> listAlreadySelected =session.getProductTransportModeVOs();
		Collection<OneTimeVO> mode = session.getSelectedTransModeLovVOs();
		session.removeSelectedTransModeLovVOs();
		session.removeNextAction();
		if(mode!=null){ 
			Collection<ProductTransportModeVO> selectedListFromLov
							= getNewlySelectedTransportMode(mode);
				
			if(listAlreadySelected==null){
				session.setProductTransportModeVOs(selectedListFromLov);
			}else{
						
			Collection<ProductTransportModeVO> newSetOfList =
									new ArrayList<ProductTransportModeVO>(listAlreadySelected);
			
			for(ProductTransportModeVO newVO : selectedListFromLov){
				boolean isPresent = false;
				for(ProductTransportModeVO oldVO : listAlreadySelected){
					if(!ProductVO.OPERATION_FLAG_DELETE.equals(oldVO.getOperationFlag())){
					if(oldVO.getTransportMode().equalsIgnoreCase(newVO.getTransportMode())){
						isPresent = true;
					}
					}
				}
				if(!isPresent){
					newSetOfList.add(newVO);
				}
			}
			session.setProductTransportModeVOs(newSetOfList);
			}	
					
		}	
		resetLovAction(maintainProductForm);
		invocationContext.target = "screenload_success";
	
		}
		
		   
		 /**
		  * The function converts the VO seleced from the LOV to Product Specific VO
		  * @param selectedList
		  * @author A-1754
		  * @return Collection<ProductTransportModeVO> list
		  */
		    private Collection<ProductTransportModeVO> getNewlySelectedTransportMode(Collection<OneTimeVO> selectedList) {
		    	Collection<ProductTransportModeVO> list = new ArrayList<ProductTransportModeVO>();
		    	if(selectedList!=null){
		    	for(OneTimeVO vo:  selectedList){
		    		ProductTransportModeVO newVO = new ProductTransportModeVO();
		    		newVO.setTransportMode(vo.getFieldValue());
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
