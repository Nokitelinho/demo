/*
 * UCMReconcileMultiMapper.java Created on jul 13, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 */

package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
	public class UCMReconcileMultiMapper implements MultiMapper<ULDFlightMessageReconcileVO> {
		  private Log log=LogFactory.getLogger("ULD_DEFAULTS");
	  
		  /**
	       * @param rs
	       * @return 
	       * @throws SQLException
	       */
	 public List<ULDFlightMessageReconcileVO> map(ResultSet rs) throws SQLException{
		 log.entering("UCMReconcileMultiMapper","UCMReconcileMultiMapper"); 
		 
		  List<ULDFlightMessageReconcileVO> reconcileVOs = new ArrayList<ULDFlightMessageReconcileVO>();
		  ULDFlightMessageReconcileVO reconcileVO = null;
		  Collection <ULDFlightMessageReconcileDetailsVO> detailVOs = null;
		  ULDFlightMessageReconcileDetailsVO detailVO = null;
		  String 	presId ="";
	      String prevId = ""; 
	    	
	      String childPresIdOne = "";
		  String childPrevIdTwo = "";
		  
		  while (rs.next()) {
			  presId = new StringBuffer(rs.getInt("FLTCARIDR"))
						     .append(rs.getString("FLTNUM"))
						      .append(rs.getInt("FLTSEQNUM"))
						        .append(rs.getString("ARPCOD"))
						         .append(rs.getString("CMPCOD"))
						          .append(rs.getString("SEQNUM"))
						           .append(rs.getString("MSGTYP"))
						          .toString();
			  if(!presId.equals(prevId)){
				  
				  if(reconcileVO != null){
					  reconcileVO.setReconcileDetailsVOs(new ArrayList<ULDFlightMessageReconcileDetailsVO> (detailVOs));
					  reconcileVOs.add(reconcileVO);
				  }
				  
				  reconcileVO = new ULDFlightMessageReconcileVO();
				  
				  reconcileVO.setCompanyCode(rs.getString("CMPCOD"));
				  reconcileVO.setFlightCarrierIdentifier(rs.getInt("FLTCARIDR"));
				  reconcileVO.setFlightNumber(rs.getString("FLTNUM"));
				  reconcileVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				  reconcileVO.setAirportCode(rs.getString("ARPCOD"));
				  reconcileVO.setMessageType(rs.getString("MSGTYP"));
				  reconcileVO.setSequenceNumber(rs.getString("SEQNUM"));
				  reconcileVO.setErrorCode(rs.getString("UCMERRCOD"));
				  reconcileVO.setCarrierCode(rs.getString("ARLCOD"));
				  reconcileVO.setOutSequenceNumber(rs.getString("OUTSEQNUM"));
				  
				  
				  if(rs.getDate("FLTDAT") != null) {
					  reconcileVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("FLTDAT")) );
				  }
				 
				  reconcileVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
				  
				  // Added by A-2412 on 13 th Nov 
				  if(rs.getTimestamp("LSTUPDTIM") != null){
					  reconcileVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION , 
								Location.NONE , rs.getTimestamp("LSTUPDTIM")));
				  }
				  // Addition by A-2412 on 13 th Nov ends 
				  prevId = presId ;
				  detailVOs = new HashSet<ULDFlightMessageReconcileDetailsVO>();
				 
			  }
			  childPresIdOne = new StringBuffer(rs.getInt("FLTCARIDR"))
									     .append(rs.getString("FLTNUM"))
									      .append(rs.getInt("FLTSEQNUM"))
									        .append(rs.getString("ARPCOD"))
									         .append(rs.getString("CMPCOD"))
									         .append(rs.getString("SEQNUM"))
									         .append(rs.getString("MSGTYP"))
									          .append(rs.getString("ULDNUM"))
									          .toString();
			  if(!childPresIdOne.equalsIgnoreCase(childPrevIdTwo)	) {
				 // if(detailVO != null) {
				//	  detailVOs.add(detailVO);
				 // }
				  detailVO = new ULDFlightMessageReconcileDetailsVO();
				  detailVO.setCompanyCode(rs.getString("CMPCOD"));
				  detailVO.setFlightCarrierIdentifier(rs.getInt("FLTCARIDR"));
				  detailVO.setFlightNumber(rs.getString("FLTNUM"));
				  detailVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("FLTDAT")) );
				  detailVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				  detailVO.setAirportCode(rs.getString("ARPCOD"));
				  detailVO.setUldNumber(rs.getString("ULDNUM"));
				  detailVO.setSequenceNumber(rs.getString("SEQNUM"));
				  detailVO.setMessageType(rs.getString("MSGTYP"));
				  detailVO.setPou(rs.getString("POU"));
				  detailVO.setContent(rs.getString("CNT"));
				  detailVO.setDamageStatus(rs.getString("DMGSTA"));
				  detailVO.setCarrierCode(rs.getString("ARLCOD"));
				  detailVO.setErrorCode(rs.getString("ULDERRCOD"));
				  
				  childPrevIdTwo = childPresIdOne;
				  
				  detailVOs.add(detailVO);	  
				  
			  }
			 
		  }
		  if(reconcileVO != null ) {
			  if(detailVO != null ) {
				  detailVOs.add(detailVO);
			  }
			  if(detailVOs != null && detailVOs.size() >0 ) {
				  reconcileVO.setReconcileDetailsVOs(new ArrayList<ULDFlightMessageReconcileDetailsVO> (detailVOs)); 
			  }
			  reconcileVOs.add(reconcileVO);
		  }
	
		return reconcileVOs;
     }
  }