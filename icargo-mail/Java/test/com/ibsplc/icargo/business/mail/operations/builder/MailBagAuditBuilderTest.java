package com.ibsplc.icargo.business.mail.operations.builder;


import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;


public class MailBagAuditBuilderTest extends AbstractFeatureTest {
	
	private MailBagAuditBuilder mailbagBuilder;
	private Mailbag mailbagBean;

	@Override
	public void setup() throws Exception {
		mailbagBuilder = spy(new MailBagAuditBuilder());
	    mailbagBean = mockBean("MailbagEntity", Mailbag.class);
	    EntityManagerMock.mockEntityManager();
	}
	

	@Test
	public void flagContainerAuditForRetaining_Carriers() throws SystemException{
		ContainerVO containerDetailsVO = new ContainerVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		containerDetailsVO.setFlightNumber("-1");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("-1");
		containerAuditVO.setFlightSequenceNumber(-1);
		containerAuditVO.setLegSerialNumber(-1);
		containerAuditVO.setCompanyCode("AV");
		mailbagBuilder.flagContainerAuditForRetaining(containerDetailsVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("-1"));
	}
	
	@Test
	public void flagContainerAuditForRetaining_Flights() throws SystemException{
		ContainerVO containerDetailsVO = new ContainerVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		containerDetailsVO.setFlightNumber("1000");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("1000");
		containerAuditVO.setFlightSequenceNumber(7);
		containerAuditVO.setLegSerialNumber(1);
		containerAuditVO.setCompanyCode("AV");
		mailbagBuilder.flagContainerAuditForRetaining(containerDetailsVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("1000"));
	}
	
	@Test
	public void flagContainerAuditForAcquital_Carriers() throws SystemException{
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		containerDetailsVO.setFlightNumber("-1");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("-1");
		containerAuditVO.setFlightSequenceNumber(-1);
		containerAuditVO.setLegSerialNumber(-1);
		containerAuditVO.setCompanyCode("AV");
		mailbagBuilder.flagContainerAuditForAcquital(containerDetailsVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("-1"));
	}
	
	@Test
	public void flagContainerAuditForAcquital_Flights() throws SystemException{
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		containerDetailsVO.setFlightNumber("1000");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("1000");
		containerAuditVO.setFlightSequenceNumber(7);
		containerAuditVO.setLegSerialNumber(1);
		containerAuditVO.setCompanyCode("AV");
		mailbagBuilder.flagContainerAuditForAcquital(containerDetailsVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("1000"));
	}

	@Test
	public void flagContainerAuditForDeletion_Carriers() throws SystemException{
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		containerDetailsVO.setFlightNumber("-1");
		containerDetailsVO.setAssignedPort("CDG");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("-1");
		containerAuditVO.setFlightSequenceNumber(-1);
		containerAuditVO.setLegSerialNumber(-1);
		containerAuditVO.setCompanyCode("AV");
		mailbagBuilder.flagContainerAuditForDeletion(containerDetailsVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("-1"));
	}
	
	@Test
	public void flagContainerAuditForDeletion_Flights() throws SystemException{
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		containerDetailsVO.setFlightNumber("1000");
		containerDetailsVO.setAssignedPort("CDG");
		containerDetailsVO.setCarrierCode("AV");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("1000");
		containerAuditVO.setFlightSequenceNumber(7);
		containerAuditVO.setLegSerialNumber(1);
		containerAuditVO.setCompanyCode("AV");
		mailbagBuilder.flagContainerAuditForDeletion(containerDetailsVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("1000"));
	}
	
	@Test
	public void flagContainerAuditForUnassignment_carriers() throws SystemException{
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		containerDetailsVO.setFlightNumber("-1");
		containerDetailsVO.setAssignedPort("CDG");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("-1");
		containerAuditVO.setFlightSequenceNumber(-1);
		containerAuditVO.setLegSerialNumber(-1);
		containerAuditVO.setCompanyCode("AV");
		mailbagBuilder.flagContainerAuditForUnassignment(containerDetailsVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("-1"));
	}
	
	@Test
	public void flagContainerAuditForUnassignment_Flights() throws SystemException{
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		containerDetailsVO.setFlightNumber("1000");
		containerDetailsVO.setAssignedPort("CDG");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("1000");
		containerAuditVO.setFlightSequenceNumber(7);
		containerAuditVO.setLegSerialNumber(1);
		containerAuditVO.setCompanyCode("AV");
		mailbagBuilder.flagContainerAuditForUnassignment(containerDetailsVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("1000"));
	}
	
	@Test
	public void flagContainerAuditForArrival_Carriers() throws SystemException{
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		Collection<ContainerDetailsVO> containers = new ArrayList<>();
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		mailArrivalVO.setContainerDetails(containers);
		mailArrivalVO.setAirportCode("CDG");
		containerDetailsVO.setFlightNumber("-1");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("-1");
		containerAuditVO.setFlightSequenceNumber(-1);
		containerAuditVO.setLegSerialNumber(-1);
		containerAuditVO.setCompanyCode("AV");
		containers.add(containerDetailsVO);
		mailArrivalVO.setContainerDetails(containers);
		mailArrivalVO.setDeliveryNeeded(true);
		mailbagBuilder.flagContainerAuditForArrival(mailArrivalVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("-1"));
	}
	
	@Test
	public void flagContainerAuditForArrival_Flights() throws SystemException{
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		Collection<ContainerDetailsVO> containers = new ArrayList<>();
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,ContainerVO.ENTITY);
		mailArrivalVO.setAirportCode("CDG");
		containerDetailsVO.setFlightNumber("1000");
		containerAuditVO.setContainerNumber("AKE00991AV");
		containerAuditVO.setAssignedPort("CDG");
		containerAuditVO.setCarrierId(1134);
		containerAuditVO.setFlightNumber("1000");
		containerAuditVO.setFlightSequenceNumber(7);
		containerAuditVO.setLegSerialNumber(1);
		containerAuditVO.setCompanyCode("AV");
		containers.add(containerDetailsVO);
		mailArrivalVO.setContainerDetails(containers);
		mailArrivalVO.setDeliveryNeeded(false);
		mailbagBuilder.flagContainerAuditForArrival(mailArrivalVO);
		assertThat(containerDetailsVO.getFlightNumber(),is("1000"));
	}
	@Test
	public void shouldPopulateActionCodeForLostResdit() throws Exception{
		ResditEventVO resditEventVO=new ResditEventVO();
		resditEventVO.setResditEventCode("49");
		ConsignmentInformationVO consignmentInformationVO = new ConsignmentInformationVO();
		consignmentInformationVO.setTransportInformationVOs(new ArrayList<>());
		consignmentInformationVO.setContainerInformationVOs(new ArrayList<>());
		ReceptacleInformationVO receptacleInformationVO = new ReceptacleInformationVO();
		receptacleInformationVO.setMailSequenceNumber(1111);
		receptacleInformationVO.setReceptacleID("USDFWADEFRAAACA13003003001200");
		mailbagBuilder.flagMailbagAuditForResdit(resditEventVO,consignmentInformationVO,receptacleInformationVO);
		verify(mailbagBuilder, times(1)).flagMailbagAuditForResdit(resditEventVO,consignmentInformationVO,receptacleInformationVO);
	}
	@Test
	public void shouldSkipActionCodeIfEventCodeIsNull() throws Exception{
		ResditEventVO resditEventVO=new ResditEventVO();
		resditEventVO.setResditEventCode(null);
		ConsignmentInformationVO consignmentInformationVO = new ConsignmentInformationVO();
		consignmentInformationVO.setTransportInformationVOs(new ArrayList<>());
		consignmentInformationVO.setContainerInformationVOs(new ArrayList<>());
		ReceptacleInformationVO receptacleInformationVO = new ReceptacleInformationVO();
		receptacleInformationVO.setMailSequenceNumber(1111);
		receptacleInformationVO.setReceptacleID("USDFWADEFRAAACA13003003001200");
		mailbagBuilder.flagMailbagAuditForResdit(resditEventVO,consignmentInformationVO,receptacleInformationVO);
		verify(mailbagBuilder, times(1)).flagMailbagAuditForResdit(resditEventVO,consignmentInformationVO,receptacleInformationVO);
	}	
	@Test
	public void testFlagAuditForMailOperartionsSuccess() throws Exception{
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(12344);
		mailbagVO.setCompanyCode("IBS");
		Mailbag mailbag = new Mailbag();
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(12344);
		mailbag.setMailbagPK(mailbagPK);
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		mailbagBuilder.flagAuditForMailOperartions(mailbagVOs,MailbagAuditVO. MAILBAG_DELIVERED);
		verify(mailbagBuilder, times(1)).flagAuditForMailOperartions(mailbagVOs,MailbagAuditVO. MAILBAG_DELIVERED);
	}	
	@Test
	public void testFlagAuditForMailOperartionsWithNullMailbagVOs() throws Exception{
		Collection<MailbagVO> mailbagVOs = null;
		mailbagBuilder.flagAuditForMailOperartions(mailbagVOs,MailbagAuditVO. MAILBAG_DELIVERED);
		verify(mailbagBuilder, times(1)).flagAuditForMailOperartions(mailbagVOs,MailbagAuditVO. MAILBAG_DELIVERED);
	}	
	@Test
	public void testFlagAuditForMailOperartionsWithEmptyMailbagVOs() throws Exception{
		Collection<MailbagVO> mailbagVOs = new ArrayList<>(); ;
		mailbagBuilder.flagAuditForMailOperartions(mailbagVOs,MailbagAuditVO. MAILBAG_DELIVERED);
		verify(mailbagBuilder, times(1)).flagAuditForMailOperartions(mailbagVOs,MailbagAuditVO. MAILBAG_DELIVERED);
	}	
	@Test
	public void testFlagAuditForMailOperartions() throws Exception{
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(12344);
		mailbagVO.setCompanyCode("IBS");
		Collection<MailbagVO> mailbagVOs = new ArrayList<>(); ;
		Mailbag mailbag = new Mailbag();
		mailbag.setLatestStatus("DLV");
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(12344);
		mailbag.setMailbagPK(mailbagPK);
		mailbagVOs.add(mailbagVO);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		mailbagBuilder.flagAuditForMailOperartions(mailbagVOs,MailbagAuditVO. MAILBAG_DELIVERED);
		verify(mailbagBuilder, times(1)).flagAuditForMailOperartions(mailbagVOs,MailbagAuditVO. MAILBAG_DELIVERED);
	}	

}
