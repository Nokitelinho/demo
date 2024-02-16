/**
 * Java file	: 	com.ibsplc.icargo.business.defaults.vo.converter;
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.converter;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;

@BeanConverterRegistry
public class ULDFlightMessageReconcileVOConverter {

	@BeanConversion(from = ULDFlightMessageReconcileVO.class, to = FlightFilterMessageVO.class)
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVOConverter.toFlightFilterMessageVO
	 *	Added on 	:	26-Jul-2022
	 * 	Used for 	:	Converter for ULDFlightMessageReconcileVO
	 *	Parameters	:	@param reconcileVO
	 *	Parameters	:	@return 
	 *	Return type	: 	FlightFilterMessageVO
	 */
	public static FlightFilterMessageVO toFlightFilterMessageVO(ULDFlightMessageReconcileVO reconcileVO) {
		FlightFilterMessageVO filterVO=new FlightFilterMessageVO();
		filterVO.setFlightCarrierId(reconcileVO.getFlightCarrierIdentifier());
		filterVO.setFlightNumber(reconcileVO.getFlightNumber());
		filterVO.setFlightSequenceNumber(reconcileVO.getFlightSequenceNumber());
		filterVO.setAirportCode(reconcileVO.getAirportCode());
		filterVO.setCompanyCode(reconcileVO.getCompanyCode()); 
		filterVO.setFlightDate(reconcileVO.getFlightDate());
		return filterVO;
	}
}
