package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

public class ProductPerformanceForm extends ScreenModel {
	private String isView;
	private String productCode;
	private String productName;
	private String scc;
	private String transMode;
	private String fromDate="";
	private String toDate="";
	private String priority;
	private String origin;
	private String destination;
	
	
	private static final String BUNDLE = "productperformanceresources"; 

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
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return Returns the priority.
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority The priority to set.
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
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return Returns the transMode.
	 */
	public String getTransMode() {
		return transMode;
	}
	/**
	 * @param transMode The transMode to set.
	 */
	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}
	public String getScreenId() {
		// To be reviewed Auto-generated method stub
		return "products.defaults.productperformance";
	}
	public String getProduct() {
		// To be reviewed Auto-generated method stub
		return "products";
	}
	public String getSubProduct() {
		// To be reviewed Auto-generated method stub
		return "defaults";
	}
	/**
	 * @return Returns the isView.
	 */
	public String getIsView() {
		return isView;
	}
	/**
	 * @param isView The isView to set.
	 */
	public void setIsView(String isView) {
		this.isView = isView;
	}
	

}
