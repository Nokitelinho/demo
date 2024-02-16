package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class PostalAdministrationModel extends BaseModel {
	private String companyCode;
	private String paCode;
	private String paName;
	private String address;
	private String countryCode;
	private String operationFlag;
	private boolean partialResdit;
	private boolean msgEventLocationNeeded;
	private String settlementCurrencyCode;
	private String baseType;
	private String billingSource;
	private String billingFrequency;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String conPerson;
	private String state;
	private String country;
	private String mobile;
	private String postCod;
	private String phone1;
	private String phone2;
	private String fax;
	private String email;
	private String city;
	private String remarks;
	private String debInvCode;
	private String status;
	private String accNum;
	private String cusCode;
	private String vatNumber;
	private String autoEmailReqd;
	private int dueInDays;
	private String proformaInvoiceRequired;
	private String gibCustomerFlag;
	private int resditTriggerPeriod;
	private String settlementLevel;
	private double tolerancePercent;
	private double toleranceValue;
	private double maxValue;
	private int dupMailbagPeriod;
	private String secondaryEmail1;
	private String secondaryEmail2;
	private boolean isPASSPA;
	private HashMap<String, Collection<PostalAdministrationDetailsModel>> postalAdministrationDetailsVOs;
	private Collection<PostalAdministrationDetailsModel> paDetails;
	private String messagingEnabled;
	private String basisType;
	private String residtversion;
	private String latValLevel;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
