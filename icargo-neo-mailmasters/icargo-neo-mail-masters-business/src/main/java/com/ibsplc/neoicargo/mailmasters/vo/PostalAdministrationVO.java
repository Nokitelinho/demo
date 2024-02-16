package com.ibsplc.neoicargo.mailmasters.vo;

import java.util.Collection;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class PostalAdministrationVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
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
	private ZonedDateTime lastUpdateTime;
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
	private HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs;
	private Collection<PostalAdministrationDetailsVO> paDetails;
	public static final String BILLING_SOURCE_BILLING = "B";
	public static final String BILLING_SOURCE_REPORTING = "R";
	/** 
	* Is MessagingEnabled For PA
	*/
	private String messagingEnabled;
	private String basisType;
	/** 
	* CRQ-AirNZ985
	*/
	private String residtversion;
	private String latValLevel;

	/** 
	* @param isPASSPA the isPASSPA to setSetter for isPASSPA  Added by : A-8061 on 10-Jun-2021 Used for :
	*/
	public void setPASSPA(boolean isPASSPA) {
		this.isPASSPA = isPASSPA;
	}
}
