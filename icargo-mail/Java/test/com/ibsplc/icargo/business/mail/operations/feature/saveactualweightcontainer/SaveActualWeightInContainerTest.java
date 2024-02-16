package com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.enrichers.ActualWeightStatusEnricher;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeature;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.enrichers.ConsignmentDetailsEnricher;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedULDProductProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedULDProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class SaveActualWeightInContainerTest extends AbstractFeatureTest {
	private SaveActualWeightInContainerFeature spy;
	private  ActualWeightStatusEnricher enricher;
	private SharedULDProductProxy sharedULDProductProxy;
	private MailTrackingDefaultsDAO dao;
	private static final String MAIL_OPERATIONS = "mail.operations";
	public void setup() throws Exception {

		spy = spy((SaveActualWeightInContainerFeature) ICargoSproutAdapter.getBean(SaveActualWeightInContainerFeatureConstants.SAVE_ACTUAL_WEIGHT_CONTAINER));
		enricher = mockBean(SaveActualWeightInContainerFeatureConstants.ACTUAL_WEIGHT_STATUS_ENRICHER, ActualWeightStatusEnricher.class);
		sharedULDProductProxy = mockProxy(SharedULDProductProxy.class);
		dao = mock(MailTrackingDefaultsDAO.class);
					
	}
	
	@Test
	public void SaveActualWeightContainerPerform() throws SystemException, BusinessException, FinderException, PersistenceException {
		Collection<ULDTypeVO> uldTypes=new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		ULDTypeVO uldType=new ULDTypeVO();
		uldType.setCompanyCode("QF");
		uldType.setUldType("C");
		uldTypes.add(uldType);
		ContainerVO containervo= new ContainerVO();
		containervo.setContainerNumber("AKE02007QF");
		containervo.setCompanyCode("QF");
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO ();
		ULDTypeFilterVO uldTypeFilterVO=new ULDTypeFilterVO();
		uldTypeFilterVO.setCompanyCode("QF");
		uldTypeFilterVO.setUldTypeCode("AKE");
		doReturn(uldTypes).when(sharedULDProductProxy).findULDTypes(uldTypeFilterVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		spy.fetchFeatureConfig(containervo);
		enricher.enrich(containervo);
		spy.perform(containervo);

	}
	
	
}
