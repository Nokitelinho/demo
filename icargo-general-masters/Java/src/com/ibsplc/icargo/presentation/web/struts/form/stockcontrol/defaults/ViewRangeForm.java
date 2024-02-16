package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-1952
 *
 */
public class ViewRangeForm extends ScreenModel
{

	 private String stockHolder;
	 private String docType;
	 private String subType;
	 private String reference;
	 private boolean isManual;
	 private String totalAvailableStock;
	 private String totalAllocatedStock;
	 private String lastPageNumber; 
	 private String displayPage;
	 private String startRange;
	 private String endRange;
	 private String confirmedCheck;


	public String getConfirmedCheck() {
		return confirmedCheck;
	}
	public void setConfirmedCheck(String confirmedCheck) {
		this.confirmedCheck = confirmedCheck;
	}
	public String getStartRange() {
		return startRange;
	}
	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}
	public String getEndRange() {
		return endRange;
	}
	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}
	public String getLastPageNumber() {
		return lastPageNumber;
	}
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	public String getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "viewrangeresources";

	private String bundle;


	/**
	* @return Returns the bundle.
	*/
	public String getBundle() {
		return BUNDLE;
	}
	/**
	* @param bundle The bundle to set.
	*/
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

/**
 *
 * @return stockHolder
 */
    public String getStockHolder()
    {
        return stockHolder;
    }
/**
 *
 * @return docType
 */
    public String getDocType()
    {
        return docType;
    }
/**
 *
 * @return subType
 */
    public String getSubType()
    {
        return subType;
    }
/**
 *
 * @return reference
 */
    public String getReference()
    {
        return reference;
    }
/**
 *
 * @return manual
 */
    public boolean isManual()
    {
        return isManual;
    }
/**
 *
 * @return totalAvailableStock
 */
    public String getTotalAvailableStock()
    {
        return totalAvailableStock;
    }
/**
 *
 * @return totalAllocatedStock
 */
    public String getTotalAllocatedStock()
    {
        return totalAllocatedStock;
    }
/**
 *
 * @param stockHolder
 */
    public void setStockHolder(String stockHolder)
    {
        this.stockHolder = stockHolder;
    }
/**
 *
 * @param docType
 */
    public void setDocType(String docType)
    {
        this.docType = docType;
    }
/**
 *
 * @param subType
 */
    public void setSubType(String subType)
    {
        this.subType = subType;
    }
/**
 *
 * @param reference
 */
    public void setReference(String reference)
    {
        this.reference = reference;
    }
/**
 *
 * @param isManual
 */
    public void setManual(boolean isManual)
    {
        this.isManual = isManual;
    }
/**
 *
 * @param totalAvailableStock
 */
    public void setTotalAvailableStock(String totalAvailableStock)
    {
        this.totalAvailableStock = totalAvailableStock;
    }
/**
 *
 * @param totalAllocatedStock
 */
    public void setTotalAllocatedStock(String totalAllocatedStock)
    {
        this.totalAllocatedStock = totalAllocatedStock;
    }
/**
 * @return ScreenId
 */
    public String getScreenId()
    {
        return "stockcontrol.defaults.viewrange";
    }
/**
 * @return Product
 */
    public String getProduct()
    {
        return "stockcontrol";
    }
/**
 * @return SubProduct
 */
    public String getSubProduct()
    {
        return "defaults";
    }


}