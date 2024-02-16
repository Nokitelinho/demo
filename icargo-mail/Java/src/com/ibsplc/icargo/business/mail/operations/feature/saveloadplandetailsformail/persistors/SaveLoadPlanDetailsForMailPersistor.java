package com.ibsplc.icargo.business.mail.operations.feature.saveloadplandetailsformail.persistors;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.FlightLoadPlanContainer;
import com.ibsplc.icargo.business.mail.operations.FlightLoadPlanContainerPK;
import com.ibsplc.icargo.business.mail.operations.feature.saveloadplandetailsformail.SaveLoadPlanDetailsForMailFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.FlightLoadPlanContainerVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveLoadPlanDetailsForMailPersistor {
	private static final Log LOGGER = LogFactory.getLogger(SaveLoadPlanDetailsForMailFeatureConstants.MODULE_SUBMODULE);

	public void persist(FlightLoadPlanContainerVO loadPlanVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		FlightLoadPlanContainerPK loadPlanContainerPK = null;
		FlightLoadPlanContainer flightLoadPlanContainer=null;
		if (loadPlanVO!= null) {						
				if(FlightLoadPlanContainerVO.OPERATION_FLAG_INSERT.equals(loadPlanVO.getOperationFlag())){
					updatePreviousLoadPlanStatusForContainer(loadPlanVO);
					loadPlanVO.setPlanStatus("N");
					newFlightLoadPlanContainer(loadPlanVO);
				}else if(FlightLoadPlanContainerVO.OPERATION_FLAG_UPDATE.equals(loadPlanVO.getOperationFlag())){
					loadPlanContainerPK=new FlightLoadPlanContainerPK();
					populateLoadPlanPK(loadPlanVO,loadPlanContainerPK);
					try {
						flightLoadPlanContainer=FlightLoadPlanContainer.find(loadPlanContainerPK);
					} catch (FinderException e) {
						LOGGER.log(Log.FINE, e);
					}
					if(flightLoadPlanContainer!=null) {
						loadPlanVO.setPlanStatus(flightLoadPlanContainer.getPlanStatus());
						flightLoadPlanContainer.update(loadPlanVO);
					}
					
				}else if(FlightLoadPlanContainerVO.OPERATION_FLAG_DELETE.equals(loadPlanVO.getOperationFlag())){
					loadPlanContainerPK=new FlightLoadPlanContainerPK();
					populateLoadPlanPK(loadPlanVO,loadPlanContainerPK);
					updateLoadPlanStatus(loadPlanContainerPK,"D");
				}else{
					LOGGER.log(Log.FINE, "Operation Flag empty");
				}
				
			
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
	}
	
	private void updatePreviousLoadPlanStatusForContainer(FlightLoadPlanContainerVO loadPlanVO)
			throws SystemException {		
		Collection<FlightLoadPlanContainerVO> loadPlanVOs =FlightLoadPlanContainer.findPreviousLoadPlanVersionsForContainer(loadPlanVO);
		for(FlightLoadPlanContainerVO loanPlanContainerVO:loadPlanVOs){
			if(!loanPlanContainerVO.getPlanStatus().equals("D")) { 
			FlightLoadPlanContainerPK loadPlanContainerPK = new FlightLoadPlanContainerPK();
			populateLoadPlanPK(loanPlanContainerVO,loadPlanContainerPK);
			updateLoadPlanStatus(loadPlanContainerPK,"M");
			}
		}
	}
	
	private void populateLoadPlanPK(FlightLoadPlanContainerVO loadPlanVO,FlightLoadPlanContainerPK loadPlanContainerPK)
	{
		loadPlanContainerPK.setCompanyCode(loadPlanVO.getCompanyCode());
		loadPlanContainerPK.setCarrierId(loadPlanVO.getCarrierId());
		loadPlanContainerPK.setContainerNumber(loadPlanVO.getContainerNumber());
		loadPlanContainerPK.setFlightNumber(loadPlanVO.getFlightNumber());
		loadPlanContainerPK.setFlightSequenceNumber(loadPlanVO.getFlightSequenceNumber());
		loadPlanContainerPK.setSegOrigin(loadPlanVO.getSegOrigin());
		loadPlanContainerPK.setSegDestination(loadPlanVO.getSegDestination());
		loadPlanContainerPK.setLoadPlanVersion(loadPlanVO.getLoadPlanVersion());
	}
	
	private void updateLoadPlanStatus(FlightLoadPlanContainerPK flightLoadPlanContainerPK,String status)
			throws SystemException {
		FlightLoadPlanContainer flightLoadPlanContainer=null;
		try {
			flightLoadPlanContainer=FlightLoadPlanContainer.find(flightLoadPlanContainerPK);
		} catch (FinderException e) {
			LOGGER.log(Log.FINE, e);
		}
		if(flightLoadPlanContainer!=null) {
			flightLoadPlanContainer.setPlanStatus(status);
		}
		
	}
	
	private void newFlightLoadPlanContainer(FlightLoadPlanContainerVO loadPlanVO) throws SystemException {
		FlightLoadPlanContainer flightLoadPlanContainer = new FlightLoadPlanContainer(loadPlanVO);
        try {
			PersistenceController.getEntityManager().persist(flightLoadPlanContainer);
		} catch (CreateException e) {
			LOGGER.log(Log.FINE, e);
		}

	}
	}
