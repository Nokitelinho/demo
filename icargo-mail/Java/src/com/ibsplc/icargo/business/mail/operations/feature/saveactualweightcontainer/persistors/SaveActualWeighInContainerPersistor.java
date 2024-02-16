package com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.persistors;

import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.Container;
import com.ibsplc.icargo.business.mail.operations.ContainerPK;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.SaveActualWeightInContainerFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveActualWeighInContainerPersistor {
	private static final Log LOGGER = LogFactory.getLogger(SaveActualWeightInContainerFeatureConstants.MODULE_SUBMODULE);
	private static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";
	public ContainerVO persist(ContainerVO containerVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		MailController mailController = (MailController)  SpringAdapter.getInstance().getBean(MAIL_CONTROLLER_BEAN);
		ContainerAssignmentVO containerAssignmentVO = mailController.findLatestContainerAssignment(
				containerVO.getContainerNumber());
		if (Objects.nonNull(containerAssignmentVO)
				&& ContainerAssignmentVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
			Container container = null;
			ContainerPK containerPK = new ContainerPK();
			containerPK.setCompanyCode(containerAssignmentVO.getCompanyCode());
			containerPK.setContainerNumber(containerAssignmentVO.getContainerNumber());
			containerPK.setAssignmentPort(containerAssignmentVO.getAirportCode());
			containerPK.setCarrierId(containerAssignmentVO.getCarrierId());
			containerPK.setFlightNumber(containerAssignmentVO.getFlightNumber());
			containerPK.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
			containerPK.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
			try {
				container = Container.find(containerPK);
			} catch (FinderException e) {
				LOGGER.log(Log.INFO, e);
			}
			if (Objects.nonNull(container)) {
				container.setActualWeight(containerVO.getActualWeight().getSystemValue());
				container.setActualWeightDisplayValue(containerVO.getActualWeight().getDisplayValue());
				container.setActualWeightDisplayUnit(containerVO.getActualWeight().getDisplayUnit());
				if(containerVO.getActWgtSta()!=null){
				container.setActWgtSta(containerVO.getActWgtSta());
				 }
                containerVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
                containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
                containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
                containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
                containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
                containerVO.setAssignedPort(containerAssignmentVO.getAirportCode());
                containerVO.setFlightDate(containerAssignmentVO.getFlightDate());
                containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
			}
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
		return containerVO;
	}

}
