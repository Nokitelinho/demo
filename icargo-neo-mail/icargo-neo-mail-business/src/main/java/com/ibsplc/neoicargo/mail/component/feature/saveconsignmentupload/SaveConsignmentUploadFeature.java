package com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Component(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FEATURE)
@FeatureConfigSource("feature/saveconsignmentupload")
//TODO: Neo to code the event publish functionality
//@Feature(exception = MailTrackingBusinessException.class, event = SaveConsignmentUploadFeatureConstants.MAIL_OPERATIONS_SAVEMAILBAGHISTORY_CONSIGNMENT_UPLOAD)
public class SaveConsignmentUploadFeature extends AbstractFeature<ConsignmentDocumentVO> {

	private static final Log LOGGER = LogFactory.getLogger(SaveConsignmentUploadFeatureConstants.MODULE_SUBMODULE);
	@Autowired
	PerformSaveConsignmentDocument performSaveConsignmentDocument;
	@Override
	protected ConsignmentDocumentVO perform(ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, BusinessException {
		LOGGER.entering(this.getClass().getSimpleName(), "perform");
		/**
		 * If existing consignment present save them first(perform deletion)
		 */
		if (Objects.nonNull(consignmentDocumentVO.getExistingConsignmentDocumentVOs())
				&& !consignmentDocumentVO.getExistingConsignmentDocumentVOs().isEmpty()) {
			try {
				performSaveConsignmentDocument
						.perform(consignmentDocumentVO.getExistingConsignmentDocumentVOs().iterator().next());
			} catch (MailbagAlreadyAcceptedException | InvalidMailTagFormatException | DuplicateDSNException
					| DuplicateMailBagsException e) {
				LOGGER.log(Log.FINE, e.getMessage(), e);
				MailOperationsBusinessException mailOperationsBusinessException = new MailOperationsBusinessException();
				Collection<ErrorVO> errors = new ArrayList<>();
				String[] errorData = new String[] { String.valueOf(consignmentDocumentVO.getLineCount()),
						consignmentDocumentVO.getConsignmentNumber() };
				ErrorVO error = new ErrorVO(SaveConsignmentUploadFeatureConstants.ERROR_EXISTING_CONSIGNMENT,
						errorData);
				//TODO:Neo to check whether error description needed
				//error.setErrorDescription(SaveConsignmentUploadFeatureConstants.ERROR_EXISTING_CONSIGNMENT);
				errors.add(error);
				mailOperationsBusinessException.addErrors(errors);
				throw mailOperationsBusinessException;
			}
		}
		/**
		 * Save the new consignment details
		 */
		try {
			performSaveConsignmentDocument.perform(consignmentDocumentVO);
		} catch (MailbagAlreadyAcceptedException | InvalidMailTagFormatException | DuplicateDSNException
				| DuplicateMailBagsException e) {
			LOGGER.log(Log.FINE, e.getMessage(), e);
			MailOperationsBusinessException mailOperationsBusinessException = new MailOperationsBusinessException();
			Collection<ErrorVO> errors = new ArrayList<>();
			String[] errorData = new String[] { String.valueOf(consignmentDocumentVO.getLineCount()),
					consignmentDocumentVO.getConsignmentNumber() };
			ErrorVO error = new ErrorVO(SaveConsignmentUploadFeatureConstants.ERROR_NEW_CONSIGNMENT, errorData);
			//error.setErrorDescription(SaveConsignmentUploadFeatureConstants.ERROR_NEW_CONSIGNMENT);
			errors.add(error);
			mailOperationsBusinessException.addErrors(errors);
			throw mailOperationsBusinessException;
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "perform");
		return consignmentDocumentVO;
	}

}
