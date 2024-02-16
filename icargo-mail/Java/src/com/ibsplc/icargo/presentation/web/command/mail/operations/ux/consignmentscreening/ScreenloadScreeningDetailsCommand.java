package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ConsignmentScreeningModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenloadScreeningDetailsCommand extends AbstractCommand{

	
	private Log log = LogFactory.getLogger("OPERATIONS MAIL");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
		LogonAttributes logonAttributes = getLogonAttribute();
		ConsignmentScreeningModel consignmentScreeningModel = (ConsignmentScreeningModel)actionContext.getScreenModel();
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
	     consignmentScreeningModel.setOneTimeValues(oneTimeValues);
		
	     ResponseVO responseVO = new ResponseVO();
			ArrayList<ConsignmentScreeningModel> results = new ArrayList<ConsignmentScreeningModel>();
			results.add(consignmentScreeningModel);
			responseVO.setResults(results);
		    actionContext.setResponseVO(responseVO);
		    
		    log.exiting("ScreenloadScreeningDetails","execute");
	}

	private Collection<String> getOneTimeParameterTypes() {
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add("mail.operations.screeningmethod");
		oneTimeList.add("mail.operations.securitystatuscodes");
		oneTimeList.add("mail.operations.applicableregulationtransport");
		oneTimeList.add("mail.operations.securityreasoncode");
		oneTimeList.add("mail.operations.applicableRegulationBorderAgencyAuthority");
		oneTimeList.add("mail.operations.applicableRegulationFlag");
		oneTimeList.add("mail.operations.consignorstatuscode");
		oneTimeList.add("mail.operations.results");
		oneTimeList.add("mail.operations.securitystatuscode");
		return oneTimeList;
	}
	
}
