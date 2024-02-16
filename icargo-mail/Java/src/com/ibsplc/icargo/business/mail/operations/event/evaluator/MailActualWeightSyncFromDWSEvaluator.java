package com.ibsplc.icargo.business.mail.operations.event.evaluator;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultProductProxy;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.framework.evaluator.Evaluator;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.context.ClientContext;
import com.ibsplc.xibase.server.framework.config.ResolverFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.frontcontroller.AbstractControl;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailActualWeightSyncFromDWSEvaluator implements Evaluator<DWSMasterVO> {
	private static final Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");
	@Override
	public boolean evaluate(DWSMasterVO dWSMasterVO) {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add("mail.operations.modeofmailsyncfordws");
		HashMap<String, String> systemParameterMap = null;
		
			try {
				systemParameterMap =  Proxy.getInstance().get(SharedDefaultProductProxy.class).findSystemParameterByCodes(systemParameters);
			} catch (ProxyException | SystemException exception) {
				LOGGER.log(Log.SEVERE, exception.getMessage(), exception);
			}
		
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get("mail.operations.modeofmailsyncfordws");
		}
		
		String screenId =null;
		try {
			ClientContext context = ResolverFactory.getClientContextResolver().resolve(null);
			screenId = context.getParameter(AbstractControl.REQ_TRIGGER_POINT);
		} catch (SystemException exception) {
			LOGGER.log(Log.SEVERE, exception.getMessage(), exception);
		}
		return ("OPR063".equals(screenId) && "Y".equals(sysparValue));
	}

}
