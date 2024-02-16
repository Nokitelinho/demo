
/*
 * UserDetailsForRoleGroupsPaginationFilterQuery Created on 30 Jul, 2014
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.persistence.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.UserLovFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserLovVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/*
 * @author A-5497
 */
public class UserDetailsForRoleGroupsPaginationFilterQuery extends
			PageableNativeQuery<UserLovVO>  {
	
	private UserLovFilterVO userLovFilterVO;
	private String appendQuery1;
	private String appendQuery2;
	private String appendQuery3;
	/**
	 * constructor
	 * @param totalRecordCount
	 * @param mapper
	 * @param userLovFilterVO
	 * @throws SystemException
	 */
	public UserDetailsForRoleGroupsPaginationFilterQuery( 
			int totalRecordCount,Mapper<UserLovVO> mapper,
			UserLovFilterVO userLovFilterVO,String appendQuery1, 
			String appendQuery2,String appendQuery3) 
			throws SystemException {
		super(totalRecordCount, mapper, PersistenceController.getEntityManager().currentSession());
		this.userLovFilterVO = userLovFilterVO;
		this.appendQuery1 = appendQuery1;
		this.appendQuery2 = appendQuery2;
		this.appendQuery3 = appendQuery3;
	}
	
	/**
	 * returns the native query
	 * @return String
	 */
	public String getNativeQuery() {
		String companyCode = userLovFilterVO.getCompanyCode();
		String userCode =  userLovFilterVO.getUserCode(); 
		String userFirstName = userLovFilterVO.getUserFirstName();
		String stationCode = userLovFilterVO.getStationCode();
		
		StringBuilder sbul = new StringBuilder().append(appendQuery1);
		int index = 0;
		this.setParameter(++index, companyCode);
		
		if ( userLovFilterVO.getRoleGroupCodes() != null &&  
				userLovFilterVO.getRoleGroupCodes().size() > 0 ) {
			sbul.append("AND ( ");
			StringBuilder rolStr = new StringBuilder();
			for(String roleGroupCode : userLovFilterVO.getRoleGroupCodes()){
				rolStr.append("MST.DEFROLGRP ='");
				rolStr.append(roleGroupCode);
				rolStr.append("' OR ");
			}
			sbul.append(rolStr.toString().substring(0,rolStr.length()-4));
			sbul.append(" )");
		}
		if ( userCode != null && userCode.trim().length() > 0 ) {
			sbul.append(" AND MST.USRCOD LIKE '");
			userCode = userCode.replace('*','%');
			if(!userCode.contains("*") && !userCode.contains("%")) {
				userCode = userCode.concat("%");
			}
			sbul.append(userCode);
			sbul.append("'");
		}
		if ( userFirstName != null && userFirstName .trim().length() > 0 ) {
			sbul.append(" AND MST.FSTNAM LIKE '");
			sbul.append( userFirstName.replace('*','%'));
			sbul.append("'");
		}
		
		if ( stationCode != null && stationCode.trim().length() > 0 ) {
			sbul.append(" AND MST.DEFSTN ='");
			sbul.append(stationCode);
			sbul.append("'");
		}
		
		sbul.append(" UNION ");
		
		sbul.append(appendQuery2);
		this.setParameter(++index, companyCode);
		
		
		if ( userLovFilterVO.getRoleGroupCodes() != null &&
				userLovFilterVO.getRoleGroupCodes().size() > 0 ) {
			sbul.append("AND ( ");
			StringBuilder rolStr = new StringBuilder();
			for(String roleGroupCode : userLovFilterVO.getRoleGroupCodes()){
				rolStr.append("ROL.ROLGRPCOD ='");
				rolStr.append(roleGroupCode);
				rolStr.append("' OR ");
			}
			sbul.append(rolStr.toString().substring(0,rolStr.length()-4));
			sbul.append(" )");
		}
		if ( stationCode != null && stationCode.trim().length() > 0 ) {
			sbul.append(" AND ROL.STNCOD ='");
			sbul.append(stationCode);
			sbul.append("'");
		}
		
		if ( userCode != null && userCode.trim().length() > 0 ) {
			sbul.append(" AND ROL.USRCOD LIKE '");
			userCode = userCode.replace('*','%');
			if(!userCode.contains("*") && !userCode.contains("%")) {
				userCode = userCode.concat("%");
			}
			sbul.append(userCode);
			sbul.append("'");
		}
		if ( userFirstName != null && userFirstName .trim().length() > 0 ) {
			sbul.append(" AND MST.FSTNAM LIKE '");
			sbul.append( userFirstName.replace('*','%'));
			sbul.append("'");
		}
	
		
		
		sbul.append(" UNION ");
		
		sbul.append(appendQuery3);
		this.setParameter(++index, companyCode);
		
		if ( stationCode != null && stationCode.trim().length() > 0 ) {
			sbul.append(" AND ROL.STNCOD ='");
			sbul.append(stationCode);
			sbul.append("'");
		}
		if ( userLovFilterVO.getRoleGroupCodes() != null &&
				userLovFilterVO.getRoleGroupCodes().size() > 0 ) {
			sbul.append("AND ( ");
			StringBuilder rolStr = new StringBuilder();
			for(String roleGroupCode : userLovFilterVO.getRoleGroupCodes()){
				rolStr.append("ROL.DEFROLGRP ='");
				rolStr.append(roleGroupCode);
				rolStr.append("' OR ");
			}
			sbul.append(rolStr.toString().substring(0,rolStr.length()-4));
			sbul.append(" )");
		}
		if ( stationCode != null && stationCode.trim().length() > 0 ) {
			sbul.append(" AND ROL.STNCOD ='");
			sbul.append(stationCode);
			sbul.append("'");
		}
		
		if ( userCode != null && userCode.trim().length() > 0 ) {
			sbul.append(" AND ROL.USRCOD LIKE '");
			userCode = userCode.replace('*','%');
			if(!userCode.contains("*") && !userCode.contains("%")) {
				userCode = userCode.concat("%");
			}
			sbul.append(userCode);
			sbul.append("'");
		}
		if ( userFirstName != null && userFirstName .trim().length() > 0 ) {
			sbul.append(" AND MST.FSTNAM LIKE '");
			sbul.append( userFirstName.replace('*','%'));
			sbul.append("'");
		}
		
		return sbul.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.xibase.server.framework.persistence.query.Query#getResultList()
	 */
	public void getResultList() {
	}

}