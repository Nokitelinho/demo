package com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.persistors;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.component.ConsignmentDocument;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.MailbagPK;
import com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.vo.CarditPawbDetailsVO;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SavePAWBDetailsPersistor {
	public void persist(CarditPawbDetailsVO carditPawbDetailsVO) {
		log.debug(this.getClass().getSimpleName() + " : " + "persist" + " Entering");
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
			if (carditPawbDetailsVO.getConsignmentDocumentVO() != null) {
				updateConsignmentDocument(carditPawbDetailsVO);
			}
		}
		log.debug(this.getClass().getSimpleName() + " : " + "persist" + " Exiting");
	}

	private void performAttachAWBDetailsForMailbag(Collection<MailbagVO> mailbagVOs,
			ShipmentValidationVO shipmentValidationVO) {
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
				log.info("Exception :", e);
			}
		}
	}

	private void updateConsignmentDocument(CarditPawbDetailsVO carditPawbDetailsVO) {
		ConsignmentDocumentVO consignmentDocumentVO = carditPawbDetailsVO.getConsignmentDocumentVO();
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		try {
			consignmentDocument = ConsignmentDocument.find(consignmentDocumentVO);
			consignmentDocumentVO.setMasterDocumentNumber(carditPawbDetailsVO.getMasterDocumentNumber());
			consignmentDocument.updateMasterDocumentNumber(consignmentDocumentVO);
		}  catch (SystemException e) {
			log.debug(e.getMessage());
		}
	}
}
