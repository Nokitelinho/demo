package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class TransferManifestVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* module
	*/
	public static final String MODULE = "mail";
	/** 
	* submodule
	*/
	public static final String SUBMODULE = "operations";
	/** 
	* entity
	*/
	public static final String ENTITY = "mail.operations.TransferManifest";
	private String companyCode;
	private String transferManifestId;
	private String airPort;
	private String transferredToCarrierCode;
	private String transferredToFltNumber;
	private String transferredFromCarCode;
	private String transferredFromFltNum;
	private ZonedDateTime fromFltDat;
	private ZonedDateTime toFltDat;
	private ZonedDateTime transferredDate;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String toCarCodeDesc;
	private String fromCarCodeDesc;
	private int totalBags;
	private Quantity totalWeight;
	private String transferStatus;
	private Collection<DSNVO> dsnVOs;
	private long transferredfrmFltSeqNum;
	private int transferredfrmSegSerNum;
	private String containerNumber;
	private String mailbagId;
	private long mailsequenceNumber;
	private String tranferSource;
	private String status;
}
