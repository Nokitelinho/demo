package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.StockcontrolDefaultsEProxy;
import com.ibsplc.neoicargo.mail.exception.StockcontrolDefaultsProxyException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author a-1883
 */
@Component
@Slf4j
public class StockcontrolDefaultsProxy {
	@Autowired
	private StockcontrolDefaultsEProxy stockcontrolDefaultsEProxy;
	/**
	 * If stock is approvers'
	 */
	private static final String STOCK_FOR_APPROVER = "stockcontrol.defaults.stockforapprover";
	/**
	 * when the stockHolder contains some stock, but doesn't have any stock with the documentType as "AWB"
	 */
	private static final String STOCKHOLDER_AWBSTOCK_NOTFOUND = "stockcontrol.defaults.awbstocknotfoundforstockholder";
	/**
	 * when no stock is found for a specific stockHOlder
	 */
	private static final String STOCKHOLDER_STOCK_NOTFOUND = "stockcontrol.defaults.nostockfoundforstockholder";
	/**
	 */
	private static final String STOCKHOLDER_NOTFOUNDFOR_AGENT = "stockcontrol.defaults.stockholdernotfoundforagent";
	/**
	 */
	private static final String INVALID_STOCK_HOLDER = "stockcontrol.defaults.invalidstockholder";
	/**
	 * thrown when a specific AWB number is not found in any of the stock of  any of the stockholder
	 */
	private static final String AWBNUMBER_NOTFOUND_INANYSTOCK = "stockcontrol.defaults.awbnumbernotfoundinanystock";
	/**
	 * thrown when agent is not mapped to stockHolder
	 */
	private static final String AGENT_MAPPING_NOTFOUND = "stockcontrol.defaults.agentmappingnotfound";
	/**
	 * if the document number is found in some range of some stockHolder but that stock holder is not the same as the referred one i.e, if the document number belongs to some other stockHolder
	 */
	private static final String DOCUMENT_NOTOF_STOCKHOLDER = "stockcontrol.defaults.documentnotbelongingtostockholder";

	/**
	 * @author a-1883
	 * @param documentFilterVO
	 * @return DocumentValidationVO
	 * @throws SystemException
	 */
	public DocumentValidationVO validateDocumentInStock(DocumentFilterVO documentFilterVO) throws StockcontrolDefaultsProxyException {
		DocumentValidationVO documentValidationVO = null;
		try {
			log.debug("StockcontrolDefaultsProxy" + " : " + "validateDocumentInStock" + " Entering");
			documentValidationVO = stockcontrolDefaultsEProxy.validateDocument(documentFilterVO);
			return documentValidationVO;

		}catch(ServiceException proxyException) {
			Collection<ErrorVO> errors = proxyException.getErrors();
			for (ErrorVO errorVO : errors) {
				if (STOCK_FOR_APPROVER.equals(errorVO.getErrorCode())) {
					break;
				} else {
					documentValidationVO = null;
				}
			}
		}
		return documentValidationVO;
	}

	/**
	 * This method finds the next document no
	 * @author a-1883
	 * @param documentFilterVO
	 * @return DocumentValidationVO
	 * @throws SystemException
	 */
	public DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documentFilterVO)
			throws StockcontrolDefaultsProxyException {
		log.debug("StockcontrolDefaultsProxy" + " : " + "findNextDocumentNumber" + " Entering");
		DocumentValidationVO documentValidationVO = null;
		try {
			documentValidationVO = stockcontrolDefaultsEProxy.findNextDocumentNumber(documentFilterVO);
			return documentValidationVO;
		} catch (ServiceException proxyException) {
			Collection<ErrorVO> errors = proxyException.getErrors();
			if (errors != null && errors.size() > 0) {
				for (ErrorVO errorVO : errors) {
					if (StringUtils.contains(errorVO.getErrorCode(), "NEO_STOCK_026")) {
						throw new StockcontrolDefaultsProxyException(StockcontrolDefaultsProxyException.NO_AWBSTOCK);
					} else if (STOCKHOLDER_STOCK_NOTFOUND.equals(errorVO.getErrorCode())) {
						throw new StockcontrolDefaultsProxyException(
								StockcontrolDefaultsProxyException.STOCKHOLDER_NO_STOCK);
					} else if (STOCKHOLDER_NOTFOUNDFOR_AGENT.equals(errorVO.getErrorCode())) {
						throw new StockcontrolDefaultsProxyException(
								StockcontrolDefaultsProxyException.NO_STHFOR_AGENT);
					} else if (INVALID_STOCK_HOLDER.equals(errorVO.getErrorCode())) {
						throw new StockcontrolDefaultsProxyException(
								StockcontrolDefaultsProxyException.INVALID_STOCKHOLDER);
					} else {
						log.info(" findNextDocumentNumber For Bug 30318 , temporarily , suppressed the excetions");
						documentValidationVO = null;
					}
				}
			}
			return documentValidationVO;
		}
	}

	/**
	 * This method fetches the document subtype from stock
	 * @author U-1519
	 * @param documentFilterVO
	 * @throws SystemException
	 */
	public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO) throws BusinessException {
		return stockcontrolDefaultsEProxy.findAutoPopulateSubtype(documentFilterVO);
	}

	/**
	 * @param stockAgentFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Page<StockAgentVO> findStockAgentMappings(StockAgentFilterVO stockAgentFilterVO) throws BusinessException {
		return stockcontrolDefaultsEProxy.findStockAgentMappings(stockAgentFilterVO);
	}
	/**
	 * This method deletes document from stock
	 *
	 * @author a-1883
	 * @param documentFilterVO
	 * @throws com.ibsplc.xibase.server.framework.exceptions.SystemException
	 */
	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
			throws SystemException, StockcontrolDefaultsProxyException {
		log.info("StockcontrolDefaultsProxy-deleteDocumentFromStock");
		try {
			stockcontrolDefaultsEProxy.deleteDocumentFromStock(documentFilterVO);
		} catch (SystemException systemException) {
			Collection<ErrorVO> errors = systemException.getErrors();
			for(ErrorVO errorVO : errors) {
				if(AWBNUMBER_NOTFOUND_INANYSTOCK.equals(
						errorVO.getErrorCode())) {
					throw new StockcontrolDefaultsProxyException(
							StockcontrolDefaultsProxyException.NOAWB_INSTOCK,
							errorVO.getErrorData());
				} else if(AGENT_MAPPING_NOTFOUND.equals(
						errorVO.getErrorCode())) {
					throw new StockcontrolDefaultsProxyException(
							StockcontrolDefaultsProxyException.NO_STHFOR_AGENT,
							errorVO.getErrorData());
				}else if(DOCUMENT_NOTOF_STOCKHOLDER.equals(
						errorVO.getErrorCode())) {
					throw new StockcontrolDefaultsProxyException(
							StockcontrolDefaultsProxyException.DOC_NOTOF_STH,
							errorVO.getErrorData());
				}else{
					//For Bug 30318 , temporarily , suppressed the excetions, if any other
					log.info("deleteDocumentFromStock For Bug 30318 , temporarily , suppressed the excetions");
				}
			}
			//throw new SystemException(proxyException.getMessage());
		}
	}
}
