package com.ibsplc.neoicargo.mail.component.feature.stampresdit;



import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.component.feature.stampresdit.persistors.MailResditPersistor;
import com.ibsplc.neoicargo.mail.vo.MailResditVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(StampResditFeatureConstants.STAMP_RESDIT_FEATURE)
@FeatureConfigSource("feature/stampresdit")
public class StampResditFeature extends AbstractFeature<MailResditVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(StampResditFeatureConstants.MODULE_SUBMODULE);


	@Override
	protected Void perform(MailResditVO mailResditVO)  {
		LOGGER.entering(getClass().getSimpleName(), "perform");
		try {
			new MailResditPersistor().persist(mailResditVO);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return null;
	}

}
