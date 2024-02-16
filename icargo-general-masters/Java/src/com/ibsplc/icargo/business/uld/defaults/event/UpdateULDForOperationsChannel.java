/*
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.event;

import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.event.evaluator.UpdateULDForOPSOnSaveAcceptanceChannelEvaluator;
import com.ibsplc.icargo.business.uld.defaults.event.evaluator.UpdateULDsForOperationsOnSaveULDAcceptanceEvaluator;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.event.AbstractChannel;
import com.ibsplc.icargo.framework.event.annotations.EventChannel;
import com.ibsplc.icargo.framework.event.annotations.EventListener;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;

/**
 * 
 * @author A-7900
 *
 */
@Module("uld")
@SubModule("defaults")
@EventChannel(value = "uld.defaults.updateULDForOperationsChannel", 
		targetClass = FlightDetailsVO.class, 
		targetType = ElementType.LIST, 
		listeners = {
			@EventListener(condition="SourceFeatureOfSaveAcceptance != 'OPERATIONS_SHIPMENT_SAVEULDACCEPTANCE'",evaluator = UpdateULDForOPSOnSaveAcceptanceChannelEvaluator.class, event = "OPERATIONS_SHIPMENT_SAVEACCEPTANCE"),
			@EventListener(evaluator = UpdateULDsForOperationsOnSaveULDAcceptanceEvaluator.class, event = "OPERATIONS_SHIPMENT_SAVEULDACCEPTANCE")})
public class UpdateULDForOperationsChannel extends AbstractChannel {

	@Override
	public void send(EventVO eventVO) throws Throwable {
		Collection<FlightDetailsVO> flightDetailsVOs = (Collection<FlightDetailsVO>) eventVO.getPayload();
		despatchRequest("updateULDsForOperations", flightDetailsVOs);

	}

}
