/*
 * ULDListPaginationFilterQuery.java Created on Dec 23, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.util.Objects;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class ULDListPaginationFilterQuery extends PageableNativeQuery<ULDListVO> {
	
	private Log log = LogFactory.getLogger("ULD_DEfaults");

	private static final String FACILITY_TYPE="FAC";
	private static final String COUNTRY_CODE="CNT";
	private static final String HEAD="HDQ";
	private static final String REGION="REG";
	private static final String AIRPORT="ARP";
	private ULDListFilterVO uldListFilterVO;

	/**
	 *
	 */
	private String baseQuery;
	private boolean isOracleDataSource;
	private static final String MODULE_NAME = "ULDListFilterQuery";

	
	/**
	 *
	 * @param uldListFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public ULDListPaginationFilterQuery(int totalRecordCount,String baseQuery,ULDListMapper mapper, ULDListFilterVO uldListFilterVO,boolean isOracleDataSource)
			throws SystemException {
		super(totalRecordCount,mapper);
		this.uldListFilterVO = uldListFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDataSource = isOracleDataSource;
	}
	
	/**
	 * @return
	 */
	public String getNativeQuery() {

		log.entering(MODULE_NAME, "getNativeQuery");
		int index = 0;
		log.log(Log.FINE, "The Filter Values ", uldListFilterVO);
		String companyCode = uldListFilterVO.getCompanyCode();
		String uldGroupCode = uldListFilterVO.getUldGroupCode();
		String uldTypeCode = uldListFilterVO.getUldTypeCode();
		String manufacturer = uldListFilterVO.getManufacturer();
		String uldNumber = uldListFilterVO.getUldNumber();
		String location = uldListFilterVO.getLocation();
		 LocalDate lastMovementDate = uldListFilterVO.getLastMovementDate();
		String currentStation = uldListFilterVO.getCurrentStation();
		String ownerStation = uldListFilterVO.getOwnerStation();
		String overallStatus = uldListFilterVO.getOverallStatus();
		String damageStatus = uldListFilterVO.getDamageStatus();
		String cleanlinessStatus = uldListFilterVO.getCleanlinessStatus();
		String levelValue = uldListFilterVO.getLevelValue();
		String airlineIdentifier = null;
		 String uldNature = uldListFilterVO.getUldNature();
		String oalUldOnly = uldListFilterVO.getOalUldOnly();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		 
		// Added by A-2412 for AgentFilter
		String agentCode = uldListFilterVO.getAgentCode();
		// Added by a-3045 for CR AirNZ415 starts
		 LocalDate fromDate = uldListFilterVO.getFromDate();
		 LocalDate toDate = uldListFilterVO.getToDate();
		 log.log(Log.FINE, "---------toDate ------->", toDate);
		log.log(Log.FINE, "---------uldListFilterVO.getToDate() ------->",
				uldListFilterVO.getToDate());
		String ownerAirlineIdentifier = null;
		 if (uldListFilterVO.getOwnerAirlineidentifier() != 0
					&& !(uldListFilterVO.getOwnerAirlineidentifier() < 0)) {
			 ownerAirlineIdentifier = String.valueOf(uldListFilterVO
						.getOwnerAirlineidentifier());
			}
		 
		// Added by a-3045 for CR AirNZ415 ends
		

		if (uldListFilterVO.getAirlineidentifier() != 0
				&& !(uldListFilterVO.getAirlineidentifier() < 0)) {
			airlineIdentifier = String.valueOf(uldListFilterVO
					.getAirlineidentifier());
		}

		
		
		StringBuilder stringBuilder = new StringBuilder(baseQuery);
		//addition for getting remaining days
		if(uldListFilterVO.isFromListULD()){
			StringBuilder uldTxnQuery = new StringBuilder();
			//stringBuilder.append(" ),ULDTXN AS(");
			uldTxnQuery.append(" WITH ULDTXN AS(");
			uldTxnQuery.append(" SELECT CMPCOD TXNCMPCOD, ULDNUM TXNULDNUM,");
			uldTxnQuery.append(" CAST  (  ( frelonprd -(to_number(TO_CHAR(to_date(?,'yyyy-mm-dd'),'ddmmyyyy')) - to_number(TO_CHAR(txndat,'ddmmyyyy')) ) ) as INTEGER)as dayrem");
			this.setParameter(++index, currentDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT).substring(0,10));

			/*	stringBuilder.append(" WHEN DMRFQY = 'W'   THEN ((FRELONPRD*7)-(FLOOR(((TRUNC (SYSDATE)- TRUNC(TXNDAT))))))");
			stringBuilder.append("  WHEN DMRFQY = 'F' THEN ((FRELONPRD*14)-(FLOOR(((TRUNC (SYSDATE)- TRUNC(TXNDAT))))))");
			stringBuilder.append("  WHEN DMRFQY = 'M' THEN ((FRELONPRD*30)-(FLOOR(((TRUNC (SYSDATE)- TRUNC(TXNDAT))))))  END dayrem ");*/
			uldTxnQuery.append("  FROM (SELECT  MIN(PRI) OVER(PARTITION BY A.ULDNUM,A.TXNREFNUM)MINPRI,A.* FROM (");
			uldTxnQuery.append(" SELECT CASE  WHEN AGRDTL.PTYTYP   = TXN.PTYTYP  AND AGRDTL.PTYCOD    = TXN.PTYCOD AND AGRDTL.FRMPTYCOD = TXN.RTNPTYCOD");
			uldTxnQuery.append(" AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND AGRDTL.ARPCOD    = TXN.TXNARPCOD THEN 1");
			uldTxnQuery.append(" WHEN AGRDTL.PTYTYP   = TXN.PTYTYP AND AGRDTL.PTYCOD    = TXN.PTYCOD AND AGRDTL.FRMPTYCOD = TXN.RTNPTYCOD AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND AGRDTL.ARPCOD    = 'HDQ' THEN 2");
			uldTxnQuery.append("  WHEN AGRDTL.PTYTYP   = TXN.PTYTYP  AND AGRDTL.PTYCOD    = TXN.PTYCOD  AND AGRDTL.FRMPTYCOD = TXN.RTNPTYCOD  THEN 3");
			uldTxnQuery.append("   WHEN AGRDTL.PTYTYP   = TXN.PTYTYP AND AGRDTL.PTYCOD   IN( 'ALL AIRLINES','ALL AGENTS','ALL OTHERS')  AND AGRDTL.FRMPTYCOD = TXN.RTNPTYCOD");
			uldTxnQuery.append("  AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND AGRDTL.ARPCOD    = TXN.TXNARPCOD  THEN 4");
			uldTxnQuery.append(" WHEN AGRDTL.PTYTYP   = TXN.PTYTYP ");
			uldTxnQuery.append("   AND AGRDTL.PTYCOD   IN( 'ALL AIRLINES','ALL AGENTS','ALL OTHERS')   AND AGRDTL.FRMPTYCOD = TXN.RTNPTYCOD    AND TXN.TXNTYP       = 'L'");
			uldTxnQuery.append("   AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3) AND AGRDTL.ARPCOD    = 'HDQ'  THEN 5");
			uldTxnQuery.append("   WHEN AGRDTL.PTYTYP   = TXN.PTYTYP  AND AGRDTL.PTYCOD   IN( 'ALL AIRLINES','ALL AGENTS','ALL OTHERS')   AND AGRDTL.FRMPTYCOD = TXN.RTNPTYCOD  THEN 6");
			uldTxnQuery.append("  WHEN AGRDTL.PTYTYP    = TXN.PTYTYP   AND AGRDTL.PTYCOD    IN( TXN.PTYCOD,'ALL AIRLINES','ALL AGENTS','ALL OTHERS')  AND AGRDTL.FRMPTYCOD IN( 'ALL AIRLINES','ALL OTHERS')");
			uldTxnQuery.append(" AND AGRDTL.ULDTYPCOD  = SUBSTR(TXN.ULDNUM,1,3)   AND AGRDTL.ARPCOD     = TXN.TXNARPCOD THEN 7");
			uldTxnQuery.append(" WHEN AGRDTL.PTYTYP    = TXN.PTYTYP  AND AGRDTL.PTYCOD    IN( TXN.PTYCOD,'ALL AIRLINES','ALL AGENTS','ALL OTHERS') AND AGRDTL.FRMPTYCOD IN( 'ALL AIRLINES','ALL OTHERS')  ");
			uldTxnQuery.append("    AND AGRDTL.ULDTYPCOD  = SUBSTR(TXN.ULDNUM,1,3)  AND AGRDTL.ARPCOD     = 'HDQ' THEN 8");
			uldTxnQuery.append("   WHEN AGRDTL.PTYTYP    = TXN.PTYTYP  AND AGRDTL.PTYCOD    IN(TXN.PTYCOD, 'ALL AIRLINES','ALL AGENTS','ALL OTHERS')");
			uldTxnQuery.append("    AND AGRDTL.FRMPTYCOD IN( 'ALL AIRLINES','ALL OTHERS')   THEN 9");
			uldTxnQuery.append("   WHEN AGRDTL.PTYTYP   = TXN.PTYTYP   AND AGRDTL.PTYCOD    = 'ALL'    AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3)");
			uldTxnQuery.append("   AND AGRDTL.ARPCOD    = TXN.TXNARPCOD  THEN 10");
			uldTxnQuery.append("  WHEN AGRDTL.PTYTYP   = TXN.PTYTYP AND AGRDTL.PTYCOD    = 'ALL'  AND AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3)   AND AGRDTL.ARPCOD    = 'HDQ' THEN 11");
			uldTxnQuery.append("  WHEN agrdtl.FRMPTYCOD = 'ALL' THEN 12  WHEN agrdtl.ptycod = 'ALL' THEN 13");
			uldTxnQuery.append("   END PRI ,TXN.TXNREFNUM, TXN.ULDNUM,TXN.CMPCOD, TXN.TXNDAT,");
			uldTxnQuery.append("  CASE    WHEN AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3)   AND AGRDTL.ARPCOD    IN (TXN.TXNARPCOD,'HDQ')");
			uldTxnQuery.append("  AND TXN.TXNDAT BETWEEN AGRDTL.DTLAGRMNTFRMDAT AND COALESCE(   AGRDTL.DTLAGRMNTTOODAT,TO_DATE('31-DEC-2999','DD-MON-RRRR'))");
			uldTxnQuery.append("   AND to_date(?,'yyyy-mm-dd') <= COALESCE(AGRDTL.AGRMNTTOODAT,TO_DATE('31-DEC-2999','DD-MON-RRRR'))");
			this.setParameter(++index, currentDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT).substring(0,10));
			uldTxnQuery.append("  THEN AGRDTL.DTLFRELONPRD  ELSE AGRDTL.FRELONPRD END FRELONPRD,");
			uldTxnQuery.append("  CASE  WHEN AGRDTL.ULDTYPCOD = SUBSTR(TXN.ULDNUM,1,3)    AND AGRDTL.ARPCOD    IN (TXN.TXNARPCOD,'HDQ')   AND TXN.TXNDAT BETWEEN AGRDTL.DTLAGRMNTFRMDAT AND COALESCE(");
			uldTxnQuery.append("   AGRDTL.DTLAGRMNTTOODAT,TO_DATE('31-DEC-2999','DD-MON-RRRR'))  AND to_date(?,'yyyy-mm-dd') <= COALESCE(AGRDTL.DTLAGRMNTTOODAT,TO_DATE(  '31-DEC-2999','DD-MON-RRRR'))");
			this.setParameter(++index, currentDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT).substring(0,10));
			uldTxnQuery.append(" THEN AGRDTL.DTLDMRFQY    ELSE AGRDTL.DMRFQY END DMRFQY   FROM (");
			uldTxnQuery.append("SELECT AGR.CMPCOD,AGR.AGRMNTNUM , AGR.PTYTYP , AGR.TXNTYP ,AGR.PTYCOD , AGR.FRMPTYTYP , AGR.FRMPTYCOD,AGR.FRELONPRD,  AGR.CURCOD ,AGR.DMRFQY,    DTL.DMRFQY DTLDMRFQY , ");
			uldTxnQuery.append(" AGR.AGRMNTFRMDAT, AGR.AGRMNTTOODAT,  DTL.AGRMNTFRMDAT DTLAGRMNTFRMDAT,  DTL.AGRMNTTOODAT DTLAGRMNTTOODAT,  DTL.FRELONPRD AS DTLFRELONPRD,    DTL.TAXAMT DTLTAXAMT,");
			uldTxnQuery.append("  DTL.CURCOD AS DTLCURCOD, DTL.ARPCOD AS ARPCOD, DTL.ULDTYPCOD  FROM ULDAGRMNT AGR LEFT OUTER JOIN ULDAGRMNTDTL DTL  ON");
			uldTxnQuery.append("  AGR.CMPCOD      = DTL.CMPCOD  AND AGR.AGRMNTNUM = DTL.AGRMNTNUM");
			uldTxnQuery.append(" WHERE  AGR.AGRMNTSTA = 'A'  AND AGR.CMPCOD  = ? )  AGRDTL , ");
			if(Objects.nonNull(uldListFilterVO.getUldNumber())){
				uldTxnQuery.append("(select txnmst.uldnum,txnmst.ptytyp,txnmst.ptycod,txnmst.rtnptycod,");
				uldTxnQuery.append( " txnmst.txnarpcod, txnmst.txntyp,txnmst.txnrefnum,  txnmst.cmpcod,  txnmst.txndat, txnmst.txnsta");
				uldTxnQuery.append(" from uldtxnmst txnmst, uldmst uld where txnmst.CMPCOD =uld.CMPCOD ");
				uldTxnQuery.append( " and txnmst.ULDNUM =uld.uldnum  and  txnmst.ULDNUM =?");
				this.setParameter(++index, uldListFilterVO.getUldNumber());
			}else{
				uldTxnQuery.append("(select txnmst.uldnum,txnmst.ptytyp,txnmst.ptycod,txnmst.rtnptycod,");
				uldTxnQuery.append( " txnmst.txnarpcod, txnmst.txntyp,txnmst.txnrefnum,  txnmst.cmpcod,  txnmst.txndat, txnmst.txnsta");
				uldTxnQuery.append(" from uldtxnmst txnmst, uldmst uld where txnmst.CMPCOD =uld.CMPCOD");
				uldTxnQuery.append( " and txnmst.ULDNUM =uld.uldnum  and  uld.oprarlidr = "+airlineIdentifier);
			}
			uldTxnQuery.append(" )TXN  WHERE ");
			uldTxnQuery.append("  TXN.CMPCOD      = AGRDTL.CMPCOD  AND AGRDTL.TXNTYP = TXN.TXNTYP AND ");
			uldTxnQuery.append("(  (AGRDTL.PTYCOD            = TXN.PTYCOD   AND AGRDTL.PTYTYP          = TXN.PTYTYP    AND AGRDTL.FRMPTYCOD       = TXN.RTNPTYCOD )");
			uldTxnQuery.append("  OR( TXN.PTYTYP = 'A'   AND AGRDTL.FRMPTYCOD       = 'ALL AIRLINES' )");
			uldTxnQuery.append(" OR (TXN.PTYTYP = 'A'  AND AGRDTL.PTYCOD          = 'ALL AIRLINES' )");
			uldTxnQuery.append(" OR (TXN.PTYTYP  = 'G'  AND AGRDTL.PTYCOD    = 'ALL AGENTS' )");
			uldTxnQuery.append(" OR (TXN.PTYTYP = 'O' AND AGRDTL.PTYCOD  = 'ALL OTHERS' )");
			uldTxnQuery.append(" OR(AGRDTL.PTYTYP  = 'L'    AND AGRDTL.PTYCOD = 'ALL' ");
			uldTxnQuery.append("  AND SUBSTR(TXN.ULDNUM,1,3) = COALESCE(AGRDTL.ULDTYPCOD,SUBSTR(TXN.ULDNUM,1,3))) ");
			uldTxnQuery.append("  OR (AGRDTL.FRMPTYTYP        = 'L'   AND AGRDTL.FRMPTYCOD       = 'ALL' " );
			uldTxnQuery.append("AND SUBSTR(TXN.ULDNUM,1,3) = COALESCE(AGRDTL.ULDTYPCOD,SUBSTR( TXN.ULDNUM,1,3))))");
			uldTxnQuery.append(" AND (TRUNC(TXN.TXNDAT) BETWEEN TRUNC(AGRDTL.AGRMNTFRMDAT) AND COALESCE(TRUNC(AGRDTL.AGRMNTTOODAT),TO_DATE('01-DEC-2999','DD-MON-RRRR')))");
			uldTxnQuery.append("  AND TXN.TXNSTA = 'T'   AND TXN.CMPCOD = ? AND TXN.TXNTYP = 'L'  ");
			//filter add as available
			this.setParameter(++index, companyCode);
			this.setParameter(++index, companyCode);
			/*if(uldNumber!=null){
				stringBuilder.append("   AND TXN.ULDNUM=? ");
				this.setParameter(++index, uldNumber);
			}
			if(currentStation!=null){
				stringBuilder.append("   AND  TXN.TXNARPCOD =? ");
				this.setParameter(++index, currentStation);
			}*/
			uldTxnQuery.append("   ORDER BY  TXN.ULDNUM ,TXN.TXNREFNUM)A)B WHERE  B.PRI = B.MINPRI )  ");
			//stringBuilder.append("  SELECT * FROM lstuld,ULDTXN WHERE LSTULD.CMPCOD  =ULDTXN.TXNCMPCOD(+)  AND lstuld.uldnum= ULDTXN.TXNULDNUM(+) ");
			StringBuilder finalQuery = uldTxnQuery.append(stringBuilder);
			stringBuilder = (finalQuery);
		}

		if(uldListFilterVO.isFromListULD()){
			//Added for bugfix Loaned To was not correct...
			stringBuilder.append(" (SELECT MIN(PTYCOD) FROM ULDTXNMST TXN")
				.append(" WHERE   ULD.CMPCOD =TXN.CMPCOD  AND ")
				.append(" ULD.ULDNUM= TXN.ULDNUM AND ")
				.append(" ULD.LONREFNUM=TXN.TXNREFNUM ")
				.append(" AND TXN.TXNSTA = 'T') LOANEDTO, ");
		}else{
			stringBuilder.append(" NULL AS LOANEDTO , ");
		}
		stringBuilder.append(" (CASE WHEN ULD.FACTYP IN ('RPR','AGT') ")
			.append(" THEN 'Y' ELSE 'N' END ) OFFSTA  ");
          	
		
		if(COUNTRY_CODE.equals(uldListFilterVO.getLevelType())){
			stringBuilder.append(", ARP.CNTCOD ");
		}
		if(REGION.equals(uldListFilterVO.getLevelType())){
			stringBuilder.append(", CNT.REGCOD ");
		}

		//Modified by A-3415 for ICRD-114031 Starts
		stringBuilder.append(" , CASE WHEN SUBSTR(ULD.ULDNUM,-2) = OPRARL.TWOAPHCOD THEN '11' ELSE SUBSTR(ULD.ULDNUM,-2) END ULDSRT ");
		//Modified by A-3415 for ICRD-114031 Ends		
		if(uldListFilterVO.isFromListULD()) {
			stringBuilder.append(",uldtxn.txncmpcod txncmpcod,");
			stringBuilder.append("uldtxn.txnuldnum txnuldnum,");
			stringBuilder.append("uldtxn.dayrem dayrem ");
		}
		//Modified by A-3415 for ICRD-114031 Starts
		stringBuilder.append(" FROM SHRARLMST MST, SHRARLMST OPRARL, ULDMST ULD ");
		//Modified by A-3415 for ICRD-114031 Ends
		
		// Added by A-5135 for ICRD-43243
		// Modified by A-3415 for ICRD-114031 Ends
		/* Added by A-5135 for ICRD-43243 starts */
			stringBuilder
			.append(" LEFT OUTER JOIN ULDDMG  DMG   ON (ULD.CMPCOD  = DMG.CMPCOD AND ULD.ULDNUM  = DMG.ULDNUM  AND DMG.CLSDAT IS NULL)");
			stringBuilder.append(" LEFT OUTER JOIN ULDTXN ON (ULDTXN.TXNCMPCOD = ULD.CMPCOD");
			stringBuilder.append("  AND ULDTXN.TXNULDNUM = ULD.ULDNUM)");
			
			if(COUNTRY_CODE.equals(uldListFilterVO.getLevelType())){
				stringBuilder.append(" INNER JOIN shrarpmst   arp"
						+ "  ON( uld.cmpcod = arp.cmpcod "
						+ "AND uld.curarp = arp.arpcod) ");
			}
			if(REGION.equals(uldListFilterVO.getLevelType())){
				stringBuilder.append(" INNER JOIN shrarpmst arp"
						+ "  ON( uld.cmpcod = arp.cmpcod "
						+ "AND uld.curarp = arp.arpcod) ");
				stringBuilder.append(" INNER JOIN SHRCNTMST cnt"
						+ "  ON( ARP.CMPCOD = CNT.CMPCOD "
						+ "AND ARP.CNTCOD = CNT.CNTCOD) ");
			}	
		stringBuilder.append("  WHERE ULD.CMPCOD = MST.CMPCOD ");		
		stringBuilder
				.append(" AND ULD.OWNARLIDR = MST.ARLIDR AND ULD.CMPCOD = ? ");
		this.setParameter(++index, companyCode);

		// Modified by A-3415 for ICRD-114031 Starts
			stringBuilder.append(" AND ULD.CMPCOD = OPRARL.CMPCOD ");
			stringBuilder.append(" AND ULD.OPRARLIDR = OPRARL.ARLIDR ");
		
		stringBuilder
				.append(" AND (DMG.DMGSEQNUM IS NULL OR DMG.DMGSEQNUM = (SELECT MAX(DMGSEQNUM)");
		stringBuilder
				.append(" FROM ULDDMG DMG WHERE ULD.CMPCOD = DMG.CMPCOD AND ULD.ULDNUM = DMG.ULDNUM))");
		/* Added by A-5135 for ICRD-43243 ends */
			if (airlineIdentifier != null && airlineIdentifier.trim().length() > 0) {
					stringBuilder.append(" AND ULD.OPRARLIDR = ? ");
			this.setParameter(++index, Integer.parseInt(airlineIdentifier));
		}
		if (uldGroupCode != null && uldGroupCode.trim().length() > 0) {
			  stringBuilder.append(" AND ULD.ULDGRPCOD = ?  ");
			this.setParameter(++index, uldGroupCode);
		   }
		if (uldTypeCode != null && uldTypeCode.trim().length() > 0) {
			  stringBuilder.append("  AND ULD.ULDTYP = ?  ");
			this.setParameter(++index, uldTypeCode);
		   }
		//modified as part of ICRD-324854
		if (manufacturer != null && manufacturer.trim().length() > 0) {
			  stringBuilder.append("  AND UPPER(ULD.MFR) =?  ");
			this.setParameter(++index, manufacturer.toUpperCase());
		  }
		if (uldNumber != null && uldNumber.trim().length() > 0) {
			  stringBuilder.append("  AND ULD.ULDNUM = ?  ");
			this.setParameter(++index, uldNumber);
		  }
		if (oalUldOnly != null && ("on".equalsIgnoreCase(oalUldOnly) && uldListFilterVO.getAirlineCode() != null)){ 
			//stringBuilder.append(" AND ULD.ULDNUM not like '%?'"); 
			//stringBuilder.append("?'");
			//this.setParameter(++index, uldListFilterVO.getAirlineCode()); 
			stringBuilder.append(" AND ULD.ULDNUM NOT LIKE ?");
			StringBuilder builder = new StringBuilder("%").append(uldListFilterVO.getAirlineCode());			
			this.setParameter(++index, builder.toString());
			}
		// Added By Preet for bug Num 5846		
		// This is to pick ULD numbers having alphanumeric characters --eg
		// AKE45J8KZ
		if ((uldListFilterVO.getUldRangeFrom() > -1)
				&& (uldListFilterVO.getUldRangeTo() > -1)) {
			// Modified by A-3415 for ICRD-114094 Starts
			stringBuilder
					.append(" AND TO_NUMBER(TRANSLATE(SUBSTR (ULD.ULDNUM,4,(LENGTH (ULD.ULDNUM)-3-LENGTH");
			if(!isOracleDataSource){
				stringBuilder.append("((select distinct (CASE when SHR.APHCODUSE = 2 THEN SHR.TWOAPHCOD  ELSE SHR.THRAPHCOD END ) ");
			}else{
				stringBuilder.append("((SELECT DISTINCT (DECODE(APHCODUSE,2, TWOAPHCOD,THRAPHCOD))");
			}
			stringBuilder.append("FROM SHRARLMST SHR WHERE SHR.CMPCOD =? AND SHR.ARLIDR = ULD.OWNARLIDR ))))");
			stringBuilder
					.append(",'ABCDEFGHIJKLMNOPQRSTUVWXYZ','0000000000000000000000000')) BETWEEN ?  AND ? ");
                       
			this.setParameter(++index, companyCode);
			// Modified by A-3415 for ICRD-114094 Ends
			this.setParameter(++index, uldListFilterVO.getUldRangeFrom());
			this.setParameter(++index, uldListFilterVO.getUldRangeTo());
		}
		
		// Added by Preet for Bug num 5846 --ends
		if (location != null && location.trim().length() > 0) {
			  stringBuilder.append("  AND  ULD.LOC=?  ");
			this.setParameter(++index, location);
		  }
		if (lastMovementDate != null) {
			  stringBuilder.append(" AND TRUNC(ULD.LSTMVTDAT) < ? ");
			this.setParameter(++index, lastMovementDate);
		   }
		if (currentStation != null && currentStation.trim().length() > 0) {
			stringBuilder.append(" AND  ULD.CURARP =  ? ");
			this.setParameter(++index, currentStation);
		}
		if (uldListFilterVO.getLevelType() != null
				&& uldListFilterVO.getLevelType().trim().length() > 0) {
			if (AIRPORT.equals(uldListFilterVO.getLevelType())) {
				stringBuilder.append(" AND  ULD.CURARP = ? ");
				this.setParameter(++index, levelValue.toUpperCase());
			}
		}
		if (ownerStation != null && ownerStation.trim().length() > 0) {
			  stringBuilder.append("  AND  ULD.OWNARP=?  ");
			this.setParameter(++index, ownerStation);
		 }
		if (overallStatus != null && overallStatus.trim().length() > 0) {
			  stringBuilder.append(" AND   ULD.OVLSTA =?  ");
			this.setParameter(++index, overallStatus);
		  }
		if (damageStatus != null && damageStatus.trim().length() > 0) {
			  stringBuilder.append("  AND  ULD.DMGSTA=?  ");
			this.setParameter(++index, damageStatus);
		  }
		if (cleanlinessStatus != null && cleanlinessStatus.trim().length() > 0) {
			  stringBuilder.append("  AND  ULD.CLNSTA=?  ");
			this.setParameter(++index, cleanlinessStatus);
		  }

		if (uldNature != null && uldNature.trim().length() > 0) {
			if (!"ALL".equals(uldNature)) {
				  stringBuilder.append("  AND  ULD.ULDNAT =?  ");
				this.setParameter(++index, uldNature);
			  }
		}
		// Added by A-2412 for AgentFilter
		if (agentCode != null && agentCode.trim().length() > 0) {
			stringBuilder.append(" AND ULD.RELTOO = ? ");
			this.setParameter(++index, agentCode);
		  }
		// Added by a-3045 for CR AirNZ415 starts
		if (ownerAirlineIdentifier != null
				&& ownerAirlineIdentifier.trim().length() > 0) {
			  stringBuilder.append("  AND  ULD.OWNARLIDR = ?  ");
			  this.setParameter(++index, Integer.parseInt(ownerAirlineIdentifier));
		  }
		if (fromDate != null) {
			stringBuilder
					.append(" AND COALESCE(TRUNC(ULD.LSTMVTDAT),TRUNC(to_date(?,'yyyy-mm-dd'))) >= TRUNC(to_date(?,'yyyy-mm-dd hh24:mi:ss')) ");
			this.setParameter(++index, currentDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT).substring(0,10));
			this.setParameter(++index, fromDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT_TIME).substring(0,19));
		}
		if (toDate != null) {
			log.log(Log.FINE, "---------toDate ------->", toDate);
			stringBuilder
					.append(" AND COALESCE(TRUNC(ULD.LSTMVTDAT),TRUNC(to_date(?,'yyyy-mm-dd'))) <= TRUNC(to_date(?,'yyyy-mm-dd hh24:mi:ss')) ");
			this.setParameter(++index, currentDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT).substring(0,10));
			this.setParameter(++index, toDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT_TIME).substring(0,19));
		}	
		
		// Added by a-3045 for CR AirNZ415 ends
		if (uldListFilterVO.getLevelType() != null
				&& uldListFilterVO.getLevelType().length() > 0) {
			log.log(Log.FINE, "The LEVEL TYPE from filterQuery: ",
					uldListFilterVO.getLevelType());
			if (COUNTRY_CODE.equals(uldListFilterVO.getLevelType())) {
				if (levelValue != null && levelValue.trim().length() > 0) {
					log.log(Log.FINE, "The level Value is ", levelValue);
					stringBuilder.append(" AND  ARP.CNTCOD IN  ( ? ");
					this.setParameter(++index, levelValue);
					stringBuilder.append(" ) ");
				}
			} else if (REGION.equals(uldListFilterVO.getLevelType())) {
				if (levelValue != null && levelValue.length() > 0) {
					log.log(Log.FINE, "The level Value is ", levelValue);
					stringBuilder.append(" AND  CNT.REGCOD IN  ( ? ");
					this.setParameter(++index, levelValue);
					stringBuilder.append(" ) ");
				}
			
			}
			/**
			 * else if(HEAD.equals(uldListFilterVO.getLevelType())){
			 * }
			 **/
			else if (FACILITY_TYPE.equals(uldListFilterVO.getLevelType())) {
				if (levelValue != null && levelValue.length() > 0) {
					String levelType[] = levelValue.split(",");
					for (int i = 0; i < levelType.length; i++) {
						if (i == 0) {
							String[] facilityValues = levelType[i].split("-");
							stringBuilder
									.append(" AND (( ULD.FACTYP =? AND ULD.LOC =? ) ");
							this.setParameter(++index, facilityValues[0]);
							this.setParameter(++index, facilityValues[1]);
						} else {
							String[] facilityValues = levelType[i].split("-");
							stringBuilder
									.append(" OR (ULD.FACTYP =? AND ULD.LOC =? ) ");
							this.setParameter(++index, facilityValues[0]);
							this.setParameter(++index, facilityValues[1]);
						}
					}
					stringBuilder.append(" ) ");
	
				}
			}
		}
		
		if (uldListFilterVO.getContent() != null
				&& uldListFilterVO.getContent().trim().length() > 0) {
			stringBuilder.append(" AND ULD.CNT = ? ");
			this.setParameter(++index, uldListFilterVO.getContent());
		}
		// Changed by a-3045 for bug 46783 on 19May09 starts
		if ("Y".equals(uldListFilterVO.getOffairportFlag())) {
			
			stringBuilder.append(" AND ULD.FACTYP IN ('RPR','AGT') ");
		} else if ("N".equals(uldListFilterVO.getOffairportFlag())) {
			stringBuilder
					.append(" AND (ULD.FACTYP NOT IN ('RPR','AGT') OR ULD.FACTYP IS NULL) ");
		}
		
		// Added By A-6841 for CRQ ICRD-155382 Starts
		if ("Y".equals(uldListFilterVO.getOccupiedULDFlag())) {
			stringBuilder.append(" AND ULD.ULDOCPSTA = 'Y' ");
		} else if ("N".equals(uldListFilterVO.getOccupiedULDFlag())) {
			stringBuilder.append(" AND ULD.ULDOCPSTA = 'N' ");
		}
		if ("Y".equals(uldListFilterVO.getInTransitFlag())) {
			stringBuilder.append(" AND ULD.TRNSTA = 'Y' ");
			/**
			 * This is to be implemented during Operation Phase 4
			 * stringBuilder.append(
			 * "AND ((SEG.ULDNUM IS NOT NULL AND (SEG.ULDSTA IS NULL OR  SEG.ULDSTA <> 'C') ) "
			 * ); stringBuilder.append(
			 * "  OR OPRARP.ULDNUM IS NOT NULL OR  MTK.CONNUM IS NOT NULL OR ULD.TRNSTA = 'Y') "
			 * );
			 **/
		} else if ("N".equals(uldListFilterVO.getInTransitFlag())) {
			stringBuilder.append(" AND ULD.TRNSTA = 'N' ");
		}
		// Modified by A-3415 for ICRD-114031 Starts
		if (uldListFilterVO.getAirlineidentifier() != 0
				&& !(uldListFilterVO.getAirlineidentifier() < 0)) {
			stringBuilder
					.append(" ORDER BY CASE WHEN SUBSTR(ULD.ULDNUM,-2) = OPRARL.TWOAPHCOD THEN '11' ELSE SUBSTR(ULD.ULDNUM,-2) END , ULD.ULDNUM ");
		} else {
			stringBuilder.append(" ORDER BY ULD.ULDNUM ");
		}
		// Modified by A-3415 for ICRD-114031 Ends
		log.log(Log.FINE, "The query ------", stringBuilder.toString());
		return stringBuilder.toString();

	}
	

}
