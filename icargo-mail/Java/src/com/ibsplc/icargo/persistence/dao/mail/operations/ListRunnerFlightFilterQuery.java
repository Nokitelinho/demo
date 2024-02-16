/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ListRunnerFlightFilterQuery.java
 *
 *	Created by	:	A-5526
 *	Created on	:	12-Oct-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.util.Objects;

import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ListRunnerFlightFilterQuery.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5526	:	12-Oct-2018	:	Draft
 */
public class ListRunnerFlightFilterQuery extends PageableNativeQuery<RunnerFlightVO> {

	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	private RunnerFlightFilterVO runnerFlightFilterVO;
	private String baseQry;
	private String additionalQuery;
	private boolean isOracleDataSource;
	
	private static final String LISTAGG_DWSBLK_CONIDR =  "listagg(DWSBLK.CONIDR,',') within GROUP(ORDER BY DWSBLK.CONIDR)";
	private static final String STRINGAGG_DWSBLK_CONIDR  = "string_agg(DWSBLK.CONIDR, ',' ORDER BY DWSBLK.CONIDR)";
	private static final String CAST_INTEGER  = ":: INTEGER";
	private static final String EMPTY  = "";
	private static final String LIKE_BULK  = "%BULK%";
	
	public ListRunnerFlightFilterQuery(int pageSize, int totalRecordsCount, boolean isOracleDataSource, RunnerFlightFilterVO runnerFlightFilterVO,
			String baseQuery,String additionalQuery, RunnerFlightMultiMapper runnerFlightMultiMapper) throws SystemException {
		super(pageSize, totalRecordsCount, runnerFlightMultiMapper);
		this.runnerFlightFilterVO = runnerFlightFilterVO;
		this.baseQry = getDBSpecificQueryForListRunners(baseQuery);
		this.additionalQuery = additionalQuery;
		this.isOracleDataSource = isOracleDataSource;
	}
	
	private String getDBSpecificQueryForListRunners(String qry){
		if(Objects.isNull(qry)){
			return null;
		}
		if(this.isOracleDataSource){
			return String.format(qry, LISTAGG_DWSBLK_CONIDR, EMPTY, LIKE_BULK);
		}else{
			return String.format(qry, STRINGAGG_DWSBLK_CONIDR, CAST_INTEGER, LIKE_BULK);
		}
	}

	public String getNativeQuery() {
		log.entering("ListRunnerFlightFilterQuery", "getNativeQuery");
		int index = 0;
		StringBuilder mainQuery = new StringBuilder();
		StringBuilder stringBuilder = new StringBuilder().append(baseQry);
		this.setParameter(++index, runnerFlightFilterVO.getCompanyCode());
		this.setParameter(++index, runnerFlightFilterVO.getAirportCode());
		if (RunnerFlightVO.RUN_DIRECTION_OUTBOUND.equals(runnerFlightFilterVO.getRunDirection())) {
			index = setOutboundFromFlightDate(index, stringBuilder);
			if (runnerFlightFilterVO.getOutboundFlightCarrierCode() != null
					&& runnerFlightFilterVO.getOutboundFlightCarrierCode().trim().length() > 0) {
				stringBuilder.append("AND EXPFLT.FLTCARCOD = ?");
				this.setParameter(++index, runnerFlightFilterVO.getOutboundFlightCarrierCode());
			}
			if (runnerFlightFilterVO.getOutboundFlightNumber() != null
					&& runnerFlightFilterVO.getOutboundFlightNumber().trim().length() > 0) {
				stringBuilder.append("AND EXPFLT.FLTNUM = ?");
				this.setParameter(++index, runnerFlightFilterVO.getOutboundFlightNumber());
			}
			if (runnerFlightFilterVO.getOutboundFlightDate() != null) {
				stringBuilder.append("AND TO_CHAR(LEG.STD,'dd-MON-YYYY') = ?");
				this.setParameter(++index, runnerFlightFilterVO.getOutboundFlightDate());
			}
		} else if (RunnerFlightVO.RUN_DIRECTION_INBOUND.equals(runnerFlightFilterVO.getRunDirection())) {
			if (RunnerFlightVO.LISTTYPE_INBOUND.equals(runnerFlightFilterVO.getInboundListType())) {
				this.setParameter(++index, runnerFlightFilterVO.getAirportCode());
				this.setParameter(++index, runnerFlightFilterVO.getAirportCode());
				index = setInboundToFlightDate(index, stringBuilder);
				if (runnerFlightFilterVO.getInboundFlightCarrierCode() != null
						&& runnerFlightFilterVO.getInboundFlightCarrierCode().trim().length() > 0) {
					stringBuilder.append("AND MALCON.FLTCARCOD = ?");
					this.setParameter(++index, runnerFlightFilterVO.getInboundFlightCarrierCode());
				}
				if (runnerFlightFilterVO.getInboundFlightNumber() != null
						&& runnerFlightFilterVO.getInboundFlightNumber().trim().length() > 0) {
					stringBuilder.append("AND MALCON.FLTNUM = ?");
					this.setParameter(++index, runnerFlightFilterVO.getInboundFlightNumber());
				}
				if (runnerFlightFilterVO.getInboundFlightDate() != null) {
					stringBuilder.append("AND TO_CHAR(LEG.STA,'dd-MON-YYYY') = ?");
					this.setParameter(++index, runnerFlightFilterVO.getInboundFlightDate());
				}
			} else if (RunnerFlightVO.LISTTYPE_REFUSAL.equals(runnerFlightFilterVO.getInboundListType())) {
				if (runnerFlightFilterVO.getInboundFromFlightDate() != null
						&& runnerFlightFilterVO.getInboundToFlightDate() != null) {
					index = updateInboundToFlightDate(index, stringBuilder);
				}
				if (runnerFlightFilterVO.getInboundFlightCarrierCode() != null
						&& runnerFlightFilterVO.getInboundFlightCarrierCode().trim().length() > 0) {
					stringBuilder.append("AND EXPFLT.FLTCARCOD = ?");
					this.setParameter(++index, runnerFlightFilterVO.getInboundFlightCarrierCode());
				}
				if (runnerFlightFilterVO.getInboundFlightNumber() != null
						&& runnerFlightFilterVO.getInboundFlightNumber().trim().length() > 0) {
					stringBuilder.append("AND EXPFLT.FLTNUM= ?");
					this.setParameter(++index, runnerFlightFilterVO.getInboundFlightNumber());
				}
				if (runnerFlightFilterVO.getInboundFlightDate() != null) {
					stringBuilder.append("AND TO_CHAR(LEG.STD,'dd-MON-YYYY') = ?");
					this.setParameter(++index, runnerFlightFilterVO.getInboundFlightDate());
				}
			}
		}
		if (RunnerFlightVO.RUN_DIRECTION_INBOUND.equals(runnerFlightFilterVO.getRunDirection()) 
				&& RunnerFlightVO.LISTTYPE_REFUSAL.equals(runnerFlightFilterVO.getInboundListType())) {
			mainQuery=new StringBuilder(stringBuilder).append(additionalQuery);
		}else {	
		mainQuery.append(stringBuilder);
		}	
		mainQuery.append(") RESULT_TABLE");
		mainQuery.append(" ORDER BY RESULT_TABLE.FLTDAT");
		return mainQuery.toString();

	}

	private int updateInboundToFlightDate(int index, StringBuilder stringBuilder) {
		if (this.isOracleDataSource) {
		 stringBuilder.append("AND TO_NUMBER(TO_CHAR(COALESCE(LEG.ATD, LEG.ETD, LEG.STD),'YYYYMMDDhh24miss')) ");
		stringBuilder.append(" BETWEEN TO_NUMBER(TO_CHAR(?,'YYYYMMDDhh24miss')) AND TO_NUMBER(TO_CHAR(?,'YYYYMMDDhh24miss'))");
		}
		else {
			stringBuilder.append("AND COALESCE(LEG.ATD, COALESCE(LEG.ETD, LEG.STD)) BETWEEN ? AND ?");
		}
		
		this.setParameter(++index, runnerFlightFilterVO.getInboundFromFlightDate());
		this.setParameter(++index, runnerFlightFilterVO.getInboundToFlightDate());
		return index;
	}

	private int setInboundToFlightDate(int index, StringBuilder stringBuilder) {
		if (runnerFlightFilterVO.getInboundFromFlightDate() != null
				&& runnerFlightFilterVO.getInboundToFlightDate() != null) {
			if (this.isOracleDataSource) {
				stringBuilder.append(
						"AND TO_NUMBER(TO_CHAR(COALESCE(LEG.ATA, LEG.ETA, LEG.STA),'YYYYMMDDhh24miss')) ");
				stringBuilder.append(
						" BETWEEN TO_NUMBER(TO_CHAR(?,'YYYYMMDDhh24miss')) AND TO_NUMBER(TO_CHAR(?,'YYYYMMDDhh24miss'))");
			} else {
				stringBuilder.append("AND COALESCE(LEG.ATA, COALESCE(LEG.ETA, LEG.STA)) BETWEEN ? AND ?");
			}
			this.setParameter(++index, runnerFlightFilterVO.getInboundFromFlightDate());
			this.setParameter(++index, runnerFlightFilterVO.getInboundToFlightDate());
		}
		return index;
	}

	private int setOutboundFromFlightDate(int index, StringBuilder stringBuilder) {
		if (runnerFlightFilterVO.getOutboundFromFlightDate() != null
				&& runnerFlightFilterVO.getOutboundToFlightDate() != null) {
			if(this.isOracleDataSource){ 
			 stringBuilder.append(" AND TO_NUMBER(TO_CHAR(COALESCE(LEG.ATD, LEG.ETD, LEG.STD),'YYYYMMDDhh24miss')) ");
			 stringBuilder.append("BETWEEN TO_NUMBER(TO_CHAR(?,'YYYYMMDDhh24miss')) AND TO_NUMBER(TO_CHAR(?,'YYYYMMDDhh24miss'))");
			}else {
				stringBuilder.append("AND COALESCE(LEG.ATD, COALESCE(LEG.ETD, LEG.STD)) BETWEEN ? AND ?");
			 }
			
			this.setParameter(++index, runnerFlightFilterVO.getOutboundFromFlightDate());
			this.setParameter(++index, runnerFlightFilterVO.getOutboundToFlightDate());
		}
		return index;
	}
}
