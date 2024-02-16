package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class MailbagAuditVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
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
	public static final String SUB_MOD_OPERATIONS = "operations";
	public static final String MAILBAG_DELETED = "DELETED";
	public static final String MAILBAG_MODIFIED = "MODIFYMAL";
	public static final String ENTITY_MAILBAG = "mail.operations.Mailbag";
	public static final String ENTITY_MAIL = "MAL";
	public static final String[] AUDITABLEFIELDS = new String[] { "Company Code", "Mailbag ID", "Scan Date" };
	public static final String MAL_CMPCOD_UPDATED = "UPDATEMAL";
	/** 
	* Mail ScannedDateTime Update for Changed scan time Audit //Added for ICRD-140584
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
	public static final String RESDIT_PENDING = "RESDIT_PENDING";
	public static final String RESDIT_RETURNED = "RESDIT_RETURNED";
	public static final String RESDIT_READYFOR_DELIVERY = "RESDIT_READYFOR_DELIVERY";
	public static final String RESDIT_ARRIVED = "RESDIT_ARRIVED";
	public static final String RESDIT_TRANSPORT_COMPLETED = "RESDIT_TRANSPORT_COMPLETED";
	public static final String MAILBAG_ORG_DEST_MODIFIED = "Mailbag Modified";

	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private String mailSubclass;
	private String mailCategoryCode;
	private int year;
	private String mailbagId;
	/** 
	* Last update user code
	*/
	private String lastUpdateUser;
	private long mailSequenceNumber;
}
