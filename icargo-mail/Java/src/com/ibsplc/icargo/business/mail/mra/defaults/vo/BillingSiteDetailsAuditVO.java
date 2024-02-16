/*
 * BillingSiteAuditVO.java Created on Dec 19, 2013
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * The Class BillingSiteDetailsAuditVO.
 *
 * @author A-5219
 */
public class BillingSiteDetailsAuditVO extends AuditVO {


	/** The Constant AUDIT_MODULENAME. */
	public static final String AUDIT_MODULENAME = "mail";

	/** submodule name. */
    public static final String AUDIT_SUBMODULENAME = "mra";

    /** Entity name. */
    public static final String AUDIT_ENTITY = "mail.mra.billingsite";

    /** The Constant BILLINGSITE_CREATED. */
    public static final String BILLINGSITE_CREATED="MTKMRABLGSITCRT";

    /** The Constant BILLINGSITE_DELETED. */
    public static final String BILLINGSITE_DELETED="MTKMRABLGSITDEL";

    /** The Constant BILLINGSITE_MODIFIED. */
    public static final String BILLINGSITE_MODIFIED="MTKMRABLGSITMOD";

    /** The billing site code. */
    private String billingSiteCode;

	/** The key type. */
	private String keyType;

	/** The max seq number. */
	private int maxSeqNumber;

	/** The generated site code. */
	private String generatedSiteCode;


	/**
	 * Gets the key type.
	 *
	 * @return the keyType
	 */
	public String getKeyType() {
		return keyType;
	}

	/**
	 * Sets the key type.
	 *
	 * @param keyType the keyType to set
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	/**
	 * Gets the max seq number.
	 *
	 * @return the maxSeqNumber
	 */
	public int getMaxSeqNumber() {
		return maxSeqNumber;
	}

	/**
	 * Sets the max seq number.
	 *
	 * @param maxSeqNumber the maxSeqNumber to set
	 */
	public void setMaxSeqNumber(int maxSeqNumber) {
		this.maxSeqNumber = maxSeqNumber;
	}

	/**
	 * Gets the billing site code.
	 *
	 * @return the billingSite
	 */
	public String getBillingSiteCode() {
		return billingSiteCode;
	}

	/**
	 * Sets the billing site code.
	 *
	 * @param billingSiteCode the new billing site code
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}


	/**
	 * Instantiates a new billing site details audit vo.
	 *
	 * @param moduleName the module name
	 * @param subModuleName the sub module name
	 * @param entityName the entity name
	 */
	public BillingSiteDetailsAuditVO(String moduleName, String subModuleName,String entityName) {
		super(moduleName, subModuleName, entityName);

	}

	/**
	 * Sets the generated site code.
	 *
	 * @param generatedSiteCode the generatedSiteCode to set
	 */
	public void setGeneratedSiteCode(String generatedSiteCode) {
		this.generatedSiteCode = generatedSiteCode;
	}

	/**
	 * Gets the generated site code.
	 *
	 * @return the generatedSiteCode
	 */
	public String getGeneratedSiteCode() {
		return generatedSiteCode;
	}


	}
