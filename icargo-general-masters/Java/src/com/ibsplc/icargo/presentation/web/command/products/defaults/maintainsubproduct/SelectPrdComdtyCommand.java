/*
 * SelectPrdComdtyCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;

import com.ibsplc.icargo.business.shared.commodity.vo.CommoditySccVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;


import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-1754
 *
 */
public class SelectPrdComdtyCommand extends BaseCommand {

	private static final String ALLOW_FLAG = "Allow";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */
		public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
			log.entering("SelectPrdComdtyCommand","execute");
		MaintainSubProductForm maintainSubProductForm= (MaintainSubProductForm)invocationContext.screenModel;
		MaintainSubProductSessionInterface maintainSubProductSessionInterface = getScreenSession(
				"product.defaults", "products.defaults.maintainsubproducts");
		MaintainProductSessionInterface session=getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		Collection<RestrictionCommodityVO> listAlreadySelected =maintainSubProductSessionInterface.getCommodityVOs();
		Collection<CommoditySccVO> comdty = session.getSelectedComodityLovVOs();
	
		session.removeSelectedComodityLovVOs();
		session.removeNextAction();
	
		if(comdty!=null){ 
		
			Collection<RestrictionCommodityVO> selectedComdty
			= getSelectedCommodities(comdty,maintainSubProductForm);				
			if(listAlreadySelected==null){
				maintainSubProductSessionInterface.setCommodityVOs(selectedComdty);
			}else{						
			Collection<RestrictionCommodityVO> newSetOfList =
									new ArrayList<RestrictionCommodityVO>(listAlreadySelected);
			
			for(RestrictionCommodityVO newVO : selectedComdty){
				boolean isPresent = false;
				for(RestrictionCommodityVO oldVO : listAlreadySelected){
					if(!ProductVO.OPERATION_FLAG_DELETE.equals(oldVO.getOperationFlag())){
					if(oldVO.getCommodity().equals(newVO.getCommodity())){
						isPresent = true;
					}
					}
				}
				if(!isPresent){
					newSetOfList.add(newVO);
				}
			}
			maintainSubProductSessionInterface.setCommodityVOs(newSetOfList);
		
			}	
					
		}	
		resetLovAction(maintainSubProductForm);
		log.exiting("SelectPrdComdtyCommand","execute");
		invocationContext.target = "screenload_success";
	
		}
		
		   

	    

/**
 *  The function to convert the VOs selected from LOV's to product specific vo 
 * @param comdty
 * @param maintainSubProductForm
 * @return Collection<RestrictionCommodityVO>
 */
		private Collection<RestrictionCommodityVO> getSelectedCommodities(
				Collection<CommoditySccVO> comdty,MaintainSubProductForm maintainSubProductForm){
			Collection<RestrictionCommodityVO> comdtyList = new ArrayList<RestrictionCommodityVO>();
			if(comdty!=null){
			String restriction = maintainSubProductForm.getCommodityStatus();
			boolean isRestrict = true;
			if(restriction.equals(ALLOW_FLAG)){
				isRestrict = false;
			}
			for(CommoditySccVO lovVO : comdty){
				RestrictionCommodityVO vo = new RestrictionCommodityVO();
				vo.setCommodity(lovVO.getCommodityCode());
				
				vo.setSccCode(lovVO.getSccCode());
				vo.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
				vo.setIsRestricted(isRestrict);
				comdtyList.add(vo);
			}
			}
			return comdtyList;
			
		}
	    /**

	    /**
	     * The function to reset the form fileds used to display the LOV
	     *@param  maintainSubProductForm
	     *@return void
	     *@author A-1754
	     *@exception none
	     */
	    private void resetLovAction(MaintainSubProductForm maintainSubProductForm){
	    	maintainSubProductForm.setLovAction("");
	    	maintainSubProductForm.setNextAction("");
	    	maintainSubProductForm.setParentSession("");
	    }
		    
	}
