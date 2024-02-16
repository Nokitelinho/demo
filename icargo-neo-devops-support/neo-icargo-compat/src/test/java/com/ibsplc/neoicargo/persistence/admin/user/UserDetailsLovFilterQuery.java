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
 * @author A-1863
 *
 */
public class UserDetailsLovFilterQuery extends NativeQuery  {
	private UserLovFilterVO userLovFilterVO;
	private String baseQuery;
	/**
	 * constructor
	 * @param userLovFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public UserDetailsLovFilterQuery ( UserLovFilterVO userLovFilterVO, String baseQuery ) 
	throws SystemException {
		super(PersistenceController.getEntityManager().currentSession());
		this.userLovFilterVO = userLovFilterVO;
		this.baseQuery = baseQuery;
		
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
		
		StringBuilder sbul = new StringBuilder().append(baseQuery);
		int index = 0;
		this.setParameter(++index, companyCode);
		
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
		if ( userLovFilterVO.getAirportCode() != null && 
				userLovFilterVO.getAirportCode().trim().length() > 0 ) {
			sbul.append(" AND MST.DEFARP LIKE '");
			sbul.append( userLovFilterVO.getAirportCode().replace('*','%'));
			sbul.append("'");
		}
		if ( roleCode != null && roleCode.trim().length() > 0 ) {
			sbul.append(" AND ROL.ROLGRPCOD = '");
			sbul.append(roleCode);
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