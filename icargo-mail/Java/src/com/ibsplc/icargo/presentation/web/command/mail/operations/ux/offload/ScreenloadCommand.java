package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.offload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OffloadModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadDSNFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadMailFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.offload.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	13-Feb-2019	:	Draft
 */
public class ScreenloadCommand extends AbstractCommand {

	
     private Log log = LogFactory.getLogger("Mail operations offload ");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREENID = "mail.operations.ux.offload";
	
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		log.entering("ScreenloadCommand", "execute");
		OffloadModel offloadModel = (OffloadModel) actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		LogonAttributes logonAttributes = getLogonAttribute();
		OffloadFilter offloadFilter = new OffloadFilter(); 
		OffloadDSNFilter offloadDSNFilter = new OffloadDSNFilter();
		OffloadMailFilter offloadMailFilter = new OffloadMailFilter();
		offloadFilter.setUpliftAirport(logonAttributes.getAirportCode());
		offloadDSNFilter.setUpliftAirport(logonAttributes.getAirportCode());
		offloadMailFilter.setUpliftAirport(logonAttributes.getAirportCode());
		
		offloadModel.setOffloadFilter(offloadFilter);
		offloadModel.setOffloadDSNFilter(offloadDSNFilter);
		offloadModel.setOffloadMailFilter(offloadMailFilter);
		
		//For one times
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
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
		
	    offloadModel.setOneTimeValues(MailOperationsModelConverter.constructOneTimeValues(oneTimeValues));
	    
	    
		responseVO.setStatus("success");
		 List<OffloadModel> results = new ArrayList();
		 results.add(offloadModel);
		 responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		log.exiting("ScreenloadCommand", "execute");
	}
	
	 private Collection<String> getOneTimeParameterTypes()
	  {
	    Collection<String> parameterTypes = new ArrayList();
	    parameterTypes.add("mailtracking.defaults.mailcategory");
	    parameterTypes.add("mailtracking.defaults.mailclass");
	    parameterTypes.add("mailtracking.defaults.containertype");
	    parameterTypes.add("mailtracking.defaults.offload.reasoncode");
	    
	      
	    return parameterTypes;
	  }
}
