package com.ibsplc.neoicargo.mailmasters.vo;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class MailbagEnquiryFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String ooe;
	private String doe;
	private String mailCategoryCode;
	private String mailSubclass;
	private String year;
	private String despatchSerialNumber;
	private String receptacleSerialNumber;
	private String currentStatus;
	private String scanPort;
	private ZonedDateTime scanFromDate;
	private ZonedDateTime scanToDate;
	private String damageFlag;
	private String scanUser;
	private String containerNumber;
	private String carrierCode;
	private String flightNumber;
	private ZonedDateTime flightDate;
	private int carrierId;
	private String companyCode;
	private String fromCarrier;
	private String carditPresent;
	private boolean isInventory;
	private String destinationCity;
	private String transitFlag;
	/** 
	* Added By Karthick V to include the Incoming Flight in the Inventory ..
	*/
	private String fromFlightNumber;
	private int totalRecords;
	private int pageNumber;
	private String fromScreen;
	private String consigmentNumber;
	private String upuCode;
	private String fromExportList;
	private String mailbagId;
	private ZonedDateTime reqDeliveryTime;
	private String origin;
	private String awbNumber;
	private String fromDate;
	private String toDate;
	private String lyingList;
	private String filterType;
	private int defaultPageSize;
	private String pacode;
	private String upliftAirport;
	private String uldNumber;
	private String originAirportCode;
	private String destinationAirportCode;
	private int flightCarrierIdr;
	private int pageSize;
	private String serviceLevel;
	private String onTimeDelivery;
	private ArrayList<String> mailBagsToList;
	private ZonedDateTime transportServWindow;
	private ZonedDateTime consignmentDate;
	private String transferFromCarrier;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private int flightSequenceNumber;

	/** 
	* @param isInventory The isInventory to set.
	*/
	public void setInventory(boolean isInventory) {
		this.isInventory = isInventory;
	}
}
