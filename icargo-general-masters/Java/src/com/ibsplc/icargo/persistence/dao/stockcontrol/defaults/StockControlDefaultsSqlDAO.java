/*
 * StockControlDefaultsSqlDAO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentForBookingVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.InformAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderStockDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockReuseHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.PageableQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Procedure;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 *
 */
/**
 * @author a-2871
 *
 */
public class StockControlDefaultsSqlDAO extends AbstractQueryDAO implements
		StockControlDefaultsDAO {


	private static final int SUBSTRING_COUNT = 7;

	private static final int STRING_START = 0;
	//Added by A-8368 as part of sonar issue - IASCB-104546
	private static final String STATUS = "STATUS";

	private static final String DEFAULT_STOCKHOLDERCODE_FOR_CTO = "stock.defaults.defaultstockholdercodeforcto";

	private static final String STOCKCONTROL_DEFAULTS_FINDRESERVATIONDETAILS = "stockcontrol.defaults.findReservationDetails";

	private static final String STOCKCONTROL_DEFAULTS_FIND_STOCKHOLDER_DETAILS = "stockcontrol.defaults.findStockHolderDetails";

	private static final String STOCKCONTROL_DEFAULTS_FIND_STOCK_DETAILS = "stockcontrol.defaults.findStock";

	private static final String STOCKCONTROL_DEFAULTS_FIND_STOCKREQUEST_DETAILS = "stockcontrol.defaults.findStockRequestDetails";

	private static final String STOCKCONTROL_DEFAULTS_FIND_STOCKHOLDER_TYPES = "stockcontrol.defaults.findStockHolderTypes";

	private static final String STOCKCONTROL_DEFAULTS_FIND_STOCKREQUESTS = "stockcontrol.defaults.findStockRequests";

	private static final String STOCKCONTROL_DEFAULTS_FIND_APPROVER_LOV = "stockcontrol.defaults.findStockLov";

	private static final String STOCKCONTROL_DEFAULTS_ROWNUM_RANK_QUERY= "SELECT RESULT_TABLE.* ,ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM(";
	
	   private static final String STOCKCONTROL_DEFAULTS_SUFFIX_QUERY=") RESULT_TABLE";

	private static final String STOCKCONTROL_DEFAULTS_VALIDATESTOCKHOLDER = "stockcontrol.defaults.validateStockHolder";

	private static final String STOCKCONTROL_DEFAULTS_VALIDATE_STOCKHOLDER_TYPE = "stockcontrol.defaults.validateStockHolderTypeCode";

	private static final String STOCKCONTROL_DEFAULTS_FIND_STOCKHOLDERS = "stockcontrol.defaults.findStockHolders";

	private static final String STOCKCONTROL_DEFAULTS_FIND_RANGES = "stockcontrol.defaults.findRanges";

	private static final String STOCKCONTROL_DEFAULTS_FIND_ALLOCATED_RANGES = "stockcontrol.defaults.findAllocatedRanges";

	private static final String STOCKCONTROL_DEFAULTS_MONITOR_ALLOCATED_AVAILABLE_STOCK = "stockcontrol.defaults.allocatedStock";

	private static final String STOCKCONTROL_DEFAULTS_MONITOR_PLACED_RECIEVED_STOCK = "stockcontrol.defaults.placedStock";

	private static final String STOCKCONTROL_DEFAULTS_FIND_STOCKHOLDER_CODES = "stockcontrol.defaults.findStockHolderCodes";

	private static final String STOCKCONTROL_DEFAULTS_CHECKDUPLICATERANGE = "stockcontrol.defaults.checkDuplicateRange";

	private static final String STOCKCONTROL_DEFAULTS_FINDBLACKLISTRANGES = "stockcontrol.defaults.findBlacklistRanges";
	private static final String STOCKCONTROL_DEFAULTS_FINDBLACKLISTRANGES_FOR_BLACKLIST = "stockcontrol.defaults.findBlacklistRangesForBlacklist";
	
	 private static final String STOCKCONTROL_DEFAULTS_FIND_AVLRNG_FORCUSTOMER = "stockcontrol.defaults.findAvailableRangeForCustomer";
	private static final String STOCKCONTROL_DEFAULTS_FINDBLACKLISTRANGESFORREVOKE = "stockcontrol.defaults.findBlacklistRangesForRevoke";

	private static final String STOCKCONTROL_DEFAULTS_CHECKBLACKLISTRANGE = "stockcontrol.defaults.checkBlacklistRanges";

	private static final String STOCKCONTROL_DEFAULTS_FIND_BLACKLISTED_STOCK = "stockcontrol.defaults.findBlacklistedStock";

	private static final String STOCKCONTROL_DEFAULTS_CHECK_TYPE_CODE = "stockcontrol.defaults.checkTypeCode";

	private static final String STOCKCONTROL_DEFAULTS_FIND_PRIORITIES = "stockcontrol.defaults.findPriorities";

	 private static final String	STOCKCONTROL_DEFAULTS_FINDSTOCKHOLDERFORCUSTOMER = "stockcontrol.defaults.findStockHolderForCustomer";
	
	private static final String STOCKCONTROL_DEFAULTS_FIND_APPROVERCODE = "stockcontrol.defaults.findApproverCode";

	private static final String STOCKCONTROL_DEFAULTS_STOCK_EXISTS = "stockcontrol.defaults.stockExists";

	private static final String STOCKCONTROL_DEFAULTS_FINDRANGEDELETE = "stockcontrol.defaults.findRangesDelete";

	//private static final String STOCKCONTROL_DEFAULTS_VALIDATE_DOCUMENT = "stockcontrol.defaults.validateDocument";

	private static final String STOCKCONTROL_DEFAULTS_CHECK_FOR_BLACKLISTED_DOCUMENT = "stockcontrol.defaults.checkForBlacklistedDocument";

	private static final String STOCKCONTROL_DEFAULTS_IS_ALREADY_BLACKLISTED = "stockcontrol.defaults.alreadyBlackListed";

	private static final String STOCKCONTROL_DEFAULTS_IS_DOC_BLACKLISTED = "stockcontrol.defaults.checkIfDocNumberBlackListed";

	private static final String STOCKCONTROL_DEFAULTS_IS_DOC_VOID = "stockcontrol.defaults.checkIfDocNumberIsVoid";

	private static final String STOCKCONTROL_DEFAULTS_FINDSTOCKFORAIRLINE = "stockcontrol.defaults.findStockForAirline";

	private static final String STOCKCONTROL_DEFAULTS_FINDSTOCKLIST = "stockcontrol.defaults.findStockList";

	private static final String STOCKCONTROL_DEFAULTS_FINDSTOCKFORSTOCKHOLDER = "stockcontrol.defaults.findStockForStockHolder";

	private static final String STOCKCONTROL_DEFAULTS_FINDSTOCKRQSTFOROAL = "stockcontrol.defaults.findStockRequestForOAL";

	private static final String STOCKCONTROL_DEFAULTS_FINDRESERVATIONLISTING = "stockcontrol.defaults.findReservationListing";

	private static final String STOCKCONTROL_DEFAULTS_FINDEXPIREDRESERVEAWBS = "stockcontrol.defaults.findExpiredReserveAwbs";

	private static final String STOCKCONTROL_DEFAULTS_FINDDOCUMENTDETAILS = "stockcontrol.defaults.findDocumentDetails";

	private static final String STOCKCONTROL_DEFAULTS_FINDALLSTOCKHOLDERDETAILS = "stockcontrol.defaults.findAllStockHolderDetails";

	private static final String	STOCKCONTROL_DEFAULTS_CHECKAPPROVER = "stockcontrol.defaults.checkApprover";

	private static final String	STOCKCONTROL_DEFAULTS_FINDRANGEFORTRANSFER = "stockcontrol.defaults.findRangeForTransfer";

	private static final String	STOCKCONTROL_DEFAULTS_FINDSTOCKAGENTMAPPINGS = "stockcontrol.defaults.findStockAgentMappings";

	private static final String	STOCKCONTROL_DEFAULTS_CHECKDOCUMENTEXISTSINANYSTOCK = "stockcontrol.defaults.checkDocumentExistsInAnyStock";

	private static final String	STOCKCONTROL_DEFAULTS_FINDSUBTYPEFORDOCUMENT = "stockcontrol.defaults.findSubTypeForDocument";

	private static final String	STOCKCONTROL_DEFAULTS_FINDAGENTSFORSTOCKHOLDER = "stockcontrol.defaults.findAgentsForStockHolder";

	private static final String	STOCKCONTROL_DEFAULTS_FINDAUTOPROCESSINGQUANTITY = "stockcontrol.defaults.findAutoProcessingQuantity";

	private static final String	STOCKCONTROL_DEFAULTS_FINDSTOCKTYPEPRIORITY = "stockcontrol.defaults.findStockTypePriority";

	private static final String	STOCKCONTROL_DEFAULTS_CHECKDUPLICATEHQ = "stockcontrol.defaults.checkForDuplicateHeadQuarters";

	private static final String	STOCKCONTROL_DEFAULTS_CHECKRESERVEDDOCUMENTEXISTS = "stockcontrol.defaults.checkReservedDocumentExists";

	private static final String	STOCKCONTROL_DEFAULTS_FIND_STOCKRANGE_UTILISATION = "stockcontrol.defaults.findStockRangeUtilisation";

	private static final String STOCKCONTROL_DEFAULTS_DELETESTOCKRANGEUTILISATION = "stockcontrol.defaults.deleteStockRangeUtilisation";

	private static final String STOCKCONTROL_DEFAULTS_FIND_STOCKRANGE_UTILISATIONEXISTS = "stockcontrol.defaults.findStockRangeUtilisationExists";

	private static final String STOCKCONTROL_DEFAULTS_CHECK_STOCKRANGE_UTILISED = "stockcontrol.defaults.checkStockRangeUtilised";

	private static final String	STOCKCONTROL_DEFAULTS_MONITORSTOCK =
		"stockcontrol.defaults.monitorStockForAStockHolder";

	private static final String STOCKCONTROL_DEFAULTS_FIND_FINDPRIVILEAGE = "stockcontrol.defaults.findPrivileage";

	private static final String STOCKCONTROL_DEFAULTS_FIND_FINDRANGESTOBEDELETEDFORBLACKLISTING =
		"stockcontrol.defaults.findRangesToBeDeletedForBlacklisting";
	private static final String STOCKCONTROL_DEFAULTS_FIND_FINDRANGESFORVIEWRANGE =
	"stockcontrol.defaults.findRangesForViewRange";

	private static final String POSTGRES_STOCKCONTROL_DEFAULTS_FIND_FINDRANGESFORVIEWRANGE =
	"postgres.stockcontrol.defaults.findRangesForViewRange";

	private static final String STOCKCONTROL_DEFAULTS_FIND_FINDAVAILABLESTOCKQTY =
	"stockcontrol.defaults.findAvailableStockQuantity"; 
	
	private static final String STOCKCONTROL_DEFAULTS_FIND_FINDRANGESFORMERGE = "stockcontrol.defaults.findrangesformerge";
	 
	private static final String STOCKCONTROL_DEFAULTS_FIND_AWBSTOCKDETAILS = "stockcontrol.defaults.findawbstockdetailsforprint";
	
	private static final String  STOCKCONTROL_DEFAULTS_CHECK_AWB_IN_OPS="stockcontrol.defaults.checkAWBExistsInOperation";
	
	private static final String STOCKCONTROL_DEFAULTS_CHECK_UTL_LOG="stockcontrol.defaults.checkStockRangeUtilisedLog";
   
	private static final String	STOCKCONTROL_DEFAULTS_FIND_CUSTOMER_STKDETAILS = "stockcontrol.defaults.findStockDetailsForCustomer";
	
	private static final String STOCKCONTROL_DEFAULTS_FINDUSEDRANGESFORCUSTOMER = "stockcontrol.defaults.findUsedRangeForCustomer";
	
	private static final String STOCKCONTROL_DEFAULTS_FIND_ALLOCRNG_FORCUSTOMER = "stockcontrol.defaults.findAllocatedRangeForCustomer";
	
	private static final String	STOCKCONTROL_DEFAULTS_LIST_STOCK_DETAILS = "stockcontrol.defaults.listStockDetails";

	private static final String	STOCKCONTROL_DEFAULTS_LIST_STOCK_SUMMARY_DETAILS = "stockcontrol.defaults.listStockSummaryDetails";
	private static final String STOCKCONTROL_DEFAULTS_FIND_RANGESFORHQ = "stockcontrol.defaults.findRangesForHQ";

	private static final String STOCKCONTROL_DEFAULTS_FINDUTILISATIONRANGESFORMERGE="stockcontrol.defaults.findUtilisationRangesForMerge";

	private static final String STOCKCONTROL_DEFAULTS_FINDSTOCKRANGEHISTORY="stockcontrol.defaults.findStockRangeHistory";

	private static final String STOCKCONTROL_DEFAULTS_FINDAWBSTOCKDETAILS="stockcontrol.defaults.findAwbStockDetails";

	private static final String STOCKCONTROL_DEFAULTS_FINDSTOCKUTILISATIONHISTORY="stockcontrol.defaults.findStockUtilisationDetails";

	private static final String STOCKCONTROL_DEFAULTS_FINDSTOCKUTILISATIONHISTORYUSED="stockcontrol.defaults.findStockUtilisationDetailsUsed";

	private static final String STOCKCONTROL_DEFAULTS_VALIDATESTOCKPERIOD="stockcontrol.defaults.validatestockperiod";

	private static final String STOCKCONTROL_DEFAULTS_FINDDISTRIBUTEDRANGESFORHQ="stockcontrol.defaults.findDistributedRangesForHQ";

	private static final String STOCKCONTROL_DEFAULTS_FIND_FINDALLOCATEDSTOCKQTY="stockcontrol.defaults.findAllocatedQty";

	private static final String STOCKCONTROL_DEFAULTS_FINDUSEDRANGESFORMERGE="stockcontrol.defaults.findUsedRangesForMerge";

	private static final String STOCKCONTROL_DEFAULTS_FINDUSEDRANGESINHIS="stockcontrol.defaults.findUsedRangesInHis";

	private static final String	STOCKCONTROL_DEFAULTS_FINDSUBTYPEFORREOPENDOCUMENT = "stockcontrol.defaults.findSubTypeForReopenDocument";

	private static final String STOCKCONTROL_DEFAULTS_FINDAGENTCSVDETAILS="stockcontrol.defaults.findAgentCSVDetails";

	private static final String STOCKCONTROL_DEFAULTS_CHECKDOCUMENTSTATUS="stockcontrol.defaults.checkDocumentStatus";

	private static final String STOCKCONTROL_DEFAULTS_SUBTYPESFORSTOCKHOLDER="stockcontrol.defaults.findsubtypesforstockholder";

	private static final String STOCKCONTROL_DEFAULTS_FINDUSEDSTOCKSFROMUTILISATION="stockcontrol.defaults.findusedstocksfromutilisation";

	private static final String STOCKCONTROL_DEFAULTS_CHECKFORAPPROVERSTOCK="stockcontrol.defaults.checkforapproverstock";

	private static final String STOCKCONTROL_DEFAULTS_FINDONLYUSEDSTOCKFROMUTL="stockcontrol.defaults.findonlyusedstockfromutl";

	private static final String STOCKCONTROL_DEFAULTS_CHECKUSEDRANGESINHIS="stockcontrol.defaults.checkUsedRangesInHis";

	private static final String STOCKCONTROL_DEFAULTS_CHECKRANGESINSTKRNGUTL="stockcontrol.defaults.checkRangesInStockUtilisation";
	private static final String STOCKCONTROL_DEFAULTS_RETURNFIRSTLEVELSTOCKHOLDER="stockcontrol.defaults.returnfirstlevelstockholder";

	private static final String STOCKCONTROL_DEFAULTS_CHECKUSEDRANGESINUTLHIS="stockcontrol.defaults.checkUsedRangesInUTLHis";

	private static final String STOCKCONTROL_DEFAULTS_FINDSUBTYPEFROMUTL="stockcontrol.defaults.finddocsubtypefromutl";
	
	
	private static final String	STOCKCONTROL_DEFAULTS_LIST_STOCK_SUMMARY_DETAILS_PROCEDURE = "stockcontrol.defaults.listStockSummaryDetailsProcedure";
	
	private static final String STOCKCONTROL_DEFAULTS_AUTODEPLETESTOCK = "stockcontrol.defaults.autodepletestock";
	private static final String STOCKCONTROL_DEFAULTS_CHECKSTOCKUTILISATIONFORRANGE = "stockcontrol.defaults.checkUtilisationForRange";
	private static final String POSTGRES_STOCKCONTROL_DEFAULTS_CHECKSTOCKUTILISATIONFORRANGE = "postgres.stockcontrol.defaults.checkUtilisationForRange";

	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";
	
	private static final String DOCTYP_AWB="AWB";
	private static final String STOCKCONTROL_DEFAULTS_FINDBLACKLISTFROMUTL = "stockcontrol.defaults.findBlacklistedStockFromUTL";//Added By A-6767 For ICRD-139677
	
	private static final String STOCKCONTROL_DEFAULTS_DELETEBLACKLISTEDSTK_FROM_UTL = "stockcontrol.defaults.deleteBlacklistedStockFromUTL";//Added By A-6767 For ICRD-139677
	
	private static final String STOCKCONTROL_DEFAULTS_FINDAUTOPOPULATE_SUBTYPE="stockcontrol.defaults.findAutoPopulateSubtype";
	
	private static final String STOCKCONTROL_DEFAULTS_REMOVE_STOCK_REUSE_HISTORY = "stockcontrol.defaults.removeStockReuseHistory";

	private static final String STOCKCONTROL_DEFAULTS_FIND_STOCK_REUSE_HISTORY = "stockcontrol.defaults.findStockReuseHistory";
	
	private Log log = LogFactory.getLogger("STOCK CONTROL SQLDAO");


	/**
	 * This method returns stockvalidationVo for all valid stockholders in the
	 * given collection.
	 *
	 * @param companyCode
	 * @param stockHolderCodes
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> findStockHoldersForValidation(String companyCode,
			Collection<String> stockHolderCodes) throws SystemException,
			PersistenceException {
		log.log(Log.FINE, "-------->Inside Validate SqlDAO------->");
		Query qry = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_VALIDATESTOCKHOLDER);
		qry.append(getInWhereClause(stockHolderCodes));
		qry.setParameter(1, companyCode);
		return qry.getResultList(new ValidateStockHolderMapper());
	}

	/**
	 * @author A-1883
	 * @param stockRequestVO
	 * @return ollection<String>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> validateStockHolderTypeCode(
			StockRequestVO stockRequestVO) throws SystemException,
			PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_VALIDATE_STOCKHOLDER_TYPE);
		int index = 0;
		query.setParameter(++index, stockRequestVO.getCompanyCode());
		query.setParameter(++index, stockRequestVO.getStockHolderCode());
		query.setParameter(++index, stockRequestVO.getStockHolderType());
		if(stockRequestVO.getDocumentType()!=null && stockRequestVO
				.getDocumentType().trim().length()>0){
					query.append(" AND stkhldstk.DOCTYP = ? ");
			query.setParameter(++index, stockRequestVO.getDocumentType());
		}
		if(stockRequestVO.getDocumentSubType()!=null && stockRequestVO
				.getDocumentSubType().trim().length()>0){
					query.append(" AND stkhldstk.DOCSUBTYP = ? ");
			query.setParameter(++index, stockRequestVO.getDocumentSubType());
		}
		return query.getResultList(new ValidateStockHolderTypeCodeMapper());
	}

	/**
	 * This method issues a DAO query to obtain the details of a particular
	 * stockholder
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @return StockHolderVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public StockHolderVO findStockHolderDetails(String companyCode,
			String stockHolderCode) throws SystemException,
			PersistenceException {
		StockHolderVO stockHolderVO = new StockHolderVO();
		stockHolderVO = findStockHolderSqlDetails(companyCode, stockHolderCode);
		if(stockHolderVO != null) {
			stockHolderVO.setStock(findStockDetails(companyCode, stockHolderCode));
		}
		return stockHolderVO;
	}

	/**
	 * This method finds details for stock holders which meet the filter
	 * criteria and all stock holders who fall under them.
	 *
	 * @param filterVO
	 * @return Collection<StockHolderDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	/*public Page<StockHolderDetailsVO> findStockHolders(
			StockHolderFilterVO filterVO) throws SystemException,
			PersistenceException {
		Query query =getQueryManager().createNamedNativeQuery
		(STOCKCONTROL_DEFAULTS_FINDSTOCKTYPEPRIORITY);
		query.setParameter(1,filterVO.getCompanyCode());
		query.setParameter(2,filterVO.getStockHolderType());
		int priority = query.getSingleResult(getIntMapper("STKHLDPRY"));
		log.log(Log.FINE,"the priority:----"+priority);
		filterVO.setStockHolderPriority(priority);
		String baseQry = getQueryManager().getNamedNativeQueryString(
				STOCKCONTROL_DEFAULTS_FIND_STOCKHOLDERS);
		query = new StockHolderFilterQuery(filterVO, baseQry);
		Page<StockHolderDetailsVO> stockHolders = query
				.getResultList(new StockHolderMapper());
		log.log(Log.FINE, "\n\n ===============After Mapper======"
				+ stockHolders);
		Collection<StockHolderDetailsVO> stockHolderTree = buildStockHolderTree(
				stockHolders, filterVO);
		return stockHolderTree;

	}
	 */

	public Page<StockHolderDetailsVO> findStockHolders(
			StockHolderFilterVO filterVO)
			throws SystemException, PersistenceException {
		log.log(Log.FINE,"----------findStockHolders:-------------------*****---");
		Query query =getQueryManager().createNamedNativeQuery
		(STOCKCONTROL_DEFAULTS_FINDSTOCKTYPEPRIORITY);

		query.setParameter(1,filterVO.getCompanyCode());
		query.setParameter(2,filterVO.getStockHolderType());
		int priority = query.getSingleResult(getIntMapper("STKHLDPRY"));
		log.log(Log.FINE, "the priority@@@@@@@@:----", priority);
		filterVO.setStockHolderPriority(priority);
		
		StringBuilder rankQuery=new StringBuilder();
		rankQuery.append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_ROWNUM_QUERY);
		String nativeQuery = getQueryManager().getNamedNativeQueryString(
				STOCKCONTROL_DEFAULTS_FIND_STOCKHOLDERS);
		String baseQuery=rankQuery.append(nativeQuery).toString();
		 //query = new StockHolderFilterQuery(filterVO, baseQry);

		// log.log(Log.FINE,"the priority##################:----");

		// PageableQuery<StockHolderDetailsVO> pageableQuery = new PageableQuery<StockHolderDetailsVO>(
		//			query, new StockHolderMapper());
		//Added by A-5175 for icrd-20959 starts
		 PageableNativeQuery<StockHolderDetailsVO> pageableNativeQuery=new StockHolderFilterQuery(filterVO.getTotalRecordCount(),new StockHolderMapper(), filterVO, baseQuery);
         pageableNativeQuery.append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_SUFFIX_QUERY);
		 
		 /*Page<StockHolderDetailsVO> op= pageableQuery.getPageAbsolute
		 (filterVO.getPageNumber(),
					filterVO.getAbsoluteIndex());
			if(op!=null){
				log.log(Log.FINE,"----------ACTUAL PAGE SIZE:----"+op.getActualPageSize());
		 		log.log(Log.FINE,"----------DISPLAY PAGE SIX:---"+op.getDefaultPageSize());
			}
	 		log.log(Log.FINE, "\n\n\n%%%%%%resultMonitorVOs%%%%%%%"
					+ op);
			Collection<StockHolderDetailsVO> stockHolders = query
					.getResultList(new StockHolderMapper());
			log.log(Log.FINE, "\n\n ===============After Mapper======"
					+ stockHolders);
			Collection<StockHolderDetailsVO> stockHolderTree = buildStockHolderTree(
					stockHolders, filterVO);
					*/
			return pageableNativeQuery.getPage(filterVO.getPageNumber());

			//Added by A-5175 for icrd-20959 ends

		   //return pageableQuery.getPage(displayPage);
	}
	/**
	 * @author a-4421
	 * @param source
	 * @throws SystemException
	 */
	public void updateStockUtilization(String source)throws SystemException{
		log.log(Log.FINE,"------>updateStockUtilization in sqlDAO");
		Procedure utilProcedure = getQueryManager().createNamedNativeProcedure(STOCKCONTROL_DEFAULTS_LIST_STOCK_SUMMARY_DETAILS_PROCEDURE);
		LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
		
		utilProcedure.setSensitivity(true);
		utilProcedure.setParameter(1, logonAttributes.getCompanyCode());
		utilProcedure.setParameter(2, logonAttributes.getUserName());
		utilProcedure.setParameter(3, source);
		utilProcedure.setParameter(4, "OK");
	//	utilProcedure.setOutParameter(5, SqlType.STRING);  
		utilProcedure.execute();
	}
	/**
	 * @author a-4421
	 * @param filterVO
	 * @return 
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<StockDetailsVO> listStockDetails(
			StockDetailsFilterVO filterVO)
			throws SystemException, PersistenceException {
		log.log(Log.FINE,"----------listStockDetails:-------------------*****---");
		updateStockUtilization("LIST");
		
		PageableQuery<StockDetailsVO> pgqry = null;
		Query query = null;
		int index = 0;
		if(filterVO.getStockHolderCode()==null || filterVO.getStockHolderCode().trim().length()==0){
			log.log(Log.FINE,
					"StockHolder code is not given calling the query",
					STOCKCONTROL_DEFAULTS_LIST_STOCK_SUMMARY_DETAILS);
			query=getQueryManager().createNamedNativeQuery
			(STOCKCONTROL_DEFAULTS_LIST_STOCK_SUMMARY_DETAILS);	
			}
		else{
			
			query=getQueryManager().createNamedNativeQuery
			(STOCKCONTROL_DEFAULTS_LIST_STOCK_DETAILS);
				log.log(Log.FINE,
						"StockHolder code is given calling the query",
						STOCKCONTROL_DEFAULTS_LIST_STOCK_DETAILS);
			
		}
		
		query.setParameter(++index,filterVO.getCompanyCode());
		query.setParameter(++index,filterVO.getDocumentType());	 
		//Modified by A-8445 as part of IASCB-70552
		query.setParameter(++index,filterVO.getEndDate().toStringFormat("yyyy-MM-dd").substring(0,10));
		query.setParameter(++index,filterVO.getStartDate().toStringFormat("yyyy-MM-dd").substring(0,10));
		query.setParameter(++index, filterVO.getStockHolderType());
		
		if (filterVO.getDocumentSubType() != null && filterVO.getDocumentSubType().trim().length()>0) {
			query.append(" AND stk.DOCSUBTYP = ?");
			query.setParameter(++index, filterVO.getDocumentSubType());
		}
		if (filterVO.getStockHolderCode()!= null && filterVO.getStockHolderCode().trim().length()>0) {
			query.append(" AND stk.STKHLDCOD = ?");
			query.setParameter(++index, filterVO.getStockHolderCode());
		}
		if(filterVO.getStockHolderCode()==null || filterVO.getStockHolderCode().trim().length()==0){
			query.append(" GROUP BY stk.STKHLDCOD,  stk.DOCTYP ,  stk.DOCSUBTYP ,"
					+"  stk.CMPCOD ) A , STKHLDSTKDTL B   WHERE a. cmpcod = b.cmpcod AND"
					+" a.stkhldcod = b.stkhldcod AND a.doctyp = b.doctyp AND"
					+" a.docsubtyp = b.docsubtyp AND TRUNC(a.TXNDATUTC) = TRUNC(b.TXNDATUTC)"
					+" ORDER BY a.STKHLDCOD");
		}
		else
		{
			query.append(" ORDER BY TXNDATUTC");
		}
		log.log(Log.FINE, "query in sqlDao------", query);
		pgqry = new PageableQuery <StockDetailsVO>(query,
				new StockDetailsSummaryMapper(),filterVO.getPageSize());
		
		return pgqry.getPage(filterVO.getPageNumber());
	}


	/**
	 * @author a-2871
	 *
	 */
      class StockHolderMapper implements MultiMapper<StockHolderDetailsVO> {



		/**
		 * @param resultSet
		 * @throws
		 * @return
		 */
		public List<StockHolderDetailsVO> map(ResultSet resultSet)
				throws SQLException {

			List<StockHolderDetailsVO> list = new 		ArrayList<StockHolderDetailsVO>();
			while(resultSet.next()){
				StockHolderDetailsVO stockHolderDetailsVO = new StockHolderDetailsVO();
				stockHolderDetailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
				stockHolderDetailsVO.setStockHolderType(resultSet
						.getString("STKHLDTYP"));
				stockHolderDetailsVO.setStockHolderCode(resultSet
						.getString("STKHLDCOD"));
				stockHolderDetailsVO.setAwbPrefix(resultSet.getString("THRNUMCOD"));
				stockHolderDetailsVO.setDocType(resultSet.getString("DOCTYP"));
				stockHolderDetailsVO
						.setDocSubType(resultSet.getString("DOCSUBTYP"));
				if (resultSet.getString("ORDLVL") != null
						&& !"".equals(resultSet.getString("ORDLVL"))) {
					stockHolderDetailsVO.setReorderLevel(Integer.parseInt(resultSet
							.getString("ORDLVL")));
				}
				if (resultSet.getString("ORDQTY") != null
						&& !"".equals(resultSet.getString("ORDQTY"))) {
					stockHolderDetailsVO.setReorderQuantity(Integer
							.parseInt(resultSet.getString("ORDQTY")));
				}
				if ("Y".equals(resultSet.getString("ORDALT"))) {
					stockHolderDetailsVO.setReorderAlert(true);
				} else if ("N".equals(resultSet.getString("ORDALT"))) {
					stockHolderDetailsVO.setReorderAlert(false);
				}
				if ("Y".equals(resultSet.getString("AUTREQFLG"))) {
					stockHolderDetailsVO.setAutoStockRequest(true);
				} else if ("N".equals(resultSet.getString("AUTREQFLG"))) {
					stockHolderDetailsVO.setAutoStockRequest(false);
				}
				stockHolderDetailsVO.setApproverCode(resultSet
						.getString("STKAPRCOD"));
				stockHolderDetailsVO.setLastUpdateTime(new LocalDate
						(LocalDate.NO_STATION,Location.NONE,resultSet
								.getTimestamp("LSTUPDTIM")));
				stockHolderDetailsVO.setLastUpdateUser(resultSet.getString
						("LSTUPDUSR"));
				//increment();
				list.add(stockHolderDetailsVO);
			}
			log.log(Log.FINE, "***** LIst Values *************", list);
					return list;
		}
	}

	public Collection<RangeVO> findRanges(RangeFilterVO rangeFilterVO)
			throws SystemException, PersistenceException {
		log.entering("StockControlDefaultsSqlDAO", "-------findRanges-----");
		String baseQry = getQueryManager().getNamedNativeQueryString(
				STOCKCONTROL_DEFAULTS_FIND_RANGES);
		Query query = new RangeFilterQuery(rangeFilterVO, baseQry);
		query.setSensitivity(true);
		Collection<RangeVO> rangeVOs = query.getResultList(new RangeMapper());
		long difference = 0;
		long sum = 0;
		long stRange = findLong(rangeFilterVO.getStartRange());
		for (RangeVO rangeVO : rangeVOs) {
			if (stRange > findLong(rangeVO.getStartRange())) {
				rangeVO.setStartRange(rangeFilterVO.getStartRange());
				rangeVO.setNumberOfDocuments(difference(
						rangeVO.getStartRange(), rangeVO.getEndRange()));
			}
			if (rangeFilterVO.getEndRange() != null
					&& rangeFilterVO.getEndRange().trim().length() != 0) {
				long edRange = findLong(rangeFilterVO.getEndRange());
				if (edRange < findLong(rangeVO.getEndRange())) {
					rangeVO.setEndRange(rangeFilterVO.getEndRange());
					rangeVO.setNumberOfDocuments(difference(rangeVO
							.getStartRange(), rangeVO.getEndRange()));
				}
			}
			log.log(Log.FINE, "\n****rangeVO*****", rangeVO);
		}
		if (rangeFilterVO.getNumberOfDocuments() != null
				&& rangeFilterVO.getNumberOfDocuments().trim().length() != 0) {
			long noOfDocs = findLong(rangeFilterVO.getNumberOfDocuments());
			Collection<RangeVO> newRangeVOs = new ArrayList<RangeVO>();
			for (RangeVO rangeVO : rangeVOs) {
				if(sum==noOfDocs){									//added by a2434 to avoid extra ranges if both no of docs & End range is specified
					break;
				}
				sum += rangeVO.getNumberOfDocuments();
				if (sum > noOfDocs) {
					log.log(Log.FINE, "Inside sum > noOfDocs ");
					difference = sum - noOfDocs;
					log.log(Log.FINE, "sum----> ", sum);
					log.log(Log.FINE, "difference----> ", difference);
					// long endRange= findLong(rangeVO.getEndRange()) -
					// difference;
					// rangeVO.setEndRange(Long.toString(endRange));
					rangeVO.setEndRange(subtract(rangeVO.getEndRange(),
							difference));
					rangeVO.setNumberOfDocuments(difference(rangeVO
							.getStartRange(), rangeVO.getEndRange()));
					newRangeVOs.add(rangeVO);
					break;                                                 //added by a2434 to avoid extra ranges if both no of docs & End range is specified

				}
				newRangeVOs.add(rangeVO);								   //added by a2434 to avoid extra ranges if both no of docs & End range is specified
			}// for loop
			rangeVOs = newRangeVOs;										   //added by a2434 to avoid extra ranges if both no of docs & End range is specified
		}
		log.log(Log.FINE, "Range VOs are --->", rangeVOs);
		log.exiting("StockControlDefaultsSqlDAO", "---------findRanges---");
		Collection<RangeVO> ranges = getStockRanges(rangeFilterVO);
		if(rangeVOs.size()>0){
			for(RangeVO rangeVO : rangeVOs){
		      	if(ranges!=null){
		      		for(RangeVO range:ranges){
		      			if(DOCTYP_AWB.equals(rangeFilterVO.getDocumentType())){
		      			if((range.getStartRange().substring(0,7)).equals(rangeVO.getStartRange()))
		      				{
		      				rangeVO.setMasterDocumentNumbers(range.getMasterDocumentNumbers());
		      				}
		      			}
		      			else{
		      				if((range.getStartRange()).equals(rangeVO.getStartRange()))
			      				{
			      				rangeVO.setMasterDocumentNumbers(range.getMasterDocumentNumbers());
			      				}
		      			}
		      		}
		      	}
				
			}
		}
		return rangeVOs;
	}

	private Collection<RangeVO> getStockRanges(RangeFilterVO rangeFilterVO)
			throws SystemException, PersistenceException {
		StockFilterVO stockFilterVO = new StockFilterVO();
		stockFilterVO.setCompanyCode(rangeFilterVO.getCompanyCode());
		stockFilterVO.setAirlineIdentifier(rangeFilterVO.getAirlineIdentifier());
		stockFilterVO.setDocumentType(rangeFilterVO.getDocumentType());
		stockFilterVO.setDocumentSubType(rangeFilterVO.getDocumentSubType());
		stockFilterVO.setStockHolderCode(rangeFilterVO.getStockHolderCode());		      	
		stockFilterVO.setManual(rangeFilterVO.isManual());
		return findRangesForViewRange(stockFilterVO);
	}

	/**
	 * This mapper is used for findRanges()
	 *
	 * @author A-1883
	 *
	 */
	private class RangeMapper implements Mapper<RangeVO> {
		/**
		 * This method is used to map the result of query to RangeVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return RangeVO
		 */
		public RangeVO map(ResultSet resultSet) throws SQLException {
			RangeVO rangeVO = new RangeVO();
			rangeVO.setStartRange(resultSet.getString("STARNG"));
			rangeVO.setEndRange(resultSet.getString("ENDRNG"));
			rangeVO.setNumberOfDocuments(resultSet.getLong("DOCNUM"));
			rangeVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
			rangeVO.setCompanyCode(resultSet.getString("CMPCOD"));
			rangeVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
			rangeVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			rangeVO.setDocumentType(resultSet.getString("DOCTYP"));
			rangeVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			rangeVO.setSequenceNumber(resultSet.getString("RNGSEQNUM"));
			return rangeVO;
		}
	}

	/**
	 * @param rangeString
	 * @param offset
	 * @return String
	 */
	private String subtract(String rangeString, long offset) {
		log.entering("StockControlDefaultsSqlDAO", "subtract");
		char[] sArray = rangeString.toCharArray();
		int carry = 0;
		for (long k = 0; k < offset; k++) {
			carry = 1;
			for (int i = sArray.length - 1; i >= 0; i--) {
				int charValue = sArray[i];
				charValue -= carry;
				if (charValue == 47) {
					charValue = 57;
					carry = 1;
				} else if (charValue == 64) {
					charValue = 90;
					carry = 1;
				} else {
					carry = 0;
				}
				// System.out.println("carry :" + carry);
				sArray[i] = (char) charValue;
				if (carry == 0){
					break;
				}
			}
		}
		// if (carry==1) return null;
		// System.out.println(sArray);
		log.exiting("StockControlDefaultsSqlDAO", "subtract");
		return new String(sArray);
	}

	/**
	 * This method is used to monitor the stock of a stock holder
	 *
	 * @param stockFilterVO
	 * @return Collection<MonitorStockVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<MonitorStockVO> monitorStock(StockFilterVO stockFilterVO)
			throws SystemException, PersistenceException {
		log.log(Log.FINE, "\n\n$$$$stockFilterVO$$$$", stockFilterVO);
		List<String> cmpCode = null;
		String stockHolderType = stockFilterVO.getStockHolderType();
		if (stockHolderType != null && stockHolderType.trim().length() != 0) {
			log.log(Log.FINE, "\n\n Caling Validation ");
			Query qry = getQueryManager().createNamedNativeQuery(
					STOCKCONTROL_DEFAULTS_CHECK_TYPE_CODE);
			String code = stockFilterVO.getCompanyCode();
			String stkHolderCode = stockFilterVO.getStockHolderCode();
			qry.setParameter(1, code);
			qry.setParameter(2, stkHolderCode);
			qry.setParameter(3, stockHolderType);
			cmpCode = qry.getResultList(new ValidateMapper());
		}
		log.log(Log.FINE, "\n\n\ncompanyCode", cmpCode);
		if ((cmpCode != null && cmpCode.size() > 0 && cmpCode.get(0).trim()
				.length() != 0)
				|| (stockHolderType == null || stockHolderType.trim().length() == 0)) {
			log.log(Log.FINE, "\n\n Calling Monitor Query");
			StringBuffer masterQuery = new StringBuffer();
		    masterQuery.append(STOCKCONTROL_DEFAULTS_ROWNUM_RANK_QUERY);
		    String query = getQueryManager().getNamedNativeQueryString(
		    		STOCKCONTROL_DEFAULTS_MONITORSTOCK);
		    masterQuery.append(query);
		    PageableNativeQuery<MonitorStockVO> pgNativeQuery = 
    			new PageableNativeQuery<MonitorStockVO>(0,
    					masterQuery.toString(),new MonitorStockMapper());
			int index=0;
			//Query query = getQueryManager().createNamedNativeQuery(
			//		STOCKCONTROL_DEFAULTS_MONITOR_ALLOCATED_AVAILABLE_STOCK);
			String companyCode = stockFilterVO.getCompanyCode();
			String docType = stockFilterVO.getDocumentType();
			String docSubType = stockFilterVO.getDocumentSubType();
			String stockHolderCode = stockFilterVO.getStockHolderCode();
			int airlineId = stockFilterVO.getAirlineIdentifier();
			String manualFlag = "";	
			if (stockFilterVO.isManual()) {
				manualFlag = StockFilterVO.FLAG_YES;	
			} else {
				manualFlag = StockFilterVO.FLAG_NO;	
			}
			/*query.setParameter(1, companyCode);
			query.setParameter(2, stockHolderCode);
			query.setParameter(3, stockHolderCode);
			query.setParameter(4, docType);
			query.setParameter(5, docSubType);
			query.setParameter(6, manualFlag);
			query.setParameter(7, stockHolderCode);
			query.setParameter(8, companyCode);
			query.setParameter(9, stockHolderCode);
			query.setParameter(10, docType);
			query.setParameter(11, docSubType);
			query.setParameter(12, manualFlag);
			query.setParameter(13, companyCode);
			query.setParameter(14, manualFlag);
			query.setParameter(15, docType);
			query.setParameter(16, docSubType);
			query.setParameter(17, stockHolderCode);
			query.setParameter(18, companyCode);
			query.setParameter(19, stockHolderCode);
			query.setParameter(20, stockHolderCode);
			query.setParameter(21, docType);
			query.setParameter(22, docSubType);*/
			/*query.setParameter(1, manualFlag);
			query.setParameter(2, manualFlag);
			query.setParameter(3, manualFlag);
			query.setParameter(4, companyCode);
			query.setParameter(5, stockHolderCode);
			query.setParameter(6, stockHolderCode);
			query.setParameter(7, docType);
			query.setParameter(8, docSubType);
			query.setParameter(9, airlineId);
			query.setParameter(10, companyCode);
			query.setParameter(11, stockHolderCode);
			query.setParameter(12, stockHolderCode);
			query.setParameter(13, docType);
			query.setParameter(14, docSubType);
			query.setParameter(15, airlineId);
			query.setParameter(16, manualFlag);
			query.append(" WHERE Z.STKHLDCOD <> ? ");
			query.setParameter(17,stockHolderCode);*/
			pgNativeQuery.setParameter(++index, manualFlag);
			pgNativeQuery.setParameter(++index, manualFlag);
			pgNativeQuery.setParameter(++index, manualFlag);
			pgNativeQuery.setParameter(++index, manualFlag);
			pgNativeQuery.setParameter(++index, companyCode);
			pgNativeQuery.setParameter(++index, stockHolderCode);
			pgNativeQuery.setParameter(++index, stockHolderCode);
			pgNativeQuery.setParameter(++index, docType);
			pgNativeQuery.setParameter(++index, docSubType);
			pgNativeQuery.setParameter(++index, airlineId);
			pgNativeQuery.setParameter(++index, companyCode);
			pgNativeQuery.setParameter(++index, stockHolderCode);
			pgNativeQuery.setParameter(++index, stockHolderCode);
			pgNativeQuery.setParameter(++index, docType);
			pgNativeQuery.setParameter(++index, docSubType);
			pgNativeQuery.setParameter(++index, airlineId);
			pgNativeQuery.setParameter(++index, manualFlag);
			pgNativeQuery.append(" WHERE Z.STKHLDCOD <> ? ");
			pgNativeQuery.setParameter(++index,stockHolderCode);
			addPrivilegeConditions(stockFilterVO,pgNativeQuery,index);	
			pgNativeQuery.append(" GROUP BY Z.CMPCOD,Z.DOCTYP,Z.DOCSUBTYP,Z.STKHLDCOD");
			pgNativeQuery.append(" ORDER BY Z.STKHLDCOD");
			//log.log(Log.FINE, "\n\n\n%%%%%%resultMonitorVOs%%%%%%%", op);
			pgNativeQuery.append(STOCKCONTROL_DEFAULTS_SUFFIX_QUERY);
			
			return pgNativeQuery.getPage(stockFilterVO.getPageNumber());
		} else {
			return null;
		}

	}

	/**
	 * @author A-1944
	 * @return MonitorStockVO
	 * @param stockFilterVO
	 */
	public MonitorStockVO findMonitoringStockHolderDetails(StockFilterVO
			stockFilterVO)throws SystemException,PersistenceException{
		List<String> cmpCode = null;
		String stockHolderType = stockFilterVO.getStockHolderType();
		if (stockHolderType != null && stockHolderType.trim().length() != 0) {
			log.log(Log.FINE, "\n\n Caling Validation ");
			Query qry = getQueryManager().createNamedNativeQuery(
					STOCKCONTROL_DEFAULTS_CHECK_TYPE_CODE);
			String code = stockFilterVO.getCompanyCode();
			String stkHolderCode = stockFilterVO.getStockHolderCode();
			qry.setParameter(1, code);
			qry.setParameter(2, stkHolderCode);
			qry.setParameter(3, stockHolderType);
			cmpCode = qry.getResultList(new ValidateMapper());
		}
		log.log(Log.FINE, "\n\n\ncompanyCode", cmpCode);
		if ((cmpCode != null && cmpCode.size() > 0 && cmpCode.get(0).trim()
				.length() != 0)
				|| (stockHolderType == null || stockHolderType.trim().length() == 0)) {
			log.log(Log.FINE, "\n\n Calling Monitor Query");
			//Query query = getQueryManager().createNamedNativeQuery(
			//		STOCKCONTROL_DEFAULTS_MONITOR_ALLOCATED_AVAILABLE_STOCK);
			int index = 0;
			Query query = getQueryManager().createNamedNativeQuery
			(STOCKCONTROL_DEFAULTS_MONITORSTOCK);
			query.setSensitivity(true);
			String companyCode = stockFilterVO.getCompanyCode();
			String docType = stockFilterVO.getDocumentType();
			String docSubType = stockFilterVO.getDocumentSubType();
			String stockHolderCode = stockFilterVO.getStockHolderCode();
			int airlineId = stockFilterVO.getAirlineIdentifier();
			String manualFlag = "";
			if (stockFilterVO.isManual()) {
				manualFlag = StockFilterVO.FLAG_YES;
			} else {
				manualFlag = StockFilterVO.FLAG_NO;
			}
		/*	query.setParameter(1, companyCode);
			query.setParameter(2, stockHolderCode);
			query.setParameter(3, stockHolderCode);
			query.setParameter(4, docType);
			query.setParameter(5, docSubType);
			query.setParameter(6, manualFlag);
			query.setParameter(7, stockHolderCode);
			query.setParameter(8, companyCode);
			query.setParameter(9, stockHolderCode);
			query.setParameter(10, docType);
			query.setParameter(11, docSubType);
			query.setParameter(12, manualFlag);
			query.setParameter(13, companyCode);
			query.setParameter(14, manualFlag);
			query.setParameter(15, docType);
			query.setParameter(16, docSubType);
			query.setParameter(17, stockHolderCode);
			query.setParameter(18, companyCode);
			query.setParameter(19, stockHolderCode);
			query.setParameter(20, stockHolderCode);
			query.setParameter(21, docType);
			query.setParameter(22, docSubType);*/

			/*query.setParameter(1, manualFlag);
			query.setParameter(2, manualFlag);
			query.setParameter(3, manualFlag);
			query.setParameter(4, companyCode);
			query.setParameter(5, stockHolderCode);
			query.setParameter(6, stockHolderCode);
			query.setParameter(7, docType);
			query.setParameter(8, docSubType);
			query.setParameter(9, airlineId);
			query.setParameter(10, companyCode);
			query.setParameter(11, stockHolderCode);
			query.setParameter(12, stockHolderCode);
			query.setParameter(13, docType);
			query.setParameter(14, docSubType);
			query.setParameter(15, airlineId);
			query.setParameter(16, manualFlag);
			query.append(" WHERE Z.STKHLDCOD = ? ");
			query.setParameter(17,stockHolderCode);*/
			query.setParameter(++index, manualFlag);
			query.setParameter(++index, manualFlag);
			query.setParameter(++index, manualFlag);
			query.setParameter(++index, manualFlag);
			query.setParameter(++index, companyCode);
			query.setParameter(++index, stockHolderCode);
			query.setParameter(++index, stockHolderCode);
			query.setParameter(++index, docType);
			query.setParameter(++index, docSubType);
			query.setParameter(++index, airlineId);
			query.setParameter(++index, companyCode);
			query.setParameter(++index, stockHolderCode);
			query.setParameter(++index, stockHolderCode);
			query.setParameter(++index, docType);
			query.setParameter(++index, docSubType);
			query.setParameter(++index, airlineId);
			query.setParameter(++index, manualFlag);
			query.append(" WHERE Z.STKHLDCOD = ? ");
			query.setParameter(++index,stockHolderCode);
			addPrivilegeConditions(stockFilterVO,query,index);		
			query.append(" GROUP BY Z.CMPCOD,Z.DOCTYP,Z.DOCSUBTYP,Z.STKHLDCOD ");
			MonitorStockVO monitorStockVO = query
					 .getSingleResult(new MonitorStockForStockHolderMapper());
			log.log(Log.FINE, "\n\n\n%%%%%%resultMonitorVOs%%%%%%%",
					monitorStockVO);
			/*	if(monitorStockVO!=null){
				Collection<MonitorStockVO> mntcol = new ArrayList<MonitorStockVO>();
				mntcol.add(monitorStockVO);
				Collection<RangeVO> ranges = findAvailableStockQty(stockFilterVO,mntcol);
				if(ranges!=null && ranges.size()>0){
					for(RangeVO rngVo : ranges){
						monitorStockVO.setAvailableStock(rngVo.getNumberOfDocuments());
					}
				}
			}	 */
			return monitorStockVO;
		} else {
			return null;
		}

	}
	/**
	 *
	 * @author A-1885
	 *
	 */
	private class MonitorStockForStockHolderMapper implements
	Mapper<MonitorStockVO>{
		/**
		 *
		 */
		public MonitorStockVO map(ResultSet rs)throws SQLException{
			MonitorStockVO stkVo = new MonitorStockVO();
			stkVo.setCompanyCode(rs.getString("CMPCOD"));
			stkVo.setDocumentType(rs.getString("DOCTYP"));
			stkVo.setDocumentSubType(rs.getString("DOCSUBTYP"));
			stkVo.setStockHolderCode(rs.getString("STKHLDCOD"));
			stkVo.setStockHolderType(rs.getString("STKHLDTYP"));
			stkVo.setStockHolderName(rs.getString("STKHLDNAM"));
			stkVo.setApproverCode(rs.getString("STKAPRCOD"));
			/*stkVo.setManAllocatedStock(rs.getLong("MANALCSTK"));
			stkVo.setManAvailableStock(rs.getLong("MANAVLSTK"));
			stkVo.setPhyAllocatedStock(rs.getLong("PHYALCSTK"));
			stkVo.setPhyAvailableStock(rs.getLong("PHYAVLSTK"));
			stkVo.setAllocatedStock(stkVo.getManAllocatedStock()+stkVo.getPhyAllocatedStock());
			stkVo.setAvailableStock(stkVo.getManAvailableStock()+stkVo.getPhyAvailableStock());*/
			stkVo.setAvailableStock(rs.getLong("AVLSTK"));
			//stkVo.setAllocatedStock(rs.getLong("ALCSTK"));
			stkVo.setAllocatedStock(rs.getLong("ALCSTKCNT"));
			stkVo.setRequestsPlaced(rs.getLong("REQ_PLACE"));
			stkVo.setRequestsReceived(rs.getLong("REQ_REC"));
			return stkVo;
		}
	}

	/**
		 *
		 * @author A-1885
		 *
		 */
		private class AvailableQtyMapper implements Mapper<RangeVO>{

			public RangeVO map(ResultSet rs)throws SQLException{
				RangeVO rangeVo = new RangeVO();
				rangeVo.setStockHolderCode(rs.getString("STKHLDCOD"));
				rangeVo.setNumberOfDocuments(rs.getLong("DOCNUM"));
				return rangeVo;
			}

		}


	/**
	 * This mapper is used for monitorStock()
	 *
	 * @author A-1883
	 *
	 */
	private class MonitorStockMapper implements MultiMapper<MonitorStockVO>{
		/**
		 * @param resultSet
		 * @throws SQLException
		 * @return MonitorStockVO
		 */
		public List<MonitorStockVO> map(ResultSet resultSet)
		throws SQLException {
			List<MonitorStockVO> list = null;
			MonitorStockVO stkVo = null;
			while(resultSet.next()){
				if(stkVo!= null){
					list.add(stkVo);
				}
				stkVo = new MonitorStockVO();
				stkVo.setCompanyCode(resultSet.getString("CMPCOD"));
				stkVo.setDocumentType(resultSet.getString("DOCTYP"));
				stkVo.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
				stkVo.setStockHolderCode(resultSet.getString("STKHLDCOD"));
				stkVo.setStockHolderType(resultSet.getString("STKHLDTYP"));
				stkVo.setStockHolderName(resultSet.getString("STKHLDNAM"));
				stkVo.setApproverCode(resultSet.getString("STKAPRCOD"));
				/*stkVo.setManAllocatedStock(resultSet.getLong("MANALCSTK"));
				stkVo.setManAvailableStock(resultSet.getLong("MANAVLSTK"));
				stkVo.setPhyAllocatedStock(resultSet.getLong("PHYALCSTK"));
				stkVo.setPhyAvailableStock(resultSet.getLong("PHYAVLSTK"));
				stkVo.setAllocatedStock(stkVo.getManAllocatedStock()+stkVo.getPhyAllocatedStock());
				stkVo.setAvailableStock(stkVo.getManAvailableStock()+stkVo.getPhyAvailableStock());*/
				stkVo.setAvailableStock(resultSet.getLong("AVLSTK"));
				//stkVo.setAllocatedStock(resultSet.getLong("ALCSTK"));
				stkVo.setAllocatedStock(resultSet.getLong("ALCSTK"));
				stkVo.setRequestsPlaced(resultSet.getLong("REQ_PLACE"));
				stkVo.setRequestsReceived(resultSet.getLong("REQ_REC"));
				if(list==null){
					list = new ArrayList<MonitorStockVO>();
				}
			}
//			This part of the code is required to add the last parent
//			The last parent wont be added by the main loop
			if(stkVo != null){
				list.add(stkVo);
			}
			return list;
		}
	}

	/**
	 * @author A-1883
	 */
	private class MonitorMapper implements Mapper<MonitorStockVO> {
		/**
		 * @param resultSet
		 * @throws SQLException
		 * @return MonitorStockVO
		 */
		public MonitorStockVO map(ResultSet resultSet) throws SQLException {
			MonitorStockVO monitorStockVO = new MonitorStockVO();
			monitorStockVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			monitorStockVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			monitorStockVO.setRequestsReceived(resultSet.getLong("REQ_REC"));
			monitorStockVO.setRequestsPlaced(resultSet.getLong("REQ_PLACE"));
			monitorStockVO.setAllocatedStock(resultSet.getLong("PHYALCSTK"));
			monitorStockVO.setAvailableStock(resultSet.getLong("PHYAVLSTK"));
			monitorStockVO.setStockHolderType(resultSet.getString
					("STKHLDTYP"));
			return monitorStockVO;
		}
	}

	/**
	 * This mapper is used for validate stock holder code and type
	 *
	 * @author A-1883
	 *
	 */
	private class ValidateMapper implements Mapper<String> {
		/**
		 * This method is used for validate stock holder code and type
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return String
		 */
		public String map(ResultSet resultSet) throws SQLException {
			return resultSet.getString("CMPCOD");
		}
	}

	/**
	 * This method will find the blacklisted stock matching the filter criteria
	 *
	 * @param stockFilterVO
	 * @param displayPage
	 * @return Page<RangeVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<BlacklistStockVO> findBlacklistedStock(
			StockFilterVO stockFilterVO, int displayPage)
			throws SystemException, PersistenceException {
		String baseQry = getQueryManager().getNamedNativeQueryString(
				STOCKCONTROL_DEFAULTS_FIND_BLACKLISTED_STOCK);
		//Added by A-5214 as part from the ICRD-20959 starts
		StringBuilder rankQuery = new StringBuilder().append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(baseQry);
		
		PageableNativeQuery<BlacklistStockVO> query = new FindBlacklistedStockFilterQuery(new BlacklistedStockMapper(),stockFilterVO,rankQuery.toString());
		log.log(Log.INFO, "Query is", query.toString());
		return query.getPage(displayPage);
		//Added by A-5214 as part from the ICRD-20959 ends
	}

	/**
	 * This mapper is used for findBlacklistedStock()
	 *
	 * @author A-1883
	 *
	 */
    private class BlacklistedStockMapper implements Mapper<BlacklistStockVO> {
		/**
		 * This method is used to map the result of query to BlacklistStockVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return BlacklistStockVO
		 */
		public BlacklistStockVO map(ResultSet resultSet) throws SQLException {
			BlacklistStockVO blacklistStockVO = new BlacklistStockVO();
			blacklistStockVO.setCompanyCode(resultSet.getString("CMPCOD"));
			blacklistStockVO.setDocumentType(resultSet.getString("DOCTYP"));
			blacklistStockVO.setDocumentSubType(resultSet
					.getString("DOCSUBTYP"));
			if(resultSet.getString("STARNG")!=null){
			blacklistStockVO.setRangeFrom(resultSet.getString("STARNG"));
			}else{
				blacklistStockVO.setRangeFrom(resultSet.getString("MSTDOCNUM"));	
			}
			if(resultSet.getString("STARNG")!=null){
			blacklistStockVO.setRangeTo(resultSet.getString("ENDRNG"));
			}else{
				blacklistStockVO.setRangeTo(resultSet.getString("MSTDOCNUM"));
			}
			blacklistStockVO.setRemarks(resultSet.getString("BLKLSTRMK"));
			//Added by A-3791 for ICRD-110209
			blacklistStockVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			blacklistStockVO.setFirstLevelStockHolder(resultSet.getString("FRTSTKHLDCOD"));
			blacklistStockVO.setSecondLevelStockHolder(resultSet.getString("SECLVLSTKHLDCOD"));   
			blacklistStockVO.setStatus(resultSet.getString("BLKSTA"));
			/*
			 * blacklistStockVO.setBlacklistDate(new LocalDate
			 * (resultSet.getDate("BLKLSTDAT"),false));
			 */
			if(resultSet.getDate("BLKLSTDAT")!=null){
			blacklistStockVO.setBlacklistDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet
					.getDate("BLKLSTDAT")));
			}
			blacklistStockVO.setLastUpdateTime(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,resultSet
							.getTimestamp("LSTUPDTIM")));
			blacklistStockVO.setLastUpdateUser(resultSet.getString
					("LSTUPDUSR"));
			//Added For ICRD-139677
			blacklistStockVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
			blacklistStockVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			blacklistStockVO.setAsciiRangeFrom(resultSet.getLong("ASCDOCNUM"));
			blacklistStockVO.setAsciiRangeTo(resultSet.getLong("ASCDOCNUM"));
			return blacklistStockVO;
		}
	}

	/**
	 * This method is used to find the stock holder codes for a collection of
	 * stock control privileges
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param privileges
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> findStockHolderCodes(String companyCode,
			Collection<String> privileges) throws SystemException,
			PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FIND_STOCKHOLDER_CODES);
		//query.append(getInWhereClause(privileges));
		query.setParameter(1, companyCode);
		return query.getResultList(new StockHolderCodeMapper());
	}

	/**
	 * This mapper is used for findStockHolderCodes()
	 *
	 * @author A-1883
	 *
	 */
	private class StockHolderCodeMapper implements Mapper<String> {
		/**
		 * This method is used to map the result of query to RangeVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return String
		 */
		public String map(ResultSet resultSet) throws SQLException {
			return resultSet.getString("STKHLDCOD");
		}
	}

	/**
	 * @author A-1883
	 * @param stockFilterVO
	 * @return Collection<RangeVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<RangeVO> findAllocatedRanges(StockFilterVO stockFilterVO)
			throws SystemException, PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FIND_ALLOCATED_RANGES);
		query.setParameter(1, stockFilterVO.getCompanyCode());
		query.setParameter(2, stockFilterVO.getStockHolderCode());
		query.setParameter(3, stockFilterVO.getDocumentType());
		query.setParameter(4, stockFilterVO.getDocumentSubType());
		String manualFlag=null;
		if (stockFilterVO.isManual()) {
			manualFlag = StockFilterVO.FLAG_YES;
		} else {
			manualFlag = StockFilterVO.FLAG_NO;	
		}
		query.setParameter(5, manualFlag);
		query.setParameter(6, stockFilterVO.getAirlineIdentifier());
		return query.getResultList(new FindAllocatedRangesMapper());
	}

	/**
	 * This mapper is used for findAllocatedRanges()
	 *
	 * @author A-1883
	 *
	 */
	private class FindAllocatedRangesMapper implements Mapper<RangeVO> {
		/**
		 * This method is used to map the result of query to RangeVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return RangeVO
		 */
		public RangeVO map(ResultSet resultSet) throws SQLException {
			RangeVO rangeVO = new RangeVO();
			rangeVO.setStartRange(resultSet.getString("STARNG"));
			rangeVO.setEndRange(resultSet.getString("ENDRNG"));
			rangeVO.setNumberOfDocuments(resultSet.getLong("DOCNUM"));
			//Added by A-3791 for ICRD-110168 Starts
			rangeVO.setStockHolderCode(resultSet.getString("TOSTKHLDCOD"));
			rangeVO.setStockAcceptanceDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getDate("ALTDAT")));
			//Added by A-3791 for ICRD-110168 Ends
			return rangeVO;
		}
	}

	/**
	 * This method is invoked to obtain the stock holder lov
	 *
	 * @param filterVO
	 * @param displayPage
	 * @return Page<StockHolderLovVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<StockHolderLovVO> findStockHolderLov(
			StockHolderLovFilterVO filterVO, int displayPage)
			throws SystemException, PersistenceException {
		log.entering("Stock SQl DAO", "findStockHolderLov");
		log.log(Log.FINE, "filterVO-->", filterVO);
		//modified the method by A-5103 for ICRD-32647 
        StringBuffer masterQuery = new StringBuffer();
        masterQuery.append(STOCKCONTROL_DEFAULTS_ROWNUM_RANK_QUERY);
        String queryString = getQueryManager().getNamedNativeQueryString(
				STOCKCONTROL_DEFAULTS_FIND_APPROVER_LOV);
        masterQuery.append(queryString);
        PageableNativeQuery<StockHolderLovVO> pgNativeQuery = 
    			new PageableNativeQuery<StockHolderLovVO>(0,
    					masterQuery.toString(),new Mapper<StockHolderLovVO>(){

					/**
					 * find the productLovVO
					 *
					 * @param resultSet
					 * @return ProductLovVO
					 * @throws SQLException
					 */
					public StockHolderLovVO map(ResultSet resultSet)
							throws SQLException {
						//log.entering("Stock SqlDAO", "map");
						StockHolderLovVO stockHolderLovVO = new StockHolderLovVO();
						stockHolderLovVO.setStockHolderCode(resultSet
								.getString("STKHLDCOD"));
						stockHolderLovVO.setStockHolderType(resultSet
								.getString("STKHLDTYP"));
						stockHolderLovVO.setStockHolderName(resultSet
								.getString("STKHLDNAM"));
						stockHolderLovVO.setDescription(resultSet
								.getString("STKHLDDES"));
						return stockHolderLovVO;
					}
				});
      
		int index = 0;
		String companyCode = filterVO.getCompanyCode();
		String documentType = filterVO.getDocumentType();
		String documentSubType = filterVO.getDocumentSubType();
		String stockHolderType = filterVO.getStockHolderType();
		String stockHolderCode = filterVO.getStockHolderCode();
		String stockHolderName = filterVO.getStockHolderName();
		String stockApproverCode = filterVO.getApproverCode();
		pgNativeQuery.setParameter(++index, companyCode);
		if (documentType != null && documentType.trim().length() != 0) {
			documentType = documentType.replace('*', '%');
			pgNativeQuery.append(" AND STOCK.DOCTYP = ? ");
			pgNativeQuery.setParameter(++index, documentType);
		}
		if (documentSubType != null && documentSubType.trim().length() != 0) {
			documentSubType = documentSubType.replace('*', '%');
			pgNativeQuery.append(" AND STOCK.DOCSUBTYP = ? ");
			pgNativeQuery.setParameter(++index, documentSubType);
		}
		if (!filterVO.isRequestedBy()) {
			if (stockHolderType != null && stockHolderType.trim().length() != 0) {
				stockHolderType = stockHolderType.replace('*', '%');
				pgNativeQuery.append(" AND  STOCKHOLDER.STKHLDTYP = ? ");
				pgNativeQuery.setParameter(++index, stockHolderType);
			}
		}
		if (stockHolderCode != null && stockHolderCode.trim().length() != 0) {
			stockHolderCode = stockHolderCode.replace('*', '%');
			pgNativeQuery.append(" AND STOCKHOLDER.STKHLDCOD LIKE ?");
			pgNativeQuery.setParameter(++index, stockHolderCode);
		}
		if (stockHolderName != null && stockHolderName.trim().length() != 0) {
			stockHolderName = stockHolderName.replace('*', '%');
			pgNativeQuery.append(" AND upper(STOCKHOLDER.STKHLDNAM) LIKE ?");
			pgNativeQuery.setParameter(++index, stockHolderName.toUpperCase());
		}
		if (stockApproverCode != null && stockApproverCode.trim().length() != 0) {
			stockApproverCode = stockApproverCode.replace('*', '%');
			pgNativeQuery.append(" AND STOCK.STKAPRCOD = ? ");
			pgNativeQuery.append(" AND  EXISTS (SELECT STKHLDCOD FROM stkhldmst ");
			pgNativeQuery.append(" WHERE STKHLDCOD = ?	 AND  STKHLDTYP = ? ) ");
			pgNativeQuery.setParameter(++index, stockApproverCode);
			pgNativeQuery.setParameter(++index, stockApproverCode);
			pgNativeQuery.setParameter(++index, stockHolderType);
		}
		
		pgNativeQuery.append(STOCKCONTROL_DEFAULTS_SUFFIX_QUERY);
		log.log(Log.FINE, " Calling query ");
		
		return pgNativeQuery.getPage(displayPage);

	}

	/**
	 * This method is used to find the stock requests matching the specified
	 * filter criteria
	 * @param stockRequestFilterVO
	 * @param displayPage
	 * @return Page<StockRequestVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	/* changed by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-20959 and ScreenId: STK016
	 */
	public Page<StockRequestVO> findStockRequests(
			StockRequestFilterVO stockRequestFilterVO, int displayPage)
			throws SystemException, PersistenceException {
		String baseQry = getQueryManager().getNamedNativeQueryString(STOCKCONTROL_DEFAULTS_FIND_STOCKREQUESTS);
		//Query query = new StockRequestFilterQuery(stockRequestFilterVO, baseQry);
		PageableNativeQuery<StockRequestVO> pageableNativeQuery = null;
		if(stockRequestFilterVO.getPageSize() > 0){
			pageableNativeQuery = new StockRequestFilterQuery(stockRequestFilterVO.getPageSize(),new StockRequestMapper(),stockRequestFilterVO, baseQry,isOracleDataSource());
		}else{
			pageableNativeQuery = new StockRequestFilterQuery(new StockRequestMapper(),stockRequestFilterVO, baseQry,isOracleDataSource());
		}
		log.log(Log.INFO, "Query is", pageableNativeQuery.toString());
		return pageableNativeQuery.getPage(displayPage);
	}

	/**
	 * @author A-1883 This method is used to find the details of a particular
	 *         stock request
	 * @param stockRequestFilterVO
	 * @return StockRequestVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public StockRequestVO findStockRequestDetails(
			StockRequestFilterVO stockRequestFilterVO) throws SystemException,
			PersistenceException {
		StockRequestVO stockRequestVO = new StockRequestVO();
		return getStockRequestDetails(stockRequestFilterVO);
	}

	/**
	 * This method will fetch the stock requests for approval Stock requests
	 * having status other than Completed, rejected are retrieved
	 *
	 * @param stockRequestFilterVO
	 * @return Collection<StockRequestVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<StockRequestVO> findStockRequestsForApproval(
			StockRequestFilterVO stockRequestFilterVO) throws SystemException,
			PersistenceException {
		return null;
	}

	/**
	 * This method is used to fetch the stockholder types sorted based on
	 * prioity
	 *
	 * @param companyCode
	 * @return Collection<StockHolderPriorityVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<StockHolderPriorityVO> findStockHolderTypes(
			String companyCode) throws SystemException, PersistenceException {
		Query qry = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FIND_STOCKHOLDER_TYPES);
		qry.setParameter(1, companyCode);
		return qry.getResultList(new StockHolderTypesMapper());

	}

	private StockHolderVO findStockHolderSqlDetails(String companyCode,
			String stockHolderCode) throws SystemException,
			PersistenceException {
		List<StockHolderVO> stockHolderList = new ArrayList<StockHolderVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FIND_STOCKHOLDER_DETAILS);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, stockHolderCode);
		stockHolderList = qry.getResultList(new StockControlDetailsMapper());
		if(stockHolderList != null && stockHolderList.size() > 0) {
			return stockHolderList.get(0);
		}
		return null;
	}

	private Collection<StockVO> findStockDetails(String companyCode,
			String stockHolderCode) throws SystemException {
		//List<StockVO> stockList = new ArrayList<StockVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FIND_STOCK_DETAILS);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, stockHolderCode);
		return qry.getResultList(new StockDetailsMapper());
	}

	/**
	 *
	 * @author A-1885
	 *
	 */
	private class StockControlDetailsMapper implements Mapper<StockHolderVO> {
		/**
		 * Method for mapping StockHolder
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockHolderVO
		 */
		public StockHolderVO map(ResultSet resultSet) throws SQLException {
			StockHolderVO stockHolderVO = new StockHolderVO();
			stockHolderVO.setCompanyCode(resultSet.getString("CMPCOD"));
			stockHolderVO.setControlPrivilege(resultSet.getString("CTLPVG"));
			stockHolderVO.setDescription(resultSet.getString("STKHLDDES"));
			stockHolderVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			stockHolderVO.setStockHolderType(resultSet.getString("STKHLDTYP"));
			stockHolderVO.setStockHolderName(resultSet.getString("STKHLDNAM"));
			stockHolderVO.setStockHolderContactDetails(resultSet
					.getString("CNTADR"));
			stockHolderVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
			stockHolderVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet
					.getTimestamp("LSTUPDTIM")));
			return stockHolderVO;
		}
	}

	/**
	 * @author A-1885
	 */
	private class StockDetailsMapper implements Mapper<StockVO> {
		/**
		 * Method for mapping StockHolder
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockHolderVO
		 */
		public StockVO map(ResultSet resultSet) throws SQLException {
			StockVO stockVO = new StockVO();
			stockVO.setCompanyCode(resultSet.getString("CMPCOD"));
			stockVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			if (StockVO.FLAG_YES.equals(resultSet.getString("AUTREQFLG"))) {
				stockVO.setAutoRequestFlag(true);
			} else {
				stockVO.setAutoRequestFlag(false);
			}
			if (StockVO.FLAG_YES.equals(resultSet.getString("ORDALT"))) {
				stockVO.setReorderAlertFlag(true);
			} else {
				stockVO.setReorderAlertFlag(false);
			}
			if (StockVO.FLAG_YES.equals(resultSet.getString("AUTGENFLG"))) {
				stockVO.setAutoPopulateFlag(true);
			} else {
				stockVO.setAutoPopulateFlag(false); 
			}
			stockVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			stockVO.setDocumentType(resultSet.getString("DOCTYP"));
			stockVO.setRemarks(resultSet.getString("STKRMK"));
			stockVO.setReorderLevel(resultSet.getInt("ORDLVL"));
			stockVO.setReorderQuantity(resultSet.getInt("ORDQTY"));
			stockVO.setStockApproverCode(resultSet.getString("STKAPRCOD"));
			stockVO
					.setStockApproverCompany(resultSet
							.getString("STKAPRCMPCOD"));
			stockVO.setAutoprocessQuantity(resultSet.getInt("ATOPRCQTY"));
			stockVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
			return stockVO;
		}
	}

	/**
	 * @author A-1885
	 */
	private class StockHolderTypesMapper implements
			Mapper<StockHolderPriorityVO> {
		/**
		 * Method for mapping StockHolder
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockHolderVO
		 */
		public StockHolderPriorityVO map(ResultSet resultSet)
				throws SQLException {
			StockHolderPriorityVO stockHolderPriorityVO = new StockHolderPriorityVO();
			stockHolderPriorityVO.setCompanyCode(resultSet.getString("CMPCOD"));
			stockHolderPriorityVO.setStockHolderType(resultSet
					.getString("STKHLDTYP"));
			stockHolderPriorityVO.setPriority(resultSet.getLong("STKHLDPRY"));
			return stockHolderPriorityVO;
		}
	}

	/**
	 * 
	 * @param stockRequestFilterVO
	 * @return StockRequestVO
	 * @throws SystemException
	 */
	private StockRequestVO getStockRequestDetails(StockRequestFilterVO stockRequestFilterVO) throws SystemException {
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FIND_STOCKREQUEST_DETAILS);
		int index = 0;
		query.setParameter(++index, stockRequestFilterVO.getCompanyCode());
		query.setParameter(++index, stockRequestFilterVO.getRequestRefNumber());
		// Added by A-5153 for CRQ_ICRD-107872
		if (PRVLG_RUL_STK_HLDR.equals(stockRequestFilterVO.getPrivilegeRule())
				&& PRVLG_LVL_STKHLD.equals(stockRequestFilterVO
						.getPrivilegeLevelType())
				&& stockRequestFilterVO.getPrivilegeLevelValue() != null
				&& stockRequestFilterVO.getPrivilegeLevelValue().trim()
						.length() > 0) {
			String[] levelValues = stockRequestFilterVO
					.getPrivilegeLevelValue().split(",");
			query.append(" AND (stkhldstk.STKHLDCOD IN (");
			boolean isFirst = true;
			for (String val : levelValues) {
				if (!isFirst) {
					query.append(", ");
				}
				query.append("?");
				query.setParameter(++index, val);
				isFirst = false;
			}
			query.append(") OR stkhldstk.STKAPRCOD IN (");
			isFirst = true;
			for (String val : levelValues) {
				if (!isFirst) {
					query.append(", ");
				}
				query.append("?");
				query.setParameter(++index, val);
				isFirst = false;
			}
			query.append("))");

		}
		
		List<StockRequestVO> list = query
				.getResultList(new StockRequestDetailsMapper());
		if(list != null && list.size() > 0){
			log.log(Log.FINE, "***StockRequestVO***", list.get(0));
			return list.get(0);
		}else{
			return new StockRequestVO();
		}
		

	}

	/**
	 * @author A-1883 Mapper used for findStockRequestDetails()
	 *
	 */
	private class StockRequestDetailsMapper implements Mapper<StockRequestVO> {
		/**
		 * This method is used to map the result of query to StockRequestVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockRequestVO
		 */
		public StockRequestVO map(ResultSet resultSet) throws SQLException {
			StockRequestVO stockRequestVO = new StockRequestVO();
			stockRequestVO.setCompanyCode(resultSet.getString("CMPCOD"));
			stockRequestVO
					.setRequestRefNumber(resultSet.getString("REQREFNUM"));
			stockRequestVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			stockRequestVO.setRequestDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet
					.getDate("REQDAT")));
			stockRequestVO.setDocumentType(resultSet.getString("DOCTYP"));
			stockRequestVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			if (StockRequestVO.FLAG_YES
					.equals(resultSet.getString("MNLSTKFLG"))) {
				stockRequestVO.setManual(true);
			} else {
				stockRequestVO.setManual(false);
			}
			stockRequestVO.setRequestedStock(resultSet.getLong("REQSTKQTY"));
			stockRequestVO.setApprovedStock(resultSet.getLong("APRSTKQTY"));
			stockRequestVO.setAllocatedStock(resultSet.getLong("ALCSTKQTY"));
			stockRequestVO.setRemarks(resultSet.getString("REQRMK"));
			stockRequestVO.setApprovalRemarks(resultSet.getString("APRREJREM"));
			stockRequestVO.setStatus(resultSet.getString("REQSTA"));
			stockRequestVO.setLastUpdateDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet
					.getTimestamp("LSTUPDDAT")));
			stockRequestVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
			stockRequestVO.setStockHolderType(resultSet.getString("STKHLDTYP"));
			return stockRequestVO;
		}
	}

	/**
	 * @author A-1883 Mapper class for List stock requests
	 */
	class StockRequestMapper implements Mapper<StockRequestVO> {
		/**
		 * This method is used to map the result of query to StockRequestVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockRequestVO
		 */
		public StockRequestVO map(ResultSet resultSet) throws SQLException {
			StockRequestVO stockRequestVO = new StockRequestVO();
			stockRequestVO.setCompanyCode(resultSet.getString("CMPCOD"));
			stockRequestVO
					.setRequestRefNumber(resultSet.getString("REQREFNUM"));
			stockRequestVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			stockRequestVO.setRequestDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet
					.getDate("REQDAT")));
			stockRequestVO.setDocumentType(resultSet.getString("DOCTYP"));
			stockRequestVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			stockRequestVO.setRequestedStock(resultSet.getLong("REQSTKQTY"));
			stockRequestVO.setStatus(resultSet.getString("REQSTA"));
			stockRequestVO.setAllocatedStock(resultSet.getLong("ALCSTKQTY"));
			stockRequestVO.setApprovedStock(resultSet.getLong("APRSTKQTY"));
			stockRequestVO.setPersistedApprovedStock(resultSet
					.getLong("APRSTKQTY"));
			if (StockRequestVO.FLAG_YES
					.equals(resultSet.getString("MNLSTKFLG"))) {
				stockRequestVO.setManual(true);
			} else {
				stockRequestVO.setManual(false);
			}
			stockRequestVO.setLastUpdateDate(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,
							resultSet.getTimestamp("LSTUPDDAT")));
			stockRequestVO.setLastStockHolderUpdateTime(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,
							resultSet.getTimestamp("LSTUPDTIM")));
			stockRequestVO.setAirlineIdentifier(resultSet.getString("ARLIDR"));
            stockRequestVO.setStockHolderType(resultSet.getString("STKHLDTYP"));
			stockRequestVO.setStockHolderType(resultSet.getString("STKHLDTYP"));
			// added by A-7740 as a part of ICRD-225106
			stockRequestVO.setRemarks(resultSet.getString("REQRMK"));
			return stockRequestVO;
		}
	}

	/**
	 * @author A-1883 Mapper class for Validate StockHolder Type and Code
	 */
	class ValidateStockHolderTypeCodeMapper implements Mapper<String> {
		/**
		 * This method is used to map the result of query
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return String
		 */
		public String map(ResultSet resultSet) throws SQLException {
			return resultSet.getString("STKHLDNAM");
		}
	}

	/**
	 *
	 * @param whereValues
	 * @return String
	 */
	private String getInWhereClause(Collection<String> whereValues) {
		String clause = null;
		if ((whereValues != null) && (whereValues.size() > 0)) {
			StringBuilder sbul = new StringBuilder().append(" IN ( ");
			ArrayList<String> values = new ArrayList<String>(whereValues);
			String first = values.get(0);
			sbul.append("'").append(first).append("'");
			values.remove(0);
			for (String value : values) {
				sbul.append(",'").append(value).append("'");
			}
			sbul.append(")").toString();
			clause = sbul.toString();
		}
		return clause;
	}

	/**
	 * @author A-1885
	 */
	private class ValidateStockHolderMapper implements Mapper<String> {
		/**
		 * Method for mapping StockHolder
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockHolderVO
		 */
		public String map(ResultSet resultSet) throws SQLException {
			return resultSet.getString("STKHLDCOD");
		}
	}

	/**
	 * Method for check duplicate ranges
	 *
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	public Collection<RangeVO> checkDuplicateRange(String companyCode,
			int airlineIdentifier, String docType, String docSubType,
			String startRange, String endRange) throws SystemException {

		log.log(Log.FINE, "companyCode---", companyCode);
		log.log(Log.FINE, "airlineIdentifier---", airlineIdentifier);
		log.log(Log.FINE, "docType---", docType);
		log.log(Log.FINE, "docSubType---", docSubType);
		log.log(Log.FINE, "startRange---", startRange);
		log.log(Log.FINE, "endRange---", endRange);
		Query qry = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_CHECKDUPLICATERANGE);
		int index = 0;
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, airlineIdentifier);
		qry.setParameter(++index, docType);
		//qry.setParameter(++index, docSubType);
		// qry.setParameter(3,docSubType);
		//Edited as part of ICRD-46860
		qry.setParameter(++index, startRange);
		qry.setParameter(++index, endRange);
		qry.setParameter(++index, startRange);
		qry.setParameter(++index, endRange);

		// added by Sinoob starts
		qry.setParameter(++index,  startRange);
		qry.setParameter(++index,  endRange);
		// added by Sinoob ends
		//Added for ICRD-ICRD-28321 starts
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, airlineIdentifier);
		qry.setParameter(++index, docType);
		//qry.setParameter(++index, docSubType);
		qry.setParameter(++index,  startRange);
		qry.setParameter(++index,  endRange);
		qry.setParameter(++index,  startRange);
		qry.setParameter(++index,  endRange);
		qry.setParameter(++index,  startRange);
		qry.setParameter(++index,  endRange);
		//Added for ICRD-ICRD-28321 ends
		Collection<RangeVO> rangeVOs = qry.getResultList(new DuplicateCheckMapper());
		log.log(Log.FINE, "returning rangeVOs---", rangeVOs);
		return rangeVOs;

	}



	/**
	 * class for duplicate range mapping
	 *
	 * @author A-1885
	 *
	 */
	class DuplicateCheckMapper implements Mapper<RangeVO> {
		/**
		 * Method for mapping
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return RangeVO
		 */
		public RangeVO map(ResultSet resultSet) throws SQLException {
			RangeVO rangeVO = new RangeVO();
			rangeVO.setEndRange(resultSet.getString("ENDRNG"));
			rangeVO.setStartRange(resultSet.getString("STARNG"));
			return rangeVO;
		}
	}

	/**
	 * Method for finding all black listed stock
	 *
	 * @param companyCode
	 * @param airlineId
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	public Collection<StockVO> findBlacklistRanges(String companyCode,
			int airlineId, String docType, String docSubType,
			String startRange, String endRange) throws SystemException {
		Query qry = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FINDBLACKLISTRANGES);
		int index = 0;
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, airlineId);
		qry.setParameter(++index, docType);
		qry.setParameter(++index, docSubType);
		qry.setParameter(++index, findLong(startRange));
		qry.setParameter(++index, findLong(endRange));
		qry.setParameter(++index, findLong(startRange));
		qry.setParameter(++index, findLong(endRange));
		Collection<StockVO> stockVOs = qry.getResultList(new BlacklistMapper());
		log.log(Log.FINE, "---Raje--->", stockVOs);
		return stockVOs;
	}
	public Collection<StockVO> findBlacklistRangesForBlackList(String companyCode,
			int airlineId, String docType, String docSubType,
			String startRange, String endRange) throws SystemException {
		Query qry = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FINDBLACKLISTRANGES_FOR_BLACKLIST);
		int index = 0;
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, airlineId);
		qry.setParameter(++index, docType);
		qry.setParameter(++index, docSubType);
		qry.setParameter(++index, findLong(startRange));
		qry.setParameter(++index, findLong(endRange));
		qry.setParameter(++index, findLong(startRange));
		qry.setParameter(++index, findLong(endRange));
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, DocumentValidationVO.DOC_TYP_AWB.equals(docType)? ShipmentDetailVO.AWB_DOCUMENT_TYPE:docType);
		qry.setParameter(++index, docSubType);
		qry.setParameter(++index, findLong(startRange));
		qry.setParameter(++index, findLong(endRange));
		Collection<StockVO> stockVOs = qry.getResultList(new BlacklistMapper());
		log.log(Log.FINE, "---Raje--->", stockVOs);
		return stockVOs;

	}

	/**
	 * Class for blacklist mapping
	 *
	 * @author A-1885
	 *
	 */
	class BlacklistMapper implements Mapper<StockVO> {
		/**
		 * Method for mapping result set to stock vo
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockVO
		 */
		public StockVO map(ResultSet resultSet) throws SQLException {
			StockVO stockVO = new StockVO();
			stockVO.setCompanyCode(resultSet.getString("CMPCOD"));
			stockVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			Collection<RangeVO> ranges = new ArrayList<RangeVO>();
			RangeVO rangeVo = new RangeVO();
			rangeVo.setStartRange(resultSet.getString("STARNG"));
			rangeVo.setEndRange(resultSet.getString("ENDRNG"));
			rangeVo.setAsciiStartRange(resultSet.getLong("ASCSTARNG"));
			rangeVo.setAsciiEndRange(resultSet.getLong("ASCENDRNG"));
			//rangeVo.setManual(resultSet.getBoolean("MNLFLG"));
			//added for ICRD-254030
			if ("Y".equals(resultSet.getString("MNLFLG"))) {
				rangeVo.setManual(true);
			} else {
				rangeVo.setManual(false);
			}
			ranges.add(rangeVo);
			stockVO.setRanges(ranges);
			return stockVO;
		}
	}

	/**
	 * Method for finding all black listed stock for Revoking
	 *
	 * @param companyCode
	 * @param airlineId
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */

	public Collection<BlacklistStockVO> findBlackListRangesForRevoke(String companyCode,
			int airlineId, String docType, String docSubType,
			String startRange, String endRange) throws SystemException {
		Query qry = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FINDBLACKLISTRANGESFORREVOKE);
		int index=0;
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, airlineId);
		qry.setParameter(++index, docType);
		qry.setParameter(++index, docSubType);
		qry.setParameter(++index, findLong(startRange));
		qry.setParameter(++index, findLong(endRange));
		qry.setParameter(++index, findLong(startRange));
		qry.setParameter(++index, findLong(endRange));

		return qry.getResultList(new BlackListForRevokeMapper());
	}

	/**
	 * Class for blacklistForRevoke mapping
	 *
	 * @author A-2850
	 *
	 */
	class BlackListForRevokeMapper implements Mapper<BlacklistStockVO>{
		/**
		 * Method for mapping result set to stock vo
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockVO
		 */
		public BlacklistStockVO map(ResultSet resultSet) throws SQLException {
			BlacklistStockVO blacklistStockVO = new BlacklistStockVO();
			blacklistStockVO.setCompanyCode(resultSet.getString("CMPCOD"));
			blacklistStockVO.setDocumentType(resultSet.getString("DOCTYP"));
			blacklistStockVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			blacklistStockVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
			blacklistStockVO.setRangeFrom(resultSet.getString("STARNG"));
			blacklistStockVO.setRangeTo(resultSet.getString("ENDRNG"));
			blacklistStockVO.setAsciiRangeFrom(resultSet.getLong("ASCSTARNG"));
			blacklistStockVO.setAsciiRangeTo(resultSet.getLong("ASCENDRNG"));
			//Added by A-7373 for for ICRD-241944
			if ("Y".equalsIgnoreCase(resultSet.getString("MNLFLG"))){
				blacklistStockVO.setManual(true);
			}
			else{
				blacklistStockVO.setManual(false);
			}
			return blacklistStockVO;
		}
	}

	/**
	 * Method for checking blacklisted ranges
	 *
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	public String checkBlacklistRanges(String companyCode,
			int airlineIdentifier, String docType, String docSubType,
			long asciiStartRange, long asciiEndRange) throws SystemException {
		Query qry = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_CHECKBLACKLISTRANGE);
		int index = 0;
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, airlineIdentifier);
		qry.setParameter(++index, docType);
		//qry.setParameter(++index, docSubType);Commented by A-5639 for ICRD-231530
		qry.setParameter(++index, asciiStartRange);
		qry.setParameter(++index, asciiEndRange);
		qry.setParameter(++index, asciiStartRange);
		qry.setParameter(++index, asciiEndRange);
		qry.setParameter(++index, asciiStartRange);
		qry.setParameter(++index, asciiEndRange);
		return qry.getSingleResult(new CheckBlacklistMapper());

	}

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public List<String> checkStock(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws SystemException,
			PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_STOCK_EXISTS);
		query.setParameter(1, companyCode);
		query.setParameter(2, stockHolderCode);
		query.setParameter(3, docType);
		query.setParameter(4, docSubType);
		return query.getResultList(new StockExistsMapper());
	}

	/**
	 * This mapper is used for checkStock()
	 *
	 * @author A-1883
	 */
	private class StockExistsMapper implements Mapper<String> {
		/**
		 * This method is used to map the result of query to String
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return String
		 */
		public String map(ResultSet resultSet) throws SQLException {
			return resultSet.getString("STKAPRCOD");
		}
	}

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findApproverCode(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws SystemException,
			PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FIND_APPROVERCODE);
		query.setParameter(1, companyCode);
		query.setParameter(2, stockHolderCode);
		query.setParameter(3, docType);
		query.setParameter(4, docSubType);
		List<String> approverCode = query.getResultList(new ApproverMapper());
		if(approverCode!=null && approverCode.size() > 0) {
			return approverCode.get(0);
		} else {
			return null;
		}

	}

	/**
	 * This mapper is used for findApproverCode()
	 *
	 * @author A-1883
	 */
	private class ApproverMapper implements Mapper<String> {
		/**
		 * This method is used to map the result of query to String
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return String
		 */
		public String map(ResultSet resultSet) throws SQLException {
			return resultSet.getString("STKAPRCOD");
		}
	}

	/**
	 * @param companyCode
	 * @param stockHolderCodes
	 * @return Collection<StockHolderPriorityVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<StockHolderPriorityVO> findPriorities(String companyCode,
			Collection<String> stockHolderCodes) throws SystemException,
			PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FIND_PRIORITIES);
		query.append(getInWhereClause(stockHolderCodes));
		query.setParameter(1, companyCode);
		return query.getResultList(new PrioritiesMapper());
	}

	/**
	 * This mapper is used for findPriorities()
	 *
	 * @author A-1883
	 *
	 */
	private class PrioritiesMapper implements Mapper<StockHolderPriorityVO> {
		/**
		 * This method is used to map the result of query to
		 * StockHolderPriorityVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockHolderPriorityVO
		 */
		public StockHolderPriorityVO map(ResultSet resultSet)
				throws SQLException {
			StockHolderPriorityVO stockHolderPriorityVO = new StockHolderPriorityVO();
			stockHolderPriorityVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			stockHolderPriorityVO.setStockHolderType(resultSet.getString("STKHLDTYP"));
			stockHolderPriorityVO.setPriority(resultSet.getLong("STKHLDPRY"));
			return stockHolderPriorityVO;
		}
	}

	/**
	 * @param letter
	 * @return long
	 */
	private long calculateBase(char letter) {
		long longValue = letter;
		long base = 0;
		try {
			base = Integer.parseInt("" + letter);
		} catch (NumberFormatException numberFormatException) {
			base = longValue - 65;
		}
		return base;
	}

	/**
	 * To get the numeric value of the string
	 *
	 * @param range
	 * @return Numeric value
	 */
	private long findLong(String range) {
		log.log(Log.FINE, "--------Entering ascii convertion----->>>>");
		char[] sArray = range.trim().toCharArray();
		long base = 1;
		long sNumber = 0;
		for (int i = sArray.length - 1; i >= 0; i--) {
			sNumber += base * calculateBase(sArray[i]);
			int value = sArray[i];
			if (value > 57) {
				base *= 26;
			} else {
				base *= 10;
			}
		}
		return sNumber;
	}

	/**
	 * To find the difference between range from and range to value
	 *
	 * @param rangeFrom
	 *            Range from value
	 * @param rangeTo
	 *            Range to value
	 * @return Difference between the ranges
	 */
	private int difference(String rangeFrom, String rangeTo) {

		long diff = findLong(rangeTo) - findLong(rangeFrom);
		diff++;
		return (int) diff;
	}

	/**
	 * Class for blacklist stock checking
	 *
	 * @author A-1885
	 *
	 */
	class CheckBlacklistMapper implements Mapper<String> {
		/**
		 * Method for mapping result set to string
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return String
		 */
		public String map(ResultSet resultSet) throws SQLException {
			return resultSet.getString("CMPCOD");
		}
	}

	/**
	 * @param companyCode
	 * @param docType
	 * @param docSubType
	 * @param incomeRange
	 * @throws SystemException
	 * @throws PersistenceException
	 * @return RangeVO
	 */
	public RangeVO findRangeDelete(String companyCode, String docType,
			String docSubType, String incomeRange) throws SystemException,
			PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FINDRANGEDELETE);
		query.setParameter(1, companyCode);
		query.setParameter(2, docType);
		query.setParameter(3, findLong(incomeRange));
		query.setParameter(4, findLong(incomeRange));
		if (docSubType != null && docSubType.trim().length()>0) {
			docSubType = docSubType.replace('*', '%');
			query.append(" AND DOCSUBTYP = ? ");
			query.setParameter(5, docSubType);
		}
		return query.getSingleResult(new FindDeleteRanges());
	}

	/**
	 * Class for finding deleting ranges
	 *
	 * @author A-1885
	 *
	 */
	class FindDeleteRanges implements Mapper<RangeVO> {
		/**
		 * Method for mapping result set to RangeVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return RangeVO
		 */
		public RangeVO map(ResultSet resultSet) throws SQLException {
			RangeVO rangeVO = new RangeVO();
			rangeVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			rangeVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			if ("Y".equals(resultSet.getString("MNLFLG"))) {
				rangeVO.setManual(true);
			} else {
				rangeVO.setManual(false);
			}
			return rangeVO;
		}
	}

	/**
	 * @author A-1883
	 * @param companyCode
	 * @param docType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkForBlacklistedDocument(String key,
			String docType, String documentNumber) throws SystemException,
			PersistenceException {
		log.entering("SqlDAO", "checkForBlacklistedDocument()");
		log.log(Log.FINE, " docType :", docType);
		log.log(Log.FINE, " DocumentNumebr :", documentNumber);
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_CHECK_FOR_BLACKLISTED_DOCUMENT);
		String docNumber = null;
		if(documentNumber!=null && documentNumber.trim().length()>=7){
			docNumber = documentNumber.substring(0,7);
		}else{
			docNumber = documentNumber;
		}
		String companyCode="";
		String airlineIdentifier="";   
		if(key!=null && key.contains("#")){  	
			String[] tokens=key.split("#");
			companyCode=tokens[0];  
			airlineIdentifier=tokens[1];       
		}else{      
			companyCode = key;          
		} 
		int index=0;
		log.log(Log.FINE, "------THE RANGE----:-", docNumber);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, docType);
		query.setParameter(++index, String.valueOf(findLong(docNumber)));
		if(airlineIdentifier!=null && airlineIdentifier.trim().length()>0){
			query.append(" AND ARLIDR = TO_NUMBER(?) ");
			query.setParameter(++index, airlineIdentifier);
		}
		log.log(Log.FINE, "--QUERY--:---", query.toString());
		String doc = query
				.getSingleResult(getStringMapper("CMPCOD"));
		log.log(Log.FINE, "--Result--:", doc);
		log.log(Log.FINE, "--QUERY--:---", query.toString());
		log.exiting("SqlDAO", "checkForBlacklistedDocument()");
		// true indicates that documentNumber is blacklisted
		return (doc == null ? false : true);
	}

	/**
	 * Class for checkForBlacklistedDocument()
	 *
	 * @author A-1883
	 *
	 */
	class CheckForBlacklistedDocumentMapper implements Mapper<String> {
		/**
		 * @param resultSet
		 * @throws SQLException
		 * @return String
		 */
		public String map(ResultSet resultSet) throws SQLException {
			log.entering("SqlDAO", "map()");
			return resultSet.getString("CMPCOD");
		}
	}

	/**
	 * @author A-1883
	 * @param blacklistStockVO
	 * @return boolean
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean alreadyBlackListed(BlacklistStockVO blacklistStockVO)
			throws SystemException, PersistenceException {
		log.entering("StockControl Sql DAO", "alreadyBlackListed");
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_IS_ALREADY_BLACKLISTED);
		query.setParameter(1, blacklistStockVO.getCompanyCode());
		query.setParameter(2, blacklistStockVO.getDocumentType());
		query.setParameter(3, blacklistStockVO.getAirlineIdentifier());
		// query.setParameter(3,blacklistStockVO.getDocumentSubType());
		query.setParameter(4, blacklistStockVO.getRangeFrom());
		query.setParameter(5, blacklistStockVO.getRangeTo());
		query.setParameter(6, blacklistStockVO.getRangeFrom());
		query.setParameter(7, blacklistStockVO.getRangeTo());
		String companyCode = query
				.getSingleResult(new CheckForBlacklistedDocumentMapper());
		log.exiting("StockControl Sql DAO", "alreadyBlackListed");
		return (companyCode == null ? false : true);
	}

	// *************************************************************************
	// ****************** R2 coding starts ***************************
	// *************************************************************************

	/*
	 * Added by Sinoob
	 */
	/**
	 * @param stockFilterVO
	 * @return StockAllocationVO
	 * @throws SystemException
	 */
	public StockAllocationVO findStockForAirline(StockFilterVO stockFilterVO)
			throws SystemException {
		log.log(Log.INFO, "ENTRY");

		log.log(Log.INFO, "--**--stockFilterVO", stockFilterVO);
		int index = 0;
		List<StockAllocationVO> stockAllocations = null;
		StockAllocationVO stockAllocationVO = null;
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FINDSTOCKFORAIRLINE);
		query.setParameter(++index, stockFilterVO.getCompanyCode());
		query.setParameter(++index, stockFilterVO.getAirlineIdentifier());
		if (stockFilterVO.getDocumentType() != null
				&& stockFilterVO.getDocumentType().trim().length() > 0) {
			query.append(" AND STK.DOCTYP = ? ");
			query.setParameter(++index, stockFilterVO.getDocumentType());
		}

		if (stockFilterVO.getDocumentSubType() != null
				&& stockFilterVO.getDocumentSubType().trim().length() > 0) {
			query.append(" AND STK.DOCSUBTYP = ? ");
			query.setParameter(++index, stockFilterVO.getDocumentSubType());
		}

		if (stockFilterVO.getStockHolderCode() != null
				&& stockFilterVO.getStockHolderCode().trim().length() > 0) {
			query.append(" AND STK.STKHLDCOD = ? ");
			query.setParameter(++index, stockFilterVO.getStockHolderCode());
		}
		query.append(" ORDER BY RNG.RNGACPDAT, RNG.ASCSTARNG ");
		stockAllocations = query
				.getResultList(new StockForAirlineMultiMapper());
		log.log(Log.FINER, "Values in Collection : ", stockAllocations);
		if (stockAllocations != null && stockAllocations.size() > 0) {
			stockAllocationVO = stockAllocations.get(0);
			log.log(Log.FINER, "stockAllocationVO: ", stockAllocationVO);
		}
		log.log(Log.INFO, "RETURN");
		return stockAllocationVO;
	}

	// Added by Sinoob ends

	/**
	 *
	 * @param stockFilterVO
	 * @return Page<StockVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<StockVO> findStockList(StockFilterVO stockFilterVO)
			throws SystemException, PersistenceException {
		log.entering("StockControl SqlDAO", "findStockList");
		StringBuilder rankQuery=new StringBuilder();

		String nativeQuery = getQueryManager().getNamedNativeQueryString(STOCKCONTROL_DEFAULTS_FINDSTOCKLIST);
		if(stockFilterVO.getAirportCode()!=null &&
                stockFilterVO.getAirportCode().length()>0){
		    String dynamicFilter = " AND ARL.ARPCOD = '"+stockFilterVO.getAirportCode()+"' ";
            nativeQuery = String.format(nativeQuery,dynamicFilter);
        } else {
			nativeQuery = String.format(nativeQuery,"");
		}

		Query query = getQueryManager().createNativeQuery(nativeQuery);
		rankQuery.append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(query);
		PageableNativeQuery<StockVO> pgqry = new PageableNativeQuery<StockVO>(stockFilterVO.getTotalRecords(),rankQuery.toString(),new StockListingMapper());
		
		
		int index = 0;
		pgqry.setParameter(++index, stockFilterVO.getCompanyCode());
		if (stockFilterVO.getDocumentType() != null) {
			pgqry.append(" AND STK.DOCTYP = ?");
			pgqry.setParameter(++index, stockFilterVO.getDocumentType());
		}
		if (stockFilterVO.getDocumentSubType() != null) {
			pgqry.append(" AND STK.DOCSUBTYP = ?");
			pgqry.setParameter(++index, stockFilterVO.getDocumentSubType());
		}

		if(stockFilterVO.getStockHolderCode()!=null &&
				stockFilterVO.getStockHolderCode().length() > 0){
			pgqry.append(" AND STK.STKHLDCOD = ?");
			pgqry.setParameter(++index, stockFilterVO.getStockHolderCode());
		}
		if (stockFilterVO.getAirlineIdentifier() > 0) {
			pgqry.append(" AND STK.ARLIDR = ?");
			pgqry.setParameter(++index, stockFilterVO.getAirlineIdentifier());
		}
		pgqry.append(" GROUP BY STK.ARLIDR,RNG.DOCTYP, RNG.DOCSUBTYP, ARL.PARVAL");
		pgqry.append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_SUFFIX_QUERY);
		
		return pgqry.getPage(stockFilterVO.getPageNumber());
	}

	/**
	 * @param stockFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<StockRequestForOALVO> findStockRequestForOAL(
			StockFilterVO stockFilterVO) throws SystemException,
			PersistenceException {
		log.entering("StockControl SqlDAO", "findStockRequestForOAL");
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FINDSTOCKRQSTFOROAL);
		query.setParameter(1, stockFilterVO.getCompanyCode());
		query.setParameter(2, stockFilterVO.getAirlineIdentifier());
		query.setParameter(3, stockFilterVO.getDocumentType());
		query.setParameter(4, stockFilterVO.getDocumentSubType());
		return query.getResultList(new StockRequestForOALMapper());
	}

	/**
	 *
	 * @param reservationFilterVO
	 * @return Collection<ReservationVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ReservationVO> findReservationListing(
			ReservationFilterVO reservationFilterVO) throws SystemException,
			PersistenceException {
		log.entering("StockControl SqlDAO", "findReservationListing");
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FINDRESERVATIONLISTING);
		int index = 0;
		query.setParameter(++index, reservationFilterVO.getCompanyCode());

		if (reservationFilterVO.getAirlineIdentifier() != 0) {
			query.append(" AND AWB.ARLIDR = ?");
			query.setParameter(++index, reservationFilterVO
					.getAirlineIdentifier());
		}
		if (reservationFilterVO.getAirportCode() != null) {
			query.append(" AND AWB.ARPCOD = ?");
			query.setParameter(++index, reservationFilterVO.getAirportCode());
		}
		if (reservationFilterVO.getCustomerCode() != null) {
			query.append(" AND AWB.CUSCOD = ?");
			query.setParameter(++index, reservationFilterVO.getCustomerCode());
		}
		if (reservationFilterVO.getDocumentType() != null) {
			query.append(" AND AWB.DOCTYP = ?");
			query.setParameter(++index, reservationFilterVO.getDocumentType());
		}
		if (reservationFilterVO.getExpiryFromDate() != null) {
			query.append(" AND AWB.EXPDAT >= ?");
			query
					.setParameter(++index, reservationFilterVO
							.getExpiryFromDate());
		}
		if (reservationFilterVO.getExpiryToDate() != null) {
			query.append(" AND AWB.EXPDAT <= ?");
			query.setParameter(++index, reservationFilterVO.getExpiryToDate());
		}
		if (reservationFilterVO.getReservationFromDate() != null) {
			query.append(" AND AWB.RESDAT >= ?");
			query.setParameter(++index, reservationFilterVO
					.getReservationFromDate());
		}
		if (reservationFilterVO.getReservationToDate() != null) {
			query.append(" AND AWB.RESDAT <= ?");
			query.setParameter(++index, reservationFilterVO
					.getReservationToDate());
		}

		return query.getResultList(new ReservationListingMultiMapper());
	}

	/**
	 * @param reservationFilterVO
	 * @param expiryPeriod
	 * @return
	 * @throws SystemException
	 */
	public Collection<ReservationVO> findExpiredReserveAwbs(ReservationFilterVO reservationFilterVO, int expiryPeriod) throws SystemException{
		log.log(Log.INFO,"ENTRY");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDEXPIREDRESERVEAWBS);
		query.setParameter(++index, reservationFilterVO.getCompanyCode());
		query.setParameter(++index, reservationFilterVO.getAirportCode());
		query.setParameter(++index, expiryPeriod);
		query.setParameter(++index, reservationFilterVO.getCurrentDate());

		Collection<ReservationVO> reservationVOs = query.getResultList(new ExpiredAWBMapper());

		log.log(Log.INFO, "RETURN\n\n\nReservationsVOs : ", reservationVOs);
		return reservationVOs;

	}

	/*
	 * added by Sinoob
	 */
	/**
     * This method automatically commits the database before retrieving records
     * from it...
	 *
	 * @param stockFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public StockVO findStockForStockHolder(StockFilterVO stockFilterVO) throws SystemException, PersistenceException{
		log.log(Log.INFO,"ENTRY");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDSTOCKFORSTOCKHOLDER);
		query.setParameter(++index, stockFilterVO.getCompanyCode());
		query.setParameter(++index, stockFilterVO.getAirportCode());
		query.setParameter(++index, stockFilterVO.getAirlineIdentifier());
		query.setParameter(++index, stockFilterVO.getStockHolderCode());
		query.setParameter(++index, stockFilterVO.getDocumentType());
		query.setParameter(++index, stockFilterVO.getDocumentSubType());

		// for committing the database, because this method itself is called
		// before committing by the framework after depleting the stock
		query.setSensitivity(true);
		StockVO stockVO = query.getSingleResult(new StockListingMapper());
		log.log(Log.INFO,"RETURN");
		return stockVO;
	}
	// added by Sinoob ends

	/**
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param documentNumber
	 * @throws SystemException
	 * @throws PersistenceException
	 * @return StockRequestVO
	 */
	public StockRequestVO findDocumentDetails(String companyCode, int airlineIdentifier,
			String documentNumber) throws SystemException, PersistenceException{

		log.entering("StockControl SqlDAO", "findDocumentDetails");
		int index = 0;
		String rangeNo = documentNumber.substring(STRING_START, SUBSTRING_COUNT);
		long docNumber = findLong(rangeNo);
		Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDDOCUMENTDETAILS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, airlineIdentifier);
		query.setParameter(++index, docNumber);
		query.setParameter(++index, docNumber);

		return query.getSingleResult(new DocumentDetailsMapper());
	}

	/**
	 * @author A-1927
	 * Mapper class for getting document details
	 */
	class DocumentDetailsMapper implements Mapper<StockRequestVO> {
		/**
		 * This method is used to map the result of query to StockRequestVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockRequestVO
		 */
		public StockRequestVO map(ResultSet resultSet) throws SQLException {
			StockRequestVO stockRequestVO = new StockRequestVO();
			stockRequestVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			stockRequestVO.setStockHolderName(resultSet.getString("STKHLDNAM"));
			stockRequestVO.setStockHolderType(resultSet.getString("STKHLDTYP"));
			stockRequestVO.setDocumentType(resultSet.getString("DOCTYP"));
			stockRequestVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			stockRequestVO.setProductCode(resultSet.getString("PRDCOD"));
			stockRequestVO.setAgentCode(resultSet.getString("AGTCOD"));
			stockRequestVO.setAgentName(resultSet.getString("AGTNAM"));
			return stockRequestVO;
		}
	}
	/**
	 * @author a-1885
	 *
	 * @param filterVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<StockHolderStockDetailsVO> findStockHolderStockDetails
	(InformAgentFilterVO filterVo)throws SystemException,PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery
		(STOCKCONTROL_DEFAULTS_FINDALLSTOCKHOLDERDETAILS);
		int index =0;
		query.setParameter(++index,filterVo.getCompanyCode());
		Collection<StockHolderStockDetailsVO> results = null;
		return query.getResultList(new StockHolderStockMapper());
	}
	/**
	 *
	 * @author a-1885
	 *
	 */
	private class StockHolderStockMapper implements
						Mapper<StockHolderStockDetailsVO>{
		/**
		 * @author a-1885
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public StockHolderStockDetailsVO map(ResultSet rs)throws
		SQLException{
			StockHolderStockDetailsVO detailsVo = new
			StockHolderStockDetailsVO();

			detailsVo.setCompanyCode(rs.getString("CMPCOD"));
			detailsVo.setStockHolderCode(rs.getString("STKHLDCOD"));
			detailsVo.setContactDetails(rs.getString("CNTADR"));
			detailsVo.setDocType(rs.getString("DOCTYP"));
			detailsVo.setDocSubType(rs.getString("DOCSUBTYP"));
			detailsVo.setAutoProcessingQty(rs.getInt("ATOPRCQTY"));
			if("Y".equals(rs.getString("AUTREQFLG"))){
				detailsVo.setAutoProcessing(true);
			}
			else{
				detailsVo.setAutoProcessing(false);
			}
			detailsVo.setManualStockAvailable(rs.getInt("MNLSTKQTYAVL"));
			if("Y".equals(rs.getString("ORDALT"))){
				detailsVo.setReorderAlertFlag(true);
			}
			else{
				detailsVo.setReorderAlertFlag(false);
			}
			detailsVo.setReorderLevel(rs.getLong("ORDLVL"));
			detailsVo.setReorderQuantity(rs.getInt("ORDQTY"));
			detailsVo.setPhysicalStockAvailable(rs.getInt("PHTSTKQTYAVL"));
			detailsVo.setStockApproverCode(rs.getString("STKAPRCOD"));
			detailsVo.setAirlineId(rs.getString("ARLIDR"));
			return detailsVo;
		}
	}

	/**
	 * Method to find if the stock holder code is an approver
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkApprover(String companyCode, String stockHolderCode)
	throws SystemException, PersistenceException {
		log.entering("StockControl Sql DAO", "checkApprover");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_CHECKAPPROVER);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, stockHolderCode);
		int count = query
				.getSingleResult(getIntMapper("COUNT"));
		log.exiting("StockControl Sql DAO", "checkApprover");
		return (count>0 ? true : false);
	}
	/**
	 * @author a-1885
	 *
	 * @param stockAllocationVo
	 * @param startRange
	 * @param numberOfDocuments
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<RangeVO> findRangeForTransfer(StockAllocationVO
			stockAllocationVo,String startRange,long numberOfDocuments)
			throws SystemException,PersistenceException{
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FINDRANGEFORTRANSFER);
		Collection<RangeVO> ranges = new ArrayList<RangeVO>();
		query.setParameter(++index,stockAllocationVo.getCompanyCode());
		query.setParameter(++index,stockAllocationVo.getAirlineIdentifier());
		query.setParameter(++index,stockAllocationVo.getStockControlFor());
		query.setParameter(++index,stockAllocationVo.getDocumentType());
		query.setParameter(++index,stockAllocationVo.getDocumentSubType());
		query.setParameter(++index,findLong(startRange));
		query.setParameter(++index,findLong(startRange));
		ranges = query.getResultList(new RangeForTransfer());
		return groupRanges(ranges, numberOfDocuments, startRange);
	}
	/**
	 * @author a-1885
	 * @param ranges
	 * @param numberOfDocuments
	 * @return
	 */
	private Collection<RangeVO> groupRanges(Collection<RangeVO> ranges,long
			numberOfDocuments,String startrange){
		log.log(Log.FINE, "-----INSIDE GROUP RANGES------", ranges);
		long asciStartRange = findLong(startrange);
		Collection<RangeVO> groupedRanges = new ArrayList<RangeVO>();
		long count = 0;
		long documents = 0;
		if(ranges!=null && ranges.size()>0){
			for(RangeVO rangevo : ranges){
				log.log(Log.FINE, "Start Range---", asciStartRange);
				log.log(Log.FINE, "Start Range---", rangevo.getAsciiStartRange());
				rangevo.setAsciiEndRange(findLong(rangevo.getEndRange()));
				log.log(Log.FINE, "Start Range---", rangevo.getAsciiEndRange());
				if(asciStartRange>rangevo.getAsciiStartRange() && asciStartRange<=rangevo.getAsciiEndRange()){
					rangevo.setAsciiStartRange(asciStartRange);
					rangevo.setStartRange(startrange);
					log.log(Log.FINE, "Start Range---", asciStartRange);
					count+=rangevo.getAsciiEndRange()-rangevo.getAsciiStartRange()+1;
					log.log(Log.FINE, "Count value----", count);
				}else{
					count+=rangevo.getNumberOfDocuments();
				}
				log.log(Log.FINE, "Count value----", count);
				if(count<=numberOfDocuments){
					if(groupedRanges==null){
						groupedRanges = new ArrayList<RangeVO>();
					}
					documents+=rangevo.getAsciiEndRange()-rangevo.getAsciiStartRange()+1;
					groupedRanges.add(rangevo);
					if(count==numberOfDocuments){
						break;
					}
				}
				else{
					long endRange = rangevo.getAsciiStartRange()+(numberOfDocuments-
							documents)-1;
					rangevo.setEndRange(String.valueOf(endRange));
					int avlLength = rangevo.getEndRange().length();
					int totLength = 7-avlLength;
					StringBuilder endStr = new StringBuilder();
					for(int index =1;index<=totLength;index++){
						endStr.append("0");
					}
					endStr.append(rangevo.getEndRange());
					rangevo.setEndRange(endStr.toString());
					if(groupedRanges==null){
						groupedRanges = new ArrayList<RangeVO>();
					}
					groupedRanges.add(rangevo);
					break;
				}
				log.log(Log.FINE,
						"Range to Transfer After new range alteration  ",
						rangevo);
			}
		}
		return groupedRanges;
	}
	/**
	 *
	 * @author a-1885
	 *
	 */
	private class RangeForTransfer implements Mapper<RangeVO>{

		/**
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public RangeVO map(ResultSet rs)throws SQLException{
			RangeVO rangeVo = new RangeVO();
			rangeVo.setStartRange(rs.getString("STARNG"));
			rangeVo.setEndRange(rs.getString("ENDRNG"));
			rangeVo.setAsciiStartRange(rs.getLong("ASCSTARNG"));
			rangeVo.setAsciiEndRange(rs.getLong("ASCENDRNG"));
			rangeVo.setNumberOfDocuments(rs.getLong("DOCNUM"));
			return rangeVo;
		}
	}


	/*
	 * added by Sinoob T S
	 */
	/**
	 * @param stockAgentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<StockAgentVO> findStockAgentMappings(
			StockAgentFilterVO stockAgentFilterVO)
		throws SystemException, PersistenceException{

		log.log(Log.FINER, "stockAgentFilterVO : ", stockAgentFilterVO);
		int index = 0;
		StringBuffer masterQuery = new StringBuffer();
	    masterQuery.append(STOCKCONTROL_DEFAULTS_ROWNUM_RANK_QUERY);
	    String query = getQueryManager().getNamedNativeQueryString(
	    		STOCKCONTROL_DEFAULTS_FINDSTOCKAGENTMAPPINGS);
	    masterQuery.append(query);
	    PageableNativeQuery<StockAgentVO> pgNativeQuery = 
			new PageableNativeQuery<StockAgentVO>(0,
					masterQuery.toString(),new StockAgentMapper());
	    pgNativeQuery.setParameter(++index, stockAgentFilterVO.getCompanyCode());

		if(stockAgentFilterVO.getAgentCode() != null &&
				stockAgentFilterVO.getAgentCode().trim().length() != 0){
			pgNativeQuery.append(" AND AGT.AGTCOD = ? ");
			pgNativeQuery.setParameter(++index, stockAgentFilterVO.getAgentCode().trim());
		}
		if(stockAgentFilterVO.getStockHolderCode() != null &&
				stockAgentFilterVO.getStockHolderCode().trim().length() != 0){
			pgNativeQuery.append(" AND AGT.STKHLDCOD = ? ");
			pgNativeQuery.setParameter(++index, stockAgentFilterVO.getStockHolderCode().trim());
		}
		pgNativeQuery.append(STOCKCONTROL_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(stockAgentFilterVO.getPageNumber());
	}


	/**
     * Checks whether any Stockholder's Stock contains this awbNumber
	 * inside the range. If so returns the stockHolder code
	 * otherwise throws exception
	 *
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String checkDocumentExistsInAnyStock(String companyCode,
			int airlineId, String documentType, long documentNumber)
			throws SystemException, PersistenceException{
		log.log(Log.FINE, "companyCode : ", companyCode);
		log.log(Log.FINE, "airlineId : ", airlineId);
		log.log(Log.FINE, "documentType : ", documentType);
		log.log(Log.FINE, "documentNumber : ", documentNumber);
		int index = 0;
		String stkHolderCode = null;

		Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_CHECKDOCUMENTEXISTSINANYSTOCK);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, airlineId);
		query.setParameter(++index, documentType);
		query.setParameter(++index, documentNumber);

		stkHolderCode = query.getSingleResult(getStringMapper("STKHLDCOD"));
		log.log(Log.INFO, "$$$$$ stkHolderCode : ", stkHolderCode);
		log.log(Log.FINE,"RETURN");
		return stkHolderCode;
	}


	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> findAgentsForStockHolder(
			String companyCode, String stockHolderCode)
		throws SystemException, PersistenceException{

		log.log(Log.FINE, "companyCode : ", companyCode);
		log.log(Log.FINE, "stockHolderCode : ", stockHolderCode);
		Collection<String> agents = null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDAGENTSFORSTOCKHOLDER);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, stockHolderCode);
		agents = query.getResultList(getStringMapper("AGTCOD"));
		log.log(Log.FINE, "agents for stockholder are : ", agents);
		log.log(Log.FINE,"RETURN");
		return agents;
	}

	/**
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findSubTypeForDocument(String companyCode, int airlineId,
			String documentType, long documentNumber)
		throws SystemException, PersistenceException{
		log.log(Log.FINE,"ENTRY");

		log.log(Log.FINE, "companyCode : ", companyCode);
		log.log(Log.FINE, "airlineId : ", airlineId);
		log.log(Log.FINE, "documentType : ", documentType);
		log.log(Log.FINE, "documentNumber : ", documentNumber);
		int index = 0;
		String docSubType = null;

		Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDSUBTYPEFORDOCUMENT);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, airlineId);
		query.setParameter(++index, documentType);
		query.setParameter(++index, documentNumber);

		docSubType = query.getSingleResult(getStringMapper("DOCSUBTYP"));
		log.log(Log.INFO, "$$$$$ docSubType : ", docSubType);
		log.log(Log.FINE,"RETURN");
		return docSubType;
	}

	/*
	 * added by Sinoob T S ends
	 */

	/**
	 * @param requestVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findAutoProcessingQuantity(StockRequestVO requestVo)throws SystemException,
	PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery
		(STOCKCONTROL_DEFAULTS_FINDAUTOPROCESSINGQUANTITY);
		query.setParameter(1,requestVo.getCompanyCode());
		query.setParameter(2,requestVo.getStockHolderCode());
		query.setParameter(3,requestVo.getDocumentType());
		query.setParameter(4,requestVo.getDocumentSubType());
		return query.getSingleResult(new AutoProcessingQuantityMapper());
	}


	/**
	 *
	 * @author a-1885
	 *
	 */
	private class AutoProcessingQuantityMapper implements Mapper<String>{
		/**
		 * @author a-1885
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public String map(ResultSet rs)throws SQLException{
			return rs.getString("STKHLDCOD");
		}
	}
	/**
	 * @author a-1885
	 */
	public String checkDuplicateHeadQuarters(String companyCode,String stockHolderType)
	throws SystemException,PersistenceException{
		log.log(Log.FINE, "---SQLDAO -checkDuplicateHeadQuarters-",
				stockHolderType);
		String hq = null;
		Query query = getQueryManager().createNamedNativeQuery
		(STOCKCONTROL_DEFAULTS_CHECKDUPLICATEHQ);
		query.setParameter(1,companyCode);
		query.setParameter(2,stockHolderType);
		hq= query.getSingleResult(new DuplicateHeadQuarterMapper());
		log.log(Log.FINE, "---SQLDAO -checkDuplicateHeadQuarters--Return-", hq);
		return hq;
	}
	
	
	public String findHeadQuarterDetails(String companyCode,int airlineIdentifier,String documentType,String documentSubType)
	throws SystemException,PersistenceException{
		log.log(Log.FINE, "---SQLDAO -findHeadQuarterDetails-", documentType);
		String hq = null;
		Query query = getQueryManager().createNamedNativeQuery
		(STOCKCONTROL_DEFAULTS_CHECKDUPLICATEHQ);
		query.setParameter(1,companyCode);
		query.setParameter(2,airlineIdentifier);
		query.setParameter(3,documentType);
		query.setParameter(4,documentSubType);
		hq= query.getSingleResult(new DuplicateHeadQuarterMapper());
		log.log(Log.FINE, "---SQLDAO -findHeadQuarterDetails--Return-", hq);
		return hq;
	}
	/**
	 *
	 * @author a-1885
	 *
	 */
	private class DuplicateHeadQuarterMapper implements Mapper<String>{
		/**
		 * @author a-1885
		 */
		public String map(ResultSet rs)throws SQLException{
			return rs.getString("STKHLDCOD");
		}
	}

	/**
	 * @param stockAllocationVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
//	Commented for Query optimisation, ( removing the use of LPAD )
//	re-implemented with overLoaded method

/*	public Collection<String> checkReservedDocumentExists(StockAllocationVO stockAllocationVO)
		throws SystemException, PersistenceException{
		log.log(Log.FINER, "ENTRY");
		log.log(Log.FINEST,"====stockAllocationVO" + stockAllocationVO);
		Collection<String> reservedDocuments = null;
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();

		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_CHECKRESERVEDDOCUMENTEXISTS);
		qry.setParameter(++index, stockAllocationVO.getCompanyCode());
		if(stockAllocationVO.getAirportCode() != null){
			log.log(Log.FINEST,"===setting airporCode from stkAllcVo in the Query:" + stockAllocationVO.getAirportCode());
			qry.setParameter(++index, stockAllocationVO.getAirportCode());
		}else{
			log.log(Log.FINEST,"===settin airporCode from logon in the query:" +logon.getAirportCode());
			qry.setParameter(++index, logon.getAirportCode());
		}

		qry.setParameter(++index, stockAllocationVO.getAirlineIdentifier());


    	Collection<RangeVO> rangeVOColl = stockAllocationVO.getRanges();
    	int rangeVOCollSize = rangeVOColl.size();
    	log.log(Log.FINE, "rangeVOCollSize----->"+ rangeVOCollSize);
    	int innerIndex = 1;
    	if (rangeVOColl != null) {
    		for (RangeVO vo : rangeVOColl) {
    			log.log(Log.FINE, "i------->>" + innerIndex);
    			if (vo.getStartRange() != null && vo.getEndRange() != null) {
    				qry.append("( LPAD(AWB.DOCNUM, LENGTH(AWB.DOCNUM) - 1) BETWEEN  ? AND ? ) ");
    				qry.setParameter(++index, vo.getStartRange());
    				qry.setParameter(++index, vo.getEndRange());
    			}
    			if (innerIndex < rangeVOCollSize) {
					log.log(Log.FINE,"-----------i<=rangeVOCollSize");
					qry.append(" OR ");
    				innerIndex = innerIndex + 1;
    			}
    		}
    		qry.append(" ) ");
    	}

    	reservedDocuments = qry.getResultList(getStringMapper("MSTDOCNUM"));

		log.log(Log.FINE,"===returning reservedDocs : " + reservedDocuments);
		log.log(Log.FINER, "RETURN");
		return reservedDocuments;
	}

*/

	/**
	 * @param stockFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> checkReservedDocumentExists(StockFilterVO stockFilterVO)
			throws SystemException, PersistenceException{
		log.log(Log.FINER, "ENTRY");
		log.log(Log.FINEST, "====StockFilterVO", stockFilterVO);
		Collection<String> reservedDocuments = null;

		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_CHECKRESERVEDDOCUMENTEXISTS);
		qry.setParameter(++index, stockFilterVO.getCompanyCode());
		qry.setParameter(++index, stockFilterVO.getAirportCode());
		qry.setParameter(++index, stockFilterVO.getAirlineIdentifier());

		qry.setParameter(++index, stockFilterVO.getRangeFrom());
		qry.setParameter(++index, stockFilterVO.getRangeFrom());

		qry.setParameter(++index, stockFilterVO.getRangeTo());
		qry.setParameter(++index, stockFilterVO.getRangeTo());

    	reservedDocuments = qry.getResultList(getStringMapper("MSTDOCNUM"));

		log.log(Log.FINE, "===returning reservedDocs : ", reservedDocuments);
		log.log(Log.FINER, "RETURN");
		return reservedDocuments;

	}

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ReservationVO findReservationDetails(DocumentFilterVO filterVO)
			throws SystemException, PersistenceException{

		log.log(Log.FINER, "ENTRY");
		log.log(Log.FINEST, "====DocumentFilterVO", filterVO);
		ReservationVO reservationVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDRESERVATIONDETAILS);

		qry.setParameter(++index, DEFAULT_STOCKHOLDERCODE_FOR_CTO);
		qry.setParameter(++index, filterVO.getCompanyCode());
		qry.setParameter(++index, filterVO.getAirlineIdentifier());
		qry.setParameter(++index, filterVO.getDocumentNumber());

    	//String airport = qry.getSingleResult(getStringMapper("RESARPCOD"));
		reservationVO = qry.getSingleResult(new ReservationDetailsMapper());


		log.log(Log.FINE, "===reservationVO : ", reservationVO);
		log.log(Log.FINER, "RETURN");
		return reservationVO;



	}

	/**
	 * @param stockDepleteFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<StockAllocationVO> findStockRangeUtilisation
	(StockDepleteFilterVO stockDepleteFilterVO)throws
	SystemException,PersistenceException{

		String baseQry = getQueryManager().getNamedNativeQueryString(
				STOCKCONTROL_DEFAULTS_FIND_STOCKRANGE_UTILISATION);
		Query query = new StockRangeUtilisatonFilterQuery(stockDepleteFilterVO,
				baseQry);
		return query.getResultList(new StockRangeUtilisationMultiMapper());
	}
	/**
	 * @author a-1885
	 */
	/*public void deleteStockRangeUtilisation(String companyCode,int airlineId)
	throws SystemException,PersistenceException{
		log.entering("StockSQLDAO", "deleteStockRangeUtilisation");
		log.log(Log.FINE,"-companyCode-->"+companyCode);
		log.log(Log.FINE,"-companyCode-->"+airlineId);
		String result = null;
		Procedure deleteProcedure = getQueryManager().createNamedNativeProcedure(
				STOCKCONTROL_DEFAULTS_DELETESTOCKRANGEUTILISATION);
		deleteProcedure.setParameter(1,companyCode);
		deleteProcedure.setParameter(2,airlineId);
		deleteProcedure.setOutParameter(3,SqlType.STRING);
		deleteProcedure.execute();
    	log.log(Log.FINE,"---DeltaProc in SQLDAO---");

	}*/

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param documentType
	 * @param documentSubType
	 * @param airlineIdentifier
	 * @param rangeVO
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findStockRangeUtilisationExists(String companyCode,
			String stockHolderCode,String documentType,String documentSubType,
			int airlineIdentifier,RangeVO rangeVO)throws
	SystemException,PersistenceException{
		log.entering("StockControlDefaultsSqlDAO", "findStockRangeUtilisationExists");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FIND_STOCKRANGE_UTILISATIONEXISTS);

		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, stockHolderCode);
		qry.setParameter(++index, documentType);
		qry.setParameter(++index, documentSubType);
		qry.setParameter(++index, airlineIdentifier);
		qry.setParameter(++index, rangeVO.getStartRange());

		return qry.getSingleResult(getStringMapper("SEQNUM"));
	}

	/**
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param documentNumber
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkStockRangeUtilised(String companyCode,int airlineIdentifier,String stockHolderCode,
			String documentType,String documentSubType,String documentNumber)throws	SystemException,PersistenceException{
		log.entering("StockControlDefaultsSqlDAO", "checkStockRangeUtilised--->");
		                
		log.log(Log.FINE, " DOCUMENET NUMBER IN  :: ", documentNumber);
		int index = 0;
		boolean isStockRangeUtilised = false ;
		Query qry = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_CHECK_STOCKRANGE_UTILISED);

		qry.setParameter(++index, companyCode);
		if(airlineIdentifier!=0){
			qry.append(" AND ARLIDR = ? ");
			qry.setParameter(++index, airlineIdentifier);
			}
		if(stockHolderCode!=null && stockHolderCode.trim().length()!=0){
			qry.append(" AND STKHLDCOD = ? ");
			qry.setParameter(++index, stockHolderCode);
			}
		if(documentType!=null && documentType.trim().length()!=0){
			qry.append(" AND DOCTYP = ? ");
			qry.setParameter(++index, documentType);
			}
		if(documentSubType!=null && documentSubType.trim().length()!=0){
			qry.append(" AND DOCSUBTYP = ? ");
			qry.setParameter(++index, documentSubType);
			}
		//Edited as part of ICRD-46860
		if(DocumentValidationVO.DOC_TYP_AWB.equals(documentType)) {
		if(documentNumber!=null && documentNumber.trim().length()!=0){    
			qry.append(" AND MSTDOCNUM = LPAD(?, 7, '0') ");  //Added by Sathish for 70161   
			qry.setParameter(++index, documentNumber);            
			}
		} else {			
			if(documentNumber!=null && documentNumber.trim().length()!=0){ 
				qry.append(" AND MSTDOCNUM = ?"); 
				qry.setParameter(++index, documentNumber);    
			}
                
		}
		String cmpcod = qry.getSingleResult(getStringMapper("CMPCOD"));
		if((!"".equals(cmpcod)) && (cmpcod!=null)){
			isStockRangeUtilised = true;
		}
		return isStockRangeUtilised;
	}




public String findPrivileage(String stockControlFor)throws SystemException,PersistenceException{
	log.entering("StockControlDefaultsSqlDAO", "findPrivileage");
	int index = 0;
	Query qry = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FIND_FINDPRIVILEAGE);

	qry.setParameter(++index, stockControlFor);

	log.entering("StockControlDefaultsSqlDAO", "findPrivileage Queryyyyy"+qry);
	String privileage = qry.getSingleResult(getStringMapper("CTLPVG"));
	log.entering("StockControlDefaultsSqlDAO", "Privileage in SQLDAO iSSS"+privileage);
	return privileage;
}

/**
 * @param companyCode
 * @param airlineIdentifier
 * @param documentNumber
 * @return String
 * @throws SystemException
 * @throws PersistenceException
 */
public Collection<BlacklistStockVO> findRangesToBeDeleted(String companyCode,
				int airlineId, String docType, String docSubType,
			String startRange, String endRange)throws SystemException,PersistenceException{
		Query qry = getQueryManager().createNamedNativeQuery(
						STOCKCONTROL_DEFAULTS_FIND_FINDRANGESTOBEDELETEDFORBLACKLISTING);
				int index=0;
				qry.setParameter(++index, companyCode);
				qry.setParameter(++index, airlineId);
				qry.setParameter(++index, docType);
				qry.setParameter(++index, docSubType);
				qry.setParameter(++index, findLong(startRange));
				qry.setParameter(++index, findLong(endRange));

		return qry.getResultList(new BlackListForRevokeMapper());

	}
/**
 * @author a-1885
 * @param stockFilterVO
 * @return ranges
 * @throws SystemException
 * @throws PersistenceException
 */
public Collection<RangeVO> findRangesForViewRange(StockFilterVO stockFilterVO)throws SystemException,
PersistenceException{
		Query qry = null;
		if(isOracleDataSource()) {
			qry = getQueryManager().createNamedNativeQuery(
					STOCKCONTROL_DEFAULTS_FIND_FINDRANGESFORVIEWRANGE);
		} else {
			qry = getQueryManager().createNamedNativeQuery(POSTGRES_STOCKCONTROL_DEFAULTS_FIND_FINDRANGESFORVIEWRANGE);
		}
		String manual = stockFilterVO.isManual() ? StockFilterVO.FLAG_YES
      			:StockFilterVO.FLAG_NO ;
      	qry.setParameter(1,stockFilterVO.getCompanyCode());
      	qry.setParameter(2,stockFilterVO.getAirlineIdentifier());
      	qry.setParameter(3,stockFilterVO.getDocumentType());
      	qry.setParameter(4,stockFilterVO.getDocumentSubType());
      	qry.setParameter(5,stockFilterVO.getStockHolderCode());
      	qry.setParameter(6,manual);
      	return qry.getResultList(new RangeForViewRangeMapper());

}

/**
 * @author a-2434
 * @param Collection<RangeVO> rangeVOS
 * @return Collection
 * @throws SystemException
 */
public Collection<RangeVO> findRangesforMerge(Collection<RangeVO> rangeVOS) throws SystemException{
	Collection<RangeVO> newRangeVOS = new HashSet<RangeVO>();
	//Collection<RangeVO> ranges = null;
	for(RangeVO rangeVO : rangeVOS){
		log.log(Log.FINE, "----------------", rangeVO);
		rangeVO.setAsciiStartRange(findLong(rangeVO.getStartRange()));
		rangeVO.setAsciiEndRange(findLong(rangeVO.getEndRange()));
		newRangeVOS = findRangeforMerge(rangeVO,newRangeVOS);
		/*if(ranges != null || ranges.size()>0){
			newRangeVOS.addAll(ranges);
		}*/
	}
	log
			.log(
					Log.FINE,
					"---------------------------Ranges found for merging--------------------",
					newRangeVOS);
	return newRangeVOS;
}
/**
 * This method will be called recursively until all the cuncurrent ranges are found
 * @author a-2434
 * @param Collection<RangeVO>
 * @return Collection
 * @throws SystemException
 */
private Collection<RangeVO> findRangeforMerge(RangeVO rangeVO,Collection<RangeVO> newRangeVOS) throws SystemException{
	Collection<RangeVO> rangeVOS = new ArrayList<RangeVO>();
	//log.log(Log.FINE,"in find--at begining------------ "+newRangeVOS);
	Query qry = getQueryManager().createNamedNativeQuery(
			STOCKCONTROL_DEFAULTS_FIND_FINDRANGESFORMERGE);
	int index = 1;
	long ascStaRng = 0;
	long ascEndRng = 0;
	qry.setParameter(index++,rangeVO.getCompanyCode());
	qry.setParameter(index++,rangeVO.getAirlineIdentifier());
	qry.setParameter(index++,rangeVO.getDocumentType());
	qry.setParameter(index++,rangeVO.getDocumentSubType());
	qry.setParameter(index++,rangeVO.getStockHolderCode());
	if(rangeVO.isManual()){
		//Modified by A-7373 the manual flag value
		qry.setParameter(index++,"Y");
	}else{
		qry.setParameter(index++,"N");
	}
	if(rangeVO.getOperationFlag()==null||!("S").equals(rangeVO.getOperationFlag())){
	//if(!rangeVO.getStartRange().equals("")){
		qry.append(" ASCENDRNG = ? ");
		ascStaRng = rangeVO.getAsciiStartRange();
		ascStaRng--;
		qry.setParameter(index++,ascStaRng);
		rangeVO.setOperationFlag(null);
		if(rangeVO.getOperationFlag()==null||!("E").equals(rangeVO.getOperationFlag())){
			qry.append("OR ");
		}
	}
	if(rangeVO.getOperationFlag()==null||!("E").equals(rangeVO.getOperationFlag())){
	//if(!rangeVO.getEndRange().equals("")){
		qry.append(" ASCSTARNG = ?");
		ascEndRng = rangeVO.getAsciiEndRange();
		ascEndRng++;
		qry.setParameter(index++,ascEndRng);
		rangeVO.setOperationFlag(null);
	}
	qry.append(")");
	rangeVOS = qry.getResultList(new Mapper<RangeVO>(){

		
		public RangeVO map(ResultSet rs) throws SQLException {
			RangeVO rangeVO = new RangeVO();
			rangeVO.setCompanyCode(rs.getString("CMPCOD"));
			rangeVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
			rangeVO.setDocumentType(rs.getString("DOCTYP"));
			rangeVO.setDocumentSubType(rs.getString("DOCSUBTYP"));
			rangeVO.setStockHolderCode(rs.getString("STKHLDCOD"));
			rangeVO.setSequenceNumber(rs.getString("RNGSEQNUM"));
			rangeVO.setStartRange(rs.getString("STARNG"));
			rangeVO.setEndRange(rs.getString("ENDRNG"));
			rangeVO.setAsciiStartRange(rs.getLong("ASCSTARNG"));
			rangeVO.setAsciiEndRange(rs.getLong("ASCENDRNG"));
			//Modified the flag value by A-7373
			if("Y".equalsIgnoreCase(rs.getString("MNLFLG"))){
				rangeVO.setManual(true);
			} else{
				rangeVO.setManual(false);
			}
			return rangeVO;
		}
		
	});
	//log.log(Log.FINE,"in find-------------- "+rangeVOS);
	newRangeVOS.addAll(rangeVOS);
	//log.log(Log.FINE,"in find---after----------- "+newRangeVOS);
	if(rangeVOS == null || rangeVOS .size() ==0){
		return newRangeVOS;
	}else{
		for(RangeVO rangevo: rangeVOS){
			if(rangevo.getAsciiStartRange()==ascEndRng){
				//rangevo.setStartRange("");
				rangevo.setOperationFlag("S");
				findRangeforMerge(rangevo,newRangeVOS);
			} 
			if(rangevo.getAsciiEndRange()==ascStaRng){
				//rangevo.setEndRange("");
				rangevo.setOperationFlag("E");
				findRangeforMerge(rangevo,newRangeVOS);
			}
		}
	}
	return newRangeVOS;
}

/**
 *
 * @author a-1885
 *
 */
private class RangeForViewRangeMapper implements Mapper<RangeVO>{
	/**
	 * @author a-1885
	 */
	public RangeVO map(ResultSet rs)throws SQLException{
		RangeVO rangeVo = new RangeVO();
		//int checkDigit=7;
		/*StringBuilder startRange= new StringBuilder();
		StringBuilder endRange= new StringBuilder();
		int appendStartRange = Integer.parseInt((rs.getString("STARNG")))%checkDigit ;
		int appendEndRange = Integer.parseInt((rs.getString("ENDRNG")))%checkDigit ;
		startRange.append(rs.getString("STARNG")).append(appendStartRange);
		endRange.append(rs.getString("ENDRNG")).append(appendEndRange);
		rangeVo.setStartRange(startRange.toString());
		rangeVo.setEndRange(endRange.toString());*/
		
		//Added by A-3791 for ICRD-110168
		if(rs.getString("RNGACPDAT")!=null){
		rangeVo.setStockAcceptanceDate(new LocalDate(LocalDate.NO_STATION,
				Location.NONE,rs.getDate("RNGACPDAT")));
		}
		rangeVo.setStartRange(rs.getString("STARNG"));
		rangeVo.setEndRange(rs.getString("ENDRNG"));
		rangeVo.setNumberOfDocuments(rs.getLong("DOCNUM"));
		String masterDocumentNumber = rs.getString("MSTDOCNUM");
		if(masterDocumentNumber!=null){
			Collection<String> masterDocumentNumbers = new ArrayList<String>();
			String[] documentNumbers = masterDocumentNumber.split(",");
			for(int i=0;i<documentNumbers.length;i++){
				/*int utilisedDoc = Integer.parseInt((documentNumbers[i]))%checkDigit ;
				StringBuilder utilisedDocBuilder= new StringBuilder();
				utilisedDocBuilder.append(documentNumbers[i]).append(utilisedDoc);
				masterDocumentNumbers.add(utilisedDocBuilder.toString());*/
				masterDocumentNumbers.add(documentNumbers[i]);
			}
			rangeVo.setMasterDocumentNumbers(masterDocumentNumbers);
		}
		return rangeVo;
	}
}
/**
 * @author A-3184
 * @param stockFilterVO
 * @return Collection<RangeVO>
 * @throws SystemException
 * @throws PersistenceException
 */
public Collection<RangeVO> findAWBStockDetailsForPrint(StockFilterVO stockFilterVO)
		throws SystemException {
	Query query = getQueryManager().createNamedNativeQuery(
			STOCKCONTROL_DEFAULTS_FIND_AWBSTOCKDETAILS);
	query.setParameter(1, stockFilterVO.getCompanyCode());
	query.setParameter(2, stockFilterVO.getStockHolderCode());
	query.setParameter(3, stockFilterVO.getStockHolderCode());	
	Collection<RangeVO> rangeVOs = query
			.getResultList(new FindAWBStockDetailsForPrintMapper());
	log.log(Log.FINE, "Inside findAWBStockDetailsForPrint --->", rangeVOs);
	return rangeVOs;
}

/**
 * This mapper is used for findAWBStockDetailsForPrint()
 *
 * @author A-3184
 *
 */
private class FindAWBStockDetailsForPrintMapper implements Mapper<RangeVO> {
	/**
	 * This method is used to map the result of query to RangeVO
	 *
	 * @param resultSet
	 * @throws SQLException
	 * @return RangeVO
	 */
	public RangeVO map(ResultSet resultSet) throws SQLException {				
			RangeVO rangeVO = new RangeVO();
			String startRng = resultSet.getString("STARNG");
			String endRng = resultSet.getString("ENDRNG");
			String awbpfx = resultSet.getString("AWBPFX");
			int checkDigit = resultSet.getInt("AWBCHKDGT");
			log.log(Log.FINE, "Inside appendRanges --->");
			if(checkDigit!=0){
			int appendStartRange = Integer.parseInt(startRng)%(checkDigit) ;

			int appendEndRange = Integer.parseInt(endRng)%(checkDigit) ;		    			

			StringBuilder startRange=new StringBuilder(awbpfx).append("-").append(startRng).append(appendStartRange);
			
			StringBuilder endRange=new StringBuilder(awbpfx).append("-").append(endRng).append(appendEndRange);			
			rangeVO.setStartRange(startRange.toString());
			rangeVO.setEndRange(endRange.toString());	
			}
			rangeVO.setNumberOfDocuments(resultSet.getInt("DOCNUM"));							
			rangeVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			rangeVO.setStockHolderName(resultSet.getString("STKHLDNAM"));
		return rangeVO;         
	}
	
}                 
/**
 * @author A-3155
 */
public boolean checkAWBExistsInOperation(String companyCode,String masterDocumentNumber,int ownerID, String documentType, String documentSubType) throws SystemException{
	Query query = getQueryManager().createNamedNativeQuery(
			STOCKCONTROL_DEFAULTS_CHECK_AWB_IN_OPS);
	query.setParameter(1, companyCode);
	query.setParameter(2, ownerID);
	query.setParameter(3, masterDocumentNumber);
	//query.setParameter(4, documentType);
	//query.setParameter(5, documentSubType);
	                        
	String awb=query.getSingleResult(getStringMapper("MSTDOCNUM"));
	log.log(Log.FINE, "Inside checkAWBExistsInOperation  --->  :", awb);
	return awb!=null ;                                         
}
	/**               
	 * @author A-3155
	 */  
	public boolean checkStockRangeUtilisedLog(String companyCode,int airlineIdentifier,String documentNumber, String documentType, String documentSubType)throws
	SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_CHECK_UTL_LOG);       
		query.setParameter(1, companyCode);
		query.setParameter(2, airlineIdentifier);
		query.setParameter(3, String.valueOf(findLong(documentNumber)));
		query.setParameter(4, documentType);
		query.setParameter(5, documentSubType);
		                         
		String awb=query.getSingleResult(getStringMapper("MSTDOCNUM"));
		log.log(Log.FINE, "Inside checkStockRangeUtilisedLOG  --->  :", awb);
		return awb!=null ;                                         
		
	}
	/**
	 * This method is used to return the stock details of a customer.
	 *
	 * @param stockDetailsFilterVO
	 * @return Collection<StockDetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
 	public StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO)
		throws SystemException, PersistenceException {
	 	log.log(Log.FINE, "\n\n---stockDetailsFilterVO---",
				stockDetailsFilterVO);
		Query query = getQueryManager().createNamedNativeQuery
		(STOCKCONTROL_DEFAULTS_FIND_CUSTOMER_STKDETAILS);
		String companyCode = stockDetailsFilterVO.getCompanyCode();
		String stockHolderCode = stockDetailsFilterVO.getStockHolderCode();

		query.setParameter(1, companyCode);
		query.setParameter(2, stockHolderCode);
		query.setParameter(3, companyCode);
		query.setParameter(4, stockHolderCode);
		query.append(" WHERE Z.STKHLDCOD = ? ");
		query.setParameter(5,stockHolderCode);
		query.append(" AND  Z.CMPCOD=MST.CMPCOD ");
		query.append("AND Z.STKAPRCOD=MST.STKHLDCOD ");
		query.append(" GROUP BY Z.CMPCOD,Z.DOCTYP,Z.DOCSUBTYP,Z.STKHLDCOD");
		StockDetailsVO stockDetailsVO =query.getSingleResult(new CustomerStockDetailsMapper());
		if(stockDetailsVO!=null){
			    Collection<RangeVO> availableRngs = findAvailableRangesForCustomer(stockDetailsFilterVO);
				log.log(Log.FINE, "----->>>>AvailableRanges<<<<<---->>",
						availableRngs);
				Collection<RangeVO> allocatedRngs = findAllocatedRangesForCustomer(stockDetailsFilterVO);
				log.log(Log.FINE, "----->>>>allocatedRngs<<<<<---->>",
						allocatedRngs);
				Collection<RangeVO> usedRngs = findUsedRangesForCustomer(stockDetailsFilterVO);
				log.log(Log.FINE, "----->>>>usedRngs<<<<<---->>", usedRngs);
				int availableSize = availableRngs.size();
				int allocatedSize = allocatedRngs.size();
				int usedSize = usedRngs.size();

				int result=(availableSize>=allocatedSize && availableSize>=usedSize)? 0:
						(allocatedSize>=availableSize && allocatedSize>=usedSize)?1:
						(usedSize>=availableSize && usedSize>=allocatedSize)? 2:100;

				Map<Integer,Collection<RangeVO>> rangeMap = new HashMap<Integer,Collection<RangeVO>>();

					rangeMap.put(0,availableRngs);
					rangeMap.put(1,allocatedRngs);
					rangeMap.put(2,usedRngs);

			stockDetailsVO.setCustomerRanges(rangeMap.get(result));
			ArrayList<RangeVO> customerRangeVOs=(ArrayList<RangeVO>)stockDetailsVO.getCustomerRanges();
			if(customerRangeVOs!=null){
				int i =0;
				while(i<=2){
					if(i!=result){
						int count=0;
						for(RangeVO range:rangeMap.get(i)){
							RangeVO finalRange = customerRangeVOs.get(count);
							if(i==0){
								finalRange.setAvlStartRange(range.getAvlStartRange());
								finalRange.setAvlEndRange(range.getAvlEndRange());
								finalRange.setAvlNumberOfDocuments(range.getAvlNumberOfDocuments());
								finalRange.setAvailableRange(range.getAvailableRange());
							}
							if(i==1){
								finalRange.setAllocStartRange(range.getAllocStartRange());
								finalRange.setAllocEndRange(range.getAllocEndRange());
								finalRange.setAllocNumberOfDocuments(range.getAllocNumberOfDocuments());
								finalRange.setFromStockHolderCode(range.getFromStockHolderCode());
								finalRange.setAllocatedRange(range.getAllocatedRange());
							}
							if(i==2){
								finalRange.setUsedStartRange(range.getUsedStartRange());
								finalRange.setUsedEndRange(range.getUsedEndRange());
								finalRange.setUsedNumberOfDocuments(range.getUsedNumberOfDocuments());
								finalRange.setUsedRange(range.getUsedRange());
							}
							count++;
						}
					}
					i++;
				}

				stockDetailsVO.setCustomerRanges(customerRangeVOs);
			}
	 		log.log(Log.FINE, "\n\n\n%%%%%%resultMonitorVOs%%%%%%%",
					stockDetailsVO);
			log.log(Log.FINE, "\n\n\n%%%%%%ranges%%%%%%%", stockDetailsVO.getCustomerRanges());
		}
		return stockDetailsVO;
	}
 	/**
	 * This mapper is used for findcustomerStockDetails()
	 *
	 * @author A-3184
	 *
	 */
	class CustomerStockDetailsMapper implements Mapper<StockDetailsVO> {

		/**
		 * @param arg0
		 * @return
		 * @throws SQLException
		 */
		public StockDetailsVO map(ResultSet resultSet) throws SQLException {
			StockDetailsVO stkVo = new StockDetailsVO();
			stkVo.setCompanyCode(resultSet.getString("CMPCOD"));
			stkVo.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			stkVo.setStockHolderType(resultSet.getString("STKHLDTYP"));
			stkVo.setStockHolderName(resultSet.getString("STKHLDNAM"));
			stkVo.setApproverCode(resultSet.getString("STKAPRCOD"));
			stkVo.setAvailableStock(resultSet.getLong("AVLSTK"));
			stkVo.setRequestPlaced(resultSet.getLong("REQ_PLACE"));
			stkVo.setRequestReceived(resultSet.getLong("REQ_REC"));
			stkVo.setStockApproverName(resultSet.getString("STKAPRNAM"));
			return stkVo;
		}

	}

	/**
	 * @author a-3184
	 * @param stockDetailsFilterVO
	 * @return ranges
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<RangeVO> findAvailableRangesForCustomer(StockDetailsFilterVO stockDetailsFilterVO)throws SystemException,
	PersistenceException{

			log.log(Log.FINE, "--stockDetailsFilterVO--", stockDetailsFilterVO);
			Query qry = getQueryManager().createNamedNativeQuery(
						STOCKCONTROL_DEFAULTS_FIND_AVLRNG_FORCUSTOMER);


	      	qry.setParameter(1,stockDetailsFilterVO.getCompanyCode());

	      	qry.setParameter(2,stockDetailsFilterVO.getStockHolderCode());

	      	if(stockDetailsFilterVO.getStartDate()!=null){
	      		qry.append("AND STKRNG.RNGACPDAT >=  ? ");
	      		qry.setParameter(3,stockDetailsFilterVO.getStartDate());
	      	}
	      	if(stockDetailsFilterVO.getEndDate()!=null){
	      		qry.append("AND STKRNG.RNGACPDAT <=  ? ");
	      		qry.setParameter(4,stockDetailsFilterVO.getEndDate());
	      	}
	      	qry.append("ORDER BY AWBPREFIX,STARNG");
	      	return qry.getResultList(new FindAvlRngForCustomerMapper());

	}
	/**
	*
	* @author a-3184
	*
	*/
	private class FindAvlRngForCustomerMapper implements Mapper<RangeVO>{
		/**
		 * @author a-3184
		 */
		public RangeVO map(ResultSet rs)throws SQLException{
			RangeVO rangeVo = new RangeVO();
			rangeVo.setAvlStartRange(rs.getString("AVLSTARNG"));
			rangeVo.setAvlEndRange(rs.getString("AVLENDRNG"));
			rangeVo.setAvlNumberOfDocuments(rs.getLong("AVLDOCNUM"));
			rangeVo.setAwbPrefix(rs.getString("AWBPREFIX"));
			rangeVo.setAirlineIdentifier(rs.getInt("ARLIDR"));
			StringBuilder availRange = new StringBuilder();
			availRange.append(rangeVo.getAvlStartRange()).append("-").append(rangeVo.getAvlEndRange());
			rangeVo.setAvailableRange(availRange.toString());
			return rangeVo;
		}
	}
	/**
	 * @author A-3184
	 * @param stockDetailsFilterVO
	 * @return Collection<RangeVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<RangeVO> findAllocatedRangesForCustomer(StockDetailsFilterVO stockDetailsFilterVO)
			throws SystemException, PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				STOCKCONTROL_DEFAULTS_FIND_ALLOCRNG_FORCUSTOMER);

		LocalDate startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);

		query.setParameter(1, stockDetailsFilterVO.getCompanyCode());

		query.setParameter(2, stockDetailsFilterVO.getStockHolderCode());

		if(stockDetailsFilterVO.getStartDate()!=null){
			String startDateStr = new StringBuilder().append(stockDetailsFilterVO.getStartDate().toDisplayDateOnlyFormat())
			.append(" ").append("00:00").toString();
			startDate.setDateAndTime(startDateStr,true);
      		query.append("AND TXN.TXNDAT >=  ? ");
      		query.setParameter(3,startDate);
      	}
      	if(stockDetailsFilterVO.getEndDate()!=null){
      		String endDateStr = new StringBuilder().append(stockDetailsFilterVO.getEndDate().toDisplayDateOnlyFormat())
    		.append(" ").append("23:59").toString();
    		endDate.setDateAndTime(endDateStr,true);
      		query.append("AND TXN.TXNDAT <=  ? ");
      		query.setParameter(4,endDate);
      	}
      	query.append(" ORDER BY AWBPREFIX,STARNG ");
		log.log(Log.INFO, "\n\nstockDetailsFilterVO\n\n", stockDetailsFilterVO);
		return query.getResultList(new FindAllocRngForCustMapper());
	}
	/**
	 * This mapper is used for findAllocatedStocksForCustomer()
	 *
	 * @author A-3184
	 *
	 */
	private class FindAllocRngForCustMapper implements Mapper<RangeVO> {
		/**
		 * This method is used to map the result of query to RangeVO
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return RangeVO
		 */
		public RangeVO map(ResultSet resultSet) throws SQLException {
			RangeVO rangeVO = new RangeVO();
			rangeVO.setAllocStartRange(resultSet.getString("ALLCSTARNG"));
			rangeVO.setFromStockHolderCode(resultSet.getString("FRMSTKHLDCOD"));
			rangeVO.setAllocEndRange(resultSet.getString("ALLCENDRNG"));
			rangeVO.setAllocNumberOfDocuments(resultSet.getLong("ALLCDOCNUM"));
			rangeVO.setAwbPrefix(resultSet.getString("AWBPREFIX"));
			StringBuilder allocRange = new StringBuilder();
			allocRange.append(rangeVO.getAllocStartRange()).append("-").append(rangeVO.getAllocEndRange());
			rangeVO.setAllocatedRange(allocRange.toString());
			return rangeVO;
		}
	}
	
	/**
	*@author A-3184
	* @param stockDetailsFilterVO
	* @return Collection<RangeVO>
	* @throws SystemException
	* @throws PersistenceException
	*/

	private Collection<RangeVO> findUsedRangesForCustomer(StockDetailsFilterVO stockDetailsFilterVO)
		throws SystemException,	PersistenceException {
	log.entering("StockControl SqlDAO", "findUsedStocksForCustomer");
	LocalDate startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
	LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
	Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDUSEDRANGESFORCUSTOMER);
	Collection<RangeVO> rangeVOs=new ArrayList<RangeVO>();
	log.log(Log.FINE,
			"----In findUsedStocksForCustomer----------stockDetailsFilterVO",
			stockDetailsFilterVO);
	int index = 0;

	query.setParameter(1, stockDetailsFilterVO.getCompanyCode());
	query.setParameter(2, stockDetailsFilterVO.getStockHolderCode());
	if(stockDetailsFilterVO.getStartDate()!=null){
		String startDateStr = new StringBuilder().append(stockDetailsFilterVO.getStartDate().toDisplayDateOnlyFormat())
		.append(" ").append("00:00").toString();
		startDate.setDateAndTime(startDateStr,true);
  		query.append("AND TXN.TXNDAT >=  ? ");
  		query.setParameter(3,startDate);
  	}
  	if(stockDetailsFilterVO.getEndDate()!=null){
  		String endDateStr = new StringBuilder().append(stockDetailsFilterVO.getEndDate().toDisplayDateOnlyFormat())
		.append(" ").append("23:59").toString();
		endDate.setDateAndTime(endDateStr,true);
  		query.append("AND TXN.TXNDAT <=  ? ");
  		query.setParameter(4,endDate);
  	}
	rangeVOs=query.getResultList(new FindUsedRangesForCustomerMapper());
	log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", query);
	log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", rangeVOs);
	return rangeVOs;

	}
	
	/**
	*@author A-3184
	* @param rangeVO
	* @return Collection<RangeVO>
	* @throws SystemException
	* @throws PersistenceException
	*/
	private class FindUsedRangesForCustomerMapper implements Mapper<RangeVO> {
		/**
		 * Method for mapping StockRangeHistory
		 *
		 * @param resultSet
		 * @throws SQLException
		 * @return StockRangeHistoryVO
		 */
		public RangeVO map(ResultSet resultSet) throws SQLException {

			RangeVO rangeVO=new RangeVO();

			rangeVO.setStockHolderCode(resultSet.getString("FRMSTKHLDCOD"));
			rangeVO.setUsedStartRange(resultSet.getString("USEDSTARNG"));
			rangeVO.setUsedEndRange(resultSet.getString("USEDENDRNG"));
			rangeVO.setUsedNumberOfDocuments(resultSet.getLong("USEDDOCNUM"));
			rangeVO.setAwbPrefix(resultSet.getString("AWBPREFIX"));
			StringBuilder usedRange = new StringBuilder();
			usedRange.append(rangeVO.getUsedStartRange()).append("-").append(rangeVO.getUsedEndRange());
			rangeVO.setUsedRange(usedRange.toString());
			log.log(Log.INFO, "$$$$mapper  >>>>>>>>>>", rangeVO);
			return rangeVO;

			} 
		}
		public String findStockHolderForCustomer(String companyCode,String customerCode) 
		throws SystemException, PersistenceException {
			log.entering("StockControlDefaultsSqlDAO", "findStockHolderForCustomer");
			int index = 0;
			Query qry = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDSTOCKHOLDERFORCUSTOMER);
		
			qry.setParameter(++index, companyCode);
			qry.setParameter(++index, customerCode);
			
			String stockHolderCode = qry.getSingleResult(getStringMapper("STKHLDCOD"));
			log.log(Log.INFO, "StockHolder for Customer", stockHolderCode);
		return stockHolderCode;
}
		/**
		 *@author A-3184
		 * @param rangeVO
		 * @return Collection<RangeVO>
		 * @throws SystemException
		 * @throws PersistenceException
		 */

		public Collection<RangeVO> findUsedRangesInHis(
				RangeVO rangeVO,String status) throws SystemException,
				PersistenceException {
			log.entering("StockControl SqlDAO", "findUsedRangesForMerge");    

			Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDUSEDRANGESINHIS);
			Collection<RangeVO> rangeVOs=new ArrayList<RangeVO>();
			log.log(Log.FINE,
					"----In findUsedRangesForMerge---------------RangeVO",
					rangeVO);
			int index = 0;

			query.setParameter(++index, rangeVO.getCompanyCode()); 
			query.setParameter(++index, rangeVO.getAirlineIdentifier());
			query.setParameter(++index, rangeVO.getDocumentType());
			query.setParameter(++index, rangeVO.getDocumentSubType());
			//Commented to fetch used history from TXNHIS table,with irrespective of stockhoder.
			//query.setParameter(++index, rangeVO.getStockHolderCode());
			query.setParameter(++index, status);
			query.setParameter(++index, rangeVO.getAsciiStartRange());
			query.setParameter(++index, rangeVO.getAsciiEndRange());

			rangeVOs=query.getResultList(new FindUsedRangesInHisMapper());
			log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", query);
			log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", rangeVOs);
			return rangeVOs;



		}		
		
		/**
		 *@author A-3184
		 * @param rangeVO
		 * @return Collection<RangeVO>
		 * @throws SystemException
		 * @throws PersistenceException
		*/
			private class FindUsedRangesInHisMapper implements Mapper<RangeVO> {
			/**
			 * Method for mapping StockRangeHistory
			 *
			 * @param resultSet
			 * @throws SQLException
			 * @return StockRangeHistoryVO
			 */
			public RangeVO map(ResultSet resultSet) throws SQLException {

				RangeVO rangeVO=new RangeVO();

				rangeVO.setCompanyCode(resultSet.getString("CMPCOD"));
				rangeVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
				rangeVO.setSequenceNumber(resultSet.getString("HISSEQNUM"));
				rangeVO.setDocumentType(resultSet.getString("DOCTYP"));
				rangeVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
				rangeVO.setStockHolderCode(resultSet.getString("FRMSTKHLDCOD"));
				rangeVO.setAsciiStartRange(resultSet.getLong("ASCSTARNG"));
				rangeVO.setAsciiEndRange(resultSet.getLong("ASCENDRNG"));
				rangeVO.setStartRange(resultSet.getString("STARNG"));
				rangeVO.setEndRange(resultSet.getString("ENDRNG"));
				if("N".equalsIgnoreCase(resultSet.getString("RNGTYP"))){
					rangeVO.setManual(false);
				} else{
					rangeVO.setManual(true);
				}
				log.log(Log.INFO, "$$$$mapper  >>>>>>>>>>", rangeVO);
				return rangeVO;

				}
			}		
		
			/**
			 *@author A-3184
			 * @param rangeFilterVO
			 * @return Collection<RangeVO>
			 * @throws SystemException
			 * @throws PersistenceException
			 */
		    public Collection<RangeVO> findRangesForHQ(RangeFilterVO rangeFilterVO)
			throws SystemException, PersistenceException {

			log.entering("StockControlDefaultsSqlDAO", "-------findRangesforHQ-----");
			String baseQry = getQueryManager().getNamedNativeQueryString(
				STOCKCONTROL_DEFAULTS_FIND_RANGESFORHQ);
			log.log(Log.FINE, "-----base query-----------", baseQry);
			Query query = new RangeFilterQuery(rangeFilterVO, baseQry);
			log.log(Log.FINE, "-----final query-----------", query);
			query.setSensitivity(true);
			Collection<RangeVO> rangeVOs = query.getResultList(new RangeMapperForHQ());

			log.log(Log.FINE, "\n****ranges from query*****", rangeVOs);
			long difference = 0;
			long sum = 0;
			long stRange = findLong(rangeFilterVO.getStartRange());
			for (RangeVO rangeVO : rangeVOs) {
			if (stRange > findLong(rangeVO.getStartRange())) {
				rangeVO.setStartRange(rangeFilterVO.getStartRange());
				rangeVO.setNumberOfDocuments(difference(
						rangeVO.getStartRange(), rangeVO.getEndRange()));
			}
			if (rangeFilterVO.getEndRange() != null
					&& rangeFilterVO.getEndRange().trim().length() != 0) {
				long edRange = findLong(rangeFilterVO.getEndRange());
				if (edRange < findLong(rangeVO.getEndRange())) {
					rangeVO.setEndRange(rangeFilterVO.getEndRange());
					rangeVO.setNumberOfDocuments(difference(rangeVO
							.getStartRange(), rangeVO.getEndRange()));
				}
			}
			log.log(Log.FINE, "\n****rangeVO*****", rangeVO);
			}
			if (rangeFilterVO.getNumberOfDocuments() != null
				&& rangeFilterVO.getNumberOfDocuments().trim().length() != 0) {
			long noOfDocs = findLong(rangeFilterVO.getNumberOfDocuments());
			Collection<RangeVO> newRangeVOs = new ArrayList<RangeVO>();
			for (RangeVO rangeVO : rangeVOs) {
				if(sum==noOfDocs){
					break;
				} 
				sum += rangeVO.getNumberOfDocuments();
				if (sum > noOfDocs) {
					log.log(Log.FINE, "Inside sum > noOfDocs ");
					difference = sum - noOfDocs;
					log.log(Log.FINE, "sum----> ", sum);
					log.log(Log.FINE, "difference----> ", difference);
					// long endRange= findLong(rangeVO.getEndRange()) -
					// difference;
					// rangeVO.setEndRange(Long.toString(endRange));
					rangeVO.setEndRange(subtract(rangeVO.getEndRange(),
							difference));
					rangeVO.setNumberOfDocuments(difference(rangeVO
							.getStartRange(), rangeVO.getEndRange()));
					newRangeVOs.add(rangeVO);
					break;

				}

				newRangeVOs.add(rangeVO);
			}// for loop
			rangeVOs = newRangeVOs;
			}
			log.log(Log.FINE, "Range VOs are --->", rangeVOs);
			log.exiting("StockControlDefaultsSqlDAO", "---------RangesforHQ---");
			return rangeVOs;

			}
		    
		    /**
			* This mapper is used for findRanges()
			*
			* @author A-3184
			*
			*/
			private class RangeMapperForHQ implements Mapper<RangeVO> {
			/**
			* This method is used to map the result of query to RangeVO
			*
			* @param resultSet
			* @throws SQLException
			* @return RangeVO
			*/
			public RangeVO map(ResultSet resultSet) throws SQLException {
			RangeVO rangeVo = new RangeVO();
			rangeVo.setStartRange(resultSet.getString("STARNG"));
			rangeVo.setEndRange(resultSet.getString("ENDRNG"));
			rangeVo.setNumberOfDocuments(resultSet.getLong("DOCNUM"));
			rangeVo.setCompanyCode(resultSet.getString("CMPCOD"));
			rangeVo.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
			rangeVo.setStockHolderCode(resultSet.getString("STKHLDCOD"));
			rangeVo.setDocumentType(resultSet.getString("DOCTYP"));
			rangeVo.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			rangeVo.setSequenceNumber(resultSet.getString("RNGSEQNUM"));
			if("Y".equalsIgnoreCase(resultSet.getString("MNLFLG"))){
				rangeVo.setManual(true);
			} else{
				rangeVo.setManual(false);
			}

			return rangeVo;
			}
			}		    
			/**
			 *@author A-3184
			 * @param rangeVO
			 * @return Collection<RangeVO>
			 * @throws SystemException
			 * @throws PersistenceException
			 */

			public Collection<RangeVO> checkForUsedStockInHis(
					BlacklistStockVO blacklistStockVO) throws SystemException,
					PersistenceException {
				log.entering("StockControl SqlDAO", "checkForUsedStockInHis");

				Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_CHECKUSEDRANGESINHIS);
				Collection<RangeVO> rangeVOs=new ArrayList<RangeVO>();
				log.log(Log.FINE,
						"----In checkForUsedStockInHis---------------RangeVO",
						blacklistStockVO);
				int index = 0;

				query.setParameter(++index, blacklistStockVO.getCompanyCode());
				query.setParameter(++index, blacklistStockVO.getAirlineIdentifier());
				query.setParameter(++index, blacklistStockVO.getDocumentType());
				query.setParameter(++index, blacklistStockVO.getDocumentSubType());
				query.setParameter(++index, findLong(blacklistStockVO.getRangeFrom()));
				query.setParameter(++index, findLong(blacklistStockVO.getRangeTo()));
				query.setParameter(++index, findLong(blacklistStockVO.getRangeFrom()));
				query.setParameter(++index, findLong(blacklistStockVO.getRangeTo()));

				rangeVOs=query.getResultList(new CheckUsedRangesInHisMapper());
				log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", query);
				log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", rangeVOs);
				return rangeVOs;



			}	    
			/**
			 *@author A-3184
			 * @param rangeVO
			 * @return Collection<RangeVO>
			 * @throws SystemException
			 * @throws PersistenceException
			*/
				private class CheckUsedRangesInHisMapper implements Mapper<RangeVO> {
				/**
				 * Method for mapping StockRangeHistory
				 *
				 * @param resultSet
				 * @throws SQLException
				 * @return StockRangeHistoryVO
				 */
				public RangeVO map(ResultSet resultSet) throws SQLException {

					RangeVO rangeVO=new RangeVO();

					rangeVO.setCompanyCode(resultSet.getString("CMPCOD"));
					rangeVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
					rangeVO.setSequenceNumber(resultSet.getString("HISSEQNUM"));
					rangeVO.setDocumentType(resultSet.getString("DOCTYP"));
					rangeVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
					rangeVO.setStockHolderCode(resultSet.getString("FRMSTKHLDCOD"));
					rangeVO.setAsciiStartRange(resultSet.getLong("ASCSTARNG"));
					rangeVO.setAsciiEndRange(resultSet.getLong("ASCENDRNG"));
					rangeVO.setStartRange(resultSet.getString("STARNG"));
					rangeVO.setEndRange(resultSet.getString("ENDRNG"));
					if("N".equalsIgnoreCase(resultSet.getString("RNGTYP"))){
						rangeVO.setManual(false); 
					} else{
						rangeVO.setManual(true);
					}
					log.log(Log.INFO, "$$$$mapper  >>>>>>>>>>", rangeVO);
					return rangeVO;

					}
				}
				/**
				 *@author A-3184
				 * @param rangeVO
				 * @return Collection<RangeVO>
				 * @throws SystemException
				 * @throws PersistenceException
				 */

				public Collection<RangeVO> checkForUsedStockInUTLHis(
						BlacklistStockVO blacklistStockVO) throws SystemException,
						PersistenceException {
					log.entering("StockControl SqlDAO", "checkForUsedStockInUTLHis");

					Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_CHECKUSEDRANGESINUTLHIS);
					Collection<RangeVO> rangeVOs=new ArrayList<RangeVO>();
					log
							.log(
									Log.FINE,
									"----In checkUsedRangesInUTLHis---------------RangeVO",
									blacklistStockVO);
					int index = 0;

					query.setParameter(++index, blacklistStockVO.getCompanyCode());
					query.setParameter(++index, blacklistStockVO.getAirlineIdentifier());
					query.setParameter(++index, blacklistStockVO.getDocumentType());
					query.setParameter(++index, blacklistStockVO.getDocumentSubType());
					query.setParameter(++index, findLong(blacklistStockVO.getRangeFrom()));
					query.setParameter(++index, findLong(blacklistStockVO.getRangeTo()));
					query.setParameter(++index, findLong(blacklistStockVO.getRangeFrom()));
					query.setParameter(++index, findLong(blacklistStockVO.getRangeTo()));

					rangeVOs=query.getResultList(new CheckUsedRangesInUTLHisMapper());
					log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", query);
					log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", rangeVOs);
					return rangeVOs;



				}	
				/**
				 *@author A-3184
				 * @param rangeVO
				 * @return Collection<RangeVO>
				 * @throws SystemException
				 * @throws PersistenceException
				*/
					private class CheckUsedRangesInUTLHisMapper implements Mapper<RangeVO> {
					/**
					 * Method for mapping StockRangeHistory
					 *
					 * @param resultSet
					 * @throws SQLException
					 * @return StockRangeHistoryVO
					 */
					public RangeVO map(ResultSet resultSet) throws SQLException {

						RangeVO rangeVO=new RangeVO();

						rangeVO.setCompanyCode(resultSet.getString("CMPCOD"));
						rangeVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
						rangeVO.setSequenceNumber(resultSet.getString("HISSEQNUM"));
						rangeVO.setDocumentType(resultSet.getString("DOCTYP"));
						rangeVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
						rangeVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
						rangeVO.setAsciiStartRange(resultSet.getLong("ASCSTARNG"));
						rangeVO.setAsciiEndRange(resultSet.getLong("ASCENDRNG"));
						rangeVO.setStartRange(resultSet.getString("STARNG"));
						rangeVO.setEndRange(resultSet.getString("ENDRNG"));
						if("N".equalsIgnoreCase(resultSet.getString("RNGTYP"))){
							rangeVO.setManual(false);
						} else{
							rangeVO.setManual(true);
						}
						log.log(Log.INFO, "$$$$mapper  >>>>>>>>>>", rangeVO);
						return rangeVO;

						}
					}
					/**
					 * @author A-3184
					 * @param companyCode
					 * @param airlineId
					 * @param documentType
					 * @return Collection<String>
					 * @throws SystemException
					 */
					public Collection<String> findUsedStocksFromUtilisation(String companyCode,int airlineId,String documentType,
							String subType) throws SystemException,
							PersistenceException {
						Query query = getQueryManager().createNamedNativeQuery(
								STOCKCONTROL_DEFAULTS_FINDUSEDSTOCKSFROMUTILISATION);
						int index = 0;
						query.setParameter(++index,companyCode);
						query.setParameter(++index, airlineId);
						query.setParameter(++index, documentType);
						query.setParameter(++index,subType);

						return query.getResultList(new UsedStocksFromUtilisationMapper());
					}

					/**
					 * @author A-1883 Mapper class for getting used stocks from STKRNGUTL
					 */
					class UsedStocksFromUtilisationMapper implements Mapper<String> {
						/**
						 * This method is used to map the result of query
						 *
						 * @param resultSet
						 * @throws SQLException
						 * @return String
						 */
						public String map(ResultSet resultSet) throws SQLException {
							return resultSet.getString("MSTDOCNUM");
						}
					}			
					/**
					 * @param companyCode
					 * @param airlineId
					 * @param documentType
					 * @param documentNumber
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					public StockRangeVO findDocSubTypeFromUtl(String companyCode,
							String documentType, long documentNumber)
						throws SystemException, PersistenceException{
						log.log(Log.FINE,"ENTRY");
						log.log(Log.FINE, "companyCode : ", companyCode);
						log.log(Log.FINE, "documentType : ", documentType);
						log.log(Log.FINE, "documentNumber : ", documentNumber);
						int index = 0;

						Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDSUBTYPEFROMUTL);
						query.setParameter(++index, companyCode);
						query.setParameter(++index, documentType);
						query.setParameter(++index, documentNumber);

						return query.getSingleResult(new Mapper<StockRangeVO>(){

							public StockRangeVO map(ResultSet rs) throws SQLException {
								// To be reviewed Auto-generated method stub\
								StockRangeVO stockRangeVO = new StockRangeVO();
								stockRangeVO.setDocumentSubType(rs.getString("DOCSUBTYP"));
								stockRangeVO.setStockHolderCode(rs.getString("STKHLDCOD"));
								return stockRangeVO;
							}

						});


				}			
					/**
					 * Method for finding ranges from stkrngutl
					 *
					 * @param companyCode
					 * @param airlineId
					 * @param docType
					 * @param docSubType
					 * @param startRange
					 * @param endRange
					 * @return
					 * @throws SystemException
					 */
					public Collection<RangeVO> checkRangesInUtl(String companyCode,
							int airlineId, String docType, String docSubType,
							String startRange, String endRange) throws SystemException {
						Query qry = getQueryManager().createNamedNativeQuery(
								STOCKCONTROL_DEFAULTS_CHECKRANGESINSTKRNGUTL);
						int index = 0;
						qry.setParameter(++index, companyCode);
						qry.setParameter(++index, airlineId);
						qry.setParameter(++index, docType);
						qry.setParameter(++index, docSubType);
						qry.setParameter(++index, findLong(startRange));
						qry.setParameter(++index, findLong(endRange));
						qry.setParameter(++index, findLong(startRange));
						qry.setParameter(++index, findLong(endRange));
						return qry.getResultList(new checkRangesInUtlMapper());

					}

					/**
					 * Class for checkRangesInUtl mapping
					 *
					 * @author A-3184
					 *
					 */
					class checkRangesInUtlMapper implements Mapper<RangeVO> {
						/**
						 * Method for mapping result set to stock vo
						 *
						 * @param resultSet
						 * @throws SQLException
						 * @return StockVO
						 */
						public RangeVO map(ResultSet resultSet) throws SQLException {
							RangeVO rangeVO = new RangeVO();
							rangeVO.setCompanyCode(resultSet.getString("CMPCOD"));
							rangeVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
							rangeVO.setStartRange(resultSet.getString("MSTDOCNUM"));
							rangeVO.setEndRange(rangeVO.getStartRange());
							return rangeVO;
						}
					}
					
					/**
					 * Method for checking Document Status
					 *
					 * @param companyCode
					 * @param docType
					 * @param docSubType
					 * @return
					 * @throws SystemException
					 */
					public String checkDocumentStatus(String companyCode,
							 String docType, String docSubType) throws SystemException {
						Query qry = getQueryManager().createNamedNativeQuery(
								STOCKCONTROL_DEFAULTS_CHECKDOCUMENTSTATUS);
						int index = 0;
						qry.setParameter(++index, companyCode);
						qry.setParameter(++index, docType);
						qry.setParameter(++index, docSubType);

						return qry.getSingleResult(new CheckDocumentStatusMapper());
					}

					/**
					 * Class for document status checking
					 *
					 * @author A-3184
					 *
					 */
					class CheckDocumentStatusMapper implements Mapper<String> {
						/**
						 * Method for mapping result set to string
						 *
						 * @param resultSet
						 * @throws SQLException
						 * @return String
						 */
						public String map(ResultSet resultSet) throws SQLException {
							return resultSet.getString("DOCSTA");
						}
					}				
					/**
					 * @author a-1885
					 * logClearingThreshold added under 
					 * BUG_ICRD-23686_AiynaSuresh_28Mar13
					 */
					public void deleteStockRangeUtilisation(String companyCode,int airlineId,GMTDate actualDate,
							int logClearingThreshold)
					throws SystemException,PersistenceException{
						log.entering("StockSQLDAO", "deleteStockRangeUtilisation");
						log.log(Log.FINE, "--companyCode-->", companyCode);
						log.log(Log.FINE, "-companyCode-->", airlineId);
						String result = null;
						Timestamp actualSqlDate = null;
						Procedure deleteProcedure = getQueryManager().createNamedNativeProcedure(
								STOCKCONTROL_DEFAULTS_DELETESTOCKRANGEUTILISATION);
						deleteProcedure.setParameter(1,companyCode);
						deleteProcedure.setParameter(2,airlineId);
						actualSqlDate = actualDate.toSqlTimeStamp();
						actualSqlDate.setNanos(0);
						deleteProcedure.setParameter(3,actualSqlDate);
						// Added under BUG_ICRD-23686_AiynaSuresh_28Mar13
						deleteProcedure.setParameter(4,logClearingThreshold);
						deleteProcedure.setOutParameter(5,SqlType.STRING);
						deleteProcedure.execute();
						deleteProcedure.setSensitivity(true);
				    	log.log(Log.FINE,"---DeltaProc in SQLDAO---");

					}			
					/**
					 * @param companyCode
					 * @param airlineId 
					 * @param documentType
					 * @param documentNumber
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					public DocumentFilterVO findSubTypeForReopenDocument(DocumentFilterVO filtervo, long documentNumber)
						throws SystemException, PersistenceException{
						log.log(Log.FINE,"ENTRY findSubTypeForReopenDocument");

						log.log(Log.FINE, "companyCode : ", filtervo.getCompanyCode());
						log.log(Log.FINE, "airlineId : ", filtervo.getAirlineIdentifier());
						log.log(Log.FINE, "documentType : ", filtervo.getDocumentType());
						log.log(Log.FINE, "documentNumber : ", documentNumber);
						int index = 0;

						Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDSUBTYPEFORREOPENDOCUMENT);
						query.setParameter(++index,filtervo.getCompanyCode());
						query.setParameter(++index,filtervo.getAirlineIdentifier());
						query.setParameter(++index,filtervo.getDocumentType());
						query.setParameter(++index, documentNumber);


						return query.getSingleResult(new Mapper<DocumentFilterVO>(){

							public DocumentFilterVO map(ResultSet rs) throws SQLException {
								// To be reviewed Auto-generated method stub\
								DocumentFilterVO documentFilterVO = new DocumentFilterVO();
								documentFilterVO.setDocumentSubType(rs.getString("DOCSUBTYP"));
								documentFilterVO.setStockOwner(rs.getString("STKHLDCOD"));
								return documentFilterVO;
							}

						});


					}				
					/**
					 *@author A-3184
					 * @param rangeVO
					 * @return Collection<RangeVO>
					 * @throws SystemException
					 * @throws PersistenceException
					 */

					public String returnFirstLevelStockHolder(
							BlacklistStockVO blacklistStockVO) throws SystemException,
							PersistenceException {
						log.entering("StockControl SqlDAO", "returnFirstLevelStockHolder");

						Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_RETURNFIRSTLEVELSTOCKHOLDER);
						String firstLevelStockHolder=null;
						log
								.log(
										Log.FINE,
										"----In returnFirstLevelStockHolder-----blacklistStockVO",
										blacklistStockVO);
						int index = 0;

						query.setParameter(++index, blacklistStockVO.getCompanyCode());
						query.setParameter(++index, blacklistStockVO.getAirlineIdentifier());
						query.setParameter(++index, blacklistStockVO.getDocumentType());
						query.setParameter(++index, blacklistStockVO.getDocumentSubType());
						query.setParameter(++index, findLong(blacklistStockVO.getRangeFrom()));
						query.setParameter(++index, findLong(blacklistStockVO.getRangeTo()));
						query.setParameter(++index, findLong(blacklistStockVO.getRangeFrom()));
						query.setParameter(++index, findLong(blacklistStockVO.getRangeTo()));

						firstLevelStockHolder=query.getSingleResult(getStringMapper("TOSTKHLDCOD"));
						log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>",
								firstLevelStockHolder);
						return firstLevelStockHolder;

					}	
					/**
					 * Method to find stock is only used or executed..
					 * @author A-3184
					 * @param stockAllocationVO
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					public boolean findOnlyUsedStockFromUtl(StockAllocationVO stockAllocationVO)
					throws SystemException, PersistenceException {
						log.entering("StockControl Sql DAO", "findOnlyUsedStockFromUtl");
						log
								.log(
										Log.FINE,
										"\n\n---vaules to findOnlyUsedStockFromUtl----------",
										stockAllocationVO);
							int index = 0;
							String startRange=null;
							String endRange=null;
							Query query = getQueryManager().createNamedNativeQuery(
									STOCKCONTROL_DEFAULTS_FINDONLYUSEDSTOCKFROMUTL);
							for(RangeVO rangeVO:stockAllocationVO.getRanges()){
								startRange=rangeVO.getStartRange();
								endRange=rangeVO.getEndRange();
							}
							log
									.log(
											Log.FINE,
											"\n\n---findOnlyUsedStockFromUtl------------",
											query);
							query.setParameter(++index,stockAllocationVO.getCompanyCode());
							query.setParameter(++index,stockAllocationVO.getAirlineIdentifier());
							query.setParameter(++index,stockAllocationVO.getDocumentType());
							query.setParameter(++index,stockAllocationVO.getDocumentSubType());
							query.setParameter(++index,stockAllocationVO.getStockHolderCode());
							query.setParameter(++index,startRange);
							int count = query.getSingleResult(getIntMapper("COUNT"));
							log.exiting("StockControl Sql DAO", "findOnlyUsedStockFromUtl");
							return (count>=1 ? true : false);
							}				
					
					/**
					 *@author A-3184
					 * @param stockRangeHistoryVO
					 * @return Collection<StockRangeHistoryVO>
					 * @throws SystemException
					 * @throws PersistenceException
					 */

					public Collection<StockRangeHistoryVO>findStockRangeHistory(StockRangeFilterVO stockRangeFilterVO)
									throws SystemException,PersistenceException {
						//Collection<StockRangeHistoryVO> stockRangeHistoryVO = new ArrayList<StockRangeHistoryVO>();
						log.entering("StockControl SqlDAO", "findStockRangeHistory");
						log.log(Log.FINE,
								"\n\n----check for awbnum-------------",
								stockRangeFilterVO.getAwb());
						boolean isHistory = stockRangeFilterVO.isHistory();
					//	String awbnum = stockRangeFilterVO.getStartRange();
						String awbnum = stockRangeFilterVO.getAwb();
									if (!isHistory){
										if (awbnum!=null && awbnum.trim().length()>0){
											return findAwbStockDetails(stockRangeFilterVO);
										}else {
											return findStockUtilisationHistory(stockRangeFilterVO);

										}
									}
									else{                         
										return findStockHistory(stockRangeFilterVO);
									}

					}
					
					/**
					 * @author A-3184
					 * @param stockRangeFilterVO
					 * @return Collection<StockRangeHistoryVO>
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					private Collection<StockRangeHistoryVO> findAwbStockDetails(
							StockRangeFilterVO stockRangeFilterVO) throws SystemException,
							PersistenceException {
						log.entering("StockControl SqlDAO", "findAwbStockDetails");
						Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDAWBSTOCKDETAILS);
						int index = 0;

							query.setParameter(++index, stockRangeFilterVO.getCompanyCode());

							query.setParameter(++index,stockRangeFilterVO.getAirlineIdentifier());

							query.setParameter(++index, Integer.parseInt(stockRangeFilterVO.getAwb()));

							query.setParameter(++index, Integer.parseInt(stockRangeFilterVO.getAwb()));

							query.setParameter(++index, stockRangeFilterVO.getCompanyCode());

							query.setParameter(++index,stockRangeFilterVO.getAirlineIdentifier());

							query.setParameter(++index, Integer.parseInt(stockRangeFilterVO.getAwb()));

							query.setParameter(++index, Integer.parseInt(stockRangeFilterVO.getAwb()));
							addPrivilegeConditions(stockRangeFilterVO, query, index);
							
							query.append(" ORDER BY Z.TXNDAT");

					log.log(Log.FINE, "\n\n------------------", query.toString());
					Collection<StockRangeHistoryVO> stockRangeHistoryVOs=
						query.getResultList(new AwbStockDetailsMapper());
					if(stockRangeHistoryVOs!=null && stockRangeHistoryVOs.size()>0){
						for(StockRangeHistoryVO historyVo:stockRangeHistoryVOs){

							int checkDigit=historyVo.getCheckDigit();
							if(checkDigit!=0){
							int appendStartRange = Integer.parseInt(stockRangeFilterVO.getAwb())%checkDigit;

							StringBuilder awbRange= new StringBuilder(stockRangeFilterVO.getAwb()).append(appendStartRange);

							historyVo.setAwbRange(awbRange.toString());
							}
							//Added to convert GMT time to login station local time.
							/* Modified By T-1925 for Bug ICRD-6288*/
					//		if(!StockAllocationVO.MODE_USED.equalsIgnoreCase(historyVo.getStatus())){
								//GMTDate txndatGMT = new GMTDate(historyVo.getTransactionDate(),true);
								/*Timestamp txnTimestamp =new Timestamp(historyVo.getTransactionDate().getTimeInMillis());
								LocalDate txndatLocal  =  new LocalDate(stockRangeFilterVO.getStation(),Location.ARP,txnTimestamp);
								historyVo.setTransDateStr(txndatLocal.toTimeStampFormat());
								historyVo.setTransactionDate(txndatLocal);*/
								
			log
									.log(
											Log.FINE,
											"\n\n----AwbRange with 8 digit--------------",
											historyVo.getAwbRange());
						}
					}



					log.exiting("StockControl SqlDAO", "findAwbStockDetails");
					log.log(Log.INFO, "$$$$ From SqlDAO ");
					return stockRangeHistoryVOs;
				}					
					
					/**
					 * @author A-3184
					 * @param
					 * @return stockRangeHistoryVO
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					private class AwbStockDetailsMapper implements Mapper<StockRangeHistoryVO> {
						/**
						 * Method for mapping StockRangeHistory
						 *
						 * @param resultSet
						 * @throws SQLException
						 * @return StockRangeHistoryVO
						 */
						public StockRangeHistoryVO map(ResultSet resultSet) throws SQLException {
							log.log(Log.INFO, "$$$$Inside mapper >>>>>>>>>>" );
							//log.log(Log.FINE,"\n\n----resultset--------------"+resultSet);
							StockRangeHistoryVO stockRangeHistoryVO = new StockRangeHistoryVO();
							stockRangeHistoryVO.setCompanyCode(resultSet.getString("CMPCOD"));

							stockRangeHistoryVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
							stockRangeHistoryVO.setToStockHolderCode(resultSet.getString("TOSTKHLDCOD"));
							stockRangeHistoryVO.setFromStockHolderCode(resultSet.getString("FRMSTKHLDCOD"));
							stockRangeHistoryVO.setCheckDigit(resultSet.getInt("AWBCHKDGT"));
							stockRangeHistoryVO.setStatus(resultSet.getString(STATUS));
							
							// Added by A-5153 for BUG_ICRD-109545
							LogonAttributes logonAttributes = null;
							try {
								logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
							} catch (SystemException e) {
								log.log(Log.SEVERE, "SystemException caught......");
								//e.printStackTrace();
							}
							if(resultSet.getDate("TXNDAT") != null) {
								GMTDate txndatGMT = new GMTDate(resultSet.getTimestamp("TXNDAT"));
								LocalDate transactionDate = new LocalDate(txndatGMT,
										logonAttributes.getStationCode(), Location.STN);
								stockRangeHistoryVO.setTransactionDate(transactionDate);
								stockRangeHistoryVO.setTransDateStr(transactionDate.toTimeStampFormat());
							}
							
							/*if(resultSet.getDate("TXNDAT") != null) {
							stockRangeHistoryVO.setTransactionDate(new LocalDate(LocalDate.NO_STATION,
									Location.NONE,resultSet
									.getTimestamp("TXNDAT")));
							LocalDate transactionDate = new LocalDate(LocalDate.NO_STATION,
									Location.NONE,resultSet.getTimestamp("TXNDAT"));
							if(StockAllocationVO.MODE_USED.equalsIgnoreCase(resultSet.getString("STATUS"))){
								stockRangeHistoryVO.setTransDateStr(transactionDate.toDisplayDateOnlyFormat());
							}else{
							stockRangeHistoryVO.setTransDateStr(transactionDate.toDisplayFormat());
							}
							}*/
							stockRangeHistoryVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
							return stockRangeHistoryVO;
						}

				}
				
					/**
					 * @author A-3184
					 * @param stockRangeFilterVO
					 * @return Collection<StockRangeHistoryVO>
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					private Collection<StockRangeHistoryVO> findStockUtilisationHistory(
							StockRangeFilterVO stockRangeFilterVO) throws SystemException,
							PersistenceException {
						log.entering("StockControl SqlDAO", "findStockUtilisationHistory");
						Query query=null;
						 String  status = stockRangeFilterVO.getStatus();
						 LocalDate startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
					    LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
					    String Flag=stockRangeFilterVO.getRangeType();
				    	if(StockAllocationVO.MODE_NEUTRAL.equals(Flag)){
				    		Flag = "N";
				    	}else{
				    		Flag = "Y";
				    	}
							if((StockAllocationVO.MODE_UNUSED).equalsIgnoreCase(status)){
								 query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDSTOCKUTILISATIONHISTORY);
								int index = 0;
								query.setParameter(++index, stockRangeFilterVO.getCompanyCode());
								log.log(Log.FINE,"\n\n----------INSIDE F---");

								if (stockRangeFilterVO.getAirlineIdentifier() != 0) {
									query.append(" AND RNG.ARLIDR = ?");
									query.setParameter(++index, stockRangeFilterVO
											.getAirlineIdentifier());

								}

								if ((stockRangeFilterVO.getEndRange()==null || stockRangeFilterVO.getEndRange().trim().length()==0) && (stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)) {
									query.append(" AND  (RNG.ASCSTARNG >= ? OR (? between RNG.ASCSTARNG and  RNG.ASCENDRNG))");
									query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
									query.setParameter(++index,  findLong(stockRangeFilterVO.getStartRange()));
								}
								if ((stockRangeFilterVO.getStartRange()==null || stockRangeFilterVO.getStartRange().trim().length()==0) && (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)) {
									query.append(" AND  (RNG.ASCENDRNG <= ? OR (? between RNG.ASCSTARNG and  RNG.ASCENDRNG))");
									query.setParameter(++index,  findLong(stockRangeFilterVO.getEndRange()));
									query.setParameter(++index,  findLong(stockRangeFilterVO.getEndRange()));
								}
								if((stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)){
									query.append("  AND ( ( ? BETWEEN RNG.ASCSTARNG AND RNG.ASCENDRNG) OR ( ? BETWEEN RNG.ASCSTARNG AND RNG.ASCENDRNG)OR ( RNG.ASCSTARNG >= ? AND RNG.ASCENDRNG <= ? ) )");
									query.setParameter(++index,  findLong(stockRangeFilterVO.getStartRange()));
									query.setParameter(++index,  findLong(stockRangeFilterVO.getEndRange()));
									query.setParameter(++index,  findLong(stockRangeFilterVO.getStartRange()));
									query.setParameter(++index,  findLong(stockRangeFilterVO.getEndRange()));
								}
								/*if (stockRangeFilterVO.getRangeType() != null && stockRangeFilterVO.getRangeType().trim().length()>0) {
									query.append(" AND RNG.MNLFLG= ?");
									query.setParameter(++index,Flag);
								}*/
								if (stockRangeFilterVO.getFromStockHolderCode() != null && stockRangeFilterVO.getFromStockHolderCode().trim().length()>0) {
									query.append("AND RNG.STKHLDCOD=?");
								    query.setParameter(++index, stockRangeFilterVO.getFromStockHolderCode());
								}
								if (stockRangeFilterVO.getDocumentType() != null && stockRangeFilterVO.getDocumentType().trim().length()>0) {
									query.append(" AND RNG.DOCTYP= ?");
									query.setParameter(++index, stockRangeFilterVO.getDocumentType());
								}
								if (stockRangeFilterVO.getDocumentSubType() != null && stockRangeFilterVO.getDocumentSubType().trim().length()>0) {
									query.append(" AND RNG.DOCSUBTYP= ?");
									query.setParameter(++index, stockRangeFilterVO.getDocumentSubType());
								}

								if (stockRangeFilterVO.getStartDate() != null && stockRangeFilterVO.getEndDate() !=null) {
									if(isOracleDataSource()) {
									query.append(" AND TRUNC(RNG.RNGACPDAT) BETWEEN ? AND ? ");
									}else {
										query.append(" AND date_trunc('day', rng.rngacpdat) BETWEEN ? AND ? ");
									}									
									query.setParameter(++index, stockRangeFilterVO
											.getStartDate());
									query.setParameter(++index, stockRangeFilterVO.getEndDate());

								}
								if (stockRangeFilterVO.getAccountNumber() != null && stockRangeFilterVO.getAccountNumber().trim().length()>0) {
									query.append("AND RNG.STKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");
									query.setParameter(++index, stockRangeFilterVO.getAccountNumber());
								}

										}

								if(("").equals(status)){
									query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDSTOCKUTILISATIONHISTORY);
									int index = 0;
									query.setParameter(++index, stockRangeFilterVO.getCompanyCode());

									if ((stockRangeFilterVO.getEndRange()==null || stockRangeFilterVO.getEndRange().trim().length()==0) && (stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)) {
										query.append(" AND  (RNG.ASCSTARNG >= ? OR (? between RNG.ASCSTARNG and  RNG.ASCENDRNG))");
										query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
									}
									if ((stockRangeFilterVO.getStartRange()==null || stockRangeFilterVO.getStartRange().trim().length()==0) && (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)) {
										query.append(" AND  (RNG.ASCENDRNG <= ? OR (? between RNG.ASCSTARNG and  RNG.ASCENDRNG))");
										query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
									}
									if((stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)){
										query.append("  AND ( ( ? BETWEEN RNG.ASCSTARNG AND RNG.ASCENDRNG) OR ( ? BETWEEN RNG.ASCSTARNG AND RNG.ASCENDRNG)OR ( RNG.ASCSTARNG >= ? AND RNG.ASCENDRNG <= ? ) )");
										query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
									}
									/*if (stockRangeFilterVO.getRangeType() != null && stockRangeFilterVO.getRangeType().trim().length()>0) {
										query.append(" AND RNG.MNLFLG= ?");
										query.setParameter(++index,Flag);
									}*/
									if (stockRangeFilterVO.getFromStockHolderCode() != null && stockRangeFilterVO.getFromStockHolderCode().trim().length()>0) {
										query.append("AND RNG.STKHLDCOD=?");
										query.setParameter(++index, stockRangeFilterVO.getFromStockHolderCode());
									}
									if (stockRangeFilterVO.getDocumentType() != null && stockRangeFilterVO.getDocumentType().trim().length()>0) {
										query.append(" AND RNG.DOCTYP= ?");
										query.setParameter(++index, stockRangeFilterVO.getDocumentType());
									}
									if (stockRangeFilterVO.getDocumentSubType() != null && stockRangeFilterVO.getDocumentSubType().trim().length()>0) {
										query.append(" AND RNG.DOCSUBTYP= ?");
										query.setParameter(++index, stockRangeFilterVO.getDocumentSubType());
									}
									if (stockRangeFilterVO.getStartDate() != null && stockRangeFilterVO.getEndDate() !=null) {
										if(isOracleDataSource()) {
										query.append(" AND TRUNC(RNG.RNGACPDAT) BETWEEN ? AND ? ");
										}else {
											query.append(" AND date_trunc('day', rng.rngacpdat) BETWEEN ? AND ? ");
										}
										query.setParameter(++index, stockRangeFilterVO
												.getStartDate());
										query.setParameter(++index, stockRangeFilterVO.getEndDate());

									}
									if (stockRangeFilterVO.getAccountNumber() != null && stockRangeFilterVO.getAccountNumber().trim().length()>0) {
										query.append("AND RNG.STKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");
										query.setParameter(++index, stockRangeFilterVO.getAccountNumber());
									}

									log.log(Log.FINE,"\n\n----------INSIDE UNION---");
									query.append("UNION ");

									//Added by A-5220 for ICRD-20959 starts
									query.append("SELECT DISTINCT UTLHIST.FRMSTKHLDCOD AS STKHLDCOD,UTLHIST.RNGTYP AS MNLFLG,UTLHIST.DOCTYP,UTLHIST.DOCSUBTYP,UTLHIST.STARNG,UTLHIST.ENDRNG,UTLHIST.NUMDOC AS DOCNUM,UTLHIST.TXNDAT AS STKDAT,COALESCE(UTLHIST.STATUS,'U') AS STATUS,ARLMST.AWBCHKDGT FROM STKRNGTXNHIS UTLHIST,SHRARLMST ARLMST WHERE ARLMST.CMPCOD = UTLHIST.CMPCOD AND ARLMST.ARLIDR=UTLHIST.ARLIDR AND UTLHIST.CMPCOD=? AND UTLHIST.ARLIDR=?");

									//query.append(" WHERE UTLHIST.CMPCOD=?"); 

									query.setParameter(++index, stockRangeFilterVO.getCompanyCode());

									//query.append(" AND ARLMST.ARLIDR=UTLHIST.ARLIDR");

									//query.append(" AND UTLHIST.ARLIDR=?");
									//Added by A-5220 for ICRD-20959 ends

									query.setParameter(++index, stockRangeFilterVO.getAirlineIdentifier());

									query.append(" AND UTLHIST.STATUS=?");

									query.setParameter(++index, StockAllocationVO.MODE_USED);

									if ((stockRangeFilterVO.getEndRange()==null || stockRangeFilterVO.getEndRange().trim().length()==0) && (stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)) {
										query.append(" AND  (UTLHIST.ASCSTARNG >= ? OR (? between UTLHIST.ASCSTARNG and  UTLHIST.ASCENDRNG))");
										query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
									}


									if ((stockRangeFilterVO.getStartRange()==null || stockRangeFilterVO.getStartRange().trim().length()==0) && (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)) {
										query.append(" AND  (UTLHIST.ASCENDRNG <= ? OR (? between UTLHIST.ASCSTARNG and  UTLHIST.ASCENDRNG))");
										query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
									}

									if((stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)){
										query.append("  AND ( ( ? BETWEEN UTLHIST.ASCSTARNG AND UTLHIST.ASCENDRNG) OR ( ? BETWEEN UTLHIST.ASCSTARNG AND UTLHIST.ASCENDRNG)OR ( UTLHIST.ASCSTARNG >= ? AND UTLHIST.ASCENDRNG <= ? ) )");
										query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
										query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
									}

									if (stockRangeFilterVO.getFromStockHolderCode() != null && stockRangeFilterVO.getFromStockHolderCode().trim().length()>0) {
										query.append(" AND UTLHIST.FRMSTKHLDCOD= ?");
										query.setParameter(++index, stockRangeFilterVO
												.getFromStockHolderCode());
									}

									/*if (stockRangeFilterVO.getRangeType() != null && stockRangeFilterVO.getRangeType().trim().length()>0) {
										query.append(" AND UTLHIST.RNGTYP= ?");
										query.setParameter(++index, stockRangeFilterVO.getRangeType());
									}*/

									if (stockRangeFilterVO.getStatus() != null && stockRangeFilterVO.getStatus().trim().length()>0) {
										query.append(" AND UTLHIST.STATUS= ?");
										query.setParameter(++index, stockRangeFilterVO.getStatus());
									}
									if (stockRangeFilterVO.getDocumentType() != null && stockRangeFilterVO.getDocumentType().trim().length()>0) {
										query.append(" AND UTLHIST.DOCTYP= ?");
										query.setParameter(++index, stockRangeFilterVO.getDocumentType());
									}
									if (stockRangeFilterVO.getDocumentSubType() != null && stockRangeFilterVO.getDocumentSubType().trim().length()>0) {
										query.append(" AND UTLHIST.DOCSUBTYP= ?");
										query.setParameter(++index, stockRangeFilterVO.getDocumentSubType());
									}

									log.log(Log.FINE,
											"\n\n----startdate--------------",
											stockRangeFilterVO.getStartDate());
									log.log(Log.FINE,
											"\n\n----enddate--------------",
											stockRangeFilterVO.getEndDate());
									if (stockRangeFilterVO.getStartDate() != null && stockRangeFilterVO.getEndDate() !=null) {

										String startDateStr = new StringBuilder().append(stockRangeFilterVO.getStartDate().toDisplayDateOnlyFormat())
										.append(" ").append("00:00").toString();
										String endDateStr = new StringBuilder().append(stockRangeFilterVO.getEndDate().toDisplayDateOnlyFormat())
										.append(" ").append("23:59").toString();
										startDate.setDateAndTime(startDateStr,true);
										endDate.setDateAndTime(endDateStr,true);
										query.append(" AND UTLHIST.TXNDAT >= ? ");
										query.append(" AND UTLHIST.TXNDAT <= ? ");
								    	query.setParameter(++index,startDate);
								    	query.setParameter(++index,endDate);

									}
									if (stockRangeFilterVO.getAccountNumber() != null && stockRangeFilterVO.getAccountNumber().trim().length()>0) {
										query.append("AND UTLHIST.FRMSTKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");
										query.setParameter(++index, stockRangeFilterVO.getAccountNumber());
									}

								}
									if ((StockAllocationVO.MODE_USED).equalsIgnoreCase(status)){

										query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDSTOCKUTILISATIONHISTORYUSED);
										int index = 0;
										query.setParameter(++index, stockRangeFilterVO.getCompanyCode());

										query.setParameter(++index, stockRangeFilterVO.getAirlineIdentifier());

										log.log(Log.FINE,"\n\n----------INSIDE U---");

										if ((stockRangeFilterVO.getEndRange()==null || stockRangeFilterVO.getEndRange().trim().length()==0) && (stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)) {
											query.append("  AND  (UTLHIST.ASCSTARNG >= ? OR (? between UTLHIST.ASCSTARNG and  UTLHIST.ASCENDRNG))");
											query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
											query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
										}


										if ((stockRangeFilterVO.getStartRange()==null || stockRangeFilterVO.getStartRange().trim().length()==0) && (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)) {
											query.append(" AND  (UTLHIST.ASCENDRNG <= ? OR (? between UTLHIST.ASCSTARNG and  UTLHIST.ASCENDRNG))");
											query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
											query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
										}

										if((stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)){
											query.append("  AND ( ( ? BETWEEN UTLHIST.ASCSTARNG AND UTLHIST.ASCENDRNG) OR ( ? BETWEEN UTLHIST.ASCSTARNG AND UTLHIST.ASCENDRNG)OR ( UTLHIST.ASCSTARNG >= ? AND UTLHIST.ASCENDRNG <= ? ) )");
											query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
											query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
											query.setParameter(++index, findLong(stockRangeFilterVO.getStartRange()));
											query.setParameter(++index, findLong(stockRangeFilterVO.getEndRange()));
										}

										if (stockRangeFilterVO.getFromStockHolderCode() != null && stockRangeFilterVO.getFromStockHolderCode().trim().length()>0) {
											query.append(" AND UTLHIST.FRMSTKHLDCOD= ?");
											query.setParameter(++index, stockRangeFilterVO
													.getFromStockHolderCode());
										}

										/*if (stockRangeFilterVO.getRangeType() != null && stockRangeFilterVO.getRangeType().trim().length()>0) {
											query.append(" AND UTLHIST.RNGTYP= ?");
											query.setParameter(++index, stockRangeFilterVO.getRangeType());
										}*/

										if (stockRangeFilterVO.getStatus() != null && stockRangeFilterVO.getStatus().trim().length()>0) {
											query.append(" AND UTLHIST.STATUS= ?");
											query.setParameter(++index, stockRangeFilterVO.getStatus());
										}
										if (stockRangeFilterVO.getDocumentType() != null && stockRangeFilterVO.getDocumentType().trim().length()>0) {
											query.append(" AND UTLHIST.DOCTYP= ?");
											query.setParameter(++index, stockRangeFilterVO.getDocumentType());
										}
										if (stockRangeFilterVO.getDocumentSubType() != null && stockRangeFilterVO.getDocumentSubType().trim().length()>0) {
											query.append(" AND UTLHIST.DOCSUBTYP= ?");
											query.setParameter(++index, stockRangeFilterVO.getDocumentSubType());
										}

										log
												.log(
														Log.FINE,
														"\n\n----startdate--------------",
														stockRangeFilterVO.getStartDate());
										log
												.log(
														Log.FINE,
														"\n\n----enddate--------------",
														stockRangeFilterVO.getEndDate());
										if (stockRangeFilterVO.getStartDate() != null && stockRangeFilterVO.getEndDate() !=null) {
											String startDateStr = new StringBuilder().append(stockRangeFilterVO.getStartDate().toDisplayDateOnlyFormat())
											.append(" ").append("00:00").toString();
											String endDateStr = new StringBuilder().append(stockRangeFilterVO.getEndDate().toDisplayDateOnlyFormat())
											.append(" ").append("23:59").toString();
											startDate.setDateAndTime(startDateStr,true);
											endDate.setDateAndTime(endDateStr,true);
											query.append(" AND UTLHIST.TXNDAT >= ? ");
											query.append(" AND UTLHIST.TXNDAT <= ? ");
									    	query.setParameter(++index,startDate);
									    	query.setParameter(++index,endDate);

										}
										if (stockRangeFilterVO.getAccountNumber() != null && stockRangeFilterVO.getAccountNumber().trim().length()>0) {
											query.append("AND UTLHIST.FRMSTKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");
											query.setParameter(++index, stockRangeFilterVO.getAccountNumber());
										}


								}

									query.append(" ORDER BY STKHLDCOD,MNLFLG,STATUS");

									log
											.log(
													Log.FINE,
													"\n\n------------------print query: ",
													query);
									Collection<StockRangeHistoryVO> stockRangeHistoryVOs=
									query.getResultList(new StockRangeUtilisationMapper());




						log.exiting("StockControl SqlDAO", "findStockRangeHistory");



						return stockRangeHistoryVOs;

					}					
					/**
					 * @author A-3184
					 * @param
					 * @return Collection<StockRangeHistoryVO>
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					private class StockRangeUtilisationMapper implements Mapper<StockRangeHistoryVO> {
						/**
						 * Method for mapping StockRangeHistory
						 *
						 * @param resultSet
						 * @throws SQLException
						 * @return StockRangeHistoryVO
						 */
						public StockRangeHistoryVO map(ResultSet resultSet) throws SQLException {
							log.log(Log.INFO, "$$$$Inside mapper >>>>>>>>>>>" );

							StockRangeHistoryVO stockRangeHistoryVO=new StockRangeHistoryVO();
							LocalDate transDate = null;

							stockRangeHistoryVO.setCompanyCode(resultSet.getString("CMPCOD"));
							stockRangeHistoryVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
							stockRangeHistoryVO.setFromStockHolderCode(resultSet.getString("STKHLDCOD"));
							stockRangeHistoryVO.setNumberOfDocuments(resultSet.getLong("DOCNUM"));
							//Edited as part of ICRD-46860
							if(StockAllocationVO.MODE_NEUTRAL.equalsIgnoreCase(resultSet.getString("MNLFLG")))   {
								stockRangeHistoryVO.setRangeType(StockAllocationVO.MODE_NEUTRAL);
							} else {
								stockRangeHistoryVO.setRangeType(StockAllocationVO.MODE_MANUAL);
							}
							stockRangeHistoryVO.setDocumentType(resultSet.getString("DOCTYP")); 
							stockRangeHistoryVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
							stockRangeHistoryVO.setStartRange(resultSet.getString("STARNG"));    
							stockRangeHistoryVO.setEndRange(resultSet.getString("ENDRNG"));                    
							stockRangeHistoryVO.setStatus(resultSet.getString(STATUS));
							if(resultSet.getDate("STKDAT")!=null) {
								transDate = new LocalDate(LocalDate.NO_STATION,
										Location.NONE,resultSet
										.getDate("STKDAT"));
								stockRangeHistoryVO.setTransactionDate(transDate);
								stockRangeHistoryVO.setTransDateStr(transDate.toDisplayDateOnlyFormat());
							} 
							//Edited as part of ICRD-46860
							if(DocumentValidationVO.DOC_TYP_AWB.equals(stockRangeHistoryVO.getDocumentType())) {
							int checkDigit=resultSet.getInt("AWBCHKDGT");
							if(checkDigit!=0){
							int appendStartRange = Integer.parseInt((resultSet.getString("STARNG")))%checkDigit ;

							int appendEndRange = Integer.parseInt((resultSet.getString("ENDRNG")))%checkDigit ;


							StringBuilder awbRange= new StringBuilder(resultSet.getString("STARNG"));
							StringBuilder s=awbRange.append(appendStartRange).append("-").append(resultSet.getString("ENDRNG")).append(appendEndRange);

							stockRangeHistoryVO.setAwbRange(s.toString());
								log.log(Log.FINE, "\n\n----AwbRange----------------",
									s);
							}
						    } else {
								stockRangeHistoryVO.setAwbRange(new StringBuilder(resultSet.getString("STARNG")).append("-").
										append(resultSet.getString("ENDRNG")).toString());
							}
							return stockRangeHistoryVO;
						}
					}
					
					/**
					 * @author A-3184
					 * @param stockRangeFilterVO
					 * @return Collection<StockRangeHistoryVO>
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					private Collection<StockRangeHistoryVO> findStockHistory(
							StockRangeFilterVO stockRangeFilterVO) throws SystemException,
							PersistenceException {
						log.entering("StockControl SqlDAO", "findStockHistory");
						LocalDate startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
				    	LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
				    	String  status = stockRangeFilterVO.getStatus();
				    	if(StockAllocationVO.RECEIVED_ALLOCATION.equals(stockRangeFilterVO.getStatus())){
				    		status=StockAllocationVO.MODE_ALLOCATE;
				    	}
						if(StockAllocationVO.RECEIVED_TRANSFER.equals(stockRangeFilterVO.getStatus())){
							status=StockAllocationVO.MODE_TRANSFER;
						}
						if(StockAllocationVO.RECEIVED_RETURN.equals(stockRangeFilterVO.getStatus())){
							status=StockAllocationVO.MODE_RETURN;
						}
				    	Query query = null;
				    	String Flag=stockRangeFilterVO.getRangeType();
				    	if(StockAllocationVO.MODE_NEUTRAL.equals(Flag)){
				    		Flag = "N";
				    	}else{
				    		Flag = "Y";
				    	}

						query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDSTOCKRANGEHISTORY);
						int index = 0;



						query.setParameter(++index, stockRangeFilterVO.getCompanyCode());

						if (stockRangeFilterVO.getAirlineIdentifier() != 0) {
							query.append(" AND HIST.ARLIDR = ?");
							query.setParameter(++index, stockRangeFilterVO
									.getAirlineIdentifier());

						}
						//Edited as part of ICRD-46860
						if(!DocumentValidationVO.DOC_TYP_INV.equals(stockRangeFilterVO.getDocumentType())){
						if ((stockRangeFilterVO.getEndRange()==null || stockRangeFilterVO.getEndRange().trim().length()==0) && (stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)) {
							query.append(" AND  (HIST.ASCSTARNG >= ? OR (? between HIST.ASCSTARNG and  HIST.ASCENDRNG))");
							query.setParameter(++index, stockRangeFilterVO.getStartRange());
							query.setParameter(++index, stockRangeFilterVO.getStartRange());
						}

						if ((stockRangeFilterVO.getStartRange()==null || stockRangeFilterVO.getStartRange().trim().length()==0) && (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)) {
							query.append(" AND  (HIST.ASCENDRNG <= ? OR (? between HIST.ASCSTARNG and  HIST.ASCENDRNG))");
							query.setParameter(++index, stockRangeFilterVO.getEndRange());
							query.setParameter(++index, stockRangeFilterVO.getEndRange());
						}
						if((stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getStartRange().trim().length()>0)&& (stockRangeFilterVO.getEndRange() != null && stockRangeFilterVO.getEndRange().trim().length()>0)){
							query.append("  AND ( ( ? BETWEEN HIST.ASCSTARNG AND HIST.ASCENDRNG) OR ( ? BETWEEN HIST.ASCSTARNG AND HIST.ASCENDRNG)OR ( HIST.ASCSTARNG >= ? AND HIST.ASCENDRNG <= ? ) )");
							query.setParameter(++index, stockRangeFilterVO.getStartRange());
							query.setParameter(++index, stockRangeFilterVO.getEndRange());
							query.setParameter(++index, stockRangeFilterVO.getStartRange());
							query.setParameter(++index, stockRangeFilterVO.getEndRange());
						}
					  }
						if (stockRangeFilterVO.getFromStockHolderCode() != null && stockRangeFilterVO.getFromStockHolderCode().trim().length()>0) {
							//Added by A-2881 for ICRD-3082
							if(StockAllocationVO.RECEIVED_ALLOCATION.equals(stockRangeFilterVO.getStatus())
									||StockAllocationVO.RECEIVED_TRANSFER.equals(stockRangeFilterVO.getStatus())
									||StockAllocationVO.RECEIVED_RETURN.equals(stockRangeFilterVO.getStatus())){
								query.append(" AND HIST.TOSTKHLDCOD= ?");
								query.setParameter(++index, stockRangeFilterVO
										.getFromStockHolderCode());
							}else{
							query.append(" AND HIST.FRMSTKHLDCOD= ?");
							query.setParameter(++index, stockRangeFilterVO
									.getFromStockHolderCode());
						}
						}
						/*if (stockRangeFilterVO.getRangeType() != null && stockRangeFilterVO.getRangeType().trim().length()>0) {
							query.append(" AND HIST.RNGTYP= ?");
							query.setParameter(++index, stockRangeFilterVO.getRangeType());
						}*/
						if (stockRangeFilterVO.getDocumentType() != null && stockRangeFilterVO.getDocumentType().trim().length()>0) {
							query.append(" AND HIST.DOCTYP= ?");
							query.setParameter(++index, stockRangeFilterVO.getDocumentType());
						}
						if (stockRangeFilterVO.getDocumentSubType() != null && stockRangeFilterVO.getDocumentSubType().trim().length()>0) {
							query.append(" AND HIST.DOCSUBTYP= ?");
							query.setParameter(++index, stockRangeFilterVO.getDocumentSubType());
						}

						if (status!= null && status.trim().length()>0) {
							query.append(" AND HIST.STATUS= ?");
							query.setParameter(++index,status);  
						}else{
							//Added by A-2881 for ICRD-3082
							//Including all exisitng status
							if (stockRangeFilterVO.getAvailableStatus() != null
									&& !stockRangeFilterVO.getAvailableStatus().isEmpty()) {
								query.append(" AND HIST.STATUS  IN (");
								int i = 0;
								for (String avl : stockRangeFilterVO.getAvailableStatus()) {
									if (i == 0) {
										query.append("?");
									} else {
										query.append(",?");
									}
									query.setParameter(++index, avl);
									i++;
								}
								query.append(")");
							}
							
							
						}
						if (stockRangeFilterVO.getAccountNumber() != null && stockRangeFilterVO.getAccountNumber().trim().length()>0) {

							query.append("AND HIST.FRMSTKHLDCOD IN (SELECT STKHLD.STKHLDCOD FROM STKHLDAGT STKHLD,SHRAGTMST AGTMST WHERE AGTMST.AGTCOD =  STKHLD.AGTCOD AND AGTMST.AGTACCCOD = ?)");

							query.setParameter(++index, stockRangeFilterVO.getAccountNumber());
						}
						
						if (stockRangeFilterVO.getUserId() != null && stockRangeFilterVO.getUserId().trim().length()>0) {

							query.append("AND HIST.USRCOD= ?");

							query.setParameter(++index, stockRangeFilterVO.getUserId());
						}
						log.log(Log.FINE, "\n\n----startdate--------------",
								stockRangeFilterVO.getStartDate());
						log.log(Log.FINE, "\n\n----enddate--------------",
								stockRangeFilterVO.getEndDate());
						if (stockRangeFilterVO.getStartDate() != null && stockRangeFilterVO.getEndDate() !=null) {

							String startDateStr = new StringBuilder().append(stockRangeFilterVO.getStartDate().toDisplayDateOnlyFormat())
							.append(" ").append("00:00").toString();
							String endDateStr = new StringBuilder().append(stockRangeFilterVO.getEndDate().toDisplayDateOnlyFormat())
							.append(" ").append("23:59").toString();
							startDate.setDateAndTime(startDateStr,true);
							endDate.setDateAndTime(endDateStr,true);
							query.append(" AND HIST.TXNDAT >= ? ");
							query.append(" AND HIST.TXNDAT <= ? ");
					    	query.setParameter(++index,startDate);
					    	query.setParameter(++index,endDate);
							}
						//Added as part of ICRD-46860 starts
						if(DocumentValidationVO.DOC_TYP_INV.equals(stockRangeFilterVO.getDocumentType())){
							if(stockRangeFilterVO.getStartRange() != null && stockRangeFilterVO.getEndRange() != null) {
								query.append("AND HIST.STARNG= ?");
								query.setParameter(++index, stockRangeFilterVO.getStartRange());
								query.append("AND HIST.ENDRNG= ?");
								query.setParameter(++index, stockRangeFilterVO.getEndRange());
							}
						}
						//Added as part of ICRD-46860 ends
				    		query.append(" ORDER BY STKDAT ");
						log.log(Log.FINE, "\n\n------------------", query);
							Collection<StockRangeHistoryVO> stockRangeHistoryVOs = query.getResultList(new StockRangeHistoryMapper());


						log.exiting("StockControl SqlDAO", "findStockRangeHistory");
						log.log(Log.INFO, "$$$$ From SqlDAO ",
								stockRangeHistoryVOs);
						return stockRangeHistoryVOs;

					}					
					
					/**
					 * @author A-3184
					 * @param
					 * @return Collection<StockRangeHistoryVO>
					 * @throws SystemException
					 * @throws PersistenceException
					 */

					private class StockRangeHistoryMapper implements Mapper<StockRangeHistoryVO> {
						/**
						 * Method for mapping StockRangeHistory
						 *
						 * @param resultSet
						 * @throws SQLException
						 * @return StockRangeHistoryVO
						 */
						public StockRangeHistoryVO map(ResultSet resultSet) throws SQLException {
							log.log(Log.INFO, "$$$$Inside mapper >>>>>>>>" );
							log.log(Log.FINE,
									"\n\n----resultset--------------",
									resultSet);
							LocalDate transDate = null;
							StockRangeHistoryVO stockRangeHistoryVO = new StockRangeHistoryVO();
							stockRangeHistoryVO.setCompanyCode(resultSet.getString("CMPCOD"));
							stockRangeHistoryVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
							stockRangeHistoryVO.setToStockHolderCode(resultSet.getString("TOSTKHLDCOD"));
							stockRangeHistoryVO.setNumberOfDocuments(resultSet.getLong("DOCNUM"));
							stockRangeHistoryVO.setFromStockHolderCode(resultSet.getString("STKHLDCOD"));
							if(StockAllocationVO.MODE_NEUTRAL.equalsIgnoreCase(resultSet.getString("MNLFLG")))   {
								stockRangeHistoryVO.setRangeType(StockAllocationVO.MODE_NEUTRAL);
							} else {
								stockRangeHistoryVO.setRangeType(StockAllocationVO.MODE_MANUAL);
							}
							//Edited as part of ICRD-46860
							stockRangeHistoryVO.setDocumentType(resultSet.getString("DOCTYP"));
							stockRangeHistoryVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
							stockRangeHistoryVO.setStartRange(resultSet.getString("STARNG"));
							stockRangeHistoryVO.setEndRange(resultSet.getString("ENDRNG"));
							stockRangeHistoryVO.setStatus(resultSet.getString(STATUS));
							if(resultSet.getDate("STKDAT") != null) {
								transDate = new LocalDate(LocalDate.NO_STATION,
										Location.NONE,resultSet
										.getDate("STKDAT"));
								stockRangeHistoryVO.setTransactionDate(transDate);
								stockRangeHistoryVO.setTransDateStr(transDate.toDisplayDateOnlyFormat());
							}

							if(DocumentValidationVO.DOC_TYP_AWB.equals(stockRangeHistoryVO.getDocumentType())) {
							int checkDigit=resultSet.getInt("AWBCHKDGT");
							if(checkDigit!=0){
							int appendStartRange = Integer.parseInt((resultSet.getString("STARNG")))%checkDigit ;

							int appendEndRange = Integer.parseInt((resultSet.getString("ENDRNG")))%checkDigit ;


							StringBuilder awbRange= new StringBuilder(resultSet.getString("STARNG"));
							StringBuilder s=awbRange.append(appendStartRange).append("-").append(resultSet.getString("ENDRNG")).append(appendEndRange);
							stockRangeHistoryVO.setAwbRange(s.toString());
							log.log(Log.FINE, "\n\n----AwbRange--------------",
									s);
							//added by A-2881 for ICRD-3082
								}
							} else {
								stockRangeHistoryVO.setAwbRange(new StringBuilder(resultSet.getString("STARNG")).append("-").
										append(resultSet.getString("ENDRNG")).toString());
							}
							stockRangeHistoryVO.setRemarks(resultSet.getString("TXNRMK"));
							stockRangeHistoryVO.setVoidingCharge(resultSet.getDouble("VODCHG"));
							stockRangeHistoryVO.setCurrencyCode(resultSet.getString("CURCOD"));
							


							return stockRangeHistoryVO;
						}

				}	
					
					/**
					 * @author A-3184
					 * @param stockAllocationVO
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					public List<Integer> validateStockPeriod(
							StockAllocationVO stockAllocationVO,int stockIntroductionPeriod) throws SystemException,
							PersistenceException {
						log.entering("StockControl SqlDAO", "validateStockPeriod");

						int index = 0;
						String nativeQuery = getQueryManager().getNamedNativeQueryString(STOCKCONTROL_DEFAULTS_VALIDATESTOCKPERIOD);
						if(isOracleDataSource()) {
							String dynamicQuery ="?* 365 > to_timestamp(?,'YYYY-MM-DD HH24:MI:SS')";

							nativeQuery = String.format(nativeQuery,dynamicQuery);
						} else {
							String dynamicQuery = "(( ? * 365)::NUMERIC " +
									"|| ' days')::INTERVAL > to_timestamp(?,'YYYY-MM-DD HH24:MI:SS')";
							nativeQuery = String.format(nativeQuery,dynamicQuery);
						}

						Query query  = getQueryManager().createNativeQuery(nativeQuery);
						LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
						LocalDate currentDate=new LocalDate(logon.getAirportCode(),
								Location.ARP,true);
						//GMTDate staGMT = new GMTDate(currentDate,true);

							String startRange=null;
							String endRange=null;
							for(RangeVO rangeVO:stockAllocationVO.getRanges()){
								startRange=rangeVO.getStartRange();
								endRange=rangeVO.getEndRange();
							}
							query.setParameter(++index, stockAllocationVO.getCompanyCode());

							query.setParameter(++index, stockAllocationVO.getAirlineIdentifier());

							query.setParameter(++index, stockAllocationVO.getDocumentType());

							//query.setParameter(++index, stockAllocationVO.getDocumentSubType());

							query.setParameter(++index, Integer.parseInt(startRange));

							query.setParameter(++index, Integer.parseInt(endRange));

							query.setParameter(++index, Integer.parseInt(startRange));

							query.setParameter(++index, Integer.parseInt(endRange));

							query.setParameter(++index, stockIntroductionPeriod);
						    String formattedDate = currentDate.toStringFormat("yyyy-MM-dd").substring(0,10);
							query.setParameter(++index, formattedDate);

					log.log(Log.FINE, "\n\n------------------", query.toString());
					List<Integer> asciiStartRanges = query.getResultList(new ValidateStockPeriodMapper());
					 
						log.exiting("StockControl SqlDAO", "validateStockPeriod");
						return asciiStartRanges;

				}


					/**
					 * @author A-3184
					 * @param
					 * @return Integer
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					private class ValidateStockPeriodMapper implements Mapper<Integer> {
						/**
						 * Method for mapping validateStock
						 *
						 * @param resultSet
						 * @throws SQLException
						 * @return Integer
						 */
						public Integer map(ResultSet resultSet) throws SQLException {
							log.log(Log.INFO, "$$$$Inside mapper >>>>>>>>>>" );

							return resultSet.getInt("ASCSTARNG");							
								
							
						}
					}					
					/**
					 * @author A-2415
					 * @param
					 * @return stockRangeHistoryVO
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					public Collection<StockRangeHistoryVO> findAgentCSVDetails(StockRangeFilterVO stockRangeFilterVO) throws SystemException, PersistenceException {
						log.entering("StockControl SqlDAO", "findAgentCSVDetails");
						Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDAGENTCSVDETAILS);
						int index = 0;

							query.setParameter(++index, stockRangeFilterVO.getCompanyCode());

							query.setParameter(++index,stockRangeFilterVO.getStartDate());

							query.setParameter(++index, stockRangeFilterVO.getEndDate());



							log.log(Log.FINE, "\n\n------------------", query.toString());
							Collection<StockRangeHistoryVO> stockRangeHistoryVOs=
								query.getResultList(new AgentCSVDetailsMapper());


							log.exiting("StockControl SqlDAO", "findAgentCSVDetails");
							log.log(Log.INFO, "$$$$ From SqlDAO ");
							return stockRangeHistoryVOs;
					}
					/**
					 *
					 * @author A-2415
					 *
					 */
					private class AgentCSVDetailsMapper implements Mapper<StockRangeHistoryVO> {
						/**
						 * Method for mapping StockRangeHistory
						 *
						 * @param resultSet
						 * @throws SQLException
						 * @return StockRangeHistoryVO
						 */
						public StockRangeHistoryVO map(ResultSet resultSet) throws SQLException {
							log.log(Log.INFO, "$$$$Inside mapper >>>>>>>>>>" );
							//log.log(Log.FINE,"\n\n----resultset--------------"+resultSet);
							StockRangeHistoryVO stockRangeHistoryVO = new StockRangeHistoryVO();
							//MST.STKHLDCOD, HIS.NUMDOC, HIS.STARNG, HIS.ENDRNG FROM STKHLDMST MST, STKRNGTXNHIS HIS
							stockRangeHistoryVO.setToStockHolderCode(resultSet.getString("STKHLDCOD"));
							stockRangeHistoryVO.setNumberOfDocuments(resultSet.getInt("NUMDOC"));

							//added for bug 27458 to append chekdigit starts

							//Edited as part of ICRD-46860
							if(DocumentValidationVO.DOC_TYP_AWB.equals(stockRangeHistoryVO.getDocumentType())) {
								int checkDigit=resultSet.getInt("AWBCHKDGT");
							int appendStartRange = Integer.parseInt((resultSet.getString("STARNG")))%checkDigit ;

							int appendEndRange = Integer.parseInt((resultSet.getString("ENDRNG")))%checkDigit ;

							StringBuilder start=new StringBuilder(resultSet.getString("STARNG")).append(appendStartRange);

							StringBuilder end=new StringBuilder(resultSet.getString("ENDRNG")).append(appendEndRange);

							stockRangeHistoryVO.setStartRange(start.toString());
							stockRangeHistoryVO.setEndRange(end.toString());
							} else {
								stockRangeHistoryVO.setStartRange(resultSet.getString("STARNG"));
								stockRangeHistoryVO.setEndRange(resultSet.getString("ENDRNG"));
							}
							//added for bug 27458 ends

							return stockRangeHistoryVO;
						}
					}					
					/**
					 * @author A-3184
					 * @param companyCode
					 * @param airlineId
					 * @param documentType
					 * @param stockHolderCode
					 * @param isRegionorstation
					 * @return Collection<String>
					 * @throws SystemException
					 */
					public Collection<String> findSubTypesForStockHolder(String companyCode,int airlineId,String documentType,
							String subType,String stockHolderCode,boolean isRegionorstation) throws SystemException,
							PersistenceException {
						Query query = getQueryManager().createNamedNativeQuery(
								STOCKCONTROL_DEFAULTS_SUBTYPESFORSTOCKHOLDER);
						int index = 0;
						query.setParameter(++index,companyCode);
						query.setParameter(++index, airlineId);
						query.setParameter(++index, documentType);
						query.setParameter(++index,stockHolderCode);

						if(subType !=null && !isRegionorstation){
							query.append("AND DOCSUBTYP = ? ");
							query.setParameter(++index,subType);
						}

						if(subType == null || isRegionorstation){
						query.append(" AND DOCSUBTYP <> 'COMAT' ");

						}

						return query.getResultList(new SubTypesOfStockHolderMapper());
					}

					/**
					 * @author A-1883 Mapper class for getting subtype for StockHolder
					 */
					class SubTypesOfStockHolderMapper implements Mapper<String> {
						/**
						 * This method is used to map the result of query
						 *
						 * @param resultSet
						 * @throws SQLException
						 * @return String
						 */
						public String map(ResultSet resultSet) throws SQLException {
							return resultSet.getString("DOCSUBTYP");
						}
					}
					/**
					 *@author A-3184
					 * @param rangeVO
					 * @return Collection<RangeVO>
					 * @throws SystemException
					 * @throws PersistenceException
					 */

					public Collection<RangeVO> findUsedRangesForMerge(
							RangeVO rangeVO,String status) throws SystemException,
							PersistenceException {
						log.entering("StockControl SqlDAO", "findUsedRangesForMerge");

						Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDUSEDRANGESFORMERGE);
						Collection<RangeVO> rangeVOs=new ArrayList<RangeVO>();
						log.log(Log.FINE, "----In sql---------------RangeVO",
								rangeVO);
						int index = 0;
						long ascStaRng = 0;
						long ascEndRng = 0;
						LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,
									Location.NONE,false);

						query.setParameter(++index, rangeVO.getCompanyCode());
						query.setParameter(++index, rangeVO.getAirlineIdentifier());
						query.setParameter(++index, rangeVO.getDocumentType());
						query.setParameter(++index, rangeVO.getDocumentSubType());
						query.setParameter(++index, rangeVO.getStockHolderCode());
						query.setParameter(++index, status);

						if(rangeVO.getAsciiStartRange()>=0){
							query.append(" ASCENDRNG = ? ");
						    ascStaRng = rangeVO.getAsciiStartRange();
							ascStaRng--;
							query.setParameter(++index,ascStaRng);
							if(rangeVO.getAsciiEndRange()>=0){
								query.append(" OR ");
							}

						    }
						if(rangeVO.getAsciiEndRange()>=0){
							query.append("ASCSTARNG = ? ");
							ascEndRng=rangeVO.getAsciiEndRange();
							ascEndRng++;
							query.setParameter(++index,ascEndRng);
						}
						query.append(")");

						query.append(" AND trunc(TXNDAT)=?");    
						query.setParameter(++index,currentDate);         
						

						rangeVOs=query.getResultList(new FindUsedRangesForMergeMapper());
						log
								.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>",
										query);
						log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>",
								rangeVOs);
						return rangeVOs;



					}

					/**
					 *@author A-3184
					 * @param rangeVO
					 * @return Collection<RangeVO>
					 * @throws SystemException
					 * @throws PersistenceException
				 */
				private class FindUsedRangesForMergeMapper implements Mapper<RangeVO> {
					/**
					 * Method for mapping StockRangeHistory
					 *
					 * @param resultSet
					 * @throws SQLException
					 * @return StockRangeHistoryVO
					 */
					public RangeVO map(ResultSet resultSet) throws SQLException {

						RangeVO rangeVO=new RangeVO();

						rangeVO.setCompanyCode(resultSet.getString("CMPCOD"));
						rangeVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
						rangeVO.setSequenceNumber(resultSet.getString("HISSEQNUM"));
						rangeVO.setDocumentType(resultSet.getString("DOCTYP"));
						rangeVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
						rangeVO.setStockHolderCode(resultSet.getString("FRMSTKHLDCOD"));
						rangeVO.setAsciiStartRange(resultSet.getLong("ASCSTARNG"));
						rangeVO.setAsciiEndRange(resultSet.getLong("ASCENDRNG"));
						rangeVO.setStartRange(resultSet.getString("STARNG"));
						rangeVO.setEndRange(resultSet.getString("ENDRNG"));
						if("N".equalsIgnoreCase(resultSet.getString("RNGTYP"))){
							rangeVO.setManual(false);
						} else{
							rangeVO.setManual(true);
						}
						log.log(Log.INFO, "$$$$mapper  >>>>>>>>>>", rangeVO);
						return rangeVO;

					}


					}
				/**
				 *@author A-3155
				 * @param rangeVO
				 * @return Collection<RangeVO>
				 * @throws SystemException
				 * @throws PersistenceException
				 */

				public Collection<RangeVO> findUtilisationRangesForMerge(
						RangeVO rangeVO) throws SystemException,
						PersistenceException {
					log.entering("StockControl SqlDAO", "findStockRangeHistory");

					Query query = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDUTILISATIONRANGESFORMERGE);
					Collection<RangeVO> rangeVOs=new ArrayList<RangeVO>();
					log.log(Log.FINE, "----In sql---------------RangeVO",
							rangeVO);
					int index = 0;
					long ascStaRng = 0;
					long ascEndRng = 0;

					query.setParameter(++index, rangeVO.getCompanyCode());
					query.setParameter(++index, rangeVO.getAirlineIdentifier());
					query.setParameter(++index, rangeVO.getDocumentType());
					query.setParameter(++index, rangeVO.getDocumentSubType());
					query.setParameter(++index, rangeVO.getStockHolderCode());

					if(rangeVO.getAsciiStartRange()!=0){
						query.append(" ASCENDRNG = ? ");
					    ascStaRng = rangeVO.getAsciiStartRange();
						ascStaRng--;
						query.setParameter(++index,ascStaRng);
						if(rangeVO.getAsciiEndRange()!=0){
							query.append(" OR ");
						}

					    }
					if(rangeVO.getAsciiEndRange()!=0){
						query.append("ASCSTARNG = ? ");
						ascEndRng=rangeVO.getAsciiEndRange();
						ascEndRng++;
						query.setParameter(++index,ascEndRng);
					}
					query.append(")");

					rangeVOs=query.getResultList(new FindUtilisationRangesForMergeMapper());
					log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", query);
					log.log(Log.INFO, "$$$$InsideSql DAO >>>>>>>>>>", rangeVOs);
					return rangeVOs;



				}

				/**
				 *@author A-3155
				 * @param rangeVO
				 * @return Collection<RangeVO>
				 * @throws SystemException
				 * @throws PersistenceException
			 */
			private class FindUtilisationRangesForMergeMapper implements Mapper<RangeVO> {
				/**
				 * Method for mapping StockRangeHistory
				 *
				 * @param resultSet
				 * @throws SQLException
				 * @return StockRangeHistoryVO
				 */
				public RangeVO map(ResultSet resultSet) throws SQLException {

					RangeVO rangeVO=new RangeVO();

					rangeVO.setCompanyCode(resultSet.getString("CMPCOD"));
					rangeVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
					rangeVO.setSequenceNumber(resultSet.getString("HISSEQNUM"));
					rangeVO.setDocumentType(resultSet.getString("DOCTYP"));
					rangeVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
					rangeVO.setStockHolderCode(resultSet.getString("STKHLDCOD"));
					rangeVO.setAsciiStartRange(resultSet.getLong("ASCSTARNG"));
					rangeVO.setAsciiEndRange(resultSet.getLong("ASCENDRNG"));
					rangeVO.setStartRange(resultSet.getString("STARNG"));
					rangeVO.setEndRange(resultSet.getString("ENDRNG"));
					log.log(Log.INFO, "$$$$mapper  >>>>>>>>>>", rangeVO);
					return rangeVO;

				}


				}
				
			/**
			 * Method for checking whether Approver Contains Stock or not
			 *
			 * @param companyCode
			 * @param docType
			 * @param docSubType
			 * @return
			 * @throws SystemException
			 */
			public String checkForApproverStock(String companyCode,int airlineId,String docType,
					String docSubType,String stkApproverCode) throws SystemException {
				Query qry = getQueryManager().createNamedNativeQuery(
						STOCKCONTROL_DEFAULTS_CHECKFORAPPROVERSTOCK);
				int index = 0;
				qry.setParameter(++index, companyCode);
				qry.setParameter(++index,airlineId);
				qry.setParameter(++index, docType);
				qry.setParameter(++index, docSubType);
				qry.setParameter(++index, stkApproverCode);

				return qry.getSingleResult(new CheckForApproverStockMapper());
			}

			/**
			 * checking whether Approver Contains Stock or not
			 *
			 * @author A-3184
			 *
			 */
			class CheckForApproverStockMapper implements Mapper<String> {
				/**
				 * Method for mapping result set to string
				 *
				 * @param resultSet
				 * @throws SQLException
				 * @return String
				 */
				public String map(ResultSet resultSet) throws SQLException {
					return resultSet.getString("CMPCOD");
				}
			}			
			/**
			 * Method to find distributed Ranges For HQ
			 * @author A-3184
			 * @param rangeFilterVO
			 * @return
			 * @throws SystemException
			 * @throws PersistenceException
			 */
			public boolean findDistributedRangesForHQ(RangeFilterVO rangeFilterVO)
			throws SystemException, PersistenceException {
				log.entering("StockControl Sql DAO", "finddistributedrangesforHQ");
				log.log(Log.FINE,
						"\n\n---vaules to distributedrangesforHQ----------",
						rangeFilterVO);
					if((rangeFilterVO.getStartRange() != null && rangeFilterVO.getStartRange().trim().length()>0)
							&& (rangeFilterVO.getEndRange() != null && rangeFilterVO.getEndRange().trim().length()>0)){
					int index = 0;
					Query query = getQueryManager().createNamedNativeQuery(
							STOCKCONTROL_DEFAULTS_FINDDISTRIBUTEDRANGESFORHQ);
					log.log(Log.FINE, "\n\n---distributed for HQ------------",
							query);
					query.setParameter(++index,rangeFilterVO.getCompanyCode());
					query.setParameter(++index,rangeFilterVO.getAirlineIdentifier());
					query.setParameter(++index,rangeFilterVO.getDocumentType());
					query.setParameter(++index,rangeFilterVO.getDocumentSubType());
					query.setParameter(++index,rangeFilterVO.getStockHolderCode());
					query.setParameter(++index, rangeFilterVO.getStartRange());
					query.setParameter(++index, rangeFilterVO.getEndRange());
					query.setParameter(++index, rangeFilterVO.getStartRange());
					query.setParameter(++index, rangeFilterVO.getEndRange());
					int count = query
							.getSingleResult(getIntMapper("COUNT"));
					log.exiting("StockControl Sql DAO", "finddistributedrangesforHQ");
					return (count>0 ? true : false);
					}
						return false; 
					
				}
			/**
			 *
			 * @author A-4443
			 *
			 */
			public Collection<TransitStockVO> findTransitStocks(StockRequestFilterVO stockRequestFilterVO) throws SystemException {
				Query qry = getQueryManager().createNamedNativeQuery(
						StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_FINDTRANSITSTOCKS);
				int index = 0;
				if(stockRequestFilterVO!=null){
				qry.setParameter(++index, stockRequestFilterVO.getCompanyCode());
				qry.setParameter(++index, Integer.parseInt(stockRequestFilterVO.getAirlineIdentifier()));
				qry.setParameter(++index, stockRequestFilterVO.getDocumentType());
				qry.setParameter(++index, stockRequestFilterVO.getDocumentSubType());
				qry.setParameter(++index, stockRequestFilterVO.getStockHolderCode());
				//added as part of ICRD-15656
				qry.setParameter(++index, stockRequestFilterVO.getStockHolderType()); 
				
				
				if ((stockRequestFilterVO.getOperation() != null)
						&& !("".equals(stockRequestFilterVO.getOperation()))
						&& (stockRequestFilterVO.getOperation().trim().length() != 0)) {
					qry.append("and txncod = ? ");
					qry.setParameter(++index, stockRequestFilterVO.getOperation());
				}
				if ((stockRequestFilterVO.getFromDate() != null)
						&& !("".equals(stockRequestFilterVO.getFromDate()
								.toString()))) {
					qry.append("and (to_char (txndat, 'DD-Mon-YYYY') >= ? ");
					qry.setParameter(++index, stockRequestFilterVO.getFromDate()
							.toDisplayDateOnlyFormat());
				}
				if (stockRequestFilterVO.getToDate() != null) {
					qry.append("and to_char (txndat, 'DD-Mon-YYYY') <= ? ");
					qry.setParameter(++index, stockRequestFilterVO.getToDate()
							.toDisplayDateOnlyFormat());
				}
				if (stockRequestFilterVO.getFromDate() != null) {
					qry.append(")");
				}
	
				if (!("".equals(stockRequestFilterVO.getStatus()))
						&& (stockRequestFilterVO.getStatus().trim().length() != 0)) {
					if (!"ALL".equals(stockRequestFilterVO.getStatus())) {
					qry.append(" and cfrsta = ? ");
					qry.setParameter(++index, stockRequestFilterVO.getStatus());
					}
				}
				/*if(!("".equals(stockRequestFilterVO.getAirlineIdentifier())) && (stockRequestFilterVO.getAirlineIdentifier().trim().length()!=0)){
					qry.append(" and arlidr = ? ");
					qry.setParameter(++index, stockRequestFilterVO.getAirlineIdentifier());
				}*/
				}
				Collection<TransitStockVO> transitStockVOs = qry.getResultList(new TransitStockListingMultiMapper());
				log.log(Log.FINE, "\n\n---transitStockVOs------------",
						transitStockVOs);
				return transitStockVOs;
			}

			/**
			 *
			 * @author A-4443
			 *
			 */
			public Collection<TransitStockVO> findBlackListRangesFromTransit(BlacklistStockVO blacklistStockVO) throws SystemException {
				Query qry = getQueryManager().createNamedNativeQuery(
						StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_FINDBLACKLISTRANGESFROMTRANSIT);
				int index = 0;
				if(blacklistStockVO!=null){
				qry.setParameter(++index, blacklistStockVO.getCompanyCode());
				qry.setParameter(++index, blacklistStockVO.getAirlineIdentifier());
				qry.setParameter(++index, blacklistStockVO.getDocumentType());
				qry.setParameter(++index, blacklistStockVO.getDocumentSubType());
				qry.setParameter(++index, Integer.parseInt(blacklistStockVO.getRangeFrom()));
				qry.setParameter(++index, Integer.parseInt(blacklistStockVO.getRangeTo()));
				qry.setParameter(++index, Integer.parseInt(blacklistStockVO.getRangeFrom()));
				qry.setParameter(++index, Integer.parseInt(blacklistStockVO.getRangeTo()));
				}
				Collection<TransitStockVO> transitStockVOs = qry.getResultList(new BlackListRangesFromTransitMultiMapper());
				log.log(Log.FINE, "\n\n---transitStockVOs------------",
						transitStockVOs);
				return transitStockVOs;
			}
			
			/**
			 * @author A-2881
			 * @param stockRangeFilterVO
			 * @return
			 * @throws SystemException
			 * @throws PersistenceException
			 */
			private Page<StockRangeHistoryVO> findStockUtilisationHistoryForPage(
					StockRangeFilterVO stockRangeFilterVO) throws SystemException,
					PersistenceException {
				log.entering("StockControl SqlDAO", "findStockUtilisationHistoryForPage");
				String baseQuery = null;
				
				//Added by A-5220 for ICRD-20959 starts
				PageableNativeQuery<StockRangeHistoryVO> query = null;
				String status = stockRangeFilterVO.getStatus();
				log.log(Log.INFO, "status: ", status);
				//PageableNativeQuery<StockRangeHistoryVO> pgqry = null;
				//Added by A-5220 for ICRD-20959 ends
				if ((StockAllocationVO.MODE_UNUSED).equalsIgnoreCase(status)) {
					baseQuery = getQueryManager().getNamedNativeQueryString(
							STOCKCONTROL_DEFAULTS_FINDSTOCKUTILISATIONHISTORY);
				}

				//Modified by A-5220 for ICRD-20959 starts
				if (status==null||("").equals(status)) {
					//Modified by A-5220 for ICRD-20959 ends
					baseQuery = getQueryManager().getNamedNativeQueryString(
							STOCKCONTROL_DEFAULTS_FINDSTOCKUTILISATIONHISTORY);
				}
				if ((StockAllocationVO.MODE_USED).equalsIgnoreCase(status)) {
					baseQuery = getQueryManager().getNamedNativeQueryString(
							STOCKCONTROL_DEFAULTS_FINDSTOCKUTILISATIONHISTORYUSED);
				}
				//Added by A-5220 for ICRD-20959 starts
				
				StringBuilder rankQuery = new StringBuilder();
				rankQuery.append( StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_ROWNUM_QUERY );
				rankQuery.append(baseQuery);
				
				//Added by A-5220 for ICRD-20959 ends
				if(stockRangeFilterVO.getPageSize() > 0){
					query = new StockRangeHistoryUtilFilterQuery(stockRangeFilterVO.getPageSize(),
							new StockRangeUtilisationMapper(), stockRangeFilterVO,
							rankQuery.toString(), status, isOracleDataSource());
				}else{
				query = new StockRangeHistoryUtilFilterQuery(
						new StockRangeUtilisationMapper(), stockRangeFilterVO,
						rankQuery.toString(), status, isOracleDataSource());
				}

				log.log(Log.FINE, "\n\n------------------", query);
				log.exiting("StockControl SqlDAO", "findStockRangeHistory");
				log.log(Log.INFO, "list Filter Query is", query.toString());
				//Added by A-5220 for ICRD-20959 ends
				return query.getPage(stockRangeFilterVO.getPageNumber());

			}	
			
			
			/**
			 * @author A-2881
			 * @param stockRangeFilterVO
			 * @return
			 * @throws SystemException
			 * @throws PersistenceException
			 */
			public Page<StockRangeHistoryVO>findStockRangeHistoryForPage(StockRangeFilterVO stockRangeFilterVO)
								throws SystemException,
								PersistenceException {
				log.entering("StockControl SqlDAO", "findPaginatedStockRangeHistory");
				boolean isHistory = stockRangeFilterVO.isHistory();
				log.log(Log.INFO, "isHistory: ", isHistory);
				if (!isHistory) {
					return findStockUtilisationHistoryForPage(stockRangeFilterVO);
				} else {
					return findStockHistoryForPage(stockRangeFilterVO);
				}

			}
			
			/**
			 * @author A-2881
			 * @param stockRangeFilterVO
			 * @return
			 * @throws SystemException
			 * @throws PersistenceException
			 */
			private Page<StockRangeHistoryVO> findStockHistoryForPage(
										StockRangeFilterVO stockRangeFilterVO) throws SystemException,
										PersistenceException {
				log.entering("StockControl SqlDAO", "findStockHistoryForPage");
				String baseQuery = null;
				//Added by A-5220 for ICRD-20959 starts
				PageableNativeQuery<StockRangeHistoryVO> query = null;
				//PageableQuery pgqry = null;
				baseQuery = getQueryManager().getNamedNativeQueryString(
						STOCKCONTROL_DEFAULTS_FINDSTOCKRANGEHISTORY);
				StringBuilder rankQuery = new StringBuilder();
				rankQuery.append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_ROWNUM_QUERY);
				//rankQuery.append(" HIST.TXNDAT ) AS RANK FROM ( ");
				rankQuery.append(baseQuery);
				query = new StockRangeHistoryFilterQuery(new StockRangeHistoryMapper(), stockRangeFilterVO, rankQuery.toString());
				log.log(Log.FINE, "\n\n------------------", query);
				/*pgqry = new PageableQuery<StockRangeHistoryVO>(query,
						new StockRangeHistoryMapper());*/
				//Added by A-5220 for ICRD-20959 ends
				log.exiting("StockControl SqlDAO", "findStockHistoryForPage");
				return query.getPage(stockRangeFilterVO.getPageNumber());

			}	
	
			/**
			 * @author A-5153
			 * @param filterVO
			 * @throws SystemException
			 * @throws PersistenceException 
			 * 
			 */
			public void autoDepleteStock()throws SystemException, PersistenceException {
				log.entering("StockSQLDAO", "autoDepleteStock");
				String outParameter = null;
				Procedure depleteProcedure = getQueryManager().createNamedNativeProcedure(
						STOCKCONTROL_DEFAULTS_AUTODEPLETESTOCK);
				depleteProcedure.setOutParameter(1,SqlType.STRING);
				depleteProcedure.execute();

				outParameter = (String)depleteProcedure.getParameter(1);
				log.log(Log.INFO, "outParameter --->", outParameter);
				if(outParameter!=null && !"OK".equals(outParameter)) {
					throw new PersistenceException(outParameter);
				}
				log.exiting("StockSQLDAO","depleteAllDocumentFromStock");


			}	
			
			public String checkStockUtilisationForRange(StockFilterVO stockFilterVO)throws SystemException, PersistenceException {
				log.entering("StockSQLDAO", "checkStockUtilisationForRange");
				Query qry = null;

				if(isOracleDataSource()) {
					qry = getQueryManager().createNamedNativeQuery(
							STOCKCONTROL_DEFAULTS_CHECKSTOCKUTILISATIONFORRANGE);
				} else {
					qry = getQueryManager().createNamedNativeQuery(POSTGRES_STOCKCONTROL_DEFAULTS_CHECKSTOCKUTILISATIONFORRANGE);
				}

				int index = 0;
				qry.setParameter(++index, stockFilterVO.getCompanyCode());
				qry.setParameter(++index, stockFilterVO.getAirlineIdentifier());
				qry.setParameter(++index, stockFilterVO.getDocumentType());
				qry.setParameter(++index, stockFilterVO.getDocumentSubType());
				if(stockFilterVO.getStockHolderCode()!=null && stockFilterVO.getStockHolderCode().trim().length()>0){
					qry.append(" AND stkhldcod = ? ");
					qry.setParameter(++index, stockFilterVO.getStockHolderCode());
				}
				if(stockFilterVO.getRangeFrom()!=null && stockFilterVO.getRangeFrom().trim().length()>0 
						&& stockFilterVO.getRangeTo()!=null && stockFilterVO.getRangeTo().trim().length()>0){
					qry.append(" AND ascdocnum BETWEEN ? AND ? ");
					qry.setParameter(++index, findLong(stockFilterVO.getRangeFrom()));
					qry.setParameter(++index, findLong(stockFilterVO.getRangeTo()));
				}
				return qry.getSingleResult(new CheckStockUtilisationForRangeMapper());
			}	
			class CheckStockUtilisationForRangeMapper implements Mapper<String> {
				/**
				 * @param resultSet
				 * @throws SQLException
				 * @return String
				 */
				public String map(ResultSet resultSet) throws SQLException {
					log.entering("SqlDAO", "map()");
					return resultSet.getString("MSTDOCNUM");
				}
			}
			/**
			 * @author a-5249
			 * addPrivilegeConditions()
			 * @param stockFilterVO
			 * @param query
			 * @param index
			 */
			private void addPrivilegeConditions(StockFilterVO stockFilterVO,Query query,int index){
				if(PRVLG_RUL_STK_HLDR.equals(stockFilterVO.getPrivilegeRule())&&
						PRVLG_LVL_STKHLD.equals(stockFilterVO.getPrivilegeLevelType())&&
						stockFilterVO.getPrivilegeLevelValue()!=null && 
						stockFilterVO.getPrivilegeLevelValue().trim().length()>0){
					String[] levelValues = stockFilterVO.getPrivilegeLevelValue().split(",");
					query.append(" AND (Z.STKHLDCOD IN (");
					boolean isFirst = true;
					for(String val : levelValues){
						if(!isFirst){
							query.append(", ");	
						}
						query.append("?");	
						query.setParameter(++index, val);
						isFirst = false;
					}
					query.append(") OR Z.STKAPRCOD IN (");
					isFirst = true;
					for(String val : levelValues){
						if(!isFirst){
							query.append(", ");	
						}
						query.append("?");	
						query.setParameter(++index, val);
						isFirst = false;
					}
					query.append("))");				
					
				}
			}
			
			/**
			 * @author a-5249
			 * addPrivilegeConditions()
			 * @param stockFilterVO
			 * @param query
			 * @param index
			 */
			private void addPrivilegeConditions(StockRangeFilterVO stockRangeFilterVO,Query query,int index){
				if(PRVLG_RUL_STK_HLDR.equals(stockRangeFilterVO.getPrivilegeRule())&&
						PRVLG_LVL_STKHLD.equals(stockRangeFilterVO.getPrivilegeLevelType())&&
						stockRangeFilterVO.getPrivilegeLevelValue()!=null && 
						stockRangeFilterVO.getPrivilegeLevelValue().trim().length()>0){
					String[] levelValues = stockRangeFilterVO.getPrivilegeLevelValue().split(",");
					query.append(" WHERE (Z.STKHLDCOD IN (");
					boolean isFirst = true;
					for(String val : levelValues){
						if(!isFirst){
							query.append(", ");	
						}
						query.append("?");	
						query.setParameter(++index, val);
						isFirst = false;
					}
					query.append(") OR Z.STKAPRCOD IN (");
					isFirst = true;
					for(String val : levelValues){
						if(!isFirst){
							query.append(", ");	
						}
						query.append("?");	
						query.setParameter(++index, val);
						isFirst = false;
					}
					query.append(") OR Z.TOSTKHLDCOD IN (");
					isFirst = true;
					for(String val : levelValues){
						if(!isFirst){
							query.append(", ");	
						}
						query.append("?");	
						query.setParameter(++index, val);
						isFirst = false;
					}
					query.append("))");				
					
				}
			}
			/**
			 * This method will find the blacklisted stock 
			 * @return Collection<BlacklistStockVO>
			 * @throws SystemException
			 * @throws PersistenceException
			 */
			public Collection<BlacklistStockVO> findBlacklistedStockFromUTL()
					throws SystemException, PersistenceException {
				String nativeQuery = getQueryManager().getNamedNativeQueryString(STOCKCONTROL_DEFAULTS_FINDBLACKLISTFROMUTL);
				if(isOracleDataSource()) {
					String dynamicQuery =" AND ( lstupdtimutc + ( 20 / ( 24 * 60 ) ) ) < sys_extract_utc(systimestamp) ";
					nativeQuery = String.format(nativeQuery,dynamicQuery);
				} else {
					String dynamicQuery = "  AND (lstupdtimutc + ((20::NUMERIC / (24 * 60)::NUMERIC) || ' days')::INTERVAL) < pkg_frmwrk_date.fun_get_sysdateutc() ";
					nativeQuery = String.format(nativeQuery,dynamicQuery);
				}

				Query qry  = getQueryManager().createNativeQuery(nativeQuery);
				Collection<BlacklistStockVO> blacklistStockVOs;
				LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
				int index = 0;
				qry.setParameter(++index, logonAttributes.getCompanyCode());
				return qry.getResultList(new BlacklistedStockMapper());
			}
			/**
			 * This method will delete Blacklisted stocks with status "B"
			 * @return 
			 * @throws SystemException
			 * @throws PersistenceException
			 */
			public void deleteBlackListedStockFromUTL(ArrayList<String> masterDocNumbers) throws SystemException,
			PersistenceException{
				log.entering("SQLDAO","deleteBlackListedStock-*****--"); 
				LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
				
				 int index = 0;
				 StringBuilder builder=new StringBuilder();
				
				for(String masterDocNumber:masterDocNumbers){
		        	if(masterDocNumber!=null&&masterDocNumber.trim().length()>0){
		        		if(index>0){
		        			builder.append(",");
		        		}
		        		builder.append("'");
		        		builder.append(masterDocNumber.trim());
		        		builder.append("'");
		        		index++;
		        	}
		        }
					Query query=getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_DELETEBLACKLISTEDSTK_FROM_UTL);
					int indexValue = 0;
					query.setParameter(++indexValue, logonAttributes.getCompanyCode());
	                query.append(builder.toString()).append(")");
                query.executeUpdate(); 

			}
			@Override
			public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO)
					throws SystemException, PersistenceException {
				log.entering("SQLDAO","findAutoPopulateSubtype-*****--"); 
				Query qry = getQueryManager().createNamedNativeQuery(STOCKCONTROL_DEFAULTS_FINDAUTOPOPULATE_SUBTYPE);
				int index = 0;
				qry.setParameter(++index, documentFilterVO.getCompanyCode());
				qry.setParameter(++index, documentFilterVO.getStockHolderCode());
				qry.setParameter(++index, documentFilterVO.getDocumentType());
				return qry.getSingleResult(new SubtypeMapper());
			}
			class SubtypeMapper implements Mapper<String>{
				@Override
				public String map(ResultSet resultSet) throws SQLException {
					return resultSet.getString("DOCSUBTYP");
				}
			}
			/**
			 * 
			 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO#findAWBUsageDetails(java.lang.String, java.lang.String, long, int)
			 *	Added by 			: A-6858 on 13-Mar-2018
			 * 	Used for 	:
			 *	Parameters	:	@param country
			 *	Parameters	:	@param companyCode
			 *	Parameters	:	@param masterDocumentNumber
			 *	Parameters	:	@param ownerID
			 *	Parameters	:	@return
			 *	Parameters	:	@throws PersistenceException
			 *	Parameters	:	@throws SystemException
			 */
		 	public ShipmentForBookingVO findAWBUsageDetails(String country,String companyCode,long 
					 masterDocumentNumber, int ownerID,String originDestinationIndicator)
			   		 throws PersistenceException, SystemException {
		 		log.entering("SQLDAO","findAWBUsageDetails-*****--"); 
		 		Query qry = getQueryManager().createNamedNativeQuery(
		 				StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_FIND_AWB_USAGEDETAILS);
		 		int index =0;
		 		qry.setParameter(++index,companyCode);
		 		qry.setParameter(++index,ownerID);
		 		qry.setParameter(++index,String.valueOf(masterDocumentNumber));
		 		if("O".equals(originDestinationIndicator)){
		 			qry.append(" (SELECT 1 FROM SHRARPMST WHERE CMPCOD=IDX.CMPCOD AND ARPCOD=IDX.ORGCOD AND CNTCOD= ? ) ");
		 		qry.setParameter(++index,country);
		   		}else{
		   			qry.append(" (SELECT 1 FROM SHRARPMST WHERE CMPCOD=IDX.CMPCOD AND ARPCOD=IDX.DSTCOD AND CNTCOD= ? ) ");
		   			qry.setParameter(++index,country);
		   		}
		 		qry.append(" ORDER BY LSTUPDTIM DESC ) SELECT * FROM IDX WHERE rnk =1 ");
		 		ShipmentForBookingVO shipmentForBookingVO = 
		 				qry.getSingleResult(new Mapper<ShipmentForBookingVO>(){
							public ShipmentForBookingVO map(ResultSet rs) throws SQLException {
								ShipmentForBookingVO shipmentForBookingVO = new ShipmentForBookingVO();
								shipmentForBookingVO.setShipmentStatus(rs.getString("SHPSTA"));
								LocalDate lastUpdateddate = new LocalDate(LocalDate.NO_STATION, Location.NONE, 
										rs.getDate("LSTUPDTIM"));
								shipmentForBookingVO.setLastUpdateTime(lastUpdateddate);
								return shipmentForBookingVO;
							}
		   				});
		 		log.exiting("SQLDAO","findAWBUsageDetails-*****--"); 
		   		return shipmentForBookingVO;	
			}
		 	
			public String saveStockDetails(StockAllocationVO stockAllocationVO)
					throws SystemException {
				log.entering("StockControlDefaultsSqlDAO", "saveStockDetails");
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				LocalDate currentDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, true);
			    int index = 0;
			    Procedure procedure = getQueryManager().createNamedNativeProcedure(
			    		StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_SAVE_STOCK_DETAILS);
			    procedure.setParameter(++index, stockAllocationVO.getCompanyCode());
			    procedure.setParameter(++index, stockAllocationVO.getAirlineIdentifier());
			    procedure.setParameter(++index, stockAllocationVO.getDocumentNumber());
			    procedure.setParameter(++index, stockAllocationVO.getDocumentType());
			    procedure.setParameter(++index, stockAllocationVO.getDocumentSubType());
			    procedure.setParameter(++index, findLong(stockAllocationVO.getDocumentNumber()));
			    procedure.setParameter(++index, "Y");
			    procedure.setParameter(++index, stockAllocationVO.getLastUpdateUser());
			    procedure.setParameter(++index, currentDate.toGMTDate().toSqlTimeStamp());
			    procedure.setOutParameter(++index, SqlType.STRING);
			    procedure.execute();
			    String processStatus = (String)procedure.getParameter(index);
			    return processStatus;
			}
			
			public boolean isStockDetailsExists(DocumentFilterVO documentFilterVO) throws PersistenceException, SystemException {
		    	int index = 0;
		        Query query = getQueryManager()
		                .createNamedNativeQuery(
		                		StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_FIND_STOCK_DETAILS);
		        query.setParameter(++index, documentFilterVO.getCompanyCode());
		        query.setParameter(++index, documentFilterVO.getAirlineIdentifier());
		        query.setParameter(++index, documentFilterVO.getDocumentNumber());
		        query.setParameter(++index, documentFilterVO.getDocumentType());
		        query.setParameter(++index, documentFilterVO.getDocumentSubType());
		        return query.getSingleResult(getStringMapper("MSTDOCNUM")) != null ? true:false;
		    }
	/**
	 * 	
	 * 	Method		:	StockControlDefaultsSqlDAO.saveStockRangeUtilisationLog
	 *	Added by 	:	A-8146 on 21-Aug-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param airlineIdentifier
	 *	Parameters	:	@param documentNumber
	 *	Parameters	:	@param documentType
	 *	Parameters	:	@param documentSubType
	 *	Parameters	:	@param lastUpdateUser
	 *	Parameters	:	@param lastUpdateTime
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	boolean
	 */
	@Override
	public void saveStockRangeUtilisationLog(String companyCode, int airlineIdentifier, String documentNumber,
			String documentType, String documentSubType, String lastUpdateUser, Calendar lastUpdateTime)
			throws PersistenceException, SystemException {
		int index = 0; 
		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_SAVE_STOCK_RANGE_UTILISATION_LOG);
		procedure.setSensitivity(true);
		procedure.setParameter(++index, companyCode);
		procedure.setParameter(++index, airlineIdentifier);
		procedure.setParameter(++index, documentNumber);
		procedure.setParameter(++index, documentType);
		procedure.setParameter(++index, documentSubType);
		procedure.setParameter(++index, lastUpdateUser);
		procedure.setParameter(++index, lastUpdateTime);
		procedure.setOutParameter(++index, SqlType.STRING);
	    procedure.execute();
	    String outParameter = (String)procedure.getParameter(index);
	    if(outParameter!=null && !"OK".equals(outParameter)) {
			throw new PersistenceException(outParameter);
		}
	    }
			
			public Page<RangeVO> findAvailableRanges(StockFilterVO stockFilterVO) throws SystemException,
		 	PersistenceException {
		 		StringBuilder rankQuery=new StringBuilder();
				rankQuery.append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_ROWNUM_QUERY);
				String nativeQuery = null;
				if(isOracleDataSource()) {
					nativeQuery = getQueryManager().getNamedNativeQueryString(
							STOCKCONTROL_DEFAULTS_FIND_FINDRANGESFORVIEWRANGE);
				} else {
					nativeQuery = getQueryManager().getNamedNativeQueryString(
							POSTGRES_STOCKCONTROL_DEFAULTS_FIND_FINDRANGESFORVIEWRANGE);
				}
				String baseQuery = rankQuery.append(nativeQuery).toString();
				PageableNativeQuery<RangeVO> pgqry = new PageableNativeQuery<RangeVO>(stockFilterVO.getTotalRecords(),
						baseQuery, new RangeForViewRangeMapper());
				pgqry.append(StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_SUFFIX_QUERY);
		 		int index =0;
		 		String manual = stockFilterVO.isManual() ? StockFilterVO.FLAG_YES
		 				:StockFilterVO.FLAG_NO ;
		 		pgqry.setParameter(++index,stockFilterVO.getCompanyCode());
		 		pgqry.setParameter(++index,stockFilterVO.getAirlineIdentifier());
		 		pgqry.setParameter(++index,stockFilterVO.getDocumentType());
		 		pgqry.setParameter(++index,stockFilterVO.getDocumentSubType());
		 		pgqry.setParameter(++index,stockFilterVO.getStockHolderCode());
		 		pgqry.setParameter(++index,manual);
		 		
		 		return pgqry.getPage(stockFilterVO.getPageNumber());
		 	}
			public int findTotalNoOfDocuments(StockFilterVO stockFilterVO) throws SystemException,
		 	PersistenceException {
		 		Query qry = getQueryManager().createNamedNativeQuery(
		 				StockControlDefaultsPersistenceConstants.STOCKCONTROL_DEFAULTS_FIND_FINDTOTALDOCS);
			String manual = stockFilterVO.isManual() ? StockFilterVO.FLAG_YES : StockFilterVO.FLAG_NO;
			int index = 0;
	      	qry.setParameter(++index,stockFilterVO.getCompanyCode());
	      	qry.setParameter(++index,stockFilterVO.getAirlineIdentifier());
	      	qry.setParameter(++index,stockFilterVO.getDocumentType());
	      	qry.setParameter(++index,stockFilterVO.getDocumentSubType());
	      	qry.setParameter(++index,stockFilterVO.getStockHolderCode());
	      	qry.setParameter(++index,manual);
	      	return qry.getSingleResult(getIntMapper("TOTDOCS"));
			}

	/**
	 *
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param documentNumber
	 * @param documentSubType
	 * @return
	 */
	public boolean checkIfAWBBlackListed(BlacklistStockVO blacklistStockVO)
			throws SystemException {
		log.entering("StockControl Sql DAO", "checkIfAWBBlackListed");

		String queryString = getQueryManager().getNamedNativeQueryString(STOCKCONTROL_DEFAULTS_IS_DOC_BLACKLISTED);
		 if(blacklistStockVO.getDocumentSubType() != null && !blacklistStockVO.getDocumentSubType().isEmpty()){
		 	queryString = queryString + " and docsubtyp = ?";
		 }
		Query query = getQueryManager().createNativeQuery(
				queryString);
		query.setParameter(1, blacklistStockVO.getCompanyCode());
		query.setParameter(2, blacklistStockVO.getDocumentType());
		query.setParameter(3, blacklistStockVO.getRangeFrom());
		query.setParameter(4, blacklistStockVO.getRangeTo());
		query.setParameter(5, blacklistStockVO.getRangeFrom());
		query.setParameter(6, blacklistStockVO.getRangeTo());
		if(blacklistStockVO.getDocumentSubType() != null && !blacklistStockVO.getDocumentSubType().isEmpty()){
			query.setParameter(7, blacklistStockVO.getDocumentSubType());
		}
		log.exiting("StockControl Sql DAO", "checkIfAWBBlackListed");
		return query.getSingleResult(getStringMapper("BLKSTA")) != null ? true:false;
	}

	/**
	 *
	 * @param companyCode
	 * @param documentNumber
	 * @param documentType
	 * @param documentSubType
	 * @return
	 * @throws SystemException
	 */
	public boolean checkIFAWBIsVoid(String companyCode,  String documentNumber,
									String documentType, String documentSubType) throws SystemException {
		log.entering("StockControl Sql DAO", "checkIFAWBIsVoid");

		String queryString = getQueryManager().getNamedNativeQueryString(STOCKCONTROL_DEFAULTS_IS_DOC_VOID);
		if(documentSubType != null && !documentSubType.isEmpty()){
			queryString = String.format(queryString," and docsubtyp = ? ") ;
		}
		else {
			queryString = String.format(queryString, " ");
		}
		Query query = getQueryManager().createNativeQuery(
				queryString);
		query.setParameter(1, companyCode);
		query.setParameter(2, documentType);
		query.setParameter(3, documentNumber);
		query.setParameter(4, documentNumber);
		query.setParameter(5, documentNumber);
		query.setParameter(6, documentNumber);
		if(documentSubType != null && !documentSubType.isEmpty()){
			query.setParameter(7,documentSubType);
		}
		log.exiting("StockControl Sql DAO", "checkIFAWBIsVoid");
		String status = query.getSingleResult(getStringMapper(STATUS));
		return  status!= null && status.equalsIgnoreCase("V") ? true : false;
	}

	@Override
	public Collection <Integer> removeStockReuseHistory(StockReuseHistoryVO stockReuseHistoryVO) throws SystemException {
		
		String query = getQueryManager().getNamedNativeQueryString(STOCKCONTROL_DEFAULTS_REMOVE_STOCK_REUSE_HISTORY);
		Query qry = getQueryManager().createNativeQuery(
				 query);
		int index = 0;
      	qry.setParameter(++index,stockReuseHistoryVO.getCompanyCode());
      	qry.setParameter(++index,stockReuseHistoryVO.getMasterDocumentNumber());
      	qry.setParameter(++index,stockReuseHistoryVO.getDocumentOwnerId());
      	qry.setParameter(++index,stockReuseHistoryVO.getDuplicateNumber());
      	qry.setParameter(++index,stockReuseHistoryVO.getSequenceNumber());
      	
      	return qry.getResultList(new StockReuseHistoryMapper());
	}
	private class StockReuseHistoryMapper implements Mapper<Integer> {
		
		public Integer map(ResultSet resultSet) throws SQLException {
			return resultSet.getInt("SERNUM");
		}
	}
	
	public StockReuseHistoryVO findAWBReuseHistoryDetails(DocumentFilterVO documentFilterVO,String countryCode)throws SystemException {
		
		String query = getQueryManager().getNamedNativeQueryString(STOCKCONTROL_DEFAULTS_FIND_STOCK_REUSE_HISTORY);
		Query qry = getQueryManager().createNativeQuery(
				 query);
		int index = 0;
		qry.setParameter(++index,documentFilterVO.getCompanyCode());
		qry.setParameter(++index,documentFilterVO.getAirlineIdentifier());
		qry.setParameter(++index,documentFilterVO.getDocumentNumber());
		qry.setParameter(++index,countryCode);		
		return qry.getSingleResult(new Mapper<StockReuseHistoryVO>(){
						public StockReuseHistoryVO map(ResultSet rs) throws SQLException {
							StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
							if(rs.getDate("CAPDAT")!=null){
							LocalDate captureDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, 
									rs.getDate("CAPDAT"));
							stockReuseHistoryVO.setCaptureDate(captureDate);						
							}
							return stockReuseHistoryVO;
						}
	   				});
 	}

}

