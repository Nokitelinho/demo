/*
m * MailTrackingDefaultsBI.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVolumeDetailsVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.vo.*;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mailsecurityandscreening.SecurityAndScreeningMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.operations.vo.StorageUnitCheckinVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.LocationEnquiryFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.template.TemplateRenderingException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.sprout.multitenant.flow.FlowDescriptor;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.AvoidForcedStaleDataChecks;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.AutoForwardDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.*;

//import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;

/**
 * @author a-1303
 *
 */
public interface MailTrackingDefaultsBI {

	/**
	 *
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String findRealTimeuploadrequired(String uploadSystemParameter)
			throws SystemException, RemoteException;


	/**
	 * Added by A-5526
	 *
	 * @param scannedMailDetailsVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailHHTBusniessException
	 * @throws MailMLDBusniessException
	 */
	public void saveMailScannedDetails(
			Collection<MailScanDetailVO> mailScanDetailVOs)
			throws RemoteException, SystemException;

	public void updateGHTformailbags(Collection<OperationalFlightVO> operationalFlightVOs)throws SystemException, RemoteException, ProxyException;
	/**
	 * Added by A-5526
	 *
	 * @param scannedMailDetailsVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailHHTBusniessException
	 * @throws MailMLDBusniessException
	 */
	public ScannedMailDetailsVO saveMailUploadDetails(
			Collection<MailUploadVO> mailBagVOs, String scanningPort)
			throws RemoteException, SystemException, MailHHTBusniessException,
			MailMLDBusniessException, MailTrackingBusinessException;

	public void saveMailDetailsFromJob(Collection<MailUploadVO> mailBagVOs,
			String scanningPort) throws RemoteException, SystemException,
			MailHHTBusniessException, MailMLDBusniessException,
			MailTrackingBusinessException;

	/**
	 * Performs group delivery Jan 29, 2007, A-1739
	 *
	 * @param mailArrivalVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	@AvoidForcedStaleDataChecks
	void deliverMailbags(MailArrivalVO mailArrivalVO) throws SystemException,
			RemoteException, MailTrackingBusinessException;

	/**
	 * @author A-1936 Added By Karthick V as the part of the NCA Mail Tracking
	 *         Cr
	 * @param mailbagsInInventory
	 * @param poaCode
	 * @throws BusinessDelegateException
	 */
	@AvoidForcedStaleDataChecks
	void deliverMailBagsFromInventory(
			Collection<MailInInventoryListVO> mailbagsInInventory)
			throws SystemException, RemoteException;

	/**
	 * @author a-2553
	 * @param containerInInventory
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void deliverContainersFromInventory(
			Collection<ContainerInInventoryListVO> containerInInventory)
			throws SystemException, RemoteException;

	/**
	 * saveScannedDeliverMails
	 *
	 * @param deliverVosForSave
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	@AvoidForcedStaleDataChecks
	public void saveScannedDeliverMails(
			Collection<MailArrivalVO> deliverVosForSave)
			throws SystemException, RemoteException,
			MailTrackingBusinessException;

	/**
	 * @author A-1739 This method is used to saveAcceptanceDetails
	 * @param mailAcceptanceVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	@AvoidForcedStaleDataChecks
	Collection<ScannedMailDetailsVO> saveAcceptanceDetails(
			MailAcceptanceVO mailAcceptanceVO) throws SystemException,
			RemoteException, MailTrackingBusinessException;

	/**
	 *
	 * @param scannedMailbagsToReturn
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	@AvoidForcedStaleDataChecks
	Collection<ScannedMailDetailsVO> offloadScannedMailbags(
			Collection<MailbagVO> scannedMailbagsToReturn)
			throws SystemException, RemoteException,
			MailTrackingBusinessException;

	/**
	 * @author a-1936 This method is used to offload the
	 *         Containers\dsns\mailbags
	 * @param offloadVo
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	Collection<ContainerDetailsVO> offload(OffloadVO offloadVo)
			throws SystemException, RemoteException,
			MailTrackingBusinessException;

	/**
	 *
	 * @param saveScannedDeliverMails
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	public void saveScannedOffloadMails(Collection<OffloadVO> OffloadVosForSave)
			throws SystemException, RemoteException,
			MailTrackingBusinessException;

	/**
	 * @author A-1739 This method is used to returnMailbags
	 * @param mailbagsToReturn
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	@AvoidForcedStaleDataChecks
	Collection<ContainerDetailsVO> returnMailbags(
			Collection<MailbagVO> mailbagsToReturn) throws SystemException,
			RemoteException, MailTrackingBusinessException;

	/**
	 * Added by A-5526
	 *
	 * @param scannedMailDetailsVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailMLDBusniessException
	 */
	public void saveAndProcessMailBags(ScannedMailDetailsVO scannedMailDetailsVO)
			throws RemoteException, SystemException, MailHHTBusniessException,
			MailMLDBusniessException, MailTrackingBusinessException;

	/**
	 * @author a-1936 This method is used to validate the Flight
	 * @param flightFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Collection<FlightValidationVO> validateFlight(FlightFilterVO flightFilterVO)
			throws RemoteException, SystemException;

	/**
	 *
	 * Method : MailTrackingDefaultsBI.validateMailFlight Added by : A-5160 on
	 * 26-Nov-2014 Used for : validate flight for mail Parameters : @param
	 * flightFilterVO Parameters : @return Parameters : @throws RemoteException
	 * Parameters : @throws SystemException Return type : Collection
	 */
	public Collection<FlightValidationVO> validateMailFlight(
			FlightFilterVO flightFilterVO) throws RemoteException,
			SystemException;

	/**
	 * @author A-2037 The method is used to find the mail acceptance details for
	 *         flight assigned.
	 * @param operationalFlightVO
	 * @return MailAcceptanceVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	MailAcceptanceVO findFlightAcceptanceDetails(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			RemoteException;

	/**
	 * @author a-1936 This method is used to get the Lopcation of the Warehouse
	 *         for acceptmail
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param transactionCodes
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Map<String, Collection<String>> findWarehouseTransactionLocations(
			LocationEnquiryFilterVO filterVO) throws SystemException,
			RemoteException;

	/**
	 * @author a-1936
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ContainerDetailsVO> findMailbagsInContainer(
			Collection<ContainerDetailsVO> containers) throws SystemException,
			RemoteException;

	/**
	 * @author A-1936 This method is used to find the WareHouses For Given
	 *         Airport
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<WarehouseVO> findAllWarehouses(String companyCode,
			String airportCode) throws SystemException, RemoteException;

	/**
	 * Need 2 checks: Checks whether the containe has any other assignment. If
	 * assigned to another flight, check the staus of that flight. If it is not
	 * departed, throw exception saying that 'Already assigned to another open
	 * flight' . If that flight is departed, it can be assigned. If the ULD
	 * final destination does not match with the last POU in onward flight
	 * routing then throw exception
	 *
	 * @author a-1936
	 * @param containerVO
	 * @throws RemoteException
	 * @throws SystemException
	 */

	ContainerVO validateContainer(String airportCode, ContainerVO containerVO)
			throws RemoteException, SystemException,
			MailTrackingBusinessException;

	/**
	 * @author a-1936 Checks whether the given flight is closed for mail
	 *         operations at current airport.
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	boolean isFlightClosedForMailOperations(
			OperationalFlightVO operationalFlightVO) throws RemoteException,
			SystemException;

	/**
	 * @author A-2037 Method for OfficeOfExchangeLOV containing code and
	 *         description
	 * @param companyCode
	 * @param code
	 * @param description
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(OfficeOfExchangeVO officeofExchangeVO , int pageNumber,int defaultSize)
			throws SystemException, RemoteException;

	/**
	 * @author A-2037 Method for MailSubClassLOV containing code and description
	 * @param companyCode
	 * @param code
	 * @param description
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<MailSubClassVO> findMailSubClassCodeLov(String companyCode,
			String code, String description, int pageNumber,int defaultSize)
			throws SystemException, RemoteException;

	/**
	 * * @author A-2037 This method is used to find Postal Administration Code
	 *
	 * @param companyCode
	 * @param paCode
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	PostalAdministrationVO findPACode(String companyCode, String paCode)
			throws SystemException, RemoteException;

	/**
	 * @author A-1936 This method is used to validateMailBags
	 * @param mailbagVos
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	boolean validateMailBags(Collection<MailbagVO> mailbagVos)
			throws SystemException, RemoteException,
			MailTrackingBusinessException;

	/**
	 * @author a-1936 This method is used to validate the WareHouse Location
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param locationCode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	LocationValidationVO validateLocation(String companyCode,
			String airportCode, String warehouseCode, String locationCode)
			throws SystemException, RemoteException;

	/**
	 * @author A-3227 - FEB 10, 2009
	 * @param companyCode
	 * @param despatchDetailsVOs
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<DespatchDetailsVO> validateConsignmentDetails(
			String companyCode, Collection<DespatchDetailsVO> despatchDetailsVOs)
			throws SystemException, RemoteException;

	/**
	 * This method is used to validate the DSNs Say OOE,DOE
	 *
	 * @param dsnVos
	 * @throws SystemException
	 * @throws RemoteException
	 */
	boolean validateDSNs(Collection<DSNVO> dsnVos) throws SystemException,
			RemoteException, MailTrackingBusinessException;

	/**
	 * Method to find all ulds in Flight
	 *
	 * @param reassignedFlightValidationVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ContainerVO> findAllULDsInAssignedFlight(
			FlightValidationVO reassignedFlightValidationVO)
			throws SystemException, RemoteException;

	public void reassignContainers(
			Collection<ContainerVO> containersToReassign,
			OperationalFlightVO toFlightVO) throws SystemException,
			RemoteException, MailTrackingBusinessException;

	public MailAcceptanceVO findDestinationAcceptanceDetails(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			RemoteException;

	public Collection<PartnerCarrierVO> findAllPartnerCarriers(
			String companyCode, String ownCarrierCode, String airportCode)
			throws SystemException, RemoteException;

	public Collection<CoTerminusVO> findAllCoTerminusAirports(
			CoTerminusFilterVO filterVO)
			throws SystemException, RemoteException;

	/*added by A-8149 for ICRD-243386*/
	public Page<MailServiceStandardVO> listServiceStandardDetails(
			MailServiceStandardFilterVO mailServiceStandardFilterVO,int pageNumber )
			throws SystemException, RemoteException;
/**
 *
 * 	Method		:	MailTrackingDefaultsBI.findRdtMasterDetails
 *	Added by 	:	A-6991 on 17-Jul-2018
 * 	Used for 	:   ICRD-212544
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<MailRdtMasterVO>
 */
	public Collection<MailRdtMasterVO> findRdtMasterDetails(
			RdtMasterFilterVO filterVO)
			throws SystemException, RemoteException;
	
	public boolean validateCoterminusairports(String actualAirport,String eventAirport,String eventCode,String paCode,LocalDate dspDate) throws SystemException,RemoteException;

	/**
	 * @author A-2037 This method is used to find Preadvice for outbound mail
	 *         and it gives the details of the ULDs and the receptacles based on
	 *         CARDIT
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public PreAdviceVO findPreAdvice(OperationalFlightVO operationalFlightVO)
			throws SystemException, RemoteException;
		 /**
		    * @author a-1936
		    * This method is used to find the Transfer Manifest for the Different Transactions
		    * @param tranferManifestFilterVo
		    * @return
		    * @throws SystemException
		    * @throws RemoteException
		    */
		   public Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo)
		   throws SystemException ,RemoteException;

	/**
	 * @author a-1936 This method is used to find the containers and its
	 *         associated DSNs in the Flight.
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public MailManifestVO findContainersInFlightForManifest(
			OperationalFlightVO operationalFlightVo) throws SystemException,
			RemoteException;

	/**
	 * @author a-1936 This method is used to find out the Mail Bags and
	 *         Despatches in the Containers for the Manifest..
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ContainerDetailsVO> findMailbagsInContainerForManifest(
			Collection<ContainerDetailsVO> containers) throws SystemException,
			RemoteException;

	/**
	 * @author a-1883
	 * @param companyCode
	 * @param airportCode
	 * @param isGHA
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String findStockHolderForMail(String companyCode,
			String airportCode, Boolean isGHA) throws SystemException,
			RemoteException;

	/**
	 * @author a-1883
	 * @param aWBFilterVO
	 * @return AWBDetailVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	public AWBDetailVO findAWBDetails(AWBFilterVO aWBFilterVO)
			throws SystemException, RemoteException,
			MailTrackingBusinessException;

	/**
	 * @author a-3251 SREEJITH P.C.
	 * @param operationalFlightVO
	 * @param mailManifestVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingBusinessException
	 */

	public void closeFlightManifest(OperationalFlightVO operationalFlightVO,
			MailManifestVO mailManifestVO) throws SystemException,
			RemoteException, MailTrackingBusinessException;

	/**
	 * This method is used to find the Offload Details for a Flight say at
	 * different levels say Containers,DSNS,MailBags
	 *
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public OffloadVO findOffloadDetails(OffloadFilterVO offloadFilterVO)
			throws SystemException, RemoteException;

	/**
	 * @author a-1936 This method is used to reopen the Flight
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void reopenFlight(OperationalFlightVO operationalFlightVO)
			throws SystemException, RemoteException;

	/**
	 * @author a-3251 SREEJITH P.C.
	 * @param operationalFlightVO
	 * @param mailAcceptanceVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingBusinessException
	 */

	public void closeFlightAcceptance(OperationalFlightVO operationalFlightVO,
			MailAcceptanceVO mailAcceptanceVO) throws SystemException,
			RemoteException, MailTrackingBusinessException;

	/**
	 * @return
	 * @throws SystemException
	 */
	public OfficeOfExchangeVO validateOfficeOfExchange(String companyCode,
			String officeOfExchange) throws SystemException, RemoteException;

	/**
	 * @author a-1936 This method is used to find the mailBags
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<MailbagVO> findMailbags(
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws SystemException, RemoteException;


			  /**
			 * @author a-2553
			 * Added By Paulson as the  part of  the Air NewZealand CR...
			 * @param operationalFlightVo
			 * @return
			 */
			public Map<String,Object> generateTransferManifestReport(ReportSpec reportSpec)
				throws SystemException, RemoteException ;


			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsBI.generateTransferManifestMailbagLevelReport
			 *	Added by 	:	A-8061 on 09-Nov-2020
			 * 	Used for 	:
			 *	Parameters	:	@param reportSpec
			 *	Parameters	:	@return
			 *	Parameters	:	@throws SystemException
			 *	Parameters	:	@throws RemoteException 
			 *	Return type	: 	Map<String,Object>
			 */
			public Map<String,Object> generateTransferManifestMailbagLevelReport(ReportSpec reportSpec)
					throws SystemException, RemoteException ;


		    /**
		     * @param containerVOs
		     * @param operationalFlightVO
		     * @throws SystemException
		     * @throws RemoteException
		     * @throws MailTrackingBusinessException
		     */
		    @AvoidForcedStaleDataChecks
		    TransferManifestVO transferContainers(Collection<ContainerVO>
		    containerVOs,OperationalFlightVO operationalFlightVO,String printFlag)
		    throws SystemException,RemoteException, MailTrackingBusinessException;

		    /**
			 * @author A-2037 This method is used to find the History of a Mailbag
			 * @param companyCode
			 * @param mailbagId
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			Collection<MailbagHistoryVO> findMailbagHistories(String companyCode,String mailBagId,  /*modified by A-8149 for ICRD-248207*/
					long mailSequenceNumber) throws SystemException, RemoteException;


			//Added for IASCB-174718
			Collection<MailHistoryRemarksVO> findMailbagNotes(String mailBagId) 
					throws SystemException, RemoteException;
			 /**
		     *
		     * @param reportSpec
		     * @throws SystemException
		     * @throws RemoteException
		     * @throws ReportGenerationException
		     */
		    Map generateFindMailbagHistoriesReport(ReportSpec reportSpec)
		    throws RemoteException, SystemException, ReportGenerationException;



			/**
			 *  This method is used to save Postal Administration Code
			 * @param postalAdministrationVO
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws MailTrackingBusinessException
			 */
			void savePACode(PostalAdministrationVO postalAdministrationVO)
			throws SystemException, RemoteException, MailTrackingBusinessException;
			/**
			 *This method is used to save office of Exchange Code
			 * @param officeOfExchangeVOs
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws MailTrackingBusinessException
			 */
			void saveOfficeOfExchange(Collection<OfficeOfExchangeVO> officeOfExchangeVOs)
					throws SystemException, RemoteException,
					MailTrackingBusinessException;

			/**
			 * @author a-2037 This method is used to save Mail sub class codes
			 * @param mailSubClassVOs
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws MailTrackingBusinessException
			 */
			void saveMailSubClassCodes(Collection<MailSubClassVO> mailSubClassVOs)
					throws SystemException, RemoteException,
					MailTrackingBusinessException;

			 /**
			 *
			 * @param reportSpec
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws ReportGenerationException
			 */
			Map<String,Object> generateMailTag(ReportSpec reportSpec)
			throws SystemException,RemoteException,ReportGenerationException;

	/**
	 * method added for new search consignment screen
	 *
	 * @author A-2667
	 * @param carditEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws MailTrackingBusinessException
	 * @throws RemoteException
	 */
	public Collection<MailbagVO> findConsignmentDetails(
			CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws SystemException, MailTrackingBusinessException,
			RemoteException;

	/**
	 * @author A-3227 RENO K ABRAHAM
	 * @param dsnVO
	 * @param mode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<DespatchDetailsVO> findDespatchesOnDSN(DSNVO dsnVO,
			String mode) throws SystemException, RemoteException;

	/**
	 * @author a-1936 This method is used to remove the EmptyULDs(ULDs with no
	 *         MailBags\Despatches)
	 * @param containerDetailsVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void unassignEmptyULDs(
			Collection<ContainerDetailsVO> containerDetailsVOs)
			throws SystemException, RemoteException;

	/**
	 * @author a-1936 This method is used to return all the containers assigned
	 *         to a particularFlight
	 * @param operationalFlightVO
	 * @return Collection<ContainerVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<ContainerVO> findFlightAssignedContainers(
			OperationalFlightVO operationalFlightVO) throws RemoteException,
			SystemException;

	/**
	 * @author a-1936 This method is used to return all the containers assigned
	 *         to a particular destination and Carrier
	 * @param destinationFilterVO
	 * @return Collection<ContainerVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<ContainerVO> findDestinationAssignedContainers(
			DestinationFilterVO destinationFilterVO) throws RemoteException,
			SystemException;

	/**
	 * This method is used to reassign the MailBags
	 *
	 * @param mailbagsToReassign
	 * @param toContainerVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	public Collection<ContainerDetailsVO> reassignMailbags(
			Collection<MailbagVO> mailbagsToReassign, ContainerVO toContainerVO)
			throws SystemException, RemoteException,
			MailTrackingBusinessException;


			/**
			 * @author A-2037 This method is used to find Local PAs
			 * @param companyCode
			 * @param countryCode
			 * @return Collection<PostalAdministrationVO>
			 * @throws SystemException
			 * @throws RemoteException
			 */
			Collection<PostalAdministrationVO> findLocalPAs(String companyCode,
					String countryCode) throws SystemException, RemoteException;


	/**
	 * returns data for displaying manifest level Jan 19, 2007, A-1739
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws SystemException
	 */
	public Map<String, Object> generateMailManifest(ReportSpec reportSpec)
			throws SystemException, RemoteException;

	/**
	 * @author A-3227 Reno K Abraham
	 * @param reportSpec
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ReportGenerationException
	 */
	public Map generateFindDamageMailReport(ReportSpec reportSpec)
			throws RemoteException, SystemException, ReportGenerationException;


	 /**
	 * This method will implement the audit method in Auditor.
	 * It will calls the audit method of the audit controller.
	 * @param auditVo
	 * @throws AuditException
	 * @throws RemoteException
	 * @return
	 */
   void audit(Collection<AuditVO> auditVo) throws AuditException, RemoteException;

	/**
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 */
	public void saveConsignmentDocumentFromManifest(Collection<ConsignmentDocumentVO> consignmentDocumentVOs)
	throws SystemException,RemoteException;

	/**
		 *
		 * @param conatinerstoAcquit
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void autoAcquitContainers(Collection<ContainerDetailsVO> conatinerstoAcquit)
		throws SystemException,RemoteException;
		   /**
		    * This method fetches the latest Container Assignment
		    * irrespective of the PORT to which it is assigned.
		    * This to know the current assignment of the Container.
		    *
		    * @param containerNumber
		    * @return
		    * @throws SystemException
		    * @throws RemoteException
		    * @throws MailTrackingBusinessException
		    */
			public ContainerAssignmentVO findLatestContainerAssignment(String containerNumber)
			throws SystemException,RemoteException,MailTrackingBusinessException;
			 /***
			  * @author a-6245
			  * @param mailArrivalVOs
			  * @throws SystemException
			  * @throws RemoteException
			  * @throws MailTrackingBusinessException
			  */
			 @AvoidForcedStaleDataChecks
				void saveChangeFlightDetails(Collection<MailArrivalVO>mailArrivalVOs)
						throws SystemException, RemoteException,MailTrackingBusinessException;
			    /**
			     * @author a-1883 This method is used to reopen the InboundFlight
			     * @param operationalFlightVO
			     * @throws SystemException
			     * @throws RemoteException
			     */
			    @AvoidForcedStaleDataChecks
			    void reopenInboundFlight(OperationalFlightVO operationalFlightVO)
			            throws SystemException ,RemoteException;
				   /**
				*
				* Jan 25, 2007, A-1739
				* @param carditEnquiryFilterVO
				* @return
				* @throws SystemException
				* @throws RemoteException
				*/
			   CarditEnquiryVO findCarditDetails(CarditEnquiryFilterVO carditEnquiryFilterVO)
			   	throws SystemException, RemoteException;
				/**
				 * Send a RESDIT message manually from carditenq screen
				 * Feb 9, 2007, A-1739
				 * @param carditEnquiryVO
				 * @throws SystemException
				 * @throws RemoteException
				 */
				void sendResdit(CarditEnquiryVO carditEnquiryVO)
					throws SystemException , RemoteException,MailTrackingBusinessException;
				   /**
			     * @author A-3251
			     * @param reportSpec
			     * @throws SystemException
			     * @throws RemoteException
			     * @throws ReportGenerationException
			     */
			    Map generateDailyMailStationReport(ReportSpec reportspec)
			    throws RemoteException,SystemException,ReportGenerationException;
				/**
				 * @author A-2037 Method for PALov containing PACode and PADescription
				 * @param companyCode
				 * @param paCode
				 * @param paName
				 * @param pageNumber
				 * @return
				 * @throws SystemException
				 * @throws RemoteException
				 */
				Page<PostalAdministrationVO> findPALov(String companyCode, String paCode,
						String paName, int pageNumber,int defaultSize) throws SystemException,
						RemoteException;
				/**
	 * @author a-1883 This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public ConsignmentDocumentVO findConsignmentDocumentDetails(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException,
			RemoteException;

	/**
	 * @author a-1936
	 * @param containerVOs
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingBusinessException
	 */
	public void deleteContainers(Collection<ContainerVO> containerVOs)
			throws RemoteException, SystemException,
			MailTrackingBusinessException;


	/**
				 * This method deletes Consignment document details and its childs
				 *
				 * @author a-1883
				 * @param consignmentDocumentVO
				 * @throws SystemException
				 * @throws RemoteException
				 * @throws MailTrackingBusinessException
				 */
				@AvoidForcedStaleDataChecks
				void deleteConsignmentDocumentDetails(
						ConsignmentDocumentVO consignmentDocumentVO)
						throws SystemException, RemoteException,
						MailTrackingBusinessException;

	/**
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public  String findPAForOfficeOfExchange(
			String companyCode, String officeOfExchange)
	throws SystemException , RemoteException;

			/**
			 * @author a-1739
			 * @param mailbagVOs
			 * @throws SystemException
			 * @throws RemoteException
			 */
			@AvoidForcedStaleDataChecks
			void saveDamageDetailsForMailbag(Collection<MailbagVO> mailbagVOs)
					throws SystemException, RemoteException;


			/**
			 * @author A-2037 This method is used to find the Damaged Mailbag Details
			 * @param companyCode
			 * @param mailbagId
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			Collection<DamagedMailbagVO> findMailbagDamages(String companyCode,
					String mailbagId) throws SystemException, RemoteException;


			/**
			 * @author a-1936 This method i sused to find the containerDetails
			 * @param searchContainerFilterVO
			 * @param pageNumber
			 * @return
			 * @throws RemoteException
			 * @throws SystemException
			 */

			Page<ContainerVO> findContainers(
					SearchContainerFilterVO searchContainerFilterVO, int pageNumber)
					throws RemoteException, SystemException;


			  /**
		 	 * @author A-2553 Paulson Ouseph A
			 * @param reportSpec
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public Map generateMailStatusReport(ReportSpec reportSpec)
				throws  SystemException,RemoteException,ReportGenerationException;




	/**
	 * @author a-2037 This method is used to find all the mail subclass codes
	 * @param companyCode
	 * @param subclassCode
	 * @return Collection<MailSubClassVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<MailSubClassVO> findMailSubClassCodes(String companyCode,
			String subclassCode) throws SystemException, RemoteException;

	public Collection<EmbargoDetailsVO> checkEmbargoForMail(
			Collection<ShipmentDetailsVO> shipmentDetailsVos) throws  SystemException,RemoteException ;
			/**
			 * a-2553
			 * @param carditEnquiryFilterVO,pageNumber
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO,int pageNumber)
			throws SystemException, RemoteException;

			/**
			 * @author a-2037 This method is used to find all the mail subclass codes
			 * @param companyCode
			 * @param officeOfExchange
			 * @param pageNumber
			 * @return Collection<OfficeOfExchangeVO>
			 * @throws SystemException
			 * @throws RemoteException
			 */
			Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode,
					String officeOfExchange, int pageNumber) throws SystemException,
					RemoteException;
			/**
			 * @author A-5931 Method for MailBoxIdLov containing mailboxCode and mailboxDesc
			 * @param companyCode
			 * @param mailboxCode
			 * @param mailboxDesc
			 * @param pageNumber
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			Page<MailBoxIdLovVO> findMailBoxIdLov(String companyCode, String mailboxCode,
					String mailboxDesc, int pageNumber,int defaultSize) throws SystemException,
					RemoteException;

			/**
			 * @author A-5931 Method for saving Collection<MailBoxIdLovVO
			 * @param Collection<MailBoxIdLovVO
			 * @param void
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			// Commented the method as part of ICRD-153078
			 // Uncommented as part of ICRD-234820
			public void saveMailboxIDs(Collection<MailBoxIdLovVO > mailBoxIdVOs)throws SystemException, RemoteException;

			/**
			 * @author a-1936 This method is used to save the container details for the
			 *         Flight
			 * @param operationalFlightVO
			 * @param containerVos
			 * @throws SystemException
			 * @throws RemoteException
			 */
			void saveContainers(OperationalFlightVO operationalFlightVO,
					Collection<ContainerVO> containerVos) throws SystemException,
					RemoteException, MailTrackingBusinessException;

			/**
			 * @author A-1739 This method is used to find Arrival Details
			 * @param opFlightVO
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */

			MailArrivalVO findArrivalDetails(MailArrivalFilterVO mailArrivalFilterVO)
					throws SystemException, RemoteException;


			  /**
		     * For validating inb flight
		     * Oct 6, 2006, a-1739
		     * @param flightVO
		     * @return
		     * @throws SystemException
		     * @throws RemoteException
		     */
		    OperationalFlightVO validateInboundFlight(OperationalFlightVO flightVO)
		   	throws SystemException ,RemoteException;


			/**
			 * @author A-1739 This method is used to saveArrivalDetails
			 * @param mailArrivalVO
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws MailTrackingBusinessException
			 */
			@AvoidForcedStaleDataChecks
			void saveArrivalDetails(MailArrivalVO mailArrivalVO)
					throws SystemException, RemoteException,MailTrackingBusinessException;

		    /**
		     * @author a-1883
		     * @param operationalFlightVO
		     * @return boolean
		     * @throws SystemException
		     * @throws RemoteException
		     */
		    boolean isFlightClosedForInboundOperations(OperationalFlightVO
		            operationalFlightVO) throws SystemException,RemoteException;


			/**
			 * @author A-5945 This method is used to undo arrive mailbags
			 * @param mailArrivalVO
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws MailTrackingBusinessException
			 */
		    void undoArriveContainer(MailArrivalVO mailarrivalvo) throws SystemException, RemoteException, MailTrackingBusinessException;

		    /**
		     * @author a-1883 This method is used to close the InboundFlight
		     * @param operationalFlightVO
		     * @throws SystemException
		     * @throws RemoteException
		     * @throws MailTrackingBusinessException
		     */
		    @AvoidForcedStaleDataChecks
		    void closeInboundFlight(OperationalFlightVO operationalFlightVO)
		            throws SystemException ,RemoteException, MailTrackingBusinessException;

		    /**
		       * @author a-1883
		       * @param operationalFlightVO
		       * @return
		       * @throws SystemException
		       * @throws RemoteException
		       */
		       Collection<MailDiscrepancyVO> findMailDiscrepancies(
		      		OperationalFlightVO operationalFlightVO)
		      		throws SystemException,RemoteException;

		       /**
				 * @author a-1883
				 * @param consignmentDocumentVO
				 * @return Integer
				 * @throws SystemException
				 * @throws RemoteException
				 * @throws MailTrackingBusinessException
				 */
				@AvoidForcedStaleDataChecks
				Integer saveConsignmentDocument(ConsignmentDocumentVO consignmentDocumentVO)
						throws SystemException, RemoteException,
						MailTrackingBusinessException,DuplicateDSNException;


				   /**
				* @author A-2107
				* @param consignmentFilterVO
				* @throws SystemException
				* @throws RemoteException
				*/
			   public Collection<MailbagVO> findCartIds(ConsignmentFilterVO consignmentFilterVO)
			   throws SystemException,RemoteException;
			   /**
				   * @author A-3227  - FEB 18, 2009
				   * @param companyCode
				   * @param officeOfExchanges
				   * @return
				   * @throws SystemException
				   * @throws RemoteException
				   */
				   public Collection<ArrayList<String>> findCityAndAirportForOE(String companyCode,Collection<String> officeOfExchanges)
			   throws SystemException,RemoteException;
					/**
					 * Added for icrd-95515
					 * @param companyCode
					 * @param airportCode
					 * @return
					 * @throws SystemException
					 * @throws RemoteException
					 */
					public  Collection<String> findOfficeOfExchangesForAirport(
							String companyCode, String airportCode)throws SystemException,RemoteException;
/**
				 * @return
				 * @throws SystemException
				 */
				public Collection<MailbagVO> findDSNMailbags(DSNVO dsnVO)
				   throws SystemException,RemoteException;
			    /**
				    * @author a-2553
				    * @param despatchDetailsVOs
				    * @param mailbagVOs
				    * @param containerVO
				    * @param toPrint
				    * @return
				    * @throws SystemException
				    *  @throws RemoteException
				    */
				  //Modified as part of bug ICRD-97415 by A-5526 starts(return type void to TransferManifestVO)
				    @AvoidForcedStaleDataChecks
				    TransferManifestVO transferMail(Collection<DespatchDetailsVO> despatchDetailsVOs,
						   Collection<MailbagVO> mailbagVOs,ContainerVO containerVO,String toPrint)
				     throws SystemException,RemoteException,MailTrackingBusinessException;


				    /**
				     *
				     * @param companyCode
				     * @param officeOfExchange
				     * @return
				     * @throws SystemException
				     * @throws RemoteException
				     */
				    PostalAdministrationVO findPADetails(
				            String companyCode,String officeOfExchange)
				            throws SystemException, RemoteException;
				    /**
				     * @author A-3227 RENO K ABRAHAM
					 * @param companyCode
					 * @param subclass
					 * @return
				     * @throws SystemException
				     * @throws RemoteException
				     */
				    boolean validateMailSubClass(String companyCode,String subclass)
			   		throws  SystemException,RemoteException;
					/**
					 * @author A-3251
					 * @param postalAdministrationDetailsVO
					 * @throws SystemException
					 * @throws RemoteException	 *
					 */
				   public PostalAdministrationDetailsVO validatePoaDetails(PostalAdministrationDetailsVO postalAdministrationDetailsVO)
				   throws SystemException,RemoteException;
					/**
					 * 	Method		:	MailTrackingDefaultsBI.findAllPACodes
					 *	Added by 	:	A-4809 on 08-Jan-2014
					 * 	Used for 	:	ICRD-42160 to find all activePAs
					 *	Parameters	:	@param generateInvoiceFilterVO
					 *	Parameters	:	@return
					 *	Parameters	:	@throws SystemException
					 *	Parameters	:	@throws RemoteException
					 *	Return type	: 	Collection<PostalAdministrationVO>
					 */
					public Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO)
					throws SystemException,RemoteException;
					/**
					 * 	Method		:	MailTrackingDefaultsBI.auditMailbagsForMRA
					 *	Added by 	:	a-4809 on Apr 3, 2014
					 * 	Used for 	:	to stamp audit entries for mailbag
					 *	Parameters	:	@param dsnAuditVOs
					 *	Parameters	:	@throws SystemException
					 *	Parameters	:	@throws RemoteException
					 *	Return type	: 	void
					 */
					/* Commented the method as part of ICRD-153078
					public void auditMailbagsForMRA(Collection<MailbagAuditVO> mailbagAuditVOs)
				            throws SystemException, RemoteException;*/
							/**
				  *
				  * @param companyCode
				  * @return
				  * @throws SystemException
				  * @throws RemoteException
				  * @throws MailHHTBusniessException
				  * @throws MailMLDBusniessException
				  * @throws MailTrackingBusinessException
				  */
					public Collection<MailScanDetailVO> fetchMailScannedDetails(String companyCode,int uploadCount)
					throws SystemException, RemoteException,MailHHTBusniessException,
					MailMLDBusniessException,MailTrackingBusinessException;

					/**
					 *
					 * @param mailScanDetailVOs
					 * @throws SystemException
					 * @throws RemoteException
					 */
					/* Commented the method as part of ICRD-153078
					public void updateMailUploadstatus(MailScanDetailVO mailScanDetailVOs,String status)
					throws SystemException, RemoteException;*/


					public void saveMailUploadDetailsFromJob( Collection<MailScanDetailVO> mailScanDetailVOs,
							String scanningPort) throws RemoteException, SystemException;

					   public void saveConsignmentForManifestedDSN(ConsignmentDocumentVO consignmentDocumentVO)
								throws SystemException, RemoteException,MailTrackingBusinessException;
					   /**
					     * @author A-3227 RENO K ABRAHAM
					     * @param reportSpec
					     * @throws SystemException
					     * @throws RemoteException
					     * @throws ReportGenerationException
					     */
					    Map generateMailHandedOverReport(ReportSpec reportSpec)
					    throws RemoteException, SystemException, ReportGenerationException;

					    /**
						 *
						 * 	Method		:	MailTrackingDefaultsBI.saveMailUploadDetailsFromMLD
						 *	Added by 	:	A-4803 on 14-Oct-2014
						 * 	Used for 	:   Saving details from MLD messages
						 *	Parameters	:	@param mldMasterVOs
						 *	Parameters	:	@throws RemoteException
						 *	Parameters	:	@throws SystemException
						 *	Return type	: 	void
						 * @throws MailHHTBusniessException
						 * @throws MailMLDBusniessException
						 */
						public Map<String, Collection<MLDMasterVO>> saveMailUploadDetailsFromMLD(Collection<MLDMasterVO> mldMasterVOs) throws
						RemoteException, SystemException, MailHHTBusniessException, MailMLDBusniessException,MailTrackingBusinessException;

						/**
						 *
						 * @param mLDConfigurationFilterVO
						 * @return
						 * @throws SystemException
						 * @throws RemoteException
						 */
						public Collection<MLDConfigurationVO> findMLDCongfigurations(MLDConfigurationFilterVO mLDConfigurationFilterVO ) throws SystemException,RemoteException;
						/**
						 *
						 * @param mLDConfigurationVOs
						 * @throws SystemException
						 * @throws RemoteException
						 * @throws MailTrackingBusinessException
						 */
						void saveMLDConfigurations(Collection<MLDConfigurationVO> mLDConfigurationVOs)throws SystemException, RemoteException, MailTrackingBusinessException;
						/**
						 * @author A-1936 This method is used to save the PartnerCarriers..
						 * @param partnerCarrierVOs
						 * @throws SystemException
						 * @throws RemoteException
						 * @throws MailTrackingBusinessException
						 */
						public void savePartnerCarriers(
								Collection<PartnerCarrierVO> partnerCarrierVOs)
								throws SystemException, RemoteException,
								MailTrackingBusinessException;

						public void saveCoterminusDetails(
								Collection<CoTerminusVO> coterminusVOs)
								throws SystemException, RemoteException,
								MailTrackingBusinessException;

						/*added by A-8149 for ICRD-243386*/
						public void saveServiceStandardDetails(
								Collection<MailServiceStandardVO> mailServiceStandardVOs, Collection<MailServiceStandardVO> mailServiceStandardVOstodelete)
								throws SystemException, RemoteException,
								MailTrackingBusinessException;
                               public void saveRdtMasterDetails(
								Collection<MailRdtMasterVO> mailRdtMasterVOs)
								throws SystemException, RemoteException,
								MailTrackingBusinessException;
					   	      public Collection<ErrorVO> saveRdtMasterDetailsXls(
								Collection<MailRdtMasterVO> mailRdtMasterVOs)
								throws SystemException, RemoteException,
								MailTrackingBusinessException;
						
						/**
						 * TODO Purpose Feb 2, 2007, A-1739
						 *
						 * @param resditConfigVO
						 * @throws SystemException
						 * @throws RemoteException
						 * @see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#saveResditConfiguration(com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditConfigurationVO)
						 */
						public void saveResditConfiguration(ResditConfigurationVO resditConfigVO)
								throws SystemException, RemoteException;

						/**
						 * TODO Purpose Feb 2, 2007, A-1739
						 *
						 * @param companyCode
						 * @param carrierId
						 * @return
						 * @throws SystemException
						 * @throws RemoteException
						 * @see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#findResditConfigurationForAirline(java.lang.String,
						 *      int)
						 */
						public ResditConfigurationVO findResditConfigurationForAirline(
								String companyCode, int carrierId) throws SystemException,
								RemoteException;




						/**
						 * This method is used to generate the Import Manifest Report ..
						 * @param reportSpec
						 * @return
						 * @throws SystemException
						 * @throws RemoteException
						 */
						 Map<String,Object> generateImportManifestReport(ReportSpec reportSpec)
							throws SystemException ,RemoteException;

						 /**
						     * @author A-5526
						     * @param reportSpec
						     * @throws SystemException
						     * @throws RemoteException
						     * @throws ReportGenerationException
						     */
						    Map generateTransferManifestReportForMail(ReportSpec reportSpec)
						    throws RemoteException, SystemException, ReportGenerationException;
							   /**
							 * This method does the ULD Acquittal at Non Mechanized port
							 * @author A-3227  RENO K ABRAHAM - 09/09/2009
							 * @param operationalFlightVO the operationalFlightVO
							 * @throws SystemException
							 * @throws RemoteException
							 */
						    /**
						     * @author A-5526
						     * @param reportSpec
						     * @throws SystemException
						     * @throws RemoteException
						     * @throws ReportGenerationException
						     */
						    Map generateTransferManifestReportForContainer(ReportSpec reportSpec)
						    throws RemoteException, SystemException, ReportGenerationException;
							void initiateULDAcquittance(OperationalFlightVO operationalFlightVO)
							throws SystemException, RemoteException;
							/**
							 * @author A-5526
							 * This method is to send MLD messages
							 * @param companyCode
							 * @throws SystemException
							 * @throws RemoteException
							 */
							public void triggerMLDMessages(String companyCode,int recordCount) throws SystemException,RemoteException;
							/**
							 * Close inbound flight for mail operation.
							 *
							 * @param companyCode the company code
							 * @throws SystemException the system exception
							 * @throws RemoteException the remote exception
							 */
							void closeInboundFlightForMailOperation(String companyCode)
							throws SystemException, RemoteException;
							/**
							 * @author A-1885
							 * @param companyCode
							 * @param time
							 * @throws SystemException
							 * @throws RemoteException
							 */
							public void closeFlightForMailOperation(String companyCode,int time, String airportCode)
						throws SystemException,RemoteException;
							/**
							 * @author A-5166
							 * Added for ICRD-36146 on 07-Mar-2013
							 * @param companyCode
							 * @throws SystemException
							 * @throws RemoteException
							 */
							void initiateArrivalForFlights(ArriveAndImportMailVO arriveAndImportMailVO)
							throws SystemException, RemoteException;


							 /***
							 * @param dSNEnquiryFilterVO
							 * @param pageNumber
							 * @return
							 * @throws SystemException
							 * @throws RemoteException
							 */
							Page<DespatchDetailsVO> findDSNs(DSNEnquiryFilterVO dSNEnquiryFilterVO,	int pageNumber) throws SystemException, RemoteException;
							/**
							 *
							 * @param reportSpec
							 * @return
							 * @throws SystemException
							 * @throws RemoteException
							 * @throws ReportGenerationException
							 */
							Map<String,Object> generateConsignmentReport(ReportSpec reportSpec)
							throws SystemException,RemoteException,ReportGenerationException;

							/**
							 * @author A-1739 This method is used to saveCarditMessages
							 * @param ediInterchangeVO
							 * @throws RemoteException
							 * @throws SystemException
							 * @throws MailTrackingBusinessException
							 * @throws InvocationTargetException 
							 * @throws IllegalAccessException 
							 */
							 @AvoidForcedStaleDataChecks
							Collection<ErrorVO> saveCarditMessages(EDIInterchangeVO ediInterchangeVO)
									throws RemoteException, SystemException, MailTrackingBusinessException, IllegalAccessException, InvocationTargetException;

							 /**
							     * @param mailbags
							     * @return
							     * @throws SystemException
							     * @throws RemoteException
							     * @throws MailTrackingBusinessException
							     */
							    Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagVO> validateScannedMailbagDetails(
										Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagVO> mailbags)
										throws SystemException, RemoteException,
											MailTrackingBusinessException;

							    /**
								 * Invokes the RDT_EVT_RCR procedure
								 * A-1739
								 * @param companyCode the company code
								 * @throws SystemException
								 * @throws RemoteException
								 */
								void invokeResditReceiver(String companyCode)
								throws SystemException, RemoteException;

								/**
								 * Starts resdit building
								 * Sep 8, 2006, a-1739
								 * @param companyCode
								 * @throws SystemException
								 * @throws RemoteException
								 */
								Collection<ResditEventVO> checkForResditEvents(String companyCode)
								throws SystemException, RemoteException;

								/**
								 * method: findMailbagsforFlightSegments
								 * @author A-5249
								 * to change the assigned flight status to TBA if mailbag present
								 * @param cmpcod
								 * @param carierid
								 * @param fltnum
								 * @param fltseqnum
								 * @param segments
								 * @return boolean
								 * @throws SystemException
								 * @throws RemoteException
								 */
							   boolean findMailbagsforFlightSegments( String cmpcod, int carierid, String fltnum,
										long fltseqnum, Collection<FlightSegmentVO> segments,String cancellation) throws SystemException,RemoteException;

								/**
								 * @author A-1936 This method is used to find the MailBags Accepted to a
								 *         ParticularFlight and Flag the Uplifted Resdits for the Same
								 * @param operationalFlightVO
								 * @throws SystemException
								 * @throws RemoteException
								 */
								void flagUpliftedResditForMailbags(
										Collection<OperationalFlightVO> operationalFlightVOs)
										throws SystemException, RemoteException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.flagTransportCompletedResditForMailbags
								 *	Added by 	:
								 * 	Used for 	:
								 *	Parameters	:	@param operationalFlightVOs
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Return type	: 	void
								 */
								public void flagTransportCompletedResditForMailbags(
									Collection<OperationalFlightVO> operationalFlightVOs)
									throws SystemException, RemoteException;
								/**
								 *
								 * Mar 28, 2007, a-1739
								 * @param containers
								 * @return
								 * @throws SystemException
								 */
								Map<String, ContainerAssignmentVO>
								findContainerAssignments(Collection<ContainerVO> containers)
								throws SystemException,RemoteException;
								/**
								 *
								 * @param controlReferenceNumber
								 * @param sendDate
								 * @param fileName
								 * @throws RemoteException
								 * @throws SystemException
								 * @author A-2572
								 */
								public void updateResditSendStatus(ResditMessageVO resditMessageVO)
								throws RemoteException, SystemException;
							    /**
								 *
								 * @param mailMRDMessageVO
								 * @throws RemoteException
								 * @throws SystemException
								 * @throws MailHHTBusniessException
								 */
								public Collection<ErrorVO> handleMRDMessage(MailMRDVO messageVO)

								throws RemoteException, SystemException,MailHHTBusniessException,MailTrackingBusinessException,TemplateRenderingException;
								

								/**
								 *
								 * @param resditEvents
								 * @throws SystemException
								 * @throws RemoteException
								 */
								void buildResdit(Collection<ResditEventVO> resditEvents)
								throws SystemException, RemoteException;
								/**
								 *
								 * @param companyCode
								 * @param txnId
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 */
								public Object[] getTxnParameters(String companyCode,String txnId)
										throws SystemException,RemoteException;



								public void resolveTransaction(String companyCode,String txnId,String remarks)
								throws RemoteException, SystemException ;
								/**
								 * @author A-5526
								 * @param reportSpec
								 * @return Map<String,Object>
								 * @throws SystemException
								 * @throws RemoteException
								 * @throws ReportGenerationException
								 */
								Map<String,Object> generateConsignmentReports(ReportSpec reportSpec)
								throws SystemException,RemoteException,ReportGenerationException;
								/**
								 * @author a-1883
								 * @param documentFilterVO
								 * @return DocumentValidationVO
								 * @throws SystemException
								 * @throws RemoteException
								 */
								DocumentValidationVO validateDocumentInStock(
										DocumentFilterVO documentFilterVO) throws SystemException,
										RemoteException;
								/**
								 * @author a-1883
								 * @param aWBDetailVO
								 * @param containerDetailsVO
								 * @throws SystemException
								 * @throws RemoteException
								 */
								@AvoidForcedStaleDataChecks
								void attachAWBDetails(AWBDetailVO aWBDetailVO,
										ContainerDetailsVO containerDetailsVO) throws SystemException,
										RemoteException,PersistenceException;
								/**
								 * This method deletes document from stock
								 *
								 * @author a-1883
								 * @param documentFilterVO
								 * @throws SystemException
								 * @throws RemoteException
								 */
								void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
										throws SystemException, RemoteException,
										MailTrackingBusinessException;
								/**
								 * Attatches awb by grouping and taking from stock
								 * Apr 11, 2007, a-1739
								 * @param containerDetailsVOs
								 * @param operationalFlightVO
								 * @return
								 * @throws RemoteException
								 * @throws SystemException
								 * @throws MailTrackingBusinessException
								 */
								@AvoidForcedStaleDataChecks
								Collection<ContainerDetailsVO> autoAttachAWBDetails(
							    		 Collection<ContainerDetailsVO> containerDetailsVOs,
							    		 OperationalFlightVO operationalFlightVO)
							    		 throws RemoteException,SystemException,
							    		 MailTrackingBusinessException;
								/**
								 * @author
								 * @param ContainerVO
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 */
								public void updateActualWeightForMailULD(ContainerVO containerVo)
								throws SystemException,RemoteException;
								/**
								 * @author A-1885
								 * @param webServicesVos
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 */

									public List <MailUploadVO>
									performMailOperationForGHA(Collection<MailWebserviceVO> webServicesVos,String scanningPort)
									throws SystemException,RemoteException,MailHHTBusniessException,MailTrackingBusinessException;

									public void handleAdvice(com.ibsplc.xibase.server.framework.interceptor.vo.AsyncAdviceHelperVO adviceAsyncHelperVO) throws RemoteException;

							    /**
							     * @author a-1936
							     * This  method is used to find the mailbags  and  the Despatches in the Flight  in a container for  the Manifest..
							     * @param containers
							     * @return
							     * @throws SystemException
							     * @throws RemoteException
							     */
							    Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(Collection<ContainerDetailsVO> containers)
							     throws SystemException,RemoteException;


									/**
									 * @author a-6371 This method is used to find the MailHandDetailsVo
									 * @param findMailOnHandDetails
									 * @return
									 * @throws RemoteException
									 * @throws SystemException
									 */

									public Page<MailOnHandDetailsVO> findMailOnHandDetails(
											SearchContainerFilterVO searchContainerFilterVO,int pageNumber)
											throws RemoteException, SystemException;


								/**
								 *
								 * @param mailMRDMessageVO
								 * @throws RemoteException
								 * @throws SystemException
								 * @throws MailHHTBusniessException
								 */
								public Collection<ErrorVO> handleMRDHO22Message(MailMRDVO messageVO)

								throws RemoteException, SystemException,MailHHTBusniessException,MailTrackingBusinessException,TemplateRenderingException;
								
								

								public void saveAllValidMailBags(Collection<ScannedMailDetailsVO> validScannedMailVOs)
								   throws SystemException,RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException;


								public Collection<MailUploadVO> createMailScanVOSForErrorStamping(
										Collection<MailWebserviceVO> mailWebserviceVOs,String scannedPort,StringBuilder errorString, String errorFromMapping ) throws SystemException,RemoteException,BusinessDelegateException ;


								MailManifestVO findMailAWBDetails(OperationalFlightVO operationalFlightVO)
								throws SystemException, RemoteException;

								/**
								 *
								 * @param operationalFlightVO
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 * @throws MailTrackingBusinessException
								 * @throws CloseFlightException
								 * @throws ULDDefaultsProxyException
								 */
								public void closeMailExportFlight(OperationalFlightVO operationalFlightVO)
								throws SystemException,RemoteException, MailTrackingBusinessException;

								/**
								 *
								 * @param operationalFlightVO
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 * @throws CloseFlightException
								 * @throws ULDDefaultsProxyException
								 * @throws MailTrackingBusinessException
								 */
								public void closeMailImportFlight(OperationalFlightVO operationalFlightVO)
								throws SystemException,RemoteException, MailTrackingBusinessException;
								/**
								 *
								 * @param fileUploadFilterVO
								 * @throws SystemException
								 * @throws RemoteException
								 * @throws MailTrackingBusinessException
								 */
								public String processMailOperationFromFile(
										FileUploadFilterVO fileUploadFilterVO)
								throws SystemException,RemoteException, MailTrackingBusinessException;
								/**
								 * fetchDataForOfflineUpload
								 *
								 * @param companyCode
								 * @param fileType
								 * @return
								 * @throws SystemException

								 */
								public Collection<MailUploadVO> fetchDataForOfflineUpload(
										String companyCode, String fileType) throws SystemException,RemoteException, MailTrackingBusinessException;
								/**
								 *
								 * @param fileUploadFilterVO
								 * @throws SystemException
								 * @throws RemoteException
								 * @throws MailTrackingBusinessException
								 */
								public void removeDataFromTempTable(
										FileUploadFilterVO fileUploadFilterVO)
								throws SystemException,RemoteException, MailTrackingBusinessException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.findOneTimeDescription
								 *	Added by 	:	A-6991 on 13-Jul-2017
								 * 	Used for 	:   ICRD-208718
								 *	Parameters	:	@param companyCode
								 *	Parameters	:	@return
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Parameters	:	@throws MailTrackingBusinessException
								 *	Return type	: 	Map<String,Collection<OneTimeVO>>
								 */
								public Map<String, Collection<OneTimeVO>> findOneTimeDescription(
										String companyCode,String oneTimeCode) throws SystemException,RemoteException, MailTrackingBusinessException;
								/**
								 * Method		:	MailTrackingDefaultsBI.validateULDsForOperation
								 * Added by 	:	A-7794 on 25-Sept-2017
								 * Used for 	:   ICRD-223303
								 * @param flightDetailsVo
								 * @return
								 * @throws SystemException
								 */
								public void validateULDsForOperation(FlightDetailsVO flightDetailsVo) throws SystemException, RemoteException;



								/**
								 * @author a-7794 This method is used to...fetch audit details
								 * @param mailAuditFilterVO
								 * @return Collection<AuditDetailsVO>
								 * @throws SystemException
								 * @throws RemoteException
								 * ICRD-229934
								 */
								Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterVO mailAuditFilterVO)
										throws SystemException,RemoteException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.findULDsInAssignedFlight
								 *	Added by 	:	A-6991 on 20-Nov-2017
								 * 	Used for 	:   ICRD-77772
								 *	Parameters	:	@param operationalFlightVO
								 *	Parameters	:	@return
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Return type	: 	Collection<ContainerVO>
								 */
								Collection<ContainerVO> findULDsInAssignedFlight(OperationalFlightVO
								            operationalFlightVO) throws SystemException,RemoteException;


								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.findMailDetailsForMailTag
								 *	Added by 	:	a-6245 on 07-Jun-2017
								 * 	Used for 	:
								 *	Parameters	:	@param companyCode
								 *	Parameters	:	@param mailId
								 *	Parameters	:	@param airportCode
								 *	Parameters	:	@return
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Return type	: 	MailbagVO
								 */
								public MailbagVO findMailDetailsForMailTag(
										String companyCode, String mailId)
											throws SystemException, RemoteException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.findMailbagIdForMailTag
								 *	Added by 	:	a-6245 on 22-Jun-2017
								 * 	Used for 	:
								 *	Parameters	:	@param companyCode
								 *	Parameters	:	@param mailId
								 *	Parameters	:	@return
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Return type	: 	MailbagVO
								 */
								public MailbagVO findMailbagIdForMailTag(
										MailbagVO mailbagVO)
											throws SystemException, RemoteException;


								/**
								 *
								 * @param mailAuditHistoryFilterVO
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 */
								Collection<MailBagAuditHistoryVO> findMailAuditHistoryDetails(MailAuditHistoryFilterVO mailAuditHistoryFilterVO )  throws SystemException,RemoteException;

								/**
								 *
								 * @param mailbagEnquiryFilterVO
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 */
								Collection<MailbagHistoryVO> findMailStatusDetails(MailbagEnquiryFilterVO mailbagEnquiryFilterVO )  throws SystemException,RemoteException;
								/**
								 *
								 * @param entities
								 * @param b
								 * @param companyCode
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 */
								HashMap<String, String> findAuditTransactionCodes(
										 Collection<String> entities, boolean b, String companyCode)  throws SystemException,RemoteException;

								public  HashMap<String, Collection<FlightValidationVO>> validateFlightsForAirport(
										Collection<FlightFilterVO> flightFilterVOs)throws SystemException,RemoteException;

								/**
								 * @author A-5526 This method is used to find the MailBags Accepted to a
								 *         ParticularFlight and Flag MLD-UPL messages for the Same
								 * @param operationalFlightVO
								 * @throws SystemException
								 * @throws RemoteException
								 */
								void flagMLDForUpliftedMailbags(
										Collection<OperationalFlightVO> operationalFlightVOs)
										throws SystemException, RemoteException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.findMailAWBDetails
								 *	Added by 	:	A-6991 on 03-May-2017
								 * 	Used for 	:
								 *	Parameters	:	@param operationalFlightVO
								 *	Parameters	:	@return
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Return type	: 	MailManifestVO
								 */

								public void closeFlight(OperationalFlightVO operationalFlightVO)
										throws SystemException, RemoteException;
								/**
								 * @author A-7871 
								 * for ICRD-257316
								 * @param containerDetailsVO
								 * @throws PersistenceException 
								 * @throws BusinessDelegateException
								 */
								public int findMailbagcountInContainer(ContainerVO containerVO)
										throws SystemException, RemoteException, PersistenceException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.findOfficeOfExchangeForAirports
								 *	Added by 	:	a-6245 on 10-Jul-2017
								 * 	Used for 	:
								 *	Parameters	:	@param companyCode
								 *	Parameters	:	@param paCode
								 *	Parameters	:	@return
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Return type	: 	Map<String,String>
								 */
								public Map<String,String> findOfficeOfExchangeForPA(String companyCode,
										String paCode) throws SystemException,RemoteException ;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.performMailAWBTransactions
								 *	Added by 	:	a-7779 on 31-Aug-2017
								 * 	Used for 	:
								 *	Parameters	:	@param mailFlightSummaryVO
								 *	Parameters	:	@param eventCode
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Return type	: 	void
								 */
								@FlowDescriptor(flow = "PerformMailAWBTransactions_Flow", inputs = { "mailFlightSummaryVO" ,"eventCode"})
								public void performMailAWBTransactions(MailFlightSummaryVO mailFlightSummaryVO,String eventCode)
										throws SystemException,RemoteException;
								public void closeInboundFlightAfterULDAcquitalForProxy(OperationalFlightVO operationalFlightVO)
										throws SystemException, RemoteException;
								public void closeInboundFlightAfterULDAcquital(OperationalFlightVO operationalFlightVO)
										throws SystemException, RemoteException;
								public void releasingMailsForULDAcquittance(MailArrivalVO mailArrivalVO,
										OperationalFlightVO operationalFlightVO)
										throws SystemException, RemoteException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.findAgentCodeForPA
								 *	Added by 	:	U-1267 on 07-Nov-2017
								 * 	Used for 	:	ICRD-211205
								 *	Parameters	:	@param companyCode
								 *	Parameters	:	@param paCode
								 *	Parameters	:	@return
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Return type	: 	String
								 */
								public String findAgentCodeForPA(String companyCode,
										String paCode) throws SystemException,RemoteException ;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.findNextDocumentNumber
								 *	Added by 	:	U-1267 on 09-Nov-2017
								 * 	Used for 	:	ICRD-211205
								 *	Parameters	:	@param documnetFilterVO
								 *	Parameters	:	@return
								 *	Parameters	:	@throws RemoteException
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws MailTrackingBusinessException
								 *	Return type	: 	DocumentValidationVO
								 */
								public DocumentValidationVO findNextDocumentNumber(
										DocumentFilterVO documnetFilterVO) throws RemoteException,
										SystemException, MailTrackingBusinessException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.detachAWBDetails
								 *	Added by 	:	U-1267 on 09-Nov-2017
								 * 	Used for 	:	ICRD-211205
								 *	Parameters	:	@param containerDetailsVO
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Return type	: 	void
								 */
								public void detachAWBDetails(ContainerDetailsVO containerDetailsVO)
										throws SystemException, RemoteException;
								/**
								 *
								 * Method		:	MailTrackingDefaultsBI.transferContainersAtExport
								 * Added by 	:	A-7371 on 05-Jan-2018
								 * Used for 	:	ICRD-133987
								 * @param containerVOs
								 * @param operationalFlightVO
								 * @param printFlag
								 * @return TransferManifestVO
								 * @throws SystemException
								 * @throws RemoteException
								 * @throws MailTrackingBusinessException
								 */
								public TransferManifestVO transferContainersAtExport(Collection<ContainerVO>
							    containerVOs,OperationalFlightVO operationalFlightVO,String printFlag)
							    throws SystemException,RemoteException, MailTrackingBusinessException;
								/**
								 * Method		:	MailTrackingDefaultsBI.transferMailAtExport
								 * Added by 	:	A-7371 on 05-Jan-2018
								 * Used for 	:	ICRD-133987
								 * @param despatchDetailsVOs
								 * @param mailbagVOs
								 * @param containerVO
								 * @param toPrint
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 * @throws MailTrackingBusinessException
								 */
								public TransferManifestVO transferMailAtExport(
										   Collection<MailbagVO> mailbagVOs,ContainerVO containerVO,String toPrint)
								     throws SystemException,RemoteException,MailTrackingBusinessException;
								/**
								 * @author A-7540
								 * @param reportPublishJobVO
								 * @throws SystemException
								 * @throws RemoteException
								 * @throws ProxyException
								 */
								public void generateResditPublishReport(String companyCode,String paCode,int days)
										throws SystemException, RemoteException, ProxyException;
								/**
								 * @author A-8061
								 * @param carditEnquiryFilterVO
								 * @throws SystemException
								 * @throws RemoteException
								 */
								public String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO)
										throws SystemException, RemoteException;
			/**
			 * @author A-6986
			 * @param mailServiceLevelVOs
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public Collection<ErrorVO> saveMailServiceLevelDtls(Collection<MailServiceLevelVO> mailServiceLevelVOs)
					throws SystemException, RemoteException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.listPostalCalendarDetails
								 *	Added by 	:	A-8164 on 04-Jul-2018
								 * 	Used for 	:	ICRD-236925
								 *	Parameters	:	@param uSPSPostalCalendarFilterVO
								 *	Parameters	:	@return
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Parameters	:	@throws MailTrackingBusinessException
								 *	Return type	: 	Collection<USPSPostalCalendarVO>
								 */
								public Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
										USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO)
												throws SystemException,RemoteException,MailTrackingBusinessException;
								/**
								 *
								 * 	Method		:	MailTrackingDefaultsBI.savePostalCalendar
								 *	Added by 	:	A-8164 on 04-Jul-2018
								 * 	Used for 	:	ICRD-236925
								 *	Parameters	:	@param uSPSPostalCalendarVOs
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws RemoteException
								 *	Parameters	:	@throws MailTrackingBusinessException
								 *	Return type	: 	void
								 */
								public void savePostalCalendar(
										Collection<USPSPostalCalendarVO> uSPSPostalCalendarVOs)
										throws SystemException, RemoteException,
										MailTrackingBusinessException;
  /**
			 * @author A-7540
			 * @param newMailbgVOs
			 * @param isScanned
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public ScannedMailDetailsVO doLATValidation(Collection<MailbagVO> newMailbgVOs,boolean isScanned)
					throws SystemException, RemoteException,MailHHTBusniessException;

			/**
			 * a-6986
								 * @param reportSpec
								 * @return
								 * @throws SystemException
								 * @throws RemoteException
								 * @throwsReportGenerationException
								 */
								public Map<String,Object> generateAV7Report(ReportSpec reportSpec)
										throws SystemException,RemoteException,ReportGenerationException ;
											
								/**
								 * @author A-6986
								 * @param gpaContractVOs
								 * @throws SystemException
								 * @throws RemoteException
								 */
								public void saveContractDetails(Collection<GPAContractVO> gpaContractVOs) 
										throws SystemException, RemoteException,MailTrackingBusinessException;
								/**
								 * @author A-6986
								 * @param gpaContractFilterVO
								 * @param pageNumber
								 * @throws SystemException
								 * @throws RemoteException
								 */
								public  Collection<GPAContractVO> listContractDetails (
										GPAContractFilterVO gpaContractFilterVO)throws SystemException, RemoteException;
										
								

			/**
			 * a-6986
			 * @param MailHandoverVOs
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public void saveMailHandoverDetails(Collection<MailHandoverVO> MailHandoverVOs)
					throws SystemException, RemoteException;

			/**
			 * a-6986
			 * @param mailHandoverFilterVO
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO,int pageNumber )
			throws SystemException, RemoteException;
			
			/**
			 * @author A-8236
			 * @param mailBagVOs
			 * @return
			 * @throws RemoteException
			 * @throws SystemException
			 * @throws MailHHTBusniessException
			 * @throws MailMLDBusniessException
			 * @throws MailTrackingBusinessException
			 * @throws PersistenceException 
			 */
			@FlowDescriptor(flow = "SaveMailUploadDetailsForAndroid_Flow", inputs = { "mailBagVO" ,"scanningPort"}, output = "scannedMailDetailsVO")
			public ScannedMailDetailsVO saveMailUploadDetailsForAndroid(
					MailUploadVO mailBagVO, String scanningPort)
					throws RemoteException, SystemException, MailHHTBusniessException,
					MailMLDBusniessException, MailTrackingBusinessException, PersistenceException;
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsBI.validateMailBagDetails
			 *	Added by 	:	A-7871 on 13-Jul-2018
			 * 	Used for 	:	ICRD-227884
			 *	Parameters	:	@param MailUploadVO
			 *	Parameters	:	@throws SystemException
			 *	Return type	: 	void
			 * @throws RemoteException 
			 */
			@FlowDescriptor(flow = "ValidateScannedMailDtlsForAndroid_Flow", inputs = { "mailUploadVO"}, output="scannedMailDetailsVO")
			public ScannedMailDetailsVO validateMailBagDetails(MailUploadVO mailUploadVO) throws SystemException, RemoteException, MailHHTBusniessException,
			MailMLDBusniessException, MailTrackingBusinessException;
			
			/**
			 * @author
			 * @param companyCode
			 * @param fileType
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws MailTrackingBusinessException
			 */
			public String validateFromFile(FileUploadFilterVO fileUploadFilterVO) 
					throws SystemException,RemoteException, MailTrackingBusinessException;
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsBI.validateFlightAndContainer
			 *	Added by 	:	A-8164 on 28-Feb-2019
			 * 	Used for 	:
			 *	Parameters	:	@param mailUploadVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws SystemException
			 *	Parameters	:	@throws RemoteException
			 *	Parameters	:	@throws MailHHTBusniessException
			 *	Parameters	:	@throws MailMLDBusniessException
			 *	Parameters	:	@throws MailTrackingBusinessException 
			 *	Return type	: 	ScannedMailDetailsVO
			 */
			public ScannedMailDetailsVO validateFlightAndContainer(MailUploadVO mailUploadVO) throws SystemException, RemoteException, MailHHTBusniessException,
					MailMLDBusniessException, MailTrackingBusinessException;
			
            public Page<MailAcceptanceVO>  findOutboundFlightsDetails(OperationalFlightVO operationalFlightVO,int pageNumber)
					 throws SystemException, RemoteException, ProxyException;
			public Page <ContainerDetailsVO> getAcceptedContainers(OperationalFlightVO operationalFlightVO,int pageNumber)
					throws SystemException, RemoteException;
			public Page<MailbagVO> getMailbagsinContainer(ContainerDetailsVO containerVO, int pageNumber)
							throws SystemException, RemoteException;
			public Page<DSNVO> getMailbagsinContainerdsnview(ContainerDetailsVO containerVO, int pageNumber)
					throws SystemException, RemoteException;
	        public MailbagVO findCarditSummaryView(CarditEnquiryFilterVO carditEnquiryFilterVO)
	        		throws SystemException, RemoteException;
	        public Page<MailbagVO> findGroupedCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
	        		throws SystemException, RemoteException;
	        public MailbagVO findLyinglistSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
	        		throws SystemException, RemoteException;
	        public Page<MailbagVO> findGroupedLyingList(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) 
					throws SystemException, RemoteException;
		    public Page<MailAcceptanceVO>  findOutboundCarrierDetails(OperationalFlightVO operationalFlightVO,int pageNumber)
					 throws SystemException, RemoteException, ProxyException;
			public Page<MailbagVO> getMailbagsinCarrierContainer(ContainerDetailsVO containerVO, int pageNumber)
					throws SystemException, RemoteException;
			public Page<DSNVO>  getMailbagsinCarrierdsnview(ContainerDetailsVO containerVO, int pageNumber)
					throws SystemException, RemoteException;
			public Collection<DSNVO>  getDSNsForContainer(ContainerDetailsVO containerVO)
					throws SystemException, RemoteException;
			public Collection<DSNVO>  getRoutingInfoforDSN(Collection<DSNVO> dsnVos,ContainerDetailsVO containerDetailsVO)
					throws SystemException, RemoteException;
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsBI.listFlightDetails
			 *	Added by 	:	A-8164 on 24-Sep-2018
			 * 	Used for 	:
			 *	Parameters	:	@param mailArrivalVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws SystemException
			 *	Parameters	:	@throws RemoteException 
			 *	Return type	: 	Collection<MailArrivalVO>
			 * @throws PersistenceException 
			 */
			public Page<MailArrivalVO> listFlightDetails(MailArrivalVO mailArrivalVO)
					throws SystemException, RemoteException, PersistenceException;
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsBI.closeInboundFlights
			 *	Added by 	:	A-8164 on 11-Dec-2018
			 * 	Used for 	:	Closing multiple inbound flights
			 *	Parameters	:	@param operationalFlightVOs
			 *	Parameters	:	@throws SystemException
			 *	Parameters	:	@throws RemoteException
			 *	Parameters	:	@throws MailTrackingBusinessException 
			 *	Return type	: 	void
			 */
			@AvoidForcedStaleDataChecks
		    void closeInboundFlights(Collection<OperationalFlightVO> operationalFlightVOs)
		            throws SystemException ,RemoteException, MailTrackingBusinessException;
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsBI.reopenInboundFlight
			 *	Added by 	:	A-8164 on 11-Dec-2018
			 * 	Used for 	:	For reopening multiple inbound flights
			 *	Parameters	:	@param operationalFlightVO
			 *	Parameters	:	@throws SystemException
			 *	Parameters	:	@throws RemoteException 
			 *	Return type	: 	void
			 */
			@AvoidForcedStaleDataChecks
		    void reopenInboundFlights(Collection<OperationalFlightVO> operationalFlightVOs)
		            throws SystemException ,RemoteException;
			
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsBI.populateMailArrivalVOForInbound
			 *	Added by 	:	A-8164 on 27-Dec-2018
			 * 	Used for 	:	
			 *	Parameters	:	@param operationalFlightVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws SystemException
			 *	Parameters	:	@throws RemoteException
			 *	Parameters	:	@throws PersistenceException 
			 *	Return type	: 	MailArrivalVO
			 * @throws MailTrackingBusinessException 
			 */
			public MailArrivalVO populateMailArrivalVOForInbound(OperationalFlightVO operationalFlightVO)
					throws SystemException, RemoteException, PersistenceException, MailTrackingBusinessException;
			
			public Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO)
					throws SystemException, RemoteException, PersistenceException, MailTrackingBusinessException;
			
			public Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
					throws SystemException, RemoteException, PersistenceException, MailTrackingBusinessException;
			
			public Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
					throws SystemException, RemoteException, PersistenceException, MailTrackingBusinessException;
			/**
			 * This method is used to find the Offload Details for a Flight say at
			 * different levels say Containers,DSNS,MailBags
			 * Author A-7929
			 * @param offloadFilterVO
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public OffloadVO findOffLoadDetails(OffloadFilterVO offloadFilterVO) throws SystemException, RemoteException;
			public MailInConsignmentVO populatePCIDetailsforUSPS(MailInConsignmentVO mailInConsignment, String airport, String companyCode, String rcpOrg, String rcpDest, String year)
				    throws SystemException, RemoteException;
           /**
            * 
            * 	Method		:	MailTrackingDefaultsBI.findMailbagBillingStatus
            *	Added by 	:	a-8331 on 25-Oct-2019
            * 	Used for 	:
            *	Parameters	:	@param mailbagvo
            *	Parameters	:	@return
            *	Parameters	:	@throws SystemException
            *	Parameters	:	@throws RemoteException 
            *	Return type	: 	Collection<DocumentBillingDetailsVO>
            */
			public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo) throws SystemException, RemoteException;
			/**
			 * 
			 * 	Method		:	MailTrackingDefaultsBI.voidMailbags
			 *	Added by 	:	a-8331 on 25-Oct-2019
			 * 	Used for 	:
			 *	Parameters	:	@param documentBillingDetails
			 *	Parameters	:	@throws SystemException
			 *	Parameters	:	@throws RemoteException 
			 *	Return type	: 	void
			 */
			public void voidMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetails) throws SystemException, RemoteException;












			 
			 
			 
			 
			 
			 
			 

			 

			

  
		






			public void calculateULDContentId(Collection<ContainerDetailsVO> containerVOs, OperationalFlightVO toFlightVO)
					throws SystemException, RemoteException;

			@FlowDescriptor(flow = "FlagSecurityScreeningDetails_Flow", inputs = {"scannedMailDetailsVO"})
			public void saveScreeningDetails(ScannedMailDetailsVO scannedMailDetailsVO)
					throws SystemException, RemoteException;
			/**
			 * @author A-6986
			 * @param incentiveConfigurationFilterVO
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public  Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails (
					IncentiveConfigurationFilterVO incentiveConfigurationFilterVO)throws SystemException, RemoteException;
			/**
			 * @author A-6986
			 * @param incentiveConfigurationVOs
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public void saveIncentiveConfigurationDetails(Collection<IncentiveConfigurationVO> incentiveConfigurationVOs)
					throws SystemException, RemoteException,MailTrackingBusinessException;
			/**
			  *
			  * 	Method		:	MailTrackingDefaultsBI.findRunnerFlights
			  *	Added by 	:	A-5526 on 12-Oct-2018
			  * 	Added for 	: CRQ ICRD-239811
			  *	Parameters	:	@param runnerFlightFilterVO
			  *	Parameters	:	@return
			  *	Parameters	:	@throws RemoteException
			  *	Parameters	:	@throws SystemException
			  *	Return type	: 	Page<RunnerFlightVO>
			  */
			 public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO)
			    		throws RemoteException, SystemException, PersistenceException ;

			 /**
			  *
			  * Method		:	MailTrackingDefaultsBI.findMailbagsForTruckFlight
			  *	Added by 	:	A-7929 on 23-Oct-2018
			  * Added for 	:  CRQ ICRD-241437
			  *	Parameters	:	@param mailbagEnquiryFilterVO,pageNumber
			  *	Parameters	:	@return
			  *	Parameters	:	@throws RemoteException
			  *	Parameters	:	@throws SystemException
			  *	Return type	: 	Page<MailbagVO>
			  */
			 public Page<MailbagVO> findMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,int pageNumber)
			    		throws  SystemException, RemoteException ;


			/**
			 *
			 * 	Method		:	MailTrackingDefaultsBI.saveRoutingIndexDetails
			 *	Added by 	:	A-7531 on 12-Oct-2018
			 * 	Used for 	:
			 *	Parameters	:	@param routingIndexVOs
			 *	Parameters	:	@throws RemoteException
			 *	Parameters	:	@throws SystemException
			 *	Parameters	:	@throws MailTrackingBusinessException
			 *	Parameters	:	@throws FinderException
			 *	Return type	: 	void
			 */
			@AvoidForcedStaleDataChecks
			public void saveRoutingIndexDetails(Collection<RoutingIndexVO> routingIndexVOs)throws RemoteException, SystemException,MailTrackingBusinessException, FinderException;

/**
 *
 * 	Method		:	MailTrackingDefaultsBI.findRoutingIndex
 *	Added by 	:	A-7531 on 30-Oct-2018
 * 	Used for 	:
 *	Parameters	:	@param routingIndex
 *	Parameters	:	@param companycode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<RoutingIndexVO>
 */
			public Collection<RoutingIndexVO> findRoutingIndex(
					RoutingIndexVO routingIndexVO) throws SystemException, RemoteException;

   public MailbagVO findDsnAndRsnForMailbag(MailbagVO mailbagVO)
			throws SystemException, RemoteException;	  


			 /**
			  *
			  * @param filterVO
			  * @return
			  * @throws SystemException
			  * @throws RemoteException
			  */
			 public Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(ForceMajeureRequestFilterVO filterVO, int pageNumber)
					 throws  SystemException, RemoteException ;
  			 /**
			  *
			  * @param filterVO
			  * @return
			  * @throws SystemException
			  * @throws RemoteException
			  */
			 public void saveForceMajeureRequest(ForceMajeureRequestFilterVO filterVO)
					 throws  SystemException, RemoteException ;


			 /**
			  *
			  * @param invoiceTransactionLogVO
			  * @return
			  * @throws SystemException
			  * @throws RemoteException
			  */
			 public InvoiceTransactionLogVO initTxnForForceMajeure(
						InvoiceTransactionLogVO invoiceTransactionLogVO )
						throws SystemException, RemoteException ;
			 
			 
			 /**
			  * 
			  * @param filterVO
			  * @param pageNumber
			  * @return
			  * @throws SystemException
			  * @throws RemoteException
			  */
			 public Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO filterVO, int pageNumber)
					 throws  SystemException, RemoteException ;
			 
			 
			 /**
			  * 
			  * @param filterVO
			  * @param pageNumber
			  * @return
			  * @throws SystemException
			  * @throws RemoteException
			  */
			 public Page<ForceMajeureRequestVO> listForceMajeureRequestIds(ForceMajeureRequestFilterVO filterVO, int pageNumber)
					 throws  SystemException, RemoteException ;
			 
			 
			 /**
			  * 
			  * @param requestVOs
			  * @throws SystemException
			  * @throws RemoteException
			  */
			 public void deleteForceMajeureRequest(Collection<ForceMajeureRequestVO> requestVOs)
					 throws  SystemException, RemoteException ;
			 
			 /**
			  * 
			  * @param requestVO
			  * @throws SystemException
			  * @throws RemoteException
			  */
			 public void updateForceMajeureRequest(ForceMajeureRequestFilterVO requestVO)
					 throws  SystemException, RemoteException ;

			 /**
			  * @author a-7540
			  * @param reassignedFlightValidationVO
			  * @return
			  * @throws SystemException
			  * @throws RemoteException
			  */
			 public Collection<ContainerVO> findAllContainersInAssignedFlight(
						FlightValidationVO reassignedFlightValidationVO)
						throws SystemException, RemoteException;
			 
			 /**
				* @author A-7540
				* @throws MailMLDBusniessException 
				* @throws MailTrackingBusinessException 
			 * @throws MailHHTBusniessException 
				* 
				*/
		public Collection<ErrorVO> processResditMails(Collection<MailScanDetailVO> mailScanDetailVO) 
			throws SystemException, RemoteException, MailTrackingBusinessException, MailMLDBusniessException, MailHHTBusniessException; 
/**
 * @author A-5526
 * @param mailIdr
 * @param companyCode
 * @return
 * @throws SystemException
 * @throws RemoteException
 */

			public long findMailBagSequenceNumberFromMailIdr(String mailIdr, String companyCode)throws SystemException,
			RemoteException;
			/***
			 * @author A-7794
			 * @param fileUploadFilterVO
			 * @return
			 * @throws PersistenceException
			 * @throws SystemException
			 * @throws RemoteException 
			 */
			public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO)throws PersistenceException, SystemException, RemoteException ;
			
			/**
			 * @author A-5526
			 * Added for CRQ ICRD-233864 
			 * @param mailArrivalVO
			 * @throws BusinessDelegateException
			 */
			 public void onStatustoReadyforDelivery(MailArrivalVO mailArrivalVO)
					 throws SystemException, RemoteException, MailTrackingBusinessException;
 /**
 * @author A-7929
 * @param containerActionData
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public void saveContentID(Collection<ContainerVO> containerVOs) throws  SystemException, RemoteException ;

/**
* @author A-8672
* @param MailbagVO
* @return
* @throws SystemException
* @throws RemoteException
*/
public abstract void updateActualWeightForMailbag(MailbagVO paramMailbagVO)
	    throws SystemException, RemoteException;	 
  
//Added by A-8464 for ICRD-243079
/**
* @author A-8464
* @param MailbagVO
* @return
* @throws SystemException
* @throws RemoteException
*/
public Collection<MailMonitorSummaryVO>  getPerformanceMonitorDetails(MailMonitorFilterVO filterVO) throws  SystemException, RemoteException ;
		
//Added by A-8464 for ICRD-243079
/**
* @author A-8464
* @param MailbagVO
* @return
* @throws SystemException
* @throws RemoteException
*/
public Page<MailbagVO>  getPerformanceMonitorMailbags(MailMonitorFilterVO filterVO,String type,int pageNumber) throws  SystemException, RemoteException ;

/**
 * @param OperationalFlightVO
 * @throws SystemException
 * @author A-8438
 */
public  MailManifestVO findMailbagManifest(OperationalFlightVO OperationalFlightVO) throws SystemException, RemoteException;

public  MailManifestVO findMailAWBManifest(OperationalFlightVO operationalFlightVO) throws SystemException, RemoteException;

public  MailManifestVO findDSNMailbagManifest(OperationalFlightVO operationalFlightVO) throws SystemException, RemoteException;

public  MailManifestVO findDestnCatManifest(OperationalFlightVO operationalFlightVO) throws SystemException, RemoteException;

public  Collection<MailHandedOverVO> generateMailHandedOverRT(MailHandedOverFilterVO mailHandedOverFilterVO) throws SystemException, RemoteException;
/**
 * @param USPSPostalCalendarFilterVO
 * @throws SystemException
 * @author A-8527
 */
public  Collection<USPSPostalCalendarVO> validateFrmToDateRange(USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws SystemException, RemoteException;

/**
 * @param MailbagEnquiryFilterVO
 * @throws SystemException
 * @author A-8464
 */
public MailbagVO findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) 
		throws SystemException, RemoteException;

/**
 * @param MailbagEnquiryFilterVO
 * @throws SystemException
 * @author U-1519
 */
public MailbagVO findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) 
		throws SystemException, RemoteException;

/**
 * @param flightFilterVOs
 * @throws SystemException
 * @author A-8176
 */
public Collection<FlightSegmentCapacitySummaryVO> fetchFlightCapacityDetails(Collection<FlightFilterVO> flightFilterVOs)
		throws SystemException, RemoteException;

/**
 * @author A-7371
 * @param uspsPostalCalendarFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO)throws SystemException, RemoteException;
 
/**
* @author A-7929
* @param DWSMasterVO
* @return
* @throws SystemException
* @throws RemoteException
 * @throws FinderException 
*/
public String fetchMailContentIDs(DWSMasterVO dwsMasterVO,String containerNumber,String assignedAirport) throws  SystemException, RemoteException, FinderException ;
/**
 * 
 * 	Method		:	MailTrackingDefaultsBI.findDuplicateMailbag
 *	Added by 	:	A-7531 on 16-May-2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param mailBagId
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws FinderException 
 *	Return type	: 	ArrayList<MailbagVO>
 */
public ArrayList<MailbagVO>findDuplicateMailbag(String companyCode, String mailBagId) throws  SystemException, RemoteException, FinderException ;

	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsBI.sendULDAnnounceForFlight
	 *	Added by 	:	A-8164 on 12-Feb-2021
	 * 	Used for 	:	Sending UCS Announce from mail outbound
	 *	Parameters	:	@param mailManifestVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	public void sendULDAnnounceForFlight(MailManifestVO mailManifestVO) 
			throws SystemException, RemoteException;
	
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsBI.updateMailULDDetailsFromMHS
	 *	Added by 	:	A-8164 on 15-Feb-2021
	 * 	Used for 	:
	 *	Parameters	:	@param storageUnitCheckinVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	boolean
	 */
	public boolean updateMailULDDetailsFromMHS(StorageUnitCheckinVO storageUnitCheckinVO)
			 throws SystemException,ProxyException,RemoteException;

public Page<MailbagVO> findDeviationMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) throws SystemException, RemoteException;

public MailboxIdVO findMailboxId(MailboxIdVO mailboxIdVO)throws  SystemException, RemoteException, FinderException ;
	
public void saveMailboxId(MailboxIdVO mailboxIdVO)throws  SystemException, RemoteException, FinderException ;

//Added by A-8527 for IASCB-28086 Starts
public String findProductsByName(String companyCode,String product)throws SystemException, RemoteException;

public void buildResditProxy(Collection<ResditEventVO> resditEvent) throws SystemException, RemoteException;

public void saveCarditTempMessages(Collection<CarditTempMsgVO> carditTempMsgVOs)
        throws RemoteException, SystemException, MailTrackingBusinessException;

public Collection<CarditTempMsgVO> getTempCarditMessages(String companyCode,String includeMailBoxIdr,String excludeMailBoxIdr,String includedOrigins,String excludedOrigins,int pageSize ,int noOfdays)
		throws  SystemException, RemoteException, FinderException ;

/**
 * 	Method		:	MailTrackingDefaultsBI.saveCarditMsgs
 *	Added by 	:	A-6287 on 03-Mar-2020
 * 	Used for 	:
 *	Parameters	:	@param ediInterchangeVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws MailTrackingBusinessException 
 *	Return type	: 	void
 */
public Collection<ErrorVO> saveCarditMsgs(EDIInterchangeVO ediInterchangeVO)
		throws RemoteException, SystemException, MailTrackingBusinessException;	


//Added by A-8893 for IASCB-34152 starts
public void deleteEmptyContainer(ContainerDetailsVO containerDetailsVO)
		throws SystemException, RemoteException;
//Added by A-8893 for IASCB-34152 ends
/**
 * 
 * 	Method		:	MailTrackingDefaultsBI.updateGateClearStatus
 *	Added by 	:	U-1467 on 09-Mar-2020	:
 *	Parameters	:	@param operationalFlightVO
 *	Parameters	:	@param gateClearanceStatus
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void updateGateClearStatus(com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO operationalFlightVO,
									  String gateClearanceStatus) throws RemoteException, SystemException;
/**
* @author U-1317
* @throws MailMLDBusniessException 
* @throws MailTrackingBusinessException 
* @throws MailHHTBusniessException 
* 
*/
public Collection<ErrorVO> processResditMailsForAllEvents(Collection<MailUploadVO> mailScanDetailVO) 
throws SystemException, RemoteException, MailTrackingBusinessException, MailMLDBusniessException, MailHHTBusniessException; 

/**
 * @author A-8353 for IASCB-45360
 * @throws MailHHTBusniessException
 * @throws MailMLDBusniessException
 * @throws SystemException
 * @throws RemoteException
 */
@FlowDescriptor(flow = "FlagResditForAcceptanceInTruck_Flow", inputs = { "scannedMailDetailsVO"})
 public void flagResditForAcceptanceInTruck(ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException, MailHHTBusniessException, SystemException,RemoteException;
/**
 * @author A-7540
 * @param ediInterchangeVO
 * @return 
 * @throws RemoteException
 * @throws SystemException
 * @throws MailTrackingBusinessException
 */
public Collection<ErrorVO> saveCDTMessages(EDIInterchangeVO ediInterchangeVO)
		throws RemoteException, SystemException, MailTrackingBusinessException;
/**
 * 
 * @param offloadVo
 * @throws SystemException
 * @throws RemoteException
 * @throws MailTrackingBusinessException
 */
public void  removeFromInbound(OffloadVO offloadVo)
		throws SystemException, RemoteException,
		MailTrackingBusinessException;
public ErrorVO validateContainerNumberForDeviatedMailbags(ContainerDetailsVO containerDetailsVO, long mailSequenceNumber)
		throws RemoteException, SystemException, MailTrackingBusinessException;

/**
 * @author A-5526 This method is used to find the History of a Mailbag
 * @param companyCode
 * @param mailbagId
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode,String mailBagId,  
		long mailSequenceNumber) throws SystemException, RemoteException;

/**
 * @author A-8353 
 * @throws PersistenceException
 * @throws SystemException
 * @throws RemoteException
 */
public MailbagInULDForSegmentVO getManifestInfo(MailbagVO mailbagVO) throws PersistenceException, SystemException,RemoteException;
/**
 * @author A-8353 
 * @throws SystemException
 * @throws RemoteException
 */
public String checkMailInULDExistForNextSeg(String containerNumber,String airpotCode,String companyCode) throws  SystemException,RemoteException;
public void saveMailRuleConfiguration(MailRuleConfigVO mailRuleConfigVO)throws  SystemException, RemoteException, FinderException ;

/**
 * @author A-8672
 * @param ContainerVO
 * @throws RemoteException
 * @throws SystemException
 * @throws FinderException
 */
public void updateRetainFlagForContainer(ContainerVO containerVO) throws RemoteException, FinderException, SystemException;
/**
 * 
 *	Parameters	:	@param autoAttachAWBJobScheduleVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void createAutoAttachAWBJobSchedule(AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO) throws RemoteException, SystemException;


/**
 * @author A-9084 
 * @param consignmentNumber
 * @param companyCode
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws PersistenceException
 * @throws FinderException
 */
public ConsignmentDocumentVO findConsignmentScreeningDetails(String consignmentNumber, String companyCode,String poaCode)throws  SystemException, RemoteException, PersistenceException, FinderException ;

/**
 * 
 *	Parameters	:	@param TransferManifestVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void saveTransferFromManifest(TransferManifestVO transferManifestVO) throws SystemException,RemoteException;
/**
 * 
 *	Parameters	:	@param TransferManifestVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */

public void rejectTransferFromManifest(TransferManifestVO transferManifestVO) throws SystemException,RemoteException; 
/**
 * 
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param tranferManifestId
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	List<TransferManifestVO>
 */
public List<TransferManifestVO> findTransferManifestDetailsForTransfer(
		String companyCode,String tranferManifestId)throws SystemException ,RemoteException;


/**
 * @author A-9084 
 * @param Collection<ConsignmentScreeningVO>
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws FinderException 
 */
public void saveConsignmentScreeningDetails(ConsignmentDocumentVO consignmentDocumentVO)throws SystemException, RemoteException, FinderException;	
/**
 * 
 * 	Method		:	MailTrackingDefaultsBI.findTransferManifestConsignmentDetails
 *	Added by 	:	U-1418 on 11-Nov-2020
 * 	Used for 	:
 *	Parameters	:	@param transferManifestVO
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Collection<ConsignmentDocumentVO>
 */
public Collection<ConsignmentDocumentVO> findTransferManifestConsignmentDetails(TransferManifestVO transferManifestVO) throws RemoteException, SystemException;

/**
 * @author A-9084 
 * @param reportSpec
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws PersistenceException
 * @throws FinderException
 */
public HashMap<String, Object> generateConsignmentSecurityReport(ReportSpec reportSpec)
		throws SystemException, RemoteException, ProxyException, PersistenceException;

/**
 * 
 * @param mailbagVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public MailbagVO findMailBillingStatus(MailbagVO mailbagVO) throws SystemException, RemoteException;

/**
 * @author A-9084 
 * @param reportSpec
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws PersistenceException
 * @throws ReportGenerationException 
 * @throws FinderException
 */
public Map<String, Object> generateConsignmentSummaryReports(ReportSpec reportSpec)
		throws SystemException, RemoteException, ProxyException, PersistenceException, ReportGenerationException;


public Collection<MailEventVO> findMailEvent(MailEventVO mailEventVO) throws SystemException,RemoteException ;
public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO) throws SystemException, RemoteException;

public String findAutoPopulateSubtype(
		DocumentFilterVO documnetFilterVO) throws RemoteException,
		SystemException, MailTrackingBusinessException;
/**
 * @author A-8353
 * @param companyCode
 * @param airportCode
 * @return
 */
public  String findOfficeOfExchangeForCarditMissingDomMail(String companyCode,String airportCode) throws SystemException,RemoteException ;

/**
 * 
 * 	Method		:	MailTrackingDefaultsBI.saveMailUploadDetailsFromMailOnload
 *	Added by 	:	A-8061 on 07-Apr-2021
 * 	Used for 	:
 *	Parameters	:	@param mailUploadVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws MailHHTBusniessException
 *	Parameters	:	@throws MailMLDBusniessException
 *	Parameters	:	@throws MailTrackingBusinessException 
 *	Return type	: 	Map<String,ErrorVO>
 */
public Map<String, ErrorVO> saveMailUploadDetailsFromMailOnload(Collection <MailUploadVO> mailUploadVOs) throws 
RemoteException, SystemException, MailHHTBusniessException, MailMLDBusniessException,MailTrackingBusinessException;
/**
 * @A-8353
 * @param mailbagVOs
 * @throws SystemException
 * @throws RemoteException
 */
public  void updateOriginAndDestinationForMailbag(Collection<MailbagVO>  mailbagVOs) throws SystemException,RemoteException ;


	public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(ImportOperationsFilterVO filterVO,
			Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException, RemoteException, SystemException;
	public Collection<OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO)
			throws SystemException, PersistenceException, RemoteException ;
	public Collection<ManifestVO> findExportFlightOperationsDetails(ImportOperationsFilterVO filterVO,
			Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException, RemoteException, SystemException;

	public Collection<ConsignmentDocumentVO> fetchConsignmentDetailsForUpload(FileUploadFilterVO fileUploadFilterVO)
			throws RemoteException, SystemException;

	public void saveUploadedConsignmentData(Collection<ConsignmentDocumentVO> consignmentDocumentVOs)
			throws SystemException, RemoteException, MailTrackingBusinessException;

	public void saveMailbagHistory(Collection<MailbagHistoryVO> mailbagHistoryVOs)
			throws RemoteException, SystemException;
	/**
	 * @author A-8893 
	 * @param ContainerVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void releaseContainers(Collection<ContainerVO> containerVOsToBeReleased)
			throws RemoteException,SystemException;
		
	
	public byte[] findMailbagDamageImages(String companyCode,
			String id)
			throws RemoteException,SystemException;

	public Collection<ContainerDetailsVO> findContainerJourneyID(ConsignmentFilterVO consignmentFilterVO)
			throws RemoteException, SystemException;

	public void stampResdits(Collection<MailResditVO>mailResditVOs)
			throws RemoteException, SystemException, MailTrackingBusinessException;
	
	public Collection<ContainerDetailsVO> findArrivalDetailsForReleasingMails(OperationalFlightVO operationalFlightVO)
			throws RemoteException, SystemException;
	
	public String findMailboxIdFromConfig(MailbagVO mailbagVO) throws RemoteException, SystemException;
	/**
	 * @author A-8353
	 * @param mailBagVO
	 * @param scanningPort
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void performErrorStampingForFoundMailWebServices(
			MailUploadVO mailBagVO, String scanningPort)
			throws RemoteException, SystemException;

	public Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO)
			throws RemoteException, SystemException;
	
	/**
	 * @author A-9084
	 * @param mailAuditFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException 
	 * @throws RemoteException 
	 */
	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterVO mailAuditFilterVO) throws BusinessDelegateException, SystemException, RemoteException;
	public void dettachMailBookingDetails(Collection<MailbagVO> mailbagVOs) throws RemoteException, SystemException;

	public void attachAWBForMail(Collection<MailBookingDetailVO> mailBookingDetailVOs,Collection<MailbagVO> mailbagVOs) throws SystemException, RemoteException;
	
	public String findNearestAirportOfCity(String companyCode, String exchangeCode) throws SystemException, RemoteException;
 	
	public void markUnmarkUldIndicator(ContainerVO containerVO) throws SystemException,RemoteException;

public long insertMailbagAndHistory(MailbagVO mailbagVO) throws SystemException,RemoteException ;

/**
 * @author A-9998
 * @param mailBagVOs
 * @return
 * @throws RemoteException
 * @throws SystemException
 * @throws MailHHTBusniessException
 * @throws MailMLDBusniessException
 * @throws MailTrackingBusinessException
 * @throws PersistenceException 
 */
public ScannedMailDetailsVO saveMailUploadDetailsForULDFULIndicator(
		MailUploadVO mailBagVO, String scanningPort)
		throws RemoteException, SystemException, MailHHTBusniessException,
		MailMLDBusniessException, MailTrackingBusinessException, PersistenceException;

	public void triggerEmailForPureTransferContainers(Collection<OperationalFlightVO> operationalFlightVOs)
			throws SystemException, RemoteException;
				public void saveSecurityDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs)throws SystemException, RemoteException ;

	public MailbagVO listmailbagSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo)
			throws SystemException, RemoteException;

	/**
	 * @author A-10383
	 * Method		:	MailTrackingDefaultsBI.editscreeningDetails
	 * @param consignmentScreeningVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void editscreeningDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs)throws SystemException, RemoteException ;
	/**
	 * @author A-10383
	 * Method		:	MailTrackingDefaultsBI.deletescreeningDetails
	 * @param consignmentScreeningVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void deletescreeningDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs)throws SystemException, RemoteException ;
	public Map<String, Object> generateMailSecurityReport(ReportSpec reportSpec)
			throws SystemException, RemoteException, ProxyException, PersistenceException, ReportGenerationException;
 	
	/**
	 * 	Method		:	MailTrackingDefaultsBI.saveMailSecurityStatus
	 *	Added by 	:	A-4809 on 18-May-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	public void saveMailSecurityStatus(MailbagVO mailbagVO) throws SystemException,RemoteException;
	
	/**
	 * 	Method		:	MailTrackingDefaultsBI.saveScreeningConsginorDetails
	 *	Added by 	:	A-4809 on 19-May-2022
	 * 	Used for 	:
	 *	Parameters	:	@param contTransferMap
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	@FlowDescriptor(flow = "FlagScreeningConsginorDetails_Flow", inputs = {"contTransferMap"})
	public void saveScreeningConsginorDetails(Map<String, Object> contTransferMap)
			throws SystemException, RemoteException;
	
public MailbagVO findAirportFromMailbag(MailbagVO mailbagVO) throws SystemException,FinderException, RemoteException ;	
	public void saveFligthLoadPlanForMail(Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs) throws SystemException, BusinessException,RemoteException;
  /**
	 * 	Method		:	MailTrackingDefaultsBI.findLoadPlandetails
	 *	Added by 	:	A-9477 on 13-JUL-2022
	 * 	Used for 	:
	 *	Parameters	:	@param SearchContainerFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	public  Collection<FlightLoadPlanContainerVO> findLoadPlandetails(SearchContainerFilterVO searchContainerFilterVO) throws SystemException,RemoteException;
	/**
	 * @author A-10383
	 * Method		:	MailTrackingDefaultsBI.saveConsignmentDetailsMaster
	 * @param consignmentScreeningVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveConsignmentDetailsMaster(ConsignmentDocumentVO  consignmentDocumentVO)throws SystemException, RemoteException ;
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @return 
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, ErrorVO> saveSecurityScreeningFromService(SecurityAndScreeningMessageVO securityAndScreeningMessageVO) throws SystemException,RemoteException,MailTrackingBusinessException;
    public Map<String,String> findAirportParameterCode(FlightFilterVO flightFilterVO,Collection<String> parCodes)throws SystemException, RemoteException ;
    
    public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(Collection<FlightListingFilterVO> flightListingFilterVOs) 
    		throws SystemException, RemoteException;

	/**
	 * @param mailMasterDataFilterVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws PersistenceException
	 * @author 204082
	 * Added for IASCB-159276 on 27-Sep-2022
	 */
	public void publishMasterDataForMail(MailMasterDataFilterVO mailMasterDataFilterVO)
			throws SystemException, RemoteException, PersistenceException;

	public void publishMailDetails(MailMasterDataFilterVO mailMasterDataFilterVO)
			throws SystemException, RemoteException, PersistenceException;
 
    /**
     * @author A-8353
     * @param securityScreeningValidationFilterVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws BusinessException
     */
    public Collection<SecurityScreeningValidationVO> findSecurityScreeningValidations(SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO)throws SystemException, RemoteException,BusinessException ;
	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param selectedContainerVOs
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws BusinessException
	 */
    public SecurityScreeningValidationVO doSecurityAndScreeningValidationAtContainerLevel(OperationalFlightVO operationalFlightVO,Collection<ContainerVO> selectedContainerVOs)throws SystemException, RemoteException,BusinessException ;
    public void updateIntFlgAsNForMailBagsInConatiner(HbaMarkingVO hbaMarkingVO) throws SystemException, RemoteException;


	/**-
	 * @author 204084
	 * Added as part of CRQ IASCB-164529
	 * @param routingIndexVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<RoutingIndexVO> getPlannedRoutingIndexDetails(RoutingIndexVO routingIndexVO) throws SystemException, RemoteException;
    /**
     * 
     * 	Method		:	MailTrackingDefaultsBI.generateCN46ConsignmentReport
     *	Added by 	:	A-10647 on 27-Oct-2022
     * 	Used for 	:
     *	Parameters	:	@param reportSpec
     *	Parameters	:	@return
     *	Parameters	:	@throws SystemException
     *	Parameters	:	@throws RemoteException
     *	Parameters	:	@throws PersistenceException
     *	Parameters	:	@throws ReportGenerationException 
     *	Return type	: 	Map<String,Object>
     */
    public Map<String,Object> generateCN46ConsignmentReport(ReportSpec reportSpec)
			throws SystemException,RemoteException,PersistenceException,ReportGenerationException;
   /**
    *  
    * 	Method		:	MailTrackingDefaultsBI.generateCN46ConsignmentSummaryReport
    *	Added by 	:	A-10647 on 27-Oct-2022
    * 	Used for 	:
    *	Parameters	:	@param reportSpec
    *	Parameters	:	@return
    *	Parameters	:	@throws SystemException
    *	Parameters	:	@throws RemoteException
    *	Parameters	:	@throws ProxyException
    *	Parameters	:	@throws PersistenceException
    *	Parameters	:	@throws ReportGenerationException 
    *	Return type	: 	Map<String,Object>
    */
    public Map<String, Object> generateCN46ConsignmentSummaryReport(ReportSpec reportSpec)
    		throws SystemException, RemoteException, ProxyException, PersistenceException, ReportGenerationException;

	/**
	 * @param companyCode
	 * @param airportCode
	 * @return OfficeOfExchangeVO
	 * @throws RemoteException
	 * @author 204082
	 * Added for IASCB-164537 on 09-Nov-2022
	 */
	Collection<OfficeOfExchangeVO> getExchangeOfficeDetails(String companyCode, String airportCode) throws RemoteException, SystemException;
    public String generateAutomaticBarrowId(String cmpcod) throws SystemException, RemoteException;
    /**
     *
     * 	Method		:	MailTrackingDefaultsBI.findCN46TransferManifestDetails
     *	Added by 	:	A-10647 on 27-Oct-2022
     * 	Used for 	:
     *	Parameters	:	@param transferManifestVO
     *	Parameters	:	@return
     *	Parameters	:	@throws RemoteException
     *	Parameters	:	@throws SystemException
     *	Return type	: 	Collection<ConsignmentDocumentVO>
     */
        public Collection<ConsignmentDocumentVO> findCN46TransferManifestDetails(TransferManifestVO transferManifestVO) throws RemoteException, SystemException;


	/**
	 * @author 204083
	 * Method		:	MailTrackingDefaultsBI.getMailbagDetailsForValidation
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<MailbagVO> getMailbagDetailsForValidation(String companyCode, String airportCode) throws SystemException, RemoteException;
	/**
	 * @author A-8353
	 */
    public Collection<SecurityScreeningValidationVO> doApplicableRegulationFlagValidationForPABuidContainer(MailbagVO mailbagVO, SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO)throws SystemException, RemoteException,BusinessException ;
	
	
/**
 * @param fetchFlightPreAdviceDetails
 * @throws SystemException
 * * @throws RemoteException
 * @author A-10555
 */
public Collection<MailAcceptanceVO>fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs)
		throws SystemException, RemoteException;

	public ContainerVO updateActualWeightForMailContainer(ContainerVO containerVO)
			throws SystemException, RemoteException;

	public Collection<OperationalFlightVO> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO)
			throws SystemException, RemoteException;
	public Page<MailTransitVO> findMailTransit(
			MailTransitFilterVO mailTransitFilterVO, int pageNumber)
			throws SystemException, PersistenceException,RemoteException;
	public Collection<FlightSegmentCapacitySummaryVO> findFlightListings(
			FlightFilterVO filterVO)
			throws SystemException, ProxyException,RemoteException;
	public Page<FlightSegmentCapacitySummaryVO> findActiveAllotments(
			FlightSegmentCapacityFilterVO filterVo)
			throws SystemException, ProxyException,RemoteException;
	public MailbagVO findMailConsumed(
			MailTransitFilterVO filterVo)
			throws SystemException ,RemoteException;
	public void updateMLDMsgSentTime(MLDMessageVO mldMessageVO) throws RemoteException, SystemException;
			
			/**
	 * @param attachPAWBToConsignmentJobScheduleVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws PersistenceException
	 * @author A-9998
	 * Added for IASCB-158296
	 */
	@FlowDescriptor(flow = "CreatePAWBForConsignment_Flow", inputs = {"noOfDays"})
	public void createPAWBForConsignment(int noOfDays)
			throws SystemException, RemoteException, PersistenceException;
	
	
	
	/**
	 * @author a-9998
	 * @param carditVO
	 * @return AWBDetailVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingBusinessException
	 */
	public AWBDetailVO findMstDocNumForAWBDetails(CarditVO carditVO)
			throws SystemException, RemoteException,
			MailTrackingBusinessException;

		Collection<MailbagHistoryVO> findMailbagHistoriesFromWebScreen(String companyCode,String mailBagId,  
			long mailSequenceNumber) throws SystemException, RemoteException;
	/**
	 * @author A-9998
	 * @param mailBagVOs
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailHHTBusniessException
	 * @throws MailMLDBusniessException
	 * @throws MailTrackingBusinessException
	 * @throws PersistenceException 
	 */
	public void saveMailScanDetails(
			MailUploadVO mailBagVO)
			throws RemoteException, SystemException, MailHHTBusniessException,
			MailMLDBusniessException, MailTrackingBusinessException, PersistenceException;
			
	public void saveMalRdtMsgAddDtl(Collection<MessageDespatchDetailsVO> messageAddressDetails,Collection<AutoForwardDetailsVO> participantDetails,MessageVO messageVo,String selectedResditVersion,Collection<String> selectedResdits,Collection<MailbagVO> selectedMailbags)
			throws SystemException, RemoteException;
	Page<DSNVO> listCarditDsnDetails(DSNEnquiryFilterVO dSNEnquiryFilterVO) throws SystemException, RemoteException,MailTrackingBusinessException;
				
	public Map<String, Object> generateCN46ReportForFlightlevel(ReportSpec reportSpec)
			throws SystemException, RemoteException;

	
	public void saveScreeningDetailsFromHHT(Collection<ConsignmentScreeningVO> consignmentScreeningVos) throws SystemException, RemoteException, FinderException, PersistenceException,MailHHTBusniessException;

	public Collection<FlightSegmentVolumeDetailsVO> fetchFlightVolumeDetails(Collection<FlightFilterVO> flightFilterVOs)
			throws SystemException, RemoteException;
	public Collection<ULDTypeVO> findULDTypes(ULDTypeFilterVO uldTypeFilterVO)
			throws SystemException, RemoteException, ProxyException, BusinessDelegateException;
	
	void stampACC3IdentifierForPreviousLegMailBags(
			Collection<OperationalFlightVO> operationalFlightVOs)
			throws SystemException, RemoteException;  	

	public ScannedMailDetailsVO validateContainerMailWeightCapture(MailUploadVO mailUploadVO) throws SystemException, RemoteException, MailHHTBusniessException,
	MailMLDBusniessException, MailTrackingBusinessException;  
	  public ContainerVO saveActualWeight(ContainerVO containerVO) throws SystemException, RemoteException, MailHHTBusniessException,
	MailMLDBusniessException, MailTrackingBusinessException;
	  
	public void publishMailOperationDataForRapidSystem(MailOperationDataFilterVO mailOperationDataFilterVO)
				throws SystemException, RemoteException;
	
	public ConsignmentDocumentVO generateConsignmentSummaryReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException,
    RemoteException, PersistenceException;

	public TransferManifestVO generateTransferManifestReportDetails(String companyCode,String transferManifestId ) throws SystemException,
	RemoteException, PersistenceException;
	
	public ConsignmentDocumentVO generateConsignmentSecurityReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException,
			RemoteException, PersistenceException;
	public  Collection<MailbagVO> generateMailTagDetails(Collection<MailbagVO> mailbagVOs) throws SystemException, RemoteException ;
	public ConsignmentDocumentVO generateConsignmentReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException,
	RemoteException, PersistenceException ;

   public  Collection<ConsignmentDocumentVO> generateCN46ConsignmentReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException,
RemoteException, PersistenceException ;

public  Collection<ConsignmentDocumentVO> generateCN46ConsignmentSummaryReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException,
   RemoteException, PersistenceException ;
   public  MailbagVO fetchMailSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo) throws SystemException, RemoteException,PersistenceException;
   public  String findRoutingDetails(String companyCode,long malseqnum) throws SystemException, RemoteException,PersistenceException;
 public  Collection<MailStatusVO> generateMailStatusRT(MailStatusFilterVO mailStatusFilterVO)throws SystemException, RemoteException,PersistenceException;
	public  Collection<DailyMailStationReportVO> generateDailyMailStationRT(DailyMailStationFilterVO filterVO)throws SystemException, RemoteException,ReportGenerationException;
	public  Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO damageMailReportFilterVO)throws SystemException, RemoteException,ReportGenerationException;
public  MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo) throws SystemException, RemoteException,ReportGenerationException;

}