/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.AgentPartyTypeDetailsEnricher.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers;


import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;



import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.AgentPartyTypeDetailsEnricher.java
 *	This class is used for
 *	This enricher should be executed in order 2 else will affect non-carrier id enricher
 *	Filter conditions over getUldTransactionDetailsVOs will be more expanded during code re-factoring
 */
@FeatureComponent("LUCMessageFeature.AgentPartyTypeDetailsEnricher")
public class AgentPartyTypeDetailsEnricher extends Enricher<TransactionVO> {
	private static final Log LOGGER = LogFactory.getLogger("ULD DEFAULTS");

	@Override
	public void enrich(TransactionVO transactionVO) throws SystemException {
		LOGGER.entering(this.getClass().getCanonicalName(), "enrich");
		for (ULDTransactionDetailsVO transactionDetailsVO : transactionVO.getUldTransactionDetailsVOs().stream()
		.filter(transactionDetail -> (transactionDetail.isLUCMessageRequired())).collect(Collectors.toList())) {
			if (Objects.equals(ULDTransactionDetailsVO.AGENT, transactionDetailsVO.getPartyType())) {
				/*
				 * In the case of Acceptance we have partyType as agent and the from party will be the agent.
				 * So here we need to set as per the below logic. On other cases where partyType is Agent like in ULD Delivery
				 * we can proceed by maintaining values as such as the from Party is the airline only in the case of delivery.
				 */
				if(Objects.equals(FlightDetailsVO.ACCEPTANCE, transactionVO.getTransactionId())){
					transactionDetailsVO.setFromPartyCode(
							transactionDetailsVO.getUldNumber().substring(transactionDetailsVO.getUldNumber().length() - 2));
				}
		        String ctrlRcptNo = findCRNForULDTransaction(transactionDetailsVO.getCompanyCode(),
		        		transactionDetailsVO.getFromPartyCode());
		        if (Objects.nonNull(ctrlRcptNo)) {
		        	StringJoiner crn = new StringJoiner("")
			        		.add(ctrlRcptNo.substring(0, 4)).add("0")
			                .add(ctrlRcptNo.substring(4, 11));
			        
			        transactionDetailsVO.setControlReceiptNumber(crn.toString());
		        }
			}
		}
	}
	

	private String findCRNForULDTransaction(String companyCode, String carrierCode ) throws SystemException {
		return getULDController().findCRNForULDTransaction(companyCode, carrierCode);
	}
	
	private ULDController getULDController() throws SystemException {
		return (ULDController) SpringAdapter.getInstance().getBean("ULDController");
	}
	
}