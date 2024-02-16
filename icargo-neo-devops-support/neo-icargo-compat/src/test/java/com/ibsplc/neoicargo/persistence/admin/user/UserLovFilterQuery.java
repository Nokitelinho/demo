/*
 * UserLovFilterQuery.java Created on Mar 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.persistence.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.UserLovFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

/**
 * @author A-1872
 *
 */
public class UserLovFilterQuery extends NativeQuery  {
	
	private UserLovFilterVO userLovFilterVO;
	private String baseQuery;
	private String appendQuery;
	/**
	 * constructor
	 * @param userLovFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public UserLovFilterQuery ( UserLovFilterVO userLovFilterVO, String baseQuery, String appendQuery ) 
	throws SystemException {
		super(PersistenceController.getEntityManager().currentSession());
		this.userLovFilterVO = userLovFilterVO;
		this.baseQuery = baseQuery;
		this.appendQuery = appendQuery;
		
	}
	
	/**
	 * returns the native query
	 * @return String
	 */
	public String getNativeQuery() {
		String companyCode = userLovFilterVO.getCompanyCode();
		String roleCode = userLovFilterVO.getRolegroupCode(); 
		String userCode =  userLovFilterVO.getUserCode();
		String userFirstName = userLovFilterVO.getUserFirstName();
		String userMiddleName = userLovFilterVO.getUserMiddleName();
		String stationCode = userLovFilterVO.getStationCode();
		
		StringBuilder sbul = new StringBuilder().append(baseQuery);
		int index = 0;
		this.setParameter(++index, companyCode);
		
		if(userLovFilterVO.getAirportCode() != null &&
				userLovFilterVO.getAirportCode().trim().length() > 0){
			sbul.append(" AND mst.DEFARP = ? ");
			this.setParameter(++index, userLovFilterVO.getAirportCode());
		}
		
		if ( roleCode != null && roleCode.trim().length() > 0 ) {
			sbul.append("AND MST.DEFROLGRP ='");
			sbul.append(roleCode);
			sbul.append("'");
		}
		if ( userLovFilterVO.getRoleGroupCodes() != null &&  userLovFilterVO.getRoleGroupCodes().size() > 0 ) {
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
			sbul.append(userCode.replace('*','%'));
			sbul.append("'");
		}
		if ( userFirstName != null && userFirstName .trim().length() > 0 ) {
			sbul.append(" AND MST.FSTNAM LIKE '");
			sbul.append( userFirstName.replace('*','%'));
			sbul.append("'");
		}
		if ( userMiddleName != null && userMiddleName.trim().length() > 0 ) {
			sbul.append(" AND MST.MDLNAM LIKE '");
			sbul.append( userMiddleName.replace('*','%'));
			sbul.append("'");
		}
		if ( stationCode != null && stationCode.trim().length() > 0 ) {
			sbul.append(" AND MST.DEFSTN ='");
			sbul.append(stationCode);
			sbul.append("'");
}
		sbul.append(" UNION ");
		sbul.append(appendQuery);
		this.setParameter(++index, companyCode);
		if ( roleCode != null && roleCode.trim().length() > 0 ) {
			sbul.append(" AND ROL.ROLGRPCOD = '");
			sbul.append(roleCode);
			sbul.append("'");
		}
		if ( stationCode != null && stationCode.trim().length() > 0 ) {
			sbul.append(" AND MST.DEFSTN ='");
			sbul.append(stationCode);
			sbul.append("'");
}
		if ( userLovFilterVO.getRoleGroupCodes() != null &&  userLovFilterVO.getRoleGroupCodes().size() > 0 ) {
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
		if ( userCode != null && userCode.trim().length() > 0 ) {
			sbul.append(" AND ROL.USRCOD LIKE '");
			sbul.append(userCode.replace('*','%'));
			sbul.append("'");
		}
		if ( userFirstName != null && userFirstName .trim().length() > 0 ) {
			sbul.append(" AND MST.FSTNAM LIKE '");
			sbul.append( userFirstName.replace('*','%'));
			sbul.append("'");
		}
		if ( userMiddleName != null && userMiddleName.trim().length() > 0 ) {
			sbul.append(" AND MST.MDLNAM LIKE '");
			sbul.append( userMiddleName.replace('*','%'));
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
