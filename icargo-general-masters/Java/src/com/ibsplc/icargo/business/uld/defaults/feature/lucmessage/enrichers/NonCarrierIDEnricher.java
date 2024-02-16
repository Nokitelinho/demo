/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.NonCarrierIDEnricher.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParameterConfigurationVO;
import com.ibsplc.icargo.framework.feature.Proxy;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.NonCarrierIDEnricher.java
 *	This class is used for	enriching non-carrier id which will be populated in LUC messages.
 *	Filter conditions over getUldTransactionDetailsVOs will be more expanded during code re-factoring
 */
@FeatureComponent("LUCMessageFeature.NonCarrierIDEnricher")
public class NonCarrierIDEnricher extends Enricher<TransactionVO> {
	
	private static final String PARTYTYPE_CUSTOMER = "C";

	private static final String MASTER_TYPE_LUC_CUSTOMER_CODE_MAPPING = "Customer code mapping for LUC messages"; 
	
	private static final Log LOGGER = LogFactory.getLogger("ULD DEFAULTS");
	@Override
	public void enrich(TransactionVO transactionVO) throws SystemException {
		LOGGER.entering(this.getClass().getCanonicalName(), "enrich");
	
		for (ULDTransactionDetailsVO transactionDetailsVO : transactionVO.getUldTransactionDetailsVOs().stream()
		.filter(filterItem -> (filterItem.isLUCMessageRequired() && isValidPartyType(filterItem.getPartyType())))
		.collect(Collectors.toList())) {
			Collection<GeneralParameterConfigurationVO> generalParameterConfigurationVOs = new ArrayList<>();
			GeneralParameterConfigurationVO generalParameterConfigurationVO = new GeneralParameterConfigurationVO();
			generalParameterConfigurationVO.setCompanyCode(transactionDetailsVO.getCompanyCode());
			generalParameterConfigurationVO.setMasterType(MASTER_TYPE_LUC_CUSTOMER_CODE_MAPPING);
			generalParameterConfigurationVOs.add(generalParameterConfigurationVO);
			generalParameterConfigurationVOs = Proxy.getInstance().get(SharedDefaultsProxy.class)
					.findGeneralParameterConfigurations(generalParameterConfigurationVOs);
			if (Objects.nonNull(generalParameterConfigurationVOs)) {
				Optional<GeneralParameterConfigurationVO> optionalConfigVO = generalParameterConfigurationVOs.stream()
						.filter(configuration -> (Objects.equals(configuration.getConfigurationReferenceOne(), 
										getValueForReferenceOne(transactionDetailsVO, transactionVO.getTransactionId()))
								&& Objects.equals(configuration.getConfigurationReferenceTwo(), 
										transactionDetailsVO.getTransactionStationCode())
								&& Objects.equals(configuration.getParmeterCode(),
										getValueForParameterCode(transactionDetailsVO, transactionVO.getTransactionId()))))
						.findFirst();
				if (optionalConfigVO.isPresent()) {
					transactionDetailsVO.setNonCarrierId(optionalConfigVO.get().getParameterValue());
				}
			}
		}
	}
	
	/**
	 * 	Method		:	NonCarrierIDEnricher.getValueForReferenceOne
	 *	Added on 	:	12-Dec-2022
	 * 	Used for 	:	getting partyCode based on transaction.
	 * 					this logic will be more expanded during code re-factoring
	 *	Parameters	:	@param transactionDetailsVO
	 *	Parameters	:	@param transactionId
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	private String getValueForReferenceOne(ULDTransactionDetailsVO transactionDetailsVO, String  transactionId) {
		return Objects.equals(FlightDetailsVO.ACCEPTANCE, transactionId) ?
				transactionDetailsVO.getToPartyCode() : transactionDetailsVO.getFromPartyCode();
	}
	
	/**
	 * 	Method		:	NonCarrierIDEnricher.getValueForParameterCode
	 *	Added on 	:	12-Dec-2022
	 * 	Used for 	:	getting partyCode based on transaction.
	 * 					this will be more expanded during code re-factoring
	 *	Parameters	:	@param transactionDetailsVO
	 *	Parameters	:	@param transactionId
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	private String getValueForParameterCode(ULDTransactionDetailsVO transactionDetailsVO, String  transactionId) {
		return Objects.equals(FlightDetailsVO.ACCEPTANCE, transactionId) ?
				transactionDetailsVO.getFromPartyCode() : transactionDetailsVO.getToPartyCode();
	}

	
	private boolean isValidPartyType(String partyType) {
		return Objects.equals(ULDTransactionDetailsVO.AIRLINE, partyType) || 
				Objects.equals(ULDTransactionDetailsVO.AGENT, partyType) ||
				Objects.equals(PARTYTYPE_CUSTOMER, partyType);
	}
}
