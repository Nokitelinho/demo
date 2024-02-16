/*
 * SelectPrdServiceCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.shared.service.vo.ServiceLovVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-1754
 *
 */
public class SelectPrdServiceCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
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
		 			
		Collection<ProductServiceVO> listAlreadySelected =session.getProductServiceVOs();
		Collection<ServiceLovVO> services = session.getSelectedServiceLovVOs();
		session.removeSelectedServiceLovVOs();
		session.removeNextAction();
		if(services!=null){ 
			Collection<ProductServiceVO> selectedListFromLov
							= getNewlySelectedServices(services);
				
			if(listAlreadySelected==null){
				session.setProductServiceVOs(selectedListFromLov);
			}else{
						
			Collection<ProductServiceVO> newSetOfList =
									new ArrayList<ProductServiceVO>(listAlreadySelected);
			
			for(ProductServiceVO newVO : selectedListFromLov){
				boolean isPresent = false;
				for(ProductServiceVO oldVO : listAlreadySelected){
					if(!ProductVO.OPERATION_FLAG_DELETE.equals(oldVO.getOperationFlag())){
					if(oldVO.getServiceCode().equalsIgnoreCase(newVO.getServiceCode())){
						isPresent = true;
					}
					}
				}
				if(!isPresent){
					newSetOfList.add(newVO);
				}
			}
			session.setProductServiceVOs(newSetOfList);
			}	
					
		}	
		resetLovAction(maintainProductForm);
		invocationContext.target = "screenload_success";
	
		}
		
		   

	    
	    /**
	     * The function converts the VO seleced from the LOV to Product Specific VO
	     * @param selectedList
	     * @return @return Collection<ProductServiceVO> list
	     */
	    private Collection<ProductServiceVO> getNewlySelectedServices(Collection<ServiceLovVO> selectedList) {
	    	
	    	Collection<ProductServiceVO> list = new ArrayList<ProductServiceVO>();
	    	if(selectedList!=null){
	    	for(ServiceLovVO vo:  selectedList){
	    		ProductServiceVO newVO = new ProductServiceVO();
	    		newVO.setServiceCode(vo.getServiceCode());
	    		newVO.setTransportationPlanExist(vo.getTransportationPlanIndicator());
	    		newVO.setServiceDescription(vo.getServiceDescrption());
	    		newVO.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
	    		list.add(newVO);
	    		
	    	}
	    	}
	    	return list;
	    }
	 
	    /**
	     * The function to reset the form fileds used to display the LOV
	     *@param  mintainProductForm
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
