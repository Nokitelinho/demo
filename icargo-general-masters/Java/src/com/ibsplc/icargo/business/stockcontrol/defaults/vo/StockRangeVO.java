/*
 * StockRangeVO.java Created on Sep 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1366
 *
 */
public class StockRangeVO extends AbstractVO {
    
    /**
     * Company Code
     */
    private String companyCode;
    /**
     * Stock Hodler Code
     */
    private String stockHolderCode;
    /**
     * Reference
     */
    private String reference;
    /**
     * Docuemnt Type
     */
    private String documentType;
    /**
     * Document SubType
     */
    private String documentSubType;
    /**
     * Manual Flag
     */
    private boolean isManual;
    /**
     * Collection<RangeVO> available ranges
     */
    private Collection<RangeVO> availableRanges;
    /**
     * Collection<RangeVO> allocated ranges
     */
    private Collection<RangeVO> allocatedRanges;
    /**
     * Default Constructor
     *
     */
    public StockRangeVO(){
        
    }
    
    /**
     * @return Returns the allocatedRanges.
     */
    public Collection<RangeVO> getAllocatedRanges() {
        return allocatedRanges;
    }
    /**
     * @param allocatedRanges The allocatedRanges to set.
     */
    public void setAllocatedRanges(Collection<RangeVO> allocatedRanges) {
        this.allocatedRanges = allocatedRanges;
    }
    /**
     * @return Returns the availableRanges.
     */
    public Collection<RangeVO> getAvailableRanges() {
        return availableRanges;
    }
    /**
     * @param availableRanges The availableRanges to set.
     */
    public void setAvailableRanges(Collection<RangeVO> availableRanges) {
        this.availableRanges = availableRanges;
    }
    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }
    /**
     * @param companyCode The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    /**
     * @return Returns the documentSubType.
     */
    public String getDocumentSubType() {
        return documentSubType;
    }
    /**
     * @param documentSubType The documentSubType to set.
     */
    public void setDocumentSubType(String documentSubType) {
        this.documentSubType = documentSubType;
    }
    /**
     * @return Returns the documentType.
     */
    public String getDocumentType() {
        return documentType;
    }
    /**
     * @param documentType The documentType to set.
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    /**
     * @return Returns the isManual.
     */
    public boolean isManual() {
        return isManual;
    }
    /**
     * @param isManual The isManual to set.
     */
    public void setManual(boolean isManual) {
        this.isManual = isManual;
    }
    /**
     * @return Returns the reference.
     */
    public String getReference() {
        return reference;
    }
    /**
     * @param reference The reference to set.
     */
    public void setReference(String reference) {
        this.reference = reference;
    }
    /**
     * @return Returns the stockHolderCode.
     */
    public String getStockHolderCode() {
        return stockHolderCode;
    }
    /**
     * @param stockHolderCode The stockHolderCode to set.
     */
    public void setStockHolderCode(String stockHolderCode) {
        this.stockHolderCode = stockHolderCode;
    }
}
