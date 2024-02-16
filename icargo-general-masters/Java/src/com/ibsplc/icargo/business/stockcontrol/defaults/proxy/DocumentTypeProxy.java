/*
 * DocumentTypeProxy.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-1366
 *
 */
@Module("shared")
@SubModule("document")
public class DocumentTypeProxy extends ProductProxy {

    /**
     */
    public void findRanges() {
    }
    /**
     */
    public void checkDuplicateRanges() {
    }
    /**
     * Method for validating Ranges
     * @param documentVO
     * @throws ProxyException
     * @throws SystemException
     */
    public void validateRanges(DocumentVO documentVO)
    throws ProxyException, SystemException{
    	despatchRequest("validateRange",documentVO);
    }
    /**
     * Method for merging ranges
     * @param documentVO
     * @return
     * @throws ProxyException
     * @throws SystemException
     */
    public Collection<SharedRangeVO> mergeRanges(DocumentVO documentVO)
    throws ProxyException, SystemException{
    	return despatchRequest("mergeRanges",documentVO);
    }
    /**
     * Method for spliting the ranges
     * @param originalRanges
     * @param availableRanges
     * @return
     * @throws ProxyException
     * @throws SystemException
     */
    public Collection<SharedRangeVO> splitRanges(Collection<SharedRangeVO> originalRanges,Collection<SharedRangeVO> availableRanges) 
    throws ProxyException, SystemException{
    	return despatchRequest("splitRanges",originalRanges,availableRanges);
    }
    
    /**
     * @author a-1863
     * @param documentFilterVO
     * @return Collection<DocumentVO>
     * @throws ProxyException
     * @throws SystemException
     */
    public Collection<DocumentVO> findDocumentDetails(DocumentFilterVO documentFilterVO) 
    throws ProxyException, SystemException{
    	return despatchRequest("findDocumentDetails",documentFilterVO);
    }
        
}
