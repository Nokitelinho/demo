/*
 * AgreementOverLapFilterQuery.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;

/**
 * @author A-1347
 *
 */
public class AgreementOverLapFilterQuery extends NativeQuery {


	private Log log = LogFactory.getLogger("ULD_DEFAULTS");

	private static final String DELETE_FLAG="A";

	private ULDAgreementVO uldAgreementVO;

    private String baseQuery;


/**
 * 
 * @param uldAgreementVO
 * @param baseQry
 * @throws SystemException
 */
    public AgreementOverLapFilterQuery(
		ULDAgreementVO uldAgreementVO,String baseQry) throws SystemException {
		super();
		this.uldAgreementVO = uldAgreementVO;
		this.baseQuery = baseQry;
    }


  /**
   *
   * @return
   */
    public String getNativeQuery() {
        log.entering( "AgreementOverLapFilterQuery","getNativeQuery");
        StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
		int index = 0 ;
		this.setParameter(++index,uldAgreementVO.getCompanyCode());//1 FROM xml
		this.setParameter(++index,uldAgreementVO.getTxnType());//2 FROM xml
		this.setParameter(++index,uldAgreementVO.getPartyType());//3 FROM xml
		this.setParameter(++index,uldAgreementVO.getPartyCode());//4 FROM xml
		if(uldAgreementVO.getFromPartyType() != null &&
				uldAgreementVO.getFromPartyType().trim().length() > 0 ){
			stringBuilder.append(" AND FRMPTYTYP = ? "); //6
			this.setParameter(++index,uldAgreementVO.getFromPartyType());//6
		}
		if(uldAgreementVO.getFromPartyCode() != null &&
				uldAgreementVO.getFromPartyCode().trim().length() > 0 ){
			stringBuilder.append(" AND FRMPTYCOD = ? "); //6
			this.setParameter(++index,uldAgreementVO.getFromPartyCode());//6
		} 
		//this.setParameter(++index,DELETE_FLAG);//5 FROM xml

		if(uldAgreementVO.getAgreementNumber() != null &&
				uldAgreementVO.getAgreementNumber().trim().length() > 0 ){
			stringBuilder.append(" AND AGRMNTNUM <> ? "); //6
			this.setParameter(++index,uldAgreementVO.getAgreementNumber());//6
		}
		stringBuilder.append(" AND ( ")
		.append(" ? ")// 7 FROM DATE FROM CLIENT
		.append(" BETWEEN AGRMNTFRMDAT AND AGRMNTTOODAT ");
		this.setParameter(++index,uldAgreementVO.getAgreementFromDate().toCalendar());//7
		if(uldAgreementVO.getAgreementToDate() != null){
			stringBuilder.append(" OR ( ")
			.append(" ? ")// 8 TO DATE FROM CLIENT
			.append(" BETWEEN AGRMNTFRMDAT AND AGRMNTTOODAT ) ");
			this.setParameter(++index,uldAgreementVO.getAgreementToDate()==null?null:uldAgreementVO.getAgreementToDate().toCalendar());//8
		}
		if(uldAgreementVO.getAgreementToDate() != null){
			stringBuilder.append(" OR ( ")
			.append(" AGRMNTNUM = ( ")
			.append(" SELECT DISTINCT AGRMNTNUM FROM ULDAGRMNT WHERE ")
			.append(" CASE WHEN TRUNC(AGRMNTTOODAT)IS NULL THEN ")
			.append(" 	(SELECT AGRMNTNUM FROM ULDAGRMNT WHERE ")
			.append(" 	CMPCOD = ? AND  ")//9
			.append(" 	TXNTYP = ? AND  ")//10
			.append(" 	PTYTYP = ? AND  ")//11			
			.append(" 	PTYCOD = ?  ")//12
			.append(" AND	AGRMNTSTA = ?  ");//13
			this.setParameter(++index,uldAgreementVO.getCompanyCode());//9
			this.setParameter(++index,uldAgreementVO.getTxnType());//10
			this.setParameter(++index,uldAgreementVO.getPartyType());//11
			this.setParameter(++index,uldAgreementVO.getPartyCode());//12
			this.setParameter(++index,DELETE_FLAG);//13 
 
			if(uldAgreementVO.getAgreementNumber() != null){
						stringBuilder.append(" AND AGRMNTNUM <> ? "); //14
						this.setParameter(++index,uldAgreementVO.getAgreementNumber());//14
			}
			stringBuilder.append(" 	AND TRUNC(AGRMNTTOODAT) IS NULL AND ")
			.append(" 	?  ")      // 15 TO DATE FROM THE CLIENT
.append(" 	 >= AGRMNTFRMDAT) ELSE NULL  END = AGRMNTNUM ) )");
			this.setParameter(++index,uldAgreementVO.getAgreementToDate()==null?null:uldAgreementVO.getAgreementToDate().toCalendar());//15
		}
		if(uldAgreementVO.getAgreementToDate() == null){
			stringBuilder.append(" OR ( ")
			.append(" AGRMNTNUM = ( ")
			.append(" 	SELECT DISTINCT AGRMNTNUM FROM ULDAGRMNT WHERE ")
			.append(" 	CMPCOD = ? AND ")//16
			.append(" 	TXNTYP = ? AND ")//17
			.append(" 	PTYTYP = ? AND ")//18
			.append(" 	PTYCOD = ? ")//19
			.append(" AND	AGRMNTSTA = ? ");//20
			this.setParameter(++index,uldAgreementVO.getCompanyCode());//16
			this.setParameter(++index,uldAgreementVO.getTxnType());//17
			this.setParameter(++index,uldAgreementVO.getPartyType());//18
			this.setParameter(++index,uldAgreementVO.getPartyCode());//19
			this.setParameter(++index,DELETE_FLAG);//20
			if(uldAgreementVO.getAgreementNumber() != null){
					stringBuilder.append(" AND AGRMNTNUM <> ?	 ");//21
					this.setParameter(++index,uldAgreementVO.getAgreementNumber());//21
			}
			stringBuilder.append(" AND AGRMNTTOODAT IS NULL ) ) ");
		}
		if(uldAgreementVO.getAgreementToDate() != null){
			stringBuilder.append(" OR ( ")
			.append(" AGRMNTFRMDAT  BETWEEN ? ")//22 FROM DATE FROM CLIENT
			.append(" AND ? )");//23 TO DATE FROM CLIENT
			stringBuilder.append(" OR (  ")
			.append(" AGRMNTTOODAT BETWEEN ? ")//24 FROM DATE FROM CLIENT
			.append(" AND ? )");//25 TO DATE FROM CLIENT
			stringBuilder.append(" OR ( ")
			.append(" ? ")//26 FROM DATE FROM CLEINT
			.append(" BETWEEN AGRMNTFRMDAT AND AGRMNTTOODAT ) ");
			stringBuilder.append(" OR ( ")
			.append(" ? ") //27 TO DATE FROM CLIENT
			.append(" BETWEEN AGRMNTFRMDAT AND AGRMNTTOODAT	) ");
			this.setParameter(++index,uldAgreementVO.getAgreementFromDate().toCalendar());//22
			this.setParameter(++index,uldAgreementVO.getAgreementToDate()==null?null:uldAgreementVO.getAgreementToDate().toCalendar());//23
			this.setParameter(++index,uldAgreementVO.getAgreementFromDate());//24
			this.setParameter(++index,uldAgreementVO.getAgreementToDate()==null?null:uldAgreementVO.getAgreementToDate().toCalendar());//25
			this.setParameter(++index,uldAgreementVO.getAgreementFromDate());//26
			this.setParameter(++index,uldAgreementVO.getAgreementToDate()==null?null:uldAgreementVO.getAgreementToDate().toCalendar());//27
		} else {
			stringBuilder.append(" OR ( ")
			.append(" AGRMNTFRMDAT  >= ? )");//28 FROM DATE FROM CLIENT
			stringBuilder.append(" OR (  ")
			.append(" AGRMNTTOODAT >= ? ) ");//29 FROM DATE FROM CLIENT
			this.setParameter(++index,uldAgreementVO.getAgreementFromDate().toCalendar());//28
			this.setParameter(++index,uldAgreementVO.getAgreementFromDate().toCalendar());//29
		}
		stringBuilder.append(" )");

		log.log(Log.INFO, "Query:::", stringBuilder.toString());
		return stringBuilder.toString();

   }
}
