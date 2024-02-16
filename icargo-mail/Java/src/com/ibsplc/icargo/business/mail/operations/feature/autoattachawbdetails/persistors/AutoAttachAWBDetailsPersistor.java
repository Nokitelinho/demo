package com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.persistors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class AutoAttachAWBDetailsPersistor {
	private static final Log LOGGER = LogFactory.getLogger(AutoAttachAWBDetailsFeatureConstants.MODULE_SUBMODULE);

	public void persist(MailManifestDetailsVO mailManifestDetailsVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		Collection<MailbagVO> mailbagVOs = mailManifestDetailsVO.getMailbagVOs(); 
		ShipmentValidationVO shipmentValidationVO = mailManifestDetailsVO.getShipmentValidationVO();
		if (Objects.nonNull(mailbagVOs) && !mailbagVOs.isEmpty()) {
			performAttachAWBDetailsForMailbag(mailbagVOs, shipmentValidationVO);

		}
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
	}

	private void performAttachAWBDetailsForMailbag(Collection<MailbagVO> mailbagVOs,
			ShipmentValidationVO shipmentValidationVO) throws SystemException {
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbag = null;
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
			try {
				mailbagPK.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(),
						mailbagVO.getCompanyCode()));
			} catch (SystemException e) {
				throw new SystemException(e.getMessage());
			}
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
