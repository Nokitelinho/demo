/*
 * ULDDefaultsServicesEJB.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.services.uld.defaults;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.capacity.booking.CapacityBookingBusinessException;
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
import com.ibsplc.icargo.business.uld.defaults.AgreementDoesNotExistsException;
import com.ibsplc.icargo.business.uld.defaults.CurrencyConversionException;
import com.ibsplc.icargo.business.uld.defaults.DimensionConversionException;
import com.ibsplc.icargo.business.uld.defaults.DiscrepancyAlreadyCaughtException;
import com.ibsplc.icargo.business.uld.defaults.DuplicateManufacturerNumberExistsException;
import com.ibsplc.icargo.business.uld.defaults.InvalidULDFormatException;
import com.ibsplc.icargo.business.uld.defaults.MessageConfigException;
import com.ibsplc.icargo.business.uld.defaults.ULDAgreementExistsForULDTransaction;
import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBI;
import com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBusinessException;
import com.ibsplc.icargo.business.uld.defaults.ULDDoesNotExistsException;
import com.ibsplc.icargo.business.uld.defaults.ULDInOperationException;
import com.ibsplc.icargo.business.uld.defaults.message.UCMInExistsForStationException;
import com.ibsplc.icargo.business.uld.defaults.message.UCMInOutMismatchException;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.AgreementAlreadyExistForThePartyException;
import com.ibsplc.icargo.business.uld.defaults.misc.DefaultFlagAlreadySetException;
import com.ibsplc.icargo.business.uld.defaults.misc.FacilityCodeInUseException;
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
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDInTransactionException;
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
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author 
 * Bean implementation class for Enterprise Bean: ULDDefaultsEJB
 * 
 * @ejb.bean description="ULDDefaultsServices"
 *           display-name="ULDDefaultsServices"
 *           jndi-name="com.ibsplc.icargo.services.uld.defaults.ULDDefaultsServicesHome"
 *           name="ULDDefaultsServices" type="Stateless" view-type="remote"
 *           remote-business-interface="com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBI"
 * 
 * @ejb.transaction type="Supports"
 * 
 */
public class ULDDefaultsServicesEJB extends AbstractFacadeEJB implements 
		ULDDefaultsBI {
 
	private Log log = LogFactory.getLogger("ULD MANAGEMENT");

	private static final String MODULE_NAME = "ULDDefaultsServicesEJB";

	/**
	 * This method retrieves the uld details of the specified filter condition
	 * 
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * 
	 */
	public Page<ULDListVO> findULDList(ULDListFilterVO uldListFilterVO,
			int pageNumber) throws SystemException, RemoteException {
		log.entering(MODULE_NAME, "findULDList");
		return new ULDController().findULDList(uldListFilterVO, pageNumber);
	}

	/**
	 * This method retrieves the stock details of the specified accessory code
	 * 
	 * @param companyCode
	 * @param accessoryCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @return AccessoriesStockConfigVO
	 * @throws SystemException
	 */
	public AccessoriesStockConfigVO findAccessoriesStockDetails( 
			String companyCode, String accessoryCode, String stationCode,
			int airlineIdentifier) throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findAccessoriesStockDetails");
		ULDController uLDController = new ULDController();
		return uLDController.findAccessoriesStockDetails(companyCode,
				accessoryCode, stationCode, airlineIdentifier);
	}

	/**
	 * This method saves the accessory stock details
	 * 
	 * @param accessoriesStockConfigVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveAccessoriesStock(
			AccessoriesStockConfigVO accessoriesStockConfigVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "saveAccessoriesStock");
		ULDController uLDController = new ULDController();
		uLDController.saveAccessoriesStock(accessoriesStockConfigVO);
		log.exiting("ULDDefaultsServicesEJB", "saveAccessoriesStock");
	}

	/**
	 * This method validates the format of the specified ULD
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	public boolean validateULDFormat(String companyCode, String uldNumber)
			throws SystemException, ULDDefaultsBusinessException,
			RemoteException {
		try {
			log.entering("INSIDE THE SERVICES EJB", "validateULDFormat");
			return new ULDController()
					.validateULDFormat(companyCode, uldNumber);
		} catch (InvalidULDFormatException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}
	}

	
	/**
	 * This method retrieves the uld movement history
	 * 
	 * @param uldMovementFilterVO
	 * @return Collection<ULDMovementDetailVO>
	 * @throws SystemException
	 */
	public Map printExternalMovementsReport(ReportSpec reportSpec)
				throws ULDDefaultsBusinessException, RemoteException , SystemException{
		try {
			log.entering("INSIDE THE SERVICES EJB", "validateULDFormat");
			return new ULDController()
					.printExternalMovementsReport(reportSpec);
		} catch (ULDDefaultsBusinessException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}
	
	}
	/**
	 * This method retrieves the details of the specified ULD
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public ULDVO findULDDetails(String companyCode, String uldNumber)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering(MODULE_NAME, "findULDDetails");
		log.entering(MODULE_NAME, "findULDDetails");
		return new ULDController().findULDDetails(companyCode, uldNumber);
	}

	/**
	 * This method saves the uld details
	 * 
	 * @param uldVo
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	public void saveULD(ULDVO uldVo) throws SystemException,
			ULDDefaultsBusinessException, RemoteException {
		try {
			new ULDController().saveULD(uldVo);
		} catch (DuplicateManufacturerNumberExistsException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (ULDInTransactionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (DimensionConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}

	}

	/**
	 * This method creates multiple ULDs with the charecteristics specified in
	 * uldVo
	 * 
	 * @param uldVo
	 * @param uldNumberVos
	 * @return Collection<String>
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	public Collection<String> createMultipleULDs(ULDVO uldVo,
			Collection<String> uldNumberVos) throws SystemException,
			RemoteException, ULDDefaultsBusinessException {
		try {
			return new ULDController().createMultipleULDs(uldVo, uldNumberVos);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (DimensionConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}
	}

	/**
	 * This method retrieves the structural details defined at the ULD Type
	 * specified for the ULD
	 * 
	 * @param companyCode
	 * @param uldType
	 * @return ULDTypeVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public ULDTypeVO findULDTypeStructuralDetails(String companyCode,
			String uldType) throws SystemException, RemoteException {
		log.entering("INSIDE THE EJB", "findULDTypeStructuralDetails");
		return new ULDController().findULDTypeStructuralDetails(companyCode,
				uldType);

	}

	/**
	 * This method is used to list the details of the specified ULD Group
	 * 
	 * @param companyCode
	 * @param uldGroupCode
	 * @return ULDGroupVO
	 * @throws SystemException
	 */
	// public ULDGroupVO findULDGroupDetails(
	// String companyCode, String uldGroupCode)
	// throws SystemException {
	// return null;
	// }
	/**
	 * This method is used to create a uld group
	 * 
	 * @param uldGroupVO
	 * @throws SystemException
	 */
	// public void createULDGroup(ULDGroupVO uldGroupVO)
	// throws SystemException {
	// }
	/**
	 * This method is used to modify a uld group and to update the selected ULD
	 * types with the changes
	 * 
	 * @param uldGroupVO
	 * @param Collection
	 *            <String> uldtypes
	 * @throws SystemException
	 */
	// public void modifyULDGroup(ULDGroupVO uldGroupVO, Collection uldTypes)
	// throws SystemException {
	// }
	




	/**
	 * This method retrieves the stock details of the specified accessory code
	 * 
	 * @param companyCode
	 * @param accessoryCode
	 * @return AccessoriesStockConfigVO
	 * @throws SystemException
	 */
	public AccessoriesStockConfigVO findAccessoriesStockDetails(
			String companyCode, String accessoryCode) throws SystemException,
			RemoteException {
		return null;
	}

	/**
	 * This method is used to find the ULDType details
	 * 
	 * @param String
	 *            companyCode
	 * @param String
	 *            uldTypeCode
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	// public ULDTypeVO findULDType(String companyCode,
	// String uldTypeCode)
	// throws RemoteException,SystemException{
	// return null;
	// }
	/**
	 * This method is used to save the ULDType details based on VO.
	 * 
	 * @param ULDTypeVO
	 *            uldTypeVO
	 * @return
	 * @throws SystemException
	 */

	// public void saveULDType(ULDTypeVO uldTypeVO)
	// throws RemoteException,SystemException{
	//
	// }
	/**
	 * This method is used to delete the ULDType details based on filter.
	 * 
	 * @param companyCode
	 * @param uldTypeCode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void deleteULDType(String companyCode, String uldTypeCode)
			throws RemoteException, SystemException {

	}

	/**
	 * This method is used to retrieve the ULDs associated to a Company and
	 * ULDType.
	 * 
	 * @param companyCode
	 * @param uldTypeCode
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<String> findAssociatedULDs(String companyCode,
			String uldTypeCode) throws SystemException, RemoteException {
		return null;
	}

	/**
	 * This method is used to save the ULDType template for a collection of ULDs
	 * Specified.
	 * 
	 * @param ULDTypeVO
	 *            uldTypeVO
	 * @param Collection
	 *            <String>
	 * @return
	 * @throws SystemException
	 */
	// To be reviewed Collection<String>
	// public void modifyULDType(ULDTypeVO uldTypeVO,Collection ulds)
	// throws SystemException,RemoteException{
	//
	// }
	/**
	 * This method is used to populate the ULDGroups associated to a Company in
	 * the system.
	 * 
	 * @param companyCode
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<String> populateULDGroupLOV(String companyCode)
			throws SystemException, RemoteException {
		return null;

	}




	/**
	 * 
	 * @param companyCode
	 * @param agreementNumber
	 * @return ULDAgreementVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public ULDAgreementVO findULDAgreementDetails(String companyCode,
			String agreementNumber) throws RemoteException, SystemException {
		log.entering("ULDDefaultsServicesEJB", "findULDAgreementDetails");
		return new ULDController().findULDAgreementDetails(companyCode,
				agreementNumber);
	}

	//Added by A-8445 as a part of IASCB-28460 Starts
	/**
	 * This method is used for listing uld agreement in the system
	 * 
	 * @param uldAgreementFilterVO
	 * @return Page<ULDAgreementDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<ULDAgreementDetailsVO> findULDAgreementDetailsPagination(String companyCode,
			String agreementNumber, ULDAgreementFilterVO uldAgreementFilterVO) throws RemoteException,
			SystemException {
		log.entering("ULDDefaultsServicesEJB", "findULDAgreementDetailsPagination");
		return new ULDController().findULDAgreementDetailsPagination(companyCode,
				agreementNumber,uldAgreementFilterVO);
	}
	//Added by A-8445 as a part of IASCB-28460 Ends
	
	/**
	 * 
	 * @param uldAgreementVO
	 * @return String
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	public String createULDAgreement(ULDAgreementVO uldAgreementVO)
			throws RemoteException, SystemException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaultsServicesEJB", "createULDAgreement");
		try {
			return new ULDController().createULDAgreement(uldAgreementVO);
		} catch (AgreementAlreadyExistForThePartyException e) {
			throw new ULDDefaultsBusinessException(e);
		} catch (ULDAgreementExistsForULDTransaction e) {
			throw new ULDDefaultsBusinessException(e);
		}
	}



	/**
	 * This method is used for listing uld agreement in the system
	 * 
	 * @param uldAgreementFilterVO
	 * @return Page<ULDAgreementVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<ULDAgreementVO> listULDAgreements(
			ULDAgreementFilterVO uldAgreementFilterVO) throws RemoteException,
			SystemException {
		log.entering("ULDDefaultsServicesEJB", "listULDAgreements");
		return new ULDController().listULDAgreements(uldAgreementFilterVO);
	}

	/**
	 * This method is used for populating the ULD Agreements
	 * 
	 * @param companyCode
	 * @param pageNumber
	 * @return Page<Strings>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page populateULDAgreementLOV(ULDAgreementFilterVO uldAgreementFilterVO)
	throws RemoteException, SystemException {
		log.entering("ULDDefaultsservicesEJB", "populateULDAgreementLOV");
		return new ULDController().populateULDAgreementLOV(uldAgreementFilterVO);
	}

	/**
	 * This method is used to update the status of ULD Agreement to the changed
	 * status.ie Active to Inactive and ViceVeraa
	 * 
	 * @param companyCode
	 * @param agreementNumbers
	 * @param changedStatus
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException 
	 */
	/*
	 * public void updateULDAgreementStatus( String companyCode, Collection<String>
	 * agreementNumbers, String changedStatus) throws
	 * RemoteException,SystemException{
	 * log.entering("ULDDefaultsservicesEJB","updateULDAgreementStatus"); new
	 * ULDController().updateULDAgreementStatus(companyCode , agreementNumbers ,
	 * changedStatus); }
	 */

	public void updateULDAgreementStatus(Collection<ULDAgreementVO> uldAgrVOs,
			String changedStatus) throws RemoteException, SystemException, ULDDefaultsBusinessException {
		log.entering("ULDDefaultsservicesEJB", "updateULDAgreementStatus");
		try {
			new ULDController().updateULDAgreementStatus(uldAgrVOs, changedStatus);
		} catch (AgreementAlreadyExistForThePartyException e) {
			throw new ULDDefaultsBusinessException(e);
		}
	}

	/**
	 * This method is used to create modify and delete ULDStockConfiguration
	 * 
	 * @param uLDStockConfigVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveULDStockConfig(
			Collection<ULDStockConfigVO> uLDStockConfigVOs)
			throws RemoteException, SystemException {
		log.entering("ULDDefaultsservicesEJB", "saveULDStockConfig");
		new ULDController().saveULDStockConfig(uLDStockConfigVOs);
	}

	/**
	 * @param uldStockConfigFilterVO
	 * @return Collection
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<ULDStockConfigVO> listULDStockConfig(
			ULDStockConfigFilterVO uldStockConfigFilterVO)
			throws RemoteException, SystemException {
		log.entering("ULDDefaultsservicesEJB", "listULDStockConfig");
		return new ULDController().listULDStockConfig(uldStockConfigFilterVO);
	}


	/**
	 * 
	 * 
	 * @param companyCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @return Collection
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<String> findStationUlds(String companyCode, String uldNumber,
			String stationCode,String transactionType, int airlineIdentifier, int displayPage)
			throws RemoteException, SystemException {
		log.entering("INSIDE THE SERVICES EJB", "findStationUlds");
		return new ULDController().findStationUlds(companyCode, uldNumber,
				stationCode, transactionType, airlineIdentifier, displayPage);
	}



	/**
	 * This method is used to save ULDs and Accessories participating in a
	 * Loan/Borrow Transaction
	 * 
	 * @param transactionVO
	 * @throws RemoteException
	 * @return
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 * 
	 */

	public Collection<ErrorVO> saveULDTransaction(TransactionVO transactionVO)
			throws RemoteException, SystemException,
			ULDDefaultsBusinessException,MessageConfigException {
		log.entering("INSIDE THE SERVICES EJB", "saveULDTransaction");
		log.entering("INSIDE THE SERVICES EJB", "saveULDTransaction");
		try {
			return new ULDController().saveULDTransaction(transactionVO);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (DimensionConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}

	}



	/**
	 * This method is used for listing uld transaction
	 * 
	 * @author A-1883
	 * @param uldTransactionFilterVO
	 * @return TransactionListVO
	 * @throws SystemException
	 * @throws RemoteException
	 */

	public TransactionListVO listULDTransactionDetails(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException,
			RemoteException {
		log.entering("INSIDE THE SERVICES EJB", "listULDTransactionDetails");
		return new ULDController()
				.listULDTransactionDetails(uldTransactionFilterVO);
	}

	/**
	 * 
	 * @param uldTransactionFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public TransactionListVO findULDTransactionDetailsCol(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException,
			RemoteException {
		return new ULDController()
				.findULDTransactionDetailsCol(uldTransactionFilterVO);
	}

	


	
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
			RemoteException {
		return new ULDController().findTotalDemmurage(transactionFilterVO);
	}


	
	/**
	 * This method is used for returning the ULD's after Calculating the
	 * Demurrage Charges Accrued against the UDLds
	 * @author a-3278
	 * @param uldTransactionDetailsVOs
	 * @return Collection<ULDTransactionDetailsVO>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Collection<ULDTransactionDetailsVO> calculateULDDemmurage(
			Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("INSIDE THE EJB", "calculateULDDemmurage");
		try {
			return new ULDController()
					.calculateULDDemmurage(uldTransactionDetailsVOs);
		} catch (AgreementDoesNotExistsException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}
	}

	/**
	 * This method is used for listing uld Invoice
	 * 
	 * @param chargingInvoiceFilterVO
	 * @param pageNumber
	 * @return Page<ULDChargingInvoiceVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ULDChargingInvoiceVO> listULDChargingInvoice(
			ChargingInvoiceFilterVO chargingInvoiceFilterVO, int pageNumber)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "listULDChargingInvoice");
		return new ULDController().listULDChargingInvoice(
				chargingInvoiceFilterVO, pageNumber);
	}

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
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "viewULDChargingInvoiceDetails");
		return new ULDController().viewULDChargingInvoiceDetails(companyCode,
				invoiceRefNumber);
	}

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
			throws SystemException, RemoteException {
		log
				.entering("ULDDefaultsServicesEJB",
						"generateInvoiceForReturnedUlds");
		return new ULDController().generateInvoiceForReturnedUlds(
				uldChargingInvoiceVO, uldTransactionDetailsVOs);
	}

	/**
	 * @param transactionListVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 * @return
	 */
	public void saveReturnTransaction(TransactionListVO transactionListVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException,
			CurrencyConversionException,DimensionConversionException {
		log.entering("INSIDE THE EJB", "saveReturnTransaction");
			new ULDController().saveReturnTransactionFeature(transactionListVO);
		}


	/**
	 * @param uldDamageFilterVO
	 * @return Collection<ULDDamageRepairDetailsVO>
	 * @throws SystemException
	 */
	public Page<ULDDamageDetailsListVO> findULDDamageList(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findULDDamageList");
		return new ULDController().findULDDamageList(uldDamageFilterVO);
	}

	/**
	 * @param uldDamageDeleteVOs
	 * @return
	 * @throws SystemException
	 */
	public void deleteULDDamages(
			Collection<ULDDamageDeleteVO> uldDamageDeleteVOs)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "deleteULDDamages");
		new ULDController().deleteULDDamages(uldDamageDeleteVOs);
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param pageNumber
	 * @return Page
	 * @throws SystemException
	 */
	public Page<ULDDamageReferenceNumberLovVO> findULDDamageReferenceNumberLov(
			String companyCode, String uldNumber, int pageNumber)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB",
				"findULDDamageReferenceNumberLov");
		return new ULDController().findULDDamageReferenceNumberLov(companyCode,
				uldNumber, pageNumber);
	}

	/**
	 * @param uldRepairFilterVO
	 * @return Page
	 * @throws SystemException
	 */
	public Page<ULDRepairDetailsListVO> listULDRepairDetails(
			ULDRepairFilterVO uldRepairFilterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findULDDamageList");
		return new ULDController().listULDRepairDetails(uldRepairFilterVO);
	}

	/**
	 * @param accessoriesStockConfigFilterVO
	 * @param displayPage
	 * @return Page<AccessoriesStockConfigVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<AccessoriesStockConfigVO> findAccessoryStockList(
			AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO,
			int displayPage) throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findAccessoryStockList");
		ULDController uldController = new ULDController();
		return uldController.findAccessoryStockList(
				accessoriesStockConfigFilterVO, displayPage);
	}

	/**
	 * @param uldDamageFilterVO
	 * @return ULDDamageRepairDetailsVO
	 * @throws SystemException
	 */
	public ULDDamageRepairDetailsVO findULDDamageDetails(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findULDDamageDetails");
		return new ULDController().findULDDamageDetails(uldDamageFilterVO);
	}

	/**
	 * 
	 * @param uldDamageRepairDetailsVO
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	public void saveULDDamage(ULDDamageRepairDetailsVO uldDamageRepairDetailsVO)
			throws SystemException, ULDDefaultsBusinessException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "saveULDDamage");
		try {
			ULDController uldController = (ULDController)SpringAdapter.getInstance().getBean("ULDController");
			uldController.saveULDDamage(uldDamageRepairDetailsVO);
//			new ULDController().saveULDDamage(uldDamageRepairDetailsVO);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}
	}

	/**
	 * @param uldNOs
	 * @param uldMovementVos
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	public void saveULDMovement(Collection<String> uldNOs,
			Collection<ULDMovementVO> uldMovementVos, String screenID, String overrideFlag) throws RemoteException,
			SystemException, ULDDefaultsBusinessException {
		log.entering("INSIDE THE SERVICES EJB", " saveULDMovement");
		try {
			new ULDController().saveULDMovementFromScreen(uldNOs, uldMovementVos, screenID,overrideFlag);
		} catch (ULDDoesNotExistsException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}
	}

	/**
	 * 
	 * @param uldMovementFilterVO
	 * @param displayPage
	 * @return Page<ULDMovementDetailVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ULDMovementDetailVO> findULDMovementHistory(
			ULDMovementFilterVO uldMovementFilterVO, int displayPage)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findULDMovementHistory");
		ULDController uldController = new ULDController();
		return uldController.findULDMovementHistory(uldMovementFilterVO,
				displayPage);
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return ULDValidationVO
	 * @throws SystemException
	 */
	public ULDValidationVO findCurrentULDDetails(String companyCode,
			String uldNumber) throws SystemException, RemoteException {
		log.entering("ULDDefaultsDelegate", "findCurrentULDDetails");
		ULDController uldController = new ULDController();
		return uldController.findCurrentULDDetails(companyCode, uldNumber);
	}

	/**
	 * @param uldVos
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	public void saveULDs(Collection<ULDVO> uldVos) throws SystemException,
			ULDDefaultsBusinessException, RemoteException {
		try {
			new ULDController().saveULDs(uldVos);
		} catch (DuplicateManufacturerNumberExistsException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (ULDInTransactionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (DimensionConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}
	}

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
	public Page<String> findInvoiceRefNumberLov(String companyCode,
			int displayPage, String invRefNo) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findInvoiceRefNumberLov");
		return new ULDController().findInvoiceRefNumberLov(companyCode,
				displayPage, invRefNo);
	}

	/**
	 * @author A-1883
	 * @param companyCode
	 * @param uldNumbers
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<String> validateMultipleULDFormats(String companyCode,
			Collection<String> uldNumbers) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "validateMultipleULDFormats");
		return new ULDController().validateMultipleULDFormats(companyCode,
				uldNumbers);
	}

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
	public ULDRepairInvoiceVO findRepairInvoiceDetails(String companyCode,
			String invoiceRefNumber) throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findRepairInvoiceDetails");
		return new ULDController().findRepairInvoiceDetails(companyCode,
				invoiceRefNumber);
	}

	/**
	 * This method is used to update ULD Repair InvoiceDetails (waived amount
	 * and remarks)
	 * 
	 * @param uLDRepairInvoiceDetailsVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void updateULDRepairInvoiceDetails(
			Collection<ULDRepairInvoiceDetailsVO> uLDRepairInvoiceDetailsVOs)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "updateULDRepairInvoiceDetails");
		new ULDController()
				.updateULDRepairInvoiceDetails(uLDRepairInvoiceDetailsVOs);
		log.exiting("ULDDefaultsServicesEJB", "updateULDRepairInvoiceDetails");
	}

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
	public Page<ULDStockListVO> findULDStockList(
			ULDStockConfigFilterVO uLDStockConfigFilterVO, int displayPage)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findULDStockList");
		return new ULDController().findULDStockList(uLDStockConfigFilterVO,
				displayPage);
	}

	/**
	 * This method is used to find the available ulds at the station which can
	 * be loaned.
	 * 
	 * @param companyCode
	 * @param transactionStation
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<String> findAvailableULDsAtStation(String companyCode,
			String transactionStation) throws SystemException, RemoteException {
		return null;
	}

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
	public ULDValidationVO validateULD(String companyCode, String uldNumber)
			throws SystemException, RemoteException {
		log.entering("Services EJB", "validateULD");
		return new ULDController().validateULD(companyCode, uldNumber);
	}

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
			ULDDefaultsBusinessException {
		try {
			return new ULDController()
					.findAgreementNumberForTransaction(uldTransactionDetailsVos);
		} catch (AgreementDoesNotExistsException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}
	}

	/**
	 * This method saves multiple accessory stock details
	 * 
	 * @param accessoriesStockConfigVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveAccessories(
			Collection<AccessoriesStockConfigVO> accessoriesStockConfigVOs)
			throws SystemException, RemoteException {
		log.entering("Services EJB", "saveAccessories");
		new ULDController().saveAccessories(accessoriesStockConfigVOs);
		log.exiting("Services EJB", "saveAccessories");
	}

	/**
	 * @param uldDiscrepancyVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 * @return
	 */
	public String saveULDDiscrepencyDetails(
			Collection<ULDDiscrepancyVO> uldDiscrepancyVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaultsServicesEJB", "saveULDDiscrepencyDetails");
		try {
			return new ULDController()
					.saveULDDiscrepencyDetails(uldDiscrepancyVOs);
		} catch (DiscrepancyAlreadyCaughtException discrepancyAlreadyCaughtException) {
			throw new ULDDefaultsBusinessException(
					discrepancyAlreadyCaughtException);
		}
	}

	


	/**
	 * 
	 * @param lucMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public Collection<MessageVO> generateLUCMessage(LUCMessageVO lucMessageVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaultsServicesEJB", "generateLUCMessage");
		try {
			return new ULDController().generateLUCMessage(lucMessageVO);
		} catch (MessageConfigException messageConfigException) {
			throw new ULDDefaultsBusinessException(messageConfigException);
		}
	}


	/**
	 * 
	 * @param discrepancyFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ULDDiscrepancyVO> listULDDiscrepancyDetails(
			ULDDiscrepancyFilterVO discrepancyFilterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "listULDDiscrepancyDetails");
		return new ULDController()
				.listUldDiscrepancyDetails(discrepancyFilterVO);
	}



	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public void processSCMMessage(ULDSCMReconcileVO reconcileVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaultsServicesEJB", "processSCMMessage");
		try {
			new ULDController().processSCMMessage(reconcileVO);
		} catch (MessageConfigException messageConfigException) {
			throw new ULDDefaultsBusinessException(messageConfigException);
		}
		catch (CurrencyConversionException currencyConversionException) {
			throw new ULDDefaultsBusinessException(currencyConversionException);
		}catch(DimensionConversionException dimensionConversionException){
			throw new ULDDefaultsBusinessException(dimensionConversionException);
		}
	}

	/**
	 * Modified by A-5165 for ICRD-262166- returns collection of errors for warning
	 * @param reconcileVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public Collection<ErrorVO> processUCMMessage(
			Collection<ULDFlightMessageReconcileVO> reconcileVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		// Added by A-5165 for ICRD-262166- returns collection of errors for warning		 
		Collection<ErrorVO> errors=null;
		log.entering("ULDDefaultsServicesEJB", "processSCMMessage");
		try {
			//new ULDController().processUCMMessage(reconcileVOs);
			ULDController uldController = (ULDController)SpringAdapter.getInstance().getBean("ULDController");
			errors=uldController.processUCMMessage(reconcileVOs);
			
			//((ULDDefaultsBI)SpringAdapter.getInstance().getBean("ULDController")).processUCMMessage(reconcileVOs);
		
		} catch (UCMInExistsForStationException e) {
			throw new ULDDefaultsBusinessException(e);
		} catch (CurrencyConversionException e) {
			throw new ULDDefaultsBusinessException(e);
		}
		 catch (DimensionConversionException e) {
				throw new ULDDefaultsBusinessException(e);
			}
		 catch (AgreementDoesNotExistsException e) {
				throw new ULDDefaultsBusinessException(e);
			}
		 catch (MessageConfigException e) {
				throw new ULDDefaultsBusinessException(e);
			}
		return errors;
	}

	/**
	 * 
	 * @param mucMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 * @return
	 */
	public void processMUCMessage(MUCMessageVO mucMessageVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException,
			CurrencyConversionException, DimensionConversionException {
		log.entering("ULDDefaultsServicesEJB", "processMUCMessage");
		try {
			new ULDController().processMUCMessage(mucMessageVO);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (MessageConfigException messageConfigException) {
			throw new ULDDefaultsBusinessException(messageConfigException);
		}catch (DimensionConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} 
	}

	/**
	 * 
	 * @param companyCode
	 * @param airportCode
	 * @return HashMap<String,Collection<String>>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public HashMap<String, Collection<String>> populateLocation(
			String companyCode, String airportCode) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "populateLocation");
		return new ULDController().populateLocation(companyCode, airportCode);
	}

	/**
	 * 
	 * @param uldVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * 
	 */
	public void setLocationForULD(Collection<ULDVO> uldVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaultsServicesEJB", "setLocationForULD");
		new ULDController().setLocationForULD(uldVOs);
	}

	
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public ULDDamagePictureVO findULDDamagePicture(String companyCode,
			String uldNumber, long sequenceNumber,int imageSequenceNumber) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findULDDamagePicture");
		return new ULDController().findULDDamagePicture(companyCode, uldNumber,
				sequenceNumber,imageSequenceNumber);
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public int findOperationalAirlineForULD(String companyCode, String uldNumber)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findOperationalAirlineForULD");
		return new ULDController().findOperationalAirlineForULD(companyCode,
				uldNumber);
	}

	


	/**
	 * 
	 * @param cpmMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public void saveCPMDetails(CPMMessageVO cpmMessageVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "saveCPMDetails");
		new ULDController().saveCPMDetails(cpmMessageVO);
		log.exiting("ULDDefaultsServicesEJB", "saveCPMDetails");
	}

	

	/**
	 * @param flightDetailsVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public void validateULDsForOperation(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaultsServicesEJB", "validateULDsForOperation");
		try {
			new ULDController().validateULDsForOperation(flightDetailsVO);
		} catch (ULDDoesNotExistsException e) {
			throw new ULDDefaultsBusinessException(e);
		}
	}




	/**
	 * 
	 * @param locationVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 * @return
	 */
	public void saveULDAirportLocation(
			Collection<ULDAirportLocationVO> locationVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaultsServicesEJB", "saveULDAirportLocation");
		try {
			new ULDController().saveULDAirportLocation(locationVOs);
		} catch (DefaultFlagAlreadySetException e) {
			throw new ULDDefaultsBusinessException(e);
		} catch (FacilityCodeInUseException e) {
			throw new ULDDefaultsBusinessException(e);
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param airportCode
	 * @param facilityType
	 * @return Collection<ULDAirportLocationVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ULDAirportLocationVO> listULDAirportLocation(
			String companyCode, String airportCode, String facilityType)
			throws SystemException, RemoteException {
		log.entering("ULDDefaultsServicesEJB", "listULDAirportLocation");
		return new ULDController().listULDAirportLocation(companyCode,
				airportCode, facilityType);
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param carrierCode
	 * @return
	 * @throws SystemException 
	 * @throws RemoteException 
	 */
	public AirlineValidationVO populateAirlineCodes(String companyCode,
			String carrierCode)  
	throws SystemException, RemoteException {  
		log.entering("ULDDefaultsServicesEJB", "populateAirlineCodes");  
		return new ULDController().populateAirlineCodes(companyCode,
				carrierCode);
	}


	/**
	 * This method validates the flight to check if the flight exists and
	 * returns the flight specific details.
	 * 
	 * @param flightFilterVo
	 * @return Collection<FlightValidationVO>
	 * @throws SystemException
	 * @throws ProxyException
	 * 
	 */
	public Collection<FlightValidationVO> fetchFlightDetails(
			FlightFilterVO flightFilterVo) throws SystemException,
			RemoteException {
		return new ULDController().fetchFlightDetails(flightFilterVo);
	}

	/**
	 * 
	 * @param reconcileVOs
	 * @return Collection<ULDFlightMessageReconcileVO>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Collection<ULDFlightMessageReconcileVO> saveULDFlightMessageReconcile(
			Collection<ULDFlightMessageReconcileVO> reconcileVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaulstServicesEJB", "saveULDFlightMessageReconcile");
		try {
			return new ULDController()
					.saveULDFlightMessageReconcile(reconcileVOs);
		} catch (UCMInExistsForStationException e) {
			throw new ULDDefaultsBusinessException(e);
		}
	}

	/**
	 * 
	 * @param filterVO
	 * @return ULDFlightMessageReconcileVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public ULDFlightMessageReconcileVO listUCMMessage(
			FlightFilterMessageVO filterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaulstServicesEJB", "listUCMMessage");
		return new ULDController().listUCMMessage(filterVO);
	}

	/**
	 * 
	 * @param filterVO
	 * @return Page<ULDFlightMessageReconcileVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ULDFlightMessageReconcileVO> listUCMErrors(
			FlightFilterMessageVO filterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaulstServicesEJB", "listUCMErrors");
		return new ULDController().listUCMErrors(filterVO);
	}

	/**
	 * @author A-1950
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<String> findUCMNoLOV(FlightFilterMessageVO filterVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "findUCMNoLOV");
		return new ULDController().findUCMNoLOV(filterVO);
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ULDFlightMessageReconcileDetailsVO> listUldErrors(
			FlightFilterMessageVO filterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaulstServicesEJB", "listUldErrors");
		return new ULDController().listUldErrors(filterVO);
	}

	/**
	 * @author A-1950
	 * 
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ULDFlightMessageReconcileVO> listUCMsForComparison(
			FlightFilterMessageVO flightFilterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaulstServicesEJB", "listUCMsForComparison");
		return new ULDController().listUCMsForComparison(flightFilterVO);
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ULDFlightMessageReconcileDetailsVO> listUCMINOUTMessage(
			FlightFilterMessageVO filterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaulstServicesEJB", "listUCMINOUTMessage");
		return new ULDController().listUCMINOUTMessage(filterVO);
	}

	/**
	 * @author A-1950
	 * @param uldReconcileDetailsVO
	 * @return String
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String reconcileUCMULDError(
			ULDFlightMessageReconcileDetailsVO uldReconcileDetailsVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "reconcileUCMULDError");
		return new ULDController().reconcileUCMULDError(uldReconcileDetailsVO);
	}

	/**
	 * 
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Collection<ULDFlightMessageReconcileDetailsVO> listUCMOUTForInOutMismatch(
			FlightFilterMessageVO flightFilterVO) throws SystemException,
			RemoteException, ULDDefaultsBusinessException {
		log.entering("ULDDefaulstServicesEJB", "listUCMOUTForInOutMismatch");
		try {
			return new ULDController()
					.listUCMOUTForInOutMismatch(flightFilterVO);
		} catch (UCMInOutMismatchException e) {
			throw new ULDDefaultsBusinessException(e);
		}
	}

	/**
	 * @author A-1950
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String findULDCurrentStation(String companyCode, String uldNumber)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "findULDCurrentStation");
		return new ULDController()
				.findULDCurrentStation(companyCode, uldNumber);
	}

	/**
	 * @author A-1950
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void updateULDTransitStatus(String companyCode, String uldNumber)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "updateULDTransitStatus");
		new ULDController().updateULDTransitStatus(companyCode, uldNumber);
	}



	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ULDVO> findUldDetailsForSCM(SCMMessageFilterVO filterVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "getUldDetailsForSCM");
		return new ULDController().findUldDetailsForSCM(filterVO);
	}

	/**
	 * 
	 * @param reconcileVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public HashMap<String, Collection<MessageVO>> saveSCMMessage(
			Collection<ULDSCMReconcileVO> reconcileVOs, ULDListFilterVO uldListFilterVO, Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs) throws SystemException,
			RemoteException, ULDDefaultsBusinessException {
		log.entering("ULDDefaulstServicesEJB", "saveSCMMessage");
		try {
			try {
				return new ULDController().saveSCMMessage(reconcileVOs, uldListFilterVO,additionaldespatchDetailsVOs);
			} catch (ULDDoesNotExistsException uldDoesNotExistException) {
				throw new ULDDefaultsBusinessException(uldDoesNotExistException);
			} 
		} catch (MessageConfigException messageConfigException) {
			throw new ULDDefaultsBusinessException(messageConfigException);
		}
	}
	
	public String saveSCMReconcialtionDetails(Collection<ULDSCMReconcileVO> reconcileVOs) throws SystemException,
			RemoteException, ULDDefaultsBusinessException {
		log.entering("ULDDefaulstServicesEJB", "saveSCMReconcialtionDetails");
		try {
			try {
				return new ULDController().saveSCMReconcialtionDetails(reconcileVOs);
			} catch (ULDDoesNotExistsException uldDoesNotExistException) {
				throw new ULDDefaultsBusinessException(uldDoesNotExistException);
			}
		} catch (MessageConfigException messageConfigException) {
			throw new ULDDefaultsBusinessException(messageConfigException);
		}
		
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ULDSCMReconcileVO> listSCMMessage(SCMMessageFilterVO filterVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "listSCMMessage");
		return new ULDController().listSCMMessage(filterVO);
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ULDSCMReconcileDetailsVO> listULDErrorsInSCM(
			SCMMessageFilterVO filterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaulstServicesEJB", "listULDErrorsInSCM");
		return new ULDController().listULDErrorsInSCM(filterVO);
	}

	/**
	 * 
	 * @param flightMessageReconcileVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public void sendUCM(ULDFlightMessageReconcileVO flightMessageReconcileVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "sendUCM");
		new ULDController().sendUCM(flightMessageReconcileVO);

	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String findCurrentAirportForULD(SCMMessageFilterVO filterVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "findCurrentAirportForULD");
		return new ULDController().findCurrentAirportForULD(filterVO);
	}



	/**
	 * 
	 * @param uldDetails
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void updateUCMULDDetails(
			Collection<ULDFlightMessageReconcileDetailsVO> uldDetails)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "updateUCMULDDetails");
		new ULDController().updateUCMULDDetails(uldDetails);
	}

	/**
	 * 
	 * @param uldDetails
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public void removeErrorCodeForULDsInSCM(
			Collection<ULDSCMReconcileDetailsVO> uldDetails)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "removeErrorCodeForULDsInSCM");
		new ULDController().removeErrorCodeForULDsInSCM(uldDetails);
	}

	/**
	 * 
	 * @param filterVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public Collection<MessageVO> sendSCMMessage(SCMMessageFilterVO filterVO, Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaulstServicesEJB", "sendSCMMessage");
		try {
			return new ULDController().sendSCMMessage(filterVO,additionaldespatchDetailsVOs);
		} catch (MessageConfigException messageConfigException) {
			throw new ULDDefaultsBusinessException(messageConfigException);
		}
	}

	/**
	 * 
	 * @param uldDetails
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public void deleteULDsInSCM(Collection<ULDSCMReconcileDetailsVO> uldDetails)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "deleteULDsInSCM");
		new ULDController().deleteULDsInSCM(uldDetails);
	}

	/**
	 * 
	 * @param poolOwnerVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public void saveULDPoolOwner(Collection<ULDPoolOwnerVO> poolOwnerVOs)
			throws SystemException, RemoteException,ULDDefaultsBusinessException {
		log.entering("ULDDefaulstServicesEJB", "saveULDPoolOwner");
		new ULDController().saveULDPoolOwner(poolOwnerVOs);
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ULDPoolOwnerVO> listULDPoolOwner(ULDPoolOwnerFilterVO filterVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "listULDPoolOwner");
		return new ULDController().listULDPoolOwner(filterVO);
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ULDSCMReconcileVO> listSCMMessageLOV(SCMMessageFilterVO filterVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "listSCMMessageLOV");
		return new ULDController().listSCMMessageLOV(filterVO);
	}



	/**
	 * @author A-1950
	 * @param lucMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 * @return
	 */
	public void processLUCMessage(LUCMessageVO lucMessageVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaulstServicesEJB", "processLUCMessage");
		try {
			new ULDController().processLUCMessage(lucMessageVO);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (DimensionConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}catch(MessageConfigException ex){
			throw new ULDDefaultsBusinessException(ex);
		}catch(AgreementDoesNotExistsException ex){
			throw new ULDDefaultsBusinessException(ex);
		} catch (BusinessException e) {
			throw new ULDDefaultsBusinessException(e);
		}
	}

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param airlineIdentifier
	 * @param uldIdentifier
	 * @param date
	 * @throws SystemException
	 * @throws RemoteException
	 * @return
	 */
	public void generateSCMFromMonitorULD(String companyCode,
			ULDStockListVO uLDStockListVO) throws SystemException,
			RemoteException, ULDDefaultsBusinessException {
		log.entering("ULDDefaulstServicesEJB", "processLUCMessage");
		try {
			new ULDController().generateSCMFromMonitorULD(companyCode,
					uLDStockListVO);
		} catch (MessageConfigException messageConfigException) {
			throw new ULDDefaultsBusinessException(messageConfigException);
		}
	}


	/**
	 * 
	 * A-1950
	 * 
	 * @param uldValidationVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public void validateULDForWarehouseOccupancy(
			Collection<ULDValidationVO> uldValidationVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		try {
			new ULDController()
					.validateULDForWarehouseOccupancy(uldValidationVOs);
		} catch (ULDInOperationException uldInOperationException) {
			throw new ULDDefaultsBusinessException(uldInOperationException);
		}
	}

	/**
	 * 
	 * A-1950
	 * 
	 * @param uldValidationVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public void checkForULDInOperation(
			Collection<ULDValidationVO> uldValidationVOs)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		try {
			new ULDController().checkForULDInOperation(uldValidationVOs);
		} catch (ULDInOperationException uldInOperationException) {
			throw new ULDDefaultsBusinessException(uldInOperationException);
		}
	}



	/**
	 * 
	 * A-1950
	 * 
	 * @param flightDetailsVO
	 * @throws SystemException
	 */
	/*
	public void updateULDForFlightFinalisation(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException {
		log
				.entering("ULDDefaulstServicesEJB",
						"updateULDForFlightFinalisation");
		new ULDController().updateULDForFlightFinalisation(flightDetailsVO);
	}*/

	/**
	 * 
	 * A-1950
	 * 
	 * @param flightDetailsVO
	 * @throws SystemException
	 */
	/*
	public void updateULDForFlightClosure(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "updateULDForFlightClosure");
		new ULDController().updateULDForFlightClosure(flightDetailsVO);
	}
	 */
	/**
	 * 
	 * A-1950
	 * 
	 * @param flightDetailsVO
	 * @throws SystemException
	 */
	public void updateULDForOperations(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		log.entering("ULDDefaulstServicesEJB", "updateULDForOperations");
		try {
			new ULDController().updateULDForOperations(flightDetailsVO);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (MessageConfigException messageConfigException) {
			throw new ULDDefaultsBusinessException(messageConfigException);
		} catch (DimensionConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		}
	}

	/**
	 * 
	 * A-1950
	 * 
	 * @param flightDetailsVO
	 * @throws SystemException
	 */
	/*
	public void updateULDForOffload(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "updateULDForOffload");
		new ULDController().updateULDForOffload(flightDetailsVO);
	}
	 */



	/**
	 * @author A-1936 This method is used to save the Exception Flights for
	 *         which the UCM has to be sent manually
	 * @param ucmExceptionFlightVos
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveExceptionFlights(
			Collection<UCMExceptionFlightVO> ucmExceptionFlightVos)
			throws SystemException, RemoteException {
		log.entering("ULDEJB", "saveExceptionFlights");
		new ULDController().saveExceptionFlights(ucmExceptionFlightVos);
	}

	/**
	 * @author A-1936 This method is used to find the Exception Flights for
	 *         which the UCM has to be sent manually
	 * @param companyCode
	 * @param pol
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<UCMExceptionFlightVO> findExceptionFlights(
			String companyCode, String pol) throws SystemException,
			RemoteException {
		log.entering("ULDEJB", "findExceptionFlights");
		return new ULDController().findExceptionFlights(companyCode, pol);
	}

	/**
	 * 
	 * A-1950
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public ULDListVO fetchULDDetailsForTransaction(String companyCode,
			String uldNumber) throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "fetchULDDetailsForTransaction");
		return new ULDController().fetchULDDetailsForTransaction(companyCode,
				uldNumber);
	}

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
	public String findCRNForULDTransaction(String companyCode,
			String carrierCode) throws SystemException, RemoteException {
		log.entering("ULDDefaulstServicesEJB", "findCRNForULDTransaction");
		return new ULDController().findCRNForULDTransaction(companyCode,
				carrierCode);
	}

	/**
	 * 
	 * A-1950
	 * 
	 * @param transactionDetails
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public void updateULDForDelivery(
			Collection<ULDTransactionDetailsVO> transactionDetails)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException,MessageConfigException {
		log.entering("ULDDefaulstServicesEJB", "updateULDForDelivery");
		try {
			new ULDController().updateULDForDelivery(transactionDetails);
		} catch (CurrencyConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (DimensionConversionException ex) {
			throw new ULDDefaultsBusinessException(ex);
		} catch (BusinessException e) {
			throw new ULDDefaultsBusinessException(e);
		}
	}

	/**
	 * 
	 * A-1950
	 * 
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void updateULDPrice(String companyCode, int period) throws SystemException,
			RemoteException {
		new ULDController().updateULDPrice(companyCode,period);
	}

	/**
	 * A-1950
	 */
	public void updateULDStatusAsLost(String companyCode, int period)
			throws SystemException, RemoteException {
		new ULDController().updateULDStatusAsLost(companyCode, period);
	}

	/**
	 * 
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printLoanBorrowULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printLoanBorrowULD(reportSpec);
	}
	
	/**
	 * 
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printLoanBorrowULDForPage(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printLoanBorrowULDForPage(reportSpec);
	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printListULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printListULD(reportSpec);
	}
	/**
	 * 
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printUldInventoryList(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printUldInventoryList(reportSpec);
	} 
	
	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printPageListULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printPageListULD(reportSpec);
	}
	
	
	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printMonitorULDStock(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printMonitorULDStock(reportSpec);
	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printMaintainULDAgreement(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printMaintainULDAgreement(reportSpec);
	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printListULDAgreement(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printListULDAgreement(reportSpec);
	}

	
	/** 
	 * @author A-3278
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printuldtransactionReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printULDAvailabilityReport(reportSpec);
	}
	

	

	/**
	 * @param reportSpec
	 * @return reportSpec
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printULDChargingInvoice(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printULDChargingInvoice(reportSpec);
	}

	/**
	 * @param reportSpec
	 * @return reportSpec
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printULDDamageReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printULDDamageReport(reportSpec);
	}

	/**
	 * 
	 * @param deleteULDMovement
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void deleteULDMovements(
			Collection<ULDMovementDetailVO> uldMovementDetails)
			throws SystemException, RemoteException {
		new ULDController().deleteULDMovements(uldMovementDetails);
	}

	// Added by A-2412
	public Map<String, Object> printListUldMovement(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printListUldMovement(reportSpec);
	}

	public Map<String, Object> printMaintainUldStock(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printMaintainUldStock(reportSpec);
	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	/*
	 * public Map<String,Object> printMaintainUldStock(ReportSpec reportSpec)
	 * throws SystemException,RemoteException,ULDDefaultsBusinessException {
	 * return new ULDController().printMaintainUldStock(reportSpec); }
	 */
	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	/*
	 * public Map<String,Object> printListUldMovement(ReportSpec reportSpec)
	 * throws SystemException,RemoteException,ULDDefaultsBusinessException {
	 * return new ULDController().printListUldMovement(reportSpec); }
	 */
	/**
	 * @author A-2619
	 * @return Page This method is used to list the ULD History
	 * @param uldHistoryVO.
	 * 
	 */
	public Page<ULDHistoryVO> listULDHistory(ULDHistoryVO uldHistoryVO)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().listULDHistory(uldHistoryVO);
	}

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
			ULDDefaultsBusinessException {
		return new ULDController().printULDHistory(reportSpec);
	}

	/* Added by A-2412 on 18th Oct for Editable CRN cr */
	public Collection<String> checkForDuplicateCRN(String companyCode,
			TransactionVO transactionVO) throws SystemException,
			RemoteException {
		return new ULDController().checkForDuplicateCRN(companyCode,
				transactionVO);
	}

	/* Addition by A-2412 on 18th Oct for Editable CRN cr ends */

	public Map<String, Object> printUCRLoanBorrowULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printUCRLoanBorrowULD(reportSpec);
	}

	

	/**
	 * This method is for sending MUC
	 * 
	 * @param transactionDetailsVOs
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ULDTransactionDetailsVO> sendMUCMessage(
			Collection<ULDTransactionDetailsVO> transactionDetailsVOs)
			throws SystemException, RemoteException {
		return new ULDController().sendMUCMessage(transactionDetailsVOs);
	}

	/**
	 * 
	 * @author A-2883
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printListRepairULD(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printListRepairULD(reportSpec);
	}

	

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
	public Collection<ULDHandledCarrierVO> findHandledCarrierSetup(
			ULDHandledCarrierVO handledCarrierSetupVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findhandledcarriersetup");
		ULDController uldController = new ULDController();
		return uldController.findHandledCarrierSetup(handledCarrierSetupVO);
	}

	/**
	 * This method save the carrier details
	 * 
	 * @author A-2883
	 * @param handledCarrierSetupVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveHandledCarrier(
			Collection<ULDHandledCarrierVO> handledCarrierSetupVO)
			throws SystemException, RemoteException {
		new ULDController().saveHandledCarrier(handledCarrierSetupVO);
	}

	/**
	 * @author A-2408
	 * @param companyCode
	 * @param twoAlphaCode
	 * @param threeAlphaCode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String findOwnerCode(String companyCode, String twoAlphaCode,
			String threeAlphaCode) throws SystemException, RemoteException {
		return new ULDController().findOwnerCode(companyCode, twoAlphaCode,
				threeAlphaCode);
	}

	/**
	 * @author A-2412 This method deletes the accessory details
	 * @param accessoryTransactionVos
	 * @return Collection
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void deleteAccessoryDetails(
			Collection<AccessoryTransactionVO> accessoryTransactionVos)
			throws SystemException, RemoteException {
		new ULDController().deleteAccessoryDetails(accessoryTransactionVos);
	}



	public Page<ULDDiscrepancyVO> populateLocationLov(String companyCode,
			int displayPage, String comboValue, String airportCode)
			throws SystemException, RemoteException {
		return new ULDController().populateLocationLov(companyCode,
				displayPage, comboValue, airportCode);
	}

	

	/**
	 * @author a-2883
	 * @param reportSpec
	 * @return Map<String,Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> printMaintainDamageRepairReport(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().printMaintainDamageRepairReport(reportSpec);
	}

	/**
	 * @author A-2412
	 * @param uldIntMvtFilterVO
	 * @param pageNumber
	 * @return Page
	 * @throws SystemException
	 * @throws RemoteException
	 */

	public Page<ULDIntMvtDetailVO> findIntULDMovementHistory(
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO, int pageNumber)
			throws SystemException, RemoteException {
		return new ULDController().findIntULDMovementHistory(uldIntMvtFilterVO,
				pageNumber);
	}

	/**
	 * @author A-3045
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	public Map<String, Object> generateULDDiscrepancyReport(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			ULDDefaultsBusinessException {
		return new ULDController().generateULDDiscrepancyReport(reportSpec);
	}

	 /**
	 * @author A-3045
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws ULDDefaultsBusinessException
	 */
	  public Map<String,Object> printULDIntMovementHistory(ReportSpec reportSpec)
		throws SystemException,RemoteException,ULDDefaultsBusinessException{
		   return new ULDController().printUldIntMvtHistory(reportSpec);
	   }
	  
	/**
	 * @author A-2667
	 * @param RelocateULDVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	  public Collection<AuditDetailsVO> findULDAuditEnquiryDetails(RelocateULDVO relocateULDVO)
			throws SystemException, RemoteException {
				return new ULDController().findULDAuditEnquiryDetails(relocateULDVO);
	  }
	  /**
		 * @param reportSpec
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 * @throws ULDDefaultsBusinessException
		 */
		public Map<String, Object> printUldStationStock(ReportSpec reportSpec)
		throws SystemException, RemoteException,
		ULDDefaultsBusinessException{
			return new ULDController().printUldStationStock(reportSpec);
		}
		
		/**
		 * @author A-1950
		 * @param relocateULDVO
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void sendAlertForULDStockDeviation(String companyCode)
		throws SystemException, RemoteException {
			new ULDController().sendAlertForULDStockDeviation(companyCode);
		}
		
		/**
		 * @author A-1950
		 * @param companyCode
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void sendAlertForULDAccStockDepletion(String companyCode)
		throws SystemException, RemoteException {
			new ULDController().sendAlertForULDAccStockDepletion(companyCode);
		}
		
		/**
		 * 
		 * @param companyCode
		 * @param period
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void sendAlertForDiscrepancy(String companyCode,int period)
		throws SystemException, RemoteException {
			new ULDController().sendAlertForDiscrepancy(companyCode,period);
		}
		/**
		 * @param reportSpec
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 * @throws ULDDefaultsBusinessException
		 */
		public Map<String, Object> printUldAuditEnquiry(ReportSpec reportSpec)
		throws SystemException, RemoteException,
		ULDDefaultsBusinessException{
			return new ULDController().printUldAuditEnquiry(reportSpec);
		}
	

		/**
		 * @author A-3459
		 * @param companyCode
		 * @param section
		 * @param ULDDamageChecklistVO
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public Collection<ULDDamageChecklistVO> listULDDamageChecklistMaster(String companyCode,String section)
				throws SystemException, RemoteException {
			log.entering("ULDDefaulstServicesEJB", "listULDDamageChecklistMaster");
			return new ULDController().listULDDamageChecklistMaster(companyCode,section);
		}
		/**
		 * This method saves the damage checklist master details
		 * 
		 * @param uldDamageChecklistVOs
		 * @return
		 * @throws RemoteException
		 * @throws SystemException
		 */

		public void saveULDDamageChecklistMaster(
				Collection<ULDDamageChecklistVO> uldDamageChecklistVOs)
				throws SystemException, RemoteException {
			new ULDController().saveULDDamageChecklistMaster(uldDamageChecklistVOs);
		}
		
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
			String airportCode) throws SystemException, RemoteException {
		return new ULDController().findAllWarehousesforULD(companyCode, airportCode);
	}
	 /**
	  * @author A-2408
		 * @param companyCode
		 * @param period
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void updateSCMStatusForULD(String companyCode,int period)
		   throws SystemException,ULDDefaultsBusinessException,RemoteException{
			try{
			new ULDController().updateSCMStatusForULD(companyCode,period);
			} catch (CurrencyConversionException ex) {
				throw new ULDDefaultsBusinessException(ex);
			} catch (DimensionConversionException ex) {
				throw new ULDDefaultsBusinessException(ex);
			}
		}
		/**
		* @author A-3429
	    * @param uLDMovementFilterVO
	    * @return ULDNumberVO
	    * @throws SystemException
	    * @throws RemoteException
	    */
		public ULDNumberVO findULDHistoryCounts(ULDMovementFilterVO uLDMovementFilterVO)
		throws SystemException, RemoteException{
			return new ULDController().findULDHistoryCounts( uLDMovementFilterVO);
		}
		//added by jisha for qf1022 starts
		/**
		 * @author a-3093
		 * for QF1022
		 * @param uldRepairFilterVO
		 * @param displayPage
		 * @throws SystemException
		 */
		public Page<ULDRepairDetailsListVO> listDamageRepairDetails(
				UldDmgRprFilterVO uldRepairFilterVO,int displayPage) throws SystemException,
						RemoteException, ULDDefaultsBusinessException 				
						{
			log.entering("ULDDefaultsServicesEJB", "listDamageRepairDetails");
			return new ULDController().listDamageRepairDetails(uldRepairFilterVO,displayPage);
		}
		/**
		 * @author a-3093
		 * for QF1022
		 * @param uldmovementFilterVO
		 * @throws SystemException
		 */
		public Page<OperationalULDAuditVO> listBuildupBreakdowndetails(
				ULDMovementFilterVO uldmovementFilterVO) throws SystemException,
						RemoteException, ULDDefaultsBusinessException {
			log.entering("ULDDefaultsServicesEJB", "listBuildupBreakdowndetails");
			return new ULDController().listBuildupBreakdowndetails(uldmovementFilterVO);
		}
		/**
		 * @author a-3093
		 * for QF1022
		 * @param uldmovementFilterVO
		 * @throws SystemException
		 */
		public Page<AuditDetailsVO> findULDActionHistory(
				ULDMovementFilterVO uldmovementFilterVO) throws SystemException,
				RemoteException, ULDDefaultsBusinessException 
				{
			log.entering("ULDDefaultsServicesEJB", "findULDActionHistory");
			return new ULDController().findULDActionHistory(uldmovementFilterVO); 
				}
		
		//added by jisha forqf1022 ends
		/**
		 * 
		 * @param reconcileVOs
		 * @throws SystemException
		 * @throws RemoteException
		 * @return
		 * @throws ULDDoesNotExistsException 
		 */
		public HashMap<String, Collection<MessageVO>> sendSCMMessageforUlds(
				Collection<ULDSCMReconcileVO> reconcileVOs, Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs) throws SystemException,
				RemoteException, ULDDefaultsBusinessException {
			log.entering("ULDDefaulstServicesEJB", "saveSCMMessage");
			try {
					try{
				return new ULDController().sendSCMMessageforUlds(reconcileVOs,additionaldespatchDetailsVOs);
					}
					catch (ULDDoesNotExistsException uldDoesNotExistException) {
						throw new ULDDefaultsBusinessException(uldDoesNotExistException);
					} 
			} catch (BusinessException messageConfigException) {
				throw new ULDDefaultsBusinessException(messageConfigException);
			}
		}
		/**
		 * @author A-2408
		 * @param uldPoolOwnerFilterVO
		 * @return
		 * @throws SystemException
		 * this method checks if two airlines are pool owners in segment
		 */
		public boolean checkforPoolOwner(ULDPoolOwnerFilterVO uldPoolOwnerFilterVO)
			throws SystemException, RemoteException {
			log.entering("ULDDefaulstServicesEJB", "checkforPoolOwner");
			return new ULDController().checkforPoolOwner(uldPoolOwnerFilterVO);
	}
		/**
		 * @author a-3459
		 * for QF1022
		 * @param uldFlightMessageReconcileVO
		 * @throws SystemException
		 */
		public Page<ULDFlightMessageReconcileVO> findMissingUCMs(ULDFlightMessageReconcileVO uldFlightMessageReconcileVO) 
			throws SystemException,RemoteException, ULDDefaultsBusinessException {
			log.entering("ULDDefaultsServicesEJB", "findMissingUCMs");
			return new ULDController().findMissingUCMs(uldFlightMessageReconcileVO);
		}	
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
		public Page<String> findMUCRefNumberLov(String companyCode,
				int displayPage, String mucRefNum, String mucDate) throws SystemException,
				RemoteException {
			log.entering("ULDDefaultsServicesEJB", "findMUCRefNumberLov");
			return new ULDController().findMUCRefNumberLov(companyCode,
					displayPage, mucRefNum, mucDate);
		}
		/**
		 * For findMUCAuditDetails for MUC Tracking 
		 * as a part of CR QF1013
		 * @author A-3045
		 * @param companyCode
		 * @param mucReferenceNumber
		 * @param MucDate
		 * @param controlReceiptNumber
		 * @return Collection<ULDConfigAuditVO>
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public Collection<ULDConfigAuditVO> findMUCAuditDetails(ULDTransactionDetailsVO uldTransactionDetailsVO)
		throws SystemException, RemoteException {
			log.entering("ULDDefaultsServicesEJB", "findMUCRefNumberLov");
			return new ULDController().findMUCAuditDetails(uldTransactionDetailsVO);
		}
		
		/**
		 * For updateULDTransaction for MUC Tracking 
		 * as a part of CR QF1013
		 * @author A-3045
		 * @param uldTransactionDetailsVOs
		 * @return void
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void updateULDTransaction(Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
		throws SystemException, RemoteException {
			log.entering("ULDDefaultsServicesEJB", "updateULDTransaction");
			new ULDController().updateULDTransaction(uldTransactionDetailsVOs);
		}
		
		/**
		 * For updateMUCStatus 
		 * as a part of CR QF1142
		 * @author A-3045
		 * @param uldTransactionDetailsVOs
		 * @return void
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void updateMUCStatus(Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
		throws SystemException, RemoteException {
			log.entering("ULDDefaultsServicesEJB", "updateMUCStatus");
			new ULDController().updateMUCStatus(uldTransactionDetailsVOs);
		}
		/**
		 * @author A-2408
		 * @param reportSpec
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 * @throws ULDDefaultsBusinessException
		 */
		public Map<String, Object> printInternalMovements(ReportSpec reportSpec)
		throws SystemException, RemoteException,ULDDefaultsBusinessException{
			return new ULDController().printInternalMovements(reportSpec);
		}
		/**
		  * @author A-2553
		  * @param reportSpec
		 	* @return
		  * @throws SystemException
		  * @throws ReportGenerationException
		  */
		  public Map<String,Object> generateTransactionDetailsReport(ReportSpec reportSpec)throws
		  SystemException, RemoteException,ULDDefaultsBusinessException,ReportGenerationException{
			  return new ULDController().generateTransactionDetailsReport(reportSpec);
		  }
		  
		  /**
			 * @author a-2412
			 * @param reportSpec
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws ULDDefaultsBusinessException
			 * @throws ReportGenerationException
			 */
			public Map<String, Object> printBuildUpDetails(ReportSpec reportSpec)
				throws SystemException, RemoteException,ULDDefaultsBusinessException,ReportGenerationException {
			return new ULDController().printBuildUpDetails(reportSpec);
		}
		
			/**
			 *@author A-2883 
			 * @param reportSpec
			 * @return Map<String, Object> 
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws ULDDefaultsBusinessException
			 */
			public Map<String, Object>  listDamageRepairDetailsReport(
				ReportSpec reportSpec) throws SystemException,
				RemoteException, ULDDefaultsBusinessException 				
				{
					log.entering("ULDDefaultsServicesEJB", "listDamageRepairDetailsReport");
					return new ULDController().listDamageRepairDetailsReport(reportSpec);
			}	
			
			public void markULDFlightMovements(FlightDetailsVO flightDetailsVO)
			throws SystemException, RemoteException {
				log.entering("ULDDefaulstServicesEJB", "markULDFlightMovements");
				new ULDController().markULDFlightMovements(flightDetailsVO);
			}
			
			//added by a-3045 for bug17959 starts
			//for finding the ULDs in use
			/**
			 * For checkULDInUse
			 * @author A-3045
			 * @param uldNumbers
			 * @return Collection<String>
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public Collection<String> checkULDInUse(String companyCode,Collection<String> uldNumbers)
			throws SystemException, RemoteException{
				log.entering("ULDDefaulstServicesEJB", "checkULDInUse");
				return new ULDController().checkULDInUse(companyCode, uldNumbers);
			}
			
			/**
			 *This method is used for listing Transaction details in ULD History screen
			 * 
			 * @author A-3045
			 * @param uldTransactionFilterVO
			 * @return TransactionListVO
			 * @throws SystemException
			 * @throws RemoteException
			 */

			public TransactionListVO findTransactionHistory(
					TransactionFilterVO uldTransactionFilterVO) throws SystemException,
					RemoteException {
				log.entering("INSIDE THE SERVICES EJB", "findTransactionHistory");
				return new ULDController()
						.findTransactionHistory(uldTransactionFilterVO);
			}
			
			/**
			 * This method retrieves the uld details of the specified filter condition for SCM
			 * @author A-3045
			 * @param uldListFilterVO
			 * @param pageNumber
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 * 
			 */
			public Page<ULDVO> findULDListForSCM(ULDListFilterVO uldListFilterVO,
					int pageNumber) throws SystemException, RemoteException {
				log.entering(MODULE_NAME, "findULDListForSCM");
				return new ULDController().findULDListForSCM(uldListFilterVO, pageNumber);
			}
			
			
	
			public Collection<ErrorVO> validateULDsForTransaction(TransactionVO transactionVO)
			throws SystemException, RemoteException{
				log.entering("ULDDefaultsServicesEJB", "validateULDsForTransaction");
				return new ULDController().validateULDsForTransaction(transactionVO);
			}
			
		
			
			/**
			 * This method retrieves the uld details of the specified filter condition for SCM validation screen at a particular for a specified period
			 * @author A-3459
			 * @param scmValidationFilterVO
			 * @return
			 * @throws SystemException
			 * @throws RemoteException
			 * 
			 */
			public Page<SCMValidationVO> findSCMValidationList(SCMValidationFilterVO scmValidationFilterVO) 
				throws SystemException, RemoteException {
				log.entering(MODULE_NAME, "findULDListForSCM");
				return new ULDController().findSCMValidationList(scmValidationFilterVO);
			}
			
			/**
			 * @param reportSpec
			 * @return reportSpec
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws ULDDefaultsBusinessException
			 */
			public Map<String, Object> printSCMValidationReport(ReportSpec reportSpec)
					throws SystemException, RemoteException,
					ULDDefaultsBusinessException {
				return new ULDController().printSCMValidationReport(reportSpec);
			}
			
			/**
			 * @author a-3278
			 * To offload the ULDs from flight
			 * @param uldVos
			 * @throws SystemException
			 * @throws ULDDefaultsBusinessException
			 */
			public void offloadULDs(Collection<ULDVO> uldVos) throws SystemException,					
					ULDDefaultsBusinessException, RemoteException{	
				new ULDController().offloadULDs(uldVos);
			}
			/**
			 *
			 * @param companyCode
			 * @param partyType
			 * @return Collection<ULDServiceabilityVO>
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public Collection<ULDServiceabilityVO> listULDServiceability(
					String companyCode, String partyType) throws SystemException,
					RemoteException {
				log.entering("ULDDefaultsServicesEJB", "listULDServiceability");
				return new ULDController()
						.listULDServiceability(companyCode, partyType);
			}
		
			/**
			 *
			 * @param serviceabilityVOs
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws ULDDefaultsBusinessException
			 * @return
			 */
			public void saveULDServiceability(
					Collection<ULDServiceabilityVO> serviceabilityVOs)
					throws SystemException, RemoteException,
					ULDDefaultsBusinessException {
				log.entering("ULDDefaultsServicesEJB", "saveULDServiceability");
				new ULDController().saveULDServiceability(serviceabilityVOs);
		
			}

			/**
			 *
			 * @param companyCode
			 * @param partyType
			 * @return Collection<FlightFilterVO>
			 * @throws SystemException
			 * @throws RemoteException
			 * @throws ULDDefaultsBusinessException 
			 */
			public Collection<FlightFilterVO> validateFlightsForSegments(
					Collection<FlightFilterVO> flightFilterVOs) throws SystemException,
					RemoteException, ULDDefaultsBusinessException {
				log.entering("ULDDefaultsServicesEJB", "validateFlightsForSegments");
				return new ULDController()
						.validateFlightsForSegments(flightFilterVOs);
			}

			/**
			 * @param estimatedULDStockFilterVO
			 * @param displayPage
			 * @return Page<EstimatedULDStockVO>
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public Page<EstimatedULDStockVO> findEstimatedULDStockList(
					EstimatedULDStockFilterVO estimatedULDStockFilterVO,
					int displayPage) throws SystemException, RemoteException {
				log.entering("ULDDefaultsServicesEJB", "findEstimatedULDStockList");
				ULDController uldController = new ULDController();
				return uldController.findEstimatedULDStockList(
						estimatedULDStockFilterVO, displayPage);
			}
			/**
			 * @param excessStockAirportFilterVO
			 * @param displayPage
			 * @return Page<EstimatedULDStockVO>
			 * @throws SystemException
			 * @throws RemoteException
			 */
			public Page<ExcessStockAirportVO> findExcessStockAirportList(
					ExcessStockAirportFilterVO excessStockAirportFilterVO,
					int displayPage) throws SystemException, RemoteException {
				log.entering("ULDDefaultsServicesEJB", "findExcessStockAirportList");
				ULDController uldController = new ULDController();
				return uldController.findExcessStockAirportList(
						excessStockAirportFilterVO, displayPage);
			}
/**
 * @author A-5125
 *  Implementation for sending Message with Envelope Encoding
 * This method will encode the envelope and will send the message to the
* Addresses specified in the messageVOs. Addresses should be present in the
* messageVos with mode, envelope code			
 * @param messageVOs
 * @throws RemoteException
 * @throws SystemException
 */
public void sendMessageWithEnvelopeEncoding(Collection<MessageVO> messageVOs)
			throws RemoteException, SystemException{
				log.entering("ULD SERVICES EJB","SendMessageWithEnvelopeEncoding") ;
				new ULDController().sendMessageWithEnvelopeEncoding(messageVOs) ;
			}
public Page<FlightSegmentForBookingVO> listFlightDetails(FlightAvailabilityFilterVO fltAvbFilterVO)throws BusinessDelegateException{
	log.entering("ULDDefaultsDelegate", "listFlightDetails");
	log.log(Log.FINE, "excessStockFilterVO--->", fltAvbFilterVO);
	ULDController uldController = new ULDController();
	Page<FlightSegmentForBookingVO> fltSegForBookingVO=null; 
	try {
		fltSegForBookingVO= uldController.listFlightDetails(fltAvbFilterVO);
	} catch (CapacityBookingBusinessException e) {
		// TODO Auto-generated catch block
		
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
	
	} catch (SystemException e) {
		// TODO Auto-generated catch block
		
	}
	return fltSegForBookingVO;
}
	/**
	 * Added for ICRD-192217
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void sendULDStockNotifications(EstimatedULDStockFilterVO estimatedULDStockFilterVO) throws RemoteException,SystemException{
		new ULDController().sendULDStockNotifications(estimatedULDStockFilterVO);
	}
	
	/**
	 * Added for ICRD-192280
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void sendUCMMissingNotifications(EstimatedULDStockFilterVO estimatedULDStockFilterVO) throws RemoteException,SystemException{
		new ULDController().sendUCMMissingNotifications(estimatedULDStockFilterVO);
	}
	
	/**
	 * Added for ICRD-114538
	 * @author A-3415
	 * @param TransactionFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<String> checkIfOpenTransactionExists(Collection<TransactionFilterVO> filterVOs) throws RemoteException,SystemException{
		return new ULDController().checkIfOpenTransactionExists(filterVOs);
	}
	
	public Collection<MessageRuleDefenitionVO> findMessageTypeAndVersion(String companyCode, String messageType) throws RemoteException, SystemException{
		return new ULDController().findMessageTypeAndVersion(companyCode, messageType);
	}
	
//Added by A-6991 for ICRD-192185
	public boolean validateFlightArrival(FlightValidationVO flightValidationVO, ArrayList<String> destination) throws RemoteException,SystemException {
		// To be reviewed Auto-generated method stub
		log.entering("ULDDefaultsServicesEJB", "validateFlightArrival");
		//Boolean isValid = despatchRequest("validateFlightArrival", flightValidationVO, destination);
		return new ULDController().validateFlightArrival(flightValidationVO, destination);
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBI#findConsolidatedUCMsForFlight(com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO)
	 *	Added by 			: A-7359 on 07-Sep-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@param uldFlightMessageFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 */
	public ULDFlightMessageReconcileVO findConsolidatedUCMsForFlight(FlightFilterMessageVO uldFlightMessageFilterVO) throws RemoteException,SystemException{
		log.entering("ULDDefaultsDelegate", "findConsolidatedUCMsForFlight");
		return new ULDController().findConsolidatedUCMsForFlight(uldFlightMessageFilterVO);
	}
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBI#findULDDamageRepairDetails(com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO)
	 *	Added by 			: A-7043 on 13-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param uldDamageFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 */
	public ULDDamageRepairDetailsVO findULDDamageRepairDetails(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException,
			RemoteException {
		log.entering("ULDDefaultsServicesEJB", "findULDDamageRepairDetails");
		return new ULDController().findULDDamageRepairDetails(uldDamageFilterVO);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBI#updateMultipleULDDamageDetails(java.util.Collection)
	 *	Added by 			: A-7359 on 11-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param uldDamageRepairDetailsVOs
	 *	Parameters	:	@throws BusinessDelegateException 
	 * @throws SystemException 
	 */
	public void updateMultipleULDDamageDetails(Collection<ULDDamageRepairDetailsVO> uldDamageRepairDetailsVOs)throws  SystemException,RemoteException {
		log.entering("ULDDefaultsServicesEJB", "udateMultipleULDDamageDetails");
		new ULDController().updateMultipleULDDamageDetails(uldDamageRepairDetailsVOs);
	}
	
	public Collection<ULDDamagePictureVO> findULDDamagePictures(ULDDamageFilterVO uldDamageFilterVO) throws SystemException{
		log.entering("findULDDamagePictures", "findULDDamagePictures");
		return new ULDController().findULDDamagePictures(uldDamageFilterVO);
	}
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
			throws SystemException, RemoteException, ULDDefaultsBusinessException {
		return new ULDController().validateULDAirportLocation(companyCode,airportCode,locationCode,facilityTypeCode);
	}
	/**
	 * A-8368
	 */
	public void findUldsForMarkMovement(String companyCode, int rowCount)
			throws SystemException, RemoteException {
		new ULDController().findUldsForMarkMovement(companyCode, rowCount);
	}
	
	/**
	 * Added for IASCB-14723
	 * 
	 * @author A-7900
	 * @param flightDetailsVOs
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ULDDefaultsBusinessException
	 */
	public void updateULDsForOperations(Collection<FlightDetailsVO> flightDetailsVOs)
			throws SystemException, RemoteException, ULDDefaultsBusinessException {
		new ULDController().updateULDsForOperations(flightDetailsVOs);
	}

	/**
	 * 	Used for 	:   IASCB-162396 for sending UCMMessage
	 *	Parameters	:	@param UCMMessageVO ucmMessageVO
	 *  Parameters	:	@throws SystemException
	 *  Parameters	:	@throws ULDDefaultsBusinessException
	 *	Return type	: 	void
	 */
	public void generateUCMMessage(Collection<UCMMessageVO> ucmMessageVOs)
			throws SystemException, ULDDefaultsBusinessException {
		new ULDController().generateUCMMessage(ucmMessageVOs);
	}

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
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.uld.defaults.OperationsShipmentBI#findAirportsforSCMJob
	 *	Added on 			: 18-Oct-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode,airportGroup,noOfDays
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException
	 */
	public Collection<String> findAirportsforSCMJob(String companyCode,Collection<String> airportGroup,String noOfDays)
			throws RemoteException, SystemException, PersistenceException  {
		log.entering("ULDDefaultsServicesEJB", "findAirportsforSCMJob");
		return new ULDController().findAirportsforSCMJob(companyCode,airportGroup,noOfDays);
	}
	
	/**
	 * 	Method		:	ULDDefaultsServicesEJB.findSpecialInstructionsForShipments
	 * 	Used for 	:   IASCB-110115 for sending SCM Reminder notifications
	 *	Parameters	:	@param airports
	 *	Parameters	:	@throws RemoteException 
	 *  Parameters	:	@throws SystemException
	 *  Parameters	:	@throws BusinessException
	 *	Return type	: 	void
	 */
	@Override
	public void sendSCMReminderNotifications(Collection<String> airports) throws RemoteException, SystemException, BusinessException {
		log.entering("OperationsShipmentServicesEJB", "sendSCMReminderNotifications");
		new ULDController().sendSCMReminderNotifications(airports);
		log.exiting("OperationsShipmentServicesEJB", "sendSCMReminderNotifications");
	}
	/**
	 * 
	 * 	Method		:	ULDDefaultsServicesEJB.findBestFitULDAgreement
	 *	Added on 	:	19-Apr-2023
	 * 	Used for 	:	finding best fit agreement
	 *	Parameters	:	@param uldTransactionDetailsVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ULDAgreementVO
	 */
	public ULDAgreementVO findBestFitULDAgreement(ULDTransactionDetailsVO uldTransactionDetailsVO) throws RemoteException, SystemException {
		return new ULDController().findBestFitULDAgreement(uldTransactionDetailsVO);
	}

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBI#updateULDTransactionWithDemurrageDetails(com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO)
	 *	Added on 			: 24-Apr-2023
	 * 	Used for 	:	updating transaction with demurrage details
	 *	Parameters	:	@param transactionVO
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws BusinessException
	 */
	public void updateULDTransactionWithDemurrageDetails(TransactionVO transactionVO)
			throws RemoteException, SystemException, BusinessException {
		new ULDController().updateULDTransactionWithDemurrageDetails(transactionVO);
		
		
	}
}
