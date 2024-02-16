package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance;

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

/**
 * @author A-8164
 *
 */
public class ScreenloadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String TARGET = "screenload_success";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("mailperformanceScreenloadCommand", "execute");

		MailPerformanceForm mailPerformanceForm = (MailPerformanceForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		MailPerformanceSession mailPerformanceSession = 
				getScreenSession(MODULE_NAME,SCREEN_ID);
		
		//mailPerformanceSession.setCoTerminusVOs(null);  
        mailPerformanceSession.removeMailHandoverVOs();
		//Added by A-7540
		mailPerformanceSession.removeMailServiceStandardVOs();
		
		mailPerformanceSession.setGPAContractVOs(null);
		mailPerformanceSession.setMailServiceStandardVOs(null);//added as part of ICRD-304479

		mailPerformanceSession.setIncentiveConfigurationVOs(null);
		mailPerformanceSession.setIncentiveParameters(null);
		mailPerformanceSession.setBasis(null);
		mailPerformanceSession.setFormula(null);
		mailPerformanceSession.setFormulaBasis(null);
		mailPerformanceForm.setAirport("");
		
		mailPerformanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);   
		mailPerformanceForm.setAirport(getApplicationSession().getLogonVO().getStationCode());
		SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList();
		oneTimeList.add("mailtracking.defaults.resditmodesforcoterminousconfig");
		oneTimeList.add("mail.operations.usps.calendartype");
		oneTimeList.add("mail.operations.mailservicelevels");
		oneTimeList.add("mailtracking.defaults.mailclass");		
		try {
			hashMap = defaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException localBusinessDelegateException3) {    
			this.log.log(7, "onetime fetch exception"); 
		}
		Collection<OneTimeVO> allResditModes = (Collection<OneTimeVO>) hashMap
				.get("mailtracking.defaults.resditmodesforcoterminousconfig"); 
		Collection<OneTimeVO> serviceLevels = (Collection<OneTimeVO>) hashMap
				.get("mail.operations.mailservicelevels");
		Collection<OneTimeVO> mailClasses = (Collection<OneTimeVO>) hashMap
				.get("mailtracking.defaults.mailclass");
		Collection<OneTimeVO> resditModes=new ArrayList<OneTimeVO>();
		resditModes.addAll(allResditModes);//Commented and changed the one time by A-8164 for ICRD-342541
		/*if (allResditModes != null) {
			  
			log.log(Log.INFO, "Sizeee----", allResditModes.size());
			for (OneTimeVO list : allResditModes) {
				if(MailConstantsVO.RESDIT_ASSIGNED.equals(list.getFieldValue()) || MailConstantsVO.RESDIT_DELIVERED.equals(list.getFieldValue()) || MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(list.getFieldValue())){
					resditModes.add(list);
				}      
				log.log(Log.INFO, "LIST----------", list.getFieldDescription());
			}
		}*/
		
		Collection<OneTimeVO> serviceLevel = (Collection<OneTimeVO>) hashMap
				.get("mail.operations.mailservicelevels");
		if (serviceLevel != null) {
			log.log(Log.INFO, "Sizeee----", serviceLevel.size());
			for (OneTimeVO list : serviceLevel) {
				log.log(Log.INFO, "LIST----------", list.getFieldDescription());
			}
		}
		
		Collection<OneTimeVO> calendarTypes = (Collection<OneTimeVO>) hashMap
				.get("mail.operations.usps.calendartype");
		if (calendarTypes != null) {
			log.log(Log.INFO, "Sizeee----", calendarTypes.size());
			for (OneTimeVO list : calendarTypes) {
				log.log(Log.INFO, "LIST----------", list.getFieldDescription());
			}
		}
		mailPerformanceSession.setResditModes((ArrayList<OneTimeVO>)resditModes);
		mailPerformanceSession.setCalendarTypes((ArrayList<OneTimeVO>)calendarTypes);	 
        mailPerformanceSession.setServiceLevel((ArrayList<OneTimeVO>)serviceLevels);
		mailPerformanceSession.setServiceLevels((ArrayList<OneTimeVO>)serviceLevel);
		mailPerformanceSession.setMailClasses((ArrayList<OneTimeVO>)mailClasses);
		mailPerformanceForm.setScreenStatusFlag
			(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		log.exiting("mailperformanceScreenloadCommand", "execute");
	}

}
