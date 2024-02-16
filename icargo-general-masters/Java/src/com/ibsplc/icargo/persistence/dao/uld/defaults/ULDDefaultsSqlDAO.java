/*
 * ULDDefaultsSqlDAO.java Created on Jan 5, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMNilIncomingULDDetailsVO;
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
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDNumberVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
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
import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ULDDefaultsSqlDAO extends AbstractQueryDAO implements
ULDDefaultsDAO { 

	private Log log = LogFactory.getLogger(" ULD_DEFAULTS");

	private static final String FLAG_YES = "true";

	private static final String FLAG_NO = "false";

	private static final String MODULE_NAME = "ULDDefaultsSqlDAO";

	private static final String LIST_ULDSTOCKCONFIG = "uld.defaults.listuldstockconfig";
	
	private static final String ULD_DEFAULTS_TOTDEMURRAGE = "uld.defaults.findtotaldemmurage";

	private static final String LOANED_TRANSACTION = "L";

	private static final String LOANEDREFNUMBER = "LONREFNUM";

	private static final String BORROWEDREFNUM = "BRWREFNUM";
	
	private static final String FIND_ALPHACODEUSE="uld.defaults.findalphacodeinuse";
	
	// Added by Preet for AirNZ 517 --starts
	private static final String AGENT = "G";
	
	private static final String AIRLINE = "A";
	
	private static final String OTHER = "O";
	
	private static final String CUSTOMER = "C";
	
	private static final String PLACEHOLDER1 = "placeholder1";

	
	// Added by Preet for AirNZ 517 --ends
	
	//Added by a-3278 for bug 18347 on 05Oct08
	private static final String BLANK = "*";
	//a-3278 ends

	//added by a-4752 as part of icrd-3733 
	private static final String SECTION_PARAM = "uld.defaults.section";
	private static final String ULD_DEFAULTS_DENSE_RANK_QUERY=
    	" SELECT RESULT_TABLE.* ,DENSE_RANK() OVER ( ORDER BY ";
	private static final String ULD_DEFAULTS_RANK_QUERY_DESC=
 		" RESULT_TABLE.TOTAL_BALANCE desc,RESULT_TABLE.ARLCOD,RESULT_TABLE.ARPCOD,RESULT_TABLE.ULDGRPCOD) RANK FROM ( ";
	private static final String ULD_DEFAULTS_RANK_QUERY_ASC=
 		" RESULT_TABLE.TOTAL_BALANCE asc,RESULT_TABLE.ARLCOD,RESULT_TABLE.ARPCOD,RESULT_TABLE.ULDGRPCOD) RANK FROM ( ";
	private static final String ULD_DEFAULTS_RANK_QUERY=
 		" RESULT_TABLE.ARLCOD,RESULT_TABLE.ARPCOD,RESULT_TABLE.ULDGRPCOD) RANK FROM ( ";
	private static final String ULD_ERRORS_RANK_QUERY=
 		" RESULT_TABLE.CMPCOD,RESULT_TABLE.ARPCOD,RESULT_TABLE.ARLIDR,RESULT_TABLE.SEQNUM,RESULT_TABLE.ULDNUM) RANK FROM ( ";
	private static final String ULD_SCM_RANK_QUERY=
 		" RESULT_TABLE.CMPCOD DESC, RESULT_TABLE.ARPCOD DESC,RESULT_TABLE.ARLIDR DESC,CAST(RESULT_TABLE.SEQNUM AS INT) DESC) RANK FROM ( ";
	private static final String ULD_SORTING_ORDER_DESC ="DESC";
	private static final String ULD_SORTING_ORDER_ASC ="ASC";
	 public static final String ULD_DEFAULTS_SUFFIX_QUERY=") RESULT_TABLE";
	 private static final Log LOGGER = LogFactory.getLogger(ULDDefaultsSqlDAO.class.getSimpleName());

	/**
	 * This method retrieves the repair head details for invoicing.
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param invoiceRefNumber
	 * @return ULDRepairInvoiceVO
	 * @throws SystemException
	 */
	public ULDRepairInvoiceVO findRepairInvoiceDetails(String companyCode,
			String invoiceRefNumber) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findRepairInvoiceDetails");
		log.log(Log.FINE, "\n invoiceRefNumber", invoiceRefNumber);
		Query query = getQueryManager()
		.createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_INVOICE_DETAILS_FOR_REPAIR);
		query.setParameter(1, companyCode);
		query.setParameter(2, invoiceRefNumber);
		ULDRepairInvoiceVO uLDRepairInvoiceVO = query
		.getSingleResult(new InvoiceDetailsForRepairMapper());
		log.log(Log.FINE, "Collected Invoice info For Repair ");
		Query qry = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_REPAIR_INVOICE_DETAILS);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, invoiceRefNumber);
		Collection<ULDRepairInvoiceDetailsVO> uLDRepairInvoiceDetailsVOs = qry
		.getResultList(new ULDRepairInvoiceDetailsMultiMapper());
		uLDRepairInvoiceVO
		.setULDRepairInvoiceDetailsVOs(uLDRepairInvoiceDetailsVOs);
		log.log(Log.FINE, "ULDRepairInvoiceVO From server", uLDRepairInvoiceVO);
		log.exiting("ULDDefaultsSqlDAO", "findRepairInvoiceDetails");
		return uLDRepairInvoiceVO;
	}

	
	/**
	 * InvoiceDetailsForRepairMapper.java Created on Dec 19, 2005
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	private class InvoiceDetailsForRepairMapper implements
	Mapper<ULDRepairInvoiceVO> {
		/**
		 * @param resultSet
		 * @return ULDRepairInvoiceVO
		 * @throws SQLException
		 */
		public ULDRepairInvoiceVO map(ResultSet resultSet) throws SQLException {
			log.entering("InvoiceDetailsForRepairMapper", " map");
			ULDRepairInvoiceVO uLDRepairInvoiceVO = new ULDRepairInvoiceVO();
			Date date = resultSet.getDate("INVDAT");
			if (date != null) {
				uLDRepairInvoiceVO.setInvoiceDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, date));
			}
			uLDRepairInvoiceVO.setInvoiceToCode(resultSet.getString("INVCOD"));
			uLDRepairInvoiceVO.setInvoiceToName(resultSet
					.getString("INVCODNAM"));
			log.exiting("InvoiceDetailsForRepairMapper", " map");
			return uLDRepairInvoiceVO;

		}
	}

	/**
	 * This method validates if the ULD exists in the system
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param uldNumber
	 * @return ULDValidationVO
	 * @throws SystemException
	 */
	public ULDValidationVO validateULD(String companyCode, String uldNumber)
	throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "validateULD");
		ULDValidationVO uldValidationVO = null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.VALIDATE_ULD);
		query.setParameter(++index, uldNumber);
		query.setParameter(++index, companyCode);
		uldValidationVO = query.getSingleResult(new ULDValidationMapper());
		log.log(Log.FINE, "inside SqlDAO..uldValidationVO-->", uldValidationVO);
		log.exiting("ULDDefaultsSqlDAO", "validateULD");
		return uldValidationVO;
	}

	/**
	 * This method is used to list the damage reports according to the specified
	 * filter criteria
	 *
	 * @param uldDamageFilterVO
	 * @return Collection<ULDDamageDetailsListVO>
	 * @throws SystemException
	 */
	public Page<ULDDamageDetailsListVO> findULDDamageList(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findULDDamageList");
		//added by A-5223 for ICRD-22824 starts
	      StringBuilder rankQuery = new StringBuilder();
	      rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.FIND_ULDDAMAGE_LIST);
		log.log(Log.INFO, "!!!!!!!!!baseQuery", baseQuery);
		rankQuery.append(baseQuery);
	      PageableNativeQuery<ULDDamageDetailsListVO> pgqry = 
				new ListDamageReportFilterQuery(new ULDDamageListMapper(),uldDamageFilterVO,rankQuery.toString());
	      pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
	    //added by A-5223 for ICRD-22824 ends
		/*Query query = new ListDamageReportFilterQuery(uldDamageFilterVO,
				baseQuery);
		PageableQuery<ULDDamageDetailsListVO> pgqry = new PageableQuery<ULDDamageDetailsListVO>(
					query, new ULDDamageListMapper());*/
		return pgqry.getPage(uldDamageFilterVO.getPageNumber());

	}

	/**
	 * @param companyCode
	 * @param uldNumber
	 * @param pageNumber
	 * @return Page
	 * @throws SystemException
	 */
	public Page<ULDDamageReferenceNumberLovVO> findULDDamageReferenceNumberLov(
			String companyCode, String uldNumber, int pageNumber)
			throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findULDDamageReferenceNumberLov");
		
		// Modified by A-5280 for ICRD-32647
		PageableNativeQuery<ULDDamageReferenceNumberLovVO> pgqry = null;
		String query = null;
    	
    	 query = getQueryManager().getNamedNativeQueryString(ULDDefaultsPersistenceConstants.FIND_ULDDAMAGEDREFERENCENUMBER_LOV);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(query);
		// Added by A-5280 for ICRD-32647 ends
		// Modified by A-5280 for ICRD-32647
	    pgqry = new PageableNativeQuery<ULDDamageReferenceNumberLovVO>(0,
	    		rankQuery.toString(), new ULDDamageReferenceNumberLovMapper());
		
	    pgqry.setParameter(1, companyCode);
		if (uldNumber != null && uldNumber.trim().length() > 0) {
			pgqry.append(" AND ULDNUM = ? ");
			pgqry.setParameter(2, uldNumber);
		}
		//Added as a part of Bug ICRD-318056
		pgqry.append("ORDER BY DMGREFNUM");
		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 ends
		Page<ULDDamageReferenceNumberLovVO> page = pgqry.getPage(pageNumber);

		log.log(Log.INFO, "!!!!!! Page<ULDDamageReferenceNumberLovVO>", page);
		return page;
	}

	/**
	 * @param uldRepairFilterVO
	 * @return Page<ULDDamageRepairDetailsVO>
	 * @throws SystemException
	 */
	public Page<ULDRepairDetailsListVO> listULDRepairDetails(
			ULDRepairFilterVO uldRepairFilterVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "listULDRepairDetails");
		StringBuilder rankQuery = new StringBuilder();//Added by A-5214 as part from the ICRD-22824
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.LIST_ULDREPAIRDETAILS);
		//Added by A-5214 as part from the ICRD-22824 STARTS  
        rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
        rankQuery.append(baseQuery);  
        //Added by A-5214 as part from the ICRD-22824 ENDS
        PageableNativeQuery<ULDRepairDetailsListVO> query = new ULDRepairFilterQuery(uldRepairFilterVO, rankQuery.toString(),new ULDRepairListMapper());
		
		return query.getPage(uldRepairFilterVO.getPageNumber());
	}

	/**
	 * This method is used to list the damage details for the specified damage
	 * reference number for a particular ULD.
	 *
	 * @param uldDamageFilterVO
	 * @return
	 * @throws SystemException
	 */
	public ULDDamageRepairDetailsVO findULDDamageDetails(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException {
		ULDDamageRepairDetailsVO damageRepairVO = null;
		log.entering("ULDDefaultsSqlDAO", "findULDDamageDetails");
		Query qry = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULDDAMAGEDETAILS);
		qry.setParameter(1, uldDamageFilterVO.getCompanyCode());
		qry.setParameter(2, uldDamageFilterVO.getUldNumber());
		List<ULDDamageRepairDetailsVO> damageVOs = qry
		.getResultList(new ULDDamageDetailsMultiMapper());
		if (damageVOs != null && damageVOs.size() > 0) {
			damageRepairVO = damageVOs.get(0);
		}
		log.log(Log.INFO, "!!!!!ULDDamageRepairDetailsVO", damageRepairVO);
		return damageRepairVO;
	}

	/**
	 * This method retrieves the uld movement history
	 *
	 * @param uldMovementFilterVO
	 * @param displayPage
	 * @return Page<ULDMovementDetailVO>
	 * @throws SystemException
	 */
	public Page<ULDMovementDetailVO> findULDMovementHistory(
			ULDMovementFilterVO uldMovementFilterVO, int displayPage)
			throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findULDMovementHistory");
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		String baseQry = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.ULD_FIND_ULDMVTDTL);
		String qQuery = rankQuery.append(baseQry).toString();
		//Query query = new ULDMovementFilterQuery(uldMovementFilterVO, baseQry);
		/*PageableQuery<ULDMovementDetailVO> pgqry = new PageableQuery<ULDMovementDetailVO>(
					query, new ULDMovementHistoryMapper());*/
		PageableNativeQuery<ULDMovementDetailVO> pgqry = new ULDMovementFilterQuery(uldMovementFilterVO, qQuery, new ULDMovementHistoryMapper());
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		log.exiting("ULDDefaultsSqlDAO", "findULDMovementHistory");
		return pgqry.getPage(displayPage);
	}

	/**
	 * This method retrieves the uld details of the specified filter condition
	 *
	 * @param accessoriesStockConfigFilterVO
	 * @param displayPage
	 * @return Page<AccessoriesStockConfigListVO>
	 * @throws SystemException
	 */
	public Page<AccessoriesStockConfigVO> findAccessoryStockList(
			AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO,
			int displayPage) throws SystemException {
		
		//Added By A-5183 For < ICRD-22824 > Starts
		log.entering("ULDDefaultsSqlDAO", "findAccessoryStockList");
		
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);		
		String query = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.ULD_FIND_ULDACCSTK_LIST);
		String baseQuery = rankQuery.append(query).toString();
		PageableNativeQuery<AccessoriesStockConfigVO> pgqry = new ListAccessoriesStockFilterQuery(accessoriesStockConfigFilterVO,baseQuery,
				new AccessoriesStockMapper());
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		
		/*PageableQuery<AccessoriesStockConfigVO> pgqry = null;
		Query query = new ListAccessoriesStockFilterQuery(
				accessoriesStockConfigFilterVO, baseQry);
		pgqry = new PageableQuery<AccessoriesStockConfigVO>(query,
				new AccessoriesStockMapper());*/
		log.exiting("ULDDefaultsSqlDAO", "findAccessoryStockList");
		return pgqry.getPage(displayPage);
		
	    //Added By A-5183 For < ICRD-22824 > Ends
	}

	/**
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<ULDListVO> findULDList(ULDListFilterVO uldListFilterVO,
			int pageNumber) throws SystemException {
		log.entering(MODULE_NAME, "findULDList");
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		/*if(uldListFilterVO.isFromListULD()){
			rankQuery.append("  WITH LSTULD AS(")	;
		}*/
		String query = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FIND_ULD_LIST);
		String baseQuery = rankQuery.append(query).toString();
		//Commented by a-3459 as part of QF CR 1186 starts
		/*Query qry = new ULDListFilterQuery(uldListFilterVO, baseQuery);
		PageableQuery<ULDListVO> pgqry = new PageableQuery<ULDListVO>(qry,
				new ULDListMapper());
		*/	
		//Commented by a-3459 as part of QF CR 1186 ends
		//added by a-3459 as part of QF CR 1186 on 24-12-2008
		PageableNativeQuery<ULDListVO> pgqry = new ULDListPaginationFilterQuery(
				uldListFilterVO.getTotalRecords(),baseQuery,new ULDListMapper(),uldListFilterVO,isOracleDataSource());
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		return pgqry.getPage(pageNumber);

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
			int airlineIdentifier) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findAccessoriesStockDetails");
		AccessoriesStockConfigVO accessoriesStockConfigVO = null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.ULD_FIND_ULDACCSTK_DETAILS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, accessoryCode);
		query.setParameter(++index, stationCode);
		query.setParameter(++index, airlineIdentifier);
		accessoriesStockConfigVO = query
		.getSingleResult(new AccessoriesStockMapper());
		log.exiting("ULDDefaultsSqlDAO", "findAccessoriesStockDetails");
		return accessoriesStockConfigVO;
	}

	/**
	 * @param companyCode
	 * @param uldNumber
	 * @return ULDValidationVO
	 * @throws SystemException
	 */
	public ULDValidationVO findCurrentULDDetails(String companyCode,
			String uldNumber) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findCurrentULDDetails");
		ULDValidationVO uldValidationVO = null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_CURRENTULDDETAILS);
		query.setParameter(++index, uldNumber);
		query.setParameter(++index, companyCode);
		uldValidationVO = query.getSingleResult(new ULDValidationMapper());
		log.log(Log.FINE, "inside sql..uldValidationVO-->", uldValidationVO);
		log.exiting("ULDDefaultsSqlDAO", "findCurrentULDDetails");
		return uldValidationVO;
	}

	/**
	 * This method validates the format of the specified ULD
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @throws SystemException
	 */
	public void validateULDFormat(String companyCode, String uldNumber)
	throws SystemException {

	}

	/**
	 * This method retrieves the details of the specified ULD
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ULDVO findULDDetails(String companyCode, String uldNumber)
	throws SystemException, PersistenceException {
		int index = 0;
		ULDVO uldVo = null;
		log.entering("INSIDE THE SQLDAO", "findULDDetails");
		log.log(Log.FINE, "uldNumber", uldNumber);
		log.log(Log.FINE, "companyCode", companyCode);
		Query qry = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FINDULDDETAILS);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, uldNumber);
		log.log(Log.FINE, "Query", qry);
		List<ULDVO> uldVos = qry.getResultList(new ULDDetailsMapper());
		log.log(Log.FINE, "uldVos", uldVos);
		if (uldVos != null && uldVos.size() > 0) {
			uldVo = uldVos.get(0);
		}
		log.log(Log.FINE, "uldVo", uldVo);
		return uldVo;
	}

	/**
	 * This method retrieves the structural details defined at the ULD Type
	 * specified for the ULD
	 *
	 * @param companyCode
	 * @param uldType
	 * @return
	 * @throws SystemException
	 */
	// public ULDTypeVO findULDTypeStructuralDetails(
	// String companyCode, String uldType)
	// throws SystemException{
	// return null;
	// }
	/**
	 * This method checks if there already exists a manufaturer-serial number
	 * combination for any ULD
	 *
	 * @param uldVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkDuplicateManufacturerNumber(ULDVO uldVo)
	throws SystemException, PersistenceException {
		int index = 0;
		boolean isDuplicateManufacturerNumber = false;
		log.entering("INSIDE THE SQLDAO", "checkDuplicateManufacturerNumber");
		Query qry = getQueryManager()
		.createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.CHECK_DUPLICATE_MANUFACTURER_NUMBER);
		if(uldVo.getManufacturer() != null){
		qry.setParameter(++index, uldVo.getManufacturer());
		qry.setParameter(++index, uldVo.getUldSerialNumber());
		Mapper<String> stringMapper = getStringMapper("CMPCOD");
		String companyCode = qry.getSingleResult(stringMapper);
		if (companyCode != null) {
			isDuplicateManufacturerNumber = true;
		}
		}
		log.log(Log.FINE, "checkDuplicateManufacturerNumber",
				isDuplicateManufacturerNumber);
		return isDuplicateManufacturerNumber;
	}

	/**
	 * This method checks if the uld is currently loaned or borrowed
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkULDInTransaction(String companyCode, String uldNumber)
	throws SystemException, PersistenceException {
		int index = 0;
		log.entering("INSIDE THE SQLDAO", "checkULDInTransaction");
		Query qry = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.CHECK_ULD_IN_TRANSACTION);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, uldNumber);

		boolean isULDInTransaction = FLAG_YES.equals(qry
				.getSingleResult(new ULDInTransactionMapper()));
		log.log(Log.FINE, "isULDInTransaction>>>>>>>>>>>", isULDInTransaction);
		return isULDInTransaction;
	}

	/**
	 * This method checks if the Agreement is Overlapping
	 *
	 * @param uldAgreementVO
	 * @return Collection<ULDAgreementExceptionVO>
	 * @throws SystemException
	 */
	public Collection<ULDAgreementExceptionVO> checkULDAgreementDetails(
			ULDAgreementVO uldAgreementVO) throws SystemException {
		log.entering("INSIDE THE SQLDAO", "findULDAgreementDetails");

		log.log(Log.INFO, "!!!!!!!ULDAgreementVO", uldAgreementVO);
		String baseQry = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.ULDAGREEMENT_ALREADY_EXISTS);
		log.log(Log.FINE, " Base Query !!!!!!!!!!!!=", baseQry);
		Query qry = new AgreementOverLapFilterQuery(uldAgreementVO, baseQry);
		log.log(Log.INFO, "!!!!!!!Query:::", qry);
		Collection<ULDAgreementExceptionVO> coll = qry
		.getResultList(new ULDAgreementExceptionMapper());
		log.log(Log.INFO, "!!!!!Collection", coll);
		return coll;
	}

	/**
	 * This methd checks if the ULD is either loaned/borrowed or if the
	 * manufacturer-serial number combination already exists for the ULD
	 *
	 * @param uldVo
	 * @return
	 * @throws SystemException
	 */
	public boolean validateULDForModification(ULDVO uldVo)
	throws SystemException {
		return false;
	}

	/**
	 * This method is used to retrieve the ULDType details based on the filter.
	 *
	 * @param String
	 *            companyCode
	 * @param String
	 *            uldTypeCode
	 * @return ULDTypeVO
	 * @throws SystemException
	 */

	// public ULDTypeVO findULDType(String companyCode, String uldTypeCode)
	// throws SystemException{
	// return null;
	// }
	/**
	 * This method is used to list the details of the specified ULD Group
	 *
	 * @param companyCode
	 * @param uldGroupCode
	 * @return ULDGroupVO
	 * @throws SystemException
	 */
	// public ULDGroupVO findULDGroupDetails(String companyCode, String
	// uldGroupCode)
	// throws SystemException {
	// return null;
	// }
	/**
	 * This method is used to retrieve the ULDs associated to a Company and
	 * ULDType.
	 *
	 * @param companyCode
	 * @param uldTypeCode
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public Collection<String> findAssociatedULDs(String companyCode,
			String uldTypeCode) throws SystemException {
		return null;
	}

	/**
	 * This method is used to check if ULD Types exists for the specified ULD
	 * Group
	 *
	 * @param companyCode
	 * @param uldGroupCode
	 * @return Collection<ULDTypeVO>
	 * @throws SystemException
	 */
	public Collection<String> findULDTypesForGroup(String companyCode,
			String uldGroupCode) throws SystemException {
		return null;
	}

	/**
	 * This method is used to populate the ULDGroups associated to a Company in
	 * the system.
	 *
	 * @param companyCode
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public Collection<String> populateULDGroupLOV(String companyCode)
	throws SystemException {
		return null;

	}

	/**
	 * This method retrieves all the ULD types for the sppecified group
	 *
	 * @param companyCode
	 * @param uldGroupCode
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public Collection<String> findULDTypesByGroup(String companyCode,
			String uldGroupCode) throws SystemException {
		return null;
	}

	/**
	 * This method validates the ULD type specified
	 *
	 * @param companyCode
	 * @param uldType
	 * @return boolean
	 * @throws SystemException
	 */
	public boolean validateULDType(String companyCode, String uldType)
	throws SystemException {

		return false;
	}

	/**
	 * This method is used to retrive the ULDs associated to a ULDType.
	 *
	 * @param companyCode
	 * @param uldTypeCode
	 * @return Collection
	 * @throws SystemException
	 */
	public Collection<String> findULDsForType(String companyCode,
			String uldTypeCode) throws SystemException {
		return null;
	}

	/**
	 * This method is used to populate the ULDTypes associated to a Company in
	 * the system.
	 *
	 * @param companyCode
	 * @return Collection<String>
	 * @throws SystemException
	 *
	 */
	public Collection<String> populateULDTypeLOV(String companyCode)
	throws SystemException {
		return null;
	}

	/**
	 * This method is used to find the ULDType details
	 *
	 * @param companyCode
	 * @param agreementNumber
	 * @return ULDAgreementVO
	 * @throws SystemException
	 */
	public ULDAgreementVO findULDAgreementDetails(String companyCode,
			String agreementNumber) throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		ULDAgreementVO uldAgreementVO = null;
		int index = 0;
		Query qry = getQueryManager()
				.createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FIND_ULD_AGMNT_DTLS);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, agreementNumber);
		List<ULDAgreementVO> uldAgreementVOs = qry
				.getResultList(new ULDAgreementDetailsMultiMapper(
						logonAttributes.getAirportCode()));
		if (uldAgreementVOs != null && uldAgreementVOs.size() > 0) {
			uldAgreementVO = uldAgreementVOs.get(0);
		}
		return uldAgreementVO;

	}

	//Added by A-8445 as a part of IASCB-28460 Starts
	public Page<ULDAgreementDetailsVO> findULDAgreementDetailsPagination(String companyCode,
			String agreementNumber,ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		String query = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);	
		
		if(uldAgreementFilterVO.getUldTypeCodeFilter() != null 
    			&& uldAgreementFilterVO.getUldTypeCodeFilter().trim().length() > 0){
			query = getQueryManager().getNamedNativeQueryString(
					ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FIND_ULD_AGMNT_FILTER_DTLS);
		} else {
		   query = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FIND_ULD_AGMNT_DTLS);
		}
		String baseQuery = rankQuery.append(query).toString();	
		PageableNativeQuery<ULDAgreementDetailsVO> pgqry = new ULDAgreementDetailsPaginationFilterQuery(uldAgreementFilterVO,baseQuery,new ULDAgreementPageDetailsMultiMapper(logonAttributes.getAirportCode()));
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		return pgqry.getPage(uldAgreementFilterVO.getPageNumber());
	}
	//Added by A-8445 as a part of IASCB-28460 Ends 
	
	/**
	 * This method is used for listing uld agreement in the system
	 *
	 * @param uldAgreementFilterVO
	 * @return Page<ULDAgreementVO>
	 * @throws SystemException
	 */
	public Page<ULDAgreementVO> listULDAgreements(
			ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "listULDAgreements");
		//Added By A-5183 For < ICRD-22824 > Starts
		
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);		
		String query = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.LIST_ULDAGREEMENT);
		String baseQuery = rankQuery.append(query).toString();		
		PageableNativeQuery<ULDAgreementVO> pageQuery = new ULDAgreementFilterQuery(uldAgreementFilterVO,baseQuery,new ULDAgreementMapper());
		pageQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		return pageQuery.getPage(uldAgreementFilterVO.getPageNumber());
		/*
		Query query = new ULDAgreementFilterQuery(uldAgreementFilterVO,
				baseQuery);
		PageableQuery<ULDAgreementVO> pageQuery = new PageableQuery<ULDAgreementVO>(
					query, new ULDAgreementMapper());
		return pageQuery.getPage(uldAgreementFilterVO.getPageNumber());*/		
		
		 //Added By A-5183 For < ICRD-22824 > Ends
	}

	/**
	 * This method is used for populating the ULD Agreements
	 *
	 * @param companyCode
	 * @param pageNumber
	 * @param agreementNo
	 * @return Page
	 * @throws SystemException

mergingissue
	 */
	public Page<ULDAgreementVO> populateULDAgreementLOV(ULDAgreementFilterVO uldAgreementFilterVO)
	throws SystemException {
		log.entering("INSIDE THE SQLDAO", "populateULDAgreementLOV");
		//log.log(Log.INFO, "!!!!!!!COMPANY CODE" + companyCode);
		//changed and added by a-3045 for CR QF1154 starts
		// Modified by A-5280 for ICRD-32647
		PageableNativeQuery<ULDAgreementVO> pgqry = null;
		String query = null;
    	
    	 query = getQueryManager().getNamedNativeQueryString(ULDDefaultsPersistenceConstants.POPULATE_ULDAGREEMENTLOV);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(query);
		// Added by A-5280 for ICRD-32647 ends
		
		// Modified by A-5280 for ICRD-32647
		pgqry = new PageableNativeQuery<ULDAgreementVO>(0,
				rankQuery.toString(), new ULDAgreementMapper());
		
		int index = 0;
		pgqry.setParameter(++index, uldAgreementFilterVO.getCompanyCode());
		String agreementNo = null;
		String ptyName = null;
		String ptyCode = null;
		if (uldAgreementFilterVO.getAgreementNumber() != null) {
			agreementNo = uldAgreementFilterVO.getAgreementNumber().replace('*', '%');
			pgqry.append(" AND AGRMNTNUM LIKE ? ");
			pgqry.setParameter(++index, new StringBuilder().append(agreementNo).append(
					"%").toString());
		}
		if(uldAgreementFilterVO.getPartyCode() != null){
			ptyCode = uldAgreementFilterVO.getPartyCode().replace('*', '%');
			pgqry.append(" AND PTYCOD LIKE ?");
			pgqry.setParameter(++index,new StringBuilder().append(ptyCode).append(
			"%").toString());
		}
		if(uldAgreementFilterVO.getPartyName() != null){
			ptyName = uldAgreementFilterVO.getPartyName().replace('*', '%');
			pgqry.append(" AND PTYNAM LIKE ?");
			pgqry.setParameter(++index,new StringBuilder().append(ptyName).append(
					"%").toString());
		}
		pgqry
				.append(" ORDER BY TO_NUMBER(SUBSTR(AGRMNTNUM,4,LENGTH(AGRMNTNUM))) ");
		
		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 starts
	
		log.exiting("ULDDefaultsSqlDAO", "populateULDAgreementLOV");
		return pgqry.getPage(uldAgreementFilterVO.getPageNumber());
		//changed and added by a-3045 for CR QF1154 ends
	}

	/**
	 * This method is used to list the ULD Set up Configuration
	 *
	 * @param uldStockConfigFilterVO
	 * @return Collection
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ULDStockConfigVO> listULDStockConfig(
			ULDStockConfigFilterVO uldStockConfigFilterVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listULDStockConfig");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				LIST_ULDSTOCKCONFIG);
		
		//if(!uldStockConfigFilterVO.getViewByNatureFlag()){
			query.append(" , SHRULDTYPMST C ");
		//}		
		query.append(" WHERE  B.CMPCOD = A.CMPCOD AND  B.ARLIDR = A.ARLIDR " );
		
		//if(!uldStockConfigFilterVO.getViewByNatureFlag()){
			query.append(" AND A.CMPCOD = C.CMPCOD ");
			query.append(" AND A.ULDTYPCOD = C.ULDTYPCOD ");
		//}
		query.append(" AND A.CMPCOD = ? AND A.ARPCOD = ?");
		
		query.setParameter(++index, uldStockConfigFilterVO.getCompanyCode());
		query.setParameter(++index, uldStockConfigFilterVO.getStationCode());

		if (uldStockConfigFilterVO.getAirlineIdentifier() != 0) {
			query.append(" AND A.ARLIDR = ? ");
			query.setParameter(++index, uldStockConfigFilterVO
					.getAirlineIdentifier());
		}
		//if(!uldStockConfigFilterVO.getViewByNatureFlag()){
			if(uldStockConfigFilterVO.getUldGroupCode() != null && 
					uldStockConfigFilterVO.getUldGroupCode().trim().length() > 0){
				query.append(" AND C.ULDGRPCOD = ? ");
				query.setParameter(++index, uldStockConfigFilterVO.getUldGroupCode());
			
			}
		//}else{
			if((uldStockConfigFilterVO.getUldNature() != null
					&& uldStockConfigFilterVO.getUldNature().trim().length() > 0) &&
					!("ALL".equalsIgnoreCase(uldStockConfigFilterVO.getUldNature()))){
				query.append(" AND A.ULDNAT = ? ");
				query.setParameter(++index, uldStockConfigFilterVO.getUldNature());
			}
			if(uldStockConfigFilterVO.getUldTypeCode()!=null 
				&& uldStockConfigFilterVO.getUldTypeCode().trim().length()> 0){
					query.append(" AND A.ULDTYPCOD= ? ");
					query.setParameter(++index, uldStockConfigFilterVO.getUldTypeCode());
				}
			
		//}
		Collection<ULDStockConfigVO> coll = query
		.getResultList(new ULDStockConfigMapper());
		log.log(Log.INFO, "!!!!Collection<ULDStockConfigVO>", coll);
		return coll;
	}

	/**
	 * This method is used to find the ULD Set up Configuration
	 *
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param stationCode
	 * @param uldType
	 * @return ULDStockConfigVO
	 * @throws SystemException
	 */
	public ULDStockConfigVO findULDStockConfig(String companyCode,
			int airlineIdentifier, String stationCode, String uldType)
	throws SystemException {
		return null;
	}

/**
 * @param companyCode
 * @param uldNumber
 * @param stationCode
 * @param airlineIdentifier
 * @param displayPage
 * @param isAirlineUser
 * @return Collection<String>
 * @throws SystemException
 * @throws PersistenceException
 */
	public Page<String> findStationUlds(String companyCode,String uldNumber,
			String stationCode,String transactionType, int airlineIdentifier,int displayPage)
			throws SystemException, PersistenceException {
		
		int index = 0;
		//Modified by A-5220 for ICRD-32647 starts
		String qry = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.FIND_STATION_ULDS);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.
				ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(qry);
		Mapper<String> stringMapper = getStringMapper("ULDNUM");
		//Collection<String> ulds = qry.getResultList(stringMapper);
		PageableNativeQuery<String> pgqry =
			new PageableNativeQuery<String>(0, 
					rankQuery.toString(), stringMapper);
		//Modified by A-5220 for ICRD-32647 ends
		pgqry.setParameter(++index, companyCode);
		pgqry.setParameter(++index, stationCode);
		
		pgqry.append(" AND OPRARLIDR = ? ");
		pgqry.setParameter(++index, airlineIdentifier);
		if(LOANED_TRANSACTION.equals(transactionType)){
			pgqry.append(" AND  LONREFNUM =0 ");			
		}
		log.log(Log.INFO, "%%%%%%%%%%%% uldNumber", uldNumber);
		if(uldNumber != null && uldNumber.trim().length() > 0){
			uldNumber = new StringBuilder(uldNumber).append("*").toString();
			log.log(Log.INFO, "%%%%%%%%%%%% uldNumber", uldNumber);
			String uldNum = uldNumber.replace('*','%');
			log.log(Log.INFO, "%%%%%%%%%%%% uldNum", uldNum);
			log.log(Log.INFO, "%%%%%%%%%%%% uldNumber", uldNumber);
			pgqry.append(" AND ULDNUM LIKE ? " );
			pgqry.setParameter(++index,new StringBuilder(uldNum).append("%").toString());
		}
		pgqry.append(" ORDER BY ULDNUM");
		log.log(Log.INFO, "%%%%%%%%%%%% qry", qry);
		log.log(Log.INFO, "%%%%%%%%%%%% uldNumber", uldNumber);
		//qry.setParameter(++index,companyCode);				
		//return ulds;
		//Modified by A-5220 for ICRD-32647 starts
		pgqry.append(ULDDefaultsPersistenceConstants.
				ULD_DEFAULTS_SUFFIX_QUERY);
		//Modified by A-5220 for ICRD-32647 ends
		return pgqry.getPage(displayPage);		
	}


	/**
	 * This method is used to list Accessories at a purticular station
	 *
	 * @param companyCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @return Collection
	 * @throws SystemException
	 */
	public Collection<String> findStationAccessories(String companyCode,
			String stationCode, int airlineIdentifier) throws SystemException {
		return null;
	}

	/**
	 * This method is used for listing uld agreement in the system for a ULD
	 * Transaction
	 *
	 * @param uldAgreementFilterVO
	 * @return Page<ULDAgreementVO>
	 * @throws SystemException
	 */
	public Page<ULDAgreementVO> findULDAgreementsForTransaction(
			ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException {
		return null;
	}

	/**
	 * This method is used for listing uld transaction
	 *
	 * @author A-1883
	 * @param uldTransactionFilterVO
	 * @return TransactionListVO
	 * @throws SystemException
	 */

	public TransactionListVO listULDTransactionDetails(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "listULDTransactionDetails");
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.LIST__LOANBORROWENQUIRY);
		String baseQuery2;
		if(Objects.equals(ULDDefaultsPersistenceConstants.FLAG_YES, uldTransactionFilterVO.getIsAgreementListingRequired())){
			baseQuery2 = getQueryManager().getNamedNativeQueryString(ULDDefaultsPersistenceConstants.LIST_LOANDEMURAGEENQ);
		}else{
			baseQuery2 = getQueryManager().getNamedNativeQueryString(ULDDefaultsPersistenceConstants.LIST_LOANBORROWENQUIRYWITHAGRDTL);
		}
		PageableNativeQuery<ULDTransactionDetailsVO> pageQuery = new ULDLoanBorrowFilterQuery(
				uldTransactionFilterVO.getTotalRecord(), new ListULDTransactionMapper(),uldTransactionFilterVO,baseQuery,baseQuery2,isOracleDataSource());
		TransactionListVO transactionListVO = new TransactionListVO();
		transactionListVO.setTransactionDetailsPage(
				pageQuery.getPage(uldTransactionFilterVO.getPageNumber()));
		return transactionListVO;
	}
	
	/**
	 * This method is used for showing the Grand total Demmurage amount
	 *
	 * @author A-3278
	 * @param uldTransactionFilterVO
	 * @return ULDTransactionDetailsVO
	 * @throws SystemException
	 */

	public ULDTransactionDetailsVO findTotalDemmurage(TransactionFilterVO transactionFilterVO)
			throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findTotalDemmurage");
		log.log(Log.FINE, "**********transactionFilterVO*********",
				transactionFilterVO);
		/*Query qry = new ULDTotalDemurrageFilterQuery(transactionFilterVO,
				baseQuery);*/
		/*added by a-3278 for bug 18347 on 05Oct08
		 * changed to procedure as a part of currency conversion implementation
		 * */
		Procedure procedure = null;
		procedure = getQueryManager().createNamedNativeProcedure(
				ULD_DEFAULTS_TOTDEMURRAGE);
		ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
		String date = "01-JAN-1900";		
		LocalDate blankDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		blankDate.setDate(date);
		int index = 0;
		if (transactionFilterVO.getCompanyCode() != null
				&& transactionFilterVO.getCompanyCode().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO
					.getCompanyCode());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getUldNumber() != null
				&& transactionFilterVO.getUldNumber().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO.getUldNumber());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getUldTypeCode() != null
				&& transactionFilterVO.getUldTypeCode().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO
					.getUldTypeCode());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getTransactionType() != null
				&& !("ALL" .equals(transactionFilterVO.getTransactionType())) 
				&& transactionFilterVO.getTransactionType().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO
					.getTransactionType());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getPartyType() != null
				&& transactionFilterVO.getPartyType().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO.getPartyType());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getTransactionStatus() != null
				&& !("ALL".equals(transactionFilterVO.getTransactionStatus())) 
				&& transactionFilterVO.getTransactionStatus().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO
					.getTransactionStatus());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getFromPartyCode() != null
				&& transactionFilterVO.getFromPartyCode().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO
					.getFromPartyCode());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getToPartyCode() != null
				&& transactionFilterVO.getToPartyCode().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO
					.getToPartyCode());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getTransactionStationCode() != null
				&& transactionFilterVO.getTransactionStationCode().trim()
						.length() > 0) {
			procedure.setParameter(++index, transactionFilterVO
					.getTransactionStationCode());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getReturnedStationCode() != null
				&& transactionFilterVO.getReturnedStationCode().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO
					.getReturnedStationCode());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getMucStatus() != null
				&& transactionFilterVO.getMucStatus().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO.getMucStatus());
		} else {
			procedure.setParameter(++index, BLANK);
		}
		if (transactionFilterVO.getTxnFromDate() != null) {
			procedure.setParameter(++index, transactionFilterVO
					.getTxnFromDate());
		} else {
			procedure.setParameter(++index, blankDate);
		}
		if (transactionFilterVO.getTxnToDate() != null) {
			procedure.setParameter(++index, transactionFilterVO.getTxnToDate());
		} else {
			procedure.setParameter(++index, blankDate);
		}		
		if (transactionFilterVO.getReturnFromDate() != null) {
			procedure.setParameter(++index, transactionFilterVO
					.getReturnFromDate());
		} else {
			procedure.setParameter(++index, blankDate);
		}
		if (transactionFilterVO.getReturnToDate() != null) {
			procedure.setParameter(++index, transactionFilterVO
					.getReturnToDate());
		} else {
			procedure.setParameter(++index, blankDate);
		}		
		if (transactionFilterVO.getDesStation() != null
				&& transactionFilterVO.getDesStation().trim().length() > 0) {
			procedure.setParameter(++index, transactionFilterVO
					.getDesStation());
		} else {
			procedure.setParameter(++index, blankDate);
		}
		//added by a-3278 on 03Dec08  for including the CRN number also as the filter in totalDemmurage calculation
		
		if (transactionFilterVO.getPrefixControlReceiptNo() != null
				&& transactionFilterVO.getPrefixControlReceiptNo().trim().length() != 0) {
			StringBuilder preCrn = new StringBuilder(transactionFilterVO.getPrefixControlReceiptNo()).append("%-");		
			procedure.setParameter(++index,preCrn.toString());
		}else{
			procedure.setParameter(++index, "%-");
		}
		
		if (transactionFilterVO.getMidControlReceiptNo() != null
				&& transactionFilterVO.getMidControlReceiptNo().trim().length() != 0) {				
			procedure.setParameter(++index, transactionFilterVO.getMidControlReceiptNo());
		}else{
			procedure.setParameter(++index,"%");
		}
		
		if (transactionFilterVO.getControlReceiptNo() != null
				&& transactionFilterVO.getControlReceiptNo().trim().length() != 0) {	
			StringBuilder sufCrn = new StringBuilder("%").append(transactionFilterVO.getControlReceiptNo());			
			procedure.setParameter(++index,sufCrn.toString());
		}else{
			procedure.setParameter(++index,"%");
		}
		//a-3278 on 03Dec08 ends

		procedure.setOutParameter(++index, SqlType.DOUBLE);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		
		String baseCurrency = (String)procedure.getParameter(index);
		double grandTotal = Double.parseDouble(String.valueOf(procedure.getParameter(index-1)));
		log.log(Log.FINE, "TotalDemmurage <<::>> ", grandTotal);
		log.log(Log.FINE, "baseCurrency <<::>> ", baseCurrency);
		// modification ends
		/*grandTotal = Double.parseDouble(qry
				.getSingleResult(getStringMapper("TOT")));*/
		uldTransactionDetailsVO.setTotalDemmurage(grandTotal);
		uldTransactionDetailsVO.setBaseCurrency(baseCurrency);		
		return uldTransactionDetailsVO;
	}

	/**
	 *
	 * @author 
	 * @param uldTransactionFilterVO
	 * @return TransactionListVO
	 * @throws SystemException
	 */
	public TransactionListVO findULDTransactionDetailsCol(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException {
		//added by a-3278 for bug 23814 on 04Nov08
		log.entering("ULDDefaultsSqlDAO", "findULDTransactionDetailsCol");
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.LIST__TRANSACTIONS);
		Query qry = new ULDTransactionFilterQuery(uldTransactionFilterVO,
				baseQuery,isOracleDataSource());
		TransactionListVO transactionListVO = new TransactionListVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVO = new ArrayList<ULDTransactionDetailsVO>();
		uldTransactionDetailsVO = qry
				.getResultList(new ListULDTransactionMapper());
		transactionListVO.setUldTransactionsDetails(uldTransactionDetailsVO);
		return transactionListVO;
		//added by a-3278 for bug 23814 on 04Nov08 ends
		
		/*log.entering("ULDDefaultsSqlDAO", "listULDTransactionDetails");
		int index = 1;
		int iCount = 0;
		
		TransactionListVO transactionListVO = new TransactionListVO();
		Collection<ULDTransactionDetailsVO> transactionDetails = new ArrayList<ULDTransactionDetailsVO>();
		
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.LIST_ULD_TRANSACTIONS);
		query.setParameter(1, uldTransactionFilterVO.getCompanyCode());
		String uldNumber = uldTransactionFilterVO.getUldNumber();
		String uldTypeCode = uldTransactionFilterVO.getUldTypeCode();
		String txnType = uldTransactionFilterVO.getTransactionType();
		String txnStatus = uldTransactionFilterVO.getTransactionStatus();
		String partyType = uldTransactionFilterVO.getPartyType();
		String fromPartyCode = uldTransactionFilterVO.getFromPartyCode();
		String toPartyCode = uldTransactionFilterVO.getToPartyCode();
		String txnStation = uldTransactionFilterVO.getTransactionStationCode();
		String returnStation = uldTransactionFilterVO.getReturnedStationCode();
		LocalDate txnFromDate = uldTransactionFilterVO.getTxnFromDate();
		LocalDate txnToDate = uldTransactionFilterVO.getTxnToDate();
		LocalDate retFromDate = uldTransactionFilterVO.getReturnFromDate();
		LocalDate retToDate = uldTransactionFilterVO.getReturnToDate();
		Collection<String> uldNumbers = uldTransactionFilterVO.getUldNumbers();
		//added by a-3045 for CR QF1013 starts
		//added for MUC Tracking
		String mucIataStatus = uldTransactionFilterVO.getMucIataStatus();
		String mucReferenceNumber = uldTransactionFilterVO.getMucReferenceNumber();
		LocalDate mucDate = uldTransactionFilterVO.getMucDate();
		//added by a-3045 for CR QF1013 ends

		if (uldNumber != null && uldNumber.trim().length() != 0) {
			query.append(" AND TXN. ULDNUM = ? ");
			query.setParameter(++index, uldNumber);
		}
		if (txnType != null &&!(TransactionFilterVO.TRANSACTION_TYPE_ALL.equals(txnType))) {
			query.append(" AND TXN.TXNTYP = ? ");
			query.setParameter(++index, txnType);
		}
		if (uldTypeCode != null && uldTypeCode.trim().length() != 0) {
			query.append(" AND TXN.ULDTYP = ? ");
			query.setParameter(++index, uldTypeCode);
		}
		log.log(Log.FINE, "\n partyType" + partyType);
		if (partyType != null && !(partyType.equals("L")) && partyType.trim().length() != 0) {
			log.log(Log.FINE, "\n partyType" + partyType);
			query.append(" AND TXN.PTYTYP = ? ");
			query.setParameter(++index, partyType);
		}
		if (fromPartyCode != null && fromPartyCode.trim().length() != 0) {
			query.append(" AND TXN.RTNPTYCOD = ? ");
			query.setParameter(++index, fromPartyCode);
		}
		if (toPartyCode != null && toPartyCode.trim().length() != 0) {
			query.append(" AND TXN.PTYCOD = ? ");
			query.setParameter(++index, toPartyCode);
		}
		if (txnStation != null && txnStation.trim().length() != 0) {
			query.append(" AND TXN.TXNARPCOD = ? ");
			query.setParameter(++index, txnStation);
		}
		if (returnStation != null && returnStation.trim().length() != 0) {
			query.append(" AND TXN.RTNARPCOD = ? ");
			query.setParameter(++index, returnStation);
		}
		if (txnStatus != null && !(TransactionFilterVO.TRANSACTION_TYPE_ALL.equals(txnStatus))) {
			query.append("  AND TXN.TXNSTA =  ? ");
			query.setParameter(++index, txnStatus);
		}
		if (txnFromDate != null) {
			query.append("  AND TXN.TXNDAT >=  ? ");
			query.setParameter(++index, txnFromDate.toCalendar());
		}
		if (txnToDate != null) {
			query.append("  AND TXN.TXNDAT <=  ? ");
			query.setParameter(++index, txnToDate.toCalendar());
		}
		if (retFromDate != null) {
			query.append("  AND TXN.RTNDAT >= ? ");
			query.setParameter(++index, retFromDate.toCalendar());
		}
		if (retToDate != null) {
			query.append("  AND TXN.RTNDAT <= ?  ");
			query.setParameter(++index, retToDate.toCalendar());
		}
		if (uldNumbers != null && uldNumbers.size() > 0) {
			for (String uldNum : uldNumbers) {
				if (iCount == 0) {
	    				query.append(" AND TXN. ULDNUM IN (  ? ");
					query.setParameter(++index, uldNum);
				} else {
	    				query.append(" , ? ");
					query.setParameter(++index, uldNum);
	    			}
				iCount = 1;
			}
			if (iCount == 1) {
				query.append(" ) ");
			}
		}
		//added by a-3045 for CR QF1013 starts
		//added for MUC Tracking
		if (mucIataStatus != null && mucIataStatus.trim().length() != 0) {
			query.append("  AND TXN.MUCSNT =  ? ");
			query.setParameter(++index, mucIataStatus);
		}
		if (mucReferenceNumber != null && mucReferenceNumber.trim().length() != 0) {
			query.append("  AND TXN.MUCREFNUM =  ? ");
			query.setParameter(++index, mucReferenceNumber);
		}
		if (mucDate != null) {
			query.append("  AND TXN.MUCDAT =  ? ");
			query.setParameter(++index, mucDate.toCalendar());
		}
		//added by a-3045 for CR QF1013 ends
		transactionDetails = query
				.getResultList(new ListULDTransactionMapper());
		transactionListVO.setUldTransactionsDetails(transactionDetails);
		return transactionListVO;*/
		
	}

	/**
		transactionListVO.setTransactionDetailsPage(pageQuery
				.getPage(uldTransactionFilterVO.getPageNumber()));

		/*
		 * Collection<ULDTransactionDetailsVO> detailsVOs = query
		 * .getResultList(new ListULDTransactionMapper());
		 *
		 * TransactionListVO transactionListVO = new TransactionListVO();
		 * transactionListVO.setUldTransactionsDetails(detailsVOs);
		 * log.exiting("ULDDefaultsSqlDAO", "listULDTransactionDetails"); //
		 * log.log(Log.FINE, "ULDTransactionDetailsVOs From server" + //
		 * detailsVOs);
		 **/
	 /** This method is used for listing uld Invoice
	 *
	 * @author A-1883
	 * @param chargingInvoiceFilterVO
	 * @param pageNumber
	 * @return Page<ULDChargingInvoiceVO>
	 * @throws SystemException
	 */
	public Page<ULDChargingInvoiceVO> listULDChargingInvoice(
			ChargingInvoiceFilterVO chargingInvoiceFilterVO, int pageNumber)
			throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "listULDChargingInvoice");
		int index = 0;
		StringBuilder rankQuery = new StringBuilder();//Added by A-5214 as part from the ICRD-22824
		String invoiceRefNumber = chargingInvoiceFilterVO.getInvoiceRefNumber();
		String transactionType = chargingInvoiceFilterVO.getTransactionType();
		String invoicedTo = chargingInvoiceFilterVO.getInvoicedToCode();
		LocalDate fromDate = chargingInvoiceFilterVO.getInvoicedDateFrom();
		LocalDate toDate = chargingInvoiceFilterVO.getInvoicedDateTo();
		String partyType=chargingInvoiceFilterVO.getPartyType();
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.LIST_ULD_CHARGING_INVOICE);
		//Added by A-5214 as part from the ICRD-22824 STARTS  
        rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
        rankQuery.append(query);  
        //Added by A-5214 as part from the ICRD-22824 ENDS
		
		PageableNativeQuery<ULDChargingInvoiceVO> pageQuery = new PageableNativeQuery<ULDChargingInvoiceVO>(chargingInvoiceFilterVO.getTotalRecords(),
				rankQuery.toString(), new ListULDInvoiceMapper());
		 
		pageQuery.setParameter(++index, chargingInvoiceFilterVO.getCompanyCode());

		if (invoiceRefNumber != null && invoiceRefNumber.trim().length() != 0) {
			pageQuery.append(" AND INV.INVREFNUM = ? ");
			pageQuery.setParameter(++index, invoiceRefNumber);
		}
		if (!(ChargingInvoiceFilterVO.TRANSACTION_TYPE_ALL
				.equals(transactionType))) {
			pageQuery.append(" AND INV.TXNTYP = ? ");
			pageQuery.setParameter(++index, transactionType);
		}
		if (invoicedTo != null && invoicedTo.trim().length() != 0) {
			pageQuery.append("  AND INV.INVCOD = ? ");
			pageQuery.setParameter(++index, invoicedTo);
		}
		if (fromDate != null) {
			pageQuery.append("  AND INV.INVDAT >=  ? ");
			pageQuery.setParameter(++index, fromDate.toCalendar());
		}
		if (toDate != null) {
			pageQuery.append(" AND INV.INVDAT <=  ? ");
			pageQuery.setParameter(++index, toDate.toCalendar());
		}
		if(partyType !=null && partyType.trim().length()>0){
			pageQuery.append(" AND INV.PTYTYP = ? ");
			pageQuery.setParameter(++index, partyType);
		}
		
		pageQuery.append("ORDER BY INVREFNUM");
		pageQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		log.exiting("ULDDefaultsSqlDAO", "listULDChargingInvoice");
		return pageQuery.getPage(pageNumber);
	}

	/**
	 * This method is used for listing uld Invoice Details
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param invoiceRefNumber
	 * @return Collection<ULDTransactionDetailsVO>
	 * @throws SystemException
	 */
	public Collection<ULDTransactionDetailsVO> viewULDChargingInvoiceDetails(
			String companyCode, String invoiceRefNumber) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "viewULDChargingInvoiceDetails");
		log.log(Log.FINE, "\n invoiceRefNumber", invoiceRefNumber);
		Query query = getQueryManager()
				.createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.VIEW_ULD_CHARGING_INVOICE_DETAILS);
		query.setParameter(1, companyCode);
		query.setParameter(2, invoiceRefNumber);
		Collection<ULDTransactionDetailsVO> detailsVOs = query
		.getResultList(new ViewULDTxnInvoiceDetailsMapper());
		log.log(Log.FINE, "ULDTransactionDetailsVOs From server", detailsVOs);
		log.exiting("ULDDefaultsSqlDAO", "viewULDChargingInvoiceDetails");
		return detailsVOs;
	}

	/**
	 * This method is used for listing uld transaction
	 *
	 * @author A-1883
	 * @param uldTransactionFilterVO
	 * @return TransactionListVO
	 * @throws SystemException
	 */
	public TransactionListVO listAccessoryTransactionDetails(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "listAccessoryTransactionDetails");
		int index = 1;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.LIST_ACCESSORY_TRANSACTIONS);
		query.setParameter(1, uldTransactionFilterVO.getCompanyCode());
		String accessoryCode = uldTransactionFilterVO.getAccessoryCode();
		String partyType = uldTransactionFilterVO.getPartyType();
		//changed by a-3045 for bug 20362 starts
		//String fromPartyCode = uldTransactionFilterVO.getFromPartyCode();
		String toPartyCode = uldTransactionFilterVO.getToPartyCode();
		//changed by a-3045 for bug 20362 ends
		String txnStation = uldTransactionFilterVO.getTransactionStationCode();
		String txnType = uldTransactionFilterVO.getTransactionType();
		if (accessoryCode != null && accessoryCode.trim().length() != 0) {
			query.append(" AND ACCCOD = ? ");
			query.setParameter(++index, accessoryCode);
		}
		if (partyType != null && partyType.trim().length() != 0) {
			query.append(" AND PTYTYP = ? ");
			query.setParameter(++index, partyType);
		}
		//changed by a-3045 for bug 20362 starts
		if (toPartyCode != null && toPartyCode.trim().length() != 0) {
			query.append(" AND PTYCOD = ? ");
			query.setParameter(++index, toPartyCode);
		}
		//changed by a-3045 for bug 20362 ends
		if (txnStation != null && txnStation.trim().length() != 0) {
			query.append(" AND TXNARPCOD = ? ");
			query.setParameter(++index, txnStation);
		}
		//added by jisha for QF1022 starts
		if(txnType!=null && txnType.trim().length()!=0){
		if (!(TransactionFilterVO.TRANSACTION_TYPE_ALL.equals(txnType))) {
			query.append(" AND TXNTYP = ? ");
			query.setParameter(++index, txnType);
		}
		}
		//added by jisha for QF1022 ends
		//changed by a-3045 for bug 20362 starts
		if (uldTransactionFilterVO.getTxnFromDate() != null) {
			query.append("  AND TXNDAT >= ? ");
			query.setParameter(++index, uldTransactionFilterVO.getTxnFromDate().toCalendar());
		}
		if (uldTransactionFilterVO.getTxnToDate() != null) {
			query.append("  AND TXNDAT <= ?  ");
			query.setParameter(++index, uldTransactionFilterVO.getTxnToDate().toCalendar());
		}
		//changed by a-3045 for bug 20362 ends
		Collection<AccessoryTransactionVO> accessoryTransactions = query
		.getResultList(new ListAccessoryTransactionMapper());
		TransactionListVO transactionListVO = new TransactionListVO();
		transactionListVO.setAccessoryTransactions(accessoryTransactions);
		log.log(Log.FINE, "AccessoryTransactionVOs-->", accessoryTransactions);
		log.exiting("ULDDefaultsSqlDAO", "listAccessoryTransactionDetails");
		return transactionListVO;
	}


	/**
	 * ULDInTransactionMapper.java Created on Dec 19, 2005
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	private class ULDInTransactionMapper implements Mapper<String> {
		/**
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public String map(ResultSet rs) throws SQLException {
			log.entering("INSIDE THE TRANSACTION MAPPER", " map(ResultSet rs)");
			int loanReferenceNumber = rs.getInt("LONREFNUM");
			int borrowReferenceNumber = rs.getInt("BRWREFNUM");
			if (loanReferenceNumber != 0 || borrowReferenceNumber != 0) {
				return FLAG_YES;
			}
			return FLAG_NO;
		}
	}

	/**
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @param transactionType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */

	public boolean checkLoanedULDAlreadyLoaned(String companyCode,
			String uldNumber, String transactionType) throws SystemException,
			PersistenceException {
		int index = 0;
		boolean isLoaned = false;
		String key = null;
		String refNUM = "";
		if (transactionType.equals(LOANED_TRANSACTION)) {
			key = ULDDefaultsPersistenceConstants.LOANED_ULD_ALREADY_LOANED;
			refNUM = LOANEDREFNUMBER;
		} else {
			key = ULDDefaultsPersistenceConstants.BORROWED_ULD_ALREADY_BORROWED;
			refNUM = BORROWEDREFNUM;
		}
		log.entering("INSIDE THE SQLDAO", "CHECKLOANEDULDALREADYLOANED");
		log.log(Log.FINE, "KEY IS>>>>>>>>>>>>>>>>>>> ", key);
		Query qry = getQueryManager().createNamedNativeQuery(key);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, uldNumber);
		Mapper<String> integerMapper = getStringMapper(refNUM);
		String referenceNumber = qry.getSingleResult(integerMapper);
		if (referenceNumber != null) {
			int reference = Integer.parseInt(referenceNumber);
			if (reference > 0) {
				isLoaned = true;
			}
		}
		return isLoaned;
	}

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
			PersistenceException {
		int index = 0;
		String key = null;
		boolean isBorrowedULDLoanedToSameParty = false;
		if (transactionType.equals(LOANED_TRANSACTION)) {
			key = ULDDefaultsPersistenceConstants.BORROWED_ULD_LOANEDTO_SAMEPARTY;
		} else {
			key = ULDDefaultsPersistenceConstants.LOANED_ULD_BORROWEDFROM_SAMEPARTY;
		}
		Query query = getQueryManager().createNamedNativeQuery(key);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, uldNumber);
		query.setParameter(++index, partycode);
		query.setParameter(++index, partyType);

		Mapper<String> stringMapper = getStringMapper("CMPCOD");
		String cmpCode = query.getSingleResult(stringMapper);
		if (cmpCode != null) {
			isBorrowedULDLoanedToSameParty = true;
		}
		return isBorrowedULDLoanedToSameParty;

	}

	/**
	 * For InvoiceRefNumber LOV
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param invRefNo
	 * @param displayPage
	 * @return Page<String>
	 * @throws SystemException
	 */
	public Page<String> findInvoiceRefNumberLov(String companyCode,
			int displayPage, String invRefNo) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findInvoiceRefNumberLov");
		// Modified by A-5280 for ICRD-32647
		PageableNativeQuery<String> pgqry = null;
		String query = null;
    	 query = getQueryManager().getNamedNativeQueryString(ULDDefaultsPersistenceConstants.FIND_INVOICE_REFERENCENUM_LOV);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(query);
		// Added by A-5280 for ICRD-32647 ends
		// Modified by A-5280 for ICRD-32647
		pgqry = new PageableNativeQuery<String>(0,rankQuery.toString(), getStringMapper("INVREFNUM"));
		
		 pgqry.setParameter(1, companyCode);
		if (invRefNo != null && invRefNo.trim().length() > 0) {
			invRefNo = invRefNo.replace('*', '%');
			pgqry.append(" AND INVREFNUM LIKE ? ");
			pgqry.setParameter(2, new StringBuilder().append(invRefNo).append(
					"%").toString());
		}

		Page<String> refNumbers = null;

		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 ends
		
		refNumbers = pgqry.getPage(displayPage);
		log.log(Log.FINE, "lovVOs From server", refNumbers);
		log.exiting("ULDDefaultsSqlDAO", "findInvoiceRefNumberLov");
		return refNumbers;
	}


	/**
	 * InvoiceRefNumberLovMapper.java Created on Dec 19, 2005
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	private class InvoiceRefNumberLovMapper implements Mapper<String> {

		/**
		 * @param rs
		 * @return String
		 * @throws SQLException
		 */
		public String map(ResultSet rs) throws SQLException {
			log.entering("InvoiceRefNumberLovMapper", " map");
			return rs.getString("INVREFNUM");

		}
	}

	/**
	 * @param uldAgreementFilterVO
	 * @return ULDAgreementVO
	 * @throws SystemException
	 */
	public ULDAgreementVO findULDAgreementForReturnTransaction(
			ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException {
		log.entering("INSIDE THE SQLDAO",
		"findULDAgreementForReturnTransaction"+uldAgreementFilterVO);
		ULDAgreementVO uldAgreementVo = null;
		List<ULDAgreementVO> uLDAgreementVOs = null;
		Collection<ULDAgreementDetailsVO> uldAgreementDetailVOs = null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULDAGMNTS_FOR_RETURNTXN);
		query.setParameter(++index, uldAgreementFilterVO.getCompanyCode());
		query.setParameter(++index, uldAgreementFilterVO.getPartyCode());
		query.setParameter(++index, uldAgreementFilterVO.getTxnType());
		//added as part of ICRD-232684 by A-4393 starts ,Curently handled for type airline
		//added as part of ICRD-232684 by A-4393 ends 
		query.setParameter(++index, uldAgreementFilterVO.getUldTypeCode());
		// Added by Preet for AirNZ 517 --starts
		query.setParameter(++index, uldAgreementFilterVO.getTransactionStation());
		// Added by Preet for AirNZ 517 --ends
		query.setParameter(++index, uldAgreementFilterVO.getPartyType());
		if(uldAgreementFilterVO.getFromPartyCode()!=null&&uldAgreementFilterVO.getFromPartyCode().length()>0){
			query.append( "AND AGR.FRMPTYTYP = ?");
			query.append( "AND AGR.FRMPTYCOD in (?,'ALL AIRLINES')");
			query.setParameter(++index, ULDDefaultsPersistenceConstants.AIRLINE);
			query.setParameter(++index, uldAgreementFilterVO.getFromPartyCode()); 
		}
		
		if (uldAgreementFilterVO.getTransactionDate() != null) {
			//Changed by a-3045 for bug20335, added TRUNC for every date in this method
			query.append("  AND  TRUNC(to_date(?,'yyyy-mm-dd'))  >=  TRUNC(AGRDTL.AGRMNTFRMDAT)  ");
			query.append("AND  (TRUNC(AGRDTL.AGRMNTTOODAT)  IS NULL OR TRUNC(to_date(?,'yyyy-mm-dd')) <= (TRUNC(AGRDTL.AGRMNTTOODAT)))");
			
			query.setParameter(++index, uldAgreementFilterVO
					.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			query.setParameter(++index, uldAgreementFilterVO
					.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			log.log(Log.FINE, "THE TRANSACTION DATE IS",
					uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
		}
		uldAgreementVo = query
		.getSingleResult(new AgreementDetailsForReturnTransactionMapper());
		if (uldAgreementVo == null) {
			log.log(Log.INFO, "ULDAGREEMENTVO IS NULL");
			index = 0;
			Query qry = getQueryManager()
			.createNamedNativeQuery(
					ULDDefaultsPersistenceConstants.FIND_ULDAGMNTS_GENERIC_FOR_RET_TXN);
			qry.setParameter(++index, uldAgreementFilterVO.getCompanyCode());
			qry.setParameter(++index, uldAgreementFilterVO.getPartyCode());
			qry.setParameter(++index, uldAgreementFilterVO.getTxnType());
			qry.setParameter(++index, uldAgreementFilterVO.getPartyType());
			//added as part of ICRD-232684 by A-4393 starts ,Curently handled for type airline
			if(uldAgreementFilterVO.getFromPartyCode()!=null&&uldAgreementFilterVO.getFromPartyCode().length()>0){
				qry.append( "AND AGR.FRMPTYTYP = ?");
				qry.append( "AND AGR.FRMPTYCOD in (?,'ALL AIRLINES')");
				qry.setParameter(++index, ULDDefaultsPersistenceConstants.AIRLINE);
				qry.setParameter(++index, uldAgreementFilterVO.getFromPartyCode()); 
			}
			if (uldAgreementFilterVO.getTransactionDate() != null) {
				qry.append("  AND  TRUNC(to_date(?,'yyyy-mm-dd'))  >=  TRUNC(AGR.AGRMNTFRMDAT)  ");
				qry
						.append("AND  (TRUNC(AGR.AGRMNTTOODAT)  IS NULL OR TRUNC(to_date(?,'yyyy-mm-dd')) <= (TRUNC(AGR.AGRMNTTOODAT)))");
				qry.setParameter(++index, uldAgreementFilterVO
						.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
				qry.setParameter(++index, uldAgreementFilterVO
						.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
				log.log(Log.FINE, "THE TRANSACTION DATE IS",
						uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
				uldAgreementVo = qry
				.getSingleResult(new AgreementDetailsForReturnTransactionMapper());

			}

		}
		// Added by Preet on 3rd April for AirNZ 517 starts
		// To pick up ALL Airline / Agent / Others agreement
		if (uldAgreementVo == null) {
			log.log(Log.INFO, "ULDAGREEMENT Checking for party code = ALL for Airline/Agent/Others");
			index = 0;
			Query qry = getQueryManager().createNamedNativeQuery(
					ULDDefaultsPersistenceConstants.FIND_ULDAGMTS_FOR_ALL_PTY);

			qry.setParameter(++index, uldAgreementFilterVO.getCompanyCode());
			qry.setParameter(++index, uldAgreementFilterVO.getTxnType());
			// Added by Preet for AirNZ 517 starts
			
			if(AGENT.equals(uldAgreementFilterVO.getPartyType())){
				qry.setParameter(++index,
						ULDDefaultsPersistenceConstants.ALL_AGENT);
				qry.setParameter(++index,
						ULDDefaultsPersistenceConstants.AGENT);
			}else if(AIRLINE.equals(uldAgreementFilterVO.getPartyType())){
				qry.setParameter(++index,
						ULDDefaultsPersistenceConstants.ALL_AIRLINE);
				qry.setParameter(++index,
						ULDDefaultsPersistenceConstants.AIRLINE);
			}
			//Added for ICRD-323967 starts
			else if(CUSTOMER.equals(uldAgreementFilterVO.getPartyType())){
				qry.setParameter(++index,
						ULDDefaultsPersistenceConstants.ALL_CUSTOMERS);
				qry.setParameter(++index,
						ULDDefaultsPersistenceConstants.CUSTOMERS);
			}
			//Added for ICRD-323967 ends
			else if(OTHER.equals(uldAgreementFilterVO.getPartyType())){
				qry.setParameter(++index,
						ULDDefaultsPersistenceConstants.ALL_OTHER);
				qry.setParameter(++index,
						ULDDefaultsPersistenceConstants.OTHER);
			}
			/*qry.setParameter(++index,
					ULDDefaultsPersistenceConstants.ALLPARTYCODE);
			qry.setParameter(++index,
					ULDDefaultsPersistenceConstants.ALLPARTYTYPE);*/
			
			qry.setParameter(++index, uldAgreementFilterVO.getUldTypeCode());
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionStation());
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
     //added as part of ICRD-232684 by A-4393 starts ,Curently handled for type airline
			if(uldAgreementFilterVO.getFromPartyCode()!=null&&uldAgreementFilterVO.getFromPartyCode().length()>0){
				qry.append( "AND AGR.FRMPTYTYP = ?");
				qry.append( "AND AGR.FRMPTYCOD in (?,'ALL AIRLINES')");
				qry.setParameter(++index, ULDDefaultsPersistenceConstants.AIRLINE);
				qry.setParameter(++index, uldAgreementFilterVO.getFromPartyCode()); 
			}
			//added as part of ICRD-232684 by A-4393 ends 
			uLDAgreementVOs = qry
					.getResultList(new AgreementDetailsForAllPartyMultiMapper());
			if (uLDAgreementVOs != null && uLDAgreementVOs.size() > 0) {
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));

			uLDAgreementVOs = qry
					.getResultList(new AgreementDetailsForAllPartyMultiMapper());
			}
			
			if (uLDAgreementVOs != null && uLDAgreementVOs.size() > 0) {
				log.log(Log.FINE, "ULDAGREEMENT VO FORM ALL AIRLINE/ AGENT /OTHER PARTY ");
				uldAgreementVo = uLDAgreementVOs.get(0);
				if (uldAgreementVo.getCompanyCode() == null
						|| uldAgreementVo.getCompanyCode().trim().length() == 0) {
					uldAgreementVo=null;
				}
				if(uldAgreementVo !=null){
					uldAgreementDetailVOs = uldAgreementVo
							.getUldAgreementDetailVOs();
				}
			}
			if (uldAgreementDetailVOs != null && uldAgreementDetailVOs.size() > 0) {
	        	log.log(Log.FINE, "uldAgreementDetailVOs NOT NULL ");
				for (ULDAgreementDetailsVO detailsVO : uldAgreementDetailVOs) {
					if (detailsVO.getSequenceNumber() == 0) {
	        			log.log(Log.FINE, "NO CHILD VOS");
	        			log
								.log(Log.FINE, "ULDAGREEMENT VO IS ",
										uldAgreementVo);
						break;	        			
	        		}
					if (detailsVO.getAgreementFromDate().toCalendar().before(
							uldAgreementFilterVO.getTransactionDate().toCalendar())
							|| detailsVO.getAgreementFromDate()
									.toDisplayDateOnlyFormat().equals(
											uldAgreementFilterVO
													.getTransactionDate()
													.toDisplayDateOnlyFormat())) {
	        			log.log(Log.FINE, "FROM DATE IS MATCH ");
						if (detailsVO.getAgreementToDate() == null) {
							log
									.log(
											Log.FINE,
											" TOO DATE NULL ..SO mATCH FOUND IN CHILD WITH SEQ NUM ",
											detailsVO.getSequenceNumber());
							uldAgreementVo.setCurrency(detailsVO.getCurrency());
							uldAgreementVo.setTax(detailsVO.getTax());
							uldAgreementVo.setDemurrageFrequency(detailsVO
									.getDemurrageFrequency());
							uldAgreementVo.setDemurrageRate(detailsVO
									.getDemurrageRate());
							uldAgreementVo.setFreeLoanPeriod(detailsVO
									.getFreeLoanPeriod());
							break;
						} else if (detailsVO.getAgreementToDate().toCalendar()
								.after(
										uldAgreementFilterVO.getTransactionDate()
												.toCalendar())
								|| detailsVO.getAgreementToDate()
										.toDisplayDateOnlyFormat().equals(
												uldAgreementFilterVO
														.getTransactionDate()
														.toDisplayDateOnlyFormat())) {
							log.log(Log.FINE, " TOO DATE MATCH WITH SEQ NUM",
									detailsVO.getSequenceNumber());
							uldAgreementVo.setCurrency(detailsVO.getCurrency());
							uldAgreementVo.setTax(detailsVO.getTax());
							uldAgreementVo.setDemurrageFrequency(detailsVO
									.getDemurrageFrequency());
							uldAgreementVo.setDemurrageRate(detailsVO
									.getDemurrageRate());
							uldAgreementVo.setFreeLoanPeriod(detailsVO
									.getFreeLoanPeriod());
							break;
						} else {
	        				log.log(Log.FINE, " NO TOO DATE MATCH ");
	        			}
	        		}
	        	}
			}else {
	        	log.log(Log.FINE, "ULD AGREEMANT DETAILS VOS IS NULL ");
	        }
		}
		// Added by Preet on 3rd April for AirNZ 517 ends
		
		if (uldAgreementVo == null) {
			log.log(Log.INFO, "ULDAGREEMENT Checking for party code = all");
			index = 0;
			Query qry = getQueryManager().createNamedNativeQuery(
					ULDDefaultsPersistenceConstants.FIND_ULDAGMTS_FOR_ALL_PTY);
			qry.setParameter(++index, uldAgreementFilterVO.getUldTypeCode());
			//Added by Preet for AirNZ 517 --starts
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionStation());
			// Added by Preet for AirNZ 517 --ends
			qry.setParameter(++index, uldAgreementFilterVO.getCompanyCode());
			qry.setParameter(++index, uldAgreementFilterVO.getTxnType());
			qry.setParameter(++index,
					ULDDefaultsPersistenceConstants.ALLPARTYCODE);
			qry.setParameter(++index,
					ULDDefaultsPersistenceConstants.ALLPARTYTYPE);
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate().toStringFormat("yyyy-MM-dd").substring(0,10));
			//added as part of ICRD-232684 by A-4393 starts ,Curently handled for type airline
			if(uldAgreementFilterVO.getFromPartyCode()!=null&&uldAgreementFilterVO.getFromPartyCode().length()>0){
				qry.append( "AND AGR.FRMPTYTYP = ?");
				qry.append( "AND AGR.FRMPTYCOD = ?");
				qry.setParameter(++index, ULDDefaultsPersistenceConstants.ALLPARTYTYPE); 
				qry.setParameter(++index, ULDDefaultsPersistenceConstants.ALLPARTYCODE);
			} 
			//added as part of ICRD-232684 by A-4393 ends 
			uLDAgreementVOs = qry
					.getResultList(new AgreementDetailsForAllPartyMultiMapper());
			if (uLDAgreementVOs != null && uLDAgreementVOs.size() > 0) {
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate()
					.toCalendar());
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate()
					.toCalendar());
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate()
					.toCalendar());
			qry.setParameter(++index, uldAgreementFilterVO.getTransactionDate()
					.toCalendar());

			uLDAgreementVOs = qry
					.getResultList(new AgreementDetailsForAllPartyMultiMapper());
			if (uLDAgreementVOs != null && uLDAgreementVOs.size() > 0) {
				log.log(Log.FINE, "ULDAGREEMENT VO FORM PARTY ALL ");
				uldAgreementVo = uLDAgreementVOs.get(0);
				if (uldAgreementVo.getCompanyCode() == null
						|| uldAgreementVo.getCompanyCode().trim().length() == 0) {
					return null;
				}
				uldAgreementDetailVOs = uldAgreementVo
						.getUldAgreementDetailVOs();

			}
			

		}
		if (uldAgreementDetailVOs != null && uldAgreementDetailVOs.size() > 0) {
        	log.log(Log.FINE, "uldAgreementDetailVOs NOT NULL ");
			for (ULDAgreementDetailsVO detailsVO : uldAgreementDetailVOs) {
				if (detailsVO.getSequenceNumber() == 0) {
        			log.log(Log.FINE, "NO CHILD VOS");
        			log.log(Log.FINE, "ULDAGREEMENT VO IS ", uldAgreementVo);
					return uldAgreementVo;
        		}
				if (detailsVO.getAgreementFromDate().toCalendar().before(
						uldAgreementFilterVO.getTransactionDate().toCalendar())
						|| detailsVO.getAgreementFromDate()
								.toDisplayDateOnlyFormat().equals(
										uldAgreementFilterVO
												.getTransactionDate()
												.toDisplayDateOnlyFormat())) {
        			log.log(Log.FINE, "FROM DATE IS MATCH ");
					if (detailsVO.getAgreementToDate() == null) {
						log
								.log(
										Log.FINE,
										" TOO DATE NULL ..SO mATCH FOUND IN CHILD WITH SEQ NUM ",
										detailsVO.getSequenceNumber());
						uldAgreementVo.setCurrency(detailsVO.getCurrency());
						uldAgreementVo.setTax(detailsVO.getTax());
						uldAgreementVo.setDemurrageFrequency(detailsVO
								.getDemurrageFrequency());
						uldAgreementVo.setDemurrageRate(detailsVO
								.getDemurrageRate());
						uldAgreementVo.setFreeLoanPeriod(detailsVO
								.getFreeLoanPeriod());
						break;
					} else if (detailsVO.getAgreementToDate().toCalendar()
							.after(
									uldAgreementFilterVO.getTransactionDate()
											.toCalendar())
							|| detailsVO.getAgreementToDate()
									.toDisplayDateOnlyFormat().equals(
											uldAgreementFilterVO
													.getTransactionDate()
													.toDisplayDateOnlyFormat())) {
						log.log(Log.FINE, " TOO DATE MATCH WITH SEQ NUM",
								detailsVO.getSequenceNumber());
						uldAgreementVo.setCurrency(detailsVO.getCurrency());
						uldAgreementVo.setTax(detailsVO.getTax());
						uldAgreementVo.setDemurrageFrequency(detailsVO
								.getDemurrageFrequency());
						uldAgreementVo.setDemurrageRate(detailsVO
								.getDemurrageRate());
						uldAgreementVo.setFreeLoanPeriod(detailsVO
								.getFreeLoanPeriod());
						break;
					} else {
        				log.log(Log.FINE, " NO TOO DATE MATCH ");
        			}
        		}
        	}
		} else {
        	log.log(Log.FINE, "uldAgreementDetailVOs IS NULL ");
        }
		log.log(Log.FINE, "ULDAGREEMENT VO IS ", uldAgreementVo);
		}
		return uldAgreementVo;
	}




	/**
	 * This method is used to Monitor ULD stock
	 *
	 * @author A-1883
	 * @param uLDStockConfigFilterVO
	 * @param displayPage
	 * @return Page<ULDStockListVO>
	 * @throws SystemException
	 */
	public Page<ULDStockListVO> findULDStockList(
			ULDStockConfigFilterVO uLDStockConfigFilterVO, int displayPage)
			throws SystemException {//a-5505 
		log.entering("ULDDefaultsSqlDAO", "findULDStockList");
		Query query = null;
		StringBuilder rankQuery = new StringBuilder();//a-5505 for bug ICRD-123103 
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.FIND_ULD_STOCK_LIST);
		//rankQuery.append("select * from ("); 
		rankQuery.append(ULD_DEFAULTS_DENSE_RANK_QUERY);
		if(ULD_SORTING_ORDER_DESC.equals(uLDStockConfigFilterVO.getSort())){
			rankQuery.append(" ").append(ULD_DEFAULTS_RANK_QUERY_DESC);
		}else if(ULD_SORTING_ORDER_ASC.equals(uLDStockConfigFilterVO.getSort())){
			rankQuery.append(" ").append(ULD_DEFAULTS_RANK_QUERY_ASC);
		}else{
			rankQuery.append(" ").append(ULD_DEFAULTS_RANK_QUERY);
		}
		String rnkQuery = rankQuery.append(baseQuery).toString();
		//query = new MonitorULDStockFliterQuery(uLDStockConfigFilterVO,
			//	baseQuery);
		log.log(Log.INFO, "%%%%%%%%%%%query", query);
		log.exiting("ULDDefaultsSqlDAO", "findULDStockList");
		/*PageableQuery<ULDStockListVO> pgqry = new PageableQuery<ULDStockListVO>(
				query, new FindULDStockListMapper());*/
		PageableNativeQuery<ULDStockListVO> pgqry = new MonitorULDStockFliterQuery(
				rnkQuery, new FindULDStockListMapper(),uLDStockConfigFilterVO);
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		//return pgqry.getPageAbsolute(displayPage, uLDStockConfigFilterVO
		//	.getAbsoluteIndex());
		//return pgqry.getPage(displayPage);
		return pgqry.getPage(uLDStockConfigFilterVO.getPageNumber());
	}

	 /**
		 *
		 * @param uldAgreementVO
		 * @return String
		 * @throws SystemException
		 * @throws PersistenceException
		 */
    public String checkForInvoice(ULDAgreementVO uldAgreementVO)
			throws SystemException, PersistenceException {
    	log.entering("ULDDefaultsSqlDAO", "checkForInvoice");
    	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.CHECK_FOR_INVOICE);
		query.setParameter(1, uldAgreementVO.getCompanyCode());
		query.setParameter(2, uldAgreementVO.getAgreementNumber());

    	String agrNum = query.getSingleResult(getStringMapper("AGRNUM"));
		log.log(Log.FINE, "agrNum is ", agrNum);
		return agrNum;
    }

    /**
	 *
	 * @param discrepancyFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<ULDDiscrepancyVO> listULDDiscrepancyDetails(
			ULDDiscrepancyFilterVO discrepancyFilterVO) throws SystemException,
			PersistenceException {
    	log.entering("ULDDefaultsSqlDAO", "listULDDiscrepancyDetails");
		log.log(Log.INFO, "%%%%%%%%%  ULDDiscrepancyFilterVO ",
				discrepancyFilterVO);
		int index = 0;

    	//Modified by A-5220 for ICRD-22824 starts
    	String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.LIST_ULD_DISCREPANCY);
    	StringBuilder rankQuery = new StringBuilder();
    	rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
    	rankQuery.append(baseQuery);
    	PageableNativeQuery<ULDDiscrepancyVO> query = new PageableNativeQuery<ULDDiscrepancyVO>(
    			discrepancyFilterVO.getTotalRecordsCount(),
    			rankQuery.toString(), new ULDDiscrepancyMapper());
    	//Modified by A-5220 for ICRD-22824 ends
		query.setParameter(++index, discrepancyFilterVO.getCompanyCode());
		if (discrepancyFilterVO.getUldNumber() != null
				&& discrepancyFilterVO.getUldNumber().trim().length() > 0) {
        	query.append(" AND DIS.ULDNUM = ? ");
			query.setParameter(++index, discrepancyFilterVO.getUldNumber());
        }
		if (discrepancyFilterVO.getReportingStation() != null
				&& discrepancyFilterVO.getReportingStation().trim().length() > 0) {
        	query.append(" AND DIS.RPTARP = ? ");
			query.setParameter(++index, discrepancyFilterVO
					.getReportingStation());
		}
		//Added by Sreekumar S as a part of AirNZ CR434
		if (discrepancyFilterVO.getFacilityType() != null
				&& discrepancyFilterVO.getFacilityType().trim().length() > 0) {
        	query.append(" AND DIS.FACTYP = ? ");
			query.setParameter(++index, discrepancyFilterVO
					.getFacilityType());
		}
		if (discrepancyFilterVO.getLocation() != null
				&& discrepancyFilterVO.getLocation().trim().length() > 0) {
        	query.append(" AND DIS.LOC = ? ");
			query.setParameter(++index, discrepancyFilterVO
					.getLocation());
		}
		//Added by Sreekumar S as a part of AirNZ CR434 ends
		if (discrepancyFilterVO.getOwnerStation() != null
				&& discrepancyFilterVO.getOwnerStation().trim().length() > 0) {
        	query.append(" AND MST.OWNARP = ? ");
			query.setParameter(++index, discrepancyFilterVO.getOwnerStation());
        }
		if (discrepancyFilterVO.getUldOwnerIdentifier() > 0) {
        	query.append(" AND MST.OPRARLIDR = ? ");
			query.setParameter(++index, discrepancyFilterVO
					.getUldOwnerIdentifier());
		}

		if (discrepancyFilterVO.getDiscrepancyStatus() != null
				&& discrepancyFilterVO.getDiscrepancyStatus().trim().length() > 0) {
			query.append(" and DIS.DISCOD=? ");
			query.setParameter(++index,
					discrepancyFilterVO.getDiscrepancyStatus());
		}
		log.log(Log.INFO, "%%%%%%%%%%%%  Query ", query);
		//Added by A-5220 for ICRD-22824 starts
		query.append(" ORDER BY ULDNUM ");
		query.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		//Added by A-5220 for ICRD-22824 ends
    	return query.getPage(discrepancyFilterVO.getPageNumber());
    }

	/***************************************************************************
 * @param companyCode
 * @param ulds
 * @return Collection<ULDTransactionDetailsVO>
 * @throws SystemException
 * @throws PersistenceException
 */
    public Collection<ULDTransactionDetailsVO>  listULDTransactionsForMUCReconciliation(
			String companyCode, Set<String> ulds) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO",
				"listULDTransactionsForMUCReconciliation");
    	int index = 0;
    	int iCount = 0;
    	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_TRANSACTIONS);
		query.setParameter(++index, companyCode);
    	query.append(" AND ( ");

		for (String uldNumber : ulds) {
			if (iCount == 0) {
    			query.append(" TXN.ULDNUM = ? ");
				query.setParameter(++index, uldNumber);
    			iCount = 1;
			} else {
    			query.append(" OR TXN.ULDNUM = ? ");
				query.setParameter(++index, uldNumber);
    		}
    	}
    	query.append(" ) ");

		Collection<ULDTransactionDetailsVO> detailsVOs = query
				.getResultList(new ListULDTransactionMapper());
		log.log(Log.INFO, "%%%%%%%%%%%%%%%%   detailsVOs", detailsVOs);
		return detailsVOs;
    }

    /**
     *
     * @param companyCode
     * @param airlineCode
     * @return String
     * @throws SystemException
     * @throws PersistenceException
     */
	//Commented as part of ICRD 21184
    /*public String findAirlineCode(String companyCode, String airlineCode)
			throws SystemException, PersistenceException {
    	log.entering("ULDDefaultsSqlDAO", "checkForInvoice");

    	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_AIRLINE_CODE);
		query.setParameter(1, companyCode);
		query.setParameter(2, airlineCode);

		String airlineIdentifier = query
				.getSingleResult(getStringMapper("THRNUMCOD"));
    	return airlineIdentifier;
    }*/

	
	//Commented as part of ICRD-21184

    /**
     *
     * @param companyCode
     * @param airlineId
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
//	public String findCarrierCode(String companyCode, int airlineId)
//			throws SystemException, PersistenceException {
//    	log.entering("ULDDefaultsSqlDAO", "findCarrierCode");
//    	Query query = getQueryManager().createNamedNativeQuery(
//				ULDDefaultsPersistenceConstants.FIND_CARRIER_CODE);
//		query.setParameter(1, companyCode);
//		query.setParameter(2, airlineId);
//    	String carrierCode = query.getSingleResult(getStringMapper("ARLCOD"));
//		return carrierCode;
//    }

    /**
     *
     * @param companyCode
     * @param airlineCodes
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	//Commented as part of ICRD 21184
	/*public HashMap<String, Integer> listAirlineIdentifiers(String companyCode,
			Set<String> airlineCodes) throws SystemException,
			PersistenceException {
    	log.entering("ULDDefaultsSqlDAO", "getAirlineIdentifiers");
    	int index = 0;
    	int iCount = 0;
		HashMap<String, Integer> airlineIdentifiers = new HashMap<String, Integer>();

    	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.LIST_AIRLINE_IDENTIFIERS);
		query.setParameter(++index, companyCode);

		if (airlineCodes != null) {
			query.append(" AND ( ");
			for (String airlineCode : airlineCodes) {
				if (iCount == 0) {
					query
							.append(" ARLMST.TWOAPHCOD = ? OR ARLMST.THRAPHCOD = ? ");
					query.setParameter(++index, airlineCode);
					query.setParameter(++index, airlineCode);
					iCount = 1;
				} else {
					query
							.append(" OR ARLMST.TWOAPHCOD = ? OR ARLMST.THRAPHCOD = ? ");
					query.setParameter(++index, airlineCode);
					query.setParameter(++index, airlineCode);
				}
			}
			query.append(" ) ");

			List<String> resultList = query
					.getResultList(getStringMapper("ARLCODPLUSARLIDR"));
			for (String element : resultList) {
	    		String[] array = element.split("~");
				airlineIdentifiers.put(array[0], Integer.valueOf(array[1]));
	    	}
    	}
    	return airlineIdentifiers;
    }*/

    /**
     *
     * @param companyCode
     * @param airportCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public ULDAirportLocationVO findCurrentLocation(String companyCode,
			String airportCode,String content) throws SystemException, PersistenceException {
    	log.entering("ULDDefaultsSqlDAO", "findCurrentLocation");
		ULDAirportLocationVO uLDAirportLocationVO = null;
    	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_CURRENT_LOCATION);
		query.setParameter(1, companyCode);
		query.setParameter(2, airportCode);
		if(content!=null && content.trim().length()>0){
			query.append(" AND CNT = ? ");	
			query.setParameter(3, content);
		}
		
		return query.getSingleResult(new ULDAirportLocationMapper());

	}
    
    public boolean isDummyULDMovementPresent(String companyCode,
			int carrierId,String flightnum,LocalDate flightDate,String uldNum,String pol,String pou) throws SystemException,PersistenceException{
    	int index = 0;
		boolean isDummyMovementPresent = false;
		log.entering("INSIDE THE SQLDAO", "isDummyULDMovementPresent");
		Query qry = getQueryManager()
		.createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.DUMMY_ULD_MOVEMENT_PRESENT);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, carrierId);
		qry.setParameter(++index, flightnum);
		qry.setParameter(++index, flightDate);
		qry.setParameter(++index, uldNum);
		qry.setParameter(++index, pol);
		qry.setParameter(++index, pou);
		Mapper<String> stringMapper = getStringMapper("MOVCOUNT");
		
		if (Integer.parseInt(qry.getSingleResult(stringMapper))>0) {
			isDummyMovementPresent = true;
		}
		log.log(Log.FINE, "checkDuplicateManufacturerNumber",
				isDummyMovementPresent);
		return isDummyMovementPresent;
    }

	/***************************************************************************
  * @param companyCode
  * @param airportCode
  * @return HashMap<String,Collection<String>>
  * @throws SystemException
  * @throws PersistenceException
  */
	public HashMap<String, Collection<String>> populateLocation(
			String companyCode, String airportCode) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "populateLocation");
		List<ULDVO> uldVOs = null;
		String locationType = "";
		String location = "";
		Collection<String> locations = new ArrayList<String>();
		HashMap<String, Collection<String>> hashMap = new HashMap<String, Collection<String>>();
    	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.POPULATE_CURRENT_LOCATION);
		query.setParameter(1, companyCode);
		query.setParameter(2, airportCode);

		uldVOs = query.getResultList(new PopulateLocationMapper());
		if (uldVOs != null && uldVOs.size() > 0) {
			for (ULDVO uldVO : uldVOs) {
				if (!locationType.equalsIgnoreCase(uldVO.getLocationType())) {
    				locations = new ArrayList<String>();
				}
				locationType = uldVO.getLocationType();
				location = uldVO.getLocation();
				locations.add(location);

				hashMap.put(locationType, locations);
    		}
    	}
    	return hashMap;
    }

    /**
     *
     * @param filterVO
     * @throws SystemException
     * @throws PersistenceException
     */
    public UCMMessageVO generateUCMMessageVO(FlightMessageFilterVO filterVO)
			throws SystemException, PersistenceException {
    	log.entering("ULDDefaultsSqlDAO", "populateLocation");
    	List<UCMMessageVO> uCMMessageVOs = null;
		UCMMessageVO uCMMessageVO = null;
		UCMNilIncomingULDDetailsVO incommingVO = null;
    	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.GENERATE_UCMMESSAGEVO);
		query.setParameter(1, filterVO.getFlightCarrierIdentifier());
		query.setParameter(2, filterVO.getFlightNumber());
		query.setParameter(3, filterVO.getFlightSequenceNumber());
		query.setParameter(4, filterVO.getSegmentSerialNumber());
		query.setParameter(5, filterVO.getCompanyCode());

		uCMMessageVOs = query
				.getResultList(new GenerateUCMMessageMultiMapper());
		if (uCMMessageVOs != null && uCMMessageVOs.size() > 0) {
			uCMMessageVO = uCMMessageVOs.get(0);
		}
		// uCMMessageVO.getUcmIncomingULDHeaderVO();
    	// TO CHANGE

    	uCMMessageVO.setUcmIncomingULDDetailsVOs(null);
		if (uCMMessageVO.getUcmIncomingULDDetailsVOs() == null) {
			incommingVO = new UCMNilIncomingULDDetailsVO();
    		incommingVO.setNilCode("N");
    	}
    	uCMMessageVO.setUcmNilIncomingULDDetailsVO(incommingVO);

		 return uCMMessageVO;

    }

    /**
     *
     * @param companyCode
     * @param airportCode
     * @param uldNumbers
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<ULDVO> validateULDs(String companyCode,
			String airportCode, Collection<String> uldNumbers)
			throws SystemException, PersistenceException {
    	log.entering("ULDDefaultsSqlDAO", "validateULDs");
    	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.VALIDATE_ULDS);

		int iCount = 0;
		int index = 2;
    	query.setParameter(1, companyCode);
    	query.setParameter(2, airportCode);
		if (uldNumbers != null && uldNumbers.size() > 0) {
			for (String uldNum : uldNumbers) {
				if (iCount == 0) {
    				query.append("AND ULDNUM IN ( ?");
					query.setParameter(++index, uldNum);
				} else {
    				query.append(" , ? ");
					query.setParameter(++index, uldNum);
				}
				iCount = 1;
			}
			if (iCount == 1) {
    			query.append(" ) ");
 		    }
    	}
    	return query.getResultList(new ULDDetailsMapper());

    }

    /**
     *
     * @param uldFlightMessageFilterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public boolean validateULDFlightMessageDetails(
            ULDFlightMessageFilterVO uldFlightMessageFilterVO)
            throws SystemException, PersistenceException {
        log.entering("ULDDefaultsSqlDAO", "validateULDFlightMessageDetails");
		Query query = getQueryManager()
				.createNamedNativeQuery(
                ULDDefaultsPersistenceConstants.VALIDATEULDFLIGHTMESSAGEDETAILS);
        int index = 0;
		query.setParameter(++index, uldFlightMessageFilterVO
				.getFlightCarrierId());
        query.setParameter(++index, uldFlightMessageFilterVO.getFlightNumber());
		query.setParameter(++index, uldFlightMessageFilterVO
				.getFlightSequenceNumber());
		query.setParameter(++index, uldFlightMessageFilterVO
				.getLegSerialNumber());
        query.setParameter(++index, uldFlightMessageFilterVO.getCompanyCode());
        query.setParameter(++index, uldFlightMessageFilterVO.getUldNumber());
        query.setParameter(++index, uldFlightMessageFilterVO.getFlightDate());
		query.setParameter(++index, uldFlightMessageFilterVO
				.getPointOfUnloading());
        boolean isValid = false;
        Integer integer = query.getSingleResult(getIntMapper("NUM"));
		if (integer == null) {
            isValid = true;
        }
        log.exiting("ULDDefaultsSqlDAO", "validateULDFlightMessageDetails");
        return isValid;
    }

    /**
     *
     * @param flightMessageFilterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	public FlightDetailsVO findUCMFlightDetails(
			FlightMessageFilterVO flightMessageFilterVO)
			throws SystemException, PersistenceException {
    	 log.entering("ULDDefaultsSqlDAO", "findUCMFlightDetails");
    	 FlightDetailsVO flightDetailsVO = null;
		List<FlightDetailsVO> flightDetailsVOs = null;
    	 Query query = getQueryManager().createNamedNativeQuery(
                 ULDDefaultsPersistenceConstants.GET_UCM_FLIGHTDETAILS);
		query.setParameter(1, flightMessageFilterVO
				.getFlightCarrierIdentifier());
		query.setParameter(2, flightMessageFilterVO.getFlightNumber());
		query.setParameter(3, flightMessageFilterVO.getFlightSequenceNumber());
		query.setParameter(4, flightMessageFilterVO.getSegmentSerialNumber());
		query.setParameter(5, flightMessageFilterVO.getAirportCode());
		query.setParameter(6, flightMessageFilterVO.getCompanyCode());

		flightDetailsVOs = query
				.getResultList(new UCMFlightDetailsMultiMapper());
		if (flightDetailsVOs != null && flightDetailsVOs.size() > 0) {
			flightDetailsVO = flightDetailsVOs.get(0);
    	 }

    	 return flightDetailsVO;
    }

    /**
     *
     * @param companyCode
     * @param airportCode
     * @param facilityType
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	public Collection<ULDAirportLocationVO> listULDAirportLocation(
			String companyCode, String airportCode, String facilityType)
			throws SystemException, PersistenceException {
    	log.entering("ULDDefaultsSqlDAO", "listULDAirportLocation");
		Collection<ULDAirportLocationVO> uLDAirportLocations = null;
    	Query query = getQueryManager().createNamedNativeQuery(
                ULDDefaultsPersistenceConstants.LIST_UKD_AIRPORTLOCATION);
		query.setParameter(1, companyCode);
		query.setParameter(2, airportCode);		
		if (facilityType != null
				&& facilityType.trim().length() > 0) {
			query.append(" AND FACTYP = ?");
			query.setParameter(3,facilityType);
		}
		
        log.log(Log.FINE, "QUERY--------->>", query);
		uLDAirportLocations = query
				.getResultList(new ULDAirportLocationMapper());
		log.log(Log.FINE, "uLDAirportLocations inside SQLDAO",
				uLDAirportLocations);
		return uLDAirportLocations;
    }

    /**
     *
     * @param companyCode
     * @param airportCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<ULDAirportLocationVO> findDefaultFlag(String companyCode,
			String airportCode,ArrayList<String> contents) throws SystemException, PersistenceException {
    	log.entering("ULDDefaultsSqlDAO", "findDefaultFlag");
		Collection<ULDAirportLocationVO> uLDAirportLocationVOs = null;
    	Query query = getQueryManager().createNamedNativeQuery(
                ULDDefaultsPersistenceConstants.FIND_DEFAULTFLAG);
    	int index = 0;
    	int count = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, airportCode);
		
		if(contents!=null && contents.size()>0){
					
			for (String content : contents) {
				if (count == 0) {
				   query.append("AND CNT IN( ? ");
					query.setParameter(++index, content);
				} else {
					query.append(" , ?");
					query.setParameter(++index, content);
				}
				count = 1;
			}
			if (count == 1) {
				query.append(" ) ");
			}
			
		}

    	 return query.getResultList(new ULDAirportLocationMapper());

    }

    /**
     *
     * @param companyCode
     * @param location
     * @param airportCode
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	public int checkForULDLocation(String companyCode, String location,
			String airportCode) throws SystemException, PersistenceException {
	   log.entering("ULDDefaultsSqlDAO", "checkForULDLocation");
	   Query query = getQueryManager().createNamedNativeQuery(
               ULDDefaultsPersistenceConstants.CHECK_ULD_LOCATION);
		query.setParameter(1, companyCode);
		query.setParameter(2, location);
		query.setParameter(3, airportCode);
  	   return query.getSingleResult(getIntMapper("RESULT"));
   }

   /**
    *
    * @param filterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
	public ULDFlightMessageReconcileVO listUCMMessage(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException {
	   log.entering("ULDDefaultsSqlDAO", "checkForULDLocation");
	   Query query = getQueryManager().createNamedNativeQuery(
               ULDDefaultsPersistenceConstants.LIST_UCM_MESSAGE);
	   List<ULDFlightMessageReconcileVO> reconcileVOs = null;
	   ULDFlightMessageReconcileVO reconclieVO = null;
		query.setParameter(1, filterVO.getFlightCarrierId());
		query.setParameter(2, filterVO.getFlightNumber());
		query.setParameter(3, filterVO.getFlightSequenceNumber());
		query.setParameter(4, filterVO.getAirportCode());
		query.setParameter(5, filterVO.getCompanyCode());
		query.setParameter(6, filterVO.getFlightDate());
		int index = 5;
		if (filterVO.getPointOfUnloading() != null
				&& filterVO.getPointOfUnloading().trim().length() > 0) {
			query.append("AND   MSGDTL.POU =?");
			query.setParameter(++index, filterVO.getPointOfUnloading());
		}
		reconcileVOs = query.getResultList(new ULDFlightReconcileMultiMapper());
		if (reconcileVOs != null && reconcileVOs.size() > 0) {
			reconclieVO = reconcileVOs.get(0);
		}
		return reconclieVO;

	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ULDFlightMessageReconcileVO listUCMOUTMessage(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException {
	   log.entering("ULDDefaultsSqlDAO", "listUCMOUTMessage");
	   Query query = getQueryManager().createNamedNativeQuery(
               ULDDefaultsPersistenceConstants.LIST_UCM_OUTMESSAGE);
	   List<ULDFlightMessageReconcileVO> reconcileVOs = null;
	   ULDFlightMessageReconcileVO reconclieVO = null;
		query.setParameter(1, filterVO.getFlightCarrierId());
		query.setParameter(2, filterVO.getFlightNumber());
		query.setParameter(3, filterVO.getFlightSequenceNumber());
		query.setParameter(4, filterVO.getAirportCode());
		query.setParameter(5, filterVO.getCompanyCode());
		int index = 5;
		if (filterVO.getPointOfUnloading() != null
				&& filterVO.getPointOfUnloading().trim().length() > 0) {
			query.append("AND   MSGDTL.POU =?");
			query.setParameter(++index, filterVO.getPointOfUnloading());
		}
		reconcileVOs = query.getResultList(new ULDFlightReconcileMultiMapper());
		if (reconcileVOs != null && reconcileVOs.size() > 0) {
			reconclieVO = reconcileVOs.get(0);
		}
		return reconclieVO;

	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ULDFlightMessageReconcileVO listUCMINMessage(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException {
	   log.entering("ULDDefaultsSqlDAO", "listUCMINMessage");
	   Query query = getQueryManager().createNamedNativeQuery(
               ULDDefaultsPersistenceConstants.LIST_UCM_INMESSAGE);
	   List<ULDFlightMessageReconcileVO> reconcileVOs = null;
	   ULDFlightMessageReconcileVO reconclieVO = null;

		query.setParameter(1, filterVO.getFlightCarrierId());
		query.setParameter(2, filterVO.getFlightNumber());
		query.setParameter(3, filterVO.getFlightSequenceNumber());
		query.setParameter(4, filterVO.getCompanyCode());
		query.setParameter(5, filterVO.getPointOfUnloading());

		reconcileVOs = query.getResultList(new ULDFlightReconcileMultiMapper());
		if (reconcileVOs != null && reconcileVOs.size() > 0) {
		   reconclieVO = reconcileVOs.get(0);
	   }
	   return reconclieVO;
   }

   /**
    *
    * @param filterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
	public Page<ULDFlightMessageReconcileVO> listUCMErrors(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listUCMErrors");
		int jCount = 0;
	   Query query = getQueryManager().createNamedNativeQuery(
               ULDDefaultsPersistenceConstants.LIST_UCM_ERRORS);

		query.setParameter(1, filterVO.getCompanyCode());
		int index = 1;

		if (filterVO.getAirportCode() != null
				&& filterVO.getAirportCode().trim().length() > 0) {
			query.append(" AND   ULDFLTMSGREC.ARPCOD  = ?  ");
			query.setParameter(++index, filterVO.getAirportCode());
		}
		if (filterVO.getFlightCarrierId() > 0) {
		   query.append(" AND   ULDFLTMSGREC.FLTCARIDR = ?  ");
			query.setParameter(++index, filterVO.getFlightCarrierId());
	   }
		if (filterVO.getAirportCode() != null
				&& filterVO.getAirportCode().trim().length() > 0) {
			query.append(" AND   ULDFLTMSGREC.ARPCOD  = ?  ");
			query.setParameter(++index, filterVO.getAirportCode());
		}
		if (filterVO.getFlightNumber() != null
				&& filterVO.getFlightNumber().trim().length() > 0) {
		   query.append(" AND   ULDFLTMSGREC.FLTNUM = ? ");
			query.setParameter(++index, filterVO.getFlightNumber());
	   }
		if (filterVO.getFlightSequenceNumber() > 0) {
		   query.append(" AND    ULDFLTMSGREC.FLTSEQNUM =? ");
			query.setParameter(++index, filterVO.getFlightSequenceNumber());
	   }
		if (filterVO.getFlightDate() != null) {
		   query.append(" AND ULDFLTMSGREC.FLTDAT = ? ");
			query.setParameter(++index, filterVO.getFlightDate());
	   }
		if (filterVO.getMessageType() != null
				&& filterVO.getMessageType().trim().length() > 0) {
		   query.append(" AND    ULDFLTMSGREC.MSGTYP = ? ");
			query.setParameter(++index, filterVO.getMessageType());
	   }

		if (filterVO.getUcmSequenceNumbers() != null
				&& filterVO.getUcmSequenceNumbers().size() > 0) {
			for (String seqNum : filterVO.getUcmSequenceNumbers()) {
				if (jCount == 0) {
				   query.append("AND    ULDFLTMSGREC.SEQNUM IN ( ? ");
					query.setParameter(++index, seqNum);
				} else {
					query.append(" , ?");
					query.setParameter(++index, seqNum);
			   }
				jCount = 1;
			}
			if (jCount == 1) {
				query.append(" ) ");
			}
		}
		if (filterVO.getMessageStatus() != null) {
		   query.append(" AND ULDFLTMSGREC.MSGSNDFLG = ? ");
			query.setParameter(++index, filterVO.getMessageStatus());
	   }
		if (filterVO.getFromDate() != null) {
		   query.append(" AND ULDFLTMSGREC.FLTDAT >= ? ");
			query.setParameter(++index, filterVO.getFromDate());
	   }
		if (filterVO.getToDate() != null) {
		   query.append(" AND ULDFLTMSGREC.FLTDAT <= ? ");
			query.setParameter(++index, filterVO.getToDate());
	   }
	   //query.append("  ORDER BY ULDFLTMSGREC.SEQNUM");
		//added by a-3459 as part of bug 29943 starts
		//Modified as part of bug ICRD-2489 By A-3767 on 07Jun11
		query.append("ORDER BY ULDFLTMSGREC.CMPCOD,ULDFLTMSGREC.MSGTYP,ULDFLTMSGREC.ARPCOD,ULDFLTMSGREC.FLTCARIDR," +
				"ULDFLTMSGREC.FLTNUM,ULDFLTMSGREC.FLTSEQNUM,ULDFLTMSGREC.SEQNUM desc");
		//added by a-3459 as part of bug 29943 ends
		PageableQuery<ULDFlightMessageReconcileVO> pgqry = new PageableQuery<ULDFlightMessageReconcileVO>(
					query, new ListUCMErrorsMultiMapper());
		return pgqry.getPageAbsolute(filterVO.getPageNumber(), filterVO
				.getAbsoluteIndex());

	}

   /**
   *
   * @param filterVO
   * @return
   * @throws SystemException
   * @throws PersistenceException
   */

   public Collection<String> findUCMNoLOV(FlightFilterMessageVO filterVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findUCMNoLOV");
	   int index = 0;
	   Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.FIND_UCM_NO_LOV);
		query.setParameter(++index, filterVO.getCompanyCode());
		query.setParameter(++index, filterVO.getFlightCarrierId());
		query.setParameter(++index, filterVO.getFlightNumber());
		query.setParameter(++index, filterVO.getFlightSequenceNumber());
		query.setParameter(++index, filterVO.getAirportCode());
		query.setParameter(++index, filterVO.getMessageType());
	   return query.getResultList(getStringMapper("UCMNO"));
   }

   /**
    *
    * @param filterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
	public Page<ULDFlightMessageReconcileDetailsVO> listUldErrors(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listUCMErrors");
		int iCount = 0;
		int jCount = 0;
	   Query query = getQueryManager().createNamedNativeQuery(
               ULDDefaultsPersistenceConstants.LIST_ULD_ERRORS);

		query.setParameter(1, filterVO.getAirportCode());
		query.setParameter(2, filterVO.getCompanyCode());
		int index = 2;
		if (filterVO.getFlightCarrierId() > 0) {
		   query.append("AND   ULDFLTMSGRECDTL.FLTCARIDR = ?");
			query.setParameter(++index, filterVO.getFlightCarrierId());
	   }
		if (filterVO.getFlightNumber() != null
				&& filterVO.getFlightNumber().trim().length() > 0) {
		   query.append("AND   ULDFLTMSGRECDTL.FLTNUM = ?");
			query.setParameter(++index, filterVO.getFlightNumber());
	   }
		if (filterVO.getFlightSequenceNumber() > 0) {
		   query.append("AND    ULDFLTMSGRECDTL.FLTSEQNUM =?");
			query.setParameter(++index, filterVO.getFlightSequenceNumber());
	   }
		if (filterVO.getMessageType() != null
				&& filterVO.getMessageType().trim().length() > 0) {
		   query.append("AND    ULDFLTMSGRECDTL.MSGTYP = ?");
			query.setParameter(++index, filterVO.getMessageType());
	   }

		if (filterVO.getUcmSequenceNumbers() != null
				&& filterVO.getUcmSequenceNumbers().size() > 0) {
			for (String seqNum : filterVO.getUcmSequenceNumbers()) {
				if (jCount == 0) {
				   query.append("AND    ULDFLTMSGRECDTL.SEQNUM IN ( ? ");
					query.setParameter(++index, Integer.parseInt(seqNum)); 
				} else {
					query.append(" , ?");
					query.setParameter(++index, Integer.parseInt(seqNum));
			   }
				jCount = 1;
			}
			if (jCount == 1) {
				query.append(" ) ");
			}
		}

		if (filterVO.getUldNumbers() != null
				&& filterVO.getUldNumbers().size() > 0) {
			for (String uldNumber : filterVO.getUldNumbers()) {
				if (iCount == 0) {
				   query.append("AND    ULDFLTMSGRECDTL.ULDNUM IN ( ? ");
					query.setParameter(++index, uldNumber);
				} else {
					query.append(" , ?");
					query.setParameter(++index, uldNumber);
				}
				iCount = 1;
			}
			if (iCount == 1) {
				query.append(" ) ");
			}
		}

		query.append("  ORDER BY ULDFLTMSGREC.SEQNUM");

		PageableQuery<ULDFlightMessageReconcileDetailsVO> pgqry = new PageableQuery<ULDFlightMessageReconcileDetailsVO>(
					query, new ListULDErrorsMapper());

	   return pgqry.getPage(filterVO.getPageNumber());

   }

/**
 * @param flightFilterVO
 * @return Collection<ULDFlightMessageReconcileVO>
 * @throws SystemException
 * @throws PersistenceException
 */
	public Collection<ULDFlightMessageReconcileVO> listUCMsForComparison(
			FlightFilterMessageVO flightFilterVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listUCMsForComparison");

	  Query qry = getQueryManager().createNamedNativeQuery(
			  ULDDefaultsPersistenceConstants.LIST_UCMS_FOR_COMPARISON);
	  int index = 0;

		qry.setParameter(++index, flightFilterVO.getCompanyCode());		
		qry.setParameter(++index, flightFilterVO.getFlightCarrierId());
		qry.setParameter(++index, flightFilterVO.getFlightNumber());
		
		if(FlightFilterMessageVO.FLAG_YES.equals(flightFilterVO.getFromMarkFlightMvt())){			
			if (flightFilterVO.getDestinations() != null && flightFilterVO.getDestinations().size() > 0) {
				int iCount = 0;
				for (String airport : flightFilterVO.getDestinations()) {
					if (iCount == 0) {
					   qry.append("AND RECDTL.POU IN( ? ");
					   qry.setParameter(++index, airport);
					} else {
						qry.append(" , ?");
						qry.setParameter(++index, airport);
					}
					iCount = 1;
				}
				if (iCount == 1) {
					qry.append(" ) ");
				}
			}			
		}else{
			qry.append(" AND REC.ARPCOD = ? ");
			qry.setParameter(++index, flightFilterVO.getAirportCode());
		}
		
		
		if (flightFilterVO.getFlightSequenceNumber() > 0) {
		  qry.append(" AND REC.FLTSEQNUM = ?  ");
			qry.setParameter(++index, flightFilterVO.getFlightSequenceNumber());
	  }
		if (flightFilterVO.getMessageType() != null
				&& flightFilterVO.getMessageType().trim().length() > 0) {
		  qry.append(" AND REC.MSGTYP = ?  ");
			qry.setParameter(++index, flightFilterVO.getMessageType());
	  }
		if(!FlightFilterMessageVO.FLAG_YES.equals(flightFilterVO.getFromMarkFlightMvt())){
			if (flightFilterVO.getUcmSequenceNumbers().size() > 1) {
			  qry.append(" AND REC.SEQNUM IN ( ? , ? ) ");
			} else {
			  qry.append(" AND REC.SEQNUM = ? ");
		  }
			for (String i : flightFilterVO.getUcmSequenceNumbers()) {
				qry.setParameter(++index, Integer.parseInt(i));
		  }
		}
		List<ULDFlightMessageReconcileVO> reconcileVOs = qry
				.getResultList(new UCMReconcileMultiMapper());

		log.log(Log.INFO, "%%%%%%%%%%%%%%%size  ", reconcileVOs.size());
		log
				.log(
						Log.INFO,
						"%%%%%%%%%%%%%%%List<ULDFlightMessageReconcileVO> reconcileVOs ",
						reconcileVOs);
	return reconcileVOs;
   }
	
	
	public Collection<ULDFlightMessageReconcileVO> listUCMsForFlightMovement(
			FlightFilterMessageVO flightFilterVO) throws SystemException,
			PersistenceException {

		log.entering("ULDDefaultsSqlDAO", "listUCMsForFlightMovement");

	  Query qry = getQueryManager().createNamedNativeQuery(
			  ULDDefaultsPersistenceConstants.LIST_UCMS_FOR_FLIGHT_MVT);
	  
	  	int index = 0;

		qry.setParameter(++index, flightFilterVO.getCompanyCode());		
		qry.setParameter(++index, flightFilterVO.getFlightCarrierId());
		qry.setParameter(++index, flightFilterVO.getFlightNumber());
		qry.setParameter(++index, flightFilterVO.getFlightSequenceNumber());
		qry.setParameter(++index, flightFilterVO.getMessageType());
		
		if (flightFilterVO.getFlightOrigins() != null && flightFilterVO.getFlightOrigins().size() > 0) {
			int iCount = 0;
			for (String origin : flightFilterVO.getFlightOrigins()) {
				if (iCount == 0) {
				   qry.append("AND REC.ARPCOD IN( ? ");
				   qry.setParameter(++index, origin);
				} else {
					qry.append(" , ?");
					qry.setParameter(++index, origin);
				}
				iCount = 1;
			}
			if (iCount == 1) {
				qry.append(" ) ");
			}
		}			
			//Modified by A-7359 for ICRD-192413
		qry.append("GROUP BY REC.FLTCARIDR, REC.FLTNUM, REC.FLTSEQNUM, REC.ARPCOD, REC.CMPCOD, REC.MSGTYP,REC.FLTDAT,REC.UCMMSGSRC) MST INNER JOIN ULDFLTMSGRECDTL RECDTL");
		qry.append("ON MST.FLTCARIDR = RECDTL.FLTCARIDR AND MST.FLTNUM =  RECDTL.FLTNUM AND MST.FLTSEQNUM = RECDTL.FLTSEQNUM ");
		qry.append("AND MST.ARPCOD = RECDTL.ARPCOD AND MST.CMPCOD = RECDTL.CMPCOD AND MST.MSGTYP = RECDTL.MSGTYP AND MST.SEQ = RECDTL.SEQNUM");
			
		if (flightFilterVO.getDestinations() != null && flightFilterVO.getDestinations().size() > 0) {
			int iCount = 0;
			for (String destination : flightFilterVO.getDestinations()) {
				if (iCount == 0) {
				   qry.append("WHERE RECDTL.POU IN ( ? ");
				   qry.setParameter(++index, destination);
				} else {
					qry.append(" , ?");
					qry.setParameter(++index, destination);
				}
				iCount = 1;
			}
			if (iCount == 1) {
				qry.append(" ) ");
			}
		}
		
		List<ULDFlightMessageReconcileVO> reconcileVOs = qry
				.getResultList(new UCMReconcileMultiMapper());

		log.log(Log.INFO, "%%%%%%%%%listUCMsForFlightMovement %%%%%%size  ",
				reconcileVOs.size());
		log
				.log(
						Log.INFO,
						"%%%%%%%%%%%%%%%List<ULDFlightMessageReconcileVO> reconcileVOs ",
						reconcileVOs);
	return reconcileVOs;
   
	}

	/***************************************************************************
    * @param reconcileVO
    * @return ULDFlightMessageReconcileVO
    * @throws SystemException
    * @throws PersistenceException
    */
	public ULDFlightMessageReconcileVO findCounterUCM(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findCounterUCM");

	   ULDFlightMessageReconcileVO messageReconcileVO = null;
	   int index = 0;

	   Query qry = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.FIND_COUNTER_UCM);
		qry.setParameter(++index, reconcileVO.getCompanyCode());
		qry.setParameter(++index, reconcileVO.getAirportCode());
		qry.setParameter(++index, reconcileVO.getFlightCarrierIdentifier());
		qry.setParameter(++index, reconcileVO.getFlightNumber());
		qry.setParameter(++index, reconcileVO.getFlightSequenceNumber());
		qry.setParameter(++index, reconcileVO.getMessageType());
		if(reconcileVO.getMessageSource()!=null && reconcileVO.getMessageSource().trim().length() > 0){
			qry.append(" AND REC.UCMMSGSRC = ? ");
			qry.setParameter(++index, reconcileVO.getMessageSource());
		}
		// Added by Preet on 10Sep08 starts
		// To serach the last UCM sent
		if(ULDFlightMessageReconcileVO.FLAG_YES.equals(reconcileVO.getToSearchMaxSeqNum())){			
			qry.append(" AND REC.SEQNUM = (SELECT MAX( SEQNUM) FROM ULDFLTMSGREC WHERE FLTCARIDR = ? ");
			 qry.append(" AND FLTNUM = LPAD(?, 4, '0') AND FLTSEQNUM = ? AND ARPCOD = ? AND CMPCOD= ?  AND  MSGTYP = ?");
			qry.setParameter(++index, reconcileVO.getFlightCarrierIdentifier());
			qry.setParameter(++index, reconcileVO.getFlightNumber());
			qry.setParameter(++index, reconcileVO.getFlightSequenceNumber());
			qry.setParameter(++index, reconcileVO.getAirportCode());
			qry.setParameter(++index, reconcileVO.getCompanyCode());
			qry.setParameter(++index, reconcileVO.getMessageType());
			//Added by A-7359 for ICRD-192413
			if(reconcileVO.getMessageSource()!=null && reconcileVO.getMessageSource().trim().length() > 0){
				qry.append(" AND UCMMSGSRC = ? ");
				qry.setParameter(++index, reconcileVO.getMessageSource());
			}
			qry.append(" ) ");
		}
		//Added by Preet on 10Sep08 ends 
		else if (reconcileVO.getSequenceNumber() != null) {
			qry.append(" AND REC.SEQNUM = ? ");
			qry.setParameter(++index, Integer.parseInt(reconcileVO.getSequenceNumber()));
		}
		log.log(Log.INFO, "%%%%%  Query ", qry);
		List<ULDFlightMessageReconcileVO> reconcileVOs = qry
				.getResultList(new UCMReconcileMultiMapper());
		if (reconcileVOs != null && reconcileVOs.size() > 0) {
			messageReconcileVO = reconcileVOs.get(0);
		}
		log.log(Log.INFO, "%%%%%%%% messageReconcileVO ", messageReconcileVO);
	return messageReconcileVO;
   }

   /**
    *
    * @param filterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
   public Collection<ULDFlightMessageReconcileDetailsVO> listUCMINOUTMessage(
			FlightFilterMessageVO filterVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listUCMINOUTMessage");
		Collection<ULDFlightMessageReconcileDetailsVO> detailsVOs = null;
	   Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.LIST_UCMINOUT_MESSAGE);
		query.setParameter(1, filterVO.getFlightCarrierId());
		query.setParameter(2, filterVO.getFlightNumber());
		query.setParameter(3, filterVO.getFlightSequenceNumber());
		query.setParameter(4, filterVO.getCompanyCode());
		query.setParameter(5, filterVO.getPointOfUnloading());
		query.setParameter(6, filterVO.getFlightCarrierId());
		query.setParameter(7, filterVO.getFlightNumber());
		query.setParameter(8, filterVO.getFlightSequenceNumber());
		query.setParameter(9, filterVO.getAirportCode());
		query.setParameter(10, filterVO.getCompanyCode());


	   return query.getResultList(new ListUCMINOUTMapper());
   }

   /**
    *
    * @param flightFilterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
   public Collection<ULDFlightMessageReconcileDetailsVO> listUCMOUTForInOutMismatch(
			FlightFilterMessageVO flightFilterVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listUCMOUTForInOutMismatch");
		Collection<ULDFlightMessageReconcileDetailsVO> detailsVOs = null;
	   Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.LIST_UCMOUT_FORMISMATCH);
		query.setParameter(1, flightFilterVO.getFlightCarrierId());
		query.setParameter(2, flightFilterVO.getFlightNumber());
		query.setParameter(3, flightFilterVO.getFlightSequenceNumber());
		query.setParameter(4, flightFilterVO.getCompanyCode());
		query.setParameter(5, flightFilterVO.getMessageType());
		query.setParameter(6, flightFilterVO.getPointOfUnloading());


	   return query.getResultList(new ListUCMINOUTMapper());
   }

   /**
    * 
    */
   public Page<ULDVO> findUldDetailsForSCM(SCMMessageFilterVO filterVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "getUldDetailsForSCM");
		// Added By Sreekumar S as a part of implementing pagination in
		// GenerateSCM Screen
		log.entering("ULDDefaultsSqlDAO", "getUldDetailsForSCM");

       String nativeQuery = getQueryManager()
               .getNamedNativeQueryString(ULDDefaultsPersistenceConstants.LIST_ULD_FORSCM);
       //dynamically modifing the query from xml based of the data source.
       if (isOracleDataSource()) {
           String dynamicQuery = " 1 ";
           nativeQuery = String.format(nativeQuery, dynamicQuery);
       } else {
           String dynamicQuery = " ((1)::NUMERIC || ' days')::INTERVAL ";
           nativeQuery = String.format(nativeQuery, dynamicQuery);
       }

        Query baseQuery = getQueryManager().createNativeQuery(nativeQuery);
		PageableNativeQuery <ULDVO> finalQuery = new ListULDDetailsForSCMFilterQuery(
				filterVO.getTotalRecords(), baseQuery.toString(),new ULDDetailsMapper(),filterVO);
		if(filterVO.getDynamicQueryString()!=null && 
				filterVO.getDynamicQueryString().trim().length()>0){
			finalQuery.setDynamicQuery(filterVO.getDynamicQueryString());
		}
		finalQuery.setCacheable(false);
		return finalQuery.getPage(filterVO.getPageNumber());
		

	}

   /**
    *
    * @param filterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
   public Page<ULDSCMReconcileVO> listSCMMessage(SCMMessageFilterVO filterVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listSCMMessage");
	    Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.LIST_SCM_MESSAGE);
	    
	    
	    StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULD_DEFAULTS_DENSE_RANK_QUERY);
		rankQuery.append(" ").append(ULD_SCM_RANK_QUERY);
		String finalQuery = rankQuery.append(query).toString();
		PageableNativeQuery<ULDSCMReconcileVO> pgqry = new PageableNativeQuery<ULDSCMReconcileVO>(
				filterVO.getTotalRecordsCount(),finalQuery, new ListSCMMultiMapper());
	    
	   
		pgqry.setParameter(1, filterVO.getCompanyCode());
		pgqry.setParameter(2, filterVO.getAirportCode());
		pgqry.setParameter(3, filterVO.getFlightCarrierIdentifier());
		/*
		 * if(filterVO.getStockControlDate() != null ){ query.append(" AND
		 * ULDSCMMSGREC.STKCHKDAT >= ?");
		 * query.setParameter(4,filterVO.getStockControlDate()); }
		 */

		if (filterVO.getSequenceNumber() != null
				&& filterVO.getSequenceNumber().trim().length() > 0) {
			pgqry.append(" AND  ULDSCMMSGREC.SEQNUM = ? ");
			pgqry.setParameter(4, filterVO.getSequenceNumber());
	    }
		pgqry.append(" ORDER BY TO_NUMBER(ULDSCMMSGREC.SEQNUM) ");
		/*  PageableQuery<ULDSCMReconcileVO> pgqry = new PageableQuery<ULDSCMReconcileVO>(
					query, new ListSCMMultiMapper());
		  return pgqry.getPageAbsolute(filterVO.getPageNumber(), filterVO
				.getAbsoluteIndex());
		*/
		pgqry.append(ULD_DEFAULTS_SUFFIX_QUERY);
		return pgqry.getPage(filterVO.getPageNumber());
   }

   /**
    *
    * @param filterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
	public Page<ULDSCMReconcileDetailsVO> listULDErrorsInSCM(
			SCMMessageFilterVO filterVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listULDErrorsInSCM");
		int iCount = 0;
	    int index = 0;
	    Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.LIST_ULDERRORS_INSCM);	
		//Added by Sreekumar S - AirNZ521 starts
		
		//added by A-6344 for ICRD-114404 starts
		
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULD_DEFAULTS_DENSE_RANK_QUERY);
		rankQuery.append(" ").append(ULD_ERRORS_RANK_QUERY);
		String finalQuery = rankQuery.append(query).toString();
		PageableNativeQuery<ULDSCMReconcileDetailsVO> pgqry = new PageableNativeQuery<ULDSCMReconcileDetailsVO>(
				filterVO.getTotalRecordsCount(),finalQuery, new ListULDErrorsForSCMMapper());
		
		pgqry.setParameter(++index, filterVO.getCompanyCode());
		pgqry.setParameter(++index, filterVO.getAirportCode());
		pgqry.setParameter(++index, filterVO.getFlightCarrierIdentifier());
		if (filterVO.getStockControlDate() != null) {
			pgqry.append(" AND  ULDSCMMSGREC.STKCHKDAT >=? ");
			pgqry.setParameter(++index, filterVO.getStockControlDate());
		}
		if (filterVO.getSequenceNumber() != null
				&& filterVO.getSequenceNumber().trim().length() > 0) {
			pgqry.append("AND  ULDSCMMSGRECDTL.SEQNUM =?");
			pgqry.setParameter(++index, filterVO.getSequenceNumber());
	    }
		if (filterVO.getUldNumbers() != null
				&& filterVO.getUldNumbers().size() > 0) {
			for (String uldNumber : filterVO.getUldNumbers()) {
				if (iCount == 0) {
					pgqry.append("AND ULDSCMMSGRECDTL.ULDNUM IN ( ? ");
					pgqry.setParameter(++index, uldNumber);
				} else {
					pgqry.append(" , ?");
					pgqry.setParameter(++index, uldNumber);
				}
				iCount = 1;
			}
			if (iCount == 1) {
				pgqry.append(" ) ");
			}
		}
		if (filterVO.getErrorCode() != null
				&& filterVO.getErrorCode().trim().length() > 0) {
			    pgqry.append(" AND ( ULDSCMMSGRECDTL.ERRCOD = ? ");
		    if("ERR2".equals(filterVO.getErrorCode())){
		    	pgqry.setParameter(++index, filterVO.getErrorCode());
		    	pgqry.append(" OR ULDSCMMSGRECDTL.ULDSTKSTA IN ('F','N') )");
		    }else if("ERR1".equals(filterVO.getErrorCode())){
		    	pgqry.setParameter(++index, filterVO.getErrorCode());
		    	pgqry.append(" OR ULDSCMMSGRECDTL.ULDSTKSTA = 'M' )");
		    }else{
		    	pgqry.append(" )");
		    	pgqry.setParameter(++index, filterVO.getErrorCode());
		    }
	    }
		pgqry.append(ULD_DEFAULTS_SUFFIX_QUERY);
		//added by A-6344 for ICRD-114404 ends
		log.log(Log.FINE, "Query------->>", query);
		

	    return pgqry.getPage(filterVO.getPageNumber());

   }

  /**
   * @param flightMessageReconcileVO
   * @return ULDFlightMessageReconcileVO
   * @throws SystemException
   * @throws PersistenceException
   */
	public ULDFlightMessageReconcileVO checkForINOUT(
			ULDFlightMessageReconcileVO flightMessageReconcileVO)
			throws SystemException, PersistenceException {
		log.entering("ULDFlightMessageReconcileVO", "checkForINOUT");
	   int index = 0;
	   String messageType = "OUT";
	   ULDFlightMessageReconcileVO messageReconcileVO = null;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.CHECK_FOR_UCM_INOUT);
		query.setParameter(++index, flightMessageReconcileVO.getCompanyCode());
		query.setParameter(++index, flightMessageReconcileVO.getAirportCode());
		query.setParameter(++index, flightMessageReconcileVO
				.getFlightCarrierIdentifier());
		query.setParameter(++index, flightMessageReconcileVO.getFlightNumber());
		query.setParameter(++index, flightMessageReconcileVO
				.getFlightSequenceNumber());
		query.setParameter(++index, messageType);
		if (flightMessageReconcileVO.getOutSequenceNumber() != null) {
		   query.append(" AND REC.SEQNUM = ? ");
			query.setParameter(++index, flightMessageReconcileVO
					.getOutSequenceNumber());
		}

		List<ULDFlightMessageReconcileVO> reconcileVOs = query
				.getResultList(new UCMReconcileMultiMapper());
		if (reconcileVOs != null && reconcileVOs.size() > 0) {
			messageReconcileVO = reconcileVOs.get(0);
		}
		log.log(Log.INFO, "%%%%%%%% messageReconcileVO ", messageReconcileVO);
		return messageReconcileVO;
	}
	  /**
	   * @param reconcileVO
	   * @return flightArrivalStatus
	   * @throws SystemException
	   * @throws PersistenceException
	   */
	public Collection<String> findFlightArrivalStatus(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findFlightStatus");
		Collection<String> flightArrivalStatus=null;
		int index = 0;
		 Query query = getQueryManager().createNamedNativeQuery(
				   ULDDefaultsPersistenceConstants.FIND_FLIGHT_ARRIVAL);
			query.setParameter(++index, reconcileVO.getCompanyCode());
			query.setParameter(++index, reconcileVO.getFlightCarrierIdentifier());
			query.setParameter(++index, reconcileVO.getFlightNumber());
			query.setParameter(++index, reconcileVO.getFlightSequenceNumber());
			flightArrivalStatus = query.getResultList(getStringMapper("ATA"));

			log.log(Log.INFO, "********> flightArrivalStatus ",
					flightArrivalStatus);
		return flightArrivalStatus;
	}
	  /**
	   * @param flightMessageReconcileVO
	   * @return ULDFlightMessageReconcileVO
	   * @throws SystemException
	   * @throws PersistenceException
	   */
	public Collection<String> findUcmInStatus(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "ucmInStatus");
		Collection<String> ucmInStatus=null;
		int index = 0;
		 Query query = getQueryManager().createNamedNativeQuery(
				   ULDDefaultsPersistenceConstants.FIND_UCM_IN_STATUS);
			query.setParameter(++index, reconcileVO.getCompanyCode());
			query.setParameter(++index, reconcileVO.getFlightCarrierIdentifier());
			query.setParameter(++index, reconcileVO.getFlightNumber());
			query.setParameter(++index, reconcileVO.getFlightSequenceNumber());
			ucmInStatus = query.getResultList(getStringMapper("FLTNUM"));

			log.log(Log.INFO, "********> flightArrivalStatus ", ucmInStatus);
		return ucmInStatus;
	}
	/**
	 * 
	 * @param reconcileVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> findInMesssageAirports(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findInMesssageAirports");
	   Collection<String> airportCodes = null;
		int iCount = 0;

		String[] airports = null;
		String[] stations = null;

	   Collection<String> pous = new ArrayList<String>();
		if (reconcileVO.getFlightRoute() != null
				&& reconcileVO.getFlightRoute().trim().length() > 0) {
			airports = reconcileVO.getFlightRoute().split(
					reconcileVO.getAirportCode());
			if (airports[1] != null) {
			   stations = airports[1].split("-");
				for (int k = 0; k < stations.length; k++) {
					if (stations[k] != null && stations[k].trim().length() > 0) {
				    	 pous.add(stations[k]);
				    }
			   }
		   }
	   }
		log.log(Log.INFO, "%%%%%%%%pous ", pous);
		Collection<ULDFlightMessageReconcileDetailsVO> detailsVOs = reconcileVO
				.getReconcileDetailsVOs();

		if (detailsVOs != null && detailsVOs.size() > 0) {
			for (ULDFlightMessageReconcileDetailsVO detailsVO : detailsVOs) {
				if (!pous.contains(detailsVO.getPou())) {
				   pous.add(detailsVO.getPou());
			   }
		   }
	   }

	   Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.FIND_INMESSAGE_AIRPORTS);

		query.setParameter(1, reconcileVO.getFlightCarrierIdentifier());
		query.setParameter(2, reconcileVO.getFlightNumber());
		query.setParameter(3, reconcileVO.getFlightSequenceNumber());
		query.setParameter(4, reconcileVO.getCompanyCode());

		int index = 4;

		if (pous != null && pous.size() > 0) {
			for (String airport : pous) {
				if (iCount == 0) {
				   query.append("AND ARPCOD IN( ? ");
					query.setParameter(++index, airport);
				} else {
					query.append(" , ?");
					query.setParameter(++index, airport);
				}
				iCount = 1;
			}
			if (iCount == 1) {
				query.append(" ) ");
			}
		}

	   airportCodes = query.getResultList(getStringMapper("ARPCOD"));

		log.log(Log.INFO, "%%%%%%%%airportCodes ", airportCodes);
	return airportCodes;
   }

   /**
    *
    * @param filterVO
 * @return
    * @throws SystemException
    * @throws PersistenceException
    */
   public ULDSCMReconcileVO sendSCMMessage(SCMMessageFilterVO filterVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "sendSCMMessage");
	   Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.SEND_SCM_MESSAGE);
	   query.setSensitivity(true);
	   List<ULDSCMReconcileVO>  uLDSCMReconcileVOs = null;
		ULDSCMReconcileVO uLDSCMReconcileVO = null;
		//modified by A-6344 for ICRD-97948
		query.setParameter(1, filterVO.getSequenceNumber());
		query.setParameter(2, filterVO.getSequenceNumber());
		query.setParameter(3, filterVO.getCompanyCode());
		query.setParameter(4, filterVO.getAirportCode());
		query.setParameter(5, filterVO.getFlightCarrierIdentifier());
	    LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		query.setParameter(6, currentDate.toStringFormat("yyyy-MM-dd").substring(0,10));
		query.setParameter(7, filterVO.getSequenceNumber());
		query.setParameter(8, filterVO.getCompanyCode());
		query.setParameter(9, filterVO.getAirportCode());
		query.setParameter(10, filterVO.getFlightCarrierIdentifier());
		if(filterVO.getDynamicQueryString()!=null && 
				filterVO.getDynamicQueryString().trim().length()>0){
			query.setDynamicQuery(filterVO.getDynamicQueryString());
		}
		uLDSCMReconcileVOs = query
				.getResultList(new SendSCMMessageMultiMapper());

		if (uLDSCMReconcileVOs != null && uLDSCMReconcileVOs.size() > 0) {
		   uLDSCMReconcileVO = uLDSCMReconcileVOs.get(0);
	   }

	   return uLDSCMReconcileVO;

   }

   /**
   *
   * @param filterVO
   * @return
   * @throws SystemException
   * @throws PersistenceException
   */
  public Collection<ULDPoolOwnerVO> listULDPoolOwner(
			ULDPoolOwnerFilterVO filterVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listULDPoolOwner");
		int index = 1;
		Collection<ULDPoolOwnerVO> uLDPoolOwnerVOs = null;
		ULDPoolOwnerVO uLDPoolOwnerVO = null;
		log.log(Log.FINE, "Before executing the query");
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.LIST_ULD_POOLOWNER);
		query.setParameter(1, filterVO.getCompanyCode());
		int airlineOne = filterVO.getAirlineIdentifierOne();
		int airlineTwo = filterVO.getAirlineIdentifierTwo();
		String airport = filterVO.getAirport();
		if (airlineOne > 0) {
			query.append(" AND ULDPOLOWNMST.ARLONE = ? ");
			query.setParameter(++index, airlineOne);
		}
		if (airlineTwo > 0) {
			query.append(" AND ULDPOLOWNMST.ARLTWO = ? ");
			query.setParameter(++index, airlineTwo);
		}
		if (airport != null && airport.trim().length() != 0) {
			query.append(" AND ULDPOLOWNMST.ARPCOD = ? ");
			query.setParameter(++index, airport);
		}
		query
				.append(" ORDER BY ULDPOLOWNMST.ARPCOD, ULDPOLOWNMST.ARLONE,ULDPOLOWNMST.ARLTWO, ULDPOLSEGEXP.SEQNUM");
		log.log(Log.FINE, "After executing the query");

		// log.log(Log.FINE,"ULDPoolOwnerVOS inside
		// SQLDAO--------------->>>>"+uLDPoolOwnerVOs);
		return query.getResultList(new ListULDPoolOwnerMultiMapper());
	}

   /**
    *
    * @param filterVO
    * @return
    * @throws SystemException
    * @throws PersistenceException
    */
   public Page<ULDSCMReconcileVO> listSCMMessageLOV(SCMMessageFilterVO filterVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listSCMMessageLOV");
	    // Modified by A-5280 for ICRD-32647
		PageableNativeQuery<ULDSCMReconcileVO> pgqry = null;
		String query = null;
   	
   	    query = getQueryManager().getNamedNativeQueryString(ULDDefaultsPersistenceConstants.LIST_SCM_MESSAGE_LOV);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(query);
		// Added by A-5280 for ICRD-32647 ends
		
		// Modified by A-5280 for ICRD-32647
		pgqry = new PageableNativeQuery<ULDSCMReconcileVO>(0,
				rankQuery.toString(), new ListSCMMessageLOVMapper());
	   
		pgqry.setParameter(1, filterVO.getCompanyCode());
		pgqry.setParameter(2, filterVO.getAirportCode());
		pgqry.setParameter(3, filterVO.getFlightCarrierIdentifier());
		if (filterVO.getSequenceNumber() != null
				&& filterVO.getSequenceNumber().trim().length() > 0) {
			pgqry.append(" AND ULDSCMMSGREC.SEQNUM = ? ");
			pgqry.setParameter(4, filterVO.getSequenceNumber());
		}
		//Added by A-7426 as part of ICRD-196478 starts
		pgqry.append(" ORDER BY ULDSCMMSGREC.STKCHKDAT DESC ");
		//Added by A-7426 as part of ICRD-196478 ends
		
		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 ends

	   return pgqry.getPage(filterVO.getPageNumber());

	}

	 /* MAPPER FOR SCM SEQNUM LOV ACCEPTS AIRLINE IDENTIFIER AND AIRPORT
	 * ListSCMMessageLOVMapper.java Created on Dec 19, 2005
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
   /**
	 * @param rs
	 * @return
	 * @throws SQLException
	 *
   */
   private class ListSCMMessageLOVMapper  implements Mapper<ULDSCMReconcileVO> {
	
		public ULDSCMReconcileVO map(ResultSet rs) throws SQLException {
		   ULDSCMReconcileVO uLDSCMReconcileVO = new ULDSCMReconcileVO();

		   uLDSCMReconcileVO.setCompanyCode(rs.getString("CMPCOD"));
		   uLDSCMReconcileVO.setSequenceNumber(rs.getString("SEQNUM"));
			if (rs.getDate("STKCHKDAT") != null) {
			   uLDSCMReconcileVO.setStockCheckDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, rs
								.getTimestamp("STKCHKDAT")));
		   }

		   uLDSCMReconcileVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
		   uLDSCMReconcileVO.setAirportCode(rs.getString("ARPCOD"));
		   uLDSCMReconcileVO.setMessageSendFlag(rs.getString("MSGSNDFLG"));
			log.log(Log.INFO, "%%%%%%%%uLDSCMReconcileVO ", uLDSCMReconcileVO);
		return uLDSCMReconcileVO;
	   }
   }

   /**
    *
    * @param companyCode
    * @param uldNumber
    * @param scmSequenceNumber
    * @param airportCode
    * @return findAirlineIdentifierForSCM
    * @throws SystemException
    * @throws PersistenceException
    */
	public int findAirlineIdentifierForSCM(String companyCode,
			String uldNumber, String scmSequenceNumber, String airportCode)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listSCMMessageLOV");
	   Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.FIND_AIRLINEID_FORSCM);
		query.setParameter(1, companyCode);
		query.setParameter(2, uldNumber);
		query.setParameter(3, scmSequenceNumber);
		query.setParameter(4, airportCode);

	   return query.getSingleResult(getIntMapper("ARLIDR"));
   }

	/***************************************************************************
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
	public Collection<String> findSCMFromMonitorULD(String companyCode ,ULDStockListVO uLDStockListVO/*String companyCode,
			String airportCode,
			int airlineIdentifier,
			String uldIdentifier,
			LocalDate date*/) 
   throws SystemException, PersistenceException{
	   log.entering("ULDDefaultsSqlDAO","findSCMFromMonitorULD");
	   Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.FIND_MONITOR_ULD_STOCK_SCM_ULDS);

	   query.setParameter(1,uLDStockListVO.getAirlineIdentifier());
	   query.setParameter(2,uLDStockListVO.getAirlineIdentifier());
	   query.setParameter(3,companyCode);
	   query.setParameter(4,uLDStockListVO.getAirlineIdentifier());
/*	   query.setParameter(4,uldIdentifier);
	   query.setParameter(6,airportCode);
	   query.setParameter(7,date.toCalendar());*/
	   int index=4;
	   //Added as part of ICRD-334152
	   if(uLDStockListVO.getOwnerAirlineIdentifier()>0) {
		   log.log(Log.FINE, "inside OwnerAirlineIdentifier", uLDStockListVO.getOwnerAirlineIdentifier());
			query.append(" AND mst.OWNARLIDR = ? ");
			   query.setParameter(++index, uLDStockListVO.getOwnerAirlineIdentifier());
	   }
	   if(uLDStockListVO.getUldTypeCode() != null && uLDStockListVO.getUldTypeCode().trim().length() > 0)
	   {
		   log.log(Log.FINE, "inside getUldTypeCode", uLDStockListVO.getUldTypeCode());
		query.append(" AND mst.ULDTYP = ? ");
		   query.setParameter(++index, uLDStockListVO.getUldTypeCode());
	   }
	   if(uLDStockListVO.getStationCode() != null && uLDStockListVO.getStationCode().trim().length() > 0)
	   {
		   log.log(Log.FINE, "inside getStationCode", uLDStockListVO.getStationCode());
		query.append(" AND mst.CURARP = ? ");
		   query.setParameter(++index, uLDStockListVO.getStationCode());
	   }
	   if(uLDStockListVO.getUldGroupCode() != null && uLDStockListVO.getUldGroupCode().trim().length() > 0)
	   {
		   log.log(Log.FINE, "inside getUldGroupCode", uLDStockListVO.getUldGroupCode());
		query.append(" AND mst.ULDGRPCOD = ? ");
		   query.setParameter(++index, uLDStockListVO.getUldGroupCode());
	   }
	   if(uLDStockListVO.getUldNature() != null && uLDStockListVO.getUldNature().trim().length() > 0)
	   {
		   log.log(Log.FINE, "inside getUldNature", uLDStockListVO.getUldNature());
		query.append(" AND mst.ULDNAT = ? ");
		   query.setParameter(++index, uLDStockListVO.getUldNature());
	   }
	   if(uLDStockListVO.getStockDate() != null)
	   {
		   log.log(Log.FINE, "inside getStockDate", uLDStockListVO.getStockDate());
		query.append(" AND mst.LSTMVTDAT <= ? ");
		   query.setParameter(++index, uLDStockListVO.getStockDate().toCalendar());
	   }
	   //Modified by A-4393 for ICRD-269790	 starts here
	   if(uLDStockListVO.isDiscrepancyCheck()){
		   query.append("  )Z where    COALESCE(DISCOD,'@')!='M' ");
	   }else{
	   query.append(" )z ");
	   }  
       //Modified by A-4393 for ICRD-269790	 ends here
	   log.log(Log.FINE, "---QUERY-->>", query);
	return query.getResultList( getStringMapper("ULDNUM"));
   }

   /**
    * @author A-1950
    * @param detailsVO
    * @throws SystemException
    * @throws PersistenceException
    * @return isAPoolOwner
    */
	public boolean checkForPoolOwners(
			ULDFlightMessageReconcileDetailsVO detailsVO)
			throws SystemException, PersistenceException {
	   boolean isAPoolOwner = false;
		log.entering("ULDDefaultsSqlDAO", "checkForPoolOwners");
	   Query qry = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.CHECK_FOR_POOL_OWNERS);

	   LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();

	   int index = 0;
		qry.setParameter(++index, detailsVO.getCompanyCode());
		qry.setParameter(++index, logonAttributes.getOwnAirlineIdentifier());
		qry.setParameter(++index, detailsVO.getAirportCode());
		qry.setParameter(++index, detailsVO.getPou());
		qry.setParameter(++index, detailsVO.getFlightCarrierIdentifier());
		qry.setParameter(++index, detailsVO.getFlightNumber());
		qry.setParameter(++index, detailsVO.getFlightDate());

		if ("1".equals(qry.getSingleResult(getStringMapper("RESULT")))) {
			log.log(Log.INFO, " %%%%%%% IA A POOLOWNER   ");
		   isAPoolOwner = true;
	   }
		log.log(Log.INFO, " PoolOwners or Not", isAPoolOwner);
	return isAPoolOwner;
   }

	/**
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public ULDListVO findULDListForHHT(String companyCode, String uldNumber)
		throws SystemException {
		log.entering(MODULE_NAME, "findULDListForHHT");
		int index = 0;
		log.entering("INSIDE THE SQLDAO", "findULDListForHHT");
		Query qry = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULDS_FOR_HHT);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, uldNumber);
		ULDListVO uldListVO = null;
		ArrayList<ULDListVO> uldlists = (ArrayList<ULDListVO>) qry
				.getResultList(new ULDListMapper());
		if (uldlists != null && uldlists.size() > 0) {
			uldListVO = uldlists.get(0);
		}
		return uldListVO;

	}

	//Commented as part of ICRD-21184
	/**
	 *
	 * A-1950
	 * 
	 * @param companyCode
	 * @param twoalphacode
	 * @param threealphacode
	 * @return
	 * @throws SystemException
	 */
//	public String findOwnerCode(String companyCode, String twoalphacode,
//			String threealphacode) throws SystemException {
//		 	Query query = getQueryManager().createNamedNativeQuery(
//				   ULDDefaultsPersistenceConstants.FIND_OWNERCODE);
//
//		query.setParameter(1, companyCode);
//		query.setParameter(2, twoalphacode);
//		query.setParameter(3, threealphacode);
//		   return query.getSingleResult(getStringMapper("ARLDTL"));
//	}

	/**
	 *
	 * A-1950
	 * 
	 * @param ULDListFilterVO
	 * @return Collection<String>
	 * @throws SystemException
	 */
	 public Collection<String> findULDs(ULDListFilterVO uldListFilterVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findULDs");
		 Query qry = getQueryManager().createNamedNativeQuery(
				 ULDDefaultsPersistenceConstants.FIND_ULDS);
		 int index = 0;
		qry.setParameter(++index, uldListFilterVO.getCompanyCode());
		qry.setParameter(++index, uldListFilterVO.getCurrentDepreciationDate());
		 return qry.getResultList(getStringMapper("ULDNUM"));
	 }

	/**
	 * @author A-1936 This method is used to list all the Flights for which the
	 *         UCM has to be sent manually..
	    * @param companyCode
	    * @param pol
	    * @return
	    * @throws SystemException
	    * @throws PersistenceException
	    */
	public Collection<UCMExceptionFlightVO> findExceptionFlights(
			String companyCode, String pol) throws SystemException,
			PersistenceException {
		log.entering("ULDSQLDAO", "findExceptionFlights");
	    Query query = getQueryManager().createNamedNativeQuery(
			   ULDDefaultsPersistenceConstants.FIND_EXCEPTION_FLIGHTS);
		query.setParameter(1, companyCode);
		query.setParameter(2, pol);
		Collection<UCMExceptionFlightVO> exceptionFlightVos = query
				.getResultList(new UCMExceptionFlightMapper());
		if (exceptionFlightVos != null && exceptionFlightVos.size() > 0) {
			for (UCMExceptionFlightVO exceptionFlightVo : exceptionFlightVos) {
	    	exceptionFlightVo.setCompanyCode(companyCode);
	    	exceptionFlightVo.setPol(pol);
	     }
	     }
	    return exceptionFlightVos;
	    }

	/**
	 *
	 */
	public Collection<String> findMissingULDs(String companyCode, int period)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findMissingULDs");
		Query query = getQueryManager().createNamedNativeQuery(
				   ULDDefaultsPersistenceConstants.FIND_MISSING_ULDS);
		query.setParameter(1, companyCode);
		query.setParameter(2, period);
		return query.getResultList(getStringMapper("ULDNUM"));
	}

	public Collection<ULDVO> findUldDatasForSCM(
			ULDSCMReconcileVO uLDSCMReconcileVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "getUldDetailsForSCM");
		List<ULDVO> uldVOs = null;

        String nativeQuery = getQueryManager()
                .getNamedNativeQueryString(ULDDefaultsPersistenceConstants.LIST_ULD_FORSCM);
        //dynamically modifing the query from xml based of the data source.
        if (isOracleDataSource()) {
            String dynamicQuery = " 1 ";
            nativeQuery = String.format(nativeQuery, dynamicQuery);
        } else {
            String dynamicQuery = " ((1)::NUMERIC || ' days')::INTERVAL ";
            nativeQuery = String.format(nativeQuery, dynamicQuery);
        }

        Query query = getQueryManager().createNativeQuery(nativeQuery);

		query.setParameter(1, uLDSCMReconcileVO.getCompanyCode());
		query.setParameter(2, uLDSCMReconcileVO.getAirportCode());
		query.setParameter(3, uLDSCMReconcileVO.getAirlineIdentifier());
		log.entering("ULDDefaultsSqlDAO", "Local DATE FROM CLIENT"
				+ uLDSCMReconcileVO.getStockCheckDate());
		query.setParameter(4, uLDSCMReconcileVO.getStockCheckDate());

		// Added by Preet for bug 18393 on 18Sep08 starts
		if(uLDSCMReconcileVO.getFacility()!=null && uLDSCMReconcileVO.getFacility().trim().length()>0){
			query.append(" AND ULD.FACTYP = ?  ");
			query.setParameter(5, uLDSCMReconcileVO.getFacility().trim());
		}
		if(uLDSCMReconcileVO.getLocation()!=null && uLDSCMReconcileVO.getLocation().trim().length()>0){
			query.append(" AND ULD.LOC = ?  ");
			query.setParameter(6, uLDSCMReconcileVO.getLocation().trim());
		}
		// Added by Preet for bug 18393 on 18Sep08 ends
		   return query.getResultList(new ULDDetailsMapper());
	}

	/**
	 * @author A-2619 This method is used to list the ULD History
	 * @return Page
	 * @param uldHistoryVO.
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<ULDHistoryVO> listULDHistory(ULDHistoryVO uldHistoryVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listULDHistory");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.LIST_ULD_HISTORY);
		if (uldHistoryVO.getCompanyCode() != null
				&& uldHistoryVO.getCompanyCode().trim().length() > 0) {
			query.append(" WHERE CMPCOD =  ?   ");
			query.setParameter(++index, uldHistoryVO.getCompanyCode());
		}
		if (uldHistoryVO.getUldNumber() != null
				&& uldHistoryVO.getUldNumber().trim().length() > 0) {
			query.append(" AND ULDNUM =  ?   ");
			query.setParameter(++index, uldHistoryVO.getUldNumber());
		}
		if (uldHistoryVO.getUldStatus() != null
				&& uldHistoryVO.getUldStatus().trim().length() > 0
				&& (!("Select").equals(uldHistoryVO.getUldStatus()))) {
			query.append("  AND STA =  ?   ");
			query.setParameter(++index, uldHistoryVO.getUldStatus());
		}
		if (uldHistoryVO.getCarrierCode() != null
				&& uldHistoryVO.getCarrierCode().trim().length() > 0) {
			query.append("  AND CARIDR =  ?   ");
			query.setParameter(++index, uldHistoryVO.getCarrierCode());
		}
		if (uldHistoryVO.getFlightNumber() != null
				&& uldHistoryVO.getFlightNumber().trim().length() > 0) {
			query.append("  AND FLTNUM =  ?   ");
			query.setParameter(++index, uldHistoryVO.getFlightNumber());
		}
		if (uldHistoryVO.getFlightDate() != null) {
			query.append("  AND DAT  =  ?   ");
			query.setParameter(++index, uldHistoryVO.getFlightDate());
			query.append("  AND CARIDR  IS NOT NULL ");
			query.append("  AND FLTNUM  IS NOT NULL ");
		}

		if (uldHistoryVO.getFromStation() != null
				&& uldHistoryVO.getFromStation().trim().length() > 0) {
			query.append("  AND FRMARP =  ?   ");
			query.setParameter(++index, uldHistoryVO.getFromStation());
		}
		if (uldHistoryVO.getFromDate() != null
				&& uldHistoryVO.getToDate() != null) {
			query.append("AND DAT BETWEEN  ? AND  ?");
			query.setParameter(++index, uldHistoryVO.getFromDate());
			query.setParameter(++index, uldHistoryVO.getToDate());
		}
		if (uldHistoryVO.getFromDate() != null
				&& uldHistoryVO.getToDate() == null) {
			query.append("AND DAT >= ? ");
			query.setParameter(++index, uldHistoryVO.getFromDate());
		}
		if (uldHistoryVO.getFromDate() == null
				&& uldHistoryVO.getToDate() != null) {
			query.append("AND DAT <= ? ");
			query.setParameter(++index, uldHistoryVO.getToDate());
		}

		PageableQuery<ULDHistoryVO> pgqry = new PageableQuery<ULDHistoryVO>(
				query, new ULDHistoryMapper());

		log.log(Log.FINE,
				"uldHistoryVO.getPageNumber()in ULDDefaultsSqlDAO---- ",
				uldHistoryVO.getPageNumber());
		Page<ULDHistoryVO> uldHistoryPage = pgqry.getPage(uldHistoryVO
				.getPageNumber());

		if (uldHistoryPage != null) {
			for (ULDHistoryVO uldHistoryvo : uldHistoryPage) {
				log.log(Log.FINE, "uldHistoryVO in ULDDefaultsSqlDAO---- ",
						uldHistoryvo);
			}

		} else {
			log.log(Log.FINE,
					"uldHistoryVO in ULDDefaultsSqlDAO is NULLLLLLLLLLL---- ");
		}

		return pgqry.getPage(uldHistoryVO.getPageNumber());

	}
	
	/**
	 * @author A-2412 This method is used to check for duplicate CRN number
	 * @return Collection<String>
	 * @param companyCode
	 * @param transactionVO	
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> checkForDuplicateCRN(String companyCode,TransactionVO transactionVO)
			throws SystemException,PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "checkForDuplicateCRN"+transactionVO);
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.CHECK_DUP_CRN);
		int iCount = 0;
		query.setParameter(1, companyCode);
		int index = 1;
		if (transactionVO != null
				&& transactionVO.getUldTransactionDetailsVOs().size() > 0) {
			log.log(Log.INFO, "inside checkForDuplicateCRN sqldao", transactionVO.getUldTransactionDetailsVOs());
			for (ULDTransactionDetailsVO vo : transactionVO
					.getUldTransactionDetailsVOs()) {
				if("T".equals(vo.getTransactionStatus())){
				if (iCount == 0) {
					query.append(" AND CRN IN (  ? ");
					query.setParameter(++index, vo.getControlReceiptNumber());
				} else {
					query.append(" , ? ");
					query.setParameter(++index, vo.getControlReceiptNumber());
				}
				iCount = 1;
			}else{
				if (iCount == 0) {
					query.append(" AND RTNCRN IN (  ? ");
					query.setParameter(++index, vo.getReturnCRN());
				} else {
					query.append(" , ? ");
					query.setParameter(++index, vo.getReturnCRN());
				}
				iCount = 1;
			}
			}
			if (iCount == 1) {
				query.append(" ) ");
			}
			log.log(Log.FINE, "checkForDuplicateCRN query-------", query);
		}
		Collection<String> ulds = query.getResultList(getStringMapper("CRN"));
		log.log(Log.FINE, "checkForDuplicateCRN ulds from query-------", ulds);
		return ulds;
	}
	
	/**
	 * @author A-3459 This method is used to check for Single duplicate CRN number
	 * @returnString
	 * @param companyCode
	 * @param crnNumber	
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findDuplicateCRN(String companyCode,String crnNumber)
			throws SystemException,PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "checkForDuplicateCRN");
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.CHECK_SINGLE_DUP_CRN);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, crnNumber.replaceFirst("-", new StringBuilder("-%").toString()));
		return query.getSingleResult(getStringMapper("CRN"));
	}
	/**
	 * @author A-2412 This method is used to list transaction details for UCR printing
	 * @return TransactionListVO
	 * @param uldTransactionFilterVO
	 * @throws SystemException	
	 */
	public TransactionListVO listUCRULDTransactionDetails(TransactionFilterVO uldTransactionFilterVO)
	throws SystemException{

		log.entering("ULDDefaultsSqlDAO123", "listULDTransactionDetails");
		int index = 1;
		int iCount = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.LIST_ULD_TRANSACTIONS);
		query.setParameter(1, uldTransactionFilterVO.getCompanyCode());
		Collection<String> uldNumbers = uldTransactionFilterVO.getUldNumbers();
		Collection<Integer> txnNumbers =uldTransactionFilterVO.getTransactionRefNumbers();
		
		ArrayList<Integer> txnNum = new ArrayList<Integer>();
		
		if(txnNumbers != null && txnNumbers.size()>0){
			for(int num:txnNumbers){
				txnNum.add(num);
			}
		}
		if(uldNumbers != null && uldNumbers.size()  > 0 ){
			int count=0;
	    		for(String uldNum : uldNumbers){
	    			if(iCount==0) {		    				
	    				if(uldTransactionFilterVO.getIsLoanUcrPrint() !=null && "Y".equals(uldTransactionFilterVO.getIsLoanUcrPrint())){
	    					query.append(" AND TXN.ULDNUM = ? AND TXN.TXNSTA = ? and TXN.TXNTYP=? ");
		    				query.setParameter(++index,uldNum);
		    				query.setParameter(++index,"T");
		    				query.setParameter(++index,"L");
	    				}else{
	    					query.append(" AND TXN.ULDNUM = ? AND TXN.TXNREFNUM = ? ");
		    				query.setParameter(++index,uldNum);
		    				query.setParameter(++index,txnNum.get(count));
	    				}
	    				
	    			}else {
	    				if(uldTransactionFilterVO.getIsLoanUcrPrint() !=null && "Y".equals(uldTransactionFilterVO.getIsLoanUcrPrint())){
	    					query.append(" OR TXN.ULDNUM = ? AND TXN.TXNSTA = ? and TXN.TXNTYP=?");
		    				query.setParameter(++index,uldNum);
		    				query.setParameter(++index,"T");
		    				query.setParameter(++index,"L");
	    				}else{
	    					query.append(" OR TXN.ULDNUM = ? AND TXN.TXNREFNUM = ?");
		    				query.setParameter(++index,uldNum);
		    				query.setParameter(++index,txnNum.get(count));
	    				}
	    				
	    			}
	    			iCount=1;
	    			count++;
	    		}
	    		if(iCount==1) {
	    			//query.append(" ) ");
	 		    }
		}		
		query.append(" ORDER BY CRN  ");
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs =query.getResultList( new ListULDTransactionMapper());
			
		TransactionListVO transactionListVO = new TransactionListVO();
		transactionListVO.setUldTransactionsDetails(uldTransactionDetailsVOs);

		return transactionListVO;
	}



 /* Added by A-2667 on 5th Nov for Duplicate Facility Code Check */
	public Collection<String> checkForDuplicateFacilityCode(String companyCode,String airportCode,String facilityCode,String facilityType,String content)
	throws SystemException{
		log.entering("ULDDefaultsSqlDAO", "listULDTransactionDetails");
		Query query = getQueryManager().createNamedNativeQuery(ULDDefaultsPersistenceConstants.CHECK_DUP_FACCOD);
		query.setParameter(1,companyCode);
		query.setParameter(2,airportCode);
		
		query.append(" AND FACCOD IN ( ? )");
		query.setParameter(3,facilityCode);
		//added by a-3278 for QF1006 on 08Aug08
		query.append(" AND FACTYP IN ( ? )");
		query.setParameter(4,facilityType);
		
		query.append(" AND CNT IN ( ? )");
		query.setParameter(5,content);
		//a-3278 ends
	/*	int count = 0;
		int index = 2;
		for(ULDAirportLocationVO uldAirportLocationVO : locationVOs){
			
			if(count == 0){
			query.append(" AND FACCOD IN ( ? ");
			query.setParameter(++index, uldAirportLocationVO.getFacilityCode());
			}
			else{
				query.append(" , ? ");
				query.setParameter(++index, uldAirportLocationVO.getFacilityCode());
			}
			count = 1;	
		}
		query.append(" )");
		log.log(Log.FINE,"QUERY------>>"+query);*/
		log.log(Log.INFO,"checkForDuplicateFacilityCode--%%%%%---&&&-----");
		query.setSensitivity(true);
		return query.getResultList(getStringMapper("FACCOD"));
	}

	/* Added by A-2667 on 5th Nov for Duplicate Facility Code Check Ends*/
	
	
	public HashMap<Long, Integer> findULDCountsForMovement(String companyCode,
			Collection<Long> seqNos) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findNoOfULDsForMovement");
		log.log(Log.INFO, "%%%%%%%%  CompanyCode ", companyCode);
		log.log(Log.INFO, "%%%%%%%%  seqNos ", seqNos);
		int index = 1;
		StringBuilder parameterBuilder = null;
		HashMap<Long, Integer> map = new HashMap<Long, Integer>();
		String parameter = null;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULDCOUNTS_FOR_MOVEMENT);
		query.setParameter(1, companyCode);
		if (seqNos != null && seqNos.size() > 0) {
			parameterBuilder = new StringBuilder("(");

			for (long seqNo : seqNos) {
				log.log(Log.INFO, "%%%%%%%%parameterBuilder", parameterBuilder);
				parameterBuilder.append("?,");
			}
			log.log(Log.INFO, "%%%%%%%%parameterBuilder  finalised ",
					parameterBuilder);
		}
		parameter = parameterBuilder.toString();
		if (parameter.endsWith(",")) {
			int len = parameter.length();
			parameter = parameter.substring(0, len - 1);
			parameter = new StringBuilder(parameter).append(")").toString();
		}
		log.log(Log.INFO, "%%%%%%%%parameter ", parameter);
		query.append(parameter);
		query.append(" GROUP BY MVTSEQNUM ");
		log.log(Log.INFO, "%%%%%%%%query ", query);
		for (long seqNo : seqNos) {
			log.log(Log.INFO, "%%%%%%%%seqNo", seqNo);
			query.setParameter(++index, seqNo);
		}
		Collection<String> values = query.getResultList(getStringMapper("VAL"));
		String arr[] = null;
		if (values != null) {
			for (String value : values) {
				arr = value.split("~");
				map.put(new Long(arr[0]), Integer.valueOf(arr[1]));
			}

		}
		log.log(Log.INFO, "%%%%%%%%%   map", map);
		return map;
	} 
	/**
	 * @author A-2667
	 * This method was added as a part of ANACR 1471  - to make default airline code in a few pages based on the login user
	 * Fetching Airline code from ULDUSRMST
	 */
	public String findDefaultAirlineCode(String companyCode,String userCode) throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.GET_AIRLINECODE);
		query.setParameter(1, companyCode);
		query.setParameter(2, userCode);
		return query.getSingleResult(getStringMapper("DEFARL"));
	}
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
	/*public String validateOwnerCode(String companyCode,String twoalpha,String threealpha)
	throws SystemException,PersistenceException{
		log.entering("ULDDefaultsSQL DAO","validateOwnerCode");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_ALPHACODEUSE);
		query.setParameter(1, companyCode);
		query.setParameter(2, twoalpha);
		query.setParameter(3, threealpha);
		return query.getSingleResult(getStringMapper("ARLDTL"));
	}*/

	public String findCurrentAirport(String companyCode, String uldNumber) 
	throws SystemException, PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.GET_AIRLINECODE);
		query.setParameter(1, companyCode);
		query.setParameter(2, uldNumber);
		return query.getSingleResult(getStringMapper("CURARP"));
		
	}
	
	/**
	 * This method retrieves the handled carrier for the specified filter condition
	 * @author A-2883
	 * @param ULDHandledCarrierVO
	 * @return Collection<ULDHandledCarrierVO>
	 * @throws SystemException
	 */
	public Collection<ULDHandledCarrierVO> findhandledcarriersetup(
			ULDHandledCarrierVO handledCarrierSetupVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findhandledcarriersetup");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_HANDLED_CARRIER);
			query.setParameter(++index, handledCarrierSetupVO.getCompanyCode());
		
		if (handledCarrierSetupVO.getAirlineCode() != null
				&& handledCarrierSetupVO.getAirlineCode().trim().length() > 0) {
			query.append(" AND ARLCOD =  ?   ");
			query.setParameter(++index, handledCarrierSetupVO.getAirlineCode());
		}
		if (handledCarrierSetupVO.getStationCode() != null
				&& handledCarrierSetupVO.getStationCode().trim().length() > 0) {
			query.append(" AND STNCOD =  ?   ");
			query.setParameter(++index, handledCarrierSetupVO.getStationCode());
		}
		
		
		Collection<ULDHandledCarrierVO> uldVos = query.getResultList(new ULDHandledCarrierMapper());
		
		log.log(Log.FINE, " \n ", uldVos);
		return uldVos;
	}
	
	/**
	 * This method is used for Finding the Stock Status of a ULD (used for HHT)
	 * @author A-2667
	 * @param uLDDiscrepancyFilterVO
	 * @return
	 * @throws SystemException
	 */
	public ULDDiscrepancyVO findULDStockStatusForHHT(ULDDiscrepancyFilterVO 
			uLDDiscrepancyFilterVO)throws SystemException{
		log.entering("ULDDefaultsSqlDAO", "findULDStockStatusForHHT");
		log.log(Log.FINE, "Filter VO", uLDDiscrepancyFilterVO);
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_STOCK_STATUS_FOR_HHT);
		int index = 0;
/*		if(uLDDiscrepancyFilterVO.getAgentCode() != null 
			&& uLDDiscrepancyFilterVO.getAgentCode().trim().length() > 0){
			query.append(" AND DIS.AGNCOD = ? ");
			query.setParameter(++index, uLDDiscrepancyFilterVO.getAgentCode());
		}*/
		query.append(" AND DIS.CMPCOD = ? ");
		query.setParameter(++index,uLDDiscrepancyFilterVO.getCompanyCode());
		if(uLDDiscrepancyFilterVO.getReportingStation() != null 
				&& uLDDiscrepancyFilterVO.getReportingStation().trim().length() > 0){
			query.append(" AND DIS.RPTARP = ? ");
			query.setParameter(++index, uLDDiscrepancyFilterVO.getReportingStation());
		}
		if(uLDDiscrepancyFilterVO.getUldNumber()!=null && uLDDiscrepancyFilterVO.getUldNumber().trim().length() > 0){
			query.append(" AND DIS.ULDNUM = ? ");
			query.setParameter(++index, uLDDiscrepancyFilterVO.getUldNumber());
		}
		if(uLDDiscrepancyFilterVO.getAgentLocation()!=null && uLDDiscrepancyFilterVO.getAgentLocation().trim().length() > 0){
			query.append(" AND DIS.FACTYP = ? ");
			query.setParameter(++index, uLDDiscrepancyFilterVO.getAgentLocation());
		}
		query.append(" UNION SELECT ULD.CURARP,ULD.ULDNUM , ULDTXN.TXNSTA ,ULD.FACTYP , ULD.LOC ,ULD.RELTOO ")
			.append(" FROM ULDMST ULD ,ULDTXNMST ULDTXN, ULDDIS DIS ")
				.append(" WHERE ULD.CMPCOD = ULDTXN.CMPCOD ")
					.append("  AND ULD.ULDNUM = ULDTXN.ULDNUM ");
		if(uLDDiscrepancyFilterVO.getReportingStation() != null 
				&& uLDDiscrepancyFilterVO.getReportingStation().trim().length() > 0){
			query.append(" AND ULD.CURARP = ? ");
			query.setParameter(++index, uLDDiscrepancyFilterVO.getReportingStation());
		}
		query.append(" AND ULDTXN.TXNSTA = 'T' ");
		query.append(" AND ULDTXN.TXNTYP = 'L' ");
		query.append(" AND ULDTXN.PTYTYP = 'G' ");
		query.append(" AND ULDTXN.ULDNUM = ? ");
		query.setParameter(++index, uLDDiscrepancyFilterVO.getUldNumber());
		query.append(" AND ULD.CMPCOD = ? ");
		query.setParameter(++index, uLDDiscrepancyFilterVO.getCompanyCode());
		log.log(Log.FINE, "QUERY ", query);
		ULDDiscrepancyVO uLDDiscrepancyVO = query.getSingleResult(new ULDDiscrepancyMapperForHHT());
		log.log(Log.FINE, "QUERY ", query);
		log.exiting("ULDDefaultsSqlDAO", "findULDStockStatusForHHT");
		return uLDDiscrepancyVO;
		
	}
	
	/**
	 * @param companyCode
	 * @param displayPage
	 * @param facilityType
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	
	public Page<String> populateLocationLov(String companyCode,
			int displayPage, String facilityType,String airportCode)throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.POPULATE_LOCATION_LOV);
		int index = 0;
		if(facilityType != null && facilityType.trim().length() > 0){
			query.append(" FACTYP = ?  ");
			query.setParameter(++index, facilityType);
		}
		if(airportCode != null && airportCode.trim().length() > 0){
			query.append(" AND ARPCOD = ?  ");
			query.setParameter(++index, airportCode);
		}
		log.log(Log.FINE, "query----------->>", query);
		PageableQuery<String> pgqry = new PageableQuery<String>(
				query, getStringMapper("FAC"));
		return pgqry.getPage(displayPage);
	}
	

	/**
	 * @param uldDamageFilterVO
	 * @return
	 * @throws SystemException
	 */
	public ULDDamageRepairDetailsVO findULDDamageRepairDetails(
			ULDDamageFilterVO uldDamageFilterVO) throws SystemException {
		ULDDamageRepairDetailsVO damageRepairVO = null;
		log.entering("ULDDefaultsSqlDAO", "findULDDamageRepairDetails");
		Query qry = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULDDAMAGEDREPAIRETAILS);
		qry.setParameter(1, uldDamageFilterVO.getCompanyCode());
		qry.setParameter(2, uldDamageFilterVO.getUldNumber());
		List<ULDDamageRepairDetailsVO> damageVOs = qry
		.getResultList(new ULDDamageDetailsMultiMapper());
		if (damageVOs != null && damageVOs.size() > 0) {
			damageRepairVO = damageVOs.get(0);
		}
		log.log(Log.INFO, "!!!!!ULDDamageRepairDetailsVO", damageRepairVO);
		return damageRepairVO;
	}
	
	/**
	 * @author A-2412
	 * @param uldIntMvtFilterVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 */
	public Page<ULDIntMvtDetailVO> findIntULDMovementHistory(
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO, int displayPage)
			throws SystemException{		
		log.entering("ULDDefaultsSqlDAO", "findIntULDMovementHistory");
		String baseQry = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.ULD_FIND_ULDINTMVTDTL);
		Query query = new ULDIntMvtFilterQuery(uldIntMvtFilterVO, baseQry);
		PageableQuery<ULDIntMvtDetailVO> pgqry = new PageableQuery<ULDIntMvtDetailVO>(
					query, new ULDIntMvtHistoryMapper());
		log.exiting("ULDDefaultsSqlDAO", "findIntULDMovementHistory");
		return pgqry.getPage(displayPage);
		
	}
	
	
	
	/**
	 *@author A-3045
	 * @param discrepancyFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Collection<ULDDiscrepancyVO>listULDDiscrepancy(
			ULDDiscrepancyFilterVO discrepancyFilterVO) throws SystemException,
			PersistenceException {
   	log.entering("ULDDefaultsSqlDAO", "listULDDiscrepancy%%%%%%%%%%%@@@@@@@@@@@");
   	int index = 0;
   	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.LIST_ULD_DISCREPANCY);
		query.setParameter(++index, discrepancyFilterVO.getCompanyCode());
		if (discrepancyFilterVO.getUldNumber() != null
				&& discrepancyFilterVO.getUldNumber().trim().length() > 0) {
       	query.append(" AND DIS.ULDNUM = ? ");
			query.setParameter(++index, discrepancyFilterVO.getUldNumber());
       }
		if (discrepancyFilterVO.getReportingStation() != null
				&& discrepancyFilterVO.getReportingStation().trim().length() > 0) {
       	query.append(" AND DIS.RPTARP = ? ");
			query.setParameter(++index, discrepancyFilterVO.getReportingStation());
		}		
		if (discrepancyFilterVO.getFromDate() != null) {
			query.append(" AND DIS.DISDAT >= ? ");
			query.setParameter(++index , discrepancyFilterVO.getFromDate());
		}
		if (discrepancyFilterVO.getTodate() != null) {
			query.append(" AND DIS.DISDAT <= ? ");
			query.setParameter(++index , discrepancyFilterVO.getTodate());
		}		
		if (discrepancyFilterVO.getUldOwnerIdentifier() > 0) {
       	query.append(" AND MST.OPRARLIDR = ? ");
			query.setParameter(++index, discrepancyFilterVO.getUldOwnerIdentifier());
		}
		log.log(Log.INFO, "%%%%%%%%%%%%  Query ", query);
		   	  return query.getResultList(new ULDDiscrepancyMapper());
   }

	public String findpolLocationForULD(String companyCode, String uldNumber) throws SystemException {
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_POL_LOCATION);
		int index =0;
		query.setParameter(++index, companyCode);
		if(uldNumber != null && uldNumber.trim().length() > 0){
			query.append(" AND ULDNUM = ?");
			query.setParameter(++index, uldNumber);
		}
		return query.getSingleResult(getStringMapper("LOCATION"));
		
	}
	
	/**
	 *@author A-3045
	 * @param uldIntMvtFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ULDIntMvtDetailVO> findULDIntMvtHistory(
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO) throws SystemException{
		log.entering("ULDDefaultsSqlDAO", "findULDIntMvtHistory%%%%%%%%%%%@@@@@@@@@@@");  	 	
		Query query = getQueryManager().createNamedNativeQuery(
					ULDDefaultsPersistenceConstants.ULD_FIND_ULDINTMVTDTL);
		int index = 0;
		query.setParameter(++index, uldIntMvtFilterVO.getCompanyCode());
		if (uldIntMvtFilterVO.getUldNumber() != null
				&& uldIntMvtFilterVO.getUldNumber().trim().length() > 0) {
       	query.append(" AND DTL.ULDNUM = ? ");
			query.setParameter(++index, uldIntMvtFilterVO.getUldNumber());
		}
		if (uldIntMvtFilterVO.getFromDate() != null) {
			query.append(" AND DTL.MVTDAT >= ? ");
			query.setParameter(++index , uldIntMvtFilterVO.getFromDate());
		}
		if (uldIntMvtFilterVO.getToDate() != null) {
			query.append(" AND DTL.MVTDAT <= ? ");
			query.setParameter(++index , uldIntMvtFilterVO.getToDate());
		}		
		log.log(Log.INFO, "%%%%%%%%%%%%  Query ", query);
		   	  return query.getResultList(new ULDIntMvtHistoryMapper());
   }
	
	
	/**
	 *@author A-2667
	 * @param uldIntMvtFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<AuditDetailsVO> findULDAuditEnquiryDetails(RelocateULDVO relocateULDVO)
	throws SystemException{
		//Changed the method by a-3045 for 28905 on 17Jan09
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULD_AUDIT_ENQUIRY_DETAILS);
		int index= 0;
		log.log(Log.FINE, "findULDAuditEnquiryDetails Relocate VO",
				relocateULDVO);
		query.setParameter(++index, relocateULDVO.getCompanyCode());
		if(relocateULDVO.getUldNumber() != null && 
				relocateULDVO.getUldNumber().trim().length() > 0){
			query.append(" AND ULDNUM = ? ");
			query.setParameter(++index, relocateULDVO.getUldNumber());
		}
		if(relocateULDVO.getUldSuffix() != null && 
				relocateULDVO.getUldSuffix().trim().length() > 0){
			query.append(" AND ULDNUM LIKE ? ");
			query.setParameter(++index,new StringBuilder("%").append(relocateULDVO.getUldSuffix()).toString());
		}
		if(relocateULDVO.getLocation() != null && 
				relocateULDVO.getLocation().trim().length() > 0){
			query.append(" AND (( ADLINF LIKE ? AND ACTCOD IN( 'ULDMSTCPT', 'ULDMSTUPD', 'ULDMSTDEL')) OR ( ADLINF LIKE ? AND ACTCOD = 'ULDINTMVTCPT' ))");
			query.setParameter(++index,new StringBuilder("%").append(relocateULDVO.getLocation()).append("%").toString());
			query.setParameter(++index,new StringBuilder("%").append("ToLocation - ").
				append(relocateULDVO.getLocation()).append("%").toString());
		}
		if(relocateULDVO.getCurrentStation() != null && 
				relocateULDVO.getCurrentStation().trim().length() > 0){
			query.append(" AND ((ADLINF LIKE ? )  OR (ADLINF LIKE ? ))");
			query.setParameter(++index,new StringBuilder("%").append("CurrentAirport - ").
				append(relocateULDVO.getCurrentStation()).append("%").toString());
			
			query.setParameter(++index,new StringBuilder("%").append("ToAirport - ").
					append(relocateULDVO.getCurrentStation()).append("%").toString());
		}
		if(relocateULDVO.getFacilityType() != null && 
				relocateULDVO.getFacilityType().trim().length() > 0){
			query.append(" AND ADLINF LIKE ? ");
			query.setParameter(++index,new StringBuilder("%").append("FacilityType - ").
				append(relocateULDVO.getFacilityType()).append("%").toString());
		}
		if(relocateULDVO.getTxnFromDate()!=null){
			log.log(Log.INFO, "FromDate", relocateULDVO.getTxnFromDate());
			query.append(" AND TRUNC(UPDTXNTIMUTC) >= ?");
			query.setParameter(++index,relocateULDVO.getTxnFromDate().toCalendar());
		}
		if(relocateULDVO.getTxnToDate()!=null){
			log.log(Log.INFO, "Tooo", relocateULDVO.getTxnToDate());
			query.append(" AND TRUNC(UPDTXNTIMUTC) <= ?");
			query.setParameter(++index,relocateULDVO.getTxnToDate().toCalendar());
		}		
		query.append(" UNION ALL SELECT CMPCOD,ACTCOD, AUDRMK, SUBSTR(ADLINF,1,INSTR(ADLINF,'/',1,2)-1), STNCOD, UPDUSR,")
			   .append("UPDTXNTIM, UPDTXNTIMUTC FROM ULDCFGAUD WHERE CMPCOD = ? AND ACTCOD IN ('ULDMUCGEN')");
		query.setParameter(++index,relocateULDVO.getCompanyCode());
		if(relocateULDVO.getUldNumber() != null && 
				relocateULDVO.getUldNumber().trim().length() > 0){
			query.append(" AND ADLINF LIKE ? ");
			query.setParameter(++index, new StringBuilder("%/ULDNUM:-%").append(relocateULDVO.getUldNumber()).append("%").toString());
			
			//query.append(" AND ADLINF LIKE '%/ULDNUM:-%'?'%' ");
			//query.setParameter(++index, relocateULDVO.getUldNumber());
		}
		if(relocateULDVO.getUldSuffix() != null && 
				relocateULDVO.getUldSuffix().trim().length() > 0){
			query.append(" AND ADLINF LIKE ? ");
			query.setParameter(++index, new StringBuilder("%/ULDNUM:-%").append(relocateULDVO.getUldSuffix()).append("%").toString());
			
			//query.append(" AND ADLINF LIKE '%/ULDNUM:-%'?'%' ");
			//query.setParameter(++index,relocateULDVO.getUldSuffix());
		}
		if(relocateULDVO.getLocation() != null && 
				relocateULDVO.getLocation().trim().length() > 0){
			query.append(" AND ADLINF = ? ");
			query.setParameter(++index,relocateULDVO.getLocation());
		}
		if(relocateULDVO.getCurrentStation() != null && 
				relocateULDVO.getCurrentStation().trim().length() > 0){
			query.append(" AND ADLINF = ? ");
			query.setParameter(++index,relocateULDVO.getCurrentStation());		
			
		}
		if(relocateULDVO.getFacilityType() != null && 
				relocateULDVO.getFacilityType().trim().length() > 0){
			query.append(" AND ADLINF = ? ");
			query.setParameter(++index,relocateULDVO.getFacilityType());
		}
		if(relocateULDVO.getTxnFromDate()!=null){
			log.log(Log.INFO, "FromDate", relocateULDVO.getTxnFromDate());
			query.append(" AND TRUNC(UPDTXNTIMUTC) >= ? ");
			query.setParameter(++index,relocateULDVO.getTxnFromDate().toCalendar());
		}
		if(relocateULDVO.getTxnToDate()!=null){
			log.log(Log.INFO, "Tooo", relocateULDVO.getTxnToDate());
			query.append(" AND TRUNC(UPDTXNTIMUTC) <= ? ");
			query.setParameter(++index,relocateULDVO.getTxnToDate().toCalendar());
		}
		query.append("  UNION ALL SELECT CMPCOD,ACTCOD, AUDRMK, SUBSTR(ADLINF,1,INSTR(ADLINF,'/',1,2)-1), STNCOD, UPDUSR,")
		   .append("UPDTXNTIM, UPDTXNTIMUTC FROM ULDCFGAUD WHERE CMPCOD = ? AND ACTCOD IN ('ULDSCMSNT','ULDSCMPRC')");
		
		query.setParameter(++index,relocateULDVO.getCompanyCode());
		
		if(relocateULDVO.getUldNumber() != null && 
				relocateULDVO.getUldNumber().trim().length() > 0){
			query.append(" AND ADLINF LIKE ? ");
			query.setParameter(++index,new StringBuilder("%/ULDNos. -%").append(relocateULDVO.getUldNumber()).append("%").toString());
			
			//query.append(" AND ADLINF LIKE '%/ULDNos. -%'?'%' ");
			//query.setParameter(++index, relocateULDVO.getUldNumber());
		}
		if(relocateULDVO.getUldSuffix() != null && 
				relocateULDVO.getUldSuffix().trim().length() > 0){
			query.append(" AND ADLINF LIKE ? ");
			query.setParameter(++index,new StringBuilder("%/ULDNos. -%").append(relocateULDVO.getUldSuffix()).append("%").toString());
			
			//query.append(" AND ADLINF LIKE '%/ULDNos. -%'?'%' ");
			//query.setParameter(++index,relocateULDVO.getUldSuffix());
		}
		if(relocateULDVO.getLocation() != null && 
				relocateULDVO.getLocation().trim().length() > 0){
			query.append(" AND ADLINF = ? ");
			query.setParameter(++index,relocateULDVO.getLocation());
		}
		if(relocateULDVO.getCurrentStation() != null && 
				relocateULDVO.getCurrentStation().trim().length() > 0){
			//query.append(" AND ADLINF LIKE 'Aiport - '?'%' ");
			query.append(" AND ADLINF LIKE ? ");		
			query.setParameter(++index,new StringBuilder("%Airport - ").append(relocateULDVO.getCurrentStation()).append("%").toString());		
			//query.setParameter(++index,relocateULDVO.getCurrentStation());		
		}
		if(relocateULDVO.getFacilityType() != null && 
				relocateULDVO.getFacilityType().trim().length() > 0){
			query.append(" AND ADLINF = ? ");
			query.setParameter(++index,relocateULDVO.getFacilityType());
		}
		if(relocateULDVO.getTxnFromDate()!=null){
			log.log(Log.INFO, "FromDate", relocateULDVO.getTxnFromDate());
			query.append(" AND TRUNC(UPDTXNTIMUTC) >= ?");
			query.setParameter(++index,relocateULDVO.getTxnFromDate().toCalendar());
		}
		if(relocateULDVO.getTxnToDate()!=null){
			log.log(Log.INFO, "Tooo", relocateULDVO.getTxnToDate());
			query.append(" AND TRUNC(UPDTXNTIMUTC) <= ?");
			query.setParameter(++index,relocateULDVO.getTxnToDate().toCalendar());
		}
		
		query.append("  UNION ALL SELECT CMPCOD, ACTCOD, AUDRMK, ADLINF, STNCOD, UPDUSR, UPDTXNTIM, UPDTXNTIMUTC FROM ULDCFGAUD");
		query.append("  WHERE CMPCOD = ?");
		query.setParameter(++index,relocateULDVO.getCompanyCode());
		query.append("  AND ACTCOD NOT IN('ULDSCMSNT','ULDMUCGEN','ULDSCMPRC')");
		
		if(relocateULDVO.getUldNumber() != null && 
				relocateULDVO.getUldNumber().trim().length() > 0){
			query.append(" AND ADLINF LIKE ? ");
			query.setParameter(++index,new StringBuilder("%/ULDNos. -%").append(relocateULDVO.getUldNumber()).append("%").toString());
			
			//query.append(" AND ADLINF LIKE '%/ULDNos. -%'?'%' ");
			//query.setParameter(++index, relocateULDVO.getUldNumber());
		}
		if(relocateULDVO.getUldSuffix() != null && 
				relocateULDVO.getUldSuffix().trim().length() > 0){
			query.append(" AND ADLINF LIKE ? ");
			query.setParameter(++index,new StringBuilder("%/ULDNos. -%").append(relocateULDVO.getUldSuffix()).append("%").toString());
			
			//query.append(" AND ADLINF LIKE '%/ULDNos. -%'?'%' ");
			//query.setParameter(++index,relocateULDVO.getUldSuffix());
		}
		if(relocateULDVO.getLocation() != null && 
				relocateULDVO.getLocation().trim().length() > 0){
			query.append(" AND ADLINF LIKE ? ");
			query.setParameter(++index,new StringBuilder("%").append(relocateULDVO.getLocation()).append("%").toString());
			//query.setParameter(++index,relocateULDVO.getLocation());
		}
		if(relocateULDVO.getCurrentStation() != null && 
				relocateULDVO.getCurrentStation().trim().length() > 0){
			//query.append(" AND ADLINF LIKE 'Aiport - '?'%' ");
			query.append(" AND ADLINF LIKE ? ");		
			query.setParameter(++index,new StringBuilder("%AirportCode - ").append(relocateULDVO.getCurrentStation()).append("%").toString());		
			//query.setParameter(++index,relocateULDVO.getCurrentStation());		
		}
		if(relocateULDVO.getFacilityType() != null && 
				relocateULDVO.getFacilityType().trim().length() > 0){
			query.append(" AND ADLINF LIKE ? ");
			query.setParameter(++index,new StringBuilder("%").append(relocateULDVO.getFacilityType()).append("%").toString());
			//query.setParameter(++index,relocateULDVO.getFacilityType());
		}
		
		if(relocateULDVO.getTxnFromDate()!=null){
			log.log(Log.INFO, "FromDate", relocateULDVO.getTxnFromDate());
			query.append(" AND TRUNC(UPDTXNTIMUTC) >= ?");
			query.setParameter(++index,relocateULDVO.getTxnFromDate().toCalendar());
		}
		if(relocateULDVO.getTxnToDate()!=null){
			log.log(Log.INFO, "Tooo", relocateULDVO.getTxnToDate());
			query.append(" AND TRUNC(UPDTXNTIMUTC) <= ?");
			query.setParameter(++index,relocateULDVO.getTxnToDate().toCalendar());
			
		}
		query.append(" ORDER BY UPDTXNTIMUTC");
		return query.getResultList(new ULDAuditEnquiryMapper());
	}

	/**
	 * @author A-1950
	 */
	public Collection<ULDStockListVO> findStockDeviation(String companyCode)
	throws SystemException{
		log.entering("ULDDefaultsSQLDAO","findStockDeviation");
		log.log(Log.INFO, "%%%%%%%%%%%%%CompanyCode ", companyCode);
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULD_STOCK_DEVIATION);
		query.setParameter(1,companyCode);
		return query.getResultList(new FindStockDeviationMapper());
	}
	
	/**
	 * @author A-1950
	 */
	public Collection<AccessoriesStockConfigVO> sendAlertForULDAccStockDepletion(String companyCode)
	throws SystemException{
		log.entering("ULDDefaultsSQLDAO","sendAlertForULDAccStockDepletion");
		log.log(Log.INFO, "%%%%%%%%%%%%%CompanyCode ", companyCode);
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULD_ACC_STOCK_DEPLETION);
		query.setParameter(1,companyCode);
		return query.getResultList(new AccessoriesStockMapper());
	}
	
	/**
	 * 
	 */
	public Collection<String> sendAlertForDiscrepancy(String companyCode, int period)
	throws SystemException{
		log.entering("ULDDefaultsSQLDAO","sendAlertForDiscrepancy");
		log.log(Log.INFO, "%%%%%%%%%%%%%CompanyCode ", companyCode);
		
		String queryString = getQueryManager().getNamedNativeQueryString(ULDDefaultsPersistenceConstants.FIND_ULD_DIS_FOR_ALT);	
		if(isOracleDataSource()){
		   String dynamicQuery = " (SYSDATE - NUMTOYMINTERVAL('?','MONTH')) ";
		   queryString = String.format(queryString, dynamicQuery);
		}else{
		   String dynamicQuery = " (SYSDATE() - ( ? :: NUMERIC || ' MONTH')::INTERVAL) ";
		   queryString = String.format(queryString, dynamicQuery);
		}
		
		Query query = getQueryManager().createNativeQuery(queryString);
		query.setParameter(1,companyCode);
		query.setParameter(2,period);
		return query.getResultList(getStringMapper("ULDNUMDISCOD"));
	}
	
	/**
	 * 
	 * @author a-2883
	 * @param ULDNumber
	 * @param companyCode
	 * @return Collection<ULDDamagePictureVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ULDDamagePictureVO> findULDDamagePictures(ULDDamageFilterVO uldDamageFilterVO) 
	throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSQLDAO", "findULDDamagePicture");
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULD_DMG_PIC_DETAILS);
		query.setParameter(1,uldDamageFilterVO.getCompanyCode());
		query.setParameter(2,uldDamageFilterVO.getUldNumber());
		if(uldDamageFilterVO.getDamageSequenceNumber()>0){
			query.append("  and SEQNUM=?  ");
			query.setParameter(3,uldDamageFilterVO.getDamageSequenceNumber());
		}
		log.exiting("ULDDefaultsSQLDAO", "findULDDamagePicture");
		return query.getResultList(new ListULDDamagePictureMapper(isOracleDataSource()));
	
	}
	/**
	 * @author A-2667
	 * @param ULDNumber
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<FlightDetailsVO> findCPMULDDetails(FlightMessageFilterVO flightMessageFilterVO) 
	throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSQLDAO", "findCPMULDDetails");
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_CPM_ULD_DETAILS);
		int index=0;
		query.setParameter(++index,flightMessageFilterVO.getCompanyCode());
		/*if(flightMessageFilterVO.getFlightSequenceNumber() > 0){
			query.append(" AND MST.FLTSEQNUM =  ? ");
			query.setParameter(++index, flightMessageFilterVO.getFlightSequenceNumber());
		}*/
		if(flightMessageFilterVO.getFlightNumber() != null && flightMessageFilterVO.getFlightNumber().trim().length() > 0){
			query.append("  AND MST.FLTNUM = ?  ");
			query.setParameter(++index, flightMessageFilterVO.getFlightNumber());
		}
		if(flightMessageFilterVO.getFlightCarrierIdentifier() != 0){
			query.append("  AND MST.FLTCARIDR = ? ");
			query.setParameter(++index, flightMessageFilterVO.getFlightCarrierIdentifier());
		}
		if(flightMessageFilterVO.getAirportCode() != null && flightMessageFilterVO.getAirportCode().trim().length() > 0){
			query.append("    AND DTL.POU = ? ");
			query.setParameter(++index, flightMessageFilterVO.getAirportCode());
		}
		log.exiting("ULDDefaultsSQLDAO", "findCPMULDDetails");
		return query.getResultList(new UCMFlightDetailsMultiMapper());
	}

	/**
	 *@author A-3459
	 * @param ULDDamageChecklistVO
	 * @return
	 * @throws SystemException
	 */
   
	public Collection<ULDDamageChecklistVO> listULDDamageChecklistMaster(String companyCode,String section)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listULDDamageChecklistMaster");
		Query query = getQueryManager().createNamedNativeQuery(
				   ULDDefaultsPersistenceConstants.LIST_ULD_DAMAGECHECKLISTMASTER);
		int index= 1;
			query.setParameter(1, companyCode);
			
			if (section != null && section.trim().length() > 0) {
				
				query.append(" AND ULDDMGCHKLST.SEC IN (");
				String sectionCodes[] = section.split(",");
	   			  for(String code : sectionCodes){
	   					query.append("?");
	   					if(index < sectionCodes.length){
	   				  query.append(",");
	   					}
	   				query.setParameter(++index, code);
	   			  }
	   			query.append(" ) ");
			}
			query.append(" AND SHR.FLDTYP = ? ");
			query.setParameter(++index, SECTION_PARAM);
			query.append(" ORDER BY SHR.FLDDES ");
			
			log.log(Log.INFO, "%%%%%%%%%%%%  Query ", query);

	   return query.getResultList(new ListULDDamagechecklistMasterMapper());
   }

	/**
	 * @author a-2883
	 * @param inventoryULDVO
	 * @return Page<InventoryULDVO>
	 */
	public Collection<InventoryULDVO> findULDInventoryDetails(InventoryULDVO inventoryULDVO) 
	throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findULDInventoryDetails");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULD_INVENTORY_DETAILS);
		query.setParameter(++index,inventoryULDVO.getCompanyCode());
		
		if(inventoryULDVO.getFromDate() != null){
			query.append(" AND DTL.DAT >= ? ");
			query.setParameter(++index, inventoryULDVO.getFromDate());
		}
		if(inventoryULDVO.getToDate() != null){
			query.append(" AND DTL.DAT <= ? ");
			query.setParameter(++index, inventoryULDVO.getToDate());
		}
		if(inventoryULDVO.getAirportCode() != null && inventoryULDVO.getAirportCode().trim().length() >0){
			query.append(" AND DTL.ARPCOD = ?");
			query.setParameter(++index, inventoryULDVO.getAirportCode());
		}
		if(inventoryULDVO.getUldType() != null && inventoryULDVO.getUldType().trim().length() >0){
			query.append(" AND DTL.ULDTYP = ?");
			query.setParameter(++index, inventoryULDVO.getUldType());
		}
		query.append(" ORDER BY CMPCOD,ARPCOD,ULDTYP,SEQNUM,DAT,SERNUM");
		/*PageableQuery<InventoryULDVO> pageQuery = new PageableQuery<InventoryULDVO>(
					query, new ULDInventoryMultiMapper());*/
		
		log.exiting("ULDDefaultsSqlDAO", "findULDInventoryDetails");
		//return pageQuery.getPageAbsolute(inventoryULDVO.getDisplayPage(),inventoryULDVO.getAbsoluteIndex());
		return query.getResultList(new ULDInventoryMultiMapper());
	}
	
	/**
	 * @author a-3429
	 * @param uLDMovementFilterVO
	 * @return ULDNumberVO>
	 */
	public ULDNumberVO findULDHistoryCounts(
			ULDMovementFilterVO uLDMovementFilterVO) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findULDHistoryCounts");
		int index = 0;

		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_ULD_HISTORY_COUNTS);
		query.setParameter(++index, uLDMovementFilterVO.getCompanyCode());
		query.setParameter(++index, uLDMovementFilterVO.getUldNumber());
		if (uLDMovementFilterVO.getFromDate() != null) {
			query.append(" AND TRUNC(TXN.TXNDAT) >= ? ");
			query.setParameter(++index, uLDMovementFilterVO.getFromDate());
		}
		if (uLDMovementFilterVO.getToDate() != null) {
			query.append(" AND TRUNC(TXN.RTNDAT) <= ? ");
			query.setParameter(++index, uLDMovementFilterVO.getToDate());
		}
		query.append(" ) ");
		query
				.append("  || '~'|| (SELECT COUNT ( *) FROM ULDDMG DMG WHERE DMG.CMPCOD = ? AND DMG.ULDNUM = ? ");
		query.setParameter(++index, uLDMovementFilterVO.getCompanyCode());
		query.setParameter(++index, uLDMovementFilterVO.getUldNumber());
		if (uLDMovementFilterVO.getFromDate() != null) {
			query.append(" AND TRUNC(DMG.DMGRPTDAT) >= ? ");
			query.setParameter(++index, uLDMovementFilterVO.getFromDate());
		}
		if (uLDMovementFilterVO.getToDate() != null) {
			query.append(" AND TRUNC(DMG.DMGRPTDAT) <= ? ");
			query.setParameter(++index, uLDMovementFilterVO.getToDate());
		}
		query.append(" ) ");
		query
				.append("  || '~'|| (SELECT COUNT ( *) FROM ULDRPR RPR WHERE RPR.CMPCOD = ? AND RPR.ULDNUM = ? ");
		query.setParameter(++index, uLDMovementFilterVO.getCompanyCode());
		query.setParameter(++index, uLDMovementFilterVO.getUldNumber());
		if (uLDMovementFilterVO.getFromDate() != null) {
			query.append(" AND TRUNC(RPR.RPRDAT) >= ? ");
			query.setParameter(++index, uLDMovementFilterVO.getFromDate());
		}
		if (uLDMovementFilterVO.getToDate() != null) {
			query.append(" AND TRUNC(RPR.RPRDAT)<= ? ");
			query.setParameter(++index, uLDMovementFilterVO.getToDate());
		}
		query.append(" ) ");
		query
				.append(" || '~' || (SELECT COUNT ( *) FROM ULDMVTDTL MVT WHERE MVT.CMPCOD = ? AND MVT.ULDNUM = ? ");
		query.setParameter(++index, uLDMovementFilterVO.getCompanyCode());
		query.setParameter(++index, uLDMovementFilterVO.getUldNumber());
		if (uLDMovementFilterVO.getFromDate() != null) {
			query.append(" AND TRUNC(MVT.LSTMVTDATUTC) >= ? ");
			query.setParameter(++index, uLDMovementFilterVO.getFromDate());
		}
		if (uLDMovementFilterVO.getToDate() != null) {
			query.append(" AND TRUNC(MVT.LSTMVTDATUTC)<= ? ");
			query.setParameter(++index, uLDMovementFilterVO.getToDate());
		}
		query.append(" ) ");
		query.append(" COUNT FROM DUAL ");
		
		String counts = query.getSingleResult(getStringMapper("COUNT"));
		String[] countArray = counts.split("~");
		ULDNumberVO uldNumberVO = new ULDNumberVO();
		uldNumberVO.setNoOfLoanTxns(Integer.parseInt(countArray[0]));
		uldNumberVO.setNoOfTimesDmged(Integer.parseInt(countArray[1]));
		uldNumberVO.setNoOfTimesRepaired(Integer.parseInt(countArray[2]));
		uldNumberVO.setNoOfMovements(Integer.parseInt(countArray[3]));
		return uldNumberVO;
	}
	//added by jisha for qf1022 starts
	
	/**
	 * @author a-3093
	 * for QF1022
	 * @param uldRepairFilterVO,displayPage
	 * @return <ULDRepairDetailsListVO> 
	 * @throws SystemException
	 */
	
	public	Page<ULDRepairDetailsListVO> listDamageRepairDetails(
			UldDmgRprFilterVO uldRepairFilterVO,int displayPage ) throws SystemException
			{
			log.entering("ULDDefaultsSqlDAO", "listDamageRepairDetails");
			String baseQuery = getQueryManager().getNamedNativeQueryString(
					ULDDefaultsPersistenceConstants.LIST_ULDDAMAGEREPAIRDETAILS);
			Query query = new ULDListDamageRepairFilterQuery(uldRepairFilterVO, baseQuery);
			PageableQuery<ULDRepairDetailsListVO> pgqry = new PageableQuery<ULDRepairDetailsListVO>(
						query, new ULDDamageRepairMapper ());
			return pgqry.getPage(displayPage);
		}
	/**
	 * @author a-3093
	 */
	public Page<AuditDetailsVO> findULDActionHistory(
			ULDMovementFilterVO uldmovementFilterVO) throws SystemException
			{
		log.entering("ULDDefaultsSqlDAO", "findULDActionHistory");
		int index = 0;
		
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FINDULDACTIONHISTORY);
		query.setParameter(++index,uldmovementFilterVO.getCompanyCode());
		query.setParameter(++index,uldmovementFilterVO.getUldNumber());		
		query.setParameter(++index,uldmovementFilterVO.getUldNumber());
		query.setParameter(++index,uldmovementFilterVO.getCompanyCode());
		query.setParameter(++index,uldmovementFilterVO.getUldNumber());
		/*query.setParameter(++index,uldmovementFilterVO.getCompanyCode());
		query.setParameter(++index,uldmovementFilterVO.getUldNumber());*/
						
		PageableQuery<AuditDetailsVO> pgqry = new PageableQuery<AuditDetailsVO>(
				query, new ULDConfigMapper());
		
		log.log(Log.INFO, "pgqry", pgqry);
		log.exiting("ULDDefaultsSqlDAO", "findULDActionHistory");
		return pgqry.getPage(uldmovementFilterVO.getPageNumber());
			
				
		
			}
	//added by jisha for qf1022 ends
	
	
	
	/**
	 * @author A-1950
	 * @param uldStockConfigFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ULDStockConfigVO> findDwellTimes(ULDStockConfigFilterVO uldStockConfigFilterVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO","findDwellTimes");
		log
				.log(Log.INFO, "%%%%%%%%%%findDwellTimes%%%",
						uldStockConfigFilterVO);
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_DWELL_TIMES);
		query.setParameter(++index,uldStockConfigFilterVO.getCompanyCode());
		StringBuilder strBuilder = new StringBuilder();
		String str = null;
		String queryString = null;
		if(uldStockConfigFilterVO.getAirports() != null && uldStockConfigFilterVO.getAirports().size() > 0){
			strBuilder.append(" AND STK.ARPCOD IN(");
			for(String arpcod : uldStockConfigFilterVO.getAirports()){
				strBuilder.append("?,");
				query.setParameter(++index,arpcod);
			}
			str = strBuilder.toString();
			queryString = str.substring(0,str.length()-1);
			queryString = new StringBuilder(queryString).append(")").toString();
		}
		query.append(queryString);
		
		strBuilder = new StringBuilder();
		if(uldStockConfigFilterVO.getUldTypes() != null && uldStockConfigFilterVO.getUldTypes().size() > 0){
			strBuilder.append(" AND STK.ULDTYPCOD IN(");
			for(String uldType : uldStockConfigFilterVO.getUldTypes()){
				strBuilder.append("?,");
				query.setParameter(++index,uldType);
			}
			str = strBuilder.toString();
			queryString = str.substring(0,str.length()-1);
			queryString = new StringBuilder(queryString).append(")").toString();
		}
		query.append(queryString);
		
		Collection<ULDStockConfigVO> coll = query.getResultList(new ULDStockConfigMapper());
		log.log(Log.INFO, "!!!!Collection<ULDStockConfigVO>", coll);
		return coll;
	}
	
	/**
	 * @author A-2408
	 * @param uldPoolOwnerFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkforPoolOwner(ULDPoolOwnerFilterVO uldPoolOwnerFilterVO) throws SystemException, PersistenceException{
		boolean isPoolOwner = false;
		log.entering("ULDDefaultsSqlDAO", "checkForPoolOwners");
		   Query qry = getQueryManager().createNamedNativeQuery(
				   ULDDefaultsPersistenceConstants.CHECK_POOL_OWNERS);

		    int index = 0;  
			qry.setParameter(++index, uldPoolOwnerFilterVO.getCompanyCode());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirlineIdentifierOne());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirlineIdentifierTwo());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirport());
			
			//Added by Manaf for Bug 17293 starts
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirlineIdentifierTwo());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirlineIdentifierOne());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirport());
			//Added by Manaf for Bug 17293 ends
			qry.setParameter(++index, uldPoolOwnerFilterVO.getCompanyCode());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirlineIdentifierOne());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirlineIdentifierTwo());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirport());
			//Added by Manaf for Bug 17293 starts
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirlineIdentifierTwo());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirlineIdentifierOne());
			qry.setParameter(++index, uldPoolOwnerFilterVO.getAirport());
			//qry.setParameter(++index, uldPoolOwnerFilterVO.getAirport());
			//Added by Manaf for Bug 17293 ends
			//qry.setParameter(++index, uldPoolOwnerFilterVO.getOrigin());
			/*if(uldPoolOwnerFilterVO.getDestination()!=null && uldPoolOwnerFilterVO.getDestination().trim().length()>0){
				qry.setParameter(++index, uldPoolOwnerFilterVO.getDestination());
			}else{
				qry.setParameter(++index, uldPoolOwnerFilterVO.getOrigin());
			}*/
			
			int i = 0;
			boolean odPairExist = false;
			Map<String, String> odPairs = uldPoolOwnerFilterVO.getOdpairs().entrySet().stream()
					.filter(pair -> Objects.nonNull(pair.getKey()) && Objects.nonNull(pair.getValue()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			
			for(String key : odPairs.keySet()){
				if(i == 0){
					odPairExist = true;
					qry.setParameter(++index, key);
					qry.setParameter(++index, uldPoolOwnerFilterVO.getOdpairs().get(key));
				}else{
					qry.append(" OR  EXP.ORGCOD  = ? AND EXP.DSTCOD = ? ");
					qry.setParameter(++index, key);
					qry.setParameter(++index, uldPoolOwnerFilterVO.getOdpairs().get(key));
				}
				i++;
			}
			qry.append(" )");
			qry.append(" )");
			
			if (odPairExist && "1".equals(qry.getSingleResult(getStringMapper("RESULT")))) {
				log.log(Log.INFO, " %%%%%%% IA A POOLOWNER   ");
				isPoolOwner = true;
		   }
			log.log(Log.INFO, " PoolOwners or Not", isPoolOwner);
		return isPoolOwner;

	}
	
	/**
	 * @author A-3459
	 * @param uldFlightMessageReconcileVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<ULDFlightMessageReconcileVO> findMissingUCMs(
			ULDFlightMessageReconcileVO uldFlightMessageReconcileVO)
			throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findMissingUCMs");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_MISSING_UCMS);
		
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(query); 

		PageableNativeQuery<ULDFlightMessageReconcileVO> pgqry = new PageableNativeQuery<ULDFlightMessageReconcileVO>
		(uldFlightMessageReconcileVO.getTotalRecords(),rankQuery.toString(), new ListMissingUCMsMapper());
		
		pgqry.setParameter(++index, uldFlightMessageReconcileVO
				.getCompanyCode());
		pgqry.setParameter(++index, uldFlightMessageReconcileVO
				.getCompanyCode());
		
		if (uldFlightMessageReconcileVO.getFlightCarrierIdentifier() != 0) {
			pgqry.append(" AND COALESCE(EXPFLT.FLTCARIDR,IMPFLT.FLTCARIDR) = ? ");
			pgqry.setParameter(++index, uldFlightMessageReconcileVO
					.getFlightCarrierIdentifier());
		}
		if (uldFlightMessageReconcileVO.getOrigin() != null
				&& uldFlightMessageReconcileVO.getOrigin().trim().length() > 0) {
			pgqry.append(" AND EXPFLT.ARPCOD  = ?  ");
			pgqry.setParameter(++index, uldFlightMessageReconcileVO
							.getOrigin());
		}
		if (uldFlightMessageReconcileVO.getFlightNumber() != null
				&& uldFlightMessageReconcileVO.getFlightNumber().trim()
						.length() > 0) {
			log.log(Log.FINE, "uldFlightMessageReconcileVO.getFlightNumber ",
					uldFlightMessageReconcileVO.getFlightNumber ());
			pgqry.append(" AND COALESCE(EXPFLT.FLTNUM,IMPFLT.FLTNUM) = ? ");
			pgqry.setParameter(++index, uldFlightMessageReconcileVO
					.getFlightNumber());
		}
		if (uldFlightMessageReconcileVO.getDestination() != null
				&& uldFlightMessageReconcileVO.getDestination().trim().length() > 0) {
			pgqry.append(" AND  IMPFLT.ARPCOD  = ?  ");
			pgqry.setParameter(++index, uldFlightMessageReconcileVO
					.getDestination());
		}
		if (uldFlightMessageReconcileVO.getFromDate() != null) {
			pgqry.append(" AND COALESCE(TRUNC(EXPFLT.FLTDAT),TRUNC(IMPFLT.FLTDAT)) >= ? ");
			pgqry.setParameter(++index, uldFlightMessageReconcileVO
					.getFromDate());
		}
		if (uldFlightMessageReconcileVO.getToDate() != null) {
			pgqry.append(" AND COALESCE(TRUNC(EXPFLT.FLTDAT),TRUNC(IMPFLT.FLTDAT)) <= ? ");
			pgqry.setParameter(++index, uldFlightMessageReconcileVO
							.getToDate());
		}
		pgqry.append(" )FLT)A  ");
		if(ULDFlightMessageReconcileVO.UCMOUT_MISSED.equals(
				uldFlightMessageReconcileVO.getUcmOutMissed())){
			pgqry.append(" WHERE A.UCMOUT =  ? ");
			pgqry.setParameter(++index,uldFlightMessageReconcileVO.getUcmOutMissed());
			if(ULDFlightMessageReconcileVO.UCMIN_MISSED.equals(
					uldFlightMessageReconcileVO.getUcmInMissed())){
				pgqry.append(" AND A.UCMIN =  ? ");
				pgqry.setParameter(++index,uldFlightMessageReconcileVO.getUcmInMissed());
			}
				
		}
		else{
			if(ULDFlightMessageReconcileVO.UCMIN_MISSED.equals(
					uldFlightMessageReconcileVO.getUcmInMissed())){
				pgqry.append(" WHERE A.UCMIN =  ? ");
				pgqry.setParameter(++index,uldFlightMessageReconcileVO.getUcmInMissed());
			}
		}
		
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);

		return pgqry.getPage(uldFlightMessageReconcileVO.getPageNumber());
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
	 */
	public Page<String> findMUCRefNumberLov(String companyCode,
			int displayPage, String mucRefNum, String mucDate) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findMUCRefNumberLov");
		log.log(Log.FINE, "companyCode", companyCode);
		// Modified by A-5280 for ICRD-32647
		PageableNativeQuery<String> pgqry = null;
		String query = null;
       	query = getQueryManager().getNamedNativeQueryString(ULDDefaultsPersistenceConstants.FIND_MUC_REFERENCENUM_LOV);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(query);
		// Added by A-5280 for ICRD-32647 ends
		
		// Modified by A-5280 for ICRD-32647 used getStringMapper instead of new Mapper class
		pgqry = new PageableNativeQuery<String>(0,rankQuery.toString(),
				getStringMapper("MUCREFNUM"));
		pgqry.setParameter(1, companyCode);
		int index = 2;
		if (mucRefNum != null && mucRefNum.trim().length() > 0) {
			mucRefNum = mucRefNum.replace('*', '%');
			pgqry.append(" AND TXN.MUCREFNUM LIKE ? ");
			pgqry.setParameter(index, new StringBuilder().append(mucRefNum).append(
					"%").toString());
			index++;
		}
		if (mucDate != null && mucDate.trim().length() > 0) {
			pgqry.append("AND TRUNC(TXN.MUCDAT) = ? ");
			pgqry.setParameter(index, mucDate);
			index++;
		}
		//added by a-3045 for bug17975 starts
		pgqry.append("ORDER BY TXN.MUCREFNUM");
		//added by a-3045 for bug17975 ends
		Page<String> refNumbers = null;
		
		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(ULDDefaultsPersistenceConstants.ULD_DEFAULTS_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 ends
		
		refNumbers = pgqry.getPage(displayPage);
		log.log(Log.FINE, "lovVOs From server", refNumbers);
		log.exiting("ULDDefaultsSqlDAO", "findMUCRefNumberLov");
		return refNumbers;
	}


	/**
	 * MUCRefNumberLovMapper.java Created on Aug 01, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	private class MUCRefNumberLovMapper implements Mapper<String> {

		/**
		 * @param rs
		 * @return String
		 * @throws SQLException
		 */
		public String map(ResultSet rs) throws SQLException {
			log.entering("MUCRefNumberLovMapper", " map");
			return rs.getString("MUCREFNUM");

		}
	}
	/**
	 * For findMUCAuditDetails for MUC Tracking
	 * as a part of CR QF1013
	 * @author A-3045
	 * @param companyCode
	 * @param mucReferenceNumber
	 * @param mucDate
	 * @param controlReceiptNumber
	 * @return Collection<ULDConfigAuditVO>
	 * @throws SystemException
	 */
	public Collection<ULDConfigAuditVO> findMUCAuditDetails(ULDTransactionDetailsVO uldTransactionDetailsVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findMUCAuditDetails");
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_MUC_AUDIT_DETAILS);
		query.setParameter(1, uldTransactionDetailsVO.getCompanyCode());
		if (uldTransactionDetailsVO.getMucReferenceNumber() != null && uldTransactionDetailsVO.getMucReferenceNumber().trim().length() > 0) {			
			query.append("AND (AUD.ACTCOD = 'ULDMUCUPD' AND AUD.ADLINF LIKE ? ");
			query.setParameter(2,
					new StringBuilder("%").append("ULDNUM:-").append(uldTransactionDetailsVO.getUldNumber()).
					append("/MUCREFNUM:-").append(uldTransactionDetailsVO.getMucReferenceNumber()).append("/MUCDATE - ").
					append(uldTransactionDetailsVO.getMucDate().toDisplayDateOnlyFormat()).append("/%").toString());	
			query.append(") OR (AUD.ACTCOD IN ('ULDMUCGEN','ULDMUCRNT') AND AUD.ADLINF LIKE ? ");
			query.setParameter(3,
					new StringBuilder("%").append("MUCREFNUM:-").append(uldTransactionDetailsVO.getMucReferenceNumber()).append("/MUCDATE - ").					
					append(uldTransactionDetailsVO.getMucDate().toDisplayDateOnlyFormat()).append("/%").toString());
			query.append("AND INSTR(AUD.ADLINF,?) > 0)");
			query.setParameter(4,uldTransactionDetailsVO.getUldNumber());
			query.append("ORDER BY UPDTXNTIMUTC DESC");
		}
		Collection<ULDConfigAuditVO> coll = query.getResultList(new ULDConfigAuditMapper());
		log.exiting("ULDDefaultsSqlDAO", "findMUCAuditDetails");
		return coll;
	}
	 
   /**
	 * @param companyCode
	 * @param airportCode
	 * @param facility
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ULDAirportLocationVO findLocationforFacility(String companyCode,
				String airportCode,String facility) throws SystemException, PersistenceException {
	   	log.entering("ULDDefaultsSqlDAO", "findLocationforFacility");
			ULDAirportLocationVO uLDAirportLocationVO = null;
	   	Query query = getQueryManager().createNamedNativeQuery(
					ULDDefaultsPersistenceConstants.FIND_LOCATION_FACILITY);
			query.setParameter(1, companyCode);
			query.setParameter(2, airportCode);
			query.setParameter(3, facility);
			
			return query.getSingleResult(new ULDAirportLocationMapper());
	
		}
	
	/**
	 * @author A-3045
	 * @param companyCode
	 * @param uldNumbers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> checkULDInUse(String companyCode,Collection<String> uldNumbers)
	throws SystemException, PersistenceException {
	   	log.entering("ULDDefaultsSqlDAO", "checkULDInUse");
	   	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.CHECK_ULD_INUSE);
		query.setParameter(1, companyCode);	  
		int index = 1;
		//commented for bug 91475 on 13May10 starts
		/*int iCount = 0;
		if (uldNumbers != null && uldNumbers.size() > 0) {
			for (String uldNum : uldNumbers) {
				if (iCount == 0) {
					query.append(" AND SEGULD.ULDNUM IN (  ? ");
					query.setParameter(++index, uldNum);
				} else {
					query.append(" , ? ");
					query.setParameter(++index, uldNum);
				}
				iCount = 1;
			}
			if (iCount == 1) {
				query.append(" ) ");
			}
		}	   	
		query.append("UNION SELECT ARPULD.ULDNUM FROM OPRARPULD ARPULD WHERE ARPULD.CMPCOD = ? ");
		query.setParameter(++index, companyCode);
		int iCount2 = 0;
		if (uldNumbers != null && uldNumbers.size() > 0) {
			for (String uldNum : uldNumbers) {
				if (iCount2 == 0) {
					query.append(" AND ARPULD.ULDNUM IN (  ? ");
					query.setParameter(++index, uldNum);
				} else {
					query.append(" , ? ");
					query.setParameter(++index, uldNum);
				}
				iCount2 = 1;
			}
			if (iCount2 == 1) {
				query.append(" ) ");
			}
		}
		query.append("UNION SELECT MST.ULDNUM FROM ULDMST MST WHERE MST.CMPCOD = ? AND MST.TRNSTA = 'Y' ");
		query.setParameter(++index, companyCode);*/
		//commented for bug 91475 on 13May10 ends
		int iCount3 = 0;
		if (uldNumbers != null && uldNumbers.size() > 0) {
			for (String uldNum : uldNumbers) {
				if (iCount3 == 0) {
					query.append(" AND MST.ULDNUM IN (  ? ");
					query.setParameter(++index, uldNum);
				} else {
					query.append(" , ? ");
					query.setParameter(++index, uldNum);
				}
				iCount3 = 1;
			}
			if (iCount3 == 1) {
				query.append(" ) ");
			}
		}
		 return query.getResultList(getStringMapper("ULDNUM"));	
		}
	
	//added by a-3045 for bug18712 starts
	//to update status of SCm pending ULDs while processing SCM
	//This method is implemented because only 1000 ULDs can be updated using hibernate Query
	/**
	 * @author A-3045
	 * @param uldListFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void updateSCMStatusForPendingULDs(ULDListFilterVO uldListFilterVO)
	throws SystemException, PersistenceException {
	   	log.entering("ULDDefaultsSqlDAO", "updateSCMStatusForPendingULDs");
	   	Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.UPDATE_SCM_FOR_PENDING_ULDS);
	   	LocalDate ldate = new LocalDate(uldListFilterVO.getCurrentStation(), Location.ARP, true);
	   	if(uldListFilterVO.getLastMovementDate() != null){
	   		query.setParameter(1, uldListFilterVO.getLastMovementDate());
	   	}else{
	   		query.setParameter(1, ldate);
	   	}
	   	//added by a-6344 for ICRD-105499 starts
	   	//query.setParameter(2, ldate);
	   	//added by a-6344 for ICRD-10549 ends
		query.setParameter(2, uldListFilterVO.getCompanyCode());
		query.setParameter(3, uldListFilterVO.getAirlineidentifier());
		query.setParameter(4, uldListFilterVO.getCurrentStation());
		log.log(Log.INFO,
				"############## before executeUpdate%%%%%%uldListFilterVO",
				uldListFilterVO);
		query.executeUpdate();
		log.log(Log.INFO, "############## after executeUpdate%%%%%%");
	}
	
	//added by a-3045 for bug18712 ends
	
	/**
	 * This method is used for listing uld transaction
	 *
	 * @author A-1883
	 * @param uldTransactionFilterVO
	 * @return TransactionListVO
	 * @throws SystemException
	 */

	public TransactionListVO findTransactionHistory(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findTransactionHistory");		
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_TRANSACTION_HISTORY);
		int index = 0;	
		query.setParameter(++index, uldTransactionFilterVO.getCompanyCode());
		query.setParameter(++index, uldTransactionFilterVO.getUldNumber());
		if (uldTransactionFilterVO.getTxnFromDate() != null) {
			query.append("  AND T.TXNDAT >=  ? ");
			query.setParameter(++index, uldTransactionFilterVO.getTxnFromDate().toCalendar());
		}
		if (uldTransactionFilterVO.getTxnToDate() != null) {
			query.append("  AND T.TXNDAT <=  ? ");
			query.setParameter(++index, uldTransactionFilterVO.getTxnToDate().toCalendar());
		}
		//Added by A-2052 for the bug 101704 starts
		query.append("  AND TXNSTA IN('D','T','R'))A  WHERE RANK = 1 ");
		
		//Added by A-2052 for the bug 101704 ends
		//added and changed by a-3045 for bug 27030 starts,to handle Invoiced Status.
		query.append("UNION ALL");
		/*
		 * added " T.RTNCRN AS CRN " in the Select Query by a-3278 for 28897 on 05Jan09
		 * a new field CRN is included in the ULDHistory screen for Transaction Details
		 * For ULDs with To be Returned Status the CRN field value is taken;for To be Invoiced and Invoiced txnStatus returnCRN value is used
		 */
		//Added by A-2052 for the bug  101704 starts
		// The function DECODE changed as part of ORACLE to Postgre change  BUG ID # IASCB-114968
		query.append("SELECT  DISTINCT CASE WHEN txnsta='R' THEN 'T' ELSE txnsta END AS TXNSTA,T.ULDNUM, 'B' AS ORD, T.CMPCOD,T.CRN AS CRN, T.TXNDAT, T.TXNARPCOD, " );
		query.append("CASE WHEN TXNRMK ='DUMMY' AND TXNSTA ='R' THEN RTNPTYCOD ELSE PTYCOD END PTYCOD, T.PTYIDR, ");
		query.append("CAST(NULL AS DATE) RTNDAT, T.RTNARPCOD, T.RTNPTYIDR, T.TXNRMK, T.RTNRMK," );
		query.append("CASE WHEN TXNRMK ='DUMMY' AND TXNSTA ='R' THEN PTYCOD ELSE RTNPTYCOD END RTNPTYCOD, T.LSTUPDUSR,  ");
		query.append("T.LSTUPDTIM, T.DSTAPTCOD, T.TXNREFNUM FROM ULDTXNMST T WHERE T.CMPCOD = ? AND T.TXNSTA IN('R', 'I') AND T.ULDNUM = ? ");
	
		//query.append("SELECT  T.ULDNUM ,'B' AS ORD , T.CMPCOD, 'R' TXNSTA , T.RTNCRN AS CRN , T.TXNDAT , T.TXNARPCOD ,T.PTYCOD , T.PTYIDR ,");
		//query.append("T.RTNDAT ,T.RTNARPCOD , T.RTNPTYIDR ,  T.TXNRMK ,T.RTNRMK , T.RTNPTYCOD ,T.LSTUPDUSR ,");
		//query.append("T.LSTUPDTIM,T.DSTAPTCOD FROM ULDTXNMST T WHERE   T.CMPCOD = ? AND T.TXNSTA IN ('R','I') AND T.ULDNUM = ?");
		//Added by A-2052 for the bug 101704 ends
		query.setParameter(++index, uldTransactionFilterVO.getCompanyCode());
		query.setParameter(++index, uldTransactionFilterVO.getUldNumber());
		if (uldTransactionFilterVO.getTxnFromDate() != null) {
			query.append("  AND T.TXNDAT >=  ? ");
			query.setParameter(++index, uldTransactionFilterVO.getTxnFromDate().toCalendar());
		}
		if (uldTransactionFilterVO.getTxnToDate() != null) {
			query.append("  AND T.TXNDAT <=  ? ");
			query.setParameter(++index, uldTransactionFilterVO.getTxnToDate().toCalendar());
		}
		query.append("UNION ALL");
		//Added by A-2052 for the bug 101704 starts
		query.append("SELECT T.TXNSTA,T.ULDNUM, 'C' AS ORD, T.CMPCOD, T.RTNCRN AS CRN, T.TXNDAT, T.TXNARPCOD, T.PTYCOD, T.PTYIDR,");
		query.append("T.RTNDAT, T.RTNARPCOD, T.RTNPTYIDR, T.TXNRMK, T.RTNRMK,T.RTNPTYCOD, T.LSTUPDUSR,");
		query.append("T.LSTUPDTIM, T.DSTAPTCOD, T.TXNREFNUM FROM ULDTXNMST T WHERE T.CMPCOD = ? AND T.TXNSTA IN('I') AND T.ULDNUM = ? ");
				
		//query.append("SELECT  T.ULDNUM ,'C' AS ORD , T.CMPCOD, T.TXNSTA , T.RTNCRN AS CRN , T.TXNDAT , T.TXNARPCOD ,T.PTYCOD , T.PTYIDR ,");
		//query.append("T.RTNDAT ,T.RTNARPCOD , T.RTNPTYIDR ,  T.TXNRMK ,T.RTNRMK , T.RTNPTYCOD ,T.LSTUPDUSR ,");
		//query.append("T.LSTUPDTIM,T.DSTAPTCOD FROM ULDTXNMST T WHERE   T.CMPCOD = ? AND T.TXNSTA IN ('I') AND T.ULDNUM = ?");
		//Added by A-2052 for the bug 101704 ends
		query.setParameter(++index, uldTransactionFilterVO.getCompanyCode());
		query.setParameter(++index, uldTransactionFilterVO.getUldNumber());
		if (uldTransactionFilterVO.getTxnFromDate() != null) {
			query.append("  AND T.TXNDAT >=  ? ");
			query.setParameter(++index, uldTransactionFilterVO.getTxnFromDate().toCalendar());
		}
		if (uldTransactionFilterVO.getTxnToDate() != null) {
			query.append("  AND T.TXNDAT <=  ? ");
			query.setParameter(++index, uldTransactionFilterVO.getTxnToDate().toCalendar());
		}
		//Changed by a-3045 for bug 29462 on 09Dec08
		//Changed by A-3415 for ICRD-154003 on 15Jun2016
		query.append("ORDER BY TXNDAT DESC,ORD DESC, TXNREFNUM DESC, TXNSTA");
		//added and changed by a-3045 for bug 27030 ends,to handle Invoiced Status.
		PageableQuery<ULDTransactionDetailsVO> pageQuery = 	
			new PageableQuery<ULDTransactionDetailsVO>(query, new ListULDTransactionMapper());
		TransactionListVO transactionListVO = new TransactionListVO();
		transactionListVO.setTransactionDetailsPage(
				pageQuery.getPage(uldTransactionFilterVO.getPageNumber()));
		return transactionListVO;
	}
	
	/**
    * a-3045
    * @param uldListFilterVO
    * @return Collection<ULDListVO>
    * @throws SystemException
    * @throws PersistenceException
    */
	public Collection<ULDListVO> findULDListColl(ULDListFilterVO uldListFilterVO)
			throws SystemException{
		log.entering(MODULE_NAME, "findULDListColl");
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FIND_ULD_LIST);
		Query qry = new ULDListFilterQuery(uldListFilterVO, baseQuery);
		return qry.getResultList(new ULDListMapper());
	}
	/**
	 * @param uldListFilterVO
	 * @return Collection<ULDListVO>
	 * @throws SystemException
	 */
	public Collection<ULDListVO> findUldInventoryList(ULDListFilterVO uldListFilterVO) 
			throws SystemException {
		log.entering(MODULE_NAME, "findUldInventoryList");
		Query qry = new ULDListFilterQuery(uldListFilterVO, "SELECT "); 
		return qry.getResultList(new ULDInventoryListMultiMapper()); 
	} 
	
	/**
	 * This method is used for listing collection of  uld agreement in the system,bug25282
	 * @author A-3045
	 * @param uldAgreementFilterVO
	 * @return Collection<ULDAgreementVO>
	 * @throws SystemException
	 */
	public Collection<ULDAgreementVO> listULDAgreementsColl(
			ULDAgreementFilterVO uldAgreementFilterVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "listULDAgreementsColl");
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.LIST_ULDAGREEMENT);
		//Added By A-5183 For < ICRD-22824 > 
		PageableNativeQuery<ULDAgreementVO> pageQuery = new ULDAgreementFilterQuery(uldAgreementFilterVO,baseQuery,new ULDAgreementMapper());
		//Query query = new ULDAgreementFilterQuery(uldAgreementFilterVO,
				//baseQuery);
		return pageQuery.getResultList(new ULDAgreementMapper());
		
	}
	
	/**
	 * @author A-3045
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<ULDVO> findULDListForSCM(ULDListFilterVO uldListFilterVO,
			int pageNumber) throws SystemException {
		log.entering(MODULE_NAME, "findULDListForSCM");
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FIND_ULD_LIST);
		Query qry = new ULDListFilterQuery(uldListFilterVO, baseQuery);
		PageableQuery<ULDVO> pgqry = new PageableQuery<ULDVO>(qry,
				new SCMULDListMapper());
		return pgqry.getPage(pageNumber);

	}
	
	/**
	 * @author A-3459
	 * @param uldListFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Page<SCMValidationVO> findSCMValidationList(SCMValidationFilterVO scmValidationFilterVO) 
		throws SystemException {
		log.entering(MODULE_NAME, "findSCMValidationList");
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FIND_ULD_SCMVALIDATION);
		PageableNativeQuery<SCMValidationVO> pgqry = new SCMValidationFilterQuery(query.toString(), new SCMValidationMapper(), scmValidationFilterVO);
		return pgqry.getPage(scmValidationFilterVO.getPageNumber());
	}
	/**
	 * @author A-6994
	 *
	 */
	private class SCMValidationFilterQuery extends PageableNativeQuery<SCMValidationVO> {
		private String baseQuery;
		private SCMValidationFilterVO filterVO;
		public SCMValidationFilterQuery(String baseQuery, Mapper<SCMValidationVO> arg1, SCMValidationFilterVO filterVO) throws SystemException {
			super(filterVO.getTotalRecords(), arg1);
			this.baseQuery = baseQuery;
			this.filterVO = filterVO;
		}
		@Override
		public String getNativeQuery(){
			StringBuilder sb=new StringBuilder();
			sb.append(ULDDefaultsPersistenceConstants.ULD_DEFAULT_ROWNUM_RANK_QUERY);
			sb.append(baseQuery);
		int index = 0;
		//modified A-6344 for ICRD-105499 starts
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
		
		//Added by A-6770 for ICRD-152926 Starts here
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
		//Added by A-6770 for ICRD-152926 Ends here
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
		//Added by A-6770 for ICRD-152926 Ends here
		
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
		//added by A-5844 for ICRD-131466 start
			if (filterVO.getUldTypeCode()!= null &&  filterVO.getUldTypeCode().trim().length() > 0) {
				sb.append(" AND MST.ULDTYP = ? ");
				this.setParameter(++index, filterVO.getUldTypeCode());
		}
		/*//Commented by A-7131 for ICRD-149725
		if(scmValidationFilterVO.getFacilityType() != null &&
				scmValidationFilterVO.getFacilityType().trim().length()>0){
			query.append(" AND MST.FACTYP = ? ");
			query.setParameter(++index , scmValidationFilterVO.getFacilityType());
		}
    	if(scmValidationFilterVO.getLocation() != null &&
    				scmValidationFilterVO.getLocation().trim().length()>0){
    			query.append(" AND MST.LOC = ? ");
    			query.setParameter(++index , scmValidationFilterVO.getLocation());
    	}
		if(!"B".equals(scmValidationFilterVO.getScmStatus())&& scmValidationFilterVO.getScmStatus() != null &&
				scmValidationFilterVO.getScmStatus().trim().length()>0){			
			if("Y".equals(scmValidationFilterVO.getScmStatus())){
				query.append(" AND MST.SCMFLG = ? ");
			}else{
				query.append(" AND COALESCE(MST.SCMFLG,'N') = ? ");
			}
			query.setParameter(++index , scmValidationFilterVO.getScmStatus());
		}*/
			Query query_part2 = null;
			try {
				query_part2 = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FIND_ULD_SCMVALIDATION_PART2);
			} catch (SystemException e) {
				LOGGER.log(Log.SEVERE, "SystemException"+e);
			}
			sb.append(query_part2.toString());
		//added by A-5844 for ICRD-131466 ends
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
			this.setParameter(++index, filterVO.getAirlineIdentifier());
			this.setParameter(++index, filterVO.getCompanyCode());
			this.setParameter(++index, filterVO.getAirportCode());
		

		
		
		
		

		
		
		//modified A-6344 for ICRD-105499 ends
		
		//Commented as a part of the bug 66358(Not Sighted & % Missing not including PrevMissing) on 06Oct09
		//added by a-3278 on 28Jan09 for bug 34826 -- Applying the filter conditions for selecting the NotSighted Ulds
		/*if ( scmValidationFilterVO.getUldTypeCode()!= null &&  scmValidationFilterVO.getUldTypeCode().trim().length() > 0) {
			query.append(" AND ULD.ULDTYP = ? ");
			query.setParameter(++index, scmValidationFilterVO.getUldTypeCode());
		}
		if(scmValidationFilterVO.getFacilityType() != null &&
				scmValidationFilterVO.getFacilityType().trim().length()>0){
			query.append(" AND ULD.FACTYP = ? ");
			query.setParameter(++index , scmValidationFilterVO.getFacilityType());
		}
    	if(scmValidationFilterVO.getLocation() != null &&
    				scmValidationFilterVO.getLocation().trim().length()>0){
    			query.append(" AND ULD.LOC = ? ");
    			query.setParameter(++index , scmValidationFilterVO.getLocation());
    	}
		if(!"B".equals(scmValidationFilterVO.getScmStatus())&& scmValidationFilterVO.getScmStatus() != null &&
				scmValidationFilterVO.getScmStatus().trim().length()>0){
			query.append(" AND ULD.SCMFLG = ? ");
			query.setParameter(++index , scmValidationFilterVO.getScmStatus());
		}
		query.append("  AND ULD.SCMFLG = 'N') MIS, ");
		query.append(" ULDMST MST LEFT OUTER JOIN ULDDIS DIS ");
		query.append(" ON MST.CMPCOD = DIS.CMPCOD ");
		query.append(" AND MST.ULDNUM = DIS.ULDNUM  ");
		query.append(" AND MST.CURARP = DIS.RPTARP ");
		query.append(" AND DIS.DISCOD = 'M' ");
		query.append(" AND DIS.CLSSTA = 'N'  ");
		query.append(" WHERE MST.CMPCOD = ? ");
		query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
		query.append(" AND MST.CURARP = ?  ");
		query.setParameter(++index, scmValidationFilterVO.getAirportCode());
		query.append(" AND MST.OPRARLIDR = ? ");
		query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
		query.append(" AND MST.OWNARLIDR = ? ");
		query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());*/
		//a-3278 ends
		//Commented as a part of the bug 66358(Not Sighted & % Missing not including PrevMissing) on 06Oct09 ends
		
		/*query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
		query.setParameter(++index, scmValidationFilterVO.getAirportCode());
		query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
		query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());*/
		
		//added by A-5844 for ICRD-131466 starts
		
			if ( filterVO.getUldTypeCode()!= null &&  filterVO.getUldTypeCode().trim().length() > 0) {
				sb.append(" AND MST.ULDTYP = ? ");
				this.setParameter(++index, filterVO.getUldTypeCode());
		}
		/*//Commented by A-7131 for ICRD-149725
		if(scmValidationFilterVO.getFacilityType() != null &&
				scmValidationFilterVO.getFacilityType().trim().length()>0){
			query.append(" AND MST.FACTYP = ? ");
			query.setParameter(++index , scmValidationFilterVO.getFacilityType());
		}
    	if(scmValidationFilterVO.getLocation() != null &&
    				scmValidationFilterVO.getLocation().trim().length()>0){
    			query.append(" AND MST.LOC = ? ");
    			query.setParameter(++index , scmValidationFilterVO.getLocation());
    	}
		if(!"B".equals(scmValidationFilterVO.getScmStatus())&& scmValidationFilterVO.getScmStatus() != null &&
				scmValidationFilterVO.getScmStatus().trim().length()>0){			
			if("Y".equals(scmValidationFilterVO.getScmStatus())){
				query.append(" AND MST.SCMFLG = ? ");
			}else{
				query.append(" AND COALESCE(MST.SCMFLG,'N') = ? ");
			}
			query.setParameter(++index , scmValidationFilterVO.getScmStatus());
		}*/
		//added by A-5844 for ICRD-131466 ends
		//Added by A-7131 for ICRD-149725 starts
			sb.append(") mst WHERE 1=1");
			if(filterVO.getFacilityType() != null &&
					filterVO.getFacilityType().trim().length()>0){
				sb.append(" AND FACTYP = ? ");
				this.setParameter(++index , filterVO.getFacilityType());
			}
	    	if(filterVO.getLocation() != null &&
	    			filterVO.getLocation().trim().length()>0){
	    		sb.append(" AND LOC = ? ");
	    		this.setParameter(++index , filterVO.getLocation());
	    	}
			if(!"B".equals(filterVO.getScmStatus())&& filterVO.getScmStatus() != null &&
					filterVO.getScmStatus().trim().length()>0){			
				if("Y".equals(filterVO.getScmStatus())){
					sb.append(" AND SCMFLG = ? ");
			}else{
					sb.append(" AND COALESCE(SCMFLG,'N') = ? ");
			}
				this.setParameter(++index , filterVO.getScmStatus());
		}
		//Added by A-7131 for ICRD-149725 ends
			sb.append(" ORDER BY ULDNUM ");
			//log.log(Log.FINE, "query-->", this);
			sb.append(ULDDefaultsPersistenceConstants.ULD_DEFAULT_ROWNUM_SUFFIX_QUERY);
			
		    //PageableQuery<SCMValidationVO> pgqry = new PageableQuery<SCMValidationVO>(query,)
			return sb.toString();
			}
	}
	/**
	    * a-3045
	    * @param uldListFilterVO
	    * @return Collection<ULDListVO>
	    * @throws SystemException
	    * @throws PersistenceException
	    */
		public Collection<SCMValidationVO> findSCMValidationListColl(SCMValidationFilterVO scmValidationFilterVO)
				throws SystemException{
			log.entering(MODULE_NAME, "findULDListColl");
			Query query = getQueryManager().createNamedNativeQuery(
					ULDDefaultsPersistenceConstants.ULD_DEFAULTS_FIND_ULD_SCMVALIDATION);
			
			int index = 0;
			//modified A-6994 for ICRD-178058 begin
			query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			//modified A-6994 for ICRD-178058 ends
			/*//modified A-6344 for ICRD-105499 starts
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getCompanyCode());
			query.setParameter(++index, scmValidationFilterVO.getAirportCode());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			query.setParameter(++index, scmValidationFilterVO.getAirlineIdentifier());
			//modified A-6344 for ICRD-105499 ends
*/			
			
			
			if ( scmValidationFilterVO.getUldTypeCode()!= null &&  scmValidationFilterVO.getUldTypeCode().trim().length() > 0) {
				query.append(" AND MST.ULDTYP = ? ");
				query.setParameter(++index, scmValidationFilterVO.getUldTypeCode());
			}
			if(scmValidationFilterVO.getFacilityType() != null &&
					scmValidationFilterVO.getFacilityType().trim().length()>0){
				query.append(" AND MST.FACTYP = ? ");
				query.setParameter(++index , scmValidationFilterVO.getFacilityType());
			}
	    	if(scmValidationFilterVO.getLocation() != null &&
	    				scmValidationFilterVO.getLocation().trim().length()>0){
	    			query.append(" AND MST.LOC = ? ");
	    			query.setParameter(++index , scmValidationFilterVO.getLocation());
	    	}
			if(!"B".equals(scmValidationFilterVO.getScmStatus())&& scmValidationFilterVO.getScmStatus() != null &&
					scmValidationFilterVO.getScmStatus().trim().length()>0){			
				if("Y".equals(scmValidationFilterVO.getScmStatus())){
					query.append(" AND MST.SCMFLG = ? ");
				}else{
					query.append(" AND COALESCE(MST.SCMFLG,'N') = ? ");
				}
				query.setParameter(++index , scmValidationFilterVO.getScmStatus());
			}
			//added by a-3045 for bug36506 on 12Feb09 starts
			query.append(" ORDER BY MST.ULDNUM ");
			//modified A-6994 for ICRD-178058  for print begin
			query.append(ULDDefaultsPersistenceConstants.ULD_DEFAULT_LIST_SUFFIX_QUERY);
			//modified A-6994 for ICRD-178058 for print ends
			log.log(Log.FINE, "query-->", query);
			return query.getResultList(new SCMValidationMapper());
		}
	
		/**
		 * 
		 * @param companyCode
		 * @param col
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public void updateULDDiscrepancy(String companyCode, String airportCode, Collection<String> uldnos)
		throws SystemException, PersistenceException {
		   	log.entering("ULDDefaultsSqlDAO", "updateULDDiscrepancy");
		   	Query query = getQueryManager().createNamedNativeQuery(
					ULDDefaultsPersistenceConstants.UPDATE_ULD_DISCREPANCY);
		   	int index = 0;
			query.setParameter(++index, companyCode);
			query.setParameter(++index, airportCode);
			int iCount = 0;
			if (uldnos != null && uldnos.size() > 0) {/*
				for (String uldNum : uldnos) {
					if (iCount == 0) {
						query.append(" AND ULDNUM IN (  ? ");
						query.setParameter(++index, uldNum);
					} else {
						query.append(" , ? ");
						query.setParameter(++index, uldNum);
					}
					iCount = 1;
				}
				if (iCount == 1) {
					query.append(" ) ");
				}
				
				
			*/

				int count = uldnos.size();
				int j = 0;
				StringBuilder sbd = new StringBuilder();
				for(j = 0; j < count ; j++){
					if(j == 0){
						sbd.append(" AND ULDNUM IN(?,");					
					}
					else if(j != 0 && j % 900 == 0){					
						sbd.deleteCharAt(sbd.length()-1);							
						sbd.append(" ) OR ULDNUM IN (?,");
					}else{
						sbd.append("?,"); 
					}
				}
				sbd.deleteCharAt(sbd.length()-1);
				sbd.append(" )");
				query.append(sbd.toString());
			
				
				}
			if(uldnos.size() > 0){
				for(String uld : uldnos){
					query.setParameter(++index, uld);
				}
			}
			query.setSensitivity(true);
			query.executeUpdate();
			log.log(Log.INFO, " --^^^^ after executeUpdate%%%***%%%");
		}
		
		 /**
		 	* @author A-3278
		 	* @description added as apart of increasing the performance while processing SCM
		    * @param ULDListFilterVO
		    * @return Collection<ULDListVO>
		    * @throws SystemException
		    * @throws PersistenceException
		    */
		   public Collection<ULDListVO> findULDsForSCM(ULDListFilterVO filterVO)
					throws SystemException, PersistenceException {
				log.entering("ULDDefaultsSqlDAO", "findULDsForSCM");				
				Query query = getQueryManager().createNamedNativeQuery(
						ULDDefaultsPersistenceConstants.ULDS_FOR_SCM);
				int index=0;
				log.log(Log.FINE, "filterVO -->>", filterVO);
				query.setParameter(++index, filterVO.getCompanyCode());				
				query.setParameter(++index, filterVO.getCurrentStation());				
				query.setParameter(++index, filterVO.getAirlineidentifier());		
								
				return query.getResultList(new ULDListMapper());

			}
		   
		   /**
			 * This method is used for updating the ULDs 
			 * 	with respect to the SCM Message as per the exceution of job
			 * @author A-3278
			 * @param companyCode
			 * @param period
			 * @param userd
			 * @return null
			 * @throws SystemException
			 */

			public void updateSCMStatusForULD(String companyCode, int period,
						String userId) throws SystemException {
				log.entering("ULDDefaultsSqlDAO", "updateSCMStatusForULD");
		
				Procedure procedure = null;
				procedure = getQueryManager()
						.createNamedNativeProcedure(
								ULDDefaultsPersistenceConstants.ULD_DEFAULTS_UPDATESCMSTATUSFORULD);		
				int index = 0;
				if (companyCode != null && companyCode.trim().length() > 0) {
					procedure.setParameter(++index, companyCode);
				} else {
					procedure.setParameter(++index, BLANK);
				}
				procedure.setParameter(++index, period);
		
				if (userId != null && userId.trim().length() > 0) {
					procedure.setParameter(++index, userId);
				} else {
					procedure.setParameter(++index, BLANK);
				}		
				procedure.execute();
				log.log(Log.INFO,
								"############## after executeUpdate%%%% updateSCMStatusForULD %%%%");
			}
			
			 /**
	 *
	 * @param companyCode
	 * @param partyType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ULDServiceabilityVO> listULDServiceability(
			String companyCode, String partyType) throws SystemException,
			PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "listULDServiceability");
		Collection<ULDServiceabilityVO> uldServiceabilityVOs = null;
		Query query = getQueryManager()
				.createNamedNativeQuery(
						ULDDefaultsPersistenceConstants.LIST_ULD_SERVICEABILITY_CODEMASTER);
		query.setParameter(1, companyCode);
		if (partyType != null && partyType.trim().length() > 0) {
			query.append(" AND PTYTYP = ?");
			query.setParameter(2, partyType);
		}

		log.log(Log.FINE, "QUERY--------->>", query);
		uldServiceabilityVOs = query
				.getResultList(new ULDServiceabilityMapper());
		log.log(Log.FINE, "uldServiceabilityVOs inside SQLDAO",
				uldServiceabilityVOs);
		return uldServiceabilityVOs;
	}

	/**
	 *
	 * @param companyCode
	 * @param serviceCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public int checkForServiceability(String companyCode, String serviceCode,
			String partyType) throws SystemException, PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "checkForServiceability");
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.CHECK_ULD_SERVICEABILITY);
		query.setParameter(1, companyCode);
		query.setParameter(2, serviceCode);
		return query.getSingleResult(getIntMapper("RESULT"));
	}
	/**
	 * Method to find details for demurrage calculation for a 
	 * particular ULD in a particular transaction
	 * Added for Bug 102024 
	 * @author A-3268
	 * @param transactionFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ULDTransactionDetailsVO listULDTransactionDetailsForDemurrage(
			TransactionFilterVO transactionFilterVO)
	throws SystemException, PersistenceException{
		log.entering("ULDDefaultsSqlDAO", "listULDTransactionDetails");
		log.log(Log.FINE, " transactionFilterVO : ", transactionFilterVO);
		Collection<ULDTransactionDetailsVO> transactionDetailsVOs = null;
		String baseQuery=getQueryManager().createNamedNativeQuery(ULDDefaultsPersistenceConstants.LIST__ULDDTLSFORDEMURRAGE).toString();
		
		if(isOracleDataSource())
		{
			baseQuery=baseQuery.replace(PLACEHOLDER1, new StringBuilder().append("TRUNC(current_date)-TRUNC(TXNDAT)").toString());
		}
		else
		{
			baseQuery=baseQuery.replace(PLACEHOLDER1, new StringBuilder().append("(EXTRACT(epoch FROM (TRUNC(current_date)-TRUNC(TXNDAT)))/86400)").toString());
		}
		Query qry = getQueryManager().createNativeQuery(baseQuery);
		int index = 0; 
		qry.setParameter(++index,transactionFilterVO.getCompanyCode());
		qry.setParameter(++index,transactionFilterVO.getCompanyCode());
		qry.setParameter(++index,transactionFilterVO.getTransactionStationCode());
		qry.setParameter(++index,transactionFilterVO.getUldNumber());
		// Used only for direct loan retrun cases
		if(transactionFilterVO.getControlReceiptNo() != null && 
				transactionFilterVO.getControlReceiptNo().trim().length() > 0){
			qry.append(" AND TXN.CRN = ? ");
			qry.setParameter(++index,transactionFilterVO.getControlReceiptNo());
		}
		log.log(Log.FINE, "To party code (being used) : ", transactionFilterVO.getToPartyCode());
		log.log(Log.FINE, "From party code  : ", transactionFilterVO.getFromPartyCode());
		if (transactionFilterVO.getToPartyCode() != null &&
				transactionFilterVO.getToPartyCode().trim().length() != 0) {
				qry.append(" AND TXN.RTNPTYCOD = ? ");
			qry.setParameter(++index, transactionFilterVO.getToPartyCode());
		}
		// Party type
		if (transactionFilterVO.getPartyType() != null && 
				transactionFilterVO.getPartyType().trim().length() != 0) {
			qry.append(" AND TXN.PTYTYP = ? ");
			qry.setParameter(++index, transactionFilterVO.getPartyType());			
		}
		if(transactionFilterVO.getFromPartyCode() != null &&
				transactionFilterVO.getFromPartyCode().trim().length() > 0){
				qry.append(" AND TXN.PTYCOD = ? ");
			qry.setParameter(++index, transactionFilterVO.getFromPartyCode());
		}
		// Uld Type Code
		if (transactionFilterVO.getUldTypeCode() != null && 
				transactionFilterVO.getUldTypeCode().trim().length() != 0) {
			qry.append(" AND TXN.ULDTYP = ? ");
			qry.setParameter(++index, transactionFilterVO.getUldTypeCode());
		}
		qry.append(" AND AGRDTL.DMRRAT   > 0 ");
		qry.append(" ORDER BY TXN.ULDNUM ,TXN.TXNREFNUM )A )B WHERE B.PRI = B.MINPRI ");
		transactionDetailsVOs = qry
				.getResultList(new ListULDTransactionMapper());
		if(transactionDetailsVOs != null && transactionDetailsVOs.size() > 0){
			return transactionDetailsVOs.iterator().next();
		}else{
			return null;
		}				
	}
	
	public  Collection<ULDStockListVO> findULDStockListCollection(
			ULDStockConfigFilterVO uLDStockConfigFilterVO)
			throws SystemException {////a-5505 for bug ICRD-123103 
		log.entering("ULDDefaultsSqlDAO", "findULDStockListCollection");
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				ULDDefaultsPersistenceConstants.FIND_ULD_STOCK_LIST);
		Query query = new MonitorULDStockFliterQuery(baseQuery,new FindULDStockListMapper(),uLDStockConfigFilterVO);
		return query.getResultList(new FindULDStockListMultiMapper());
	}
	
	/**
	 * Added for ICRD-192217
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws SystemException
	 */
	public  Collection<EstimatedULDStockVO> findULDStockListForNotification(EstimatedULDStockFilterVO estimatedULDStockFilterVO)
			throws SystemException { 
		log.entering("ULDDefaultsSqlDAO", "findULDStockListForNotification");
		int index = 0;
		Query query= getQueryManager().createNamedNativeQuery(ULDDefaultsPersistenceConstants.FIND_ULDSTOCKLIST_FOR_NOTIFICATION); 
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		return query.getResultList(new EstimatedULDStockMapper());
	}
	
	/**
	 * Added for ICRD-192280
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws SystemException
	 */
	public  Collection<FlightDetailsVO> findUCMMissingFlights(EstimatedULDStockFilterVO estimatedULDStockFilterVO)
			throws SystemException { 
		log.entering("ULDDefaultsSqlDAO", "findULDStockListForNotification");
		int index = 0;
		String queryString = getQueryManager().getNamedNativeQueryString(ULDDefaultsPersistenceConstants.FIND_FLIGHTS_MISSING_UCM);	
		if(isOracleDataSource()){
		   String dynamicQuery = " ?/1440 ";
		   queryString = String.format(queryString, dynamicQuery, dynamicQuery);
		}else{
		   String dynamicQuery = " ( ? || 'MINUTES')::INTERVAL ";
		   queryString = String.format(queryString, dynamicQuery, dynamicQuery);
		}
		log.log(Log.INFO, "query ", queryString);
		Query query = getQueryManager().createNativeQuery(queryString);		
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getAtdOffset());
		query.setParameter(++index,estimatedULDStockFilterVO.getUcmMonitorPeriod());
		 
		return query.getResultList(new FlightsMissingUCMMapper());
	}
	
	public Page<EstimatedULDStockVO> findEstimatedULDStockList(
			EstimatedULDStockFilterVO estimatedULDStockFilterVO, int displayPage)
			throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findEstimatedULDStockList");
		int index = 0; 
		Query query= getQueryManager().createNamedNativeQuery(
		ULDDefaultsPersistenceConstants.FIND_ESTIMATED_ULD_STOCK_LIST);
		
		query.setParameter(++index,estimatedULDStockFilterVO.getAirport());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirport());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirport());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirport());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirport());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirport());
		query.setParameter(++index,estimatedULDStockFilterVO.getCompanyCode());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirport());
		query.setParameter(++index,estimatedULDStockFilterVO.getAirlineIdentifier());
		if (estimatedULDStockFilterVO.getUldType() != null && 
				estimatedULDStockFilterVO.getUldType().trim().length() != 0) {
			query.append("AND ULDTYP= ? ");
			query.setParameter(++index,estimatedULDStockFilterVO.getUldType());
			log
					.log(
							Log.INFO,
							"%%%%%%%%%%%estimatedULDStockFilterVO.getUldType()============>",
							estimatedULDStockFilterVO.getUldType());
		}
		query.append("GROUP BY ULDTYP) Z ORDER BY ULDTYP ASC");
		  	
		
		log.log(Log.INFO, "%%%%%%%%%%%query", query);
		log.exiting("ULDDefaultsSqlDAO", "findEstimatedULDStockList");
		PageableQuery<EstimatedULDStockVO> pgqry = new PageableQuery<EstimatedULDStockVO>(
				query, new EstimatedULDStockMapper());
		 	return pgqry.getPage(displayPage);
	}
	 
	public Page<ExcessStockAirportVO> findExcessStockAirportList(
			ExcessStockAirportFilterVO excessStockAirportFilterVO, int displayPage)
			throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findExcessStockAirportList");
		int index = 0; 
		Query query= getQueryManager().createNamedNativeQuery(
		ULDDefaultsPersistenceConstants.FIND_EXCESS_STOCK_AIRPORT_LIST);
		//Commented by A-7359 for ICRD-270721 <QRY:Id>225</QRY:Id>
		/*
		query.setParameter(++index,excessStockAirportFilterVO.getUldType());
		query.setParameter(++index,excessStockAirportFilterVO.getAirlineIdentifier());
		//Added by A-7359 for ICRD-257986 starts here 
		query.setParameter(++index,excessStockAirportFilterVO.getCompanyCode());
		//Added by A-7359 for ICRD-257986 ends here
		query.setParameter(++index,excessStockAirportFilterVO.getUldType());		
		query.setParameter(++index,excessStockAirportFilterVO.getAirlineIdentifier());
		*/
		query.setParameter(++index,excessStockAirportFilterVO.getAirlineIdentifier());		
		query.setParameter(++index,excessStockAirportFilterVO.getUldType());
		query.setParameter(++index,excessStockAirportFilterVO.getCompanyCode());
/*		if (excessStockAirportFilterVO.getUldType() != null && 
				excessStockAirportFilterVO.getUldType().trim().length() != 0) {
			query.append(" ULDTYP= ? ");
			query.setParameter(++index,excessStockAirportFilterVO.getUldType());
			log.log(Log.INFO, "%%%%%%%%%%%excessStockAirportFilterVO.getUldType()============>" +excessStockAirportFilterVO.getUldType());
		}
		query.append("GROUP BY CURARP) Z ORDER BY STOCKDEVIATION ASC");
		  */	
		
		log.log(Log.INFO, "%%%%%%%%%%%query", query);
		log.exiting("ULDDefaultsSqlDAO", "findExcessStockAirportList");
		PageableQuery<ExcessStockAirportVO> pgqry = new PageableQuery<ExcessStockAirportVO>(
				query, new ExcessStockAirportMapper());
		 	return pgqry.getPage(displayPage);
	}	

	/**
	 * 	@author A-6344
	 */
	public  String findSCMSequenceNum(String comapnyCode,String aiportcode,String airlineId)
	throws SystemException,PersistenceException {
		log.entering("ULDDefaultsSqlDAO", "findSCMSequenceNum");
		int index = 0; 
		Query query= getQueryManager().createNamedNativeQuery("uld.defaults.findsequencenumber");
		
		query.setParameter(++index,comapnyCode);
		query.setParameter(++index,aiportcode);		
		query.setParameter(++index,Integer.parseInt(airlineId));
		List<String> sequenceList = query.getResultList(new Mapper<String>() {
			public String map(ResultSet rs)
			throws SQLException {

				return rs.getString("SEQNUM");
			}
		});
		if(sequenceList!=null && sequenceList.size()>0){
			return sequenceList.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 	@author A-5125
	 */
	public Collection<ULDFlightMessageReconcileDetailsVO> findUldFltMsgRecDtlsVOs(
			ULDFlightMessageFilterVO uldFltmsgFilterVO) throws SystemException {
		log.entering("ULDDefaultsSqlDAO", "findUldFltMsgRecDtlsVOs");
		String baseQry = getQueryManager()
				.getNamedNativeQueryString(
						ULDDefaultsPersistenceConstants.FIND_ALL_ULD_FLIGHT_MSG_REC_DTLS);

		Query query = new UldFlightDetailsFilterQuery(uldFltmsgFilterVO,
				baseQry);
		log.log(Log.INFO,
				"finding Collection of uldFlightMsgReconsileDetails   QUERY:",
				query);
		Collection<ULDFlightMessageReconcileDetailsVO> uldFltmsgReconcol = query
				.getResultList(new ULDFltMsgReconsileDtlsMapper());
		log.exiting("ULDDefaultsSqlDAO", "findUldFltMsgRecDtlsVOs");
		return uldFltmsgReconcol;
	}

	
	/***
	 * @author A-5125 for BUG-ICRD-50392
	 * 
	 */
	public Collection<ULDFlightMessageReconcileDetailsVO> findTransitStateULDs(
			String cmpCod, LocalDate flightDate, String flightNumber)
			throws SystemException {

		log.entering("ULDDefaultsSqlDAO", "---------findTransitStateULDs----------");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_TRANSIT_STATE_ULDS_FOR_UCM);
		query.setParameter(++index, cmpCod);
		 query.setParameter(++index, flightDate.toStringFormat("dd-MM-yyyy").substring(0,10));
		query.setParameter(++index, flightNumber);
		Collection<ULDFlightMessageReconcileDetailsVO> uldFltmsgReconVoCol = query
				.getResultList(new ULDFltMsgReconsileDtlsMapper());
		log.exiting("ULDDefaultsSqlDAO", "uldFltmsgReconcol");

		return uldFltmsgReconVoCol;
	}
	/**	 * 
	 *Added as part of the ICRD-114538
	 * @author A-3415 
	 * @param transactionFilterVO
	 * @return
	 * @throws SystemException
	 */
	public ULDTransactionDetailsVO findLastTransactionsForUld(
			TransactionFilterVO transactionFilterVO) throws SystemException,
			PersistenceException {
			log.entering("ULDDefaultsSqlDAO", "findLastTransactionsForUld");
				Query query = getQueryManager()
					.createNamedNativeQuery(
							ULDDefaultsPersistenceConstants.FIND_LAST_TRANSACTIONSFOR_ULD);
			query.setParameter(1, transactionFilterVO.getCompanyCode());
			query.setParameter(2, transactionFilterVO.getUldNumber());
			log.log(Log.FINE, "QUERY--------->>" + query);
			return query.getSingleResult(new Mapper<ULDTransactionDetailsVO>() {
				public ULDTransactionDetailsVO map(ResultSet rs) throws SQLException {
					ULDTransactionDetailsVO transactionDetailsVO = new ULDTransactionDetailsVO();
					transactionDetailsVO.setUldNumber(rs.getString("ULDNUM"));
					transactionDetailsVO.setTransactionStationCode(rs.getString("TXNARPCOD"));
					transactionDetailsVO.setReturnStationCode(rs.getString("RTNARPCOD"));
					transactionDetailsVO.setTransactionType(rs.getString("TXNTYP"));
					transactionDetailsVO.setToPartyCode(rs.getString("PTYCOD"));    
					transactionDetailsVO.setToPartyIdentifier(rs.getInt("PTYIDR"));
					transactionDetailsVO.setFromPartyCode(rs.getString("RTNPTYCOD"));
					transactionDetailsVO.setFromPartyIdentifier(rs.getInt("RTNPTYIDR"));
					transactionDetailsVO.setTransactionStatus(rs.getString("TXNSTA")); 
					transactionDetailsVO.setTransactionRefNumber(rs.getInt("TXNREFNUM"));  
					transactionDetailsVO.setPartyType(rs.getString("PTYTYP")); 
					transactionDetailsVO.setUldType(rs.getString("ULDTYP"));
					transactionDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
					if (rs.getTimestamp("TXNDAT")!= null) { 
								transactionDetailsVO.setTransactionDate(new LocalDate(transactionDetailsVO.getTransactionStationCode(), Location.ARP, rs.getTimestamp("TXNDAT")));
					}
					if (rs.getTimestamp("RTNDAT")!= null) { 
									transactionDetailsVO.setReturnDate(new LocalDate(transactionDetailsVO.getReturnStationCode(), Location.ARP, rs.getTimestamp("RTNDAT")));
					}
					return transactionDetailsVO;
				}
			});
	}



//merge solved by A-7794

	/**	 * 
	 *Added as part of the ICRD-208677
	 * @author A-7794
	 * @param operationalULDAuditFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<OperationalULDAuditVO> findOprAndMailULDAuditDetails(OperationalULDAuditFilterVO operationalULDAuditFilterVO)
		    throws SystemException, PersistenceException
		  {
		    log.entering("ULDDefaultsSQLDAO", "findOprAndMaiULDAuditDetails");
		    int index = 0;
		    Query qry = getQueryManager().createNamedNativeQuery(
		    		ULDDefaultsPersistenceConstants.FIND_OPERATIONAL_ULD_AUDIT_DETAILS);
		    index++; qry.setParameter(index, operationalULDAuditFilterVO.getCompanyCode());
		    index++; qry.setParameter(index, operationalULDAuditFilterVO.getUldNumber());
		    if (operationalULDAuditFilterVO.getFromDate() != null) {
		      qry.append("  AND TRUNC(OPR.UPDTXNTIM) >= ? ");
		      index++; qry.setParameter(index, operationalULDAuditFilterVO.getFromDate());
		    }

		    if (operationalULDAuditFilterVO.getToDate() != null) {
		      qry.append("  AND TRUNC(OPR.UPDTXNTIM) <= ? ");
		      index++; qry.setParameter(index, operationalULDAuditFilterVO.getToDate());
		    }
		    qry.append(" AND opr.sernum = ");
		    qry.append(" (SELECT MIN(sernum) FROM opruldaud aud ");
		    qry.append(" WHERE aud.cmpcod = opr.cmpcod AND aud.fltcaridr = opr.fltcaridr AND aud.fltnum = opr.fltnum AND aud.fltseqnum = opr.fltseqnum AND aud.uldnum = opr.uldnum AND aud.arpcod = opr.arpcod AND aud.actcod = opr.actcod)");
			 qry.append(" UNION ALL ");
			 Query mailqry = getQueryManager().createNamedNativeQuery(
					ULDDefaultsPersistenceConstants.FIND_OPERATIONAL_MAILULD_AUDIT_DETAILS);
		    qry.append(mailqry.toString());
		 index++; qry.setParameter(index, operationalULDAuditFilterVO.getCompanyCode());
		    index++; qry.setParameter(index, operationalULDAuditFilterVO.getUldNumber());
		    if (operationalULDAuditFilterVO.getFromDate() != null) {
		      qry.append("  AND TRUNC(OPR.UPDTXNTIM) >= ? ");
		      index++; qry.setParameter(index, operationalULDAuditFilterVO.getFromDate());
		    }

		    if (operationalULDAuditFilterVO.getToDate() != null) {
		      qry.append("  AND TRUNC(OPR.UPDTXNTIM) <= ? ");
		      index++; qry.setParameter(index, operationalULDAuditFilterVO.getToDate());
		    }
		    //Added by A-7794 as part of ICRD-224604
		  
		    qry.append(" UNION ALL ");
			 Query mailcarrierqry = getQueryManager().createNamedNativeQuery(
					ULDDefaultsPersistenceConstants.FIND_OPERATIONAL_MAILULD_AUDIT_DETAILS_FOR_OAL_CARRIER);
		    qry.append(mailcarrierqry.toString());
		    index++; qry.setParameter(index, operationalULDAuditFilterVO.getCompanyCode());
		    index++; qry.setParameter(index, operationalULDAuditFilterVO.getUldNumber()); 
		    if(operationalULDAuditFilterVO.getUldNumber().endsWith(operationalULDAuditFilterVO.getCarrierCode())){
		    	qry.append("OPR.ACTCOD     = 'CONCREATOFL' )");
		    }else{
		    	qry.append(" OPR.ACTCOD     = 'CONCREATASGN' OR OPR.ACTCOD= 'CREATE' OR OPR.ACTCOD = 'CONCREATOFL' ) ");
		    }
		    if (operationalULDAuditFilterVO.getFromDate() != null) {
		      qry.append("  AND TRUNC(OPR.UPDTXNTIM) >= ? ");
		      index++; qry.setParameter(index, operationalULDAuditFilterVO.getFromDate());
		    }

		    if (operationalULDAuditFilterVO.getToDate() != null) {
		      qry.append("  AND TRUNC(OPR.UPDTXNTIM) <= ? ");
		      index++; qry.setParameter(index, operationalULDAuditFilterVO.getToDate());
		    }
		    
		    PageableQuery pageableQuery = new PageableQuery(
		      qry, new ULDAuditMapper());
		    log.log(3, new Object[] { "Listing****", pageableQuery });
		    return pageableQuery.getPage(operationalULDAuditFilterVO
		      .getPageNumber());
		  }
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO#findlatestUCMsFromAllSources(com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO)
	 *	Added by 			: A-7359 on 07-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param uldFlightMessageFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 */
	public ArrayList<ULDFlightMessageReconcileVO> findlatestUCMsFromAllSources(
			FlightFilterMessageVO uldFlightMessageFilterVO)
			throws SystemException, PersistenceException {

		log.entering("ULDSqlDAO", "findlatestUCMsFromAllSources");

		ArrayList<ULDFlightMessageReconcileVO> detailsVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.FIND_lATESTUCM_FROMALL_SOURCES);
		
		qry.setParameter(++index, uldFlightMessageFilterVO.getCompanyCode());
		qry.setParameter(++index, uldFlightMessageFilterVO.getAirportCode());
		qry.setParameter(++index, uldFlightMessageFilterVO.getFlightCarrierId());
		qry.setParameter(++index, uldFlightMessageFilterVO.getFlightNumber()); 
		qry.setParameter(++index,
				uldFlightMessageFilterVO.getFlightSequenceNumber());
		qry.setParameter(++index, uldFlightMessageFilterVO.getMessageType());
		
		log.log(Log.INFO, "%%%%%  Query ", qry);
		detailsVOs = new ArrayList<ULDFlightMessageReconcileVO>(
				qry.getResultList(new LatestUCMsFromSourceMultiMapper()));
		
		log.exiting("ULDSqlDAO", "findlatestUCMsFromAllSources");
		return detailsVOs;

	}
	public Map<String, ULDDiscrepancyVO> findUldDiscrepancyDetails(
			ULDDiscrepancyFilterVO discrepancyFilterVO) throws SystemException {
		Map<String, ULDDiscrepancyVO> uldDisMap=new HashMap<String, ULDDiscrepancyVO>();
		int index = 0;
		Collection<ULDDiscrepancyVO>uLDDiscrepancyVOs=new ArrayList<ULDDiscrepancyVO>();
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.LIST_ULD_DISCREPANCY);
		query.setParameter(++index, discrepancyFilterVO.getCompanyCode());
		/*int count = 0;
		if (discrepancyFilterVO.getUldNumbers() != null) {
			for (String uldnum : discrepancyFilterVO.getUldNumbers()) {
				if (count == 0) {
					query.append("  AND DIS.ULDNUM IN ( ? ");
					query.setParameter(++index, uldnum);
				} else {
					query.append(" , ? ");
					query.setParameter(++index, uldnum);
				}
				count = 1;
			}
			if (count == 1) {
				query.append(" ) ");
			}
		}*/
		//commented above code and added for bug ICRD-326377
		if (discrepancyFilterVO.getUldNumbers() != null) {  
			StringBuilder uldNumberBuilder = new StringBuilder();
			int count = 0;
			for(String uldNumber: discrepancyFilterVO.getUldNumbers()) {
				if(uldNumberBuilder.length() == 0) {
					uldNumberBuilder.append("'").append(uldNumber).append("'"); 
				} else {
					
					uldNumberBuilder.append(",").append("'").append(uldNumber).append("'");
				}
				count++;
				if(count % 1000 == 0){
					if(count == 1000){
						query.append("AND (DIS.ULDNUM IN ( ").append(uldNumberBuilder.toString()).append(")");
					}else{
						query.append("OR DIS.ULDNUM IN ( ").append(uldNumberBuilder.toString()).append(")");
					}
					uldNumberBuilder = new StringBuilder();
				}
			}
			if(count < 1000){
				query.append("AND DIS.ULDNUM IN ( ").append(uldNumberBuilder.toString()).append(")");
			}else if(count > 1000){
				query.append("OR DIS.ULDNUM IN (").append(uldNumberBuilder.toString()).append("))"); 
			}else{
				query.append(")");
			}
		}
		//added for bug ICRD-326377 ends
		if (discrepancyFilterVO.getDiscrepancyStatus() != null
				&& discrepancyFilterVO.getDiscrepancyStatus().trim().length() > 0) {
			query.append(" and DIS.DISCOD=? ");
			query.setParameter(++index,
					discrepancyFilterVO.getDiscrepancyStatus());
		}
		query.append(" ORDER BY ULDNUM ");
		uLDDiscrepancyVOs = new ArrayList<ULDDiscrepancyVO>(
				query.getResultList(new ULDDiscrepancyMapper()));
		for(ULDDiscrepancyVO uLDDiscrepancyVO:uLDDiscrepancyVOs){
			if(!uldDisMap.containsKey(uLDDiscrepancyVO.getUldNumber())){
				uldDisMap.put(uLDDiscrepancyVO.getUldNumber(), uLDDiscrepancyVO);
			}
		}
    	return uldDisMap;
	}
	
	public void removeDamageImages(ULDDamageVO uldDamageVO) throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.REMOVE_DAMAGE_IMAGES);
		int index = 0;
		query.setParameter(++index, uldDamageVO.getCompanyCode());
		query.setParameter(++index, uldDamageVO.getDamageCode());
		query.setParameter(++index, uldDamageVO.getSequenceNumber());
		query.executeUpdate();
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
	public boolean validateULDAirportLocation(String companyCode, String airportCode,
											  String locationCode, String facilityTypeCode) throws SystemException {
		log.entering("ULDSqlDAO", "validateULDAirportLocation");
		boolean isLocationValid = false;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.VALIDATE_ULD_AIRPORT_LOCATION);
		int index = 0;
		query.setParameter(++index,companyCode);
		query.setParameter(++index,airportCode);
		query.setParameter(++index,locationCode);
		query.setParameter(++index,facilityTypeCode);

		if ("1".equals(query.getSingleResult(getStringMapper("RESULT")))) {
			log.log(Log.INFO, "ULD Airport locaiton is valid");
			isLocationValid = true;
		}

		return  isLocationValid;
	}
	
	
	public boolean findDuplicatePoolOwnerConfig(ULDPoolOwnerVO uldPoolOwnerVO)  
			throws SystemException, PersistenceException{
		log.entering("ULDSqlDAO", "findDuplicatePoolOwnerConfig");
		boolean isDuplicateConfigExists = false;
		Query query = getQueryManager().createNamedNativeQuery(
				ULDDefaultsPersistenceConstants.DUPLICATE_ULD_POOLOWNER);
		int index = 0;
		query.setParameter(++index, uldPoolOwnerVO.getCompanyCode());
		query.setParameter(++index, uldPoolOwnerVO.getAirlineIdentifierOne());
		query.setParameter(++index, uldPoolOwnerVO.getAirlineIdentifierTwo());
		
		if(uldPoolOwnerVO.getAirport()!=null && 
				uldPoolOwnerVO.getAirport().trim().length()>0){
			query.append("and ( arpcod = ?   ");       
			query.setParameter(++index, uldPoolOwnerVO.getAirport());
			query.append("or arpcod is null )");
		}/*else{
			query.append("arpcod is null ");
		}*/  
		
		if ("1".equals(query.getSingleResult(getStringMapper("RESULT")))) {
			log.log(Log.INFO, "Duplicate Pool Owner Config exists in DB  ");
			isDuplicateConfigExists = true;
	   }
		log.exiting("ULDSqlDAO", "findDuplicatePoolOwnerConfig");
		return isDuplicateConfigExists;
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

	@Override
	public Collection<String> findAirportsforSCMJob(String companyCode,Collection<String> airportGroup,String noOfDays) throws PersistenceException, SystemException {
		log.entering("ULDSqlDAO", "findAirportsforSCMJob");
		Query query=getQueryManager().createNamedNativeQuery(ULDDefaultsPersistenceConstants.FIND_AIRPORTS_FOR_SCM_JOB);
		int index=0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, (Integer.parseInt(noOfDays)));
		if (Objects.nonNull(airportGroup) && !airportGroup.isEmpty()) {
		query.append(" AND mst.arpcod IN (");
		boolean firstValue = true;
		for (String airport : airportGroup) {
		if (!firstValue) {
		query.append(",");
		}
		query.append("'"+airport+"'");
		firstValue = false;
		}
		query.append(")");
		}
		query.append("ORDER BY AIRPORT");
		 Collection<String> returnAirports= query.getResultList(new SCMJobSchedulerMultiMapper(airportGroup));
		 if(Objects.nonNull(returnAirports) && !returnAirports.isEmpty()) {
			 return returnAirports;
		 }else {
			 return airportGroup;
		 }
		 	

	}
	
	
}
