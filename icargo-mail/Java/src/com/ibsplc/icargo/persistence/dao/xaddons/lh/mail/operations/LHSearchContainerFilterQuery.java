/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.xaddons.lh.mail.operations.LHSearchContainerFilterQuery.java
 *
 *	Created by	:	203168
 *	Created on	:	25-Oct-2022
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.xaddons.lh.mail.operations;


import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.ContainerMapperForDestination;
import com.ibsplc.icargo.persistence.dao.mail.operations.SearchContainerFilterQuery;
import com.ibsplc.icargo.persistence.dao.mail.operations.SearchContainerMultiMapper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * Java file : com.ibsplc.icargo.persistence.dao.xaddons.lh.mail.operations.LHSearchContainerFilterQuery
 * MailBookingFilterQuery.java Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : 203168 :
 * 25-Oct-2022 : Draft
 */
public class LHSearchContainerFilterQuery extends SearchContainerFilterQuery {
	
	
	public LHSearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
			ContainerMapperForDestination mapper, boolean isOracleDataSource, String fromScreen)
			throws SystemException {
		super(baseQuery, searchContainerFilterVO, mapper, isOracleDataSource, fromScreen);
		
	}
	
	public LHSearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
			ContainerMapperForDestination mapper, boolean isOracleDataSource)
			throws SystemException {
		super(baseQuery, searchContainerFilterVO, mapper, isOracleDataSource);
		
	}
 
	public LHSearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
			SearchContainerMultiMapper mapper,boolean isOracleDataSource, String fromScreen) throws SystemException {
		super(baseQuery,searchContainerFilterVO, mapper, isOracleDataSource, fromScreen);
	}
	
	public LHSearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
			SearchContainerMultiMapper mapper,boolean isOracleDataSource) throws SystemException {
		super(baseQuery,searchContainerFilterVO, mapper, isOracleDataSource);
	}

		
@Override	
protected void appendWhereClause(StringBuilder builder) {
	         if(searchContainerFilterVO.getHbaMarkedFlag()!=null && "Y".equals(searchContainerFilterVO.getHbaMarkedFlag())) {
					builder.append("AND HBADTL.ULDREFNUM IS NOT NULL ");
				} 
				if(searchContainerFilterVO.getHbaMarkedFlag()!=null && "N".equals(searchContainerFilterVO.getHbaMarkedFlag())) {
					builder.append("AND HBADTL.ULDREFNUM IS NULL ");
				} 
	
}

@Override	
protected void appendSelectQuery(StringBuilder query) {
	query.append(",  CASE WHEN MIN(HBADTL.ULDREFNUM) IS NULL THEN 'N'  ELSE 'Y' END HBAMARKED");
	
}

@Override	
protected void appendJoinQuery(StringBuilder query) {
	query.append("  LEFT OUTER JOIN MALFLTCONHBADTL HBADTL  ")
	.append(" ON ")
	.append(" MST.CMPCOD=HBADTL.CMPCOD  AND ")
	.append(" MST.ULDREFNUM=HBADTL.ULDREFNUM   ");
	
}
}


