/*
 * ScreenLoadCommand.java Created on Jul 16, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */



package com.ibsplc.icargo.presentation.web.command.products.defaults.listsubproducts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListSubProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * ScreenloadCommand is for screenload action
 * @author A-1870
 *
 */
public class ScreenLoadCommand extends BaseCommand{
	//private static final String COMPANY_CODE = "AV";
	//Added by A-5220 for ICRD-34755 starts
	private static final String FROM_LIST_PRODUCT = "fromListProduct";
	private static final String LIST = "LIST";
	private static final String NAVIGATION = "NAVIGATION";
	//Added by A-5220 for ICRD-34755 ends
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListSubProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listsubproducts");
		ListSubProductForm listSubProductForm = (ListSubProductForm)invocationContext.screenModel;
		session.setButtonStatusFlag(listSubProductForm.getButtonStatusFlag());
		//Added by A-5220 for ICRD-34755 starts
		if(FROM_LIST_PRODUCT.equalsIgnoreCase(listSubProductForm.
				getButtonStatusFlag())){
			listSubProductForm.setDisplayPage("1");
		}
		//Added by A-5220 for ICRD-34755 ends
		ProductFilterVO productFilterVO=new ProductFilterVO();
		productFilterVO.setProductCode(listSubProductForm.getProductCode());
		productFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		listSubProductForm.setStatus("ALL");
		listSubProductForm.setTransportMode("ALL");
		listSubProductForm.setPriority("ALL");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		if(LIST.equalsIgnoreCase(listSubProductForm.getNavigationMode())){//added by a-5505 for the bug ICRD-124986
			productFilterVO.setTotalRecordsCount(-1);
		}
		productFilterVO.setPageNumber(Integer.parseInt(listSubProductForm.getDisplayPage()));

		int displayPage=Integer.parseInt(listSubProductForm.getDisplayPage());
		 try{
			 /*RequestDispatcher.startBatch();*/
				ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
				Page<SubProductVO>  subProductVO=productDefaultsDelegate.findSubProducts(productFilterVO,displayPage);
				if(subProductVO!=null){
					session.setTotalRecordsCount(subProductVO.getTotalRecordCount());
				}
				Map<String, Collection<OneTimeVO>>  oneTimes=getScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
				/*BatchedResponse[] resp = RequestDispatcher.executeBatch();

				
				if (!resp[0].hasError()){
					oneTimes =resp[0].getReturnValue();
					System.out.println("$$$$$$$$$$$$$$$$$$$$$$44getclassssssssssoneTimes$$$$$$$$$$$$$$$$44"+oneTimes.getClass());
					Set key = oneTimes.keySet();
					for(Object kTemp :key){
						Collection results = oneTimes.get(kTemp);
						for(Object obj : results){
							System.out.println("The class name is "+obj.getClass());
						}
					}
				}
				if (!resp[1].hasError()){
					subProductVO=resp[1].getReturnValue();


				}

				*/
				if(oneTimes!=null){
					Collection<OneTimeVO> resultStatus=
						oneTimes.get("products.defaults.status");
					Collection<OneTimeVO> resultTransportMode=oneTimes.get("products.defaults.transportmode");
					Collection<OneTimeVO> resultPriority=oneTimes.get("products.defaults.priority");
					session.setStatus(resultStatus);
					session.setPriority(resultPriority);
					session.setTransportMode(resultTransportMode);
				}
				Collection<OneTimeVO> oneTimeVOs =session.getStatus();
				Collection<OneTimeVO>  oneTimeVO=session.getPriority();
				if(subProductVO==null || subProductVO.size()==0){
					
					ErrorVO error = null;
					error = new ErrorVO("products.defaults.nosubproductsfound");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					if(errors!=null && errors.size()>0){
						session.setPageSubProductVO(null);
						invocationContext.addAllError(errors);
						invocationContext.target = "list_failure";
						return;
					}
				}
				if(subProductVO!=null){
					for(SubProductVO subprdVO : subProductVO){
						if(oneTimeVOs!=null){
							subprdVO.setStatus(findOneTimeDescription(oneTimeVOs,subprdVO.getStatus()));
						}
						if(oneTimeVO!=null){
							subprdVO.setProductPriority(findOneTimeDescription(oneTimeVO,subprdVO.getProductPriority()));
						}

					}
				}

				session.setPageSubProductVO(subProductVO);


		    }catch(BusinessDelegateException businessDelegateException){
				
		    	   businessDelegateException.getMessage();
		    	   errors=handleDelegateException(businessDelegateException);
			}
			 invocationContext.target = "nextscreenload_success";
	}
	/**
     * This method will be invoked at the time of screen load
     * @param companyCode
     * @return Map<String, Collection<OneTimeVO>>
     * @throws BusinessDelegateException
     */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(String companyCode)
		throws BusinessDelegateException {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<String> fieldValues = new ArrayList<String>();
		fieldValues.add("products.defaults.transportmode");
		fieldValues.add("products.defaults.status");
		fieldValues.add("products.defaults.priority");
		return new SharedDefaultsDelegate().findOneTimeValues(getApplicationSession().getLogonVO().getCompanyCode(), fieldValues);
	    }
	/**
	 * This method will the dstatus escription
	 * corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		for (OneTimeVO oneTimeVO:oneTimeVOs){
			if(status.equals(oneTimeVO.getFieldValue())){
				return oneTimeVO.getFieldDescription();
			}
		}
		return null;
	}
}
