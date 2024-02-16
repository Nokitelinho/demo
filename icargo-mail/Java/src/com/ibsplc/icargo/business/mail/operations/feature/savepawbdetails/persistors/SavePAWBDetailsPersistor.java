package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.persistors;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.ConsignmentDocument;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SavePAWBDetailsPersistor {
	private static final Log LOGGER = LogFactory.getLogger(SavePAWBDetailsFeatureConstants.MODULE_SUBMODULE);

	public void persist(CarditPawbDetailsVO carditPawbDetailsVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		if (carditPawbDetailsVO.getShipmentValidationVO() != null) {
			Collection<MailbagVO> mailbagVOs = new ArrayList<>();
			for (MailInConsignmentVO mailInConsignmentVO : carditPawbDetailsVO.getMailInConsignmentVOs()) {
				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
				mailbagVO.setMailbagId(mailInConsignmentVO.getMailId());
				mailbagVO.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
				mailbagVOs.add(mailbagVO);
			}
			if (!mailbagVOs.isEmpty()) {
				performAttachAWBDetailsForMailbag(mailbagVOs, carditPawbDetailsVO.getShipmentValidationVO());

			}
			if(carditPawbDetailsVO.getConsignmentDocumentVO()!=null) {
				updateConsignmentDocument(carditPawbDetailsVO);
			}
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
	}

	private void performAttachAWBDetailsForMailbag(Collection<MailbagVO> mailbagVOs,
			ShipmentValidationVO shipmentValidationVO) throws SystemException {
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbag = null;
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			try {
				mailbag = Mailbag.find(mailbagPK);
				mailbagVO.setMailSequenceNumber(mailbagPK.getMailSequenceNumber());
				mailbag.performAttachAWBDetailsForMailbag(mailbagVO, shipmentValidationVO);
				mailbagVO.setDocumentOwnerIdr(mailbag.getDocumentOwnerId());
				mailbagVO.setDocumentNumber(mailbag.getMasterDocumentNumber());
				mailbagVO.setShipmentPrefix(mailbag.getShipmentPrefix());
			} catch (FinderException e) {
				LOGGER.log(Log.INFO, e);
			}
			

		}
	}
	private void updateConsignmentDocument(CarditPawbDetailsVO carditPawbDetailsVO) {
		ConsignmentDocumentVO consignmentDocumentVO = carditPawbDetailsVO.getConsignmentDocumentVO();
		ConsignmentDocument consignmentDocument = new ConsignmentDocument() ;
		try {
			consignmentDocument = ConsignmentDocument.find(consignmentDocumentVO);
				consignmentDocumentVO.setMasterDocumentNumber(carditPawbDetailsVO.getMasterDocumentNumber());	
				consignmentDocument.populateAttributes(consignmentDocumentVO);
		} catch (SystemException e) {
			LOGGER.log(Log.INFO, e);
		}

	}
}
