package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * 
 * @author a-1496
 *
 */
public class ServiceMasterForm extends ScreenModel {
	
	private static final String BUNDLE = "servicemaster";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.servicemaster";
	
	
	private String bundle;
	private String service;
	private String points;
	private String redeemToKeyContact;
	private String description;
	private String listFlag;


	/**
	 * 
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * 
	 * @param bundle
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}


/**
 * 
 */
    public String getScreenId() {
        return SCREENID;
    }

/**
 * 
 */
    public String getProduct() {
        return PRODUCT;
    }

 /**
  * 
  */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
	/**
	 * @return Returns the description.
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
	 * @return Returns the listFlag.
	 */
	public String getListFlag() {
		return listFlag;
	}
	/**
	 * @param listFlag The listFlag to set.
	 */
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}
	/**
	 * @return Returns the points.
	 */
	public String getPoints() {
		return points;
	}
	/**
	 * @param points The points to set.
	 */
	public void setPoints(String points) {
		this.points = points;
	}
	/**
	 * @return Returns the redeemToKeyContact.
	 */
	public String getRedeemToKeyContact() {
		return redeemToKeyContact;
	}
	/**
	 * @param redeemToKeyContact The redeemToKeyContact to set.
	 */
	public void setRedeemToKeyContact(String redeemToKeyContact) {
		this.redeemToKeyContact = redeemToKeyContact;
	}
	/**
	 * @return Returns the service.
	 */
	public String getService() {
		return service;
	}
	/**
	 * @param service The service to set.
	 */
	public void setService(String service) {
		this.service = service;
	}
   
}
