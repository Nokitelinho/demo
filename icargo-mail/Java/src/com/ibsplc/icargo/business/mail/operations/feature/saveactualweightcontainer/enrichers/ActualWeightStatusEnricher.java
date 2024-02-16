package com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.enrichers;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class ActualWeightStatusEnricher extends Enricher<ContainerVO> {

	@Override
	public void enrich(ContainerVO containervo) throws SystemException {
		containervo.setActualWeight(containervo.getActualWeight());
	}

}
