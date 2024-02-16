/*
 * MonitorULDStockFliterQuery.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */

/**
 * Revision History
 * ----------------------------------------------------------------------------
 * Version 		Date 		Author 				Description
 * --------------------------------------------------------------------------
 * 0.1			22Jul11		Yasheen				Restructured the query for tuning[ICRD-3019] 
 */
public class MonitorULDStockFliterQuery  extends PageableNativeQuery<ULDStockListVO> {

	 private Log log = LogFactory.getLogger(" ULD_DEFAULTS");

	 private ULDStockConfigFilterVO uldStockConfigFilterVO;
	 private String baseQuery ;
	 private static final String FACILITY_TYPE_ONETIM="uld.defaults.facilitytypes";
	private static final String ULD_SORTING_ORDER_DESC ="DESC";

	private static final String ULD_SORTING_ORDER_ASC ="ASC";

/**
 *
 * @param uldStockConfigFilterVO
 * @param baseQuery
 * @throws SystemException
 */
    public MonitorULDStockFliterQuery(String baseQuery,MultiMapper<ULDStockListVO> findULDStockListMapper,ULDStockConfigFilterVO uldStockConfigFilterVO
    		) throws SystemException {
		//super();
    	super(uldStockConfigFilterVO.getTotalRecordsCount(),findULDStockListMapper);
		this.baseQuery = baseQuery;
		this.uldStockConfigFilterVO = uldStockConfigFilterVO;
		log.log(Log.INFO, "\n\nFilter VO from MonitorULDStockFliterQuery",
				uldStockConfigFilterVO);
    }

  /**
   *
   * @return
   */
    public String getNativeQuery() {
    	log.entering("ULDAgreementFilterQuery","getNativeQuery");
		int index = 0;
		int airlineIdentifier = uldStockConfigFilterVO.getAirlineIdentifier();
		 //ICRD-334152
		int ownerAirlineIdentifier = uldStockConfigFilterVO.getOwnerAirlineIdentifier();
		String uldTypeCode = uldStockConfigFilterVO.getUldTypeCode();
		String uldNature = uldStockConfigFilterVO.getUldNature();
    	StringBuilder stbld = new StringBuilder().append(baseQuery);
    	String facilities[] = null;
    	String facilityValues[] = null;
    	String airport = null;
    	String airportgroups[] = null;
    	Collection<OneTimeVO> levelTypeValues = new ArrayList<OneTimeVO>();

    	stbld.append("  Z.ARLIDR AS ARLIDR , Z.ARPCOD AS ARPCOD, ")
    			.append(" Z.ULDGRPCOD AS ULDGRPCOD,Z.ULDTYP AS ULDTYP,Z.ULDNAT AS ULDNAT,")
    			.append(" Z.INSTOCK AS INSTOCK, Z.INSTOCK-Z.NONOPR AS AVASPERSYSTEM,Z.NONOPR AS NONOPR,  ")
    			.append(" Z.OFFARP AS OFFARP, Z.MAXQTY AS MAXQTY,Z.MINQTY AS MINQTY,  ")
    			.append(" Z.INSTOCK - Z.NONOPR - COALESCE (Z.MAXQTY, 0) AS BALANCE,  ")
    			.append(" SUM ( Z.INSTOCK - Z.NONOPR - COALESCE ( Z.MAXQTY, 0 ) ) OVER   ")
    			.append(" ( PARTITION BY Z.ARLIDR, Z.ARPCOD, Z.ULDGRPCOD )TOTAL_BALANCE,(  ")
//    			.append(" SELECT DECODE (APHCODUSE, 2, TWOAPHCOD, THRAPHCOD ) ARLCOD")
    			.append(" SELECT case when APHCODUSE = 2 then TWOAPHCOD else THRAPHCOD end ARLCOD")
    			.append(" FROM SHRARLMST ARL WHERE ARL.CMPCOD = Z.CMPCOD AND ARL.ARLIDR = Z.ARLIDR ) ARLCOD        ")
    			.append(" FROM ( ")
    			.append(" ( SELECT CMPCOD, ARLIDR, ARPCOD, ULDTYP, ULDNAT,ULDGRPCOD, ")
    			.append(" MAX(MAXQTY) MAXQTY, MAX(MINQTY)MINQTY, ")
    		
    		.append(" SUM ( ")
    		.append(" CASE WHEN DMGSTA <> 'S' AND OVLSTA = 'N' ");

	    	if(ULDStockConfigFilterVO.LEVELTYPE_LOCATION.equals(uldStockConfigFilterVO.getLevelType()) &&
	    			uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0 ){

	    		facilities = uldStockConfigFilterVO.getLevelValue().split("&");
	    		airport = facilities[0];
				
				log.log(Log.INFO, "\n facilities[1]", facilities);
				facilities = facilities[1].split(",");
	    		
	    		for(int i = 0;i < facilities.length;i++){
	    			facilityValues = facilities[i].split("-");
	    			if(i == 0){
	    				int count=0;
	    				stbld.append(" AND( FACTYP = ? AND LOC = ?  ");
	    				for(OneTimeVO levelTypeValue:levelTypeValues){
							if(levelTypeValue.getFieldDescription().equalsIgnoreCase(facilityValues[0])){
								this.setParameter(++index,levelTypeValue.getFieldValue());
								count++;
								break;
							}
						}
	    					if(count==0){
							this.setParameter(++index,facilityValues[0]);
						}
	    				this.setParameter(++index, facilityValues[1]);

	    			}else{
	    				int count=0;
	    				stbld.append(" OR (FACTYP = ? AND LOC = ? )  ");
	    				for(OneTimeVO levelTypeValue:levelTypeValues){
							if(levelTypeValue.getFieldDescription().equalsIgnoreCase(facilityValues[0])){
								this.setParameter(++index,levelTypeValue.getFieldValue());
								count++;
								break;
							}
						}
	    					if(count==0){
							this.setParameter(++index,facilityValues[0]);
						}
	    				this.setParameter(++index, facilityValues[1]);
	    			}
	    		}
	    		stbld.append(" ) ");
	    	}
	    	 if(airlineIdentifier != 0){
				  stbld.append(" AND ARLIDR = ? ");
				  this.setParameter(++index, airlineIdentifier);
			  }
    		stbld.append(" THEN 1 ELSE 0 END ) NONOPR,	 ");
	    	 if(airlineIdentifier != 0){
	    		 stbld.append("SUM( ")
				  .append(" CASE WHEN ARLIDR = ? ");
				  this.setParameter(++index, airlineIdentifier);
    	    if(ULDStockConfigFilterVO.LEVELTYPE_AGENT.equals(uldStockConfigFilterVO.getLevelType()) &&
	    			uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0 ){
    	    	stbld.append("  AND RELTOO IS NOT NULL AND TRNSTA <> 'Y'");
    	    }else{
    	    	stbld.append("  AND RELTOO IS NULL AND TRNSTA <> 'Y'");
    	    }
    	    //Added by A-2052 for the bug 102181 ends
    	    if(ULDStockConfigFilterVO.LEVELTYPE_LOCATION.equals(uldStockConfigFilterVO.getLevelType()) &&
	    			uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0 ){

	    		for(int i = 0;i < facilities.length;i++){
	    			facilityValues = facilities[i].split("-");
	    			if(i == 0){
	    				int count=0;
	    				stbld.append(" AND( FACTYP = ? AND LOC = ?  ");
	    				for(OneTimeVO levelTypeValue:levelTypeValues){
							if(levelTypeValue.getFieldDescription().equalsIgnoreCase(facilityValues[0])){
								this.setParameter(++index,levelTypeValue.getFieldValue());
								count++;
								break;
							}
						}
	    				if(count==0){

							this.setParameter(++index,facilityValues[0]);
						}
	    				this.setParameter(++index, facilityValues[1]);

	    			}else{
	    				int count=0;
	    				stbld.append(" OR ( FACTYP = ? AND LOC = ? )  ");
	    				for(OneTimeVO levelTypeValue:levelTypeValues){
							if(levelTypeValue.getFieldDescription().equalsIgnoreCase(facilityValues[0])){
								this.setParameter(++index,levelTypeValue.getFieldValue());
								count++;
								break;
							}
						}
	    				if(count==0){

							this.setParameter(++index,facilityValues[0]);
						}
	    				this.setParameter(++index, facilityValues[1]);
	    			}
	    		}
	    		stbld.append(" ) ");
	    	}
			if(ULDStockConfigFilterVO.LEVELTYPE_AGENT.equals(uldStockConfigFilterVO.getLevelType()) &&
					uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0 ){
				   stbld.append(" AND DMGSTA <> 'S' AND OVLSTA <> 'L' AND LONREFNUM > 0  AND AGTCOD = AGTCOD " )
				   .append(  "  AND LONREFNUM = LONREFNUM  ")
				   .append(  "  THEN 1  ELSE 0  END ) INSTOCK,  ");
			}else{
			stbld.append(" AND DMGSTA <> 'S' AND OVLSTA <> 'L'")
					.append(  "  THEN 1  ELSE 0  END ) INSTOCK,  ");
				    }
			}
    	    stbld.append("SUM (")
    	    .append(" CASE WHEN ");
	    	 if(airlineIdentifier != 0){
				  stbld.append("  ARLIDR = ? ");
				  this.setParameter(++index, airlineIdentifier);
			  }
    	    if(ULDStockConfigFilterVO.LEVELTYPE_LOCATION.equals(uldStockConfigFilterVO.getLevelType()) &&
	    			uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0 ){

	    		for(int i = 0;i < facilities.length;i++){
	    			facilityValues = facilities[i].split("-");
	    			if(i == 0){
	    				int count=0;
	    				stbld.append(" AND( FACTYP = ? AND LOC = ?  ");
	    				for(OneTimeVO levelTypeValue:levelTypeValues){
							if(levelTypeValue.getFieldDescription().equalsIgnoreCase(facilityValues[0])){
								this.setParameter(++index,levelTypeValue.getFieldValue());
								count++;
								break;
							}
						}
	    				if(count==0){

							this.setParameter(++index,facilityValues[0]);
						}
	    				this.setParameter(++index, facilityValues[1]);

	    			}else{
	    				int count=0;
	    				stbld.append(" OR ( FACTYP = ? AND LOC = ?  ) ");
	    				for(OneTimeVO levelTypeValue:levelTypeValues){
							if(levelTypeValue.getFieldDescription().equalsIgnoreCase(facilityValues[0])){
								this.setParameter(++index,levelTypeValue.getFieldValue());
								count++;
								break;
							}
						}
	    					if(count==0){

							this.setParameter(++index,facilityValues[0]);
						}
	    				this.setParameter(++index, facilityValues[1]);
	    			}
	    		}
	    		stbld.append(" ) ");
	    	}
    		stbld.append(" 	AND FACTYP IN ( 'AGT', 'RPR' ) THEN 1 ELSE 0 END ) OFFARP ")
	    		.append("FROM ( SELECT MST.CMPCOD CMPCOD, MST.OPRARLIDR ARLIDR, MST.CURARP ARPCOD,")
	    		.append(" 	MST.ULDTYP ULDTYP, MST.ULDNAT ULDNAT,MST.LOC LOC ,")
	    		.append(" 	MST.ULDGRPCOD ULDGRPCOD, COALESCE(STK.MAXQTY,0)MAXQTY,")
	    		.append("	 COALESCE(STK.MINQTY,0)MINQTY, MST.DMGSTA, MST.OVLSTA,")
	    		.append(" 	MST.RELTOO, MST.TRNSTA, MST.FACTYP, MST.OWNARLIDR")
	    		.append("	 FROM ULDMST MST")
	    		.append(" LEFT OUTER JOIN ULDSTKCFG STK")
	    		.append(" ON  MST.CMPCOD    = STK.CMPCOD ")
	    		.append(" 	AND MST.OPRARLIDR = STK.ARLIDR ")
	    		.append(" 	AND MST.CURARP    = STK.ARPCOD  ")
	    		.append(" 	AND MST.ULDNAT    = STK.ULDNAT")
	    		.append(" 	AND MST.ULDTYP    = STK.ULDTYPCOD");
	    		if(ULDStockConfigFilterVO.LEVELTYPE_COUNTRY.equals(uldStockConfigFilterVO.getLevelType())){
		    		stbld.append(" INNER JOIN SHRARPMST ARP ")
		    			.append(" ON ARP.CMPCOD = MST.CMPCOD AND ARP.ARPCOD = MST.CURARP ");
		    	}else if(ULDStockConfigFilterVO.LEVELTYPE_REGION.equals(uldStockConfigFilterVO.getLevelType())){
		    		stbld.append(" INNER JOIN SHRARPMST ARP ")
		    		.append(" ON ARP.CMPCOD = MST.CMPCOD AND ARP.ARPCOD = MST.CURARP ")
		    		.append("INNER JOIN  SHRCNTMST CNT ")
		    		.append(" ON ARP.CMPCOD = CNT.CMPCOD AND ARP.CNTCOD = CNT.CNTCOD   ");
		    	}
	    		stbld.append(" WHERE MST.CMPCOD  = ?  ")
	    		.append("	AND MST.OPRARLIDR = ? ");
    		this.setParameter(++index, uldStockConfigFilterVO.getCompanyCode());
    		this.setParameter(++index, airlineIdentifier);
			//Added by A-8445 for CR IASCB-43732
    		if(ULDStockConfigFilterVO.LEVELTYPE_AIRPORTGROUP.equals(uldStockConfigFilterVO.getLevelType())){
    			stbld.append(" AND CURARP IN ")
    			.append(" (SELECT SHRDTL.grpent FROM SHRGENMSTGRPDTL SHRDTL ")
    				 .append(" WHERE shrdtl.cmpcod = ? ")
    				 .append(" AND shrdtl.grpcatcod='ULD' ");
    		this.setParameter(++index, uldStockConfigFilterVO.getCompanyCode());
    			if(uldStockConfigFilterVO.getLevelValue().contains(",")){
    				stbld.append(" AND shrdtl.grpnam IN ( ");
        			airportgroups = uldStockConfigFilterVO.getLevelValue().split(",");
        			for(int i = 0;i < airportgroups.length;i++){
        				if(i==0){
        					stbld.append("?");
        				} else {
        					stbld.append(",?");
        				}
        				this.setParameter(++index, airportgroups[i]);
        			}
        			stbld.append(" ) ) ) ");
        		} else {
        			stbld.append(" AND shrdtl.grpnam IN (?) ) ) ");
    		this.setParameter(++index, uldStockConfigFilterVO.getLevelValue());
        		}
    		}
	    	if(ULDStockConfigFilterVO.LEVELTYPE_LOCATION.equals(uldStockConfigFilterVO.getLevelType()) &&
	    			uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0 ){
	    		log.log(Log.INFO, "\n airport", airport);
				stbld.append(" AND MST.CURARP =  ? ");
				this.setParameter(++index, airport);

	    		facilities = uldStockConfigFilterVO.getLevelValue().split("&");
	    		airport = facilities[0];
				
				log.log(Log.INFO, "\n facilities[1]", facilities);
				facilities = facilities[1].split(",");
	    		
	    		for(int i = 0;i < facilities.length;i++){
	    			facilityValues = facilities[i].split("-");
	    			if(i == 0){
	    				int count=0;
	    				stbld.append(" AND( MST.FACTYP = ? AND MST.LOC = ?  ");
	    				for(OneTimeVO levelTypeValue:levelTypeValues){
							if(levelTypeValue.getFieldDescription().equalsIgnoreCase(facilityValues[0])){
								this.setParameter(++index,levelTypeValue.getFieldValue());
								count++;
								break;
							}
						}
	    					if(count==0){
							this.setParameter(++index,facilityValues[0]);
						}
	    				this.setParameter(++index, facilityValues[1]);

	    			}else{
	    				int count=0;
	    				stbld.append(" OR (MST.FACTYP = ? AND MST.LOC = ? )  ");
	    				for(OneTimeVO levelTypeValue:levelTypeValues){
							if(levelTypeValue.getFieldDescription().equalsIgnoreCase(facilityValues[0])){
								this.setParameter(++index,levelTypeValue.getFieldValue());
								count++;
								break;
							}
						}
	    					if(count==0){
							this.setParameter(++index,facilityValues[0]);
						}
	    				this.setParameter(++index, facilityValues[1]);
	    			}
	    		}
	    		stbld.append(" ) ");
	    	}
    		if (ULDStockConfigFilterVO.LEVELTYPE_AIRPORT.equals(uldStockConfigFilterVO.getLevelType())) {
				if (uldStockConfigFilterVO.getLevelValue() != null
						&& uldStockConfigFilterVO.getLevelValue().trim().length() > 0) {
					int count1 = 0;
					String[] returningPartyCodes = uldStockConfigFilterVO
							.getLevelValue().split(",");
					for (String rtnptycode : returningPartyCodes) {
						if (count1 == 0) {
							stbld.append(" AND CURARP IN (  ? ");
							this.setParameter(++index, rtnptycode);
						} else {
							stbld.append(" , ? ");
							this.setParameter(++index, rtnptycode);
						}
						count1 = 1;
					}
					if (count1 == 1) {
						stbld.append(" ) ");
					}
				}
			}
    		else if(ULDStockConfigFilterVO.LEVELTYPE_COUNTRY.equals(uldStockConfigFilterVO.getLevelType())){
	    		if(uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0){
	    			stbld.append(" AND ARP.CNTCOD = ? ");
	    			this.setParameter(++index, uldStockConfigFilterVO.getLevelValue());
	    		}
	    	}else if(ULDStockConfigFilterVO.LEVELTYPE_REGION.equals(uldStockConfigFilterVO.getLevelType())){
	    		if(uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0){
	    			stbld.append(" AND CNT.REGCOD = ? ");
	    			this.setParameter(++index, uldStockConfigFilterVO.getLevelValue());
	    		}
	    	}
    		if(uldStockConfigFilterVO.getUldGroupCode() != null
					&& uldStockConfigFilterVO.getUldGroupCode().trim().length() > 0){
				stbld.append(" AND MST.ULDGRPCOD = ? ");
				this.setParameter(++index, uldStockConfigFilterVO.getUldGroupCode());
			}
			if (uldTypeCode != null && uldTypeCode.trim().length() != 0) {
					stbld.append(" AND    MST.ULDTYP = ? ");
					this.setParameter(++index, uldTypeCode);
			}
			if (uldNature != null ) {
				stbld.append("  AND MST.ULDNAT = ? ");
				this.setParameter(++index, uldNature);
			}
			if (airlineIdentifier != 0) {
					stbld.append("  AND MST.OPRARLIDR = ? ");
					this.setParameter(++index, airlineIdentifier);
			}
			// ICRD-334152
			if(ownerAirlineIdentifier!=0) {
				stbld.append("  AND MST.OWNARLIDR = ? ");
				this.setParameter(++index, ownerAirlineIdentifier);
			}
    		stbld.append(" UNION ALL ")
	    		.append(" SELECT STK.CMPCOD CMPCOD, STK.ARLIDR ARLIDR, STK.ARPCOD ARPCOD, ")
	    		.append(" stk.ULDTYPCOD ULDTYP, stk.ULDNAT ULDNAT,NULL LOC,")
	    		.append(" STK.ULDGRPCOD ULDGRPCOD, STK.MAXQTY, STK.MINQTY,")
	    		.append(" NULL DMGSTA, NULL OVLSTA, NULL RELTOO, NULL TRNSTA,")
	    		.append(" NULL factyp, NULL OWNARLIDR")
	    		.append(" FROM ULDSTKCFG STK");
    		if(ULDStockConfigFilterVO.LEVELTYPE_COUNTRY.equals(uldStockConfigFilterVO.getLevelType())){
	    		stbld.append(" INNER JOIN SHRARPMST ARP ")
	    			.append(" ON ARP.CMPCOD = STK.CMPCOD AND ARP.ARPCOD = STK.ARPCOD ")
	    			.append(" AND STK.CMPCOD  =?");
	    	}else if(ULDStockConfigFilterVO.LEVELTYPE_REGION.equals(uldStockConfigFilterVO.getLevelType())){
	    		stbld.append(" INNER JOIN SHRARPMST ARP ")
	    		.append(" ON ARP.CMPCOD = STK.CMPCOD AND ARP.ARPCOD = STK.ARPCOD ")
	    		.append("INNER JOIN  SHRCNTMST CNT ")
	    		.append(" ON ARP.CMPCOD = CNT.CMPCOD AND ARP.CNTCOD = CNT.CNTCOD   ")
	    		.append(" AND STK.CMPCOD  =?");
	    	}else{
	    		stbld.append(" WHERE STK.CMPCOD  =? ");
	    	}
	    		
    		stbld.append(" AND STK.ARLIDR = ? ");
    		this.setParameter(++index, uldStockConfigFilterVO.getCompanyCode());
    		this.setParameter(++index, airlineIdentifier);
    		//Added by A-8445 for CR IASCB-43732
    		if(ULDStockConfigFilterVO.LEVELTYPE_AIRPORTGROUP.equals(uldStockConfigFilterVO.getLevelType())){
    			stbld.append(" AND ARPCOD IN ( ")
    				 .append("(SELECT SHRDTL.grpent FROM SHRGENMSTGRPDTL SHRDTL ")
    				 .append(" WHERE shrdtl.cmpcod = ? ")
    				 .append(" AND shrdtl.grpcatcod='ULD' ");
    		this.setParameter(++index, uldStockConfigFilterVO.getCompanyCode());
    			if(uldStockConfigFilterVO.getLevelValue().contains(",")){
    				stbld.append(" AND shrdtl.grpnam IN ( ");
        			airportgroups = uldStockConfigFilterVO.getLevelValue().split(",");
        			for(int i = 0;i < airportgroups.length;i++){
        				if(i==0){
        					stbld.append("?");
        				} else {
        					stbld.append(",?");
        				}
        				this.setParameter(++index, airportgroups[i]);
        			}
        			stbld.append(" ) ) ) ");
        		} else {
        			stbld.append(" AND shrdtl.grpnam IN (?) ) ) ");
    		this.setParameter(++index, uldStockConfigFilterVO.getLevelValue());
        		}
    		}
	    	if(ULDStockConfigFilterVO.LEVELTYPE_LOCATION.equals(uldStockConfigFilterVO.getLevelType()) &&
	    			uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0 ){
	    		log.log(Log.INFO, "\n airport", airport);
				stbld.append(" AND STK.ARPCOD =  ? ");
				this.setParameter(++index, airport);
	    	}
    		if (ULDStockConfigFilterVO.LEVELTYPE_AIRPORT.equals(uldStockConfigFilterVO.getLevelType())) {
    			if (uldStockConfigFilterVO.getLevelValue() != null
    					&& uldStockConfigFilterVO.getLevelValue().trim().length() > 0) {
						int count1 = 0;
						String[] returningPartyCodes = uldStockConfigFilterVO
								.getLevelValue().split(",");
						for (String rtnptycode : returningPartyCodes) {
							if (count1 == 0) {
								stbld.append(" AND ARPCOD IN (  ? ");
								this.setParameter(++index, rtnptycode);
							} else {
								stbld.append(" , ? ");
								this.setParameter(++index, rtnptycode);
							}
							count1 = 1;
						}
						if (count1 == 1) {
							stbld.append(" ) ");
						}
					}
				}
	    		else if(ULDStockConfigFilterVO.LEVELTYPE_COUNTRY.equals(uldStockConfigFilterVO.getLevelType())){
		    		if(uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0){
		    			stbld.append(" AND ARP.CNTCOD = ? ");
		    			this.setParameter(++index, uldStockConfigFilterVO.getLevelValue());
		    		}
		    	}else if(ULDStockConfigFilterVO.LEVELTYPE_REGION.equals(uldStockConfigFilterVO.getLevelType())){
		    		if(uldStockConfigFilterVO.getLevelValue() != null && uldStockConfigFilterVO.getLevelValue().trim().length() > 0){
		    			stbld.append(" AND CNT.REGCOD = ? ");
		    			this.setParameter(++index, uldStockConfigFilterVO.getLevelValue());
		    		}
		    	}
	    		if(uldStockConfigFilterVO.getUldGroupCode() != null
						&& uldStockConfigFilterVO.getUldGroupCode().trim().length() > 0){
					stbld.append(" AND STK.ULDGRPCOD = ? ");
					this.setParameter(++index, uldStockConfigFilterVO.getUldGroupCode());
				}
				if (uldTypeCode != null && uldTypeCode.trim().length() != 0) {
						stbld.append(" AND   STK.ULDTYPCOD = ? ");
						this.setParameter(++index, uldTypeCode);
				}
				if (uldNature != null ) {
					stbld.append("  AND STK.ULDNAT = ? ");
					this.setParameter(++index, uldNature);
				}
				if (airlineIdentifier != 0) {
						stbld.append("  AND STK.ARLIDR = ? ");
						this.setParameter(++index, airlineIdentifier);
				}
	    		stbld.append(" AND NOT EXISTS( ")
	    		.append("  	 SELECT '1' FROM ULDMST MST ")
	    		.append("	 WHERE MST.CMPCOD    = STK.CMPCOD AND ")
	    		.append(" 	  MST.OPRARLIDR = STK.ARLIDR AND ")
	    		.append(" 	  MST.CURARP    = STK.ARPCOD AND")
	    		.append(" 	  MST.ULDTYP    = STK.ULDTYPCOD AND")
	    		.append("  MST.ULDNAT    = STK.ULDNAT   )")
	    		.append(" ) mst GROUP BY CMPCOD, ARLIDR, ARPCOD, ULDTYP, ULDNAT, ULDGRPCOD");
    		
    		
    		if(uldStockConfigFilterVO.getSort()!=null) {

				if(ULD_SORTING_ORDER_DESC.equals(uldStockConfigFilterVO.getSort())){
					stbld.append("  ) )Z) mst1 ");
				}else if(ULD_SORTING_ORDER_ASC.equals(uldStockConfigFilterVO.getSort())){
					stbld.append("  ) )Z) mst1 ");
				}else{
					stbld.append("  )) Z) mst1 ");
				}
		   }else{
				stbld.append("  ) )Z) mst1 ");
		   }
    		log.log(Log.INFO, "!!!!QUERY", stbld.toString());
			return stbld.toString();
    }


}
