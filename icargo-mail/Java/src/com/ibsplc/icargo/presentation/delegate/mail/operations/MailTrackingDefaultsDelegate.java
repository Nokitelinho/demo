
/*
 * MailTrackingDefaultsDelegate.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.delegate.mail.operations;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.capacity.booking.vo.MailBookingVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVolumeDetailsVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.AWBFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.ControlDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.GPAContractFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.InventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.InventorySummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailActualDetailFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailActualDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAuditFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAuditHistoryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailBoxIdLovVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailDiscrepancyVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailMonitorFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailMonitorSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailOnHandDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRuleConfigVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScreeningFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceLevelVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailTransitFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailTransitVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailWebserviceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailboxIdVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.business.mail.operations.vo.PeakMessageVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO;
import com.ibsplc.icargo.business.mail.operations.vo.RdtMasterFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResiditRestrictonFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResiditRestrictonVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.mail.operations.vo.national.POMailStatementVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.AutoForwardDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.LocationEnquiryFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1303
 *
 */

@Module("mail")
@SubModule("operations")
public class MailTrackingDefaultsDelegate extends BusinessDelegate { 
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS_DEFAULTS");
	
	private static final String MODULE = "MAILOPERATIONS_DELEGTE";
	
	/**
	 * @author a-1936 This method is used to save the containers for the Flight
	 * @param operationalFlightVO
	 * @param containerVos
	 * @throws BusinessDelegateException
	 */
	public void saveContainers(OperationalFlightVO operationalFlightVO,
			Collection<ContainerVO> containerVos,Collection<LockVO> locks)
	throws BusinessDelegateException {
		log.entering(MODULE, "saveContainers");
		despatchRequest("saveContainers",locks,operationalFlightVO, containerVos);
	}
	
	/**
	 * @author a-1936 This method is used to validate the Flight
	 * @param flightFilterVO
	 * @return Collection<FlightValidationVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<FlightValidationVO> validateFlight(
			FlightFilterVO flightFilterVO) throws BusinessDelegateException {
		log.entering(MODULE, "validateFlight");
		return despatchRequest("validateFlight", flightFilterVO);
	}
	/**
	 * @author a-4777 This method is used to list the mail bag details
	 * @param dsnEnquiryFilterVO
	 * @return Collection<POMailStatementVO>
	 * @throws BusinessDelegateException
	 */
	
	public Page<POMailStatementVO> findPOMailStatementDetails(
			DSNEnquiryFilterVO dsnEnquiryFilterVO) throws BusinessDelegateException {
		log.entering(MODULE, "findPOMailStatementDetails");
		return despatchRequest("findPOMailStatementDetails", dsnEnquiryFilterVO);
	}
	
	/**
	 * @author a-1936 This method is used to validate whether the Flight is
	 *         closed For Operations.
	 * @param operationalFlightVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Boolean isFlightClosedForMailOperations(
			OperationalFlightVO operationalFlightVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "isFlightClosedForMailOperations");
		return despatchRequest("isFlightClosedForMailOperations",
				operationalFlightVO);
		
	}
	
	/**
	 * @author a-1936 Returns all ULDs assigned to the given flight from the
	 *         given airport
	 * @param operationalFlightVO
	 * @return Collection<ContainerVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<ContainerVO> findFlightAssignedContainers(
			OperationalFlightVO operationalFlightVO)
			throws BusinessDelegateException {
		log.entering(MODULE, "findFlightAssignedContainers");
		return despatchRequest("findFlightAssignedContainers",
				operationalFlightVO);
	}
	
	/**
	 * @author a-1936 This method is used to find all the Containers which are
	 *         alraedy assigned to a particular destination and for a particular
	 *         carrier
	 * @param destinationFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ContainerVO> findDestinationAssignedContainers(
			DestinationFilterVO destinationFilterVO)
			throws BusinessDelegateException {
		log.entering(MODULE, "findDestinationAssignedContainers");
		return despatchRequest("findDestinationAssignedContainers",
				destinationFilterVO);
	}
	
	/**
	 * @author a-1936 This method is used to validate the containers whether it
	 *         can be assigned to a particularFlight or Carrier/Destination
	 * @param containerVO
	 * @throws BusinessDelegateException
	 */
	public ContainerVO validateContainer(String airportCode, ContainerVO containerVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "validateContainer");
		return despatchRequest("validateContainer", airportCode, containerVO);
	}
	
	/**
	 * @author a-2037 This method is used to save Mail sub class codes
	 * @param mailSubClassVOs
	 * @throws BusinessDelegateException
	 */
	public void saveMailSubClassCodes(Collection<MailSubClassVO> mailSubClassVOs)
	throws BusinessDelegateException {
		log.entering(MODULE, "saveMailSubClassCodes");
		despatchRequest("saveMailSubClassCodes", mailSubClassVOs);
	}
	
	/**
	 * @author a-2037 This method is used to find all the mail subclass codes
	 * @param companyCode
	 * @param subclassCode
	 * @return Collection<MailSubClassVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<MailSubClassVO> findMailSubClassCodes(String companyCode,
			String subclassCode) throws BusinessDelegateException {
		log.entering(MODULE, "findMailSubClassCodes");
		return despatchRequest("findMailSubClassCodes", companyCode,
				subclassCode);
	}
	
	/**
	 * @author a-2037 This method is used to save office of Exchange Code
	 * @param officeOfExchangeVOs
	 * @throws BusinessDelegateException
	 */
	public void saveOfficeOfExchange(
			Collection<OfficeOfExchangeVO> officeOfExchangeVOs)
	throws BusinessDelegateException {
		log.entering(MODULE, "saveOfficeOfExchange");
		despatchRequest("saveOfficeOfExchange", officeOfExchangeVOs);
	}
	
	/**
	 * @author a-2037 This method is used to find all the mail subclass codes
	 * @param companyCode
	 * @param officeOfExchange
	 * @param pageNumber
	 * @return Collection<OfficeOfExchangeVO>
	 * @throws BusinessDelegateException
	 */
	public Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode,
			String officeOfExchange, int pageNumber)
			throws BusinessDelegateException {
		log.entering(MODULE, "findOfficeOfExchange");
		return despatchRequest("findOfficeOfExchange", companyCode,
				officeOfExchange, pageNumber);
	}
	
	/**
	 *
	 * @author a-1936 This method is used to delete the containers
	 * @param containerVOs
	 * @throws BusinessDelegateException
	 */
	public void deleteContainers(Collection<ContainerVO> containerVOs)
	throws BusinessDelegateException {
		log.entering(MODULE, "deleteContainers");
		despatchRequest("deleteContainers", containerVOs);
	}
	
	/**
	 * @author a-1936 This method is used to list the containersDetails..
	 * @param searchContainerFilterVO
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ContainerVO> findContainers(
			SearchContainerFilterVO searchContainerFilterVO, int pageNumber)
			throws BusinessDelegateException {
		log.entering(MODULE, "findContainers");
		return despatchRequest("findContainers", searchContainerFilterVO,
				pageNumber);
	}
	
	/**
	 * @author a-2037 This method is used to save Postal Administration Code
	 * @param postalAdministrationVO
	 * @throws BusinessDelegateException
	 */
	public void savePACode(PostalAdministrationVO postalAdministrationVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "savePACode");
		despatchRequest("savePACode", postalAdministrationVO);
	}
	
	/**
	 * @author A-2037 This method is used to find Postal Administration Code
	 *         Details
	 * @param companyCode
	 * @param paCode
	 * @return PostalAdministrationVO
	 * @throws BusinessDelegateException
	 */
	public PostalAdministrationVO findPACode(String companyCode, String paCode)
	throws BusinessDelegateException {
		log.entering(MODULE, "findPACode");
		return despatchRequest("findPACode", companyCode, paCode);
	}
	
	/**
	 * @author A-2037 Method for PALov containing PACode and PADescription
	 * @param companyCode
	 * @param paCode
	 * @param paName
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<PostalAdministrationVO> findPALov(String companyCode,
			String paCode, String paName, int pageNumber,int defaultSize)
			throws BusinessDelegateException {
		log.entering(MODULE, "findPALov");
		return despatchRequest("findPALov", companyCode, paCode, paName,
				pageNumber,defaultSize);
	}
	/**
	 * @author a-5931 This method is used to list the mail box id LOV
	 * @param mailbox id FilterVO
	 * @return Page <MailBoxIdLovVO>
	 * @throws BusinessDelegateException
	 */
	public Page<MailBoxIdLovVO> findMailBoxIdLov(String companyCode,String mailboxCode,String mailboxDesc,
			int pageNumber, int defaultSize) throws BusinessDelegateException {
		return despatchRequest("findMailBoxIdLov", companyCode, mailboxCode, mailboxDesc,
				pageNumber, defaultSize);
	}
	/**
	 * @author a-5931 This method is used to import the mail box ids 
	 * @param Collection<MailBoxIdLovVO>
	 * @return void
	 * @throws BusinessDelegateException
	 */
	// Commented the method as part of ICRD-153078
	// Uncommented as part of ICRD-234820	
	public  void saveMailboxIDs(Collection<MailBoxIdLovVO > mailBoxIdVOs) throws BusinessDelegateException {
		 despatchRequest("saveMailboxIDs", mailBoxIdVOs);
	}
	/**
	 * This method is used to reassign the Containers
	 *
	 * @author A-1739
	 * @param containersToReassign
	 * @param toFlightVO
	 * @throws BusinessDelegateException
	 */
	public void reassignContainers(
			Collection<ContainerVO> containersToReassign,
			OperationalFlightVO toFlightVO) throws BusinessDelegateException {
		despatchRequest("reassignContainers", containersToReassign, toFlightVO);
	}
	
	/**
	 * @author A-2037 Method for MailSubClassLOV containing code and description
	 * @param companyCode
	 * @param code
	 * @param description
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<MailSubClassVO> findMailSubClassCodeLov(String companyCode,
			String code, String description, int pageNumber,int defaultSize)
			throws BusinessDelegateException {
		log.entering(MODULE, "findMailSubClassCodeLov");
		return despatchRequest("findMailSubClassCodeLov", companyCode, code,
				description, pageNumber,defaultSize);
	}
	
	/**
	 * @author A-2037 This method is used to find the History of a Mailbag
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<MailbagHistoryVO> findMailbagHistories( /*modified by A-8149 for ICRD-248207*/
			String companyCode, String mailBagId, long mailSequenceNumber )
			throws BusinessDelegateException {
		log.entering(MODULE, "findMailbagHistories");
		return despatchRequest("findMailbagHistories", companyCode, mailBagId, mailSequenceNumber);
	}
	
	public Collection<MailHistoryRemarksVO> findMailbagNotes(String mailBagId)
			throws BusinessDelegateException {
		log.entering(MODULE, "findMailbagNotes");
		return despatchRequest("findMailbagNotes", mailBagId);
	}
	
	/**
	 *
	 * @author a-1936 This method is used to find the mailBags..
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<MailbagVO> findMailbags(
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws BusinessDelegateException {
		log.entering(MODULE, "findMailbags");
		return despatchRequest("findMailbags", mailbagEnquiryFilterVO,
				pageNumber);
	}
	
	/**
	 * @author A-2037 The method is used to find the mail acceptance details.
	 * @param operationalFlightVO
	 * @return MailAcceptanceVO
	 * @throws BusinessDelegateException
	 */
	public MailAcceptanceVO findFlightAcceptanceDetails(
			OperationalFlightVO operationalFlightVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "findFlightAcceptanceDetails");
		return despatchRequest("findFlightAcceptanceDetails",
				operationalFlightVO);
	}
	
	/**
	 * @author A-2037 Method for OfficeOfExchangeLOV containing code and
	 *         description
	 * @param companyCode
	 * @param code
	 * @param description
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(OfficeOfExchangeVO officeofExchangeVO, int pageNumber,int defaultSize)
			throws BusinessDelegateException {
		log.entering(MODULE, "findOfficeOfExchangeLov");
		return despatchRequest("findOfficeOfExchangeLov", officeofExchangeVO, pageNumber,defaultSize);
	}
	
	/**
	 * @author A-1936 This method is used to find all The WareHouses in a
	 *         Airport
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<WarehouseVO> findAllWarehouses(String companyCode,
			String airportCode) throws BusinessDelegateException {
		log.entering("MODULE", "findAllWarehouses");
		return despatchRequest("findAllWarehouses", companyCode, airportCode);
	}
	
	/**
	 * @author A-1936 This method is used to get the Location Corresponding to
	 *         the WareHouse
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param transactionCodes
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Map<String, Collection<String>> findWarehouseTransactionLocations(
			LocationEnquiryFilterVO filterVO)
			throws BusinessDelegateException {
		log.entering("MODULE", "findWarehouseTransactionLocations");
		return despatchRequest("findWarehouseTransactionLocations",
				filterVO);
	}
	
	/**
	 * @author a-1936 This method is used to validate the WareHouse LOcation
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param locationCode
	 * @return
	 * @throws BusinessDelegateException
	 */
	public LocationValidationVO validateLocation(String companyCode,
			String airportCode, String warehouseCode, String locationCode)
	throws BusinessDelegateException {
		log.entering("MODULE", "validateLocation");
		return despatchRequest("validateLocation", companyCode, airportCode,
				warehouseCode, locationCode);
	}
	
	/**
	 * @author A-2037 The method is used to find the mail acceptance details.
	 * @param operationalFlightVO
	 * @return MailAcceptanceVO
	 * @throws BusinessDelegateException
	 */
	public MailAcceptanceVO findDestinationAcceptanceDetails(
			OperationalFlightVO operationalFlightVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "findDestinationAcceptanceDetails");
		return despatchRequest("findDestinationAcceptanceDetails",
				operationalFlightVO);
	}
	
	/**
	 * This method is used to validate the mailBag (The mailTagFormat)
	 *
	 * @param mailbagVos
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Boolean validateMailBags(Collection<MailbagVO> mailbagVos)
	throws BusinessDelegateException {
		log.entering(MODULE, "validateMailBags");
		return despatchRequest("validateMailBags", mailbagVos);
	}
	
	/**
	 * @author a-1936 This method is used to update the FlightStatus and make it
	 *         as Reopen
	 * @param operationalFlightVO
	 * @throws BusinessDelegateException
	 */
	public void reopenFlight(OperationalFlightVO operationalFlightVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "reopenFlight");
		despatchRequest("reopenFlight", operationalFlightVO);
	}
	
	
	
	/**
	 * @author a-1936 This method is used to find the 1.Containers in the Flight
	 *         2.DSNS 3.MailBags that can be Offloaded
	 * @param offloadFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public OffloadVO findOffloadDetails(OffloadFilterVO offloadFilterVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "findOffloadDetails");
		return despatchRequest("findOffloadDetails", offloadFilterVO);
	}
	
	/**
	 * @author A-1739 This method is used to save the AccepatanceDetails
	 * @param mailAcceptanceVO
	 * @throws BusinessDelegateException
	 */
	public Collection<ScannedMailDetailsVO> saveAcceptanceDetails(MailAcceptanceVO mailAcceptanceVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "saveAcceptanceDetails");
		return despatchRequest("saveAcceptanceDetails", mailAcceptanceVO);
	}
	
	/**
	 * @author a-1936 This method is used to offload all the
	 *         Containers\dsns\mailbags
	 * @param offloadVo
	 * @throws BusinessDelegateException
	 */
	public Collection<ContainerDetailsVO> offload(OffloadVO offloadVo)
	throws BusinessDelegateException {
		log.entering(MODULE, "offload");
		return despatchRequest("offload", offloadVo);
	}
	
	/**
	 * @author A-2037 This method is used to find Preadvice for outbound mail
	 *         and it gives the details of the ULDs and the receptacles based on
	 *         CARDIT
	 * @param operationalFlightVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public PreAdviceVO findPreAdvice(OperationalFlightVO operationalFlightVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "findPreAdvice");
		return despatchRequest("findPreAdvice", operationalFlightVO);
		
	}
	
	/**
	 * This method is used to validate the DSN say DOE,OOE
	 *
	 * @param dsnVos
	 * @throws BusinessDelegateException
	 */
	public Boolean validateDSNs(Collection<DSNVO> dsnVos)
	throws BusinessDelegateException {
		log.entering(MODULE, "validateDSNs");
		return despatchRequest("validateDSNs", dsnVos);
		
	}
	
	/**
	 * @author A-2037 This method is used to find the DSNs.
	 * @param dSNEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<DespatchDetailsVO> findDSNs(
			DSNEnquiryFilterVO dSNEnquiryFilterVO, int pageNumber)
			throws BusinessDelegateException {
		log.entering(MODULE, "findDSNs");
		return despatchRequest("findDSNs", dSNEnquiryFilterVO, pageNumber);
		
	}
	
	/**
	 * This method is used to reassign the MailBags
	 *
	 * @param mailbagsToReassign
	 * @param toContainerVO
	 * @throws BusinessDelegateException
	 */
	public Collection<ContainerDetailsVO> reassignMailbags(
			Collection<MailbagVO> mailbagsToReassign, ContainerVO toContainerVO)
			throws BusinessDelegateException {
		log.entering(MODULE, "reassignMailbags");
		return despatchRequest("reassignMailbags", mailbagsToReassign,
				toContainerVO);
	}
	
	/**
	 * @author a-1936 The Reassignments are possible Only for DespatchLevel Dsns .
	 * @param dsnsToReassign
	 * @param toContainerVo
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ContainerDetailsVO> reassignDSNs(
			Collection<DespatchDetailsVO> dsnsToReassign,
			ContainerVO toContainerVo) throws BusinessDelegateException {
		return despatchRequest("reassignDSNs", dsnsToReassign, toContainerVo);
	}
	
	/**
	 * @author A-2037 This method is used to find the Damaged Mailbag Details
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<DamagedMailbagVO> findMailbagDamages(String companyCode,
			String mailbagId) throws BusinessDelegateException {
		log.entering(MODULE, "findMailbagDamages");
		return despatchRequest("findMailbagDamages", companyCode, mailbagId);
	}
	
	/**
	 * @author A-2037 This method is used to find Local PAs
	 * @param companyCode
	 * @param countryCode
	 * @return Collection<PostalAdministrationVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<PostalAdministrationVO> findLocalPAs(String companyCode,
			String countryCode) throws BusinessDelegateException {
		log.entering(MODULE, "findLocalPAs");
		return despatchRequest("findLocalPAs", companyCode, countryCode);
	}
	
	/**
	 * @param consignmentDocumentVO
	 * @return Integer
	 * @throws BusinessDelegateException
	 */
	public Integer saveConsignmentDocument(
			ConsignmentDocumentVO consignmentDocumentVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "saveConsignmentDocument");
		return despatchRequest("saveConsignmentDocument", consignmentDocumentVO);
	}
	
	/**
	 * @author a-1883 This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws BusinessDelegateException
	 */
	public ConsignmentDocumentVO findConsignmentDocumentDetails(
			ConsignmentFilterVO consignmentFilterVO)
	throws BusinessDelegateException {
		log.entering(MODULE, "findConsignmentDocumentDetails");
		return despatchRequest("findConsignmentDocumentDetails",
				consignmentFilterVO);
	}
	
	/**
	 *
	 * @param mailbagsToReturn
	 * @throws BusinessDelegateException
	 */
	public Collection<ContainerDetailsVO> returnMailbags(
			Collection<MailbagVO> mailbagsToReturn)
			throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "returnMailbags");
		return despatchRequest("returnMailbags", mailbagsToReturn);
	}
	
	/**
	 * @author a-1739
	 * @param mailbagVOs
	 * @throws BusinessDelegateException
	 */
	public void saveDamageDetailsForMailbag(Collection<MailbagVO> mailbagVOs)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate",
		"saveDamageDetailsForMailbag");
		despatchRequest("saveDamageDetailsForMailbag", mailbagVOs);
		log.exiting("MailTrackingDefaultsDelegate",
		"saveDamageDetailsForMailbag");
	}
	
	/**
	 * @author A-1739 This method is used to return the Despatches..
	 * @param damagedDSNVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ContainerDetailsVO> returnDespatches(
			Collection<DamagedDSNVO> damagedDSNVOs)
			throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "returnDespatch");
		return despatchRequest("returnDespatches", damagedDSNVOs);
	}
	/**
	 * 
	 * @param fromFlight
	 * @param toFlight
	 * @param operationName
	 * @param operationType
	 * @param port
	 * @throws BusinessDelegateException
	 */
	public void updateConsignmentDetailsForMailOperations(Object fromFlight,Object toFlight,String operationName,String operationType,String port) throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate", "updateConsignmentDetailsForMailOperations");
		 despatchRequest("updateConsignmentDetailsForMailOperations", fromFlight,toFlight,operationName,operationType,port);
	}
	/**
	 * This method deletes Consignment document details and its childs
	 *
	 * @author a-1883
	 * @param consignmentDocumentVO
	 * @throws BusinessDelegateException
	 */
	public void deleteConsignmentDocumentDetails(
			ConsignmentDocumentVO consignmentDocumentVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate",
		"deleteConsignmentDocumentDetails");
		despatchRequest("deleteConsignmentDocumentDetails",
				consignmentDocumentVO);
		log.exiting("MailTrackingDefaultsDelegate",
		"deleteConsignmentDocumentDetails");
	}
	
	/**
	 * @author A-1739 This method is used to saveDamageDetailsForDespatches
	 * @param damagedDSNVOs
	 * @throws BusinessDelegateException
	 */
	public void saveDamageDetailsForDespatches(
			Collection<DamagedDSNVO> damagedDSNVOs)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate",
		"saveDamageDetailsForDespatch");
		despatchRequest("saveDamageDetailsForDespatches", damagedDSNVOs);
		log.exiting("MailTrackingDefaultsDelegate",
		"saveDamageDetailsForDespatch");
	}
	
	/**
	 * @author A-1936 This method is used to find the DamagesForDSN...
	 * @param despatchDetailsVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<DamagedDSNVO> findDSNDamages(
			Collection<DespatchDetailsVO> despatchDetailsVOs)
			throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "findDSNDamages");
		return despatchRequest("findDSNDamages", despatchDetailsVOs);
		
	}
	
		
	/**
	 * This method returns Unaccepted ULD Details
	 *
	 * @author a-1883
	 * @param operationalFlightVO
	 * @return Collection<ContainerDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<ContainerDetailsVO> findUnacceptedULDs(
			OperationalFlightVO operationalFlightVO)
			throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "findUnacceptedULDs");
		return despatchRequest("findUnacceptedULDs", operationalFlightVO);
	}
	
	/**
	 * @author a-1936 This method is used to remove the EmptyULDs(ULDs with no
	 *         MailBags\Despatches)
	 * @param containerDetailsVOs
	 * @throws BusinessDelegateException
	 */
	public void unassignEmptyULDs(
			Collection<ContainerDetailsVO> containerDetailsVOs)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "unassignEmptyULDs");
		log.entering("MailTrackingDefaultsDelegate", "unassignEmptyULDs");
		despatchRequest("unassignEmptyULDs", containerDetailsVOs);
	}
	
	/**
	 * @author A-1739 This method is used to findArrivalDetails
	 * @param opFlightVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public MailArrivalVO findArrivalDetails(MailArrivalFilterVO filterVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "findArrivalDetails");
		return despatchRequest("findArrivalDetails", filterVO);
	}
	
	/**
	 * @author A-1936 This method is used to save the PartnerCarriers..
	 * @param partnerCarrierVOs
	 * @throws BusinessDelegateException
	 */
	public void savePartnerCarriers(
			Collection<PartnerCarrierVO> partnerCarrierVOs)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "savePartnerCarriers");
		despatchRequest("savePartnerCarriers", partnerCarrierVOs);
	}
	
	/**
	 * @author A-1876 This method is used to list the PartnerCarriers..
	 * @param companyCode
	 * @param ownCarrierCode
	 * @param airportCode
	 * @return Collection<PartnerCarrierVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<PartnerCarrierVO> findAllPartnerCarriers(
			String companyCode, String ownCarrierCode, String airportCode)
			throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "findAllPartnerCarriers");
		return despatchRequest("findAllPartnerCarriers", companyCode,
				ownCarrierCode, airportCode);
	}
	
	public void saveCoterminusDetails(
			Collection<CoTerminusVO> coterminusVOs)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "saveCoterminusDetails");
		despatchRequest("saveCoterminusDetails", coterminusVOs);
	}
	
	/*added by A-8149 for ICRD-243386 starts*/
	public void saveServiceStandardDetails(
			Collection<MailServiceStandardVO> mailServiceStandardVOs,Collection<MailServiceStandardVO> mailServiceStandardVOstodelete)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "saveServiceStandardDetails");
		despatchRequest("saveServiceStandardDetails", mailServiceStandardVOs, mailServiceStandardVOstodelete);
	}
	
	public Page<MailServiceStandardVO> listServiceStandardDetails(
			MailServiceStandardFilterVO mailServiceStandardFilterVO,int pageNumber )
			throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "listServiceStandardDetails");
		return despatchRequest("listServiceStandardDetails", mailServiceStandardFilterVO,pageNumber);
	}
	/*added by A-8149 for ICRD-243386 ends*/
	
	public Collection<CoTerminusVO> findAllCoTerminusAirports(
			CoTerminusFilterVO filterVO)
			throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "findAllCoTerminusAirports");
		return despatchRequest("findAllCoTerminusAirports", filterVO);
	}
	
public void saveRdtMasterDetails(
			Collection<MailRdtMasterVO> mailRdtMasterVOs)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "saveCoterminusDetails");
		despatchRequest("saveRdtMasterDetails", mailRdtMasterVOs);
	}
	public Collection<ErrorVO> saveRdtMasterDetailsXls( 
			Collection<MailRdtMasterVO> mailRdtMasterVOs)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "saveCoterminusDetails"); 
		return despatchRequest("saveRdtMasterDetailsXls", mailRdtMasterVOs);
	}
		public Collection<MailRdtMasterVO> findRdtMasterDetails(
			RdtMasterFilterVO filterVO)
			throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "findRdtMasterDetails");
		return despatchRequest("findRdtMasterDetails", filterVO);
	}
	/**
	 * @author A-1739 This method is used to save the ArrivalDetails
	 * @param mailArrivalVO
	 * @throws BusinessDelegateException
	 */
	public void saveArrivalDetails(MailArrivalVO mailArrivalVO,Collection<LockVO> locks)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "saveArrivalDetails");
		despatchRequest("saveArrivalDetails",locks,mailArrivalVO);
	}
	
	/**
	 * @author A-1936 This method is used to find the MailBags Tat has been
	 *         accepeted to the Flight and Flag the UpliftedResdit for the Same
	 * @param operationalFlightVO
	 * @throws BusinessDelegateException
	 */
	public void flagUpliftedResditForMailbags(
			OperationalFlightVO operationalFlightVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate",
		"flagUpliftedResditForMailbags");
		despatchRequest("flagUpliftedResditForMailbags", operationalFlightVO);
	}
	
	/**
	 * @author a-1883
	 * @param documentFilterVO
	 * @return DocumentValidationVO
	 * @throws BusinessDelegateException
	 */
	public DocumentValidationVO validateDocumentInStock(
			DocumentFilterVO documentFilterVO) throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "validateDocumentInStock");
		return despatchRequest("validateDocumentInStock", documentFilterVO);
	}
	
	/**
	 * @author a-1883
	 * @param documentFilterVO
	 * @return DocumentValidationVO
	 * @throws BusinessDelegateException
	 */
	public DocumentValidationVO findNextDocumentNumber(
			DocumentFilterVO documentFilterVO) throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "findNextDocumentNumber");
		return despatchRequest("findNextDocumentNumber", documentFilterVO);
	}
	
	/**
	 * @author a-1883
	 * @param aWBFilterVO
	 * @return AWBDetailVO
	 * @throws BusinessDelegateException
	 */
	public AWBDetailVO findAWBDetails(AWBFilterVO aWBFilterVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "findAWBDetails");
		return despatchRequest("findAWBDetails", aWBFilterVO);
	}
	
	/**
	 * @author a-1883
	 * @param documentFilterVO
	 * @throws BusinessDelegateException
	 */
	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "deleteDocumentFromStock");
		despatchRequest("deleteDocumentFromStock", documentFilterVO);
		log.exiting("MailTrackingDefaultsDelegate", "deleteDocumentFromStock");
	}
	
	/**
	 * @author a-1883
	 * @param aWBDetailVO
	 * @param containerDetailsVO
	 * @throws BusinessDelegateException
	 */
	public void attachAWBDetails(AWBDetailVO aWBDetailVO,
			ContainerDetailsVO containerDetailsVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "attachAWBDetails");
		despatchRequest("attachAWBDetails", aWBDetailVO, containerDetailsVO);
		log.exiting("MailTrackingDefaultsDelegate", "attachAWBDetails");
	}
	
	
	/**
	 * @author a-1883
	 * @param operationalFlightVO
	 * @return Boolean
	 * @throws BusinessDelegateException
	 */
	public Boolean isFlightClosedForInboundOperations(OperationalFlightVO
			operationalFlightVO) throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate",
		"isFlightClosedForInboundOperations");
		return despatchRequest("isFlightClosedForInboundOperations",
				operationalFlightVO);
	}
	/**
	 * This method is used to reopen the InboundFlight
	 * @author a-1883
	 * @param operationalFlightVO
	 * @throws BusinessDelegateException
	 */
	public void reopenInboundFlight(OperationalFlightVO operationalFlightVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","reopenInboundFlight");
		despatchRequest("reopenInboundFlight",operationalFlightVO);
		log.exiting("MailTrackingDefaultsDelegate","reopenInboundFlight");
	}
	/**
	 * This method is used to close the InboundFlight
	 * @author a-1883
	 * @param operationalFlightVO
	 * @throws BusinessDelegateException
	 */
	public void closeInboundFlight(OperationalFlightVO operationalFlightVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","closeInboundFlight");
		despatchRequest("closeInboundFlight",operationalFlightVO);
		log.exiting("MailTrackingDefaultsDelegate","closeInboundFlight");
	}
	
	public OperationalFlightVO validateInboundFlight(OperationalFlightVO flightVO)
	throws BusinessDelegateException {
		return despatchRequest("validateInboundFlight",flightVO);
	}
	
	/**
	 * Finds the details of containers and mailbags for upload screen
	 * Oct 6, 2006, a-1739
	 * @param scannedDetails
	 * @return scannedEtailsVO representing updated data
	 * @throws BusinessDelegateException
	 */
	public ScannedDetailsVO findDetailsForMailUpload(
			ScannedDetailsVO scannedDetails) throws BusinessDelegateException {
		return despatchRequest("findDetailsForMailUpload", scannedDetails);
	}
	
	/**
	 * Validates the scanned mailbags and returns the same collection.
	 * If there are errors they are set in the corresponding mailbagVO.
	 * The errortype decides the type of error and errordescription has the
	 * description
	 * Oct 9, 2006, a-1739
	 * @param mailbags
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<MailbagVO> validateScannedMailbagDetails(
			Collection<MailbagVO> mailbags) throws  BusinessDelegateException {
		return despatchRequest("validateScannedMailbagDetails", mailbags);
	}
	/**
	 * This method is used to transfer the containers 
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @param printFlag
	 * @throws BusinessDelegateException
	 */
	public TransferManifestVO transferContainers(Collection<ContainerVO>
	containerVOs,OperationalFlightVO operationalFlightVO,String printFlag)
	throws  BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","transferContainers");
		return despatchRequest("transferContainers", containerVOs,
				operationalFlightVO,printFlag);
		
	}
	
	/**
	 * @author a-1883
	 * @param despatchDetailsVOs
	 * @param containerVO
	 * @throws BusinessDelegateException
	 */
	public void transferDespatches(Collection<DespatchDetailsVO>
	despatchDetailsVOs,ContainerVO containerVO)
	throws  BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","transferDespatches");
		despatchRequest("transferDespatches",despatchDetailsVOs,containerVO);
		log.exiting("MailTrackingDefaultsDelegate","transferDespatches");
	}
	
	/**
	 * Sveas the details of scanned oubound mails
	 * Oct 9, 2006, a-1739
	 * @param scannedItems the MailAcceptanceVOs scanned
	 * @return a collection of ScannedMailDetails containing mailbags with error
	 * @throws BusinessDelegateException
	 */
	public Collection<ScannedMailDetailsVO> saveScannedOutboundDetails(
			Collection<MailAcceptanceVO> scannedItems)
			throws  BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","saveScannedOutboundDetails");
		return despatchRequest("saveScannedOutboundDetails",scannedItems);
	}
	
	/**
	 * Saves the details of mails scanned inbound
	 * Oct 9, 2006, a-1739
	 * @param mailArrivalVOs
	 * @return collectio nof scannedmaildetailsvo for all mailbags with error
	 * @throws BusinessDelegateException
	 */
	public Collection<ScannedMailDetailsVO> saveScannedInboundMails(
			Collection<MailArrivalVO> mailArrivalVOs)
			throws  BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","saveScannedInboundMails");
		return despatchRequest("saveScannedInboundMails",mailArrivalVOs);
	}
	/**
	 * @author a-1739
	 * @param scannedMailbagsToReturn
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ScannedMailDetailsVO> returnScannedMailbags(String airportCode,
			Collection<MailbagVO> scannedMailbagsToReturn)
			throws  BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","returnScannedMailbags");
		return despatchRequest("returnScannedMailbags",airportCode,
				scannedMailbagsToReturn);
	}
	
	/**
	 * @author a-2107
	 * @param scannedMailbagsToOffload
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ScannedMailDetailsVO> offloadScannedMailbags(
			Collection<MailbagVO> scannedMailbagsToOffload)
			throws  BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","offloadScannedMailbags");
		return despatchRequest("offloadScannedMailbags",
				scannedMailbagsToOffload);
	}
	
	/**
	 * Finds the container assignment of a container
	 * Oct 16, 2006, a-1739
	 * @param containerVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public ContainerAssignmentVO findContainerAssignment(
			ContainerVO containerVO) throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","returnScannedMailbags");
		return despatchRequest("findContainerAssignment", containerVO);
	}
	
	
	/**
	 * @author a-1936
	 * This method is included as the Part of NCA CR .
	 * In NCA the Mails can be accepted to the Flight at a later Point of Time
	 * In such a case the mails are actually assigned to the Carrier(even without DestinationSpecified)..
	 * The method is used to list all such containers and its assignments
	 * @param companyCode
	 * @param currentAirport
	 * @param carrierId
	 * @return
	 * @throws BusinessDelegateException
	 */
	public InventoryListVO findInventoryList(String companyCode,
			String currentAirport,int carrierId)
	throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate"," findInventoryList");
		return despatchRequest("findInventoryList",companyCode,currentAirport,carrierId);
	}
	
	
	/**
	 * @author a-1936
	 * This method is used to view the Summary Details of the Inventory List
	 * say  the Number of Bags and Weight associated with the Destination-Category
	 * @param companyCode
	 * @param currentAirport
	 * @param carrierId
	 * @return
	 * @throws BusinessDelegateException
	 */
	public InventorySummaryVO findInventorySummary(String companyCode,
			String currentAirport,int carrierId)
	throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate"," findInventorySummary");
		return despatchRequest("findInventorySummary",companyCode,currentAirport,carrierId);
	}
	
	/**
	 *  @author a-1936
	 *  This methos is used to reassign the MailBags from  the Inventory List ..
	 *   Added as the Part Of NCA CR..
	 *   @param mailInInventoryListVos
	 *   @param toContainerVO
	 *   @throws BusinessDelegateException
	 */
	public  void reassignMailbagsForInventory(Collection<MailInInventoryListVO> mailInInventoryListVos,
			ContainerVO toContainerVO)
	throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate","reassignMailbagsForInventory");
		despatchRequest("reassignMailbagsForInventory",mailInInventoryListVos,toContainerVO);
		log.exiting("MailTrackingDefaultsDelegate","reassignMailbagsForInventory");
	}
	
	
	/**
	 * @author a-1936
	 * This method is used to move all the Mailbags at the ContainerLevel From the
	 * Inventory List ..
	 * @param containerInInventoryListVOs
	 * @param toContainerVO
	 * @throws BusinessDelegateException
	 */
	public  void moveMailbagsForInventory(Collection<ContainerInInventoryListVO> containerInInventoryListVOs,
			ContainerVO toContainerVO)
	throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate","moveMailbagsForInventory");
		despatchRequest("moveMailbagsForInventory",containerInInventoryListVOs,toContainerVO);
		log.exiting("MailTrackingDefaultsDelegate","moveMailbagsForInventory");
	}
	/**
	 * @author a-1883
	 * This method used to attach AWB details to all DSNs .
	 * if they are not attached to any AWB, creates new a Shipment
	 * and attach AWB details to all.
	 * DSNs having same Origin PA, Destination PA, Category, Origin Airport
	 * and Destination Airport  will have the same AWB details if they are
	 * not already attached to any AWB.If they are already attached then
	 * then there won't be any change to them.
	 * @param containerDetailsVOs
	 * @param operationalFlightVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ContainerDetailsVO> autoAttachAWBDetails(
			Collection<ContainerDetailsVO> containerDetailsVOs,
			OperationalFlightVO operationalFlightVO)
			throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","autoAttachAWBDetails");
		return despatchRequest("autoAttachAWBDetails",containerDetailsVOs,
				operationalFlightVO);
	}
	
	
	/**
	 * @author a-1936
	 * This method is used to delete all the MailBags Selected From the
	 * Inventory List ..
	 * Note:When we Say Delete MailBags Actually these mailBags are Moved to TRASH CONTAINER
	 * All those MailBags which are not being used by the NCA can be moved to this
	 * TRASH Container ...
	 * @param mailInInventoryListVos
	 * @throws BusinessDelegateException
	 */
	public void  deleteMailbagsInInventory(Collection<MailInInventoryListVO>  mailInInventoryListVos)
	throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate","deleteMailbagsInInventory");
		despatchRequest("deleteMailbagsInInventory",mailInInventoryListVos);
		log.exiting("MailTrackingDefaultsDelegate","deleteMailbagsInInventory");
	} 
	/**
	 * @author A-2667
	 * This method is used to delete Mail Bags from MaailBagEnquiry
	 * @param mailInInventoryListVos
	 * @throws BusinessDelegateException
	 */
	public void  deleteMailsfromInventory(Collection<MailbagVO> mailbagVO)
	throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate","deleteMailsfromInventory");
		despatchRequest("deleteMailsfromInventory",mailbagVO);
		log.exiting("MailTrackingDefaultsDelegate","deleteMailsfromInventory");
	}
	/**
	 * @author a-1883
	 * NCA_CR
	 * This method is used to list Mail Discrepancies
	 * @param operationalFlightVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<MailDiscrepancyVO>  findMailDiscrepancies(OperationalFlightVO
			operationalFlightVO) throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate","findMailDiscrepancies");
		return despatchRequest("findMailDiscrepancies",operationalFlightVO);
	}
	
	/**
	 * Finds cardit details for cardit/opr flight
	 * Jan 25, 2007, A-1739
	 * @param carditEnquiryFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public CarditEnquiryVO findCarditDetails(
    		CarditEnquiryFilterVO carditEnquiryFilterVO) throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate","findCarditDetails");
		return despatchRequest("findCarditDetails",carditEnquiryFilterVO);
	} 
	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	 public  Collection<MailInFlightSummaryVO> findMailsInFlight(
	  			OperationalFlightVO operationalFlightVO)
	  			throws BusinessDelegateException{
			log.entering("MailTrackingDefaultsDelegate","findMailsInFlight");
			return despatchRequest("findMailsInFlight",operationalFlightVO);
		}
	 
	 /**
	  * This method does delivery for all the mailbags that are terminating at 
	  * the current port
	  * Jan 29, 2007, A-1739
	  * @param mailArrivalVO
	  * @throws BusinessDelegateException
	  */
	 public void deliverMailbags(MailArrivalVO mailArrivalVO)
	   throws BusinessDelegateException {
		 	log.entering("MailTrackingDefaultsDelegate","deliverMailbags");
			despatchRequest("deliverMailbags",mailArrivalVO);
	 }
	 /**
	  * @author a-1883
	  * @param mailInFlightSummaryVOs
	  * @param controlDocumentNumber
	  * @throws BusinessDelegateException
	  */
	 public void assignControlDocumentNumber(Collection<MailInFlightSummaryVO>
		mailInFlightSummaryVOs, String controlDocumentNumber)
	 throws BusinessDelegateException {
		 	log.entering("MailTrackingDefaultsDelegate","assignControlDocumentNumber");
			despatchRequest("assignControlDocumentNumber",mailInFlightSummaryVOs,
					controlDocumentNumber);
			log.exiting("MailTrackingDefaultsDelegate","assignControlDocumentNumber");
	 }
	 /**
	  * This method is used for getting Booking details of Commodity 'MAL'
	  * in a Flight 
	  * @author a-1883
	  * @param operationalFlightVO
	  * @return
	  * @throws BusinessDelegateException
	  */
	 public  Collection<MailBookingVO> findBookingDetailsForMail(
			 OperationalFlightVO operationalFlightVO) throws BusinessDelegateException {
				log.entering("MailTrackingDefaultsDelegate","findBookingDetailsForMail");
				return despatchRequest("findBookingDetailsForMail",operationalFlightVO);
	 }
	 /**
	  * @author a-1883
	  * @param companyCode
	  * @param airportCode
	  * @param isGHA
	  * @return
	  * @throws BusinessDelegateException
	  */
	 public String findStockHolderForMail(String companyCode,
			 String airportCode,boolean isGHA)
	 throws BusinessDelegateException {
		 log.entering("MailTrackingDefaultsDelegate","findStockHolderForMail");
		 return despatchRequest("findStockHolderForMail",companyCode,
				 airportCode,isGHA);
	 }
	 /**
	  * @author A-1883
	  * @param mailFlightSummaryVOs
	  * @param mailBookingVO
	  * @throws BusinessDelegateException
	  */
	 public void updateBookingDetailsForMail(Collection<MailInFlightSummaryVO>
		mailFlightSummaryVOs ,MailBookingVO mailBookingVO)
	 throws BusinessDelegateException {
		 log.entering("MailTrackingDefaultsDelegate","updateBookingDetailsForMail");
		 despatchRequest("updateBookingDetailsForMail",mailFlightSummaryVOs,mailBookingVO);
		 log.exiting("MailTrackingDefaultsDelegate","updateBookingDetailsForMail");
	 }
	
	/**
	 * Finds the resditCOnfiguration for an airline
	 * @param companyCode
	 * @param carrierId
	 * @return ResditConfigurationVO
	 * @throws BusinessDelegateException
	 */ 
	 public ResditConfigurationVO findResditConfigurationForAirline(
			 String companyCode, int carrierId) 
	 	throws BusinessDelegateException {
		 log.entering("MailTrackingDefaultsDelegate", "findResditConfiguration");
		 return despatchRequest("findResditConfigurationForAirline", 
				 companyCode, carrierId);
	 }
	 /**
	  * @param resditConfigVO
	  * @throws BusinessDelegateException
	  */
	 public void saveResditConfiguration(ResditConfigurationVO resditConfigVO) 
	 throws BusinessDelegateException {
		 log.entering("MailTrackingDefaultsDelegate", "saveResditConfiguration");
		 despatchRequest("saveResditConfiguration", resditConfigVO);
	 }
	 /**
	  * @param companyCode
	  * @param officeOfExchange
	  * @return
	  * @throws BusinessDelegateException
	  */
	 public String findPAForOfficeOfExchange(
			 String companyCode, String officeOfExchange) 
	 throws  BusinessDelegateException {
		 log.entering("MailTrackingDefaultsDelegate", "findPAForOfficeOfExchange");
		 return despatchRequest("findPAForOfficeOfExchange", companyCode,
				 officeOfExchange);
	 }
	 
	 /**
	  * For Trigger 
	  * Feb 9, 2007, A-1739
	  * @param carditEnquiryVO
	  * @throws BusinessDelegateException
	  */
	 public void sendResdit(CarditEnquiryVO carditEnquiryVO)
	 throws  BusinessDelegateException {
		 log.entering("MailTrackingDefaultsDelegate", "sendResdit");
		 despatchRequest("sendResdit", carditEnquiryVO);
		 log.exiting("MailTrackingDefaultsDelegate", "sendResdit");
	 }
	 /**
	  * This method prints Control Document Report
	  * @author A-1883
	  * @param operationalFlightVO
	  * @throws BusinessDelegateException
	  */
	 public void generateControlDocumentReport(OperationalFlightVO 
				operationalFlightVO) throws  BusinessDelegateException {
		 log.entering("MailTrackingDefaultsDelegate", "generateControlDocumentReport");
		 despatchRequest("generateControlDocumentReport", operationalFlightVO);
		 log.exiting("MailTrackingDefaultsDelegate", "generateControlDocumentReport");
	 }
	 
	 
	 /**
	  * For upload peakMessage
	  * Apr 4, 2007, a-1739
	  * @param peakMessageVO
	  * @return the processingStatus
	  * @throws BusinessDelegateException
	  */
	 public String receivePeakMessage(PeakMessageVO peakMessageVO) 
	 	throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate", "receivePeakMessage");
		return despatchRequest("receivePeakMessage", peakMessageVO); 
	 }
	 
	 /**
	 	 * @param mailActualDetailFilterVO
	 	 * @return Page<MailActualDetailVO>
	 	 * @throws SystemException
	 	 */
	     public Page<MailActualDetailVO> findMailActivityDetails(MailActualDetailFilterVO mailActualDetailFilterVO)
	     	throws BusinessDelegateException {	    	 
	    	 log.entering("MailTrackingDefaultsDelegate","findMailActivityDetails");	    	 
	         return despatchRequest("findMailActivityDetails",mailActualDetailFilterVO);
	     }
	     
	 	/**
	 	 * @author A-1876 This method is used to fetch audit details
	 	 * @param mailAuditFilterVO
	 	 * @return Collection<AuditDetailsVO>
	 	 * @throws BusinessDelegateException
	 	 */
	 	public Collection<AuditDetailsVO> findDSNAuditDetails(MailAuditFilterVO mailAuditFilterVO)
	 			throws BusinessDelegateException {
	 		log.entering("MailTrackingDefaultsDelegate", "findDSNAuditDetails");
	 		return despatchRequest("findDSNAuditDetails",mailAuditFilterVO);
	 	}
	 	
	 	/**
	 	 * @author A-1876 This method is used to fetch audit details
	 	 * @param mailAuditFilterVO
	 	 * @return Collection<AuditDetailsVO>
	 	 * @throws BusinessDelegateException
	 	 */
	 	public Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterVO mailAuditFilterVO)
	 			throws BusinessDelegateException {
	 		log.entering("MailTrackingDefaultsDelegate", "findCONAuditDetails");
	 		return despatchRequest("findCONAuditDetails",mailAuditFilterVO);
	 	}
	 	
	 	/**
	 	 * @author A-1876 This method is used to fetch audit details
	 	 * @param mailAuditFilterVO
	 	 * @return Collection<AuditDetailsVO>
	 	 * @throws BusinessDelegateException
	 	 */
	 	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterVO mailAuditFilterVO)
	 			throws BusinessDelegateException {
	 		log.entering("MailTrackingDefaultsDelegate", "findAssignFlightAuditDetails");
	 		return despatchRequest("findAssignFlightAuditDetails",mailAuditFilterVO);
	 	}
	 	
	     
	     /**
	      * @author A-2667
	      * @param carditEnquiryFilterVO
	      * @return
	      * @throws BusinessDelegateException
	      */
	     public Page<MailbagVO> findConsignmentDetails(CarditEnquiryFilterVO carditEnquiryFilterVO,int pageNumber)
	     	throws BusinessDelegateException{
	    	 log.entering("MailTrackingDefaultsDelegate","findConsignmentDetails");
	         return despatchRequest("findConsignmentDetails",carditEnquiryFilterVO,pageNumber);
	     }
	     
	     /**
	      * @author A-2553
	      * @param carditEnquiryFilterVO
	      * @return
	      * @throws BusinessDelegateException
	      */
	     public Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO,int pageNumber)
	     	throws BusinessDelegateException{
	    	 log.entering("MailTrackingDefaultsDelegate","findCarditMailDetails");
	         return despatchRequest("findCarditMails",carditEnquiryFilterVO,pageNumber);
	     }
	   
	     /**
	      * @author A-2553
	      * @param mailbagVOs
	      * @param containerVO
	      * @return
	      * @throws BusinessDelegateException
	      */
	     public void moveMailbagsInManifest(Collection<MailbagVO> mailbagVOs,ContainerVO containerVO,OperationalFlightVO operationalFlightVO,Collection<LockVO> locks)
	     	throws BusinessDelegateException{
	    	 log.entering("MailTrackingDefaultsDelegate","moveMailbagsInManifest");	    	 
	         despatchRequest("moveMailbagsInManifest",locks,mailbagVOs,containerVO,operationalFlightVO);
	     } 
	     
	     
	   /**
	    * @author A-1936
	    * Added By Karthick V as the part of the NCA Mail Tracking Cr 
	    * @param mailbagsInInventory
	    * @param poaCode
	    * @throws BusinessDelegateException
	    */  
	   public void deliverMailBagsFromInventory(Collection<MailInInventoryListVO> mailbagsInInventory)
	     throws BusinessDelegateException{
		   log.entering("MailTrackingDefaultsDelegate","deliverMailBagsFromInventory");	    	 
	       despatchRequest("deliverMailBagsFromInventory",mailbagsInInventory);
	       log.exiting("MailTrackingDefaultsDelegate","deliverMailBagsFromInventory");	 
	   }
	     
	    
	   /**
	    * @author A-1936
	    * Added By Karthick V as the part of the NCA Mail Tracking Cr 
	    * @param flightSummaryVOs
	    * @param operationalFlightVo
	    * @throws BusinessDelegateException
	    */  
	   public void createBookingsForMailsInFlight(
		        Collection<MailInFlightSummaryVO> flightSummaryVOs, OperationalFlightVO 
		        operationalFlightVo)  throws BusinessDelegateException{
	   log.entering("MailTrackingDefaultsDelegate","createBookingsForMailsInFlight");	    	 
       despatchRequest("createBookingsForMailsInFlight",flightSummaryVOs,operationalFlightVo);
       log.exiting("MailTrackingDefaultsDelegate","createBookingsForMailsInFlight");	 
   }
	   
	   /**
	    * @author A-1876
	    * Added By Roopak V S as the part of the NCA Mail Tracking Cr 
	    * @param mailbagsInInventory
	    * @param toContainerVo
	    */  
	   public void transferMailBagsFromInventory(
			   Collection<MailInInventoryListVO> mailbagsInInventory,
			   ContainerVO toContainerVo)throws BusinessDelegateException{
		   log.entering("MailTrackingDefaultsDelegate","transferMailBagsFromInventory");	    	 
	       despatchRequest("transferMailBagsFromInventory",mailbagsInInventory,toContainerVo);
	       log.exiting("MailTrackingDefaultsDelegate","transferMailBagsFromInventory");	
       }
	   
	   /**
	    * Map for reports
	    * Sep 14, 2007, A-1739
	    * @param opFlightVO
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public ControlDocumentVO findControlDocumentsForPrint(OperationalFlightVO opFlightVO)
	   					throws BusinessDelegateException {
		   log.entering("MailTrackingDefaultsDelegate","findControlDocumentsForPrint");	    	 
	       return despatchRequest("findControlDocumentsForPrint",opFlightVO);
	   }
	   
	   
	  
	   
	   	   /**
	    * @author a-1936
	    * @param containers
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public Collection<ContainerDetailsVO> findMailbagsInContainer(Collection<ContainerDetailsVO> containers)
		     throws BusinessDelegateException{
		    log.entering("Mail Tracking Defaults Delegate", "findMailbagsInContainer");
		    return despatchRequest("findMailbagsInContainer",containers);
	   } 
	   
	   /**
	    * @author a-1936
	    * @param containers
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public void deliverContainersFromInventory(Collection<ContainerInInventoryListVO> containerInInventory)
		     throws BusinessDelegateException{
		    log.entering("Mail Tracking Defaults Delegate", "deliverContainersFromInventory");
		    despatchRequest("deliverContainersFromInventory",containerInInventory);
	   } 
	   
	   
	   /**
	    * @author a-1936
	    * This method is  used to list all  the containers  and its respective DSNS present in the Flight...
	    * @param operationalFlightVo
	    * @return
	    * @throws BusinessDelegateException
	    */ 
	   public MailManifestVO findContainersInFlightForManifest(OperationalFlightVO operationalFlightVo)
	       throws BusinessDelegateException{
		   log.entering("MailTrackingDefaults Delegate","findContainersInFlight");
		   return    despatchRequest("findContainersInFlightForManifest",operationalFlightVo);
	   }
	    
	   /**
	    * 
	    * @author a-1936
	    * This method is used to  fetch the Mail bags and the Despacthes inside the Containers for the Manifest..
	    * @param containers
	    * @return
	    * @throws BusinessDelegateException
	    */
	    public Collection<ContainerDetailsVO> findMailbagsInContainerForManifest(Collection<ContainerDetailsVO> containers)
		     throws BusinessDelegateException{
		    log.entering("Mail Tracking Defaults Delegate", "findMailbagsInContainer");
		    return despatchRequest("findMailbagsInContainerForManifest",containers);
	    }
	    /**
		    * 
		    * @author a-1936
		    * This method is used to  fetch the Mail bags and the Despacthes inside the Containers for the ImportManifest..
		    * @param containers
		    * @return
		    * @throws BusinessDelegateException
		    */
		    public Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(Collection<ContainerDetailsVO> containers)
			     throws BusinessDelegateException{
			    log.entering("Mail Tracking Defaults Delegate", "findMailbagsInContainerForImportManifest");
			    return despatchRequest("findMailbagsInContainerForImportManifest",containers);
		    }
	    
	   /**
	    * @author A-3227 RENO K ABRAHAM
	    * This method call fetches the boolean value corresponding to a specific validation
	    * The method return TRUE,if all the mailbag in a particular container needs to be delivered to a single PA
	    * The Method return FALSE,if the container contains mailbags of different PA's
	    * @param mailbagVOs
	    * @return
	    * @throws BusinessDelegateException
	    */ 
	   public Boolean validatePACodeForMailBags(String companyCode,Collection<String> doe)
	   		throws BusinessDelegateException{
		   log.entering("MailTrackingDefaults Delegate", "validatePACodeForMailBags");
		    return despatchRequest("validatePACodeForMailBags",companyCode,doe);
	   }
	   /**
	    * @author A-2553	    
	    * @param despatchDetailsVOs
	    * @param mailbagVOs
	    * @param containerVO
	    * @param print
	    * @return
	    * @throws BusinessDelegateException
	    */ 
	   //Modified as part of bug ICRD-97415 by A-5526 starts(return type void to TransferManifestVO)
	   public TransferManifestVO transferMail(Collection<DespatchDetailsVO> despatchDetailsVOs,
			   Collection<MailbagVO> mailbagVOs,ContainerVO containerVO,String toPrint)
	   		throws BusinessDelegateException{
		   log.entering("MailTrackingDefaults Delegate", "transferMail");
		    return despatchRequest("transferMail",despatchDetailsVOs,mailbagVOs,containerVO,toPrint);
		    
	   }
	   
	   /**
	    * @author A-3251 Sreejith P.C. for MailAlert message	    
	    * @param operationalFlightVO
	    * @param mailAcceptanceVO
	    * @return
	    * @throws BusinessDelegateException
	    */ 
	   public void closeFlightAcceptance(OperationalFlightVO operationalFlightVO,MailAcceptanceVO mailAcceptanceVO)throws BusinessDelegateException
	   { log.entering("MailTrackingDefaults Delegate", "closeFlightAcceptance");
	    despatchRequest("closeFlightAcceptance",operationalFlightVO,mailAcceptanceVO);
	    log.exiting("MailTrackingDefaults Delegate", "closeFlightAcceptance");		   
	   }
	  
	   /**
	    * @author A-3251 Sreejith P.C. for MailAlert message	    
	    * @param operationalFlightVO
	    * @param mailAcceptanceVO
	    * @return
	    * @throws BusinessDelegateException
	    */ 
	   public void closeFlightManifest(OperationalFlightVO operationalFlightVO,MailManifestVO mailManifestVO )throws BusinessDelegateException
	   { log.entering("MailTrackingDefaults Delegate", "closeFlightManifest");
	    despatchRequest("closeFlightManifest",operationalFlightVO,mailManifestVO);
	    log.exiting("MailTrackingDefaults Delegate", "closeFlightManifest");		   
	   }
	   
	   
	   
	   
	   
	   
	   
	   
	   /**
	    * @author a-1936
	    * This method is used to find the Transfer Manifest for the Different Transactions 
	    * @param tranferManifestFilterVo
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo)
	   throws BusinessDelegateException{
		   log.entering("MailTrackingDefaults Delegate", "findTransferManifest");
		     return despatchRequest("findTransferManifest",tranferManifestFilterVo);
		    
		   
	   }
	   
	   /**
	    * @author A-3227
	    * @param dsnVO
	    * @param mode
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public Collection<DespatchDetailsVO> findDespatchesOnDSN(DSNVO dsnVO, String mode)throws BusinessDelegateException {
		   log.entering("MailTrackingDefaults Delegate", "findDespatchesOnDSN");
		     return despatchRequest("findDespatchesOnDSN",dsnVO,mode);
	   }
	   
	   /**
	    * @author a-2107
	    * @param scannedMailDetails
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public Collection<ScannedMailDetailsVO> reassignScannedMailbags(Collection<ScannedMailDetailsVO> scannedMailDetails)throws BusinessDelegateException {
		   log.entering("MailTrackingDefaults Delegate", "reassignScannedMailbags");
		     return despatchRequest("reassignScannedMailbags",scannedMailDetails);
	   }
	   
	  /**
	   * @A-2107
	   * @param scannedMailDetails
	   * @return
	   * @throws BusinessDelegateException
	   */
	   public Collection<ScannedMailDetailsVO> transferScannedMailbags(Collection<ScannedMailDetailsVO> scannedMailDetails)throws BusinessDelegateException {
		   log.entering("MailTrackingDefaults Delegate", "transferScannedMailbags");
		     return despatchRequest("transferScannedMailbags",scannedMailDetails);
	   }
	   /**
	    * @author a-2107
	    * @param ScannedMailDetailsVO
	    * @return ScannedMailDetailsVO
	    * @throws BusinessDelegateException
	    */
	   
	   public Collection<ScannedMailDetailsVO> reassignScannedDespatches(Collection<ScannedMailDetailsVO> scannedMailDetails)throws BusinessDelegateException {
		   log.entering("MailTrackingDefaults Delegate", "reassignScannedDespatches");
		     return despatchRequest("reassignScannedDespatches",scannedMailDetails);
	   }
	   
	   /**
	    * @author a-2107
	    * @param consignmentFilterVO
	    * @return MailbagVO
	    * @throws BusinessDelegateException
	    */
	   
	   public Collection<MailbagVO> findCartIds(ConsignmentFilterVO consignmentFilterVO)throws BusinessDelegateException {
		   log.entering("MailTrackingDefaults Delegate", "findCartIds");
		     return despatchRequest("findCartIds",consignmentFilterVO);
	   }
	   
	   /**
	    * @author A-3227  - FEB 10, 2009
	    * @param companyCode
	    * @param despatchDetailsVOs
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public Collection<DespatchDetailsVO> validateConsignmentDetails(String companyCode,Collection<DespatchDetailsVO> despatchDetailsVOs)
	   throws BusinessDelegateException {
		   log.entering("MailTrackingDefaults Delegate", "validateConsignmentDetails");
		     return despatchRequest("validateConsignmentDetails",companyCode,despatchDetailsVOs);
	   }  	
	   
	   /**
	    * @author A-3227  - FEB 10, 2009
	    * @param companyCode
	    * @param despatchDetailsVOs
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public Collection<ArrayList<String>> findCityAndAirportForOE(String companyCode,Collection<String> officeOfExchanges)
	   throws BusinessDelegateException {
		   log.entering("MailTrackingDefaults Delegate", "findCityAndAirportForOE");
		     return despatchRequest("findCityAndAirportForOE",companyCode,officeOfExchanges);
	   }  	
	   
	   /**
	    * @author A-3227  - JUN 24, 2009
	    * @param companyCode
	    * @param despatchDetailsVOs
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public void saveConsignmentForManifestedDSN(ConsignmentDocumentVO consignmentDocumentVO)
	   throws BusinessDelegateException {
		   log.entering("MailTrackingDefaults Delegate", "saveConsignmentForManifestedDSN");
		   despatchRequest("saveConsignmentForManifestedDSN",consignmentDocumentVO);
		   log.exiting("MailTrackingDefaults Delegate", "saveConsignmentForManifestedDSN");
	   }  		
	   
	   /**
	    * @author A-3227  - NOV 24, 2009
	    * @param companyCode
	    * @param scannedMailSession
	    * @return
	    * @throws BusinessDelegateException
	    */
	   public ScannedMailDetailsVO findDetailsForUpload(ScannedMailDetailsVO scannedMailSession)
	   throws BusinessDelegateException {
		   log.entering("MailTrackingDefaults Delegate", "findDetailsForUpload");
		   return despatchRequest("findDetailsForUpload",scannedMailSession);
	   }  	
	   
	   /**
	    * This method fetches the latest Container Assignment
	    * irrespective of the PORT to which it is assigned.
	    * This to know the current assignment of the Container.
	    * 
	    * @param containerNumber
	    * @return
	    * @throws BusinessDelegateException
	    */
		public ContainerAssignmentVO findLatestContainerAssignment(String containerNumber)
		throws BusinessDelegateException {
			   log.entering("MailTrackingDefaults Delegate", "findLatestContainerAssignment");
			   return despatchRequest("findLatestContainerAssignment",containerNumber);
		   }  
		
		/**
		 * saveScannedOffloadMails
		 * @param scannedMailSession
		 * @throws BusinessDelegateException
		 */
		public void saveScannedOffloadMails(Collection<OffloadVO> OffloadVosForSave)
		throws BusinessDelegateException {
			log.entering("MailTrackingDefaults Delegate", "saveScannedOffloadMails");
			despatchRequest("saveScannedOffloadMails",OffloadVosForSave);
			log.exiting("MailTrackingDefaults Delegate", "saveScannedOffloadMails");
		} 
		/**
		 * saveScannedDeliverMails
		 * @param scannedMailSession
		 * @throws BusinessDelegateException
		 */
		public void saveScannedDeliverMails(Collection<MailArrivalVO> deliverVosForSave)
		throws BusinessDelegateException {
			log.entering("MailTrackingDefaults Delegate", "saveScannedDeliverMails");
			despatchRequest("saveScannedDeliverMails",deliverVosForSave);
			log.exiting("MailTrackingDefaults Delegate", "saveScannedDeliverMails");
		} 
		/**
		 * 
		 * @param conatinerstoAcquit
		 * @throws BusinessDelegateException
		 */
		public void autoAcquitContainers(Collection<ContainerDetailsVO> conatinerstoAcquit) 
		throws BusinessDelegateException{
			log .entering( MODULE , "autoAcquitContainers" ); 
			despatchRequest("autoAcquitContainers", conatinerstoAcquit);
		}
		/**
	     * @author A-4809
	     * findDSNMailbags
		 * @param dsnVO
		 * @return
		 * @throws BusinessDelegateException
		 */
		public Collection<MailbagVO> findDSNMailbags(DSNVO dsnVO)throws BusinessDelegateException {
			  log .entering( "MailTrackingDefaults" , "findDSNMailbags" );
			 return despatchRequest("findDSNMailbags", dsnVO);
			   
		   }
		/**
		 * 
		 * @author A-4809
		 * @param consignmentDocumentVO
		 * @throws BusinessDelegateException
		 */
		public void saveConsignmentDocumentFromManifest(Collection<ConsignmentDocumentVO> consignmentDocumentVOs)
		throws BusinessDelegateException{
			log.entering("MailTrackingDefaultsDelegate", "saveConsignmentDocumentFromManifest");
			despatchRequest("saveConsignmentDocumentFromManifest",consignmentDocumentVOs);
		}
		
		
		/**
		 * @author A-4809
		 * @param CompanyCode
		 * @param officeOfExchange
		 * @return
		 * @throws BusinessDelegateException
		 */
		public OfficeOfExchangeVO validateOfficeOfExchange(String companyCode,String officeOfExchange)
		throws BusinessDelegateException{
			log.entering("MailTrackingDefaultsDelegate", "validateOfficeOfExchange");
			return despatchRequest("validateOfficeOfExchange",companyCode,officeOfExchange);
		}
		/**
		 * 
		 * @param flightDetails
		 * @author A-2572
		 */
		public void validateULDsForOperation(FlightDetailsVO flightDetails) 
		throws BusinessDelegateException{
			log .entering( MODULE , "validateULDsForOperation" ); 
			despatchRequest("validateULDsForOperation", flightDetails);			
		}
		/**
		 * 
		 * @param mailbagVOs
		 * @param eventCode
		 * @return
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public Map<String,Collection<String>> flagResditsForMissedEvents(Collection<MailbagVO> mailbagVOs,String eventCode) 
		throws BusinessDelegateException{
			log .entering( MODULE , "flagResditsForMissedEvents" ); 
				return despatchRequest("flagResditsForMissedEvents", mailbagVOs,eventCode);
		}
		/**
		 * 
		 * @param residitRestrictonFilterVO
		 * @return
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public Collection<ResiditRestrictonVO> findResiditRestrictions(ResiditRestrictonFilterVO residitRestrictonFilterVO)
			throws BusinessDelegateException{ 
			log .entering( MODULE , "findResiditRestrictions" ); 
			return despatchRequest("findResiditRestrictions", residitRestrictonFilterVO);
		}   
		/**
		 * 
		 * @param residitRestrictonVOs
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public void saveResiditRestrictions(Collection<ResiditRestrictonVO> residitRestrictonVOs) 
			throws BusinessDelegateException{ 
			log .entering( MODULE , "saveResiditRestrictions" ); 
			despatchRequest("saveResiditRestrictions", residitRestrictonVOs); 
		}
		/**
		 * 
		 * @param mailReconciliationVOs
		 * @param operationalFlightVO
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public void acceptMailsForReconciliation(Collection<MailReconciliationVO> mailReconciliationVOs,
				OperationalFlightVO operationalFlightVO) throws BusinessDelegateException {
			despatchRequest("acceptMailsForReconciliation", mailReconciliationVOs,
					operationalFlightVO);
		}
		/**
		 * 
		 * @param operationalFlightVO
		 * @return
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public OperationalFlightVO updateOperationalFlightVO(OperationalFlightVO operationalFlightVO)
			throws BusinessDelegateException{
			return despatchRequest("updateOperationalFlightVO", operationalFlightVO);
		}
		/**
		 * 
		 * @param operationalFlightVOs
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public void closeFlightForReconciliation(Collection<OperationalFlightVO> operationalFlightVOs)
			throws BusinessDelegateException {
		 			log.entering(MODULE, "closeFlightForReconciliation");
		 			despatchRequest("closeFlightForReconciliation", operationalFlightVOs);
		}
		/**
		 * 
		 * @param operationalFlightVO
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public void finaliseMailsForReconciliation( OperationalFlightVO operationalFlightVO)
		 throws BusinessDelegateException {
			 despatchRequest("finaliseMailsForReconciliation", operationalFlightVO);
		}
		/**
		 * 
		 * @param operationalFlightVO
		 * @return
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public Page<OperationalFlightVO> findMailFlightDetails(
				OperationalFlightVO operationalFlightVO)
				throws BusinessDelegateException {
			return despatchRequest("findMailFlightDetails", operationalFlightVO);
		}	
		/**
		 * 
		 * @param operationalFlightVO
		 * @return
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public Collection<MailReconciliationVO> findMailsForReconciliation(
				 OperationalFlightVO operationalFlightVO)
				 throws BusinessDelegateException {
			 return despatchRequest("findMailsForReconciliation",
					 operationalFlightVO);
		}
		/**
		 * 
		 * @param operationalFlightVOs
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public void reopenFlight(Collection<OperationalFlightVO> operationalFlightVOs)
			throws BusinessDelegateException {
			log.entering(MODULE, "reopenFlight");
			despatchRequest("reopenFlightForReconcile", operationalFlightVOs);
		}
		/**
		 * 
		 * @param consignmentFilterVO
		 * @return
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public Collection<ConsignmentDocumentVO> findDeliverBill(ConsignmentFilterVO consignmentFilterVO)
			throws BusinessDelegateException {
			log.entering("MailTrackingDefaults Delegate", "findDeliverBill");
			return despatchRequest("findDeliverBill",consignmentFilterVO);
		}
		/**
		 * 
		 * @param reportConsignmentList
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public void print46Report(Collection<ConsignmentDocumentVO> reportConsignmentList)
			throws BusinessDelegateException {
			log.entering("MailTrackingDefaults Delegate", "print46Report");
			despatchRequest("print46Report",reportConsignmentList);
		}
		/**
		 * 
		 * @param mailReconciliationFilterVO
		 * @return
		 * @throws BusinessDelegateException
		 */
		public String generateMailExceptionReport(MailReconciliationFilterVO mailReconciliationFilterVO)
		throws BusinessDelegateException{
			log.entering(MODULE, "generateMailExceptionReport");
			return despatchRequest("generateMailExceptionReport", mailReconciliationFilterVO);
		}
		/**
		 * 
		 * @param mailReconciliationFilterVO
		 * @return
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public Page<MailReconciliationDetailsVO> listReconciliationDetails(MailReconciliationFilterVO mailReconciliationFilterVO)
			throws BusinessDelegateException {
			log.entering(MODULE, "listReconciliationDetails");
			return despatchRequest("listReconciliationDetails", mailReconciliationFilterVO);
		}
		/**
		 * 
		 * @param mailReconciliationDetailsVO
		 * @return
		 * @throws BusinessDelegateException
		 * @author A-2572
		 */
		public Page<MessageVO> findMessageDetails(MailReconciliationDetailsVO mailReconciliationDetailsVO)
			throws BusinessDelegateException {
			log.entering(MODULE, "listReconciliationDetails");
			return despatchRequest("findMessageDetails", mailReconciliationDetailsVO);
		}

		/**
		 * 
		 * @param consignmentDocumentVO
		 * @return
		 */
		public Object saveNationalConsignmentDetails(ConsignmentDocumentVO consignmentDocumentVO) throws BusinessDelegateException{
			log.entering(MODULE, "saveNationalConsignmentDetails");
			return despatchRequest("saveNationalConsignmentDetails", consignmentDocumentVO);
		}


		/**
		 * 
		 * @param consignmentDocumentVO
		 * @return
		 */
		public Object findNationalConsignmentDetails(ConsignmentFilterVO consignmentFilterVO) throws BusinessDelegateException{
			log.entering(MODULE, "findNationalConsignmentDetails");
			return despatchRequest("findNationalConsignmentDetails", consignmentFilterVO);
		}
		
		/**
		 * @author a-5137 This method is used to save remarks 
		 * @param pOMailStatementVOs
		 * @return void
		 * @throws BusinessDelegateException
		 */
		
		public void saveRemarksFromPOMailStatement(Collection<POMailStatementVO> pOMailStatementVOs) throws BusinessDelegateException {
			log.entering(MODULE, "saveRemarksFromPOMailStatement");
			 despatchRequest("saveRemarksFromPOMailStatement", pOMailStatementVOs);
		}
		/**
		 * @author A-2037 This method is used to find the DSNs.
		 * @param dSNEnquiryFilterVO
		 * @param pageNumber
		 * @return
		 * @throws BusinessDelegateException
		 */
		public Page<DespatchDetailsVO> findDSNsForNational(
				DSNEnquiryFilterVO dSNEnquiryFilterVO, int pageNumber)
				throws BusinessDelegateException {
			log.entering(MODULE, "findDSNsForNational");
			return despatchRequest("findDSNsForNational", dSNEnquiryFilterVO, pageNumber);
			
		}
		
		/**
		 * @author A-5526 This method is used to findContainerAssignmentForUpload.
		 * @param containerVO
		 * @return
		 * @throws BusinessDelegateException
		 */
		public ContainerAssignmentVO findContainerAssignmentForUpload(ContainerVO containerVO)
		throws BusinessDelegateException {
			log.entering(MODULE, "findContainerAssignmentForUpload");
			return despatchRequest("findContainerAssignmentForUpload", containerVO);
		}
		
		/**
		 * @author a-5526 This method is used to saveAndProcessMailBags
		 * @param scannedMailDetailsVO
		 * @throws BusinessDelegateException
		 */
		public void saveAndProcessMailBags(ScannedMailDetailsVO scannedMailDetailsVO
				)
		throws BusinessDelegateException {
			log.entering(MODULE, "saveAndProcessMailBags");
			despatchRequest("saveAndProcessMailBags", scannedMailDetailsVO);
		}
		
		/**
		 * Added by A-5526
		 * @param mailBagVOs
		 * @param scanningPort
		 * @return
		 * @throws BusinessDelegateException
		 */
		public ScannedMailDetailsVO saveMailUploadDetails(Collection<MailUploadVO> mailBagVOs, String scanningPort)
		throws BusinessDelegateException {
			log.entering(MODULE, "saveMailUploadDetails");
			return despatchRequest("saveMailUploadDetails", mailBagVOs,scanningPort);            
		}
		
		

		public Object[] getTxnParameters(String companyCode,String txnId) throws BusinessDelegateException {
			log.entering(MODULE, "getTxnParameters");
			return despatchRequest("getTxnParameters",companyCode, txnId);
		}
		public void resolveTransaction(String companyCode, String txnId, String remarks)
		throws BusinessDelegateException {
	log.entering("AdminMonitoringDelegate", "resolveTransaction");
	despatchRequest("resolveTransaction", companyCode,txnId,remarks);
}	
		
		/**
		 * 	Method		:	findMailOnHandDetails
		 *	Added by 	:	A-6371 on 09-oct-2014
		 * 	Used for 	:
		 *	Parameters	:	@param SearchContainerFilterVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws BusinessDelegateException 
		 *	Return type	: 	Page<MailOnHandDetailsVO>
		 */
		public Page<MailOnHandDetailsVO> findMailOnHandDetails(SearchContainerFilterVO searchContainerFilterVO,int pageNumber) throws BusinessDelegateException
		{
			log.entering("MailTrackingDefaultdelegate", "finds mail on hand list details");
			return despatchRequest("findMailOnHandDetails", searchContainerFilterVO,pageNumber);
		}

        /**
         * 	Method		:	checkEmbargoForMail
		 *	Added by 	:	A-4810
         * @param shipmentDetailsVos
         * @return
         * @throws BusinessDelegateException
         */
	    public Collection<EmbargoDetailsVO> checkEmbargoForMail(
				Collection<ShipmentDetailsVO> shipmentDetailsVos) throws BusinessDelegateException {
			log.entering(MODULE, "checkEmbargoForMail");
			return despatchRequest("checkEmbargoForMail", shipmentDetailsVos);
		}

	    /**
		 * @author a-5160 This method is used to validate Mail Flight
		 * @param flightFilterVO
		 * @return Collection<FlightValidationVO>
		 * @throws BusinessDelegateException
		 */
		public Collection<FlightValidationVO> validateMailFlight(
				FlightFilterVO flightFilterVO) throws BusinessDelegateException {
			log.entering(MODULE, "validateFlight");
			return despatchRequest("validateMailFlight", flightFilterVO);
		}
	
		/**
		 * @author A-4810
		 * @param companyCode
		 * @param airportCode
		 * @return
		 * @throws BusinessDelegateException
		 * Added for icrd-95515
		 */
		public  Collection<String> findOfficeOfExchangesForAirport(
				String companyCode, String airportCode)
		   throws BusinessDelegateException {
			   log.entering("MailTrackingDefaults Delegate", "findOfficeOfExchangesForAirport");
			     return despatchRequest("findOfficeOfExchangesForAirport",companyCode,airportCode);
		   } 
	
		public void saveUndoArrivalDetails(MailArrivalVO mailArrivalVO) throws BusinessDelegateException{
			  despatchRequest("undoArriveContainer",mailArrivalVO);
			
		}
	
		public  Collection<ContainerVO>  findAllULDsInAssignedFlight(
				FlightValidationVO reassignedFlightValidationVO)
		   throws BusinessDelegateException {
			   log.entering("MailTrackingDefaults Delegate", "findAllULDsInAssignedFlight");
			     return despatchRequest("findAllULDsInAssignedFlight",reassignedFlightValidationVO);
		   } 
	
		public HashMap<String, String> findAuditTransactionCodes(
				Collection<String> entities, boolean b, String companyCode)  throws BusinessDelegateException {
			log.entering("MailTrackingDefaults Delegate", "findAuditTransactionCodes");
			return despatchRequest("findAuditTransactionCodes",entities,b,companyCode);
		}
		public Collection<MailBagAuditHistoryVO> findMailAuditHistoryDetails(MailAuditHistoryFilterVO mailAuditHistoryFilterVO ) throws BusinessDelegateException{
			return despatchRequest("findMailAuditHistoryDetails",mailAuditHistoryFilterVO);
		}
		
		/**
		 * @author a-6245
		 * @param mailArrivalVOs
		 * @param locks
		 * @throws BusinessDelegateException
		 */
		public void saveChangeFlightDetails(Collection<MailArrivalVO>mailArrivalVOs,Collection<LockVO> locks) throws BusinessDelegateException{
			log.entering("MailTrackingDefaultsDelegate", "saveChangeFlightDetails");
			despatchRequest("saveChangeFlightDetails",locks,mailArrivalVOs);
			log.exiting("MailTrackingDefaultsDelegate", "saveChangeFlightDetails");
		}
		/**
		 * @author A-5526 This method is used to save the MLD Configurations.
		 * @param mLDConfigurationVOs
		 * @throws BusinessDelegateException
		 */
		public void saveMLDConfigurations (
				Collection<MLDConfigurationVO> mLDConfigurationVOs)
		throws BusinessDelegateException {
			log.entering("MailTrackingDefaultsDelegate", "saveMLDConfiguarions");
			despatchRequest("saveMLDConfigurations", mLDConfigurationVOs);
		}
		/**
		 * @author A-5526 This method is used to find the MLD Configurations.
		 * @param mLDConfigurationFilterVO
		 * @throws BusinessDelegateException
		 */
		
		public Collection<MLDConfigurationVO> findMLDCongfigurations(MLDConfigurationFilterVO mLDConfigurationFilterVO ) throws BusinessDelegateException{
			return despatchRequest("findMLDCongfigurations",mLDConfigurationFilterVO);
		}
		
		public Collection<MailUploadVO> createMailScanVOSForErrorStamping(
				Collection<MailWebserviceVO> mailWebserviceVOs,String scannedPort,StringBuilder errorString, String errorFromMapping ) throws BusinessDelegateException {
			log.entering(MODULE, "createMailScanVOS");
			return despatchRequest("createMailScanVOSForErrorStamping", mailWebserviceVOs,scannedPort,errorString,errorFromMapping);
		}

		public ScannedMailDetailsVO performMailOperationForGHA(
				Collection<MailWebserviceVO> mailWebServiceVos,
				String scannedPort) throws BusinessDelegateException  {
			return despatchRequest("performMailOperationForGHA", mailWebServiceVos,scannedPort);
		}
		/**
		 * @author A-7871 
		 * for ICRD-257316
		 * @param containerDetailsVO
		 * @throws BusinessDelegateException
		 */
		public int findMailbagcountInContainer(
				ContainerVO containerVO) throws BusinessDelegateException  {
			return despatchRequest("findMailbagcountInContainer", containerVO);
		}
		

		/**
		 * 	Method		:	MailTrackingDefaultsDelegate.findMailDetailsForMailTag
		 *	Added by 	:	a-6245 on 07-Jun-2017
		 * 	Used for 	:
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@param mailId
		 *	Parameters	:	@return
		 *	Parameters	:	@throws BusinessDelegateException 
		 *	Return type	: 	MailbagVO
		 */
		public MailbagVO findMailDetailsForMailTag(
				String companyCode, String mailId)
						throws BusinessDelegateException {
			return despatchRequest("findMailDetailsForMailTag",companyCode,mailId);
	 }
		/**
		 * 
		 * 	Method		:	MailTrackingDefaultsDelegate.findMailbagIdForMailTag
		 *	Added by 	:	a-6245 on 22-Jun-2017
		 * 	Used for 	:
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@param mailId
		 *	Parameters	:	@return
		 *	Parameters	:	@throws BusinessDelegateException 
		 *	Return type	: 	MailbagVO
		 */
		public MailbagVO findMailbagIdForMailTag(MailbagVO mailbagVO)
						throws BusinessDelegateException {
			return despatchRequest("findMailbagIdForMailTag",mailbagVO);
	 }
		/**
		 * 
		 * 	Method		:	MailTrackingDefaultsDelegate.findAgentCodeForPA
		 *	Added by 	:	U-1267 on 07-Nov-2017
		 * 	Used for 	:	ICRD-211205
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@param paCode
		 *	Parameters	:	@return
		 *	Parameters	:	@throws BusinessDelegateException 
		 *	Return type	: 	String
		 */
		public String findAgentCodeForPA(String companyCode, String paCode)
				throws BusinessDelegateException {
			return despatchRequest("findAgentCodeForPA", companyCode,paCode);
	
		}
		/**
		 * 
		 * 	Method		:	MailTrackingDefaultsDelegate.detachAWBDetails
		 *	Added by 	:	U-1267 on 09-Nov-2017
		 * 	Used for 	:	ICRD-211205
		 *	Parameters	:	@param containerDetailsVO
		 *	Parameters	:	@throws BusinessDelegateException 
		 *	Return type	: 	void
		 */
		public void detachAWBDetails(ContainerDetailsVO containerDetailsVO)
				throws BusinessDelegateException {
			 despatchRequest("detachAWBDetails", containerDetailsVO);
		}
		/**
		 * 
		 * @param containerVOs
		 * @param operationalFlightVO
		 * @param printFlag
		 * @return
		 * @throws BusinessDelegateException
		 */
		public TransferManifestVO transferContainersAtExport(Collection<ContainerVO>
		containerVOs,OperationalFlightVO operationalFlightVO,String printFlag)
		throws  BusinessDelegateException {
			log.entering("MailTrackingDefaultsDelegate","transferContainersAtExport");
			return despatchRequest("transferContainersAtExport", containerVOs,
					operationalFlightVO,printFlag);
			
		}
		/**
		    * @author A-7371    
		    * @param despatchDetailsVOs
		    * @param mailbagVOs
		    * @param containerVO
		    * @param print
		    * @return
		    * @throws BusinessDelegateException
		    */ 
		   
		   public TransferManifestVO transferMailAtExport(
				   Collection<MailbagVO> mailbagVOs,ContainerVO containerVO,String toPrint)
		   		throws BusinessDelegateException{ 
			   log.entering("MailTrackingDefaults Delegate", "transferMailAtExport");
			    return despatchRequest("transferMailAtExport",mailbagVOs,containerVO,toPrint);
			    
		}
		/**
		 * @author A-7540
		 * @param reportPublishJobVO
		 * @throws BusinessDelegateException
		 */
		public void generateResditPublishReport(String companyCode,String paCode,int days)
				throws BusinessDelegateException {
			 despatchRequest("generateResditPublishReport", companyCode,paCode,days);
		}
			
			   
			/**
				 * @author A-8061
				 * @param carditEnquiryFilterVO
				 * @throws BusinessDelegateException
			 */
			public String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO)
						throws BusinessDelegateException {
				  log.entering("MailTrackingDefaults Delegate", "findGrandTotals");
					return  despatchRequest("findGrandTotals", carditEnquiryFilterVO);
			}	
			
			public Collection<ErrorVO> saveMailServiceLevelDtls(Collection<MailServiceLevelVO> mailServiceLevelVOs) 
					throws BusinessDelegateException{
				log.entering("MailTrackingDefaults Delegate", "saveMailServiceLevelDtls");
				return despatchRequest("saveMailServiceLevelDtls", mailServiceLevelVOs);
			}
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.listPostalCalendarDetails
			 *	Added by 	:	A-8164 on 04-Jul-2018
			 * 	Used for 	:	ICRD-236925
			 *	Parameters	:	@param uSPSPostalCalendarFilterVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Collection<USPSPostalCalendarVO>
			 */
			public Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
					  USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO)
					throws BusinessDelegateException{
				log.entering("MailTrackingDefaults Delegate", "listPostalCalendarDetails");
				return despatchRequest("listPostalCalendarDetails", uSPSPostalCalendarFilterVO);
			}
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.savePostalCalendar
			 *	Added by 	:	A-8164 on 04-Jul-2018
			 * 	Used for 	:	ICRD-236925
			 *	Parameters	:	@param uSPSPostalCalendarVOs
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	void
			 */
			public void savePostalCalendar (
					ArrayList<USPSPostalCalendarVO> uSPSPostalCalendarVOs) throws BusinessDelegateException{
				log.entering("MailTrackingDefaultsDelegate", "savePostalCalendar");
				despatchRequest("savePostalCalendar", uSPSPostalCalendarVOs);
				
			}
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.saveContractDetails
			 *	Added by 	:	A-6986 on 23-Jul-2018
			 * 	Used for 	:	ICRD-252821
			 *	Parameters	:	@param gpaContractVOs
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	void
			 */
			public void saveContractDetails (
					Collection<GPAContractVO> gpaContractVOs) throws BusinessDelegateException{
				log.entering("MailTrackingDefaultsDelegate", "saveContractDetails");
				  despatchRequest("saveContractDetails", gpaContractVOs);
				
			}
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.listContractDetails
			 *	Added by 	:	A-6986 on 23-Jul-2018
			 * 	Used for 	:	ICRD-252821
			 *	Parameters	:	@param gpaContractFilterVO
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Collection<GPAContractVO>
			 */
			public Collection<GPAContractVO> listContractDetails (
					GPAContractFilterVO gpaContractFilterVO) throws BusinessDelegateException{
				log.entering("MailTrackingDefaultsDelegate", "listContractdetails");
				return despatchRequest("listContractDetails", gpaContractFilterVO);
				
				
			}
			
			/**
			 * @author A-7540
			 * @param newMailbgVOs
			 * @param isScanned
			 * @return
			 * @throws BusinessDelegateException
			 */
			public ScannedMailDetailsVO doLATValidation(Collection<MailbagVO> newMailbgVOs,
					boolean isScanned) throws BusinessDelegateException{
				log.entering("MailTrackingDefaults Delegate", "doLATValidation");
				return despatchRequest("doLATValidation", newMailbgVOs,isScanned);
			}	
            /**
			 * @author A-6986
			 * @param mailHandoverVOs
			 * @throws BusinessDelegateException
			 */
			public void saveMailHandoverDetails(Collection<MailHandoverVO> mailHandoverVOs)
					throws BusinessDelegateException {
				 log.entering("MailTrackingDefaultsDelegate", "saveMailHandoverDetails");
				 despatchRequest("saveMailHandoverDetails", mailHandoverVOs);
				 log.exiting("MailTrackingDefaultsDelegate", "saveMailHandoverDetails");
			}
			/**
			 * @author A-6986
			 * @param mailHandoverFilterVO
			 * @throws BusinessDelegateException
			 */
			public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO,int pageNumber )
					throws BusinessDelegateException {
				log.entering("MailTrackingDefaultsDelegate", "findMailHandoverDetails");
				
				return despatchRequest("findMailHandoverDetails", mailHandoverFilterVO,pageNumber);
			}

			
			/**
			 * @author A-7371
			 * @param actualAirport
			 * @param eventAirport
			 * @param eventCode
			 * @param paCode
			 * @return
			 * @throws BusinessDelegateException
			 */
			public boolean validateCoterminusairports(String actualAirport,String eventAirport,
					String eventCode,String paCode,LocalDate dspDate)throws BusinessDelegateException{
				log.entering("MailTrackingDefaults Delegate", "validateCoterminusairports");
				return despatchRequest("validateCoterminusairports", actualAirport,eventAirport,eventCode,paCode,dspDate);
				
			}
			
			/**
			 * @author A-8236
			 * @param mailBagVO
			 * @param scanningPort
			 * @return
			 * @throws BusinessDelegateException
			 */
			public ScannedMailDetailsVO saveMailUploadDetailsForAndroid(MailUploadVO mailBagVO, String scanningPort)
			throws BusinessDelegateException {
				log.entering(MODULE, "saveMailUploadDetailsForAndroid");
				return despatchRequest("saveMailUploadDetailsForAndroid", mailBagVO,scanningPort);            
			}
			// For initial Main List
 public Page<MailAcceptanceVO> findOutboundFlightsDetails(OperationalFlightVO operationalFlightVO,int pageNumber)
					 throws BusinessDelegateException {
				 return despatchRequest("findOutboundFlightsDetails",operationalFlightVO,pageNumber);
			}			
			// To get Container details
			public Page<ContainerDetailsVO> getAcceptedContainers(
						OperationalFlightVO operationalFlightVO, int pageNumber)
				throws BusinessDelegateException {
					log.entering(MODULE, "getAcceptedContainers");
					return despatchRequest("getAcceptedContainers",operationalFlightVO,pageNumber);
				}
			public Page<MailbagVO> getMailbagsinContainer(
					ContainerDetailsVO containerVO, int pageNumber)
			throws BusinessDelegateException {
				log.entering(MODULE, "getMailbagsinContainer");
				return despatchRequest("getMailbagsinContainer",containerVO,pageNumber);
			}
			public Page<DSNVO> getMailbagsinContainerdsnview(
					ContainerDetailsVO containerVO, int pageNumber)
			throws BusinessDelegateException {
				log.entering(MODULE, "getMailbagsinContainerdsnview");
				return despatchRequest("getMailbagsinContainerdsnview",containerVO,pageNumber);
			}
			public MailbagVO findCarditSummaryView(CarditEnquiryFilterVO carditEnquiryFilterVO) 
					throws BusinessDelegateException {
				log.entering(MODULE, "findCarditSummaryView");
				return despatchRequest("findCarditSummaryView",carditEnquiryFilterVO);
			}
			public Page<MailbagVO> findGroupedCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber) 
					throws BusinessDelegateException {
				log.entering(MODULE, "findGroupedCarditMails");
				return despatchRequest("findGroupedCarditMails",carditEnquiryFilterVO,pageNumber);
			}
			public MailbagVO findLyinglistSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) 
					throws BusinessDelegateException {
				log.entering(MODULE, "findLyinglistSummaryView");
				return despatchRequest("findLyinglistSummaryView",mailbagEnquiryFilterVO);
			}
			public Page<MailbagVO> findGroupedLyingList(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) 
					throws BusinessDelegateException {
				log.entering(MODULE, "findGroupedLyingList");
				return despatchRequest("findGroupedLyingList",mailbagEnquiryFilterVO,pageNumber);
			}
//carrier mail list
 public Page<MailAcceptanceVO> findOutboundCarrierDetails(OperationalFlightVO operationalFlightVO,int pageNumber)
					 throws BusinessDelegateException {
				 return despatchRequest("findOutboundCarrierDetails",operationalFlightVO,pageNumber);
			}
			 public Page<MailbagVO> getMailbagsinCarrierContainer(
						ContainerDetailsVO containerVO, int pageNumber)
				throws BusinessDelegateException {
					log.entering(MODULE, "getMailbagsinCarrierContainer");
					return despatchRequest("getMailbagsinCarrierContainer",containerVO,pageNumber);
				}
			 //ned to confirm if required
			 public Page<DSNVO>  getMailbagsinCarrierdsnview(
						ContainerDetailsVO containerVO, int pageNumber)
				throws BusinessDelegateException {
					log.entering(MODULE, "getMailbagsinCarrierdsnview");
					return despatchRequest("getMailbagsinCarrierdsnview",containerVO,pageNumber);
			}
			 public Collection<DSNVO>  getDSNsForContainer(
						ContainerDetailsVO containerVO)
				throws BusinessDelegateException {
					log.entering(MODULE, "getDSNsForContainer");
					return despatchRequest("getDSNsForContainer",containerVO);
			}
			 public Collection<DSNVO>  getRoutingInfoforDSN(
					 Collection<DSNVO> dsnVos,ContainerDetailsVO containerDetailsVO)
				throws BusinessDelegateException {
					log.entering(MODULE, "getRoutingInfoforDSN");
					return despatchRequest("getRoutingInfoforDSN",dsnVos,containerDetailsVO);
			}
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.listFlightDetails
			 *	Added by 	:	A-8164 on 24-Sep-2018
			 * 	Used for 	:	List flight details for mailinbound
			 *	Parameters	:	@param mailArrivalVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Collection<MailArrivalVO>
			 */
			public Page<MailArrivalVO> listFlightDetails(MailArrivalVO mailArrivalVO)
					throws BusinessDelegateException{
				log.entering(MODULE, "listFlightDetails");
				return despatchRequest("listFlightDetails", mailArrivalVO); 
			}
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.closeInboundFlights
			 *	Added by 	:	A-8164 on 11-Dec-2018
			 * 	Used for 	:	Closing multiple inbound flights
			 *	Parameters	:	@param operationalFlightVOs
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	void
			 */
			public void closeInboundFlights(Collection<OperationalFlightVO> operationalFlightVOs)
					throws BusinessDelegateException {
						log.entering("MailTrackingDefaultsDelegate","closeInboundFlights");
						despatchRequest("closeInboundFlights",operationalFlightVOs);
						log.exiting("MailTrackingDefaultsDelegate","closeInboundFlights");
					}
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.reopenInboundFlight
			 *	Added by 	:	A-8164 on 11-Dec-2018
			 * 	Used for 	:	For reopening multiple inbound flights
			 *	Parameters	:	@param operationalFlightVOs
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	void
			 */
			public void reopenInboundFlights(Collection<OperationalFlightVO> operationalFlightVOs)
					throws BusinessDelegateException {
						log.entering("MailTrackingDefaultsDelegate","reopenInboundFlights");
						despatchRequest("reopenInboundFlights",operationalFlightVOs);
						log.exiting("MailTrackingDefaultsDelegate","reopenInboundFlights");
					}
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.populateMailArrivalVOForInbound
			 *	Added by 	:	A-8164 on 27-Dec-2018
			 * 	Used for 	:	For populating mailArrivalVo for inbound
			 *	Parameters	:	@param operationalFlightVO
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	void
			 */
			public MailArrivalVO populateMailArrivalVOForInbound(OperationalFlightVO operationalFlightVO)
					throws BusinessDelegateException {
						log.entering("MailTrackingDefaultsDelegate","populateMailArrivalVOForInbound");
						return despatchRequest("populateMailArrivalVOForInbound",operationalFlightVO);
					}
			
			public Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO)
					throws BusinessDelegateException {
						log.entering("MailTrackingDefaultsDelegate","findArrivedContainersForInbound");
						return despatchRequest("findArrivedContainersForInbound",mailArrivalFilterVO);
					}
			
			public Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
					throws BusinessDelegateException {
						log.entering("MailTrackingDefaultsDelegate","findArrivedMailbagsForInbound");
						return despatchRequest("findArrivedMailbagsForInbound",mailArrivalFilterVO);
					}
			
			public Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
					throws BusinessDelegateException {
						log.entering("MailTrackingDefaultsDelegate","findArrivedDsnsForInbound");
						return despatchRequest("findArrivedDsnsForInbound",mailArrivalFilterVO);
					}
            /**
			 * 	Method		:	MailTrackingDefaultsDelegate.findOffLoadDetails
			 *	Added by 	:	A-7929 on 18-Feb-2019
			 * 	Used for 	:
			 *	Parameters	:	@param Page<OffloadVO>
			 *	Parameters	:	@return 
			 *	Return type	: 	page
			 */
			public OffloadVO findOffLoadDetails(OffloadFilterVO offloadFilterVO) throws BusinessDelegateException {
				 log.entering("MailTrackingDefaults Delegate","findOffLoadDetails");
				return despatchRequest("findOffLoadDetails",offloadFilterVO);
			}
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.findOfficeOfExchangeForPA
			 *	Added by 	:	A-8672 on 25-Feb-2018
			 * 	Used for 	:
			 *	Parameters	:	@param companyCode
			 *	Parameters	:	@param paCode
			 *	Parameters	:	@return
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Map<String,String>
			 * @throws BusinessDelegateException 
			 */
			public MailInConsignmentVO populatePCIDetailsforUSPS(MailInConsignmentVO mailInConsignment, String airport, String companyCode, String rcpOrg, String rcpDest, String year)
				    throws SystemException, BusinessDelegateException
				  {
						log.entering("MailTrackingDefaultsDelegate","populatePCIDetailsforUSPS");
						return despatchRequest("populatePCIDetailsforUSPS",mailInConsignment, airport, companyCode, rcpOrg, rcpDest, year);
				
				  }
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.findMailbagBillingStatus
			 *	Added by 	:	a-8331 on 25-Oct-2019
			 * 	Used for 	:
			 *	Parameters	:	@param mailbagvo
			 *	Parameters	:	@return
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Collection<DocumentBillingDetailsVO>
			 */
			public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo) throws BusinessDelegateException {
					
				log.entering("MailTrackingDefaultsDelegate","findMailbagBillingStatus");
				return despatchRequest("findMailbagBillingStatus",mailbagvo);
				
			}
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.voidMailbags
			 *	Added by 	:	a-8331 on 25-Oct-2019
			 * 	Used for 	:
			 *	Parameters	:	@param documentBillingDetails
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	void
			 */
			public void voidMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetails) throws BusinessDelegateException {
				
				 despatchRequest("voidMailbags",documentBillingDetails) ;	
			}
			/**
			 * @author A-6986
			 * @param incentiveConfigurationFilterVO
			 * @return
			 * @throws BusinessDelegateException
			 */
			public Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails(IncentiveConfigurationFilterVO 
					incentiveConfigurationFilterVO)throws BusinessDelegateException {
				log.entering(MODULE, "findIncentiveConfigurationDetails");
				return despatchRequest("findIncentiveConfigurationDetails",incentiveConfigurationFilterVO);
			}
			/**
			 * @author A-6986
			 * @param incentiveConfigurationFilterVO
			 * @return
			 * @throws BusinessDelegateException
			 */
			public void saveIncentiveConfigurationDetails(Collection<IncentiveConfigurationVO> incentiveConfigurationVOs)
					throws BusinessDelegateException {
				log.entering(MODULE, "saveIncentiveConfigurationDetails");
				despatchRequest("saveIncentiveConfigurationDetails",incentiveConfigurationVOs);
			}
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.findMailbagsForTruckFlight
			 *	Added by 	:	A-7929 on 22-Oct-2018
			 * 	Used for 	:	ICRD-241437
			 *	Parameters	:	@param mailbagEnquiryFilterVO,parseInt
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Page<MailbagVO>
			 */
			public  Page<MailbagVO> findMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,int pageNumber) throws BusinessDelegateException{
				
				log.entering(MODULE, "findMailbagsForTruckFlight");
				return despatchRequest("findMailbagsForTruckFlight",mailbagEnquiryFilterVO,pageNumber);
				
			}
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.findRoutingIndex
			 *	Added by 	:	A-7531 on 30-Oct-2018
			 * 	Used for 	:
			 *	Parameters	:	@param routingIndexVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Collection<RoutingIndexVO>
			 */
			public Collection<RoutingIndexVO> findRoutingIndex(
					RoutingIndexVO routingIndexVO)
							throws BusinessDelegateException {
				return despatchRequest("findRoutingIndex",routingIndexVO);
		 }
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.findOfficeOfExchangeForPA
			 *	Added by 	:	A-7531 on 30-Oct-2018
			 * 	Used for 	:
			 *	Parameters	:	@param companyCode
			 *	Parameters	:	@param paCode
			 *	Parameters	:	@return
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Map<String,String>
			 */
			public Map<String,String> findOfficeOfExchangeForPA(String companyCode,
					String paCode) throws BusinessDelegateException {
				log.entering("MailTrackingDefaultsDelegate", "findOfficeOfExchangeForPA");
				return despatchRequest("findOfficeOfExchangeForPA",companyCode,paCode);
			}

			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.findDsnAndRsnForMailbag
			 *	Added by 	:	A-7531 on 31-Oct-2018
			 * 	Used for 	:
			 *	Parameters	:	@param maibagVO
			 *	Parameters	:	@return 
			 *	Return type	: 	MailbagVO
			 */
			public MailbagVO findDsnAndRsnForMailbag(MailbagVO mailbagVO)
					throws BusinessDelegateException{
				
				log.entering("MailTrackingDefaultsDelegate", "findDsnAndRsnForMailbag");
				return despatchRequest("findDsnAndRsnForMailbag",mailbagVO);
			}
			   
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.listForceMajeureApplicableMails
			 *	Added by 	:	A-8527 on 23-Nov-2018
			 * 	Used for 	:	ICRD-235466
			 *	Parameters	:	@param maibagVO
			 *	Parameters	:	@return 
			 *	Return type	: 	Page
			 */
			public Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(
					ForceMajeureRequestFilterVO forceMajeureRequestFilterVO, int pageNumber) throws BusinessDelegateException {
				log.entering(MODULE, "listForceMajeureApplicableMails");
				return despatchRequest("listForceMajeureApplicableMails", forceMajeureRequestFilterVO,pageNumber);
}
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.saveForceMajeureRequest
			 *	Added by 	:	A-8527 on 26-Nov-2018
			 * 	Used for 	:	ICRD-235466
			 *	Parameters	:	@param maibagVO
			 *	Parameters	:	@return 
			 *	Return type	: 	Page
			 */
			public void saveForceMajeureRequest(Collection<LockVO> locks, boolean saveflag,ForceMajeureRequestFilterVO forceMajeureRequestFilterVO)throws BusinessDelegateException {
				dispatchAsyncRequest("saveForceMajeureRequest", locks, false,forceMajeureRequestFilterVO);
			}
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.listForceMajeureRequestIds
			 *	Added by 	:	A-8527 on 26-Nov-2018
			 * 	Used for 	:	ICRD-235466
			 *	Parameters	:	@param maibagVO
			 *	Parameters	:	@return 
			 *	Return type	: 	Page
			 */
			public Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO forceMajeureRequestFilterVO, int displayPage)throws BusinessDelegateException {
				log.entering(MODULE, "listForceMajeureDetails");
				return despatchRequest("listForceMajeureDetails", forceMajeureRequestFilterVO,displayPage);
			}
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.initTxnForForceMajeure
			 *	Added by 	:	A-8527 on 26-Nov-2018
			 * 	Used for 	:	ICRD-235466
			 *	Parameters	:	@param maibagVO
			 *	Parameters	:	@return 
			 *	Return type	: 	Page
			 */
			public InvoiceTransactionLogVO initTxnForForceMajeure(InvoiceTransactionLogVO invoiceTransactionLogVO )throws BusinessDelegateException {
				log.entering(MODULE, "initTxnForForceMajeure");
				return despatchRequest("initTxnForForceMajeure", invoiceTransactionLogVO);
			}
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.listForceMajeureRequestIds
			 *	Added by 	:	A-8527 on 26-Nov-2018
			 * 	Used for 	:	ICRD-235466
			 *	Parameters	:	@param maibagVO
			 *	Parameters	:	@return 
			 *	Return type	: 	Page
			 */
			public Page<ForceMajeureRequestVO> listForceMajeureRequestIds(ForceMajeureRequestFilterVO forceMajeureRequestFilterVO, int displayPage)throws BusinessDelegateException {
				log.entering(MODULE, "listForceMajeureRequestIds");
				return despatchRequest("listForceMajeureRequestIds", forceMajeureRequestFilterVO,displayPage);
			}
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.updateForceMajeureRequest
			 *	Added by 	:	A-8527 on 26-Nov-2018
			 * 	Used for 	:	ICRD-235466
			 *	Parameters	:	@param maibagVO
			 *	Parameters	:	@return 
			 *	Return type	: 	Page
			 */
			public void updateForceMajeureRequest(ForceMajeureRequestFilterVO forceMajeureRequestFilterVO,Collection<LockVO> locks, boolean updateflag)throws BusinessDelegateException {
				log.entering(MODULE, "updateForceMajeureRequest");
				dispatchAsyncRequest("updateForceMajeureRequest", locks,false,forceMajeureRequestFilterVO);
			}
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.deleteForceMajeureRequest
			 *	Added by 	:	A-8527 on 06-Dec-2018
			 * 	Used for 	:	ICRD-235466
			 *	Parameters	:	@param maibagVO
			 *	Parameters	:	@return 
			 *	Return type	: 	Page
			 */
			public void deleteForceMajeureRequest(Collection<ForceMajeureRequestVO> deleteVOs)throws BusinessDelegateException {
				log.entering(MODULE, "deleteForceMajeureRequest");
				despatchRequest("deleteForceMajeureRequest", deleteVOs);
			}
			/**
			 * @author a-7540
			 * @param reassignedFlightValidationVO
			 * @return
			 * @throws BusinessDelegateException
			 */
			public  Collection<ContainerVO>  findAllContainersInAssignedFlight(
					FlightValidationVO reassignedFlightValidationVO)
			   throws BusinessDelegateException {
				   log.entering("MailTrackingDefaults Delegate", "findAllContainersInAssignedFlight");
				   return despatchRequest("findAllContainersInAssignedFlight",reassignedFlightValidationVO);
			}
			
			/**
			 * @author A-5526
			 * Added for CRQ ICRD-233864 
			 * @param mailArrivalVO
			 * @throws BusinessDelegateException
			 */

			public void onStatustoReadyforDelivery(MailArrivalVO mailArrivalVO) throws BusinessDelegateException{
				log.entering("MailTrackingDefaultsDelegate","saveAutoArrivalAndReadyForDelivery");
				 despatchRequest("onStatustoReadyforDelivery",mailArrivalVO);
				
			}  
             /**
			 * 	Method		:	MailTrackingDefaultsDelegate.saveContentID
			 *	Added by 	:	A-7929 on 04-02-2019
			 * 	Used for 	:	
			 *	Parameters	:	@param containerVOs
			 *	Parameters	:	@return 
			 *	Return type	: 	void
			 */
			public void saveContentID(Collection<ContainerVO> containerVOs) throws BusinessDelegateException {
				 log.entering("MailTrackingDefaults Delegate", "saveContentID");
				 despatchRequest("saveContentID", containerVOs);
			}
            /**
			 * 	Method		:	MailTrackingDefaultsDelegate.saveActualWeight
			 *	Added by 	:	A-8672 on 04-Feb-2019
			 * 	Used for 	:
			 *	Parameters	:	@param Collection<ContainerDetails>
			 *	Parameters	:	@return 
			 *	Return type	: 	void
			 */
			public void updateActualWeightForMailULD(ContainerVO containerVO)
					throws BusinessDelegateException{
				
				log.entering("MailTrackingDefaultsDelegate", "updateActualWeightForMailULD");
				despatchRequest("updateActualWeightForMailULD",containerVO);
			}
			
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.updateActualWeightForMailbag
			 *	Added by 	:	A-8672 on 08-Feb-2019
			 * 	Used for 	:
			 *	Parameters	:	@param MailbagVO
			 *	Parameters	:	@return 
			 *	Return type	: 	void
			 */
			public void updateActualWeightForMailbag(MailbagVO mailbagVO)
					throws BusinessDelegateException{
				
				log.entering("MailTrackingDefaultsDelegate", "updateActualWeightForMailbag");
				despatchRequest("updateActualWeightForMailbag",mailbagVO);
			}
			
			public Map<String, Object> generateConsignmentReports(ReportSpec reportSpec) throws BusinessDelegateException {
				log.entering("MailTrackingDefaultsDelegate", "generateConsignmentReports");
				return despatchRequest("generateConsignmentReports",reportSpec);
			}
			public Map<String, Object> generateConsignmentSummaryReports(ReportSpec reportSpec) throws BusinessDelegateException {
				log.entering("MailTrackingDefaultsDelegate", "generateConsignmentSummaryReports");
				return despatchRequest("generateConsignmentSummaryReports",reportSpec);
			}
			
			public  Collection<ConsignmentDocumentVO> findTransferManifestConsignmentDetails(TransferManifestVO transferManifestVO) throws BusinessDelegateException {
				log.entering("MailTrackingDefaultsDelegate", "findTransferManifestConsignmentDetails");
				return despatchRequest("findTransferManifestConsignmentDetails",transferManifestVO);
			}
			//Added by A-8464 for ICRD-243079
			public Collection<MailMonitorSummaryVO>  getPerformanceMonitorDetails(
					 MailMonitorFilterVO filterVO)
				throws BusinessDelegateException {
					log.entering(MODULE, "getPerformanceMonitorDetails");
					return despatchRequest("getPerformanceMonitorDetails", filterVO);
			}
			
			//Added by A-8464 for ICRD-243079
			 public Page<MailbagVO>  getPerformanceMonitorMailbags(
					 MailMonitorFilterVO filterVO,String type,int pageNumber)
				throws BusinessDelegateException {
					log.entering(MODULE, "getOnTimeMailbags");
					return despatchRequest("getPerformanceMonitorMailbags", filterVO,type,pageNumber);
			}
			public Collection<FlightSegmentCapacitySummaryVO> fetchFlightCapacityDetails(Collection<FlightFilterVO> flightFilterVOs) 
					   throws BusinessDelegateException {
						 log.entering(MODULE, "fetchFlightCapacityDetails");
							return despatchRequest("fetchFlightCapacityDetails",flightFilterVOs);
			}

			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.findDuplicateMailbag
			 *	Added by 	:	A-7531 on 16-May-2019
			 * 	Used for 	:
			 *	Parameters	:	@param companyCode
			 *	Parameters	:	@param mailBagId
			 *	Parameters	:	@return 
			 *	Return type	: 	Collection<MailbagHistoryVO>
			 */
			public ArrayList<MailbagVO> findDuplicateMailbag(String companyCode, String mailBagId)   throws BusinessDelegateException{
				// TODO Auto-generated method stub
				 log.entering(MODULE, "findDuplicateMailbag");
				return despatchRequest("findDuplicateMailbag", companyCode,mailBagId);
			}
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.sendULDAnnounceForFlight
			 *	Added by 	:	A-8164 on 12-Feb-2021
			 * 	Used for 	:	Sending ULD Announce from mail outbound
			 *	Parameters	:	@param mailManifestVO
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	void
			 */
			public void sendULDAnnounceForFlight(MailManifestVO mailManifestVO) 
					throws BusinessDelegateException {
				log.entering(MODULE, "sendULDAnnounceForFlight");
				despatchRequest("sendULDAnnounceForFlight",mailManifestVO);
				log.exiting(MODULE, "sendULDAnnounceForFlight");
			}
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.listCarditDsnDetails
			 *	Added by 	:	A-8164 on 04-Sep-2019
			 * 	Used for 	:	List Cardit DSN Details
			 *	Parameters	:	@param dsnEnquiryFilterVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Page<DSNVO>
			 */
			public Page<DSNVO> listCarditDsnDetails(DSNEnquiryFilterVO dsnEnquiryFilterVO)
					throws BusinessDelegateException {
				log.entering(MODULE, "listCarditDsnDetails");
				return despatchRequest("listCarditDsnDetails", dsnEnquiryFilterVO);
}
			
			
			
			
			
			
			
			
			
				
			
				
				
			
			

				
			   
			

				
				
			
				
			
			
			


			public MailboxIdVO findMailboxId(MailboxIdVO mailboxIdVO)throws BusinessDelegateException {
				 log.entering(MODULE, "findMailboxId");
				return despatchRequest("findMailboxId",mailboxIdVO);
			}
			public Boolean validateMailSubClass(String companyCode,String subclass) throws BusinessDelegateException {
				log.entering(MODULE, "validateMailSubClass");
				return despatchRequest("validateMailSubClass",companyCode,subclass);

			}
			public Page<MailbagVO> findDeviationMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
					int pageNumber) throws BusinessDelegateException {
				log.entering(MODULE, "findDeviationMailbags");
				return despatchRequest("findDeviationMailbags",mailbagEnquiryFilterVO,pageNumber);
			}
			public void saveMailboxId(MailboxIdVO mailboxIdVO) throws BusinessDelegateException {
				log.entering(MODULE, "saveMailboxId");
				despatchRequest("saveMailboxId",mailboxIdVO);
			}
			public String findProductsByName(String companyCode,String product)throws BusinessDelegateException {
				log.entering(MODULE, "findProductsByName");
				return despatchRequest("findProductsByName",companyCode,product);
			}
			
			public void deleteEmptyContainer(ContainerDetailsVO containerDetailsVO)
					throws BusinessDelegateException {
						log.entering(MODULE, "deleteEmptyContainer");
						despatchRequest("deleteEmptyContainer", containerDetailsVO);
					}
					
			public ErrorVO validateContainerNumberForDeviatedMailbags(ContainerDetailsVO containerDetailsVO, long mailSequenceNumber)
			        throws BusinessDelegateException{
				log.entering(MODULE, "validateContainerNumberForDeviatedMailbags");
				return despatchRequest("validateContainerNumberForDeviatedMailbags", containerDetailsVO, mailSequenceNumber);
			}
			public MailbagInULDForSegmentVO getManifestInfo(MailbagVO mailbagVO)
			        throws BusinessDelegateException{
				log.entering(MODULE, "getManifestInfo");
				return despatchRequest("getManifestInfo", mailbagVO);
			}
			public String checkMailInULDExistForNextSeg(String containerNumber,String airpotCode,String companyCode)
			        throws BusinessDelegateException{
				log.entering(MODULE, "checkMailInULDExistForNextSeg");
				return despatchRequest("checkMailInULDExistForNextSeg", containerNumber,airpotCode,companyCode);
			}

public void saveMailRuleConfiguration(MailRuleConfigVO mailRuleConfigVO) throws BusinessDelegateException {
				log.entering(MODULE, "saveMailRuleConfiguration");
				despatchRequest("saveMailRuleConfiguration",mailRuleConfigVO);
			}

public void updateRetainFlagForContainer(ContainerVO containerVO)
        throws BusinessDelegateException{
	log.entering(MODULE, "updateRetainFlagForContainer");
	despatchRequest("updateRetainFlagForContainer", containerVO);
}	   

/**
			 * 	Method		:	MailTrackingDefaultsDelegate.findConsignmentScreeningDetails
			 *	Added by 	:	A-9084 on 28-Sep-2020
			 * 	Used for 	:
			 *	Parameters	:	@param consignmentNumber
			 *	Parameters	:	@param companyCode
			 *	Parameters	:	@return 
			 *	Return type	: 	ConsignmentDocumentVO
			 */
			public ConsignmentDocumentVO findConsignmentScreeningDetails(
					String consignmentNumber, String companyCode , String poaCode) throws BusinessDelegateException{
				log.entering(MODULE, "findConsignmentScreeningDetails");
				return despatchRequest("findConsignmentScreeningDetails", consignmentNumber, companyCode,poaCode);
			}
			
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.saveTransferFromManifest
			 *	Added by 	:	A-8893 on 06-Nov-2020
			 * 	Used for 	:
			 *	Parameters	:	@param TransferManifestVO
			 */
			public void saveTransferFromManifest(TransferManifestVO transferManifestVO) throws BusinessDelegateException {
				log.entering(MODULE, "saveTransferFromManifest");
				 despatchRequest("saveTransferFromManifest", transferManifestVO);
			
				
			}
			
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.rejectTransferFromManifest
			 *	Added by 	:	A-8893 on 06-Nov-2020
			 * 	Used for 	:
			 *	Parameters	:	@param TransferManifestVO
			 */
			
			public void rejectTransferFromManifest(TransferManifestVO transferManifestVO) throws BusinessDelegateException {
				log.entering(MODULE, "rejectTransferFromManifest");
				 despatchRequest("rejectTransferFromManifest", transferManifestVO);
			
				
			}
			/**
			 * 
			 * @param offloadVo
			 * @throws BusinessDelegateException
			 */
			public void removeFromInbound(OffloadVO offloadVo)throws BusinessDelegateException {
				log.entering(MODULE, "removeFromInbound");
				despatchRequest("removeFromInbound", offloadVo);
			}
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.findTransferManifestDetailsForTransfer
			 *	Added by 	:	A-8893 on 06-Nov-2020
			 * 	Used for 	:
			 *	Parameters	:	@param companyCode
			 *	Parameters	:	@param tranferManifestId
			 *	Parameters	:	@return 
			 *	Return type	: 	List<TransferManifestVO>
			 */
			public List<TransferManifestVO> findTransferManifestDetailsForTransfer(
					String companyCode,String tranferManifestId)throws BusinessDelegateException{
			
				log.entering(MODULE, "findTransferManifestDetailsForTransfer");
				return despatchRequest("findTransferManifestDetailsForTransfer", companyCode, tranferManifestId);
			}
			/**
			 * 
			 * @param mailbagVO
			 * @return
			 * @throws BusinessDelegateException
			 */
			public MailbagVO findMailBillingStatus(MailbagVO mailbagVO)
					throws BusinessDelegateException {
				log.entering("MailTrackingDefaultsDelegate","findMailBillingStatus");
				return despatchRequest("findMailBillingStatus",mailbagVO);
			}					
			/**
			 * 	Method		:	MailTrackingDefaultsDelegate.saveConsignmentScreeningDetails
			 *	Added by 	:	A-9084 on 28-Sep-2020
			 * 	Used for 	:
			 *	Parameters	:	@param Collection<ConsignmentScreeningVO>
			 *	Parameters	:	@return 
			 *	Return type	: 	void
			 */
			public void saveConsignmentScreeningDetails(ConsignmentDocumentVO consignmentDocumentVO) throws BusinessDelegateException {
				log.entering(MODULE, "saveConsignmentScreeningDetails");
				despatchRequest("saveConsignmentScreeningDetails",consignmentDocumentVO);				
			}

			/**
			 * @author A-8353
			 * @param companyCode
			 * @param airportCode
			 * @return String
			 * @throws BusinessDelegateException
			 */
			public  String findOfficeOfExchangeForCarditMissingDomMail(String companyCode,String airportCode) throws BusinessDelegateException {
				log.entering(MODULE, "findOfficeOfExchangeForCarditMissingDomMail");
				return despatchRequest("findOfficeOfExchangeForCarditMissingDomMail",companyCode,airportCode);				
			}
            public String findAutoPopulateSubtype(
					DocumentFilterVO documentFilterVO) throws BusinessDelegateException {
				log.entering("MailTrackingDefaultsDelegate", "findAutoPopulateSubtype");
				return despatchRequest("findAutoPopulateSubtype", documentFilterVO);
			}  
            /**
			 * @author A-8353
			 * @param mailbagVOs
			 * @throws BusinessDelegateException
			 */
			public void updateOriginAndDestinationForMailbag(Collection<MailbagVO>  mailbagVOs)
						throws BusinessDelegateException{
					
					log.entering("MailTrackingDefaultsDelegate", "updateOriginAndDestinationForMailbag");
					despatchRequest("updateOriginAndDestinationForMailbag",mailbagVOs);
			}

			
			public void releaseContainers(Collection<ContainerVO> containerVOsToBeReleased)throws BusinessDelegateException {
				log.entering(MODULE, "releaseContainers");
				despatchRequest("releaseContainers",containerVOsToBeReleased);				
			
			}
			

			
			/**
			 * @author A-8893 This method is used to find the Damaged Mailbag Images
			 * @param companyCode
			 * @param mailbagId
			 * @return
			 * @throws BusinessDelegateException
			 */
			public byte[] findMailbagDamageImages(String companyCode,
					String id) throws BusinessDelegateException {
				log.entering(MODULE, "findMailbagDamageImages");
				return despatchRequest("findMailbagDamageImages", companyCode, id);
			}
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsDelegate.findContainerJourneyID
			 *	Added by 	:	A-6245 on 15-Jun-2021
			 * 	Used for 	:	IASCB-84894
			 *	Parameters	:	@param containerFilterVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws BusinessDelegateException 
			 *	Return type	: 	Collection<ContainerDetailsVO>
			 */
			public Collection<ContainerDetailsVO> findContainerJourneyID(ConsignmentFilterVO consignmentFilterVO) 
					throws BusinessDelegateException {
				log.entering(MODULE, "findContainerJourneyID");
				return despatchRequest("findContainerJourneyID", consignmentFilterVO);
			}
			
			public void attachAWBForMail(Collection<MailBookingDetailVO> mailBookingDetailVOs,Collection<MailbagVO> mailbagVOs) throws BusinessDelegateException{
				log.entering(MODULE, "attachAWBForMail");
				 despatchRequest("attachAWBForMail", mailBookingDetailVOs, mailbagVOs);
			}
			
			public String findNearestAirportOfCity(String companyCode, String exchangeCode) throws BusinessDelegateException{
				log.entering(MODULE, "findNearestAirportOfCity");
				return despatchRequest("findNearestAirportOfCity", companyCode, exchangeCode);
			}
			
			public long findMailBagSequenceNumberFromMailIdr(String mailIdr,String companyCode) throws BusinessDelegateException{
				return despatchRequest("findMailBagSequenceNumberFromMailIdr", mailIdr, companyCode);
			}

			public void markUnmarkUldIndicator(ContainerVO containerVO) throws BusinessDelegateException {
				log.entering(MODULE, "markUnmarkUldIndicator");
				despatchRequest("markUnmarkUldIndicator", containerVO);

			}
			public void saveSecurityDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) throws BusinessDelegateException {
				log.entering(MODULE, "saveSecurityDetails");
				despatchRequest("saveSecurityDetails", consignmentScreeningVOs);

			}	
			
	public MailbagVO listmailbagSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo)
			throws BusinessDelegateException {
		log.entering(MODULE, "listmailbagSecurityDetails");

		return despatchRequest("listmailbagSecurityDetails", mailScreeningFilterVo);

	}
	/**
		* 	Method		:	MailTrackingDefaultsDelegate.editscreeningDetails
		*	Added by 	:	A-10383 on 28-Apr-2022
		* 	Used for 	:	IASCB-143907
		*	Parameters	:	@param consignmentScreeningVOs
		*	Parameters	:	@return
		* @param consignmentScreeningVOs
		* @throws BusinessDelegateException
	 */
	
	public void editscreeningDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) throws BusinessDelegateException {
		log.entering(MODULE, "editscreeningDetails");
		despatchRequest("editscreeningDetails", consignmentScreeningVOs);

	}	
	/**
	* 	Method		:	MailTrackingDefaultsDelegate.deletescreeningDetails
	*	Added by 	:	A-10383 on 28-Apr-2022
	* 	Used for 	:	IASCB-143907
	*	Parameters	:	@param consignmentScreeningVOs
	*	Parameters	:	@return
	* @param consignmentScreeningVOs
	* @throws BusinessDelegateException
 */
	public void deletescreeningDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) throws BusinessDelegateException {
		log.entering(MODULE, "deletescreeningDetails");
		despatchRequest("deletescreeningDetails", consignmentScreeningVOs);

	}
	
	/**
	 * 	Method		:	MailTrackingDefaultsDelegate.saveMailSecurityStatus
	 *	Added by 	:	A-4809 on 18-May-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	void
	 */
	public void saveMailSecurityStatus(MailbagVO mailbagVO) throws BusinessDelegateException {
		log.entering(MODULE, "saveMailSecurityStatus");
		despatchRequest("saveMailSecurityStatus", mailbagVO);

	}
	/**
	* 	Method		:	MailTrackingDefaultsDelegate.saveConsignmentDetailsMaster
	*	Added by 	:	A-10383 on 18-July-2022
	* 	Used for 	:	IASCB-143907
	*	Parameters	:	@param ConsignmentDocumentVO
	*	Parameters	:	@return
	* @param ConsignmentDocumentVO
	* @throws BusinessDelegateException
 */

public void saveConsignmentDetailsMaster(ConsignmentDocumentVO  consignmentDocumentVO) throws BusinessDelegateException {
	log.entering(MODULE, "saveConsignmentDetailsMaster");
	despatchRequest("saveConsignmentDetailsMaster", consignmentDocumentVO);

}	

public Map<String,String> findAirportParameterCode(FlightFilterVO flightFilterVO,Collection<String> parCodes) throws BusinessDelegateException{
	log.entering(MODULE, "findAirportParameterCode");
	return despatchRequest("findAirportParameterCode", flightFilterVO,parCodes);
}
	
  public Collection<SecurityScreeningValidationVO> findSecurityScreeningValidations(SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO) throws BusinessDelegateException{
	log.entering(MODULE, "findSecurityScreeningValidations");
	return despatchRequest("findSecurityScreeningValidations", securityScreeningValidationFilterVO);
}

public SecurityScreeningValidationVO doSecurityAndScreeningValidationAtContainerLevel(
		OperationalFlightVO operationalFlightVO, Collection<ContainerVO> selectedContainerVOs) throws BusinessDelegateException {
	return despatchRequest("doSecurityAndScreeningValidationAtContainerLevel",operationalFlightVO, selectedContainerVOs);
}

/**
 * 
 * 	Method		:	MailTrackingDefaultsDelegate.generateCN46ConsignmentReport
 *	Added by 	:	A-10647 on 27-Oct-2022
 * 	Used for 	:
 *	Parameters	:	@param reportSpec
 *	Parameters	:	@return
 *	Parameters	:	@throws BusinessDelegateException 
 *	Return type	: 	Map<String,Object>
 */
public Map<String, Object> generateCN46ConsignmentReport(ReportSpec reportSpec) throws BusinessDelegateException {
	log.entering(MODULE, "generateCN46ConsignmentReport");
	return despatchRequest("generateCN46ConsignmentReport",reportSpec);
}
/**
 * 
 * 	Method		:	MailTrackingDefaultsDelegate.generateCN46ConsignmentSummaryReport
 *	Added by 	:	A-10647 on 27-Oct-2022
 * 	Used for 	:
 *	Parameters	:	@param reportSpec
 *	Parameters	:	@return
 *	Parameters	:	@throws BusinessDelegateException 
 *	Return type	: 	Map<String,Object>
 */
public Map<String, Object> generateCN46ConsignmentSummaryReport(ReportSpec reportSpec) throws BusinessDelegateException {
	log.entering(MODULE, "generateCN46ConsignmentSummaryReport");
	return despatchRequest("generateCN46ConsignmentSummaryReport",reportSpec);
}

public String generateAutomaticBarrowId(String cmpcod) throws BusinessDelegateException {
	    log.entering(MODULE, "generateAutomaticBarrowId");
	    return despatchRequest("generateAutomaticBarrowId", cmpcod);
	  }

/**
 * 
 * 	Method		:	MailTrackingDefaultsDelegate.findCN46TransferManifestDetails
 *	Added by 	:	A-10647 on 27-Oct-2022
 * 	Used for 	:
 *	Parameters	:	@param transferManifestVO
 *	Parameters	:	@return
 *	Parameters	:	@throws BusinessDelegateException 
 *	Return type	: 	Collection<ConsignmentDocumentVO>
 */
public  Collection<ConsignmentDocumentVO> findCN46TransferManifestDetails(TransferManifestVO transferManifestVO) throws BusinessDelegateException {
	log.entering(MODULE, "findCN46TransferManifestDetails");
	return despatchRequest("findCN46TransferManifestDetails",transferManifestVO);
}
/**
 * @author A-8353
 * @param mailbagVO 
 * @param securityScreeningValidationFilterVO
 * @return
 * @throws BusinessDelegateException
 */
public Collection<SecurityScreeningValidationVO> doApplicableRegulationFlagValidationForPABuidContainer(MailbagVO mailbagVO, SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO) throws BusinessDelegateException{
	log.entering(MODULE, "doApplicableRegulationFlagValidationForPABuidContainer");
	return despatchRequest("doApplicableRegulationFlagValidationForPABuidContainer",mailbagVO, securityScreeningValidationFilterVO);
}

public Collection<MailAcceptanceVO> fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs) 
		   throws BusinessDelegateException {
			 log.entering(MODULE, "fetchFlightPreAdviceDetails");
				return despatchRequest("fetchFlightPreAdviceDetails",flightFilterVOs);
}

public Page<MailTransitVO> findMailTransit(
			MailTransitFilterVO mailTransitFilterVO, int pageNumber)
			throws BusinessDelegateException {
		log.entering(MODULE, "findMailTransit");
		return despatchRequest("findMailTransit", mailTransitFilterVO,
				pageNumber);
	}
	public Collection<FlightSegmentCapacitySummaryVO> findFlightListings(
			FlightFilterVO flightFilterVO)
			throws  BusinessDelegateException {
		log.entering(MODULE, "findFlightListings");
		return despatchRequest("findFlightListings", flightFilterVO);
	}
	public Page<FlightSegmentCapacitySummaryVO> findActiveAllotments(
			FlightSegmentCapacityFilterVO filterVo)
			throws  BusinessDelegateException {
		log.entering(MODULE, "findActiveAllotments");
		return despatchRequest("findActiveAllotments", filterVo);
	}
	public MailbagVO findMailConsumed(
			MailTransitFilterVO filterVo)
			throws  BusinessDelegateException {
		log.entering(MODULE, "findMailConsumed");
		return despatchRequest("findMailConsumed", filterVo);
	}
	
	
/**
 * @author a-9998
 * @param carditVO
 * @return AWBDetailVO
 * @throws BusinessDelegateException
 */
public AWBDetailVO findMstDocNumForAWBDetails(CarditVO carditVO)
throws BusinessDelegateException {
	log.entering(MODULE, "findMstDocNumForAWBDetails");
	return despatchRequest("findMstDocNumForAWBDetails", carditVO);
}
public Collection<MailbagHistoryVO> findMailbagHistoriesFromWebScreen(String companyCode, String mailBagId,
		long mailSequenceNumber) throws BusinessDelegateException {
	log.entering(MODULE, "findMailbagHistoriesFromWebScreen");
	return despatchRequest("findMailbagHistoriesFromWebScreen", companyCode, mailBagId, mailSequenceNumber);
}
public void saveMalRdtMsgAddDtl(Collection<MessageDespatchDetailsVO> messageAddressDetails,Collection<AutoForwardDetailsVO> participantDetails,MessageVO messageVo,String selectedResditVersion,List<String> selectedResdits,Collection<MailbagVO> selectedMailbags) throws BusinessDelegateException {
	log.entering(MODULE, "saveMalRdtMsgAddDtl");
	despatchRequest("saveMalRdtMsgAddDtl",messageAddressDetails,participantDetails,messageVo,selectedResditVersion,selectedResdits,selectedMailbags);
}
	public Collection<FlightSegmentVolumeDetailsVO> fetchFlightVolumeDetails(Collection<FlightFilterVO> flightFilterVOs)
			throws BusinessDelegateException {
		log.entering(MODULE, "fetchFlightVolumeDetails");
		return despatchRequest("fetchFlightVolumeDetails", flightFilterVOs);
	}
	public Collection<ULDTypeVO> findULDTypes(ULDTypeFilterVO uldTypeFilterVO) throws BusinessDelegateException{
		log.entering(MODULE, "findULDTypes");
		return despatchRequest("findULDTypes", uldTypeFilterVO);
	}
}
