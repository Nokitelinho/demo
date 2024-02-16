package com.ibsplc.icargo.business.mail.operations.worker;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInboundAutoAttachAWBJobScheduleVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;

public class MailInboundAutoAttachAWBWorkerTest extends AbstractFeatureTest {

	private MailInboundAutoAttachAWBWorker worker;
	private MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO;
	private WorkerContext workerContext;

	@Override
	public void setup() throws Exception {
		worker = spy(MailInboundAutoAttachAWBWorker.class);
		EntityManagerMock.mockEntityManager();
		mailInboundAutoAttachAWBJobScheduleVO = new MailInboundAutoAttachAWBJobScheduleVO();
		workerContext = new WorkerContext();
		mockDespatchRequest(MailInboundAutoAttachAWBWorker.class);
		setupMailInboundAutoAttachAWBJobScheduleVO();
	}

	public OperationalFlightVO setupOperationalFlightVO() {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(getCompanyCode());
		operationalFlightVO.setCarrierCode("QF");
		operationalFlightVO.setCarrierId(1134);
		operationalFlightVO.setFlightNumber("1233");
		operationalFlightVO.setFlightSequenceNumber(1);
		return operationalFlightVO;
	}

	public void setupMailInboundAutoAttachAWBJobScheduleVO() {
		mailInboundAutoAttachAWBJobScheduleVO.setCompanyCode(getCompanyCode());
		mailInboundAutoAttachAWBJobScheduleVO.setCarrierCodes("AA,QF");
		mailInboundAutoAttachAWBJobScheduleVO.setPointOfLadingCountries("AU,US");
		mailInboundAutoAttachAWBJobScheduleVO.setPointOfUnladingCountries("AU,US");
		worker.instantiateJobScheduleVO();
		workerContext.setJobScheduleVO(mailInboundAutoAttachAWBJobScheduleVO);
	}

	@Test
	public void shouldNotAutoAttachAWBWithMailInboundAutoAttachAWBWorker_When_FlightsIsNull() throws Exception {
		doReturn(null).when(worker).findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO);
		worker.execute(workerContext);
		verify(worker, times(0)).autoAttachAWBDetails(any(), any());
	}

	@Test
	public void shouldNotAutoAttachAWBWithMailInboundAutoAttachAWBWorker_When_FlightsIsEmpty() throws Exception {
		doReturn(new ArrayList<>()).when(worker)
				.findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO);
		worker.execute(workerContext);
		verify(worker, times(0)).autoAttachAWBDetails(any(), any());
	}

	@Test
	public void shouldNotAutoAttachAWBWithMailInboundAutoAttachAWBWorker_When_MailArrivalVOIsNull() throws Exception {
		OperationalFlightVO operationalFlightVO = setupOperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		doReturn(Stream.of(operationalFlightVO).collect(Collectors.toList())).when(worker)
				.findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO);
		doReturn(null).when(worker).getMailArrivalVOs(operationalFlightVO);
		worker.execute(workerContext);
		verify(worker, times(0)).autoAttachAWBDetails(any(), any());
	}

	@Test
	public void shouldNotAutoAttachAWBWithMailInboundAutoAttachAWBWorker_When_ContainerDetailsIsNull() throws Exception {
		OperationalFlightVO operationalFlightVO = setupOperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		doReturn(Stream.of(operationalFlightVO).collect(Collectors.toList())).when(worker)
				.findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO);
		doReturn(new MailArrivalVO()).when(worker).getMailArrivalVOs(operationalFlightVO);
		worker.execute(workerContext);
		verify(worker, times(0)).autoAttachAWBDetails(any(), any());
	}

	@Test
	public void shouldNotAutoAttachAWBWithMailInboundAutoAttachAWBWorker_When_ContainerDetailsIsEmpty() throws Exception {
		OperationalFlightVO operationalFlightVO = setupOperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		doReturn(Stream.of(operationalFlightVO).collect(Collectors.toList())).when(worker)
				.findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO);
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		mailArrivalVO.setContainerDetails(new ArrayList<>());
		doReturn(mailArrivalVO).when(worker).getMailArrivalVOs(operationalFlightVO);
		worker.execute(workerContext);
		verify(worker, times(0)).autoAttachAWBDetails(any(), any());
	}

	@Test
	public void shouldAutoAttachAWBWithMailInboundAutoAttachAWBWorker() throws Exception {
		OperationalFlightVO operationalFlightVO = setupOperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		doReturn(Stream.of(operationalFlightVO).collect(Collectors.toList())).when(worker)
				.findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO);
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		mailArrivalVO.setContainerDetails(Stream.of(containerDetailsVO).collect(Collectors.toList()));
		doReturn(mailArrivalVO).when(worker).getMailArrivalVOs(operationalFlightVO);
		worker.execute(workerContext);
		verify(worker, times(1)).autoAttachAWBDetails(any(), any());
	}

}
