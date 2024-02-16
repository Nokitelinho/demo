/*
 * DSNAuditVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author a-5991
 *
 */
public class DSNAuditVO extends AuditVO {

	/**
	 * Action code for billing status change
	 */
	public static final String DSN_STACHG = "STACHG";
	
	/**
	 * Action code for rejection memo
	 */
	public static final String DSN_REJMEM = "REJMEM";
	
	/**
	 * Action code for cca issue
	 */
	public static final String DSN_CCA = "CCA";
	
	/**
	 * Action code for rate audit
	 */
	public static final String DSN_RATAUD = "RATAUD";
	/**
	 * Action code for Manual Accounting
	 */
	public static final String DSN_DBT = "DSNDBT";
	
	/**
	 * Module name
	 */
	public static final String MOD_NAM = "mailtracking";
	
	/**
	 * Submodule name . Used from mra module
	 */
	public static final String SUB_MOD_MRA = "mra";
    /**
     * @param moduleName
     * @param subModuleName
     * @param entityName
     */
    public DSNAuditVO(String moduleName, String subModuleName, String entityName) {
        super(moduleName, subModuleName, entityName);
    }
 
    private String dsn;
    private String originExchangeOffice;
    private String destinationExchangeOffice;
    private String mailCategoryCode;
    private String mailSubclass;
    private int year;   
    
    private String mailClass;
   
   /**
     * Last update user code
     */
    private String lastUpdateUser;
    
    /**
     * @return Returns the destinationExchangeOffice.
     */
    public String getDestinationExchangeOffice() {
        return destinationExchangeOffice;
    }

    /**
     * @param destinationExchangeOffice The destinationExchangeOffice to set.
     */
    public void setDestinationExchangeOffice(String destinationExchangeOffice) {
        this.destinationExchangeOffice = destinationExchangeOffice;
    }

    /**
     * @return Returns the dsn.
     */
    public String getDsn() {
        return dsn;
    }

    /**
     * @param dsn The dsn to set.
     */
    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    /**
     * @return Returns the lastUpdateUser.
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * @param lastUpdateUser The lastUpdateUser to set.
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * @return Returns the mailClass.
     */
    public String getMailSubclass() {
        return mailSubclass;
    }

    /**
     * @param mailClass The mailClass to set.
     */
    public void setMailSubclass(String mailClass) {
        this.mailSubclass = mailClass;
    }

    /**
     * @return Returns the originExchangeOffice.
     */
    public String getOriginExchangeOffice() {
        return originExchangeOffice;
    }

    /**
     * @param originExchangeOffice The originExchangeOffice to set.
     */
    public void setOriginExchangeOffice(String originExchangeOffice) {
        this.originExchangeOffice = originExchangeOffice;
    }

    /**
     * @return Returns the year.
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}


}
