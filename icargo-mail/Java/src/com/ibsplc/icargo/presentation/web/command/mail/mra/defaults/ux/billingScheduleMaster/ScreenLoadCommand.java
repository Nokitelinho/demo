package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.billingScheduleMaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.BillingScheduleMasterModel;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-9498
 *
 */
public class ScreenLoadCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MRA MAIL");

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		BillingScheduleMasterModel billingScheduleMasterModel = (BillingScheduleMasterModel) actionContext
				.getScreenModel();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
			
		} catch (BusinessDelegateException e) {
			actionContext.addAllError(handleDelegateException(e));
		}
		this.log.log(5, new Object[] { "oneTimeValues ---> ", oneTimeValues });
		this.log.log(5, new Object[] { "LoginAirport ---> ", logonAttributes.getAirportCode() });
		billingScheduleMasterModel.setOneTimeValues((oneTimeValues));
		ResponseVO responseVO = new ResponseVO();
		
		
		ArrayList<BillingScheduleMasterModel> results = new ArrayList<BillingScheduleMasterModel>();
		results.add(billingScheduleMasterModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		log.exiting("ScreenLoadCommand", "execute");
	}

	private Collection<String> getOneTimeParameterTypes() {
		Collection<String> parameterTypes = new ArrayList();
		parameterTypes.add("mail.mra.masters.billingtype");
		parameterTypes.add("mail.mra.masters.billingperiods");
		return parameterTypes;
	}

	public static Map<String, Collection<OneTime>> constructOneTimeValues(
			Map<String, Collection<OneTimeVO>> oneTimeValues) {
		HashMap<String, Collection<OneTime>> oneTimeValuesMap = new HashMap<String, Collection<OneTime>>();
		for (Map.Entry<String, Collection<OneTimeVO>> oneTimeValue : oneTimeValues.entrySet()) {
			ArrayList<OneTime> oneTimes = new ArrayList<OneTime>();
			for (OneTimeVO oneTimeVO : oneTimeValue.getValue()) {
				OneTime oneTime = new OneTime();
				oneTime.setFieldType(oneTimeVO.getFieldType());
				oneTime.setFieldValue(oneTimeVO.getFieldValue());
				oneTime.setFieldDescription(oneTimeVO.getFieldDescription());
				oneTimes.add(oneTime);
			}
			oneTimeValuesMap.put(oneTimeValue.getKey(), oneTimes);
		}
		return oneTimeValuesMap;
	}

}
