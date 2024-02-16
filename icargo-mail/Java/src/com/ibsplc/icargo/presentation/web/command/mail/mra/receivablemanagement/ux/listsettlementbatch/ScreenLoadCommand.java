package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.ListSettlementBatchModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MAIL mra screenload");

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		ListSettlementBatchModel listSettlementBatchModel = (ListSettlementBatchModel) actionContext.getScreenModel();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;

		try {
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
			actionContext.addAllError(handleDelegateException(e));
		}
		listSettlementBatchModel.setOneTimeValues(MailMRAModelConverter.constructOneTimeValues(oneTimeValues));
		ResponseVO responseVO = new ResponseVO();
		List<ListSettlementBatchModel> results = new ArrayList<>();
		results.add(listSettlementBatchModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		this.log.exiting("ScreenLoadCommand", "execute");
	}

	private Collection<String> getOneTimeParameterTypes() {
		Collection<String> parameterTypes = new ArrayList<>();
		parameterTypes.add("mail.mra.receivablemanagement.batchstatus");
		return parameterTypes;
	}
}
