/*
 * ProductFilterVO.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestRequestVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestResponseVO;

/**
 * @author A-1358
 *
 */
 // Amritha added
public class ProductFilterVO extends AbstractVO implements Serializable{

	private static final long serialVersionUID = -7591299721453765813L;

	private String companyCode;

    private String productCode;

    private String productName;
    
    private List<String> productNames;

    /**
     * Fetched from Onetime. Possible values are
     * N - New, I - Inactive, A- Active
     */
    private String status;

    /**
     * Indicates whether rate is defined for the product
     * in Tariff
     */
    private boolean isRateDefined;

   	private String priority;

    private String scc;

   	private String transportMode;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Collection transportModes;

    private Collection sccs;

    private Collection priorities;
    
    private int displayPage;
    
    public static final String FLAG_ALL="ALL";
    
    private int totalRecords;//Added by A-5201 as part for the ICRD-22065

    private int totalRecordsCount;
    private int pageNumber;
    //Added by A-5867 for  ICRD-111938
    private String origin;
    private String destination;
    private String commodityCode; 
    private double weight;
    private String productCategory;//Added for ICRD-166985 by A-5117
    
    /**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}

	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	/**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return Returns the scc.
     */
    public String getScc() {
        return scc;
    }

    /**
     * @param scc The scc to set.
     */
    public void setScc(String scc) {
        this.scc = scc;
    }

   /**
     * @return Returns the transportMode.
     */
    public String getTransportMode() {
        return transportMode;
    }

    /**
     * @param transportMode The transportMode to set.
     */
    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

   /**
     * @return Returns the companyCode.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param companyCode The companyCode to set.
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

   /**
     * @return Returns the productCode.
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * @param productCode The productCode to set.
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
   /**
     * @return Returns the isRateDefined.
     */
    public boolean getIsRateDefined() {
        return isRateDefined;
    }

    /**
     * @param isRateDefined The isRateDefined to set.
     */
    public void setIsRateDefined(boolean isRateDefined) {
        this.isRateDefined = isRateDefined;
    }

    /**
     * @return Returns the endDate.
     */

    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * @param endDate The endDate to set.
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    /**
     * @return Returns the productName.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName The productName to set.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return Returns the endDate.
     */

    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * @param FromDate The fromDate to set.
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return Returns the priorities.
     */
    public Collection getPriorities() {
        return priorities;
    }
    /**
     * @param priorities The priorities to set.
     */
    public void setPriorities(Collection priorities) {
        this.priorities = priorities;
    }
    /**
     * @return Returns the sccs.
     */
    public Collection getSccs() {
        return sccs;
    }
    /**
     * @param sccs The sccs to set.
     */
    public void setSccs(Collection sccs) {
        this.sccs = sccs;
    }
    /**
     * @return Returns the transportModes.
     */
    public Collection getTransportModes() {
        return transportModes;
    }
    /**
     * @param transportModes The transportModes to set.
     */
    public void setTransportModes(Collection transportModes) {
        this.transportModes = transportModes;
    }

	/**
	 * @return Returns the displayPage.
	 */
	public int getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}
	
	//Added by A-5201 as part from the ICRD-22065 starts
	/**
	 * @param setTotalRecords to set total number of records
	*/
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
 
    /**
	 * @return the total number of records
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	//Added by A-5201 as part from the ICRD-22065 end
/**
 * 
 */
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
/**
 * 
 * @param totalRecordsCount
 */
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
/**
 * 
 * @return
 */
public int getPageNumber() {
	return pageNumber;
}
/**
 * 
 * @param pageNumber
 */
public void setPageNumber(int pageNumber) {
	this.pageNumber = pageNumber;
}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public List<String> getProductNames() {
		return productNames;
	}

	public void setProductNames(List<String> productNames) {
		this.productNames = productNames;
	}

	
	
	public SuggestResponseVO getMappedVO() {
		return null;
	}
	public void setMappingVO(SuggestRequestVO requestVO) {
		this.productName=requestVO.getCodeVal();
	}
}
