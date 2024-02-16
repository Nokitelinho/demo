/*
 * CustomerMgmntDefaultsSqlDAO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.CustomerServicesVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListPointsAccumulatedFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ParameterDescriptionVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.RedemptionValidationVO;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/*
 * @author A-1496
 * 
 */
public class CustomerMgmntDefaultsSqlDAO extends AbstractQueryDAO implements
		CustomerMgmntDefaultsDAO {

	private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");

	/***
	 * @author A-1883
	 * @param companyCode
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	/*public Collection<LoyaltyProgrammeVO> findAllLoyaltyProgrammes(
			String companyCode) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO",
				"findAllLoyaltyProgrammes");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.FIND_ALL_LOYALTY_PROGRAMMES);
		query.setParameter(1, companyCode);
		Collection<LoyaltyProgrammeVO> programmes = query
				.getResultList(new AllLoyaltyProgrammesMapper());
		log.exiting("CustomerMgmntDefaults Sql DAO", "findAllLoyaltyProgrammes");
		return programmes;
	}*/

	/*
	 * AllLoyaltyProgrammesMapper.java Created on Dec 19, 2005
	 * 
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 * 
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	/**
	 * 
	 */
	/*private class AllLoyaltyProgrammesMapper implements
			Mapper<LoyaltyProgrammeVO> {
		/**
		 * @param resultSet
		 * @return LoyaltyProgrammeVO
		 * @throws SQLException
		 */
		/*public LoyaltyProgrammeVO map(ResultSet resultSet) throws SQLException {
			log.entering("AllLoyaltyProgrammesMapper", "map");
			LoyaltyProgrammeVO loyaltyProgrammeVO = new LoyaltyProgrammeVO();
			loyaltyProgrammeVO.setCompanyCode(resultSet.getString("CMPCOD"));
			loyaltyProgrammeVO.setLoyaltyProgrammeCode(resultSet
					.getString("LTYPRGCOD"));
			loyaltyProgrammeVO.setLoyaltyProgrammeDesc(resultSet
					.getString("LTYPRGDES"));
			Date fromDate = resultSet.getDate("FRMDAT");
			if (fromDate != null) {
				loyaltyProgrammeVO.setFromDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, fromDate));
			}
			Date toDate = resultSet.getDate("TOODAT");
			if (toDate != null) {
				loyaltyProgrammeVO.setToDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, toDate));
			}
			log.exiting("AllLoyaltyProgrammesMapper", "map");
			return loyaltyProgrammeVO;
		}*/
	/*}*/

	/**
	 * List Loyalty programmes based on Filter
	 * 
	 * @author A-1883
	 * @param loyaltyProgrammeFilterVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Collection<LoyaltyProgrammeVO> listLoyaltyProgramme(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "listLoyaltyProgramme");
		LocalDate fromDate = loyaltyProgrammeFilterVO.getFromDate();
		LocalDate toDate = loyaltyProgrammeFilterVO.getToDate();
		String loyaltyProgramCode = loyaltyProgrammeFilterVO
				.getLoyaltyProgrammeCode();
		int index = 1;
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.LIST_LOYALTY_PROGRAMMES);
		query.setParameter(1, loyaltyProgrammeFilterVO.getCompanyCode());
		if (loyaltyProgramCode != null
				&& loyaltyProgramCode.trim().length() > 0) {
			query.append(" AND prgmst.ltyprgcod = ? ");
			query.setParameter(++index, loyaltyProgramCode);
		}
		if (toDate != null) {
			query.append(" AND prgmst.frmdat <= ? ");
			query.setParameter(++index, toDate.toCalendar());
		}
		if (fromDate != null) {
			query.append(" AND prgmst.toodat >= ? ");
			query.setParameter(++index, fromDate.toCalendar());
		}
		query.append(" ORDER BY ltyprgcod ");
		Collection<LoyaltyProgrammeVO> programmesVOs = query
				.getResultList(new ListLoyaltyProgrammesMultiMapper());
		log.exiting("CustomerMgmntDefaults Sql DAO", "listLoyaltyProgramme");
		return programmesVOs;
	}

	/**
	 * 
	 * @param companyCode
	 * @return Collection<LoyaltyAttributeVO>
	 * @throws SystemException
	 */
	public Collection<LoyaltyAttributeVO> listLoyaltyAttributeDetails(
			String companyCode) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO",
				"listLoyaltyAttributeDetails");
		Query query = getQueryManager()
				.createNamedNativeQuery(
						CustomerMgmntPersistenceConstants.LIST_LOYALTY_ATTRIBUTE_DETAILS);
		query.setParameter(1, companyCode);
		Collection<LoyaltyAttributeVO> attributeDetails = query
				.getResultList(new LoyaltyAttributeDetailsMapper());
		log.exiting("CustomerMgmntDefaults Sql DAO",
				"listLoyaltyAttributeDetails");
		return attributeDetails;
	}

	/**
	 * 
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupVO>
	 * @throws SystemException
	 */
	/*
	 * public Collection<CustomerLoyaltyGroupVO> listCustomerGroup(String
	 * companyCode , String groupCode) throws SystemException{
	 * log.entering("CustomerMgmntDefaults Sql DAO","listCustomerGroup");
	 * Collection<CustomerLoyaltyGroupVO> customerGroupVOs =null; Query query
	 * =getQueryManager().createNamedNativeQuery(
	 * CustomerMgmntPersistenceConstants.LIST_CUSTOMER_GROUP);
	 * query.setParameter(1,companyCode); if(groupCode != null &&
	 * groupCode.trim().length() >0 ) { query.append("AND GRPCOD = ? ");
	 * query.setParameter(2,groupCode); } query.append("ORDER BY GRPCOD ");
	 * customerGroupVOs =query.getResultList(new CustomerGroupMapper());
	 * 
	 * return customerGroupVOs;
	 * 
	 * }
	 */

	/**
	 * 
	 * @param companyCode
	 * @param groupCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	/*
	 * public Page<CustomerLoyaltyGroupVO> customerGroupLOV(String companyCode ,
	 * String groupCode,int pageNumber) throws SystemException{
	 * log.entering("CustomerMgmntDefaults Sql DAO","customerGroupLOV"); Query
	 * query =getQueryManager().createNamedNativeQuery(
	 * CustomerMgmntPersistenceConstants.LIST_CUSTOMER_GROUPLOV);
	 * 
	 * query.setParameter(1,companyCode); if(groupCode != null &&
	 * groupCode.trim().length() >0 ) { query.append("AND GRPCOD = ? ");
	 * query.setParameter(2,groupCode); } PageableQuery<CustomerLoyaltyGroupVO>
	 * pgqry = new PageableQuery<CustomerLoyaltyGroupVO>(query, new
	 * CustomerGroupMapper()); return pgqry.getPage(pageNumber); }
	 */

	/**
	 * 
	 * @param companyCode
	 * @param tempCustCode
	 * @return TempCustomerVO
	 * @throws SystemException
	 */
	/*
	 * public TempCustomerVO listTempCustomer(String companyCode, String
	 * tempCustCode) throws SystemException{
	 * log.entering("CustomerMgmntDefaults Sql DAO","listTempCustomer");
	 * List<TempCustomerVO> tempCustomerVOs = new ArrayList<TempCustomerVO>();
	 * TempCustomerVO tempCustomerVO = null; Query query
	 * =getQueryManager().createNamedNativeQuery(
	 * CustomerMgmntPersistenceConstants.LIST_TEMP_CUSTOMER);
	 * query.setParameter(1,companyCode); if(tempCustCode != null &&
	 * tempCustCode.trim().length() >0 ) { query.append("AND CUSCOD = ? ");
	 * query.setParameter(2,tempCustCode); } tempCustomerVOs =
	 * query.getResultList(new TempCustMapper()); if(tempCustomerVOs != null &&
	 * tempCustomerVOs.size() >0) { tempCustomerVO =tempCustomerVOs.get(0); }
	 * return tempCustomerVO; }
	 */
	/**
	 * 
	 * @param listTempCustomerVO
	 * @return TempCustomerVO
	 * @throws SystemException
	 */
	/*
	 * public Page<TempCustomerVO> listTempCustomerDetails(ListTempCustomerVO
	 * listTempCustomerVO)throws SystemException{
	 * log.entering("CustomerMgmntDefaults Sql DAO","listTempCustomer");
	 * StringBuilder sb = new StringBuilder(); Query query
	 * =getQueryManager().createNamedNativeQuery(
	 * CustomerMgmntPersistenceConstants.LIST_TEMP_CUSTOMER_DETAILS);
	 * query.setParameter(1,listTempCustomerVO.getCompanyCode()); int index =1;
	 * if(listTempCustomerVO.getTempCustCode() != null &&
	 * listTempCustomerVO.getTempCustCode().trim().length() >0) {
	 * query.append("AND CUSCOD = ? ");
	 * query.setParameter(++index,listTempCustomerVO.getTempCustCode()); }
	 * 
	 * if(listTempCustomerVO.getTempCustName() != null &&
	 * listTempCustomerVO.getTempCustName().trim().length() >0) {
	 * query.append("AND CUSNAM LIKE ? "); String custName =
	 * sb.append("%").append(
	 * listTempCustomerVO.getTempCustName()).append("%").toString();
	 * 
	 * query.setParameter(++index,custName); }
	 * 
	 * if(listTempCustomerVO.getActiveStatus() != null &&
	 * listTempCustomerVO.getActiveStatus().trim().length() >0) {
	 * query.append("AND ACTSTA = ? ");
	 * query.setParameter(++index,listTempCustomerVO.getActiveStatus()); }
	 * 
	 * if(listTempCustomerVO.getStationCode() != null &&
	 * listTempCustomerVO.getStationCode().trim().length() >0) {
	 * query.append("AND STNCOD = ? ");
	 * query.setParameter(++index,listTempCustomerVO.getStationCode()); }
	 * 
	 * if(listTempCustomerVO.getFromDate() != null) {
	 * query.append("AND CRTDAT >= ? ");
	 * query.setParameter(++index,listTempCustomerVO.getFromDate()); }
	 * if(listTempCustomerVO.getToDate() != null) {
	 * query.append("AND CRTDAT <= ? ");
	 * query.setParameter(++index,listTempCustomerVO.getToDate()); }
	 * 
	 * PageableQuery<TempCustomerVO> pgqry = new
	 * PageableQuery<TempCustomerVO>(query, new TempCustMapper()); return
	 * pgqry.getPage(listTempCustomerVO.getDisplayPage());
	 * 
	 * }
	 */
	/**
	 * Validates parameter code and values
	 * 
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Collection<LoyaltyProgrammeVO> validateParameter(
			LoyaltyProgrammeVO loyaltyProgrammeVO) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "validateParameter");
		String baseQry = getQueryManager().getNamedNativeQueryString(
				CustomerMgmntPersistenceConstants.VALIDATE_PARAMETER);
		Query query = new ValidateParameterFilterQuery(loyaltyProgrammeVO,
				baseQry);
		query.setParameter(1, loyaltyProgrammeVO.getCompanyCode());
		Collection<LoyaltyProgrammeVO> loyaltyProgrammeVOs = query
				.getResultList(new ValidateParameterMapper());
		log.exiting("CustomerMgmntDefaults Sql DAO", "validateParameter");
		return loyaltyProgrammeVOs;
	}

	/*
	 * ValidateParameterMapper.java Created on Dec 19, 2005
	 * 
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 * 
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	/**
	 * 
	 */
	private class ValidateParameterMapper implements Mapper<LoyaltyProgrammeVO> {
		/**
		 * @param resultSet
		 * @return LoyaltyProgrammeVO
		 * @throws SQLException
		 */
		public LoyaltyProgrammeVO map(ResultSet resultSet) throws SQLException {
			log.entering("ValidateParameterMapper", "map");
			LoyaltyProgrammeVO loyaltyProgrammeVO = new LoyaltyProgrammeVO();
			loyaltyProgrammeVO.setLoyaltyProgrammeCode(resultSet
					.getString("LTYPRGCOD"));
			Date fromDate = resultSet.getDate("FRMDAT");
			if (fromDate != null) {
				loyaltyProgrammeVO.setFromDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, fromDate));
			}
			Date toDate = resultSet.getDate("TOODAT");
			if (toDate != null) {
				loyaltyProgrammeVO.setToDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, toDate));
			}
			return loyaltyProgrammeVO;
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupLoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Collection<CustomerGroupLoyaltyProgrammeVO> listGroupLoyaltyPgm(
			String companyCode, String groupCode) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "listGroupLoyaltyPgm");
		List<CustomerGroupLoyaltyProgrammeVO> groupLoyaltyProgrammeVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.LIST_GROUP_LOYALTY_PGM);
		query.setParameter(1, companyCode);
		if (groupCode != null && groupCode.trim().length() > 0) {
			query.append("AND CUSGRPLTYPRG.GRPCOD = ? ");
			query.setParameter(2, groupCode);
		}

		return query.getResultList(new CustomerGroupLoyaltyPgmMapper());

	}

	/**
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public Collection<String> checkCustomerAttached(
			LoyaltyProgrammeVO loyaltyProgrammeVO) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "checkCustomerAttached");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.CHECK_CUSTOMER_ATTACHED);
		query.setParameter(1, loyaltyProgrammeVO.getCompanyCode());
		query.setParameter(2, loyaltyProgrammeVO.getLoyaltyProgrammeCode());
		query.setParameter(3, loyaltyProgrammeVO.getFromDate().toCalendar());
		query.setParameter(4, loyaltyProgrammeVO.getToDate().toCalendar());
		query.setParameter(5, loyaltyProgrammeVO.getFromDate().toCalendar());
		query.setParameter(6, loyaltyProgrammeVO.getToDate().toCalendar());
		query.setParameter(7, loyaltyProgrammeVO.getCompanyCode());
		query.setParameter(8, loyaltyProgrammeVO.getLoyaltyProgrammeCode());
		query.setParameter(9, loyaltyProgrammeVO.getFromDate().toCalendar());
		query.setParameter(10, loyaltyProgrammeVO.getToDate().toCalendar());
		query.setParameter(11, loyaltyProgrammeVO.getFromDate().toCalendar());
		query.setParameter(12, loyaltyProgrammeVO.getToDate().toCalendar());
		Collection<String> codes = query
				.getResultList(new CheckCustomerAttatchedMapper());
		log.exiting("CustomerMgmntDefaults Sql DAO", "checkCustomerAttached");
		return codes;
	}

	/*
	 * CheckCustomerAttatchedMapper.java Created on Dec 19, 2005
	 * 
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 * 
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	/**
	 * 
	 */
	private class CheckCustomerAttatchedMapper implements Mapper<String> {
		/**
		 * @param resultSet
		 * @return String
		 * @throws SQLException
		 */
		public String map(ResultSet resultSet) throws SQLException {
			log.entering("CheckCustomerAttatchedMapper", "map");
			return resultSet.getString("CODE");
		}
	}

	/**
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public Collection<String> checkLoyaltyProgrammeAttached(
			LoyaltyProgrammeVO loyaltyProgrammeVO) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO",
				"checkLoyaltyProgrammeAttached");
		Query query = getQueryManager()
				.createNamedNativeQuery(
						CustomerMgmntPersistenceConstants.CHECK_LOYALTY_PROGRAMME_ATTACHED);
		query.setParameter(1, loyaltyProgrammeVO.getCompanyCode());
		query.setParameter(2, loyaltyProgrammeVO.getLoyaltyProgrammeCode());
		query.setParameter(3, loyaltyProgrammeVO.getCompanyCode());
		query.setParameter(4, loyaltyProgrammeVO.getLoyaltyProgrammeCode());
		// reusing CheckCustomerAttatchedMapper
		Collection<String> codes = query
				.getResultList(new CheckCustomerAttatchedMapper());
		log.exiting("CustomerMgmntDefaults Sql DAO",
				"checkLoyaltyProgrammeAttached");
		return codes;
	}

	/**
	 * Lists All Loyalty Programmes based on Filter
	 * 
	 * @author A-1883
	 * @param loyaltyProgrammeFilterVO
	 * @param pageNumber
	 * @return Page<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Page<LoyaltyProgrammeVO> listAllLoyaltyProgrammes(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO, int pageNumber)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO","listAllLoyaltyProgrammes"+loyaltyProgrammeFilterVO);
		LocalDate fromDate = loyaltyProgrammeFilterVO.getFromDate();
		LocalDate toDate = loyaltyProgrammeFilterVO.getToDate();
		String loyaltyProgramCode = loyaltyProgrammeFilterVO.getLoyaltyProgrammeCode();
		String loyaltyDescription = loyaltyProgrammeFilterVO.getLoyaltyDescription();
		int index = 1;
		String query = getQueryManager().getNamedNativeQueryString(
				   CustomerMgmntPersistenceConstants.LIST_ALL_LOYALTY_PROGRAMMES);
		
		//Added by A-5220 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(CustomerMgmntPersistenceConstants.
				CUSTOMERMANAGEMENT_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(query);
		//Added by A-5220 for ICRD-32647 ends

		/*Query query =  getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.LIST_ALL_LOYALTY_PROGRAMMES);*/
		   
		 PageableNativeQuery<LoyaltyProgrammeVO> pgNativeQuery = 
				new PageableNativeQuery<LoyaltyProgrammeVO>(0,
						rankQuery.toString(),new LoyaltyProgrammesMapper());

		
		 pgNativeQuery.setParameter(1,loyaltyProgrammeFilterVO.getCompanyCode());
		
		if(loyaltyProgramCode != null && loyaltyProgramCode.trim().length() > 0){
			int l = loyaltyProgramCode.length();
			if("*".equals(loyaltyProgramCode.substring(l-1,l))){
				log.log(Log.INFO, "%%%%%%loyaltyProgramCode",
						loyaltyProgramCode);
				String code = loyaltyProgramCode.replace('*','%');
				log.log(Log.INFO, "%%%%%%code ", code);
				pgNativeQuery.append(" AND ltyprgcod LIKE ? ");
				pgNativeQuery.setParameter(++index,code);
			}else{
				pgNativeQuery.append(" AND ltyprgcod = ? ");
				pgNativeQuery.setParameter(++index,loyaltyProgramCode);
			}
		}
		if(toDate != null ){
			pgNativeQuery.append(" AND frmdat <= ? ");
			pgNativeQuery.setParameter(++index,toDate.toCalendar());
		}
		if(fromDate != null){
			pgNativeQuery.append(" AND toodat >= ? ");
			pgNativeQuery.setParameter(++index,fromDate.toCalendar());
		}
		if(loyaltyDescription != null 
				&& loyaltyDescription.trim().length() > 0){
			int l = loyaltyDescription.length();
			if("*".equals(loyaltyDescription.substring(l-1,l))){
				loyaltyDescription = loyaltyDescription.replace('*','%');
				pgNativeQuery.append(" AND ltyprgdes LIKE ? ");
				pgNativeQuery.setParameter(++index,loyaltyDescription);
			}else{
				pgNativeQuery.append(" AND ltyprgdes = ? ");
				pgNativeQuery.setParameter(++index,loyaltyDescription);
			}
		}
		/*PageableQuery<LoyaltyProgrammeVO> pageableQuery = new 
					PageableQuery<LoyaltyProgrammeVO>(query,
							new LoyaltyProgrammesMapper());*/
		log.exiting("CustomerMgmntDefaults Sql DAO","listAllLoyaltyProgrammes");
		//Added by A-5220 for ICRD-32647 starts
		pgNativeQuery.append(CustomerMgmntPersistenceConstants.
				CUSTOMERMANAGEMENT_DEFAULTS_SUFFIX_QUERY);
		//Added by A-5220 for ICRD-32647 ends
		return pgNativeQuery.getPage(pageNumber);
	}

	/**
	 * @author A-1883
	 * @param airWayBillLoyaltyProgramVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Collection<LoyaltyProgrammeVO> findAttachedLoyaltyProgrammes(
			AirWayBillLoyaltyProgramVO airWayBillLoyaltyProgramVO)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO",
				"findAttachedLoyaltyProgrammes");
		String loyaltyGroupCode = findCustomerGorup(airWayBillLoyaltyProgramVO);
		Query query = getQueryManager()
				.createNamedNativeQuery(
						CustomerMgmntPersistenceConstants.FIND_ATTACHED_LOYALTY_PROGRAMMES);
		query.setParameter(1, airWayBillLoyaltyProgramVO.getCompanyCode());
		query.setParameter(2, airWayBillLoyaltyProgramVO.getCustomerCode());
		query.setParameter(3, airWayBillLoyaltyProgramVO.getAwbDate()
				.toCalendar());
		if (loyaltyGroupCode != null && loyaltyGroupCode.trim().length() > 0) {
			log.log(Log.FINE, "Loyalty Group Code : ", loyaltyGroupCode);
			query.append(" UNION ALL SELECT cmpcod,ltyprgcod ");
			query.append(" FROM CUSGRPLTYPRG ");
			query.append(" WHERE cmpcod = ? AND grpcod = ? AND ");
			query.append(" ? BETWEEN frmdat AND toodat ");
			query.setParameter(4, airWayBillLoyaltyProgramVO.getCompanyCode());
			query.setParameter(5, loyaltyGroupCode);
			query.setParameter(6, airWayBillLoyaltyProgramVO.getAwbDate()
					.toCalendar());
		}
		return query.getResultList(new AttachedLoyaltyProgrammesMapper());
	}

	/*
	 * AttachedLoyaltyProgrammesMapper.java Created on Dec 19, 2005
	 * 
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 * 
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	/**
	  * 
	  */
	private class AttachedLoyaltyProgrammesMapper implements
			Mapper<LoyaltyProgrammeVO> {
		/**
		 * @param resultSet
		 * @return LoyaltyProgrammeVO
		 * @throws SQLException
		 */
		public LoyaltyProgrammeVO map(ResultSet resultSet) throws SQLException {
			log.entering("AttachedLoyaltyProgrammesMapper", "map");
			LoyaltyProgrammeVO loyaltyProgrammeVO = new LoyaltyProgrammeVO();
			loyaltyProgrammeVO.setCompanyCode(resultSet.getString("CMPCOD"));
			loyaltyProgrammeVO.setLoyaltyProgrammeCode(resultSet
					.getString("LTYPRGCOD"));
			log.exiting("AttachedLoyaltyProgrammesMapper", "map");
			return loyaltyProgrammeVO;
		}
	}

	/**
	 * @author A-1883
	 * @param airWayBillLoyaltyProgramVO
	 * @return String
	 * @throws SystemException
	 */
	private String findCustomerGorup(
			AirWayBillLoyaltyProgramVO airWayBillLoyaltyProgramVO)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "findCustomerGorup");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.FIND_CUSTOMER_GROUP);
		query.setParameter(1, airWayBillLoyaltyProgramVO.getCompanyCode());
		query.setParameter(2, airWayBillLoyaltyProgramVO.getCustomerCode());
		return query.getSingleResult(new FindCustomerGorupMapper());
	}

	/*
	 * FindCustomerGorupMapper.java Created on Dec 19, 2005
	 * 
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 * 
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	/****
	  * 
	  */
	private class FindCustomerGorupMapper implements Mapper<String> {
		/**
		 * @param resultSet
		 * @return String
		 * @throws SQLException
		 */
		public String map(ResultSet resultSet) throws SQLException {
			log.entering("FindCustomerGorupMapper", "map");
			return resultSet.getString("CUSGRP");
		}
	}

	/**
	 * @author A-1883
	 * @param listPointsAccumulatedFilterVO
	 * @param pageNumber
	 * @return Collection<ListCustomerPointsVO>
	 * @throws SystemException
	 */
	public Page<ListCustomerPointsVO> listLoyaltyPointsForAwb(
			ListPointsAccumulatedFilterVO listPointsAccumulatedFilterVO,
			int pageNumber) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "listLoyaltyPointsForAwb");
         //Added by : A-5175 on 24-Oct-2012	 for icrd-22065 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(CustomerMgmntPersistenceConstants.CUSTOMERMANAGEMENT_DEFAULTS_DENSE_RANK_QUERY);
		rankQuery.append("RESULT_TABLE.MSTAWBNUM");
		rankQuery.append(") RANK ");
		rankQuery.append("FROM ( ");
		String nativeQuery = getQueryManager().getNamedNativeQueryString(
				CustomerMgmntPersistenceConstants.LIST_LOYALTY_POINTS_FOR_AWB);
		String baseQuery = rankQuery.append(nativeQuery).toString();
		// Query query = new LoyaltyPointsForAwbFilterQuery(
		// listPointsAccumulatedFilterVO, baseQuery);
		PageableNativeQuery<ListCustomerPointsVO> query = new LoyaltyPointsForAwbFilterQuery(
				listPointsAccumulatedFilterVO.getTotalRecordCount(),
				new LoyaltyPointsForAwbMultiMapper(),
				listPointsAccumulatedFilterVO, baseQuery);
		query.setParameter(1, listPointsAccumulatedFilterVO.getCompanyCode());
		query.append(CustomerMgmntPersistenceConstants.CUSTOMERMANAGEMENT_DEFAULTS_SUFFIX_QUERY);
		//PageableQuery<ListCustomerPointsVO> pgqry = new PageableQuery<ListCustomerPointsVO>(
		//		query, new LoyaltyPointsForAwbMultiMapper());
		log.exiting("CustomerMgmntDefaults Sql DAO", "listLoyaltyPointsForAwb");
		//return pgqry.getPageAbsolute(pageNumber,
		//		listPointsAccumulatedFilterVO.getAbsoluteIndex());
		return query.getPage(listPointsAccumulatedFilterVO.getPageNumber());
		
	    //Added by : A-5175 on 24-Oct-2012	 for icrd-22065 ends
	}

	/**
	 * @param customerGroupVO
	 * @return String
	 * @throws SystemException
	 */
	/*
	 * public String checkForCustomer(CustomerLoyaltyGroupVO customerGroupVO)
	 * throws SystemException{
	 * log.entering("CustomerMgmntDefaults Sql DAO","checkForCustomer"); Query
	 * query = getQueryManager().createNamedNativeQuery(
	 * CustomerMgmntPersistenceConstants.CHECK_FOR_CUSTOMER);
	 * query.setParameter(1,customerGroupVO.getCompanyCode());
	 * query.setParameter(2,customerGroupVO.getGroupCode()); String
	 * customerGroup = query.getSingleResult( getStringMapper("CUSGRP")); return
	 * customerGroup;
	 * 
	 * }
	 */

	/**
	 * 
	 * @param companyCode
	 * @param customerCode
	 * @param groupCode
	 * @return Collection<AttachLoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Collection<AttachLoyaltyProgrammeVO> listLoyaltyPgmToCustomers(
			String companyCode, String customerCode, String groupCode)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "checkForCustomer");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.LIST_LOYALTYPGM_CUSTOMERS);
		query.setParameter(1, companyCode);
		query.setParameter(2, customerCode);

		if (groupCode != null && groupCode.trim().length() > 0) {
			query.append("UNION ");

			query.append("SELECT DISTINCT  CUSGRPLTYPRG.CMPCOD AS CMPCOD,'Y' AS GROUPFLAG,CUSGRPLTYPRG.FRMDAT AS FRMDAT,");
			query.append(" CUSGRPLTYPRG.TOODAT AS TOODAT ,CUSGRPLTYPRG.LTYPRGCOD AS LTYPRGCOD,");
			query.append("CUSLTYPRGMST.FRMDAT AS LTYFRMDAT,CUSLTYPRGMST.TOODAT AS  LTYTOODAT,'' AS SEQNUM,'' AS CUSCOD");
			query.append(" FROM CUSGRPLTYPRG,CUSLTYPRGMST,SHRCUSMST");
			query.append("  WHERE SHRCUSMST.CMPCOD = CUSGRPLTYPRG.CMPCOD");
			query.append("  AND SHRCUSMST.CUSGRP = CUSGRPLTYPRG.GRPCOD");

			query.append("	  AND CUSGRPLTYPRG.CMPCOD =CUSLTYPRGMST.CMPCOD");
			query.append("	  AND CUSGRPLTYPRG.LTYPRGCOD = CUSLTYPRGMST.LTYPRGCOD");

			query.append("	  AND SHRCUSMST.CMPCOD =? ");
			query.append("	  AND SHRCUSMST.CUSGRP =? ");

			query.setParameter(3, companyCode);
			query.setParameter(4, groupCode);
		}

		return query.getResultList(new LoyaltyPgmToCustomersMapper());

	}

	/**
	 * @author A-1883
	 * @param companyCode
	 * @return Collection<ParameterDescriptionVO>
	 * @throws SystemException
	 */
	public Collection<ParameterDescriptionVO> findParameterDetails(
			String companyCode) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "findParameterDetails");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.FIND_PARAMETER_DETAILS);
		query.setParameter(1, companyCode);
		return query.getResultList(new ParameterDescriptionMapper());
	}

	/*
	 * ParameterDescriptionMapper.java Created on Dec 19, 2005
	 * 
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 * 
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	/**
		 * 
		 */
	private class ParameterDescriptionMapper implements
			Mapper<ParameterDescriptionVO> {
		/**
		 * @param resultSet
		 * @return ParameterDescriptionVO
		 * @throws SQLException
		 */
		public ParameterDescriptionVO map(ResultSet resultSet)
				throws SQLException {
			log.entering("ParameterDescriptionMapper", "map");
			ParameterDescriptionVO parameterDescriptionVO = new ParameterDescriptionVO();
			parameterDescriptionVO.setParameter(resultSet.getString("PRM"));
			parameterDescriptionVO
					.setDefaultUnit(resultSet.getString("DEFUNT"));
			parameterDescriptionVO.setCheckNumberField(resultSet
					.getString("CHKNUMFLD"));
			parameterDescriptionVO.setParameterDesc(resultSet
					.getString("PRMDES"));
			log.exiting("ParameterDescriptionMapper", "map");
			return parameterDescriptionVO;
		}
	}

	/**
	 * 
	 * @param filterVO
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public Collection<String> listPointAccumulated(
			AirWayBillLoyaltyProgramFilterVO filterVO) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "listPointAccumulated");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.LIST_POINTS_ACCUMULATED);

		query.setParameter(1, filterVO.getCompanyCode());
		query.setParameter(2, filterVO.getCustomerCode());
		int index = 2;
		if (filterVO.getMasterAwbNumber() != null
				&& filterVO.getMasterAwbNumber().trim().length() > 0) {
			query.append("AND MSTAWBNUM= ?");
			query.setParameter(++index, filterVO.getMasterAwbNumber());
		}
		if (filterVO.getAwbNumber() != null
				&& filterVO.getAwbNumber().trim().length() > 0) {
			query.append("AND AWBNUM= ?");
			query.setParameter(++index, filterVO.getAwbNumber());
		}
		if (filterVO.getOwnerId() > 0) {
			query.append("AND DOCOWRIDR = ?");
			query.setParameter(++index, filterVO.getOwnerId());
		}
		if (filterVO.getDuplicateNumber() > 0) {
			query.append("AND DUPNUM = ?");
			query.setParameter(++index, filterVO.getDuplicateNumber());
		}
		if (filterVO.getSequenceNumber() > 0) {
			query.append("AND SEQNUM = ?");
			query.setParameter(++index, filterVO.getSequenceNumber());
		}

		return query.getResultList(new PointAccumulatedMapper());

	}

	/*
	 * PointAccumulatedMapper.java Created on Dec 19, 2005
	 * 
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 * 
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	/**
		  * 
		  */
	private class PointAccumulatedMapper implements Mapper<String> {
		/**
		 * @param resultSet
		 * @return String
		 * @throws SQLException
		 */
		public String map(ResultSet resultSet) throws SQLException {
			log.entering("PointAccumulatedMapper", "map");
			return resultSet.getString("LTYPRGCOD");
		}
	}

	/**
	 * 
	 * @param filterVO
	 * @return Collection<AirWayBillLoyaltyProgramVO>
	 * @throws SystemException
	 */
	public Collection<AirWayBillLoyaltyProgramVO> showPoints(
			AirWayBillLoyaltyProgramFilterVO filterVO) throws SystemException {
		log.entering("ParameterDescriptionMapper", "listPointAccumulated");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.SHOW_POINTS);

		query.setParameter(1, filterVO.getCompanyCode());
		query.setParameter(2, filterVO.getCustomerCode());
		int index = 2;
		if (filterVO.getMasterAwbNumber() != null
				&& filterVO.getMasterAwbNumber().trim().length() > 0) {
			query.append("AND MSTAWBNUM= ?");
			query.setParameter(++index, filterVO.getMasterAwbNumber());
		}
		if (filterVO.getAwbNumber() != null
				&& filterVO.getAwbNumber().trim().length() > 0) {
			query.append("AND AWBNUM= ?");
			query.setParameter(++index, filterVO.getAwbNumber());
		}
		if (filterVO.getOwnerId() > 0) {
			query.append("AND DOCOWRIDR = ?");
			query.setParameter(++index, filterVO.getOwnerId());
		}
		if (filterVO.getDuplicateNumber() > 0) {
			query.append("AND DUPNUM = ?");
			query.setParameter(++index, filterVO.getDuplicateNumber());
		}
		if (filterVO.getSequenceNumber() > 0) {
			query.append("AND SEQNUM = ?");
			query.setParameter(++index, filterVO.getSequenceNumber());
		}
		if (filterVO.getLoyaltyProgramee() != null
				&& filterVO.getLoyaltyProgramee().trim().length() > 0) {
			query.append("AND LTYPRGCOD = ?");
			query.setParameter(++index, filterVO.getLoyaltyProgramee());

		}
		return query.getResultList(new LoyaltyPointsMapper());
	}

	/**
	 * Finds unit of pameter (Distance and Revenue)
	 * 
	 * @author A-1883
	 * @param companyCode
	 * @param parameterCode
	 * @return String
	 * @throws SystemException
	 */
	public String findParameterUnit(String companyCode, String parameterCode)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "findParameterUnit");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.FIND_PARAMETER_UNIT);
		query.setParameter(1, companyCode);
		query.setParameter(2, parameterCode);
		return query.getSingleResult(new ParameterUnitMapper());
	}

	/*
	 * ParameterUnitMapper.java Created on Dec 19, 2005
	 * 
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 * 
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	/**
		  * 
		  */
	private class ParameterUnitMapper implements Mapper<String> {
		/**
		 * @param resultSet
		 * @return String
		 * @throws SQLException
		 */
		public String map(ResultSet resultSet) throws SQLException {
			log.entering("ParameterUnitMapper", "map");
			return resultSet.getString("DEFUNTCOD");
		}
	}

	/**
	 * 
	 * @param programPointVOs
	 * @return double
	 * @throws SystemException
	 */
	public double findEntryPoints(
			Collection<AttachLoyaltyProgrammeVO> programPointVOs)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "findEntryPoints");
		String companyCode = null;
		Collection<String> loyaltyPGMs = new ArrayList<String>();

		int iCounter = 0;
		if (programPointVOs != null && programPointVOs.size() > 0) {
			for (AttachLoyaltyProgrammeVO pgmVO : programPointVOs) {
				companyCode = pgmVO.getCompanyCode();
				loyaltyPGMs.add(pgmVO.getLoyaltyProgrammeCode());
			}
		}

		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.FIND_ENTRY_POINTS);
		query.setParameter(1, companyCode);
		int index = 1;
		if (loyaltyPGMs != null && loyaltyPGMs.size() > 0) {

			for (String loyaltyProgrammeCode : loyaltyPGMs) {
				if (iCounter == 0) {
					query.append(" AND  CUSLTYPRGMST.LTYPRGCOD IN ( ? ");
					query.setParameter(++index, loyaltyProgrammeCode);
				} else {
					query.append(" , ? ");
					query.setParameter(++index, loyaltyProgrammeCode);
				}
				iCounter = 1;
			}
			if (iCounter == 1) {
				query.append(" ) ");
			}
		}

		return query.getSingleResult(getLongMapper("POINTS"));
	}

	/**
	 * Returns Loyalty programme expiry details
	 * 
	 * @author a-1883
	 * @param redemptionValidationVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Collection<LoyaltyProgrammeVO> validateCustomerForPointsRedemption(
			RedemptionValidationVO redemptionValidationVO)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO",
				"validateCustomerForPointsRedemption");
		Query query = getQueryManager()
				.createNamedNativeQuery(
						CustomerMgmntPersistenceConstants.VALIDATE_CUSTOMER_FOR_PTS_REDEMPTION);
		query.setParameter(1, redemptionValidationVO.getCompanyCode());
		query.setParameter(2, redemptionValidationVO.getCustomerCode());
		query.setParameter(3, redemptionValidationVO.getCompanyCode());
		query.setParameter(4, redemptionValidationVO.getCustomerCode());
		return query.getResultList(new ValidationForPtsRedemptionMapper());
	}

	/**
	 * 
	 * @param programmeVO
	 * @return
	 * @throws SystemException
	 */
	public String checkForCustomerLoyalty(
			CustomerGroupLoyaltyProgrammeVO programmeVO) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "checkForCustomerLoyalty");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.CHECK_FOR_CUSTOMERLOYALTY);
		query.setParameter(1, programmeVO.getCompanyCode());
		query.setParameter(2, programmeVO.getGroupCode());
		query.setParameter(3, programmeVO.getLoyaltyProgramCode());

		query.setParameter(4, programmeVO.getFromDate().toCalendar());
		query.setParameter(5, programmeVO.getToDate().toCalendar());

		query.setParameter(6, programmeVO.getFromDate().toCalendar());
		query.setParameter(7, programmeVO.getToDate().toCalendar());

		query.setParameter(8, programmeVO.getFromDate().toCalendar());
		query.setParameter(9, programmeVO.getToDate().toCalendar());

		return query.getSingleResult(getStringMapper("RESULT"));

	}

	/**
	 * @author a-1883
	 * @param companyCode
	 * @param currentDate
	 * @param pageNumber
	 * @return Page<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Page<LoyaltyProgrammeVO> runningLoyaltyProgrammeLOV(
			String companyCode, LocalDate currentDate, int pageNumber)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO",
				"runningLoyaltyProgrammeLOV");
		//Modified by A-5220 for ICRD-32647 starts
		String query = getQueryManager()
				.getNamedNativeQueryString(
						CustomerMgmntPersistenceConstants.
						RUNNING_LOYALTY_PROGRAMME_LOV);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(CustomerMgmntPersistenceConstants.
				CUSTOMERMANAGEMENT_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(query);
		PageableNativeQuery<LoyaltyProgrammeVO> pgqry =
			new PageableNativeQuery<LoyaltyProgrammeVO>(0,
					rankQuery.toString(),
					new RunningLoyaltyProgrammeMapper());
		
		//Modified by A-5220 for ICRD-32647 ends
		pgqry.setParameter(1, companyCode);
		pgqry.setParameter(2, currentDate.toCalendar());
		pgqry.setParameter(3, currentDate.toCalendar());
		//Modified by A-5220 for ICRD-32647 starts
		pgqry.append(CustomerMgmntPersistenceConstants.
				CUSTOMERMANAGEMENT_DEFAULTS_SUFFIX_QUERY);
		//Modified by A-5220 for ICRD-32647 ends
		log.exiting("CustomerMgmntDefaults Sql DAO",
				"runningLoyaltyProgrammeLOV");
		return pgqry.getPage(pageNumber);
	}

	/**
	 * 
	 * @param serviceVO
	 * @return
	 * @throws SystemException
	 */
	public String checkForService(CustomerServicesVO serviceVO)
			throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "checkForService");
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.CHECK_FOR_SERVICES);
		query.setParameter(1, serviceVO.getCompanyCode());
		query.setParameter(2, serviceVO.getServiceCode());
		return query.getSingleResult(getStringMapper("RESULT"));
	}

	/**
	 * @author a-1883
	 * @param companyCode
	 * @param serviceCode
	 * 
	 * @return CustomerServicesVO
	 * @throws SystemException
	 */
	public CustomerServicesVO listCustomerServices(String companyCode,
			String serviceCode) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "listCustomerServices");
		List<CustomerServicesVO> servicesVOs = new ArrayList<CustomerServicesVO>();
		CustomerServicesVO serviceVO = null;
		Query query = getQueryManager().createNamedNativeQuery(
				CustomerMgmntPersistenceConstants.LIST_CUSTOMER_SERVICES);
		query.setParameter(1, companyCode);
		query.setParameter(2, serviceCode);
		servicesVOs = query.getResultList(new CustomerServicesMapper());
		if (servicesVOs != null && servicesVOs.size() > 0) {
			serviceVO = servicesVOs.get(0);
		}
		return serviceVO;
	}

	/**
	 * 
	 * @param companyCode
	 * @param serviceCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<CustomerServicesVO> customerServicesLOV(String companyCode,
			String serviceCode, int pageNumber) throws SystemException {
		log.entering("CustomerMgmntDefaults Sql DAO", "customerServicesLOV");
		//Modified by A-5220 for ICRD-32647 starts
		String query = getQueryManager().getNamedNativeQueryString(
				CustomerMgmntPersistenceConstants.CUSTOMER_SERVICES_LOV);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(CustomerMgmntPersistenceConstants.
				CUSTOMERMANAGEMENT_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(query);
		PageableNativeQuery<CustomerServicesVO> pgqry = 
			new PageableNativeQuery<CustomerServicesVO>(0, rankQuery.toString(),
					new CustomerServicesMapper());
		//Modified by A-5220 for ICRD-32647 ends
		pgqry.setParameter(1, companyCode);
		if (serviceCode != null && serviceCode.trim().length() > 0) {
			pgqry.append("AND SRVCOD =?");
			pgqry.setParameter(2, serviceCode);
		}
		//Modified by A-5220 for ICRD-32647 starts
		pgqry.append(CustomerMgmntPersistenceConstants.
				CUSTOMERMANAGEMENT_DEFAULTS_SUFFIX_QUERY);
		//Modified by A-5220 for ICRD-32647 ends
		return pgqry.getPage(pageNumber);
	}

	/*
	 * CustomerServicesMapper.java Created on Dec 19, 2005
	 * 
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 * 
	 * This software is the proprietary information of IBS Software Services (P)
	 * Ltd. Use is subject to license terms.
	 */
	/**
	 * 
	 */
	private class CustomerServicesMapper implements Mapper<CustomerServicesVO> {
		/**
		 * @param resultSet
		 * @return String
		 * @throws SQLException
		 */
		public CustomerServicesVO map(ResultSet resultSet) throws SQLException {
			log.entering("ParameterUnitMapper", "map");
			CustomerServicesVO serviceVO = new CustomerServicesVO();
			serviceVO.setCompanyCode(resultSet.getString("CMPCOD"));
			serviceVO.setServiceCode(resultSet.getString("SRVCOD"));
			serviceVO.setServiceDescription(resultSet.getString("SRVDES"));
			serviceVO.setKeyContactFlag(resultSet.getString("KEYCNTFLG"));
			serviceVO.setLastUpdatedUser(resultSet.getString("LSTUPDUSR"));
			serviceVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, resultSet.getTimestamp("LSTUPDTIM")));
			Timestamp time = resultSet.getTimestamp("LSTUPDTIM");
			if (time != null) {
				serviceVO.setLastUpdatedTime(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, time));
			}
			serviceVO.setPoints(resultSet.getDouble("PNT"));

			return serviceVO;
		}
	}

}
