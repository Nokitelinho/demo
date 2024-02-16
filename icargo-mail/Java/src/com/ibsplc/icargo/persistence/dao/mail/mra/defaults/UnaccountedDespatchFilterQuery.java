/*
 * UnaccountedDespatchFilterQuery.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2107
 *
 */
public class UnaccountedDespatchFilterQuery extends NativeQuery {

	private String baseQueryForR1;
	
	private String baseQueryForR1WithExp;
	
	private String baseQueryForR2;

	private UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO;

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	private static final String CLASS_NAME = "UnaccountedDespatchFilterQuery";
	
	private static final String GROUP_BY_FOR_R2 = new StringBuilder("GROUP BY SEGFLT.CMPCOD ,ORGEXG.CTYCOD,DSTEXG.CTYCOD,")
	.append("FLT.FLTCARCOD,FLT.FLTNUM,FLT.FLTDAT,ULD.CURCOD,ULD.MALRAT,POL,POU,ULD.DSN,ULD.MALCTGCOD,ULD.MALSUBCLS").toString(); 
	
	private static final String GROUP_BY_For_R1_WithExp = new StringBuilder("GROUP BY SEGFLT.CMPCOD ,ORGEXG.CTYCOD,DSTEXG.CTYCOD,")
	.append("FLT.FLTCARCOD,FLT.FLTNUM,FLT.FLTDAT,ULD.CURCOD,ULD.MALRAT,POL,POU,ULD.DSN,ULD.MALCTGCOD,ULD.MALSUBCLS").toString();
	
	private static final String R1_WithExp = new StringBuilder(" AND EXISTS (SELECT 1 FROM MALMRABLGMST MST ")
	.append(" WHERE MST.CMPCOD = ULD.CMPCOD ")
	//.append(" AND MST.BLGBAS = ULD.ORGEXGOFC||ULD.DSTEXGOFC||ULD.MALCTGCOD||ULD.MALSUBCLS||ULD.YER||ULD.DSN ")
	.append(" AND MST.MALSEQNUM = ULD.MALSEQNUM ")
	.append(" AND MST.ULDNUM = ULD.ULDNUM ")
	.append(" AND NOT EXISTS (SELECT 1 FROM MALMRABLGDTL DTL ")
	.append(" WHERE MST.CMPCOD = DTL.CMPCOD ")
	.append(" AND MST.MALSEQNUM  = DTL.MALSEQNUM ")
	//.append(" AND MST.CSGSEQNUM  = DTL.CSGSEQNUM ")
	//.append(" AND MST.POACOD     = DTL.POACOD ")
	//.append(" AND MST.BLGBAS = DTL.BLGBAS ")
	.append(" AND TRUNC(DTL.FLTDAT) BETWEEN TO_DATE(?, 'DD-MON-YYYY' ) AND TO_DATE (?,'DD-MON-YYYY') ").toString(); 
	   
	  
//	  AND DTL.FLTCARCOD = 'NZ'
//	  AND DTL.FLTNUM    = '0303')
//	  )  
   /**
    *
    * @param baseQuery
    * @param rateCardFilterVO
    * @throws SystemException
    */
    public UnaccountedDespatchFilterQuery(String baseQueryForR1,String baseQueryForR1WithExp,String baseQueryForR2,UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)
    throws SystemException {
    	this.baseQueryForR1 = baseQueryForR1;
    	this.baseQueryForR1WithExp = baseQueryForR1WithExp;
    	this.baseQueryForR2 = baseQueryForR2;
    	this.unaccountedDispatchesFilterVO = unaccountedDispatchesFilterVO;
    }

    /**
     * @author a-2049
     * @return String
     */
    public String getNativeQuery() {
    	log.entering(CLASS_NAME,"getNativeQuery");
    	StringBuilder filterQuery = new StringBuilder();
    	int index = 0;
    	if("R1".equalsIgnoreCase(unaccountedDispatchesFilterVO.getReasonCode())){
    	    filterQuery.append(this.baseQueryForR1);
    	   	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightFromDate());
	    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightToDate());
			if(unaccountedDispatchesFilterVO.getCarrierCode() != null && unaccountedDispatchesFilterVO.getCarrierCode().trim().length() >0){
				filterQuery.append("AND DTL.FLTCARCOD = ?" );
				this.setParameter(++index, unaccountedDispatchesFilterVO.getCarrierCode());
			}
			if(unaccountedDispatchesFilterVO.getFlightNumber() != null && unaccountedDispatchesFilterVO.getFlightNumber().trim().length() >0){
				filterQuery.append("AND DTL.FLTNUM = ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getFlightNumber());
			}
			if(unaccountedDispatchesFilterVO.getDeparturePort() != null && unaccountedDispatchesFilterVO.getDeparturePort().trim().length() >0){
				filterQuery.append("AND ORGCTY.SRVARPCOD = ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getDeparturePort());
			}
			if(unaccountedDispatchesFilterVO.getFinalDestination() != null && unaccountedDispatchesFilterVO.getFinalDestination().trim().length() >0){
				filterQuery.append("AND DSTCTY.SRVARPCOD = ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getFinalDestination());
			}
			filterQuery.append(" UNION ALL " );
			filterQuery.append(baseQueryForR1WithExp);
    	   	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightFromDate());
	    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightToDate());
			if(unaccountedDispatchesFilterVO.getCarrierCode() != null && unaccountedDispatchesFilterVO.getCarrierCode().trim().length() >0){
				filterQuery.append("AND FLT.FLTCARCOD = ?" );
				this.setParameter(++index, unaccountedDispatchesFilterVO.getCarrierCode());
			}
			if(unaccountedDispatchesFilterVO.getFlightNumber() != null && unaccountedDispatchesFilterVO.getFlightNumber().trim().length() >0){
				filterQuery.append("AND FLT.FLTNUM = ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getFlightNumber());
			}
			if(unaccountedDispatchesFilterVO.getDeparturePort() != null && unaccountedDispatchesFilterVO.getDeparturePort().trim().length() >0){
				filterQuery.append("AND ORGCTY.SRVARPCOD = ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getDeparturePort());
			}
			if(unaccountedDispatchesFilterVO.getFinalDestination() != null && unaccountedDispatchesFilterVO.getFinalDestination().trim().length() >0){
				filterQuery.append("AND DSTCTY.SRVARPCOD = ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getFinalDestination());
			}
			filterQuery.append(R1_WithExp);
    	   	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightFromDate());
	    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightToDate());
			if(unaccountedDispatchesFilterVO.getCarrierCode() != null && unaccountedDispatchesFilterVO.getCarrierCode().trim().length() >0){
				filterQuery.append("AND DTL.FLTCARCOD = ?" );
				this.setParameter(++index, unaccountedDispatchesFilterVO.getCarrierCode());
			}
			if(unaccountedDispatchesFilterVO.getFlightNumber() != null && unaccountedDispatchesFilterVO.getFlightNumber().trim().length() >0){
				filterQuery.append("AND DTL.FLTNUM = ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getFlightNumber());
			}
			filterQuery.append(") )").append(GROUP_BY_For_R1_WithExp);
	   	}else if ("R2".equalsIgnoreCase(unaccountedDispatchesFilterVO.getReasonCode())){
	   		/*
	   		 * "R2" - Despatches those are present in Mail Ops and 
	   		 * not yet Imported to MRA for Accounting purpose
	   		 */
    	    filterQuery.append(this.baseQueryForR2);
	    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightFromDate());
	    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightToDate());
	    	if(unaccountedDispatchesFilterVO.getCarrierCode() != null && unaccountedDispatchesFilterVO.getCarrierCode().trim().length() >0){
	    		filterQuery.append("AND FLT.FLTCARCOD = ? " );
				this.setParameter(++index, unaccountedDispatchesFilterVO.getCarrierCode());
			}
			if(unaccountedDispatchesFilterVO.getFlightNumber() != null && unaccountedDispatchesFilterVO.getFlightNumber().trim().length() >0){
				filterQuery.append("AND FLT.FLTNUM = ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getFlightNumber());
			}
			if(unaccountedDispatchesFilterVO.getDeparturePort() != null && unaccountedDispatchesFilterVO.getDeparturePort().trim().length() >0){
				filterQuery.append("AND ORGCTY.SRVARPCOD =  ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getDeparturePort());
			}
			if(unaccountedDispatchesFilterVO.getFinalDestination() != null && unaccountedDispatchesFilterVO.getFinalDestination().trim().length() >0){
				filterQuery.append("AND DSTCTY.SRVARPCOD = ? " );
				this.setParameter(++index,unaccountedDispatchesFilterVO.getFinalDestination());
			}
			filterQuery.append(GROUP_BY_FOR_R2);
	   	}else if ("".equals(unaccountedDispatchesFilterVO.getReasonCode())){
		   		filterQuery.append(this.baseQueryForR1);
		   		this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightFromDate());
		    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightToDate());
				if(unaccountedDispatchesFilterVO.getCarrierCode() != null && unaccountedDispatchesFilterVO.getCarrierCode().trim().length() >0){
					filterQuery.append("AND DTL.FLTCARCOD = ? " );
					this.setParameter(++index, unaccountedDispatchesFilterVO.getCarrierCode());
				}
				if(unaccountedDispatchesFilterVO.getFlightNumber() != null && unaccountedDispatchesFilterVO.getFlightNumber().trim().length() >0){
					filterQuery.append("AND DTL.FLTNUM = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getFlightNumber());
				}
				if(unaccountedDispatchesFilterVO.getDeparturePort() != null && unaccountedDispatchesFilterVO.getDeparturePort().trim().length() >0){
					filterQuery.append("AND ORGCTY.SRVARPCOD = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getDeparturePort());
				}
				if(unaccountedDispatchesFilterVO.getFinalDestination() != null && unaccountedDispatchesFilterVO.getFinalDestination().trim().length() >0){
					filterQuery.append("AND DSTCTY.SRVARPCOD = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getFinalDestination());
				}
				filterQuery.append(" UNION ALL " );
				filterQuery.append(baseQueryForR1WithExp);
	    	   	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightFromDate());
		    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightToDate());
				if(unaccountedDispatchesFilterVO.getCarrierCode() != null && unaccountedDispatchesFilterVO.getCarrierCode().trim().length() >0){
					filterQuery.append("AND FLT.FLTCARCOD = ?" );
					this.setParameter(++index, unaccountedDispatchesFilterVO.getCarrierCode());
				}
				if(unaccountedDispatchesFilterVO.getFlightNumber() != null && unaccountedDispatchesFilterVO.getFlightNumber().trim().length() >0){
					filterQuery.append("AND FLT.FLTNUM = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getFlightNumber());
				}
				if(unaccountedDispatchesFilterVO.getDeparturePort() != null && unaccountedDispatchesFilterVO.getDeparturePort().trim().length() >0){
					filterQuery.append("AND ORGCTY.SRVARPCOD = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getDeparturePort());
				}
				if(unaccountedDispatchesFilterVO.getFinalDestination() != null && unaccountedDispatchesFilterVO.getFinalDestination().trim().length() >0){
					filterQuery.append("AND DSTCTY.SRVARPCOD = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getFinalDestination());
				}
				filterQuery.append(R1_WithExp);
	    	   	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightFromDate());
		    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightToDate());
				if(unaccountedDispatchesFilterVO.getCarrierCode() != null && unaccountedDispatchesFilterVO.getCarrierCode().trim().length() >0){
					filterQuery.append("AND DTL.FLTCARCOD = ?" );
					this.setParameter(++index, unaccountedDispatchesFilterVO.getCarrierCode());
				}
				if(unaccountedDispatchesFilterVO.getFlightNumber() != null && unaccountedDispatchesFilterVO.getFlightNumber().trim().length() >0){
					filterQuery.append("AND DTL.FLTNUM = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getFlightNumber());
				}
				filterQuery.append(") )").append(GROUP_BY_For_R1_WithExp);
				filterQuery.append(" UNION ALL " );
				filterQuery.append(baseQueryForR2);
		    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightFromDate());
		    	this.setParameter(++index, unaccountedDispatchesFilterVO.getFlightToDate());
		    	if(unaccountedDispatchesFilterVO.getCarrierCode() != null && unaccountedDispatchesFilterVO.getCarrierCode().trim().length() >0){
		    		filterQuery.append("AND FLT.FLTCARCOD = ? " );
					this.setParameter(++index, unaccountedDispatchesFilterVO.getCarrierCode());
				}
				if(unaccountedDispatchesFilterVO.getFlightNumber() != null && unaccountedDispatchesFilterVO.getFlightNumber().trim().length() >0){
					filterQuery.append("AND FLT.FLTNUM = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getFlightNumber());
				}
				if(unaccountedDispatchesFilterVO.getDeparturePort() != null && unaccountedDispatchesFilterVO.getDeparturePort().trim().length() >0){
					filterQuery.append("AND ORGCTY.SRVARPCOD = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getDeparturePort());
				}
				if(unaccountedDispatchesFilterVO.getFinalDestination() != null && unaccountedDispatchesFilterVO.getFinalDestination().trim().length() >0){
					filterQuery.append("AND DSTCTY.SRVARPCOD = ? " );
					this.setParameter(++index,unaccountedDispatchesFilterVO.getFinalDestination());
				}
				filterQuery.append(GROUP_BY_FOR_R2);
	   	}
        return filterQuery.toString();
    }

}
