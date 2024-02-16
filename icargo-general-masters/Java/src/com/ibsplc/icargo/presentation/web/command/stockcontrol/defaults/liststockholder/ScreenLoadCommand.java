/*
 * ScreenLoadCommand.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockholder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockHolderForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String AWB = "AWB";
	
	private static final String S = "S";	

	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		log.entering("ScreenLoadCommand","execute"); 
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		ListStockHolderSession session =
			getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.liststockholder");
		ListStockHolderForm form = (ListStockHolderForm)invocationContext.screenModel;
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		session.removeAllAttributes();
		boolean hasError = handleScreenLoadDetails(companyCode,session);
		form.setDocType("");
		form.setDocSubType("");
		if(hasError){
			log.log(Log.FINE,"<-------------------------Error Fetching data------------------------->");
			Object[] obj = { "errorindb" };
			error = new ErrorVO("stockcontrol.defaults.errorindatabase", obj); //
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			//added by A-5131 for ICRD-24891
			form.setScreenStatusFlag
			(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = "screenload_success";
		}else{
			log.log(Log.FINE,"<-------------------------Success Fetching data-------------------------->");
			//added by A-5131 for ICRD-24891
			form.setScreenStatusFlag
			(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = "screenload_success";
		}

		log.exiting("ScreenLoadCommand","execute");
	}
	
	/**
	 * For #102543
	 * @author A-2589
	 * @param session
	 * @throws BusinessDelegateException
	 * 
	 */
	private void loadSessionWithPartnerAirlines(ListStockHolderSession session) throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO=new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(airlineLovFilterVO, 1));   
	}
	/**
	 * The method to get the screenload details
	 * @param companyCode
	 * @param session
	 */
	private boolean handleScreenLoadDetails(String companyCode,ListStockHolderSession session ) {
			log.entering("ScreenLoadCommand","handleScreenLoadDetails");
			Collection<ErrorVO> errors = null;
			boolean hasError = false;
		       try{
		    	    //One times
		    	    Collection<String> fieldTypes = new ArrayList<String>();
					fieldTypes.add("stockcontrol.defaults.stockholdertypes");
					Map<String, Collection<OneTimeVO>> oneTimes =
								 new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldTypes) ;
					Collection<OneTimeVO> stockHolder = oneTimes.get("stockcontrol.defaults.stockholdertypes");
					log
							.log(
									Log.FINE,
									"\n\n StockHolder Onetime in Command--------------->",
									stockHolder);
					session.setOneTimeStock(stockHolder);
					//Document Types
					HashMap<String,Collection<String>> documentList =
						new HashMap<String,Collection<String>>(new DocumentTypeDelegate().findAllDocuments(companyCode));
					log.log(Log.FINE,
							"\n\n DocumentList  in Command--------------->",
							documentList);
					//documentList.put("",null);
					session.setDocTypeMap(documentList);

					//Setting stock holders priority
					Collection<StockHolderPriorityVO> stockHolderpriorityVos =
						 new StockControlDefaultsDelegate().findStockHolderTypes(companyCode);
					populatePriorityStockHolders(stockHolderpriorityVos,stockHolder,session);
					log
							.log(
									Log.FINE,
									"------------------stockHolderpriorityVos-----------",
									stockHolderpriorityVos);
					/*
					 * Load session with the partner airline details
					 */
					loadSessionWithPartnerAirlines(session);

			}catch(BusinessDelegateException businessDelegateException){
				log.log(Log.FINE,"\n\n<---------------------INSIDE EXCEPTION------------------------------------>");
//printStackTrrace()();
				log.log(Log.FINE,"\n\n<---------------------INSIDE EXCEPTION------------------------------------>");
				errors = handleDelegateException(businessDelegateException);
			}
			if(errors!=null && errors.size()>0){
				hasError=true;
			}
			log.exiting("ScreenLoadCommand","handleScreenLoadDetails");
			return hasError;

		 }
/**
 * 
 * @param stockHolderpriorityVos
 * @param stockHolder
 * @param session
 */
	private void populatePriorityStockHolders(
			Collection<StockHolderPriorityVO> stockHolderpriorityVos,
			Collection<OneTimeVO> stockHolder,ListStockHolderSession session){
		log.entering("ScreenLoadCommand","populatePriorityStockHolders");
		if(stockHolderpriorityVos!=null){
			for(StockHolderPriorityVO priorityVO : stockHolderpriorityVos){
				for(OneTimeVO onetime : stockHolder){
					if(onetime.getFieldValue().equals(priorityVO.getStockHolderType())){
						priorityVO.setStockHolderDescription(onetime.getFieldDescription());
					}
				}
			}
			session.setPrioritizedStockHolders(stockHolderpriorityVos);
			log.exiting("ScreenLoadCommand","populatePriorityStockHolders");
		}
	}

	



}
