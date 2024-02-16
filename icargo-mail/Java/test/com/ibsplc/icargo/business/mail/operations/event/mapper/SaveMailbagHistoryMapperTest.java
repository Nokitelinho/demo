package com.ibsplc.icargo.business.mail.operations.event.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class SaveMailbagHistoryMapperTest extends AbstractFeatureTest {

	private ConsignmentDocumentVO consignmentDocumentVO;
	private SaveMailbagHistoryMapper saveMailbagHistoryMapper;

	@Override
	public void setup() throws Exception {
		saveMailbagHistoryMapper = (SaveMailbagHistoryMapper) (ICargoSproutAdapter
				.getBean("mail.operations.saveMailbagHistoryMapper"));
		consignmentDocumentVO = setUpConsignmentDocumentVO();

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
	public void shouldMapConsignmentDocumentVOToMailbagHistoryVO() throws Exception {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = saveMailbagHistoryMapper
				.mapConsignmentDocumentVOToMailbagHistoryVOs(consignmentDocumentVO);
		assertNotNull(mailbagHistoryVOs);
	}

	@Test
	public void shouldNotMapConsignmentDocumentVOToMailbagHistoryVO_WhenConsignmentIsNull() throws Exception {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = saveMailbagHistoryMapper
				.mapConsignmentDocumentVOToMailbagHistoryVOs(null);
		assertTrue(mailbagHistoryVOs.size() == 0);
	}

	@Test
	public void shouldNotMapConsignmentDocumentVOToMailbagHistoryVO_When_MailDetailsIsNull() throws Exception {
		consignmentDocumentVO.setMailInConsignmentVOs(null);
		Collection<MailbagHistoryVO> mailbagHistoryVOs = saveMailbagHistoryMapper
				.mapConsignmentDocumentVOToMailbagHistoryVOs(consignmentDocumentVO);
		assertTrue(mailbagHistoryVOs.size() == 0);
	}

	@Test
	public void shouldNotMapConsignmentDocumentVOToMailbagHistoryVO_When_MailDetailsIsEmpty() throws Exception {
		consignmentDocumentVO.setMailInConsignmentVOs(new Page<MailInConsignmentVO>());
		Collection<MailbagHistoryVO> mailbagHistoryVOs = saveMailbagHistoryMapper
				.mapConsignmentDocumentVOToMailbagHistoryVOs(consignmentDocumentVO);
		assertTrue(mailbagHistoryVOs.size() == 0);
	}

	@Test
	public void shouldNotMapFlightDetailsToMailbagHistoryVO_When_RoutingConsignmentIsNull() throws Exception {
		consignmentDocumentVO.setRoutingInConsignmentVOs(null);
		consignmentDocumentVO.getMailInConsignmentVOs().iterator().next().setUldNumber("AKE1234AV");
		Collection<MailbagHistoryVO> mailbagHistoryVOs = saveMailbagHistoryMapper
				.mapConsignmentDocumentVOToMailbagHistoryVOs(consignmentDocumentVO);
		assertNull(mailbagHistoryVOs.iterator().next().getFlightDate());
	}

	@Test
	public void shouldNotMapFlightDetailsToMailbagHistoryVO_When_RoutingConsignmentIsEmpty() throws Exception {
		consignmentDocumentVO.setRoutingInConsignmentVOs(new ArrayList<RoutingInConsignmentVO>());
		consignmentDocumentVO.getMailInConsignmentVOs().iterator().next().setUldNumber("");
		Collection<MailbagHistoryVO> mailbagHistoryVOs = saveMailbagHistoryMapper
				.mapConsignmentDocumentVOToMailbagHistoryVOs(consignmentDocumentVO);
		assertNull(mailbagHistoryVOs.iterator().next().getFlightDate());
	}
}
