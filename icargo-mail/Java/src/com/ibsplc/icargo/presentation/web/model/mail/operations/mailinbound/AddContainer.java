package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AddContainer.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Oct-2018		:	Draft
 */
public class AddContainer {

	private boolean barrow;
	
	private String containerNo;
	
	private String pol;
	
	private String desination;
	
	private boolean paBuilt;
	
	private boolean rcvd;
	
	private boolean dlvd;
	
	private boolean intact;
	
	private String remarks;

	public boolean isBarrow() {
		return barrow;
	}

	public void setBarrow(boolean barrow) {
		this.barrow = barrow;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getDesination() {
		return desination;
	}

	public void setDesination(String desination) {
		this.desination = desination;
	}

	public boolean isPaBuilt() {
		return paBuilt;
	}

	public void setPaBuilt(boolean paBuilt) {
		this.paBuilt = paBuilt;
	}

	public boolean isRcvd() {
		return rcvd;
	}

	public void setRcvd(boolean rcvd) {
		this.rcvd = rcvd;
	}

	public boolean isDlvd() {
		return dlvd;
	}

	public void setDlvd(boolean dlvd) {
		this.dlvd = dlvd;
	}

	public boolean isIntact() {
		return intact;
	}

	public void setIntact(boolean intact) {
		this.intact = intact;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
