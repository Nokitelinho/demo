/* MRAProrationExceptionsFilterQuery.java Created on Sep 2008

 *

 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS
 *  Software Services (P) Ltd.

 * Use is subject to license terms.

 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3108
 *
 */
public class MRAProrationExceptionsFilterQuery extends PageableNativeQuery<ProrationExceptionVO> {
	

	private Log log = LogFactory.getLogger("ProrationExceptionsFilterQuery");
	private ProrationExceptionsFilterVO prorationExceptionsFilterVO;
	private String baseQuery;    
	
	private static final String EXCEPTION_ASSINEDSTATUS_UNASSIGNED="U";
	private static final String EXCEPTION_ASSINEDSTATUS_ASSIGNED="A";
	//added by A-5223 for ICRD-21098 starts
//	private static final String MRA_DEFAULTS_SUFFIX_QUERY = ") RESULT_TABLE";
	//modified by A-5223 for ICRD-21098 starts
	public MRAProrationExceptionsFilterQuery(Mapper<ProrationExceptionVO> mapper, ProrationExceptionsFilterVO prorationExceptionsFilterVO,String baseQuery) throws SystemException {
		super(prorationExceptionsFilterVO.getTotalRecords(),mapper);
		this.prorationExceptionsFilterVO = prorationExceptionsFilterVO;
		this.baseQuery = baseQuery;
	}
	//modified by A-5223 for ICRD-21098 starts
	/**
	 * This method constructs the query that is requied to list the proration
	 * details depending on various filter conditions.
	 * 
	 * 
	 * @return String
	 * 
	 */
	@Override
	public String getNativeQuery(){
		
		log.entering("ProrationExceptionsFilterQuery", "getNativeQuery");
		
		StringBuilder query = new StringBuilder().append(baseQuery);
		int index = 0;
		String empty = "";
		log.log(Log.INFO,
				"prorationExceptionsFilterVO.getFlightCarrierId()=========",
				prorationExceptionsFilterVO.getFlightCarrierId());
		//this.setParameter(++index,prorationExceptionsFilterVO.getFlightCarrierId());  
		this.setParameter(++index, prorationExceptionsFilterVO.getCompanyCode());		
		if (prorationExceptionsFilterVO.getFromDate() != null){
    		this.setParameter(++index, prorationExceptionsFilterVO.getFromDate().toDisplayDateOnlyFormat());
    		}
    		if (prorationExceptionsFilterVO.getToDate() != null) {
    			this.setParameter(++index, prorationExceptionsFilterVO.getToDate().toDisplayDateOnlyFormat()); 
    		}
		if (prorationExceptionsFilterVO.getOriginOfficeOfExchange()!= null
				&& !empty.equals(prorationExceptionsFilterVO.getOriginOfficeOfExchange())) {
			query.append("AND a.ORGEXGOFC = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getOriginOfficeOfExchange());
		}
		if (prorationExceptionsFilterVO.getDestinationOfficeOfExchange() != null
				&& !empty.equals(prorationExceptionsFilterVO.getDestinationOfficeOfExchange())) {
			query.append("AND a.DSTEXGOFC = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getDestinationOfficeOfExchange());
		}
		if (prorationExceptionsFilterVO.getMailCategory() != null
				&& !empty.equals(prorationExceptionsFilterVO.getMailCategory())) {
			query.append("AND a.MALCTGCOD = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getMailCategory());
		}
		if (prorationExceptionsFilterVO.getSubClass() != null
				&& !empty.equals(prorationExceptionsFilterVO.getSubClass())) {
			query.append("AND a.MALSUBCLS = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getSubClass());
		}
		if (prorationExceptionsFilterVO.getYear() != null
				&& !empty.equals(prorationExceptionsFilterVO.getYear())) {
			query.append("AND a.YER = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getYear());
		}
		if (prorationExceptionsFilterVO.getReceptacleSerialNumber() != null
				&& !empty.equals(prorationExceptionsFilterVO.getReceptacleSerialNumber())) {
			query.append("AND a.RSN = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getReceptacleSerialNumber());
		}
		if (prorationExceptionsFilterVO.getHighestNumberIndicator() != null
				&& !empty.equals(prorationExceptionsFilterVO.getHighestNumberIndicator())) {
			query.append("AND a.HSN = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getHighestNumberIndicator());
		}
		if (prorationExceptionsFilterVO.getRegisteredIndicator() != null
				&& !empty.equals(prorationExceptionsFilterVO.getRegisteredIndicator())) {
			query.append("AND a.REGIND = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getRegisteredIndicator());
		}
		//A-8331
		if (prorationExceptionsFilterVO.getCsgdocnum() != null
				&& !empty.equals(prorationExceptionsFilterVO.getCsgdocnum())) {
			query.append("AND a.CSGDOCNUM = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getCsgdocnum());
		}
		
		if (prorationExceptionsFilterVO.getExceptionCode() != null
				&& !empty.equals(prorationExceptionsFilterVO.getExceptionCode())) {
			query.append("AND a.EXPCOD = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getExceptionCode());
		}
		if (prorationExceptionsFilterVO.getFlightCarrierCode() != null
				&& !empty.equals(prorationExceptionsFilterVO.getFlightCarrierCode())) {
			query.append("AND a.FLTCARCOD = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getFlightCarrierCode());
		}
		if (prorationExceptionsFilterVO.getFlightNumber() != null
				&& !empty.equals(prorationExceptionsFilterVO.getFlightNumber())) {
			query.append("AND a.FLTNUM = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getFlightNumber());
		}
		if (prorationExceptionsFilterVO.getFlightDate() != null) {
			query.append("AND TRUNC(a.FLTDAT) = to_date(?, 'dd-MON-yyyy') ");
			this.setParameter(++index, prorationExceptionsFilterVO.getFlightDate().toDisplayDateOnlyFormat());
		}
		if (prorationExceptionsFilterVO.getSegmentOrigin() != null
				&& !empty.equals(prorationExceptionsFilterVO.getSegmentOrigin())) {
			//query.append("AND RTG.POL = ?");
			query.append("AND a.ORGARPCOD = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getSegmentOrigin());
		}
		if (prorationExceptionsFilterVO.getSegmentDestination() != null
				&& !empty.equals(prorationExceptionsFilterVO.getSegmentDestination())) {
			//query.append("AND RTG.POU = ?");
			query.append("AND a.DSTARPCOD = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getSegmentDestination());
		}
		
		if (prorationExceptionsFilterVO.getDispatchNo() != null
				&& !empty.equals(prorationExceptionsFilterVO.getDispatchNo())) {
			query.append("AND a.DSN = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getDispatchNo());
		}
		//Added as part of ICRD-205027 starts
		if (prorationExceptionsFilterVO.getMailbagId() != null
				&& !empty.equals(prorationExceptionsFilterVO.getMailbagId())) {
			query.append("AND a.MALIDR = ?");//modified by a-7871 for ICRD-250597
			this.setParameter(++index, prorationExceptionsFilterVO.getMailbagId());
		}
		//Added as part of ICRD-205027 ends
		if (prorationExceptionsFilterVO.getStatus() != null
				&& !empty.equals(prorationExceptionsFilterVO.getStatus())) {
			query.append("AND a.EXPSTA = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getStatus());
		}
		
		if (prorationExceptionsFilterVO.getAsignee()!= null
				&& !empty.equals(prorationExceptionsFilterVO.getAsignee())) {
			query.append("AND a.ASDUSR = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getAsignee());
		}
		if (prorationExceptionsFilterVO.getAssignedStatus()!= null 				
				&& !empty.equals(prorationExceptionsFilterVO.getAssignedStatus())) {
			if(prorationExceptionsFilterVO.getAssignedStatus().equals(EXCEPTION_ASSINEDSTATUS_ASSIGNED)){
			query.append("AND a.ASDUSR IS NOT NULL ");
			}
			else if(prorationExceptionsFilterVO.getAssignedStatus().equals(EXCEPTION_ASSINEDSTATUS_UNASSIGNED)){
				query.append("AND a.ASDUSR IS NULL ");
			}
		}


	

		if(prorationExceptionsFilterVO.getBlgbas()!=null 
				&& prorationExceptionsFilterVO.getBlgbas().trim().length()>0 ){
			query.append("AND a.MALIDR = ?");//modified by a-7871 for ICRD-250597
			this.setParameter(++index, prorationExceptionsFilterVO.getBlgbas());
			
		}
		if(prorationExceptionsFilterVO.getCsgdocnum()!=null 
				&& prorationExceptionsFilterVO.getCsgdocnum().trim().length()>0 ){
			query.append("AND a.CSGDOCNUM = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getCsgdocnum());
			
		}
		if(prorationExceptionsFilterVO.getCsgseqnum()>0 ){
			query.append("AND a.CSGSEQNUM = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getCsgseqnum());
			
		}
		if(prorationExceptionsFilterVO.getPoaCode()!=null 
				&& prorationExceptionsFilterVO.getPoaCode().trim().length()>0 ){
			query.append("AND a.POACOD = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getPoaCode());
			
		}
		//Added as Part of CR ID:ICRD-241594
		if(prorationExceptionsFilterVO.getGpaCode()!=null 
				&& prorationExceptionsFilterVO.getGpaCode().trim().length()>0 ){
			query.append("AND a.POACOD = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getGpaCode());
			}
		//End Here for ICRD-241594
		if(prorationExceptionsFilterVO.getStatus() != null && prorationExceptionsFilterVO.getStatus().trim().length() > 0){
			query.append("AND a.EXPSTA = ?");
			this.setParameter(++index, prorationExceptionsFilterVO.getStatus());
		}
		query.append("ORDER BY a.RTGSERNUM,a.RCVDAT ");
		query.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
		log.log(Log.INFO, "FlightFilterQuery", query);
		log.log(Log.INFO, "FlightFilterQuery", query.toString());
		return query.toString();
	}
}
