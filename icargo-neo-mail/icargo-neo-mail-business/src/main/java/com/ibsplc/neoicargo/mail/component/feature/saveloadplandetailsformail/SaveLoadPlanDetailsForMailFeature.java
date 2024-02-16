package com.ibsplc.neoicargo.mail.component.feature.saveloadplandetailsformail;



import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.component.feature.saveloadplandetailsformail.persistors.SaveLoadPlanDetailsForMailPersistor;
import com.ibsplc.neoicargo.mail.vo.FlightLoadPlanContainerVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.springframework.stereotype.Component;

@Component(SaveLoadPlanDetailsForMailFeatureConstants.SAVE_LOADPLAN_FEATURE)
@FeatureConfigSource("mail/saveloadplanformail")
public class SaveLoadPlanDetailsForMailFeature extends AbstractFeature<FlightLoadPlanContainerVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveLoadPlanDetailsForMailFeatureConstants.MODULE_SUBMODULE);


	@SuppressWarnings("unchecked")
	@Override
	protected Void perform(FlightLoadPlanContainerVO loadPlanVO)  {
		LOGGER.entering(getClass().getSimpleName(), "perform");
		try {
			new SaveLoadPlanDetailsForMailPersistor().persist(loadPlanVO);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return null;
	}
}
