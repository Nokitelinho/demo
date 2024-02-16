package com.ibsplc.neoicargo.mail.component.feature.stampresdit.validators;

import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.ResditController;
import com.ibsplc.neoicargo.mail.component.feature.stampresdit.StampResditFeatureConstants;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailResditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Component(StampResditFeatureConstants.STAMP_RESDIT_VALIDATOR)
public class StampResditValidator extends Validator<MailResditVO> {
	@Autowired
	private MailController mailController;
	@Autowired
	private ResditController resditController;
	private static final Log LOGGER = LogFactory.getLogger(StampResditFeatureConstants.MODULE_SUBMODULE);

	@Override
	public void validate(MailResditVO mailResditVO) throws MailOperationsBusinessException {
		LOGGER.entering(getClass().getSimpleName(), "validate");
		String preCheckEnabled = getSystemParamterValue();

		if (Objects.isNull(preCheckEnabled)) {
			MailOperationsBusinessException mailOperationsBusinessException = new MailOperationsBusinessException();
			mailOperationsBusinessException.addErrors(constructErrors(mailResditVO));
			throw mailOperationsBusinessException;
		}
		try {
			if (Objects.nonNull(preCheckEnabled) && MailConstantsVO.FLAG_YES.equals(preCheckEnabled)
					&& !canStampResdits(mailResditVO)) {
				MailOperationsBusinessException mailOperationsBusinessException = new MailOperationsBusinessException();
				mailOperationsBusinessException.addErrors(constructErrors(mailResditVO));
				throw mailOperationsBusinessException;
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		LOGGER.exiting(getClass().getSimpleName(), "validate");
	}

	private Collection<ErrorVO> constructErrors(MailResditVO mailResditVO) {
		Collection<ErrorVO> errors = new ArrayList<>();
		String[] errorData = new String[] {mailResditVO.getEventCode()};
		ErrorVO error = new ErrorVO(StampResditFeatureConstants.CANNOT_STAMP_RESDIT_FOR_EVENT_CODE,errorData);
		error.setErrorCode(StampResditFeatureConstants.CANNOT_STAMP_RESDIT_FOR_EVENT_CODE);
		errors.add(error);
		return errors;
	}

	private boolean canStampResdits(MailResditVO mailResditVO) throws SystemException {
		/**
		 * canStampResdits to be re-factored into the feature in future
		 */
		return resditController.canStampResdits(mailResditVO);
	}

	private  String getSystemParamterValue() {
		return mailController.findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED);
	}
}
