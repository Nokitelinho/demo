/*
 * ULDDefaultsBI.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import java.rmi.RemoteException;
import java.util.*;

import com.ibsplc.icargo.business.capacity.booking.vo.FlightAvailabilityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentForBookingVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.msgbroker.config.format.vo.MessageRuleDefenitionVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cpm.CPMMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.muc.MUCMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDeleteVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageReferenceNumberLovVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHandledCarrierVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDNumberVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.AvoidForcedStaleDataChecks;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;


/**
 * @author A-1496
 *
 */
public interface ULDDefaultsBI {

	

	/**
	 * This method saves the invoice details for the repairs done on a ULD
	 *
	 * @param uldRepairVO
	 * @throws SystemException
	 */
	// void saveRepairInvoice(ULDRepairVO uldRepairVO)
	// throws SystemException;
	/**
	 * This method retrieves the repair head details for invoicing.
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param invoiceRefNumber
	 * @return ULDRepairInvoiceVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	ULDRepairInvoiceVO findRepairInvoiceDetails(String companyCode,
			String invoiceRefNumber) throws SystemException, RemoteException;

	/**
	 * This method validates if the ULD exists in the system
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param uldNumber
	 * @return ULDValidationVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	ULDValidationVO validateULD(String companyCode, String uldNumber)
			throws SystemException, RemoteException;

	/**
	 * This method is used to list the damage reports according to the specified
	 * filter criteria
	 *
	 * @param uldDamageFilterVO
	 * @return Collection<ULDDamageListVO>
	 * @throws SystemException
	 */
	Page<ULDDamageDetailsListVO> findULDDamageList(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException,
			RemoteException;

	/**
	 *
	 * @param uldDamageDeleteVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void deleteULDDamages(Collection<ULDDamageDeleteVO> uldDamageDeleteVOs)
			throws SystemException, RemoteException;


	/**
	 * This method retrieves the uld movement history
	 *
	 * @param reportSpec
	 * @return Map
	 * @throws SystemException
	 */
	Map printExternalMovementsReport(ReportSpec reportSpec)
				throws ULDDefaultsBusinessException, RemoteException , SystemException;
	/**
	 *
	 * @param uldRepairFilterVO
	 * @return Page
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDRepairDetailsListVO> listULDRepairDetails(
			ULDRepairFilterVO uldRepairFilterVO) throws SystemException,
			RemoteException;

	/**
	 * This methos is used to save the details of the uld damage.
	 *
	 * @param uldDamageRepairDetailsVO
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	@AvoidForcedStaleDataChecks
	void saveULDDamage(ULDDamageRepairDetailsVO uldDamageRepairDetailsVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * This method is used to list the damage details for the specified damage
	 * reference number for a particular ULD.
	 *
	 * @param uldDamageFilterVO
	 * @return
	 * @throws SystemException
	 */
	ULDDamageRepairDetailsVO findULDDamageDetails(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException,
			RemoteException;

	/**
	 * This method retrieves the uld movement history
	 *
	 * @param uldMovementFilterVO
	 * @return Collection<ULDMovementVO>
	 * @throws SystemException
	 */
	// Collection findULDMovementHistory(ULDMovementFilterVO
	// uldMovementHistoryVO)
	// throws RemoteException , SystemException;
	/**
	 * @param uldNOs
	 * @param uldMovementVos
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	@AvoidForcedStaleDataChecks
	public void saveULDMovement(Collection<String> uldNOs,
			Collection<ULDMovementVO> uldMovementVos, String screenID, String overrideFlag) throws RemoteException,
			SystemException, ULDDefaultsBusinessException;

	/**
	 * This method retrieves the uld details of the specified filter condition
	 *
	 * @param accessoriesStockConfigFilterVO
	 * @param displayPage
	 * @return Page
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<AccessoriesStockConfigVO> findAccessoryStockList(
			AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO,
			int displayPage) throws SystemException, RemoteException;

	/**
	 *
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDListVO> findULDList(ULDListFilterVO uldListFilterVO, int pageNumber)
			throws SystemException, RemoteException;

	
	/**
	 * This method retrieves the stock details of the specified accessory code
	 *
	 * @param companyCode
	 * @param accessoryCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	AccessoriesStockConfigVO findAccessoriesStockDetails(String companyCode,
			String accessoryCode, String stationCode, int airlineIdentifier)
			throws RemoteException, SystemException;

	/**
	 * This method saves the accessory stock details
	 *
	 * @param accessoriesStockConfigVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void saveAccessoriesStock(AccessoriesStockConfigVO accessoriesStockConfigVO)
			throws RemoteException, SystemException;

	/**
	 * This method validates the format of the specified ULD
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @throws RemoteException
	 * @throws SystemException
	 */
	boolean validateULDFormat(String companyCode, String uldNumber)
			throws RemoteException, SystemException,
			ULDDefaultsBusinessException;

	/**
	 * This method retrieves the details of the specified ULD
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	ULDVO findULDDetails(String companyCode, String uldNumber)
			throws RemoteException, SystemException,
			ULDDefaultsBusinessException;

	/**
	 * This method saves the uld details
	 *
	 * @param uldVo
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void saveULD(ULDVO uldVo) throws RemoteException, SystemException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * @param uldVo
	 * @param uldNumberVos
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */

	Collection<String> createMultipleULDs(ULDVO uldVo,
			Collection<String> uldNumberVos) throws RemoteException,
			SystemException, ULDDefaultsBusinessException;

	/**
	 * This method retrieves the structural details defined at the ULD Type
	 * specified for the ULD
	 *
	 * @param companyCode
	 * @param uldTypeCode
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	ULDTypeVO findULDTypeStructuralDetails(String companyCode,
			String uldTypeCode) throws RemoteException, SystemException;







	


	/**
	 * This method is used to find the ULDType details
	 *
	 * @param companyCode
	 * @param agreementNumber
	 * @return ULDAgreementVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	ULDAgreementVO findULDAgreementDetails(String companyCode,
			String agreementNumber) throws RemoteException, SystemException;

	//Added by A-8445 as a part of IASCB-28460 Starts
	/**
	 * This method is used to find the ULDType details with Pagination
	 *
	 * @param companyCode
	 * @param agreementNumber
	 * @param uldAgreementFilterVO
	 * @return Page
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<ULDAgreementDetailsVO> findULDAgreementDetailsPagination(String companyCode,
			String agreementNumber, ULDAgreementFilterVO uldAgreementFilterVO) throws RemoteException,
			SystemException;
	//Added by A-8445 as a part of IASCB-28460 Ends
	
	/**
	 * This method is used to create a ULD Agreement
	 *
	 * @param uldAgreementVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	String createULDAgreement(ULDAgreementVO uldAgreementVO)
			throws RemoteException, SystemException,
			ULDDefaultsBusinessException;



	/**
	 * This method is used for listing uld agreement in the system
	 *
	 * @param uldAgreementFilterVO
	 * @return Page
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<ULDAgreementVO> listULDAgreements(
			ULDAgreementFilterVO uldAgreementFilterVO) throws RemoteException,
			SystemException;

	/**
	 * This method is used for populating the ULD Agreements
	 *
	 * @param companyCode
	 * @param pageNumber
	 * @return Page
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page populateULDAgreementLOV(ULDAgreementFilterVO uldAgreementFilterVO)
	throws RemoteException, SystemException;

	/**
	 * This method is used to update the status of ULD Agreement to the changed
	 * status.ie Active to Inactive and ViceVeraa
	 *
	 * @param companyCode
	 * @param agreementNumbers
	 * @param changedStatus
	 * @throws RemoteException
	 * @throws SystemException
	 */
	/*
	 * public void updateULDAgreementStatus(String companyCode, Collection<String>
	 * agreementNumbers, String changedStatus) throws RemoteException,
	 * SystemException;
	 */

	public void updateULDAgreementStatus(Collection<ULDAgreementVO> uldAgrVOs,
			String changedStatus) throws RemoteException, SystemException,ULDDefaultsBusinessException;

	/**
	 * This method is used to create modify and delete ULDStockConfiguration
	 *
	 * @param uLDStockConfigVOs
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void saveULDStockConfig(Collection<ULDStockConfigVO> uLDStockConfigVOs)
			throws RemoteException, SystemException;

	/**
	 * This method is used to list the ULD Set up Configuration
	 *
	 * @param uldStockConfigFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Collection<ULDStockConfigVO> listULDStockConfig(
			ULDStockConfigFilterVO uldStockConfigFilterVO)
			throws RemoteException, SystemException;



	/**
	 * This method is used to list ULDs at a purticular station
	 *
	 * @param companyCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @return String
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<String> findStationUlds(String companyCode, String uldNumber,
			String stationCode,String transactionType, int airlineIdentifier, int displayPage)
			throws RemoteException, SystemException;



	/**
	 *
	 * @param transactionVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */

	@AvoidForcedStaleDataChecks
	public Collection<ErrorVO> saveULDTransaction(TransactionVO transactionVO)
			throws RemoteException, SystemException,
			ULDDefaultsBusinessException,MessageConfigException;

	/*
	 * This method is used for listing uld agreement in the system for a ULD
	 * Transaction
	 *
	 * @param String ULDAgreementFilterVO @return Page<ULDAgreementVO>
	 *
	 * @throws SystemException @throws RemoteException
	 */
	// To be reviewed Page<ULDAgreementVO>


	/**
	 * This method is used for listing uld transaction
	 *
	 * @author A-1883
	 * @param transactionFilterVO
	 * @return TransactionListVO
	 * @throws SystemException
	 * @throws RemoteException
	 */

	public TransactionListVO listULDTransactionDetails(
			TransactionFilterVO transactionFilterVO) throws SystemException,
			RemoteException;

	/**
	 *
	 * @param uldTransactionFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	TransactionListVO findULDTransactionDetailsCol(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException,
			RemoteException;




	/**
	 * @author A-3278
	 * for QF1015 on 24Jul08
	 * to find the total demmurage calculated
	 * @param transactionFilterVO
	 * @return ULDTransactionDetailsVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public ULDTransactionDetailsVO findTotalDemmurage(
			TransactionFilterVO transactionFilterVO) throws SystemException,
			RemoteException;



	/**
	 * This method is used for calculating the
	 * Demurrage Charges Accrued against the UDLds
	 * @author a-3278
	 * @param uldTransactionDetailsVOs
	 * @return Collection
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ULDTransactionDetailsVO> calculateULDDemmurage(
			Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;


	/**
	 * This method is used for listing uld Invoice
	 *
	 * @author A-1883
	 * @param chargingInvoiceFilterVO
	 * @param pageNumber
	 * @return Page<ULDChargingInvoiceVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ULDChargingInvoiceVO> listULDChargingInvoice(
			ChargingInvoiceFilterVO chargingInvoiceFilterVO, int pageNumber)
			throws SystemException, RemoteException;

	/**
	 * This method is used for listing uld Invoice Details
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param invoiceRefNumber
	 * @return Collection<ULDTransactionDetailsVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ULDTransactionDetailsVO> viewULDChargingInvoiceDetails(
			String companyCode, String invoiceRefNumber)
			throws SystemException, RemoteException;

	/**
	 * This method is used fo Generating Invoice for returned ULDs
	 *
	 * @param uldChargingInvoiceVO
	 * @param uldTransactionDetailsVOs
	 * @return String
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String generateInvoiceForReturnedUlds(
			ULDChargingInvoiceVO uldChargingInvoiceVO,
			Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
			throws SystemException, RemoteException;

	/**
	 * This method is used for Saving the Returned ULDs and Accessories
	 *
	 * @param transactionListVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	public void saveReturnTransaction(TransactionListVO transactionListVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException,
			CurrencyConversionException, DimensionConversionException;

	/**
	 *
	 * @param uldVos
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	void saveULDs(Collection<ULDVO> uldVos) throws RemoteException,
			SystemException, ULDDefaultsBusinessException;

	/**
	 *
	 * @param uldMovementFilterVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDMovementDetailVO> findULDMovementHistory(
			ULDMovementFilterVO uldMovementFilterVO, int displayPage)
			throws SystemException, RemoteException;

	/**
	 * For InvoiceRefNumber LOV
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param displayPage
	 * @return Page<String>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<String> findInvoiceRefNumberLov(String companyCode, int displayPage,
			String invRefNo) throws SystemException, RemoteException;

	/**
	 * @author A-1883
	 * @param companyCode
	 * @param uldNumbers
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<String> validateMultipleULDFormats(String companyCode,
			Collection<String> uldNumbers) throws SystemException,
			RemoteException;

	/**
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDDamageReferenceNumberLovVO> findULDDamageReferenceNumberLov(
			String companyCode, String uldNumber, int pageNumber)
			throws SystemException, RemoteException;

	/**
	 * This method is used to update ULD Repair InvoiceDetails (waived amount
	 * and remarks)
	 *
	 * @param uLDRepairInvoiceDetailsVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void updateULDRepairInvoiceDetails(
			Collection<ULDRepairInvoiceDetailsVO> uLDRepairInvoiceDetailsVOs)
			throws SystemException, RemoteException;

	/**
	 * This method is used to Monitor ULD stock
	 *
	 * @author A-1883
	 * @param uLDStockConfigFilterVO
	 * @param displayPage
	 * @return Page<ULDStockListVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDStockListVO> findULDStockList(
			ULDStockConfigFilterVO uLDStockConfigFilterVO, int displayPage)
			throws SystemException, RemoteException;

	/**
	 * This method is used to find the Agreeement Detaiuls For the Transaction
	 *
	 * @param uldTransactionDetailsVos
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Collection<String> findAgreementNumberForTransaction(
			Collection<ULDTransactionDetailsVO> uldTransactionDetailsVos)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * This method saves multiple accessory stock details
	 *
	 * @param accessoriesStockConfigVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void saveAccessories(
			Collection<AccessoriesStockConfigVO> accessoriesStockConfigVOs)
			throws SystemException, RemoteException;



	/**
	 *
	 * @param lucMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<MessageVO> generateLUCMessage(LUCMessageVO lucMessageVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;



	/**
	 *
	 * @param uldDiscrepancyVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	 String saveULDDiscrepencyDetails(
			Collection<ULDDiscrepancyVO> uldDiscrepancyVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * @param discrepancyFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDDiscrepancyVO> listULDDiscrepancyDetails(
			ULDDiscrepancyFilterVO discrepancyFilterVO) throws SystemException,
			RemoteException;



	/**
	 *
	 * @param reconcileVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	public void processSCMMessage(ULDSCMReconcileVO reconcileVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * @param mucMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void processMUCMessage(MUCMessageVO mucMessageVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException,
			CurrencyConversionException, DimensionConversionException;

	/**
	 *
	 * @param auditVo
	 * @throws AuditException
	 * @throws RemoteException
	 */

	void audit(Collection<AuditVO> auditVo) throws AuditException,
			RemoteException;

	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	HashMap<String, Collection<String>> populateLocation(String companyCode,
			String airportCode) throws SystemException, RemoteException;

	/**
	 *
	 * @param uldVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	// @AvoidForcedStaleDataChecks
	void setLocationForULD(Collection<ULDVO> uldVOs) throws SystemException,
			RemoteException, ULDDefaultsBusinessException;

	/**
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	ULDDamagePictureVO findULDDamagePicture(String companyCode,
			String uldNumber, long sequenceNumber,int imageSequenceNumber ) throws SystemException,
			RemoteException;

	/**
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	int findOperationalAirlineForULD(String companyCode, String uldNumber)
			throws SystemException, RemoteException;





	/**
	 * Modified by A-5165 for ICRD-262166- returns collection of errors for warning
	 * @param reconcileVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	@AvoidForcedStaleDataChecks
	Collection<ErrorVO> processUCMMessage(Collection<ULDFlightMessageReconcileVO> reconcileVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * @param cpmMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	void saveCPMDetails(CPMMessageVO cpmMessageVO) throws SystemException,
			RemoteException;



	/**
	 * @author A-2667
	 * @param FlightDetailsVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	void validateULDsForOperation(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;


	

	/**
	 *
	 * @param locationVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	void saveULDAirportLocation(Collection<ULDAirportLocationVO> locationVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @param facilityType
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ULDAirportLocationVO> listULDAirportLocation(String companyCode,
			String airportCode, String facilityType) throws SystemException,
			RemoteException;

	/**
	 * 
	 * @param companyCode
	 * @param carrierCode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	AirlineValidationVO populateAirlineCodes(String companyCode,
			String carrierCode)throws SystemException, RemoteException;  



	/**
	 *
	 * @param reconcileVOs
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	@AvoidForcedStaleDataChecks
	Collection<ULDFlightMessageReconcileVO> saveULDFlightMessageReconcile(
			Collection<ULDFlightMessageReconcileVO> reconcileVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	ULDFlightMessageReconcileVO listUCMMessage(FlightFilterMessageVO filterVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param flightFilterVo
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<FlightValidationVO> fetchFlightDetails(
			FlightFilterVO flightFilterVo) throws SystemException,
			RemoteException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDFlightMessageReconcileVO> listUCMErrors(
			FlightFilterMessageVO filterVO) throws SystemException,
			RemoteException;

	/**
	 * @author A-1950
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<String> findUCMNoLOV(FlightFilterMessageVO filterVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDFlightMessageReconcileDetailsVO> listUldErrors(
			FlightFilterMessageVO filterVO) throws SystemException,
			RemoteException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ULDFlightMessageReconcileDetailsVO> listUCMINOUTMessage(
			FlightFilterMessageVO filterVO) throws SystemException,
			RemoteException;

	/**
	 * @author A-1950
	 * @param uldReconcileDetailsVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	String reconcileUCMULDError(
			ULDFlightMessageReconcileDetailsVO uldReconcileDetailsVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Collection<ULDFlightMessageReconcileDetailsVO> listUCMOUTForInOutMismatch(
			FlightFilterMessageVO flightFilterVO) throws SystemException,
			RemoteException, ULDDefaultsBusinessException;

	/**
	 * @author A-1950
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	String findULDCurrentStation(String companyCode, String uldNumber)
			throws SystemException, RemoteException;

	/**
	 * @author A-1950
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	void updateULDTransitStatus(String companyCode, String uldNumber)
			throws SystemException, RemoteException;

	

	/**
	 * @author A-1950
	 *
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ULDFlightMessageReconcileVO> listUCMsForComparison(
			FlightFilterMessageVO flightFilterVO) throws SystemException,
			RemoteException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ULDVO> findUldDetailsForSCM(SCMMessageFilterVO filterVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param reconcileVOs
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	HashMap<String, Collection<MessageVO>> saveSCMMessage(
			Collection<ULDSCMReconcileVO> reconcileVOs, ULDListFilterVO uldListFilterVO, Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs) throws SystemException,
			RemoteException, ULDDefaultsBusinessException;

	public String saveSCMReconcialtionDetails(Collection<ULDSCMReconcileVO> reconcileVOs) throws SystemException,
			RemoteException, ULDDefaultsBusinessException;
	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDSCMReconcileVO> listSCMMessage(SCMMessageFilterVO filterVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDSCMReconcileDetailsVO> listULDErrorsInSCM(
			SCMMessageFilterVO filterVO) throws SystemException,
			RemoteException;

	/**
	 *
	 * @param flightMessageReconcileVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	void sendUCM(ULDFlightMessageReconcileVO flightMessageReconcileVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	String findCurrentAirportForULD(SCMMessageFilterVO filterVO)
			throws SystemException, RemoteException;



	/**
	 *
	 * @param uldDetails
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void updateUCMULDDetails(
			Collection<ULDFlightMessageReconcileDetailsVO> uldDetails)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param uldDetails
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void removeErrorCodeForULDsInSCM(
			Collection<ULDSCMReconcileDetailsVO> uldDetails)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param filterVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	Collection<MessageVO> sendSCMMessage(SCMMessageFilterVO filterVO, Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * @param uldDetails
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	void deleteULDsInSCM(Collection<ULDSCMReconcileDetailsVO> uldDetails)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param poolOwnerVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void saveULDPoolOwner(Collection<ULDPoolOwnerVO> poolOwnerVOs)
			throws SystemException, RemoteException,ULDDefaultsBusinessException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ULDPoolOwnerVO> listULDPoolOwner(ULDPoolOwnerFilterVO filterVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ULDSCMReconcileVO> listSCMMessageLOV(SCMMessageFilterVO filterVO)
			throws SystemException, RemoteException;

	

	/**
	 * @author A-1950
	 *
	 * @param lucMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	void processLUCMessage(LUCMessageVO lucMessageVO) throws SystemException,
			RemoteException, ULDDefaultsBusinessException;

	/***************************************************************************
	 *
	 * @param companyCode
	 * @param airportCode
	 * @param airlineIdentifier
	 * @param uldIdentifier
	 * @param date
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void generateSCMFromMonitorULD(String companyCode,
			ULDStockListVO uLDStockListVO) throws SystemException,
			RemoteException, ULDDefaultsBusinessException;



	/**
	 *
	 * A-1950
	 *
	 * @param uldValidationVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	void validateULDForWarehouseOccupancy(
			Collection<ULDValidationVO> uldValidationVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * A-1950
	 *
	 * @param uldValidationVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	void checkForULDInOperation(Collection<ULDValidationVO> uldValidationVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;



	/**
	 *
	 * A-1950
	 *
	 * @param flightDetailsVO
	 * @throws SystemException
	 */
	/*
	@AvoidForcedStaleDataChecks
	void updateULDForFlightFinalisation(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException;

	 */
	/**
	 *
	 * A-1950
	 *
	 * @param flightDetailsVO
	 * @throws SystemException
	 */
	/*
	@AvoidForcedStaleDataChecks
	void updateULDForFlightClosure(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException;
    */
	/**
	 *
	 * A-1950
	 *
	 * @param flightDetailsVO
	 * @throws SystemException
	 */
	@AvoidForcedStaleDataChecks
	void updateULDForOperations(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * A-1950
	 *
	 * @param flightDetailsVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	/*
	@AvoidForcedStaleDataChecks
	void updateULDForOffload(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException;
	*/
	

	

	/**
	 * @author A-1936 This method is used to save the Exception Flights for
	 *         which the UCM has to be sent manually
	 * @param ucmExceptionFlightVos
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void saveExceptionFlights(
			Collection<UCMExceptionFlightVO> ucmExceptionFlightVos)
			throws SystemException, RemoteException;

	/**
	 * @author A-1936 This method is used to find the Exception Flights for
	 *         which the UCM has to be sent manually
	 * @param companyCode
	 * @param pol
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<UCMExceptionFlightVO> findExceptionFlights(String companyCode,
			String pol) throws SystemException, RemoteException;

	/**
	 * The method is used to pick uldNature, uldDamageStatus etc for a uldNumber
	 * A-1950
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	ULDListVO fetchULDDetailsForTransaction(String companyCode, String uldNumber)
			throws SystemException, RemoteException;

	/**
	 *
	 * A-1950
	 *
	 * @param companyCode
	 * @param carrierCode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	String findCRNForULDTransaction(String companyCode, String carrierCode)
			throws SystemException, RemoteException;

	/**
	 *
	 * A-1950
	 *
	 * @param transactionDetails
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	void updateULDForDelivery(
			Collection<ULDTransactionDetailsVO> transactionDetails)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException,MessageConfigException;

	/**
	 *
	 * A-1950
	 *
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void updateULDPrice(String companyCode,int period) throws SystemException,
			RemoteException;

	/**
	 *
	 * A-1950
	 *
	 * @param period
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	void updateULDStatusAsLost(String companyCode, int period)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printLoanBorrowULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printLoanBorrowULDForPage(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printListULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;
	/**
	 * 
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printUldInventoryList(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printPageListULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;
	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printMonitorULDStock(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printMaintainULDAgreement(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printListULDAgreement(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;


	/**
	 * @author A-3278
	 * Method for printing the ULD availability
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printuldtransactionReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;




	/**
	 *
	 * @param uldMovementDetails
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void deleteULDMovements(Collection<ULDMovementDetailVO> uldMovementDetails)
			throws SystemException, RemoteException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printULDChargingInvoice(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printULDDamageReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printMaintainUldStock(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printListUldMovement(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @author A-2619
	 * @return Page This method is used to list the ULD History
	 * @param uldHistoryVO.
	 * @throws BusinessDelegateException.
	 */
	public Page<ULDHistoryVO> listULDHistory(ULDHistoryVO uldHistoryVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @author A-2619
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printULDHistory(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/* Added by A-2412 on 18th Oct for Editable CRN cr */
	public Collection<String> checkForDuplicateCRN(String companyCode,
			TransactionVO transactionVO) throws SystemException,
			RemoteException;

	/* Addition by A-2412 on 18th Oct for Editable CRN cr ends */

	public Map<String, Object> printUCRLoanBorrowULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	

	/**
	 * @param transactionDetailsVOs
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	Collection<ULDTransactionDetailsVO> sendMUCMessage(
			Collection<ULDTransactionDetailsVO> transactionDetailsVOs)
			throws SystemException, RemoteException;

	/**
	 * @author A-2883
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printListRepairULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;



	/**
	 * This method retrieves the carrier details of the specified filter
	 * condition
	 *
	 * @author A-2883
	 * @param handledCarrierSetupVO
	 * @return Collection
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ULDHandledCarrierVO> findHandledCarrierSetup(
			ULDHandledCarrierVO handledCarrierSetupVO) throws SystemException,
			RemoteException;

	/**
	 * This method save the carrier details
	 *
	 * @author A-2883
	 * @param handledCarrierSetupVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	public void saveHandledCarrier(
			Collection<ULDHandledCarrierVO> handledCarrierSetupVO)
			throws SystemException, RemoteException;

	/**
	 * @author A-2408
	 * @param companyCode
	 * @param twoAlphaCode
	 * @param threeAlphaCode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	String findOwnerCode(String companyCode, String twoAlphaCode,
			String threeAlphaCode) throws SystemException, RemoteException;

	/**
	 * @author A-2412
	 * @param transactionVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	public void deleteAccessoryDetails(
			Collection<AccessoryTransactionVO> accessoryTransactionVos)
			throws SystemException, RemoteException;


	/**
	 * @author A-2667
	 * @param companyCode
	 * @param displayPage
	 * @param comboValue
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	public Page<ULDDiscrepancyVO> populateLocationLov(String companyCode,
			int displayPage, String comboValue, String airportCode)
			throws SystemException, RemoteException;

	/**
	 * @author a-2883
	 * @param reportSpec
	 * @return Map<String,Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printMaintainDamageRepairReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @author A-2412
	 * @param uldIntMvtFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */

	public Page<ULDIntMvtDetailVO> findIntULDMovementHistory(
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO, int pageNumber)
			throws SystemException, RemoteException;

	/**
	 * @author A-3045
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> generateULDDiscrepancyReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;



	/**
	 * @author A-3045
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
   Map<String,Object> printULDIntMovementHistory(ReportSpec reportSpec)
	throws SystemException,RemoteException,ULDDefaultsBusinessException;

   /**
    * @author A-2667
    * @param relocateULDVO
    * @return Collection<AuditDetailsVO>
    * @throws SystemException
    * @throws RemoteException
    */
   Collection<AuditDetailsVO> findULDAuditEnquiryDetails(
		   RelocateULDVO relocateULDVO)throws SystemException,RemoteException;
	   /**
		 * @param reportSpec
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 * @throws ULDDefaultsBusinessException
		 */
	Map<String, Object> printUldStationStock(ReportSpec reportSpec)
		throws SystemException, RemoteException,ULDDefaultsBusinessException;
	/**
	 * @author A-1950
	 * @param companyCode
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void sendAlertForULDStockDeviation(String companyCode)
	throws SystemException, RemoteException;

	/**
	 * @author A-1950
	 * @param companyCode
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void sendAlertForULDAccStockDepletion(String companyCode)
	throws SystemException, RemoteException;

	/**
	 * @author A-1950
	 * @param companyCode
	 * @param period
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void sendAlertForDiscrepancy(String companyCode,int period)
	throws SystemException, RemoteException;
	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	Map<String, Object> printUldAuditEnquiry(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	ULDDefaultsBusinessException;



	/**
	 * @author A-3459
	 * @param ULDDamageChecklistVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	 Collection<ULDDamageChecklistVO> listULDDamageChecklistMaster(String companyCode,String section)
			throws SystemException, RemoteException;

	 /**
	  	 * @author A-3459
		 * @param uldDamageChecklistVOs
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void saveULDDamageChecklistMaster(Collection<ULDDamageChecklistVO> uldDamageChecklistVOs)
				throws SystemException, RemoteException;

		/**
		* @author A-3459
	    * Finds all the warehouses
	    * @param companyCode
	    * @param airportCode
	    * @return Collection<WarehouseVO>
	    * @throws SystemException
	    * @throws RemoteException
	    */
	   public Collection<WarehouseVO> findAllWarehousesforULD(String companyCode,
				String airportCode) throws SystemException,RemoteException;
	   /**
		 * @author A-2408
		 * @param companyCode
		 * @param period
		 * @throws SystemException
		 * @throws RemoteException
		 */
	   @AvoidForcedStaleDataChecks
		public void updateSCMStatusForULD(String companyCode,int period)
		   throws SystemException,ULDDefaultsBusinessException,RemoteException;

	 //added by jisha for QF1022 starts

	  /**
	   * @author a-3093
	   * @param uldRepairFilterVO
	   * @param displayPage
	   * @return
	   * @throws SystemException
	   */
	   Page<ULDRepairDetailsListVO> listDamageRepairDetails(
				UldDmgRprFilterVO uldRepairFilterVO ,int displayPage) throws SystemException,
				RemoteException,ULDDefaultsBusinessException;
		/**
		 * @author a-3093
		 * @param uldmovementFilterVO
		 * @return
		 * @throws SystemException
		 */
		Page<OperationalULDAuditVO> listBuildupBreakdowndetails(
				ULDMovementFilterVO uldmovementFilterVO) throws SystemException,
				RemoteException,ULDDefaultsBusinessException;
		/**
		 * @author a-3093
		 * @param uldmovementFilterVO
		 * @return
		 * @throws SystemException
		 */
		Page<AuditDetailsVO> findULDActionHistory(
				ULDMovementFilterVO uldmovementFilterVO) throws SystemException,
				RemoteException,ULDDefaultsBusinessException;

     //added by jisha for QF1022 ends
	   	 /**
		 * @author A-3429
		 * @param uLDMovementFilterVO
		 * @throws SystemException
		 * @return ULDNumberVO
		 * @throws RemoteException
		 */
		public ULDNumberVO findULDHistoryCounts(ULDMovementFilterVO uLDMovementFilterVO)
		throws SystemException, RemoteException;


	   /**
		 *
		 * @param reconcileVOs
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		@AvoidForcedStaleDataChecks
		HashMap<String, Collection<MessageVO>> sendSCMMessageforUlds(
				Collection<ULDSCMReconcileVO> reconcileVOs, Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs) throws SystemException,
				RemoteException, ULDDefaultsBusinessException;
		/**
		 * @author A-2408
		 * @param uldPoolOwnerFilterVO
		 * @return
		 * @throws SystemException
		 * this method checks if two airlines are pool owners in segment
		 */
		public boolean checkforPoolOwner(ULDPoolOwnerFilterVO uldPoolOwnerFilterVO)
			throws SystemException, RemoteException;
		/**
		 * @author a-3459
		 * @param uldFlightMessageReconcileVO
		 * @return
		 * @throws SystemException
		 */
		Page<ULDFlightMessageReconcileVO> findMissingUCMs(ULDFlightMessageReconcileVO uldFlightMessageReconcileVO)
			throws SystemException,RemoteException,ULDDefaultsBusinessException;
		 /**
		 * For MUCRefNumber LOV
		 *
		 * @author A-3045
		 * @param companyCode
		 * @param displayPage
		 * @param mucRefNum
		 * @param mucDate
		 * @return Page<String>
		 * @throws SystemException
		 * @throws RemoteException
		 */
		Page<String> findMUCRefNumberLov(String companyCode, int displayPage,
				String mucRefNum, String mucDate) throws SystemException, RemoteException;

		/**
		 * For MUC Action
		 *
		 * @author A-3045
		 * @param companyCode
		 * @param mucReferenceNumber
		 * @param MucDate
		 * @param controlReceiptNumber
		 * @return Collection<ULDConfigAuditVO>
		 * @throws SystemException
		 * @throws RemoteException
		 */
		Collection<ULDConfigAuditVO> findMUCAuditDetails(ULDTransactionDetailsVO uldTransactionDetailsVO)
		throws SystemException, RemoteException;

		/**
		 * For updateULDTransaction for MUC Tracking
		 * as a part of CR QF1013
		 * @author A-3045
		 * @param uldTransactionDetailsVOs
		 * @return void
		 * @throws SystemException
		 * @throws RemoteException
		 */
		void updateULDTransaction(Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
		throws SystemException, RemoteException;

		/**
		 * For updateMUCStatus
		 * as a part of CR QF1142
		 * @author A-3045
		 * @param uldTransactionDetailsVOs
		 * @return void
		 * @throws SystemException
		 * @throws RemoteException
		 */
		void updateMUCStatus(Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
		throws SystemException, RemoteException;
		/**
		 * @author A-2408
		 * @param reportSpec
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 * @throws ULDDefaultsBusinessException
		 */
		public Map<String, Object> printInternalMovements(ReportSpec reportSpec)
		throws SystemException, RemoteException,ULDDefaultsBusinessException;

		/**
		  * @author A-2553
		  * @param reportSpec
		 	* @return
		  * @throws SystemException
		  * @throws ReportGenerationException
		  */
		  public Map<String,Object> generateTransactionDetailsReport(ReportSpec reportSpec)throws
		  SystemException, RemoteException,ULDDefaultsBusinessException,ReportGenerationException;

		 /**
		 * @author a-2412
		 * @param reportSpec
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 * @throws ULDDefaultsBusinessException
		 * @throws ReportGenerationException
		 */
		public Map<String, Object> printBuildUpDetails(ReportSpec reportSpec)throws SystemException,
		  RemoteException,ULDDefaultsBusinessException,ReportGenerationException;

		/**
		 * @return A-2883
		 * @param reportSpec
		 * @return Map<String, Object>
		 * @throws SystemException
		 * @throws RemoteException
		 * @throws ULDDefaultsBusinessException
		 */
		public Map<String, Object>  listDamageRepairDetailsReport(
				ReportSpec reportSpec) throws SystemException,
				RemoteException, ULDDefaultsBusinessException;


		/**
		 * @author a-2412
		 * @param flightDetailsVO
		 * @throws SystemException
		 * @throws RemoteException
		 */
		@AvoidForcedStaleDataChecks
		void markULDFlightMovements(FlightDetailsVO flightDetailsVO)
				throws SystemException, RemoteException;

		//added by a-3045 for bug17959 starts
		//for finding the ULDs in use
		/**
		 * For checkULDInUse
		 * @author A-3045
		 * @param uldNumbers
		 * @return Collection<String>
		 */
		Collection<String> checkULDInUse(String companyCode,Collection<String> uldNumbers)
		throws SystemException, RemoteException;

		/**
		 *  This method is used for listing Transaction details in ULD History screen
		 *
		 * @author A-3045
		 * @param transactionFilterVO
		 * @return TransactionListVO
		 * @throws SystemException
		 * @throws RemoteException
		 */

		public TransactionListVO findTransactionHistory(
				TransactionFilterVO transactionFilterVO) throws SystemException,
				RemoteException;

		/**
		 * This method retrieves the uld details of the specified filter condition for SCM
		 * @author A-3045
		 * @param uldListFilterVO
		 * @param pageNumber
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		Page<ULDVO> findULDListForSCM(ULDListFilterVO uldListFilterVO, int pageNumber)
				throws SystemException, RemoteException;




	/**
	 * @author a-2412
	 * @param transactionVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ErrorVO> validateULDsForTransaction(TransactionVO transactionVO)
	throws SystemException, RemoteException;



	/**
	 * This method retrieves the uld details of the specified filter condition for SCM validation screen at a particular for a specified period
	 * @author A-3459
	 * @param scmValidationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<SCMValidationVO> findSCMValidationList(SCMValidationFilterVO scmValidationFilterVO)
			throws SystemException, RemoteException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printSCMValidationReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @author a-3278
	 * To offload the ULDs from flight
	 * @param uldVos
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	void offloadULDs(Collection<ULDVO> uldVos) throws RemoteException,
			SystemException, ULDDefaultsBusinessException;
	/**
	 *
	 * @param companyCode
	 * @param partyType
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ULDServiceabilityVO> listULDServiceability(String companyCode,
			String partyType) throws SystemException, RemoteException;

	/**
	 *
	 * @param serviceabilityVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	void saveULDServiceability(Collection<ULDServiceabilityVO> serviceabilityVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException;

	/**
	 * @author a-2412
	 * @param transactionVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<FlightFilterVO> validateFlightsForSegments(Collection<FlightFilterVO> flightFilterVOs)
	throws SystemException, RemoteException, ULDDefaultsBusinessException;
	/**
	 * @param eventAsyncHelperVO
	 * @throws RemoteException
	 */
	public void handleEvents(com.ibsplc.xibase.server.framework.event.vo.EventAsyncHelperVO eventAsyncHelperVO) throws RemoteException;

	/**
	 * This method retrieves the uld details of the specified filter condition
	 *
	 * @param EstimatedULDStockFilterVO
	 * @param displayPage
	 * @return Page
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<EstimatedULDStockVO> findEstimatedULDStockList(
			EstimatedULDStockFilterVO estimatedULDStockFilterVO,
			int displayPage) throws SystemException, RemoteException;
	/**
	 * This method retrieves the uld details of the specified filter condition
	 *
	 * @param ExcessStockAirportFilterVO
	 * @param displayPage
	 * @return Page
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ExcessStockAirportVO> findExcessStockAirportList(
			ExcessStockAirportFilterVO excessStockAirportFilterVO,
			int displayPage) throws SystemException, RemoteException;
	/**
	 * @author A-5125
	 * @param messageVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void sendMessageWithEnvelopeEncoding(
			Collection<MessageVO> messageVOs)throws RemoteException, SystemException;
	public Page<FlightSegmentForBookingVO> listFlightDetails(FlightAvailabilityFilterVO fltAvbFilterVO)throws BusinessDelegateException,RemoteException;
	/**
	 * Added for ICRD-192217
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void sendULDStockNotifications(EstimatedULDStockFilterVO estimatedULDStockFilterVO) throws RemoteException,SystemException;
	/**
	 * Added for ICRD-19280
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void sendUCMMissingNotifications(EstimatedULDStockFilterVO estimatedULDStockFilterVO) throws RemoteException,SystemException;
	
	/**
	 * Added for ICRD-114538
	 * @author A-3415
	 * @param TransactionFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<String> checkIfOpenTransactionExists(Collection<TransactionFilterVO> filterVOs) throws RemoteException,SystemException;
	
	/**
	 * Added for ICRD-188218
	 * @author A-6841
	 * @param messageType
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<MessageRuleDefenitionVO> findMessageTypeAndVersion(String companyCode, String messageType) throws RemoteException, SystemException;
	 
//Added by A-6991 for ICRD-192185
	public boolean validateFlightArrival(FlightValidationVO flightValidationVO, ArrayList<String> destination) throws RemoteException, SystemException;
/**
	 * 
	 * 	Method		:	ULDDefaultsBI.findConsolidatedUCMsForFlight
	 *	Added by 	:	A-7359 on 07-Sep-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@param uldFlightMessageFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ULDFlightMessageReconcileVO
	 */
	public ULDFlightMessageReconcileVO findConsolidatedUCMsForFlight(FlightFilterMessageVO uldFlightMessageFilterVO)throws RemoteException, SystemException;
	/**
	 * 	Method		:	ULDDefaultsBI.findULDDamageRepairDetails
	 *	Added by 	:	A-7043 on 13-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param uldDamageFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	ULDDamageRepairDetailsVO
	 */
	ULDDamageRepairDetailsVO findULDDamageRepairDetails(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException,
			RemoteException;
	/**
	 * 
	 * 	Method		:	ULDDefaultsBI.updateMultipleULDDamageDetails
	 *	Added by 	:	A-8176 on 13-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param uldDamageRepairDetailsVOs
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	public void updateMultipleULDDamageDetails(
			Collection<ULDDamageRepairDetailsVO> uldDamageRepairDetailsVOs)
			throws SystemException, RemoteException;
	
	/**
	 * This method return all pictures associated with a uld damage
	 * Added by A-7636 as part of ICRD-245031
	 * */
	
	public Collection<ULDDamagePictureVO> findULDDamagePictures(ULDDamageFilterVO uldDamageFilterVO)
			throws SystemException, RemoteException;
	/**
	 *
	 * Added for IASCB-63472
	 * @param companyCode
	 * @param airportCode
	 * @param locationCode
	 * @param facilityTypeCode
	 * @return
	 * @throws SystemException
	 *
	 */
	public boolean validateULDAirportLocation(String companyCode, String airportCode, String locationCode, String facilityTypeCode)
			throws SystemException, RemoteException, ULDDefaultsBusinessException;
	/**
	 * @author A-8368
	 * @param companyCode
	 * @param rowCount
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void findUldsForMarkMovement(
			String companyCode, int rowCount)
			throws SystemException, RemoteException;
	/**
	 * Added by A-7900 as part of IASCB-14723
	 * 
	 * @param flightDetailsVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	void updateULDsForOperations(Collection<FlightDetailsVO> flightDetailsVOs)
			throws SystemException, RemoteException, ULDDefaultsBusinessException;


	/**
	 * 	Used for 	:   IASCB-162396 for sending UCMMessage
	 *	Parameters	:	@param Collection<UCMMessageVO> ucmMessageVOs
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ULDDefaultsBusinessException
	 *	Return type	: 	void
	 */
	void generateUCMMessage(Collection<UCMMessageVO> ucmMessageVOs)
			throws SystemException, RemoteException, ULDDefaultsBusinessException;

	/**
	 *
	 * Added by A-9558 as part of IASCB-55163
	 * @param companyCode
	 * @param airportCode
	 * @param locationCode
	 * @param facilityTypeCode
	 * @return
	 * @throws SystemException
	 *
	 */

	/**
	 * 	Method		:	ULDDefaultsBI.findSpecialInstructionsForShipments
	 * 	Used for 	:   IASCB-110115 for sending SCM Reminder notifications
	 *	Parameters	:	@param airports
	 *	Parameters	:	@throws RemoteException 
	 *  Parameters	:	@throws SystemException
	 *  Parameters	:	@throws BusinessException
	 *	Return type	: 	void
	 */
	public void sendSCMReminderNotifications(Collection<String> airports) throws RemoteException, SystemException, BusinessException;
	
	/**
	 * 
	 * 	Method		:	OperationsShipmentBI.findAirportsforSCMJob
	 *	Added on 	:	18-Oct-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode,airportGroup,noOfDays
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	public Collection<String> findAirportsforSCMJob(
			String companyCode,Collection<String> airportGroup,String noOfDays)throws RemoteException, SystemException, PersistenceException;
	
	/**
	 * 
	 * 	Method		:	ULDDefaultsBI.findBestFitULDAgreement
	 *	Added on 	:	19-Apr-2023
	 * 	Used for 	:
	 *	Parameters	:	@param uldTransactionDetailsVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ULDAgreementVO
	 */
	public ULDAgreementVO findBestFitULDAgreement(ULDTransactionDetailsVO uldTransactionDetailsVO) 
			throws RemoteException, SystemException;
	/**
	 * 
	 * 	Method		:	ULDDefaultsBI.updateULDTransactionWithDemurrageDetails
	 *	Added on 	:	20-Apr-2023
	 * 	Used for 	:
	 *	Parameters	:	@param TransactionVO
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void updateULDTransactionWithDemurrageDetails(TransactionVO transactionVO)throws RemoteException, SystemException, BusinessException;
	
}

