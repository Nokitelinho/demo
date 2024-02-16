package com.ibsplc.icargo.business.mail.operations.event;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.icargo.business.mail.mra.worker.USPSInvoicProcessingWorker;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import static org.mockito.Mockito.*;

public class MailActualWeightSyncFromDWSChannelTest extends AbstractFeatureTest{
	private MailActualWeightSyncFromDWSChannel mailActualWeightSyncFromDWSChannel;
	private DWSMasterVO dWSMasterVO;
	Collection<ContainerVO> ContainerVOs = new ArrayList<ContainerVO>();
	ContainerVO containerVO;
	EventVO eventVO;
	Measure measure;
	@Override
	public void setup() throws Exception {
		// TODO Auto-generated method stub
		EntityManagerMock.mockEntityManager();
		mailActualWeightSyncFromDWSChannel = spy(MailActualWeightSyncFromDWSChannel.class);
		dWSMasterVO = setUpDWSMasterVO();
		//containerVO = mock(ContainerVO.class);
		containerVO = new ContainerVO();
		//eventVO = mock(EventVO.class);
		measure = mock(Measure.class);
		mockDespatchRequest(MailActualWeightSyncFromDWSChannel.class);
	}
	private DWSMasterVO setUpDWSMasterVO() {
		 dWSMasterVO=new DWSMasterVO();
		return dWSMasterVO;
	}
	
	@Test
	public void sendwithvalue() throws Throwable {
		containerVO.setContainerNumber("AKE12121QF");
		containerVO.setCompanyCode("AV");
		//measure=12;
		containerVO.setActualWeight(measure);
		containerVO.setSourceIndicator("M");
		ContainerVOs.add(containerVO);
		eventVO = new EventVO("SAVE_DWS",ContainerVOs);
		eventVO.setTriggerPoint("OPR063");
		doNothing().when(mailActualWeightSyncFromDWSChannel).savedespatchRequest(any(ContainerVO.class));
		 mailActualWeightSyncFromDWSChannel.send(eventVO);
		
	}
	@Test
	public void sendwithoutvalue() throws Throwable {
		containerVO.setContainerNumber("AKE12121QF");
		ContainerVOs.add(containerVO);
		eventVO = new EventVO("SAVE_DWS",ContainerVOs);
		eventVO.setEventType("SAVE_DWS");
		eventVO.setPayload("containerVOs");
		//doReturn(ContainerVOs).when(eventVO).getPayload();
	//	 mailActualWeightSyncFromDWSChannel.send(eventVO);
	 mailActualWeightSyncFromDWSChannel.send(any(EventVO.class));
	//	verify(saveMailbagHistoryChannel,times(1)).despatchsaveMailbagHistoryRequest(mailbagHistoryVOs);
	}
	@Test
	public void sendwithvalueMail() throws Throwable {
		containerVO.setContainerNumber("AKE12121QF");
		containerVO.setCompanyCode("AV");
		//measure=12;
		containerVO.setActualWeight(measure);
		containerVO.setSourceIndicator("MAIL");
		ContainerVOs.add(containerVO);
		eventVO = new EventVO("SAVE_DWS",ContainerVOs);
		eventVO.setTriggerPoint("OPR063");
		//doNothing().when(mailActualWeightSyncFromDWSChannel).SavedespatchRequest(any(ContainerVO.class));
		 mailActualWeightSyncFromDWSChannel.send(eventVO);
		
	}

}
