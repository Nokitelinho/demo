package com.ibsplc.icargo.services.uld.defaults.micro;

import java.rmi.RemoteException;

import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.RecordULDMovementMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.TransactionListMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.TransactionMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDAirportLocationMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDDamageRepairDetailsMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDDiscrepancyFilterMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDDiscrepancyMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDListFilterMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDListMicroPageVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDListMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDValidationMicroVO;

/**
 * @author A-2052
 *
 */

public interface UldDefaultsSOAPIntf extends java.rmi.Remote {

	/**
	 * @author A-2052 This method is used to validate ULD format.
	 * @param uldNumber
	 * @throws RemoteException
	 */
	String validateULDFormat(String companyCode,String uldNumber) throws RemoteException;

	/**
	 * @author A-2052 This method is used to set Location for ULD.
	 * @param uldVOs
	 * @throws RemoteException
	 */
	void setLocationForULD(ULDMicroVO uldVOs) throws RemoteException;

	/**
	 * @author A-2052 This method is used to list the List ULD.
	 * @param uldNumber
	 * @return
	 * @throws RemoteException
	 */
	ULDListMicroVO findULDList(String companyCode,String uldNumber) throws RemoteException;
	/**
	 * @author A-2037 This is used to record the ULD Movement Details
	 * @param uldNOs
	 * @param uldMovementVos
	 * @throws RemoteException
	 */

	 void saveULDMovement(RecordULDMovementMicroVO recordULDMovementMicroVO)
	 throws RemoteException;

	/**
	 * @author A-1868
	 * This method is used to save the ULD discrepancy details.
	 * @param uldDiscrepancyMicroVO
	 * @throws RemoteException
	 */
	 String saveULDDiscrepancyDetails(ULDDiscrepancyMicroVO uldDiscrepancyMicroVO)
	 	throws RemoteException;

	/**
	 * @author A-2052
	 * This method is used to list the return ULD details.
	 * @param companyCode
	 * @param uldNumber
	 * @param txntype
	 * @throws RemoteException
	 */
	 TransactionListMicroVO listULDTransactionDetails(String companyCode,String uldNumber,String txntype)
		throws RemoteException;

	/**
	 * @author A-2052
	 * This method is used to save the return ULD details.
	 * @param uldNumber
	 * @param txntype
	 * @throws RemoteException
	 */
	 void saveReturnTransaction(TransactionListMicroVO vo) throws RemoteException;

	/**
	 * @author A-2052 This method is used to list the List ULD.
	 * @param uldListFilterMicroVO
	 * @return
	 * @throws RemoteException
	 */
	 ULDListMicroPageVO findULDList(ULDListFilterMicroVO uldListFilterMicroVO)
	 	throws RemoteException;


	   /**
		 * @author A-2006
		 * This method is used to save the ULD Damage details.
		 * @param ULDDamageMicroVO
		 * @throws RemoteException
		 */
		 void saveULDDamage(ULDDamageRepairDetailsMicroVO uldDamageRepairDetailsMicroVO)
		 	throws RemoteException;

	 /**
	 * @author A-2052 This method is used to validate facility Code
	 * @param uldVO
	 * @return
	 * @throws RemoteException
	 */
	 String listULDAirportLocation(ULDMicroVO uldVO) throws RemoteException ;


	 /**
	 * @author A-2006
	 * This method is used to validate ULD
	 * @param uldNumber,companyCode
	 * @throws RemoteException
	 */

   ULDValidationMicroVO validateULD(String companyCode, String uldNumber)
	throws RemoteException;
   /**
	 * @author A-2052 This method is used to populate facility Code lov
	 * @param fieldTypes
	 * @return
	 * @throws RemoteException
	 */
	ULDAirportLocationMicroVO[] listULDAirportLocation
		(String companyCode, String currentStation,String facilityType)
			throws RemoteException  ;

	/**
	 * @author A-2052 This method is used to save Loan Borrow ULD Transaction.
	 * @param transactionMicroVO
	 * @throws RemoteException
	 */
	String saveULDTransaction(TransactionMicroVO transactionMicroVO)
		throws RemoteException ;
	 
	 /**
	 * @author A-2052 This method is used to list the ULD number for hht.
	 * @param uldNumber
	 * @throws RemoteException
	 */
	 ULDListMicroVO findULDListForHHT(String companyCode,String uldNumber) 
	 	throws RemoteException;
	 
	/**
	 * @author A-2052 This method is used to list ULD No
	 * @param companyCode, uldNumber
	 * @return findULDDetails
	 * @throws RemoteException
	 */


	  ULDListMicroVO findULDDetails(String companyCode, String uldNumber)
		throws RemoteException ;
	  /**
	   * 
	   * @param discrepancyMicroVO
	   * @throws RemoteException
	   */
	  void captureMissingULD(ULDDiscrepancyMicroVO discrepancyMicroVO)throws RemoteException ;
	 /**
	  * 
	  * @param discrepancyVO
	  * @throws RemoteException
	  */
	  void  updateULDInventory(ULDDiscrepancyMicroVO discrepancyVO)throws RemoteException ;

	 /**
	  *
	  * @param companyCode, uldNUmber
	  * @throws RemoteException
	  */
	  ULDDamageRepairDetailsMicroVO findULDDamageDetails(String companyCode,String uldNumber) throws RemoteException ;
	
	/**
	 * This method returns the ULDStockStatus for HHT
	 * 
	 * @author A-2052
	 * @param uLDDiscrepancyFilterVO
	 * @return ULDDiscrepancyVO
	 * @throws BusinessDelegateException
	 */
	ULDDiscrepancyMicroVO findULDStockStatusForHHT(
			ULDDiscrepancyFilterMicroVO uldDiscrepancyFilterMicroVO)
			throws RemoteException;
	/**
	 * 
	 * @param uldDiscrepancyMicroVO
	 * @return
	 * @throws RemoteException
	 * @author a-2572
	 */
	String updateULDStockStatusForHHT(
			ULDDiscrepancyMicroVO uldDiscrepancyMicroVO) throws RemoteException;
	
	/**
	 * 
	 * @param companyCode
	 * @param carrierCode
	 * @return
	 * @throws RemoteException
	 * @author A-2547
	 */
	 String findCRNForULDTransaction(String companyCode,String carrierCode)throws RemoteException;
	
	
}
