/*
 * UserLovForPaginationFilterQuery.java Created on Mar 26, 2013
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.persistence.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.UserLovFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserLovVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-5220
 * This file is newly added because two methods from sql dao are using the same
 * filter query. findUserDetailsLov method needs pagination whereas findUsers
 * doesn't need the pagination. findUserDetailsLov method uses this filter query
 */
//Modified by A-5220 for ICRD-32647 starts
public class UserLovForPaginationFilterQuery extends
			PageableNativeQuery<UserLovVO>  {
	
	private UserLovFilterVO userLovFilterVO;
	private String baseQuery;
	private String appendQuery;

	/**
	 * constructor
	 * @param totalRecordCount
	 * @param mapper
	 * @param userLovFilterVO
	 * @param baseQuery
	 * @param appendQuery
	 */
	
	public UserLovForPaginationFilterQuery ( int totalRecordCount,
			Mapper<UserLovVO> mapper,
			UserLovFilterVO userLovFilterVO, String baseQuery, String appendQuery ) {
		super(totalRecordCount, mapper, PersistenceController.getEntityManager().currentSession());
		this.userLovFilterVO = userLovFilterVO;
		this.baseQuery = baseQuery;
		this.appendQuery = appendQuery;
		
	}
	//Modified by A-5220 for ICRD-32647 ends
	
	/**
	 * returns the native query
	 * @return String
	 */
	public String getNativeQuery() {
		String companyCode = userLovFilterVO.getCompanyCode();
		String roleCode = userLovFilterVO.getRolegroupCode();
		String userCode = userLovFilterVO.getUserCode();
		String userFirstName = userLovFilterVO.getUserFirstName();
		String userMiddleName = userLovFilterVO.getUserMiddleName();
		String stationCode = userLovFilterVO.getStationCode();
		StringBuilder sbul = new StringBuilder().append(baseQuery);
		int index = 0;
		this.setParameter(++index, companyCode);
		
		if (roleCode != null && roleCode.trim().length() > 0) {
            roleCode = roleCode.replace('*', '%');
            if (!roleCode.contains("*") && !roleCode.contains("%")) {
                roleCode = roleCode.concat("%");
            }
			sbul.append("AND MST.DEFROLGRP LIKE '");
			sbul.append(roleCode);
			sbul.append("'");
		}
		
		if (userLovFilterVO.getRoleGroupCodes() != null && 
				userLovFilterVO.getRoleGroupCodes().size() > 0) {
			sbul.append("AND ( ");
			StringBuilder rolStr = new StringBuilder();
			
			for (String roleGroupCode : userLovFilterVO.getRoleGroupCodes()) {
				rolStr.append("MST.DEFROLGRP ='");
				rolStr.append(roleGroupCode);
				rolStr.append("' OR ");
			}
			sbul.append(rolStr.toString().substring(0, rolStr.length() - 4));
			sbul.append(" )");
		}
		
		if (userCode != null && userCode.trim().length() > 0) {

			if (userCode.contains(",")) {
				String[] userCodes = userCode.split(",");
				sbul.append("AND  MST.USRCOD  IN ");
				String groupNameQuery = getWhereClause(userCodes);
				sbul.append(groupNameQuery).append(")");
			} else {
				sbul.append(" AND MST.USRCOD LIKE '");
				userCode = userCode.replace('*', '%');
				
				if (!userCode.contains("*") && !userCode.contains("%")) {
					userCode = userCode.concat("%");
				}
				sbul.append(userCode);
				sbul.append("'");
			}
		}
		
		if (userFirstName != null && userFirstName.trim().length() > 0) {
			sbul.append(" AND MST.FSTNAM LIKE '");
			sbul.append(userFirstName.replace('*', '%'));
			sbul.append("'");
		}
		
		if (userMiddleName != null && userMiddleName.trim().length() > 0) {
			sbul.append(" AND MST.MDLNAM LIKE '");
			sbul.append(userMiddleName.replace('*', '%'));
			sbul.append("'");
		}
		
		if (stationCode != null && stationCode.trim().length() > 0) {
			sbul.append(" AND MST.DEFSTN ='");
			sbul.append(stationCode);
			sbul.append("'");
		}
		//Added by E-1307 for ICRD-201490
		if ( userLovFilterVO.getAirportCode() != null && 
				userLovFilterVO.getAirportCode().trim().length() > 0 ) {
			sbul.append(" AND MST.DEFARP LIKE '");
			sbul.append( userLovFilterVO.getAirportCode().replace('*','%'));
			sbul.append("'");
		}
		if(userLovFilterVO.isSalesRep()){
					sbul.append("AND MST.SALREPFLG = 'Y'");
		}
		sbul.append(" UNION ");
		sbul.append(appendQuery);
		this.setParameter(++index, companyCode);
		
		if (roleCode != null && roleCode.trim().length() > 0) {
			sbul.append(" AND ROL.ROLGRPCOD LIKE '");
			sbul.append(roleCode);
			sbul.append("'");
		}
		
		if (stationCode != null && stationCode.trim().length() > 0) {
			sbul.append(" AND MST.DEFSTN ='");
			sbul.append(stationCode);
			sbul.append("'");
		}
		//Added by E-1307 for ICRD-201490
		if ( userLovFilterVO.getAirportCode() != null && 
				userLovFilterVO.getAirportCode().trim().length() > 0 ) {
			sbul.append(" AND MST.DEFARP LIKE '");
			sbul.append( userLovFilterVO.getAirportCode().replace('*','%'));
			sbul.append("'");
		}
		
		if (userLovFilterVO.getRoleGroupCodes() != null && 
				userLovFilterVO.getRoleGroupCodes().size() > 0) {
			sbul.append("AND ( ");
			StringBuilder rolStr = new StringBuilder();
			
			for (String roleGroupCode : userLovFilterVO.getRoleGroupCodes()) {
				rolStr.append("ROL.ROLGRPCOD LIKE '");
				rolStr.append(roleGroupCode);
				rolStr.append("' OR ");
			}
			sbul.append(rolStr.toString().substring(0, rolStr.length() - 4));
			sbul.append(" )");
		}
		
		if (userCode != null && userCode.trim().length() > 0) {
			sbul.append(" AND ROL.USRCOD LIKE '");
			userCode = userCode.replace('*', '%');
			
			if (!userCode.contains("*") && !userCode.contains("%")) {
				userCode = userCode.concat("%");
			}
			sbul.append(userCode);
			sbul.append("'");
		}
		
		if (userFirstName != null && userFirstName.trim().length() > 0) {
			sbul.append(" AND MST.FSTNAM LIKE '");
			sbul.append(userFirstName.replace('*', '%'));
			sbul.append("'");
		}
		
		if (userMiddleName != null && userMiddleName.trim().length() > 0) {
			sbul.append(" AND MST.MDLNAM LIKE '");
			sbul.append(userMiddleName.replace('*', '%'));
			sbul.append("'");
		}
		if(userLovFilterVO.isSalesRep()){
			sbul.append("AND MST.SALREPFLG = 'Y'");
		}
		return sbul.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.xibase.server.framework.persistence.query.Query#getResultList()
	 */
	public void getResultList() {
	}

	private String getWhereClause(String[] sccCodes) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("('");

		for (String code : sccCodes) {
			buffer.append(code).append("','");
		}
		int len = buffer.length();
		return buffer.toString().substring(0, len - 3).trim() + "'";
	}

}