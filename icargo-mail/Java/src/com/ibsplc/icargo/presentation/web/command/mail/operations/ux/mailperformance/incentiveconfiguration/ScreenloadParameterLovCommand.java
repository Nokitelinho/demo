/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.incentiveconfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ParameterUxLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;




/**
 * @author A-6986
 *
 */
public class ScreenloadParameterLovCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String  USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String  USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";


	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenloadParameterLovCommand", "execute");

		ParameterUxLovForm parameterUxLovForm = (ParameterUxLovForm) invocationContext.screenModel;
		invocationContext.target = SUCCESS;
		MailPerformanceSession mailPerformanceSession =
				getScreenSession(MODULE_NAME,SCREEN_ID);
		String paCode = null;
		paCode = ((String)mailPerformanceSession.getPaCode());
		//String parameter = null;
		/*Collection<IncentiveConfigurationDetailVO> detailsVO = new ArrayList<IncentiveConfigurationDetailVO>();
		if ((parameterUxLovForm.getCode() != null) && (parameterUxLovForm.getCode().trim().length() > 0)) {
			String[] param = parameterUxLovForm.getCode().split("\n");
			
			for(int i = 0;i<param.length;i++){
				IncentiveConfigurationDetailVO detailVO = new IncentiveConfigurationDetailVO();
				String[] codeValue = param[i].split(":");
				detailVO.setDisIncParameterCode(codeValue[0].trim());
				detailVO.setDisIncParameterValue(codeValue[1].trim());
				detailsVO.add(detailVO);
			}
		//	mailPerformanceSession.setParameterVO((ArrayList<IncentiveConfigurationDetailVO>)detailsVO);
	    }
		if((parameterUxLovForm.getDescription() != null) && (parameterUxLovForm.getDescription().trim().length() >0)){
            String[] desc = parameterUxLovForm.getDescription().split(",");
            int i= 0;
            for(IncentiveConfigurationDetailVO detailVO : detailsVO){
                  detailVO.setExcludeFlag(desc[i].trim());
                  i++;
            }
     }
		if(detailsVO != null && detailsVO.size()>0 ){
			mailPerformanceSession.setParameterVO((ArrayList<IncentiveConfigurationDetailVO>)detailsVO);
		}*/
		
		if(parameterUxLovForm.getCode() == null || parameterUxLovForm.getCode().length()==0){
			mailPerformanceSession.setParameterVO(null);
		}

		SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		
		Collection<String> oneTimeList = new ArrayList<String>();
		Collection<OneTimeVO> serviceLevel = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> amot = new ArrayList<>();
		oneTimeList.add("mail.operations.mailservicelevels");
		oneTimeList.add("mail.operations.incentiveparameters");
		oneTimeList.add("mailtracking.defaults.mailcategory");
		oneTimeList.add("mail.operations.amot.yesornocombo");
		
		
		try {
			hashMap = defaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException localBusinessDelegateException3) {
			this.log.log(7, "onetime fetch exception");
		}

		Collection<OneTimeVO> incentiveParameters = (Collection<OneTimeVO>) hashMap
				.get("mail.operations.incentiveparameters");
		Collection<OneTimeVO> mailserviceLevel = (Collection<OneTimeVO>) hashMap
				.get("mail.operations.mailservicelevels");
		Collection<OneTimeVO> mailCategory = (Collection<OneTimeVO>) hashMap
				.get("mailtracking.defaults.mailcategory");
		Collection<OneTimeVO> mailAmot = (Collection<OneTimeVO>) hashMap
				.get("mail.operations.amot.yesornocombo");
		
		if (mailserviceLevel != null) {
			log.log(Log.INFO, "Sizeee----", mailserviceLevel.size());
			for (OneTimeVO list : mailserviceLevel) {
				log.log(Log.INFO, "LIST----------", list.getFieldDescription());
			}
		}
		if (incentiveParameters != null) {
			log.log(Log.INFO, "Sizeee----", incentiveParameters.size());
			for (OneTimeVO list : incentiveParameters) {
				log.log(Log.INFO, "LIST----------", list.getFieldDescription());
			}
		}

		//Checking PA for servicelevel
		String paInt = null;
		String paDom = null;
		try{
			paInt = findSystemParameterValue(USPS_INTERNATIONAL_PA);	
			paDom = findSystemParameterValue(USPS_DOMESTIC_PA);	
		}catch (BusinessDelegateException localBusinessDelegateException3) {
			this.log.log(7, "syspar fetch exception");
		}
			
		if(paInt.equals(paCode)){
			
			for (OneTimeVO list : mailserviceLevel) {
				if(list.getFieldDescription().contains("Expedited")
						|| list.getFieldDescription().contains("Preferred")
						|| list.getFieldDescription().contains("Defferred")){
					serviceLevel.add(list);
				}
			}
			
		}else if(paDom.equals(paCode)){
			
			for (OneTimeVO list : mailserviceLevel) {
				if(list.getFieldDescription().contains("Priority Mail Express")
						|| list.getFieldDescription().contains("Priority Mail")
						//Commented as part of ICRD-337563
						//|| list.getFieldDescription().contains("Express Mail")
						//Added as part of ICRD-337563
				        ||list.getFieldDescription().contains("First Class Mail")) {
					serviceLevel.add(list);
				}
			}
		}
		if (Objects.nonNull(mailAmot)) {
			for (OneTimeVO list : mailAmot) {
				if(list.getFieldDescription().contains("Yes")
						|| list.getFieldDescription().contains("No")){
					amot.add(list);
				}
				}
			}
 
        mailPerformanceSession.setIncentiveParameters((ArrayList<OneTimeVO>)incentiveParameters);
        mailPerformanceSession.setMailCategory((ArrayList<OneTimeVO>)mailCategory);
        
        mailPerformanceSession.setMailServiceLevels((ArrayList<OneTimeVO>)serviceLevel);
        mailPerformanceSession.setMailAmot((ArrayList<OneTimeVO>)amot);

        parameterUxLovForm.setScreenStatusFlag
		(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		log.exiting("ScreenloadParameterLovCommand", "execute");

	}
	
	public String findSystemParameterValue(String syspar) throws BusinessDelegateException{
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
		}

}
