/*
 * ScrnLdMilestoneLovCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;



import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.EventLovVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.shared.milestone.vo.MilestoneFilterVO;
import com.ibsplc.icargo.business.shared.milestone.vo.MilestoneLovVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.milestone.MilestoneDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;

import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MileStoneLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class ScrnLdMilestoneLovCommand extends BaseCommand {

	
	//private static final String COMPANY_CODE="AV";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadSccLovCommand","execute");
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");	
		MaintainSubProductSessionInterface	maintainSubProductSessionInterface = getScreenSession(
						"product.defaults", "products.defaults.maintainsubproducts");
		MileStoneLovForm mileStoneLovForm = (MileStoneLovForm)invocationContext.screenModel;
				
		MilestoneFilterVO filtervo = new MilestoneFilterVO();
		filtervo.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		filtervo.setMilestoneCode("*");
		filtervo.setMilestoneDescription("*");
		Collection<MilestoneLovVO> milestones = findMilestoneCollection(filtervo);
		Collection<ProductServiceVO> productServices = null;
	//	MaintainSubProductModel parentSubProductModel = null ;
		if("MAINTAIN_PRODUCT_SESSION".equals(mileStoneLovForm.getParentSession())){
			productServices=session.getProductServiceVOs();
		}else{
		
			productServices=maintainSubProductSessionInterface.getProductService();
		}
		
		Collection<EventLovVO> listToScreen = new ArrayList<EventLovVO>();
		if(productServices!=null){	
		for(ProductServiceVO vo: productServices){
			if(vo.isTransportationPlanExist()){
				
				EventLovVO eventServiceVO = new EventLovVO();		
				eventServiceVO.setCompanyCode(vo.getCompanyCode());
				eventServiceVO.setMilestoneCode(vo.getServiceCode());
				eventServiceVO.setMilestoneDescription(vo.getServiceDescription());
				eventServiceVO.setService(true);
				listToScreen.add(eventServiceVO);
			}
		}
		}
		if(milestones!=null){
		for(MilestoneLovVO milestoneVo : milestones){
			EventLovVO eventVO = new EventLovVO();		
			eventVO.setCompanyCode(milestoneVo.getCompanyCode());
			eventVO.setMilestoneCode(milestoneVo.getMilestoneCode());
			eventVO.setMilestoneDescription(milestoneVo.getMilestoneDescription());
			eventVO.setService(false);
			listToScreen.add(eventVO);
		}
		}
		session.setMileStoneLovVos(listToScreen);
		//if(session.getNextAction()==null){
			session.setNextAction(mileStoneLovForm.getNextAction());		
		//}	
		invocationContext.target = "screenload_success";
		log.exiting("ScreenLoadSccLovCommand","execute");
		
	}
	
	/**
	 * The method to get milestone lov vos.
	 * @param milestoneFilterVO
	 * @return Collection<MilestoneLovVO>
	 * @author A-1754
	 */
	private  Collection<MilestoneLovVO> findMilestoneCollection(MilestoneFilterVO milestoneFilterVO){
		Collection<MilestoneLovVO> milestones = null;
		try{
		 milestones = new MilestoneDelegate().findMilestoneCollection(milestoneFilterVO);
		}catch(BusinessDelegateException businessDelegateException ){
			
		}
		return milestones;
	}
	
}