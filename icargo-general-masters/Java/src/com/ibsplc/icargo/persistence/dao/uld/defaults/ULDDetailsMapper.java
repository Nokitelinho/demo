/*
 * ULDDetailsMapper.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 */

/**
 * This class implements a Mapper<<ULDVO>
 * @author A-1347
 *
 */
public class ULDDetailsMapper implements Mapper<ULDVO> {

	private Log log= LogFactory.getLogger("ULD_DEFAULTS");

    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     */
	public ULDVO map(ResultSet rs) throws SQLException{
	  log.entering("INSIDE THE MAPPER","ULDDetailsMapper");
	  ULDVO uldVO =new ULDVO();
	  uldVO.setUldContour(rs.getString("ULDCNT"));
	  	Measure baseDimLength = new Measure(UnitConstants.DIMENSION,0.0,rs.getDouble("DISBASLEN"),rs.getString("DISDIMUNT"));
		Measure baseDimWidth = new Measure(UnitConstants.DIMENSION,0.0,rs.getDouble("DISBASWID"),rs.getString("DISDIMUNT"));
		Measure baseDimHeight = new Measure(UnitConstants.DIMENSION,0.0,rs.getDouble("DISBASHGT"),rs.getString("DISDIMUNT"));
		//Measure maxVolume = new Measure(UnitConstants.VOLUME,0.0,rs.getDouble("MAXVOL"),rs.getString("MAXVOLUNT"));
		Measure structWtLmt = new Measure(UnitConstants.WEIGHT,0.0,rs.getDouble("DISSTRWGT"),rs.getString("DISSTRWGTUNT"));
		Measure tareWeight = new Measure(UnitConstants.WEIGHT,0.0,rs.getDouble("DISTARWGT"),rs.getString("DISTARWGTUNT"));
		//Measure baggageWeight = new Measure(UnitConstants.WEIGHT,0.0,rs.getDouble("BAGWGT"),rs.getString("BAGWGTUNT"));
		//Measure weightLmt = new Measure(UnitConstants.WEIGHT,0.0,rs.getDouble("WGTLMT"),rs.getString("WGTLMTUNT"));

	  uldVO.setTareWeight(tareWeight);
	  //uldVO.setDisplayTareWeightUnit(rs.getString("DISTARWGTUNT"));
	  uldVO.setBaseHeight(baseDimHeight);
	  uldVO.setBaseLength(baseDimLength);
	  uldVO.setBaseWidth(baseDimWidth);
	  //uldVO.setDisplayDimensionUnit(rs.getString("DISDIMUNT"));
	  uldVO.setStructuralWeight(structWtLmt);
	  //uldVO.setDisplayStructuralWeightUnit(rs.getString("DISSTRWGTUNT"));
	  uldVO.setCleanlinessStatus(rs.getString("CLNSTA"));
	  uldVO.setCurrentStation(rs.getString("CURARP"));
	  uldVO.setCurrentValue(rs.getDouble("CURVAL"));
	  uldVO.setDisplayCurrentValue(rs.getDouble("DISCURVAL"));//uncommented by a-5505 for bug ICRD-126614
	  uldVO.setCurrentValueUnit(rs.getString("CURVALUNT"));
	  uldVO.setDamageStatus(rs.getString("DMGSTA"));
	  uldVO.setIataReplacementCost(rs.getDouble("ITAREPCST"));
	  uldVO.setDisplayIataReplacementCost(rs.getDouble("DISITAREPCST"));
	  uldVO.setDisplayIataReplacementCostUnit(rs.getString("ITAREPCSTUNT"));
	  uldVO.setLocation(rs.getString("LOC"));
	  //uldVO.setLocation(rs.getString("LOC"));
	  uldVO.setManufacturer(rs.getString("MFR"));
	  uldVO.setUldPrice(rs.getDouble("ULDPRC"));
	  uldVO.setDisplayUldPrice(rs.getDouble("DISULDPRC"));
	  uldVO.setUldPriceUnit(rs.getString("ULDPRCUNT"));
	  uldVO.setUldSerialNumber(rs.getString("ULDSERNUM"));
	  if(rs.getDate("PURDAT")!=null){
		  uldVO.setPurchaseDate(new LocalDate(uldVO.getCurrentStation() , Location.ARP , rs.getDate("PURDAT")));
	  }
	  uldVO.setPurchaseInvoiceNumber(rs.getString("PURINVNUM"));
	  uldVO.setOverallStatus(rs.getString("OVLSTA"));
	  uldVO.setOwnerStation(rs.getString("OWNARP"));
	  uldVO.setOperationalAirlineIdentifier(Integer.valueOf(rs.getString("OPRARLIDR")));
	  uldVO.setOperationalAirlineCode(rs.getString("OPRARLCOD"));
	  uldVO.setOperationalOwnerAirlineCode(rs.getString("OWNARLCOD"));
	  uldVO.setUldType(rs.getString("ULDTYP"));
	  uldVO.setVendor(rs.getString("VDR"));
	  uldVO.setRemarks(rs.getString("ULDRMK"));

	  uldVO.setCompanyCode(rs.getString("CMPCOD"));
	  uldVO.setUldNumber(rs.getString("ULDNUM"));
	  uldVO.setFacilityType(rs.getString("FACTYP"));
	  //uldVO.setFacilityType(rs.getString("FACCOD"));
	  
	  uldVO.setTransitStatus(rs.getString("TRNSTA"));
	  uldVO.setUldNature(rs.getString("ULDNAT"));
	  uldVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
	  uldVO.setUldGroupCode(rs.getString("ULDGRPCOD"));
	  if(rs.getTimestamp("LSTUPDTIM") != null){
		  uldVO.setLastUpdateTime(
				  new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("LSTUPDTIM")));  
	  }
	  uldVO.setOperationalAirlineIdentifier(rs.getInt("OPRARLIDR"));
	  if(rs.getDate("MANDAT")!=null){
		  uldVO.setManufactureDate(new LocalDate(uldVO.getCurrentStation() , Location.ARP , rs.getDate("MANDAT")));
	  }
	  uldVO.setLifeSpan(rs.getInt("LIFSPN"));
	  uldVO.setTsoNumber(rs.getString("TSONUM"));
	  //Commented by A-7359 for ICRD-268401 starts here 
	  /*//added by a-3045 for bug 24479 on 16Jan09 starts
	  final int seconds = 3600;
	  final int hours = 24;
	  final int millis = 1000 * seconds * hours;
	  double currValue = uldVO.getDisplayUldPrice();
	  LocalDate currentDate = new LocalDate(uldVO.getCurrentStation(),Location.ARP,true);
	  log.log(Log.FINE, "\n before depreciating currValue", currValue);
	if(uldVO.getPurchaseDate() != null){
		  long diffMillis = currentDate.findDifference(uldVO.getPurchaseDate());
		  long dayDiff = diffMillis / millis;
		  long yearCount = dayDiff/365;
		  double depreciationPercent = rs.getDouble("ULDDEPPTG");
		  for(int i=0;i < yearCount;i++){
			  currValue = currValue *((100 - depreciationPercent)/100);
		  }   
		  log.log(Log.FINE, "\n dayDiff", dayDiff);
		log.log(Log.FINE, "\n depreciationPercent", depreciationPercent);
		log.log(Log.FINE, "\n currValue", currValue);	   
	  }
	  uldVO.setDisplayCurrentValue(currValue);
	  //added by a-3045 for bug 24479 on 16Jan09 ends
*/	
	  //Commented by A-7359 for ICRD-268401 ends here 
	  //added by a-6344 for ICRD-55460 starts
	  uldVO.setReconciliationSeqNum(rs.getString("SEQNUM")); 
	  uldVO.setScmStatusFlag(rs.getString("ULDSTKSTA"));
	  uldVO.setScmMessageSendingFlag(rs.getString("MSGSNDFLG"));
	  if(rs.getTimestamp("STKCHKDAT")!=null){
	  uldVO.setStockCheckDate(new LocalDate(uldVO.getCurrentStation() , Location.ARP , rs.getTimestamp("STKCHKDAT")));
	  }//added by a-6344 for ICRD-55460 ends
		if (ULDVO.SCM_MISSING_STOCK.equals(rs.getString("DISCOD"))) {
			uldVO.setMissingDiscrepancyCaptured(true);
		}
	  
	  return uldVO;

	}

}
