/*
 * ContainerMapperForDestination.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * This class is used to map the ResultSet into VO
 * @author a-1936
 *
 */

public class ContainerMapperForDestination implements Mapper<ContainerVO> {

	
	private static final Log LOG = LogFactory.getLogger("Mail Operations");
	/**
	 * This method is used to map the ContainerDetails in to
	 * the ContainerVO
	 * @author a-1936
	 * @param rs
	 * @return 
	 * @throws SQLException
	 */
	 
	public  ContainerVO  map(ResultSet rs)
	     throws SQLException{
		    
		   ContainerVO  containerVO = new ContainerVO(); 
		   String assignedPort=rs.getString("ASGPRT");
		   containerVO.setCompanyCode(rs.getString("CMPCOD"));
		   containerVO.setContainerNumber(rs.getString("CONNUM"));
		   containerVO.setAssignedPort(assignedPort);
		   containerVO.setCarrierId(rs.getInt("FLTCARIDR"));
		   containerVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		   containerVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		   containerVO.setFlightNumber(rs.getString("FLTNUM"));
		   containerVO.setFinalDestination(rs.getString("DSTCOD"));
		   if(rs.getTimestamp("ASGDAT")!=null){
		     containerVO.setAssignedDate(new LocalDate(assignedPort,Location.ARP,rs.getTimestamp("ASGDAT")));
		   }
		  
		   containerVO.setPou(rs.getString("POU"));
		   containerVO.setAssignedUser(rs.getString("USRCOD"));
		   containerVO.setRemarks(rs.getString("RMK"));
		   containerVO.setType(rs.getString("CONTYP"));
		   containerVO.setBags(rs.getInt("BAGCNT"));  
		   //containerVO.setWeight(rs.getInt("BAGWGT")); 
		   containerVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getInt("BAGWGT")));//added by A-7371
		   containerVO.setAcceptanceFlag(rs.getString("ACPFLG"));
		   containerVO.setCarrierCode(rs.getString("MSTFLTCARCOD"));
		   Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
		   if(lstUpdTime != null) {
			   containerVO.setLastUpdateTime(
					   new LocalDate(LocalDate.NO_STATION, 
							   Location.NONE, lstUpdTime));
		   }
		   Timestamp uldLastUpdTim = rs.getTimestamp("ULDLSTUPDTIM");
		   if(uldLastUpdTim != null) {
			   containerVO.setULDLastUpdateTime(
					   new LocalDate(LocalDate.NO_STATION,
							   Location.NONE, uldLastUpdTim));
		   }
		   containerVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		   containerVO.setULDLastUpdateUser(rs.getString("ULDLSTUPDUSR"));
		   containerVO.setContainerJnyID(rs.getString("CONJRNIDR"));
		   containerVO.setShipperBuiltCode(rs.getString("SBCODE"));
		   containerVO.setTransitFlag(rs.getString("TRNFLG"));
		   containerVO.setReleasedFlag(rs.getString("RELFLG"));
		   //Added by A-3429 for ICRD-83340
		   containerVO.setSubclassGroup(rs.getString("SUBCLSGRP"));
		   containerVO.setUldFulIndFlag(rs.getString("ULDFULIND")); 
		    containerVO.setActWgtSta(rs.getString("ACTWGTSTA"));
		   containerVO.setUldReferenceNo(rs.getLong("ULDREFNUM"));
		   if(rs.getString("SCNPRT") != null && rs.getTimestamp("SCNDAT") != null){
				LocalDate scnDate = new LocalDate(rs.getString("SCNPRT") ,Location.ARP, rs.getTimestamp("SCNDAT"));
			//scnDate.setDateAndTime(rs.getString("SCNDAT"));
				LocalDate currentDate = new LocalDate(rs.getString("SCNPRT") ,Location.ARP, true);
				long timeDiff = currentDate.findDifference(scnDate);
				int timediffIndays = (int)(timeDiff/(1000*60*60*24));
				if(timeDiff % (1000*60*60*24) > 0 ){
					timediffIndays = timediffIndays + 1;
				}
				containerVO.setNoOfDaysInCurrentLoc(timediffIndays);
			}
		   if(rs.getString("CNTIDR") != null && rs.getString("CNTIDR").trim().length()>0){
		   containerVO.setContentId(rs.getString("CNTIDR"));  //Added by A-7929 as part of ICRD-219699
		   }
		 //Added by A-8672 as part of ICRD-212042
		 //Modified by A-7540 for IASCB-25432
		  Measure actualWeight = new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACTULDWGT")); 
		  containerVO.setActualWeight(actualWeight); 		
		  containerVO.setPlannedFlightNum(rs.getInt("PLNFLTNUM"));
		  containerVO.setPlannedFlightCarrierCode(rs.getString("PLNFLTCARCOD"));
			if (rs.getTimestamp("PLNFLTDAT") != null) {
				containerVO.setPlannedFlightDate(new LocalDate(assignedPort,
						Location.ARP, rs.getTimestamp("PLNFLTDAT")));
			}
			containerVO.setExpClsflg(rs.getString("CLSFLG"));
			containerVO.setHbaMarked(rs.getString("HBAMARKED"));
			
			Money amount;		 	 
			 try {
					amount = CurrencyHelper.getMoney(rs.getString("BASCURCOD"));
					amount.setAmount(rs.getDouble("PROCHG"));   
					containerVO.setProvosionalCharge(amount);						
				} catch (CurrencyException e) {
					LOG.log(Log.INFO,e);
				}
			 if(containerVO.getBags()!=rs.getInt("RATEDCOUNT")) {
				 containerVO.setRateAvailforallMailbags("N"); 
			 }
			 else {
				 containerVO.setRateAvailforallMailbags("Y");  
			 }
			 containerVO.setBaseCurrency(rs.getString("BASCURCOD"));
		 return containerVO;
	 }
	
	
}
