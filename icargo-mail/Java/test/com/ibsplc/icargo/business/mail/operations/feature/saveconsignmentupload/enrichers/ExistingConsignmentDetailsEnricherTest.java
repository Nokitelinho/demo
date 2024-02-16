package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.enrichers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class ExistingConsignmentDetailsEnricherTest extends AbstractFeatureTest {

	private static final String MODULE_SUBMODULE = "mail.operations";
	private ExistingConsignmentDetailsEnricher enricher;
	private ConsignmentDocumentVO consignmentDocumentVO;
	private MailTrackingDefaultsDAO dao;

	@Override
	public void setup() throws Exception {
		enricher = spy((ExistingConsignmentDetailsEnricher) (ICargoSproutAdapter
				.getBean(SaveConsignmentUploadFeatureConstants.EXISTING_CONSIGNMENT_DETAILS)));
		consignmentDocumentVO = setUpConsignmentDocumentVO();
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);

	}

	private ConsignmentDocumentVO setUpConsignmentDocumentVO() {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		consignmentDocumentVO.setConsignmentNumber("DEUS123456");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setType("CN38");
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setOperation("OUTBOUND");
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		mailInConsignmentVO.setMailId("DEFRAAUSDFWAACA01200120001200");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(new Page<MailInConsignmentVO>(
				(ArrayList<MailInConsignmentVO>) mailInConsignmentVOs, 0, 0, 0, 0, 0, false));
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setOnwardFlightNumber("0123");
		routingInConsignmentVO.setOnwardCarrierCode(getCompanyCode());
		routingInConsignmentVO.setPol("FRA");
		routingInConsignmentVO.setPou("DFW");
		routingInConsignmentVO.setCompanyCode(getCompanyCode());
		routingInConsignmentVO.setConsignmentNumber("DEUS123456");
		routingInConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);
		return consignmentDocumentVO;
	}
	
	@Test
	public void shouldEnrichExistingConsignmentDetails_When_ExistingConsignmentIsPresent() throws Exception{
		ConsignmentDocumentVO existingConsignmentDocumentVO = new ConsignmentDocumentVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MODULE_SUBMODULE);
		doReturn(existingConsignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		enricher.enrich(consignmentDocumentVO);
		assertTrue(Objects.nonNull(consignmentDocumentVO.getExistingConsignmentDocumentVOs()));
	}
	@Test
	public void shouldNotEnrichExistingConsignmentDetails_When_ExistingConsignmentIsNotPresent() throws Exception{
		ConsignmentDocumentVO existingConsignmentDocumentVO = null;
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MODULE_SUBMODULE);
		doReturn(existingConsignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		enricher.enrich(consignmentDocumentVO);
		assertTrue(Objects.isNull(consignmentDocumentVO.getExistingConsignmentDocumentVOs()));
	}
	@Test(expected = SystemException.class)
	public void shouldThrowSystemException_When_PersistenceExceptionIsThrown() throws Exception{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MODULE_SUBMODULE);
		doThrow(PersistenceException.class).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		enricher.enrich(consignmentDocumentVO);
	}	
	@Test(expected = SystemException.class)
	public void shouldThrowSystemException_When_PersistenceExceptionIsThrownForDao() throws Exception{
		doThrow(PersistenceException.class).when(PersistenceController.getEntityManager()).getQueryDAO(MODULE_SUBMODULE);
		enricher.enrich(consignmentDocumentVO);
	}		

}
