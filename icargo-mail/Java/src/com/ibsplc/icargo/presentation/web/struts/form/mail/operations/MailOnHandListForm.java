package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
// TODO: Auto-generated Javadoc

/**
 * The Class MailOnHandListForm.
 */
public class MailOnHandListForm  extends ScreenModel{
	
	/** The Constant SCREEN_ID. */
	private static final String SCREEN_ID = "mailtracking.defaults.mailonhandlist";
	
	/** The Constant PRODUCT_NAME. */
	private static final String PRODUCT_NAME = "mail";
	
	
	/** The Constant SUBPRODUCT_NAME. */
	private static final String SUBPRODUCT_NAME = "operations";
	
	private static final String BUNDLE = "MailOnHandListResources";
	
	
	/** The airport. */
	private String airport;
	
	/** The assignedto. */
	private String assignedto;
	
	/** The from date. */
	private String fromDate;
	
	
	/** The to date. */
	private String toDate;
	
	/** The currentport. */
	private String currentport;
	
	/** The Destination. */
	private String Destination;
	
	/** The Sub class group. */
	private String SubClassGroup;
	
	/** The Noof uld. */
	private String NoofULD;
	/** The Noof md. */
	private String NoofMD;
	
	/** The Noof days. */
	private String NoofDays;
	
	/** The Noof days. */
	private String fromScreen;
	
	public String getFromScreen() {
		return fromScreen;
	}


	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}


	public String[] getSelectmaillist() {
		return selectmaillist;
	}


	public void setSelectmaillist(String[] selectmaillist) {
		this.selectmaillist = selectmaillist;
	}

	private String lastPageNum;
	
	private String[] selectmaillist;
	
	public String getLastPageNum() {
		return lastPageNum;
	}


	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}


	public String getDisplayPage() {
		return displayPage;
	}


	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	private String displayPage;
	
	public String getAirport() {
		return airport;
	}


	public void setAirport(String airport) {
		this.airport = airport;
	}


	public String getAssignedto() {
		return assignedto;
	}


	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}

	@DateFieldId(id="MailOnHandListDateRange",fieldType="from")
	public String getFromDate() {
		return fromDate;
	}


	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	@DateFieldId(id="MailOnHandListDateRange",fieldType="to")
	public String getToDate() {
		return toDate;
	}


	public void setToDate(String toDate) {
		this.toDate = toDate;
	}


	public String getCurrentport() {
		return currentport;
	}


	public void setCurrentport(String currentport) {
		this.currentport = currentport;
	}


	public String getDestination() {
		return Destination;
	}


	public void setDestination(String destination) {
		Destination = destination;
	}


	public String getSubClassGroup() {
		return SubClassGroup;
	}


	public void setSubClassGroup(String subClassGroup) {
		SubClassGroup = subClassGroup;
	}


	public String getNoofULD() {
		return NoofULD;
	}


	public void setNoofULD(String noofULD) {
		NoofULD = noofULD;
	}


	public String getNoofMD() {
		return NoofMD;
	}


	public void setNoofMD(String noofMD) {
		NoofMD = noofMD;
	}


	public String getNoofDays() {
		return NoofDays;
	}


	public void setNoofDays(String noofDays) {
		NoofDays = noofDays;
	}


	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	
	
	
	  /* (non-Javadoc)
  	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
  	 */
  	public String getScreenId() {
	        return SCREEN_ID;
	    }
	  
	  
	  /* (non-Javadoc)
  	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
  	 */
  	public String getProduct() {
	        return PRODUCT_NAME;
	    }

	    /**
    	 * Gets the sub product.
    	 *
    	 * @return SUBPRODUCT_NAME - String
    	 */
	    public String getSubProduct() {
	        return SUBPRODUCT_NAME;
	    }

}
