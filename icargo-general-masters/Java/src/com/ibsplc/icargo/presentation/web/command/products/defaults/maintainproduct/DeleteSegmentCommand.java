/*
 * DeleteSegmentCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import java.util.ArrayList;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
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
public class DeleteSegmentCommand extends BaseCommand {
	
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
		String[] segment =  maintainProductForm.getSegmentRowId();
		ArrayList<RestrictionSegmentVO> allSegments = 
					(ArrayList<RestrictionSegmentVO>)session.getProductSegmentVOs();
		ArrayList<RestrictionSegmentVO> newSegmentSet = new ArrayList<RestrictionSegmentVO>
													(session.getProductSegmentVOs());
		for(int i=0;i<segment.length;i++){
				int count = Integer.parseInt(segment[i]);
				RestrictionSegmentVO vo = (RestrictionSegmentVO)allSegments.get(count);
					if(ProductVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){ 
						newSegmentSet.remove(vo);
					}else{ //Updating the milestone of the serivce already present in DB
						newSegmentSet.remove(vo);
						if(!SAVEAS.equalsIgnoreCase(maintainProductForm.getMode())){
							vo.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
							newSegmentSet.add(vo);
						}
					}
				}
									
		if(newSegmentSet.size()==0){
			session.setProductSegmentVOs(null);
		}else{
			session.setProductSegmentVOs(newSegmentSet);
 		}
	
		invocationContext.target = "screenload_success";
		
	}

}
