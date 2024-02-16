
package com.ibsplc.icargo.business.mail.mra.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.addons.trucking.vo.TruckOrderShipmentVO;
import com.ibsplc.icargo.business.addons.trucking.vo.TruckOrderVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailAWBVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailVO;
import com.ibsplc.icargo.framework.event.EventConstants.ParameterMap;
import com.ibsplc.icargo.framework.event.EventMapper;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.event.TruckOrderUpdateMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8061	:	17-Jul-2018	:	Draft
 */
@Module("mail")
@SubModule("mra")
public class TruckOrderUpdateMapper implements EventMapper {
	
	/**
	 * 
	 * 	Method		:	TruckOrderUpdateMapper.convertToTruckOrderMailVO
	 *	Added by 	:	a-8061 on 17-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param truckOrderVO
	 *	Parameters	:	@return 
	 *	Return type	: 	TruckOrderMailVO
	 */
	public static TruckOrderMailVO convertToTruckOrderMailVO(TruckOrderVO truckOrderVO) throws SystemException {
		
		TruckOrderMailVO truckOrderMailVO= new TruckOrderMailVO();
		Collection<TruckOrderMailAWBVO> truckOrderMailAWBVOs= new ArrayList<TruckOrderMailAWBVO>();
		TruckOrderMailAWBVO truckOrderMailAWBVO=null;
		Collection<TruckOrderShipmentVO> truckOrderShipmentVOs=truckOrderVO.getTruckOrderShipmentVOs();

		truckOrderMailVO.setFlightCarrierIdr(truckOrderVO.getFlightCarrierIdr());
		truckOrderMailVO.setFlightNumber(truckOrderVO.getFlightNumber());
		truckOrderMailVO.setFlightSequenceNumber(truckOrderVO.getFlightSequenceNumber());
		truckOrderMailVO.setCurrencyCode(truckOrderVO.getCurrencyCode());
		truckOrderMailVO.setTruckOrderNumber(truckOrderVO.getTruckOrderNumber());
		truckOrderMailVO.setTruckOrderStatus(truckOrderVO.getTruckOrderStatus());
		
		if(truckOrderShipmentVOs!=null && !truckOrderShipmentVOs.isEmpty()){

			for(TruckOrderShipmentVO truckOrderShipmentVO: truckOrderShipmentVOs){

								truckOrderMailAWBVO= new TruckOrderMailAWBVO();
								truckOrderMailAWBVO.setDocumentOwnerId(truckOrderShipmentVO.getDocumentOwnerId());
								truckOrderMailAWBVO.setDuplicateNumber(truckOrderShipmentVO.getDuplicateNumber());
								truckOrderMailAWBVO.setMasterDocumentNumber(truckOrderShipmentVO.getMasterDocumentNumber());
								truckOrderMailAWBVO.setShipmentPrefix(truckOrderShipmentVO.getShipmentPrefix());
								truckOrderMailAWBVO.setSequenceNumber(truckOrderShipmentVO.getSequenceNumber());
								truckOrderMailAWBVO.setSegmentOrgin(truckOrderShipmentVO.getSegmentOrgin());
								truckOrderMailAWBVO.setSegmentDestination(truckOrderShipmentVO.getSegmentDestination());
								truckOrderMailAWBVO.setSegmentSerialNumber(1);
								truckOrderMailAWBVO.setTotalTruckCharge(truckOrderShipmentVO.getTotalTruckCharge());
								truckOrderMailAWBVO.setTotalOtherCharge(truckOrderShipmentVO.getOtherCharge());
								truckOrderMailAWBVO.setTruckChargeVAT(truckOrderShipmentVO.getTruckChargeVAT());
								truckOrderMailAWBVO.setOtherChargeVAT(truckOrderShipmentVO.getOtherChargeVAT());
								truckOrderMailAWBVOs.add(truckOrderMailAWBVO);

			}
		}
		
		
		truckOrderMailVO.setTruckOrderMailAWBVO(truckOrderMailAWBVOs);
		return truckOrderMailVO;
		
	}

	@Override
	public EventVO mapToEventVO(HashMap<ParameterMap, Object[]> parameterMap) throws SystemException {
		//log.entering("TruckOrderUpdateMapper", "mapToEventVO");
		Object[] parameters = parameterMap.get(ParameterMap.PARAMETERS);
		EventVO eventVO = new EventVO((String)parameterMap.get(ParameterMap.EVENT)[0], parameters);
		//log.exiting("TruckOrderUpdateMapper", "mapToEventVO");
		return eventVO;
	}

	
	
	
	
}
