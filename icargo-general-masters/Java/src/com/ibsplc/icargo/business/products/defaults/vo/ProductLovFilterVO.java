package com.ibsplc.icargo.business.products.defaults.vo;
import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.suggest.MasterTypeMapper;
import com.ibsplc.xibase.util.suggest.vo.SuggestRequestVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestResponseVO;

/**
 * ProductLovFilterVO for specifying the filter criteria for the productLov
 * @author A-1885
 *
 */
public class ProductLovFilterVO extends AbstractVO implements Serializable,MasterTypeMapper{

	//private static final long serialVersionUID = -1374205468883429329L;

	private String companyCode;

	private String productName;

	private LocalDate startDate;

	private LocalDate endDate;

	private boolean isProductNotExpired;

	private LocalDate currentDate;
	
	private boolean isActive;
	
	private LocalDate bkgDate;
	
	 private int pageNumber;
	    
	    private int absoluteIndex;

	private String sccCode;
	
	private String customerCode;
	
	//Added for ICRD-334203 by A-7920
	private int defaultPageSize;


	public int getAbsoluteIndex() {
			return absoluteIndex;
		}

		public void setAbsoluteIndex(int absoluteIndex) {
			this.absoluteIndex = absoluteIndex;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCompanyCode(){
		return companyCode;
	}

	public void setCompanyCode(String companyCode){
		this.companyCode=companyCode;
	}

	public String getProductName(){
		return productName;
	}

	public void setProductName(String productName){
		this.productName=productName;
	}

	public LocalDate getStartDate(){
		return startDate;
	}

	public void setStartDate(LocalDate startDate){
		this.startDate=startDate;
	}

	public LocalDate getEndDate(){
		return endDate;
	}

	public void setEndDate(LocalDate endDate){
		this.endDate=endDate;
	}

	 /**
		 * @return Returns the currentDate.
		 */
		public LocalDate getCurrentDate() {
		    return currentDate;
		}
	   /**
		* @param endDate The currentDate to set.
		*/
		public void setCurrentDate(LocalDate currentDate) {
		    this.currentDate = currentDate;
    }

    public boolean getisProductNotExpired() {
			return isProductNotExpired;
		}
		public void setIsProductNotExpired(boolean isProductNotExpired) {
			this.isProductNotExpired = isProductNotExpired;
	}

		public LocalDate getBkgDate() {
			return bkgDate;
		}

		public void setBkgDate(LocalDate bkgDate) {
			this.bkgDate = bkgDate;
		}
		/**
		 * 	Getter for sccCode 
		 *	Added by : A-8146 on 18-May-2018
		 * 	Used for :
		 */
		public String getSccCode() {
			return sccCode;
		}
		/**
		 *  @param sccCode the sccCode to set
		 * 	Setter for sccCode 
		 *	Added by : A-8146 on 18-May-2018
		 * 	Used for :
		 */
		public void setSccCode(String sccCode) {
			this.sccCode = sccCode;
		}

		/*
		 * 
		 * For autosuggest feature
		 * 
		 */
		@Override
		public SuggestResponseVO getMappedVO() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setMappingVO(SuggestRequestVO requestVO) {
			// TODO Auto-generated method stub
			this.productName=requestVO.getCodeVal();
		}
		
		/**
		 * Gets the default page size.
		 *
		 * @return the default page size
		 */
		public int getDefaultPageSize() {
			return defaultPageSize;
		}

		/**
		 * Sets the default page size.
		 *
		 * @param defaultPageSize the new default page size
		 */
		public void setDefaultPageSize(int defaultPageSize) {
			this.defaultPageSize = defaultPageSize;
		}
		public String getCustomerCode() {
			return customerCode;
		}

		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}		
}