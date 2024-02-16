/*
 * ScreenLoadComdtyLovCommand.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommoditySccVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.CommodityLovForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class ScreenLoadComdtyLovCommand extends BaseCommand {
	
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
		log.entering("ScreenLoadComdtyLovCommand","execute");			
		//MaintainSubProductModel parentSubProductModel ;
		Collection<ProductSCCVO> productScc = new ArrayList<ProductSCCVO>();
		Collection<String> sccCodes=null;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");	
		MaintainSubProductSessionInterface
			subProductSessionInterface  = getScreenSession(
				"product.defaults", "products.defaults.maintainsubproducts");
		CommodityLovForm commodityForm = (CommodityLovForm)invocationContext.screenModel;
		if("MAINTAIN_PRODUCT_SESSION".equals(commodityForm.getParentSession())){
			
			
			productScc=session.getProductSccVOs();
			sccCodes = getSccCodes(productScc);
		}else{
			
			String scc=subProductSessionInterface.getProductScc();
			sccCodes = new ArrayList<String>();
			sccCodes.add(scc);
		}
		if(sccCodes.size()!=0 || sccCodes.isEmpty() == false){
			Page<CommoditySccVO> page = findCommodityLov(getApplicationSession().getLogonVO().getCompanyCode(),sccCodes,commodityForm.getCode(),
						commodityForm.getDescription(),
						Integer.parseInt(commodityForm.getDisplayPage()));
		session.setCommodityLovVOs(page);
		}else{
			session.setCommodityLovVOs(null);
		}
		//if(session.getNextAction()==null){
			session.setNextAction(commodityForm.getNextAction());			
		//}
		invocationContext.target = "screenload_success";
		log.exiting("ScreenLoadComdtyLovCommand","execute");
		
	}
	/**
	 * The method to get the SCC codes given a collection of ProductSCCVO
	 * @param productScc
	 * @return
	 */
	private Collection<String> getSccCodes(Collection<ProductSCCVO> productScc){
		Collection<String> sccCodes = new ArrayList<String>();
		if(productScc!=null){
		for(ProductSCCVO vo : productScc){
			sccCodes.add(vo.getScc());
		}
		}
		return sccCodes;
		
	}
	
	
	 
	/**
	 * @param companyCode
	 * @param sccCodes
	 * @param commodityCode
	 * @param commodityDesc
	 * @param pageNumber
	 * @return
	 */
	public Page<CommoditySccVO> findCommodityLov(String companyCode, Collection<String> sccCodes, String commodityCode, String commodityDesc, int pageNumber) {
		Page<CommoditySccVO> page = null;
	    try{
	    	page = 
	    		new CommodityDelegate().findCommodity(
	    				companyCode,sccCodes,commodityCode,commodityDesc,pageNumber);

	    }catch(BusinessDelegateException e){	    	
	    }
	    return page;
	}
}