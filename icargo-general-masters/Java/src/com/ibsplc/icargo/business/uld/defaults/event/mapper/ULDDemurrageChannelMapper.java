/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.event.mapper.ULDDemurrageChannelMapper.java
 *
 *	Created on	:	24-Apr-2023
 *
 *  Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.event.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.feature.Proxy;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.event.mapper.ULDDemurrageChannelMapper.java
 *	This class is used for mapping VO for saving demurrage details
 */
@BeanConverterRegistry
public class ULDDemurrageChannelMapper {
	
	@Autowired
	Proxy proxy;
	
	/**
	 * 
	 * 	Method		:	ULDDemurrageChannelMapper.mapToTransactionVO
	 *	Added on 	:	20-Apr-2023
	 * 	Used for 	:	Mapping a new TransactionVO
	 *	Parameters	:	@param transactionVO
	 *	Parameters	:	@return 
	 *	Return type	: 	TransactionVO
	 */
	@BeanConversion(from=TransactionVO.class, to=TransactionVO.class, toType=ElementType.SINGLE, 
			group = "SAVE_ULD_TRANSACTION_EVENT")
	public TransactionVO mapToTransactionVO(TransactionVO transactionVO){
		TransactionVO transactionVOForUpdate = new TransactionVO();
		Collection<ULDTransactionDetailsVO> detailVOsForUpdate = new ArrayList<>();
		transactionVO.getUldTransactionDetailsVOs().stream()
		.forEach(detailVO->{
			ULDTransactionDetailsVO detailVOForUpdate = populateAttributes(detailVO);
			detailVOsForUpdate.add(detailVOForUpdate);
		});
		transactionVOForUpdate.setUldTransactionDetailsVOs(detailVOsForUpdate);
		return transactionVOForUpdate;
		
	}
	
	/**
	 * 
	 * 	Method		:	ULDDemurrageChannelMapper.populateAttributes
	 *	Added on 	:	20-Apr-2023
	 * 	Used for 	:	Mapping VO
	 *	Parameters	:	@param detailVO
	 *	Parameters	:	@return 
	 *	Return type	: 	ULDTransactionDetailsVO
	 */
	private ULDTransactionDetailsVO populateAttributes(ULDTransactionDetailsVO detailVO) {
		ULDTransactionDetailsVO detailVOForUpdate = new ULDTransactionDetailsVO(); 
		detailVOForUpdate.setUldNumber(detailVO.getUldNumber());
		detailVOForUpdate.setCompanyCode(detailVO.getCompanyCode());
		detailVOForUpdate.setTransactionRefNumber(detailVO.getTransactionRefNumber());
		detailVOForUpdate.setTransactionType(detailVO.getTransactionType());
		detailVOForUpdate.setToPartyCode(detailVO.getToPartyCode());
		detailVOForUpdate.setFromPartyCode(detailVO.getFromPartyCode());
		detailVOForUpdate.setPartyType(detailVO.getPartyType());
		detailVOForUpdate.setTransactionDate(detailVO.getTransactionDate());
		detailVOForUpdate.setUldType(detailVO.getUldType());
		detailVOForUpdate.setTransactionStationCode(detailVO.getTransactionStationCode());
		return detailVOForUpdate;

	}	
	
}
