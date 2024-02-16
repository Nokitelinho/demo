/*
 * ULDDefaultsDelegate.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.delegate.uld.defaults;

//import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.ibsplc.icargo.business.capacity.booking.vo.FlightAvailabilityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentForBookingVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.flight.schedule.vo.FlightScheduleFilterVO;
import com.ibsplc.icargo.business.msgbroker.config.format.vo.MessageRuleDefenitionVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cpm.CPMMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.muc.MUCMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.scm.SCMMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightMessageFilterVO;
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
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDReturnTxnVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDNumberVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.storageunit.vo.ULDCheckinVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.entity.AvoidForcedStaleDataChecks;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1496
 *
 */

@Module("uld")
@SubModule("defaults")
public class ULDDefaultsDelegate extends BusinessDelegate {

	private Log log = LogFactory.getLogger("ULD MANAGEMENT");

	/**
	 * This method is used to relocate a ULD to the specified location
	 *
	 * @author A-1950
	 *
	 * @param relocateULDVOs
	 * @throws BusinessDelegateException
	 */
	public void relocateULD(Collection<RelocateULDVO> relocateULDVOs)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "relocateULD");
		despatchRequest("relocateULD", relocateULDVOs);
	}

	/**
	 * This method saves the invoice details for the repairs done on a ULD
	 *
	 * @param uldRepairVO
	 * @throws BusinessDelegateException
	 */
	public void saveRepairInvoice(ULDRepairVO uldRepairVO)
	throws BusinessDelegateException {
	}

	/**
	 * This method retrieves the repair head details for invoicing.
	 * @author A-1883
	 * @param companyCode
	 * @param invoiceRefNumber
	 * @return ULDRepairInvoiceVO
	 * @throws BusinessDelegateException
	 */
	public ULDRepairInvoiceVO findRepairInvoiceDetails(String companyCode,
			String invoiceRefNumber) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findRepairInvoiceDetails");
		return despatchRequest("findRepairInvoiceDetails", companyCode,
				invoiceRefNumber);
	}

	/**
	 * @author A-1883
	 * @param companyCode
	 * @param uldNumber
	 * @return ULDValidationVO
	 * @throws BusinessDelegateException
	 */
	public ULDValidationVO validateULD(String companyCode, String uldNumber)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "validateULD");
		return despatchRequest("validateULD", companyCode, uldNumber);

	}

	/**
	 * This method is used to list the damage reports according
	 * to the specified filter criteria
	 *
	 * @param uldDamageFilterVO
	 * @return Collection<ULDDamageListVO>
	 * @throws BusinessDelegateException
	 */
	public Page<ULDDamageDetailsListVO> findULDDamageList(
			ULDDamageFilterVO uldDamageFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDDamageList");
		log.log(Log.INFO, "!!!!uldDamageFilterVO", uldDamageFilterVO);
		/*for(ULDDamageRepairDetailsVO vo : page){
			log.log(Log.INFO,"!!!!!!ULDDamageRepairDetailsVO"+vo);
		}*/
		return despatchRequest("findULDDamageList", uldDamageFilterVO);
	}

	/**
	 *
	 * @param uldDamageDeleteVOs
	 * @throws BusinessDelegateException
	 */
	public void deleteULDDamages(
			Collection<ULDDamageDeleteVO> uldDamageDeleteVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "deleteULDDamages");
		log.log(Log.INFO, "!!!!!!!uldDamageDeleteVOs", uldDamageDeleteVOs);
		despatchRequest("deleteULDDamages", uldDamageDeleteVOs);
	}

	/**
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @param pageNumber
	 * @return Page<ULDDamageReferenceNumberLovVO>
	 * @throws BusinessDelegateException
	 */
	public Page<ULDDamageReferenceNumberLovVO> findULDDamageReferenceNumberLov(
			String companyCode, String uldNumber, int pageNumber)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDDamageReferenceNumberLov");
		log.log(Log.INFO, "!!!!!companyCode", companyCode);
		log.log(Log.INFO, "!!!!!uldNumber", uldNumber);
		//log.log(Log.INFO,"!!!!!Page<ULDDamageReferenceNumberLovVO> "+page);
		return despatchRequest("findULDDamageReferenceNumberLov", companyCode, uldNumber, pageNumber);
	}

	/**
	 *
	 * @param uldRepairFilterVO
	 * @return Page<ULDDamageRepairDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Page<ULDRepairDetailsListVO> listULDRepairDetails(
			ULDRepairFilterVO uldRepairFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDDamageList");
		log.log(Log.INFO, "!!!!uldRepairFilterVO", uldRepairFilterVO);
	/*	for(ULDDamageRepairDetailsVO vo : page){
			log.log(Log.INFO,"!!!!!!ULDDamageRepairDetailsVO"+vo);
		}    */
		return despatchRequest("listULDRepairDetails", uldRepairFilterVO);
	}

	/**
	 * This methos is used to save the details of the uld damage.
	 *
	 * @param uldDamageRepairDetailsVO
	 * @throws BusinessDelegateException
	 */
	public void saveULDDamage(ULDDamageRepairDetailsVO uldDamageRepairDetailsVO)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveULDDamage");
		despatchRequest("saveULDDamage", uldDamageRepairDetailsVO);
	}

	/**
	 * This method is used to list the damage details for the
	 * specified damage reference number for a particular ULD.
	 *
	 * @param uldDamageFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public ULDDamageRepairDetailsVO findULDDamageDetails(
			ULDDamageFilterVO uldDamageFilterVO)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDDamageDetails");
		log.log(Log.INFO, "!!!!!!uldDamageFilterVO", uldDamageFilterVO);
		ULDDamageRepairDetailsVO damageVO = despatchRequest(
				"findULDDamageDetails", uldDamageFilterVO);
		log.log(Log.INFO, "!!!!ULDDamageRepairDetailsVO", damageVO);
		return damageVO;
	}

	/**
	 * This method retrieves the uld movement history
	 *
	 * @param uldMovementFilterVO
	 * @param pageNumber
	 * @return Collection<ULDMovementVO>
	 * @throws BusinessDelegateException
	 */
	public Page<ULDMovementDetailVO> findULDMovementHistory(
			ULDMovementFilterVO uldMovementFilterVO, int pageNumber)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDMovementHistory");
		return despatchRequest("findULDMovementHistory", uldMovementFilterVO,
				pageNumber);
	}

	/**
	 *
	 * @param uldNOs
	 * @param uldMovementVos
	 * @throws BusinessDelegateException
	 */
	public void saveULDMovement(Collection<String> uldNOs,
			Collection<ULDMovementVO> uldMovementVos, String screenID, String overrideFlag, Collection<LockVO> lockVOs)
	throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "INSIDE THE DELEGATE");
		log.log(Log.FINE, "SAVE ULD MOVEMENT", uldNOs);
		log.log(Log.FINE, "SAVE ULD MOVEMENT", uldMovementVos);
		despatchRequest("saveULDMovement", lockVOs, new Object[] { uldNOs,
				uldMovementVos,screenID,overrideFlag });
	}

	/**
	 * This method retrieves the uld details of the specified filter condition
	 *
	 * @param accessoriesStockConfigFilterVO
	 * @param pageNumber
	 * @return Page<AccessoriesStockConfigVO>
	 * @throws BusinessDelegateException
	 */
	public Page<AccessoriesStockConfigVO> findAccessoryStockList(
			AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO,
			int pageNumber) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findAccessoryStockList");
		log.log(Log.FINE, "AccessoriesStockConfigFilterVO--->",
				accessoriesStockConfigFilterVO);
		log.log(Log.FINE, "pageNumber--->", pageNumber);
		return despatchRequest("findAccessoryStockList",
				accessoriesStockConfigFilterVO, pageNumber);
	}

	/**
	 *
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ULDListVO> findULDList(ULDListFilterVO uldListFilterVO,
			int pageNumber) throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "findULDList");
		return despatchRequest("findULDList", uldListFilterVO, pageNumber);
	}

	/**
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return ULDValidationVO
	 * @throws BusinessDelegateException
	 */
//	public ULDValidationVO findCurrentULDDetails(
//			String companyCode, String uldNumber)
//	throws BusinessDelegateException {
//		log.entering("ULDDefaultsDelegate","findCurrentULDDetails");
//		return despatchRequest("findCurrentULDDetails",companyCode,uldNumber);
//	}
	/**
	 *  This method retrieves the stock details of the specified accessory code
	 * @param companyCode
	 * @param accessoryCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @return AccessoriesStockConfigVO
	 * @throws BusinessDelegateException
	 */
	public AccessoriesStockConfigVO findAccessoriesStockDetails(
			String companyCode, String accessoryCode, String stationCode,
			int airlineIdentifier) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findAccessoriesStockDetails");
		return despatchRequest("findAccessoriesStockDetails", companyCode,
				accessoryCode, stationCode, airlineIdentifier);
	}

	/**
	 * This method saves the accessory stock details
	 *
	 * @param accessoriesStockConfigVO
	 * @throws BusinessDelegateException
	 */
	public void saveAccessoriesStock(
			AccessoriesStockConfigVO accessoriesStockConfigVO)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveAccessoriesStock");
		despatchRequest("saveAccessoriesStock", accessoriesStockConfigVO);
		log.exiting("ULDDefaultsDelegate", "saveAccessoriesStock");
	}

	/**
	 * This method saves multiple accessory stock details
	 *
	 * @param accessoriesStockConfigVOs
	 * @throws BusinessDelegateException
	 */
	public void saveAccessories(
			Collection<AccessoriesStockConfigVO> accessoriesStockConfigVOs)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveAccessories");
		despatchRequest("saveAccessories", accessoriesStockConfigVOs);
		log.exiting("ULDDefaultsDelegate", "saveAccessories");
	}

	/**
	 * This method validates the format of the specified ULD
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @throws BusinessDelegateException
	 */
	public boolean  validateULDFormat(String companyCode, String uldNumber)
	throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "validateULDFormat");
		Boolean isValid = despatchRequest("validateULDFormat", companyCode,
				uldNumber);
		return isValid.booleanValue();

	}

	/**
	 * This method retrieves the details of the specified ULD
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public ULDVO findULDDetails(String companyCode, String uldNumber)
	throws BusinessDelegateException {
		log.entering("inside the Delegate", "findULDDetails");
		return despatchRequest("findULDDetails", companyCode, uldNumber);
	}

	/**
	 * This method saves the uld details
	 * @param uldVo
	 * @throws BusinessDelegateException
	 */
	public void saveULD(ULDVO uldVo) throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "saveULD");
		despatchRequest("saveULD", uldVo);

	}

	/**
	 *
	 * @param uldVo
	 * @param uldNumberVos
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<String> createMultipleULDs(ULDVO uldVo,
			Collection<String> uldNumberVos) throws BusinessDelegateException {
		log.entering("inside the Delegate", "createMultipleULDs");
		return despatchRequest("createMultipleULDs", uldVo, uldNumberVos);
	}

	/**
	 *
	 * @param companyCode
	 * @param uldTypeCode
	 * @return
	 * @throws BusinessDelegateException
	 */
	public ULDTypeVO findULDTypeStructuralDetails(String companyCode,
			String uldTypeCode) throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "findULDTypeStructuralDetails");
		return despatchRequest("findULDTypeStructuralDetails", companyCode,
				uldTypeCode);
	}

	/**
	 * This method is used to list the details of the specified ULD Group
	 *
	 * @param companyCode
	 * @param uldGroupCode
	 * @return ULDGroupVO
	 * @throws BusinessDelegateException
	 */
//	@Action("findULDGroupDetails")
//	public ULDGroupVO findULDGroupDetails(String companyCode, String uldGroupCode)
//	throws BusinessDelegateException {
//	return null;
//	}
	/**
	 * This method is used for creating a uld group
	 *
	 * @param uldGroupVO
	 * @throws BusinessDelegateException
	 */
//	@Action("createULDGroup")
//	public void createULDGroup(ULDGroupVO uldGroupVO)
//	throws BusinessDelegateException {
//	}
	/**
	 * This method is used to modify a uld group and
	 * to update the selected ULD types with the changes
	 *
	 * @param uldGroupVO
	 * @param Collection<String> uldtypes
	 * @throws BusinessDelegateException
	 */
//	@Action("modifyULDGroup")
//	public void modifyULDGroup(ULDGroupVO uldGroupVO, Collection uldTypes)
//	throws BusinessDelegateException {
//	}
	/**
	 * This method is used to delete the specified ULD Group
	 *
	 * @param companyCode
	 * @param uldGroupCode
	 * @return
	 * @throws BusinessDelegateException
	 */
//	@Action("deleteULDGroup")
	public void deleteULDGroup(String companyCode, String uldGroupCode)
	throws BusinessDelegateException {
	}

	/**
	 * This method is used to populate the ULDGroups associated to a Company in the system.
	 * @param companyCode
	 * @return Collection<String>
	 * @throws BusinessDelegateException
	 *
	 */
//	@Action("populateULDGroupLOV")
	//To be reviewed Collection<String>
	public Collection<String> populateULDGroupLOV(String companyCode)
			throws BusinessDelegateException {
		return null;

	}

	/**
	 * This method retrieves all the ULD types for the specified group
	 *
	 * @param companyCode
	 * @param uldGroupCode
	 * @return Collection<String>
	 * @throws SystemException
	 */
//	@Action("findULDTypesByGroup")
	//To be reviewed Collection<String>
	public Collection<String> findULDTypesByGroup(String companyCode,
			String uldGroupCode) throws SystemException {
		return null;
	}

	/**
	 * This method is used to find the ULDType details
	 * @param String companyCode
	 * @param String uldTypeCode
	 * @return
	 *
	 * @throws BusinessDelegateException
	 */
//	public ULDTypeVO findULDType(String companyCode,
//	String uldTypeCode)
//	throws BusinessDelegateException{
//	return null;
//	}
	/**
	 * This method is used to save the ULDType details based on VO.
	 * @param ULDTypeVO uldTypeVO
	 * @return
	 * @throws SystemException
	 */
//	@Action("saveULDType")
//	public void saveULDType(ULDTypeVO uldTypeVO)
//	throws BusinessDelegateException{
//	}
	/**
	 * This method is used to save the ULDType template for a collection of ULDs Specified.
	 * @param ULDTypeVO uldTypeVO
	 * @param Collection<String> ulds
	 * @return
	 * @throws SystemException
	 */
//	@Action("modifyULDType")
	//To be reviewed Collection<String> ulds
//	public void modifyULDType(ULDTypeVO uldTypeVO,
//	Collection ulds)
//	throws BusinessDelegateException{
//
//	}
	/**
	 * This method is used to delete the ULDType details based on filter.
	 * @param String companyCode
	 * @param String uldTypeCode
	 * @return
	 * @throws SystemException
	 */
//	@Action("deleteULDType")
//	public void deleteULDType(String companyCode,
//	String uldTypeCode)
//	throws BusinessDelegateException{
//
//	}
	/**
	 * This method is used to retrieve the ULDs associated to a Company and ULDType.
	 * @param  companyCode
	 * @param  uldTypeCode
	 * @return Collection<String>
	 * @throws BusinessDelegateException
	 */
//	@Action("findAssociatedULDs")
	//To be reviewed Collection<String>
	public Collection<String> findAssociatedULDs(String companyCode,
			String uldTypeCode) throws BusinessDelegateException {

		return null;

	}

	/**
	 * This method is used to populate the ULDTypes associated to a Company in the system.
	 * @param  companyCode
	 * @return Collection<String>
	 * @throws BusinessDelegateException
	 *
	 */
//	@Action("populateULDTypeLOV")
	//To be reviewed Collection<String>
	public Collection<String> populateULDTypeLOV(String companyCode)
			throws BusinessDelegateException {
		return null;

	}

	/**
	 * This method is used to list the details of the specified ULD Group
	 *
	 * @param companyCode
	 * @param agreementNumber
	 * @return ULDAgreementVO
	 * @throws BusinessDelegateException
	 */
//	@Action("findULDAgreementDetails")
	public ULDAgreementVO findULDAgreementDetails(String companyCode,
			String agreementNumber) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDAgreementDetails");
		return despatchRequest("findULDAgreementDetails", companyCode,
				agreementNumber);
	}
	
	//Added by A-8445 as a part of IASCB-28460 Starts
	/**
	 * This method is used to list the details of the specified ULD Group
	 *
     * @param companyCode
	 * @param agreementNumber
	 *@param  uldAgreementFilterVO
	 *@return Page<ULDAgreementVO>
	 *
	 *@throws BusinessDelegateException
	 */
	//To be reviewed Page<ULDAgreementDetailsVO>
	//@Action("findULDAgreementDetailsPagination")
	public Page<ULDAgreementDetailsVO> findULDAgreementDetailsPagination(String companyCode,
			String agreementNumber, ULDAgreementFilterVO uldAgreementFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDAgreementDetailsPagination");
		log.log(Log.INFO, "!!!!!ULDAgreementFilterVO", uldAgreementFilterVO);
		Page<ULDAgreementDetailsVO> uldAgreementDetailsVOs = despatchRequest(
				"findULDAgreementDetailsPagination", companyCode, agreementNumber, uldAgreementFilterVO);
		log.log(Log.INFO, "!!!!!Page<ULDAgreementDetailsVO>", uldAgreementDetailsVOs);
		return	uldAgreementDetailsVOs;
	}
	//Added by A-8445 as a part of IASCB-28460 Ends
	
	/**
	 * This method is used to update the status of ULD Agreement to
	 * the changed status.ie Active to Inactive and ViceVeraa
	 *
	 * @param companyCode
	 * @param agreementNumbers
	 * @param changedStatus
	 * @return
	 * @throws BusinessDelegateException
	 */
	//@Action("updateULDAgreementStatus")
	/*public void updateULDAgreementStatus(
			String companyCode, Collection<String> agreementNumbers,
			String changedStatus)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate","updateULDAgreementStatus");
		despatchRequest("updateULDAgreementStatus", companyCode ,
				agreementNumbers, changedStatus);
	}*/

	public void updateULDAgreementStatus(Collection<ULDAgreementVO> uldAgrVOs,
			String changedStatus) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "updateULDAgreementStatus");
		despatchRequest("updateULDAgreementStatus", uldAgrVOs, changedStatus);
	}

	/**
	 *  This method is used for creating a uld agreement
	 *
	 * @param uldAgreementVO
	 * @return String
	 * @throws BusinessDelegateException
	 */
	public String createULDAgreement(ULDAgreementVO uldAgreementVO)
	throws BusinessDelegateException {
		String agreementNumber = despatchRequest("createULDAgreement",
				uldAgreementVO);
		log.log(Log.INFO, "!!!!!!!agreementNumber", agreementNumber);
		return agreementNumber;
	}
	
	
	/**
	 * This method is used for deleting a uld agreement
	 *
	 * @param  companyCode
	 * @param  agreementCode
	 * @throws BusinessDelegateException
	 */
//	@Action("deleteULDAgreement")
	public void deleteULDAgreement(String companyCode, String agreementCode)
	throws BusinessDelegateException {
	}

	/**
	 * This method is used for populating the ULD Agreements
	 *@param  companyCode
	 *@param pageNumber
	 *@return Page<ULDAgreementVO>
	 *
	 *@throws BusinessDelegateException
	 */
	//@Action("populateULDAgreementLOV")
	//To be reviewed Collection<Strings>
	public Page<ULDAgreementVO> populateULDAgreementLOV(ULDAgreementFilterVO uldAgreementFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "populateULDAgreementLOV");
		//log.log(Log.INFO, "!!!!!!companyCode!!!!!!!!" + companyCode);
		Page<ULDAgreementVO> uldAgreementVOs = despatchRequest(
				"populateULDAgreementLOV", uldAgreementFilterVO);
		log.log(Log.INFO, "!!!!!!Page<ULDAgreementVO>", uldAgreementVOs);
		return uldAgreementVOs;
	}

	/**
	 * This method is used for listing uld agreement in the system
	 *
	 *@param  uldAgreementFilterVO
	 *@return Page<ULDAgreementVO>
	 *
	 *@throws BusinessDelegateException
	 */
	//To be reviewed Page<ULDAgreementVO>
	//@Action("listULDAgreements")
	public Page<ULDAgreementVO> listULDAgreements(
			ULDAgreementFilterVO uldAgreementFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDAgreements");
		log.log(Log.INFO, "!!!!!ULDAgreementFilterVO", uldAgreementFilterVO);
		Page<ULDAgreementVO> uldAgreementVOs = despatchRequest(
				"listULDAgreements", uldAgreementFilterVO);
		log.log(Log.INFO, "!!!!!Page<ULDAgreementVO>", uldAgreementVOs);
		return	uldAgreementVOs;
	}

	/**
	 * This method is used to create modify and delete
	 * ULDStockConfiguration
	 * @param  uldStockConfigVOs
	 * @throws BusinessDelegateException
	 *
	 */
	//To be reviewed Collection<ULDStockConfigVO>
	//@Action("saveULDStockConfig")
	public void saveULDStockConfig(
			Collection<ULDStockConfigVO> uldStockConfigVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveULDStockConfig");
		log.log(Log.INFO, "!!!!!Collection<ULDStockConfigVO> ",
				uldStockConfigVOs);
		despatchRequest("saveULDStockConfig", uldStockConfigVOs);
	}

	/**
	 * This method is used to list the ULD Set up Configuration
	 *
	 * @param  uldStockConfigFilterVO
	 * *
	 * @throws BusinessDelegateException
	 * @return Collection<ULDStockConfigVO>
	 *
	 */
	//Collection<ULDStockListVO>
	//@Action("listULDStockConfig")
	public Collection<ULDStockConfigVO> listULDStockConfig(
			ULDStockConfigFilterVO uldStockConfigFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDStockConfig");
		log
				.log(Log.INFO, "!!!!!uldStockConfigFilterVO",
						uldStockConfigFilterVO);
		Collection<ULDStockConfigVO> uldStockConfigVOs = despatchRequest(
				"listULDStockConfig", uldStockConfigFilterVO);
		log.log(Log.INFO, "!!!!!uldStockConfigVOs", uldStockConfigVOs);
		return uldStockConfigVOs;
	}

	/**
	 * This method is used to find the ULD Set up Configuration
	 *
	 * @param  companyCode
	 * @param  airlineIdentifier
	 * @param  stationCode
	 * @param  uldType
	 * @return ULDStockConfigVO
	 * * *
	 * @throws BusinessDelegateException
	 *
	 */
	//@Action("findULDStockConfig")
	public ULDStockConfigVO findULDStockConfig(String companyCode,
			int airlineIdentifier, String stationCode, String uldType)
			throws BusinessDelegateException {
		return null;
	}

	/**
	 * This method is used to list the ulds at the particularStation for a ParticularAirlineIdentifier
	 * @param companyCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @return Collection<String>
	 * @throws BusinessDelegateException
	 */
	public Page<String> findStationUlds(String companyCode,
			String uldNumber, String stationCode,String transactionType ,int airlineIdentifier , int displayPage)
			throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE ", "findStationUlds");
		return despatchRequest("findStationUlds", companyCode, uldNumber,
				stationCode,transactionType, airlineIdentifier,displayPage);
	}
	/**
	 * This method is used to list Accessories at a purticular station
	 *
	 * @param  companyCode
	 * @param  stationCode
	 * @param airlineIdentifier
	 * @return Collection<String>
	 * * *
	 * @throws BusinessDelegateException
	 *
	 */
//	@Action("findStationAccessories")
	//Collection<String>
	public Collection<String> findStationAccessories(String companyCode,
			String stationCode, int airlineIdentifier)
			throws BusinessDelegateException {
		return null;
	}

	/**
	 *
	 * @param transactionVO
	 * @return
	 * @throws BusinessDelegateException
	 */

	public Collection<ErrorVO> saveULDTransaction(TransactionVO transactionVO,
			Collection<LockVO> lockVOs) throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "saveULDTransaction");
		return despatchRequest("saveULDTransaction", lockVOs, transactionVO);
	}

	/*This method is used for listing uld agreement in the system
	 *
	 *@param ULDAgreementFilterVO
	 *@return Page<ULDAgreementVO>
	 *
	 *@throws BusinessDelegateException
	 */
	//@Action("findULDAgreementsForTransaction")
	//To be reviewed Page<ULDAgreementVO>
	/**
	 * @param uldAgreementFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ULDAgreementVO>  findULDAgreementsForTransaction(
			ULDAgreementFilterVO uldAgreementFilterVO)
			throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE ", "findULDAgreementsForTransaction");
		return despatchRequest("findULDAgreementsForTransaction",
				uldAgreementFilterVO);
	}

	/**
	 * This method is used for listing uld transaction
	 * @author A-1883
	 *@param  uldTransactionFilterVO
	 *@return TransactionListVO
	 *@throws BusinessDelegateException
	 */
	public TransactionListVO listULDTransactionDetails(
			TransactionFilterVO uldTransactionFilterVO)
			throws BusinessDelegateException {
		log.entering("INSIDE ULD Defaults Delegate",
				"listULDTransactionDetails");
		return despatchRequest("listULDTransactionDetails",
				uldTransactionFilterVO);
	}

	/**
	 *
	 * @param uldTransactionFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public TransactionListVO findULDTransactionDetailsCol(
			TransactionFilterVO uldTransactionFilterVO)
			throws BusinessDelegateException {
		log.entering("INSIDE ULD Defaults Delegate",
				"findULDTransactionDetailsColl");
		return despatchRequest("findULDTransactionDetailsCol",
				uldTransactionFilterVO);
	}

	/**
	 * This method is used for modify transactions
	 *
	 *@param  transactionListVO
	 *	 *
	 *@throws BusinessDelegateException
	 */
	//@Action("modifyTransactionDetails")
	public  void modifyTransactionDetails(TransactionListVO transactionListVO)
			throws BusinessDelegateException {

	}

	/**
	 * This method is used for delete ULD transactions
	 *
	 *@param  transactionListVO
	 *	 *
	 *@throws BusinessDelegateException
	 */
	//@Action("deleteTransactionDetails")
	public  void deleteTransactionDetails(TransactionListVO transactionListVO)
			throws BusinessDelegateException {

	}

	/**
	 * This method is used for returning the ULD's after
	 * Calculating the Demurrage Charges Accrued against the UDLds
	 *
	 *@param  uldTransactionDetailsVOs
	 *@return Collection<ULDTransactionDetailsVO>
	 *
	 *@throws BusinessDelegateException
	 */
	//@Action("calculateReturnULDCharges")
	//To be reviewed Collection<ULDTransactionDetailsVO>
	//
	public Collection<ULDTransactionDetailsVO> calculateReturnULDCharges(
			Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
			throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE ", "calculateReturnULDCharges");
		return despatchRequest("calculateReturnULDCharges",
				uldTransactionDetailsVOs);
	}

	/**
	 * This method is used for calculating the Demurrage Charges Accrued against the UDLds
	 * @author a-3278
	 *@param  uldTransactionDetailsVOs
	 *@return Collection<ULDTransactionDetailsVO>
	 *
	 *@throws BusinessDelegateException
	 */
	public Collection<ULDTransactionDetailsVO> calculateULDDemmurage(
			Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
			throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE ", "calculateULDDemmurage");
		return despatchRequest("calculateULDDemmurage",
				uldTransactionDetailsVOs);
	}

	/**
	 * This method is used for listing uld Invoice
	 *@author A-1883
	 *@param chargingInvoiceFilterVO
	 *@param pageNumber
	 *@return Page<ULDChargingInvoiceVO>
	 *@throws BusinessDelegateException
	 */
	public Page<ULDChargingInvoiceVO> listULDChargingInvoice(
			ChargingInvoiceFilterVO chargingInvoiceFilterVO, int pageNumber)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDChargingInvoice");
		return despatchRequest("listULDChargingInvoice",
				chargingInvoiceFilterVO, pageNumber);
	}

	/**
	 * This method is used for listing uld Invoice Details
	 * @author A-1883
	 * @param companyCode
	 * @param invoiceRefNumber
	 * @return Collection<ULDTransactionDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<ULDTransactionDetailsVO> viewULDChargingInvoiceDetails(
			String companyCode,	String invoiceRefNumber)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "viewULDChargingInvoiceDetails");
		return despatchRequest("viewULDChargingInvoiceDetails", companyCode,
				invoiceRefNumber);
	}

	/**
	 *This method is used fo Generating Invoice for returned ULDs
	 *@author A-1883
	 *@param uldChargingInvoiceVO
	 *@param uldTransactionDetailsVOs
	 *@return String
	 *@throws BusinessDelegateException
	 */
	public String generateInvoiceForReturnedUlds(
			ULDChargingInvoiceVO uldChargingInvoiceVO,
			Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "generateInvoiceForReturnedUlds");
		return despatchRequest("generateInvoiceForReturnedUlds",
				uldChargingInvoiceVO, uldTransactionDetailsVOs);
	}

	/**
	 * This method is used for Saving the Returned ULDs
	 * and Accessories
	 *@param transactionListVO
	 *@throws BusinessDelegateException
	 */
	//@Action("saveReturnTransaction")
	public void saveReturnTransaction(TransactionListVO transactionListVO,
			Collection<LockVO> lockVOs) throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "saveReturnTransaction");
		despatchRequest("saveReturnTransaction", lockVOs, transactionListVO);
	}

	/**
	 */
	public void validateULD() {
	}

	/**
	 *
	 * @param uldVos
	 * @throws BusinessDelegateException
	 */
	public void saveULDs(Collection<ULDVO> uldVos)
	throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "saveULDs");
		despatchRequest("saveULDs", uldVos);

	}

	/**
	 * For InvoiceRefNumber LOV
	 * @author A-1883
	 * @param companyCode
	 * @param displayPage
	 * @return Page<String>
	 * @throws BusinessDelegateException
	 */
	public Page<String> findInvoiceRefNumberLov(String companyCode,
			int displayPage, String invRefNo) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findInvoiceRefNumberLov");
		return despatchRequest("findInvoiceRefNumberLov", companyCode,
				displayPage, invRefNo);
	}

	/**
	 * @author A-1883
	 * @param companyCode
	 * @param uldNumbers
	 * @return Collection<String>
	 * @throws BusinessDelegateException
	 */
	public Collection<String> validateMultipleULDFormats(String companyCode,
			Collection<String> uldNumbers) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "validateMultipleULDFormats");
		return despatchRequest("validateMultipleULDFormats", companyCode,
				uldNumbers);
	}

	/**
	 * This method is used to update ULD Repair InvoiceDetails (waived amount and remarks)
	 * @param uLDRepairInvoiceDetailsVOs
	 * @throws BusinessDelegateException
	 */
	public void updateULDRepairInvoiceDetails(
			Collection<ULDRepairInvoiceDetailsVO> uLDRepairInvoiceDetailsVOs)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "updateULDRepairInvoiceDetails");
		despatchRequest("updateULDRepairInvoiceDetails",
				uLDRepairInvoiceDetailsVOs);
		log.exiting("ULDDefaultsDelegate", "updateULDRepairInvoiceDetails");
	}

	/**
	 * This method is used to Monitor ULD stock
	 * @author A-1883
	 * @param uLDStockConfigFilterVO
	 * @param displayPage
	 * @return Page<ULDStockListVO>
	 * @throws BusinessDelegateException
	 */
	public Page<ULDStockListVO> findULDStockList(
			ULDStockConfigFilterVO uLDStockConfigFilterVO, int displayPage)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDStockList");
		return despatchRequest("findULDStockList", uLDStockConfigFilterVO,
				displayPage);
	}

	/**
	 * This method is used to find the Agreement Details For the Transaction
	 * @param uldTransactionDetailsVos
	 * @throws BusinessDelegateException
	 * @return
	 */
	public Collection<String> findAgreementNumberForTransaction(
			Collection<ULDTransactionDetailsVO> uldTransactionDetailsVos)
			throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE ",
				"findAgreementNumberForTransaction");
		log.entering("INSIDE THE DELEGATE ",
				"findAgreementNumberForTransaction");
		return despatchRequest("findAgreementNumberForTransaction",
				uldTransactionDetailsVos);
	}

	/**
	 * @author A-3278
	 *  for QF1015 on 24Jul08
	 *  to find the total demmurage calculated
	 * @param TransactionFilterVO
	 * @return ULDTransactionDetailsVO
	 * @throws BusinessDelegateException
	 */
	public ULDTransactionDetailsVO findTotalDemmurage(
			TransactionFilterVO transactionFilterVO)
			throws BusinessDelegateException {
		log.entering("INSIDE ULD Defaults Delegate", "findTotalDemmurage");
		return despatchRequest("findTotalDemmurage", transactionFilterVO);
	}

	/**
	 *
	 * @param ucmMessageVO
	 * @throws BusinessDelegateException
	 */
	public void generateUCMMessage(UCMMessageVO ucmMessageVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "generateUCMMessage");
		log.log(Log.INFO, "%%%%%%%%%%%%  ucmMessageVO ", ucmMessageVO);
		despatchRequest("generateUCMMessage", ucmMessageVO);
	}

	/**
	 *
	 * @param scmMessageVO
	 * @throws BusinessDelegateException
	 */
	public void generateSCMMessage(SCMMessageVO scmMessageVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "generateSCMMessage");
		log.log(Log.INFO, "%%%%%%%%%%%%  scmMessageVO ", scmMessageVO);
		despatchRequest("generateSCMMessage", scmMessageVO);
	}

	/**
	 *
	 * @param lucMessageVO
	 * @throws BusinessDelegateException
	 */
	public Collection<MessageVO> generateLUCMessage(LUCMessageVO lucMessageVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "generateLUCMessage");
		log.log(Log.INFO, "%%%%%%%%%%%%  lucMessageVO", lucMessageVO);
		return despatchRequest("generateLUCMessage", lucMessageVO);
	}

	/**
	 *
	 * @param mucMessageVO
	 * @throws BusinessDelegateException
	 */
	public void generateMUCMessage(MUCMessageVO mucMessageVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "generateMUCMessage");
		log.log(Log.INFO, "%%%%%%%%%%%%  mucMessageVO", mucMessageVO);
		despatchRequest("generateMUCMessage", mucMessageVO);
	}

	/**
	 *
	 * @param uldDiscrepancyVOs
	 * @throws BusinessDelegateException
	 */
	public String saveULDDiscrepencyDetails(
			Collection<ULDDiscrepancyVO> uldDiscrepancyVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveULDDiscrepencyDetails");
		log.log(Log.INFO, "%%%%%%%%%%%%%%%%Collection<ULDDiscrepancyVO> ",
				uldDiscrepancyVOs);
		return despatchRequest("saveULDDiscrepencyDetails", uldDiscrepancyVOs);
	}

	/**
	 *
	 * @param discrepancyFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ULDDiscrepancyVO> listULDDiscrepancyDetails(
			ULDDiscrepancyFilterVO discrepancyFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDDiscrepancyDetails");
		log.log(Log.INFO, "%%%%%%%%%%%%%%%%ULDDiscrepancyFilterVO ",
				discrepancyFilterVO);
		Page<ULDDiscrepancyVO> descrepanyDetails = despatchRequest(
				"listULDDiscrepancyDetails", discrepancyFilterVO);
		// Added by A-5235 for ICRD-25691 begin
		if(descrepanyDetails != null && descrepanyDetails.size()>0){
			for (ULDDiscrepancyVO discrepancyVO : descrepanyDetails) {
				log.log(Log.INFO, "%%%%%%%%%%%%%%%%discrepancyVO ",
						discrepancyVO);
			}
		}
		// ICRD-25691 end
		return descrepanyDetails;
	}

	/**
	 *
	 * @param scmMessageVO
	 * @throws BusinessDelegateException
	 */
	public void sendSCMForDiscrepancyResolution(SCMMessageVO scmMessageVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "sendSCMForDiscrepancyResolution");
		log.log(Log.INFO, "%%%%%%% scmMessageVO", scmMessageVO);
		despatchRequest("sendSCMForDiscrepancyResolution", scmMessageVO);
	}

	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws BusinessDelegateException
	 */
	public HashMap<String, Collection<String>> populateLocation(
			String companyCode, String airportCode)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "populateLocation");
		HashMap<String, Collection<String>> map = despatchRequest(
				"populateLocation", companyCode, airportCode);

			if (map != null) {
				log.log(Log.FINE, "MAP NOT NULL");
				Set<String> keys = map.keySet();
				for (String key : keys) {
				log.log(Log.FINE, "key ------- ", key);
					log.log(Log.FINE, "value = ", map.get(key));
				}
		} else {
				log.log(Log.FINE, "MAP is NULL");
			}
			return map;

	}

	/**
	 *
	 * @param uldVOs
	 * @throws BusinessDelegateException
	 *
	 */
	public void setLocationForULD(Collection<ULDVO> uldVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "populateLocation");
		despatchRequest("setLocationForULD", uldVOs);
	}

	/**
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public ULDDamagePictureVO findULDDamagePicture(String companyCode,
			String uldNumber, long sequenceNumber)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDDamagePicture");
		return despatchRequest("findULDDamagePicture", companyCode, uldNumber,
				sequenceNumber);
	}

	public ULDDamagePictureVO fetchULDDamagePicture(String companyCode,
			String uldNumber, long sequenceNumber, int imageSequenceNumber)
		    throws BusinessDelegateException{
		log.entering("ULDDefaultsDelegate", "fetchULDDamagePicture");
		return despatchRequest("fetchULDDamagePicture", companyCode, uldNumber,
				sequenceNumber,imageSequenceNumber);
	}
	/**
	 *
	 * @param companyCode
	 * @param comapnyCode
	 * @param uldNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Integer findOperationalAirlineForULD(String companyCode,
			String uldNumber) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findOperationalAirlineForULD");
		return despatchRequest("findOperationalAirlineForULD", companyCode,
				uldNumber);
	}

	/**
	 * @author A-1950
	 *
	 * @param flightDetailsVO
	 * @throws BusinessDelegateException
	 */
	public void recordULDMovement(FlightDetailsVO flightDetailsVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "recordULDMovement");
		despatchRequest("recordULDMovement", flightDetailsVO);
	}

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public UCMMessageVO generateUCMMessageVO(FlightMessageFilterVO filterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "generateUCMMessageVO");
		return despatchRequest("generateUCMMessageVO", filterVO);
	}

	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @param uldNumbers
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ULDVO> validateULDs(String companyCode,
			String airportCode, Collection<String> uldNumbers)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "validateULDs");
		return despatchRequest("validateULDs", companyCode, airportCode,
				uldNumbers);
	}


	/**
	 * @author A-2667
	 * @param FlightDetailsVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public  void validateULDsForOperation(FlightDetailsVO flightDetailsVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "validateULDs");
		despatchRequest("validateULDsForOperation", flightDetailsVO);
	}

	/**
	 *
	 * @param flightMessageFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public FlightDetailsVO findUCMFlightDetails(
			FlightMessageFilterVO flightMessageFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findUCMFlightDetails");
		return despatchRequest("findUCMFlightDetails", flightMessageFilterVO);
	}

	/**
	 *
	 * @param locationVOs
	 * @throws BusinessDelegateException
	 */
	public void saveULDAirportLocation(
			Collection<ULDAirportLocationVO> locationVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveULDAirportLocation");
		despatchRequest("saveULDAirportLocation", locationVOs);
	}

	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @param facilityType
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ULDAirportLocationVO> listULDAirportLocation(
			String companyCode, String airportCode, String facilityType)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDAirportLocation");
		return despatchRequest("listULDAirportLocation", companyCode,
				airportCode, facilityType);
	}

	/**
	 *
	 * @param uldCheckinVOs
	 * @throws BusinessDelegateException
	 */
	public void saveAndCheckinULD(Collection<ULDCheckinVO> uldCheckinVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveAndCheckinULD");
		despatchRequest("saveAndCheckinULD", uldCheckinVOs);
	}

    /**
	 * This method validates the flight to check if the flight exists and returns
	 * the flight specific details.
	 * @param flightFilterVo
	 * @throws BusinessDelegateException
	 * @return Collection<FlightValidationVO>

	 */
	public Collection<FlightValidationVO> fetchFlightDetails(
			FlightFilterVO flightFilterVo) throws BusinessDelegateException {
		return despatchRequest("fetchFlightDetails", flightFilterVo);
    }

	/**
	 *
	 * @param reconcileVOs
	 * @throws BusinessDelegateException
	 */
	public Collection<ULDFlightMessageReconcileVO> saveULDFlightMessageReconcile(
			Collection<ULDFlightMessageReconcileVO> reconcileVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveULDFlightMessageReconcile");
		return despatchRequest("saveULDFlightMessageReconcile", reconcileVOs);
	}

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public ULDFlightMessageReconcileVO listUCMMessage(
			FlightFilterMessageVO filterVO) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listUCMMessage");
		return despatchRequest("listUCMMessage", filterVO);
	}

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ULDFlightMessageReconcileVO> listUCMErrors(
			FlightFilterMessageVO filterVO) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listUCMErrors");
		return despatchRequest("listUCMErrors", filterVO);
    }

    /**
     * @author A-1950
     * @param filterVO
     * @return
     * @throws BusinessDelegateException
     */
    public Collection<String> findUCMNoLOV(FlightFilterMessageVO filterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findUCMNoLOV");
		return despatchRequest("findUCMNoLOV", filterVO);
    }

    /**
     *
     * @param filterVO
     * @return
     * @throws BusinessDelegateException
     */
    public Page<ULDFlightMessageReconcileDetailsVO> listUldErrors(
			FlightFilterMessageVO filterVO) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listUldErrors");
		return despatchRequest("listUldErrors", filterVO);
    }

    /**
     * @author A-1950
     *
     * @param flightFilterVO
     * @return
     * @throws BusinessDelegateException
     */
	public Collection<ULDFlightMessageReconcileVO> listUCMsForComparison(
			FlightFilterMessageVO flightFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listUCMsForComparison");
		return despatchRequest("listUCMsForComparison", flightFilterVO);
	}

	
	
    /**
     *
     * @param filterVO
     * @return
     * @throws BusinessDelegateException
     */
    public Collection<ULDFlightMessageReconcileDetailsVO> listUCMINOUTMessage(
			FlightFilterMessageVO filterVO) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listUCMINOUTMessage");
		return despatchRequest("listUCMINOUTMessage", filterVO);
    }

    /**
     *
     * @param uldReconcileDetailsVO
     * @throws BusinessDelegateException
     */
	public String reconcileUCMULDError(
			ULDFlightMessageReconcileDetailsVO uldReconcileDetailsVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "ULDisAddedToTheStock");
		return despatchRequest("reconcileUCMULDError", uldReconcileDetailsVO);
	}

    /**
     *
     * @param flightFilterVO
     * @return
     * @throws BusinessDelegateException
     */
    public Collection<ULDFlightMessageReconcileDetailsVO> listUCMOUTForInOutMismatch(
			FlightFilterMessageVO flightFilterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listUCMOUTForInOutMismatch");
		return despatchRequest("listUCMOUTForInOutMismatch", flightFilterVO);
    }

    /**
     *
     * @param companyCode
     * @param uldNumber
     * @return
     * @throws BusinessDelegateException
     */
	public String findULDCurrentStation(String companyCode, String uldNumber)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDCurrentStation");
		return despatchRequest("findULDCurrentStation", companyCode, uldNumber);
    }

    /**
     *
     * @param companyCode
     * @param uldNumber
     * @throws BusinessDelegateException
     */
	public void updateULDTransitStatus(String companyCode, String uldNumber)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "updateULDTransitStatus");
		despatchRequest("updateULDTransitStatus", companyCode, uldNumber);
    }

	/**
	 *
	 * @param reconcileVOs
	 * @throws BusinessDelegateException
	 */
	public Collection<ULDFlightMessageReconcileVO> saveINOUTMessage(
			Collection<ULDFlightMessageReconcileVO> reconcileVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveINOUTMessage");
		return despatchRequest("saveINOUTMessage", reconcileVOs);
	}

    /**
     *
     * @param filterVO
     * @return
     * @throws BusinessDelegateException
     */
    public Page<ULDVO> findUldDetailsForSCM(SCMMessageFilterVO filterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findUldDetailsForSCM");
		return despatchRequest("findUldDetailsForSCM", filterVO);

    }

    /**
     *
     * @param reconcileVOs
     * @param additionaldespatchDetailsVOs 
     * @return
     * @throws BusinessDelegateException
     */
    public HashMap<String,Collection<MessageVO>> saveSCMMessage(Collection<ULDSCMReconcileVO> reconcileVOs,ULDListFilterVO uldListFilterVO, Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveSCMMessage");
		return despatchRequest("saveSCMMessage", reconcileVOs,uldListFilterVO,additionaldespatchDetailsVOs);
    }
    /**
    *
    * @param reconcileVOs
    * @return
    * @throws BusinessDelegateException
    */
   public String saveSCMReconcialtionDetails(Collection<ULDSCMReconcileVO> reconcileVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveSCMReconcialtionDetails");
		return despatchRequest("saveSCMReconcialtionDetails", reconcileVOs);
   }
    
   
    /**
     *
     * @param filterVO
     * @return
     * @throws BusinessDelegateException
     */
    public Page<ULDSCMReconcileVO> listSCMMessage(SCMMessageFilterVO filterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listSCMMessage");
		return despatchRequest("listSCMMessage", filterVO);
    }

    /**
     *
     * @param filterVO
     * @return
     * @throws BusinessDelegateException
     */
	public Page<ULDSCMReconcileDetailsVO> listULDErrorsInSCM(
			SCMMessageFilterVO filterVO) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDErrorsInSCM");
		return despatchRequest("listULDErrorsInSCM", filterVO);
    }

    /**
     *
     * @param flightMessageReconcileVO
     * @throws BusinessDelegateException
     */
    public void sendUCM(ULDFlightMessageReconcileVO  flightMessageReconcileVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "sendUCM");
		despatchRequest("sendUCM", flightMessageReconcileVO);
    }

    /**
     *
     * @param filterVO
     * @return
     * @throws BusinessDelegateException
     */
	public String findCurrentAirportForULD(SCMMessageFilterVO filterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findCurrentAirportForULD");
		return despatchRequest("findCurrentAirportForULD", filterVO);
    }

    /**
     * @author A-1950
     * @param uldDetails
     * @throws BusinessDelegateException
     */
	public void deleteUCMULDDetails(
			Collection<ULDFlightMessageReconcileDetailsVO> uldDetails)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "deleteUCMULDDetails");
		despatchRequest("deleteUCMULDDetails", uldDetails);
    }

    /**
     *
     * @param uldDetails
     * @throws BusinessDelegateException
     */
	public void updateUCMULDDetails(
			Collection<ULDFlightMessageReconcileDetailsVO> uldDetails)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "updateUCMULDDetails");
		despatchRequest("updateUCMULDDetails", uldDetails);
	}

	/**
	 *
	 * @param uldDetails
	 * @throws BusinessDelegateException
	 */
	public void removeErrorCodeForULDsInSCM(
			Collection<ULDSCMReconcileDetailsVO> uldDetails)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "removeErrorCodeForULDsInSCM");
		despatchRequest("removeErrorCodeForULDsInSCM", uldDetails);
	}

    /**
     *
     * @param filterVO
     * @throws BusinessDelegateException
     */
    public Collection<MessageVO> sendSCMMessage(SCMMessageFilterVO filterVO, Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "sendSCMMessage");
		return despatchRequest("sendSCMMessage", filterVO,additionaldespatchDetailsVOs);
    }

    /**
     *
     * @param uldDetails
     * @throws BusinessDelegateException
     */
    public void deleteULDsInSCM(Collection<ULDSCMReconcileDetailsVO> uldDetails)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "deleteULDsInSCM");
		despatchRequest("deleteULDsInSCM", uldDetails);
    }

    /**
     *
     * @param poolOwnerVOs
     * @throws BusinessDelegateException
     */
    public void saveULDPoolOwner(Collection<ULDPoolOwnerVO> poolOwnerVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveULDPoolOwner");
		despatchRequest("saveULDPoolOwner", poolOwnerVOs);
    }

    /**
     *
     * @param filterVO
     * @return
     * @throws BusinessDelegateException
     */
    public Collection<ULDPoolOwnerVO> listULDPoolOwner(ULDPoolOwnerFilterVO filterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDPoolOwner");
		return despatchRequest("listULDPoolOwner", filterVO);
    }

    /**
     *
     * @param filterVO
     * @return
     * @throws BusinessDelegateException
     */
    public Page<ULDSCMReconcileVO> listSCMMessageLOV(SCMMessageFilterVO filterVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listSCMMessageLOV");
		return despatchRequest("listSCMMessageLOV", filterVO);
    }

    /**
     * @param flightDetailsVO
     * @throws BusinessDelegateException
     */
    public void saveFlightMessage(FlightDetailsVO flightDetailsVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveFlightMessage");
		despatchRequest("saveFlightMessage", flightDetailsVO);
    }

    /***
     *
     * @param cpmMessageVO
     * @throws BusinessDelegateException
     */
	public void saveCPMDetails(CPMMessageVO cpmMessageVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveCPMDetails");
		despatchRequest("saveCPMDetails", cpmMessageVO);

	}

	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @param airlineIdentifier
	 * @param uldIdentifier
	 * @param date
	 * @throws BusinessDelegateException
	 */
	public void generateSCMFromMonitorULD(String companyCode,
			ULDStockListVO uLDStockListVO
			) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "generateSCMFromMonitorULD");
		despatchRequest("generateSCMFromMonitorULD", companyCode,uLDStockListVO);

	}

	/***
	 *
	 * @param uldReturnTxnVOs
	 * @throws BusinessDelegateException
	 */
	public void returnULDFromOperations(
			Collection<ULDReturnTxnVO> uldReturnTxnVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "returnULDFromOperations");
		despatchRequest("returnULDFromOperations", uldReturnTxnVOs);

	}

	/**
	 *
	 * A-1950
	 * @param uldValidationVOs
	 * @throws BusinessDelegateException
	 */
	public void validateULDForWarehouseOccupancy(
			Collection<ULDValidationVO> uldValidationVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "validateULDForWarehouseOccupancy");
		despatchRequest("validateULDForWarehouseOccupancy", uldValidationVOs);
	}

	/**
	 *
	 * A-1950
	 * @param uldValidationVOs
	 * @throws BusinessDelegateException
	 */
	public void checkForULDInOperation(
			Collection<ULDValidationVO> uldValidationVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "checkForULDInOperation");
		despatchRequest("checkForULDInOperation", uldValidationVOs);
	}

	




   /**
    *
    * A-1950
    * @param flightDetailsVO
    * @throws SystemException
    */
    public void updateULDForOperations(FlightDetailsVO flightDetailsVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "updateULDForOperations");
		despatchRequest("updateULDForOperations", flightDetailsVO);
    }

  

	/**
	 *
	 * A-1950
	 * @param discrepancyVO
	 * @throws BusinessDelegateException
	 */
   public void captureMissingULD(ULDDiscrepancyVO discrepancyVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "captureMissingULD");
		despatchRequest("captureMissingULD", discrepancyVO);
   }

   /**
    *
    * A-1950
    * @param discrepancyVO
    * @throws BusinessDelegateException
    */
   public void updateULDInventory(ULDDiscrepancyVO discrepancyVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "updateULDInventory");
		despatchRequest("updateULDInventory", discrepancyVO);
  }

   /**
    * @author A-1936
    * This method is used to save the Exception Flights(i.e Fligts for which the UCM has to be sent manually) ..
    * @param ucmExceptionFlightVos.
    * @throws BusinessDelegateException.
    */
	public void saveExceptionFlights(
			Collection<UCMExceptionFlightVO> ucmExceptionFlightVos)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveExceptionFlights");
		despatchRequest("saveExceptionFlights", ucmExceptionFlightVos);

   }

   /**
    * @author A-1936
    * This method is used to find the Exception Flights(i.e Fligts for which the UCM has to be sent manually) ..
    * @param ucmExceptionFlightVos.
    * @throws BusinessDelegateException.
    */
	public Collection<UCMExceptionFlightVO> findExceptionFlights(
			String companyCode, String pol) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveExceptionFlights");
		return despatchRequest("findExceptionFlights", companyCode, pol);
   }

   /**
    * the method is used to pick the nature , damage status , currentairport etc
    * A-1950
    * @param companyCode
    * @param uldNumber
    * @return
    * @throws BusinessDelegateException
    */
	public ULDListVO fetchULDDetailsForTransaction(String companyCode,
			String uldNumber) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "fetchULDDetailsForTransaction");
		return despatchRequest("fetchULDDetailsForTransaction", companyCode,
				uldNumber);
}

   /**
    *
    * A-1950
    * @param companyCode
    * @param carrierCode
    * @return
    * @throws BusinessDelegateException
    */
	public String findCRNForULDTransaction(String companyCode,
			String carrierCode) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findCRNForULDTransaction");
		return despatchRequest("findCRNForULDTransaction", companyCode,
				carrierCode);
	}

	/**
	 * @author A-2619
	 * This method is used to list the ULD History
	 * @return Page
	 * @param uldHistoryVO.
	 * @throws BusinessDelegateException.
	 */
	public Page<ULDHistoryVO> listULDHistory(ULDHistoryVO uldHistoryVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDHistory");
		return despatchRequest("listULDHistory", uldHistoryVO);
	}

	/* Added by A-2412 on 18th Oct for Editable CRN cr */
	public Collection<String> checkForDuplicateCRN(String companyCode,TransactionVO transactionVO) throws BusinessDelegateException {
	 log.entering("ULDDefaultsDelegate", "checkForDuplicateCRN");
	 return despatchRequest("checkForDuplicateCRN",companyCode,transactionVO);
	 }
	/* Addition by A-2412 on 18th Oct for Editable CRN cr ends */


	/**
	 *
	 * @param deleteULDMovement
	 * @throws BusinessDelegateException
	 */
	public void deleteULDMovements(Collection<ULDMovementDetailVO> uldMovementDetails)
	throws BusinessDelegateException{
		log.entering("ULDDefaultsDelegate","deleteULDMovements");
		despatchRequest("deleteULDMovements",uldMovementDetails);
	}
	/**
	 * @param reconcileVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<MessageVO> encodeSCMMessage(ULDSCMReconcileVO reconcileVO)
	throws BusinessDelegateException{
		return despatchRequest("encodeSCMMessage",reconcileVO);
	}

	/**
	 * This method is for sending MUC
	 * @param transactionDetailsVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ULDTransactionDetailsVO> sendMUCMessage(Collection<ULDTransactionDetailsVO> transactionDetailsVOs)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "sendMUCMessage");
		return despatchRequest("sendMUCMessage",transactionDetailsVOs);
	}


	/**
	 * This method is Defaulting Airline Code in a few pages.Added as a part of
	 * @param transactionDetailsVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String findDefaultAirlineCode(String companyCode,String userCode)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findDefaultAirlineCode");
		return despatchRequest("findDefaultAirlineCode",companyCode,userCode);
	}

	/**
	 * This method retrieves the carrier details of the specified filter condition
	 * @author A-2883
	 * @param  handledCarrierSetupVO
	 * @return Collection<HandledCarrierSetupVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<ULDHandledCarrierVO> findHandledCarrierSetup(
			ULDHandledCarrierVO handledCarrierSetupVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findhandledcarriersetup");
		log.log(Log.FINE, "HandledCarrierSetupVO--->", handledCarrierSetupVO);
				return despatchRequest("findHandledCarrierSetup",
						handledCarrierSetupVO);
	}


	/**
	 * This method updates the carrier details
	 * @author A-2883
	 * @param  handledCarrierSetupVO
	 * @throws BusinessDelegateException
	 */
	public void saveHandledCarrier(
			Collection<ULDHandledCarrierVO> handledCarrierVO)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveHandledCarrier");
		despatchRequest("saveHandledCarrier", handledCarrierVO);
	}
	/**
	 * @param companyCode
	 * @param twoAlphaCode
	 * @param threeAlphaCode
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String findOwnerCode(String companyCode, String twoAlphaCode,
			String threeAlphaCode) throws BusinessDelegateException{
		return despatchRequest("findOwnerCode",companyCode,twoAlphaCode,threeAlphaCode);
	}

	/**
	 *
	 * @param reconcileVOs
	 * @throws BusinessDelegateException
	 */
	@AvoidForcedStaleDataChecks
	public void processUCMMessage(
			Collection<ULDFlightMessageReconcileVO> reconcileVOs) throws BusinessDelegateException{
		log.entering("ULDDefaultsDelegate", "processUCMMessage");
		despatchRequest("processUCMMessage",reconcileVOs);
	}



    /**


	 * This method deletes the accessory details
	 * @author A-2412
	 * @param accessoryTransactionVos
	 * @throws BusinessDelegateException
	 */
	public void deleteAccessoryDetails(
			Collection<AccessoryTransactionVO> accessoryTransactionVos)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "deleteAccessoryDetails");
		despatchRequest("deleteAccessoryDetails", accessoryTransactionVos);
	}

	/**
	 * This method returns the ULDStockStatus for HHT
	 * @author A-2667
	 * @param uLDDiscrepancyFilterVO
	 * @return ULDDiscrepancyVO
	 * @throws BusinessDelegateException
	 */
	public ULDDiscrepancyVO findULDStockStatusForHHT(ULDDiscrepancyFilterVO
			uLDDiscrepancyFilterVO)throws BusinessDelegateException{
		log.entering("ULDDefaultsDelegate", "findULDStockStatusForHHT");
		return despatchRequest("findULDStockStatusForHHT", uLDDiscrepancyFilterVO);
	}

	/**
	 * @author A-2667
	 * @param companyCode
	 * @param displayPage
	 * @param comboValue
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ULDDiscrepancyVO> populateLocationLov(String companyCode,
			int displayPage,String comboValue,String airportCode)throws BusinessDelegateException{
		log.entering("ULDDefaultsDelegate", "populateLocationLov");
		return despatchRequest("populateLocationLov", companyCode,displayPage,comboValue,airportCode);
	}

	/**
	 * @author A-2412
	 * @param uldIntMvtFilterVO
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ULDIntMvtDetailVO> findIntULDMovementHistory(
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO, int pageNumber)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findIntULDMovementHistory");
		return despatchRequest("findIntULDMovementHistory", uldIntMvtFilterVO,
				pageNumber);
	}
	

	/**
	 * @author A-2667
	 * @param relocateULDVO
	 * @return Collection<AuditDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<AuditDetailsVO> findULDAuditEnquiryDetails(
			   RelocateULDVO relocateULDVO)throws BusinessDelegateException{
		log.entering("ULDDefaultsDelegate", "findULDAuditEnquiryDetails");
		return despatchRequest("findULDAuditEnquiryDetails",relocateULDVO);
	}

	/**
	 * @author A-1950
	 * @param uldDiscrepancyVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String updateULDStockStatusForHHT(ULDDiscrepancyVO uldDiscrepancyVO)
	throws BusinessDelegateException{
		return despatchRequest("updateULDStockStatusForHHT",uldDiscrepancyVO);
	}

	/**
    * @author A-3459
    * @param companyCode
	* @param section
    * @param uldDamageChecklistVOs
    * @return
    * @throws BusinessDelegateException
    */
   public Collection<ULDDamageChecklistVO> listULDDamageChecklistMaster(String companyCode,String section)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDDamageChecklistMaster");
		return despatchRequest("listULDDamageChecklistMaster",companyCode,section );
   }

   /**
    * @author A-3459
    *
    * @param uldDamageChecklistVOs
    * @throws BusinessDelegateException
    */
   public void saveULDDamageChecklistMaster(Collection<ULDDamageChecklistVO> uldDamageChecklistVOs)
   		throws BusinessDelegateException{
   		   log.entering("ULDDefaultsDelegate","saveULDDamageChecklistMaster");
   		   despatchRequest("saveULDDamageChecklistMaster",uldDamageChecklistVOs);
      	}
   /**
    *  @author A-3459
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<WarehouseVO> findAllWarehousesforULD(String companyCode,
				String airportCode) throws BusinessDelegateException{
		 return despatchRequest("findAllWarehousesforULD",companyCode,airportCode);
	 }

	/**
	 * @author a-2883
	 * @param inventoryULDVO
	 * @return Page<InventoryULDVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<InventoryULDVO> listInventoryDetails(
		InventoryULDVO inventoryULDVO)
		throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listInventoryDetails");
		return despatchRequest("listInventoryDetails", inventoryULDVO);
	}

	/**
	 * @author a-2883
	 * @param inventoryULDVOs
	 * @throws BusinessDelegateException
	 */
	public void updateInventoryDetails(
		Collection<InventoryULDVO> inventoryULDVOs)
		throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "updateInventoryDetails");
		despatchRequest("updateInventoryDetails",inventoryULDVOs);
	}

	/**
	 * @author a-2883
	 * @return List<InventoryULDVO>
	 * @param filterVO
	 * @throws BusinessDelegateException
	 */
	public List<InventoryULDVO> findFlightLegULDDetails(
		FlightScheduleFilterVO filterVO)
		throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findFlightLegULDDetails");
		return despatchRequest("findFlightLegULDDetails",filterVO);
	}
	/**
	 * @author a-3429
	 * @return ULDNumberVO
	 * @param uLDMovementFilterVO
	 * @throws BusinessDelegateException
	 */
	public ULDNumberVO findULDHistoryCounts(
			ULDMovementFilterVO uLDMovementFilterVO)
		throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDHistoryCounts");
		return despatchRequest("findULDHistoryCounts",uLDMovementFilterVO);
	}
	//added by jisha for qf1022 starts
	/**
	 * @author a-3093
	 * for QF1022
	 * @param uldRepairFilterVO
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ULDRepairDetailsListVO> listDamageRepairDetails(
			UldDmgRprFilterVO uldRepairFilterVO,int pageNumber)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listDamageRepairDetails");
		log.log(Log.INFO, "!!!!uldRepairFilterVO", uldRepairFilterVO);

		return despatchRequest("listDamageRepairDetails", uldRepairFilterVO, pageNumber);
		}
	/**
	 * @author a-3093
	 * for QF1022
	 * @param uldmovementFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<OperationalULDAuditVO> listBuildupBreakdowndetails(
			ULDMovementFilterVO uldmovementFilterVO) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listBuildupBreakdowndetails");
		log.log(Log.INFO, "!!!!uldmovementFilterVO", uldmovementFilterVO);
		return despatchRequest("listBuildupBreakdowndetails", uldmovementFilterVO);

	}
	/**
	 * @author a-3093
	 * @param uldmovementFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<AuditDetailsVO> findULDActionHistory(
			ULDMovementFilterVO uldmovementFilterVO) throws BusinessDelegateException
			{
		log.entering("ULDDefaultsServicesEJB", "findULDActionHistory");
		return despatchRequest("findULDActionHistory", uldmovementFilterVO);
			}
	//added by jisha for qf1022 ends


	 /**
    *
    * @param reconcileVOs
	 * @param additionaldespatchDetailsVOs 
    * @return
    * @throws BusinessDelegateException
    */
   public HashMap<String,Collection<MessageVO>> sendSCMMessageforUlds(Collection<ULDSCMReconcileVO> reconcileVOs, Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "sendSCMMessage");
		return despatchRequest("sendSCMMessageforUlds", reconcileVOs,additionaldespatchDetailsVOs);
   }
   /**
    * This method is used to find the list of Missing UCM OUT received and UCM IN received
	 * @author a-3459
	 * for QF1021
	 * @param uldFlightMessageReconcileVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ULDFlightMessageReconcileVO> findMissingUCMs(
			ULDFlightMessageReconcileVO uldFlightMessageReconcileVO) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findMissingUCMs");
		log.log(Log.INFO, "!!!!ULDFlightMessageReconcileVO",
				uldFlightMessageReconcileVO);
		return despatchRequest("findMissingUCMs", uldFlightMessageReconcileVO);

	}
   //added by a-3045 for CR QF1013 starts
	/**
	 * For MUCRefNo LOV
	 * @author A-3045
	 * @param companyCode
	 * @param displayPage
	 * @param mucRefNum
	 * @param mucDate
	 * @return Page<String>
	 * @throws BusinessDelegateException
	 */
	public Page<String> findMUCRefNumberLov(String companyCode,
			int displayPage, String mucRefNum, String mucDate) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findMUCRefNumberLov");
		return despatchRequest("findMUCRefNumberLov", companyCode,
				displayPage, mucRefNum, mucDate);
	}
	/**
	 * For findMUCAuditDetails
	 * @author A-3045
	 * @param companyCode
	 * @param mucReferenceNumber
	 * @param MucDate
	 * @param controlReceiptNumber
	 * @return Collection<ULDConfigAuditVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<ULDConfigAuditVO> findMUCAuditDetails(ULDTransactionDetailsVO uldTransactionDetailsVO)
		throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findMUCRefNumberLov");
		return despatchRequest("findMUCAuditDetails", uldTransactionDetailsVO);
	}
	/**
	 * For updateULDTransaction
	 * @author A-3045
	 * @param uldTransactionDetailsVOs
	 * @return void
	 * @throws BusinessDelegateException
	 */
	public void updateULDTransaction(Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
		throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "updateULDTransaction");
		despatchRequest("updateULDTransaction", uldTransactionDetailsVOs);
	}
	  //added by a-3045 for CR QF1013 ends
	//added by a-3045 for CR QF1142 starts
	/**
	 * For updateMUCStatus
	 * @author A-3045
	 * @param uldTransactionDetailsVOs
	 * @return void
	 * @throws BusinessDelegateException
	 */
	public void updateMUCStatus(Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs)
		throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "updateMUCStatus");
		despatchRequest("updateMUCStatus", uldTransactionDetailsVOs);
	}
	//added by a-3045 for CR QF1142 ends

	public void markULDFlightMovements(FlightDetailsVO flightDetailsVO)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "markULDFlightMovements");
		despatchRequest("markULDFlightMovements", flightDetailsVO);
	}

	//added by a-3045 for bug17959 starts
	//for finding the ULDs in use
	/**
	 * For checkULDInUse
	 * @author A-3045
	 * @param uldNumbers
	 * @return Collection<String>
	 * @throws BusinessDelegateException
	 */
	public Collection<String> checkULDInUse(String companyCode,Collection<String> uldNumbers)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "checkULDInUse");
		return despatchRequest("checkULDInUse", companyCode, uldNumbers);
	}

	/**
	 * Added by Asharaf Binu P
	 * @param companyCode
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void sendAlertForULDStockDeviation(String companyCode)
	throws BusinessDelegateException {
		despatchRequest("sendAlertForULDStockDeviation", companyCode);
	}

	 /**
    * @a-3459
    * @param uldPoolOwnerFilterVO
    * @return
    * @throws BusinessDelegateException
    */
    public boolean  checkforPoolOwner(ULDPoolOwnerFilterVO uldPoolOwnerFilterVO)
	throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "checkforPoolOwner");
		Boolean isValid = despatchRequest("checkforPoolOwner", uldPoolOwnerFilterVO);
		return isValid.booleanValue();
	}

	/**
	 * This method is used for listing Transaction details in ULD History screen
	 * @author A-3045
	 *@param  uldTransactionFilterVO
	 *@return TransactionListVO
	 *@throws BusinessDelegateException
	 */
	public TransactionListVO findTransactionHistory(
			TransactionFilterVO uldTransactionFilterVO)
			throws BusinessDelegateException {
		log.entering("INSIDE ULD Defaults Delegate",
				"findTransactionHistory");
		return despatchRequest("findTransactionHistory",
				uldTransactionFilterVO);
	}

	/**
	 * This method retrieves the uld details of the specified filter condition for SCM
	 * @author A-3045
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ULDVO> findULDListForSCM(ULDListFilterVO uldListFilterVO,
			int pageNumber) throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "findULDListForSCM");
		return despatchRequest("findULDListForSCM", uldListFilterVO, pageNumber);
	}

	public Collection<ErrorVO> validateULDsForTransaction(
			TransactionVO transactionVO) throws BusinessDelegateException {
		log.entering("INSIDE ULD Defaults Delegate",
				"validateULDsForTransaction");
		return despatchRequest("validateULDsForTransaction", transactionVO);
	}

	/**
	 * This method retrieves the uld details of the specified filter condition for SCM validation screen at a particular for a specified period
	 * @author A-3459
	 * @param scmValidationFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<SCMValidationVO> findSCMValidationList(SCMValidationFilterVO scmValidationFilterVO)
		throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "findSCMValidationList");
		return despatchRequest("findSCMValidationList", scmValidationFilterVO);
	}

	/**
	 * @author a-3278
	 *To offload the ULDs from flight
	 * @param uldVos
	 * @throws BusinessDelegateException
	 */
	public void offloadULDs(Collection<ULDVO> uldVos)
	throws BusinessDelegateException {
		log.entering("INSIDE THE DELEGATE", "offloadULDs");
		despatchRequest("offloadULDs", uldVos);
	}

	/**
	 *
	 * @param companyCode
	 * @param partyType
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ULDServiceabilityVO> listULDServiceability(
			String companyCode, String partyType)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "listULDServiceability");
		return despatchRequest("listULDServiceability", companyCode, partyType);
	}

	/**
	 *
	 * @param serviceabilityVOs
	 * @throws BusinessDelegateException
	 */
	public void saveULDServiceability(
			Collection<ULDServiceabilityVO> serviceabilityVOs)
			throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "saveULDServiceability");
		despatchRequest("saveULDServiceability", serviceabilityVOs);
	}
	/**
	 * Method to validate flight segments
	 *
	 * @param flightFilterVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<FlightFilterVO> validateFlightsForSegments(
		Collection<FlightFilterVO> flightFilterVOs)
		throws BusinessDelegateException {
		log.entering("ULD Delegate","validateFlightsForSegments");
		return despatchRequest("validateFlightsForSegments",flightFilterVOs);
    }
	/**
	 * @author A-2934
	 * @param 
	 * @param 
	 * @return 
	 * @throws BusinessDelegateException 
	 * @throws BusinessDelegateException
	 */
	public boolean validateFlightArrival(FlightValidationVO flightValidationVO, ArrayList<String> destination) throws BusinessDelegateException {
		// To be reviewed Auto-generated method stub
		log.entering("ULDDefaultsDelegate", "validateFlightArrival");
		Boolean isValid = despatchRequest("validateFlightArrival", flightValidationVO, destination);
		return isValid.booleanValue();
	}
	/**
	 * Method will find the estimated stock of ulds in an airport
	 * @author A-2934
	 * @param EstimatedULDStockFilterVO
	 * @param pageNumber
	 * @return AccessoriesStockConfigVOs
	 * @throws BusinessDelegateException 
	 */
	public Page<EstimatedULDStockVO> findEstimatedULDStockList(
			EstimatedULDStockFilterVO estimatedULDStockFilterVO,
			int pageNumber) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findEstimatedULDStockList");
		log.log(Log.FINE, "estimatedULDStockFilterVO--->",
				estimatedULDStockFilterVO);
		log.log(Log.FINE, "pageNumber--->", pageNumber);
		return despatchRequest("findEstimatedULDStockList",
				estimatedULDStockFilterVO, pageNumber);
	}

	/**
	 * Method will find the estimated stock of ulds in an airport
	 * @author A-2934
	 * @param EstimatedULDStockFilterVO
	 * @param pageNumber
	 * @return AccessoriesStockConfigVOs
	 * @throws BusinessDelegateException 
	 */
	public Page<ExcessStockAirportVO> findExcessStockAirportList(
			ExcessStockAirportFilterVO excessStockAirportFilterVO,
			int pageNumber) throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findExcessStockAirportList");
		log.log(Log.FINE, "excessStockAirportFilterVO--->",
				excessStockAirportFilterVO);
		log.log(Log.FINE, "pageNumber--->", pageNumber);
		return despatchRequest("findExcessStockAirportList",
				excessStockAirportFilterVO, pageNumber);
	}

/**
 * To send SCM message from Uld Module
 * @author A-5125 
 * @param messageVOs
 * @throws BusinessDelegateException
 */
public void sendMessageWithEnvelopeEncoding(Collection<MessageVO> messageVOs)
    throws BusinessDelegateException{
	log.entering("ULDDefaultsDelegate", "sendMessageWithEnvelopeEncoding");
	log.log(Log.FINE, "MessageVOs--->", messageVOs);
	despatchRequest("sendMessageWithEnvelopeEncoding",messageVOs);
	}	
public Page<FlightSegmentForBookingVO> listFlightDetails(FlightAvailabilityFilterVO fltAvbFilterVO)throws BusinessDelegateException{
	log.entering("ULDDefaultsDelegate", "listFlightDetails");
	log.log(Log.FINE, "excessStockFilterVO--->", fltAvbFilterVO);
	return despatchRequest("listFlightDetails",fltAvbFilterVO);
	}	
	/**
	 * Added for ICRD-114538
	 * @author A-3415
	 * @param TransactionFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<String> checkIfOpenTransactionExists(Collection<TransactionFilterVO> filterVOs)throws BusinessDelegateException{
		log.entering("ULDDefaultsDelegate", "checkIfOpenTransactionExists");
		return despatchRequest("checkIfOpenTransactionExists",filterVOs);
	}
	/**
	 * 
	 * 	Method		:	ULDDefaultsDelegate.findMessageTypeAndVersion
	 *	Added by 	:	A-6841 on Jan 17, 2017
	 * 	Used for 	:	Finding the highest version of a specific message
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param messageType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	Collection<MessageRuleDefenitionVO>
	 */
	public Collection<MessageRuleDefenitionVO> findMessageTypeAndVersion(
			String companyCode, String messageType)
			throws BusinessDelegateException {
		return despatchRequest("findMessageTypeAndVersion", companyCode,
				messageType);
 }
	/**
	 * 
	 * 	Method		:	ULDDefaultsDelegate.findConsolidatedUCMsForFlight
	 *	Added by 	:	A-7359 on 07-Sep-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@param uldFlightMessageFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	ULDFlightMessageReconcileVO
	 */
	public ULDFlightMessageReconcileVO findConsolidatedUCMsForFlight(FlightFilterMessageVO uldFlightMessageFilterVO) throws BusinessDelegateException{
		log.entering("ULDDefaultsDelegate", "findConsolidatedUCMsForFlight");
		return despatchRequest("findConsolidatedUCMsForFlight", uldFlightMessageFilterVO);
	}
	

	
	/**
	 * 	Method		:	ULDDefaultsDelegate.findULDDamageRepairDetails
	 *	Added by 	:	A-7043 on 13-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param uldDamageFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	ULDDamageRepairDetailsVO
	 */
	public ULDDamageRepairDetailsVO findULDDamageRepairDetails(
			ULDDamageFilterVO uldDamageFilterVO)
	throws BusinessDelegateException {
		log.entering("ULDDefaultsDelegate", "findULDDamageRepairDetails");
		log.log(Log.INFO, "!!!!!!uldDamageFilterVO", uldDamageFilterVO);
		ULDDamageRepairDetailsVO damageVO = despatchRequest(
				"findULDDamageRepairDetails", uldDamageFilterVO);
		log.log(Log.INFO, "!!!!ULDDamageRepairDetailsVO", damageVO);
		return damageVO;
	}
	/**
	 * 
	 * 	Method		:	ULDDefaultsDelegate.updateMultipleULDDamageDetails
	 *	Added by 	:	A-8176 on 13-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param uldDamageRepairDetailsVOs
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	void
	 */
	public void updateMultipleULDDamageDetails(
			Collection<ULDDamageRepairDetailsVO> uldDamageRepairDetailsVOs)
			throws BusinessDelegateException {
		log.entering("updateMultipleULDDamageDetails",
				"updateMultipleULDDamageDetails");
		despatchRequest("updateMultipleULDDamageDetails",
				uldDamageRepairDetailsVOs);
	}
	/**
	 * 
	 * @param uldDamageFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 * Method to get all pictures associated with a damage
	 */
	public Collection<ULDDamagePictureVO> findULDDamagePictures(ULDDamageFilterVO uldDamageFilterVO) throws BusinessDelegateException{
		log.entering("findULDDamagePictures", "findULDDamagePictures");
		return despatchRequest("findULDDamagePictures",uldDamageFilterVO);
	}
}

