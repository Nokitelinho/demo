
package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerFilter;


public class MailContainerListModel  extends AbstractScreenModel{



	/*
	 * The constant variable for product operations
	 */
	private static final String PRODUCT = "mail";
	/*
	 * The constant for sub product flthandling
	 */
	private static final String SUBPRODUCT = "operations";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "mail.operations.ux.mailcontainerlist";	
	
	private Map<String, Collection<OneTimeVO>> oneTimeValues;

    private ContainerFilter containerFilter; 
	
	private PageResult<ContainerDetails> containerDetails;	
	
	private Collection<ContainerDetails> selectedContainerDetails;	

	private String carrierCode;
	
  	private String fromDate;
  	private String toDate;
  	private String mode;
	
	
	
	
	
  	 public Collection<ContainerDetails> getSelectedContainerDetails() {
 		return selectedContainerDetails;
 	}

 	public void setSelectedContainerDetails(Collection<ContainerDetails> selectedContainerDetails) {
 		this.selectedContainerDetails = selectedContainerDetails;
 	}
	
	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}



	public String getScreenId() {
		return SCREENID;
	}

	public String getProduct() {
		return PRODUCT;
	}

	public String getSubProduct() {
		return SUBPRODUCT;
	}


	public Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public PageResult<ContainerDetails> getContainerDetails() {
		return containerDetails;
	}

	public void setContainerDetails(PageResult<ContainerDetails> containerDetails) {
		this.containerDetails = containerDetails;
	}

	public ContainerFilter getContainerFilter() {
		return containerFilter;
	}

	public void setContainerFilter(ContainerFilter containerFilter) {
		this.containerFilter = containerFilter;
	}

	
	
}
