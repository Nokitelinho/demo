package com.ibsplc.icargo.presentation.web.command.xaddons.lh.mail.operations.ux.hbamarking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.xaddons.lh.mail.operations.LhMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.model.xaddons.lh.mail.operations.HbaMarkingModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends AbstractCommand{

	private static final Log LOGGER = LogFactory.getLogger("MAILOPERATIONS");
	private static final String HBATYPE_ONETIME = "mail.operations.hbatype";
	private static final String HBAPOSITION_ONETIME = "mail.operations.hbaposition";
	
	@Override
	public void execute(ActionContext actionContext)
			throws BusinessDelegateException {
		LOGGER.entering("ScreenloadCommand", "execute");
		List<HbaMarkingModel> results = new ArrayList<>();
		ResponseVO responseVO = new ResponseVO();
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		HbaMarkingModel hbaMarkingModel = (HbaMarkingModel) actionContext
				.getScreenModel();
		HashMap<String, Collection<OneTimeVO>> onetimes = getOneTimeValues(logonAttributes);
		hbaMarkingModel.setOneTimeValues(onetimes);
		LhMailOperationsDelegate lhMailOperationsDelegate = new LhMailOperationsDelegate();
		 HbaMarkingVO hbaMarkingVO = constructHbaMarkingVO(hbaMarkingModel, logonAttributes.getCompanyCode());
		 hbaMarkingVO=  lhMailOperationsDelegate.findHbaDetails(hbaMarkingVO);
		hbaMarkingModel.setHbaType(hbaMarkingVO.getHbaType());
		hbaMarkingModel.setPosition(hbaMarkingVO.getHbaPosition());
		results.add(hbaMarkingModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		LOGGER.exiting("ScreenLoadCommand","execute");

	}

	private Collection<String> getOneTimeParameterTypes() {
		ArrayList<String> parameterTypes = new ArrayList<>();
		parameterTypes.add(HBATYPE_ONETIME);
		parameterTypes.add(HBAPOSITION_ONETIME);
	
		return parameterTypes;
	}

	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(
			LogonAttributes logonAttributes) {
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		try {

			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

		return ((HashMap) oneTimeValues);
	}
	
	private  HbaMarkingVO constructHbaMarkingVO(HbaMarkingModel hbaMarkingModel, String companyCode) {
		HbaMarkingVO hbaMarkingVO = new HbaMarkingVO();
		hbaMarkingVO.setHbaPosition(hbaMarkingModel.getPosition());
		hbaMarkingVO.setUldRefNo(hbaMarkingModel.getUldReferenceNumber());
		hbaMarkingVO.setHbaType(hbaMarkingModel.getHbaType());
		hbaMarkingVO.setCompanyCode(companyCode);
		hbaMarkingVO.setOperationFlag(hbaMarkingModel.getOperationFlag());
		return hbaMarkingVO;

	}
	


}
