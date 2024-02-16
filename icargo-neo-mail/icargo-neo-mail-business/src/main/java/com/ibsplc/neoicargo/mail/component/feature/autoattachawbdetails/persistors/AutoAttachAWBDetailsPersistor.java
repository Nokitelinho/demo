package com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.persistors;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.MailbagPK;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.vo.MailManifestDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Slf4j
@Component("autoAttachAWBDetailsPersistor")
public class AutoAttachAWBDetailsPersistor {
	public void persist(MailManifestDetailsVO mailManifestDetailsVO) throws SystemException {
		log.info(this.getClass().getSimpleName(), "persist");
		Collection<MailbagVO> mailbagVOs = mailManifestDetailsVO.getMailbagVOs(); 
		ShipmentValidationVO shipmentValidationVO = mailManifestDetailsVO.getShipmentValidationVO();
		if (Objects.nonNull(mailbagVOs) && !mailbagVOs.isEmpty()) {
			performAttachAWBDetailsForMailbag(mailbagVOs, shipmentValidationVO);

		}
		log.info(this.getClass().getSimpleName(), "persist");
	}

	private void performAttachAWBDetailsForMailbag(Collection<MailbagVO> mailbagVOs,
			ShipmentValidationVO shipmentValidationVO) throws SystemException {
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbag = null;
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPK.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(),
					mailbagVO.getCompanyCode()));
			try {
				mailbag = Mailbag.find(mailbagPK);
			} catch (FinderException e) {
				throw new SystemException(e.getMessage());
			}
			mailbagVO.setMailSequenceNumber(mailbagPK.getMailSequenceNumber());
			if(mailbagVO.getDocumentNumber()==null && mailbagVO.getShipmentPrefix()==null){
			mailbag.performAttachAWBDetailsForMailbag(mailbagVO, shipmentValidationVO);
			mailbagVO.setDocumentOwnerIdr(mailbag.getDocumentOwnerId());
			mailbagVO.setDocumentNumber(mailbag.getMasterDocumentNumber());
			mailbagVO.setShipmentPrefix(mailbag.getShipmentPrefix());
			}
		}

	}

}
