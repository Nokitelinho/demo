/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.AttachFileModel.java
 *
 *	Created by	:	A-4809
 *	Created on	:	01-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.defaults;

import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.FilterFromAdvpay;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.AttachFileModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	01-Nov-2021	:	Draft
 */
public class AttachFileModel extends AbstractScreenModel{




	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mail.mra.defaults.ux.attachfile";


	private FilterFromAdvpay  filterFromAdvpay;
	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SUBPRODUCT;
	}

	@Override
	public String getSubProduct() {
		return SCREENID;
	}

	/**
	 * 	Getter for filterFromAdvpay
	 *	Added by : A-4809 on 12-Nov-2021
	 * 	Used for :
	 */
	public FilterFromAdvpay getFilterFromAdvpay() {
		return filterFromAdvpay;
	}

	/**
	 *  @param filterFromAdvpay the filterFromAdvpay to set
	 * 	Setter for filterFromAdvpay
	 *	Added by : A-4809 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setFilterFromAdvpay(FilterFromAdvpay filterFromAdvpay) {
		this.filterFromAdvpay = filterFromAdvpay;
	}




}
