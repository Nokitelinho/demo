/*
 * ULDFlightReconcileMultiMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;


import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

    /**
     * 
     * @author A-2048
     *
     */
	public class ULDFlightReconcileMultiMapper implements MultiMapper<ULDFlightMessageReconcileVO> {
		  private Log log=LogFactory.getLogger("ULD_DEFAULTS");
	  
		  /**
	       * @param rs
	       * @return 
	       * @throws SQLException
	       */
	 public List<ULDFlightMessageReconcileVO> map(ResultSet rs) throws SQLException{
		 log.entering("INSIDE THE MAPPER","ULDFlightReconcileMultiMapper"); 
		 
		  List<ULDFlightMessageReconcileVO> reconcileVOs = new ArrayList<ULDFlightMessageReconcileVO>();
		  ULDFlightMessageReconcileVO reconcileVO = null;
		  ArrayList <ULDFlightMessageReconcileDetailsVO> detailVOs = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		  ULDFlightMessageReconcileDetailsVO detailVO = null;
		  String 	presId ="";
	      String prevId = ""; 
	    	
	      String childPresIdOne = "";
		  String childPrevIdOne = "";
		  ArrayList<String> pkVals= new ArrayList<String>();
		  ArrayList<String> childpkVals= new ArrayList<String>();
		  while (rs.next()) {
			  presId =new StringBuffer(rs.getInt("FLTCARIDR"))
						     .append(rs.getString("FLTNUM"))
						      .append(rs.getInt("FLTSEQNUM"))
						      // .append(rs.getInt("LEGSERNUM"))
						        .append(rs.getString("ARPCOD"))
						         .append(rs.getString("CMPCOD"))
						          .toString();
			  if(!pkVals.contains(presId)){
				  if(reconcileVO!=null){
					  reconcileVOs.add(reconcileVO);
					  log.log(Log.INFO, "PK value--->", reconcileVO);
				  }
				  pkVals.add(presId);
				  reconcileVO = new ULDFlightMessageReconcileVO();
				  reconcileVO.setCompanyCode(rs.getString("CMPCOD"));
				  reconcileVO.setFlightCarrierIdentifier(rs.getInt("FLTCARIDR"));
				  reconcileVO.setFlightNumber(rs.getString("FLTNUM"));
				  reconcileVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				  reconcileVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				  reconcileVO.setAirportCode(rs.getString("ARPCOD"));
				  reconcileVO.setMessageType(rs.getString("MSGTYP"));
				  reconcileVO.setErrorCode(rs.getString("UCMERRCOD"));
				  
				  if(rs.getDate("FLTDAT") != null) {
					  reconcileVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("FLTDAT")) );
				  }
				 
				  reconcileVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
				  reconcileVO.setReconcileDetailsVOs(new ArrayList<ULDFlightMessageReconcileDetailsVO>());
				  prevId = presId ;
				  
				 
				 
			  }
			  /*childPresIdOne = new StringBuffer(rs.getInt("FLTCARIDR"))
									     .append(rs.getString("FLTNUM"))
									      .append(rs.getInt("FLTSEQNUM"))
									       .append(rs.getInt("LEGSERNUM"))
									        .append(rs.getString("ARPCOD"))
									         .append(rs.getString("CMPCOD"))
									          .append(rs.getString("ULDNUM"))
									          .toString();*/
			  //if(!childpkVals.contains(childPresIdOne)	) {
				 // childpkVals.add(childPresIdOne);
				
				  detailVO = new ULDFlightMessageReconcileDetailsVO();
				  detailVO.setCompanyCode(rs.getString("CMPCOD"));
				  detailVO.setFlightCarrierIdentifier(rs.getInt("FLTCARIDR"));
				  detailVO.setFlightNumber(rs.getString("FLTNUM"));
				  detailVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				  detailVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				  detailVO.setAirportCode(rs.getString("ARPCOD"));
				  detailVO.setUldNumber(rs.getString("ULDNUM"));
				  detailVO.setPou(rs.getString("POU"));
				  detailVO.setContent(rs.getString("CNT"));
				  detailVO.setDamageStatus(rs.getString("DMGSTA"));
				  detailVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("FLTDAT")) );
				  reconcileVO.getReconcileDetailsVOs().add(detailVO);
				  childPrevIdOne = childPresIdOne;
				  
			// }
			 
		  }
		 /* if(reconcileVO != null ) {
			  if(detailVO != null ) {
				  detailVOs.add(detailVO);
			  }
			  if(detailVOs != null && detailVOs.size() >0 ) {
				  reconcileVO.setReconcileDetailsVOs(new ArrayList<ULDFlightMessageReconcileDetailsVO> (detailVOs)); 
			  }
			  reconcileVOs.add(reconcileVO);
		  }*/
		  if(reconcileVO!=null){
			  reconcileVOs.add(reconcileVO);
		  }
	log.log(Log.INFO, "Reconcile VOs in server----->", reconcileVOs);
		return reconcileVOs;
     }
  }
