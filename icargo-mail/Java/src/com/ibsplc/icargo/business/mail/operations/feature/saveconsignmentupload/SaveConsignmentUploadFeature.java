package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.DuplicateDSNException;
import com.ibsplc.icargo.business.mail.operations.DuplicateMailBagsException;
import com.ibsplc.icargo.business.mail.operations.InvalidMailTagFormatException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.MailbagAlreadyAcceptedException;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FEATURE)
@Feature(exception = MailTrackingBusinessException.class, event = SaveConsignmentUploadFeatureConstants.MAIL_OPERATIONS_SAVEMAILBAGHISTORY_CONSIGNMENT_UPLOAD)
public class SaveConsignmentUploadFeature extends AbstractFeature<ConsignmentDocumentVO> {

	private static final Log LOGGER = LogFactory.getLogger(SaveConsignmentUploadFeatureConstants.MODULE_SUBMODULE);

	@Override
	protected FeatureConfigVO fetchFeatureConfig(ConsignmentDocumentVO consignmentDocumentVO) {
		return getBECConfigurationDetails();
	}

	private FeatureConfigVO getBECConfigurationDetails() {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		List<String> validatorIds = new ArrayList<>();
		List<String> enricherIds = new ArrayList<>();
		validatorIds.add(SaveConsignmentUploadFeatureConstants.PA_CODE_VALIDATOR);
		validatorIds.add(SaveConsignmentUploadFeatureConstants.FLIGHT_DETAILS_VALIDATOR);
		enricherIds.add(SaveConsignmentUploadFeatureConstants.CONSIGNMENT_DETAILS);
		enricherIds.add(SaveConsignmentUploadFeatureConstants.EXISTING_CONSIGNMENT_DETAILS);
		featureConfigVO.setValidatorIds(validatorIds);
		featureConfigVO.setEnricherId(enricherIds);
		return featureConfigVO;
	}

	@Override
	protected ConsignmentDocumentVO perform(ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, BusinessException {
		LOGGER.entering(this.getClass().getSimpleName(), "perform");
		PerformSaveConsignmentDocument performSaveConsignmentDocument = (PerformSaveConsignmentDocument) ICargoSproutAdapter
				.getBean("mail.operations.performSaveConsignmentDocument");
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
				MailTrackingBusinessException mailTrackingBusinessException = new MailTrackingBusinessException();
				Collection<ErrorVO> errors = new ArrayList<>();
				String[] errorData = new String[] { String.valueOf(consignmentDocumentVO.getLineCount()),
						consignmentDocumentVO.getConsignmentNumber() };
				ErrorVO error = new ErrorVO(SaveConsignmentUploadFeatureConstants.ERROR_EXISTING_CONSIGNMENT,
						errorData);
				error.setErrorDescription(SaveConsignmentUploadFeatureConstants.ERROR_EXISTING_CONSIGNMENT);
				errors.add(error);
				mailTrackingBusinessException.addErrors(errors);
				throw mailTrackingBusinessException;
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
			MailTrackingBusinessException mailTrackingBusinessException = new MailTrackingBusinessException();
			Collection<ErrorVO> errors = new ArrayList<>();
			String[] errorData = new String[] { String.valueOf(consignmentDocumentVO.getLineCount()),
					consignmentDocumentVO.getConsignmentNumber() };
			ErrorVO error = new ErrorVO(SaveConsignmentUploadFeatureConstants.ERROR_NEW_CONSIGNMENT, errorData);
			error.setErrorDescription(SaveConsignmentUploadFeatureConstants.ERROR_NEW_CONSIGNMENT);
			errors.add(error);
			mailTrackingBusinessException.addErrors(errors);
			throw mailTrackingBusinessException;
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "perform");
		return consignmentDocumentVO;
	}

}
