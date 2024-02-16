/*
 * UpdateSubProductCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
/**
 * This command updates the selcted subprodut from the sub-product mapping screen
 * @author A-1754
 *
 */
public class UpdateSubProductCommand extends BaseCommand {
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
		String[] checkedSubProducts = maintainProductForm.getSubProductChecked();
		Collection<SubProductVO> subProductVOInSession = session.getSubProductVOs();
		Collection<SubProductVO> subProductVOToUpdate = new ArrayList<SubProductVO>();
		
		for(SubProductVO subProductVO : subProductVOInSession){
			subProductVO.setProductPriority(findOneTimeCode(session.getPriorityOneTIme(),subProductVO.getProductPriority()));
			for(int i=0;i<checkedSubProducts.length;i++){
				if(subProductVO.getSubProductCode().equals(checkedSubProducts[i])){
					subProductVOToUpdate.add(subProductVO);		
				}
			}
		}
		session.setSubProductVOs(null);		
		saveSubProductDetails(subProductVOToUpdate);			
			
		invocationContext.target = "screenload_success";
		
	}

	/**
	 * Method to save the subproduct vo 
	 * @param subProductVos
	 */
	private void saveSubProductDetails(Collection<SubProductVO> subProductVos){
		try{
		new ProductDefaultsDelegate().saveSubProductDetails(subProductVos);
		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
		}
	}
	/**
	 * This method will the dstatus escription corresponding to the value from
	 * onetime
	 *
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeCode(Collection<OneTimeVO> oneTimeVOs,
			String status) {
		for (OneTimeVO oneTimeVO : oneTimeVOs) {
			if (status.equals(oneTimeVO.getFieldDescription())) {
				return oneTimeVO.getFieldValue();
			}
		}
		return null;
	}
    
	   
}
