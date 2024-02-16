package com.ibsplc.icargo.presentation.web.command.addons.mail.operations.mailawbbooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.MailAwbBookingModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class MailBookingPopupScreenloadCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("ADDONSMAIL");
	private static final String MODULE_NAME = "addonsmail";
	private static final String SCREEN_ID = "addons.mail.operations.ux.mailawbbooking";
	private static final String KEY_SHIPMENT_ONETIME = "operations.shipment.shipmentstatus";
	
	public void execute(ActionContext actionContext) {
		
		log.entering("MailBookingPopupScreenloadCommand","execute");
		MailAwbBookingModel mailAwbBookingModel=
				(MailAwbBookingModel) actionContext.getScreenModel(); 
		ResponseVO responseVO = new ResponseVO();
		List<MailAwbBookingModel> results = new ArrayList<>();
		LogonAttributes logonAttributes = getLogonAttribute();
		
		SharedDefaultsDelegate sharedDefaultsDelegate = 
	    	      new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	     
	     	try{
	     		oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
	    	    logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
	    	  }
	    	  catch (BusinessDelegateException e)
	    	  {
	    	     actionContext.addAllError(handleDelegateException(e));
	    	  }	
		
		if(oneTimeValues != null){
			mailAwbBookingModel.setOneTimeValues(oneTimeValues);
		}

		results.add(mailAwbBookingModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);

        log.exiting("MailBookingPopupScreenloadCommand","execute");
		
	}
	
	private Collection<String> getOneTimeParameterTypes() {
		Collection<String> oneTimeList = new ArrayList<>();
		oneTimeList.add(KEY_SHIPMENT_ONETIME);
		return oneTimeList;
	}

	
}
