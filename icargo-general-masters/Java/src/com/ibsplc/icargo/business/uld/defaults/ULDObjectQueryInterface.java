/*
 * ULDObjectQueryInterface.java Created on Jul 22, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.ULDDiscrepancy;
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransaction;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

/**
 * @author A-3459
 *
 */

public interface ULDObjectQueryInterface extends QueryDAO  {
	
	/**
	 * @param companyCode
	 * @param uldnos
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	 Collection<ULD> findULDObjects(String companyCode , Collection<String> uldnos)
		throws SystemException,PersistenceException;
	 /**
		 * 
		 * @param companyCode
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 */
	 Collection<ULD> findAllULDObjects(String companyCode)
		throws SystemException,PersistenceException;
	 
	 /**
		 * 
		 * @param companyCode
		 * @return
		 */
	 Collection<ULDDiscrepancy> findMissingULDObjects(String companyCode, int period)
		throws SystemException,PersistenceException;
	 /**
		 * 
		 * @param companyCode
		 * @return
		 */
	 Collection<ULDDiscrepancy> findULDDiscrepanciesObjects(String companyCode,String uldNumber,String reportingStation)
		throws SystemException,PersistenceException;
	 
	 /**
		 * @author a-3278
		 * @param companyCode
		 * @param uldnos
		 * @return Collection<ULDDiscrepancy>
		 * @throws SystemException
		 * @throws PersistenceException
		 */
	 Collection<ULDDiscrepancy> findULDDiscrepancies(String companyCode, Collection<String> uldnos)
			throws SystemException, PersistenceException;

	 /**
		 
		 * @param companyCode
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 * this method fetches all uld object for which scm flag is present
		 */
		 Collection<ULD> findAllULDObjectsforSCM(String companyCode)
		throws SystemException,PersistenceException;
		 /**
			 * 
			 * @param companyCode
			 * @return
			 */
		 Collection<ULD> findAllLostULDObjects(String companyCode, int period)
			throws SystemException,PersistenceException;
		 
		 /**
		  * 
		  * @param companyCode
		  * @param airport
		  * @return
		  * @throws SystemException
		  * @throws PersistenceException
		  */
		 Collection<ULDDiscrepancy> findULDDiscrepanciesObjectsAtAirport(String companyCode,String airport)
		 throws SystemException,PersistenceException;
		 /**
		  * Added by A-3415 for ICRD-114538
		  * @param filterVO
		  * @return
		  * @throws SystemException
		  * @throws PersistenceException
		  */
		 Collection<ULDTransaction> findOpenTxnULDObjects(TransactionFilterVO filterVO)
		 throws SystemException,PersistenceException;
		/**
		 * 	Method		:	ULDObjectQueryInterface.findULDObjectsForDepreciation
		 *	Added by 	:	A-7359 on 09-May-2018
		 * 	Used for 	:	ICRD-233082 ULD Current Value Depreciation
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return 
		 *	Return type	: 	Collection<ULD>
		 * @throws SystemException 
		 * @throws PersistenceException 
		 */
		Collection<ULD> findULDObjectsForDepreciation(String companyCode) throws PersistenceException, SystemException;
		/**
		 * Added for ICRD-333282
		 * @author A-8368
		 * @param String companyCode 
		 * @param int rowCount
		 * @throws SystemException
		 */
		public Collection<ULDFlightMessageReconcileVO> findUldsForMarkMovement(String companyCode , int rowCount)
				throws PersistenceException, SystemException;
}
