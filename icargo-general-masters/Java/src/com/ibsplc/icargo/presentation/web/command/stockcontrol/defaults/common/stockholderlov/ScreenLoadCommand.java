/*
 * ScreenLoadCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.common.stockholderlov;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockHolderLovSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockHolderLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1927
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    		log.entering("ScreenLoadCommand","execute");

			StockHolderLovForm stockHolderLovForm = (StockHolderLovForm) invocationContext.screenModel;
			StockHolderLovSession session = getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.common.stockholderlov");

			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			session.removeAllAttributes();
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
			Map<String, Collection<OneTimeVO>> screenLoad =
			handleScreenLoadDetails(logonAttributesVO.getCompanyCode());
			if(stockHolderLovForm.getLovTxtFieldName()!=null && stockHolderLovForm.getLovTxtFieldName().trim().length()>0){
			stockHolderLovForm.setCodeName(stockHolderLovForm.getLovTxtFieldName());
			}
			//session.setCodeName(stockHolderLovForm.getCodeName());
			//session.setTypeName(stockHolderLovForm.getTypeName());
			//if(session.getStockHolderTypeValue() == null){
				//System.out.println("\n\n*************Inside if-----------");
				//session.setStockHolderTypeValue(stockHolderLovForm.getStockHolderTypeValue());	
			//}
			
			/*if(session.getPrioritizedStockHolders()!=null){
				Collection<StockHolderPriorityVO> stockHolderpriorityVos = session.getPrioritizedStockHolders();
				for(StockHolderPriorityVO stockHolderPriorityVO : stockHolderpriorityVos){
					if(stockHolderPriorityVO.getStockHolderCode().equalsIgnoreCase(stockHolderLovForm.getTypeName())){
						stockHolderLovForm.setTypeName();
					}
				}
			}
			session.setTypeName();*/

			if(screenLoad != null){
					 session.setOneTimeStock(screenLoad.get("stockcontrol.defaults.stockholdertypes"));
			}

			if(("").equalsIgnoreCase(stockHolderLovForm.getStockHolderTypeValue()) || stockHolderLovForm.getStockHolderTypeValue().length()==0){
				stockHolderLovForm.setReadOnly("N");
			}
			else{
				stockHolderLovForm.setReadOnly("Y");
			}
			//System.out.println("\n\n***************Isession.getReadOnly()*************\n\n"+session.getReadOnly());
			
			/*String[] stkhldvo = stockHolderLovForm.getStockHolder();

			if(stkhldvo!=null){
			Collection<StockHolderPriorityVO> stockHolderPriorityVOs = session.getPrioritizedStockHolders();
			int count = 0;

			for(StockHolderPriorityVO stockHolderPriority:stockHolderPriorityVOs){

						 stockHolderPriority.setStockHolderCode(upper(stkhldvo[count]));
						 count++;

			}
			session.setPrioritizedStockHolders(stockHolderPriorityVOs);


			ArrayList<StockHolderPriorityVO> stockHolderPriorityVO =
						(ArrayList<StockHolderPriorityVO>)session.getPrioritizedStockHolders();

			System.out.println("-------------------Size-----------" + stockHolderPriorityVO.size());

			System.out.println("-------------------Display Page-----------" + stockHolderLovForm.getDisplayPage());
			for(int i=((stockHolderPriorityVO.size())-1);i>=0;i--){
					StockHolderPriorityVO stkhlrVO = (StockHolderPriorityVO)stockHolderPriorityVO.get(i);
					if(stkhlrVO!=null){
						System.out.println("----------------------------" +stkhlrVO.getStockHolderCode());
						if(stkhlrVO.getStockHolderCode()!=null && !"".equals(stkhlrVO.getStockHolderCode())){
							System.out.println("----------Assigned------------------" +stkhlrVO.getStockHolderCode());
							//stockHolderName = stkhlrVO.getStockHolderCode();
							approverCode = upper(stkhlrVO.getStockHolderCode());
							stockHolderType = stkhlrVO.getStockHolderType();
							/*if(i>0){
								StockHolderPriorityVO stkhlrappVO = (StockHolderPriorityVO)stockHolderPriorityVO.get(i-1);
								approverCode=stkhlrappVO.getStockHolderCode();
							}
							System.out.println("----------Approver Code--------"+approverCode);*/
                        /*	break;
						}
					}
			}

		}*/
			ErrorVO error = null;
			/*if(stockHolderLovForm.getStockHolderType().equalsIgnoreCase("") || stockHolderLovForm.getStockHolderType().length()==0){

				System.out.println("\n\n***************Inside Error check*************\n\n");
				Object[] obj = { "stockHolder Type is blank" };
				error = new ErrorVO("stockcontrol.defaults.plsenterstockholdertype", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);

			}

			if (errors != null && errors.size() > 0) {
					System.out.println("\n\n***************Inside failure*************\n\n");
					invocationContext.addAllError(errors);
					invocationContext.target = "screenload_failure";
					return;
			}*/
			StockHolderLovFilterVO stockHolderLovFilterVO=new StockHolderLovFilterVO();
			stockHolderLovFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
			stockHolderLovFilterVO.setStockHolderCode(upper(stockHolderLovForm.getCode()));
			stockHolderLovFilterVO.setStockHolderName(stockHolderLovForm.getDescription());
			if(AbstractVO.FLAG_NO.equalsIgnoreCase(stockHolderLovForm.getReadOnly())){
				stockHolderLovFilterVO.setStockHolderType(stockHolderLovForm.getStockHolderType());
				stockHolderLovForm.setStkHolderType(stockHolderLovForm.getStockHolderType());
			}
			else{
				stockHolderLovFilterVO.setStockHolderType(stockHolderLovForm.getStockHolderTypeValue());
				stockHolderLovForm.setStkHolderType(stockHolderLovForm.getStockHolderTypeValue());
			}
			if (stockHolderLovForm.getDocumentType() != null
				&& stockHolderLovForm.getDocumentType().trim().length() > 0) {
				stockHolderLovFilterVO.setDocumentType(stockHolderLovForm.getDocumentType());
			}
			if (stockHolderLovForm.getDocumentSubType() != null
				&& stockHolderLovForm.getDocumentSubType().trim().length() > 0) {
				stockHolderLovFilterVO.setDocumentSubType(stockHolderLovForm.getDocumentSubType());
			}
			
			/*stockHolderLovFilterVO.setApproverCode(approverCode);
			if((!"".equals(approverCode) && "".equals(stockHolderLovForm.getCode())) 
					|| (!"".equals(approverCode) && !"".equals(stockHolderLovForm.getCode()))){
				stockHolderLovFilterVO.setRequestedBy(true);
			}*/
			/*System.out.println("\n\n**************stockHolderLovFilterVO***********\n\n"
					+stockHolderLovFilterVO);*/

			Page<StockHolderLovVO> stockHolderLovVOs =findStockHolderLov(stockHolderLovFilterVO,Integer.parseInt(stockHolderLovForm.getDisplayPage()));

			if(stockHolderLovVOs!=null){
				if(stockHolderLovVOs.size()==0){
					stockHolderLovVOs = null;
				}
			}
			if(stockHolderLovVOs==null||stockHolderLovVOs.size()==0){

				//System.out.println("\n\n***************Inside Error check*************\n\n");
				Object[] obj = { "stockHolderLovVOs is Null" };
				error = new ErrorVO("SKCM_091", obj);
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);

			}

			if (errors != null && errors.size() > 0) {
					//System.out.println("\n\n***************Inside failure*************\n\n");
					invocationContext.addAllError(errors);
					invocationContext.target = "screenload_failure";
					return;
			}

			session.setStockHolderLovVOs(stockHolderLovVOs);
			log.exiting("ScreenLoadCommand","execute");
		    invocationContext.target = "screenload_success";




    }

    /**
	 * The fucntion to get the StockHolder Lov Vos
	 * @param stockHolderLovFilterVO
	 * @param pageNumber
	 * @return page
	 */
	 private Page<StockHolderLovVO> findStockHolderLov(StockHolderLovFilterVO stockHolderLovFilterVO, int pageNumber) {
	    	Page<StockHolderLovVO> page = null;
	        try{
				page = new StockControlDefaultsDelegate().findStockHolderLov(stockHolderLovFilterVO,pageNumber);

	        }catch(BusinessDelegateException businessDelegateException){
	        	log.log(Log.SEVERE, "BusinessDelegateException caught from findStockHolderLov");
	        }
	        return page;
    }


    /**
	 * This method will be invoked at the time of screen load
	 *
	 * @param companyCode
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(String companyCode) {
			Map<String, Collection<OneTimeVO>> oneTimes = null;
			try {

				Collection<String> fieldTypes = new ArrayList<String>();
				fieldTypes.add("stockcontrol.defaults.stockholdertypes");

				oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode, fieldTypes);
				//Setting stock holders priority
				Collection<OneTimeVO> stockHolder = oneTimes.get("stockcontrol.defaults.stockholdertypes");
				StockHolderLovSession session = getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.common.stockholderlov");
				Collection<StockHolderPriorityVO> stockHolderpriorityVos =new StockControlDefaultsDelegate().findStockHolderTypes(companyCode);
				session.setPrioritizedStockHolders(stockHolderpriorityVos);
				populatePriorityStockHolders(stockHolderpriorityVos,stockHolder,session);
				log.log(Log.FINE,
						"------------------stockHolderpriorityVos-----------",
						stockHolderpriorityVos);


			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.SEVERE, "BusinessDelegateException caught from findStockHolderTypes");
			}
			return oneTimes;
		}

		private void populatePriorityStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos,
				                    Collection<OneTimeVO> stockHolder,StockHolderLovSession session){
				if(stockHolderpriorityVos!=null){
					for(StockHolderPriorityVO priorityVO : stockHolderpriorityVos){
						for(OneTimeVO onetime : stockHolder){
							if(onetime.getFieldValue().equals(priorityVO.getStockHolderType())){
								priorityVO.setStockHolderDescription(onetime.getFieldDescription());
							}
						}
					}
						session.setPrioritizedStockHolders(stockHolderpriorityVos);
			}
			//Collection<StockHolderPriorityVO> stockHolderVos=session.getPrioritizedStockHolders();
	}


	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}


}
