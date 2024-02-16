package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNInULDForSegmentModel extends BaseModel {
	private String dsn;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailClass;
	private int year;
	private int statedBags;
	private Measure statedWeight;
	private int acceptedBags;
	private Measure acceptedWeight;
	private String operationFlag;
	private String pltEnabledFlag;
	private int receivedBags;
	private Measure receivedWeight;
	private int deliveredBags;
	private Measure deliveredWeight;
	private int documentOwnerIdentifier;
	private String masterDocumentNumber;
	private int duplicateNumber;
	private int sequenceNumber;
	private String documentOwnerCode;
	private String mailSubclass;
	private String mailCategoryCode;
	private Collection<DSNInContainerForSegmentModel> dsnInContainerForSegs;
	private Collection<MailbagInULDForSegmentModel> mailBags;
	private Collection<DSNInConsignmentForULDSegmentModel> dsnInConsignments;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String ubrNumber;
	private double mailrate;
	private String currencyCode;
	private int transferredPieces;
	private Measure transferredWeight;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
