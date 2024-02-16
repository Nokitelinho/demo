package com.ibsplc.neoicargo.mail.component.feature.savepawbdetails;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.persistors.SavePAWBDetailsPersistor;
import com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.validators.DocumentInStockValidator;
import com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.validators.MandatoryFieldsInPawbValidator;
import com.ibsplc.neoicargo.mail.vo.CarditVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("savePAWBDetailsFeature")
@FeatureConfigSource("feature/savepawbdetails")
@Slf4j

public class SavePAWBDetailsFeature extends AbstractFeature<CarditVO> {

	@Override
	protected Void perform(CarditVO carditVO) throws BusinessException {
		log.debug(getClass().getSimpleName() + " : " + "perform" + " Entering");
		new SavePAWBDetailsPersistor().persist(carditVO.getCarditPawbDetailsVO());
		return null;
	}
	/**
	 * Overriding Method : @see
	 * com.ibsplc.neoicargo.framework.orchestration.AbstractFeature#perform(com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO)
	 * Parameters : @return Parameters : @throws BusinessException
	 *
	 * @throws BusinessException
	 */
	@Override
	protected void validate(CarditVO carditVO) throws BusinessException {
		try {
			MandatoryFieldsInPawbValidator mandatoryFieldsInPawbValidator = ContextUtil.getInstance()
					.getBean(MandatoryFieldsInPawbValidator.class);
			DocumentInStockValidator stockValidator = ContextUtil.getInstance().getBean(DocumentInStockValidator.class);
			mandatoryFieldsInPawbValidator.validate(carditVO);
			stockValidator.validate(carditVO);
		}catch (SystemException e) {

		}
	}
}
