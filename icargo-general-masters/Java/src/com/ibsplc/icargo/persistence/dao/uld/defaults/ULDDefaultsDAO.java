/*
 * ULDDefaultsDAO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementExceptionVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageReferenceNumberLovVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHandledCarrierVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceVO;
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
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1496
 *
 */
public interface ULDDefaultsDAO {

    /**
     * This method retrieves the repair head details for invoicing.
     * @author A-1883
     * @param companyCode
     * @param invoiceRefNumber
     * @return ULDRepairInvoiceVO 
     * @throws SystemException
     */
	ULDRepairInvoiceVO findRepairInvoiceDetails(String companyCode,
			String invoiceRefNumber) throws SystemException;

    /**
     * This method retrieves the repair head details for invoicing.
     *
     * @param uldAgreementVO
     * @return Collection<ULDAgreementExceptionVO>
     * @throws SystemException
     */

	Collection<ULDAgreementExceptionVO> checkULDAgreementDetails(
			ULDAgreementVO uldAgreementVO) throws SystemException;

    /**
     * This method validates if the ULD exists in the system
     * @author A-1883
     * @param companyCode
     * @param uldNumber
     * @return ULDValidationVO
     * @throws SystemException
     */
    public ULDValidationVO validateULD(String companyCode, String uldNumber)
    throws SystemException;

    /**
     * This method is used to list the damage reports according
     * to the specified filter criteria
     *
     * @param uldDamageFilterVO
     * @return Collection<ULDDamageListVO>
     * @throws SystemException
     */
	Page<ULDDamageDetailsListVO> findULDDamageList(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException;

    /**
     *
     * @param companyCode
     * @param uldNumber
     * @param pageNumber
     * @return Page<ULDDamageReferenceNumberLovVO>
     * @throws SystemException
     */
	public Page<ULDDamageReferenceNumberLovVO> findULDDamageReferenceNumberLov(
			String companyCode, String uldNumber, int pageNumber)
    throws SystemException;

    /**
     *
     * @param uldRepairFilterVO
     * @return
     * @throws SystemException
     */
	Page<ULDRepairDetailsListVO> listULDRepairDetails(
			ULDRepairFilterVO uldRepairFilterVO) throws SystemException;

    /**
     *
     * @param companyCode
     * @param uldNumber
     * @return
     * @throws SystemException
     */
	ULDValidationVO findCurrentULDDetails(String companyCode, String uldNumber)
			throws SystemException;

    /**
     * This method is used to list the damage details for the
     * specified damage reference number for a particular ULD.
     *
     * @param uldDamageFilterVO
     * @return
     * @throws SystemException
     */
	ULDDamageRepairDetailsVO findULDDamageDetails(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException;

	/**
	 * 
	 * @param uldDamageFilterVO
	 * @return
	 * @throws SystemException
	 */
	ULDDamageRepairDetailsVO findULDDamageRepairDetails(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException;
	
    /**
     * This method retrieves the uld movement history
     *
     * @param uldMovementFilterVO
     * @param displayPage
     * @return Page<ULDMovementDetailVO>
     * @throws SystemException
     */
    Page<ULDMovementDetailVO> findULDMovementHistory(
			ULDMovementFilterVO uldMovementFilterVO, int displayPage)
			throws SystemException;

    /**
     * This method retrieves the uld details of the specified filter condition
     *
     * @param accessoriesStockConfigFilterVO
     * @param displayPage
     * @return Page<AccessoriesStockConfigListVO>
     * @throws SystemException
     */
    Page<AccessoriesStockConfigVO> findAccessoryStockList(
    		AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO,
			int displayPage) throws SystemException;

    /**
     *
     * @param uldListFilterVO
     * @param pageNumber
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	Page<ULDListVO> findULDList(ULDListFilterVO uldListFilterVO, int pageNumber)
			throws SystemException, PersistenceException;

    /**
     * This method retrieves the stock details of the specified accessory code
     *
     * @param companyCode
     * @param accessoryCode
     * @param stationCode
     * @param airlineIdentifier
     * @return
     * @throws SystemException
     */
	AccessoriesStockConfigVO findAccessoriesStockDetails(String companyCode,
			String accessoryCode, String stationCode, int airlineIdentifier)
			throws SystemException;

    /**
     * This method validates the format of the specified ULD
     *
     * @param companyCode
     * @param uldNumber
     * @throws SystemException
     */
    void validateULDFormat(String companyCode, String uldNumber)
    throws SystemException;

    /**
     * This method retrieves the details of the specified ULD
     *
     * @param companyCode
     * @param uldNumber
     * @return
     * @throws SystemException
     */
    ULDVO findULDDetails(String companyCode, String uldNumber)
			throws SystemException, PersistenceException;

    /**
     * This method retrieves the structural details defined at the
     * ULD Type specified for the ULD
     *
     * @param companyCode
     * @param uldType
     * @return
     * @throws SystemException
     */
//   ULDTypeVO findULDTypeStructuralDetails(
//        String companyCode, String uldType)
//    	throws SystemException;
    /**
     * This method checks if there already exists
     * a manufaturer-serial number combination for
     * any ULD
     *
     * @param uldVo
     * @return
     * @throws SystemException
     * @throws DuplicateManufacturerNumberExistsException
     */
    boolean checkDuplicateManufacturerNumber(ULDVO uldVo)
			throws SystemException, PersistenceException;

    /**
     * This method checks if the uld is currently
     * loaned or borrowed
     *
     * @param companyCode
     * @param uldNumber
     * @return
     * @throws SystemException
     * @throws ULDInTransactionException
     */
    boolean checkULDInTransaction(String companyCode, String uldNumber)
			throws SystemException, PersistenceException;

    /**
     * This methd checks if the ULD is either loaned/borrowed
     * or if the manufacturer-serial number combination
     * already exists for the ULD
     *
     * @param uldVo
     * @return
     * @throws SystemException
     * @throws DuplicateManufacturerNumberExistsException
     * @throws ULDInTransactionException
     */
	boolean validateULDForModification(ULDVO uldVo) throws SystemException,
			PersistenceException;

    /**
	 * This method is used to retrieve the ULDType details based on the filter.
	 * @param String companyCode
	 * @param String uldTypeCode
	 * @return ULDTypeVO
	 * @throws SystemException
	 */
//    ULDTypeVO findULDType(String companyCode, String uldTypeCode)
//    	throws SystemException;
    /**
     * This method is used to list the details of the specified ULD Group
     *
     * @param companyCode
     * @param uldGroupCode
     * @return ULDGroupVO
     * @throws SystemException
     */
//    ULDGroupVO findULDGroupDetails(
//            String companyCode, String uldGroupCode)
//    	throws SystemException;

    /**
	 * This method is used to retrieve the ULDs associated to a Company and ULDType.
	 * @param  companyCode
	 * @param  uldTypeCode
	 * @return Collection<String>
	 * @throws SystemException
	 */

    //To be reviewed Collection<String>
	Collection<String> findAssociatedULDs(String companyCode, String uldTypeCode)
			throws SystemException;

    /**
     * This method is used to check if ULD Types exists for the specified ULD Group
     *
     * @param companyCode
     * @param uldGroupCode
     * @return Collection<String>
     * @throws BusinessDelegateException
     */
	Collection<String> findULDTypesForGroup(String companyCode,
			String uldGroupCode) throws SystemException;

    /**
	 * This method is used to populate the ULDGroups associated to a Company in the system.
	 * @param companyCode
	 * @return Collection<String>
	 * @throws SystemException
	 *
	 */

    //To be reviewed Collection<String>
    Collection<String> populateULDGroupLOV(String companyCode)
    throws SystemException;

    /**
     * This method retrieves all the ULD types for the sppecified group
     *
     * @param companyCode
     * @param uldGroupCode
     * @return Collection<String>
     * @throws SystemException
     */
	Collection<String> findULDTypesByGroup(String companyCode,
			String uldGroupCode) throws SystemException;

	/**
	 * This method is used to retrive the ULDs associated to a ULDType.
	 * @param  companyCode
	 * @param  uldTypeCode
	 * @return Collection<String>
	 * @throws SystemException
	 */
//	To be reviewed Collection<String>
	Collection<String> findULDsForType(String companyCode, String uldTypeCode)
    throws SystemException;

    /**
     * This method validates the ULD type specified
     * @param companyCode
     * @param uldType
     * @return boolean
     * @throws SystemException
     */
	boolean validateULDType(String companyCode, String uldType)
			throws SystemException;

    /**
	 * This method is used to populate the ULDTypes associated to a Company in the system.
	 * @param companyCode
	 * @return Collection<String>
	 * @throws SystemException

	 */

    //To be reviewed Collection<String>
    Collection<String> populateULDTypeLOV(String companyCode)
    throws SystemException;

    /**
     * This method is used to find the ULDType details
     * @param  companyCode
     * @param  agreementNumber
     * @return ULDAgreementVO
     *
     * @throws SystemException
     */
	ULDAgreementVO findULDAgreementDetails(String companyCode,
			String agreementNo) throws SystemException;

	//Added by A-8445 as a part of IASCB-28460 Starts
	 /**
     * This method is used to find the ULDType details with Pagination
	 *
	 *@param  companyCode
     *@param  agreementNumber
	 *@param  uldAgreementFilterVO
	 *@return Page<ULDAgreementVO>
	 *@throws SystemException
	 */
	public Page<ULDAgreementDetailsVO> findULDAgreementDetailsPagination(String companyCode,
			String agreementNo, ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException;
	//Added by A-8445 as a part of IASCB-28460 Ends
	
    /**
     * This method is used for listing uld agreement in the system
	 *
	 *@param  uldAgreementFilterVO
	 *@return Page<ULDAgreementVO>
	 *@throws SystemException
	 */
	public Page<ULDAgreementVO> listULDAgreements(
			ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException;

	/**
	 * This method is used for populating the ULD Agreements
	 *@param  companyCode
	 *@param pageNumber
	 *@return Page<ULDAgreementVO>
	 *@throws SystemException
	 */
	//To be reviewed Collection<Strings>
	public Page<ULDAgreementVO> populateULDAgreementLOV(ULDAgreementFilterVO uldAgreementFilterVO) 
	throws SystemException;

	  /**
     * This method is used to list the ULD Set up Configuration
     *
     * @param  uldStockConfigFilterVO
     * *
     * @return Collection<ULDStockConfigVO>
     * @throws SystemException
     * @throws PersistenceException
     */
	//Page<ULDStockListVO>
	public Collection<ULDStockConfigVO> listULDStockConfig(
			ULDStockConfigFilterVO uldStockConfigFilterVO)
			throws SystemException, PersistenceException;

    /**
     * This method is used to find the ULD Set up Configuration
     *
     * @param  companyCode
     * @param  airlineIdentifier
     * @param  stationCode
     * @param  uldType
     * * *
     *
     * @throws SystemException
     */

    public ULDStockConfigVO findULDStockConfig(String companyCode,
			int airlineIdentifier, String stationCode, String uldType)
    throws SystemException;

 /**
  * 
  * @param companyCode
  * @param stationCode
  * @param airlineIdentifier
  * @param isAirlineUser
  * @return
  * @throws SystemException
  * @throws PersistenceException
  */
     //Collection<String>
    public Page<String> findStationUlds(String companyCode,
			String uldNumber, String stationCode,String transactionType, int airlineIdentifier,
			int displayPage) throws SystemException, PersistenceException;

    /**
     * This method is used to list Accessories at a purticular station
     *
     * @param  companyCode
     * @param  stationCode
     * @param  airlineIdentifier
     * @return Collection<String>
     * @throws SystemException
     */
    public Collection<String> findStationAccessories(String companyCode,
			String stationCode, int airlineIdentifier) throws SystemException;

    /**
     * This method is used for listing uld agreement in the system
	 * for a ULD Transaction
	 *
	 *@param uldAgreementFilterVO
	 *@return Page<ULDAgreementVO>
	 *
	 *@throws SystemException
	 */
	public Page<ULDAgreementVO> findULDAgreementsForTransaction(
			ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException;

	/**
	 *
	 * @param uldAgreementFilterVO
	 * @return ULDAgreementVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	ULDAgreementVO findULDAgreementForReturnTransaction(
			ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * This method is used for listing uld transaction
	 *@author A-1883
	 *@param uldTransactionFilterVO
	 *@return TransactionListVO
	 *@throws SystemException
	 */

	TransactionListVO listULDTransactionDetails(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException;
	
	/**
	 * @author A-3278
	 * for QF1015 on 24Jul08
	 * to find the total demmurage calculated
	 * @param transactionFilterVO
	 * @return ULDTransactionDetailsVO
	 * @throws SystemException
	 */
	ULDTransactionDetailsVO findTotalDemmurage(
			TransactionFilterVO transactionFilterVO) throws SystemException;

	/**
	 * 
	 * @param uldTransactionFilterVO
	 * @return
	 * @throws SystemException
	 */
	TransactionListVO findULDTransactionDetailsCol(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException;

	/**
	 * This method is used for listing uld Invoice
	 *@author A-1883
	 *@param chargingInvoiceFilterVO
	 *@param pageNumber
	 *@return Page<ULDChargingInvoiceVO>
	 *@throws SystemException
	 */
	public Page<ULDChargingInvoiceVO> listULDChargingInvoice(
			ChargingInvoiceFilterVO chargingInvoiceFilterVO, int pageNumber)
	throws SystemException;

	/**
	 * This method is used for listing uld Invoice Details
	 * @author A-1883
	 * @param companyCode
	 * @param invoiceRefNumber
	 * @return Collection<ULDTransactionDetailsVO>
	 * @throws SystemException
	 */

	public Collection<ULDTransactionDetailsVO> viewULDChargingInvoiceDetails(
			String companyCode, String invoiceRefNumber) throws SystemException;

	/**
	 *This method is used for listing uld transaction
	 *@author A-1883
	 *@param uldTransactionFilterVO
	 *@return TransactionListVO
	 *@throws SystemException
	 */
	public TransactionListVO listAccessoryTransactionDetails(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException;

	 /**
	  *
	  * @param companyCode
	  * @param uldNumber
	  * @param transactionType
	  * @return
	  * @throws SystemException
	  * @throws PersistenceException
	  */
	 public  boolean checkLoanedULDAlreadyLoaned(String companyCode,
			String uldNumber, String transactionType) throws SystemException,
			PersistenceException;

	 /**
	  *
	  * @param companyCode
	  * @param uldNumber
	  * @param partyType
	  * @param partycode
	  * @param transactionType
	  * @return
	  * @throws SystemException
	  * @throws PersistenceException
	  */
	public boolean checkBorrowedULDLoanedToSameParty(String companyCode,
			String uldNumber, String partyType, String partycode,
			String transactionType) throws SystemException,
			PersistenceException;

	 	/**
		 * For InvoiceRefNumber LOV
		 * @author A-1883
		 * @param companyCode
		 * @param displayPage
		 * @return Page<String>
		 * @throws SystemException
		 */
	public Page<String> findInvoiceRefNumberLov(String companyCode,
			int displayPage, String invRefNo) throws SystemException;

		  /**
		   * This method is used to Monitor ULD stock
		   * @author A-1883
		   * @param uLDStockConfigFilterVO
		   * @param displayPage
		   * @return Page<ULDStockListVO>
		   * @throws SystemException
		   */
	public Page<ULDStockListVO> findULDStockList(
			ULDStockConfigFilterVO uLDStockConfigFilterVO, int displayPage)
			throws SystemException;

         /**
          *
          * @param uldAgreementVO
          * @return String
          * @throws SystemException
          * @throws PersistenceException
          */
         String checkForInvoice(ULDAgreementVO uldAgreementVO)
			throws SystemException, PersistenceException;

         /**
          *
          * @param discrepancyFilterVO
          * @return
          * @throws SystemException
          * @throws PersistenceException
          */
	Page<ULDDiscrepancyVO> listULDDiscrepancyDetails(
			ULDDiscrepancyFilterVO discrepancyFilterVO) throws SystemException,
			PersistenceException;
         
         /**
          * 
          * A-1950
          * @param companyCode
          * @param period
          * @return
          * @throws SystemException
          * @throws PersistenceException
          */
	Collection<String> findMissingULDs(String companyCode, int period)
			throws SystemException, PersistenceException;

    /**
     * 
     * @param companyCode
     * @param ulds
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	Collection<ULDTransactionDetailsVO> listULDTransactionsForMUCReconciliation(
			String companyCode, Set<String> ulds) throws SystemException,
			PersistenceException;

        /**
         *
         * @param companyCode
         * @param airlineId
         * @return
         * @throws SystemException
         * @throws PersistenceException
         */
	//Commented as part of ICRD 21184
	
	//String findAirlineCode(String companyCode, String airlineId)
	//	throws SystemException, PersistenceException;
	
		//Commented as part of ICRD-21184

        /**
         *
         * @param companyCode
         * @param airlineId
         * @return
         * @throws SystemException
         * @throws PersistenceException
         */
//	String findCarrierCode(String companyCode, int airlineId)
//			throws SystemException, PersistenceException;

        /**
         *
         * @param companyCode
         * @param airlineCodes
         * @return
         * @throws SystemException
         * @throws PersistenceException
         */
	//Commented as part of ICRD 21184
	/*HashMap<String, Integer> listAirlineIdentifiers(String companyCode,
			Set<String> airlineCodes) throws SystemException,
			PersistenceException;*/

        /**
         *
         * @param companyCode
         * @param airportCode
         * @return
         * @throws SystemException
         * @throws PersistenceException
         */
        ULDAirportLocationVO findCurrentLocation(String companyCode,
			String airportCode,String content) throws SystemException, PersistenceException;
        
        boolean isDummyULDMovementPresent(String companyCode,
    			int carrierId,String flightnum,LocalDate flightDate,String uldNum,String pol,String pou) throws SystemException, PersistenceException;

        /**
         *
         * @param companyCode
         * @param airportCode
         * @return
         * @throws SystemException
         * @throws PersistenceException
         */
	HashMap<String, Collection<String>> populateLocation(String companyCode,
			String airportCode) throws SystemException, PersistenceException;

        /**
         *
         * @param filterVO
         * @return
         * @throws SystemException
         * @throws PersistenceException
         */
        UCMMessageVO generateUCMMessageVO(FlightMessageFilterVO filterVO)
			throws SystemException, PersistenceException;

        /**
         * 
         * @param companyCode
         * @param airportCode 
         * @param uldNumbers
         * @return
         * @throws SystemException
         * @throws PersistenceException
         */
	Collection<ULDVO> validateULDs(String companyCode, String airportCode,
			Collection<String> uldNumbers) throws SystemException,
			PersistenceException;

    /**
     * 
     * @param uldFlightMessageFilterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    boolean validateULDFlightMessageDetails(
            ULDFlightMessageFilterVO uldFlightMessageFilterVO)
            throws SystemException, PersistenceException;

    /**
     * 
     * @param flightMessageFilterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	FlightDetailsVO findUCMFlightDetails(
			FlightMessageFilterVO flightMessageFilterVO)
			throws SystemException, PersistenceException;

    /**
     * 
     * @param companyCode
     * @param airportCode
     * @param facilityType
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ULDAirportLocationVO> listULDAirportLocation(String companyCode,
			String airportCode, String facilityType) throws SystemException,
			PersistenceException;

    /**
     * 
     * @param companyCode
     * @param airportCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ULDAirportLocationVO> findDefaultFlag(String companyCode, String airportCode,ArrayList<String> contents)

			throws SystemException, PersistenceException;

    /**
     * 
     * @param companyCode
     * @param location
     * @param airportCode 
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	int checkForULDLocation(String companyCode, String location,
			String airportCode) throws SystemException, PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    ULDFlightMessageReconcileVO listUCMMessage(FlightFilterMessageVO filterVO)
    throws SystemException, PersistenceException;
    
    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    ULDFlightMessageReconcileVO listUCMOUTMessage(FlightFilterMessageVO filterVO)
    throws SystemException, PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    ULDFlightMessageReconcileVO listUCMINMessage(FlightFilterMessageVO filterVO)
    throws SystemException, PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	Page<ULDFlightMessageReconcileVO> listUCMErrors(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException;
    
    /**
     * @author A-1950
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
   Collection<String> findUCMNoLOV(FlightFilterMessageVO filterVO)
    throws SystemException, PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	Page<ULDFlightMessageReconcileDetailsVO> listUldErrors(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException;
    
    /**
     * @author A-1950
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	Collection<ULDFlightMessageReconcileVO> listUCMsForComparison(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException;
	
	
	
	Collection<ULDFlightMessageReconcileVO> listUCMsForFlightMovement(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException;
    
	
	
	
	
    /**
     * @author A-1950
     * 
     * @param reconcileVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	ULDFlightMessageReconcileVO findCounterUCM(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException,
			PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ULDFlightMessageReconcileDetailsVO> listUCMINOUTMessage(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException;

    /**
     * 
     * @param flightFilterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ULDFlightMessageReconcileDetailsVO> listUCMOUTForInOutMismatch(
			FlightFilterMessageVO flightFilterVO) throws SystemException,
			PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Page<ULDVO> findUldDetailsForSCM(SCMMessageFilterVO filterVO) 
    throws SystemException, PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Page<ULDSCMReconcileVO> listSCMMessage(SCMMessageFilterVO filterVO)
    throws SystemException, PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	Page<ULDSCMReconcileDetailsVO> listULDErrorsInSCM(
			SCMMessageFilterVO filterVO) throws SystemException,
			PersistenceException;
    
    /**
     * @author A-1950
     * 
     * @param flightMessageReconcileVO
     * @throws SystemException
     * @throws PersistenceException
     */
	ULDFlightMessageReconcileVO checkForINOUT(
			ULDFlightMessageReconcileVO flightMessageReconcileVO)
			throws SystemException, PersistenceException;

    /**
     * 
     * @param reconcileVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	Collection<String> findInMesssageAirports(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException,
			PersistenceException;

    /**
     * 
     * @param filterVO
     * @return 
     * @throws SystemException
     * @throws PersistenceException
     */
    ULDSCMReconcileVO sendSCMMessage(SCMMessageFilterVO filterVO) 
    throws SystemException, PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ULDPoolOwnerVO> listULDPoolOwner(ULDPoolOwnerFilterVO filterVO)
    throws SystemException, PersistenceException;

    /**
     * 
     * @param filterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Page<ULDSCMReconcileVO> listSCMMessageLOV(SCMMessageFilterVO filterVO)
    throws SystemException, PersistenceException;

  /**
   * 
   * @param companyCode
   * @param uldNumber
   * @param scmSequenceNumber
   * @param airportCode
   * @return
   * @throws SystemException
   * @throws PersistenceException
   */
	int findAirlineIdentifierForSCM(String companyCode, String uldNumber,
			String scmSequenceNumber, String airportCode)
    throws SystemException, PersistenceException;

    /***
     * 
     * @param companyCode
     * @param airportCode
     * @param airlineIdentifier
     * @param uldIdentifier
     * @param date
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<String> findSCMFromMonitorULD(String companyCode,
    		ULDStockListVO uldStockListVO
			) throws SystemException, PersistenceException;
    
    /**
     * 
     * A-1950
     * @param detailsVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    boolean checkForPoolOwners(ULDFlightMessageReconcileDetailsVO detailsVO)
    	throws SystemException, PersistenceException;
    
    /**
    *
    * @param companyCode
    * @param uldNumber
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
   ULDListVO findULDListForHHT(String companyCode, String uldNumber)
			throws SystemException, PersistenceException;

	//Commented as part of ICRD-21184
   /**
    * 
    * A-1950
    * @param companyCode
    * @param twoalphacode
    * @param threealphacode
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
//	String findOwnerCode(String companyCode, String twoalphacode,
//			String threealphacode) throws SystemException, PersistenceException;
   
   /**
    * @author A-1936
    * This method is used to list all the Flights for which the UCM has to be sent manually..
    * @param companyCode
    * @param pol
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
	Collection<UCMExceptionFlightVO> findExceptionFlights(String companyCode,
			String pol) throws SystemException, PersistenceException;
   
   /**
    * 
    * A-1950
    * @param uldListFilterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
   Collection<String> findULDs(ULDListFilterVO uldListFilterVO)
			throws SystemException, PersistenceException;

   /**
    *
    * @param companyCode
    * @param movementSequenceNumber
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
   HashMap<Long,Integer> findULDCountsForMovement(String companyCode , Collection<Long> seqNos)
   throws SystemException,PersistenceException;

   /**
    * @author A-2667
    * This method is used for fetching the details from db for particular GenerateSCM Search(Added as a part of pagination in GenerateSCMscreen)
    * @param filterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
   Collection<ULDVO> findUldDatasForSCM(ULDSCMReconcileVO uLDSCMReconcileVO) 
   throws SystemException, PersistenceException;

	/**
	 * @author A-2619
	 * This method is used to list the ULD History
	 * @return Page
	 * @param uldHistoryVO.
	 * @throws SystemException  
	 * @throws PersistenceException
	 */
	Page<ULDHistoryVO> listULDHistory(ULDHistoryVO uldHistoryVO)
			throws SystemException, PersistenceException;
	
	/* Added by A-2412 on 18th Oct for Editable CRN cr */
	   Collection<String> checkForDuplicateCRN(String companyCode,TransactionVO transactionVO)throws SystemException,PersistenceException ;
	   /* Addition by A-2412 on 18th Oct for Editable CRN cr ends */
	   
	   TransactionListVO listUCRULDTransactionDetails(TransactionFilterVO uldTransactionFilterVO)
		throws SystemException;
   
   /**
    * @author A-2667
    * This method was added for checking the duplicate entry of ULDFacility Code(From page Airport Facility Master)
    * @param Collection of ULDAirportLocationVO
    * @return
    * @throws SystemException
    */
   Collection<String> checkForDuplicateFacilityCode(String companyCode,String airportCode,String facilityCode,String facilityType,String content)
	throws SystemException;
   
   /**
    * @author A-2667
    * This method was added for fetching the Airline Code from ULDUSRMST to make default Airline Code in all the pages
    * @param companyCode and userCode
    * @return
    * @throws SystemException
    */
   String findDefaultAirlineCode(String companyCode,String userCode)
   throws SystemException,PersistenceException;
   
   /* Added by A-3459 on 26th Sep for checking Single Duplicate CRN  */
  String findDuplicateCRN(String companyCode,String crnNumber)throws SystemException,PersistenceException ;
  /* Added by A-3459 on 26th Sep for checking Single Duplicate CRN end */
   /**
    * @author A-2408
	 * @param companyCode
	 * @param twoalpha
	 * @param threealpha
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	//Commented as part of ICRD 21184
  	//public String validateOwnerCode(String companyCode,String twoalpha,String threealpha)
	//throws SystemException,PersistenceException; 
	 
	/**
	    * @author A-2667
	    * This method was added for fetching the Current Airport from ULDMST 
	    * @param companyCode and userCode
	    * @return
	    * @throws SystemException
	    */
	public String findCurrentAirport(String companyCode,String uldNumber)
	throws SystemException,PersistenceException; 
		
	/**
     * This method retrieves the uld details of the specified filter condition
     * @author A-2883
     * @param ULDHandledCarrierVO
     * @return Collection<ULDHandledCarrierVO>
     * @throws SystemException
     */
	Collection<ULDHandledCarrierVO> findhandledcarriersetup(
    		ULDHandledCarrierVO handledCarrierSetupVO)
    		throws SystemException , PersistenceException;
	/**
	 * This method is used to find the Stock Status of a ULD(used for HHT)
	 * @author A-2667
	 * @param uLDDiscrepancyFilterVO
	 * @return ULDDiscrepancyVO
	 * @throws SystemException
	 */
	ULDDiscrepancyVO findULDStockStatusForHHT(ULDDiscrepancyFilterVO 
			uLDDiscrepancyFilterVO)throws SystemException ,PersistenceException;
	/**
	 * 
	 * @param companyCode
	 * @param displayPage
	 * @param facilityType
	 * @return
	 * @throws SystemException
	 */
	Page<String> populateLocationLov(String companyCode,
			int displayPage, String comboValue,String airportCode)throws SystemException,PersistenceException;
	 
	
	/**
	 * @author A-2412
	 * @param uldIntMvtFilterVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 */
	Page<ULDIntMvtDetailVO> findIntULDMovementHistory(
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO, int displayPage)
			throws SystemException ,PersistenceException;
	
	/**
	 *
	 * @param discrepancyFilterVO
	 * @return
	 * @throws SystemException
	 */
	Collection<ULDDiscrepancyVO>listULDDiscrepancy(
			ULDDiscrepancyFilterVO discrepancyFilterVO) throws SystemException,
			PersistenceException;
	/**
	 * @author A-2667
	 * @param companyCode
	 * @param uldNumber
	 * @return String
	 * @throws SystemException
	 */
	String findpolLocationForULD(String companyCode,String uldNumber)throws SystemException,PersistenceException;
	
	/**
	 *@author A-3045
	 * @param uldIntMvtFilterVO
	 * @return Collection<ULDIntMvtDetailVO>
	 * @throws SystemException
	 */
	Collection<ULDIntMvtDetailVO>findULDIntMvtHistory(
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO) throws SystemException,PersistenceException;
	
	/**
	 * @author A-2667
	 * @param relocateULDVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 */
	Collection<AuditDetailsVO> findULDAuditEnquiryDetails(
			RelocateULDVO relocateULDVO)throws SystemException ,PersistenceException;
	
	/**
	 * @author A-1950
	 * @param companyCode
	 * @throws SystemException
	 * @throws PersistenceException
	 * @return
	 */
	Collection<ULDStockListVO> findStockDeviation(String companyCode)
	throws SystemException ,PersistenceException;
	
	/**
	 * @author A-1950
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<AccessoriesStockConfigVO> sendAlertForULDAccStockDepletion(String companyCode)
	throws SystemException ,PersistenceException;
	
	/**
	 * @author A-1950
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> sendAlertForDiscrepancy(String companyCode, int period)
	throws SystemException ,PersistenceException;
	
	/**
	 * 
	 * @author a-2883
	 * @param ULDNumber
	 * @param companyCode
	 * @return Collection<ULDDamagePictureVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ULDDamagePictureVO> findULDDamagePictures(ULDDamageFilterVO uldDamageFilterVO)
	throws SystemException, PersistenceException;
	
	/**
	 * @author A-2667
	 * @param flightMessageFilterVO
	 * @return Collection<FlightDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<FlightDetailsVO> findCPMULDDetails(FlightMessageFilterVO flightMessageFilterVO)
	throws SystemException, PersistenceException;
	
	/**
	 *  @author A-3459
	 * @param companyCode
	 * @param section
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ULDDamageChecklistVO> listULDDamageChecklistMaster(String companyCode,String section)
    throws SystemException, PersistenceException;

	/**
	 * @author a-2883
	 * @param inventoryULDVO
	 * @return InventoryULDVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<InventoryULDVO> findULDInventoryDetails(InventoryULDVO inventoryULDVO)
	throws SystemException,PersistenceException;
	
	 /**
	 * @author A-3429
	 * @param companyCode
	 * @return ULDNumberVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public ULDNumberVO findULDHistoryCounts(ULDMovementFilterVO uLDMovementFilterVO)
	throws SystemException,PersistenceException ;
	
	//added by jisha for qf1022 starts
	/**
	 * @author a-3093
	 * @param uldRepairFilterVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 */
		 
	Page<ULDRepairDetailsListVO> listDamageRepairDetails(
			UldDmgRprFilterVO uldRepairFilterVO, int displayPage) throws SystemException;

	/**
	 * @author a-3093
	 * @param uldmovementFilterVO
	 * @return
	 * @throws SystemException
	 */
	Page<AuditDetailsVO> findULDActionHistory(
			ULDMovementFilterVO uldmovementFilterVO) throws SystemException;
	
//	added by jisha for qf1022 ends
	
	/**
	 * @author A-1950
	 * 
	 * @param uldStockConfigFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ULDStockConfigVO> findDwellTimes(ULDStockConfigFilterVO uldStockConfigFilterVO)
	throws SystemException, PersistenceException;
	
	/**
	 * @author A-2408
	 * @param uldPoolOwnerFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkforPoolOwner(ULDPoolOwnerFilterVO uldPoolOwnerFilterVO) throws SystemException, PersistenceException;
	
	 /**
     * @author A-3459
     * @param uldFlightMessageReconcileVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	Page<ULDFlightMessageReconcileVO> findMissingUCMs(ULDFlightMessageReconcileVO uldFlightMessageReconcileVO)
	throws SystemException,PersistenceException;

	
 	/**
	 * For InvoiceRefNumber LOV
	 * @author A-1883
	 * @param companyCode
	 * @param displayPage
	 * @return Page<String>
	 * @throws SystemException
	 */
	public Page<String> findMUCRefNumberLov(String companyCode,
		int displayPage, String mucRefNum, String mucDate) throws SystemException;
	
	/**
	 * For InvoiceRefNumber LOV
	 * @author A-1883
	 * @param companyCode
	 * @param mucReferenceNumber
	 * @return Page<String>
	 * @throws SystemException
	 */
	public Collection<ULDConfigAuditVO> findMUCAuditDetails(ULDTransactionDetailsVO uldTransactionDetailsVO) throws SystemException;
	 /**
	 * @param companyCode
	 * @param airportCode
	 * @param facility
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ULDAirportLocationVO findLocationforFacility(String companyCode,
				String airportCode,String facility) throws SystemException, PersistenceException;
	
	/**
	 * @author A-3045
	 * @param companyCode
	 * @param uldNumbers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> checkULDInUse(String companyCode,Collection<String> uldNumbers)
	throws SystemException, PersistenceException;
	
	/**
	 * @author A-3045
	 * @param uldListFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	void updateSCMStatusForPendingULDs(ULDListFilterVO uldListFilterVO)
	throws SystemException, PersistenceException;
	
	/**
	 * @author A-3045
	 * @param transactionFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	TransactionListVO findTransactionHistory(TransactionFilterVO transactionFilterVO)
	throws SystemException;
	
	/**
    * @author A-3045
    * @param uldListFilterVO
    * @return Collection<ULDListVO>
    * @throws SystemException
    * @throws PersistenceException
    */
	Collection<ULDListVO> findULDListColl(ULDListFilterVO uldListFilterVO)
		throws SystemException, PersistenceException;
	/**
	 * 
	 * @param uldListFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ULDListVO> findUldInventoryList(ULDListFilterVO uldListFilterVO)
			throws SystemException, PersistenceException; 
	
    /**
     * This method is used for listing collection of uld agreement in the system,bug25282
	 * @author A-3045
	 *@param  uldAgreementFilterVO
	 *@return Collection<ULDAgreementVO>
	 *@throws SystemException
	 */
	public Collection<ULDAgreementVO> listULDAgreementsColl(
			ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException;
	
    /**
    * @author A-3045
    * @param uldListFilterVO
    * @param pageNumber
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
	Page<ULDVO> findULDListForSCM(ULDListFilterVO uldListFilterVO, int pageNumber)
			throws SystemException, PersistenceException;
	
		/**
	    * @author A-3459
	    * @param scmValidationFilterVO
	    * @return
	    * @throws SystemException
	    * @throws PersistenceException
	    */
		Page<SCMValidationVO> findSCMValidationList(SCMValidationFilterVO scmValidationFilterVO)
				throws SystemException, PersistenceException;
		/**
		 * 
		 * @author A-6344
		 * @param findSCMSequenceNum
		 * @return String sequenceNum
		 * @throws SystemException
		 */
		public  String findSCMSequenceNum(String comapnyCode,String aiportcode,String airlineId)
		throws SystemException,PersistenceException ;
		/**
		    * @author A-3459
		    * @param scmValidationFilterVO
		    * @return Collection<SCMValidationVO>
		    * @throws SystemException
		    * @throws PersistenceException
		    */
			Collection<SCMValidationVO> findSCMValidationListColl(SCMValidationFilterVO scmValidationFilterVO)
				throws SystemException, PersistenceException;
	
			/**
			 * 
			 * @param companyCode
			 * @param uldnos
			 * @throws SystemException
			 * @throws PersistenceException
			 */
			
			void updateULDDiscrepancy(String companyCode,String airport ,Collection<String> uldnos)
			throws SystemException, PersistenceException;
			
			
			/**
		     * @author A-3278
		     * @param filterVO
		     * @return Collection<ULDListVO>
		     * @throws SystemException
		     * @throws PersistenceException
		     */
		    Collection<ULDListVO> findULDsForSCM(ULDListFilterVO filterVO) 
		    throws SystemException, PersistenceException;
		    
		    /**
			 * @author a-3278
			 * @param companyCode
			 * @param period
			 * @param userId
			 * @throws SystemException
			 * @throws PersistenceException
			 */			
			void updateSCMStatusForULD(String companyCode,int period ,String userId)
			throws SystemException, PersistenceException;
			 /**
		     *
		     * @param companyCode
		     * @param partyType
		     * @return
		     * @throws SystemException
		     * @throws PersistenceException
		     */
		    Collection<ULDServiceabilityVO> listULDServiceability(String companyCode,
					String partyType) throws SystemException,
					PersistenceException;
		    /**
		     *
		     * @param companyCode
		     * @param serviceCode
		     * @return
		     * @throws SystemException
		     * @throws PersistenceException
		     */
			int checkForServiceability(String companyCode,String
					 serviceCode,String partyType) throws SystemException, PersistenceException;
		    /**
		     * 
		     * @param reconcileVO
		     * @return
		     * @throws SystemException
		     * @throws PersistenceException
		     */
			Collection<String> findFlightArrivalStatus(
					ULDFlightMessageReconcileVO reconcileVO) throws SystemException,
					PersistenceException;

		    /**
		     * 
		     * @param reconcileVO
		     * @return
		     * @throws SystemException
		     * @throws PersistenceException
		     */
			Collection<String> findUcmInStatus(
					ULDFlightMessageReconcileVO reconcileVO) throws SystemException,
					PersistenceException;
	/**
	 * Added for Bug 102024
	 * @author A-3268
	 * @param transactionFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	ULDTransactionDetailsVO listULDTransactionDetailsForDemurrage(TransactionFilterVO transactionFilterVO)
	throws SystemException, PersistenceException;
	
	Collection<ULDStockListVO> findULDStockListCollection(
			ULDStockConfigFilterVO uLDStockConfigFilterVO)
			throws SystemException;
	
	public Page<EstimatedULDStockVO> findEstimatedULDStockList(
			EstimatedULDStockFilterVO estimatedULDStockFilterVO, int displayPage)
			throws SystemException;

	public Page<ExcessStockAirportVO> findExcessStockAirportList(
			ExcessStockAirportFilterVO excessStockAirportFilterVO,
			int displayPage) throws SystemException;
Collection<ULDFlightMessageReconcileDetailsVO> findUldFltMsgRecDtlsVOs(ULDFlightMessageFilterVO uldFltmsgFilterVO)
throws SystemException, PersistenceException;


/**
 * @author A-5125 for BUG-ICRD-50392 
 * @param cmpCod
 * @param flightDate
 * @param flightNumber
 * @return Collection<ULDFlightMessageReconcileDetailsVO>
 * 
 */

public  Collection<ULDFlightMessageReconcileDetailsVO> findTransitStateULDs(String cmpCod, LocalDate flightDate,
		String flightNumber) throws SystemException;
	/**
	 * 
	 * Added as part of the ICRD-114538
	 * @author A-3415 
	 * @param transactionFilterVO
	 * @return
	 * @throws SystemException
	 */
	ULDTransactionDetailsVO findLastTransactionsForUld(TransactionFilterVO transactionFilterVO)
			throws SystemException, PersistenceException;

//merge solved by A-7794
	
	/**
	 * 
	 * Added as part of the ICRD-208677
	 * @author A-7794
	 * @param operationalULDAuditFilterVO
	 * @return
	 * @throws SystemException
	 * throws PersistenceException
	 */
	Page<OperationalULDAuditVO> findOprAndMailULDAuditDetails(OperationalULDAuditFilterVO operationalULDAuditFilterVO)
		    throws SystemException, PersistenceException;
	/**
	 * Added for ICRD-192217
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<EstimatedULDStockVO> findULDStockListForNotification(EstimatedULDStockFilterVO estimatedULDStockFilterVO)
			throws SystemException;
	/**
	 * Added for ICRD-192280
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<FlightDetailsVO> findUCMMissingFlights(EstimatedULDStockFilterVO estimatedULDStockFilterVO)
			throws SystemException;
	/**
	 * 
	 * 	Method		:	ULDDefaultsDAO.findlatestUCMsFromAllSources
	 *	Added by 	:	A-7359 on 07-Sep-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@param uldFlightMessageFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	ArrayList<ULDFlightMessageReconcileVO>
	 */
	public ArrayList<ULDFlightMessageReconcileVO> findlatestUCMsFromAllSources(FlightFilterMessageVO  uldFlightMessageFilterVO) throws SystemException,PersistenceException;
	public Map<String, ULDDiscrepancyVO> findUldDiscrepancyDetails(
			ULDDiscrepancyFilterVO discrepancyFilterVO) throws SystemException;
	public void removeDamageImages(ULDDamageVO uldDamageVO) throws SystemException;
	
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
	public boolean validateULDAirportLocation(String companyCode, String airportCode,
			  String locationCode, String facilityTypeCode) throws SystemException ;
	
	public boolean findDuplicatePoolOwnerConfig(ULDPoolOwnerVO uldPoolOwnerVO) throws SystemException, PersistenceException;

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
	
	public Collection<String> findAirportsforSCMJob(String companyCode,Collection<String> airportGroup,String noOfDays) 
			throws PersistenceException, SystemException;

}

