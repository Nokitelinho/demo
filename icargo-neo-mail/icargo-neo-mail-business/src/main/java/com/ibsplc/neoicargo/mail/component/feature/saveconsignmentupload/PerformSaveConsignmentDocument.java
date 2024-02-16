package com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload;


import com.ibsplc.neoicargo.mail.component.DocumentController;
import com.ibsplc.neoicargo.mail.exception.DuplicateDSNException;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.exception.InvalidMailTagFormatException;
import com.ibsplc.neoicargo.mail.exception.MailbagAlreadyAcceptedException;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.springframework.stereotype.Component;

@Component("mail.operations.performSaveConsignmentDocument")
public class PerformSaveConsignmentDocument {
	private static final Log LOGGER = LogFactory.getLogger(PerformSaveConsignmentDocument.class.getSimpleName());

	public void perform(ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, MailbagAlreadyAcceptedException, InvalidMailTagFormatException,
			DuplicateDSNException, DuplicateMailBagsException {
		LOGGER.entering("PerformSaveConsignmentDocument", "perform");
		//TODO: neo to correct below code
		//DocumentController documentController = (DocumentController) SpringAdapter.getInstance().getBean("documentController");
		new DocumentController().saveConsignmentDocument(consignmentDocumentVO);
		LOGGER.exiting("PerformSaveConsignmentDocument", "perform");
	}

}
