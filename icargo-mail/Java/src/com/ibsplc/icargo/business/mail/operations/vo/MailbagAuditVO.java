/*
 * MailbagAuditVO.java Created on JUN 30, 2016
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
public class MailbagAuditVO extends AuditVO {
	/**
	 * The constant for the Tranfer Action
	 */
	public static final String TRANSFER_ACTION = "MALTRA";
	/**
	 * Module name
	 */
	public static final String MOD_NAM = "mail";
	/**
	 * Submodule name . Used from mra module
	 */
	public static final String SUB_MOD_MRA = "mra";
	public static final String SUB_MOD_OPERATIONS ="operations" ;
	//Added by A-5945 for ICRD-	119569
	public static final String MAILBAG_DELETED = "DELETED";
	public static final String MAILBAG_MODIFIED = "MODIFYMAL";
	public static final String ENTITY_MAILBAG="mail.operations.Mailbag";
	public static final String ENTITY_MAIL ="MAL";
	public static final String[] AUDITABLEFIELDS = new String[]{
								"Company Code", "Mailbag ID","Scan Date"};
	public static final String MAL_CMPCOD_UPDATED = "UPDATEMAL";
	/**
	 * Mail ScannedDateTime Update for Changed scan time Audit
	 * //Added for ICRD-140584
	 */
	public static final String MAL_SCNDATTIM_UPDATED = "UPDATESCNDATTIM";
	public static final String MAL_AWB_ATTACHED = "ATTACHAWB";
	public static final String MAL_AWB_DEATTACHED = "DETTACHAWB";
	public static final String MAL_SETTLEMENT = "INVSETTLEMENT";
	public static final String MAILBAG_RETURNED = "RETURNED";
	public static final String MAILBAG_DELIVERED = "DELIVERED";
	public static final String MAILBAG_ARRIVED = "ARRIVED";
	public static final String MAILBAG_TRANSFER = "TRANSFERED";
	public static final String MAILBAG_ACCEPTANCE = "ACCEPTED";
	public static final String MAILBAG_REASSIGN = "REASSIGNED";
	public static final String MAILBAG_READYFORDELIVERY = "READY TO BE DELIVERED";
	public static final String MAILBAG_DAMAGED = "DAMAGED";
	public static final String MAILBAG_CARDIT = "CARDIT";
	public static final String MAILBAG_RESDIT = "RESDIT";
	public static final String MAILBAG_ASSIGNED = "ASSIGNED";
	public static final String MAILBAG_FLIGHT_DEPARTURE = "FLIGHT DEPARTURE";
	public static final String MAILBAG_FLIGHT_ARRIVAL = "FLIGHT ARRIVED";
	public static final String MAILBAG_OFFLOAD = "OFFLOADED";
	public static final String CONTAINER_TRANSFER = "TRANSFERED";
	
	public static final String RESDIT_RECEIVED = "RESDIT RECEIVED";
	public static final String RESDIT_ASSIGNED = "RESDIT ASSIGNED";
	public static final String RESDIT_LOADED = "RESDIT LOADED";
	public static final String RESDIT_UPLIFTED = "RESDIT UPLIFTED";
	public static final String RESDIT_DELIVERED = "RESDIT_DELIVERED";
	public static final String RESDIT_HANDOVER_ONLINE = "RESDIT_HANDOVER_ONLINE";
	public static final String RESDIT_HANDOVER_OFFLINE = "RESDIT_HANDOVER_OFFLINE";
	public static final String RESDIT_HANDOVER_RECEIVED = "RESDIT_HANDOVER_RECEIVED";
    public static final String RESDIT_PENDING= "RESDIT_PENDING";
    public static final String RESDIT_RETURNED = "RESDIT_RETURNED";
    public static final String RESDIT_READYFOR_DELIVERY= "RESDIT_READYFOR_DELIVERY";
    public static final String RESDIT_ARRIVED= "RESDIT_ARRIVED";
    public static final String RESDIT_TRANSPORT_COMPLETED = "RESDIT_TRANSPORT_COMPLETED";
    public static final String MAILBAG_ORG_DEST_MODIFIED = "Mailbag Modified";
    /**
     * @param moduleName
     * @param subModuleName
     * @param entityName
     */
    public MailbagAuditVO(String moduleName, String subModuleName,
            String entityName) {
        super(moduleName, subModuleName, entityName);
    }


    private String dsn;
    private String originExchangeOffice;
    private String destinationExchangeOffice;
    private String mailClass;

    //Added to include the DSN PK
    private String mailSubclass;

    private String mailCategoryCode;


    private int year;
    private String mailbagId;


    /**
     * Last update user code
     */
    private String lastUpdateUser;

    private long mailSequenceNumber;
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
     * @return Returns the mailbagId.
     */
    public String getMailbagId() {
        return mailbagId;
    }


    /**
     * @param mailbagId The mailbagId to set.
     */
    public void setMailbagId(String mailbagId) {
        this.mailbagId = mailbagId;
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
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}


	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
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
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	/**
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}


	/*@Override
	public AuditHistoryVO constructNewHistoryVO(String actionCode){
		MailBagAuditHistoryVO historyVO =
				new MailBagAuditHistoryVO(this.getModuleName(),this.getSubModuleName(),this.getEntityName());
	}*/
}
