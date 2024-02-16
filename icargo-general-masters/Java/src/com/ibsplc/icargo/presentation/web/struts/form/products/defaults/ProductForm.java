package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;

import org.apache.struts.action.ActionForm;
import java.util.Collection;

import java.io.Serializable;


/**
 * Form bean for a Struts application.
 *
 * @version 1.0
 * @author
 */
public class ProductForm extends ActionForm implements Serializable

{
	
	
	private String productName= "";
	
	private String status = "";
	
	private String description = "";
	
	private String startDate = "";
	
	private String endDate = "";
	
	private String detailedDescription= "";
	
	private String handlingInfo = "";
	
	private String remarks= "";
	
	private String[]  transportMode;
	
	private String[] priority;
	
	private String[] productScc;
	
	private Collection productServiceVO;
	
	/**
	 * @return Returns the name.
	 */
	public String getProductName() {
		return productName;
	}
	
	/**
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
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
	 * @return Returns the Description.
	 */
	public String getDescription() {
		return description;
	}
	
	
	
	
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return Returns the startdate.
	 */
	public String getStartDate() {
		return startDate;
	}
	
	
	
	
	/**
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * @return Returns the enddate.
	 */
	public String getEndDate() {
		return endDate;
	}
	
	
	/**
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return Returns the detaileddesc.
	 */
	public String getDetailedDescription() {
		return detailedDescription;
	}
	
	/**
	 * @param detailedDescription 
	 */
	public void setDetailedDescription(String detailedDescription) {
		this.detailedDescription = detailedDescription;
	}
	
	/**
	 * @return Returns the handlingInfo.
	 */
	public String getHandlingInfo() {
		return handlingInfo;
	}
	
	/**
	 * @param handlingInfo
	 */
	public void setHandlingInfo(String handlingInfo) {
		this.handlingInfo = handlingInfo;
	}
	
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	
	/**
	 * @param remarks 
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	/**
	 * @return Returns the transport.
	 */
	public String[] getTransportMode() {
		return transportMode;
	}
	
	/**
	 * @param transportMode
	 */
	
	public void setTransportMode(String[] transportMode) {
		this.transportMode = transportMode;
	}
	
	/**
	 * @return Returns the scc.
	 */
	public String[] getProductScc() {
		return productScc;
	}
	
	
	/**
	 * @param productScc 
	 */
	
	public void setProductScc(String[] productScc) {
		this.productScc = productScc;
	}
	
	/**
	 * @return Returns the priority.
	 */
	public String[] getPriority() {
		return priority;
	}
	
	
	/**
	 * @param priority 
	 */
	
	public void setPriority(String[] priority) {
		this.priority = priority;
	}
	
	/**
	 * @return Returns the service.
	 */
	public Collection getProductServiceVO() {
		return productServiceVO;
	}
	
	
	/**
	 * @param productServiceVO
	 */
	
	public void setProductServiceVO(Collection productServiceVO) {
		this.productServiceVO = productServiceVO;
	}
	
}
