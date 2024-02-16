package com.ibsplc.icargo.business.xaddons.qf.mail.operations.feature.saveactualweightcontainer.enrichers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.proxy.OperationsFltHandlingProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDWeighingDetailsVO;
import com.ibsplc.icargo.business.xaddons.qf.mail.operations.proxy.QFOperationsFltHandlingProxy;
import com.ibsplc.icargo.business.xaddons.qf.operations.flthandling.vo.WeighingDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;

public class ActualWeightStatusEnricherQFTest extends AbstractFeatureTest {
	private ActualWeightStatusEnricherQF actualWeightStatusEnricherQF;
	private static final String ULD_NUMBER = "AKE45677QF";
	QFOperationsFltHandlingProxy qFOperationsFltHandlingProxy;
	public void setup() throws Exception {
		actualWeightStatusEnricherQF = (ActualWeightStatusEnricherQF) ICargoSproutAdapter.getBean("QF-mail.operations.feature.saveactualweightcontainer.enrichers.actualweightstatusenricher");
		qFOperationsFltHandlingProxy = mockProxy(QFOperationsFltHandlingProxy.class);
	}
	

	@Test
	public void shouldEnrich() throws SystemException, ProxyException{
		ContainerVO containervo = new ContainerVO();
		containervo.setContainerNumber("AKE45677QF");
		OperationalULDWeighingDetailsVO operationalULDWeighingDetailsVO = new OperationalULDWeighingDetailsVO();
		 WeighingDetailsVO weighingDetailsVO = new WeighingDetailsVO();
		 weighingDetailsVO.setNewWeightStatus("RW");
		operationalULDWeighingDetailsVO.setSpecificData(weighingDetailsVO);
		doReturn(operationalULDWeighingDetailsVO).when(qFOperationsFltHandlingProxy).validateAndEnrichActualWeightForMailContainer(any(OperationalULDWeighingDetailsVO.class));
		actualWeightStatusEnricherQF.enrich(containervo);
		assertThat(containervo.getContainerNumber(),is(ULD_NUMBER));
	}

}
