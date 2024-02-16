package com.ibsplc.icargo.business.mail.operations.event.mapper;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSBulkDetailsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSULDDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class MailActualWeightSyncFromDWSMapperTest extends AbstractFeatureTest{
	private MailActualWeightSyncFromDWSMapper mailActualWeightSyncFromDWSMapper; 
	Collection<ContainerVO> containerVOs;
	DWSMasterVO dWSMasterVO;
	Collection<DWSULDDetailsVO> dWSULDDetailsVOs = new ArrayList<DWSULDDetailsVO>();
	DWSULDDetailsVO dWSULDDetailsVO = new DWSULDDetailsVO();
	Collection<DWSBulkDetailsVO> dWSBulkDetailsVOs = new ArrayList<DWSBulkDetailsVO>();
	DWSBulkDetailsVO dWSBulkDetailsVO = new DWSBulkDetailsVO();
	@Override
	public void setup() throws Exception {
		mailActualWeightSyncFromDWSMapper = spy(MailActualWeightSyncFromDWSMapper.class);
		 containerVOs = new ArrayList<ContainerVO>();
		 dWSMasterVO = new DWSMasterVO();
	}
	
	@Test
	public void mapDWSMasterVOToMailFlightSummaryVOCall() throws SystemException {
		doReturn(containerVOs).when(mailActualWeightSyncFromDWSMapper).convertDWSMasterVOToContainerVO(any());
		mailActualWeightSyncFromDWSMapper.mapDWSMasterVOToMailFlightSummaryVO(dWSMasterVO);
	}
	
	@Test
	public void convertDWSMasterVOToContainerVOCall_uld_u() throws SystemException {
		dWSULDDetailsVO.setOperationFlag("U");
		dWSULDDetailsVO.setContentId("M");
		dWSULDDetailsVOs.add(dWSULDDetailsVO);
		dWSMasterVO.setDwsULDDetails(dWSULDDetailsVOs);
		dWSMasterVO.setDwsBulkDetails(dWSBulkDetailsVOs);
		mailActualWeightSyncFromDWSMapper.convertDWSMasterVOToContainerVO(dWSMasterVO);
	}
	
	@Test
	public void convertDWSMasterVOToContainerVOCall_bulk_u() throws SystemException {
		dWSBulkDetailsVO.setOperationFlag("U");
		dWSBulkDetailsVO.setContentId("M");
		dWSBulkDetailsVOs.add(dWSBulkDetailsVO);
		dWSMasterVO.setDwsULDDetails(dWSULDDetailsVOs);
		dWSMasterVO.setDwsBulkDetails(dWSBulkDetailsVOs);
		mailActualWeightSyncFromDWSMapper.convertDWSMasterVOToContainerVO(dWSMasterVO);
	}
	@Test
	public void convertDWSMasterVOToContainerVOCall_uld_not_u() throws SystemException {
		dWSULDDetailsVO.setOperationFlag("A");
		dWSULDDetailsVO.setContentId("M");
		dWSULDDetailsVOs.add(dWSULDDetailsVO);
		dWSMasterVO.setDwsULDDetails(dWSULDDetailsVOs);
		dWSMasterVO.setDwsBulkDetails(dWSBulkDetailsVOs);
		mailActualWeightSyncFromDWSMapper.convertDWSMasterVOToContainerVO(dWSMasterVO);
	}
	@Test
	public void convertDWSMasterVOToContainerVOCall_uld_not_null() throws SystemException {
		dWSULDDetailsVO.setOperationFlag(null);
		dWSULDDetailsVO.setContentId("M");
		dWSULDDetailsVOs.add(dWSULDDetailsVO);
		dWSMasterVO.setDwsULDDetails(dWSULDDetailsVOs);
		dWSMasterVO.setDwsBulkDetails(dWSBulkDetailsVOs);
		mailActualWeightSyncFromDWSMapper.convertDWSMasterVOToContainerVO(dWSMasterVO);
	}
	@Test
	public void convertDWSMasterVOToContainerVOCall_bulk_not_u() throws SystemException {
		dWSBulkDetailsVO.setOperationFlag("A");
		dWSBulkDetailsVO.setContentId("M");
		dWSBulkDetailsVOs.add(dWSBulkDetailsVO);
		dWSMasterVO.setDwsULDDetails(dWSULDDetailsVOs);
		dWSMasterVO.setDwsBulkDetails(dWSBulkDetailsVOs);
		mailActualWeightSyncFromDWSMapper.convertDWSMasterVOToContainerVO(dWSMasterVO);
	}
	@Test
	public void convertDWSMasterVOToContainerVOCall_bulk_null() throws SystemException {
		dWSBulkDetailsVO.setOperationFlag(null);
		dWSBulkDetailsVO.setContentId("M");
		dWSBulkDetailsVOs.add(dWSBulkDetailsVO);
		dWSMasterVO.setDwsULDDetails(dWSULDDetailsVOs);
		dWSMasterVO.setDwsBulkDetails(dWSBulkDetailsVOs);
		mailActualWeightSyncFromDWSMapper.convertDWSMasterVOToContainerVO(dWSMasterVO);
	}

}
