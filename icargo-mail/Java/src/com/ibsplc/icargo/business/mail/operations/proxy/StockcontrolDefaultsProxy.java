/*
 * StockcontrolDefaultsProxy.java Created on May 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import com.ibsplc.icargo.business.mail.operations.StockcontrolDefaultsProxyException;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1883
 * 
 */
@Module("stockcontrol")
@SubModule("defaults")
public class StockcontrolDefaultsProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("STOCKCONTROL_DEFAULTS_PROXY");
	private static final Log LOGGER = LogFactory.getLogger("STOCKCONTROL_DEFAULTS_PROXY");
	/**
	 * If stock is approvers'
	 */
	private static final String STOCK_FOR_APPROVER = "stockcontrol.defaults.stockforapprover";
    
    	/**
	 * when the stockHolder contains some stock, but doesn't have any
	 * stock with the documentType as "AWB" 
	 */
	private static final String STOCKHOLDER_AWBSTOCK_NOTFOUND = 
        "stockcontrol.defaults.awbstocknotfoundforstockholder";
        
    /**
	 * when no stock is found for a specific stockHOlder
	 */
	private static final String STOCKHOLDER_STOCK_NOTFOUND = 
        "stockcontrol.defaults.nostockfoundforstockholder";
    
    /**
	 * 
	 */
	private static final String STOCKHOLDER_NOTFOUNDFOR_AGENT = 
        "stockcontrol.defaults.stockholdernotfoundforagent";
    
    /**
	 * 
	 */
	private static final String INVALID_STOCK_HOLDER = 
        "stockcontrol.defaults.invalidstockholder";
    
    /**
	 * thrown when a specific AWB number is not found in any of the stock of 
	 * any of the stockholder
	 */
	private static final String AWBNUMBER_NOTFOUND_INANYSTOCK = 
        "stockcontrol.defaults.awbnumbernotfoundinanystock";

    /**
	 * thrown when agent is not mapped to stockHolder
	 */
	private static final String AGENT_MAPPING_NOTFOUND =
        "stockcontrol.defaults.agentmappingnotfound";

    /**
	 * if the document number is found in some range of some stockHolder
	 * but that stock holder is not the same as the referred one
	 * i.e, if the document number belongs to some other stockHolder
	 */
	private static final String DOCUMENT_NOTOF_STOCKHOLDER = 
        "stockcontrol.defaults.documentnotbelongingtostockholder";
	/**
	 * @author a-1883
	 * @param documentFilterVO
	 * @return DocumentValidationVO
	 * @throws SystemException
	 */
	public DocumentValidationVO validateDocumentInStock(
			DocumentFilterVO documentFilterVO) throws SystemException {
		log.entering("StockcontrolDefaultsProxy", "validateDocumentInStock");
		DocumentValidationVO documentValidationVO = null;
		try {
			documentValidationVO = despatchRequest("validateDocument",
					documentFilterVO);
		} catch (ProxyException proxyException) {
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
	 * 
	 * @author a-1883
	 * @param documentFilterVO
	 * @return DocumentValidationVO
	 * @throws SystemException
	 */
	public DocumentValidationVO findNextDocumentNumber(
			DocumentFilterVO documentFilterVO) throws SystemException,
      StockcontrolDefaultsProxyException{
		log.entering("StockcontrolDefaultsProxy", "findNextDocumentNumber");
		DocumentValidationVO documentValidationVO = null;
		try {
			return despatchRequest("findNextDocumentNumber", documentFilterVO);
		} catch (ProxyException proxyException) {
          Collection<ErrorVO> errors = proxyException.getErrors();
          if(errors != null && errors.size() > 0){
	          for(ErrorVO errorVO : errors) {
	            if(STOCKHOLDER_AWBSTOCK_NOTFOUND.equals(
	                errorVO.getErrorCode())) {
	              throw new StockcontrolDefaultsProxyException(
	                  StockcontrolDefaultsProxyException.NO_AWBSTOCK);
	            } else if(STOCKHOLDER_STOCK_NOTFOUND.equals(
	                errorVO.getErrorCode())) {
	              throw new StockcontrolDefaultsProxyException(
	                  StockcontrolDefaultsProxyException.STOCKHOLDER_NO_STOCK);
	            } else if(STOCKHOLDER_NOTFOUNDFOR_AGENT.equals(
	                errorVO.getErrorCode())) {
	              throw new StockcontrolDefaultsProxyException(
	                  StockcontrolDefaultsProxyException.NO_STHFOR_AGENT);
	            } else if(INVALID_STOCK_HOLDER.equals(
	                errorVO.getErrorCode())) {
	              throw new StockcontrolDefaultsProxyException(
	                  StockcontrolDefaultsProxyException.INVALID_STOCKHOLDER);
	            } else{
	            	//For Bug 30318 , temporarily , suppressed the excetions, if any other
	            	log.log(Log.INFO," findNextDocumentNumber For Bug 30318 , temporarily , suppressed the excetions");
	            	documentValidationVO = null;
	            }
	          }
          }
			//throw new SystemException(proxyException.getMessage());
          return documentValidationVO;
		}catch(SystemException systemException) {
			Collection<ErrorVO> errors = systemException.getErrors();
	          if(errors != null && !errors.isEmpty()){
	        	 ErrorVO errorVO = errors.iterator().next();
					LOGGER.log(LOGGER.FINE, systemException);
			throw new SystemException(errorVO.getErrorCode());
	          
	          }
			return	documentValidationVO;

		}
	}

	/**
	 * This method deletes document from stock
	 * 
	 * @author a-1883
	 * @param documentFilterVO
	 * @throws SystemException
	 */
	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
			throws SystemException, StockcontrolDefaultsProxyException {
		log.entering("StockcontrolDefaultsProxy", "deleteDocumentFromStock");
		try {
			despatchRequest("deleteDocumentFromStock", documentFilterVO);
		} catch (ProxyException proxyException) {
          Collection<ErrorVO> errors = proxyException.getErrors();
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
            	log.log(Log.INFO," deleteDocumentFromStock For Bug 30318 , temporarily , suppressed the excetions");
            }
          }
			//throw new SystemException(proxyException.getMessage());
		}
	}
	
	/**
	 * This method fetches the document subtype from stock
	 * 
	 * @author U-1519
	 * @param documentFilterVO
	 * @throws SystemException
	 */
	public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO)
		    throws SystemException, ProxyException
		  {
		    return (String)despatchRequest("findAutoPopulateSubtype", documentFilterVO);
		  }
	/**
	 * 
	 * @param stockAgentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Page<StockAgentVO> findStockAgentMappings(StockAgentFilterVO stockAgentFilterVO)
			throws SystemException, ProxyException{
				return despatchRequest("findStockAgentMappings", stockAgentFilterVO);
		  }
}
