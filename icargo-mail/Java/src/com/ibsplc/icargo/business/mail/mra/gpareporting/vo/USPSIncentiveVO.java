package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class USPSIncentiveVO extends AbstractVO {
	
	private String companyCode;
	private String excAmot;


public String getCompanyCode() {
	return companyCode;
}


public String getExcAmot() {
	return excAmot;
}


public void setExcAmot(String excAmot) {
	this.excAmot = excAmot;
}


public void setCompanyCode(String companyCode) {
	this.companyCode = companyCode;
}

private String accountingRequired;


public String getAccountingRequired() {
	return accountingRequired;
}


public void setAccountingRequired(String accountingRequired) {
	this.accountingRequired = accountingRequired;
}



}
