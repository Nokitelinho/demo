package com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload.validators;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Component("postalAuthorityCodeValidator")
public class PostalAuthorityCodeValidator extends Validator<ConsignmentDocumentVO> {

	private static final Log LOGGER = LogFactory.getLogger(SaveConsignmentUploadFeatureConstants.MODULE_SUBMODULE);

	@Override
	public void validate(ConsignmentDocumentVO consignmentDocumentVO) throws BusinessException, SystemException {
		LOGGER.entering(getClass().getSimpleName(), "validate");
		String paCode = null;
		paCode = findPACodeForMailboxId(consignmentDocumentVO);
		if (paCode == null) {
			String ooe = null;
			String mailbagId = null;
			if (Objects.nonNull(consignmentDocumentVO.getMailInConsignment())
					&& !consignmentDocumentVO.getMailInConsignment().isEmpty()) {
				mailbagId = consignmentDocumentVO.getMailInConsignment().iterator().next().getMailId();
				if (Objects.nonNull(mailbagId) && mailbagId.length() >= 6) {
					ooe = mailbagId.substring(0, 6);
				}
				/*if (Objects.nonNull(ooe)) {
					paCode = OfficeOfExchange.findPAForMailboxID(consignmentDocumentVO.getCompanyCode(),
							consignmentDocumentVO.getPaCode(), ooe);
				}
				if (Objects.isNull(paCode)) {
					paCode = OfficeOfExchange.findPAForOfficeOfExchange(consignmentDocumentVO.getCompanyCode(), ooe);
				}*/
			}
		}
		validateAndRaiseBusinessException(consignmentDocumentVO, paCode);
		addtoFeatureContext(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_PACODE, paCode);

		LOGGER.exiting(getClass().getSimpleName(), "validate");
	}

	private void validateAndRaiseBusinessException(ConsignmentDocumentVO consignmentDocumentVO, String paCode)
			throws MailOperationsBusinessException {
		if (paCode == null) {
			MailOperationsBusinessException mailOperationsBusinessException = new MailOperationsBusinessException();
			Collection<ErrorVO> errors = new ArrayList<>();
			String[] errorData = new String[] { String.valueOf(consignmentDocumentVO.getLineCount()),
					consignmentDocumentVO.getConsignmentNumber() };
			ErrorVO error = new ErrorVO(SaveConsignmentUploadFeatureConstants.ERROR_NO_PA_CODE_EXISTS_FOR_MAILBOX,errorData);
			error.setErrorDescription(SaveConsignmentUploadFeatureConstants.ERROR_NO_PA_CODE_EXISTS_FOR_MAILBOX);
			errors.add(error);
			throw mailOperationsBusinessException;
		}
	}

	private String findPACodeForMailboxId(ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException {
		String paCode = null;
		//TODO: Neo to correct
		/*try {
			MailBoxId mailBoxId = MailBoxId.find(consignmentDocumentVO.getCompanyCode(),
					consignmentDocumentVO.getPaCode());
			if (SaveConsignmentUploadFeatureConstants.ACTIVE.equals(mailBoxId.getMailboxStatus())
					&& SaveConsignmentUploadFeatureConstants.POSTAL_AUTHORITY.equals(mailBoxId.getMailboxOwner())) {
				paCode = mailBoxId.getOwnerCode();
			}
		} catch (FinderException e) {
			LOGGER.log(Log.FINE, e.getMessage(),e);
			LOGGER.log(Log.FINE, "Exception finding Mailbox ID as PA");
		}*/
		return paCode;
	}

}
