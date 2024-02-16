package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingFilter;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModel;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends AbstractCommand{
	private static final Log LOGGER = LogFactory.getLogger("CUSTOMERMANAGEMENT_DEFAULTS");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String CLASS_NAME = "ScreenLoadCommand-BrokerMapping";
	private static final String ONETIME_CUSTOMERTYPE = "shared.customer.customertype";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		LOGGER.entering(CLASS_NAME, "execute");
		ResponseVO responseVO =new ResponseVO();
		BrokerMappingModel brokerMappingModel=(BrokerMappingModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute(); 
		BrokerMappingFilter brokerMappingFilter=new BrokerMappingFilter();
		brokerMappingFilter.setStation(logonAttributes.getStationCode());
		brokerMappingModel.setBrokerMappingFilter(brokerMappingFilter);
		Map<String, Collection<OneTimeVO>> oneTimeValues = handleScreenLoadDetails(
				logonAttributes.getCompanyCode());
		
		if (oneTimeValues != null) {
            Map<String, Collection<OneTime>> oneTimes = constructOneTimeValues(oneTimeValues);
            brokerMappingModel.setOneTimeValues(oneTimes);
        }
		
		List<BrokerMappingModel> results=new ArrayList<>();
		results.add(brokerMappingModel);
		responseVO.setResults(results);
		responseVO.setStatus(SCREENLOAD_SUCCESS);
		actionContext.setResponseVO(responseVO);
		
		LOGGER.entering(CLASS_NAME,"execute");
		
	}
	 private Map<String, Collection<OneTime>> constructOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues) {
		 LOGGER.entering("ScreenLoadCommand", "constructOneTimeValues");
	        HashMap<String, Collection<OneTime>> oneTimeValuesMap = new HashMap<>();
	        ArrayList<OneTime> oneTimes ;
	        for (Map.Entry<String, Collection<OneTimeVO>> oneTimeValue : oneTimeValues.entrySet()) {
	            	oneTimes = new ArrayList<>();
	            for (OneTimeVO oneTimeVO : oneTimeValue.getValue()) {
	                OneTime oneTime = new OneTime();
	                oneTime.setFieldType(oneTimeVO.getFieldType());
	                oneTime.setFieldValue(oneTimeVO.getFieldValue());
	                oneTime.setFieldDescription(oneTimeVO.getFieldDescription());
	                oneTimes.add(oneTime); 
	            }
	            if(oneTimeValue.getKey().equals(ONETIME_CUSTOMERTYPE)){
	            	OneTime oneTimeTemp = new OneTime();
	     	        oneTimeTemp.setFieldType(ONETIME_CUSTOMERTYPE);
	     	        oneTimeTemp.setFieldValue("TMP");
	     	        oneTimeTemp.setFieldDescription("Temporary Customer");
	     	       oneTimes.add(oneTimeTemp);
	            }
	            oneTimeValuesMap.put(oneTimeValue.getKey(), oneTimes);
	        }
	       
	        LOGGER.exiting("ScreenLoadCommand", "constructOneTimeValues");
	        return oneTimeValuesMap;
	    }
	 
	public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {

			Collection<String> groupTypes = new ArrayList<>();
			groupTypes.add(ONETIME_CUSTOMERTYPE);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, groupTypes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		LOGGER
				.log(Log.FINE,
						"CustomerType and StatusType oneTime Type values",
						oneTimes);
		return oneTimes;
		
	}
}
