/*
 * GPABillingDetailsFilterQuery.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
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
 * @author A-1556
 *
 */
public class GPABillingDetailsFilterQuery extends PageableNativeQuery<DocumentBillingDetailsVO> {
	private Log log = LogFactory.getLogger("MRA_GPABilling");

    private String baseQuery;
    private GPABillingEntriesFilterVO gpaBillingEntriesFilterVO;
    private static final String DATE ="yyyyMMdd";
    /**
     * @param baseQuery
     * @param gpaBillingEntriesFilterVO
     * @throws SystemException
     */
    /*
    public GPABillingDetailsFilterQuery(String baseQuery,GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException {
        super();
    	log.entering("GPABillingDetailsFilterQuery","constructor");
		this.gpaBillingEntriesFilterVO = gpaBillingEntriesFilterVO;
		this.baseQuery = baseQuery;
		log.exiting("GPABillingDetailsFilterQuery","constructor");

    }
    */
    /*Added by A-2391*/
    //added by A-5175 for QF CR icrd-21098 starts
    public GPABillingDetailsFilterQuery(int totalRecorCount, GPABillingDetailsMultiMapper detailsMultiMapper, String baseQuery, GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException {
        super(totalRecorCount,detailsMultiMapper);
    	log.entering("GPABillingDetailsFilterQuery","constructor");
		this.gpaBillingEntriesFilterVO = gpaBillingEntriesFilterVO;
		this.baseQuery = baseQuery;
		log.exiting("GPABillingDetailsFilterQuery","constructor");

    }
     //added by A-5175 for QF CR icrd-21098ends

    /**
     * @return String
     */
    public String getNativeQuery() {
    	log.entering("GPABillingDetailsFilterQuery","getNativeQuery");
    	String companyCode = gpaBillingEntriesFilterVO.getCompanyCode();
    	String status=gpaBillingEntriesFilterVO.getBillingStatus();
    	String gpaCode=gpaBillingEntriesFilterVO.getGpaCode();
    	String country=gpaBillingEntriesFilterVO.getCountryCode();
    	String category=gpaBillingEntriesFilterVO.getMailCategoryCode();
   //Added by A-6991 for ICRD-137019 Starts
    	String contractRate=gpaBillingEntriesFilterVO.getContractRate();
    	String upuRate=gpaBillingEntriesFilterVO.getUPURate();
   //Added by A-6991 for ICRD-137019 Starts

    	int index = 0;
    	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null  ) {
    		this.setParameter( ++index, Integer.parseInt(gpaBillingEntriesFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
    		this.setParameter( ++index,Integer.parseInt(gpaBillingEntriesFilterVO.getToDate().toStringFormat(DATE).substring(0, 8))  );
			
		}
		this.setParameter( ++index, companyCode );
		StringBuilder sbul = new StringBuilder(baseQuery);
		if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null  ) {
			sbul.append( "AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
			sbul.append(Integer.parseInt(gpaBillingEntriesFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
			sbul.append(" AND ");
			sbul.append(Integer.parseInt(gpaBillingEntriesFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
			sbul.append(" ");
			
		}
		/*if ( gpaBillingEntriesFilterVO.getToDate() != null  ) {
			sbul.append( "to_date( ? ");
			this.setParameter(
					++index, gpaBillingEntriesFilterVO.getToDate().toDisplayDateOnlyFormat());
			sbul.append( ",'DD-MON-YYYY')  ");

		}*/
		if ( status != null && status.trim().length() > 0  ) {
			sbul.append( "AND DTL.BLGSTA = ? ");
			this.setParameter( ++index, status );
		}
		if ( gpaCode != null && gpaCode.trim().length() > 0  ) {
			sbul.append( "AND DTL.UPDBILTOOPOA = ? ");
			this.setParameter( ++index, gpaCode );
		}
		if ( country != null && country.trim().length() > 0  ) {
			sbul.append( "AND DTL.CNTCOD = ? ");
			this.setParameter( ++index, country );
		}
		//Added by A-4809 for BUG ICRD-17509 ...Starts
		if (gpaBillingEntriesFilterVO.getConDocNumber() !=null &&
				gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){
			sbul.append("AND MST.CSGDOCNUM = ?");
			this.setParameter(++index, gpaBillingEntriesFilterVO.getConDocNumber());
		}
		if (gpaBillingEntriesFilterVO.getDsnNumber() !=null &&
				gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
			sbul.append("AND MST.DSN = ?");
			this.setParameter(++index, gpaBillingEntriesFilterVO.getDsnNumber());
		}
		if(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!=null &&
				gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
			sbul.append("AND MST.ORGEXGOFC  = ?");
			this.setParameter(++index, gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
		}
		if(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!=null &&
				gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
			sbul.append("AND MST.DSTEXGOFC  = ?");
			this.setParameter(++index, gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
		}
		//Added as part of ICRD-205027 starts
		if(gpaBillingEntriesFilterVO.getMailbagId()!=null &&
				gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
			sbul.append("AND MST.MALIDR  = ?");
			this.setParameter(++index, gpaBillingEntriesFilterVO.getMailbagId());
		}
		//Added as part of ICRD-205027 ends
		if(category!=null &&
				category.trim().length() >0){
			sbul.append("AND MST.MALCTGCOD   = ?");
			this.setParameter(++index, gpaBillingEntriesFilterVO.getMailCategoryCode());
		}
		if(gpaBillingEntriesFilterVO.getMailSubclass()!=null &&
				gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
			sbul.append("AND MST.MALSUBCLS    = ?");
			this.setParameter(++index, gpaBillingEntriesFilterVO.getMailSubclass());
		}
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
		//Added by A-6991 for CR ICRD-137019 ...Starts
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
		//Added by A-4809 for CR ICRD-137019 ...Ends


		//Added by A-4809 for BUG ICRD-17509 ...Ends
		//Added by A-4809 for CR ICRD-258393....Starts
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
		//Added by A-4809 for CR ICRD-258393....Ends
		return sbul.toString();
    }

}
