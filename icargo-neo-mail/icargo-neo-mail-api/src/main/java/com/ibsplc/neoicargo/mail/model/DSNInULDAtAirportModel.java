package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNInULDAtAirportModel extends BaseModel {
	private String dsn;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailClass;
	private int year;
	private int statedBags;
	private Measure statedWeight;
	private int acceptedBags;
	private Measure acceptedWeight;
	private String pltEnabledFlag;
	private String operationFlag;
	private int documentOwnerIdentifier;
	private String masterDocumentNumber;
	private int duplicateNumber;
	private int sequenceNumber;
	private String documentOwnerCode;
	private String mailCategoryCode;
	private String mailSubclass;
	private Collection<DSNInContainerAtAirportModel> dsnInContainerAtAirports;
	private Collection<MailbagInULDAtAirportModel> mailbagInULDVOs;
	private Collection<DSNInConsignmentForULDAtAirportModel> dsnInConsignments;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
