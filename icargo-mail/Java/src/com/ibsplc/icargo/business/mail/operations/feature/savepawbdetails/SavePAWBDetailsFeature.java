package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.persistors.SavePAWBDetailsPersistor;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators.DocumentInStockValidator;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators.MandatoryFieldsInPawbValidator;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SavePAWBDetailsFeatureConstants.SAVE_PAWB_FEATURE)
@Feature(exception = MailTrackingBusinessException.class)
public class SavePAWBDetailsFeature extends AbstractFeature<CarditVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SavePAWBDetailsFeatureConstants.MODULE_SUBMODULE);

	@Override
	protected FeatureConfigVO fetchFeatureConfig(CarditVO carditVO) {
					return getBECConfigurationDetails();
	}
	private FeatureConfigVO getBECConfigurationDetails() {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		List<String> preInvokerIds = new ArrayList<>();
		preInvokerIds.add(SavePAWBDetailsFeatureConstants.SAVE_POSTAL_SHIPMENT_INVOKER);
		preInvokerIds.add(SavePAWBDetailsFeatureConstants.SAVE_POSTAL_HAWB_INVOKER);
		featureConfigVO.setPreInvokerIds(preInvokerIds);
		return featureConfigVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Void perform(CarditVO carditVO) throws SystemException, BusinessException {
		LOGGER.entering(getClass().getSimpleName(), "perform");
		new SavePAWBDetailsPersistor().persist(carditVO.getCarditPawbDetailsVO());
		return null;
	}
	
	@Override
	protected void validate(CarditVO carditVO) throws BusinessException {
		try {
			MandatoryFieldsInPawbValidator mandatoryFieldsInPawbValidator =(MandatoryFieldsInPawbValidator) SpringAdapter.getInstance()
					.getBean(SavePAWBDetailsFeatureConstants.MANDATORY_FIELDS_IN_PAWB_VALIDATOR);
			DocumentInStockValidator stockValidator = (DocumentInStockValidator) SpringAdapter.getInstance()
			.getBean(SavePAWBDetailsFeatureConstants.DOCUMENT_IN_STOCK_VALIDATOR);
			mandatoryFieldsInPawbValidator.validate(carditVO);
			stockValidator.validate(carditVO);
		} catch (SystemException e) {
			LOGGER.log(Log.FINE, e);
		}
	}

}
