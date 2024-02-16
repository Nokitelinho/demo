/*
 * BillingMatrixFilterQuery.java created on Feb 28, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class BillingMatrixFilterQuery extends PageableNativeQuery<BillingMatrixVO>{
	
	private String baseQuery;
	private BillingMatrixFilterVO billingMatrixFilterVO=null;
	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	private static final String CLASS_NAME = "BillingMatrixFilterQuery";

	/**
	 * Argumented constructor
	 * @param baseQuery
	 * @param billingMatrixFilterVO
	 * @throws SystemException
	 */
	
	public BillingMatrixFilterQuery(Mapper<BillingMatrixVO> mapper,BillingMatrixFilterVO billingMatrixFilterVO,String baseQuery) 
	throws SystemException {
		//Added by A-5218 to enable Last Link in Pagination to start
		super(billingMatrixFilterVO.getTotalRecordCount(),mapper);
		this.baseQuery=baseQuery;
		this.billingMatrixFilterVO=billingMatrixFilterVO;
		//Added by A-5218 to enable Last Link in Pagination to end
	}

	/**
	 * 
	 */
	@Override
	public String getNativeQuery() {
		log.entering(CLASS_NAME,"getNativeQuery");
		StringBuilder filterQuery=new StringBuilder(this.baseQuery);
		int index=0;
		this.setParameter(++index,billingMatrixFilterVO.getCompanyCode());
		if(billingMatrixFilterVO.getBillingMatrixId()!=null 
				&& billingMatrixFilterVO.getBillingMatrixId().trim().length()>0){
			filterQuery.append(" AND BLGMTX.BLGMTXCOD=? ");
			this.setParameter(++index,billingMatrixFilterVO.getBillingMatrixId());
		}
		if(billingMatrixFilterVO.getAirlineCode()!=null 
				&& billingMatrixFilterVO.getAirlineCode().trim().length()>0){
			filterQuery.append(" AND BLGLIN.BILTOOPTYCOD= ?");
			this.setParameter(++index,billingMatrixFilterVO.getAirlineCode());
			
		}
		if(billingMatrixFilterVO.getPoaCode()!=null 
				&& billingMatrixFilterVO.getPoaCode().trim().length()>0){
			filterQuery.append(" AND BLGLIN.BILTOOPTYCOD=?");
			this.setParameter(++index,billingMatrixFilterVO.getPoaCode());
			
		}
		
		
		
		if(billingMatrixFilterVO.getStatus()!=null 
				&& billingMatrixFilterVO.getStatus().trim().length()>0){
			if("E".equals(billingMatrixFilterVO.getStatus())){
        		LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        		filterQuery.append(" AND BLGMTX.VLDENDDAT <? ");
        		this.setParameter(++index,currentDate.toSqlDate());
        	}else{
        	LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        	filterQuery.append(" AND BLGMTX.VLDENDDAT >=? ");
        	filterQuery.append(" AND BLGMTX.BLGMTXSTA = ? ");
        	this.setParameter(++index,currentDate.toSqlDate());
      	  	this.setParameter(++index,billingMatrixFilterVO.getStatus());
        	}
			
			/*filterQuery.append(" AND BLGMTX.BLGMTXSTA=?");
			this.setParameter(++index,billingMatrixFilterVO.getStatus());*/
			
		}
		if(billingMatrixFilterVO.getValidFrom()!=null){
			filterQuery.append(" AND BLGMTX.VLDSTRDAT >=?");
			this.setParameter(++index,billingMatrixFilterVO.getValidFrom());
		}
		if(billingMatrixFilterVO.getValidTo()!=null){
			filterQuery.append(" AND BLGMTX.VLDENDDAT <= ?");
			this.setParameter(++index,billingMatrixFilterVO.getValidTo());
		}
		log.log(Log.INFO, "\n\n***filter query returning--->", filterQuery.toString());
		log.exiting(CLASS_NAME,"getNativeQuery");
		return filterQuery.toString();
	}

}
