package com.ibsplc.icargo.business.mail.operations.event.mapper;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;

import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSBulkDetailsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSULDDetailsVO;

import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@BeanConverterRegistry("mail.operations.MailActualWeightSyncFromDWSMapper")
public class MailActualWeightSyncFromDWSMapper {
	private static final Log LOGGER = LogFactory.getLogger(MailActualWeightSyncFromDWSMapper.class.getSimpleName());
	@BeanConversion(from = DWSMasterVO.class, to = ContainerVO.class, group = "SAVE_DWS_EVENT")
	public Collection<ContainerVO> mapDWSMasterVOToMailFlightSummaryVO(
			DWSMasterVO dWSMasterVO) throws SystemException {
		return convertDWSMasterVOToContainerVO(dWSMasterVO);
	}

	public Collection<ContainerVO> convertDWSMasterVOToContainerVO(DWSMasterVO dWSMasterVO) throws SystemException {
		
		LOGGER.log(Log.FINE, "convertDWSMasterVOToContainerVO");
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		Collection<DWSULDDetailsVO> dWSULDDetailsVOs = dWSMasterVO.getDwsULDDetails();
		Collection<DWSBulkDetailsVO> dWSBulkDetailsVOs = dWSMasterVO.getDwsBulkDetails();
		for(DWSULDDetailsVO DWSULDDetailsVO : dWSULDDetailsVOs) {
			if(DWSULDDetailsVO.getOperationFlag()!=null && DWSULDDetailsVO.getOperationFlag().equals("U") && DWSULDDetailsVO.getContentId().equals("M")) {
				ContainerVO containerVO = new ContainerVO(); 
				containerVO.setContainerNumber(DWSULDDetailsVO.getUldNumber());
				containerVO.setActualWeight(DWSULDDetailsVO.getActualWeight());
				containerVO.setCompanyCode(DWSULDDetailsVO.getCompanyCode());
				containerVO.setLastUpdateUser(dWSMasterVO.getLastUpdateUser());
				containerVO.setLegSerialNumber(DWSULDDetailsVO.getLegSerialNumber());
				containerVO.setFlightSequenceNumber(DWSULDDetailsVO.getFlightSequenceNumber());
				containerVO.setContainerType("U");
				containerVO.setFlightNumber(DWSULDDetailsVO.getFlightNumber());
					containerVO.setFlightDate(DWSULDDetailsVO.getFlightDate());
					containerVO.setAssignedPort(DWSULDDetailsVO.getAirportCode());
					containerVO.setCarrierId(DWSULDDetailsVO.getCarrierId());
					containerVO.setCarrierCode(dWSMasterVO.getCarrierCode());
					containerVO.setSegmentSerialNumber(DWSULDDetailsVO.getSegSerialNumber());
					containerVO.setPou(DWSULDDetailsVO.getPou());
					containerVO.setAssignedUser(dWSMasterVO.getLastUpdateUser());
				containerVOs.add(containerVO);
		}		
		}
		for(DWSBulkDetailsVO DWSBulkDetailsVO : dWSBulkDetailsVOs) { 
			if(DWSBulkDetailsVO.getOperationFlag()!=null && DWSBulkDetailsVO.getOperationFlag().equals("U") && DWSBulkDetailsVO.getContentId().equals("M")) {
				ContainerVO containerVO = new ContainerVO();
			containerVO.setContainerNumber(DWSBulkDetailsVO.getContainerId());
			containerVO.setActualWeight(DWSBulkDetailsVO.getActualWeight());
			containerVO.setCompanyCode(DWSBulkDetailsVO.getCompanyCode());
			containerVO.setLastUpdateUser(dWSMasterVO.getLastUpdateUser());
			containerVO.setLegSerialNumber(DWSBulkDetailsVO.getLegSerialNumber());
			containerVO.setFlightSequenceNumber(DWSBulkDetailsVO.getFlightSequenceNumber());
			containerVO.setContainerType("B");
			containerVO.setFlightNumber(DWSBulkDetailsVO.getFlightNumber());
				containerVO.setFlightDate(DWSBulkDetailsVO.getFlightDate());
				containerVO.setAssignedPort(DWSBulkDetailsVO.getAirportCode());
				containerVO.setCarrierId(DWSBulkDetailsVO.getCarrierId());
				containerVO.setCarrierCode(dWSMasterVO.getCarrierCode());
				containerVO.setSegmentSerialNumber(DWSBulkDetailsVO.getSegSerialNumber());
				containerVO.setPou(DWSBulkDetailsVO.getPou());
				containerVO.setAssignedUser(dWSMasterVO.getLastUpdateUser());
			containerVOs.add(containerVO);
		}		
		}
		return containerVOs;
	}
}
