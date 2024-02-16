/*
 * MailsDeliveredFilterQuery.java Created on Mar 10, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.Date;

import com.ibsplc.icargo.business.mail.operations.vo.MailStatusFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251 SREEJITH P.C.
 *
 */
public class MailsDeliveredFilterQuery extends NativeQuery {

	private String baseQuery;
	
	private MailStatusFilterVO mailStatusFilterVO;
	
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	/**
	 * @throws SystemException
	 * @param baseQuery
	 * @param mailStatusFilterVO
	 */
	public MailsDeliveredFilterQuery(String baseQuery, 
			MailStatusFilterVO mailStatusFilterVO) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.mailStatusFilterVO = mailStatusFilterVO;
	}

	/**
	 * TODO Purpose
	 * Mar 11, 2008, A-3251
	 * @return
	 * */
	@Override
	public String getNativeQuery() {
		log.entering("MailsDeliveredFilterQuery", "getNativeQuery");
		
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		int idx = 0;
		//mandatory filter fields
		
			log.log(Log.FINE,
					"mandatory filter field Company code to Query>>>>>>> ",
					mailStatusFilterVO.getCompanyCode());
			setParameter(++idx,mailStatusFilterVO.getCompanyCode());
			//Date Range
		   LocalDate datetemp=mailStatusFilterVO.getFromDate();
	   		 Date fromdatetemp=null;
	   		if (datetemp != null) {
	   			fromdatetemp = datetemp.toSqlDate();
	   		}		   		
	   	 String fromDateString = String.valueOf(fromdatetemp);
	   	if(!"".equals(fromDateString.trim()))
	   	{
	   		log.log(Log.FINE,
					"mandatory filter field fromDate to Query>>>>>>> ",
					fromDateString);
			setParameter(++idx,fromDateString);
	   	 
	   	}
	   	datetemp=mailStatusFilterVO.getToDate();
	   		Date todatetemp=null;
	   		if (datetemp != null) {
	   			todatetemp = datetemp.toSqlDate();
	   		}	
	   	 String toDateString = String.valueOf(todatetemp);
	   	if(!"".equals(toDateString.trim()))
	   	{
	   		log.log(Log.FINE,
					"mandatory filter field toomDate to Query>>>>>>> ",
					toDateString);
			setParameter(++idx,toDateString);
	   	}
	   		log.log(Log.FINE, "!!!!!######-->BaseQuery", baseQuery);
		//optional filter fields 
	   	     //Carrier code
	   	if(!"".equals(mailStatusFilterVO.getCarrierCode().trim())&&(mailStatusFilterVO.getCarrierCode()!=null))
	   		{
	   		log
					.log(
							Log.FINE,
							"Carrier code not Null So attaching Carrier id to filter>>>>>>> ",
							mailStatusFilterVO.getCarrierid());
			queryBuilder.append("AND ULDSEG.FLTCARIDR=?");
	   		setParameter(++idx,mailStatusFilterVO.getCarrierid());
	   		}
	   		//Flight Number
	   	
		if(!"".equals(mailStatusFilterVO.getFlightNumber().trim())&&(mailStatusFilterVO.getFlightNumber()!=null))
  		{
  		log.log(Log.FINE, "Flight Number  not Null So attaching  Flight specific details to filter to filter>>>>>>> "); 
  		queryBuilder.append("AND ULDSEG.FLTNUM=? ");   		
  		queryBuilder.append("AND ULDSEG.FLTCARIDR=?");   		
 		setParameter(++idx,mailStatusFilterVO.getFlightNumber().trim());
  		setParameter(++idx,mailStatusFilterVO.getFlightCarrierid());
  		}	   	
	   		//Airport(POL)
	   	if(!"".equals(mailStatusFilterVO.getPol().trim())&&(mailStatusFilterVO.getPol()!=null))
	   	{
	   		log
					.log(
							Log.FINE,
							"Airport(POL) not Null So attaching Airport(POL) code to filter>>>>>>> ",
							mailStatusFilterVO.getPol().trim());
			queryBuilder.append("AND ULDSEG.SCNPRT=?");
	   		setParameter(++idx,mailStatusFilterVO.getPol().trim());
	   	}
	   		//Airport(POU)
	   	if(!"".equals(mailStatusFilterVO.getPou().trim())&&(mailStatusFilterVO.getPou()!=null))
	   	{	log
				.log(
						Log.FINE,
						"Airport(POU) not Null So attaching Airport(POU) code to filter>>>>>>> ",
						mailStatusFilterVO.getPou().trim());
			queryBuilder.append("AND FLTSEG.POU=?");
	   		setParameter(++idx,mailStatusFilterVO.getPou().trim());
	   	}
	   		//Postal Authority(PA)
	   	if(!"".equals(mailStatusFilterVO.getPacode().trim())&&(mailStatusFilterVO.getPacode()!=null))
	   	{
	   		log
					.log(
							Log.FINE,
							"Postal Authority(PA) not Null So attaching Postal Authority(PA) code to filter>>>>>>> ",
							mailStatusFilterVO.getPacode().trim());
			queryBuilder.append("AND MALMST.POACOD=?");  
	   		setParameter(++idx,mailStatusFilterVO.getPacode().trim());
	   	}
	   		   	
	   	
	   	log.log(Log.FINE, "!!!!!######-->Query Generated After All Filter",
				baseQuery);
				log.exiting("MailsDeliveredFilterQuery", "getNativeQuery");
		return queryBuilder.toString();
	}	
}
