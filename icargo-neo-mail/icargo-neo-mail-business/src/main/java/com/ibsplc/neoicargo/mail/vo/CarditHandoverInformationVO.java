package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class CarditHandoverInformationVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* This field is the unique key identifying a CARDIT cardit key is a combination of sender ID, consignment-document-number, despatch year
	*/
	private String carditKey;
	/** 
	* Cardit Type
	*/
	private String carditType;
	/** 
	* This field indicates acceptance(Handover Origin) location qualifier eg: 84 - place of Acceptance(Handover Origin)
	*/
	private String handoverOriginLocationQualifier;
	/** 
	* Handover Origin Place 
	*/
	private String handoverOriginLocationIdentifier;
	/** 
	* Handover Origin Location 
	*/
	private String handoverOriginLocationName;
	/** 
	* This field indicates code list responsible agency for place of Handover Origin(Acceptance)
	*/
	private String handoverOriginCodeListAgency;
	/** 
	* This field indicates code list qualifier of  place of 
	*/
	private String handoverOriginCodeListQualifier;
	/** 
	* This field indicates delivery(Handover Destination) location qualifier eg: 1 - place of Delivery(Handover Destination
	*/
	private String handoverDestnLocationQualifier;
	/** 
	* Handover destination Location Identifier
	*/
	private String handoverDestnLocationIdentifier;
	/** 
	* Handover destination Location Name 
	*/
	private String handoverDestnLocationName;
	/** 
	* This field indicates code list responsible agency for place of Handover Destination(Delivery)
	*/
	private String handoverDestnCodeListAgency;
	/** 
	* This field indicates code list qualifier of  place of Handover Destination(Delivery)
	*/
	private String handoverDestnCodeListQualifier;
	/** 
	* This field indicates transport stage qualifier eg: Z90
	*/
	private String transportStageQualifier;
	/** 
	* This field is the qualifier for handOver date/time eg: 234,63
	*/
	private String dateTimePeriodQualifier;
	/** 
	* This field is for Origin Cut Off(Collection) date/time Period
	*/
	private ZonedDateTime originCutOffPeriod;
	/** 
	* This field is for Destination Cut Off Period (Delivery) date/time Period
	*/
	private ZonedDateTime destinationCutOffPeriod;
	/** 
	* This field is format qualifier of date and time eg: 201 indicates the format YYMMDDHHMM eg: 101 indicates the format YYMMDD
	*/
	private String dateTimeFormatQualifier;
}
