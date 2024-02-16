package com.ibsplc.icargo.business.uld.defaults.feature.returnuld.enrichers;

import java.util.Objects;

import com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransaction;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.bean.BeanConversionConfigVO;
import com.ibsplc.icargo.framework.bean.BeanConversionHelper;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.returnuld.enrichers.DemurrageEnricher.java
 *	This class is used for Enrich SaveReturnTransactionFeature
 */
@FeatureComponent("SaveReturnTransactionFeature.DemurrageEnricher") 
public class DemurrageEnricher extends Enricher<TransactionListVO>{
	
	private static final String MODULE = "uld.defaults";
	private static final Log LOGGER = LogFactory.getLogger(MODULE);

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.Enricher#enrich(java.lang.Object)
	 *	Added on 			: 29-Nov-2022
	 * 	Used for 	:	enrich
	 *	Parameters	:	@param transactionListVO
	 *	Parameters	:	@throws SystemException
	 */
 @Override
  public void enrich(TransactionListVO transactionListVO) throws SystemException {
		
	 
			if (Objects.nonNull(transactionListVO.getUldTransactionsDetails())) {
				transactionListVO.getUldTransactionsDetails().stream()
				.filter(uldTransactionDtlVO->uldTransactionDtlVO.getTotal() == 0)
				.forEach(uldTransactionDetailVO->{
					TransactionFilterVO uldTransactionFilterVO = new TransactionFilterVO();
					uldTransactionFilterVO = populateUldTransactionFilterVO(uldTransactionDetailVO);
					findULDTransactionDetailsForDemurrage(uldTransactionFilterVO,uldTransactionDetailVO);
			});
		}
	}
 /**
  * 
  * 	Method		:	DemurrageEnricher.findULDTransactionDetailsForDemurrage
  *	Added on 	:	30-Nov-2022
  * Used for 	:   findULDTransactionDetailsForDemurrage
  *	Parameters	:	@param uldTransactionFilterVO
  *	Parameters	:	@param uldTransactionDetailVO 
  *	Return type	: 	void
  */
 	private void findULDTransactionDetailsForDemurrage(TransactionFilterVO uldTransactionFilterVO, ULDTransactionDetailsVO uldTransactionDetailVO) {
 		
 		ULDTransactionDetailsVO transactionVO = new ULDTransactionDetailsVO();
		try {
			transactionVO = ULDTransaction.listULDTransactionDetailsForDemurrage(uldTransactionFilterVO);
		} catch (SystemException e) {
			LOGGER.log(Log.FINE, e);
		}    
		 if (Objects.nonNull(transactionVO)){
			    uldTransactionDetailVO.setTotal((transactionVO.getDemurrageAmount()+ transactionVO.getTaxes() + transactionVO.getOtherCharges())- transactionVO.getWaived());
		        uldTransactionDetailVO.setCurrency(transactionVO.getCurrency());
		  }
	}
	/**
 	 * 
 	 * 	Method		:	DemurrageEnricher.populateUldTransactionFilterVO
 	 *	Added on 	:	30-Nov-2022
 	 * 	Used for 	:	Populating TransactionFilterVO 
 	 *	Parameters	:	@param uldTransactionDetailVO
 	 *	Parameters	:	@param uldTransactionFilterVO 
 	 *	Return type	: 	void
	 * @param uldTransactionFilterVO 
 	 */
	private TransactionFilterVO populateUldTransactionFilterVO(ULDTransactionDetailsVO uldTransactionDetailVO) {
		
		BeanConversionConfigVO configVO = new BeanConversionConfigVO();
		configVO.setFrom(ULDTransactionDetailsVO.class);
        return BeanConversionHelper.getInstance().convert(uldTransactionDetailVO,configVO, TransactionFilterVO.class);
	}

}
