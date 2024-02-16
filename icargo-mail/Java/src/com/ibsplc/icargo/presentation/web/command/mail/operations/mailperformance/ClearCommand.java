
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailperformance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class ClearCommand extends BaseCommand {

	private static final String SUCCESS = "clear_success";
	
	private Log log = LogFactory.getLogger("MailTracking,MailPerformance");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailperformance";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the clear command----------> \n\n");
    	
    	MailPerformanceForm mailperformanceForm =
							(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailperformanceSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
		    	
		mailperformanceForm.setAirport("");
		mailperformanceForm.setPacode("");
		SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	Map<String, Collection<OneTimeVO>> hashMap = 
    		      new HashMap<String, Collection<OneTimeVO>>();
    	 Collection<String> oneTimeList = new ArrayList();
    	 oneTimeList.add("mailtracking.defaults.resditevent");
    	 try
    	    {
    	      hashMap = defaultsDelegate.findOneTimeValues(companyCode, 
    	        oneTimeList);
    	    }
    	    catch (BusinessDelegateException localBusinessDelegateException3)
    	    {
    	      this.log.log(7, "onetime fetch exception");
    	    }
    	Collection<OneTimeVO> resditModes = (Collection<OneTimeVO>)hashMap.get("mailtracking.defaults.resditevent");
    	if(resditModes!=null)
    	{
    	   log.log(Log.INFO,"Sizeee----", resditModes.size());
    	for(OneTimeVO list: resditModes) {
    		log.log(Log.INFO,"LIST----------",list.getFieldDescription());
    	}
    	}
    	mailperformanceSession.setResditModes((ArrayList<OneTimeVO>)resditModes);

		mailperformanceForm.setDisableSave("Y");
		mailperformanceSession.setCoTerminusVOs(null);
		//mailperformanceSession.setAirport(null);
		mailperformanceForm.setScreenFlag("ctRadioBtn");
		mailperformanceForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = SUCCESS;
    	
	}


}
