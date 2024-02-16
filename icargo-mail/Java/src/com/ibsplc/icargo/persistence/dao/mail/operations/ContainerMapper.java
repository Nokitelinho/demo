/*
 * ContainerMapper.java Created on May 29, 2006
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
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1303
 * This class is used to map the Resultset in to the ContainerVOs.
 */
public class ContainerMapper implements Mapper<ContainerVO> {
   private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");	
	
	/**
	 * This method is used to map all the 
	 * records from the resultset in to the ContainerVO
	 * @param rs
	 * @return ContainerVO
	 * @throws SQLException
	 * 
	 */
      
    public ContainerVO map(ResultSet rs) throws SQLException {
         log.entering("Container Mapper","map(ResultSet rs)");
         
         String assignedPort=rs.getString("ASGPRT");
    	 ContainerVO containerVo = new ContainerVO();
         containerVo.setCompanyCode(rs.getString("CMPCOD"));
         containerVo.setContainerNumber(rs.getString("CONNUM"));
         containerVo.setAssignedPort(assignedPort);
         containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
         containerVo.setFlightNumber(rs.getString("FLTNUM"));
         containerVo.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
         containerVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));
         if(rs.getTimestamp("ASGDAT")!=null){
          containerVo.setAssignedDate(new LocalDate(assignedPort,
        		  Location.ARP,rs.getTimestamp("ASGDAT")));
         }
         containerVo.setAssignedUser(rs.getString("USRCOD")); 
         containerVo.setCarrierCode(rs.getString("FLTCARCOD")); 
         containerVo.setPou(rs.getString("POU"));
         containerVo.setRemarks(rs.getString("RMK"));
         containerVo.setType(rs.getString("CONTYP"));
         containerVo.setFinalDestination(rs.getString("DSTCOD"));
         containerVo.setPaBuiltFlag(rs.getString("POAFLG"));
         containerVo.setBags(rs.getInt("ACPBAG"));
	     containerVo.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")));//added by A-7371
	     //containerVo.setWeight(rs.getDouble("ACPWGT"));
	     containerVo.setAcceptanceFlag(rs.getString("ACPFLG")); 
	     containerVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));  
	     containerVo.setOffloadFlag(rs.getString("OFLFLG")); 
	     containerVo.setArrivedStatus(rs.getString("ARRSTA"));
	     containerVo.setContainerJnyID(rs.getString("CONJRNIDR"));
	     containerVo.setShipperBuiltCode(rs.getString("SBCODE"));	
		 containerVo.setWarehouseCode(rs.getString("WHSCOD"));	
		 //Added by A-5945 for ICRD-99708
	     containerVo.setIntact(rs.getString("INTFLG"));
		 containerVo.setTransitFlag(rs.getString("TRNFLG"));
	     Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
	     if(lstUpdTime != null) {
	    	 containerVo.setLastUpdateTime(
	    		 new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
	     }
	     Timestamp uldLstUpdTime = rs.getTimestamp("ULDLSTUPDTIM");
	     if(uldLstUpdTime != null) {
	     	containerVo.setULDLastUpdateTime(
	     		new LocalDate(LocalDate.NO_STATION, Location.NONE, uldLstUpdTime));
	     }
	     containerVo.setLastUpdateUser(rs.getString("LSTUPDUSR"));
	     containerVo.setULDLastUpdateUser(rs.getString("ULDLSTUPDUSR"));
	     log.log(Log.FINE, "The ContainerVO in ContainerMapper", containerVo);
		log.exiting("Container Mapper","map(ResultSet rs)"); 
    	 return containerVo;
    }

}
