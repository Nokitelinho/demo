package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.ux.brokermapping;

import com.ibsplc.icargo.framework.model.ScreenModel;

public class BrokerMappingForm extends ScreenModel{
	private static final String BUNDLE = "brokermapping";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
    private static final String SCREENID = "customermanagement.defaults.ux.brokermapping";
    
    public String getBundle() {
		return BUNDLE;
	}
	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}

}
