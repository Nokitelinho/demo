/**
 * 
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1747
 *
 */
public class CreateStockRequestForm extends ScreenModel {
	
	private String airline ="";
	private String documentType;
	private String docSubType;
	private String modeOfCommunication="";
	private String address="";
	private String content="";
	private String afterSave="N";
	private static final String BUNDLE = "createrequestresources"; 

	private String bundle;
	
	
	  /**
	 * @return Returns the afterSave.
	 */
	public String getAfterSave() {
		return this.afterSave;
	}

	/**
	 * @param afterSave The afterSave to set.
	 */
	public void setAfterSave(String afterSave) {
		this.afterSave = afterSave;
	}

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
	 * @return Returns the address.
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return this.airline;
	}

	/**
	 * @param airline The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the docSubType.
	 */
	public String getDocSubType() {
		return this.docSubType;
	}

	/**
	 * @param docSubType The docSubType to set.
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}

	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return this.documentType;
	}

	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return Returns the modeOfCommunication.
	 */
	public String getModeOfCommunication() {
		return this.modeOfCommunication;
	}

	/**
	 * @param modeOfCommunication The modeOfCommunication to set.
	 */
	public void setModeOfCommunication(String modeOfCommunication) {
		this.modeOfCommunication = modeOfCommunication;
	}

	public String getScreenId() {
	        return "stockcontrol.defaults.cto.createstockrequest";
	    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
     */
    public String getProduct() {
        return "stockcontrol";
    }
    public String getSubProduct() {
        return "defaults";
    }
}
