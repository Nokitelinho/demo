package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.enrichers;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SaveConsignmentUploadFeatureConstants.EXISTING_CONSIGNMENT_DETAILS)
public class ExistingConsignmentDetailsEnricher extends Enricher<ConsignmentDocumentVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveConsignmentUploadFeatureConstants.MODULE_SUBMODULE);

	@Override
	public void enrich(ConsignmentDocumentVO consignmentDocumentVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "enrich");
		ConsignmentDocumentVO existingConsignmentDocumentVO =null;
		Collection<ConsignmentDocumentVO> existingConsignmentDocumentVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = constructConsignmentFilterVO(consignmentDocumentVO);
		try {
			existingConsignmentDocumentVO = constructDAO().findConsignmentDocumentDetails(consignmentFilterVO);
		} catch (PersistenceException e) {
			LOGGER.log(Log.FINE, e.getMessage(),e);
			throw new SystemException(e.getMessage());
		}
		if (existingConsignmentDocumentVO != null) {
			existingConsignmentDocumentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_DELETE);
			existingConsignmentDocumentVOs.add(existingConsignmentDocumentVO);
			consignmentDocumentVO.setExistingConsignmentDocumentVOs(existingConsignmentDocumentVOs);
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "enrich");
		
	}
	
	private ConsignmentFilterVO constructConsignmentFilterVO(ConsignmentDocumentVO consignmentDocumentVO) {
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode(consignmentDocumentVO.getCompanyCode());
		consignmentFilterVO.setPaCode(consignmentDocumentVO.getPaCode());
		consignmentFilterVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
		consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_YES);
		return consignmentFilterVO;
	}
	
	protected MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class
					.cast(em.getQueryDAO(SaveConsignmentUploadFeatureConstants.QUERY_MODULE_SUBMODULE));
		} catch (PersistenceException e) {
			LOGGER.log(Log.FINE, e.getMessage(),e);
			throw new SystemException(e.getErrorCode());
		}
	}

}
