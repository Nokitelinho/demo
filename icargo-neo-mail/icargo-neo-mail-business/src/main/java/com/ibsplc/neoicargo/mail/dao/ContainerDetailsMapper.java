/*
 * ContainerMapperForDestination.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;



/**
 * This class is used to map the ResultSet into VO
 * @author a-1936
 *
 */

public class ContainerDetailsMapper implements Mapper<ContainerVO> {

	
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
		   LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		   Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
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
			containerVO.setAssignedDate(localDateUtil.getLocalDate(assignedPort,rs.getTimestamp("ASGDAT")));
		   }
		  
		   containerVO.setPou(rs.getString("POU"));
		   containerVO.setAssignedUser(rs.getString("USRCOD"));
		   containerVO.setRemarks(rs.getString("RMK"));
		   containerVO.setType(rs.getString("CONTYP"));
		   containerVO.setBags(rs.getInt("BAGCNT"));  
		   //containerVO.setWeight(rs.getInt("BAGWGT")); 
		   containerVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getInt("BAGWGT"))));//added by A-7371
		   containerVO.setAcceptanceFlag(rs.getString("ACPFLG"));
		   containerVO.setCarrierCode(rs.getString("MSTFLTCARCOD"));
		   containerVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		   containerVO.setOffloadFlag(rs.getString("OFLFLG"));
		   containerVO.setArrivedStatus(rs.getString("ARRSTA"));
		   containerVO.setTransferFlag(rs.getString("TRAFLG"));
		   Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
		   if(lstUpdTime != null) {
			   containerVO.setLastUpdateTime(
					   localDateUtil.getLocalDate(null,
							    lstUpdTime));
		   }
		   containerVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		   containerVO.setContainerJnyID(rs.getString("CONJRNIDR"));
		   containerVO.setShipperBuiltCode(rs.getString("SBCODE"));
		   containerVO.setTransitFlag(rs.getString("TRNFLG"));
		   if(rs.getString("CNTIDR") != null && rs.getString("CNTIDR").trim().length()>0){
		   containerVO.setContentId(rs.getString("CNTIDR"));  //Added by A-7929 as part of ICRD-219699
		   }
		  Quantity actualWeight = quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(rs.getDouble("ACTULDWGT")));
		  containerVO.setActualWeight(actualWeight);
		  return containerVO;
	 }
	
	
}
