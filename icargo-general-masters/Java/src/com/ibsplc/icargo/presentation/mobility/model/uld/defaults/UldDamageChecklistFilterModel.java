package com.ibsplc.icargo.presentation.mobility.model.uld.defaults;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UldDamageChecklistFilterModel implements Serializable {
	private String companyCode;
	private String section;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
}
