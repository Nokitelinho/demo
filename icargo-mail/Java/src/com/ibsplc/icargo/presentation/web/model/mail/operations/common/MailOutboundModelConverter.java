package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.presentation.web.model.operations.flthandling.common.FlightValidation;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

public class MailOutboundModelConverter {
	private static final String DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm";
	private static final String STATUS_FLAG_UPDATE = "U";
	private static final String STATUS_FLAG_INSERT = "I";
	private static final String OUTBOUND = "O";
	public static Map<String,Collection<OneTime>> constructOneTimeValues(Map<String,Collection<OneTimeVO>> oneTimeValues){
		HashMap<String,Collection<OneTime>> oneTimeValuesMap = new HashMap<String,Collection<OneTime>>();
		for(Map.Entry<String,Collection<OneTimeVO>> oneTimeValue : oneTimeValues.entrySet()){
			ArrayList<OneTime> oneTimes= new ArrayList<OneTime>();
			for(OneTimeVO oneTimeVO : oneTimeValue.getValue()){
				OneTime oneTime =  new OneTime();
				oneTime.setFieldType(oneTimeVO.getFieldType());
				oneTime.setFieldValue(oneTimeVO.getFieldValue());
				oneTime.setFieldDescription(oneTimeVO.getFieldDescription());
				oneTimes.add(oneTime);
			}
			oneTimeValuesMap.put(oneTimeValue.getKey(), oneTimes);
		}
		return oneTimeValuesMap;
	}
	public static FlightValidation constructFlightValidation(FlightValidationVO flightValidationVO,LogonAttributes logonAttributes){
		
		FlightValidation flightValidation = new FlightValidation();
		flightValidation.setCompanyCode(flightValidationVO.getCompanyCode());
		flightValidation.setAircraftType(flightValidationVO.getAircraftType());
		flightValidation.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
		flightValidation.setFlightNumber(flightValidationVO.getFlightNumber());
		flightValidation.setFlightRoute(flightValidationVO.getFlightRoute());
		flightValidation.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		flightValidation.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		flightValidation.setLegStatus(flightValidationVO.getLegStatus());
		LocalDate departureTime = null;
		if(flightValidationVO.getAtd()!=null){
			departureTime = flightValidationVO.getAtd();
			flightValidation.setDepartureTime(departureTime.toDisplayTimeOnlyFormat(true)+"(A)");
		}
		else if(flightValidationVO.getEtd()!=null){
			departureTime = flightValidationVO.getEtd();
			flightValidation.setDepartureTime(departureTime.toDisplayTimeOnlyFormat(true)+"(E)");
		}
		else{
			departureTime = flightValidationVO.getStd();
			flightValidation.setDepartureTime(departureTime.toDisplayTimeOnlyFormat(true)+"(S)");
		}
		if(flightValidationVO.getApplicableDateAtRequestedAirport()!=null){
			flightValidation.setApplicableDateAtRequestedAirport(flightValidationVO.getApplicableDateAtRequestedAirport().toDisplayFormat());
		}
		LocalDate currentTime = new LocalDate(logonAttributes
				.getAirportCode(), Location.ARP, true);	
		long timeDifference = departureTime.findDifference(currentTime);
		long diffHours = 0;
		if(timeDifference>0){
		    long diffMinutes = timeDifference / (60 * 1000) % 60;
		     diffHours = timeDifference / (60 * 60 * 1000);		
			flightValidation.setTimeToDeparture(diffHours+"h "+diffMinutes+'m');
		}
		else{
			flightValidation.setTimeToDeparture("0h 0m");
		}
		if(diffHours<12){
			flightValidation.setTimeToDepartureDifferenceFlag(true);
		}
		flightValidation.setFlightType(flightValidationVO.getFlightType());
		flightValidation.setCarrierCode(flightValidationVO.getCarrierCode());
		flightValidation.setStd(flightValidationVO.getStd().toDisplayFormat());
		if(flightValidationVO.getAtd()!=null){ 
			flightValidation.setAtd(flightValidationVO.getAtd().toDisplayFormat());
		}
		flightValidation.setTailNumber(flightValidationVO.getTailNumber());
		return flightValidation;
	}
	
	
public static List<MailAcceptance> constructMailFlights(Collection<MailAcceptanceVO> mailFlights,String carrierCode){
	List<MailAcceptance> localMailList = new ArrayList<MailAcceptance>();
		
	 if ((mailFlights != null) && (mailFlights.size() > 0))
	    {
	      Iterator localIterator = mailFlights.iterator();
	      while (localIterator.hasNext()) {
	    int totalULDs=0;
	    int totalbulks=0;
		MailAcceptance mailflight = new MailAcceptance();
		MailAcceptanceVO mailacceptancevo= (MailAcceptanceVO)localIterator.next();
		mailflight.setCompanyCode(mailacceptancevo.getCompanyCode());
		mailflight.setCarrierId(mailacceptancevo.getCarrierId());
		mailflight.setCarrierCode(carrierCode);
		mailflight.setFlightNumber(mailacceptancevo.getFlightNumber());
		mailflight.setFlightDate(mailacceptancevo.getFlightDate().toDisplayDateOnlyFormat());
		mailflight.setFlightDepartureDate(mailacceptancevo.getFlightDepartureDate().toDisplayDateOnlyFormat());
		mailflight.setFlightTime(mailacceptancevo.getFlightDepartureDate().toDisplayTimeOnlyFormat());
		mailflight.setFlightCarrierCode(carrierCode);
		mailacceptancevo.getFlightRoute().split("-");
		mailflight.setFlightOrigin(mailacceptancevo.getFlightOrigin());
		mailflight.setFlightDestination(mailacceptancevo.getFlightDestination());
		mailflight.setFlightRoute(mailacceptancevo.getFlightRoute());
		mailflight.setFlightSequenceNumber(mailacceptancevo.getFlightSequenceNumber());
		mailflight.setFlightStatus(mailacceptancevo.getFlightStatus());
		mailflight.setFlightOperationalStatus(mailacceptancevo.getFlightOperationalStatus());
		mailflight.setFlightType(mailacceptancevo.getFlightType());
		mailflight.setLegSerialNumber(mailacceptancevo.getLegSerialNumber());
		mailflight.setFlightDateDesc(mailacceptancevo.getFlightDateDesc());
		mailflight.setAircraftType(mailacceptancevo.getAircraftType());
		mailflight.setDepartureGate(mailacceptancevo.getDepartureGate());
		//Uplift airport to confirm
		mailflight.setUpliftAirport(mailacceptancevo.getPol());
		mailflight.setPol(mailacceptancevo.getPol());
		//added by A-8893 for IASCB-27535 starts
	    mailflight.setDCSinfo(mailacceptancevo.getDCSinfo());
		mailflight.setDCSregectionReason(mailacceptancevo.getDCSregectionReason());
		//added by A-8893 for IASCB-27535 ends
		mailflight.setStd(mailacceptancevo.getStd().toDisplayDateOnlyFormat());
		Preadvice preadvice =  new Preadvice();
		preadvice.setTotalBags(0);
		preadvice.setTotalWeight(null);
		mailflight.setPreadvice(preadvice);
		if(mailacceptancevo.getContainerDetails()!=null || mailacceptancevo.getContainerDetails().size()!=0) {
			List<ContainerDetails> containerlist = new ArrayList<ContainerDetails>();
			mailflight=constructContainerSummaryDetails(mailflight,(ArrayList)mailacceptancevo.getContainerDetails());
			//mailflight.setContainerDetails(containerlist);

		}
		    MailAcceptancePK flightpk= new MailAcceptancePK();
	        flightpk.setCompanyCode(mailacceptancevo.getCompanyCode());
	        //flightpk.setAirportCode("FRA");
	        flightpk.setFlightCarrierId(mailacceptancevo.getCarrierId());
	        flightpk.setFlightNumber(mailacceptancevo.getFlightNumber());
	        flightpk.setFlightSeqNum(mailacceptancevo.getFlightSequenceNumber());
	        flightpk.setLegSerNum(mailacceptancevo.getLegSerialNumber());
	        mailflight.setFlightpk(flightpk);
	        if(mailflight.getFlightOperationalStatus().equals("N")){
	        	mailflight.setManifestInfo("No Manifest Info");
	        }else{
	        	Collection<ContainerDetails> containers = mailflight.getContainerDetails();
		        StringBuilder manifestInfo = new StringBuilder();
		        manifestInfo.append("Manifested :");
		        if(containers!=null)
		        for(ContainerDetails container : containers){
		        	manifestInfo.append(container.getContainercount() + " - " + container.getContainergroup() + "(" + container.getMailbagcount());
		        	if(container.getMailbagcount()!=0){
		        		manifestInfo.append("/");
		        		if(container.getMailbagweight()!=null){
		        			manifestInfo.append(container.getMailbagweight().getRoundedDisplayValue());
		        		}
		        	}
		        	manifestInfo.append(")");
		        }
		        manifestInfo.append("Total :");
		        manifestInfo.append(mailflight.getTotalContainerCount());
		        if(mailflight.getTotalContainerCount()!=0){
		        	manifestInfo.append("/");
	        		if(mailflight.getTotalContainerWeight()!=null){
	        			manifestInfo.append(mailflight.getTotalContainerWeight().getRoundedDisplayValue());
	        		}
		        }
		        manifestInfo.append("Pre-advice :");
		        if(containers!=null)
		        for(ContainerDetails container : containers){
		        	if(container.getReceptacleCount()!=0){
		        		manifestInfo.append("(" + container.getReceptacleCount() + "/");
		        		if(container.getReceptacleWeight()!=null){
		        			manifestInfo.append(container.getReceptacleWeight().getRoundedDisplayValue());
		        		}
		        		manifestInfo.append(")");
		        	}
		        	
	        }
		        mailflight.setManifestInfo(manifestInfo.toString());
	        }
	        
		localMailList.add(mailflight);
	   }
	 }
	 return localMailList;
}

	public static List<ContainerDetails> constructContainerDetails(Collection<ContainerDetailsVO> containervos,
			LogonAttributes logonAttribute) {
		List<ContainerDetails> localcontainerList = new ArrayList<>();
		ContainerDetails container = null;
		if (containervos != null && !containervos.isEmpty()) {
			Iterator<ContainerDetailsVO> localIterator = containervos.iterator();
			while (localIterator.hasNext()) {
				ContainerDetailsVO containervo = localIterator.next();
				container = new ContainerDetails();
				container.setCompanyCode(containervo.getCompanyCode());
				container.setAirportCode(logonAttribute.getAirportCode());
				container.setAcceptanceFlag(containervo.getAcceptedFlag());
				container.setContainerNumber(containervo.getContainerNumber());
				container.setPol(containervo.getPol());
				container.setAssignedPort(containervo.getAssignedPort());
				container.setPou(containervo.getPou());
				constructFlightDetails(logonAttribute, container, containervo);
				container.setAcceptedFlag(containervo.getAcceptedFlag());
				container.setArrivedStatus(containervo.getArrivedStatus());
				container.setType(containervo.getContainerType());
				container.setTotalBags(containervo.getTotalBags());
				container.setTotalWeight(containervo.getTotalWeight());
				container.setOperationFlag(containervo.getOperationFlag());
				container.setContainerOperationFlag(containervo.getContainerOperationFlag());
				container.setDestination(containervo.getDestination());
				container.setPaBuiltFlag(containervo.getPaBuiltFlag());
				container.setContainerJnyId(containervo.getContainerJnyId());
				container.setSegmentSerialNumber(containervo.getSegmentSerialNumber());
				container.setPaCode(containervo.getPaCode());
				container.setPreassignFlag(containervo.isPreassignFlag());
				container.setContainerOperationFlag(containervo.getContainerOperationFlag());
				container.setAssignmentDate(containervo.getAssignedDate());
				container.setAssignedUser(containervo.getAssignedUser());
				container.setLastUpdateTime(containervo.getLastUpdateTime());
				container.setOperationFlag(containervo.getOperationFlag());
				container.setContainerOperationFlag(containervo.getContainerOperationFlag());
				container.setTransferFromCarrier(containervo.getTransferFromCarrier());
				container.setWareHouse(containervo.getWareHouse());
				container.setLocation(containervo.getLocation());
				container.setOnwardFlights(containervo.getOnwardFlights());
				container.setRemarks(containervo.getRemarks());
				container.setFinalDestination(containervo.getDestination());
				container.setTransitFlag(containervo.getTransitFlag());
				if (containervo.getAssignedDate() != null) {
					container.setAssignedOn(containervo.getAssignedDate().toDisplayDateOnlyFormat());
				}
				container.setUldLastUpdateTime(containervo.getUldLastUpdateTime());
				if (containervo.getActualWeight() != null) {
					container.setActualWeight(containervo.getActualWeight().getRoundedDisplayValue());
				}
				container.setContentId(containervo.getContentId());
				container.setMailDetails(constructMailbagDetails(containervo.getMailDetails(), containervo));
				container.setDesptachDetailsCollection(constructDesptachDetails(containervo.getDsnVOs(), containervo));
				container.setTotalBags(containervo.getTotalBags());
				container.setBags(containervo.getTotalBags());
				container.setUldFulIndFlag(containervo.getUldFulIndFlag());
				container.setTransactionCode(containervo.getTransactionCode());
				if (containervo.getMinReqDelveryTime() != null) {
					container.setMinReqDelveryTime(
							containervo.getMinReqDelveryTime().toDisplayFormat(DATE_TIME_FORMAT));
				}
				container.setContainerPureTransfer(containervo.getContainerPureTransfer());
				container.setOffloadedInfo(containervo.getOffloadedInfo());
				container.setOffloadCount(containervo.getOffloadCount());
				container.setRateAvailforallMailbags(containervo.getRateAvailforallMailbags());
				container.setBaseCurrency(containervo.getBaseCurrency());
			if(containervo.getProvosionalCharge() !=null) {
			container.setProvosionalCharge(containervo.getProvosionalCharge().getAmount());
			}
				localcontainerList.add(container);

			}
		}
		return localcontainerList;
	}
private static void constructFlightDetails(LogonAttributes logonAttribute, ContainerDetails container,
			ContainerDetailsVO containervo) {
		container.setCarrierId(containervo.getCarrierId());
		container.setCarrierCode(containervo.getCarrierCode());
		container.setFlightSequenceNumber(containervo.getFlightSequenceNumber());
		container.setFlightNumber(containervo.getFlightNumber());
		container.setLegSerialNumber(containervo.getLegSerialNumber());
		if (containervo.getFlightDate() != null) {
			container.setFlightDate(containervo.getFlightDate().toDisplayDateOnlyFormat());
		}
		if (Objects.nonNull(containervo.getScanDate())) {
			container.setScannedDate(containervo.getScanDate().toDisplayDateOnlyFormat());
		} else {
			container.setScannedDate(
					new LocalDate(logonAttribute.getAirportCode(), Location.ARP, false).toDisplayDateOnlyFormat());
		}
	}

/*public static List<ContainerDetail> constructCarrierContainerSummaryDetails(Collection<ContainerDetailsVO> containerList, MailFlight mailflight) {
	List<ContainerDetail> localcontainerList = new ArrayList<ContainerDetail>();
	ContainerDetail container= null;
    if ((containerList != null) && (containerList.size() > 0))
    {
      Iterator localIterator = containerList.iterator();
      int totalCount=0;
      while (localIterator.hasNext()) {
    	 
    	    ContainerDetailsVO containervo= (ContainerDetailsVO)localIterator.next();
    	    container = new ContainerDetail();
    		if(containervo.getContainerGroup() !=null || containervo.getContainerGroup().trim().length()>0) {
    		container.setContainergroup(containervo.getContainerGroup());
    		}
    		container.setContainercount(containervo.getContainercount());
    		container.setMailbagcount(containervo.getMailbagcount());
    		container.setMailbagweight(containervo.getMailbagwt());
    	//	container.setReceptacleCount(containervo.getReceptacleCount());
    	//	container.setReceivedWeight(containervo.getReceptacleWeight());
    		totalCount=totalCount+containervo.getMailbagcount();
    	//	container.setTotalBags(containervo.getTotalBags());
    	//	container.setTotalWeight(containervo.getTotalWeight());
    	//	container.setPol(containervo.getPol());
    	//	container.setPou(containervo.getPou());
    		//container.setFlightSequenceNumber(containervo.getFlightSequenceNumber());
    		//container.setLegSerialNumber(containervo.getLegSerialNumber());
    		localcontainerList.add(container);
    		
    	    
      }
      mailflight.setTotalCount(totalCount);
    }
    
    return localcontainerList;
     // {
	
	
}*/
public static MailAcceptance constructContainerSummaryDetails(MailAcceptance mailflight,List<ContainerDetailsVO> containerDetailsVO) {
	List<ContainerDetails> localcontainerList = new ArrayList<ContainerDetails>();
	ContainerDetails container= null;
	DecimalFormat decimalFormatter = new DecimalFormat("0.00");
    if ((containerDetailsVO != null) && (containerDetailsVO.size() > 0))
    {
      Iterator localIterator = containerDetailsVO.iterator();
      int totalContainerCount=0;
      double totalweight=0;
      String displayUnit = null;
      while (localIterator.hasNext()) {
    	 
    	    ContainerDetailsVO containervo= (ContainerDetailsVO)localIterator.next();
    	    container = new ContainerDetails();
    		if(containervo.getContainerGroup() !=null || containervo.getContainerGroup().trim().length()>0) {
    		container.setContainergroup(containervo.getContainerGroup());
    		}
    		container.setContainercount(containervo.getContainercount());
    		container.setMailbagcount(containervo.getMailbagcount());
    		container.setMailbagweight(containervo.getMailbagwt());
    		container.setReceptacleCount(containervo.getReceptacleCount());
    		if(containervo.getReceptacleWeight()!=null) {
    		container.setReceivedWeight(containervo.getReceptacleWeight());
    		}
    		totalContainerCount=totalContainerCount+containervo.getMailbagcount();
    		totalweight=totalweight + containervo.getMailbagwt().getRoundedDisplayValue();
    		displayUnit = containervo.getMailbagwt().getDisplayUnit();
    	//	container.setTotalBags(containervo.getTotalBags());
    	//	container.setTotalWeight(containervo.getTotalWeight());
    	//	container.setPol(containervo.getPol());
    	//	container.setPou(containervo.getPou());
    		//container.setFlightSequenceNumber(containervo.getFlightSequenceNumber());
    		//container.setLegSerialNumber(containervo.getLegSerialNumber());
    		localcontainerList.add(container);
    		
    	    
      }
      mailflight.setContainerDetails(localcontainerList);
      mailflight.setTotalContainerCount(totalContainerCount);
      Measure totalContainerWeight = new Measure(UnitConstants.MAIL_WGT,0.0,
	  			Double.parseDouble(decimalFormatter.format(totalweight)),displayUnit);
      mailflight.setTotalContainerWeight(totalContainerWeight);
    }
    
    return mailflight;
	
	
}

public static List<Mailbag> constructMailbagDetails(Collection<MailbagVO> mailbagList,ContainerDetailsVO containerVO) {
	List<Mailbag> localmailbagList = new ArrayList<Mailbag>();
	
    if ((mailbagList != null) && (mailbagList.size() > 0))
    {
      Iterator localIterator = mailbagList.iterator();
      while (localIterator.hasNext()) {
    	    Mailbag mailbag= null; 
    	    MailbagVO mailbagVO= (MailbagVO)localIterator.next();
    	    mailbag=constructMailbagDetails(mailbagVO,containerVO);
    	    localmailbagList.add(mailbag);
    	    
      }
    }
    return localmailbagList;

}

public static List<DespatchDetails> constructDesptachDetails(Collection<DSNVO> dsnList,ContainerDetailsVO containerVO) {
	List<DespatchDetails> localdsnList = new ArrayList<DespatchDetails>();
    if ((dsnList != null) && (dsnList.size() > 0))
    {
      Iterator localIterator = dsnList.iterator();
      while (localIterator.hasNext()) {
    	    DespatchDetails dsn= null; 
    	    DSNVO dsnVO= (DSNVO)localIterator.next();
    	    dsn=constructDSNDetails(dsnVO,containerVO);
    	    localdsnList.add(dsn);
      }
    }
    return localdsnList;
}
public static List<DespatchDetails> constructMailbagDetailsDSNView(Collection<DSNVO> mailbagList) {
	List<DespatchDetails> localdsnList = new ArrayList<DespatchDetails>();
	
    if ((mailbagList != null) && (mailbagList.size() > 0))
    {
      Iterator localIterator = mailbagList.iterator();
      while (localIterator.hasNext()) {
    	    DespatchDetails dsn= new DespatchDetails(); 
    	    DSNVO dsnVO= (DSNVO)localIterator.next();
    	    dsn.setDsn(dsnVO.getDsn());
    	    dsn.setOriginExchangeOffice(dsnVO.getOriginExchangeOffice());
    	    dsn.setDestinationExchangeOffice(dsnVO.getDestinationExchangeOffice());
    	    dsn.setBags(dsnVO.getBags());
    	    dsn.setMailCategoryCode(dsnVO.getMailCategoryCode());
    	    dsn.setMailSubclass(dsnVO.getMailSubclass());
    	    dsn.setMailClass(dsnVO.getMailClass());
    	    dsn.setSequenceNumber(dsnVO.getSegmentSerialNumber());
    	    dsn.setMasterDocumentNumber(dsnVO.getMasterDocumentNumber());
    	    dsn.setSequenceNumber(dsnVO.getSequenceNumber());
    	    dsn.setDuplicateNumber(dsnVO.getDuplicateNumber());
    	    dsn.setDocumentOwnerCode(dsnVO.getDocumentOwnerCode());
    	    dsn.setDocumentOwnerIdentifier(dsnVO.getDocumentOwnerIdentifier());
    	    dsn.setWeight(dsnVO.getWeight());
    	    dsn.setYear(dsnVO.getYear());
    	    dsn.setConsignmentNumber(dsnVO.getCsgDocNum() );
    	    dsn.setPaCode(dsnVO.getPaCode());
    	    localdsnList.add(dsn);
    	    
      }
    }
    return localdsnList;

}


public static Mailbag constructMailbagDetails(MailbagVO mailbagsvo, ContainerDetailsVO containerVO) {
	Mailbag mailbag = new Mailbag();
	mailbag.setCompanyCode(mailbagsvo.getCompanyCode());
	mailbag.setMailbagId(mailbagsvo.getMailbagId());
	mailbag.setMailSequenceNumber(mailbagsvo.getMailSequenceNumber());
	mailbag.setDespatchId(mailbagsvo.getDespatchId());
	mailbag.setOoe(mailbagsvo.getOoe());
	mailbag.setDoe(mailbagsvo.getDoe());
	mailbag.setMailCategoryCode(mailbagsvo.getMailCategoryCode());
	mailbag.setMailSubclass(mailbagsvo.getMailSubclass());
	mailbag.setMailClass(mailbagsvo.getMailClass());
	mailbag.setYear(mailbagsvo.getYear());
	mailbag.setDespatchSerialNumber(mailbagsvo.getDespatchSerialNumber());
	mailbag.setReceptacleSerialNumber(mailbagsvo.getReceptacleSerialNumber());
	mailbag.setRegisteredOrInsuredIndicator(mailbagsvo.getRegisteredOrInsuredIndicator());
	mailbag.setHighestNumberedReceptacle(mailbagsvo.getHighestNumberedReceptacle());
    mailbag.setMailCompanyCode(mailbagsvo.getMailCompanyCode());
    
	LocalDate consignmentDate = null;
	if (mailbag.getConsignmentDate() != null) {

		//consignmentDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
		//consignmentDate.setDate(mailbagsvo.getConsignmentDate());
		mailbag.setConsignmentDate(mailbagsvo.getConsignmentDate().toDisplayDateOnlyFormat());
	}

	//mailbagVO.setScannedUser(mailbagDetail.getScannedUser());
	mailbag.setCarrierId(mailbagsvo.getCarrierId());
	mailbag.setFlightNumber(containerVO.getFlightNumber());
	mailbag.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
	mailbag.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
	mailbag.setUldNumber(containerVO.getContainerNumber());
	mailbag.setDespatch(mailbagsvo.isDespatch());
	mailbag.setMailorigin(mailbagsvo.getMailOrigin());
	mailbag.setMailDestination(mailbagsvo.getMailDestination());
	mailbag.setRegisteredOrInsuredIndicator(mailbagsvo.getRegisteredOrInsuredIndicator());
	mailbag.setMailRemarks(mailbagsvo.getMailRemarks());
	mailbag.setCarrier(mailbagsvo.getTransferFromCarrier());
	LocalDate reDeliveryTime = null;
	/*if (mailbagDetail.getReqDeliveryTime() != null) {

		reDeliveryTime = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
		reDeliveryTime.setDate(mailbagDetail.getReqDeliveryTime());
		mailbagVO.setReqDeliveryTime(reDeliveryTime);
	}*/

	mailbag.setShipmentPrefix(mailbagsvo.getShipmentPrefix());
	mailbag.setMasterDocumentNumber(mailbagsvo.getDocumentNumber());
	mailbag.setDocumentOwnerIdr(mailbagsvo.getDocumentOwnerIdr());
	mailbag.setDuplicateNumber(mailbagsvo.getDuplicateNumber());
	mailbag.setSequenceNumber(mailbagsvo.getSequenceNumber());

	LocalDate flightDate = null;
	if (mailbag.getFlightDate() != null) {

		//flightDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
		//flightDate.setDate(mailbagsvo.getFlightDate());
		mailbag.setFlightDate(mailbagsvo.getFlightDate().toDisplayTimeOnlyFormat());
	}

	mailbag.setOperationalFlag(mailbagsvo.getOperationalFlag());
	mailbag.setContainerType(mailbagsvo.getContainerType());
	mailbag.setContainerNumber(mailbagsvo.getContainerNumber());
	mailbag.setIsoffload(mailbagsvo.isIsoffload());

	LocalDate scanDate = null;
	if (mailbagsvo.getScannedDate() != null) {


		mailbag.setScannedDate(mailbagsvo.getScannedDate().toDisplayDateOnlyFormat());
		mailbag.setScannedTime(mailbagsvo.getScannedDate().toDisplayTimeOnlyFormat());
	}

	mailbag.setScannedPort(mailbagsvo.getScannedPort());
	mailbag.setLatestStatus(mailbagsvo.getLatestStatus());
	mailbagsvo.setScannedUser(mailbagsvo.getScannedUser());
	mailbag.setOperationalStatus(mailbagsvo.getOperationalStatus());
	mailbag.setDamageFlag(mailbagsvo.getDamageFlag());
	mailbag.setConsignmentNumber(mailbagsvo.getConsignmentNumber());
	mailbag.setConsignmentSequenceNumber(mailbagsvo.getConsignmentSequenceNumber());
	mailbag.setPaCode(mailbagsvo.getPaCode());
	mailbag.setLastUpdateUser(mailbagsvo.getLastUpdateUser());
	mailbag.setMailStatus(mailbagsvo.getMailStatus());
	mailbag.setLatestStatus(mailbagsvo.getLatestStatus());
	LocalDate latestScanDate = null;
	if (mailbagsvo.getLatestScannedDate() != null) {

		//latestScanDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
		//latestScanDate.setDate(mailbagDetail.getLatestScannedDate());
		mailbag.setLatestScannedDate(mailbagsvo.getLatestScannedDate().toDisplayDateOnlyFormat());
	}

	mailbag.setDisplayUnit(mailbagsvo.getDisplayUnit());
	mailbag.setMailRemarks(mailbagsvo.getMailRemarks());
	mailbag.setCarrierCode(mailbagsvo.getCarrierCode());
	mailbag.setPou(mailbagsvo.getPou());
	if(null!=mailbagsvo.getWeight()){
		mailbag.setMailbagWeight(Double.toString(mailbagsvo.getWeight().getRoundedDisplayValue()));
		mailbag.setWeight(mailbagsvo.getWeight());
	}
	if(null!=mailbagsvo.getVolume()){
		mailbag.setMailbagVolume(Double.toString(mailbagsvo.getVolume().getRoundedDisplayValue()));//added by A-8353
		mailbag.setVolume(mailbagsvo.getVolume());
	}
	if(null!=mailbagsvo.getTransferFromCarrier()){
		mailbag.setTransferFromCarrier(mailbagsvo.getTransferFromCarrier());
	}
	//Added by A-8893 for ICRD-338285
	if(null!=mailbagsvo.getCarditPresent()){
		mailbag.setCarditPresent(mailbagsvo.getCarditPresent());
	}
	if(null!=mailbagsvo.getMraStatus()){
		mailbag.setMraStatus(mailbagsvo.getMraStatus());
	}
	if(mailbagsvo.getReqDeliveryTime()!=null) {
	mailbag.setReqDeliveryDateAndTime(mailbagsvo.getReqDeliveryTime().toDisplayFormat(DATE_TIME_FORMAT));
	}
	mailbag.setServicelevel(mailbagsvo.getMailServiceLevel());
	if(mailbagsvo.getTransWindowEndTime()!=null) {
	mailbag.setTransportSrvWindow(mailbagsvo.getTransWindowEndTime().toDisplayFormat(DATE_TIME_FORMAT));
	}
	mailbag.setPaBuiltFlag(mailbagsvo.getPaBuiltFlag());
	mailbag.setAcceptancePostalContainerNumber(mailbagsvo.getAcceptancePostalContainerNumber());
	mailbag.setPaBuiltFlagUpdate(mailbagsvo.isPaBuiltFlagUpdate());
	mailbag.setPaContainerNumberUpdate(mailbagsvo.isPaContainerNumberUpdate());
	mailbag.setAcceptancePostalAirportCode(mailbagsvo.getAcceptanceAirportCode());
	mailbag.setBellyCartId(mailbagsvo.getBellyCartId());
	mailbag.setRoutingAvlFlag(mailbagsvo.getRoutingAvlFlag());
	//mailbagVO.setWeight(mailbagDetail.getWeight());
	//mailbagVO.setVolume(mailbagDetail.getVolume());

	/*damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();

	if (mailbagDetail.getDamagedMailbags() != null && mailbagDetail.getDamagedMailbags().size() > 0) {
		
		damagedMailbagVOs = constructDamagedMailbagVOs(mailbagDetail.getDamagedMailbags(), logonAttributes);
		mailbagVO.setDamagedMailbags(damagedMailbagVOs);
	}*/

	
//	
//	mailbag.setDamageFlag(mailbags.getDamageFlag()!=null?mailbags.getDamageFlag():"");
//	mailbag.setDocumentNumber(mailbags.getDocumentNumber()!=null?mailbags.getDocumentNumber():"");
//	mailbag.setShipmentPrefix(mailbags.getShipmentPrefix()!=null?mailbags.getShipmentPrefix():"");
//	mailbag.setPaCode(mailbags.getPaCode()!=null?mailbags.getPaCode():"");
	return mailbag;
}

public static DespatchDetails constructDSNDetails(DSNVO dsnsvo, ContainerDetailsVO containerVO) {
	DespatchDetails dsn = new DespatchDetails();
	List<Mailbag> localmailbagList = new ArrayList<Mailbag>();
	dsn.setDsn(dsnsvo.getDsn());
	dsn.setOriginExchangeOffice(dsnsvo.getOriginExchangeOffice());
	dsn.setDestinationExchangeOffice(dsnsvo.getDestinationExchangeOffice());
	dsn.setMailClass(dsnsvo.getMailClass());
	dsn.setYear(dsnsvo.getYear());
	dsn.setMailCategoryCode(dsnsvo.getMailCategoryCode());
	dsn.setMailSubclass(dsnsvo.getMailSubclass());
	dsn.setBags(dsnsvo.getBags());
	dsn.setWeight(dsnsvo.getWeight());
	dsn.setShipmentCount(dsnsvo.getShipmentCount());
	dsn.setShipmentWeight(dsnsvo.getShipmentWeight());
	dsn.setShipmentVolume(dsnsvo.getShipmentVolume());
	dsn.setPrevBagCount(dsnsvo.getPrevBagCount());
    dsn.setPrevBagWeight(dsnsvo.getPrevBagWeight());
    dsn.setStatedBags(dsnsvo.getStatedBags());
    dsn.setPrevStatedWeight(dsnsvo.getPrevStatedWeight());
    dsn.setPrevStatedBags(dsnsvo.getPrevStatedBags());
	dsn.setPrevStatedBags(dsnsvo.getPrevStatedBags());
	if(dsnsvo.getMailbags()!=null && dsnsvo.getMailbags().size()>0) {
		Iterator localIterator = dsnsvo.getMailbags().iterator();
        while (localIterator.hasNext()) {
  	    Mailbag mailbag= null; 
  	    MailbagVO mailbagVO= (MailbagVO)localIterator.next();
  	    mailbag=constructMailbagDetails(mailbagVO,containerVO);
  	    localmailbagList.add(mailbag);
        }
     } 
	dsn.setMailbags(localmailbagList);
	//dsn.setOperationFlag(dsnvo.getO)
	dsn.setContainerType(dsnsvo.getContainerType());
	dsn.setAcceptanceFlag(dsnsvo.getAcceptanceFlag());
	dsn.setPou(dsnsvo.getPou());
	dsn.setDestination(dsnsvo.getDestination());
	dsn.setCarrierCode(dsnsvo.getCarrierCode());
	dsn.setDeliveredBags(dsnsvo.getDeliveredBags());
	dsn.setDeliveredWeight(dsnsvo.getDeliveredWeight());
	dsn.setReceivedBags(dsnsvo.getReceivedBags());
	//dsn.setPrevDeliveredBags(dsnvo.getPre)
	//dsn.setPrevReceivedWeight(dsnvo.getPre)
	dsn.setDocumentOwnerIdentifier(dsnsvo.getDocumentOwnerIdentifier());
	dsn.setMasterDocumentNumber(dsnsvo.getMasterDocumentNumber());
	dsn.setDuplicateNumber(dsnsvo.getDuplicateNumber());
	dsn.setSequenceNumber(dsnsvo.getSequenceNumber());
	dsn.setDocumentOwnerCode(dsnsvo.getDocumentOwnerCode());
	dsn.setShipmentCode(dsnsvo.getShipmentCode());
	dsn.setShipmentDescription(dsnsvo.getShipmentDescription());
	dsn.setOrigin(dsnsvo.getOrigin());
	dsn.setTransferFlag(dsnsvo.getTransferFlag());
	//dsn.setDsnUldSegLastUpdateTime(dsnsvo.gets)
	dsn.setContainerNumber(dsnsvo.getContainerNumber());
	dsn.setRoutingAvl(dsnsvo.getRoutingAvl());
	dsn.setPol(dsnsvo.getPol());
	dsn.setCarrierId(dsnsvo.getCarrierId());
	dsn.setFlightNumber(dsnsvo.getFlightNumber());
	dsn.setFlightSequenceNumber(dsnsvo.getFlightSequenceNumber());
	dsn.setLegSerialNumber(dsnsvo.getLegSerialNumber());
	dsn.setMailrate(dsnsvo.getMailrate());
	dsn.setCurrencyCode(dsnsvo.getCurrencyCode());
	dsn.setChargeInBase(dsnsvo.getChargeInBase());
	dsn.setChargeInUSD(dsnsvo.getChargeInUSD());
	dsn.setUbrNumber(dsnsvo.getUbrNumber());
	dsn.setBookingFlightDetailLastUpdTime(dsnsvo.getBookingFlightDetailLastUpdTime());
	dsn.setBookingLastUpdateTime(dsnsvo.getBookingLastUpdateTime());
    dsn.setAcceptedVolume(dsnsvo.getAcceptedVolume());
    dsn.setStatedVolume(dsnsvo.getStatedVolume());
    dsn.setCsgDocNum(dsnsvo.getCsgDocNum());
    dsn.setPaCode(dsnsvo.getPaCode());
    dsn.setCsgSeqNum(dsnsvo.getCsgSeqNum());
    dsn.setContainerNumber(dsnsvo.getContainerNumber());
    dsn.setConsignmentDate(dsnsvo.getConsignmentDate());
    dsn.setAcceptedDate(dsnsvo.getAcceptedDate());
    dsn.setReceivedDate(dsnsvo.getReceivedDate());
    dsn.setCurrentPort(dsnsvo.getCurrentPort());
    dsn.setTransferredPieces(dsnsvo.getTransferredPieces());
    dsn.setTransferredWeight(dsnsvo.getTransferredWeight());
    dsn.setRemarks(dsnsvo.getRemarks());
    dsn.setMailSequenceNumber(dsnsvo.getMailSequenceNumber());
    return dsn;
}
public static OperationalFlightVO constructOperationalFlightVO(MailAcceptance mailflight, LogonAttributes logonAttribute) throws BusinessDelegateException{

	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	operationalFlightVO.setCarrierCode(mailflight.getCarrierCode());
	operationalFlightVO.setCarrierId(mailflight.getCarrierId());
	operationalFlightVO.setCompanyCode(mailflight.getCompanyCode());
	operationalFlightVO.setContainerNumber(mailflight.getContainerNumber());
	
	//flightFilterVO.setStringFlightDate(carditEnquiryForm.getFlightDat());
	if(mailflight.getFlightDate()!=null){
	LocalDate date = new LocalDate(mailflight.getUpliftAirport(),Location.ARP,false);
	operationalFlightVO.setFlightDate(date.setDate(mailflight.getFlightDate()));
	}
	if(mailflight.getFlightNumber()!=null){
		operationalFlightVO.setFlightNumber(mailflight.getFlightNumber());
	}
	else{
		operationalFlightVO.setFlightNumber("-1");
	}
	
	
	if(mailflight.getFlightSequenceNumber()>0) {
    operationalFlightVO.setFlightSequenceNumber(mailflight.getFlightSequenceNumber());
	operationalFlightVO.setLegSerialNumber((int)mailflight.getLegSerialNumber());
	}
	else {
		 operationalFlightVO.setFlightSequenceNumber(-1);
		 operationalFlightVO.setLegSerialNumber(-1);
    }
	operationalFlightVO.setRecordsPerPage(100);
	//operationalFlightVO.setAirportCode(logonAttribute.getAirportCode());
	operationalFlightVO.setAirportCode(mailflight.getPol());
	//operationalFlightVO.setPol(logonAttribute.getAirportCode());
	operationalFlightVO.setPol(mailflight.getUpliftAirport());
	if(mailflight.getFlightRoute()!=null){
    operationalFlightVO.setFlightRoute(mailflight.getFlightRoute());
	}
	if(mailflight.getDestination()!=null){
		operationalFlightVO.setPou(mailflight.getDestination());
	}
	operationalFlightVO.setDirection(OUTBOUND);
	operationalFlightVO.setFlightType(mailflight.getFlightType());
	return operationalFlightVO;
}


public static ContainerDetailsVO constructContainerDetailVO(ContainerDetails container) {
	ContainerDetailsVO containervo = new ContainerDetailsVO();
	containervo.setCompanyCode(container.getCompanyCode());
	containervo.setFlightNumber(container.getFlightNumber());
	containervo.setCarrierId(container.getCarrierId());
	containervo.setContainerNumber(container.getContainerNumber());
	containervo.setCarrierCode(container.getCarrierCode());
	containervo.setContainerType(container.getType());
	//containervo.setContainergroup(containervo.getContainerGroup());
	containervo.setContainercount(container.getContainercount());
	containervo.setMailbagcount(container.getMailbagcount());
	//containervo.setMailbagweight(containervo.getMailbagwt());
	containervo.setTotalBags(container.getTotalBags());
	containervo.setTotalWeight(container.getTotalWeight());
	containervo.setPol(container.getPol());
	containervo.setPou(container.getPou());
	containervo.setFlightSequenceNumber(container.getFlightSequenceNumber());
	containervo.setLegSerialNumber(container.getLegSerialNumber());
	containervo.setSegmentSerialNumber(container.getSegmentSerialNumber());
	containervo.setDestination(container.getDestination());
	containervo.setTotalRecordSize(100);
	containervo.setAssignedPort(container.getAssignedPort());
	return containervo;
}





public  static Preadvice constructPreadvice (PreAdviceVO preadvicevo){
	Preadvice preadvice = new Preadvice();
	preadvice.setCarrierCode(preadvicevo.getCarrierCode());
	preadvice.setCarrierId(preadvicevo.getCarrierId());
	preadvice.setCompanyCode(preadvicevo.getCompanyCode());
	preadvice.setFlightDate(preadvicevo.getFlightDate());
	preadvice.setFlightNumber(preadvicevo.getFlightNumber());
	preadvice.setTotalBags(preadvicevo.getTotalBags());
	preadvice.setTotalWeight(preadvicevo.getTotalWeight());
	Collection<PreAdviceDetailsVO> preadvicedetails = preadvicevo.getPreAdviceDetails();
	List<PreadviceDetails> localArrayList = new ArrayList<PreadviceDetails>();
	 if ((preadvicedetails != null) && (preadvicedetails.size() > 0))
	    {
		  PreadviceDetails preadv= null;
		  Iterator localIterator = preadvicedetails.iterator();
	      while (localIterator.hasNext()) {
	    	 preadv= new PreadviceDetails();
	    	 PreAdviceDetailsVO preadvicedetailsvo= (PreAdviceDetailsVO)localIterator.next();
	    	 preadv.setDestinationExchangeOffice(preadvicedetailsvo.getDestinationExchangeOffice());
	    	 preadv.setMailCategory(preadvicedetailsvo.getMailCategory());
	    	 preadv.setOriginExchangeOffice(preadvicedetailsvo.getOriginExchangeOffice());
	    	 preadv.setTotalbags(preadvicedetailsvo.getTotalbags());
	    	 preadv.setTotalWeight(preadvicedetailsvo.getTotalWeight());
	    	 preadv.setUldNumbr(preadvicedetailsvo.getUldNumbr());//added by A-8353 for ICRD-346855  
	         localArrayList.add(preadv);
	      }
	      preadvice.setPreadviceDetails(localArrayList);
	    }

		return preadvice;
}


public static List<MailAcceptance> constructMailCarriers(Collection<MailAcceptanceVO> mailFlights){
	List<MailAcceptance> localMailList = new ArrayList<MailAcceptance>();
		
	 if ((mailFlights != null) && (mailFlights.size() > 0))
	    {
	      Iterator localIterator = mailFlights.iterator();
	      while (localIterator.hasNext()) {
	    int totalULDs=0;
	    int totalbulks=0;
		MailAcceptance mailflight = new MailAcceptance();
		MailAcceptanceVO mailacceptancevo= (MailAcceptanceVO)localIterator.next();
		mailflight.setCompanyCode(mailacceptancevo.getCompanyCode());
		mailflight.setCarrierCode(mailacceptancevo.getCarrierCode());
		mailflight.setFlightCarrierCode(mailacceptancevo.getCarrierCode());
		mailflight.setCarrierId(mailacceptancevo.getCarrierId());
        mailflight.setDestination(mailacceptancevo.getDestination());	
        mailflight.setUpliftAirport(mailacceptancevo.getPol());
        mailflight.setFlightSequenceNumber(-1);
        mailflight.setSegmentSerialNumber(-1);
        mailflight.setLegSerialNumber(-1);
        mailflight.setFlightNumber("-1");
        mailflight.setPol(mailacceptancevo.getPol());
		if(mailacceptancevo.getContainerDetails()!=null) {
			List<ContainerDetails> containerlist = new ArrayList<ContainerDetails>();
			mailflight=constructContainerSummaryDetails(mailflight,(ArrayList)mailacceptancevo.getContainerDetails());
			//mailflight.setContainerDetails(containerlist);

		}
		    MailAcceptancePK carrierpk= new MailAcceptancePK();
		    carrierpk.setCompanyCode(mailacceptancevo.getCompanyCode());
		  //  carrierpk.setAirportCode("FRA");
		    carrierpk.setFlightCarrierId(mailacceptancevo.getCarrierId());
		    carrierpk.setDestination(mailacceptancevo.getDestination());
	        mailflight.setCarrierpk(carrierpk);
		localMailList.add(mailflight);
	   }
	 }
	 return localMailList;
}






public static MailAcceptanceVO constructMailAcceptanceVO(MailAcceptance mailFlight, LogonAttributes logonAttribute){
	

	

	//log.entering("makeMailAcceptanceVO", "execute");
	Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
	//String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO
		//	.getMailSource()) ||  MailConstantsVO.WS.equals(scannedMailDetailsVO
			//		.getMailSource()))? outboundmodel.getAirportCode()
		//	: logonAttributes.getAirportCode();
	String airportCode = logonAttribute.getAirportCode();
	MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
	mailAcceptanceVO.setCompanyCode(mailFlight.getCompanyCode());
	mailAcceptanceVO.setCarrierId(mailFlight.getCarrierId());
	mailAcceptanceVO.setOwnAirlineCode(logonAttribute.getOwnAirlineCode());
	mailAcceptanceVO.setOwnAirlineId(logonAttribute
			.getOwnAirlineIdentifier());
	mailAcceptanceVO.setFlightCarrierCode(mailFlight
			.getCarrierCode());
	
	if ((mailFlight.getFlightDate()!= null) && (mailFlight.getFlightDate().trim().length() > 0))
    {
      LocalDate flightDate = new LocalDate("***", Location.NONE, false);
      flightDate.setDate(mailFlight.getFlightDate());
      mailAcceptanceVO.setFlightDate(flightDate);
    }
	mailAcceptanceVO.setAcceptedUser(logonAttribute.getUserId()
			.toUpperCase());
	mailAcceptanceVO.setFlightSequenceNumber(mailFlight
			.getFlightSequenceNumber());
	mailAcceptanceVO
			.setFlightNumber(mailFlight.getFlightNumber());
	mailAcceptanceVO.setLegSerialNumber(mailFlight
			.getLegSerialNumber());
	mailAcceptanceVO.setFlightType(mailFlight.getFlightType());
	mailAcceptanceVO.setPol(mailFlight.getPol());
	mailAcceptanceVO.setScanned(true);
	mailAcceptanceVO.setPreassignNeeded(false);
	mailAcceptanceVO.setMailSource(MailConstantsVO.MAIL_OUTBOUND_SCRIDR);
	mailAcceptanceVO.setDepartureGate(mailFlight.getDepartureGate());
	mailAcceptanceVO.setFromDeviationList(mailFlight.isFromDeviationList());
	mailAcceptanceVO.setPopupAction(mailFlight.getPopupAction());
	mailAcceptanceVO.setShowWarning(mailFlight.getShowWarning());
	containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
	//ContainerDetailsVO containerDetailsVO = scannedMailDetailsVO
	//		.getValidatedContainer();
	if(mailFlight.getContainer()!=null) {
	//if (containerDetailsVO == null) {
	ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setSegmentSerialNumber(mailFlight.getSegmentSerialNumber());
//	}
	//Added for icrd-127414
	if(logonAttribute != null && logonAttribute.getDefaultWarehouseCode() != null){
	 containerDetailsVO.setWareHouse(logonAttribute.getDefaultWarehouseCode());
	}
	containerDetailsVO
			.setCompanyCode(mailFlight.getCompanyCode());
	containerDetailsVO.setContainerNumber(mailFlight.getContainer().getContainerNumber());
	containerDetailsVO.setFlightNumber(mailFlight.getFlightNumber());
	LocalDate flightDate = new LocalDate("***", Location.NONE, false);
    flightDate.setDate(mailFlight.getFlightDate().toUpperCase());
    containerDetailsVO.setFlightDate(flightDate);
  
	containerDetailsVO.setFlightSequenceNumber(mailFlight.getFlightSequenceNumber());
	containerDetailsVO.setOwnAirlineCode(logonAttribute.getOwnAirlineCode());
	containerDetailsVO.setAcceptedFlag(mailFlight.getContainer().getAcceptedFlag());
		
	containerDetailsVO.setDestination(mailFlight.getContainer().getDestination());
	containerDetailsVO.setPol(mailFlight.getContainer().getPol());
	containerDetailsVO.setPou(mailFlight.getContainer().getPou());

	if (mailFlight.getContainer().getRemarks() != null
			&& mailFlight.getContainer().getRemarks().trim().length() > 0) {
		containerDetailsVO.setRemarks(mailFlight.getContainer().getRemarks());
	}
	containerDetailsVO.setAssignmentDate(new LocalDate(airportCode, ARP,true));
	//Added for ICRD-122072 by A-4810
	//if(scannedMailDetailsVO.getScannedUser() != null) {
	//containerDetailsVO.setAssignedUser(scannedMailDetailsVO.getScannedUser());
	//}
	//Added for ICRD-122072 by A-4810 ends
	containerDetailsVO.setCarrierId(mailFlight.getCarrierId());
	containerDetailsVO.setCarrierCode(mailFlight.getCarrierCode());
	containerDetailsVO.setContainerType(mailFlight.getContainer().getType());
	containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
//	if (scannedMailDetailsVO.getContainerProcessPoint()!= null &&
//			MailConstantsVO.CONTAINER_STATUS_PREASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
		containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
		containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
	//}
//	else{    
//		
//		containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
//		containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
//	}
	containerDetailsVO.setSegmentSerialNumber(mailFlight.getContainer().getSegmentSerialNumber());
	containerDetailsVOs.add(containerDetailsVO);
	/*if (scannedMailDetailsVO.getNewContainer() != null
			&& scannedMailDetailsVO.getNewContainer().trim().length() > 0) {
		containerDetailsVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
	}
	ContainerVO containerVO = new ContainerVO();
	containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
	containerVO.setContainerNumber(scannedMailDetailsVO
			.getContainerNumber());
	containerVO.setAssignedPort(scannedMailDetailsVO.getPol());


	ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
	ULDForSegment uLDForSegment = null;
	uLDForSegmentPK.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
	uLDForSegmentPK.setCarrierId(scannedMailDetailsVO.getCarrierId());
	uLDForSegmentPK.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
	uLDForSegmentPK.setFlightSequenceNumber(scannedMailDetailsVO
			.getFlightSequenceNumber());
	uLDForSegmentPK.setSegmentSerialNumber(scannedMailDetailsVO
			.getSegmentSerialNumber());
	uLDForSegmentPK.setUldNumber(scannedMailDetailsVO.getContainerNumber());

	try {
		uLDForSegment = ULDForSegment.find(uLDForSegmentPK);
	} catch (FinderException e1) {
		uLDForSegment = null;
	} catch (SystemException e1) {
		log.log(Log.SEVERE, "SystemException caught");
		uLDForSegment = null;
	}
	catch(Exception e){
		log.log(Log.SEVERE, "Exception caught");
		constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
	}

	if (containerAssignmentVO != null && uLDForSegment == null) {
		ULDAtAirportPK uLDAtAirportPK = new ULDAtAirportPK();
		uLDAtAirportPK.setAirportCode(containerAssignmentVO
				.getAirportCode());
		uLDAtAirportPK.setCarrierId(scannedMailDetailsVO.getCarrierId());
		uLDAtAirportPK
				.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		uLDAtAirportPK.setUldNumber(scannedMailDetailsVO
				.getContainerNumber());
		ULDAtAirport uLDAtAirport = null;

		try {
			uLDAtAirport = ULDAtAirport.find(uLDAtAirportPK);
		} catch (FinderException e) {
			uLDAtAirport = null;
		} catch (SystemException e) {
			log.log(Log.SEVERE, "SystemException caught");
			uLDAtAirport = null;
		}
		catch(Exception e){
			log.log(Log.SEVERE, "Exception caught");
		}
		containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);

		if (containerAssignmentVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT
				&& uLDAtAirport != null) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
		}
	} else if (containerAssignmentVO != null && uLDForSegment != null) {
		containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
		containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
	}
	if(containerAssignmentVO!=null && 
		(containerAssignmentVO.getArrivalFlag()!=null && MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))){
		containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
		containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);            
	}
		//Added by A-5945 for ICRD-125505. This condition will check those ULDs which are not arrived but acquited using acquit ULD button in import manifest screen
	if(containerAssignmentVO!=null&&(containerAssignmentVO.getArrivalFlag()!=null && MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag())) &&
			(containerAssignmentVO.getTransitFlag()!=null && MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getTransitFlag())) &&
			MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
			){
		containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
		containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);  
	}
	if(containerAssignmentVO!=null && 
			(containerAssignmentVO.getTransitFlag()!=null && MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getTransitFlag()))&&
			(MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType()) || MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType()))){
		containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
		containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);            
		if ((containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber()) && 
				 containerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO.getFlightSequenceNumber()&&
				 containerAssignmentVO.getAirportCode().equals(scannedMailDetailsVO.getAirportCode()))){
			containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);         
		}
		        
	}
	//added by a-7779 for ICRD-192536 starts
	if("EXPFLTFIN_ACPMAL".equals(scannedMailDetailsVO.getMailSource())){
		containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
		containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
	}
	//added by a-7779 for ICRD-192536 ends
	//Added as prt of bug ICRD-144132 by A-5526 starts
	containerDetailsVO.setTransferFromCarrier(scannedMailDetailsVO.getTransferFromCarrier());*/
	//Added as prt of bug ICRD-144132 by A-5526 ends
	
	/*Collection<MailbagVO> mailVOs = scannedMailDetailsVO.getMailDetails();
	OfficeOfExchangeVO officeOfExchangeVO = null;

	if (mailVOs != null && mailVOs.size() > 0
			&& (!scannedMailDetailsVO.isAcknowledged())) {

		for (MailbagVO mailbagVO : mailVOs) {
			mailbagVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
			mailbagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
			mailbagVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
			mailbagVO.setFlightNumber(scannedMailDetailsVO
					.getFlightNumber());
			mailbagVO.setContainerNumber(scannedMailDetailsVO
					.getContainerNumber());
			mailbagVO.setContainerType(scannedMailDetailsVO
					.getContainerType());
			mailbagVO.setPou(scannedMailDetailsVO.getPou());
			mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,
					1));
			//Commented as part of ICRD-144132 by A-5526 
			containerDetailsVO.setTransferFromCarrier(mailbagVO
					.getTransferFromCarrier());      

			if (mailbagVO.getPaBuiltFlag() != null
					&& mailbagVO.getPaBuiltFlag().trim().length() > 0) {

				if (mailbagVO.getPaBuiltFlag().equals(
						MailConstantsVO.FLAG_YES)) {
					containerDetailsVO.setPaBuiltFlag(mailbagVO
							.getPaBuiltFlag());

					try {
						officeOfExchangeVO = validateOfficeOfExchange(
								scannedMailDetailsVO.getCompanyCode(),
								mailbagVO.getOoe());
					} catch (SystemException e) {
						log.log(Log.SEVERE, "SystemException caught");
                         constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
					}
					catch(Exception e){
						log.log(Log.SEVERE, "SystemException caught");
						constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
					}

					if (officeOfExchangeVO != null) {
						containerDetailsVO.setPaCode(officeOfExchangeVO
								.getPoaCode());
					}
				}
			}

			double actualVolume = 0.0;
			String commodityCode = "";
			CommodityValidationVO commodityValidationVO = null;

			try {
				commodityCode = findSystemParameterValue(DEFAULTCOMMODITYCODE_SYSPARAM);
				commodityValidationVO = validateCommodity(
						scannedMailDetailsVO.getCompanyCode(),
						commodityCode);
			} catch (SystemException e) {
				log.log(Log.SEVERE, "SystemException caught");
				log.log(Log.INFO, e.getMessage());
			}
			catch(Exception e){
				log.log(Log.SEVERE, "SystemException caught");
				constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			}

			if (commodityValidationVO != null
					&& commodityValidationVO.getDensityFactor() != 0) {
				//mailbag weight is in KG,hence no need of converting to Hg
				actualVolume = (mailbagVO.getWeight().getSystemValue() )
						/ (commodityValidationVO.getDensityFactor());//modified by A-7371

				if (actualVolume < 0.01) {
					actualVolume = 0.01;
				}

			}
			//mailbagVO.setVolume(actualVolume);
			mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,actualVolume));//added by A-7371
			mailbagVO.setTransferFromCarrier(mailbagVO
					.getTransferFromCarrier());
			mailbagVO
					.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			mailbagVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
			mailbagVO.setFlightSequenceNumber(scannedMailDetailsVO
					.getFlightSequenceNumber());
			mailbagVO.setSegmentSerialNumber(scannedMailDetailsVO
					.getSegmentSerialNumber());
			mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			mailbagVO.setUldNumber(scannedMailDetailsVO
					.getContainerNumber());
			mailbagVO.setScannedPort(scannedMailDetailsVO.getPol());
			if (mailbagVO.getScannedUser() == null) {
				mailbagVO.setScannedUser(logonAttributes.getUserId()
						.toUpperCase());
			}
			mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
			mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_NO);
			mailbagVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
			mailbagVO
					.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
			mailAcceptanceVO
					.setDuplicateMailOverride(MailConstantsVO.FLAG_NO);

			if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO
					.getDamageFlag())) {
				mailbagVO.setDamageFlag(MailConstantsVO.FLAG_YES);
			} else {
				mailbagVO.setDamageFlag(MailConstantsVO.FLAG_NO);
			}

			if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO
					.getReassignFlag())) {
				mailAcceptanceVO
						.setDuplicateMailOverride(MailConstantsVO.FLAG_YES);
				mailbagVO
						.setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
			}
		}
	} else if (scannedMailDetailsVO.isAcknowledged()) {
		mailAcceptanceVO.setDuplicateMailOverride(MailConstantsVO.FLAG_YES);
	}
	containerDetailsVO.setMailDetails(mailVOs);
	HashMap<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();

	if (mailVOs != null && mailVOs.size() > 0) {

		for (MailbagVO mailbagVO : mailVOs) {
			int numBags = 0;
			double bagWgt = 0;

			if (Integer.parseInt(mailbagVO.getDespatchSerialNumber()) != 0) {
				String outerpk = mailbagVO.getOoe() + mailbagVO.getDoe()
						+ mailbagVO.getMailSubclass()
						+ mailbagVO.getMailCategoryCode()
						+ mailbagVO.getDespatchSerialNumber()
						+ mailbagVO.getYear();

				if (dsnMap.get(outerpk) == null) {
					DSNVO dsnVO = new DSNVO();
					dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
					dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
					dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
					dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
					dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
					dsnVO.setMailCategoryCode(mailbagVO
							.getMailCategoryCode());
					dsnVO.setMailClass(mailbagVO.getMailSubclass()
							.substring(0, 1));
					dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					dsnVO.setYear(mailbagVO.getYear());
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
					dsnVO.setFlightSequenceNumber(mailbagVO
							.getFlightSequenceNumber());
					dsnVO.setLegSerialNumber(mailbagVO.getLegSerialNumber());
					dsnVO.setCarrierId(mailbagVO.getCarrierId());

					for (MailbagVO innerVO : mailVOs) {
						String innerpk = innerVO.getOoe()
								+ innerVO.getDoe()
								+ innerVO.getMailSubclass()
								+ innerVO.getMailCategoryCode()
								+ innerVO.getDespatchSerialNumber()
								+ innerVO.getYear();

						if (outerpk.equals(innerpk)) {
							numBags = numBags + 1;
							bagWgt = bagWgt + innerVO.getWeight().getSystemValue();//added by A-7371
						}
					}

					if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO
							.getReassignFlag())) {
						// no: of bags and weight for reassignment will be
						// populated in controller
						numBags = numBags - 1;
						bagWgt = bagWgt - mailbagVO.getWeight().getSystemValue();//added by A-7371
					}
					dsnVO.setBags(numBags);
					//dsnVO.setWeight(bagWgt);
					dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
					dsnMap.put(outerpk, dsnVO);
					numBags = 0;
					bagWgt = 0;
				}
			}
		}
	}
	Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
	int totBags = 0;
	double totWgt = 0;

	for (String key : dsnMap.keySet()) {
		DSNVO dsnVO = dsnMap.get(key);
		totBags = totBags + dsnVO.getBags();
		//totWgt = totWgt + dsnVO.getWeight();
		totWgt = totWgt + dsnVO.getWeight().getRoundedSystemValue();//added by A-7371
		newDSNVOs.add(dsnVO);
	}
	containerDetailsVO.setDsnVOs(newDSNVOs);
	containerDetailsVO.setTotalBags(totBags);
	//containerDetailsVO.setTotalWeight(totWgt);
	containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,totWgt));//added by A-7371s
	//Null check Added for ICRD-122072 by A-4810
	if(containerDetailsVO.getAssignedUser() == null){
	containerDetailsVO.setAssignedUser(logonAttributes.getUserId()
			.toUpperCase());
	}*/
	containerDetailsVOs.add(containerDetailsVO);
	mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
	}
   //log.exiting("makeMailAcceptanceVO", "execute");
	return mailAcceptanceVO;

	

}

public static ContainerDetailsVO constructContainerDetailsVO(ContainerDetails containerDetails, LogonAttributes logonAttributes) {
	
	ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
	containerDetailsVO.setCarrierCode(containerDetails.getCarrierCode());
	containerDetailsVO.setCarrierId(containerDetails.getCarrierId());
	containerDetailsVO.setCompanyCode(containerDetails.getCompanyCode());
	containerDetailsVO.setCarrierCode(containerDetails.getCarrierCode());
	if (containerDetails.getFlightDate() != null) {
	LocalDate flightDate = new LocalDate("***", Location.NONE, false);
    flightDate.setDate(containerDetails.getFlightDate());
    containerDetailsVO.setFlightDate(flightDate);
	}
	containerDetailsVO.setFlightNumber(containerDetails.getFlightNumber());
    containerDetailsVO.setContainerNumber(containerDetails.getContainerNumber());
    containerDetailsVO.setPol(containerDetails.getPol());
    containerDetailsVO.setPou(containerDetails.getPou());
    containerDetailsVO.setAcceptedFlag(containerDetails.getAcceptedFlag());
    containerDetailsVO.setArrivedStatus(containerDetails.getArrivedStatus());
    containerDetailsVO.setFlightSequenceNumber(containerDetails.getFlightSequenceNumber());
    containerDetailsVO.setSegmentSerialNumber(containerDetails.getSegmentSerialNumber());
    containerDetailsVO.setLegSerialNumber(containerDetails.getLegSerialNumber());
    containerDetailsVO.setContainerType(containerDetails.getType());
    containerDetailsVO.setTotalBags(containerDetails.getTotalBags());
    containerDetailsVO.setTotalWeight(containerDetails.getTotalWeight());
    containerDetailsVO.setOperationFlag(containerDetails.getOperationFlag());
    containerDetailsVO.setContainerOperationFlag(containerDetails.getContainerOperationFlag());
    containerDetailsVO.setDestination(containerDetails.getDestination());
    containerDetailsVO.setPaBuiltFlag(containerDetails.getPaBuiltFlag());
    containerDetailsVO.setContainerJnyId(containerDetails.getContainerJnyId());
    containerDetailsVO.setPaCode(containerDetails.getPaCode());
    containerDetailsVO.setPreassignFlag(containerDetails.isPreassignFlag());
    containerDetailsVO.setAssignmentDate(containerDetails.getAssignmentDate());
    containerDetailsVO.setAssignedUser(containerDetails.getAssignedUser());
    containerDetailsVO.setDestination(containerDetails.getDestination());
    containerDetailsVO.setLocation(containerDetails.getLocation());
    containerDetailsVO.setTransferFromCarrier(containerDetails.getTransferFromCarrier());
    containerDetailsVO.setBellyCartId(containerDetails.getBellyCarditId());
    containerDetailsVO.setWareHouse(containerDetails.getWareHouse());
    containerDetailsVO.setOnwardFlights(containerDetails.getOnwardFlights());
    containerDetailsVO.setRemarks(containerDetails.getRemarks());
    containerDetailsVO.setContainerOperationFlag(containerDetails.getContainerOperationFlag());
    containerDetailsVO.setMailUpdateFlag(containerDetails.isMailUpdateFlag());
    if ((containerDetails.getMailBagDetailsCollection() != null) && (containerDetails.getMailBagDetailsCollection().size() > 0))
    {  
    	List<MailbagVO> localmailbagList = new ArrayList<MailbagVO>();
        Iterator localIterator = containerDetails.getMailBagDetailsCollection().iterator();
    	 while (localIterator.hasNext()) {
    	    	    MailbagVO mailbagvo= null; 
    	    	    Mailbag mailbag = (Mailbag)localIterator.next();
    	    	    mailbagvo=constructMailbagVO(mailbag,logonAttributes);
    	    	    localmailbagList.add(mailbagvo);
    	   }
    	 containerDetailsVO.setMailDetails(localmailbagList);
    }
    if(containerDetails.getDesptachDetailsCollection()!=null) {
	  List<DSNVO> despatchVos = constructDSNVOs(containerDetails.getDesptachDetailsCollection());
	  containerDetailsVO.setDsnVOs(despatchVos);
    }
	containerDetailsVO.setUldFulIndFlag(containerDetails.getUldFulIndFlag()); 
    containerDetailsVO.setFromContainerTab(containerDetails.isFromContainerTab());
    return containerDetailsVO;
	
}

public static MailbagVO constructMailbagVO(Mailbag mailbagDetail,LogonAttributes logonAttributes) {
	
	MailbagVO mailbagVO = new MailbagVO();
			
	mailbagVO.setCompanyCode(mailbagDetail.getCompanyCode());
	mailbagVO.setMailbagId(mailbagDetail.getMailbagId());
	mailbagVO.setMailSequenceNumber(mailbagDetail.getMailSequenceNumber());
	mailbagVO.setDespatchId(mailbagDetail.getDespatchId());
	mailbagVO.setOoe(mailbagDetail.getOoe());
	mailbagVO.setDoe(mailbagDetail.getDoe());
	mailbagVO.setMailCategoryCode(mailbagDetail.getMailCategoryCode());
	mailbagVO.setMailSubclass(mailbagDetail.getMailSubclass());
	mailbagVO.setMailClass(mailbagDetail.getMailClass());
	mailbagVO.setYear(mailbagDetail.getYear());
	mailbagVO.setDespatchSerialNumber(mailbagDetail.getDespatchSerialNumber());
	mailbagVO.setReceptacleSerialNumber(mailbagDetail.getReceptacleSerialNumber());
	mailbagVO.setRegisteredOrInsuredIndicator(mailbagDetail.getRegisteredOrInsuredIndicator());
	mailbagVO.setHighestNumberedReceptacle(mailbagDetail.getHighestNumberedReceptacle());

	LocalDate consignmentDate = null;
	if (mailbagDetail.getConsignmentDate() != null) {

		consignmentDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
		consignmentDate.setDate(mailbagDetail.getConsignmentDate());
		mailbagVO.setConsignmentDate(consignmentDate);
	}

	mailbagVO.setScannedUser(mailbagDetail.getScannedUser());
	mailbagVO.setCarrierId(mailbagDetail.getCarrierId());
	mailbagVO.setFlightNumber(mailbagDetail.getFlightNumber());
	mailbagVO.setFlightSequenceNumber(mailbagDetail.getFlightSequenceNumber());
	mailbagVO.setSegmentSerialNumber(mailbagDetail.getSegmentSerialNumber());
	mailbagVO.setUldNumber(mailbagDetail.getUldNumber());
	mailbagVO.setDespatch(mailbagDetail.isDespatch());
	/*if (mailbagDetail.getReqDeliveryDate() != null) {
        LocalDate rqdDlvTim=new LocalDate(logonAttributes.getAirportCode(), ARP, false);
	StringBuilder reqDeliveryTime=new StringBuilder(mailbagDetail.getReqDeliveryDate());
	if(mailbagDetail.getReqDeliveryTime()!=null&&
			mailbagDetail.getReqDeliveryTime().trim().length()>0){
			rqdDlvTim.setDateAndTime(reqDeliveryTime.toString());
		}else{
			rqdDlvTim.setDate(reqDeliveryTime.toString());
		} 
		mailbagvo.setReqDeliveryTime(rqdDlvTim);
	}
*/
	mailbagVO.setShipmentPrefix(mailbagDetail.getShipmentPrefix());
	mailbagVO.setDocumentNumber(mailbagDetail.getMasterDocumentNumber());
	mailbagVO.setDocumentOwnerIdr(mailbagDetail.getDocumentOwnerIdr());
	mailbagVO.setDuplicateNumber(mailbagDetail.getDuplicateNumber());
	mailbagVO.setSequenceNumber(mailbagDetail.getSequenceNumber());

	LocalDate flightDate = null;
	if (mailbagDetail.getFlightDate() != null) {

		flightDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
		flightDate.setDate(mailbagDetail.getFlightDate());
		mailbagVO.setFlightDate(flightDate);
	}

	mailbagVO.setOperationalFlag(mailbagDetail.getOperationalFlag());
	mailbagVO.setContainerType(mailbagDetail.getContainerType());
	mailbagVO.setContainerNumber(mailbagDetail.getContainerNumber());
	mailbagVO.setIsoffload(mailbagDetail.isIsoffload());

	LocalDate scanDate = null;
	if (mailbagDetail.getScannedDate() != null
			&& mailbagDetail.getScannedDate().trim().length()>0) {

		scanDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
		if(mailbagDetail.getScannedDate().trim().length()==20){//Added by A-8893 for IASCB-53000
		scanDate.setDateAndTime(mailbagDetail.getScannedDate());
		}else{
		scanDate.setDate(mailbagDetail.getScannedDate().substring(0,11));
		}
		mailbagVO.setScannedDate(scanDate);
	}

	mailbagVO.setScannedPort(mailbagDetail.getScannedPort());
	mailbagVO.setLatestStatus(mailbagDetail.getLatestStatus());
	mailbagVO.setOperationalStatus(mailbagDetail.getOperationalStatus());
	mailbagVO.setDamageFlag(mailbagDetail.getDamageFlag());
	mailbagVO.setConsignmentNumber(mailbagDetail.getConsignmentNumber());
	mailbagVO.setConsignmentSequenceNumber(mailbagDetail.getConsignmentSequenceNumber());
	mailbagVO.setPaCode(mailbagDetail.getPaCode());
	mailbagVO.setLastUpdateUser(mailbagDetail.getLastUpdateUser());
	mailbagVO.setMailStatus(mailbagDetail.getMailStatus());

	LocalDate latestScanDate = null;
	if (mailbagDetail.getLatestScannedDate() != null) {

		latestScanDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
		latestScanDate.setDate(mailbagDetail.getLatestScannedDate());
		mailbagVO.setLatestScannedDate(latestScanDate);
	}

	mailbagVO.setDisplayUnit(mailbagDetail.getDisplayUnit());
	mailbagVO.setMailRemarks(mailbagDetail.getMailRemarks());
	mailbagVO.setCarrierCode(mailbagDetail.getCarrierCode());
	mailbagVO.setPou(mailbagDetail.getPou());
	mailbagVO.setPol(mailbagDetail.getPol());
	mailbagVO.setWeight(mailbagDetail.getWeight());
	mailbagVO.setVolume(mailbagDetail.getVolume());
	mailbagVO.setUpliftAirport(mailbagDetail.getUpliftAirport());
	mailbagVO.setMailOrigin(mailbagDetail.getMailorigin());
	mailbagVO.setMailDestination(mailbagDetail.getMailDestination());
     mailbagVO.setOnTimeDelivery(mailbagDetail.getOnTimeDelivery());
     mailbagVO.setMailServiceLevel(mailbagDetail.getServicelevel());
    mailbagVO.setReassignFlag(mailbagDetail.getReassignFlag());
    mailbagVO.setCarrierCode(mailbagDetail.getCarrierCode());
    mailbagVO.setFromSegmentSerialNumber(mailbagDetail.getSegmentSerialNumber());
    mailbagVO.setLegSerialNumber(mailbagDetail.getLegSerialNumber());
    if(mailbagDetail.getWeight()!=null) {
     Measure actualWeight = new Measure(UnitConstants.MAIL_WGT, 0,mailbagDetail.getActualWeight(),mailbagDetail.getWeight().getDisplayUnit());
     mailbagVO.setActualWeight(actualWeight);
    }
    if(mailbagDetail.getMraStatus()!=null) {
        mailbagVO.setMraStatus(mailbagDetail.getMraStatus());
    }
    return mailbagVO;
	
}

public static List<DSNVO> constructDSNVOs(Collection<DespatchDetails> despatchDetails) {
	List<DSNVO> localDSNVoList = new ArrayList<DSNVO>();
	 if ((despatchDetails != null) && (despatchDetails.size() > 0))
	    {
	      Iterator localIterator = despatchDetails.iterator();
	      while (localIterator.hasNext()) {
	    int totalULDs=0;
	    int totalbulks=0;
	    DSNVO dsnVO = new DSNVO();
	    DespatchDetails despatch= (DespatchDetails)localIterator.next();
	    dsnVO.setDsn(despatch.getDsn());
	    dsnVO.setOriginExchangeOffice(despatch.getOriginExchangeOffice());
	    dsnVO.setDestinationExchangeOffice(despatch.getDestinationExchangeOffice());
	    dsnVO.setMailClass(despatch.getMailClass());
	    dsnVO.setYear(despatch.getYear());
	    dsnVO.setMailCategoryCode(despatch.getMailCategoryCode());
	    dsnVO.setMailSubclass(despatch.getMailSubclass());
	    dsnVO.setBags(despatch.getBags());
	    dsnVO.setWeight(despatch.getWeight());
	    dsnVO.setShipmentCount(despatch.getShipmentCount());
	    dsnVO.setShipmentWeight(despatch.getShipmentWeight());
	    dsnVO.setShipmentVolume(despatch.getShipmentVolume());
	    dsnVO.setPrevBagCount(despatch.getPrevBagCount());
	    dsnVO.setPrevBagWeight(despatch.getPrevBagWeight());
	    dsnVO.setStatedBags(despatch.getStatedBags());
	    dsnVO.setPrevStatedWeight(despatch.getPrevStatedWeight());
	    dsnVO.setPrevStatedBags(despatch.getPrevStatedBags());
	    dsnVO.setPrevStatedBags(despatch.getPrevStatedBags());
	    dsnVO.setContainerType(despatch.getContainerType());
	    dsnVO.setAcceptanceFlag(despatch.getAcceptanceFlag());
	    dsnVO.setPou(despatch.getPou());
	    dsnVO.setDestination(despatch.getDestination());
	    dsnVO.setCarrierCode(despatch.getCarrierCode());
	    dsnVO.setDeliveredBags(despatch.getDeliveredBags());
	    dsnVO.setDeliveredWeight(despatch.getDeliveredWeight());
	    dsnVO.setReceivedBags(despatch.getReceivedBags());
		//dsn.setPrevDeliveredBags(dsnvo.getPre)
		//dsn.setPrevReceivedWeight(dsnvo.getPre)
	    dsnVO.setDocumentOwnerIdentifier(despatch.getDocumentOwnerIdentifier());
	    dsnVO.setMasterDocumentNumber(despatch.getMasterDocumentNumber());
	    dsnVO.setDuplicateNumber(despatch.getDuplicateNumber());
	    dsnVO.setSequenceNumber(despatch.getSequenceNumber());
	    dsnVO.setDocumentOwnerCode(despatch.getDocumentOwnerCode());
	    dsnVO.setShipmentCode(despatch.getShipmentCode());
	    dsnVO.setShipmentDescription(despatch.getShipmentDescription());
	    dsnVO.setOrigin(despatch.getOrigin());
	    dsnVO.setTransferFlag(despatch.getTransferFlag());
		//dsn.setDsnUldSegLastUpdateTime(dsnsvo.gets)
	    dsnVO.setContainerNumber(despatch.getContainerNumber());
	    dsnVO.setRoutingAvl(despatch.getRoutingAvl());
	    dsnVO.setPol(despatch.getPol());
	    dsnVO.setCarrierId(despatch.getCarrierId());
	    dsnVO.setFlightNumber(despatch.getFlightNumber());
	    dsnVO.setFlightSequenceNumber(despatch.getFlightSequenceNumber());
	    dsnVO.setLegSerialNumber(despatch.getLegSerialNumber());
	    dsnVO.setMailrate(despatch.getMailrate());
		dsnVO.setCurrencyCode(despatch.getCurrencyCode());
		dsnVO.setChargeInBase(despatch.getChargeInBase());
		dsnVO.setChargeInUSD(despatch.getChargeInUSD());
		dsnVO.setUbrNumber(despatch.getUbrNumber());
		dsnVO.setBookingFlightDetailLastUpdTime(despatch.getBookingFlightDetailLastUpdTime());
		dsnVO.setBookingLastUpdateTime(despatch.getBookingLastUpdateTime());
		dsnVO.setAcceptedVolume(despatch.getAcceptedVolume());
		dsnVO.setStatedVolume(despatch.getStatedVolume());
		dsnVO.setCsgDocNum(despatch.getCsgDocNum());
	    dsnVO.setPaCode(despatch.getPaCode());
	    dsnVO.setCsgSeqNum(despatch.getCsgSeqNum());
	    dsnVO.setContainerNumber(despatch.getContainerNumber());
	    dsnVO.setConsignmentDate(despatch.getConsignmentDate());
	    dsnVO.setAcceptedDate(despatch.getAcceptedDate());
	    dsnVO.setReceivedDate(despatch.getReceivedDate());
	    dsnVO.setCurrentPort(despatch.getCurrentPort());
	    dsnVO.setTransferredPieces(despatch.getTransferredPieces());
	    dsnVO.setTransferredWeight(despatch.getTransferredWeight());
	    dsnVO.setRemarks(despatch.getRemarks());
	    dsnVO.setMailSequenceNumber(despatch.getMailSequenceNumber());
	    localDSNVoList.add(dsnVO);
	      }
	    }
	 return localDSNVoList;
}
public static List<Warehouse> constructWarehouse(Collection<WarehouseVO> warehouses){
	List<Warehouse> localWarehouseList = new ArrayList<Warehouse>();
		
	 if ((warehouses != null) && (warehouses.size() > 0))
	    {
	      Iterator localIterator = warehouses.iterator();
	      while (localIterator.hasNext()) {
	    int totalULDs=0;
	    int totalbulks=0;
	    Warehouse wareHouse = new Warehouse();
	    WarehouseVO wareHouseVO= (WarehouseVO)localIterator.next();
	    wareHouse.setAirportCode(wareHouseVO.getAirportCode());
	    wareHouse.setCompanyCode(wareHouseVO.getAirportCode());
	    wareHouse.setWarehouseCode(wareHouseVO.getWarehouseCode());
	    wareHouse.setDescription(wareHouseVO.getDescription());
	    localWarehouseList.add(wareHouse);
	      }
	    }
	 return localWarehouseList;
}

public static Mailbag constructCarditSummary(MailbagVO mailbagvo) {
	Mailbag mailbag= new Mailbag();
	mailbag.setCount(mailbagvo.getCount());
	    mailbag.setWeight(mailbagvo.getWeight());
	return mailbag;
	
}

public static List<Mailbag> constructCarditGroupedMailbag(Collection<MailbagVO> mailbags)
{
	List<Mailbag> localArrayList = new ArrayList<Mailbag>();
  Mailbag mailbag= null;
  if ((mailbags != null) && (mailbags.size() > 0))
  {
    Iterator localIterator = mailbags.iterator();
    while (localIterator.hasNext()) {
   // {
  	 mailbag= new Mailbag();
  	 MailbagVO mailbagvo= (MailbagVO)localIterator.next();
  	 mailbag.setDestinationCode(mailbagvo.getDestCityDesc());
  	 mailbag.setCount(mailbagvo.getCount());
  	 mailbag.setWeight(mailbagvo.getWeight());
  	 mailbag.setAcceptedBagCount(mailbagvo.getAcceptedBags());
  	 mailbag.setAcceptedWeight(mailbagvo.getAcceptedWeight());
  	 mailbag.setDeviationErrors(mailbagvo.getErrorCode());
    //  localUldManifestVO = constructUldManifestVO(localULDManifest, paramLogonAttributes);
       localArrayList.add(mailbag);
    }
  }
  return localArrayList;
}

public  static ConsignmentDocument constructConsignmentDocument(ConsignmentDocumentVO consignmentDocumentVO) {
	 ConsignmentDocument consignmentDocument = new ConsignmentDocument();
	 consignmentDocument.setCompanyCode(consignmentDocumentVO.getCompanyCode());
	 consignmentDocument.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
	 consignmentDocument.setPaCode(consignmentDocumentVO.getPaCode());
	 consignmentDocument.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
	 consignmentDocument.setConsignmentDate(consignmentDocumentVO.getConsignmentDate());
	 consignmentDocument.setAirportCode(consignmentDocumentVO.getAirportCode());
	 //consignmentDocument.setScanned(consignmentDocumentVO.get)
	 consignmentDocument.setType(consignmentDocumentVO.getType());
	 consignmentDocument.setSubType(consignmentDocumentVO.getSubType());
	 consignmentDocument.setOperation(consignmentDocumentVO.getOperation());
	 consignmentDocument.setLastUpdateUser(consignmentDocumentVO.getLastUpdateUser());
	 consignmentDocument.setLastUpdateTime(consignmentDocumentVO.getLastUpdateTime());
	 consignmentDocument.setRemarks(consignmentDocumentVO.getRemarks());
	 consignmentDocument.setStatedBags(consignmentDocumentVO.getStatedBags());
	 consignmentDocument.setStatedWeight(consignmentDocumentVO.getStatedWeight());
	 consignmentDocument.setOperationFlag(consignmentDocumentVO.getOperationFlag());
	 consignmentDocument.setOrgin(consignmentDocumentVO.getOrgin());
	 consignmentDocument.setDestination(consignmentDocumentVO.getDestination());
	 consignmentDocument.setDespatchDate(consignmentDocumentVO.getDespatchDate());
	 consignmentDocument.setPou(consignmentDocumentVO.getPou());
	 consignmentDocument.setOrginCity(consignmentDocumentVO.getOrginCity());
	 consignmentDocument.setDestinationCity(consignmentDocumentVO.getDestination());
	 consignmentDocument.setOrginAirport(consignmentDocumentVO.getOrginAirport());
	 consignmentDocument.setDateOfDept(consignmentDocumentVO.getDateOfDept());
	 consignmentDocument.setDateOfDept(consignmentDocumentVO.getDateOfDept());
	 consignmentDocument.setFlight(consignmentDocumentVO.getFlight());
	 consignmentDocument.setCarrierCode(consignmentDocumentVO.getCarrierCode());
	 consignmentDocument.setDestPort(consignmentDocumentVO.getDestPort());
	 consignmentDocument.setReportType(consignmentDocumentVO.getReportType());
	 consignmentDocument.setMailCategory(consignmentDocumentVO.getMailCategory());
	 consignmentDocument.setDestinationOfficeOfExchange(consignmentDocumentVO.getDestinationOfficeOfExchange());
	 consignmentDocument.setYear(consignmentDocumentVO.getYear());
	 consignmentDocument.setDsnNumber(consignmentDocumentVO.getDsnNumber());
	 consignmentDocument.setUldNumber(consignmentDocumentVO.getUldNumber());
	 consignmentDocument.setMailSubClass(consignmentDocumentVO.getMailSubClass());
	 consignmentDocument.setMailClass(consignmentDocumentVO.getMailClass());
	 consignmentDocument.setOperatorOrigin(consignmentDocumentVO.getOperatorOrigin());
	 consignmentDocument.setOperatorDestination(consignmentDocumentVO.getOperatorDestination());
	 consignmentDocument.setOOEDescription(consignmentDocumentVO.getOoeDescription());
	 consignmentDocument.setDOEDescription(consignmentDocumentVO.getDoeDescription());
	 consignmentDocument.setConsignmentPriority(consignmentDocumentVO.getConsignmentPriority());
	 consignmentDocument.setTransportationMeans(consignmentDocumentVO.getTransportationMeans());
	 consignmentDocument.setFlightDetails(consignmentDocumentVO.getFlightDetails());
	 consignmentDocument.setFlightRoute(consignmentDocumentVO.getFlightRoute());
	 consignmentDocument.setFirstFlightDepartureDate(consignmentDocumentVO.getFirstFlightDepartureDate());
	 consignmentDocument.setAirlineCode(consignmentDocumentVO.getAirlineCode());
	 consignmentDocument.setTotalSacks(consignmentDocumentVO.getTotalSacks());
	 consignmentDocument.setTotalWeight(consignmentDocumentVO.getTotalWeight());
	 List<MailInConsignment> localMailInConsignmentList = new ArrayList<MailInConsignment>();
	 if(consignmentDocumentVO.getMailInConsignmentcollVOs()!=null){
		 if ((consignmentDocumentVO.getMailInConsignmentcollVOs() != null) && (consignmentDocumentVO.getMailInConsignmentcollVOs().size() > 0))
		  {
			localMailInConsignmentList = constructMailConsignments((List)consignmentDocumentVO.getMailInConsignmentcollVOs());
	     } 
	 consignmentDocument.setMailInConsignmentcolls(localMailInConsignmentList);
	 }
	 return consignmentDocument;
}
public static List<MailInConsignment> constructMailConsignments(Collection<MailInConsignmentVO> mailsInConsignmentVO) {
	List<MailInConsignment> mailInConsignmentList = new ArrayList<MailInConsignment>();
   MailInConsignment mailInConsignment = null;
       Iterator localIterator = mailsInConsignmentVO.iterator();
	      while (localIterator.hasNext()) {
	    	 mailInConsignment= new MailInConsignment();
		     MailInConsignmentVO mailInConsignmentVO = (MailInConsignmentVO)localIterator.next();
		     mailInConsignment.setStatedBags(mailInConsignmentVO.getStatedBags());
		   //  mailInConsignment.setStatedWeight(mailInConsignmentVO.getStatedWeight());
		     mailInConsignment.setUldNumber(mailInConsignmentVO.getUldNumber());
		     mailInConsignment.setMailId(mailInConsignmentVO.getMailId());
		     mailInConsignment.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
		     mailInConsignment.setMailSubclass(mailInConsignmentVO.getMailSubclass());
		     mailInConsignment.setReceptacleSerialNumber(mailInConsignmentVO.getReceptacleSerialNumber());
		     mailInConsignment.setRegisteredOrInsuredIndicator(mailInConsignmentVO.getRegisteredOrInsuredIndicator());
		     mailInConsignment.setHighestNumberedReceptacle(mailInConsignmentVO.getHighestNumberedReceptacle());
		     mailInConsignment.setCompanyCode(mailInConsignmentVO.getCompanyCode());
		     mailInConsignment.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
		     mailInConsignment.setPaCode(mailInConsignmentVO.getPaCode());
		     mailInConsignment.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
		     mailInConsignment.setDsn(mailInConsignmentVO.getDsn());
		     mailInConsignment.setOriginExchangeOffice(mailInConsignmentVO.getOriginExchangeOffice());
		     mailInConsignment.setDestinationExchangeOffice(mailInConsignmentVO.getDestinationExchangeOffice());
		     mailInConsignment.setMailClass(mailInConsignmentVO.getMailClass());
		     mailInConsignment.setYear(mailInConsignmentVO.getYear());
		     mailInConsignment.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
		     mailInConsignment.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
		     mailInConsignment.setConsignmentDate(mailInConsignmentVO.getConsignmentDate());
		     mailInConsignment.setAirportCode(mailInConsignmentVO.getAirportCode());
		     mailInConsignment.setCarrierId(mailInConsignmentVO.getCarrierId());
		     mailInConsignment.setVolume(mailInConsignmentVO.getVolume());
		     mailInConsignment.setTotalLetterBags(mailInConsignmentVO.getTotalLetterBags());
		     mailInConsignment.setTotalParcelBags(mailInConsignmentVO.getTotalParcelBags());
		     mailInConsignment.setTotalLetterWeight(mailInConsignmentVO.getTotalLetterWeight());
		     mailInConsignment.setTotalParcelWeight(mailInConsignmentVO.getTotalParcelWeight());
		     mailInConsignment.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());
		     mailInConsignment.setDisplayUnit(mailInConsignmentVO.getDisplayUnit());
		     mailInConsignment.setMailOrigin(mailInConsignmentVO.getMailOrigin());
		     mailInConsignment.setMailDestination(mailInConsignmentVO.getMailDestination());
		     mailInConsignment.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
		     mailInConsignment.setOperationFlag(mailInConsignmentVO.getOperationFlag());
		     mailInConsignment.setStrWeight(mailInConsignmentVO.getStatedWeight());
		     mailInConsignmentList.add(mailInConsignment);
}
return mailInConsignmentList;
}

public static List<FlightSegmentCapacitySummary> constructFlightCapacitySummaryList(Collection<FlightSegmentCapacitySummaryVO> flightSegmentCapacitysummaryVos) {
	List<FlightSegmentCapacitySummary> flightSegmentCapacitySummary = new ArrayList<FlightSegmentCapacitySummary>();
	FlightSegmentCapacitySummary flightsegmentCapacity = null;
	if(flightSegmentCapacitysummaryVos!=null){
	       Iterator localIterator = flightSegmentCapacitysummaryVos.iterator();
		      while (localIterator.hasNext()) {
		    	  flightsegmentCapacity= new FlightSegmentCapacitySummary();
		    	  FlightSegmentCapacitySummaryVO flightSegmentCapacitySummaryVO = (FlightSegmentCapacitySummaryVO)localIterator.next();
		    	  flightsegmentCapacity.setActualArvDate(flightSegmentCapacitySummaryVO.getActualArvDate());
		    	  flightsegmentCapacity.setAllotmentId(flightSegmentCapacitySummaryVO.getAllotmentId());
		    	  flightsegmentCapacity.setSegmentOrigin(flightSegmentCapacitySummaryVO.getSegmentOrigin());
		    	  flightsegmentCapacity.setSegmentDestination(flightSegmentCapacitySummaryVO.getSegmentDestination());
		    	  if(flightSegmentCapacitySummaryVO.getSegmentTotalWeight()!=null) {
		    	   flightsegmentCapacity.setSegmentTotalWeight(flightSegmentCapacitySummaryVO.getSegmentTotalWeight());
		    	  }
		    	  flightsegmentCapacity.setFlightCarrierIdentifier(flightSegmentCapacitySummaryVO.getFlightCarrierIdentifier());
		    	  flightsegmentCapacity.setFlightNumber(flightSegmentCapacitySummaryVO.getFlightNumber());
		    	  flightsegmentCapacity.setFlightDate(flightSegmentCapacitySummaryVO.getFlightDate());
		    	  List<FlightSegmentCapacitySummary> allotments = new ArrayList<FlightSegmentCapacitySummary>();
		    		 if(flightSegmentCapacitySummaryVO.getAllotments()!=null){
		    				  allotments = constructAllotments(flightSegmentCapacitySummaryVO.getAllotments());
		    				  flightsegmentCapacity.setAllotments(allotments);
		    		 }
		    	flightSegmentCapacitySummary.add(flightsegmentCapacity);
		      }
	}
		      return flightSegmentCapacitySummary;
}
public static List<FlightSegmentCapacitySummary> constructAllotments(Collection<FlightSegmentCapacitySummaryVO> allotmentsVOs) {
	List<FlightSegmentCapacitySummary> allotmentList = new ArrayList<FlightSegmentCapacitySummary>();
	FlightSegmentCapacitySummary allotment = null;
       Iterator localIterator = allotmentsVOs.iterator();
	      while (localIterator.hasNext()) {
	    	  allotment= new FlightSegmentCapacitySummary();
	    	  FlightSegmentCapacitySummaryVO allotmentVO = (FlightSegmentCapacitySummaryVO)localIterator.next();
	    	  allotment.setAllotmentId(allotmentVO.getAllotmentId());
	    	  if(allotmentVO.getTotalWeight()!=null) {
	    	   allotment.setTotalWeight(allotmentVO.getTotalWeight());
	    	  }
	    	  if(allotmentVO.getSegmentTotalWeight()!=null) {
	    	  allotment.setSegmentTotalWeight(allotmentVO.getSegmentTotalWeight());
	    	  }
	    	  allotment.setCategoryCode(allotmentVO.getCategoryCode());
	    	  allotment.setConfirmedBookingWeight(allotmentVO.getConfirmedBookingWeight());
	    	  allotment.setAvailableWeight(allotmentVO.getAvailableWeight());
	    	  if(allotmentVO.getTotalAllotmentWeight()!=null) {
	    	  allotment.setTotalAllotmentWeight(allotmentVO.getTotalAllotmentWeight());
	    	  }
	    	  if(allotmentVO.getTotalAlotmentAvailableWeight()!=null) {
	    	  allotment.setTotalAlotmentAvailableWeight(allotmentVO.getTotalAlotmentAvailableWeight());
	    	  }
	    	  if(allotmentVO.getMalWeight()!=null) {
	    	  allotment.setMailUtised(allotmentVO.getMalWeight());
	    	  }
	    	  if(allotmentVO.getConfirmedBookingWeight()!=null){
	    		  allotment.setConfirmedBookingWeight(allotment.getConfirmedBookingWeight());
	    	  }
	    	  
	    	  
	    	  allotmentList.add(allotment);
          }
	      return allotmentList;
   }

public static  List<ContainerDetails>  constructContainerDetailsForInbound(Collection<ContainerDetailsVO> containervos, LogonAttributes logonAttribute, FlightValidationVO flightvalidationVO) {
	List<ContainerDetails> localcontainerList = new ArrayList<ContainerDetails>();
	ContainerDetails container= null;
    if ((containervos != null) && (containervos.size() > 0))
    {
      Iterator localIterator = containervos.iterator();
      while (localIterator.hasNext()) {
    	    ContainerDetailsVO containervo= (ContainerDetailsVO)localIterator.next();
    	    container = new ContainerDetails();
	        container.setCompanyCode(containervo.getCompanyCode());
	        container.setCarrierId(containervo.getCarrierId());
	        container.setCarrierCode(containervo.getCarrierCode());
	        if ((containervo.getFlightNumber() != null)) {
             container.setFlightNumber(containervo.getFlightNumber());
             }
	        if(containervo.getFlightDate()!=null){
				container.setFlightDate(containervo.getFlightDate().toDisplayDateOnlyFormat());
			}
	      container.setAirportCode(logonAttribute.getAirportCode());
	      container.setAcceptanceFlag(containervo.getAcceptedFlag());
          container.setContainerNumber(containervo.getContainerNumber());
          container.setPol(containervo.getPol());
          container.setAssignedPort(containervo.getAssignedPort());
          container.setPou(containervo.getPou());
          container.setAcceptedFlag(containervo.getAcceptedFlag());
          container.setArrivedStatus(containervo.getArrivedStatus());
          container.setFlightSequenceNumber(containervo.getFlightSequenceNumber());
          container.setLegSerialNumber(containervo.getLegSerialNumber());
          container.setType(containervo.getContainerType());
          container.setTotalBags(containervo.getTotalBags());
          container.setTotalWeight(containervo.getTotalWeight());
          container.setOperationFlag(containervo.getOperationFlag());
          container.setContainerOperationFlag(containervo.getContainerOperationFlag());
          container.setDestination(containervo.getDestination());
          container.setPaBuiltFlag(containervo.getPaBuiltFlag());
          container.setContainerJnyId(containervo.getContainerJnyId());
          container.setSegmentSerialNumber(containervo.getSegmentSerialNumber());
          container.setPaCode(containervo.getPaCode());
          container.setPreassignFlag(containervo.isPreassignFlag());
          container.setCarrierId(containervo.getCarrierId());
          container.setFlightNumber(containervo.getFlightNumber());
          container.setContainerOperationFlag(containervo.getContainerOperationFlag());
          container.setFlightSequenceNumber(containervo.getFlightSequenceNumber());
          container.setAssignmentDate(containervo.getAssignedDate());
          container.setAssignedUser(containervo.getAssignedUser());
          container.setLastUpdateTime(containervo.getLastUpdateTime());
          container.setOperationFlag(containervo.getOperationFlag());
          container.setContainerOperationFlag(containervo.getContainerOperationFlag());
          container.setTransferFromCarrier(containervo.getTransferFromCarrier());
          container.setWareHouse(containervo.getWareHouse());
          container.setLocation(containervo.getLocation());
          container.setOnwardFlights(containervo.getOnwardFlights());
          container.setRemarks(containervo.getRemarks());
          container.setFinalDestination(containervo.getDestination());
          container.setTransitFlag(containervo.getTransitFlag());
          if(containervo.getAssignedDate()!=null) {
		  container.setAssignedOn(containervo.getAssignedDate().toDisplayDateOnlyFormat());
          }
		  container.setUldLastUpdateTime(containervo.getUldLastUpdateTime()); 
		  if(containervo.getActualWeight()!=null){
			  container.setActualWeight(containervo.getActualWeight().getRoundedDisplayValue());  
		  }
		  container.setContentId(containervo.getContentId());
          List<Mailbag> mailbags=null;
          List<DespatchDetails> dsns=null;
          if(containervo.getMailDetails()!=null) {
        	 mailbags= constructMailbagDetails(containervo.getMailDetails(),containervo);
          }
          if(containervo.getDsnVOs()!=null) {
        	  dsns= constructDesptachDetails(containervo.getDsnVOs(),containervo);
           }
          container.setMailDetails(mailbags);
          container.setDesptachDetailsCollection(dsns);
          container.setTotalBags(containervo.getTotalBags());
          container.setBags(containervo.getTotalBags());
          container.setActWgtSta(containervo.getActWgtSta());
          
          if(containervo.getMinReqDelveryTime() !=null) {
          container.setMinReqDelveryTime(containervo.getMinReqDelveryTime().toDisplayFormat(DATE_TIME_FORMAT));
          }
          container.setContainerPureTransfer(containervo.getContainerPureTransfer());//Added by A-8464 for ICRD-328502
          container.setOffloadedInfo(containervo.getOffloadedInfo());
		  container.setOffloadCount(containervo.getOffloadCount());
          localcontainerList.add(container);
		
	    
      }
    }
    return localcontainerList;
}

public static Mailbag populateMailDetails(MailbagVO mailBagVo) {
	
	Mailbag mailBagDetails=new Mailbag();     
	mailBagDetails.setMailbagId(mailBagVo.getMailbagId());   
	if(null!=mailBagVo.getScannedDate()){
		mailBagDetails.setScannedDate(
				mailBagVo.getScannedDate().toDisplayFormat()); 
	} 
	if(null!=mailBagVo.getOoe()){
		mailBagDetails.setOoe(mailBagVo.getOoe());
	}
	if(null!=mailBagVo.getDoe()){
		mailBagDetails.setDoe(mailBagVo.getDoe());
	}
	if(null!=mailBagVo.getMailCategoryCode()){
		mailBagDetails.setMailCategoryCode(mailBagVo.getMailCategoryCode());
	}
	if(null!=mailBagVo.getMailSubclass()){
		mailBagDetails.setMailSubclass(mailBagVo.getMailSubclass());
	}
	if(0!=mailBagVo.getYear()){
		mailBagDetails.setYear(mailBagVo.getYear());
	}
	if(null!=mailBagVo.getDespatchSerialNumber()){
		mailBagDetails.setDespatchSerialNumber(mailBagVo.getDespatchSerialNumber());
	}
	if(null!=mailBagVo.getReceptacleSerialNumber()){
		mailBagDetails.setReceptacleSerialNumber(mailBagVo.getReceptacleSerialNumber());
	}
	if(null!=mailBagVo.getWeight()){
		mailBagDetails.setMailbagWeight(String.valueOf(mailBagVo.getWeight().getRoundedDisplayValue()));
		
	}
	if(null!=mailBagVo.getVolume()){
		mailBagDetails.setMailbagVolume(Double.toString(mailBagVo.getVolume().getRoundedDisplayValue()));
	}
	if(null!=mailBagVo.getSealNumber()){
		mailBagDetails.setSealNumber(mailBagVo.getSealNumber());
	}
	if(null!=mailBagVo.getHighestNumberedReceptacle()){
		mailBagDetails.setHighestNumberedReceptacle(mailBagVo.getHighestNumberedReceptacle());
	}
	if(null!=mailBagVo.getRegisteredOrInsuredIndicator()){
		mailBagDetails.setRegisteredOrInsuredIndicator(mailBagVo.getRegisteredOrInsuredIndicator());
	}
	if(null!=mailBagVo.getTransferFromCarrier()){
		mailBagDetails.setCarrier(mailBagVo.getTransferFromCarrier());
	}
	if(null!=mailBagVo.getMailRemarks()){
		mailBagDetails.setMailRemarks(mailBagVo.getMailRemarks());
	}
	return mailBagDetails;
   }

}
