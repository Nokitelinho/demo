/*
 * BlgLineByParmeterFilterQuery.java created on Feb 28, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class BlgLineByParmeterFilterQuery extends PageableNativeQuery<BillingLineVO>{

	private String baseQuery;
	private BillingLineFilterVO billingLineFilterVO=null;
	private Log log = LogFactory.getLogger("MRA:DEFAULTS");
	private String querySufix;
	private String rankQuery;
	private static final String CLASS_NAME = "BillingLineFilterQuery";
	private boolean isOracleDataSource;

	/**
	 * Argumented constructor
	 * @param baseQuery
	 * @param billingLineFilterVO
	 * @throws SystemException
	 *
	 */
	public BlgLineByParmeterFilterQuery(BillingLineFilterVO billingLineFilterVO, String baseQuery, String querySufix, String rankQuery, BillingLineDetailsMultiMapper multiMapper , boolean isOracleDataSource)
	throws SystemException {

	    super(billingLineFilterVO.getTotalRecordsCount(), multiMapper);
		this.baseQuery=baseQuery;
		this.billingLineFilterVO=billingLineFilterVO;
	    this.baseQuery = baseQuery;
	    this.querySufix = querySufix;
	    this.rankQuery = rankQuery;
	    this.isOracleDataSource =isOracleDataSource;

	}

	/**
	 *
	 */
	public String getNativeQuery() {
		log.entering(CLASS_NAME,"getNativeQuery");
		//StringBuilder filterQuery=new StringBuilder(this.baseQuery);
		StringBuilder filterString = new StringBuilder("");
		boolean parExist=false;
		int index=0;
	    StringBuilder finalQuery = new StringBuilder();
	    finalQuery.append(rankQuery).append(baseQuery);
		if(billingLineFilterVO.getOrigin()!= null){
			if(billingLineFilterVO.getOriginLevel()!=null && "CTY".equals(billingLineFilterVO.getOriginLevel())) {
				filterString.append("ORGCTY:" + billingLineFilterVO.getOrigin() + ",");
				parExist = true;
			}
			else if(billingLineFilterVO.getOriginLevel()!=null && "ARP".equals(billingLineFilterVO.getOriginLevel())){
				filterString.append("ORGARPCOD:" + billingLineFilterVO.getOrigin() + ",");
				parExist = true;
			}
			else if(billingLineFilterVO.getOriginLevel()!=null && "CNT".equals(billingLineFilterVO.getOriginLevel())){
				filterString.append("ORGCNT:" + billingLineFilterVO.getOrigin() + ",");
				parExist = true;
			}
			else if(billingLineFilterVO.getOriginLevel()!=null && "REG".equals(billingLineFilterVO.getOriginLevel())){
				filterString.append("ORGREG:" + billingLineFilterVO.getOrigin() + ",");
				parExist = true;
			}
		}
		if(billingLineFilterVO.getDestination()!=null){
			if(billingLineFilterVO.getDestinationLevel()!=null && "CTY".equals(billingLineFilterVO.getDestinationLevel())) {
				filterString.append("DSTCTY:"+billingLineFilterVO.getDestination()+",");
				parExist = true;
			}
			else if(billingLineFilterVO.getDestinationLevel()!=null && "ARP".equals(billingLineFilterVO.getDestinationLevel())){
				filterString.append("DSTARPCOD:"+billingLineFilterVO.getDestination()+",");
				parExist = true;
			}
			else if(billingLineFilterVO.getDestinationLevel()!=null && "CNT".equals(billingLineFilterVO.getDestinationLevel())){
				filterString.append("DSTCNT:"+billingLineFilterVO.getDestination()+",");
				parExist = true;
			}
			else if(billingLineFilterVO.getDestinationLevel()!=null && "REG".equals(billingLineFilterVO.getDestinationLevel())){
				filterString.append("DSTREG:" + billingLineFilterVO.getDestination() + ",");
				parExist = true;
			}
		}
		if(billingLineFilterVO.getUplift()!=null){
			if(billingLineFilterVO.getUpliftLevel()!=null && "CTY".equals(billingLineFilterVO.getUpliftLevel())) {
				filterString.append("UPLCTY:"+billingLineFilterVO.getUplift()+",");
				parExist = true;
			}
			else if(billingLineFilterVO.getUpliftLevel()!=null && "ARP".equals(billingLineFilterVO.getUpliftLevel())){
				filterString.append("UPLARPCOD:"+billingLineFilterVO.getUplift()+",");
				parExist = true;
			}
			else if(billingLineFilterVO.getUpliftLevel()!=null && "CNT".equals(billingLineFilterVO.getUpliftLevel())){
				filterString.append("UPLCNT:"+billingLineFilterVO.getUplift()+",");
				parExist = true;
			}
		}
		if(billingLineFilterVO.getDischarge()!=null){
			if(billingLineFilterVO.getDischargeLevel()!=null && "CTY".equals(billingLineFilterVO.getDischargeLevel())) {
				filterString.append("DISCTY:"+billingLineFilterVO.getDischarge()+",");
				parExist = true;
			}
			else if(billingLineFilterVO.getDischargeLevel()!=null && "ARP".equals(billingLineFilterVO.getDischargeLevel())){
				filterString.append("DISARPCOD:"+billingLineFilterVO.getDischarge()+",");
				parExist = true;
			}
			else if(billingLineFilterVO.getDischargeLevel()!=null && "CNT".equals(billingLineFilterVO.getDischargeLevel())){
				filterString.append("DISCNT:"+billingLineFilterVO.getDischarge()+",");
				parExist = true;
			}
		}
		if(billingLineFilterVO.getMailCategoryCode()!=null && billingLineFilterVO.getMailCategoryCode().size()>0){
			String mailCat = billingLineFilterVO.getMailCategoryCode().toString().replace("[","" ).replace("]", "");
			filterString.append("CAT:"+mailCat+",");
			parExist = true;
		}
		if(billingLineFilterVO.getMailClass()!=null && billingLineFilterVO.getMailClass().size()>0){
			String mailClass = billingLineFilterVO.getMailClass().toString().replace("[","" ).replace("]", "");
			filterString.append("CLS:"+mailClass+",");
			parExist = true;
		}
		if(billingLineFilterVO.getMailSubclass()!=null && billingLineFilterVO.getMailSubclass().size()>0){
			String mailSubClass = billingLineFilterVO.getMailSubclass().toString().replace("[","" ).replace("]", "");
			filterString.append("SUBCLS:"+mailSubClass+",");
			parExist = true;
		}
		if(billingLineFilterVO.getUldType()!=null && billingLineFilterVO.getUldType().size()>0){
			String uldType = billingLineFilterVO.getUldType().toString().replace("[","" ).replace("]", "");
			filterString.append("ULDTYP:"+uldType+",");
			parExist = true;
		}
		
		if(isOracleDataSource) {
		this.setParameter(++index,filterString.toString());
		}else{
			if(filterString.length() > 0 && filterString.substring(filterString.length() - 1).equals(",")){
				this.setParameter(++index,filterString.substring(0, filterString.length() - 1));
			}else{
				this.setParameter(++index,filterString.toString());
			}
		}
		this.setParameter(++index,billingLineFilterVO.getCompanyCode());

		
		
/*		this.setParameter(++index, parFlt);
		this.setParameter(++index, parFlt);
		this.setParameter(++index,billingLineFilterVO.getCompanyCode());*/
		if(billingLineFilterVO.getBillingMatrixId()!=null){
			finalQuery.append("AND BLG.BLGMTXCOD = ?");
			this.setParameter(++index,billingLineFilterVO.getBillingMatrixId());
			parExist = true;
		}
		if(billingLineFilterVO.getUnitCode()!=null){
			finalQuery.append(" AND BLG.UNTCOD = ?");
			this.setParameter(++index,billingLineFilterVO.getUnitCode());
			parExist = true;
		}
		if(billingLineFilterVO.getPoaCode()!=null){
			finalQuery.append("AND BLG.BILTOOPTYCOD = ?");
			this.setParameter(++index,billingLineFilterVO.getPoaCode());
			parExist = true;
		}
		if(billingLineFilterVO.getAirlineCode()!=null){
			finalQuery.append("AND BLG.BILTOOPTYCOD = ?");
			this.setParameter(++index,billingLineFilterVO.getAirlineCode());
			parExist = true;
		}
		if(billingLineFilterVO.getBillingSector()!=null){
			finalQuery.append("AND BLG.BILSEC = ?");
			this.setParameter(++index,billingLineFilterVO.getBillingSector());
			parExist = true;
		}
		//Added for ICRD-162338 starts
		if(billingLineFilterVO.getBillingLineId()>0){
			finalQuery.append("AND BLG.BLGLINSEQNUM = ?");
			this.setParameter(++index,billingLineFilterVO.getBillingLineId());
			parExist = true;
		}
		//Added for ICRD-162338 ends
/*		if(billingLineFilterVO.getBillingLineStatus()!=null){
			filterQuery.append("AND BLG.BLGLINSTA = ?");
			this.setParameter(++index,billingLineFilterVO.getBillingLineStatus());
		}*/
/*		if(billingLineFilterVO.getValidityStartDate() != null ){
			filterQuery.append(" AND INC.VLDSTRDATLIN >= ? ");
			this.setParameter(++index,billingLineFilterVO.getValidityStartDate());
		}
		if(billingLineFilterVO.getValidityEndDate() != null ){
			filterQuery.append(" AND INC.VLDENDDATLIN <= ? ");
			this.setParameter(++index,billingLineFilterVO.getValidityEndDate());
		}*/
		if(billingLineFilterVO.getBillingLineStatus() != null &&
				billingLineFilterVO.getBillingLineStatus().trim().length() > 0){
			if("E".equals(billingLineFilterVO.getBillingLineStatus())){
        		LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        		finalQuery.append(" AND BLG.VLDENDDAT <? ");
        		this.setParameter(++index,currentDate.toSqlDate());
        	}else{
        		finalQuery.append(" AND BLG.BLGLINSTA = ? ");
	      	  	this.setParameter(++index,billingLineFilterVO.getBillingLineStatus());
        	}
			parExist = true;
		}
		if(billingLineFilterVO.getValidityStartDate() != null && billingLineFilterVO.getValidityEndDate() != null){
			if(parExist)
				{
				finalQuery.append(" AND ");
				}
			/*Commented as part of IASCB-73386*/
			/*finalQuery.append(" TRUNC(BLG.VLDSTRDAT) >= ? " +
					" AND  TRUNC(BLG.VLDENDDAT)  <= ? ");
			LocalDate date = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
				date.setTime(billingLineFilterVO.getValidityStartDate().getTime());
				this.setParameter(++index,date.toDisplayDateOnlyFormat());
				date.setTime(billingLineFilterVO.getValidityEndDate().getTime());
				this.setParameter(++index,date.toDisplayDateOnlyFormat());*/
			finalQuery.append("? BETWEEN  BLG.VLDSTRDAT AND BLG.VLDENDDAT " +
					" AND ? BETWEEN  BLG.VLDSTRDAT AND BLG.VLDENDDAT ");
				this.setParameter(++index,billingLineFilterVO.getValidityStartDate());
				this.setParameter(++index,billingLineFilterVO.getValidityEndDate());
			parExist = true;
		}else if(billingLineFilterVO.getValidityStartDate() != null){
			/*Commented as part of IASCB-73386*/
			/*finalQuery.append(" AND TRUNC(BLG.VLDSTRDAT) >= ? ");
			LocalDate date = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
				date.setTime(billingLineFilterVO.getValidityStartDate().getTime());
				this.setParameter(++index,date.toDisplayDateOnlyFormat());*/
			finalQuery.append(" AND ? BETWEEN  BLG.VLDSTRDAT AND BLG.VLDENDDAT ");
			this.setParameter(++index,billingLineFilterVO.getValidityStartDate());
			parExist = true;
		}else if(billingLineFilterVO.getValidityEndDate() != null ){
			/*Commented as part of IASCB-73386*/
			/*finalQuery.append(" AND  TRUNC(BLG.VLDENDDAT) <= ? ");
			LocalDate date = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			date.setTime(billingLineFilterVO.getValidityEndDate().getTime());
			this.setParameter(++index,date.toDisplayDateOnlyFormat());*/
			finalQuery.append(" AND ? BETWEEN  BLG.VLDSTRDAT AND BLG.VLDENDDAT ");
			this.setParameter(++index,billingLineFilterVO.getValidityEndDate());
			parExist = true;
		}
		String parFlt = null;
/*		if(parExist){
			parFlt ="Y";
	        	}else{
			parFlt="N";
		}*/
		if(filterString.toString()==null || filterString.toString().isEmpty()){
			parFlt="N";














        	}else{
			parFlt ="Y";
		}
		finalQuery.append(querySufix);
		this.setParameter(++index, parFlt);
		if("A".equals(billingLineFilterVO.getBillingLineStatus()) || ("E".equals(billingLineFilterVO.getBillingLineStatus()))){
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			this.setParameter(++index, currentDate.toSqlDate());
		}else if("C".equals(billingLineFilterVO.getBillingLineStatus()) || ("I".equals(billingLineFilterVO.getBillingLineStatus())) || "N".equals(billingLineFilterVO.getBillingLineStatus())){
			this.setParameter(++index, billingLineFilterVO.getBillingLineStatus());
		}
		this.setParameter(++index, parFlt);
		if("A".equals(billingLineFilterVO.getBillingLineStatus()) || ("E".equals(billingLineFilterVO.getBillingLineStatus()))){
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			this.setParameter(++index, currentDate.toSqlDate());
		}else if("C".equals(billingLineFilterVO.getBillingLineStatus()) || ("I".equals(billingLineFilterVO.getBillingLineStatus())) || "N".equals(billingLineFilterVO.getBillingLineStatus())){
			this.setParameter(++index, billingLineFilterVO.getBillingLineStatus());
		}
		finalQuery.append(") RESULT_TABLE");
		return finalQuery.toString();
















	}
}
