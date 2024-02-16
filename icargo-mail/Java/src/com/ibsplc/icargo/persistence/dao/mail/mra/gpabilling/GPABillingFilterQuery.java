/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.GPABillingFilterQuery.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Feb 7, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.GPABillingDetailsMultiMapper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.GPABillingFilterQuery.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Feb 7, 2019	:	Draft
 */
public class GPABillingFilterQuery extends PageableNativeQuery<DocumentBillingDetailsVO>{
	private Log log = LogFactory.getLogger("MRA_GPABilling");

    private String baseQuery;
    private GPABillingEntriesFilterVO gpaBillingEntriesFilterVO;  
    
    /**
     * 	Method		:	GPABillingFilterQuery.GPABillingDetailsFilterQuery
     *	Added by 	:	A-4809 on Feb 7, 2019
     * 	Used for 	:
     *	Parameters	:	@param defaultPageSize
     *	Parameters	:	@param totalRecorCount
     *	Parameters	:	@param detailsMultiMapper
     *	Parameters	:	@param baseQuery
     *	Parameters	:	@param gpaBillingEntriesFilterVO
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	void
     */
    public GPABillingFilterQuery(int defaultPageSize,int totalRecorCount, GPABillingDetailsMultiMapper detailsMultiMapper, String baseQuery, GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException {
        super(defaultPageSize,totalRecorCount,detailsMultiMapper);
    	log.entering("GPABillingDetailsFilterQuery","constructor");
		this.gpaBillingEntriesFilterVO = gpaBillingEntriesFilterVO;
		this.baseQuery = baseQuery;
		log.exiting("GPABillingDetailsFilterQuery","constructor");

    }
    /**
     *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery#getNativeQuery()
     *	Added by 			: A-4809 on Feb 7, 2019
     * 	Used for 	:
     *	Parameters	:	@return
     */
    public String getNativeQuery() {
    	log.entering("GPABillingDetailsFilterQuery","getNativeQuery");
    	String companyCode = gpaBillingEntriesFilterVO.getCompanyCode();
    	String status=gpaBillingEntriesFilterVO.getBillingStatus();
    	String gpaCode=gpaBillingEntriesFilterVO.getGpaCode();
    	String country=gpaBillingEntriesFilterVO.getCountryCode();
    	String category=gpaBillingEntriesFilterVO.getMailCategoryCode();
    	String contractRate=gpaBillingEntriesFilterVO.getContractRate();
    	String upuRate=gpaBillingEntriesFilterVO.getUPURate();
    	int index = 0;
    	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null  ) {
    		this.setParameter( ++index, gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", "") );
    		this.setParameter( ++index, gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", "") );
			
		}    	
    	this.setParameter( ++index, companyCode );
    	StringBuilder sbul = new StringBuilder(baseQuery);
    	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null  ) {
    		sbul.append( "AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
    		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
    		sbul.append(" AND ");
    		sbul.append(gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", ""));
    		sbul.append(" ");
/*    		sbul.append( "AND TRUNC(MST.RCVDAT) BETWEEN To_DATE(");
    		sbul.append("'");
    		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toDisplayDateOnlyFormat());
    		sbul.append("', 'DD-MON-YYYY') ");
    		sbul.append(" AND To_DATE(");
    		sbul.append("'");
    		sbul.append(gpaBillingEntriesFilterVO.getToDate().toDisplayDateOnlyFormat());
    		sbul.append("', 'DD-MON-YYYY') ");
    		sbul.append(" ");*/
    	}
    	if ( status != null && status.trim().length() > 0  ) {
    		sbul.append( "AND DTL.BLGSTA = ? ");
    		this.setParameter( ++index, status );
    	}
 /*   	if ( gpaCode != null && gpaCode.trim().length() > 0  ) {
    		sbul.append( "AND DTL.UPDBILTOOPOA = ? ");
    		this.setParameter( ++index, gpaCode );
    	}*/
    	if ( country != null && country.trim().length() > 0  ) {
    		sbul.append( "AND DTL.CNTCOD = ? ");
    		this.setParameter( ++index, country );
    	}
/*   	if (gpaBillingEntriesFilterVO.getConDocNumber() !=null &&
    			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){
    		sbul.append("AND MST.CSGDOCNUM IN ( ?");
    		String csg =null;
    		if(gpaBillingEntriesFilterVO.getConDocNumber().contains(",")){
    			csg = gpaBillingEntriesFilterVO.getConDocNumber().substring(1,gpaBillingEntriesFilterVO.getConDocNumber().length()-1);
    			//String[] csgsplit = csgReqd.split(",");
    			//StringBuilder csgNo = new StringBuilder(csgsplit[0]);
    			//for(int i=1;i<csgsplit.length;i++){
    				//csgNo.append("','").append(csgsplit[i]);
    			//}
    			//csg = csgNo.toString(); 
    		}else{ 
    			csg = gpaBillingEntriesFilterVO.getConDocNumber().replace("'", ""); 	
    	}
    		this.setParameter(++index, csg);      
    		sbul.append(")");
    	}*/
   /* 	if (gpaBillingEntriesFilterVO.getDsnNumber() !=null &&
    			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
    		sbul.append("AND MST.DSN IN ( ?");
    		String dsn = gpaBillingEntriesFilterVO.getDsnNumber().replace("'", ""); 
    		this.setParameter(++index, dsn);
    		sbul.append(")");
    	}*/
/*    	if(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!=null &&
    			gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
    		sbul.append("AND MST.ORGEXGOFC  = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
    	}
    	if(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!=null &&
    			gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
    		sbul.append("AND MST.DSTEXGOFC  = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
    	}*/ 
    	if(gpaBillingEntriesFilterVO.getMailbagId()!=null &&
    			gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
    		sbul.append("AND MST.MALIDR  = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getMailbagId());
    	}
/*    	if(category!=null &&
    			category.trim().length() >0){
    		sbul.append("AND MST.MALCTGCOD   = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getMailCategoryCode());
    	}
    	if(gpaBillingEntriesFilterVO.getMailSubclass()!=null &&
    			gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
    		sbul.append("AND MST.MALSUBCLS    = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getMailSubclass());
    	}*/
    	if(gpaBillingEntriesFilterVO.getYear()!=null &&
    			gpaBillingEntriesFilterVO.getYear().trim().length() >0){
    		sbul.append("AND MST.YER = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getYear());
    	}
    	if(gpaBillingEntriesFilterVO.getRsn()!=null &&
    			gpaBillingEntriesFilterVO.getRsn().trim().length() >0){
    		sbul.append("AND MST.RSN = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getRsn());
    	}
    	if(gpaBillingEntriesFilterVO.getHni()!=null &&
    			gpaBillingEntriesFilterVO.getHni().trim().length() >0){
    		sbul.append("AND MST.HSN  = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getHni());
    	}
    	if(gpaBillingEntriesFilterVO.getRegInd()!=null &&
    			gpaBillingEntriesFilterVO.getRegInd().trim().length() >0){
    		sbul.append("AND MST.REGIND = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getRegInd());
    	}
    	if (contractRate != null
    			&& contractRate.trim().length() > 0
    			&& upuRate == null) {
    		sbul.append("AND DTL.RATTYP = ?");
    		this.setParameter(++index,
    				gpaBillingEntriesFilterVO.getContractRate());
    	}
    	if (upuRate != null
    			&& upuRate.trim().length() > 0
    			&& contractRate == null) {
    		sbul.append("AND DTL.RATTYP = ?");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getUPURate());
    	}
    	if ((upuRate != null
    			&& upuRate.trim().length() > 0
    			&& contractRate != null && contractRate.trim().length() > 0)) {
    		sbul.append("AND DTL.RATTYP IN (?,?)");
    		this.setParameter(++index, gpaBillingEntriesFilterVO.getUPURate());
    		this.setParameter(++index,
    				gpaBillingEntriesFilterVO.getContractRate());
    	}
    	if(gpaBillingEntriesFilterVO.getOrigin()!=null && gpaBillingEntriesFilterVO.getOrigin().trim().length()>0){
    		sbul.append("AND MST.ORGCTYCOD = ?");
    		index++;
    		setParameter(index, this.gpaBillingEntriesFilterVO.getOrigin());
    	}
    	if(gpaBillingEntriesFilterVO.getDestination()!=null && gpaBillingEntriesFilterVO.getDestination().trim().length()>0){
    		sbul.append("AND MST.DSTCTYCOD = ?");
    		index++;
    		setParameter(index, this.gpaBillingEntriesFilterVO.getDestination());	
    	}
    	return sbul.toString();
    }
}
