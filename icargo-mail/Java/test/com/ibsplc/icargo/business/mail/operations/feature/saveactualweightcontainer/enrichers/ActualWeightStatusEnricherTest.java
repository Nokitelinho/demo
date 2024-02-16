package com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.enrichers;
import org.junit.Test;
import com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.SaveActualWeightInContainerFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
public class ActualWeightStatusEnricherTest extends AbstractFeatureTest {
   private ActualWeightStatusEnricher enricher;
	@Override
	public void setup() throws Exception {
		enricher = mockBean(SaveActualWeightInContainerFeatureConstants.ACTUAL_WEIGHT_STATUS_ENRICHER, ActualWeightStatusEnricher.class);
		
	
	}
	
	@Test
	public void SaveActualWeightContainerPerform() throws SystemException, BusinessException, FinderException {
		ContainerVO containervo= new ContainerVO();
		containervo.setActualWeight(new Measure(UnitConstants.WEIGHT, 5));
		enricher.enrich(containervo);

	}
}
