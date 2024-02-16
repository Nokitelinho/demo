package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class DamagedMailbagVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
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
	private ZonedDateTime damageDate;
	private String remarks;
	private String operationType;
	private String operationFlag;
	private String returnedFlag;
	private String paCode;
	private String flightNumber;
	private String carrierCode;
	private String subClassCode;
	private String subClassGroup;
	private Quantity weight;
	private double declaredValue;
	private ZonedDateTime flightDate;
	private String damageType;
	private String flightOrigin;
	private String flightDestination;
	private String currencyCode;
	private double declaredValueTot;
	private String totCurrencyCode;
	private ZonedDateTime lastUpdateTime;
	private String fileName;
}
