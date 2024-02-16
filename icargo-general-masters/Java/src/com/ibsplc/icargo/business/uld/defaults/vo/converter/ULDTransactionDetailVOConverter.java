package com.ibsplc.icargo.business.uld.defaults.vo.converter;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.vo.converter.ULDTransactionDetailVOConverter.java
 *	This class is used for convert TransactionFilterVO
 */
@BeanConverterRegistry
public class ULDTransactionDetailVOConverter {

	/**
	 * 
	 * 	Method		:	ULDTransactionDetailVOConverter.toTransactionFilterVO
	 *	Added on 	:	01-Dec-2022
	 * 	Used for 	:	To convert TransactionFilterVO from ULDTransactionDetailsVO
	 *	Parameters	:	@param uldTransactionDetailVO
	 *	Parameters	:	@return 
	 *	Return type	: 	TransactionFilterVO
	 */
	@BeanConversion(from = ULDTransactionDetailsVO.class, to = TransactionFilterVO.class)
	public static TransactionFilterVO toConvertTransactionFilterVO(ULDTransactionDetailsVO uldTransactionDetailVO) {
		
		TransactionFilterVO uldTransactionFilterVO = new TransactionFilterVO();
		
		uldTransactionFilterVO.setFromPartyCode(uldTransactionDetailVO.getReturnedBy());
	    uldTransactionFilterVO.setTransactionStatus(uldTransactionDetailVO.getTransactionNature());
		uldTransactionFilterVO.setControlReceiptNo(uldTransactionDetailVO.getControlReceiptNumber());
        uldTransactionFilterVO.setTransactionStationCode(uldTransactionDetailVO.getTransactionStationCode());
        uldTransactionFilterVO.setToPartyCode(uldTransactionDetailVO.getReturnPartyCode());
        uldTransactionFilterVO.setPartyType(uldTransactionDetailVO.getPartyType());
        uldTransactionFilterVO.setUldTypeCode(uldTransactionDetailVO.getUldType());
		uldTransactionFilterVO.setTransactionType(uldTransactionDetailVO.getTransactionType());
        uldTransactionFilterVO.setUldNumber(uldTransactionDetailVO.getUldNumber());
        uldTransactionFilterVO.setCompanyCode(uldTransactionDetailVO.getCompanyCode());
		return uldTransactionFilterVO;
	}
}
