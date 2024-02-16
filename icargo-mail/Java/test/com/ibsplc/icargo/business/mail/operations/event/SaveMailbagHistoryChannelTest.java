package com.ibsplc.icargo.business.mail.operations.event;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

public class SaveMailbagHistoryChannelTest extends AbstractFeatureTest {
	
	private SaveMailbagHistoryChannel saveMailbagHistoryChannel;
	private ConsignmentDocumentVO consignmentDocumentVO;

	@Override
	public void setup() throws Exception {
		consignmentDocumentVO = setUpConsignmentDocumentVO();
		saveMailbagHistoryChannel = spy(SaveMailbagHistoryChannel.class);
		
	}
	
	private ConsignmentDocumentVO setUpConsignmentDocumentVO() {
		consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		consignmentDocumentVO.setConsignmentNumber("DEUS123456");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setType("CN38");
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		mailInConsignmentVO.setMailId("DEFRAAUSDFWAACA01200120001200");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignment(mailInConsignmentVOs);
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setOnwardFlightNumber("123");
		routingInConsignmentVO.setOnwardCarrierCode(getCompanyCode());
		routingInConsignmentVO.setCompanyCode(getCompanyCode());
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingInConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);
		return consignmentDocumentVO;
	}
	
	@Test
	public void shouldDespatchRequestWhenMethodIsCalled() throws Throwable {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<>();
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVOs.add(mailbagHistoryVO);
		doNothing().when(saveMailbagHistoryChannel).despatchsaveMailbagHistoryRequest(mailbagHistoryVOs);
		saveMailbagHistoryChannel.send(new EventVO("saveMailbagHistory", mailbagHistoryVOs));
		verify(saveMailbagHistoryChannel,times(1)).despatchsaveMailbagHistoryRequest(mailbagHistoryVOs);
	}
	@Test
	public void shouldNotDespatchRequest_When_MailbagHistoryIsNull() throws Throwable {
		saveMailbagHistoryChannel.send(new EventVO("saveMailbagHistory", null));
	}	
	@Test
	public void shouldNotDespatchRequest_When_MailbagHistoryIsEmpty() throws Throwable {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<>();
		saveMailbagHistoryChannel.send(new EventVO("saveMailbagHistory", mailbagHistoryVOs));
	}	

}
