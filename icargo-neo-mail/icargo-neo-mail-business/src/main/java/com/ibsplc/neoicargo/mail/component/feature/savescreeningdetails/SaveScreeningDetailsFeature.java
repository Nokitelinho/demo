package com.ibsplc.neoicargo.mail.component.feature.savescreeningdetails;

import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component("SaveScreeningDetailsFeature")
@FeatureConfigSource("feature/savescreeningdetailsfeature")
public class SaveScreeningDetailsFeature extends AbstractFeature<ScannedMailDetailsVO> {

	@Override
	protected ScannedMailDetailsVO perform(ScannedMailDetailsVO scannedMailDetailsVO) {
		log.info("SaveScreeningDetailsFeature-->perform");
		return scannedMailDetailsVO;

	}

}