/*
 * RateCardFilterQuery.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.Date;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class RateCardFilterQuery extends PageableNativeQuery<RateCardVO> {

	private String baseQuery ;

	private RateCardFilterVO rateCardFilterVO;

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	private static final String CLASS_NAME = "RateCardFilterQuery";

   /**
    *
    * @param baseQuery
    * @param rateCardFilterVO
    * @throws SystemException
    */
   /* public RateCardFilterQuery(String baseQuery,RateCardFilterVO rateCardFilterVO)
    throws SystemException {
    	this.baseQuery = baseQuery;
    	this.rateCardFilterVO = rateCardFilterVO;
    }
    */
	//Added by : A-5175 on 16-Oct-2012 for icrd-21098
	public RateCardFilterQuery(int totalRecordCount,RateCardDetailsMapper cardDetailsMapper, String baseQuery,RateCardFilterVO rateCardFilterVO)
    throws SystemException {
		super(totalRecordCount,cardDetailsMapper);
    	this.baseQuery = baseQuery;
    	this.rateCardFilterVO = rateCardFilterVO;
    }

    /**
     * @author a-2049
     * @return String
     */
    public String getNativeQuery() {
    	log.entering(CLASS_NAME,"getNativeQuery");
    	StringBuilder filterQuery
    						= new StringBuilder(this.baseQuery);
    	int index = 0;

    	this.setParameter(++index,rateCardFilterVO.getCompanyCode());

    	if(rateCardFilterVO.getRateCardID() != null
        		&& rateCardFilterVO.getRateCardID().length() > 0 ){
        	filterQuery.append(" AND RATMST.RATCRDCOD = ? ");
        	this.setParameter(++index,rateCardFilterVO.getRateCardID());
        }

    	if(rateCardFilterVO.getRateCardStatus() != null
        		&& rateCardFilterVO.getRateCardStatus().length() > 0 ){
    		
    		if("E".equals(rateCardFilterVO.getRateCardStatus())){
        		LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        		filterQuery.append(" AND RATMST.VLDENDDAT <? ");
        		this.setParameter(++index,currentDate.toSqlDate());
        	}else{
        	LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        	filterQuery.append(" AND RATMST.VLDENDDAT >=? ");
        	filterQuery.append(" AND RATMST.RATCRDSTA = ? ");
        	this.setParameter(++index,currentDate.toSqlDate());
      	  	this.setParameter(++index,rateCardFilterVO.getRateCardStatus());
        	}
    		
    		
        	/*filterQuery.append(" AND RATMST.RATCRDSTA = ? ");
        	this.setParameter(++index,rateCardFilterVO.getRateCardStatus());*/
        }

        if(rateCardFilterVO.getStartDate() != null ){
        	Date incomingSqlStartDate = null;
        	LocalDate incoimingStartDate = rateCardFilterVO.getStartDate();
        	incomingSqlStartDate = incoimingStartDate.toSqlDate();
        	String incomingStartDateString =String.valueOf(incomingSqlStartDate);
        	filterQuery.append(" AND trunc(TO_DATE(?,'yyyy-MM-dd')) between RATMST.VLDSTRDAT and RATMST.VLDENDDAT ");
        	this.setParameter(++index,incomingStartDateString);
        }

       /* if(rateCardFilterVO.getEndDate() != null ){
        	Date incomingSqlEndDate = null;
        	LocalDate incoimingEndDate = rateCardFilterVO.getEndDate();
        	incomingSqlEndDate = incoimingEndDate.toSqlDate();
        	String incomingEndDateString =String.valueOf(incomingSqlEndDate);
        	filterQuery.append(" AND RATMST.VLDENDDAT >= TO_DATE(?,'yyyy-MM-dd') ");
        	this.setParameter(++index,incomingEndDateString);
        }*/

        log.log(Log.FINE, "<-- the final Filter Query --> \n\n", filterQuery.toString());
		return filterQuery.toString();
    }

}
