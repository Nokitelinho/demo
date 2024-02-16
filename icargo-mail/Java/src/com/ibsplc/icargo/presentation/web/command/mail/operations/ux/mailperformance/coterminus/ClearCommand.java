package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.coterminus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ClearCommand extends BaseCommand {

private static final String SUCCESS = "clear_success";
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
log.log(Log.FINE, "\n\n in the clear command----------> \n\n");
    	
    	MailPerformanceForm mailperformanceForm =
							(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailperformanceSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
		
		mailperformanceForm.setCoAirport("");
		mailperformanceForm.setCoPacode("");
		mailperformanceForm.setFilterResdit("");       
		SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	Map<String, Collection<OneTimeVO>> hashMap = 
    		      new HashMap<String, Collection<OneTimeVO>>();
    	 Collection<String> oneTimeList = new ArrayList();
    	 oneTimeList.add("mailtracking.defaults.resditmodesforcoterminousconfig");
    	 try
    	    {
    	      hashMap = defaultsDelegate.findOneTimeValues(companyCode, 
    	        oneTimeList);
    	    }
    	    catch (BusinessDelegateException localBusinessDelegateException3) 
    	    {
    	      this.log.log(7, "onetime fetch exception");
    	    }
    	Collection<OneTimeVO> allResditModes = (Collection<OneTimeVO>)hashMap.get("mailtracking.defaults.resditmodesforcoterminousconfig");
    	Collection<OneTimeVO> resditModes=new ArrayList<OneTimeVO>();
    	resditModes.addAll(allResditModes);//Commented and changed the one time by A-8164 for ICRD-342541
    	/*if (allResditModes != null) {
			  
			log.log(Log.INFO, "Sizeee----", allResditModes.size());  
			for (OneTimeVO list : allResditModes) {
				if(MailConstantsVO.RESDIT_ASSIGNED.equals(list.getFieldValue()) || MailConstantsVO.RESDIT_DELIVERED.equals(list.getFieldValue()) || MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(list.getFieldValue())){
					resditModes.add(list);
				}      
    		log.log(Log.INFO,"LIST----------",list.getFieldDescription());
    	}
    	}*/
    	
    	mailperformanceSession.setResditModes((ArrayList<OneTimeVO>)resditModes);

		mailperformanceForm.setDisableSave("Y");
		mailperformanceSession.setCoTerminusVOs(null);
		//mailperformanceSession.setAirport(null);
		mailperformanceForm.setScreenFlag("ctRadioBtn");
		mailperformanceForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = SUCCESS;
    	mailperformanceForm.setStatusFlag("List_fail"); 
	}
	
}
