package com.ibsplc.icargo.business.mail.operations.feature.stampresdit.validators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.ResditController;
import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.StampResditFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Validator;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(StampResditFeatureConstants.STAMP_RESDIT_VALIDATOR)
public class StampResditValidator extends Validator<MailResditVO> {

	private static final Log LOGGER = LogFactory.getLogger(StampResditFeatureConstants.MODULE_SUBMODULE);

	@Override
	public void validate(MailResditVO mailResditVO) throws BusinessException, SystemException {
		LOGGER.entering(getClass().getSimpleName(), "validate");
		Map<String, String> systemParameterMap = getSystemParamterMap();
		if (Objects.isNull(systemParameterMap)) {
			MailTrackingBusinessException mailTrackingBusinessException = new MailTrackingBusinessException();
			mailTrackingBusinessException.addErrors(constructErrors(mailResditVO));
			throw mailTrackingBusinessException;
		}
		String preCheckEnabled = systemParameterMap.get(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED);
		if (Objects.nonNull(preCheckEnabled) && MailConstantsVO.FLAG_YES.equals(preCheckEnabled)
				&& !canStampResdits(mailResditVO)) {
			MailTrackingBusinessException mailTrackingBusinessException = new MailTrackingBusinessException();
			mailTrackingBusinessException.addErrors(constructErrors(mailResditVO));
			throw mailTrackingBusinessException;
		}
		LOGGER.exiting(getClass().getSimpleName(), "validate");
	}

	private Collection<ErrorVO> constructErrors(MailResditVO mailResditVO) {
		Collection<ErrorVO> errors = new ArrayList<>();
		String[] errorData = new String[] {mailResditVO.getEventCode()};
		ErrorVO error = new ErrorVO(StampResditFeatureConstants.CANNOT_STAMP_RESDIT_FOR_EVENT_CODE,errorData);
		error.setErrorDescription(StampResditFeatureConstants.CANNOT_STAMP_RESDIT_FOR_EVENT_CODE);
		errors.add(error);
		return errors;
	}

	private boolean canStampResdits(MailResditVO mailResditVO) throws SystemException {
		/**
		 * canStampResdits to be re-factored into the feature in future
		 */
		ResditController resditController = (ResditController) SpringAdapter.getInstance().getBean("resditController");
		return resditController.canStampResdits(mailResditVO);
	}

	private Map<String, String> getSystemParamterMap() throws SystemException {
		Collection<String> systemParameters = new ArrayList<>();
		systemParameters.add(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED);
		return ParameterUtil.getInstance().findSystemParameterByCodes(systemParameters);
	}
}
