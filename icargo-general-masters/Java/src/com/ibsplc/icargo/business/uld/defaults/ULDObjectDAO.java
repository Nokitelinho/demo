/*
 * ULDObjectDAO.java Created on Oct 16, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;


import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.ULDDiscrepancy;
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransaction;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDMovementMapper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.object.AbstractObjectQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.object.ObjectQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author AshrafBinu
 *
 */
/*
 * RevisionHistory
 * ----------------------------------------------------------------------------
 * Version      Date        Author          Description
 * ----------------------------------------------------------------------------
 *  0.1         06/05/2007  AshrafBinu          Created
 *
 */

public class ULDObjectDAO extends AbstractObjectQueryDAO implements ULDObjectQueryInterface{

	private static final String FIND_ULD_OBJECTS =
			"uld.defaults.finduldobjects";
	
	private static final String FIND_ALL_ULD_OBJECTS =
		"uld.defaults.findalluldobjects";

	private static final String FIND_MISSING_ULD_OBJECTS =
		"uld.defaults.findmissinguldobjects";
	
	private static final String FIND_ULDDISCREPANICIES =
		"uld.defaults.findulddiscrepancies";
	
	private static final String FIND_ULDDISCREPANICIES_OBJECTS =
		"uld.defaults.findulddiscrepanciesobjects";
	
	private static final String FIND_ULDDISCREPANICIES_OBJECTS_AT_AIRPROT =
		"uld.defaults.findulddiscrepanciesobjectsatairport";
	
	private static final String FIND_DEPRECIATION_ULD_OBJECTS=
		"uld.defaults.finduldobjectsfordepreciation";
	
	
	
	//added by a-3045 for 
	private static final String FIND_LOST_ULD_OBJECTS =
		"uld.defaults.findlostuldobjects";
	//Added by A-3415 for ICRD-114538
	private static final String FIND_OPENULDTXN_OBJECTS =
		"uld.defaults.finduldtransaction.opentransactions";
	/**
	 * Added by A-8368 as part of ICRD-333282
	 */
	public static final String FIND_ULDS_FOR_MARKMOVEMENT = "uld.defaults.findUldsForMarkMovement";
	public static final String POSTGRES_FIND_ULDS_FOR_MARKMOVEMENT = "postgres.uld.defaults.findUldsForMarkMovement";

	private Log log = LogFactory.getLogger("ULD");
	/**
	* @param operationalFlightVO
	* @return Collection<OperationalShipment>
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<ULD> findULDObjects(
		String companyCode , Collection<String> uldnos)
	throws SystemException,PersistenceException{
		int index=0;
		String baseQuey = getQueryManager().getNamedNativeQueryString(FIND_ULD_OBJECTS);
		StringBuilder baseQueryBuilder = new StringBuilder(baseQuey);
		/*if(uldnos.size() > 0){
			baseQueryBuilder.append(" AND uld.uldPK.uldNumber IN(");
			for(String uldno : uldnos){
				baseQueryBuilder.append("?,"); 
			}
		}*/
		
       /* Modified by Preet for bug 57819 on 20Aug09 starts
	    This is to handle more than 1000 ULD numbers passed to "IN" clause in the query */
		if(uldnos != null && uldnos.size() > 0){
			int count = uldnos.size();
			int j = 0;
			StringBuilder sbd = new StringBuilder();
			for(j = 0; j < count ; j++){
				if(j == 0){
					sbd.append(" AND uld.uldPK.uldNumber IN(?,");					
				}
				else if(j != 0 && j % 900 == 0){					
					sbd.deleteCharAt(sbd.length()-1);							
					sbd.append(" ) OR uld.uldPK.uldNumber IN(?,");
				}else{
					sbd.append("?,"); 
				}
			}
			//sbd.deleteCharAt(sbd.length()-1);
			//sbd.append(" )");
			baseQueryBuilder.append(sbd);
		}
		String queryString = baseQueryBuilder.toString(); 
		if(queryString.endsWith(",")){
			int len = queryString.length();
			queryString = queryString.substring(0,len-1);
			queryString = new StringBuilder(queryString).append(")").toString();
		}
		log.log(Log.INFO, "queryString is *(((------>>>>", queryString);
		ObjectQuery qry = getQueryManager().createQuery(queryString);
		qry.setParameter(++index, companyCode);
		if(uldnos.size() > 0){
			for(String uld : uldnos){
				qry.setParameter(++index, uld);
			}
		}
		
		//ObjectQuery qry = getQueryManager().createNamedQuery(
			//	FIND_ULD_OBJECTS);
		return qry.getResultList();
	}
	
	/**
	 * 
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ULD> findAllULDObjects(String companyCode)
	throws SystemException,PersistenceException{
		log.entering("ULDObjectDAO","findAllULDObjects");
		int index=0;
		ObjectQuery qry = getQueryManager().createNamedQuery(
				FIND_ALL_ULD_OBJECTS);
		qry.setParameter(++index, companyCode);
		return qry.getResultList();
	}
	
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	public Collection<ULDDiscrepancy> findMissingULDObjects(String companyCode, int period)
	throws SystemException,PersistenceException{
		log.entering("ULDObjectDAO","findMissingULDObjects");
		int index=0;
		ObjectQuery qry = getQueryManager().createNamedQuery(
				FIND_MISSING_ULD_OBJECTS);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, period);
		return qry.getResultList();
	}
	
	public Collection<ULDDiscrepancy> findULDDiscrepanciesObjects(String companyCode,String uldNumber,String reportingStation)
	throws SystemException,PersistenceException{
		log.entering("ULDObjectDAO","findULDDiscrepanciesObjects");
		int index=0;
		String baseQuery = getQueryManager().getNamedNativeQueryString(FIND_ULDDISCREPANICIES_OBJECTS);
		StringBuilder query = new StringBuilder().append(baseQuery);
		if(Objects.nonNull(reportingStation)){
			query.append(" AND RPTARP = ? ");
		}
		ObjectQuery qry = getQueryManager().createQuery(query.toString());
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, uldNumber);
		if(Objects.nonNull(reportingStation)){
			qry.setParameter(++index, reportingStation);
		}
		log.exiting("ULDObjectDAO","findULDDiscrepanciesObjects");
		return qry.getResultList();
		
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param airport
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ULDDiscrepancy> findULDDiscrepanciesObjectsAtAirport(String companyCode,String airport)
	throws SystemException,PersistenceException{
		log.entering("ULDObjectDAO","findULDDiscrepanciesObjects");
		int index=0;
		ObjectQuery qry = getQueryManager().createNamedQuery(FIND_ULDDISCREPANICIES_OBJECTS_AT_AIRPROT);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, airport);
		log.exiting("ULDObjectDAO","findULDDiscrepanciesObjects");
		return qry.getResultList();
		
	}
	
	
	/**
	* @author a-3278
	* @param companyCode
	* @param uldnos
	* @return Collection<ULDDiscrepancy>
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<ULDDiscrepancy> findULDDiscrepancies(
		String companyCode , Collection<String> uldnos)
	throws SystemException,PersistenceException{
		log.entering("ULDObjectDAO","findULDDiscrepancies");
		int index=0;
		String baseQuey = getQueryManager().getNamedNativeQueryString(FIND_ULDDISCREPANICIES);
		StringBuilder baseQueryBuilder = new StringBuilder(baseQuey);
		//modified for bug 52186 on 13Jul09
		if(uldnos != null && uldnos.size() > 0){
			int count = uldnos.size();
			int j = 0;
			StringBuilder sbd = new StringBuilder();
			for(j = 0; j < count ; j++){
				if(j == 0){
					sbd.append(" AND uLDDiscrepancy.uldDiscrepancyPK.uldNumber IN(?,");
				}
				else if(j != 0 && j % 900 == 0){
					sbd.deleteCharAt(sbd.length()-1);
					sbd.append(" ) AND uLDDiscrepancy.uldDiscrepancyPK.uldNumber IN(?,");
				}else{
					sbd.append("?,"); 
				}
			}
			sbd.deleteCharAt(sbd.length()-1);
			sbd.append(" )");
			baseQueryBuilder.append(sbd);
		}
		ObjectQuery qry = getQueryManager().createQuery(baseQueryBuilder.toString());
		qry.setParameter(++index ,companyCode);
		log.log(Log.FINE, "Query generated is----@@----->", baseQueryBuilder.toString());
		if(uldnos != null && uldnos.size() > 0){
			for(String uldnum : uldnos){					
				qry.setParameter(++index, uldnum);
			}
		}				
		//bug 52186 ends
		return qry.getResultList();
	}
	
	/**
	 * @author A-2408
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * this method fetches all uld object for which scm flag is present
	 */
	public Collection<ULD> findAllULDObjectsforSCM(String companyCode)
	throws SystemException,PersistenceException{
		log.entering("ULDObjectDAO","findAllULDObjectsforSCM");
		int index=0;
		String basequery =  getQueryManager().getNamedNativeQueryString(FIND_ALL_ULD_OBJECTS);
		StringBuilder baseQueryBuilder = new StringBuilder(basequery);
		baseQueryBuilder.append(" AND uld.scmFlag IS NOT NULL ");
		ObjectQuery qry = getQueryManager().createQuery(baseQueryBuilder.toString());
		qry.setParameter(++index, companyCode);
		return qry.getResultList();
	}
	//added by a-3045 for CR QF1020 starts
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	public Collection<ULD> findAllLostULDObjects(String companyCode, int period)
	throws SystemException,PersistenceException{
		log.entering("ULDObjectDAO","findAllLostULDObjects");
		int index=0;
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_LOST_ULD_OBJECTS);	
		if(isOracleDataSource()){
		   String dynamicQuery = " (add_months(sysdate,-?)) ";
		   queryString = String.format(queryString, dynamicQuery);
		}else{
		   String dynamicQuery = " (add_months(sysdate(),-?)) ";
		   queryString = String.format(queryString, dynamicQuery);
		}
		
		ObjectQuery qry = getQueryManager().createQuery(queryString);
		
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, period);
		log.log(Log.FINE, "\n\n\n\n*************companyCode********",
				companyCode);
		log.log(Log.FINE, "\n\n\n\n*************period********", period);
		return qry.getResultList();
	}
	//added by a-3045 for CR QF1020 ends
	/**
	 * Added by A-3415 for ICRD-114538
	 * @param
	 */
	public Collection<ULDTransaction> findOpenTxnULDObjects(TransactionFilterVO filterVO)
	throws SystemException,PersistenceException{
		log.entering("ULDObjectDAO","findOpenTxnULDObjects");
		int index=0;
		String basequery = getQueryManager().getNamedNativeQueryString(FIND_OPENULDTXN_OBJECTS);
		StringBuilder baseQueryBuilder = new StringBuilder(basequery);		
		if(filterVO.getFromPartyCode()!=null && filterVO.getFromPartyCode().trim().length() > 0){
			baseQueryBuilder.append(" AND uLDTransaction.returnPartyCode = ? ");
		}
		if(filterVO.getToPartyCode()!=null && filterVO.getToPartyCode().trim().length() > 0){
			baseQueryBuilder.append(" AND uLDTransaction.partyCode = ? ");
		}
		ObjectQuery qry = getQueryManager().createQuery(baseQueryBuilder.toString());
		qry.setParameter(++index, filterVO.getCompanyCode());
		qry.setParameter(++index, filterVO.getUldNumber());
		if(filterVO.getFromPartyCode()!=null && filterVO.getFromPartyCode().trim().length() > 0){
			qry.setParameter(++index, filterVO.getFromPartyCode().toUpperCase());
		}
		if(filterVO.getToPartyCode()!=null && filterVO.getToPartyCode().trim().length() > 0){
			qry.setParameter(++index, filterVO.getToPartyCode().toUpperCase());
		}
		return qry.getResultList();
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.uld.defaults.ULDObjectQueryInterface#findULDObjectsForDepreciation(java.lang.String)
	 *	Added by 			: A-7359 on 09-May-2018
	 * 	Used for 	:	ICRD-233082 ULD Current Value Depreciation
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 * @throws SystemException 
	 * @throws PersistenceException 
	 */
	@Override
	public Collection<ULD> findULDObjectsForDepreciation(String companyCode) throws PersistenceException, SystemException {
		log.entering("ULDObjectDAO","findULDObjectsForDepreciation");
		int index=0;
		
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,false);
		String currentDateString = currentDate.toStringFormat("yyyy-MM-dd").substring(0, 10);
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_DEPRECIATION_ULD_OBJECTS);	
		if(isOracleDataSource()){
		   String dynamicQuery = " TRUNC(to_date(?,'yyyy-mm-dd')) -365 ";
		   queryString = String.format(queryString, dynamicQuery);
		}else{
		   String dynamicQuery = " TRUNC(to_date(?,'yyyy-mm-dd')) -( 365::NUMERIC || ' days')::INTERVAL ";
		   queryString = String.format(queryString, dynamicQuery);
		}
		
		ObjectQuery qry = getQueryManager().createQuery(queryString);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, currentDateString);
		
		return qry.getResultList();
	}
	//added by A-8368 for ICRD-333282 starts
	/**
	 * 
	 * @param companyCode
	 * @param rowCount
	 * @return
	 */
	public Collection<ULDFlightMessageReconcileVO> findUldsForMarkMovement(String companyCode, int rowCount)
	throws PersistenceException, SystemException {
		log.entering("ULDDefaultsSqlDAO","findUldsForMarkMovement");
		int index=0;
		Query qry = null;
		if(isOracleDataSource()){
		 qry = getQueryManager().createNamedNativeQuery(FIND_ULDS_FOR_MARKMOVEMENT);
		 qry.append("ORDER BY LEG.STA ASC"); 
		}else{
		 qry = getQueryManager().createNamedNativeQuery(POSTGRES_FIND_ULDS_FOR_MARKMOVEMENT);
		 qry.append("ORDER BY mst.STA ASC");
		}
		
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, rowCount);
		log.log(Log.FINE, "\n\n\n\n*************companyCoder********",
				companyCode);
		log.log(Log.FINE, "\n\n\n\n*************period********", rowCount);
		 Collection<ULDFlightMessageReconcileVO> ULDFlightMessageReconcileVOs =  qry.getResultList(new ULDMovementMapper());
		return ULDFlightMessageReconcileVOs;
	}
}
