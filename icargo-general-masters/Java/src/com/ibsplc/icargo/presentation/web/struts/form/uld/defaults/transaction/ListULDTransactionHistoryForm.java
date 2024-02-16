/*
 * ListULDTransactionHistoryForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1496
 *
 */
public class ListULDTransactionHistoryForm extends ScreenModel {


	private static final String BUNDLE = "listuldtransactionhistory";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.listuldtransactionhistory";


	private String bundle;


    /**
     * @return
     */
    public String getScreenId() {
        return SCREENID;
    }

    /**
     * @return
     */
    public String getProduct() {
        return null;
    }

    /**
     * @return
     */
    public String getSubProduct() {
        return null;
    }

}
