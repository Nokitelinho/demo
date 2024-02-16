/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.incentiveconfiguration;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.BasisUxLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6986
 *
 */
public class ScreenloadBasisLovCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";



	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenloadParameterLovCommand", "execute");

		BasisUxLovForm basisUxLovForm = (BasisUxLovForm) invocationContext.screenModel;
		invocationContext.target = SUCCESS;
		MailPerformanceSession mailPerformanceSession =
				getScreenSession(MODULE_NAME,SCREEN_ID);

		SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList();
		oneTimeList.add("mail.operations.uspsdomesticratebreakdown");

		if(basisUxLovForm.getCode()!= null && basisUxLovForm.getCode().length()>0){
			mailPerformanceSession.setBasis(basisUxLovForm.getCode());
		}else{
			mailPerformanceSession.setBasis(null);
		}

		try {
			hashMap = defaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException localBusinessDelegateException3) {
			this.log.log(7, "onetime fetch exception");
		}

		Collection<OneTimeVO> rateBreakDownValues = (Collection<OneTimeVO>) hashMap
				.get("mail.operations.uspsdomesticratebreakdown");
		if (rateBreakDownValues != null) {
			log.log(Log.INFO, "Sizeee----", rateBreakDownValues.size());
			for (OneTimeVO list : rateBreakDownValues) {
				log.log(Log.INFO, "LIST----------", list.getFieldDescription());
			}
		}

		mailPerformanceSession.setRateBreakDownValues((ArrayList<OneTimeVO>)rateBreakDownValues);
		//basisUxLovForm.setOperator("");

		basisUxLovForm.setScreenStatusFlag
		(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		log.exiting("ScreenloadBasisLovCommand", "execute");

	}

}
