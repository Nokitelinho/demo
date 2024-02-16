package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DamagedMailbagModel extends BaseModel {
	private String companyCode;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private String mailbagId;
	private String damageCode;
	private String airportCode;
	private String damageDescription;
	private String userCode;
	private LocalDate damageDate;
	private String remarks;
	private String operationType;
	private String operationFlag;
	private String returnedFlag;
	private String paCode;
	private String flightNumber;
	private String carrierCode;
	private String subClassCode;
	private String subClassGroup;
	private Measure weight;
	private double declaredValue;
	private LocalDate flightDate;
	private String damageType;
	private String flightOrigin;
	private String flightDestination;
	private String currencyCode;
	private double declaredValueTot;
	private String totCurrencyCode;
	private LocalDate lastUpdateTime;
	private String fileName;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
