package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoicenquiry;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpareporting.InvoicEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoicenquiry.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7929	:	05-Nov-2018	:	Draft
 */
public class ScreenLoadCommand  extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("Mail Mra Invoic enquiry ");
	
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = " mail.mra.gpareporting.ux.invoicenquiry";
	
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		log.entering("ScreenloadCommand", "execute");
		InvoicEnquiryModel invoicEnquiryModel = (InvoicEnquiryModel) actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		LogonAttributes logonAttributes = getLogonAttribute();
		SharedDefaultsDelegate sharedDefaultsDelegate = 
	    	      new SharedDefaultsDelegate();
	     Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	     try
	    	 {
	    	      oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
	    	        logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
	    	    }
	    	    catch (BusinessDelegateException e)
	    	    {
	    	      actionContext.addAllError(handleDelegateException(e));
	    	    }
		
	    invoicEnquiryModel.setOneTimeValues(MailMRAModelConverter.constructOneTimeValues(oneTimeValues));
	    
	    //added by A-8464
	   /* AreaDelegate areaDelegate = new AreaDelegate();
	    Collection<AirportLovVO> airportLovs = areaDelegate.findAirportLovforHHT("",logonAttributes.getCompanyCode());
	    Collection<String> airportCodes = new ArrayList<String>();
	    
	    for(AirportLovVO airportLov:airportLovs){
	    	airportCodes.add(airportLov.getAirportCode());
	    }
	    
	    invoicEnquiryModel.setAirportCodes(airportCodes);*/
	    
	    Collection<MailSubClassVO> mailSubClassVOs = new ArrayList<MailSubClassVO>();
	    Collection<String> mailSubClassCodes = new ArrayList<String>();
	    mailSubClassVOs = new MailTrackingMRADelegate().findMailSubClass(logonAttributes.getCompanyCode(),"");
	    
	    for(MailSubClassVO mailSubClassVO:mailSubClassVOs){
	    	mailSubClassCodes.add(mailSubClassVO.getCode());
	    }
	    invoicEnquiryModel.setMailSubClassCodes(mailSubClassCodes);
	    
		responseVO.setStatus("success");
		  List<InvoicEnquiryModel> results = new ArrayList();
		  results.add(invoicEnquiryModel);
		  responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		log.exiting("ScreenloadCommand","execute");
	}
	
	 private Collection<String> getOneTimeParameterTypes()
	  {
	    Collection<String> parameterTypes = new ArrayList();
	    parameterTypes.add("mailtracking.defaults.mailsubclassgroup");
	    parameterTypes.add("mailtracking.defaults.mailclass");
	   
	    return parameterTypes;
	  }




}