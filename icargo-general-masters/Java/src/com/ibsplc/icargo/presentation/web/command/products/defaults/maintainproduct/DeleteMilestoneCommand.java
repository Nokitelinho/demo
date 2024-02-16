/*
 * DeleteMilestoneCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import java.util.ArrayList;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
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
public class DeleteMilestoneCommand extends BaseCommand {
	
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
		String[] milestone =  maintainProductForm.getMileStoneRowId();
		ArrayList<ProductEventVO> allMilestone = (ArrayList<ProductEventVO>)session.getProductEventVOs();
		ArrayList<ProductEventVO> newMilestoneSet = new ArrayList<ProductEventVO>
													(session.getProductEventVOs());
		for(int i=0;i<milestone.length;i++){
				int count = Integer.parseInt(milestone[i]);
				ProductEventVO vo = (ProductEventVO)allMilestone.get(count);
					if(ProductVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){ // Removing the milestone added just now
						newMilestoneSet.remove(vo);
					}else{ //Updating the milestone of the serivce already present in DB
						newMilestoneSet.remove(vo);
						if(!SAVEAS.equalsIgnoreCase(maintainProductForm.getMode())){
							vo.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
							newMilestoneSet.add(vo);
						}
					}
				}
									
		if(newMilestoneSet.size()==0){
			session.setProductEventVOs(null);
		}else{
			session.setProductEventVOs(newMilestoneSet);
		}	
		invocationContext.target = "screenload_success";
		
	}

}
