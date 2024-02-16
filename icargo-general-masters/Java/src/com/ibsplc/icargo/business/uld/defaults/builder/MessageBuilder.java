/*
 * MessageBuilder.java Created on Sept 05, 2014
 *
 * Copyright 2014 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.builder;

import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-6025
 *
 */
public class MessageBuilder extends AbstractActionBuilder{
	
	private Log log = LogFactory.getLogger("ULD");
	
	/**
	 * @param reconcileVOs
	 * @throws SystemException
	 */
	public void sendUCMMessage(
			Collection<ULDFlightMessageReconcileVO> reconcileVOs)
			throws SystemException {
		log.entering("MessageBuilder", "sendUCMMessage");
		if (reconcileVOs != null && reconcileVOs.size() > 0) {
			for (ULDFlightMessageReconcileVO reconcileVO : reconcileVOs) {
				/*FlightMessageFilterVO flightMessageFilterVO = new FlightMessageFilterVO();
				flightMessageFilterVO.setCompanyCode(reconcileVO.getCompanyCode());
				flightMessageFilterVO.setFlightCarrierIdentifier(reconcileVO.getFlightCarrierIdentifier());
				flightMessageFilterVO.setFlightNumber(reconcileVO.getFlightNumber());
				flightMessageFilterVO.setFlightSequenceNumber(reconcileVO.getFlightSequenceNumber());
				flightMessageFilterVO.setSegmentSerialNumber(reconcileVO.getLegSerialNumber());// segment serial number doubt
				flightMessageFilterVO.setAirportCode(reconcileVO.getAirportCode());*/
				try {
					//new ULDController().generateUCMMessageVO(flightMessageFilterVO, reconcileVO);
					new ULDController().generateUCMMessageVOforFWD(reconcileVO);
				} catch (SystemException e) {
					throw new SystemException(e.getErrors());
				}
			}
		}
	}
}
