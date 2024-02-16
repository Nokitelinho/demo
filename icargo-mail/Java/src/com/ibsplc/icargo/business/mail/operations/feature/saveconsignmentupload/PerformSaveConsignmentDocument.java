package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload;

import com.ibsplc.icargo.business.mail.operations.DocumentController;
import com.ibsplc.icargo.business.mail.operations.DuplicateDSNException;
import com.ibsplc.icargo.business.mail.operations.DuplicateMailBagsException;
import com.ibsplc.icargo.business.mail.operations.InvalidMailTagFormatException;
import com.ibsplc.icargo.business.mail.operations.MailbagAlreadyAcceptedException;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("mail.operations.performSaveConsignmentDocument")
public class PerformSaveConsignmentDocument {
	private static final Log LOGGER = LogFactory.getLogger(PerformSaveConsignmentDocument.class.getSimpleName());

	public void perform(ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, MailbagAlreadyAcceptedException, InvalidMailTagFormatException,
			DuplicateDSNException, DuplicateMailBagsException {
		LOGGER.entering("PerformSaveConsignmentDocument", "perform");
		DocumentController documentController = (DocumentController) SpringAdapter.getInstance().getBean("documentController");
		documentController.saveConsignmentDocument(consignmentDocumentVO);
		LOGGER.exiting("PerformSaveConsignmentDocument", "perform");
	}

}
