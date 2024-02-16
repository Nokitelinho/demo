/*
 * AddSegmentCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * The Command to add rows in segment table
 * @author A-1754
 *
 */
public class AddSegmentCommand extends BaseCommand {

	private static final String ALLOW_FLAG = "Allow";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {	
		log.entering("AddSegmentCommand","execute");
		MaintainSubProductForm maintainSubProductForm= 
			(MaintainSubProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		MaintainSubProductSessionInterface maintainProductSessionInterface = getScreenSession(
				"product.defaults", "products.defaults.maintainsubproducts");
		RestrictionSegmentVO segmentVo = new RestrictionSegmentVO();
		segmentVo.setDestination("");
		segmentVo.setOrigin("");
		segmentVo.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
		String segementRestriction = maintainSubProductForm.getSegmentStatus();
		boolean hasRestriction = true;
		if(segementRestriction.equals(ALLOW_FLAG)){
			hasRestriction = false;
		}
		segmentVo.setIsRestricted(hasRestriction);	
		Collection<RestrictionSegmentVO> segmentVOs =  maintainProductSessionInterface.getSegmentVOs();
		if(segmentVOs!=null){							
			segmentVOs.add(segmentVo);
			maintainProductSessionInterface.setSegmentVOs(segmentVOs);
		}else{
			Collection<RestrictionSegmentVO> newList = new ArrayList<RestrictionSegmentVO>();
			newList.add(segmentVo);
			maintainProductSessionInterface.setSegmentVOs(newList);
		}	
		invocationContext.target = "screenload_success";
		log.exiting("AddSegmentCommand","execute");
		
	}

}
