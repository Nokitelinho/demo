/*
 * SelectPrdCustGrpCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;

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
public class SelectPrdCustGrpCommand extends BaseCommand {
	private static final String ALLOW_FLAG = "Allow";
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
		 			
		Collection<RestrictionCustomerGroupVO> listAlreadySelected =session.getProductCustGrpVOs();
		Collection<OneTimeVO> custGrp = session.getSelectedCustGrpLovVOs();
		session.removeSelectedCustGrpLovVOs();
		session.removeNextAction();
		if(custGrp!=null){ 
			Collection<RestrictionCustomerGroupVO> selectedListFromLov
							= getNewlySelectedCustGrps(custGrp,maintainProductForm);
				
			if(listAlreadySelected==null){
				session.setProductCustGrpVOs(selectedListFromLov);
			}else{
						
			Collection<RestrictionCustomerGroupVO> newSetOfList =
									new ArrayList<RestrictionCustomerGroupVO>(listAlreadySelected);
			
			for(RestrictionCustomerGroupVO newVO : selectedListFromLov){
				boolean isPresent = false;
				for(RestrictionCustomerGroupVO oldVO : listAlreadySelected){
					if(!ProductVO.OPERATION_FLAG_DELETE.equals(oldVO.getOperationFlag())){
					if(oldVO.getCustomerGroup().equalsIgnoreCase(newVO.getCustomerGroup())){
						isPresent = true;
					}
					}
				}
				if(!isPresent){
					newSetOfList.add(newVO);
				}
			}
			session.setProductCustGrpVOs(newSetOfList);
			}	
					
		}	
		resetLovAction(maintainProductForm);
		invocationContext.target = "screenload_success";
	
		}
	 /**
	  * The function converts the VO seleced from the LOV to Product Specific VO
	  * @param selectedList
	  * @param mintainProductForm
	  * @return
	  */
	    private Collection<RestrictionCustomerGroupVO> getNewlySelectedCustGrps(
	    		Collection<OneTimeVO> selectedList,
	    		MaintainProductForm mintainProductForm){
	    	Collection<RestrictionCustomerGroupVO> list = new ArrayList<RestrictionCustomerGroupVO>();
	    	String restriction = mintainProductForm.getCustGroupStatus();
	    	boolean isRestrict = true;
	    	if(restriction.equals(ALLOW_FLAG)){
	    		isRestrict = false;
	    	}
	    	if(selectedList!=null){
	    	for(OneTimeVO vo:  selectedList){
	    		RestrictionCustomerGroupVO newVO = new RestrictionCustomerGroupVO();
	    		newVO.setCustomerGroup(vo.getFieldValue());
	    		newVO.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
	    		newVO.setIsRestricted(isRestrict);
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
