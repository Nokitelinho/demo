/*
 * ScreenLoadSccLovCommand.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.scc.vo.SCCLovFilterVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCLovVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.scc.SCCDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.SccLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1754
 *
 */

public class ScreenLoadSccLovCommand extends BaseCommand {


	//private static final String COMPANY_CODE="AV";
	private Log log = LogFactory.getLogger("ScreenLoadSccLovCommand");
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
		SccLovForm sccLovActionForm = (SccLovForm)invocationContext.screenModel;
		SCCLovFilterVO filterVO = new SCCLovFilterVO();
		filterVO.setSccCode(upper(sccLovActionForm.getSccCode()));
		filterVO.setSccDescription(sccLovActionForm.getSccDescription());
		filterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		Collection<SCCLovVO> allValues = null;
		if(session.getAllSccLovVOs() == null) {
			allValues = new ArrayList<SCCLovVO>();
		} else {
			allValues = session.getAllSccLovVOs();
		}
		log.log(Log.INFO, "allValues length-------b4------ ", allValues.size());
		Collection<SCCLovVO> currentValues = findSccLov(
						filterVO,Integer.parseInt(
								sccLovActionForm.getDisplayPage()));
		if(currentValues != null && currentValues.size() > 0){
			for(SCCLovVO sCCLovVO : currentValues){

				boolean isPresent = false;
				for(SCCLovVO vo : allValues){
					if(vo.getSccCode().equalsIgnoreCase(sCCLovVO.getSccCode())){
						isPresent = true;
						break;
					}
				}
				if(!isPresent){
					allValues.add(sCCLovVO);
				}

				/*if(!allValues.contains(sCCLovVO)) {
					allValues.add(sCCLovVO);
				}*/
			}
			log.log(Log.INFO, "currentValues length ------------- ",
					currentValues.size());
		}
		log.log(Log.INFO, "allValues length-------after------ ", allValues.size());
		session.setAllSccLovVOs(allValues);

		session.setSccLovVOs(findSccLov(
						filterVO,Integer.parseInt(
								sccLovActionForm.getDisplayPage())));
		//if(session.getNextAction()==null){
			session.setNextAction(sccLovActionForm.getNextAction());
		//}
		invocationContext.target = "screenload_success";
		log.exiting("ScreenLoadSccLovCommand","execute");

	}
	/**
	 * The method to get the SCCLOV vos
	 * @param filterVO
	 * @param pageNumber
	 * @author A-1754
	 * @return
	 */
	private  Page<SCCLovVO> findSccLov(SCCLovFilterVO filterVO, int pageNumber) {
		log.entering("ScreenLoadSccLovCommand","findSccLov");
	   Page<SCCLovVO> sccPage = null;

	    try{
	    	sccPage =
	    		new SCCDelegate().findSCCLov(
	    				filterVO,pageNumber);
	    }catch(BusinessDelegateException e){

	    }
	    log.entering("ScreenLoadSccLovCommand","findSccLov");
	    return sccPage;
	}

	/**
	 * To Convert the input to Caps
	 * @param input
	 * @return
	 */
    private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}
}
