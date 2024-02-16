package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditReceptacleModel extends BaseModel {
	private String receptacleId;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailCategoryCode;
	private int lastDigitOfYear;
	private Measure receptacleWeight;
	private String highestNumberReceptacleIndicator;
	private String regdOrInsuredIndicator;
	private String mailSubClassCode;
	private String despatchNumber;
	private String receptacleSerialNumber;
	private String handlingClass;
	private String codeListResponsibleAgency;
	private String dangerousGoodsIndicator;
	private String referenceQualifier;
	private String receptacleType;
	private String despatchIdentification;
	private String measurementApplicationQualifier;
	private String measureUnitQualifier;
	private String receptacleWeightType;
	private String documentOrMessageNameCode;
	private String lastUpdateUser;
	private String carditType;
	private String pkgType;
	private String mailBagId;
	private String receptacleStatus;
	private LocalDate updatedTime;
	private String carditKey;
	private long mailSeqNum;
	private LocalDate reqDeliveryTime;
	private String mailOrigin;
	private String mailDestination;
	private LocalDate handoverTime;
	private String sealNumber;
	private String masterDocumentNumber;
	private int ownerId;
	private int duplicateNumber;
	private int sequenceNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
