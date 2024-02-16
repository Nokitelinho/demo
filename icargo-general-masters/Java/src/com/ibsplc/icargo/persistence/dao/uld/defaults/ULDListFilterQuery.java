/*
 * ULDListFilterQuery.java Created on Jan 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1936
 *
 */
public class ULDListFilterQuery extends NativeQuery {

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

	private static final String MODULE_NAME = "ULDListFilterQuery";

//	private StringBuilder builder = null;

	/**
	 *
	 * @param uldListFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public ULDListFilterQuery(ULDListFilterVO uldListFilterVO, String baseQuery)
			throws SystemException {
		super();
		this.uldListFilterVO = uldListFilterVO;
		this.baseQuery = baseQuery;
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
		String levelValue=uldListFilterVO.getLevelValue();
		 String  airlineIdentifier = null;
		 String uldNature = uldListFilterVO.getUldNature();
		 
		//Added by A-2412 for AgentFilter
		String agentCode = uldListFilterVO.getAgentCode();
		//Added by a-3045 for CR AirNZ415 starts
		 LocalDate fromDate = uldListFilterVO.getFromDate();
		 LocalDate toDate = uldListFilterVO.getToDate();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		 log.log(Log.FINE, "---------toDate ------->", toDate);
		log.log(Log.FINE, "---------uldListFilterVO.getToDate() ------->",
				uldListFilterVO.getToDate());
		String  ownerAirlineIdentifier = null;
		 if (uldListFilterVO.getOwnerAirlineidentifier() != 0
					&& !(uldListFilterVO.getOwnerAirlineidentifier() < 0)) {
			 ownerAirlineIdentifier = String.valueOf(uldListFilterVO
						.getOwnerAirlineidentifier());
			}
		 
        //Added by a-3045 for CR AirNZ415 ends
		

		if (uldListFilterVO.getAirlineidentifier() != 0
				&& !(uldListFilterVO.getAirlineidentifier() < 0)) {
			airlineIdentifier = String.valueOf(uldListFilterVO
					.getAirlineidentifier());
		}

		// Commented By Preet for bug Num 5846		
		/*if (uldTypeCode != null) {
			builder = new StringBuilder();
			if (uldTypeCode.length() == 3) {
	    		     builder.append(" SUBSTR(ULDNUM,4,(LENGTH(ULDNUM)-3-")
	    		     .toString();
			} else if (uldTypeCode.length() == 4) {
				builder.append("SUBSTR(ULDNUM,5,(LENGTH(ULDNUM)-4-").toString();
			}
		}*/
		//Commented by Manaf for INT ULD510
		//StringBuilder query = new StringBuilder();
		
		//Commented By Preet for bug Num 5846
		
		/*query.append(
						"LENGTH((select distinct (DECODE (APHCODUSE,2,TWOAPHCOD,THRAPHCOD))")
				.append(" FROM SHRARLMST SHR, ULDMST ULD");
		query.append("  WHERE  SHR.CMPCOD = ?")
				.append("AND  SHR.ARLIDR= ?))))").toString();
		this.setParameter(++index, companyCode);*/
		StringBuilder stringBuilder = new StringBuilder(baseQuery);
		if("PRINTULDINVENTORY".equals(uldListFilterVO.getPrintType())) {
			stringBuilder.append(" DISTINCT ULD.CMPCOD,MST.TWOAPHCOD,ULD.ULDTYP,ULD.ULDNUM,");
			stringBuilder.append(" ULD.CURARP,REGEXP_REPLACE(REPLACE(ULD.ULDNUM, CASE WHEN MST.APHCODUSE=3 ");
			stringBuilder.append(" THEN MST.THRAPHCOD ELSE MST.TWOAPHCOD END ,''), '[^0-9]', '') ULDNUMPRT ");
		}else {
		if(uldListFilterVO.isFromListULD()){
//			stringBuilder.append(" CASE WHEN TXNMST.PTYIDR = ? THEN NULL ")
//			.append(" ELSE TXNMST.PTYCOD END LOANEDTO , ");	
//				this.setParameter(++index ,airlineIdentifier);
			//Added for bugfix Loaned To was not correct...
			stringBuilder.append(" (SELECT  PTYCOD FROM ULDTXNMST TXN")
				.append(" WHERE   ULD.CMPCOD =TXN.CMPCOD  AND ")
				.append(" ULD.ULDNUM= TXN.ULDNUM AND ")
				.append(" ULD.LONREFNUM=TXN.TXNREFNUM )LOANEDTO , ");
		}else{
			stringBuilder.append(" NULL AS LOANEDTO , ");
		}
		stringBuilder.append(" (CASE WHEN ULD.FACTYP IN ('RPR','AGT') ")
			.append(" THEN 'Y' ELSE 'N' END ) OFFSTA  ");
          	
		
		//stringBuilder.append(" this.setParameter(++index, companyCode) ");
		if(COUNTRY_CODE.equals(uldListFilterVO.getLevelType())){
			stringBuilder.append(", ARP.CNTCOD ");
		}
		if(REGION.equals(uldListFilterVO.getLevelType())){
			stringBuilder.append(", CNT.REGCOD ");
		}
		
//		modified for bug 57458(Only transit status value to be  chked to display occupancy status of the uld)
		//if(uldListFilterVO.isOccupiedUld()){
			/*stringBuilder.append(", (CASE WHEN OPRARP.ULDNUM IS NOT NULL  THEN 'Y' ");
			stringBuilder.append(" WHEN MTK.CONNUM IS NOT NULL THEN 'Y' ");*/
			stringBuilder.append(", (CASE WHEN ULD.TRNSTA = 'Y' THEN 'Y' ");
			//stringBuilder.append(" WHEN SEG.ULDNUM IS NOT NULL AND (SEG.ULDSTA IS NULL OR  SEG.ULDSTA <> 'C') THEN  'Y' ");
			stringBuilder.append(" ELSE  'N' END)OCCSTA ");
		//}
		/*if(FACILITY_TYPE.equals(uldListFilterVO.getLevelType())){
			stringBuilder.append(", ULD.LOC ");
		}*/
		//Modified by A-3415 for ICRD-114031 Starts
		stringBuilder.append(" , CASE WHEN SUBSTR(ULD.ULDNUM,-2) = OPRARL.TWOAPHCOD THEN '11' ELSE SUBSTR(ULD.ULDNUM,-2) END ULDSRT ");		
		}
		stringBuilder.append(" FROM SHRARLMST MST, SHRARLMST OPRARL,ULDMST ULD");
		//Modified by A-3415 for ICRD-114031 Ends	
		/*if(COUNTRY_CODE.equals(uldListFilterVO.getLevelType())){
			stringBuilder.append("  ,SHRARPMST ARP ");
		}
		if(REGION.equals(uldListFilterVO.getLevelType())){
			stringBuilder.append(" ,SHRARPMST ARP ,SHRCNTMST CNT ");
		}
		if(!"PRINTULDINVENTORY".equals(uldListFilterVO.getPrintType())) {
		//if(uldListFilterVO.isOccupiedUld()){
			stringBuilder.append(" ,OPRARPULD OPRARP,OPRSEGULD SEG, MTKCONMST MTK");
			//Modified by A-3415 for ICRD-114094
			if(uldListFilterVO.isFromListULD() && uldListFilterVO.getAirlineidentifier() != 0
					&& !(uldListFilterVO.getAirlineidentifier() < 0)){
				stringBuilder.append(" ,ULDTXNMST TXNMST");
			}
		stringBuilder.append(" ,ULDDMG DMG");//Added by A-5135 for ICRD-43243
		}*/
/*		stringBuilder.append("  WHERE ULD.CMPCOD = MST.CMPCOD ");		
		stringBuilder.append(" AND ULD.OWNARLIDR = MST.ARLIDR AND ULD.CMPCOD = ? ");
			this.setParameter(++index,companyCode);
			//if(uldListFilterVO.isOccupiedUld()){
			//Modified by A-3415 for ICRD-114031 Starts
			stringBuilder.append(" AND ULD.CMPCOD = OPRARL.CMPCOD ");
			stringBuilder.append(" AND ULD.OPRARLIDR = OPRARL.ARLIDR ");*/
			//Modified by A-3415 for ICRD-114031 Ends
			if(!"PRINTULDINVENTORY".equals(uldListFilterVO.getPrintType())) {
				stringBuilder.append(" LEFT OUTER JOIN MTKCONMST  MTK ON (ULD.CMPCOD = MTK.CMPCOD AND ULD.ULDNUM = MTK.CONNUM) ");
				stringBuilder.append(" LEFT OUTER JOIN OPRARPULD  OPRARP ON (ULD.ULDNUM = OPRARP.ULDNUM AND ULD.CMPCOD = OPRARP.CMPCOD)");
				stringBuilder.append(" LEFT OUTER JOIN OPRSEGULD  SEG ON (ULD.ULDNUM = SEG.ULDNUM AND ULD.CMPCOD = SEG.CMPCOD) ");
				stringBuilder.append(" LEFT OUTER JOIN ULDDMG  DMG ON (ULD.CMPCOD      = DMG.CMPCOD AND ULD.ULDNUM      = DMG.ULDNUM AND DMG.CLSDAT     IS NULL) ");
				stringBuilder.append(" LEFT OUTER JOIN ULDTXNMST  TXNMST ON (ULD.CMPCOD      = TXNMST.CMPCOD AND ULD.ULDNUM      = TXNMST.ULDNUM) ");
		stringBuilder.append("  WHERE ULD.CMPCOD = MST.CMPCOD ");		
		stringBuilder.append(" AND ULD.OWNARLIDR = MST.ARLIDR AND ULD.CMPCOD = ? ");
			this.setParameter(++index,companyCode);
			//if(uldListFilterVO.isOccupiedUld()){
			//Modified by A-3415 for ICRD-114031 Starts
			stringBuilder.append(" AND ULD.CMPCOD = OPRARL.CMPCOD ");
			stringBuilder.append(" AND ULD.OPRARLIDR = OPRARL.ARLIDR ");
				stringBuilder.append(" AND (DMG.DMGSEQNUM IS NULL OR DMG.DMGSEQNUM = (SELECT MAX(DMGSEQNUM)");
				stringBuilder.append(" FROM ULDDMG DMG WHERE ULD.CMPCOD = DMG.CMPCOD AND ULD.ULDNUM = DMG.ULDNUM))");
				}
				/*Added by A-5135 for ICRD-43243 ends*/
			if (airlineIdentifier != null && airlineIdentifier.trim().length() > 0) {
				if(uldListFilterVO.isFromListULD() && !"PRINTULDINVENTORY".equals(uldListFilterVO.getPrintType())){
				/*	stringBuilder.append(" AND ULD.CMPCOD = TXNMST.CMPCOD(+) ")
						.append(" AND ULD.ULDNUM = TXNMST.ULDNUM(+) ")*/
					stringBuilder.append(" AND ((TXNMST.TXNSTA  IS  NULL OR TXNMST.TXNSTA IN ('R','I')) AND ULD.OPRARLIDR = ? ")
						.append(" OR TXNMST.TXNTYP = 'L' AND TXNMST.TXNSTA = 'T' AND TXNMST.RTNPTYIDR = ? ")
					.append(" OR TXNMST.TXNTYP = 'B' AND ULD.OPRARLIDR = ? ) ");
					this.setParameter(++index , Integer.parseInt(airlineIdentifier));
					this.setParameter(++index ,Integer.parseInt(airlineIdentifier));
					this.setParameter(++index ,Integer.parseInt(airlineIdentifier));
				}else{
					stringBuilder.append(" AND ULD.OPRARLIDR = ? ");
					this.setParameter(++index ,Integer.parseInt(airlineIdentifier));
				}
			}
			//}
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
		
		// Added By Preet for bug Num 5846		
		//This is to pick ULD numbers having alphanumeric characters --eg AKE45J8KZ
		if((uldListFilterVO.getUldRangeFrom()>-1) && (uldListFilterVO.getUldRangeTo()>-1)){
			 /* stringBuilder.append("  AND ").append(builder).append(query)
			  .append(" >=?");*/
			  
			//Modified by A-3415 for ICRD-114094 Starts 
			 stringBuilder.append(" AND TO_NUMBER(TRANSLATE(SUBSTR (ULD.ULDNUM,4,(LENGTH (ULD.ULDNUM)-3-LENGTH");
			 stringBuilder.append("((SELECT DISTINCT (DECODE(APHCODUSE,2, TWOAPHCOD,THRAPHCOD))");
			 stringBuilder.append("FROM SHRARLMST SHR WHERE SHR.CMPCOD =? AND SHR.ARLIDR = ULD.OWNARLIDR ))))");
			 stringBuilder.append(",'ABCDEFGHIJKLMNOPQRSTUVWXYZ','0000000000000000000000000')) BETWEEN ?  AND ? ");
                       
			   this.setParameter(++index,companyCode);
			   //this.setParameter(++index,airlineIdentifier);
			 //Modified by A-3415 for ICRD-114094 Ends 
			   this.setParameter(++index,uldListFilterVO.getUldRangeFrom());
			   this.setParameter(++index,uldListFilterVO.getUldRangeTo());
		  }
		/* if ((uldListFilterVO.getUldRangeTo() > -1)) {
			  stringBuilder.append("  AND ").append(builder).append(query)
			  .append(" <=? ");
			this.setParameter(++index, uldListFilterVO.getUldRangeTo());
		  }*/
		
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
		if(uldListFilterVO.getLevelType()!=null && uldListFilterVO.getLevelType().trim().length()>0){
			if(AIRPORT.equals(uldListFilterVO.getLevelType())){
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
		/*if (airlineIdentifier != null && airlineIdentifier.trim().length() > 0) {
			  stringBuilder.append("  AND  ULD.OPRARLIDR =?  ");
			this.setParameter(++index, airlineIdentifier);
		  }*/
		if (uldNature != null && uldNature.trim().length() > 0) {
			if (!"ALL".equals(uldNature)) {
				  stringBuilder.append("  AND  ULD.ULDNAT =?  ");
				this.setParameter(++index, uldNature);
			  }
		}
		//Added by A-2412 for AgentFilter
		if (agentCode != null && agentCode.trim().length() > 0){
			stringBuilder.append(" AND ULD.RELTOO = ? ");
			this.setParameter(++index, agentCode);
		  }
		//Added by a-3045 for CR AirNZ415 starts
		if (ownerAirlineIdentifier != null && ownerAirlineIdentifier.trim().length() > 0) {
			  stringBuilder.append("  AND  ULD.OWNARLIDR = ?  ");
			  this.setParameter(++index, Integer.parseInt(ownerAirlineIdentifier));
		  }
		if (fromDate != null) {
			  stringBuilder.append(" AND COALESCE(TRUNC(ULD.LSTMVTDAT),TRUNC(to_date(?,'yyyy-mm-dd'))) >= TRUNC(to_date(?,'yyyy-mm-dd hh24:mi:ss')) ");
				this.setParameter(++index, currentDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT).substring(0,10));
				this.setParameter(++index, fromDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT_TIME).substring(0,19));
		}
		if (toDate != null) {
			log.log(Log.FINE, "---------toDate ------->", toDate);
			stringBuilder.append(" AND COALESCE(TRUNC(ULD.LSTMVTDAT),TRUNC(to_date(?,'yyyy-mm-dd'))) <= TRUNC(to_date(?,'yyyy-mm-dd hh24:mi:ss')) ");
			this.setParameter(++index, currentDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT).substring(0,10));
			this.setParameter(++index, toDate.toStringFormat(ULDDefaultsPersistenceConstants.DATEFORMAT_TIME).substring(0,19));
		}	
        //Added by a-3045 for CR AirNZ415 ends
		
		if(uldListFilterVO.getLevelType()!=null && uldListFilterVO.getLevelType().length()>0){
			log.log(Log.FINE, "The LEVEL TYPE from filterQuery ",
					uldListFilterVO.getLevelType());
			/*	if(uldListFilterVO.getLevelType().equals("ARP")){
				log.log(Log.FINE,"The LEVEL TYPE IS equal");
				if(levelValue!=null && levelValue.trim().length()>0){
					log.log(Log.FINE,"The LEVEL value IS not null");
				 stringBuilder.append(" AND  ULD.CURARP IN  ( ");
					this.setParameter(++index, levelValue);
					stringBuilder.append(" ) ");
				}
			}*/
			if(COUNTRY_CODE.equals(uldListFilterVO.getLevelType())){
				stringBuilder.append(" AND ULD.CMPCOD = ARP.CMPCOD ");
				stringBuilder.append(" AND ULD.CURARP = ARP.ARPCOD ");
				if(levelValue !=null && levelValue.trim().length()>0){
					log.log(Log.FINE, "The level Value is ", levelValue);
					stringBuilder.append(" AND  ARP.CNTCOD IN  ( ? ");
					this.setParameter(++index, levelValue);
					stringBuilder.append(" ) ");
				}
			}
			else if(REGION.equals(uldListFilterVO.getLevelType())){
				stringBuilder.append(" AND ULD.CMPCOD = ARP.CMPCOD ");
				stringBuilder.append(" AND ULD.CURARP = ARP.ARPCOD ");
				stringBuilder.append(" AND ARP.CMPCOD = CNT.CMPCOD ");
				stringBuilder.append(" AND ARP.CNTCOD = CNT.CNTCOD ");
				if(levelValue !=null && levelValue.length()>0){
					log.log(Log.FINE, "The level Value is ", levelValue);
					stringBuilder.append(" AND  CNT.REGCOD IN  ( ? ");
					this.setParameter(++index, levelValue);
					stringBuilder.append(" ) ");
				}
			
			}
			else if(HEAD.equals(uldListFilterVO.getLevelType())){
				//if(levelValue !=null && levelValue.length()>0){
					//stringBuilder.append(" AND ");
				//}
			}
			else if(FACILITY_TYPE.equals(uldListFilterVO.getLevelType())){
				//stringBuilder.append(" AND ULD.CMPCOD = ARPLOC.CMPCOD ");
				//stringBuilder.append(" AND ULD.CURARP = ARPLOC.ARPCOD ");
				//stringBuilder.append(" AND ULD.FACTYP = ARPLOC.FACTYP ");
				//apr-whs,apr2-whs2,apr3-hws3
					if(levelValue!=null && levelValue.length()>0){
					
					String levelType[]=levelValue.split(",");
					for(int i=0;i<levelType.length;i++){
						if(i==0){
							String[] facilityValues=levelType[i].split("-");
							//log.log(Log.FINE,"The  facilityValues from the array---0---- "+facilityValues[0]);
							//log.log(Log.FINE,"The  facilityValues from the array---1----- "+facilityValues[1]);
								stringBuilder.append(" AND (( ULD.FACTYP =? AND ULD.LOC =? ) ");
									this.setParameter(++index,facilityValues[0]);
									this.setParameter(++index,facilityValues[1]);
						}else{
							String[] facilityValues=levelType[i].split("-");
							stringBuilder.append(" OR (ULD.FACTYP =? AND ULD.LOC =? ) ");
								this.setParameter(++index,facilityValues[0]);
								this.setParameter(++index,facilityValues[1]);
						}
					
					}
					stringBuilder.append(" ) ");
	
				}
			}
		}
		
		if(uldListFilterVO.getContent()!= null && uldListFilterVO.getContent().trim().length()>0){
			stringBuilder.append(" AND ULD.CNT = ? ");
			this.setParameter(++index, uldListFilterVO.getContent());
		}
		if("Y".equals(uldListFilterVO.getOffairportFlag())){
			
			//stringBuilder.append(" AND ULD.ISOFF = ? ");
			//this.setParameter(++index,UldDmgRprFilterVO.FLAG_YES);
			//stringBuilder.append(" AND (CASE WHEN ULD.FACTYP IN ('RPR','AGT') ");
			//stringBuilder.append(" THEN 'Y' ELSE 'N' END) = 'Y' ");
			stringBuilder.append(" AND ULD.FACTYP IN ('RPR','AGT') ");
		}else if("N".equals(uldListFilterVO.getOffairportFlag())){
			stringBuilder.append(" AND (ULD.FACTYP NOT IN ('RPR','AGT') OR ULD.FACTYP IS NULL)");
		}
		
		if("Y".equals(uldListFilterVO.getOccupiedULDFlag())){
			stringBuilder.append(" AND ULD.TRNSTA = 'Y' ").append(" AND ULD.ULDOCPSTA = 'Y' ");
			/** This is to be implemented during Operation  Phase 4**/
			//stringBuilder.append("AND ((SEG.ULDNUM IS NOT NULL AND (SEG.ULDSTA IS NULL OR  SEG.ULDSTA <> 'C') ) ");
			//stringBuilder.append ("  OR OPRARP.ULDNUM IS NOT NULL OR  MTK.CONNUM IS NOT NULL OR ULD.TRNSTA = 'Y') ");
		}else if("N".equals(uldListFilterVO.getOccupiedULDFlag())){
			stringBuilder.append(" AND ULD.TRNSTA = 'N' ").append(" AND ULD.ULDOCPSTA = 'N' ");
		}
		//Changed by a-3045 for bug 46783 on 19May09 ends
		//Modified by A-3415 for ICRD-114031 Starts
		if("PRINTULDINVENTORY".equals(uldListFilterVO.getPrintType())) { 
			stringBuilder.append (" ORDER BY MST.TWOAPHCOD,ULD.ULDTYP, TO_NUMBER(regexp_replace(ULD.ULDNUM, '[^0-9]', '')) "); 
		}else {
		if (uldListFilterVO.getAirlineidentifier() != 0
				&& !(uldListFilterVO.getAirlineidentifier() < 0)) {
			stringBuilder.append (" ORDER BY CASE WHEN SUBSTR(ULD.ULDNUM,-2) = OPRARL.TWOAPHCOD THEN '11' ELSE SUBSTR(ULD.ULDNUM,-2) END , ULD.ULDNUM ");
		}else{
		stringBuilder.append ("  ORDER BY ULD.ULDNUM ");
			}	
		}		
		//Modified by A-3415 for ICRD-114031 Ends
		log.log(Log.FINE, "The query-- ----", stringBuilder.toString());
		return stringBuilder.toString();

	}
	
}
